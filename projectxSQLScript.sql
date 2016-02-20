CREATE DATABASE  IF NOT EXISTS `projectx` /*!40100 DEFAULT CHARACTER SET latin1 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'Microsoft','uuuhdhd@aol.com'),(2,'Bella','boooh@gmail.com'),(3,'YOKI','yoki@ufhf.com'),(5,'CiccioPasticcio','ciccio@pasticcio.it'),(6,'Google','google@gmail.com'),(7,'yahoooooo','fhhfhf@yahoo.com'),(8,'Bing','bingo@bing.com'),(9,'Hobs',NULL),(10,'Chdhdh',NULL),(11,'Amd',NULL),(12,'ProvaStg',NULL),(13,'Yoyoyo','yoyoyoy@hotmail.com'),(14,'yuuuuu','rureyr@uuu.com'),(15,'uuu','uuu@love.it'),(16,'wert','er@fhjfhf.com'),(17,'wertyu','erty@tityu.com'),(18,'wertyui','werty@gomail.com'),(19,'qwerty','qwerty@qwert.com'),(20,'wertyu','erty@tityu.com'),(21,'zxc','zxc@gomail.com'),(22,'zxc','zxc@gomail.com'),(23,'dsfhj','asdfg@hjjdd.coim'),(24,'dsfhj','asdfg@hjjdd.coim'),(25,'dsfhj','asdfg@hjjdd.coim'),(26,'asdfg','asdfg@hotutt.com'),(27,'asdfg','asdfg@hotutt.com'),(28,'asdfg','asdfg@kdgfghd.com'),(29,'asd','asd@ghjf.com'),(30,'asd','asd@ghjf.com'),(31,'adhjdh','df@ohg.com'),(32,'asdfgh','asdfg'),(33,'asdfgh','asdfg'),(34,'asdfgh','asdfg'),(35,'asdfgh','asdfg'),(36,'a','a'),(37,'b','b'),(38,'aaaaaa','aaaa@gmail.com'),(39,'hshshs','hsaahajja@gmail.com'),(40,'cccc','cccc'),(41,'Clienteee','afdgf@gohg.com'),(42,'kjdhuvf','dgfgfkig@gdehyhyuf.com'),(43,'shjjfd','ddfdifdijfd@gmail.com'),(44,'porova','offju@gmail.com'),(45,'dfkhu','ddhufd@gohshgd.com'),(46,'asdfghj','asdfghj@rytryry.com'),(47,'ksjfh','jdjdjdjd@gjghg.com'),(48,'jdfhghj','jsdfgjf@hhfhfhf.com'),(49,'qjwhesdfh','hdhffh@fhfhf.com'),(50,'fsÃ²oidhÃ²o','kdagho@doohf.com'),(51,'Ã Ã¨Ã Ã¨Ã ','dfrfgfdjli@hotu.com'),(52,'dhdhhdhd','dhhdhd@gmail.com'),(53,'askjdfhjf','jdshdhhd@gmnaik.com'),(54,'sdf','dfdgffd@gmail.com'),(55,'sdf','dfdgffd@gmail.com'),(56,'ffgfgff','fdfdfd'),(57,'ejrhfh','hdhdhdhd'),(58,'fddd','dd'),(59,'wedfgh','sdfg'),(60,'aqwert','asdfg'),(61,'ttt','ttt'),(62,'boh','oohh'),(63,'hudhudhu','dhuuduhd'),(64,'qwert','werty'),(65,'j','j');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `precedences`
--

DROP TABLE IF EXISTS `precedences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `precedences` (
  `idStage` int(11) NOT NULL,
  `idPrecedence` int(11) NOT NULL,
  PRIMARY KEY (`idStage`,`idPrecedence`),
  KEY `StageIdPrecedence_idx` (`idPrecedence`),
  CONSTRAINT `StageIdPrecedence` FOREIGN KEY (`idPrecedence`) REFERENCES `stage` (`idStage`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `StageIdStage` FOREIGN KEY (`idStage`) REFERENCES `stage` (`idStage`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `precedences`
--

LOCK TABLES `precedences` WRITE;
/*!40000 ALTER TABLE `precedences` DISABLE KEYS */;
INSERT INTO `precedences` VALUES (54,23),(23,30),(23,31),(30,31),(52,50),(50,51),(51,52),(30,53),(31,53),(53,54);
/*!40000 ALTER TABLE `precedences` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (36,'ccc',123445,1,40,'ccc','cccc','boooh',123,'2015-08-09',12,NULL),(37,'aaaa',123456,1,43,'aaaa','aaaaaaaaaaaa','tech',123,'2012-03-26',12,'2012-03-25'),(38,'provasuper',123456,1,44,'dfs','fvfjf','tech',12,'2016-02-03',23,'2016-02-03'),(39,'diooooo',12345678,1,45,'hfdhdhjdj','djdf','tech',12345,'2016-02-03',12,'2016-02-03'),(40,'ProvaStage333',12345,1,46,'asdfgth','adsfgh','tech',12,'2016-02-03',21,'2016-02-03'),(41,'sahyudhysfd',1234567,1,47,'jds','jfdjfkjfd','tech',12345,'2016-07-06',12,'2016-07-06'),(42,'Provvvv',123456,1,48,'dhhdhdh','fdhhdjdhdh','tech',12345,'2016-07-09',12,'2016-07-09'),(43,'sjjsjsjsjs',123456,1,49,'jszjsjsjsj','sjsjjsjs','tech',1234,'2015-06-08',12,'2015-06-08'),(44,'provacorrezioneila',1324,1,50,'afoiio','Ã²fkdslhÃ li','nature',123,'2016-02-02',20,'2016-02-02'),(45,'ciaoÃ¨Ã¨Ã¨Ã¨',1234,1,51,'wqerrtr','edffddg','tech',1234,'2016-08-20',12,'2016-08-20'),(46,'provaaaasdfghytr',1234567,1,52,'sadfghj','asdfgh','nature',12345,'2016-08-20',29,'2016-08-20'),(47,'asfgrere',13456,1,53,'sdfgddd','dfgff','biology',123,'2015-06-09',12,'2015-06-09'),(48,'tegporego',1234567,1,54,'hdshdshs','hdshdh','tech',23,'2016-08-08',12,'2016-08-08'),(49,'tegporego',1234567,1,55,'hdshdshs','hdshdh','tech',23,'2016-08-08',12,'2016-08-08'),(50,'aaaa',1234,1,56,'aaaa','aaaa','ttt',1234,'2016-08-08',23,'2016-08-08'),(51,'dudhdhdhhd',12345,1,57,'hdhdhdh','hdhdhdhd','tttt',1234,'2016-09-09',12,'2016-09-09'),(52,'tyyyyy',1234,1,58,'yyy','yyy','tech',12,'2016-09-09',12,'2016-09-09'),(53,'yyy',1234,1,59,'yyy','yyy','boh',123,'2015-06-06',12,'2015-06-06'),(54,'uu',12345,1,60,'uu','uuu','tttt',1234,'2000-08-09',12,'2000-08-09'),(55,'uuu',1234,1,61,'uuu','uuu','tech',12,'2000-09-09',12,'2000-09-09'),(56,'Pororkr',2323,1,62,'ksksk','kdjdj','nature',1234,'2000-09-09',12,'2000-09-09'),(57,'bnbnbn',12345,1,63,'bnbn','bnnbbn','tech',123,'2000-09-09',1,'2000-09-09'),(58,'hhh',1234,1,64,'hhh','hhh','hdhd',1234,'2000-09-09',1,'2000-09-09'),(59,'boh',1234,1,65,'j','j','ttt',12,'2000-09-09',12,'2000-09-09');
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
  `outsourcing` enum('False','True') DEFAULT 'False',
  `companyName` varchar(45) DEFAULT NULL,
  `companyMail` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idStage`),
  KEY `fk_Stage_Project_idx` (`idProject`),
  KEY `SupervisorStage_idx` (`idSupervisor`),
  CONSTRAINT `ProjectStage` FOREIGN KEY (`idProject`) REFERENCES `project` (`idProject`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `SupervisorStage` FOREIGN KEY (`idSupervisor`) REFERENCES `user` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stage`
--

LOCK TABLES `stage` WRITE;
/*!40000 ALTER TABLE `stage` DISABLE KEYS */;
INSERT INTO `stage` VALUES (3,'Stage1',12,'2016-02-03','2016-02-03',2,38,'speriamo dio cane','dioooo','False',NULL,NULL),(4,'Stage2222',4,'2016-02-03','2016-02-03',1,39,'dhdhdhdh','dhhdhdhd','False',NULL,NULL),(5,'stage2',12,'2016-02-03','2016-02-03',6,40,'asdfrg','asdfg','False',NULL,NULL),(6,'Stageeeee',12,'2016-07-06','2016-07-06',6,41,'asdfghjk','asdfghj','False',NULL,NULL),(7,'stage5555',12,'2016-07-06','2016-07-06',NULL,41,'dhj','hdhj','False',NULL,NULL),(8,'Stograncazzo',12,'2016-07-09','2016-07-09',2,42,'jshshshs','hdhdhhdh','False',NULL,NULL),(9,'stage44444',2,'2016-07-09','2016-07-09',4,42,'dhjyufds','dcdhfdjdd','False',NULL,NULL),(10,'sjss',123,'2016-07-09','2016-07-09',4,42,'hdhdhdhdh','hjzhshhs','False',NULL,NULL),(11,'hshdhdh',12,'2015-06-08','2015-06-08',NULL,43,'dshdhhdhd','dshdshdhhd','False',NULL,NULL),(12,'fhhfhf',12,'2015-06-08','2015-06-08',NULL,43,'hdhdhdhd','dhdhhdhd','False',NULL,NULL),(13,'cnbfchdhd',12,'2015-06-08','2015-06-08',NULL,43,'hdshdhd','shhshs','False',NULL,NULL),(14,'dhdhdh',12,'2015-06-08','2015-06-08',6,43,'dshdhhd','dhdhhd','False',NULL,NULL),(15,'dhdhhd',1,'2015-06-08','2015-06-08',7,43,'dhhdhdhd','qdhjdd','False',NULL,NULL),(16,'staandandobene',12,'2016-02-02','2016-02-02',NULL,44,'Ã²ofid','haÃ²oidgho','False',NULL,NULL),(17,'dhhdhd',21,'2016-08-20','2016-08-20',NULL,45,'fhfhfh','dhdhdh','False',NULL,NULL),(20,'sdfgh',12,'2015-06-09','2015-06-09',NULL,47,'sdfgh','swdefrg','True','dyuduudud','hdhdhdhd@gmail.com'),(21,'uuuu',12,'2015-06-09','2015-06-09',NULL,47,'uuuuu','hjsddjdj','True','dhhdhd','dhhdhdhd'),(22,'aaa',1,'2016-09-09','2016-09-09',NULL,51,'aaaa','aaa','True','Buuu','uvfudhdhd'),(23,'ahhahaah',12,'2016-09-09','2016-09-09',NULL,52,'hahaha','hahaha','True','llolo','ololo'),(24,'ttt',12,'2015-06-06','2015-06-06',NULL,53,'ttt','ttt','False',NULL,NULL),(25,'uuu',12,'2000-08-09','2000-08-09',NULL,54,'uuu','uuu','True','hh','hhhh'),(26,'uuu',12,'2000-09-09','2000-09-09',NULL,55,'uuu','uuu','True','hh','hhh'),(27,'iii',12,'2000-09-09','2000-09-09',NULL,55,'iiii','iii','True','hhhuiu','hhhhh'),(28,'kkk',1234,'2000-09-09','2000-09-09',NULL,55,'kkk','kkkkkk','True','bbb','bbb'),(29,'jjj',12,'2000-09-09','2000-09-09',NULL,55,'jjj','jjjj','True','',''),(30,'nnn',12,'2000-01-09','2000-01-09',NULL,52,'nnn','nnn','True','nn','nnn'),(31,'mmm',12,'2000-01-09','2000-01-09',NULL,52,'mm','mmm','True','',''),(32,'uuuu',12,'2000-01-09','2000-01-09',NULL,55,'uuu','uuu','True','uu','uu'),(33,'hunjcjhujc',12,'2000-09-09','2000-09-09',NULL,55,'jjnds','dh','True','mmm','mmm'),(34,'jdhfd',12,'2000-09-09','2000-09-09',NULL,53,'dhdhd','jdjdd','True','cvvbvnvqjfchfhf','jfdhd'),(35,'fh',12,'2000-09-09','2000-09-09',NULL,53,'dhdhdh','hdshdhd','True','nmm','vv'),(36,'dfhjdh',12,'2000-09-09','2000-09-09',NULL,56,'hddh','dhhdh','True','boh','hf'),(37,'fjhfh',1,'2000-09-09','2000-09-09',NULL,56,'dhdh','dhdhhd','True','nn','nnn'),(38,'mm',12,'2000-09-09','2000-09-09',NULL,56,'mm','mmm','True','mm','uuuu'),(39,'boooh',2,'2000-09-09','2000-09-09',NULL,56,'booohh','bvh','True','mmmmnbfcvh','mdfhjfd'),(40,'bmbmbm',12,'2000-09-09','2000-09-09',NULL,57,' MBMBMB','MBMBM','True','n','n'),(41,'ushuhus',1,'2000-09-09','2000-09-09',NULL,57,'huhuhu','huhu','True','m','m'),(42,'ghu',12,'2000-09-09','2000-09-09',NULL,57,'hufhufhu','huhudhufdhu','True','m','m'),(43,'mm',12,'2000-09-09','2000-09-09',NULL,57,'mmm','mmm','True','g','g'),(44,'mmfdjhfhfjfjf',12,'2000-09-09','2000-09-09',NULL,57,'mmmm','mmmmmmm','True','m','m'),(45,'fhuf',1,'2000-09-09','2000-09-09',NULL,58,'hfdhuhuf','hudfhudhu','True','hj','j'),(46,'ghhghlllll',2222,'2000-09-09','2000-09-09',NULL,58,'hfhfhfhfhf','huhuu','True','mm','mm'),(47,'hjjj',1,'2000-09-09','2000-09-09',NULL,58,'jjjjj','j','True','f','f'),(48,'hudhud',2,'2000-09-09','2000-09-09',NULL,50,'huhudhudqhudhd','hudhud','True','jf','fifijf'),(49,'jijij',123,'2000-09-09','2000-09-09',NULL,50,'ijiji','ijiji','True','j','j'),(50,'qwerty',1,'2000-09-09','2000-09-09',NULL,59,'qwerty','qwerty','True','uio','uio'),(51,'asdfgh',1,'2000-09-09','2000-09-09',NULL,59,'asdfgh','asdfgh','True','as','as'),(52,'zxcvbn',1,'2000-09-09','2000-09-09',NULL,59,'zxcvbn','zxcvbn','True','zx','zx'),(53,'pippo',1,'2000-09-09','2000-09-09',NULL,52,'pippo','pippo','True','b','b'),(54,'pluto',1,'2000-09-09','2000-09-09',NULL,52,'pluto','pluto','True','m','m');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,NULL,NULL,3,NULL),(2,NULL,NULL,3,NULL);
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
INSERT INTO `taskdevelopment` VALUES (3,1,NULL),(3,2,NULL);
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

-- Dump completed on 2016-02-20 17:39:24
