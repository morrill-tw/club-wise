CREATE DATABASE  IF NOT EXISTS `club_wise` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `club_wise`;
-- MySQL dump 10.13  Distrib 8.0.38, for macos14 (x86_64)
--
-- Host: localhost    Database: club_wise
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `club`
--

DROP TABLE IF EXISTS `club`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `club` (
  `club_name` varchar(64) NOT NULL,
  `club_description` varchar(500) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `category` enum('STEM','Arts and Culture','Sports and Recreation','Community Service and Social Impact','Social and Special Interests','Media and Communications','Religious and Spiritual') NOT NULL,
  PRIMARY KEY (`club_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `club`
--

LOCK TABLES `club` WRITE;
/*!40000 ALTER TABLE `club` DISABLE KEYS */;
INSERT INTO `club` VALUES ('Cheese Club','Eat cheese',0,'Social and Special Interests'),('Cooking Club','Make food',0,'Social and Special Interests'),('Generate','Make prototypes',1,'STEM'),('Live Music NEU','Hosts live music events.',1,'Media and Communications'),('NER','Make cool electric cars!',1,'STEM');
/*!40000 ALTER TABLE `club` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  `event_title` varchar(64) NOT NULL,
  `event_description` varchar(500) NOT NULL,
  `event_date` date NOT NULL,
  `club_name` varchar(64) NOT NULL,
  PRIMARY KEY (`event_title`,`event_date`,`club_name`),
  KEY `club_name` (`club_name`),
  CONSTRAINT `event_ibfk_1` FOREIGN KEY (`club_name`) REFERENCES `club` (`club_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES ('Boiler Room','Rave music with special guest','2025-11-05','Live Music NEU'),('Cheese making lessons','Learn to make moldy cheese','2025-08-05','Cheese Club'),('Cheese Tasting','Taste different kinds of cheeses','2025-09-05','Cheese Club'),('Cooking Lesson','Past special lesson with guests','2025-04-13','Cooking Club'),('Guest Celeebrity Chef','Gordon Ramsay comes to teach the basics','2025-10-22','Cooking Club'),('Hackathon','Coding competition for developers','2024-01-15','Generate'),('Live Concert','Live music with a full band','2025-10-15','Live Music NEU'),('Prototyping Workshop','Leran the basics of prototyping','2026-03-05','NER'),('Tech Talk','An informative event about emerging technologies.','2024-12-15','Generate'),('Watch Party for NASCAR','Join friends to watch live events together','2026-05-05','NER');
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_attends_event`
--

DROP TABLE IF EXISTS `member_attends_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_attends_event` (
  `event_title` varchar(64) NOT NULL,
  `event_date` date NOT NULL,
  `club_name` varchar(64) NOT NULL,
  `member_id` int NOT NULL,
  PRIMARY KEY (`event_title`,`event_date`,`club_name`,`member_id`),
  KEY `member_id` (`member_id`),
  CONSTRAINT `member_attends_event_ibfk_1` FOREIGN KEY (`event_title`, `event_date`, `club_name`) REFERENCES `event` (`event_title`, `event_date`, `club_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `member_attends_event_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `member_of_club` (`member_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_attends_event`
--

LOCK TABLES `member_attends_event` WRITE;
/*!40000 ALTER TABLE `member_attends_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `member_attends_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_of_club`
--

DROP TABLE IF EXISTS `member_of_club`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_of_club` (
  `member_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(64) NOT NULL,
  `last_name` varchar(64) NOT NULL,
  `join_date` date NOT NULL,
  `club_name` varchar(64) NOT NULL,
  `role_name` varchar(64) NOT NULL,
  PRIMARY KEY (`member_id`),
  KEY `club_name` (`club_name`),
  KEY `role_name` (`role_name`),
  CONSTRAINT `member_of_club_ibfk_1` FOREIGN KEY (`club_name`) REFERENCES `club` (`club_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `member_of_club_ibfk_2` FOREIGN KEY (`role_name`) REFERENCES `role` (`role_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_of_club`
--

LOCK TABLES `member_of_club` WRITE;
/*!40000 ALTER TABLE `member_of_club` DISABLE KEYS */;
INSERT INTO `member_of_club` VALUES (1,'Bob','Smith','2023-08-10','NER','President'),(2,'Sally','Parker','2024-08-21','NER','Member'),(3,'Bertha','Dillon','2022-09-01','Cooking Club','President'),(4,'Jill','Brown','2023-08-10','NER','Member'),(5,'Robby','Lloyd','2024-08-21','NER','Member'),(6,'Jackson','Dumont','2022-09-01','NER','Member'),(7,'Gracie','Abrams','2024-09-11','Live Music NEU','President'),(8,'Phoebe','Bridgers','2023-09-02','Live Music NEU','Treasurer'),(9,'Grent','Perez','2024-09-02','Live Music NEU','Member'),(10,'Noah','Kahn','2024-09-02','Live Music NEU','Vice President'),(11,'Gordon','Ramsay','2024-11-02','Cooking Club','Vice President'),(12,'Baking With','Babish','2024-08-02','Cooking Club','President'),(13,'Ellen','Degeneres','2024-11-02','Cooking Club','Treasurer'),(14,'Tyler','Kim','2024-11-02','Cooking Club','Member'),(15,'Kiki','Liu','2025-11-02','Cheese Club','President'),(16,'Eleanor','Collins','2023-07-04','Cheese Club','Vice President'),(17,'Nick','Chen','2026-07-05','Cheese Club','Treasurer'),(18,'Owen','Zacherou','2026-07-05','Generate','President'),(19,'Birg','Gloobal','2026-07-05','Generate','Member'),(20,'Vesely','Ferdinand','2026-07-05','Generate','Treasurer'),(21,'Audrey','Thorbal','2026-07-05','Generate','Vice President');
/*!40000 ALTER TABLE `member_of_club` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase` (
  `purchase_id` int NOT NULL AUTO_INCREMENT,
  `purchase_title` varchar(64) NOT NULL,
  `cost` decimal(10,2) NOT NULL,
  `club_name` varchar(64) NOT NULL,
  PRIMARY KEY (`purchase_id`),
  KEY `club_name` (`club_name`),
  CONSTRAINT `purchase_ibfk_1` FOREIGN KEY (`club_name`) REFERENCES `club` (`club_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase`
--

LOCK TABLES `purchase` WRITE;
/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
INSERT INTO `purchase` VALUES (1,'Food',20.50,'NER'),(2,'Car Parts',300.20,'NER'),(3,'Gas',30.40,'NER'),(4,'Pans',26.40,'Cooking Club'),(5,'Oil',31.40,'Cooking Club'),(6,'Grease',20.20,'Cooking Club'),(7,'Salt',3.14,'Cooking Club'),(8,'Brie',12.14,'Cheese Club'),(9,'Briar',15.14,'Cheese Club'),(10,'Mozerella',8.14,'Cheese Club'),(11,'Server Costs',4.14,'Generate'),(12,'Pizza',1.14,'Generate'),(13,'Guest Speakers',2.14,'Generate'),(14,'Hardware',3.30,'Generate'),(15,'Guitars',400.30,'Live Music NEU'),(16,'Piano',300.30,'Live Music NEU'),(17,'Drums',30.30,'Live Music NEU'),(18,'Pots',31.50,'Cooking Club');
/*!40000 ALTER TABLE `purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role_name` varchar(64) NOT NULL,
  PRIMARY KEY (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('Member'),('President'),('Secretary'),('Treasurer'),('Vice President');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `social_media`
--

DROP TABLE IF EXISTS `social_media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `social_media` (
  `platform` varchar(64) NOT NULL,
  `username` varchar(64) NOT NULL,
  `club_name` varchar(64) NOT NULL,
  PRIMARY KEY (`platform`,`username`),
  KEY `club_name` (`club_name`),
  CONSTRAINT `social_media_ibfk_1` FOREIGN KEY (`club_name`) REFERENCES `club` (`club_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `social_media`
--

LOCK TABLES `social_media` WRITE;
/*!40000 ALTER TABLE `social_media` DISABLE KEYS */;
INSERT INTO `social_media` VALUES ('LinkedIn','Eco-Warriors','Cheese Club'),('Twitter','EcoWarriors01','Cheese Club'),('Instagram','cookinsta','Cooking Club'),('Twitter','cooktweets','Cooking Club'),('Facebook','CodingClubFB','Generate'),('Instagram','coding.fun','Generate'),('Twitter','CodingClub123','Generate'),('Instagram','musicinsta','Live Music NEU'),('Twitter','musictweets','Live Music NEU');
/*!40000 ALTER TABLE `social_media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'club_wise'
--
/*!50003 DROP PROCEDURE IF EXISTS `add_event` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_event`(
    IN p_event_title VARCHAR(64),
    IN p_event_description VARCHAR(500),
    IN p_event_date DATE,
    IN p_club_name VARCHAR(64)
)
BEGIN
    IF EXISTS (
        SELECT 1
        FROM club
        WHERE club_name = p_club_name
    ) THEN
        INSERT INTO event (event_title, event_description, event_date, club_name)
        VALUES (p_event_title, p_event_description, p_event_date, p_club_name);
ELSE
SELECT CONCAT('Club "', p_club_name, '" does not exist.') AS ErrorMessage;
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `add_event_to_club` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_event_to_club`(
    IN p_event_title VARCHAR(64),
    IN p_event_description VARCHAR(500),
    IN p_event_date DATE,
    IN p_club_name VARCHAR(64)
)
BEGIN
    DECLARE v_club_exists INT;
    DECLARE v_event_exists INT;
    DECLARE v_message VARCHAR(255);

    -- Check if the club exists
SELECT COUNT(*) INTO v_club_exists
FROM club
WHERE club_name = p_club_name;

-- Check if the event already exists for the given club and date
SELECT COUNT(*) INTO v_event_exists
FROM event
WHERE event_title = p_event_title
  AND event_date = p_event_date
  AND club_name = p_club_name;

-- If the club exists and event doesn't exist, insert the event
IF v_club_exists > 0 AND v_event_exists = 0 THEN
        INSERT INTO event (event_title, event_description, event_date, club_name)
        VALUES (p_event_title, p_event_description, p_event_date, p_club_name);
        SET v_message = 'Event added successfully.';
    ELSEIF v_club_exists = 0 THEN
        SET v_message = 'Club does not exist.';
ELSE
        SET v_message = 'Event already exists for this club on the given date.';
END IF;

    -- Output the message
SELECT v_message AS ResultMessage;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `add_member_to_club` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_member_to_club`(
    IN p_first_name VARCHAR(64),
    IN p_last_name VARCHAR(64),
    IN p_join_date DATE,
    IN p_club_name VARCHAR(64),
    IN p_role_name VARCHAR(64)
)
BEGIN
    DECLARE v_club_exists INT;
    DECLARE v_role_exists INT;

    -- Check if the club exists
SELECT COUNT(*) INTO v_club_exists
FROM club
WHERE club_name = p_club_name;

-- Check if the role exists
SELECT COUNT(*) INTO v_role_exists
FROM role
WHERE role_name = p_role_name;

-- If the club exists
IF v_club_exists > 0 THEN
        -- If the role doesn't exist, insert it into the role table
        IF v_role_exists = 0 THEN
            INSERT INTO role (role_name)
            VALUES (p_role_name);
END IF;
INSERT INTO member_of_club (first_name, last_name, join_date, club_name, role_name)
VALUES (p_first_name, p_last_name, p_join_date, p_club_name, p_role_name);
ELSE
SELECT 'Failed to add member: Club does not exist.' AS ErrorMessage;
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `create_club` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_club`(
    name_p VARCHAR(64),
    description_p VARCHAR(500),
    active_p BOOL,
    category_p enum("STEM",
    "Arts and Culture",
    "Sports and Recreation",
    "Community Service and Social Impact",
    "Social and Special Interests",
    "Media and Communications",
    "Religious and Spiritual") )
BEGIN
INSERT INTO club ( club_name, club_description, active, category )
VALUES ( name_p, description_p, active_p, category_p );
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `create_purchase` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_purchase`(
IN p_title VARCHAR(64),
p_cost DECIMAL,
p_club_name VARCHAR(64)
)
BEGIN
INSERT INTO purchase ( purchase_title, cost, club_name)
VALUES (p_title, p_cost, p_club_name);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `create_social` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_social`(
IN p_platform VARCHAR(64),
p_username VARCHAR(64),
p_club_name VARCHAR(64)
)
BEGIN
INSERT INTO social_media(platform, username, club_name)
VALUES (p_platform, p_username, p_club_name);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_events_from_club` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_events_from_club`(
    IN p_club_name VARCHAR(64),
    IN p_event_title VARCHAR(64),
    IN p_event_date DATE
)
BEGIN
    DECLARE v_club_exists INT;

    -- Check if the club exists
SELECT COUNT(*) INTO v_club_exists
FROM club
WHERE club_name = p_club_name;

-- If the club exists
IF v_club_exists > 0 THEN
        -- If both event title and event date are provided, delete the specific event
DELETE FROM event
WHERE club_name = p_club_name
  AND event_title = p_event_title
  AND event_date = p_event_date;

SELECT 'Event(s) deleted successfully.' AS ResultMessage;

ELSE
        -- If the club does not exist, return an error message
SELECT 'Failed to delete events: Club does not exist.' AS ErrorMessage;
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_purchase` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_purchase`(
IN p_purchase_id INT
)
BEGIN
DELETE FROM purchase WHERE purchase_id = p_purchase_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_social` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_social`(
IN p_platform VARCHAR(64),
p_username VARCHAR(64)
)
BEGIN
DELETE FROM social_media WHERE platform = p_platform AND username = p_username;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `edit_event` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `edit_event`(
    IN p_event_title VARCHAR(64),
    IN p_event_date DATE,
    IN p_club_name VARCHAR(64),
    IN p_new_event_description VARCHAR(500)
)
BEGIN
    IF EXISTS (
        SELECT 1
        FROM event
        WHERE event_title = p_event_title
          AND event_date = p_event_date
          AND club_name = p_club_name
    ) THEN
UPDATE event
SET
    event_description = p_new_event_description
WHERE event_title = p_event_title
  AND event_date = p_event_date
  AND club_name = p_club_name;

ELSE
SELECT CONCAT('Event "', p_event_title, '" on "', p_event_date, '" for club "', p_club_name, '" does not exist.') AS ErrorMessage;
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `edit_member_info` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `edit_member_info`(
    IN p_member_id INT,
    IN p_first_name VARCHAR(64),
    IN p_last_name VARCHAR(64),
    IN p_join_date DATE
)
BEGIN
    DECLARE v_member_exists INT;

SELECT COUNT(*) INTO v_member_exists
FROM member_of_club
WHERE member_id = p_member_id;

IF v_member_exists > 0 THEN
UPDATE member_of_club
SET first_name = p_first_name,
    last_name = p_last_name,
    join_date = p_join_date
WHERE member_id = p_member_id;

SELECT CONCAT('Member with ID ', p_member_id, ' has been updated successfully.') AS Message;
ELSE
SELECT CONCAT('Member with ID ', p_member_id, ' does not exist in the database.') AS ErrorMessage;
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_clubs_by_active_status` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_clubs_by_active_status`( active_p BOOL )
BEGIN
SELECT * FROM club WHERE active = active_p;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_clubs_by_category` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_clubs_by_category`(
    IN category_p enum("STEM",
    "Arts and Culture",
    "Sports and Recreation",
    "Community Service and Social Impact",
    "Social and Special Interests",
    "Media and Communications",
    "Religious and Spiritual") )
BEGIN
SELECT * FROM club WHERE category = category_p;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_events_by_club` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_events_by_club`(
    IN p_club_name VARCHAR(64)
)
BEGIN
SELECT
    event_title,
    event_description,
    event_date
FROM
    event
WHERE
    club_name = p_club_name
ORDER BY
    event_date ASC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_members_by_club_name` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_members_by_club_name`( club_name_p VARCHAR(64) )
BEGIN
SELECT * FROM member_of_club WHERE club_name = club_name_p;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_number_of_members` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_number_of_members`(
    IN p_club_name VARCHAR(64)
)
BEGIN
SELECT COUNT(*) AS NumberOfMembers
FROM member_of_club
WHERE club_name = p_club_name;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_purchases_by_club` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_purchases_by_club`(
    IN p_club_name VARCHAR(64)
)
BEGIN
    -- Check if the club exists
    IF EXISTS (SELECT 1 FROM club WHERE club_name = p_club_name) THEN
        -- Retrieve social media details for the specified club
SELECT *
FROM purchase
WHERE club_name = p_club_name;
ELSE

SELECT CONCAT('Club with name "', p_club_name, '" does not exist.') AS ErrorMessage;
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_socials_by_club` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_socials_by_club`(
    IN p_club_name VARCHAR(64)
)
BEGIN
    -- Check if the club exists
    IF EXISTS (SELECT 1 FROM club WHERE club_name = p_club_name) THEN
        -- Retrieve social media details for the specified club
SELECT platform, username
FROM social_media
WHERE club_name = p_club_name;
ELSE
        -- If the club does not exist, return an error message
SELECT CONCAT('Club with name "', p_club_name, '" does not exist.') AS ErrorMessage;
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `remove_event` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `remove_event`(
    IN p_event_title VARCHAR(64),
    IN p_event_date DATE,
    IN p_club_name VARCHAR(64)
)
BEGIN
    IF EXISTS (
        SELECT 1
        FROM event
        WHERE event_title = p_event_title
          AND event_date = p_event_date
          AND club_name = p_club_name
    ) THEN
DELETE FROM event
WHERE event_title = p_event_title
  AND event_date = p_event_date
  AND club_name = p_club_name;
ELSE
SELECT CONCAT('Event "', p_event_title, '" on "', p_event_date, '" for club "', p_club_name, '" does not exist.') AS ErrorMessage;
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `remove_member_from_club` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `remove_member_from_club`(
    IN p_member_id INT
)
BEGIN
    IF EXISTS (SELECT 1 FROM member_of_club WHERE member_id = p_member_id) THEN
DELETE FROM member_of_club WHERE member_id = p_member_id;
SELECT CONCAT('Member with ID ', p_member_id, ' has been removed successfully.') AS Message;

ELSE
SELECT CONCAT('Member with ID ', p_member_id, ' does not exist in the database.') AS ErrorMessage;
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_club` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_club`(
    IN p_club_name VARCHAR(64),
    IN p_new_club_description VARCHAR(500),
    IN p_new_active_status BOOL,
    IN p_new_category ENUM("STEM",
    "Arts and Culture",
    "Sports and Recreation",
    "Community Service and Social Impact",
    "Social and Special Interests",
    "Media and Communications",
    "Religious and Spiritual")
)
BEGIN
    -- Check if the club exists
    IF EXISTS (
        SELECT 1
        FROM club
        WHERE club_name = p_club_name
    ) THEN
        -- Update the club details
UPDATE club
SET
    club_description = p_new_club_description,
    active = p_new_active_status,
    category = p_new_category
WHERE club_name = p_club_name;

-- Return success message
SELECT CONCAT('Club "', p_club_name, '" has been updated successfully.') AS Message;
ELSE
        -- Return failure message if the club does not exist
SELECT CONCAT('Club "', p_club_name, '" does not exist.') AS ErrorMessage;
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-09  1:29:02
