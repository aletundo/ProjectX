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
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (66,'Arcelli','arcelli.disco@unimib.com'),(67,'Rabulet','rabu@gmail.com'),(68,'Clientela','clientela@aol.com'),(69,'ClientPazzo','pazzopazzo@pazzo.com'),(70,'Clientelissimo','clientesissimo@gmail.com'),(71,'Clientelissimo','clientesissimo@gmail.com'),(72,'Puzza','djhfdhdf@fjjf.com');
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
INSERT INTO `precedences` VALUES (55,56),(60,56),(57,58),(59,60),(62,61),(63,62),(65,64),(66,65),(68,69);
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
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (60,'Progetto1',200000,9,66,'Finirlo magari','Troppe robe','Software Engeneering','2016-07-01','2016-01-01',0,NULL),(61,'Progetto2',20000,10,67,'boooh','bvooogogo','tech','2016-08-30','2016-02-01',0,NULL),(62,'ProvaCalcoloPesi',12345,9,68,'Speriamo','eeeeee','Nature','2016-07-01','2016-01-01',0,NULL),(63,'ProvaPesi2',12334,9,69,'pesiiii','qdhdhdh','Tech','2016-12-01','2016-09-01',0,NULL),(64,'ProvaDopoModifiche',12345,9,71,'shhdshs','dhhdhd','nature','2016-10-01','2016-05-01',0,1234),(65,'ProvaUpdateRate',1234,10,72,'swjsjjs','hdhdh','tech','2017-01-31','2017-01-01',100,123);
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
  PRIMARY KEY (`idStage`),
  KEY `fk_Stage_Project_idx` (`idProject`),
  KEY `SupervisorStage_idx` (`idSupervisor`),
  CONSTRAINT `ProjectStage` FOREIGN KEY (`idProject`) REFERENCES `project` (`idProject`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `SupervisorStage` FOREIGN KEY (`idSupervisor`) REFERENCES `user` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stage`
--

LOCK TABLES `stage` WRITE;
/*!40000 ALTER TABLE `stage` DISABLE KEYS */;
INSERT INTO `stage` VALUES (55,'Requirements Analysis','2016-01-01','2016-01-07',12,60,'Requirements table','The project requirements by the client','False',50,NULL,'False'),(56,'Domain Model','2016-01-08','2016-01-15',11,60,'The domain model diagram','Requirements analysis','False',0,NULL,'False'),(57,'Stageee','2016-02-01','2016-03-01',14,61,'djjd','djjdj','False',0,NULL,'False'),(58,'stagerrr','2016-03-02','2016-03-20',11,61,'erthygdf','ertghy','False',0,NULL,'False'),(59,'Stage2','2016-01-04','2016-01-31',15,60,'hdhdhd','hdhdhd','False',0,NULL,'False'),(60,'Stage3','2016-01-31','2016-02-28',14,60,'hdhdh','eehhe','False',0,NULL,'False'),(61,'Analisi dei requisiti','2016-01-01','2016-01-15',14,62,'tabella dei requisiti, casi d\'uso','testo progetto','False',0,0,'False'),(62,'Modello di dominio','2016-01-15','2016-01-31',13,62,'diagramma del modello di dominio','casi d\'uso','False',0,0,'False'),(63,'Diagrammi SSD','2016-02-01','2016-02-15',12,62,'diagrammi ssd','modello di dominio, contratti delle operazioni','False',0,0,'False'),(64,'Faccio la spesa','2016-09-01','2016-09-15',11,63,'spesa','niente','False',0,33.3333,'False'),(65,'Calo la pasta','2016-09-16','2016-09-30',15,63,'pasta cotta','spesa, acqua sul fuoco','False',0,33.3333,'False'),(66,'Cucino l\'uovo','2016-10-01','2016-10-15',15,63,'uovo cotto','pasta nell\'acqua','False',0,33.3333,'False'),(67,'Dipo','2016-03-01','2016-03-30',9,60,'fhhf','fhhfhf','True',0,0,'False'),(68,'stage233234','2016-05-01','2016-05-30',14,64,'stage242jjdhfhd','dhhdhdhd','False',0,66.6667,'False'),(69,'Stageg48474','2016-06-01','2016-06-15',13,64,'dhfhkjdfhjk','fhjhddj','False',0,33.3333,'False'),(70,'Provaupdate1','2017-01-01','2017-01-10',10,65,'jdd','qhfhfh','False',100,29.4118,'False'),(71,'Provastageupdate2','2017-01-08','2017-01-31',16,65,'jdd','djjjdsjds','False',100,70.5882,'False');
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
INSERT INTO `task` VALUES (1,'2016-02-01','2016-02-05',55,'True','Task1',21.875),(2,'2016-02-05','2016-02-10',55,'False','Task2',25),(3,'2016-01-01','2016-01-10',56,'False','fhfhfhd',NULL),(4,'2016-09-01','2016-09-03',64,'False',NULL,NULL),(5,'2016-09-04','2016-09-08',64,'False','compro la pasta',NULL),(6,'2016-09-16','2016-09-18',65,'False','riempio la pentola',NULL),(7,'2016-10-01','2016-10-05',66,'False','boooh',NULL),(8,'2016-01-08','2016-01-10',56,'False','task2234',NULL),(9,'2016-01-10','2016-01-13',56,'False','task57755',NULL),(10,'2016-01-04','2016-01-07',59,'False','taskerello',28.5714),(11,'2016-01-08','2016-01-11',59,'False','taskerello2',28.5714),(12,'2016-01-12','2016-01-17',59,'False','taskerello3',42.8571),(14,'2016-05-01','2016-05-05',68,'False','tasker',50),(15,'2016-05-06','2016-05-10',68,'False','tasker2',50),(16,'2017-01-01','2017-01-05',70,'True','provataskupdate1',50),(17,'2017-01-06','2017-01-10',70,'True','provataskupdat2',50),(18,'2017-01-08','2017-01-15',71,'True','taskissimo1',33.3333),(19,'2017-01-16','2017-01-31',71,'True','taskerello2',66.6667);
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
INSERT INTO `taskdevelopment` VALUES (12,1,'False',30),(12,11,'False',32),(14,8,'False',18),(14,9,'False',6),(14,12,'False',34),(14,15,'False',30),(15,1,'False',10),(15,6,'False',18),(15,8,'False',6),(15,9,'False',18),(15,10,'False',24),(15,12,'False',14),(16,10,'False',8),(18,14,'False',40),(23,15,'False',10),(23,18,'True',64),(24,16,'True',40),(25,9,'False',8),(25,17,'True',40),(26,19,'True',128);
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

-- Dump completed on 2016-02-27 11:12:09
