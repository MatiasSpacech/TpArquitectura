package grupo4.mscvadmin.controllers;

import grupo4.mscvadmin.feignClients.UsuarioFeignClient;
import grupo4.mscvadmin.services.UsuarioService;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

/*a. Como administrador quiero poder generar un reporte de uso de monopatines por kilómetros
para establecer si un monopatín requiere de mantenimiento. Este reporte debe poder
configurarse para incluir (o no) los tiempos de pausa.
b. Como administrador quiero poder anular cuentas de usuarios, para inhabilitar el uso
momentáneo de la aplicación.
c. Como administrador quiero consultar los monopatines con más de X viajes en un cierto año.
d. Como administrador quiero consultar el total facturado en un rango de meses de cierto año.
e. Como administrador quiero ver los usuarios que más utilizan los monopatines, filtrado por
período y por tipo de usuario.
f. Como administrador quiero hacer un ajuste de precios, y que a partir de cierta fecha el sistema
habilite los nuevos precios. */

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String getUsuario() {
        return "probando gateway";
        // return usuarioService.getUsuario();
    }

    /*
     * a. Como administrador quiero poder generar un reporte de uso de monopatines
     * por kilómetros
     * para establecer si un monopatín requiere de mantenimiento. Este reporte debe
     * poder
     * configurarse para incluir (o no) los tiempos de pausa.
     */
    @GetMapping("/reportes/monopatines")
    public String generarReporteMonopatines() {
        return "Reporte de uso de monopatines generado.";
    }

    /*
     * b. Como administrador quiero poder anular cuentas de usuarios, para
     * inhabilitar el uso
     * momentáneo de la aplicación.
     */
    @GetMapping("/usuarios/{id}/anular")
    public String anularUsuario(@PathVariable String id) {
        // Lógica para anular la cuenta de usuario
        return "Cuenta de usuario " + id + " anulada.";
    }

    /*
     * c. Como administrador quiero consultar los monopatines con más de X viajes en
     * un cierto año.
     */
    @GetMapping("/monopatines-viajes") // http://localhost:8080/api/admin/monopatines-viajes?cantidadViajes=10&anio=2024
    public String consultarMonopatinesPorViajes(
            @RequestParam int cantidadViajes,
            @RequestParam int anio) {
        // Lógica para consultar monopatines con más de X viajes en un año
        return "Monopatines con más de " + cantidadViajes + " viajes en el año " + anio;
    }

    /*
     * d. Como administrador quiero consultar el total facturado en un rango de
     * meses de cierto año.
     */
    @GetMapping("/facturacion") // http://localhost:8080/api/admin/facturacion?anio=2024&mesInicio=1&mesFin=3
    public String consultarTotalFacturado(
            @RequestParam int anio,
            @RequestParam int mesInicio,
            @RequestParam int mesFin) {
        // Lógica para consultar el total facturado en un rango de meses de un año
        return "Total facturado desde " + mesInicio + "/" + anio + " hasta " + mesFin + "/" + anio;
    }

    /*
     * e. Como administrador quiero ver los usuarios que más utilizan los
     * monopatines, filtrado por período y por tipo de usuario.
     */
    @GetMapping("/usuarios/mas-utilizan") // http://localhost:8080/api/admin/usuarios/mas-utilizan?periodo=mensual&tipoUsuario=admin
    public String verUsuariosTop(
            @RequestParam String periodo,
            @RequestParam String tipoUsuario) {
        // Lógica para ver los usuarios que más utilizan los monopatines
        return "Usuarios top en el período " + periodo + " para el tipo de usuario " + tipoUsuario;
    }

    /*
     * f. Como administrador quiero hacer un ajuste de precios, y que a partir de
     * cierta fecha el sistema
     * habilite los nuevos precios.
     */
    @GetMapping("/ajuste-precios") // http://localhost:8080/api/admin/ajuste-precios?nuevosPrecios=15.0&fechaInicio=2024-07-01
    public String hacerAjustePrecios(
            @RequestParam double nuevosPrecios,
            @RequestParam Date fechaInicio) {
        // Lógica para hacer un ajuste de precios
        return "Ajuste de precios a " + nuevosPrecios + " a partir de " + fechaInicio;
    }

}
