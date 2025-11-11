package grupo4.viajes.services;

import grupo4.viajes.dtos.ReporteViajePeriodoDTO;
import grupo4.viajes.dtos.ReporteViajeUsuariosDTO;
import grupo4.viajes.dtos.ViajePatch;
import grupo4.viajes.dtos.ViajeDTO;
import grupo4.viajes.feignClients.*;
import grupo4.viajes.feignModels.Cuenta;
import grupo4.viajes.feignModels.Monopatin;
import grupo4.viajes.feignModels.Parada;
import grupo4.viajes.feignModels.Usuario;
import grupo4.viajes.model.Viaje;
import grupo4.viajes.repository.ViajeRepository;
import grupo4.viajes.services.exceptions.CuentaNoAsociada;
import grupo4.viajes.services.exceptions.MonopatinNoDisponible;
import grupo4.viajes.services.exceptions.NotFoundException;
import grupo4.viajes.services.exceptions.SaldoCuentaInsuficiente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViajeService {

    private final ViajeRepository repository;

    private final MonopatinFeignClient monopatinFeignClient;
    private final CuentaFeignClient cuentaFeignClient;
    private final UsuarioFeignClient usuarioFeignClient;
    private final TarifaFeignClient tarifaFeignClient;
    private final ParadaFeignClient paradaFeignClient;

    // Metodo para iniciar alquiler de viaje
    @Transactional
    public ViajeDTO save(Viaje viaje) {
        // Busco el monopatin y veo si devuelve algo
        Monopatin monopatin = monopatinFeignClient.findById(viaje.getIdMonopatin());
        if(monopatin == null)
            throw new NotFoundException("Monopatin",viaje.getIdMonopatin());
        if(!monopatin.getEstado().equalsIgnoreCase("LIBRE"))
            throw new MonopatinNoDisponible(viaje.getIdMonopatin(),monopatin.getEstado());


        // Busco la cuenta y veo si devuelve algo
        Cuenta cuenta = cuentaFeignClient.findById(viaje.getIdCuenta());
        if(cuenta == null)
            throw new NotFoundException("Cuenta",viaje.getIdCuenta());
        if(cuenta.getSaldo().compareTo(BigDecimal.ZERO) == 0)
            throw new SaldoCuentaInsuficiente(cuenta.getId());

        // Busco el usuario y veo si devuelve algo
        Usuario usuario = usuarioFeignClient.findById(viaje.getIdUsuario());
        if(usuario == null)
            throw new NotFoundException("Usuario",viaje.getIdUsuario());
        // La cuenta no esta asociada al usuario
        if(!usuarioFeignClient.cuentaAsociada(usuario.getId(),cuenta.getId()))
            throw new CuentaNoAsociada(usuario.getId());


        // Busco la parada y veo si devuelve algo
        Parada paradaInicio = paradaFeignClient.findById(viaje.getParadaOrigen());
        Parada paradaFin = paradaFeignClient.findById(viaje.getParadaDestino());
        if(paradaInicio == null || paradaFin == null)
            throw new NotFoundException("Parada",viaje.getParadaOrigen().toString());


        // Obtengo la tarifa

        // Si todo siguio correctamente seteo el estado del monopatin ARREGLARRRR
//        monopatin = monopatinFeignClient.updateEstado(viaje.getIdMonopatin(),"ACTIVO");

        return new ViajeDTO(repository.save(viaje));
    }

//    @Transactional COMPLETAR
//    public ViajeDTO finalizarViaje(Long id) {
//        Viaje viaje = repository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Viaje", id));
//
//
//    }

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

    @Transactional(readOnly = true)
    public List<ReporteViajePeriodoDTO> getReporteViajeAnio(Integer anio, Integer xViajes) {
        return repository.getReporteViajeAnio(anio, xViajes);
    }

    @Transactional
    public List<ReporteViajeUsuariosDTO> getReporteViajesPorUsuariosPeriodo(Integer anioDesde, Integer anioHasta) {
        return repository.getReportesViajesPorUsuariosPeriodo(anioDesde, anioHasta);
    }
}
