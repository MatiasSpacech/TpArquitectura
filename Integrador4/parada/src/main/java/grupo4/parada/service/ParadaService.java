package grupo4.parada.service;

import grupo4.parada.entity.Parada;
import grupo4.parada.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParadaService {

    @Autowired
    private ParadaRepository paradaRepository;

    // Funcionalidad: "Registrar parada"
    public Parada registrar(Parada parada) {
        return paradaRepository.save(parada);
    }

    // Funcionalidad: "Quitar parada"
    public void quitar(Long id) {
        paradaRepository.deleteById(id);
    }

    public List<Parada> buscarTodas() {
        return paradaRepository.findAll();
    }



    // ¡LÓGICA CLAVE! La que usará el microservicio de viajes.
    // Verifica si unas coordenadas dadas corresponden a una parada válida.
//    public boolean esParadaValida(double latitud, double longitud) {
//        List<Parada> todasLasParadas = paradaRepository.findAll();
//
//        // Define un radio pequeño de tolerancia (ej. 10 metros)
//        final double RADIO_TOLERANCIA = 0.01; // en kilómetros
//
//        for (Parada parada : todasLasParadas) {
//            double distancia = calcularDistancia(latitud, longitud, parada.getLatitud(), parada.getLongitud());
//            if (distancia <= RADIO_TOLERANCIA) {
//                return true; // Encontró una parada dentro del radio
//            }
//        }
//        return false; // No hay ninguna parada cerca
//    }

    // Fórmula de Haversine para calcular la distancia entre dos puntos GPS
//    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
//        double radioTierra = 6371; // Radio de la Tierra en kilómetros
//        double dLat = Math.toRadians(lat2 - lat1);
//        double dLon = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
//                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
//                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        return radioTierra * c;
//    }
}
