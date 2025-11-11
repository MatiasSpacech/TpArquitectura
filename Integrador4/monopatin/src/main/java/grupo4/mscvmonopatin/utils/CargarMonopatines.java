package grupo4.mscvmonopatin.utils;

import grupo4.mscvmonopatin.model.Estado;
import grupo4.mscvmonopatin.model.Monopatin;
import grupo4.mscvmonopatin.repository.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CargarMonopatines implements CommandLineRunner {
    @Autowired
    private MonopatinRepository repository;

    @Override
    public void run(String... args) {
        Monopatin m1 = new Monopatin();
//        m1.setId();
        m1.setEstado(Estado.LIBRE);
        m1.setLatitud(40.416775);
        m1.setLongitud(-3.703790);
        m1.setIdParada(1L);
        m1.setKmRecorridos(150);
        m1.setTiempoUsado(60);
        repository.save(m1);

        Monopatin m2 = new Monopatin();
        m2.setEstado(Estado.ACTIVO);
        m2.setLatitud(40.420150);
        m2.setLongitud(-3.708900);
        m2.setIdParada(2L);
        m2.setKmRecorridos(320);
        m2.setTiempoUsado(145);
        repository.save(m2);

        Monopatin m3 = new Monopatin();
        m3.setEstado(Estado.LIBRE);
        m3.setLatitud(40.415230);
        m3.setLongitud(-3.699540);
        m3.setIdParada(1L);
        m3.setKmRecorridos(85);
        m3.setTiempoUsado(30);
        repository.save(m3);

        Monopatin m4 = new Monopatin();
        m4.setEstado(Estado.MANTENIMIENTO);
        m4.setLatitud(40.418560);
        m4.setLongitud(-3.706120);
        m4.setIdParada(3L);
        m4.setKmRecorridos(520);
        m4.setTiempoUsado(280);
        repository.save(m4);

        Monopatin m5 = new Monopatin();
        m5.setEstado(Estado.LIBRE);
        m5.setLatitud(40.413890);
        m5.setLongitud(-3.701230);
        m5.setIdParada(2L);
        m5.setKmRecorridos(210);
        m5.setTiempoUsado(95);
        repository.save(m5);

        Monopatin m6 = new Monopatin();
        m6.setEstado(Estado.ACTIVO);
        m6.setLatitud(40.419870);
        m6.setLongitud(-3.704560);
        m6.setIdParada(1L);
        m6.setKmRecorridos(450);
        m6.setTiempoUsado(200);
        repository.save(m6);

        Monopatin m7 = new Monopatin();
        m7.setEstado(Estado.LIBRE);
        m7.setLatitud(40.414320);
        m7.setLongitud(-3.707890);
        m7.setIdParada(3L);
        m7.setKmRecorridos(15);
        m7.setTiempoUsado(5);
        repository.save(m7);

        Monopatin m8 = new Monopatin();
        m8.setEstado(Estado.MANTENIMIENTO);
        m8.setLatitud(40.417440);
        m8.setLongitud(-3.702340);
        m8.setIdParada(2L);
        m8.setKmRecorridos(680);
        m8.setTiempoUsado(350);
        repository.save(m8);

        // 7 monopatines adicionales con atributos mixtos
        Monopatin m9 = new Monopatin();
        m9.setEstado(Estado.ACTIVO);
        m9.setLatitud(40.421340);
        m9.setLongitud(-3.705670);
        m9.setIdParada(1L);
        m9.setKmRecorridos(275);
        m9.setTiempoUsado(120);
        repository.save(m9);

        this.repository.save(m1);
        this.repository.save(m2);
        this.repository.save(m3);
        this.repository.save(m4);
        this.repository.save(m5);
        this.repository.save(m6);
        this.repository.save(m7);
        this.repository.save(m8);
        this.repository.save(m9);
    }
}
