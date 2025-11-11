package grupo4.parada.utils;

import grupo4.parada.model.Parada;
import grupo4.parada.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CargarParadas implements CommandLineRunner {
    @Autowired
    private ParadaRepository repository;

    public void run(String... args) {
        if (this.repository.count() > 0) return;

        Parada p1 = new Parada();
        p1.setNombre("Parada 1");
        p1.setCiudad("Tres Arroyos");
        p1.setDireccion("Dorrego");
        p1.setLatitud(70.416775);
        p1.setLongitud(-3.703790);

        Parada p2 = new Parada();
        p2.setNombre("Parada 2");
        p2.setCiudad("Tandil");
        p2.setDireccion("San Martin");
        p2.setLatitud(40.420150);
        p2.setLongitud(-3.708900);

        Parada p3 = new Parada();
        p3.setNombre("Parada 3");
        p3.setCiudad("Olavarria");
        p3.setDireccion("Sarmiento");
        p3.setLatitud(20.413890);
        p3.setLongitud(-3.701230);

        this.repository.save(p1);
        this.repository.save(p2);
        this.repository.save(p3);
    }

}
