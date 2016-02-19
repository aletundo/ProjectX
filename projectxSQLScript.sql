-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: projectx
-- ------------------------------------------------------
-- Server version	5.7.9

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
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `idClient` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `mail` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idClient`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'Microsoft','uuuhdhd@aol.com'),(2,'Bella','boooh@gmail.com'),(3,'YOKI','yoki@ufhf.com'),(5,'CiccioPasticcio','ciccio@pasticcio.it'),(6,'Google','google@gmail.com'),(7,'yahoooooo','fhhfhf@yahoo.com'),(8,'Bing','bingo@bing.com'),(9,'Hobs',NULL),(10,'Chdhdh',NULL),(11,'Amd',NULL),(12,'ProvaStg',NULL),(13,'Yoyoyo','yoyoyoy@hotmail.com'),(14,'yuuuuu','rureyr@uuu.com'),(15,'uuu','uuu@love.it'),(16,'wert','er@fhjfhf.com'),(17,'wertyu','erty@tityu.com'),(18,'wertyui','werty@gomail.com'),(19,'qwerty','qwerty@qwert.com'),(20,'wertyu','erty@tityu.com'),(21,'zxc','zxc@gomail.com'),(22,'zxc','zxc@gomail.com'),(23,'dsfhj','asdfg@hjjdd.coim'),(24,'dsfhj','asdfg@hjjdd.coim'),(25,'dsfhj','asdfg@hjjdd.coim'),(26,'asdfg','asdfg@hotutt.com'),(27,'asdfg','asdfg@hotutt.com'),(28,'asdfg','asdfg@kdgfghd.com'),(29,'asd','asd@ghjf.com'),(30,'asd','asd@ghjf.com'),(31,'adhjdh','df@ohg.com'),(32,'asdfgh','asdfg'),(33,'asdfgh','asdfg'),(34,'asdfgh','asdfg'),(35,'asdfgh','asdfg'),(36,'a','a'),(37,'b','b'),(38,'aaaaaa','aaaa@gmail.com'),(39,'hshshs','hsaahajja@gmail.com'),(40,'cccc','cccc'),(41,'Clienteee','afdgf@gohg.com'),(42,'kjdhuvf','dgfgfkig@gdehyhyuf.com'),(43,'shjjfd','ddfdifdijfd@gmail.com'),(44,'porova','offju@gmail.com'),(45,'dfkhu','ddhufd@gohshgd.com'),(46,'asdfghj','asdfghj@rytryry.com'),(47,'ksjfh','jdjdjdjd@gjghg.com'),(48,'jdfhghj','jsdfgjf@hhfhfhf.com'),(49,'qjwhesdfh','hdhffh@fhfhf.com'),(50,'fsÃ²oidhÃ²o','kdagho@doohf.com');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
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
  CONSTRAINT `ClientProject` FOREIGN KEY (`idClient`) REFERENCES `client` (`idClient`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ProjectManagerProject` FOREIGN KEY (`idProjectManager`) REFERENCES `user` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (36,'ccc',123445,1,40,'ccc','cccc','boooh',123,'2015-08-09',12,NULL),(37,'aaaa',123456,1,43,'aaaa','aaaaaaaaaaaa','tech',123,'2012-03-26',12,'2012-03-25'),(38,'provasuper',123456,1,44,'dfs','fvfjf','tech',12,'2016-02-03',23,'2016-02-03'),(39,'diooooo',12345678,1,45,'hfdhdhjdj','djdf','tech',12345,'2016-02-03',12,'2016-02-03'),(40,'ProvaStage333',12345,1,46,'asdfgth','adsfgh','tech',12,'2016-02-03',21,'2016-02-03'),(41,'sahyudhysfd',1234567,1,47,'jds','jfdjfkjfd','tech',12345,'2016-07-06',12,'2016-07-06'),(42,'Provvvv',123456,1,48,'dhhdhdh','fdhhdjdhdh','tech',12345,'2016-07-09',12,'2016-07-09'),(43,'sjjsjsjsjs',123456,1,49,'jszjsjsjsj','sjsjjsjs','tech',1234,'2015-06-08',12,'2015-06-08'),(44,'provacorrezioneila',1324,1,50,'afoiio','Ã²fkdslhÃ li','nature',123,'2016-02-02',20,'2016-02-02');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stage`
--

DROP TABLE IF EXISTS `stage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stage` (
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
  CONSTRAINT `ProjectStage` FOREIGN KEY (`idProject`) REFERENCES `project` (`idProject`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `SupervisorStage` FOREIGN KEY (`idSupervisor`) REFERENCES `user` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stage`
--

