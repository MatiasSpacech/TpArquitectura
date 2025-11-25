package grupo4.mscvadmin.controllers;

import grupo4.mscvadmin.feignClients.UsuarioFeignClient;
import grupo4.mscvadmin.services.UsuarioService;

import java.util.Date;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Administración", description = "Endpoints para funciones administrativas del sistema")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    @Operation(summary = "Prueba de conectividad", description = "Endpoint de prueba para verificar el gateway")
    public String getUsuario() {
        return "probando gateway";
    }

    /*
     * a. Como administrador quiero poder generar un reporte de uso de monopatines
     * por kilómetros
     * para establecer si un monopatín requiere de mantenimiento. Este reporte debe
     * poder
     * configurarse para incluir (o no) los tiempos de pausa.
     */
    @GetMapping("/reportes/monopatines")
    @Operation(summary = "Generar reporte de monopatines",
               description = "Genera un reporte de uso de monopatines por kilómetros para mantenimiento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente")
    })
    public String generarReporteMonopatines() {
        return "Reporte de uso de monopatines generado.";
    }

    /*
     * b. Como administrador quiero poder anular cuentas de usuarios, para
     * inhabilitar el uso
     * momentáneo de la aplicación.
     */
    @GetMapping("//{id}/anular")
    @Operation(summary = "Anular cuenta de usuario",
               description = "Anula la cuenta de un usuario para inhabilitar el uso de la aplicación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuenta anulada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public String anularUsuario(
            @Parameter(description = "ID del usuario a anular") @PathVariable String id) {
        return "Cuenta de usuario " + id + " anulada.";
    }

    /*
     * c. Como administrador quiero consultar los monopatines con más de X viajes en
     * un cierto año.
     */
    @GetMapping("/monopatines-viajes") // http://localhost:8080/api/admin/monopatines-viajes?cantidadViajes=10&anio=2024
    @Operation(summary = "Consultar monopatines por viajes",
               description = "Consulta los monopatines con más de X viajes en un año específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    })
    public String consultarMonopatinesPorViajes(
            @Parameter(description = "Cantidad mínima de viajes") @RequestParam int cantidadViajes,
            @Parameter(description = "Año a consultar") @RequestParam int anio) {
        // Lógica para consultar monopatines con más de X viajes en un año
        return "Monopatines con más de " + cantidadViajes + " viajes en el año " + anio;
    }

    /*
     * d. Como administrador quiero consultar el total facturado en un rango de
     * meses de cierto año.
     */
    @GetMapping("/facturacion") // http://localhost:8080/api/admin/facturacion?anio=2024&mesInicio=1&mesFin=3
    @Operation(summary = "Consultar total facturado",
               description = "Consulta el total facturado en un rango de meses de cierto año")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    })
    public String consultarTotalFacturado(
            @Parameter(description = "Año a consultar") @RequestParam int anio,
            @Parameter(description = "Mes de inicio (1-12)") @RequestParam int mesInicio,
            @Parameter(description = "Mes de fin (1-12)") @RequestParam int mesFin) {
        // Lógica para consultar el total facturado en un rango de meses de un año
        return "Total facturado desde " + mesInicio + "/" + anio + " hasta " + mesFin + "/" + anio;
    }

    /*
     * e. Como administrador quiero ver los usuarios que más utilizan los
     * monopatines, filtrado por período y por tipo de usuario.
     */
    @GetMapping("/usuarios/mas-utilizan") // http://localhost:8080/api/admin/usuarios/mas-utilizan?periodo=mensual&tipoUsuario=admin
    @Operation(summary = "Ver usuarios top",
               description = "Ver los usuarios que más utilizan los monopatines, filtrado por período y tipo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta exitosa")
    })
    public String verUsuariosTop(
            @Parameter(description = "Período a analizar") @RequestParam String periodo,
            @Parameter(description = "Tipo de usuario") @RequestParam String tipoUsuario) {
        // Lógica para ver los usuarios que más utilizan los monopatines
        return "Usuarios top en el período " + periodo + " para el tipo de usuario " + tipoUsuario;
    }

    /*
     * f. Como administrador quiero hacer un ajuste de precios, y que a partir de
     * cierta fecha el sistema
     * habilite los nuevos precios.
     */
    @GetMapping("/ajuste-precios") // http://localhost:8080/api/admin/ajuste-precios?nuevosPrecios=15.0&fechaInicio=2024-07-01
    @Operation(summary = "Ajustar precios",
               description = "Realiza un ajuste de precios que se habilita a partir de cierta fecha")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Precios ajustados exitosamente")
    })
    public String hacerAjustePrecios(
            @Parameter(description = "Nuevos precios") @RequestParam double nuevosPrecios,
            @Parameter(description = "Fecha de inicio del nuevo precio") @RequestParam Date fechaInicio) {
        // Lógica para hacer un ajuste de precios
        return "Ajuste de precios a " + nuevosPrecios + " a partir de " + fechaInicio;
    }

}
