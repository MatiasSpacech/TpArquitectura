-- MySQL dump 10.13  Distrib 8.0.43, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: paradas
-- ------------------------------------------------------
-- Server version	9.4.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `parada`
--

DROP TABLE IF EXISTS `parada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parada` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `ciudad` varchar(255) DEFAULT NULL,
                          `direccion` varchar(255) DEFAULT NULL,
                          `latitud` double NOT NULL,
                          `longitud` double NOT NULL,
                          `nombre` varchar(60) NOT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parada`
--

/*!40000 ALTER TABLE `parada` DISABLE KEYS */;
INSERT INTO `parada` VALUES (1,'Tres Arroyos','Dorrego',70.416775,-3.70379,'Parada 1'),(2,'Tandil','San Martin',40.42015,-3.7089,'Parada 2'),(3,'Olavarria','Sarmiento',20.41389,-3.70123,'Parada 3');
/*!40000 ALTER TABLE `parada` ENABLE KEYS */;

--
-- Dumping routines for database 'paradas'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-22 19:13:07

-- MySQL dump 10.13  Distrib 8.0.43, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: facturas
-- ------------------------------------------------------
-- Server version	9.4.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `factura`
--

DROP TABLE IF EXISTS `factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factura` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `fecha_emision` datetime(6) DEFAULT NULL,
                           `importe` double NOT NULL,
                           `numero_factura` varchar(255) NOT NULL,
                           `tarifa_id` bigint DEFAULT NULL,
                           `usuario_id` bigint DEFAULT NULL,
                           `viaje_id` bigint DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `UKq4rschxwntd1d2yt1heie7l5j` (`numero_factura`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factura`
--

/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
INSERT INTO `factura` VALUES (1,'2025-11-10 16:52:19.065000',150,'FACT-2025-001',1,1,NULL),(2,'2025-11-10 16:52:19.167000',200,'FACT-2025-002',1,2,NULL),(3,'2025-11-10 16:52:19.178000',250,'FACT-2025-003',2,3,NULL),(4,'2025-11-10 16:52:19.192000',300,'FACT-2025-004',2,4,NULL),(5,'2025-11-10 16:52:19.210000',350,'FACT-2025-005',3,5,NULL);
/*!40000 ALTER TABLE `factura` ENABLE KEYS */;

--
-- Dumping routines for database 'facturas'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-22 19:51:42


-- MySQL dump 10.13  Distrib 8.0.43, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: tarifas
-- ------------------------------------------------------
-- Server version	9.4.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tarifa`
--

DROP TABLE IF EXISTS `tarifa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tarifa` (
                          `id` bigint NOT NULL,
                          `cuota_mensual_premium` double DEFAULT NULL,
                          `fecha` datetime(6) DEFAULT NULL,
                          `monto` double NOT NULL,
                          `monto_extra` double NOT NULL,
                          `porcentaje_recargo_pausa` double DEFAULT NULL,
                          `tiempo_maximo_pausa_minutos` int DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarifa`
--

/*!40000 ALTER TABLE `tarifa` DISABLE KEYS */;
INSERT INTO `tarifa` VALUES (1,5000,'2025-01-01 00:00:00.000000',60,150,20,15),(2,5200,'2025-02-01 00:00:00.000000',96.90863329062499,231.08981784687498,22,15),(3,5400,'2025-03-01 00:00:00.000000',70,160,25,15),(4,5600,'2025-04-01 00:00:00.000000',75,165,28,15),(5,5800,'2025-05-01 00:00:00.000000',80,170,30,15);
/*!40000 ALTER TABLE `tarifa` ENABLE KEYS */;

--
-- Table structure for table `tarifa_seq`
--

