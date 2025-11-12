package grupo4.facturacion.service;

import grupo4.facturacion.client.TarifaFeignClient;
import grupo4.facturacion.client.UsuarioFeignClient;
import grupo4.facturacion.dto.TarifaDTO;
import grupo4.facturacion.dto.TotalFacturadoDTO;
import grupo4.facturacion.dto.UsuarioPremiumDTO;
import grupo4.facturacion.dto.ViajeFacturaRequestDTO;
import grupo4.facturacion.entity.Factura;

import grupo4.facturacion.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private TarifaFeignClient tarifaFeignClient;

    @Autowired
    private UsuarioFeignClient usuarioFeignClient;

    @Transactional(readOnly = true)
    public List<Factura> findAll(){
        return facturaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Factura findById(Long id){
        return facturaRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Factura> findByFechaBetween(Date fecha1, Date fecha2){
        return facturaRepository.findByFechaBetween(fecha1,fecha2);
    }

    @Transactional
    public Factura save(Factura factura){
        return facturaRepository.save(factura);
    }

    @Transactional
    public void delete(Long id){
        facturaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public TotalFacturadoDTO getTotalFacturadoPorRangoMeses(int anio, int mesDesde, int mesHasta) {
        TotalFacturadoDTO totalFacturado = new TotalFacturadoDTO();
        totalFacturado.setAnio(anio);
        totalFacturado.setMesDesde(mesDesde);
        totalFacturado.setMesHasta(mesHasta);
        totalFacturado.setTotalFacturado(facturaRepository.getTotalFacturadoPorRangoMeses(anio, mesDesde, mesHasta));
        return totalFacturado;
    }

    /**
     * Crea una factura al finalizar un viaje
     * Considera usuarios premium con cupo de km gratuito
     */
    @Transactional
    public Factura crearFacturaDesdeViaje(ViajeFacturaRequestDTO request) {
        // 1. Obtener la tarifa desde el microservicio
        TarifaDTO tarifa = tarifaFeignClient.findTarifaById(request.getTarifaId()).getBody();
        if (tarifa == null) {
            throw new RuntimeException("Tarifa no encontrada con ID: " + request.getTarifaId());
        }

        // 2. Obtener información del usuario premium (si aplica)
        UsuarioPremiumDTO usuario = null;
        try {
            usuario = usuarioFeignClient.getUsuarioPremium(request.getUsuarioId()).getBody();
        } catch (Exception e) {
            // Si falla, asumimos que no es premium o el servicio no está disponible
            usuario = null;
        }

        // 3. Calcular el importe considerando si es premium
        Double importe = calcularImporteConPremium(
                request.getDistanciaKm(),
                request.getTiempoPausaMinutos(),
                tarifa,
                usuario
        );

        // 4. Actualizar km consumidos del usuario premium si aplica
        if (usuario != null && usuario.isEsPremium()) {
            try {
                usuarioFeignClient.actualizarKmConsumidos(
                        usuario.getId(),
                        request.getDistanciaKm()
                );
            } catch (Exception e) {
                // Log del error pero no interrumpimos la facturación
                System.err.println("Error actualizando km consumidos: " + e.getMessage());
            }
        }

        // 5. Crear la factura
        Factura factura = new Factura();
        factura.setNumeroFactura(generarNumeroFactura());
        factura.setFechaEmision(new Date());
        factura.setImporte(importe);
        factura.setUsuarioId(request.getUsuarioId());
        factura.setTarifaId(request.getTarifaId());
        factura.setViajeId(request.getViajeId());

        // 6. Guardar en base de datos
        return facturaRepository.save(factura);
    }

    /**
     * Calcula el importe considerando:
     * - Usuario Premium con cupo mensual
     * - Recargo por pausa
     */
    private Double calcularImporteConPremium(Double distanciaKm, Integer tiempoPausaMinutos,
                                              TarifaDTO tarifa, UsuarioPremiumDTO usuario) {

        // CASO 1: Usuario NO es premium - calcula normalmente
        if (usuario == null || !usuario.isEsPremium()) {
            return calcularImporteNormal(distanciaKm, tiempoPausaMinutos, tarifa);
        }

        // CASO 2: Usuario ES premium
        Double kmRestantesCupo = usuario.getCupoMensualKm() - usuario.getKmConsumidosMes();

        // Si aún tiene cupo disponible
        if (kmRestantesCupo > 0) {

            // 2a. Todo el viaje cabe en el cupo gratuito
            if (distanciaKm <= kmRestantesCupo) {
                // Solo cobra tarifa fija + recargo por pausa si aplica
                return calcularSoloTarifaFija(tiempoPausaMinutos, tarifa);
            }

            // 2b. Parte del viaje es gratis, el resto se cobra al 50%
            Double kmExcedentes = distanciaKm - kmRestantesCupo;
            return calcularImporteParcialPremium(kmExcedentes, tiempoPausaMinutos, tarifa);
        }

        // 2c. Ya agotó su cupo - cobra 50% de la tarifa
        return calcularImportePremiumSinCupo(distanciaKm, tiempoPausaMinutos, tarifa);
    }

    /**
     * Cálculo para usuario normal (sin premium)
     */
    private Double calcularImporteNormal(Double distanciaKm, Integer tiempoPausaMinutos, TarifaDTO tarifa) {
        Double importeVariable = distanciaKm * tarifa.getMonto();
        Double tarifaFija = tarifa.getMontoExtra();
        Double importeBase = importeVariable + tarifaFija;

        // Verificar si aplica recargo por pausa
        if (tiempoPausaMinutos != null &&
            tarifa.getTiempoMaximoPausaMinutos() != null &&
            tarifa.getPorcentajeRecargoPausa() != null &&
            tiempoPausaMinutos > tarifa.getTiempoMaximoPausaMinutos()) {

            Double recargo = importeBase * tarifa.getPorcentajeRecargoPausa();
            return importeBase + recargo;
        }

        return importeBase;
    }

    /**
     * Solo cobra tarifa fija para premium dentro del cupo
     */
    private Double calcularSoloTarifaFija(Integer tiempoPausaMinutos, TarifaDTO tarifa) {
        Double importeBase = tarifa.getMontoExtra();

        // Aplicar recargo por pausa si corresponde
        if (tiempoPausaMinutos != null &&
            tarifa.getTiempoMaximoPausaMinutos() != null &&
            tarifa.getPorcentajeRecargoPausa() != null &&
            tiempoPausaMinutos > tarifa.getTiempoMaximoPausaMinutos()) {

            Double recargo = importeBase * tarifa.getPorcentajeRecargoPausa();
            return importeBase + recargo;
        }

        return importeBase;
    }

    /**
     * Calcula cuando parte del viaje es gratis y parte al 50%
     */
    private Double calcularImporteParcialPremium(Double kmExcedentes, Integer tiempoPausaMinutos, TarifaDTO tarifa) {
        // Km excedentes se cobran al 50%
        Double importeVariable = kmExcedentes * tarifa.getMonto() * 0.5;
        Double tarifaFija = tarifa.getMontoExtra();
        Double importeBase = importeVariable + tarifaFija;

        // Aplicar recargo por pausa si corresponde
        if (tiempoPausaMinutos != null &&
            tarifa.getTiempoMaximoPausaMinutos() != null &&
            tarifa.getPorcentajeRecargoPausa() != null &&
            tiempoPausaMinutos > tarifa.getTiempoMaximoPausaMinutos()) {

            Double recargo = importeBase * tarifa.getPorcentajeRecargoPausa();
            return importeBase + recargo;
        }

        return importeBase;
    }

    /**
     * Calcula cuando el premium ya agotó su cupo - cobra 50%
     */
    private Double calcularImportePremiumSinCupo(Double distanciaKm, Integer tiempoPausaMinutos, TarifaDTO tarifa) {
        // Todos los km se cobran al 50%
        Double importeVariable = distanciaKm * tarifa.getMonto() * 0.5;
        Double tarifaFija = tarifa.getMontoExtra();
        Double importeBase = importeVariable + tarifaFija;

        // Aplicar recargo por pausa si corresponde
        if (tiempoPausaMinutos != null &&
            tarifa.getTiempoMaximoPausaMinutos() != null &&
            tarifa.getPorcentajeRecargoPausa() != null &&
            tiempoPausaMinutos > tarifa.getTiempoMaximoPausaMinutos()) {

            Double recargo = importeBase * tarifa.getPorcentajeRecargoPausa();
            return importeBase + recargo;
        }

        return importeBase;
    }

    /**
     * Genera un número de factura único
     */
    private String generarNumeroFactura() {
        return "FACT-" + System.currentTimeMillis() + "-" +
               UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
