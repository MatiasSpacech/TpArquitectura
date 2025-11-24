package grupo4.mscvusuario.entity;

public enum Rol
{
    ADMIN,
    USUARIO,
    MANTENIMIENTO;

    public static Rol perteneceAlEnum(String estado) {
        try {
            return Rol.valueOf(estado.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }
}


