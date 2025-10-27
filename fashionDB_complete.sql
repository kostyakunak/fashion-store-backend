-- MySQL dump 10.13  Distrib 9.2.0, for macos15.2 (arm64)
--
-- Host: localhost    Database: fashionDB
-- ------------------------------------------------------
-- Server version	9.2.0

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
-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `addresses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `city` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `country` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `postal_code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `recipient_first_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `recipient_last_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `street` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` bigint NOT NULL,
  `is_main` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK1fa36y2oqhao3wgg2rw1pi459` (`user_id`),
  CONSTRAINT `FK1fa36y2oqhao3wgg2rw1pi459` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addresses`
--

LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT INTO `addresses` VALUES (25,'Moscow','Russia','125009','Alexander','Kunakov','Tverskaya St, 15, apt 42',42,1),(26,'Saint Petersburg','Russia','191002','Anna','Petrova','Nevsky Prospekt, 28, apt 15',43,1),(27,'Moscow','Russia','119021','Dmitry','Volkov','Leo Tolstoy St, 16, apt 8',44,1),(28,'Yekaterinburg','Russia','620075','Elena','Smirnova','Lenin St, 5, apt 12',45,1),(29,'Novosibirsk','Russia','630091','Mikhail','Kuznetsov','Red Avenue, 25, apt 7',46,1);
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `product_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `size_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpu4bcbluhsxagirmbdn7dilm5` (`product_id`),
  KEY `FKg5uhi8vpsuy0lgloxk2h4w5o6` (`user_id`),
  CONSTRAINT `FKg5uhi8vpsuy0lgloxk2h4w5o6` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKpu4bcbluhsxagirmbdn7dilm5` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (84,2,49,44,69),(86,1,62,46,67),(108,1,44,48,62),(112,1,74,49,61),(113,1,71,49,61),(114,1,61,49,77),(115,2,70,49,63),(116,1,84,49,64),(117,1,88,49,65),(118,1,48,49,64);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKt8o6pivur7nn124jehx7cygw5` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (34,'Accessories'),(35,'Bags'),(36,'Jewelry'),(32,'Men\'s Clothing'),(33,'Shoes'),(31,'Women\'s Clothing');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `images` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_id` bigint NOT NULL,
  `is_main` bit(1) DEFAULT NULL,
  `sort_order` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FKghwsjbjo7mg3iufxruvq6iu3q` (`product_id`),
  CONSTRAINT `FKghwsjbjo7mg3iufxruvq6iu3q` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=568 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
