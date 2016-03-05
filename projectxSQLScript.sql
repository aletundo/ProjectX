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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'TechCorp','techcorp@infos.com'),(2,'Findomestic','findomesticspa@findomestic.it');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `physicalresource`
--

DROP TABLE IF EXISTS `physicalresource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `physicalresource` (
  `idPhysicalResource` int(11) NOT NULL,
  `type` varchar(45) NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`idPhysicalResource`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `physicalresource`
--

LOCK TABLES `physicalresource` WRITE;
/*!40000 ALTER TABLE `physicalresource` DISABLE KEYS */;
INSERT INTO `physicalresource` VALUES (1,'Server',24),(2,'Laptop',80),(3,'Amazon EC2 Instance',8),(4,'Amazon RDS Instance',16),(5,'Amazon DynamoDB',3);
/*!40000 ALTER TABLE `physicalresource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `physicalresourceuse`
--

DROP TABLE IF EXISTS `physicalresourceuse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `physicalresourceuse` (
  `idProject` int(11) NOT NULL,
  `idPhysicalResource` int(11) NOT NULL,
  `quantityUsed` int(11) NOT NULL,
  PRIMARY KEY (`idProject`,`idPhysicalResource`),
  KEY `idPhysRes_physicalResUse_idx` (`idPhysicalResource`),
  CONSTRAINT `idProject_physicalResyUse` FOREIGN KEY (`idProject`) REFERENCES `project` (`idProject`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idPhysRes_physicalResUse` FOREIGN KEY (`idPhysicalResource`) REFERENCES `physicalresource` (`idPhysicalResource`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `physicalresourceuse`
--

LOCK TABLES `physicalresourceuse` WRITE;
/*!40000 ALTER TABLE `physicalresourceuse` DISABLE KEYS */;
INSERT INTO `physicalresourceuse` VALUES (1,1,2),(1,2,20),(1,3,3),(1,4,1),(1,5,1),(2,1,4),(2,2,50),(2,3,4),(2,4,3),(2,5,2);
/*!40000 ALTER TABLE `physicalresourceuse` ENABLE KEYS */;
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
INSERT INTO `precedences` VALUES (3,1),(3,2),(5,4),(7,6),(8,7);
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
  `deadline` date DEFAULT NULL,
  `start` date DEFAULT NULL,
  `rateWorkCompleted` float DEFAULT '0',
  `estimatedCosts` double DEFAULT NULL,
  PRIMARY KEY (`idProject`),
  KEY `UserProject_idx` (`idProjectManager`),
  KEY `ClientProject_idx` (`idClient`),
  CONSTRAINT `ClientProject` FOREIGN KEY (`idClient`) REFERENCES `client` (`idClient`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ProjectManagerProject` FOREIGN KEY (`idProjectManager`) REFERENCES `user` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'Progetto1',30000,9,1,'Vari ed eventuali obiettivi','Vari ed eventuali requisiti','Biologia, Natura, TeleControllo','2016-08-07','2016-03-07',0,29000),(2,'Progetto2InCorso',50000,9,2,'Vari obiettivi','Vari requisiti','Finanza, Business Intelligece','2016-06-01','2016-02-01',36.6723,45000);
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
  `startDay` date DEFAULT NULL,
  `finishDay` date DEFAULT NULL,
  `idSupervisor` int(11) DEFAULT NULL,
  `idProject` int(11) NOT NULL,
  `goals` longtext,
  `requirements` longtext,
  `outsourcing` enum('False','True') DEFAULT 'False',
  `rateWorkCompleted` float DEFAULT '0',
  `relativeWeight` float DEFAULT '0',
  `critical` enum('True','False') DEFAULT 'False',
  `status` enum('Started','Delay','CriticalDelay','NotStarted','Finished') DEFAULT 'NotStarted',
  PRIMARY KEY (`idStage`),
  KEY `fk_Stage_Project_idx` (`idProject`),
  KEY `SupervisorStage_idx` (`idSupervisor`),
  CONSTRAINT `ProjectStage` FOREIGN KEY (`idProject`) REFERENCES `project` (`idProject`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `SupervisorStage` FOREIGN KEY (`idSupervisor`) REFERENCES `user` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stage`
--

LOCK TABLES `stage` WRITE;
/*!40000 ALTER TABLE `stage` DISABLE KEYS */;
INSERT INTO `stage` VALUES (1,'Stage1','2016-03-07','2016-04-07',14,1,'Vari ed eventuali obiettivi','Vari ed eventuali requisiti','False',0,17.9191,'True','NotStarted'),(2,'Stage2','2016-03-07','2016-03-25',13,1,'vari ed eventuali obiettivi','vari ed eventuali requisiti','False',0,10.9827,'False','NotStarted'),(3,'Stage3','2016-04-07','2016-08-07',15,1,'vari','vari','False',0,71.0983,'True','NotStarted'),(4,'Prima fase','2016-02-01','2016-02-15',10,2,'Vari obiettivi','Vari requisiti','False',100,10.3448,'True','Finished'),(5,'Seconda fase','2016-02-15','2016-03-01',12,2,'Vari obiettivi','Vari requisiti','False',100,11.0345,'True','Finished'),(6,'Terza fase','2016-02-10','2016-03-15',11,2,'Vari obiettivi','Vari requisiti','False',30.5556,24.1379,'True','Started'),(7,'Quarta fase','2016-03-15','2016-04-15',13,2,'Vari obiettivi','Vari requisiti','False',0,21.3793,'True','NotStarted'),(8,'Quinta fase','2016-04-15','2016-06-01',16,2,'Vari obiettivi','Vari requisiti','False',0,33.1034,'True','NotStarted');
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
  `completed` enum('False','True') DEFAULT 'False',
  `name` varchar(150) DEFAULT NULL,
  `relativeWeight` float DEFAULT NULL,
  PRIMARY KEY (`idTask`),
  KEY `fk_Task_Stage1_idx` (`idStage`),
  CONSTRAINT `StageTask` FOREIGN KEY (`idStage`) REFERENCES `stage` (`idStage`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,'2016-03-07','2016-03-15',1,'False','Task1',27.2727),(2,'2016-03-15','2016-03-25',1,'False','Task2',33.3333),(3,'2016-03-25','2016-04-07',1,'False','Task3',39.3939),(4,'2016-03-07','2016-03-10',2,'False','Task4',21.0526),(5,'2016-03-11','2016-03-25',2,'False','Task5',78.9474),(6,'2016-04-07','2016-05-07',3,'False','Task6',24.4094),(7,'2016-05-07','2016-05-20',3,'False','Task7',11.0236),(8,'2016-05-20','2016-06-10',3,'False','Task8',17.3228),(9,'2016-06-10','2016-06-30',3,'False','Task9',16.5354),(10,'2016-07-01','2016-07-15',3,'False','Task10',11.811),(11,'2016-07-15','2016-08-07',3,'False','Task11',18.8976),(12,'2016-02-01','2016-02-07',4,'True','Primo task',38.8889),(13,'2016-02-05','2016-02-15',4,'True','Secondo task',61.1111),(14,'2016-02-15','2016-02-20',5,'True','Terzo task',35.2941),(15,'2016-02-20','2016-03-01',5,'True','Quarto task',64.7059),(16,'2016-02-10','2016-02-20',6,'True','FooTask',30.5556),(17,'2016-02-20','2016-03-15',6,'False','FooBarTask',69.4444),(18,'2016-03-15','2016-03-30',7,'False','BazTask',44.1176),(19,'2016-03-28','2016-04-15',7,'False','FooBar42',55.8824);
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
  `workCompleted` enum('False','True') DEFAULT 'False',
  `hoursRequired` int(11) NOT NULL,
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
INSERT INTO `taskdevelopment` VALUES (12,7,'False',28),(12,14,'True',36),(13,17,'True',96),(14,3,'False',84),(14,14,'True',12),(14,19,'False',46),(15,7,'False',84),(15,8,'False',48),(16,4,'False',32),(16,15,'True',88),(16,17,'False',80),(18,19,'False',106),(19,6,'False',248),(19,18,'False',120),(20,2,'False',88),(21,9,'False',168),(22,3,'False',20),(22,13,'True',56),(22,16,'True',32),(23,8,'False',128),(23,12,'True',56),(23,13,'True',32),(24,11,'False',120),(25,5,'False',120),(25,16,'True',56),(25,17,'True',24),(26,1,'False',72),(26,10,'False',120),(26,11,'False',72);
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
  `fullname` varchar(45) DEFAULT NULL,
  `type` enum('ProjectManager','Senior','Junior') NOT NULL,
  `salt` varchar(64) NOT NULL,
  `skills` varchar(300) DEFAULT NULL,
  `mail` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (9,'projectmanager1','79c67b1b4fad991e5b4f57b07e128031bf9f591cd0b7cef7f6d6dee8289dfeef','Kim Ross','ProjectManager','-4280404586469243456','Team Leader, Smart','kim.ross@gmail.com'),(10,'projectmanager2','fdcdbfee673016538615af3a2f68e64033b701a38110d977e2d3ec4f8f2369d0','Tim Cook','ProjectManager','2924996706303256609','J2EE Expert','tim.cook@gmail.com'),(11,'projectmanager3','c251f86c30cb815837c0a9f3ede7f614933c7ae6e50848eb8714d1406437ccdb','Sean Connery','ProjectManager','-1590948107888747297','Smart','sean.connery@gmail.com'),(12,'senior1','5c14b74d13225170e43ef58a85aed61b836f7651fa2f33637824697a304d6f28','Josh Rich','Senior','-5445241604329117679','Java, CSS3, HTML5','josh.rich@gmail.com'),(13,'senior2','08117f1ea7ada68998e5c951c0a6ac76fdbf67c98d83f221940f3db4384022e8','Marin Gheru','Senior','-6792706903863958269','Scala, Lisp','marin.gheru@gmail.com'),(14,'senior3','8c7f47796b6ca2de3b32a439db21d46f3d9aba45f6ebabf9e4e5593e3be8c46e','Robert De Niro','Senior','8023570297433157785','J2EE, EJB, Spring MVC','robert.de.niro@gmail.com'),(15,'senior4','4360400621b9f231d197a54e52fc85647cf4aed33bf45b2ecff7ffb0a72db1ac','Alex Pilju','Senior','3184291115291345812','SysAdmin, RedHat, FreeBSD','alex.pilju@gmail.com'),(16,'senior5','f40ab2933bee14efe697c119903b9fa5f28737ff05cbe59f09e8a086162932e7','Francesco Motti','Senior','-6577162478222502387','Web Technologies','francesco.motti@gmail.com'),(17,'junior1','fb626181d15c2f00af94e3aa2a202c75eb9f1db55e435f9f8f5bcb41b3c4a0c6','Roberta Valca','Junior','-5936166344488320325','J2EE, HTML5, CSS3, Javascript','roberta.valcai@gmail.com'),(18,'junior2','eef141cfa1b51edcf1f4fa409644942739d3e113012346bd1b1577d340010786','Julian Assange','Junior','-3787449028304039064','Security, PenTest','julian.assange@gmail.com'),(19,'junior3','cf07332b26c3ccc987976ecb102f3a3ba13fb3ad8b981ca8eb6f3d9826a78aeb','Edward Snowden','Junior','6393413954160680395','Security, PenTest','edward.snowden@gmail.com'),(20,'junior4','bda73696628842911a6c454aab2fd622ca43e88b836903d6501275ceefc2ad3c','Bill Gates','Junior','1283962606534700090','C++, C#, Visual Basic','bill.gates@gmail.com'),(21,'junior5','46c6d90de0380114211529742d6caf9b949cedc722491bcd3672f970e23a9f96','Tim Ryno','Junior','1093728519541714561','Prolog, Scala, Lisp','tim.ryno@gmail.com'),(22,'junior6','772fceda9c63c407673c5222341d6b12bea0c47ec57dd504f4d0011d628c373f','Federico Faggin','Junior','-4586985503350676543','Web Technologies, Sematic Web','federico.faggin@gmail.com'),(23,'junior7','7729d5f7b32eae7588d89553c43b17c0f28411d7b415fd005b7a09109c4fb420','Roberto Perico','Junior','-1267541783839583051','J2EE','roberto.perico@gmail.com'),(24,'junior8','52ac1884aa67de4f217a0d3c68345567cab6312162911e278e4a0794c3c4e541','John Belushi','Junior','-6832542702568549685','RedHat, DevOps','john.belushi@gmail.com'),(25,'junior9','b6542618a566ab8bc12fa4180187fdeb2fc17248819747a16276b969fdb98940','Martin Scorsese','Junior','-774033783096115650','Relational Database','martin.scorsese@gmail.com'),(26,'junior10','91fa608647c8e3ff773151c3f140fa8fa258b61aaa70f183d73490686b82c55b','Matt Grow','Junior','-1146180347890854893','MEAN Stack','matt.grow@gmail.com');
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

-- Dump completed on 2016-03-05 21:02:38
