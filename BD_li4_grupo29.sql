-- MySQL dump 10.13  Distrib 8.0.27, for Linux (x86_64)
--
-- Host: localhost    Database: li4
-- ------------------------------------------------------
-- Server version	8.0.27-0ubuntu0.20.04.1

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
-- Table structure for table `avaliacao`
--

DROP TABLE IF EXISTS `avaliacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `avaliacao` (
  `id` char(20) NOT NULL,
  `classificacao` int NOT NULL,
  `data` datetime NOT NULL,
  `texto` varchar(250) NOT NULL,
  `idRestaurante` varchar(9) NOT NULL,
  `usernameCliente` char(14) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avaliacao`
--

LOCK TABLES `avaliacao` WRITE;
/*!40000 ALTER TABLE `avaliacao` DISABLE KEYS */;
INSERT INTO `avaliacao` VALUES ('av1',4,'2022-01-23 00:00:00','Delicioso!!!','tapas','private53'),('av2',5,'2021-09-11 00:00:00','serviço rápido e eficaz. Empregadas muito simpáticas, comida muito boa.','dartista','nagasaki89'),('av3',3,'2022-01-06 00:00:00','boa refeição pós-jejum.','dartista','nagasaki89'),('av4',3,'2022-01-05 00:00:00','Comida chegou um pouco fria.','fuhao','extinto77');
/*!40000 ALTER TABLE `avaliacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `username` varchar(20) NOT NULL,
  `dataNascimento` date NOT NULL,
  `nome` varchar(90) NOT NULL,
  `email` varchar(100) NOT NULL,
  `telemovel` char(9) NOT NULL,
  `password` varchar(25) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES ('extinto77','2001-12-09','Extinto','extinto77@gmail.com','977777777','tinto77'),('miyagi61','2001-12-10','Miyagi','miyagi61@gmail.com','961616161','mijao61'),('nagasaki89','2001-12-08','Nagasaki','nagasaki89@gmail.com','989898989','naga89'),('private53','2001-12-11','Private','private53@gmail.com','953535353','jotinha53');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comida`
--

DROP TABLE IF EXISTS `comida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comida` (
  `id` int NOT NULL AUTO_INCREMENT,
  `vegetariano` tinyint NOT NULL,
  `nome` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12380 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comida`
--

LOCK TABLES `comida` WRITE;
/*!40000 ALTER TABLE `comida` DISABLE KEYS */;
INSERT INTO `comida` VALUES (12348,0,'pizza pepperoni'),(12349,0,'pizza anchovas'),(12350,0,'pizza ananas'),(12351,0,'pizza pepperoni'),(12352,0,'pizza angolana'),(12353,0,'Nigiri'),(12354,0,'Gunkan'),(12355,0,'Temaki'),(12356,0,'Hot roll'),(12357,0,'Joe'),(12358,0,'Hossomaki Tekkamaki'),(12359,0,'Uramaki'),(12360,0,'Hossomaki Kappamaki'),(12361,1,'salada tomate'),(12362,1,'salada beterraba'),(12363,1,'salada cenoura'),(12364,1,'salada milho'),(12365,0,'salada frango'),(12366,0,'salada masrisco'),(12367,0,'salada atum'),(12368,0,'kebab'),(12369,0,'dorum'),(12370,0,'kebab box'),(12371,0,'frango frito'),(12372,0,'frango frito'),(12373,0,'paelha'),(12374,0,'tapas'),(12375,0,'churros'),(12376,1,'gaspacho'),(12377,0,'burritos'),(12378,0,'sandes presunto'),(12379,1,'noodles');
/*!40000 ALTER TABLE `comida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurante`
--

DROP TABLE IF EXISTS `restaurante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurante` (
  `paisOrigem` varchar(45) NOT NULL,
  `localidade` varchar(90) NOT NULL,
  `rua` varchar(90) NOT NULL,
  `gps` varchar(100) NOT NULL,
  `preco` varchar(7) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `telefone` char(9) NOT NULL,
  `id` char(9) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurante`
--

LOCK TABLES `restaurante` WRITE;
/*!40000 ALTER TABLE `restaurante` DISABLE KEYS */;
INSERT INTO `restaurante` VALUES ('china','braga','Rua Damiana Maria da Silva 14','41,529383, -8,446513','8,50€','Asia Wok','253888888','asiwok'),('italia','braga','Avenida Dom Joao II, 43','41,551292, -8,395405','22,50€','Bellucci Pizzaria','910021763','belluci'),('china','braga','Rua Cidade do Porto, 81','41,537905, -8,434595','5,50€','Braga 999','964928973','braga999'),('turquia','famalicao','Travessa do Outeiro','41,514656, -8,452215','7,00€','Restaurante Burcu Kebab\'s','968207297','burcu'),('espanha','braga','Praca Paulo Vidal, 25','41,5443, -8,403344','8,00€','Buteco das Tapas','917725657','buteco'),('mexico','braga','Rua dr. Rocha Peixoto, 111','41,546708, -8,429583','15,00€','Taquería El Cascabel','253170412','cascabel'),('japao','braga','Praceta Frei Baltazar de Braga, 15','41,562053, -8,420443','17,50€','Casa do Sushi Braga','253782044','casSuxibr'),('japao','braga','Avenida Robert Smith, 38','41,541781, -8,401831','26,00€','Cosy','253260552','cosy'),('italia','braga','Largo Da Soutinha, 15','41,545872, -8,413471','10,00€','Pizza D\'artista - Braga','253618087','dartista'),('china','braga','Praca Ricardo da Rocha, 12','41,548468, -8,404172','9,50€','Restaurante Estrelas do Mar','253251621','estrlmar'),('turquia','povoa lanhoso','Rua Doutor Avelino Pereira de Carvalho, 87','41,575534, -8,27214','8,00€','Fastminutes','253738458','fastmns'),('china','barcelos','Rua Santa Marta, 246','41,535014, -8,609664','7,00€','Restaurante Chines Fu Hao','253817002','fuhao'),('china','braga','Avenida Padre Julio Fragata, 106','41,555558, -8,406577','19,00€','Grande Muralha','253781490','grtWall'),('turquia','guimaraes','Rua Paulo Vi, 152','41,436293, -8,294763','6,00€','Guimaraes Pizzaria Kebab','920267252','guipzkb'),('mexico','braga','Rua Antonio Marinho, 10','41,558545, -8,419572','10,50€','Habanero Restaurante & Bar Mexicano','253467357','habanero'),('italia','braga','Rua Dr. Carlos Loyd Braga 13','41,552568, -8,401181','17,50€','Il Fiume','253299171','ilfiume'),('turquia','braga','Rua dos Bombeiros, 19','41,544626, -8,428631','5,50€','Kazan Kebab','253058131','kazan'),('turquia','esposende','Rua dos bombeiros, 19 ','41,533161, -8,778571','5,50€','Kebab House','964439492','kbbhs'),('turquia','guimaraes','Rua Conego Dr Manuel Faria','41,551117, -8,434988','6,00€','King Doner Kebab Pizzas','933960550','kngDonr'),('japao','braga','Rua Nova de Santa Cruz 67','41,555555, -8,402573','20,00€','Lamasan','253780370','lamasan'),('china','vila verde','Praça da República, 95','41,513587, -7,99397','7,00€','Lisan','253221672','lisan'),('italia','braga','Praceta Amândio Ferreira Pinto, 8','41,568112, -8,386362','9,00€','Ristorante Pizzeria Mamma Mia','253281232','mammamia'),('turquia','braga','Avenida de Martim, 807','41,533897, -8,499211','6,00€','Martim Kebab\'s','253914101','martim'),('china','famalicao','Avenida dos Descobrimentos, 840','41,400024, -8,516623','18,00€','Mikado','912503308','mikado'),('japao','braga','Rua Dom Frei Caetano Brandao, 169','41,548853, -8,428528','22,50€','Michizaki','253086587','mixizaki'),('espanha','braga','Rua Nova de Santa Cruz, 14','41,556574, -8,400406','7,00€','100 Montaditos Braga','253773954','montadit'),('espanha','braga','Praca Conde de Agrolongo, 163','41,552945, -8,426044','9,00€','Mostarda & Chocolate Food Square','253184646','mostarda'),('china','braga','Rua de Sao Vicente, 151','41,555321, -8,422202','15,00€','O Norte da China','253616218','nrtchina'),('japao','braga','Rua do Raio, 6','41,55104, -8,418055','12,50€','Omakase','938070831','omakase'),('espanha','braga','Rua das Granjas','41,510684, -8,420957','10,50€','Os Três Padeirinhos','253618104','padeir3'),('italia','braga','Rua Dom Afonso Henriques, 33','41,549014, -8,42759','12,50€','La Piola','253096926','piola'),('italia','braga','Rua dom diogo de sousan, 19','41,550419, -8,429073','22,50€','La Porta Braga','253268199','porta'),('china','barcelos','Rua Bartolomeu Dias, 35','41,393412, -8,51863','8,00€','Rstaurante Asia','253818311','rstAsia'),('japao','braga','Rua Andrade Corvo, 48','41,550215, -8,430694','29,00€','Seikö japanese restaurant','935715941','seiko'),('japao','braga','Avenida Alfredo Barros, 72','41,5365, -8,402935','15,00€','Sushi & Co','915133933','sushico'),('china','famalicao','Rua Ana Placido, 46 Loja 1','41,412412, -8,518639','13,00€','Sushi King','252217286','suxiking'),('espanha','braga','Praca Conde de Agrolongo, 116','41,552194, -8,426097','8,00€','Mercado Das Tapas','913188076','tapas'),('japao','braga','Praça conde de agrolongo 177','41,552949, -8,425994','27,50€','Taberna Shakai','253267289','tbrshk'),('japao','braga','Rua de Caires, 280','41,549788, -8,433248','12,00€','Temple San','253037376','tmplsan'),('espanha','braga','Rua Dom Afonso Henriques São João do Souto','41,549687, -8,425065','17,50€','TurnCups Tapas bar','969436844','turncups'),('italia','braga','Rua José Maria Ottoni, 21','41,553464, -8,396087','10,00€','Pizzaria Bella Venezia','253679240','venezia');
/*!40000 ALTER TABLE `restaurante` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-23 19:09:29
