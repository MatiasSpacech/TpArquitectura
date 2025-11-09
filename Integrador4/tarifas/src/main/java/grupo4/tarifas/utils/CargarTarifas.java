package grupo4.tarifas.utils;

import grupo4.tarifas.entity.Tarifa;
import grupo4.tarifas.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class CargarTarifas implements CommandLineRunner {

    @Autowired
    private TarifaRepository tarifaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (tarifaRepository.count() == 0) {
            System.out.println("Cargando datos iniciales de tarifas...");
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            // Crear tarifas con fechas específicas
            Tarifa tarifa1 = new Tarifa();
            tarifa1.setMonto(60.0);
            tarifa1.setMontoExtra(150.0);
            tarifa1.setFecha(sdf.parse("2025-01-01"));
            tarifa1.setCuotaMensualPremium(5000.0);
            tarifa1.setPorcentajeRecargoPausa(20.0);
            tarifa1.setTiempoMaximoPausaMinutos(15);
            tarifaRepository.save(tarifa1);

            Tarifa tarifa2 = new Tarifa();
            tarifa2.setMonto(65.0);
            tarifa2.setMontoExtra(155.0);
            tarifa2.setFecha(sdf.parse("2025-02-01"));
            tarifa2.setCuotaMensualPremium(5200.0);
            tarifa2.setPorcentajeRecargoPausa(22.0);
            tarifa2.setTiempoMaximoPausaMinutos(15);
            tarifaRepository.save(tarifa2);

            Tarifa tarifa3 = new Tarifa();
            tarifa3.setMonto(70.0);
            tarifa3.setMontoExtra(160.0);
            tarifa3.setFecha(sdf.parse("2025-03-01"));
            tarifa3.setCuotaMensualPremium(5400.0);
            tarifa3.setPorcentajeRecargoPausa(25.0);
            tarifa3.setTiempoMaximoPausaMinutos(15);
            tarifaRepository.save(tarifa3);

            Tarifa tarifa4 = new Tarifa();
            tarifa4.setMonto(75.0);
            tarifa4.setMontoExtra(165.0);
            tarifa4.setFecha(sdf.parse("2025-04-01"));
            tarifa4.setCuotaMensualPremium(5600.0);
            tarifa4.setPorcentajeRecargoPausa(28.0);
            tarifa4.setTiempoMaximoPausaMinutos(15);
            tarifaRepository.save(tarifa4);

            Tarifa tarifa5 = new Tarifa();
            tarifa5.setMonto(80.0);
            tarifa5.setMontoExtra(170.0);
            tarifa5.setFecha(sdf.parse("2025-05-01"));
            tarifa5.setCuotaMensualPremium(5800.0);
            tarifa5.setPorcentajeRecargoPausa(30.0);
            tarifa5.setTiempoMaximoPausaMinutos(15);
            tarifaRepository.save(tarifa5);

            System.out.println("Tarifas cargadas correctamente!");
        }
    }
}
