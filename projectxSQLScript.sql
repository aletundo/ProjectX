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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
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

-- Dump completed on 2016-02-22 16:30:30