DROP TABLE IF EXISTS `tarifa_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tarifa_seq` (
                              `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarifa_seq`
--

/*!40000 ALTER TABLE `tarifa_seq` DISABLE KEYS */;
INSERT INTO `tarifa_seq` VALUES (101);
/*!40000 ALTER TABLE `tarifa_seq` ENABLE KEYS */;

--
-- Dumping routines for database 'tarifas'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-22 19:50:35

-- MySQL dump 10.13  Distrib 8.0.43, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: usuarios
-- ------------------------------------------------------
-- Server version	9.4.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cuenta`
--

DROP TABLE IF EXISTS `cuenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuenta` (
                          `fecha_alta` date NOT NULL,
                          `fecha_renovacion_cupo` date DEFAULT NULL,
                          `km_consumidos_mes` double DEFAULT NULL,
                          `saldo` decimal(38,2) DEFAULT NULL,
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `id_mercado_pago` varchar(255) NOT NULL,
                          `estado` enum('ACTIVA','SUSPENDIDA') NOT NULL,
                          `tipo_cuenta` enum('BASICA','PREMIUM') NOT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta`
--

/*!40000 ALTER TABLE `cuenta` DISABLE KEYS */;
INSERT INTO `cuenta` VALUES ('2025-11-22','2025-12-22',0,2500.00,1,'MP67890','ACTIVA','PREMIUM'),('2025-11-22',NULL,0,1000.00,2,'MP12345','ACTIVA','BASICA'),('2025-11-17',NULL,0,400.00,3,'MP77889','ACTIVA','BASICA'),('2025-09-22',NULL,0,500.00,4,'MP54321','SUSPENDIDA','BASICA'),('2025-11-12','2026-01-22',0,1200.00,5,'MP55667','ACTIVA','BASICA'),('2025-11-22',NULL,0,750.00,6,'MP11223','ACTIVA','BASICA'),('2025-10-22','2025-12-22',0,3000.00,7,'MP98765','ACTIVA','PREMIUM');
/*!40000 ALTER TABLE `cuenta` ENABLE KEYS */;

--
-- Table structure for table `cuenta_usuario`
--

DROP TABLE IF EXISTS `cuenta_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuenta_usuario` (
                                  `id_cuenta` bigint NOT NULL,
                                  `id_usuario` bigint NOT NULL,
                                  PRIMARY KEY (`id_cuenta`,`id_usuario`),
                                  KEY `FKglb2y52194gl9h907fuvp1mc7` (`id_usuario`),
                                  CONSTRAINT `FKa7m95ojwhc9mb44pucoxpwbxq` FOREIGN KEY (`id_cuenta`) REFERENCES `cuenta` (`id`),
                                  CONSTRAINT `FKglb2y52194gl9h907fuvp1mc7` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta_usuario`
--

/*!40000 ALTER TABLE `cuenta_usuario` DISABLE KEYS */;
INSERT INTO `cuenta_usuario` VALUES (1,1),(2,1),(1,2),(2,3),(3,3),(4,4),(6,4),(4,5),(5,5),(6,6),(7,7),(7,8);
/*!40000 ALTER TABLE `cuenta_usuario` ENABLE KEYS */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
                           `latitud` double DEFAULT NULL,
                           `longitud` double DEFAULT NULL,
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `password` varchar(60) NOT NULL,
                           `apellido` varchar(255) DEFAULT NULL,
                           `email` varchar(255) DEFAULT NULL,
                           `nombre` varchar(255) DEFAULT NULL,
                           `rol` enum('ADMIN','MANTENIMIENTO','USUARIO') NOT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `UK5171l57faosmj8myawaucatdw` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (NULL,NULL,1,'$2a$10$llMZ0uclYV7EgctU6uutzOBoEK0edGSCf8XUMJxljlTWphFR/cUga','Van Waarde','agusvan@gmail.com','Agustin','ADMIN'),(NULL,NULL,2,'$2a$10$257Zhb4kbHTUvGobX6wQuO8l9hceYEliBamfFC7653c6Lytkcpx42','Fernandez','sofia.fernandez@example.com','Sofia','USUARIO'),(NULL,NULL,3,'$2a$10$l2G8sCX4WF1QJRS2.wq80OEKqFKf6uLX4LyiwWpl2dvckUD.rBh.K','Gonzalez','martin.gonzalez@example.com','Martin','USUARIO'),(NULL,NULL,4,'$2a$10$kG4tutMZK2JEJEVaHECs/uWsng9Zm9y40BUmDLpJbcSHNJ1FYLbp2','Spacech','mati@gmail.com','Matias','USUARIO'),(NULL,NULL,5,'$2a$10$QG31jk3wLoKMVwm5HQDNY.ESyCY662WWoH1ZLDf3so0bP4S5ahy0S','Ruiz','florencia.ruiz@example.com','Florencia','USUARIO'),(NULL,NULL,6,'$2a$10$wJTuXxKF6wxxnPcqnen5Y.xp.JhwT86uRMPRwwyGUbs/GtE8kq7Ha','Martinez','ana.martinez@example.com','Ana','USUARIO'),(NULL,NULL,7,'$2a$10$v99OOCGu78wG0m4ocUrdAe6CLf5iUUzDv.ICYQCEKzU5ReLS25AmK','Lopez','carlos.lopez@example.com','Carlos','USUARIO'),(NULL,NULL,8,'$2a$10$kg79byjJoXbHxYAFOgAvEO9vdKcHoEup/nkH3T5jFbdsxXK4TMHgm','Castro','diego.castro@example.com','Diego','USUARIO'),(NULL,NULL,9,'$2a$10$O51KIMiV0/.jg./YH/G87.ImBVYaF/a9RRd9pnBOP58qg3HmnmLWu','Rodriguez','luis.rodriguez@example.com','Luis','USUARIO');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;

--
-- Dumping routines for database 'usuarios'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-22 19:51:05


-- MySQL dump 10.13  Distrib 8.0.43, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: viajes
-- ------------------------------------------------------
-- Server version	9.4.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `viaje`
--

DROP TABLE IF EXISTS `viaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `viaje` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `activo` bit(1) NOT NULL,
                         `fecha_fin` datetime(6) DEFAULT NULL,
                         `fecha_inicio` datetime(6) DEFAULT NULL,
                         `id_cuenta` bigint NOT NULL,
                         `id_monopatin` varchar(255) NOT NULL,
                         `id_tarifa` bigint NOT NULL,
                         `id_usuario` bigint NOT NULL,
                         `kilometros_recorridos` int NOT NULL,
                         `parada_destino` bigint NOT NULL,
                         `parada_origen` bigint NOT NULL,
                         `tiempo_pausa` int NOT NULL,
                         `tiempo_total_minutos` int NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viaje`
--

/*!40000 ALTER TABLE `viaje` DISABLE KEYS */;
INSERT INTO `viaje` VALUES (1,0x01,NULL,'2025-11-07 11:07:30.232486',1,'6912b90234e14ff3385b8da6',1,1,5,2,1,0,25),(2,0x01,NULL,'2025-11-08 11:07:30.282225',2,'6912b90234e14ff3385b8da7',1,1,8,3,2,5,40),(3,0x00,'2025-11-09 11:37:30.291694','2025-11-09 11:07:30.291673',4,'6912b90234e14ff3385b8da8',2,3,6,3,1,0,30),(4,0x00,'2025-11-10 11:57:30.298932','2025-11-10 11:07:30.298902',4,'6912b90234e14ff3385b8da9',2,3,10,1,2,20,50),(5,0x00,'2025-11-11 11:27:30.309918','2025-11-11 11:07:30.309898',5,'6912b90234e14ff3385b8daa',3,4,4,1,3,0,20),(6,0x01,NULL,'2025-11-12 08:07:30.320703',1,'6912b90234e14ff3385b8dab',3,1,15,2,1,10,60),(7,0x01,NULL,'2025-11-12 06:07:30.331426',4,'6912b90234e14ff3385b8dac',4,3,7,3,2,5,30),(8,0x00,'2025-11-12 09:22:30.343276','2025-11-12 09:07:30.343256',1,'6912b90234e14ff3385b8dad',4,1,3,2,3,0,15),(9,0x00,'2025-11-12 10:57:30.356128','2025-11-12 10:22:30.356106',5,'6912b90234e14ff3385b8dae',5,4,9,3,1,3,35),(10,0x00,'2025-11-12 11:02:30.365173','2025-11-12 10:37:30.365150',2,'6912b90234e14ff3385b8daf',5,1,6,1,2,0,25);
/*!40000 ALTER TABLE `viaje` ENABLE KEYS */;

--
-- Dumping routines for database 'viajes'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-22 19:51:28


-- MongoDB dump 2025-11-22T19:52:10.123+0000
-- Dumping db: db_monopatines
db_monopatines: schema
    + tables
        monopatines: table
            + columns
                _id: ObjectId
                _class: String
                estado: String
                idParada: Int64
                kmRecorridos: Int32
                latitud: Double
                longitud: Double
                tiempoUsado: Int32
