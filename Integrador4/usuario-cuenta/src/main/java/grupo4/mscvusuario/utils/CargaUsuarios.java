// java
        package grupo4.mscvusuario.utils;

        import java.math.BigDecimal;
        import java.time.LocalDate;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.CommandLineRunner;
        import org.springframework.stereotype.Component;

        import grupo4.mscvusuario.entity.Cuenta;
        import grupo4.mscvusuario.entity.EstadoCuenta;
        import grupo4.mscvusuario.entity.TipoCuenta;
        import grupo4.mscvusuario.entity.Usuario;
        import grupo4.mscvusuario.entity.Rol;
        import grupo4.mscvusuario.repository.CuentaRepository;
        import grupo4.mscvusuario.repository.UsuarioRepository;

        @Component
        public class CargaUsuarios implements CommandLineRunner {
            @Autowired
            private UsuarioRepository usuarioRepository;
            @Autowired
            private CuentaRepository cuentaRepository;

            @Override
            public void run(String... args) throws Exception {
                CargarDatosIniciales(usuarioRepository, cuentaRepository);
            }

            public static void CargarDatosIniciales(UsuarioRepository usuarioRepositoryy, CuentaRepository cuentaRepositoryy) {
                Cuenta cuenta1 = new Cuenta();
                cuenta1.setIdMercadoPago("MP12345");
                cuenta1.setSaldo(new BigDecimal("1000.00"));
                cuenta1.setEstado(EstadoCuenta.ACTIVA);
                cuenta1.setFechaAlta(LocalDate.now());
                cuenta1.setTipoCuenta(TipoCuenta.BASICA);
                cuenta1.setKmConsumidosMes(0.0);
                cuenta1.setFechaRenovacionCupo(null);

                Cuenta cuenta2 = new Cuenta();
                cuenta2.setIdMercadoPago("MP67890");
                cuenta2.setSaldo(new BigDecimal("2500.00"));
                cuenta2.setEstado(EstadoCuenta.ACTIVA);
                cuenta2.setFechaAlta(LocalDate.now());
                cuenta2.setTipoCuenta(TipoCuenta.PREMIUM);
                cuenta2.setKmConsumidosMes(150.0);
                cuenta2.setFechaRenovacionCupo(LocalDate.now().plusMonths(1));

                Cuenta cuenta3 = new Cuenta();
                cuenta3.setIdMercadoPago("MP54321");
                cuenta3.setSaldo(new BigDecimal("500.00"));
                cuenta3.setEstado(EstadoCuenta.SUSPENDIDA);
                cuenta3.setFechaAlta(LocalDate.now().minusMonths(2));
                cuenta3.setTipoCuenta(TipoCuenta.BASICA);
                cuenta3.setKmConsumidosMes(0.0);
                cuenta3.setFechaRenovacionCupo(null);

                Cuenta cuenta4 = new Cuenta();
                cuenta4.setIdMercadoPago("MP98765");
                cuenta4.setSaldo(new BigDecimal("3000.00"));
                cuenta4.setEstado(EstadoCuenta.ACTIVA);
                cuenta4.setFechaAlta(LocalDate.now().minusMonths(1));
                cuenta4.setTipoCuenta(TipoCuenta.PREMIUM);
                cuenta4.setKmConsumidosMes(200.0);
                cuenta4.setFechaRenovacionCupo(LocalDate.now().plusMonths(1));

                Cuenta cuenta5 = new Cuenta();
                cuenta5.setIdMercadoPago("MP11223");
                cuenta5.setSaldo(new BigDecimal("750.00"));
                cuenta5.setEstado(EstadoCuenta.ACTIVA);
                cuenta5.setFechaAlta(LocalDate.now());
                cuenta5.setTipoCuenta(TipoCuenta.BASICA);
                cuenta5.setKmConsumidosMes(0.0);
                cuenta5.setFechaRenovacionCupo(null);

                Usuario usuario1 = new Usuario();
                usuario1.setNombre("Juan");
                usuario1.setApellido("Perez");
                usuario1.setRol(Rol.ADMIN);
                usuario1.addCuenta(cuenta1);
                usuario1.addCuenta(cuenta2);

                Usuario usuario2 = new Usuario();
                usuario2.setNombre("Maria");
                usuario2.setApellido("Gomez");
                usuario2.setRol(Rol.USUARIO);
                usuario2.addCuenta(cuenta3);

                Usuario usuario3 = new Usuario();
                usuario3.setNombre("Carlos");
                usuario3.setApellido("Lopez");
                usuario3.setRol(Rol.USUARIO);
                usuario3.addCuenta(cuenta4);

                Usuario usuario4 = new Usuario();
                usuario4.setNombre("Ana");
                usuario4.setApellido("Martinez");
                usuario4.setRol(Rol.USUARIO);
                usuario4.addCuenta(cuenta5);

                Usuario usuario5 = new Usuario();
                usuario5.setNombre("Luis");
                usuario5.setApellido("Rodriguez");
                usuario5.setRol(Rol.USUARIO);

                // Opcional: no es necesario llamar a addUsuario si ya se usó usuario.addCuenta(...)
                // pero se puede hacer para mayor claridad:
                // cuenta1.addUsuario(usuario1);
                // cuenta2.addUsuario(usuario1);
                // cuenta3.addUsuario(usuario2);
                // cuenta4.addUsuario(usuario3);
                // cuenta5.addUsuario(usuario4);

                UsuarioRepository repoUsuario = usuarioRepositoryy;
                repoUsuario.save(usuario1);
                repoUsuario.save(usuario2);
                repoUsuario.save(usuario3);
                repoUsuario.save(usuario4);
                repoUsuario.save(usuario5);

                CuentaRepository repoCuenta = cuentaRepositoryy;
                repoCuenta.save(cuenta1);
                repoCuenta.save(cuenta2);
                repoCuenta.save(cuenta3);
                repoCuenta.save(cuenta4);
                repoCuenta.save(cuenta5);
            }
        }