LOCK TABLES `stage` WRITE;
/*!40000 ALTER TABLE `stage` DISABLE KEYS */;
INSERT INTO `stage` VALUES (3,'Stage1',12,'2016-02-03','2016-02-03',NULL,38,'speriamo dio cane','dioooo'),(4,'Stage2222',4,'2016-02-03','2016-02-03',1,39,'dhdhdhdh','dhhdhdhd'),(5,'stage2',12,'2016-02-03','2016-02-03',NULL,40,'asdfrg','asdfg'),(6,'Stageeeee',12,'2016-07-06','2016-07-06',NULL,41,'asdfghjk','asdfghj'),(7,'stage5555',12,'2016-07-06','2016-07-06',NULL,41,'dhj','hdhj'),(8,'Stograncazzo',12,'2016-07-09','2016-07-09',2,42,'jshshshs','hdhdhhdh'),(9,'stage44444',2,'2016-07-09','2016-07-09',4,42,'dhjyufds','dcdhfdjdd'),(10,'sjss',123,'2016-07-09','2016-07-09',4,42,'hdhdhdhdh','hjzhshhs'),(11,'hshdhdh',12,'2015-06-08','2015-06-08',NULL,43,'dshdhhdhd','dshdshdhhd'),(12,'fhhfhf',12,'2015-06-08','2015-06-08',NULL,43,'hdhdhdhd','dhdhhdhd'),(13,'cnbfchdhd',12,'2015-06-08','2015-06-08',NULL,43,'hdshdhd','shhshs'),(14,'dhdhdh',12,'2015-06-08','2015-06-08',6,43,'dshdhhd','dhdhhd'),(15,'dhdhhd',1,'2015-06-08','2015-06-08',7,43,'dhhdhdhd','qdhjdd'),(16,'staandandobene',12,'2016-02-02','2016-02-02',NULL,44,'Ã²ofid','haÃ²oidgho');
/*!40000 ALTER TABLE `stage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `idTask` int(11) NOT NULL AUTO_INCREMENT,
  `startDay` date DEFAULT NULL,
  `finishDay` date DEFAULT NULL,
  `idStage` int(11) NOT NULL,
  `completed` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idTask`),
  KEY `fk_Task_Stage1_idx` (`idStage`),
  CONSTRAINT `StageTask` FOREIGN KEY (`idStage`) REFERENCES `stage` (`idStage`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taskdevelopment`
--

DROP TABLE IF EXISTS `taskdevelopment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taskdevelopment` (
  `idDeveloper` int(11) NOT NULL,
  `idTask` int(11) NOT NULL,
  `workCompleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idDeveloper`,`idTask`),
  KEY `TaskDeveloped_idx` (`idTask`),
  CONSTRAINT `DeveloperTask` FOREIGN KEY (`idDeveloper`) REFERENCES `user` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `TaskDevelop` FOREIGN KEY (`idTask`) REFERENCES `task` (`idTask`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taskdevelopment`
--

LOCK TABLES `taskdevelopment` WRITE;
/*!40000 ALTER TABLE `taskdevelopment` DISABLE KEYS */;
/*!40000 ALTER TABLE `taskdevelopment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `hashpw` varchar(64) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `type` enum('ProjectManager','Senior','Junior') NOT NULL,
  `salt` varchar(64) NOT NULL,
  `skills` varchar(300) DEFAULT NULL,
  `mail` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'user1','38e7f59446743beac67b699738ceabf74f3ec9169d2e31566f77abe56ee876ef','Alessandro Tundo',NULL,'ProjectManager','-3408564752822225002',NULL,NULL),(2,'user2','1e57bd7a43baa78a4f0df0c76608284d77a626aba83def6f3353f325ec891a80','Ale Vazzola',NULL,'Senior','4403057260337259784',NULL,NULL),(3,'user3','464f5cc5f855863571574aeb03d2906eeb22ee288d29a0cc3c0b1443cfd4c2f9','Ilaria Pigazzini',NULL,'Junior','-6296899952466904893',NULL,NULL),(4,'user4','5d5e6eebf03795c3f42a6de67a743484bc89376709790bd3c72413057b18fa10','Victor Popescu',NULL,'Senior','-589528451402326433',NULL,NULL),(5,'albertonava','8624ae2c1997705f5b5e685c18519738a46fee470edfbbb0fc6be34310ff3b0e','Alberto Nava',NULL,'Junior','1812160898009983697',NULL,NULL),(6,'user5','8624ae2c1997705f5b5e685c18519738a46fee470edfbbb0fc6be34310ff3b0e','Prova','Provino','Senior','1812160898009983697',NULL,NULL),(7,'user6','8624ae2c1997705f5b5e685c18519738a46fee470edfbbb0fc6be34310ff3b0e','Prosss','Prororo','ProjectManager','1812160898009983697',NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-19 10:41:45
