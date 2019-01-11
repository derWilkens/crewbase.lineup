-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: lineup
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Dumping data for table `sec_constraint`
--
-- WHERE:  created_by is not NULL and delete_ts is null

LOCK TABLES `sec_constraint` WRITE;
/*!40000 ALTER TABLE `sec_constraint` DISABLE KEYS */;
INSERT INTO `sec_constraint` VALUES ('5dec7a21f2b47858140a8047b4bbe4b2','2016-05-09 11:04:43.172','admin',2,'2018-12-30 22:22:24.035','admin',NULL,NULL,NULL,'db','read','lineup$AppUser',NULL,'{E}.client = :session$client_id',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<filter>\n  <and>\n    <c name=\"client\" class=\"java.lang.Integer\" operatorType=\"EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[queryEntity.client = :component$filterWithoutId.client81566]]>\n      <param name=\"component$filterWithoutId.client81566\" javaClass=\"java.lang.Integer\">NULL</param>\n    </c>\n  </and>\n</filter>\n',1,'888ea8bbc4649d41fbdf7bc0f37855cb'),('68bf0408f455f6c5f77829ee4591575d','2016-05-09 11:15:04.171','admin',1,NULL,NULL,NULL,NULL,NULL,'db','read','sec$Group',NULL,'{E}.id in (select h.group.id from sec$GroupHierarchy h where h.group.id = :session$userGroupId or h.parent.id = :session$userGroupId)',NULL,NULL,1,'888ea8bbc4649d41fbdf7bc0f37855cb'),('8bf946251c624686d8bfbdd62b702d4e','2016-05-09 11:26:29.742','admin',3,NULL,NULL,NULL,NULL,NULL,'db','read','sec$Role',NULL,'{E}.name like \'client_%\'',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<filter>\n  <and>\n    <c name=\"name\" class=\"java.lang.String\" operatorType=\"STARTS_WITH\" width=\"1\" type=\"PROPERTY\"><![CDATA[queryEntity.name like :component$filterWithoutId.name88818 ESCAPE \'\' ]]>\n      <param name=\"component$filterWithoutId.name88818\" javaClass=\"java.lang.String\">client_</param>\n    </c>\n  </and>\n</filter>\n',1,'888ea8bbc4649d41fbdf7bc0f37855cb'),('e2700808eb38aa75a554dfaf28aad84f','2016-05-09 11:19:41.404','admin',1,NULL,NULL,NULL,NULL,NULL,'db','read','sec$User',NULL,'{E}.group.id in (select h.group.id from sec$GroupHierarchy h where h.group.id = :session$userGroupId or h.parent.id = :session$userGroupId)',NULL,NULL,1,'888ea8bbc4649d41fbdf7bc0f37855cb');
/*!40000 ALTER TABLE `sec_constraint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sec_filter`
--
-- WHERE:  created_by is not NULL and delete_ts is null

LOCK TABLES `sec_filter` WRITE;
/*!40000 ALTER TABLE `sec_filter` DISABLE KEYS */;
/*!40000 ALTER TABLE `sec_filter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sec_group`
--
-- WHERE:  created_by is not NULL and delete_ts is null

LOCK TABLES `sec_group` WRITE;
/*!40000 ALTER TABLE `sec_group` DISABLE KEYS */;
INSERT INTO `sec_group` VALUES ('1f02f531d6d99abdff0d4a64a47d5486','2016-05-09 10:58:18.794','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','Sirius Cybernetics Corp.','888ea8bbc4649d41fbdf7bc0f37855cb'),('888ea8bbc4649d41fbdf7bc0f37855cb','2016-05-09 10:57:39.975','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','Clients','0fa2b1a51d684d699fbddff348347f93'),('e571013b664d000572cf5af3dfa6d3e9','2016-05-09 10:58:01.173','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','Stark Industries','888ea8bbc4649d41fbdf7bc0f37855cb');
/*!40000 ALTER TABLE `sec_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sec_permission`
--
-- WHERE:  created_by is not NULL and delete_ts is null

LOCK TABLES `sec_permission` WRITE;
/*!40000 ALTER TABLE `sec_permission` DISABLE KEYS */;
INSERT INTO `sec_permission` VALUES ('2116a40259679bd103edd56707004579','2016-05-09 12:42:37.962','admin',1,'2016-05-09 12:42:37.962',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sec$User.browse',0,'50e7762e624baa1d69752f773a735c15'),('213bc537355ccc353a97b86fd96ff1a9','2016-05-09 12:42:37.964','admin',1,'2016-05-09 12:42:37.964',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'appProperties',0,'50e7762e624baa1d69752f773a735c15'),('27c9aa77332d96e06c68512e09d47c04','2016-05-09 12:42:37.962','admin',1,'2016-05-09 12:42:37.962',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'entityInspector.browse',0,'50e7762e624baa1d69752f773a735c15'),('3957be1923637687cda5381784e5d3eb','2016-05-09 12:42:37.963','admin',1,'2016-05-09 12:42:37.963',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sec$Group.browse',0,'50e7762e624baa1d69752f773a735c15'),('3b874cc4116f5353ac608278686db21d','2016-05-09 12:42:37.963','admin',1,'2016-05-09 12:42:37.963',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'entityRestore',0,'50e7762e624baa1d69752f773a735c15'),('40f28a86b9de9dbfa9aa17f7079fdd9c','2016-05-09 11:56:56.772','admin',1,'2016-05-09 11:56:56.772',NULL,NULL,NULL,'1000-01-01 00:00:00.000',20,'sec$Role:update',0,'50e7762e624baa1d69752f773a735c15'),('48d1499edc4cf4f4d46e1022067c1770','2016-05-09 12:42:37.963','admin',1,'2016-05-09 12:42:37.963',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sys$ScheduledTask.browse',0,'50e7762e624baa1d69752f773a735c15'),('4daeda43e9c7542eabb9ce0545919d6b','2016-05-09 12:38:58.402','admin',1,'2016-05-09 12:38:58.402',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sec$User.browse',1,'4082fc82562a346cfddb6cca3a88d500'),('761e398ffbb58c509e2cb502621177f9','2016-05-09 12:42:37.963','admin',1,'2016-05-09 12:42:37.963',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sys$SendingMessage.browse',0,'50e7762e624baa1d69752f773a735c15'),('793f337e52232b5ba5ec06bb11bcbdc3','2016-05-09 11:56:56.771','admin',1,'2016-05-09 11:56:56.771',NULL,NULL,NULL,'1000-01-01 00:00:00.000',20,'sec$Role:delete',0,'50e7762e624baa1d69752f773a735c15'),('924fd69d9d556eaebba9b478e36eaa02','2016-05-09 12:42:37.963','admin',1,'2016-05-09 12:42:37.963',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sys$LockInfo.browse',0,'50e7762e624baa1d69752f773a735c15'),('ab78634416640bfc6ae24beba6297b1a','2016-05-09 11:56:56.772','admin',1,'2016-05-09 11:56:56.772',NULL,NULL,NULL,'1000-01-01 00:00:00.000',20,'sec$Permission:delete',0,'50e7762e624baa1d69752f773a735c15'),('ad03a21ddf72c0e6f262cbcb2cefafb9','2016-05-09 12:42:37.963','admin',1,'2016-05-09 12:42:37.963',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'entityLog',0,'50e7762e624baa1d69752f773a735c15'),('b4addac200882fe48763a5b4e22cd9f6','2016-05-09 12:42:37.962','admin',1,'2016-05-09 12:42:37.962',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'serverLog',0,'50e7762e624baa1d69752f773a735c15'),('b8fb2afc782683497977329058dddc2e','2016-05-09 12:42:37.964','admin',1,'2016-05-09 12:42:37.964',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sys$Category.browse',0,'50e7762e624baa1d69752f773a735c15'),('beee693ad7a433c0d4b648ee81a5a6b9','2016-05-09 12:42:37.963','admin',1,'2016-05-09 12:42:37.963',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sys$FileDescriptor.browse',0,'50e7762e624baa1d69752f773a735c15'),('c764b4531be00baed9a6d58d95878d70','2016-05-09 12:42:37.962','admin',1,'2016-05-09 12:42:37.962',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sec$UserSessionEntity.browse',0,'50e7762e624baa1d69752f773a735c15'),('c8880c39a73ed63a50012b4340ba5eeb','2016-05-09 11:56:56.772','admin',1,'2016-05-09 11:56:56.772',NULL,NULL,NULL,'1000-01-01 00:00:00.000',20,'sec$Permission:update',0,'50e7762e624baa1d69752f773a735c15'),('d90b3761ec0788e2e87d91567cf01684','2016-05-09 11:56:56.771','admin',1,'2016-05-09 11:56:56.771',NULL,NULL,NULL,'1000-01-01 00:00:00.000',20,'sec$Permission:create',0,'50e7762e624baa1d69752f773a735c15'),('dc127fcfe686ec17100b0e622f56124f','2016-05-09 12:38:58.402','admin',1,'2016-05-09 12:38:58.402',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sec$Role.browse',1,'4082fc82562a346cfddb6cca3a88d500'),('e59fa181b5c67a3624708dfe054372b6','2016-05-09 11:56:56.771','admin',1,'2016-05-09 11:56:56.771',NULL,NULL,NULL,'1000-01-01 00:00:00.000',20,'sec$Role:create',0,'50e7762e624baa1d69752f773a735c15'),('e5f7c017c897fda2ac25964c66c2be5c','2016-05-09 12:42:37.962','admin',1,'2016-05-09 12:42:37.962',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'jmxConsole',0,'50e7762e624baa1d69752f773a735c15'),('ef4bec0a4348eafcb81084da97500371','2016-05-09 12:42:37.964','admin',1,'2016-05-09 12:42:37.964',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'performanceStatistics',0,'50e7762e624baa1d69752f773a735c15'),('f79f2c076dc223fab2a182b0225e8784','2016-05-09 12:42:37.962','admin',1,'2016-05-09 12:42:37.962',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sec$Role.browse',0,'50e7762e624baa1d69752f773a735c15');
/*!40000 ALTER TABLE `sec_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sec_role`
--
-- WHERE:  created_by is not NULL and delete_ts is null

LOCK TABLES `sec_role` WRITE;
/*!40000 ALTER TABLE `sec_role` DISABLE KEYS */;
INSERT INTO `sec_role` VALUES ('4082fc82562a346cfddb6cca3a88d500','2016-05-09 12:04:58.474','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','client_admins',NULL,NULL,NULL,NULL),('50e7762e624baa1d69752f773a735c15','2016-05-09 11:29:26.516','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','Users',NULL,'Default role for client users',1,NULL),('ce3c39ab0b5adfc1e3b7918e2fee1c22','2016-05-09 12:05:11.131','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','client_users',NULL,NULL,0,NULL);
/*!40000 ALTER TABLE `sec_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sec_session_attr`
--
-- WHERE:  created_by is not NULL and delete_ts is null

LOCK TABLES `sec_session_attr` WRITE;
/*!40000 ALTER TABLE `sec_session_attr` DISABLE KEYS */;
INSERT INTO `sec_session_attr` VALUES ('4921539c93c481b7f1616e84cb7abbeb','2016-05-09 11:02:54.805','admin',1,NULL,NULL,NULL,NULL,'client_id','2','int','1f02f531d6d99abdff0d4a64a47d5486'),('85ce060ebe3d1a49a5dd9af0ee0e145b','2016-05-09 11:02:41.836','admin',1,NULL,NULL,NULL,NULL,'client_id','1','int','e571013b664d000572cf5af3dfa6d3e9');
/*!40000 ALTER TABLE `sec_session_attr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sec_user`
--
-- WHERE:  created_by is not NULL and delete_ts is null

LOCK TABLES `sec_user` WRITE;
/*!40000 ALTER TABLE `sec_user` DISABLE KEYS */;
INSERT INTO `sec_user` VALUES ('0a5104fda2a77c48af601cd45771b21f','2018-12-30 22:19:46.661','admin',1,'2018-12-30 22:19:46.661',NULL,NULL,NULL,'1000-01-01 00:00:00.000','qwe','qwe','7f503d0791891ac55556f403c1d3f7f1e7bd5910','Max Mustermann','Max','Mustermann','Master',NULL,NULL,'de',NULL,NULL,1,'1f02f531d6d99abdff0d4a64a47d5486',NULL,0),('2c537f196039a63ed57fa46558dbb547','2016-05-09 11:15:44.230','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','stark','stark','31b7cf3b9ea2dd0a0d7ccc35cf9c56a189d8017b','Tony Stark','Tony','Stark',NULL,NULL,NULL,'en',NULL,NULL,1,'e571013b664d000572cf5af3dfa6d3e9',NULL,0),('739241240560fa3c49967961dbc730d6','2016-05-10 10:27:26.938','stark',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','potts','potts','79883add5204e24982ed4d7f454c3e9432fa3c21','Pepper Potts','Pepper','Potts',NULL,NULL,NULL,'en',NULL,NULL,1,'e571013b664d000572cf5af3dfa6d3e9',NULL,0),('7c0416f6b4dacb7b64e53a0c15bd40de','2016-05-09 11:16:10.548','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','dent','dent','6aa822894d78fd547ae8c1ba412dab0aefbb40f1','Arthur Dent','Arthur','Dent',NULL,NULL,NULL,'en',NULL,NULL,1,'1f02f531d6d99abdff0d4a64a47d5486',NULL,0);
/*!40000 ALTER TABLE `sec_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sec_user_role`
--
-- WHERE:  created_by is not NULL and delete_ts is null

LOCK TABLES `sec_user_role` WRITE;
/*!40000 ALTER TABLE `sec_user_role` DISABLE KEYS */;
INSERT INTO `sec_user_role` VALUES ('0093908cb9f729f9f811d3321c0c843e','2018-12-30 22:19:46.662','admin',1,'2018-12-30 22:19:46.662',NULL,NULL,NULL,'1000-01-01 00:00:00.000','0a5104fda2a77c48af601cd45771b21f','0c018061b26f4de2a5bedff348347f93'),('027b14b72fa53057473ebd6ec3cc793b','2016-05-10 10:27:26.000','stark',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','739241240560fa3c49967961dbc730d6','ce3c39ab0b5adfc1e3b7918e2fee1c22'),('0e2cb90684f390b2b33a266237645fb8','2016-05-09 12:41:13.000','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','2c537f196039a63ed57fa46558dbb547','4082fc82562a346cfddb6cca3a88d500'),('3c43be6734318e5e1ce8862f317f0fa9','2016-05-09 11:32:51.000','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','7c0416f6b4dacb7b64e53a0c15bd40de','50e7762e624baa1d69752f773a735c15'),('40da1e46264d831b4c7fb803c6842fed','2018-12-30 22:19:46.662','admin',1,'2018-12-30 22:19:46.662',NULL,NULL,NULL,'1000-01-01 00:00:00.000','0a5104fda2a77c48af601cd45771b21f','50e7762e624baa1d69752f773a735c15'),('7888e90068e902b00a17acdc8b41b57a','2016-05-10 10:27:26.000','stark',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','739241240560fa3c49967961dbc730d6','50e7762e624baa1d69752f773a735c15'),('7e7f7c8233532a08d8f3832495f96bdc','2016-05-09 11:32:43.000','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','2c537f196039a63ed57fa46558dbb547','50e7762e624baa1d69752f773a735c15'),('9b09a9cafbc2c2eb5c7ef8c864fc6cc7','2016-05-10 10:29:32.000','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','7c0416f6b4dacb7b64e53a0c15bd40de','4082fc82562a346cfddb6cca3a88d500');
/*!40000 ALTER TABLE `sec_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-01 14:28:40