CREATE DATABASE  IF NOT EXISTS `projectx` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `projectx`;
-- MySQL dump 10.13  Distrib 5.5.46, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: projectx
-- ------------------------------------------------------
-- Server version	5.5.46-0+deb8u1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Client`
--

DROP TABLE IF EXISTS `Client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Client` (
  `idClient` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `mail` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idClient`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Client`
--

LOCK TABLES `Client` WRITE;
/*!40000 ALTER TABLE `Client` DISABLE KEYS */;
INSERT INTO `Client` VALUES (1,'Microsoft','uuuhdhd@aol.com'),(2,'Bella','boooh@gmail.com'),(3,'YOKI','yoki@ufhf.com'),(5,'CiccioPasticcio','ciccio@pasticcio.it'),(6,'Google','google@gmail.com'),(7,'yahoooooo','fhhfhf@yahoo.com'),(8,'Bing','bingo@bing.com'),(9,'Hobs',NULL);
/*!40000 ALTER TABLE `Client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Project`
--

DROP TABLE IF EXISTS `Project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Project` (
  `idProject` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `budget` double DEFAULT NULL,
  `idProjectManager` int(11) NOT NULL,
  `idClient` int(11) NOT NULL,
  `goals` longtext,
  `requirements` longtext,
  `subjectAreas` varchar(300) DEFAULT NULL,
  `estimatedCosts` double DEFAULT NULL,
  `deadline` date DEFAULT NULL,
  `estimatedDuration` int(11) DEFAULT NULL,
  PRIMARY KEY (`idProject`),
  KEY `UserProject_idx` (`idProjectManager`),
  KEY `ClientProject_idx` (`idClient`),
  CONSTRAINT `ClientProject` FOREIGN KEY (`idClient`) REFERENCES `Client` (`idClient`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ProjectManagerProject` FOREIGN KEY (`idProjectManager`) REFERENCES `User` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Project`
--

LOCK TABLES `Project` WRITE;
/*!40000 ALTER TABLE `Project` DISABLE KEYS */;
INSERT INTO `Project` VALUES (1,'Profhf',293847,1,1,NULL,NULL,NULL,NULL,NULL,NULL),(2,'Prova',3234,1,2,NULL,NULL,NULL,NULL,NULL,NULL),(3,'Prjhy',2345678,2,3,NULL,NULL,NULL,NULL,NULL,NULL),(4,'BelloDeMamma',8478474,1,8,'Eh chehfhcjdhje, chhje3djefje, dhfdehjfheiuhciud, ....fjd','fhduhufvdhuh, dfuvf , Bellele','Cdoiodoid, djudhd, hdhdh',24975,'2016-03-12',20),(5,'Prova form',2333,1,9,'hdshdhd','dhhdhd','Nature',223,'2016-12-31',45);
/*!40000 ALTER TABLE `Project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Stage`
--

DROP TABLE IF EXISTS `Stage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Stage` (
  `idStage` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `startDay` date DEFAULT NULL,
  `finishDay` date DEFAULT NULL,
  `idSupervisor` int(11) NOT NULL,
  `idProject` int(11) NOT NULL,
  `goals` longtext,
  `requirements` longtext,
  PRIMARY KEY (`idStage`),
  KEY `fk_Stage_Project_idx` (`idProject`),
  KEY `SupervisorStage_idx` (`idSupervisor`),
  CONSTRAINT `ProjectStage` FOREIGN KEY (`idProject`) REFERENCES `Project` (`idProject`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `SupervisorStage` FOREIGN KEY (`idSupervisor`) REFERENCES `User` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Stage`
--

LOCK TABLES `Stage` WRITE;
/*!40000 ALTER TABLE `Stage` DISABLE KEYS */;
INSERT INTO `Stage` VALUES (1,'Stage1',8,NULL,NULL,4,1,NULL,NULL),(2,'jgjg',7,NULL,NULL,1,3,NULL,NULL);
/*!40000 ALTER TABLE `Stage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Task`
--

DROP TABLE IF EXISTS `Task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Task` (
  `idTask` int(11) NOT NULL AUTO_INCREMENT,
  `startDay` date DEFAULT NULL,
  `finishDay` date DEFAULT NULL,
  `idStage` int(11) NOT NULL,
  `completed` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idTask`),
  KEY `fk_Task_Stage1_idx` (`idStage`),
  CONSTRAINT `StageTask` FOREIGN KEY (`idStage`) REFERENCES `Stage` (`idStage`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Task`
--

LOCK TABLES `Task` WRITE;
/*!40000 ALTER TABLE `Task` DISABLE KEYS */;
/*!40000 ALTER TABLE `Task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TaskDevelopment`
--

DROP TABLE IF EXISTS `TaskDevelopment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TaskDevelopment` (
  `idDeveloper` int(11) NOT NULL,
  `idTask` int(11) NOT NULL,
  `workCompleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idDeveloper`,`idTask`),
  KEY `TaskDeveloped_idx` (`idTask`),
  CONSTRAINT `DeveloperTask` FOREIGN KEY (`idDeveloper`) REFERENCES `User` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `TaskDevelop` FOREIGN KEY (`idTask`) REFERENCES `Task` (`idTask`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TaskDevelopment`
--

LOCK TABLES `TaskDevelopment` WRITE;
/*!40000 ALTER TABLE `TaskDevelopment` DISABLE KEYS */;
/*!40000 ALTER TABLE `TaskDevelopment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `hashpw` varchar(64) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `type` enum('ProjectManager','Senior','Junior') NOT NULL,
  `salt` varchar(64) NOT NULL,
  `skills` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'user1','38e7f59446743beac67b699738ceabf74f3ec9169d2e31566f77abe56ee876ef','Alessandro Tundo',NULL,'ProjectManager','-3408564752822225002',NULL),(2,'user2','1e57bd7a43baa78a4f0df0c76608284d77a626aba83def6f3353f325ec891a80','Ale Vazzola',NULL,'Senior','4403057260337259784',NULL),(3,'user3','464f5cc5f855863571574aeb03d2906eeb22ee288d29a0cc3c0b1443cfd4c2f9','Ilaria Pigazzini',NULL,'Junior','-6296899952466904893',NULL),(4,'user4','5d5e6eebf03795c3f42a6de67a743484bc89376709790bd3c72413057b18fa10','Victor Popescu',NULL,'Senior','-589528451402326433',NULL);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-16 23:47:15
