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
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Client`
--

LOCK TABLES `Client` WRITE;
/*!40000 ALTER TABLE `Client` DISABLE KEYS */;
INSERT INTO `Client` VALUES (1,'Microsoft','uuuhdhd@aol.com'),(2,'Bella','boooh@gmail.com'),(3,'YOKI','yoki@ufhf.com'),(5,'CiccioPasticcio','ciccio@pasticcio.it'),(6,'Google','google@gmail.com'),(7,'yahoooooo','fhhfhf@yahoo.com'),(8,'Bing','bingo@bing.com'),(9,'Hobs',NULL),(10,'Chdhdh',NULL),(11,'Amd',NULL),(12,'ProvaStg',NULL),(13,'Yoyoyo','yoyoyoy@hotmail.com'),(14,'yuuuuu','rureyr@uuu.com'),(15,'uuu','uuu@love.it'),(16,'wert','er@fhjfhf.com'),(17,'wertyu','erty@tityu.com'),(18,'wertyui','werty@gomail.com'),(19,'qwerty','qwerty@qwert.com'),(20,'wertyu','erty@tityu.com'),(21,'zxc','zxc@gomail.com'),(22,'zxc','zxc@gomail.com'),(23,'dsfhj','asdfg@hjjdd.coim'),(24,'dsfhj','asdfg@hjjdd.coim'),(25,'dsfhj','asdfg@hjjdd.coim'),(26,'asdfg','asdfg@hotutt.com'),(27,'asdfg','asdfg@hotutt.com'),(28,'asdfg','asdfg@kdgfghd.com'),(29,'asd','asd@ghjf.com'),(30,'asd','asd@ghjf.com'),(31,'adhjdh','df@ohg.com'),(32,'asdfgh','asdfg'),(33,'asdfgh','asdfg'),(34,'asdfgh','asdfg'),(35,'asdfgh','asdfg'),(36,'a','a'),(37,'b','b'),(38,'aaaaaa','aaaa@gmail.com'),(39,'hshshs','hsaahajja@gmail.com'),(40,'cccc','cccc'),(41,'Clienteee','afdgf@gohg.com'),(42,'kjdhuvf','dgfgfkig@gdehyhyuf.com'),(43,'shjjfd','ddfdifdijfd@gmail.com'),(44,'porova','offju@gmail.com'),(45,'dfkhu','ddhufd@gohshgd.com'),(46,'asdfghj','asdfghj@rytryry.com'),(47,'ksjfh','jdjdjdjd@gjghg.com'),(48,'jdfhghj','jsdfgjf@hhfhfhf.com'),(49,'qjwhesdfh','hdhffh@fhfhf.com');
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
  `start` date DEFAULT NULL,
  PRIMARY KEY (`idProject`),
  KEY `UserProject_idx` (`idProjectManager`),
  KEY `ClientProject_idx` (`idClient`),
  CONSTRAINT `ClientProject` FOREIGN KEY (`idClient`) REFERENCES `Client` (`idClient`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ProjectManagerProject` FOREIGN KEY (`idProjectManager`) REFERENCES `User` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Project`
--

LOCK TABLES `Project` WRITE;
/*!40000 ALTER TABLE `Project` DISABLE KEYS */;
INSERT INTO `Project` VALUES (36,'ccc',123445,1,40,'ccc','cccc','boooh',123,'2015-08-09',12,NULL),(37,'aaaa',123456,1,43,'aaaa','aaaaaaaaaaaa','tech',123,'2012-03-26',12,'2012-03-25'),(38,'provasuper',123456,1,44,'dfs','fvfjf','tech',12,'2016-02-03',23,'2016-02-03'),(39,'diooooo',12345678,1,45,'hfdhdhjdj','djdf','tech',12345,'2016-02-03',12,'2016-02-03'),(40,'ProvaStage333',12345,1,46,'asdfgth','adsfgh','tech',12,'2016-02-03',21,'2016-02-03'),(41,'sahyudhysfd',1234567,1,47,'jds','jfdjfkjfd','tech',12345,'2016-07-06',12,'2016-07-06'),(42,'Provvvv',123456,1,48,'dhhdhdh','fdhhdjdhdh','tech',12345,'2016-07-09',12,'2016-07-09'),(43,'sjjsjsjsjs',123456,1,49,'jszjsjsjsj','sjsjjsjs','tech',1234,'2015-06-08',12,'2015-06-08');
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
  `estimatedDuration` int(11) DEFAULT NULL,
  `startDay` date DEFAULT NULL,
  `finishDay` date DEFAULT NULL,
  `idSupervisor` int(11) DEFAULT NULL,
  `idProject` int(11) NOT NULL,
  `goals` longtext,
  `requirements` longtext,
  PRIMARY KEY (`idStage`),
  KEY `fk_Stage_Project_idx` (`idProject`),
  KEY `SupervisorStage_idx` (`idSupervisor`),
  CONSTRAINT `ProjectStage` FOREIGN KEY (`idProject`) REFERENCES `Project` (`idProject`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `SupervisorStage` FOREIGN KEY (`idSupervisor`) REFERENCES `User` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Stage`