INSERT INTO `images` VALUES (379,'https://images.pexels.com/photos/6371812/pexels-photo-6371812.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',44,_binary '',1),(380,'https://images.pexels.com/photos/13526890/pexels-photo-13526890.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',44,_binary '\0',2),(381,'https://images.pexels.com/photos/9956571/pexels-photo-9956571.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',44,_binary '\0',3),(385,'https://images.pexels.com/photos/6798640/pexels-photo-6798640.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',46,_binary '',1),(386,'https://images.pexels.com/photos/10418432/pexels-photo-10418432.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',46,_binary '\0',2),(387,'https://images.pexels.com/photos/34419222/pexels-photo-34419222.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',46,_binary '\0',3),(388,'https://images.pexels.com/photos/18404961/pexels-photo-18404961.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',47,_binary '',1),(389,'https://images.pexels.com/photos/9603625/pexels-photo-9603625.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',47,_binary '\0',2),(390,'https://images.pexels.com/photos/9603628/pexels-photo-9603628.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',47,_binary '\0',3),(391,'https://images.pexels.com/photos/15835264/pexels-photo-15835264.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',48,_binary '',1),(392,'https://images.pexels.com/photos/28868050/pexels-photo-28868050.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',48,_binary '\0',2),(393,'https://images.pexels.com/photos/6766302/pexels-photo-6766302.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',48,_binary '\0',3),(394,'https://images.pexels.com/photos/1043474/pexels-photo-1043474.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',49,_binary '',1),(395,'https://images.pexels.com/photos/8187671/pexels-photo-8187671.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',49,_binary '\0',2),(396,'https://images.pexels.com/photos/8187696/pexels-photo-8187696.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',49,_binary '\0',3),(397,'https://images.pexels.com/photos/15667095/pexels-photo-15667095.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',50,_binary '',1),(398,'https://images.pexels.com/photos/5789591/pexels-photo-5789591.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',50,_binary '\0',2),(399,'https://images.pexels.com/photos/9899496/pexels-photo-9899496.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',50,_binary '\0',3),(400,'https://images.pexels.com/photos/34402772/pexels-photo-34402772.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',51,_binary '',1),(401,'https://images.pexels.com/photos/34402774/pexels-photo-34402774.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',51,_binary '\0',2),(402,'https://images.pexels.com/photos/34380734/pexels-photo-34380734.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',51,_binary '\0',3),(403,'https://images.pexels.com/photos/27127400/pexels-photo-27127400.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',52,_binary '',1),(404,'https://images.pexels.com/photos/2043590/pexels-photo-2043590.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',52,_binary '\0',2),(405,'https://images.pexels.com/photos/22500809/pexels-photo-22500809.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',52,_binary '\0',3),(406,'https://images.pexels.com/photos/18394309/pexels-photo-18394309.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',53,_binary '',1),(407,'https://images.pexels.com/photos/1848886/pexels-photo-1848886.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',53,_binary '\0',2),(408,'https://images.pexels.com/photos/3737918/pexels-photo-3737918.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',53,_binary '\0',3),(409,'https://images.pexels.com/photos/34384506/pexels-photo-34384506.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',54,_binary '',1),(410,'https://images.pexels.com/photos/11075617/pexels-photo-11075617.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',54,_binary '\0',2),(411,'https://images.pexels.com/photos/30256468/pexels-photo-30256468.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',54,_binary '\0',3),(412,'https://images.pexels.com/photos/12417688/pexels-photo-12417688.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',55,_binary '',1),(413,'https://images.pexels.com/photos/23319168/pexels-photo-23319168.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',55,_binary '\0',2),(414,'https://images.pexels.com/photos/33350887/pexels-photo-33350887.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',55,_binary '\0',3),(415,'https://images.pexels.com/photos/34414154/pexels-photo-34414154.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',56,_binary '',1),(416,'https://images.pexels.com/photos/2120584/pexels-photo-2120584.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',56,_binary '\0',2),(417,'https://images.pexels.com/photos/33041188/pexels-photo-33041188.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',56,_binary '\0',3),(418,'https://images.pexels.com/photos/8441480/pexels-photo-8441480.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',57,_binary '',1),(419,'https://images.pexels.com/photos/12289155/pexels-photo-12289155.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',57,_binary '\0',2),(420,'https://images.pexels.com/photos/6371788/pexels-photo-6371788.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',57,_binary '\0',3),(421,'https://images.pexels.com/photos/8113006/pexels-photo-8113006.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',58,_binary '',1),(422,'https://images.pexels.com/photos/16113402/pexels-photo-16113402.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',58,_binary '\0',2),(423,'https://images.pexels.com/photos/14970272/pexels-photo-14970272.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',58,_binary '\0',3),(424,'https://images.pexels.com/photos/18601494/pexels-photo-18601494.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',59,_binary '',1),(425,'https://images.pexels.com/photos/29359829/pexels-photo-29359829.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',59,_binary '\0',2),(426,'https://images.pexels.com/photos/27850567/pexels-photo-27850567.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',59,_binary '\0',3),(427,'https://images.pexels.com/photos/16346895/pexels-photo-16346895.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',60,_binary '',1),(428,'https://images.pexels.com/photos/28606334/pexels-photo-28606334.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',60,_binary '\0',2),(429,'https://images.pexels.com/photos/25227840/pexels-photo-25227840.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',60,_binary '\0',3),(430,'https://images.pexels.com/photos/4846584/pexels-photo-4846584.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',61,_binary '',1),(431,'https://images.pexels.com/photos/1314058/pexels-photo-1314058.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',61,_binary '\0',2),(432,'https://images.pexels.com/photos/842960/pexels-photo-842960.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',61,_binary '\0',3),(433,'https://images.pexels.com/photos/2735970/pexels-photo-2735970.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',62,_binary '',1),(434,'https://images.pexels.com/photos/2740658/pexels-photo-2740658.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',62,_binary '\0',2),(435,'https://images.pexels.com/photos/4714615/pexels-photo-4714615.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',62,_binary '\0',3),(436,'https://images.pexels.com/photos/34372591/pexels-photo-34372591.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',63,_binary '',1),(437,'https://images.pexels.com/photos/34372575/pexels-photo-34372575.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',63,_binary '\0',2),(438,'https://images.pexels.com/photos/4291121/pexels-photo-4291121.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',63,_binary '\0',3),(439,'https://images.pexels.com/photos/8959643/pexels-photo-8959643.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',64,_binary '',1),(440,'https://images.pexels.com/photos/34399156/pexels-photo-34399156.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',64,_binary '\0',2),(441,'https://images.pexels.com/photos/34399158/pexels-photo-34399158.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',64,_binary '\0',3),(442,'https://images.pexels.com/photos/15893780/pexels-photo-15893780.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',65,_binary '',1),(443,'https://images.pexels.com/photos/32100313/pexels-photo-32100313.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',65,_binary '\0',2),(444,'https://images.pexels.com/photos/10597054/pexels-photo-10597054.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',65,_binary '\0',3),(445,'https://images.pexels.com/photos/9788969/pexels-photo-9788969.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',66,_binary '',1),(446,'https://images.pexels.com/photos/17071210/pexels-photo-17071210.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',66,_binary '\0',2),(447,'https://images.pexels.com/photos/4570560/pexels-photo-4570560.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',66,_binary '\0',3),(448,'https://images.pexels.com/photos/17244592/pexels-photo-17244592.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',67,_binary '',1),(449,'https://images.pexels.com/photos/17243662/pexels-photo-17243662.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',67,_binary '\0',2),(450,'https://images.pexels.com/photos/17243495/pexels-photo-17243495.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',67,_binary '\0',3),(451,'https://images.pexels.com/photos/8452062/pexels-photo-8452062.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',68,_binary '',1),(452,'https://images.pexels.com/photos/7716939/pexels-photo-7716939.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',68,_binary '\0',2),(453,'https://images.pexels.com/photos/30416961/pexels-photo-30416961.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',68,_binary '\0',3),(454,'https://images.pexels.com/photos/20462054/pexels-photo-20462054.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',69,_binary '',1),(455,'https://images.pexels.com/photos/20564413/pexels-photo-20564413.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',69,_binary '\0',2),(456,'https://images.pexels.com/photos/19862411/pexels-photo-19862411.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',69,_binary '\0',3),(457,'https://images.pexels.com/photos/6996083/pexels-photo-6996083.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',70,_binary '',1),(458,'https://images.pexels.com/photos/7943226/pexels-photo-7943226.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',70,_binary '\0',2),(459,'https://images.pexels.com/photos/6995898/pexels-photo-6995898.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',70,_binary '\0',3),(460,'https://images.pexels.com/photos/4972914/pexels-photo-4972914.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',71,_binary '',1),(461,'https://images.pexels.com/photos/2858321/pexels-photo-2858321.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',71,_binary '\0',2),(462,'https://images.pexels.com/photos/20153288/pexels-photo-20153288.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',71,_binary '\0',3),(463,'https://images.pexels.com/photos/1620765/pexels-photo-1620765.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',72,_binary '',1),(464,'https://images.pexels.com/photos/7432250/pexels-photo-7432250.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',72,_binary '\0',2),(465,'https://images.pexels.com/photos/2249249/pexels-photo-2249249.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',72,_binary '\0',3),(466,'https://images.pexels.com/photos/8515487/pexels-photo-8515487.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',73,_binary '',1),(467,'https://images.pexels.com/photos/32589036/pexels-photo-32589036.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',73,_binary '\0',2),(468,'https://images.pexels.com/photos/8515488/pexels-photo-8515488.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',73,_binary '\0',3),(469,'https://images.pexels.com/photos/6218355/pexels-photo-6218355.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',74,_binary '',1),(470,'https://images.pexels.com/photos/14127450/pexels-photo-14127450.png?auto=compress&cs=tinysrgb&h=650&w=940',74,_binary '\0',2),(471,'https://images.pexels.com/photos/23495731/pexels-photo-23495731.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',74,_binary '\0',3),(472,'https://images.pexels.com/photos/5973286/pexels-photo-5973286.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',75,_binary '',1),(473,'https://images.pexels.com/photos/20337359/pexels-photo-20337359.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',75,_binary '\0',2),(474,'https://images.pexels.com/photos/4690501/pexels-photo-4690501.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',75,_binary '\0',3),(475,'https://images.pexels.com/photos/10613156/pexels-photo-10613156.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',76,_binary '',1),(476,'https://images.pexels.com/photos/6453528/pexels-photo-6453528.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',76,_binary '\0',2),(477,'https://images.pexels.com/photos/10206444/pexels-photo-10206444.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',76,_binary '\0',3),(478,'https://images.pexels.com/photos/15577053/pexels-photo-15577053.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',77,_binary '',1),(479,'https://images.pexels.com/photos/12911882/pexels-photo-12911882.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',77,_binary '\0',2),(480,'https://images.pexels.com/photos/18138596/pexels-photo-18138596.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',77,_binary '\0',3),(481,'https://images.pexels.com/photos/21765085/pexels-photo-21765085.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',78,_binary '',1),(482,'https://images.pexels.com/photos/29471503/pexels-photo-29471503.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',78,_binary '\0',2),(483,'https://images.pexels.com/photos/31546245/pexels-photo-31546245.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',78,_binary '\0',3),(484,'https://images.pexels.com/photos/31529647/pexels-photo-31529647.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',79,_binary '',1),(485,'https://images.pexels.com/photos/26100314/pexels-photo-26100314.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',79,_binary '\0',2),(486,'https://images.pexels.com/photos/11842366/pexels-photo-11842366.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',79,_binary '\0',3),(487,'https://images.pexels.com/photos/34432093/pexels-photo-34432093.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',80,_binary '',1),(488,'https://images.pexels.com/photos/12513230/pexels-photo-12513230.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',80,_binary '\0',2),(489,'https://images.pexels.com/photos/10280614/pexels-photo-10280614.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',80,_binary '\0',3),(490,'https://images.pexels.com/photos/8411586/pexels-photo-8411586.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',81,_binary '',1),(491,'https://images.pexels.com/photos/5727047/pexels-photo-5727047.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',81,_binary '\0',2),(492,'https://images.pexels.com/photos/30710126/pexels-photo-30710126.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',81,_binary '\0',3),(493,'https://images.pexels.com/photos/7588168/pexels-photo-7588168.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',82,_binary '',1),(494,'https://images.pexels.com/photos/7327181/pexels-photo-7327181.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',82,_binary '\0',2),(495,'https://images.pexels.com/photos/7803555/pexels-photo-7803555.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',82,_binary '\0',3),(496,'https://images.pexels.com/photos/8764428/pexels-photo-8764428.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',83,_binary '',1),(497,'https://images.pexels.com/photos/30371816/pexels-photo-30371816.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',83,_binary '\0',2),(498,'https://images.pexels.com/photos/9717985/pexels-photo-9717985.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',83,_binary '\0',3),(499,'https://images.pexels.com/photos/4641824/pexels-photo-4641824.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',84,_binary '',1),(500,'https://images.pexels.com/photos/8899473/pexels-photo-8899473.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',84,_binary '\0',2),(501,'https://images.pexels.com/photos/18920428/pexels-photo-18920428.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',84,_binary '\0',3),(502,'https://images.pexels.com/photos/10650700/pexels-photo-10650700.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',85,_binary '',1),(503,'https://images.pexels.com/photos/30592378/pexels-photo-30592378.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',85,_binary '\0',2),(504,'https://images.pexels.com/photos/1617971/pexels-photo-1617971.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',85,_binary '\0',3),(505,'https://images.pexels.com/photos/7109118/pexels-photo-7109118.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',86,_binary '',1),(506,'https://images.pexels.com/photos/8180627/pexels-photo-8180627.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',86,_binary '\0',2),(507,'https://images.pexels.com/photos/9488415/pexels-photo-9488415.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',86,_binary '\0',3),(508,'https://images.pexels.com/photos/25015040/pexels-photo-25015040.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',87,_binary '',1),(509,'https://images.pexels.com/photos/18073681/pexels-photo-18073681.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',87,_binary '\0',2),(510,'https://images.pexels.com/photos/7804889/pexels-photo-7804889.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',87,_binary '\0',3),(511,'https://images.pexels.com/photos/33558727/pexels-photo-33558727.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',88,_binary '',1),(512,'https://images.pexels.com/photos/4487594/pexels-photo-4487594.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',88,_binary '\0',2),(513,'https://images.pexels.com/photos/19928294/pexels-photo-19928294.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',88,_binary '\0',3),(514,'https://images.pexels.com/photos/10844021/pexels-photo-10844021.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',89,_binary '',1),(515,'https://images.pexels.com/photos/1126999/pexels-photo-1126999.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',89,_binary '\0',2),(516,'https://images.pexels.com/photos/5592267/pexels-photo-5592267.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',89,_binary '\0',3),(517,'https://images.pexels.com/photos/33262508/pexels-photo-33262508.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',90,_binary '',1),(518,'https://images.pexels.com/photos/8152917/pexels-photo-8152917.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',90,_binary '\0',2),(519,'https://images.pexels.com/photos/6454940/pexels-photo-6454940.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',90,_binary '\0',3),(520,'https://images.pexels.com/photos/9594086/pexels-photo-9594086.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',91,_binary '',1),(521,'https://images.pexels.com/photos/7445019/pexels-photo-7445019.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',91,_binary '\0',2),(522,'https://images.pexels.com/photos/8453424/pexels-photo-8453424.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',91,_binary '\0',3),(523,'https://images.pexels.com/photos/17965011/pexels-photo-17965011.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',92,_binary '',1),(524,'https://images.pexels.com/photos/17244592/pexels-photo-17244592.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',92,_binary '\0',2),(525,'https://images.pexels.com/photos/17244602/pexels-photo-17244602.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',92,_binary '\0',3),(526,'https://images.pexels.com/photos/6295121/pexels-photo-6295121.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',93,_binary '',1),(527,'https://images.pexels.com/photos/6995892/pexels-photo-6995892.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',93,_binary '\0',2),(528,'https://images.pexels.com/photos/17983217/pexels-photo-17983217.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',93,_binary '\0',3),(529,'https://images.pexels.com/photos/34359458/pexels-photo-34359458.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',94,_binary '',1),(530,'https://images.pexels.com/photos/30433852/pexels-photo-30433852.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',94,_binary '\0',2),(531,'https://images.pexels.com/photos/15193921/pexels-photo-15193921.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',94,_binary '\0',3),(532,'https://images.pexels.com/photos/11936199/pexels-photo-11936199.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',95,_binary '',1),(533,'https://images.pexels.com/photos/12286497/pexels-photo-12286497.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',95,_binary '\0',2),(534,'https://images.pexels.com/photos/14037325/pexels-photo-14037325.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',95,_binary '\0',3),(535,'https://images.pexels.com/photos/28236529/pexels-photo-28236529.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',96,_binary '',1),(536,'https://images.pexels.com/photos/1035691/pexels-photo-1035691.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',96,_binary '\0',2),(537,'https://images.pexels.com/photos/8113006/pexels-photo-8113006.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',96,_binary '\0',3),(538,'https://images.pexels.com/photos/6862124/pexels-photo-6862124.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',97,_binary '',1),(539,'https://images.pexels.com/photos/33933256/pexels-photo-33933256.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',97,_binary '\0',2),(540,'https://images.pexels.com/photos/27274323/pexels-photo-27274323.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',97,_binary '\0',3),(541,'https://images.pexels.com/photos/30215186/pexels-photo-30215186.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',98,_binary '',1),(542,'https://images.pexels.com/photos/12740515/pexels-photo-12740515.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',98,_binary '\0',2),(543,'https://images.pexels.com/photos/34054517/pexels-photo-34054517.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',98,_binary '\0',3),(544,'https://images.pexels.com/photos/10798609/pexels-photo-10798609.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',99,_binary '',1),(545,'https://images.pexels.com/photos/8933054/pexels-photo-8933054.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',99,_binary '\0',2),(546,'https://images.pexels.com/photos/8093143/pexels-photo-8093143.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',99,_binary '\0',3),(547,'https://images.pexels.com/photos/34432093/pexels-photo-34432093.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',100,_binary '',1),(548,'https://images.pexels.com/photos/34429833/pexels-photo-34429833.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',100,_binary '\0',2),(549,'https://images.pexels.com/photos/34429810/pexels-photo-34429810.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',100,_binary '\0',3),(550,'https://images.pexels.com/photos/34380734/pexels-photo-34380734.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',101,_binary '',1),(551,'https://images.pexels.com/photos/34346669/pexels-photo-34346669.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',101,_binary '\0',2),(552,'https://images.pexels.com/photos/9784295/pexels-photo-9784295.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',101,_binary '\0',3),(553,'https://images.pexels.com/photos/27467423/pexels-photo-27467423.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',102,_binary '',1),(554,'https://images.pexels.com/photos/28027963/pexels-photo-28027963.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',102,_binary '\0',2),(555,'https://images.pexels.com/photos/28028171/pexels-photo-28028171.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',102,_binary '\0',3),(556,'https://images.pexels.com/photos/33405252/pexels-photo-33405252.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',103,_binary '',1),(557,'https://images.pexels.com/photos/16117426/pexels-photo-16117426.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',103,_binary '\0',2),(558,'https://images.pexels.com/photos/21820212/pexels-photo-21820212.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',103,_binary '\0',3),(559,'https://images.pexels.com/photos/4068314/pexels-photo-4068314.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',104,_binary '',1),(560,'https://images.pexels.com/photos/8148587/pexels-photo-8148587.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',104,_binary '\0',2),(561,'https://images.pexels.com/photos/7500306/pexels-photo-7500306.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',104,_binary '\0',3),(562,'https://images.pexels.com/photos/8989582/pexels-photo-8989582.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',105,_binary '',1),(563,'https://images.pexels.com/photos/29569061/pexels-photo-29569061.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',105,_binary '\0',2),(564,'https://images.pexels.com/photos/16329601/pexels-photo-16329601.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',105,_binary '\0',3),(565,'https://images.pexels.com/photos/9241476/pexels-photo-9241476.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',106,_binary '',1),(566,'https://images.pexels.com/photos/5199799/pexels-photo-5199799.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',106,_binary '\0',2),(567,'https://images.pexels.com/photos/5995849/pexels-photo-5995849.jpeg?auto=compress&cs=tinysrgb&h=650&w=940',106,_binary '\0',3);
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price_at_purchase` decimal(38,2) NOT NULL,
  `quantity` int NOT NULL,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `size_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4q98utpd73imf4yhttm3w0eax` (`product_id`),
  KEY `FKq1pfc355kfs5d2yjbdfhk4m5o` (`size_id`),
  KEY `FKjyu2qbqt8gnvno9oe9j2s2ldk` (`order_id`),
  CONSTRAINT `FK4q98utpd73imf4yhttm3w0eax` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKq1pfc355kfs5d2yjbdfhk4m5o` FOREIGN KEY (`size_id`) REFERENCES `sizes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (99,72.00,1,82,44,69),(100,55.00,1,82,46,69),(101,212.50,1,83,48,70),(102,102.00,1,83,52,68),(103,29.75,1,83,56,67),(104,85.00,1,84,60,67),(105,153.00,1,85,55,72),(106,127.50,1,86,47,69),(107,85.00,1,86,53,68);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_order_details_insert` AFTER INSERT ON `order_details` FOR EACH ROW BEGIN
    UPDATE orders o
    SET o.total_price = COALESCE((
        SELECT SUM(od.quantity * od.price_at_purchase)
        FROM order_details od
        WHERE od.order_id = o.id
    ), 0)
    WHERE o.id = NEW.order_id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_order_details_update` AFTER UPDATE ON `order_details` FOR EACH ROW BEGIN
    UPDATE orders o
    SET o.total_price = COALESCE((
        SELECT SUM(od.quantity * od.price_at_purchase)
        FROM order_details od
        WHERE od.order_id = o.id
    ), 0)
    WHERE o.id = NEW.order_id OR o.id = OLD.order_id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_order_details_delete` AFTER DELETE ON `order_details` FOR EACH ROW BEGIN
    UPDATE orders o
    SET o.total_price = COALESCE((
        SELECT SUM(od.quantity * od.price_at_purchase)
        FROM order_details od
        WHERE od.order_id = o.id
    ), 0)
    WHERE o.id = OLD.order_id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `status` enum('AWAITING_PAYMENT','CANCELLED','DELIVERED','PENDING','SHIPPED') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `address_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`),
  KEY `fk_orders_address` (`address_id`),
  CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_orders_address` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (82,'2024-01-15 14:30:00.000000','DELIVERED',127.00,43,NULL),(83,'2024-01-20 10:15:00.000000','SHIPPED',344.25,44,NULL),(84,'2024-01-25 16:45:00.000000','PENDING',85.00,45,NULL),(85,'2024-02-01 09:20:00.000000','DELIVERED',153.00,46,NULL),(86,'2024-02-05 13:10:00.000000','SHIPPED',212.50,43,NULL),(87,'2024-01-15 14:30:00.000000','DELIVERED',127.00,43,NULL),(88,'2024-01-20 10:15:00.000000','SHIPPED',189.25,44,NULL),(89,'2024-01-25 16:45:00.000000','PENDING',85.00,45,NULL),(90,'2024-02-01 09:20:00.000000','DELIVERED',153.00,46,NULL),(91,'2024-02-05 13:10:00.000000','SHIPPED',212.50,43,NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(38,2) NOT NULL,
  `payment_date` datetime(6) NOT NULL,
  `order_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj94hgy9v5fw1munb90tar2eje` (`user_id`),
  KEY `FK81gagumt0r8y3rmudcgpbk42l` (`order_id`),
  CONSTRAINT `FK81gagumt0r8y3rmudcgpbk42l` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKj94hgy9v5fw1munb90tar2eje` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (7,127.00,'2024-01-15 14:35:00.000000',82,43),(8,189.25,'2024-01-20 10:20:00.000000',83,44),(9,85.00,'2024-01-25 16:50:00.000000',84,45),(10,153.00,'2024-02-01 09:25:00.000000',85,46),(11,212.50,'2024-02-05 13:15:00.000000',86,43);
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prices`
--

DROP TABLE IF EXISTS `prices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prices` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `original_price` decimal(10,2) DEFAULT NULL,
  `present_price` decimal(10,2) DEFAULT NULL,
  `product_id` bigint NOT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhpva2t51a39twh6gdkxdcllyf` (`product_id`),
  CONSTRAINT `FKhpva2t51a39twh6gdkxdcllyf` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1745012379088 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prices`
--

LOCK TABLES `prices` WRITE;
/*!40000 ALTER TABLE `prices` DISABLE KEYS */;
INSERT INTO `prices` VALUES (1745012378983,'2025-10-24 20:33:34.000000',85.00,72.00,44,NULL),(1745012378985,'2025-10-24 20:33:34.000000',65.00,55.00,46,NULL),(1745012378986,'2025-10-24 20:33:34.000000',150.00,127.50,47,NULL),(1745012378987,'2025-10-24 20:33:34.000000',250.00,212.50,48,NULL),(1745012378988,'2025-10-24 20:33:34.000000',45.00,38.25,49,NULL),(1745012378989,'2025-10-24 20:33:34.000000',85.00,72.25,50,NULL),(1745012378990,'2025-10-24 20:33:34.000000',55.00,46.75,51,NULL),(1745012378991,'2025-10-24 20:33:34.000000',120.00,102.00,52,NULL),(1745012378992,'2025-10-24 20:33:34.000000',85.00,72.25,53,NULL),(1745012378993,'2025-10-24 20:33:34.000000',150.00,127.50,54,NULL),(1745012378994,'2025-10-24 20:33:34.000000',180.00,153.00,55,NULL),(1745012378995,'2025-10-24 20:33:34.000000',35.00,29.75,56,NULL),(1745012378996,'2025-10-24 20:33:34.000000',25.00,21.25,57,NULL),(1745012378997,'2025-10-24 20:33:34.000000',18.00,15.30,58,NULL),(1745012378998,'2025-10-24 20:33:34.000000',220.00,187.00,59,NULL),(1745012378999,'2025-10-24 20:33:34.000000',85.00,72.25,60,NULL),(1745012379000,'2025-10-24 20:33:34.000000',180.00,153.00,61,NULL),(1745012379001,'2025-10-24 20:33:34.000000',45.00,38.25,62,NULL),(1745012379002,'2025-10-24 20:33:34.000000',32.00,27.20,63,NULL),(1745012379003,'2025-10-24 20:33:34.000000',120.00,102.00,64,NULL),(1745012379004,'2025-10-24 20:33:46.000000',85.00,72.00,44,NULL),(1745012379006,'2025-10-24 20:33:46.000000',65.00,55.00,46,NULL),(1745012379007,'2025-10-24 20:33:46.000000',150.00,127.50,47,NULL),(1745012379008,'2025-10-24 20:33:46.000000',250.00,212.50,48,NULL),(1745012379009,'2025-10-24 20:33:46.000000',45.00,38.25,49,NULL),(1745012379010,'2025-10-24 20:33:46.000000',85.00,72.25,50,NULL),(1745012379011,'2025-10-24 20:33:46.000000',55.00,46.75,51,NULL),(1745012379012,'2025-10-24 20:33:46.000000',120.00,102.00,52,NULL),(1745012379013,'2025-10-24 20:33:46.000000',85.00,72.25,53,NULL),(1745012379014,'2025-10-24 20:33:46.000000',150.00,127.50,54,NULL),(1745012379015,'2025-10-24 20:33:46.000000',180.00,153.00,55,NULL),(1745012379016,'2025-10-24 20:33:46.000000',35.00,29.75,56,NULL),(1745012379017,'2025-10-24 20:33:46.000000',25.00,21.25,57,NULL),(1745012379018,'2025-10-24 20:33:46.000000',18.00,15.30,58,NULL),(1745012379019,'2025-10-24 20:33:46.000000',220.00,187.00,59,NULL),(1745012379020,'2025-10-24 20:33:46.000000',85.00,72.25,60,NULL),(1745012379021,'2025-10-24 20:33:46.000000',180.00,153.00,61,NULL),(1745012379022,'2025-10-24 20:33:46.000000',45.00,38.25,62,NULL),(1745012379023,'2025-10-24 20:33:46.000000',32.00,27.20,63,NULL),(1745012379024,'2025-10-24 20:33:46.000000',120.00,102.00,64,NULL),(1745012379025,'2025-10-24 20:34:01.000000',85.00,72.00,44,NULL),(1745012379027,'2025-10-24 20:34:01.000000',65.00,55.00,46,NULL),(1745012379028,'2025-10-24 20:34:01.000000',150.00,127.50,47,NULL),(1745012379029,'2025-10-24 20:34:01.000000',250.00,212.50,48,NULL),(1745012379030,'2025-10-24 20:34:01.000000',45.00,38.25,49,NULL),(1745012379031,'2025-10-24 20:34:01.000000',85.00,72.25,50,NULL),(1745012379032,'2025-10-24 20:34:01.000000',55.00,46.75,51,NULL),(1745012379033,'2025-10-24 20:34:01.000000',120.00,102.00,52,NULL),(1745012379034,'2025-10-24 20:34:01.000000',85.00,72.25,53,NULL),(1745012379035,'2025-10-24 20:34:01.000000',150.00,127.50,54,NULL),(1745012379036,'2025-10-24 20:34:01.000000',180.00,153.00,55,NULL),(1745012379037,'2025-10-24 20:34:01.000000',35.00,29.75,56,NULL),(1745012379038,'2025-10-24 20:34:01.000000',25.00,21.25,57,NULL),(1745012379039,'2025-10-24 20:34:01.000000',18.00,15.30,58,NULL),(1745012379040,'2025-10-24 20:34:01.000000',220.00,187.00,59,NULL),(1745012379041,'2025-10-24 20:34:01.000000',85.00,72.25,60,NULL),(1745012379042,'2025-10-24 20:34:01.000000',180.00,153.00,61,NULL),(1745012379043,'2025-10-24 20:34:01.000000',45.00,38.25,62,NULL),(1745012379044,'2025-10-24 20:34:01.000000',32.00,27.20,63,NULL),(1745012379045,'2025-10-24 20:34:01.000000',120.00,102.00,64,NULL),(1745012379046,'2025-10-24 22:13:19.000000',180.00,153.00,65,NULL),(1745012379047,'2025-10-24 22:13:19.000000',65.00,55.25,66,NULL),(1745012379048,'2025-10-24 22:13:19.000000',55.00,46.75,67,NULL),(1745012379049,'2025-10-24 22:13:19.000000',75.00,63.75,68,NULL),(1745012379050,'2025-10-24 22:13:19.000000',220.00,187.00,69,NULL),(1745012379051,'2025-10-24 22:13:19.000000',95.00,80.75,70,NULL),(1745012379052,'2025-10-24 22:13:19.000000',110.00,93.50,71,NULL),(1745012379053,'2025-10-24 22:13:19.000000',85.00,72.25,72,NULL),(1745012379054,'2025-10-24 22:13:19.000000',95.00,80.75,73,NULL),(1745012379055,'2025-10-24 22:13:19.000000',70.00,59.50,74,NULL),(1745012379056,'2025-10-24 22:13:19.000000',78.00,66.30,75,NULL),(1745012379057,'2025-10-24 22:13:19.000000',48.00,40.80,76,NULL),(1745012379058,'2025-10-24 22:13:19.000000',135.00,114.75,77,NULL),(1745012379059,'2025-10-24 22:13:19.000000',42.00,35.70,78,NULL),(1745012379060,'2025-10-24 22:13:19.000000',165.00,140.25,79,NULL),(1745012379061,'2025-10-24 22:13:19.000000',280.00,238.00,80,NULL),(1745012379062,'2025-10-24 22:13:19.000000',55.00,46.75,81,NULL),(1745012379063,'2025-10-24 22:13:19.000000',75.00,63.75,82,NULL),(1745012379064,'2025-10-24 22:13:19.000000',85.00,72.25,83,NULL),(1745012379065,'2025-10-24 22:13:19.000000',38.00,32.30,84,NULL),(1745012379066,'2025-10-24 22:13:19.000000',195.00,165.75,85,NULL),(1745012379067,'2025-10-24 22:13:19.000000',52.00,44.20,86,NULL),(1745012379068,'2025-10-24 22:13:19.000000',68.00,57.80,87,NULL),(1745012379069,'2025-10-24 22:13:19.000000',45.00,38.25,88,NULL),(1745012379070,'2025-10-24 22:13:19.000000',125.00,106.25,89,NULL),(1745012379071,'2025-10-24 22:13:19.000000',58.00,49.30,90,NULL),(1745012379072,'2025-10-24 22:13:19.000000',72.00,61.20,91,NULL),(1745012379073,'2025-10-24 22:13:19.000000',145.00,123.25,92,NULL),(1745012379074,'2025-10-24 22:13:19.000000',95.00,80.75,93,NULL),(1745012379075,'2025-10-24 22:13:19.000000',132.00,112.20,94,NULL),(1745012379076,'2025-10-24 22:13:19.000000',78.00,66.30,95,NULL),(1745012379077,'2025-10-24 22:13:19.000000',168.00,142.80,96,NULL),(1745012379078,'2025-10-24 22:13:19.000000',115.00,97.75,97,NULL),(1745012379079,'2025-10-24 22:13:19.000000',65.00,55.25,98,NULL),(1745012379080,'2025-10-24 22:13:19.000000',125.00,106.25,99,NULL),(1745012379081,'2025-10-24 22:13:19.000000',95.00,80.75,100,NULL),(1745012379082,'2025-10-24 22:13:19.000000',68.00,57.80,101,NULL),(1745012379083,'2025-10-24 22:13:19.000000',55.00,46.75,102,NULL),(1745012379084,'2025-10-24 22:13:19.000000',28.00,23.80,103,NULL),(1745012379085,'2025-10-24 22:13:19.000000',88.00,74.80,104,NULL),(1745012379086,'2025-10-24 22:13:19.000000',195.00,165.75,105,NULL),(1745012379087,'2025-10-24 22:13:19.000000',58.00,49.30,106,NULL);
/*!40000 ALTER TABLE `prices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `measurements` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `product_details` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `category_id` bigint NOT NULL,
  `category` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `image_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `price` double DEFAULT NULL,
  `archived` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (44,'S-M-L','Міді сукня з квітковим принтом','Міді довжина\nПрямий крій\nКласичний фіт',31,NULL,'Тканина скюба-стиль: товста, еластична та супер-гладка\nОсновна: 75% Бавовна, 20% Поліестер, 5% Еластан.',NULL,NULL,1),(46,'S-M-L-XL','Вузькі джинси','Вузький крій\nВисокий пояс\nКласичні кишені',31,NULL,'Еластична тканина: міцна, розтягується та зберігає форму\nОсновна: 98% Бавовна, 2% Еластан.',NULL,NULL,1),(47,'S-M-L','Кардиган з кашеміру','Відкритий перед\nДовгі рукави\nКласичний крій',31,NULL,'М\'який кардиган з натурального кашеміру. Універсальний елемент для будь-якої пори року.',NULL,NULL,0),(48,'M-L-XL','Класичний костюм','Класичні лацкани\nПрямий крій\nКласичні кишені',32,NULL,'Діловий костюм з вовни. Високоякісний пошив та ідеальна посадка.',NULL,NULL,0),(49,'M-L-XL','Сорочка Оксфорд','Класичний комір\nГудзички спереду\nПрямий крій',32,NULL,'Класична сорочка з тканини оксфорд. Ідеально підходить як для офісу, так і для повсякденного носіння.',NULL,NULL,0),(50,'M-L-XL','Светр з мериносової вовни','Круглий виріз\nДовгі рукави\nКласичний крій',32,NULL,'Теплий светр з натуральної мериносової вовни. Відмінно зберігає тепло та комфорт.',NULL,NULL,0),(51,'M-L-XL','Штани чіно','Прямий крій\nКласичні кишені\nCasual стиль',32,NULL,'Стильні штани чіно з міцної бавовняної тканини. Ідеальні для casual стилю.',NULL,NULL,0),(52,'36-37-38-39-40','Туфлі на підборах','Закритий носок\nКласичний дизайн\nЕлегантний стиль',33,NULL,'Елегантні туфлі на середніх підборах. Комфортні та стильні для особливих випадків.',NULL,NULL,0),(53,'36-37-38-39-40-41-42','Білі кросівки','Класичний дизайн\nЗручна підошва\nПовсякденний стиль',33,NULL,'Класичні білі кросівки. Зручні та універсальні для повсякденного носіння.',NULL,NULL,0),(54,'40-41-42-43-44','Челсі чоботи','Еластичні вставки\nКласичний дизайн\nОсінньо-зимовий стиль',33,NULL,'Стильні чоботи челсі з м\'якої шкіри. Ідеальні для осінньо-зимового сезону.',NULL,NULL,0),(55,'36-37-38-39-40','Чоботи на платформі','Модний дизайн\nВисокі підбори\nЯскравий стиль',33,NULL,'Модні чоботи на платформі. Ідеальні для створення яскравих образів.',NULL,NULL,0),(56,'Universal','Шовковий шарф','Класичний розмір\nЕлегантний дизайн\nУніверсальний аксесуар',34,NULL,'Розкішний шарф з натурального шовку. Елегантний аксесуар для будь-якого образу.',NULL,NULL,0),(57,'S-M-L','Шкіряний ремінь','Класична пряжка\nУніверсальний розмір\nПрактичний аксесуар',34,NULL,'Класичний ремінь з натуральної шкіри. Практичний та стильний аксесуар.',NULL,NULL,0),(58,'Universal','Вовняний капелюх','Теплий матеріал\nКласичний дизайн\nЗимовий аксесуар',34,NULL,'Теплий капелюх з мериносової вовни. Ідеальний для холодної погоди.',NULL,NULL,0),(59,'Medium size','Шкіряна сумка-тоут','Великий розмір\nДовгі ручки\nПрактичний дизайн',35,NULL,'Стильна сумка-тоут з натуральної шкіри. Просторна та практична.',NULL,NULL,1),(60,'Compact','Клатч з ланцюжком','Компактний розмір\nЕлегантний дизайн\nВечірній стиль',35,NULL,'Елегантний клатч з металевим ланцюжком. Ідеальний для вечірніх виходів.',NULL,NULL,0),(61,'Medium size','Шкіряний рюкзак','Сучасний дизайн\nЗручні ремені\nПовсякденний стиль',35,NULL,'Сучасний рюкзак з натуральної шкіри. Зручний та стильний для повсякденного використання.',NULL,NULL,0),(62,'Universal','Срібні сережки','Делікатний дизайн\nКласичний стиль\nУніверсальний аксесуар',36,NULL,'Елегантні сережки зі сталі з покриттям. Делікатний та стильний аксесуар.',NULL,NULL,0),(63,'Size 16-18','Кільце з каменем','Стильний дизайн\nЕлегантний вигляд\nЯскравий аксесуар',36,NULL,'Стильне кільце з напівкоштовним каменем. Яскравий та елегантний аксесуар.',NULL,NULL,0),(64,'45-50 cm','Золотий ланцюжок','Тонкий ланцюжок\nУніверсальний дизайн\nКласичний стиль',36,NULL,'Тонкий золотий ланцюжок. Універсальна прикраса для будь-якого образу.',NULL,NULL,0),(65,NULL,'Вечірня сукня з пайетками','Довга сукня\nЕлегантний дизайн\nОсобливі випадки',31,NULL,'Елегантна довга вечірня сукня з пайетками. Ідеальна для особливих випадків.',NULL,NULL,1),(66,NULL,'Бавовняна літня сукня','Легкий матеріал\nКомфортний крій\nЛітній стиль',31,NULL,'Зручна бавовняна літня сукня. Легка та комфортна для теплих днів.',NULL,NULL,0),(67,NULL,'Чорна спідниця-олівець','Олівець крій\nПрофесійний стиль\nДіловий вигляд',31,NULL,'Професійна спідниця-олівець чорного кольору. Ідеальна для ділового стилю.',NULL,NULL,0),(68,NULL,'Блузка з шифону з оборками','Оборки\nРомантичний дизайн\nЕлегантний стиль',31,NULL,'Легка блузка з шифону з ніжними оборками. Романтичний та елегантний вигляд.',NULL,NULL,0),(69,NULL,'Шкіряна куртка-мотоцикліст','Асиметрична застібка\nМотоцикліст стиль\nСтильний дизайн',31,NULL,'Стильна шкіряна куртка в стилі мотоцикліста з асиметричною застібкою.',NULL,NULL,0),(70,NULL,'Трикотажна сукня з водолазкою','Водолазка\nЗатишний дизайн\nПрохолодний сезон',31,NULL,'Затишна трикотажна сукня з водолазкою. Зручна та стильна для прохолодних днів.',NULL,NULL,0),(71,NULL,'Бордова сукня з запахом','Запах\nФемінний дизайн\nЕлегантний стиль',31,NULL,'Елегантна сукня з запахом бордового кольору. Фемінний та стильний вигляд.',NULL,NULL,0),(72,NULL,'Біла сукня-сорочка','Довгі рукави\nСорочка крій\nУніверсальний стиль',31,NULL,'Класична біла сукня-сорочка з довгими рукавами. Універсальна та стильна.',NULL,NULL,1),(73,NULL,'Джинсова куртка з потертостями','Потертості\nМодний дизайн\nНеформальний стиль',31,NULL,'Модна джинсова куртка з потертостями. Стильний та неформальний вигляд.',NULL,NULL,0),(74,NULL,'Кремова блузка з мереживом','Мереживо\nФемінний дизайн\nРомантичний стиль',31,NULL,'Фемінна блузка з мереживом кремового кольору. Романтичний та елегантний вигляд.',NULL,NULL,0),(75,NULL,'Спідниця з плісированої сатини','Пліси\nМіді довжина\nЕлегантний стиль',31,NULL,'Універсальна міді спідниця з плісированої сатини. Елегантна та стильна.',NULL,NULL,0),(76,NULL,'Йога штани з високим поясом','Спортивний дизайн\nЗручний фіт',31,NULL,'Зручні йога штани з високим поясом. Ідеальні для спорту та повсякденного носіння.',NULL,NULL,0),(77,NULL,'Темно-синій підігнаний блейзер','Підігнаний крій\nКласичний дизайн\nПрофесійний стиль',31,NULL,'Елегантний блейзер темно-синього кольору з класичним кроєм. Професійний вигляд.',NULL,NULL,0),(78,NULL,'Топ з оголеними плечима','Оборки\nРомантичний дизайн\nФемінний стиль',31,NULL,'Романтичний топ з оголеними плечима з ніжними оборками. Фемінний та стильний.',NULL,NULL,0),(79,NULL,'Бежевий тренч','Класичний крій\nПрактичний дизайн\nЕлегантний стиль',31,NULL,'Стильний тренч бежевого кольору з класичним кроєм. Елегантний та практичний.',NULL,NULL,0),(80,NULL,'Преміум шкіряна куртка','Висока якість\nСтильний дизайн\nДовговічність',32,NULL,'Преміум шкіряна куртка з високою якістю пошиву. Стильна та довговічна.',NULL,NULL,0),(81,NULL,'Темно-синя бавовняна поло','Класичний дизайн\nПрактичний стиль',32,NULL,'Зручна поло сорочка темно-синього кольору. Класична та практична.',NULL,NULL,0),(82,NULL,'Джинси з прямими штанинами','Класичний дизайн\nУніверсальний фіт\nСтильний вигляд',32,NULL,'Класичні джинси з прямими штанинами. Універсальні та стильні.',NULL,NULL,0),(83,NULL,'Сірі ділові штани','Професійний дизайн\nЕлегантний крій\nДіловий стиль',32,NULL,'Елегантні ділові штани сірого кольору. Професійний та стильний вигляд.',NULL,NULL,0),(84,NULL,'Сіра сорочка хенлі','Хенлі крій\nПовсякденний дизайн\nЗручний стиль',32,NULL,'Повсякденна сорочка хенлі сірого кольору. Зручна та стильна.',NULL,NULL,0),(85,NULL,'Зимова парка','Теплий матеріал\nЗимовий дизайн\nЗахист від холоду',32,NULL,'Тепла парка з хутряним капюшоном. Ідеальна для холодної зими.',NULL,NULL,1),(86,NULL,'Спортивні штани для бігу','Спортивний дизайн\nФункціональність',32,NULL,'Спортивні штани для бігу з бічними смугами. Зручні та функціональні.',NULL,NULL,0),(87,NULL,'Чорний діловий жилет','Сатинова підкладка\nПрофесійний дизайн\nДіловий стиль',32,NULL,'Діловий жилет чорного кольору з сатиновою підкладкою. Елегантний та професійний.',NULL,NULL,0),(88,NULL,'Камуфляжні шорти карго','Великі кишені\nПрактичний дизайн\nСтильний вигляд',32,NULL,'Зручні шорти карго камуфляжного кольору з великими кишенями. Практичні та стильні.',NULL,NULL,0),(89,NULL,'Оливкова куртка-бомбер','Бомбер стиль\nМодний дизайн\nПрактичний вигляд',32,NULL,'Стильна куртка-бомбер оливкового кольору. Модна та практична.',NULL,NULL,0),(90,NULL,'Червона сорочка з фланелі','Затишний дизайн\nКласичний стиль',32,NULL,'Класична сорочка з фланелі червоного кольору. Затишна та стильна.',NULL,NULL,0),(91,NULL,'Біла сорочка з лляного полотна','Дихаюча тканина\nЛітній стиль',32,NULL,'Дихаюча сорочка з лляного полотна білого кольору. Легка та комфортна.',NULL,NULL,0),(92,NULL,'Коричневі туфлі оксфорд','Оксфорд стиль\nКласичний дизайн',33,NULL,'Класичні туфлі оксфорд коричневого кольору з натуральної шкіри. Елегантні та довговічні.',NULL,NULL,0),(93,NULL,'Сірі бігові кросівки','Біговий дизайн\nСпортивний стиль',33,NULL,'Зручні бігові кросівки сірого кольору з амортизацією. Ідеальні для спорту.',NULL,NULL,0),(94,NULL,'Бордові лофери','Елегантний дизайн\nЗручний стиль',33,NULL,'Елегантні лофери бордового кольору з натуральної шкіри. Стильні та зручні.',NULL,NULL,0),(95,NULL,'Чорні кросівки з високим верхом','Високий верх\nМодний дизайн\nУніверсальний стиль',33,NULL,'Модні кросівки з високим верхом чорного кольору. Стильні та універсальні.',NULL,NULL,0),(96,NULL,'Непромокні зимові чоботи','Зимовий дизайн\nЗахист від холоду',33,NULL,'Теплі зимові чоботи з хутряною підкладкою. Захищають від холоду та вологи.',NULL,NULL,0),(97,NULL,'Чоботи з квадратними підборами','Стильний дизайн\nКомфортні\nЕлегантний вигляд',33,NULL,'Стильні чоботи з квадратними підборами. Комфортні та елегантні.',NULL,NULL,0),(98,NULL,'Темно-сині кросівки без шнурків','Без шнурків\nПовсякденний дизайн\nПрактичний стиль',33,NULL,'Повсякденні кросівки без шнурків темно-синього кольору. Зручні та практичні.',NULL,NULL,0),(99,NULL,'Тілесні туфлі з гострим носом','Гострий ніс\nВишуканий дизайн\nУніверсальний стиль',33,NULL,'Вишукані туфлі з гострим носом тілесного кольору. Елегантні та універсальні.',NULL,NULL,1),(100,NULL,'Поляризовані сонцезахисні окуляри','Стильний дизайн\nЗахист від сонця',34,NULL,'Стильні сонцезахисні окуляри з поляризованими лінзами. Захищають від сонця.',NULL,NULL,0),(101,NULL,'Рукавички з кашеміру','Сірий колір\nТеплий матеріал\nЗимовий аксесуар',34,NULL,'Теплі рукавички з кашеміру сірого кольору. М\'які та зручні для холодної погоди.',NULL,NULL,0),(102,NULL,'Шкіряний гаманець-двоскладка','Двоскладка\nКілька відділень\nПрактичний дизайн',34,NULL,'Класичний шкіряний гаманець-двоскладка з кількома відділеннями. Практичний та стильний.',NULL,NULL,0),(103,NULL,'Бейсбольна кепка з вишивкою','Бейсбольний стиль\nМодний дизайн\nПовсякденний аксесуар',34,NULL,'Модна бейсбольна кепка з вишивкою. Стильна та практична для повсякденного носіння.',NULL,NULL,0),(104,NULL,'Полотняна сумка-курьєр','Великий розмір\nПрактичний дизайн\nПовсякденний стиль',35,NULL,'Простора сумка-курьєр з полотна. Практична та зручна для повсякденного використання.',NULL,NULL,0),(105,NULL,'Наплічна сумка з ланцюжком','Ланцюжок\nКомпактний розмір\nЕлегантний дизайн',35,NULL,'Елегантна наплічна сумка з ланцюжком чорного кольору. Компактна та стильна.',NULL,NULL,0),(106,NULL,'Срібний кулон у формі серця','Форма серця\nДелікатний дизайн\nРомантичний стиль',36,NULL,'Делікатний кулон-намисто зі срібла у формі серця. Романтичний та елегантний.',NULL,NULL,0);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sizes`
