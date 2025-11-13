package grupo4.viajes.services;

import grupo4.viajes.dtos.ReporteViajePeriodoDTO;
import grupo4.viajes.dtos.ReporteViajeUsuariosDTO;
import grupo4.viajes.dtos.ViajePatch;
import grupo4.viajes.dtos.ViajeDTO;
import grupo4.viajes.feignClients.*;
import grupo4.viajes.feignModels.*;
import grupo4.viajes.model.Viaje;
import grupo4.viajes.repository.ViajeRepository;
import grupo4.viajes.services.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ViajeService {

    private final ViajeRepository repository;

    private final MonopatinFeignClient monopatinFeignClient;
    private final CuentaFeignClient cuentaFeignClient;
    private final UsuarioFeignClient usuarioFeignClient;
    private final TarifaFeignClient tarifaFeignClient;
    private final ParadaFeignClient paradaFeignClient;
    private final FacturacionFeignClient facturacionFeignClient;

    // Metodo para iniciar alquiler de viaje
    @Transactional
    public ViajeDTO save(Viaje viaje) {
        // Busco el monopatin y veo si devuelve algo
        Monopatin monopatin = monopatinFeignClient.findById(viaje.getIdMonopatin());
        if(monopatin == null)
            throw new NotFoundException("Monopatin",viaje.getIdMonopatin());
        if(!monopatin.getEstado().equalsIgnoreCase("LIBRE"))
            throw new MonopatinNoDisponibleException(viaje.getIdMonopatin(),monopatin.getEstado());


        // Busco la cuenta y veo si devuelve algo
        Cuenta cuenta = cuentaFeignClient.findById(viaje.getIdCuenta());
        if(cuenta == null)
            throw new NotFoundException("Cuenta",viaje.getIdCuenta());
        if(cuenta.getSaldo().compareTo(BigDecimal.ZERO) == 0)
            throw new SaldoCuentaInsuficienteException(cuenta.getId());

        // Busco el usuario y veo si devuelve algo
        Usuario usuario = usuarioFeignClient.findById(viaje.getIdUsuario());
        if(usuario == null)
            throw new NotFoundException("Usuario",viaje.getIdUsuario());
        // La cuenta no esta asociada al usuario
        if(!usuarioFeignClient.cuentaAsociada(usuario.getId(),cuenta.getId()))
            throw new CuentaNoAsociadaException(usuario.getId());


        // Busco la parada y veo si devuelve algo
        Parada paradaInicio = paradaFeignClient.findById(viaje.getParadaOrigen());
        Parada paradaFin = paradaFeignClient.findById(viaje.getParadaDestino());
        if(paradaInicio == null || paradaFin == null)
            throw new NotFoundException("Parada",viaje.getParadaOrigen().toString());


        // Obtengo la tarifa
        Tarifa tarifa = tarifaFeignClient.findTarifaById(viaje.getIdTarifa());
        if(tarifa == null)
            throw new NotFoundException("Tarifa",viaje.getIdTarifa());

        // Seteo el estado del monopatin a activo
        monopatin = monopatinFeignClient.updateEstado(monopatin.getId(),"activo");

        return new ViajeDTO(repository.save(viaje));
    }

    @Transactional
    public Map<String,Object> finalizarViaje(Long id) {
        Viaje viaje = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Viaje", id));

        if(!viaje.isActivo())
            throw new ViajeFinalizadoException(id);

        Parada paradaDestino = paradaFeignClient.findById(viaje.getParadaDestino());
        if(paradaDestino == null)
            throw new NotFoundException("Parada", viaje.getParadaDestino().toString());

        Monopatin monopatin = monopatinFeignClient.findById(viaje.getIdMonopatin());
        if(monopatin == null)
            throw new NotFoundException("Monopatin",viaje.getIdMonopatin());

        // Verifico que la parada destino sea valida y el monopatin este en ese lugar
        if(monopatin.getLatitud() != paradaDestino.getLatitud() &&
           monopatin.getLongitud() != paradaDestino.getLongitud()) {
            System.out.println("entro monopatin latitud");
            throw new DestinoNoValidoException(viaje.getParadaDestino());
        }

        // Precalculo el monto a reservar y veo si la cuenta los tiene antes
        Cuenta cuenta = cuentaFeignClient.findById(viaje.getIdCuenta());
        Tarifa tarifa = tarifaFeignClient.findTarifaById(viaje.getIdTarifa());
        BigDecimal montoArestar = precalcularCostoViaje(tarifa, viaje);
        if(montoArestar.compareTo(cuenta.getSaldo()) > 0) {
            throw new SaldoCuentaInsuficienteException(cuenta.getId());
        }
        System.out.println("pase el monto a calcular");
        // Genero la factura con el viaje
        FacturaEmitida facturaEcha = facturacionFeignClient.generarFactura(new Factura(viaje));
        if(facturaEcha != null) {
            // Resto el saldo de la cuenta
            cuenta = cuentaFeignClient.restarSaldo(cuenta.getId(),montoArestar);
        }

        // Seteo el estado del viaje a finalizado
        viaje.setFechaFin(LocalDateTime.now());
        viaje.setActivo(false);
        repository.save(viaje);
        System.out.println("llego hasta seteo del monopatin");
        // Seteo el estado del monopatin a libre
        monopatin = monopatinFeignClient.updateEstado(monopatin.getId(),"libre");

        // Genero un map con la factura y el viaje
        Map<String,Object> retorno = new HashMap<>();
        retorno.put("factura", facturaEcha);
        retorno.put("viaje", viaje);
        retorno.put("cuenta",cuenta);

        return retorno;
    }

    private BigDecimal precalcularCostoViaje(Tarifa tarifa, Viaje viaje) {
        Double montoBase = tarifa.getMonto();
        Double montoExtra = tarifa.getMontoExtra();
        Double precioBase = montoBase+montoExtra;

        if(tarifa.getTiempoMaximoPausaMinutos() != 0 && viaje.getTiempoTotalMinutos() > tarifa.getTiempoMaximoPausaMinutos()) {
            Double recargo = precioBase * tarifa.getPorcentajeRecargoPausa();
            return BigDecimal.valueOf(precioBase + recargo);
        } else {
            return BigDecimal.valueOf(precioBase);
        }
    }

    @Transactional(readOnly = true)
    public ViajeDTO findById(Long id) {
        return new ViajeDTO(repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Viaje", id)));
    }

    @Transactional(readOnly = true)
    public List<ViajeDTO> findAll() {
        return repository.findAll().stream().map(ViajeDTO::new).toList();
    }

    @Transactional
    public void deleteById(Long id) {
        Viaje viaje = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Viaje", id));

        repository.deleteById(id);
    }

    @Transactional
    public ViajeDTO patch(Long id, ViajePatch viajePatch) {
        Viaje viaje = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Viaje", id));

        if(viajePatch.paradaOrigen() != null)
            viaje.setParadaOrigen(viajePatch.paradaOrigen());
        if(viajePatch.paradaDestino() != null)
            viaje.setParadaDestino(viajePatch.paradaDestino());
        if(viajePatch.idTarifa() != null)
            viaje.setIdTarifa(viajePatch.idTarifa());
        if(viajePatch.idMonopatin() != null)
            viaje.setIdMonopatin(viajePatch.idMonopatin());
        if(viajePatch.idUsuario() != null)
            viaje.setIdUsuario(viajePatch.idUsuario());
        if(viajePatch.idCuenta() != null)
            viaje.setIdCuenta(viajePatch.idCuenta());
        if(viajePatch.fechaInicio() != null)
            viaje.setFechaInicio(viajePatch.fechaInicio());
        if(viajePatch.fechaFin() != null)
            viaje.setFechaFin(viajePatch.fechaFin());
        if(viajePatch.tiempoTotalMinutos() != null)
            viaje.setTiempoTotalMinutos(viajePatch.tiempoTotalMinutos());
        if(viajePatch.kilometrosRecorridos() != null)
            viaje.setKilometrosRecorridos(viajePatch.kilometrosRecorridos());
        if(viajePatch.activo() != null)
            viaje.setActivo(viajePatch.activo());

        return new ViajeDTO(repository.save(viaje));
    }

    // Punto c
    @Transactional(readOnly = true)
    public List<ReporteViajePeriodoDTO> getReporteViajeAnio(Integer anio, Integer xViajes) {
        return repository.getReporteViajeAnio(anio, xViajes);
    }

    // Punto e
    @Transactional(readOnly = true)
    public List<ReporteViajeUsuariosDTO> getReporteViajesPorUsuariosPeriodo(Integer anioDesde, Integer anioHasta, String rol) {
        if (rol != null) {
            Set<Long> usuarios = usuarioFeignClient.getUsuarioByRol(rol);
            // Si no tengo nada quiere decir que el rol no existe
            if(usuarios.isEmpty())
                throw new IllegalArgumentException("Tipo de rol no existe");

            return repository.getReportesViajesPorUsuariosPeriodoPorTipoUsuario(anioDesde, anioHasta, usuarios);
        }
        // Si no me pasan un rol traigo todos los reportes de todos los usuarios
        return repository.getReportesViajesPorUsuariosPeriodo(anioDesde, anioHasta);
    }

    // Punto h
    @Transactional(readOnly = true)
    public Map<String, Object> getReportesUsuarioYasociadosPerido(Long idUsuario, Integer anioDesde, Integer anioHasta) {
        Usuario usuario = usuarioFeignClient.findById(idUsuario);
        if(usuario == null)
            throw new NotFoundException("Usuario",idUsuario);

        // Traigo todas las cuentas del usuario para usarlo en el query de jpql
        Set<Cuenta> cuentasUsuario = usuarioFeignClient.getCuentasByUsuario(idUsuario);
        
        // Extraigo los IDs de las cuentas
        Set<Long> cuentaIds = cuentasUsuario.stream()
                .map(Cuenta::getId)
                .collect(java.util.stream.Collectors.toSet());

        // Lista de reportes del usuario y ademas todos los que usen las cuentas
        List<ReporteViajeUsuariosDTO> reportes = repository.
                getReportesViajesPorUsuarioYcuentasAsociadasPeriodo(cuentaIds, anioDesde, anioHasta);

        // Remuevo el reporte  main buscado del usuario por parametro
        ReporteViajeUsuariosDTO reporteMain = reportes.stream()
                .filter(r -> r.getIdUsuario().equals(idUsuario)).findFirst().orElse(null);

        if(reporteMain != null)
            reportes.remove(reporteMain);

        Map<String, Object> retorno = new HashMap<>();
        retorno.put("reporte usuario buscado", reporteMain);
        retorno.put("usuarios asociado cuentas", reportes);

        return retorno;
    }
}