--

LOCK TABLES `Stage` WRITE;
/*!40000 ALTER TABLE `Stage` DISABLE KEYS */;
INSERT INTO `Stage` VALUES (3,'Stage1',12,'2016-02-03','2016-02-03',NULL,38,'speriamo dio cane','dioooo'),(4,'Stage2222',4,'2016-02-03','2016-02-03',1,39,'dhdhdhdh','dhhdhdhd'),(5,'stage2',12,'2016-02-03','2016-02-03',NULL,40,'asdfrg','asdfg'),(6,'Stageeeee',12,'2016-07-06','2016-07-06',NULL,41,'asdfghjk','asdfghj'),(7,'stage5555',12,'2016-07-06','2016-07-06',NULL,41,'dhj','hdhj'),(8,'Stograncazzo',12,'2016-07-09','2016-07-09',2,42,'jshshshs','hdhdhhdh'),(9,'stage44444',2,'2016-07-09','2016-07-09',4,42,'dhjyufds','dcdhfdjdd'),(10,'sjss',123,'2016-07-09','2016-07-09',4,42,'hdhdhdhdh','hjzhshhs'),(11,'hshdhdh',12,'2015-06-08','2015-06-08',NULL,43,'dshdhhdhd','dshdshdhhd'),(12,'fhhfhf',12,'2015-06-08','2015-06-08',NULL,43,'hdhdhdhd','dhdhhdhd'),(13,'cnbfchdhd',12,'2015-06-08','2015-06-08',NULL,43,'hdshdhd','shhshs'),(14,'dhdhdh',12,'2015-06-08','2015-06-08',6,43,'dshdhhd','dhdhhd'),(15,'dhdhhd',1,'2015-06-08','2015-06-08',7,43,'dhhdhdhd','qdhjdd');
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
  CONSTRAINT `StageTask` FOREIGN KEY (`idStage`) REFERENCES `Stage` (`idStage`) ON DELETE CASCADE ON UPDATE CASCADE
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
  CONSTRAINT `DeveloperTask` FOREIGN KEY (`idDeveloper`) REFERENCES `User` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `TaskDevelop` FOREIGN KEY (`idTask`) REFERENCES `Task` (`idTask`) ON DELETE CASCADE ON UPDATE CASCADE
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'user1','38e7f59446743beac67b699738ceabf74f3ec9169d2e31566f77abe56ee876ef','Alessandro Tundo',NULL,'ProjectManager','-3408564752822225002',NULL),(2,'user2','1e57bd7a43baa78a4f0df0c76608284d77a626aba83def6f3353f325ec891a80','Ale Vazzola',NULL,'Senior','4403057260337259784',NULL),(3,'user3','464f5cc5f855863571574aeb03d2906eeb22ee288d29a0cc3c0b1443cfd4c2f9','Ilaria Pigazzini',NULL,'Junior','-6296899952466904893',NULL),(4,'user4','5d5e6eebf03795c3f42a6de67a743484bc89376709790bd3c72413057b18fa10','Victor Popescu',NULL,'Senior','-589528451402326433',NULL),(5,'albertonava','8624ae2c1997705f5b5e685c18519738a46fee470edfbbb0fc6be34310ff3b0e','Alberto Nava',NULL,'Junior','1812160898009983697',NULL),(6,'user5','8624ae2c1997705f5b5e685c18519738a46fee470edfbbb0fc6be34310ff3b0e','Prova','Provino','Senior','1812160898009983697',NULL),(7,'user6','8624ae2c1997705f5b5e685c18519738a46fee470edfbbb0fc6be34310ff3b0e','Prosss','Prororo','ProjectManager','1812160898009983697',NULL);
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

-- Dump completed on 2016-02-19  9:42:02