--

DROP TABLE IF EXISTS `sizes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sizes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKrmd719hqv99q34v9yfelrkq3v` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sizes`
--

LOCK TABLES `sizes` WRITE;
/*!40000 ALTER TABLE `sizes` DISABLE KEYS */;
INSERT INTO `sizes` VALUES (67,'36'),(68,'37'),(69,'38'),(70,'39'),(71,'40'),(72,'41'),(73,'42'),(74,'43'),(75,'44'),(76,'45'),(64,'L'),(80,'Large'),(63,'M'),(79,'Medium'),(77,'One Size'),(62,'S'),(78,'Small'),(65,'XL'),(61,'XS'),(66,'XXL');
/*!40000 ALTER TABLE `sizes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `first_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `last_login` datetime(6) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (42,'admin@kounak-fashion.ru','Alexander','Kunakov','$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi','+79161234567','ADMIN',NULL,_binary '',NULL,'2025-10-24 20:30:44'),(43,'anna.petrova@email.ru','Anna','Petrova','$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi','+79161234568','USER',NULL,_binary '',NULL,'2025-10-24 20:30:44'),(44,'dmitry.volkov@gmail.com','Dmitry','Volkov','$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi','+79161234569','USER',NULL,_binary '',NULL,'2025-10-24 20:30:44'),(45,'elena.smirnova@yandex.ru','Elena','Smirnova','$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi','+79161234570','USER',NULL,_binary '',NULL,'2025-10-24 20:30:44'),(46,'mikhail.kuznetsov@mail.ru','Mikhail','Kuznetsov','$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi','+79161234571','USER',NULL,_binary '',NULL,'2025-10-24 20:30:44'),(47,'admin@kounak.com','Admin','Kounak','$2b$12$75onlqjxGck.j3uWicyB0ud85A/W82flVtQd7k0/nE.N97LIhMuGy','+79161234567','ADMIN','Admin Office, Moscow',_binary '','2025-10-24 21:55:16.847037','2025-10-24 22:53:08'),(48,'koc9ihbi4@gmail.com','Константин','Кунак','$2a$10$ObH3ktBFtP.kxMIEjzpv2eoVa/ew5/U718TVieour78mCkGu/1sr2','+75714900960','ADMIN',NULL,_binary '','2025-10-25 13:29:51.414101','2025-10-24 22:46:13'),(49,'test@example.com','Test','User','$2a$10$2f9W/njhWIFcEMPYejr9PeeAYFdNYENYLOQKkjsZN1RAPc3blHGa.','+71234567890','USER',NULL,_binary '','2025-10-25 14:25:40.674608','2025-10-25 14:07:39');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `product_id` bigint NOT NULL,
  `size_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKow13o6v2o8btmca0nw5pblpss` (`product_id`),
  KEY `FK8htn7nnye2lswo3sx0j4d17pc` (`size_id`),
  CONSTRAINT `FK8htn7nnye2lswo3sx0j4d17pc` FOREIGN KEY (`size_id`) REFERENCES `sizes` (`id`),
  CONSTRAINT `FKow13o6v2o8btmca0nw5pblpss` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17450123791172688 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
INSERT INTO `warehouse` VALUES (17450123791172067,20,52,67),(17450123791172068,25,52,68),(17450123791172069,30,52,69),(17450123791172070,28,52,70),(17450123791172071,22,52,71),(17450123791172072,35,53,67),(17450123791172073,40,53,68),(17450123791172074,45,53,69),(17450123791172075,38,53,70),(17450123791172076,32,53,71),(17450123791172077,25,53,72),(17450123791172078,20,53,73),(17450123791172079,15,54,71),(17450123791172080,18,54,72),(17450123791172081,22,54,73),(17450123791172082,20,54,74),(17450123791172083,16,54,75),(17450123791172084,12,55,67),(17450123791172085,15,55,68),(17450123791172086,18,55,69),(17450123791172087,16,55,70),(17450123791172088,14,55,71),(17450123791172089,50,56,77),(17450123791172090,40,57,77),(17450123791172093,60,58,77),(17450123791172094,25,59,77),(17450123791172095,20,60,77),(17450123791172096,18,61,77),(17450123791172097,100,62,77),(17450123791172098,80,63,77),(17450123791172099,60,64,77),(17450123791172426,15,65,61),(17450123791172427,20,65,62),(17450123791172428,25,65,63),(17450123791172429,20,65,64),(17450123791172430,15,65,65),(17450123791172431,20,66,61),(17450123791172432,25,66,62),(17450123791172433,30,66,63),(17450123791172434,25,66,64),(17450123791172435,20,66,65),(17450123791172436,18,67,61),(17450123791172437,22,67,62),(17450123791172438,28,67,63),(17450123791172439,22,67,64),(17450123791172440,18,67,65),(17450123791172441,20,68,61),(17450123791172442,25,68,62),(17450123791172443,30,68,63),(17450123791172444,25,68,64),(17450123791172445,20,68,65),(17450123791172446,12,69,61),(17450123791172447,15,69,62),(17450123791172448,18,69,63),(17450123791172449,15,69,64),(17450123791172450,12,69,65),(17450123791172451,15,70,61),(17450123791172452,20,70,62),(17450123791172453,25,70,63),(17450123791172454,20,70,64),(17450123791172455,15,70,65),(17450123791172456,18,71,61),(17450123791172457,22,71,62),(17450123791172458,28,71,63),(17450123791172459,22,71,64),(17450123791172460,18,71,65),(17450123791172461,20,72,61),(17450123791172462,25,72,62),(17450123791172463,30,72,63),(17450123791172464,25,72,64),(17450123791172465,20,72,65),(17450123791172466,15,73,61),(17450123791172467,20,73,62),(17450123791172468,25,73,63),(17450123791172469,20,73,64),(17450123791172470,15,73,65),(17450123791172471,18,74,61),(17450123791172472,22,74,62),(17450123791172473,28,74,63),(17450123791172474,22,74,64),(17450123791172475,18,74,65),(17450123791172476,20,75,61),(17450123791172477,25,75,62),(17450123791172478,30,75,63),(17450123791172479,25,75,64),(17450123791172480,20,75,65),(17450123791172481,25,76,61),(17450123791172482,30,76,62),(17450123791172483,35,76,63),(17450123791172484,30,76,64),(17450123791172485,25,76,65),(17450123791172486,15,77,61),(17450123791172487,20,77,62),(17450123791172488,25,77,63),(17450123791172489,20,77,64),(17450123791172490,15,77,65),(17450123791172491,22,78,61),(17450123791172492,28,78,62),(17450123791172493,32,78,63),(17450123791172494,28,78,64),(17450123791172495,22,78,65),(17450123791172496,12,79,61),(17450123791172497,15,79,62),(17450123791172498,18,79,63),(17450123791172499,15,79,64),(17450123791172500,12,79,65),(17450123791172501,10,80,62),(17450123791172502,15,80,63),(17450123791172503,18,80,64),(17450123791172504,15,80,65),(17450123791172505,10,80,66),(17450123791172506,20,81,62),(17450123791172507,25,81,63),(17450123791172508,30,81,64),(17450123791172509,25,81,65),(17450123791172510,20,81,66),(17450123791172511,18,82,62),(17450123791172512,22,82,63),(17450123791172513,28,82,64),(17450123791172514,22,82,65),(17450123791172515,18,82,66),(17450123791172516,15,83,62),(17450123791172517,20,83,63),(17450123791172518,25,83,64),(17450123791172519,20,83,65),(17450123791172520,15,83,66),(17450123791172521,22,84,62),(17450123791172522,28,84,63),(17450123791172523,32,84,64),(17450123791172524,28,84,65),(17450123791172525,22,84,66),(17450123791172526,12,85,62),(17450123791172527,15,85,63),(17450123791172528,18,85,64),(17450123791172529,15,85,65),(17450123791172530,12,85,66),(17450123791172531,20,86,62),(17450123791172532,25,86,63),(17450123791172533,30,86,64),(17450123791172534,25,86,65),(17450123791172535,20,86,66),(17450123791172536,15,87,62),(17450123791172537,20,87,63),(17450123791172538,25,87,64),(17450123791172539,20,87,65),(17450123791172540,15,87,66),(17450123791172541,18,88,62),(17450123791172542,22,88,63),(17450123791172543,28,88,64),(17450123791172544,22,88,65),(17450123791172545,18,88,66),(17450123791172546,12,89,62),(17450123791172547,15,89,63),(17450123791172548,18,89,64),(17450123791172549,15,89,65),(17450123791172550,12,89,66),(17450123791172551,20,90,62),(17450123791172552,25,90,63),(17450123791172553,30,90,64),(17450123791172554,25,90,65),(17450123791172555,20,90,66),(17450123791172556,18,91,62),(17450123791172557,22,91,63),(17450123791172558,28,91,64),(17450123791172559,22,91,65),(17450123791172560,18,91,66),(17450123791172561,12,92,67),(17450123791172562,15,92,68),(17450123791172563,18,92,69),(17450123791172564,20,92,70),(17450123791172565,18,92,71),(17450123791172566,15,92,72),(17450123791172567,12,92,73),(17450123791172568,10,92,74),(17450123791172569,8,92,75),(17450123791172570,6,92,76),(17450123791172571,15,93,67),(17450123791172572,18,93,68),(17450123791172573,22,93,69),(17450123791172574,25,93,70),(17450123791172575,22,93,71),(17450123791172576,18,93,72),(17450123791172577,15,93,73),(17450123791172578,12,93,74),(17450123791172579,10,93,75),(17450123791172580,8,93,76),(17450123791172581,10,94,67),(17450123791172582,12,94,68),(17450123791172583,15,94,69),(17450123791172584,18,94,70),(17450123791172585,15,94,71),(17450123791172586,12,94,72),(17450123791172587,10,94,73),(17450123791172588,8,94,74),(17450123791172589,6,94,75),(17450123791172590,5,94,76),(17450123791172591,18,95,67),(17450123791172592,22,95,68),(17450123791172593,25,95,69),(17450123791172594,28,95,70),(17450123791172595,25,95,71),(17450123791172596,22,95,72),(17450123791172597,18,95,73),(17450123791172598,15,95,74),(17450123791172599,12,95,75),(17450123791172600,10,95,76),(17450123791172601,12,96,67),(17450123791172602,15,96,68),(17450123791172603,18,96,69),(17450123791172604,20,96,70),(17450123791172605,18,96,71),(17450123791172606,15,96,72),(17450123791172607,12,96,73),(17450123791172608,10,96,74),(17450123791172609,8,96,75),(17450123791172610,6,96,76),(17450123791172611,15,97,67),(17450123791172612,18,97,68),(17450123791172613,22,97,69),(17450123791172614,25,97,70),(17450123791172615,22,97,71),(17450123791172616,18,97,72),(17450123791172617,15,97,73),(17450123791172618,12,97,74),(17450123791172619,10,97,75),(17450123791172620,8,97,76),(17450123791172621,18,98,67),(17450123791172622,22,98,68),(17450123791172623,25,98,69),(17450123791172624,28,98,70),(17450123791172625,25,98,71),(17450123791172626,22,98,72),(17450123791172627,18,98,73),(17450123791172628,15,98,74),(17450123791172629,12,98,75),(17450123791172630,10,98,76),(17450123791172631,15,99,67),(17450123791172632,18,99,68),(17450123791172633,22,99,69),(17450123791172634,25,99,70),(17450123791172635,22,99,71),(17450123791172636,18,99,72),(17450123791172637,15,99,73),(17450123791172638,12,99,74),(17450123791172639,10,99,75),(17450123791172640,8,99,76),(17450123791172641,50,100,77),(17450123791172642,40,101,77),(17450123791172643,45,102,77),(17450123791172644,60,103,77),(17450123791172645,30,104,77),(17450123791172646,25,105,77),(17450123791172647,35,106,77),(17450123791172648,15,44,61),(17450123791172649,20,44,62),(17450123791172650,25,44,63),(17450123791172651,20,44,64),(17450123791172652,15,44,65),(17450123791172658,20,46,61),(17450123791172659,25,46,62),(17450123791172660,30,46,63),(17450123791172661,25,46,64),(17450123791172662,20,46,65),(17450123791172663,15,47,61),(17450123791172664,20,47,62),(17450123791172665,25,47,63),(17450123791172666,20,47,64),(17450123791172667,15,47,65),(17450123791172668,10,48,62),(17450123791172669,15,48,63),(17450123791172670,18,48,64),(17450123791172671,15,48,65),(17450123791172672,10,48,66),(17450123791172673,20,49,62),(17450123791172674,25,49,63),(17450123791172675,30,49,64),(17450123791172676,25,49,65),(17450123791172677,20,49,66),(17450123791172678,18,50,62),(17450123791172679,22,50,63),(17450123791172680,28,50,64),(17450123791172681,22,50,65),(17450123791172682,18,50,66),(17450123791172683,20,51,62),(17450123791172684,25,51,63),(17450123791172685,30,51,64),(17450123791172686,25,51,65),(17450123791172687,20,51,66);
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wishlist`
--

DROP TABLE IF EXISTS `wishlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wishlist` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6p7qhvy1bfkri13u29x6pu8au` (`product_id`),
  KEY `FKtrd6335blsefl2gxpb8lr0gr7` (`user_id`),
  CONSTRAINT `FK6p7qhvy1bfkri13u29x6pu8au` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKtrd6335blsefl2gxpb8lr0gr7` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishlist`
--

LOCK TABLES `wishlist` WRITE;
/*!40000 ALTER TABLE `wishlist` DISABLE KEYS */;
INSERT INTO `wishlist` VALUES (62,47,43),(63,50,44),(64,54,45),(65,63,46),(66,64,43);
/*!40000 ALTER TABLE `wishlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'fashionDB'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-26  3:22:30
