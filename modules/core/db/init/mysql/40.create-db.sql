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
-- WHERE:  created_by is not NULL

LOCK TABLES `sec_constraint` WRITE;
/*!40000 ALTER TABLE `sec_constraint` DISABLE KEYS */;
INSERT INTO `sec_constraint` VALUES ('5dec7a21f2b47858140a8047b4bbe4b2','2016-05-09 11:04:43.172','admin',1,NULL,NULL,NULL,NULL,NULL,'db','read','lineup$UserPreference',NULL,'{E}.client = :session$client_id',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<filter>\n  <and>\n    <c name=\"client\" class=\"java.lang.Integer\" operatorType=\"EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[queryEntity.client = :component$filterWithoutId.client81566]]>\n      <param name=\"component$filterWithoutId.client81566\" javaClass=\"java.lang.Integer\">NULL</param>\n    </c>\n  </and>\n</filter>\n',1,'888ea8bbc4649d41fbdf7bc0f37855cb'),('66d2f1ae4d6f22597e89728b8885b3ea','2016-05-09 11:05:21.153','admin',1,NULL,NULL,NULL,NULL,NULL,'db','read','lineup$AppUser',NULL,'{E}.client = :session$client_id',NULL,NULL,1,'888ea8bbc4649d41fbdf7bc0f37855cb'),('68bf0408f455f6c5f77829ee4591575d','2016-05-09 11:15:04.171','admin',1,NULL,NULL,NULL,NULL,NULL,'db','read','sec$Group',NULL,'{E}.id in (select h.group.id from sec$GroupHierarchy h where h.group.id = :session$userGroupId or h.parent.id = :session$userGroupId)',NULL,NULL,1,'888ea8bbc4649d41fbdf7bc0f37855cb'),('8bf946251c624686d8bfbdd62b702d4e','2016-05-09 11:26:29.742','admin',3,NULL,NULL,NULL,NULL,NULL,'db','read','sec$Role',NULL,'{E}.name like \'client_%\'',NULL,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<filter>\n  <and>\n    <c name=\"name\" class=\"java.lang.String\" operatorType=\"STARTS_WITH\" width=\"1\" type=\"PROPERTY\"><![CDATA[queryEntity.name like :component$filterWithoutId.name88818 ESCAPE \'\\\' ]]>\n      <param name=\"component$filterWithoutId.name88818\" javaClass=\"java.lang.String\">client_</param>\n    </c>\n  </and>\n</filter>\n',1,'888ea8bbc4649d41fbdf7bc0f37855cb'),('e2700808eb38aa75a554dfaf28aad84f','2016-05-09 11:19:41.404','admin',1,NULL,NULL,NULL,NULL,NULL,'db','read','sec$User',NULL,'{E}.group.id in (select h.group.id from sec$GroupHierarchy h where h.group.id = :session$userGroupId or h.parent.id = :session$userGroupId)',NULL,NULL,1,'888ea8bbc4649d41fbdf7bc0f37855cb');
/*!40000 ALTER TABLE `sec_constraint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sec_filter`
--
-- WHERE:  created_by is not NULL

LOCK TABLES `sec_filter` WRITE;
/*!40000 ALTER TABLE `sec_filter` DISABLE KEYS */;
/*!40000 ALTER TABLE `sec_filter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sec_group`
--
-- WHERE:  created_by is not NULL

LOCK TABLES `sec_group` WRITE;
/*!40000 ALTER TABLE `sec_group` DISABLE KEYS */;
INSERT INTO `sec_group` VALUES ('1f02f531d6d99abdff0d4a64a47d5486','2016-05-09 10:58:18.794','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','Sirius Cybernetics Corp.','888ea8bbc4649d41fbdf7bc0f37855cb'),('888ea8bbc4649d41fbdf7bc0f37855cb','2016-05-09 10:57:39.975','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','Clients','0fa2b1a51d684d699fbddff348347f93'),('e571013b664d000572cf5af3dfa6d3e9','2016-05-09 10:58:01.173','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','Stark Industries','888ea8bbc4649d41fbdf7bc0f37855cb');
/*!40000 ALTER TABLE `sec_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sec_group_hierarchy`
--
-- WHERE:  created_by is not NULL

LOCK TABLES `sec_group_hierarchy` WRITE;
/*!40000 ALTER TABLE `sec_group_hierarchy` DISABLE KEYS */;
INSERT INTO `sec_group_hierarchy` VALUES ('2b2a145726725e7dadb4463686a30fd0','2016-05-09 10:58:18.801','admin','1f02f531d6d99abdff0d4a64a47d5486','888ea8bbc4649d41fbdf7bc0f37855cb',1),('5698abf8753bed7395b4f3f228496ceb','2016-05-09 10:58:01.186','admin','e571013b664d000572cf5af3dfa6d3e9','888ea8bbc4649d41fbdf7bc0f37855cb',1),('84974be1c7d8b743a6ea529a96cf27db','2016-05-09 10:57:39.984','admin','888ea8bbc4649d41fbdf7bc0f37855cb','0fa2b1a51d684d699fbddff348347f93',0),('c643a3d8c2fdd2f1612ad1717bc75ca7','2016-05-09 10:58:01.186','admin','e571013b664d000572cf5af3dfa6d3e9','0fa2b1a51d684d699fbddff348347f93',0),('d375c62e96a0cdc6f3047c42dffb86cc','2016-05-09 10:58:18.801','admin','1f02f531d6d99abdff0d4a64a47d5486','0fa2b1a51d684d699fbddff348347f93',0);
/*!40000 ALTER TABLE `sec_group_hierarchy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sec_permission`
--
-- WHERE:  created_by is not NULL

LOCK TABLES `sec_permission` WRITE;
/*!40000 ALTER TABLE `sec_permission` DISABLE KEYS */;
INSERT INTO `sec_permission` VALUES ('042ea2d90b4a8ade7c9f295923f68f76','2016-05-09 12:38:26.532','admin',2,'2016-05-09 12:38:26.532',NULL,'2016-05-09 12:42:59.948','admin','1000-01-01 00:00:00.000',10,'sys$ScheduledTask.browse',0,'ce3c39ab0b5adfc1e3b7918e2fee1c22'),('0844549d96a3fd449f76a00f934fffb0','2016-05-09 12:38:26.533','admin',2,'2016-05-09 12:38:26.533',NULL,'2016-05-09 12:42:59.964','admin','1000-01-01 00:00:00.000',10,'sec$Role.browse',0,'ce3c39ab0b5adfc1e3b7918e2fee1c22'),('2116a40259679bd103edd56707004579','2016-05-09 12:42:37.962','admin',1,'2016-05-09 12:42:37.962',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sec$User.browse',0,'50e7762e624baa1d69752f773a735c15'),('213bc537355ccc353a97b86fd96ff1a9','2016-05-09 12:42:37.964','admin',1,'2016-05-09 12:42:37.964',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'appProperties',0,'50e7762e624baa1d69752f773a735c15'),('27c9aa77332d96e06c68512e09d47c04','2016-05-09 12:42:37.962','admin',1,'2016-05-09 12:42:37.962',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'entityInspector.browse',0,'50e7762e624baa1d69752f773a735c15'),('29fbf7943210842cf5932452c61ad7b9','2016-05-09 12:38:26.533','admin',2,'2016-05-09 12:38:26.533',NULL,'2016-05-09 12:42:59.961','admin','1000-01-01 00:00:00.000',10,'sys$Category.browse',0,'ce3c39ab0b5adfc1e3b7918e2fee1c22'),('2eff2301f779984e7863d3846b415880','2016-05-09 12:38:26.533','admin',2,'2016-05-09 12:38:26.533',NULL,'2016-05-09 12:42:59.957','admin','1000-01-01 00:00:00.000',10,'performanceStatistics',0,'ce3c39ab0b5adfc1e3b7918e2fee1c22'),('366764fd6a126d11ac56c605e2a504bd','2016-05-09 12:38:26.533','admin',2,'2016-05-09 12:38:26.533',NULL,'2016-05-09 12:42:59.963','admin','1000-01-01 00:00:00.000',10,'sec$UserSessionEntity.browse',0,'ce3c39ab0b5adfc1e3b7918e2fee1c22'),('3957be1923637687cda5381784e5d3eb','2016-05-09 12:42:37.963','admin',1,'2016-05-09 12:42:37.963',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sec$Group.browse',0,'50e7762e624baa1d69752f773a735c15'),('3a9ae29dc3e5530477e001044e690e11','2016-05-09 11:29:26.516','admin',2,'2016-05-09 11:29:26.516',NULL,'2016-05-09 11:56:56.775','admin','1000-01-01 00:00:00.000',20,'sec$Role:read',0,'50e7762e624baa1d69752f773a735c15'),('3b874cc4116f5353ac608278686db21d','2016-05-09 12:42:37.963','admin',1,'2016-05-09 12:42:37.963',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'entityRestore',0,'50e7762e624baa1d69752f773a735c15'),('40f28a86b9de9dbfa9aa17f7079fdd9c','2016-05-09 11:56:56.772','admin',1,'2016-05-09 11:56:56.772',NULL,NULL,NULL,'1000-01-01 00:00:00.000',20,'sec$Role:update',0,'50e7762e624baa1d69752f773a735c15'),('48d1499edc4cf4f4d46e1022067c1770','2016-05-09 12:42:37.963','admin',1,'2016-05-09 12:42:37.963',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sys$ScheduledTask.browse',0,'50e7762e624baa1d69752f773a735c15'),('4daeda43e9c7542eabb9ce0545919d6b','2016-05-09 12:38:58.402','admin',1,'2016-05-09 12:38:58.402',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sec$User.browse',1,'4082fc82562a346cfddb6cca3a88d500'),('4fdddc00427657e394b7ebff49af45d0','2016-05-09 12:38:26.534','admin',2,'2016-05-09 12:38:26.534',NULL,'2016-05-09 12:42:59.968','admin','1000-01-01 00:00:00.000',10,'appProperties',0,'ce3c39ab0b5adfc1e3b7918e2fee1c22'),('572d958c9ee59970f62d77a4e7963fc3','2016-05-09 12:38:26.533','admin',2,'2016-05-09 12:38:26.533',NULL,'2016-05-09 12:42:59.952','admin','1000-01-01 00:00:00.000',10,'sys$FileDescriptor.browse',0,'ce3c39ab0b5adfc1e3b7918e2fee1c22'),('5d400f39886b0cb368ee7b22395a8f57','2016-05-09 12:38:26.532','admin',2,'2016-05-09 12:38:26.532',NULL,'2016-05-09 12:42:59.942','admin','1000-01-01 00:00:00.000',10,'serverLog',0,'ce3c39ab0b5adfc1e3b7918e2fee1c22'),('675c8a5c3151040ea90d5c40a8c8ac46','2016-05-09 12:38:26.534','admin',2,'2016-05-09 12:38:26.534',NULL,'2016-05-09 12:42:59.967','admin','1000-01-01 00:00:00.000',10,'sys$LockInfo.browse',0,'ce3c39ab0b5adfc1e3b7918e2fee1c22'),('69898247167b53226af3ffc7a5348e30','2016-05-09 12:38:26.533','admin',2,'2016-05-09 12:38:26.533',NULL,'2016-05-09 12:42:59.951','admin','1000-01-01 00:00:00.000',10,'sys$SendingMessage.browse',0,'ce3c39ab0b5adfc1e3b7918e2fee1c22'),('74bd74972c53932018078767b5d6a1d7','2016-05-09 12:38:26.534','admin',2,'2016-05-09 12:38:26.534',NULL,'2016-05-09 12:42:59.965','admin','1000-01-01 00:00:00.000',10,'entityRestore',0,'ce3c39ab0b5adfc1e3b7918e2fee1c22'),('7577c0d53605648f824bcbbef0118f40','2016-05-09 12:38:26.533','admin',2,'2016-05-09 12:38:26.533',NULL,'2016-05-09 12:42:59.954','admin','1000-01-01 00:00:00.000',10,'entityLog',0,'ce3c39ab0b5adfc1e3b7918e2fee1c22'),('761e398ffbb58c509e2cb502621177f9','2016-05-09 12:42:37.963','admin',1,'2016-05-09 12:42:37.963',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sys$SendingMessage.browse',0,'50e7762e624baa1d69752f773a735c15'),('793f337e52232b5ba5ec06bb11bcbdc3','2016-05-09 11:56:56.771','admin',1,'2016-05-09 11:56:56.771',NULL,NULL,NULL,'1000-01-01 00:00:00.000',20,'sec$Role:delete',0,'50e7762e624baa1d69752f773a735c15'),('924fd69d9d556eaebba9b478e36eaa02','2016-05-09 12:42:37.963','admin',1,'2016-05-09 12:42:37.963',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sys$LockInfo.browse',0,'50e7762e624baa1d69752f773a735c15'),('a30d02712520cec45cf7b1651b537cdb','2016-05-09 12:38:26.532','admin',2,'2016-05-09 12:38:26.532',NULL,'2016-05-09 12:42:59.944','admin','1000-01-01 00:00:00.000',10,'sec$Group.browse',0,'ce3c39ab0b5adfc1e3b7918e2fee1c22'),('ab78634416640bfc6ae24beba6297b1a','2016-05-09 11:56:56.772','admin',1,'2016-05-09 11:56:56.772',NULL,NULL,NULL,'1000-01-01 00:00:00.000',20,'sec$Permission:delete',0,'50e7762e624baa1d69752f773a735c15'),('ad03a21ddf72c0e6f262cbcb2cefafb9','2016-05-09 12:42:37.963','admin',1,'2016-05-09 12:42:37.963',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'entityLog',0,'50e7762e624baa1d69752f773a735c15'),('b4addac200882fe48763a5b4e22cd9f6','2016-05-09 12:42:37.962','admin',1,'2016-05-09 12:42:37.962',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'serverLog',0,'50e7762e624baa1d69752f773a735c15'),('b6b492ba0df88b6c0b8f2c4dfa00491a','2016-05-09 12:38:26.532','admin',2,'2016-05-09 12:38:26.532',NULL,'2016-05-09 12:40:10.898','admin','1000-01-01 00:00:00.000',10,'administration',0,'ce3c39ab0b5adfc1e3b7918e2fee1c22'),('b8fb2afc782683497977329058dddc2e','2016-05-09 12:42:37.964','admin',1,'2016-05-09 12:42:37.964',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sys$Category.browse',0,'50e7762e624baa1d69752f773a735c15'),('beee693ad7a433c0d4b648ee81a5a6b9','2016-05-09 12:42:37.963','admin',1,'2016-05-09 12:42:37.963',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sys$FileDescriptor.browse',0,'50e7762e624baa1d69752f773a735c15'),('c764b4531be00baed9a6d58d95878d70','2016-05-09 12:42:37.962','admin',1,'2016-05-09 12:42:37.962',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sec$UserSessionEntity.browse',0,'50e7762e624baa1d69752f773a735c15'),('c8880c39a73ed63a50012b4340ba5eeb','2016-05-09 11:56:56.772','admin',1,'2016-05-09 11:56:56.772',NULL,NULL,NULL,'1000-01-01 00:00:00.000',20,'sec$Permission:update',0,'50e7762e624baa1d69752f773a735c15'),('d23c4c89de0f24c18f7adda23e73c615','2016-05-09 12:38:26.533','admin',2,'2016-05-09 12:38:26.533',NULL,'2016-05-09 12:42:59.949','admin','1000-01-01 00:00:00.000',10,'help',0,'ce3c39ab0b5adfc1e3b7918e2fee1c22'),('d28c14435fbccf9c5e87cbbefbccf78a','2016-05-09 12:38:26.533','admin',2,'2016-05-09 12:38:26.533',NULL,'2016-05-09 12:42:59.959','admin','1000-01-01 00:00:00.000',10,'sec$User.browse',0,'ce3c39ab0b5adfc1e3b7918e2fee1c22'),('d90b3761ec0788e2e87d91567cf01684','2016-05-09 11:56:56.771','admin',1,'2016-05-09 11:56:56.771',NULL,NULL,NULL,'1000-01-01 00:00:00.000',20,'sec$Permission:create',0,'50e7762e624baa1d69752f773a735c15'),('db1d33b1e2f772807c49c436c677d25d','2016-05-09 12:38:26.533','admin',2,'2016-05-09 12:38:26.533',NULL,'2016-05-09 12:42:59.956','admin','1000-01-01 00:00:00.000',10,'entityInspector.browse',0,'ce3c39ab0b5adfc1e3b7918e2fee1c22'),('dc127fcfe686ec17100b0e622f56124f','2016-05-09 12:38:58.402','admin',1,'2016-05-09 12:38:58.402',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sec$Role.browse',1,'4082fc82562a346cfddb6cca3a88d500'),('e59fa181b5c67a3624708dfe054372b6','2016-05-09 11:56:56.771','admin',1,'2016-05-09 11:56:56.771',NULL,NULL,NULL,'1000-01-01 00:00:00.000',20,'sec$Role:create',0,'50e7762e624baa1d69752f773a735c15'),('e5f7c017c897fda2ac25964c66c2be5c','2016-05-09 12:42:37.962','admin',1,'2016-05-09 12:42:37.962',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'jmxConsole',0,'50e7762e624baa1d69752f773a735c15'),('ef4bec0a4348eafcb81084da97500371','2016-05-09 12:42:37.964','admin',1,'2016-05-09 12:42:37.964',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'performanceStatistics',0,'50e7762e624baa1d69752f773a735c15'),('f79f2c076dc223fab2a182b0225e8784','2016-05-09 12:42:37.962','admin',1,'2016-05-09 12:42:37.962',NULL,NULL,NULL,'1000-01-01 00:00:00.000',10,'sec$Role.browse',0,'50e7762e624baa1d69752f773a735c15'),('f88f5703c952559306e75431ca608c1f','2016-05-09 12:38:26.532','admin',2,'2016-05-09 12:38:26.532',NULL,'2016-05-09 12:42:59.946','admin','1000-01-01 00:00:00.000',10,'jmxConsole',0,'ce3c39ab0b5adfc1e3b7918e2fee1c22'),('fbd156b4f6f2d13c0cb204c185a6a791','2016-05-09 11:29:26.516','admin',2,'2016-05-09 11:29:26.516',NULL,'2016-05-09 11:56:56.777','admin','1000-01-01 00:00:00.000',20,'sec$Permission:read',0,'50e7762e624baa1d69752f773a735c15');
/*!40000 ALTER TABLE `sec_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sec_role`
--
-- WHERE:  created_by is not NULL

LOCK TABLES `sec_role` WRITE;
/*!40000 ALTER TABLE `sec_role` DISABLE KEYS */;
INSERT INTO `sec_role` VALUES ('4082fc82562a346cfddb6cca3a88d500','2016-05-09 12:04:58.474','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','client_admins',NULL,NULL,NULL,NULL),('50e7762e624baa1d69752f773a735c15','2016-05-09 11:29:26.516','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','Users',NULL,'Default role for client users',1,NULL),('ce3c39ab0b5adfc1e3b7918e2fee1c22','2016-05-09 12:05:11.131','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','client_users',NULL,NULL,0,NULL);
/*!40000 ALTER TABLE `sec_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sec_session_attr`
--
-- WHERE:  created_by is not NULL

LOCK TABLES `sec_session_attr` WRITE;
/*!40000 ALTER TABLE `sec_session_attr` DISABLE KEYS */;
INSERT INTO `sec_session_attr` VALUES ('4921539c93c481b7f1616e84cb7abbeb','2016-05-09 11:02:54.805','admin',1,NULL,NULL,NULL,NULL,'client_id','2','int','1f02f531d6d99abdff0d4a64a47d5486'),('85ce060ebe3d1a49a5dd9af0ee0e145b','2016-05-09 11:02:41.836','admin',1,NULL,NULL,NULL,NULL,'client_id','1','int','e571013b664d000572cf5af3dfa6d3e9');
/*!40000 ALTER TABLE `sec_session_attr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sec_user`
--
-- WHERE:  created_by is not NULL

LOCK TABLES `sec_user` WRITE;
/*!40000 ALTER TABLE `sec_user` DISABLE KEYS */;
INSERT INTO `sec_user` VALUES ('2c537f196039a63ed57fa46558dbb547','2016-05-09 11:15:44.230','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','stark','stark','31b7cf3b9ea2dd0a0d7ccc35cf9c56a189d8017b','Tony Stark','Tony','Stark',NULL,NULL,NULL,'en',NULL,NULL,1,'e571013b664d000572cf5af3dfa6d3e9',NULL,0),('739241240560fa3c49967961dbc730d6','2016-05-10 10:27:26.938','stark',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','potts','potts','79883add5204e24982ed4d7f454c3e9432fa3c21','Pepper Potts','Pepper','Potts',NULL,NULL,NULL,'en',NULL,NULL,1,'e571013b664d000572cf5af3dfa6d3e9',NULL,0),('7c0416f6b4dacb7b64e53a0c15bd40de','2016-05-09 11:16:10.548','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','dent','dent','6aa822894d78fd547ae8c1ba412dab0aefbb40f1','Arthur Dent','Arthur','Dent',NULL,NULL,NULL,'en',NULL,NULL,1,'1f02f531d6d99abdff0d4a64a47d5486',NULL,0);
/*!40000 ALTER TABLE `sec_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sec_user_role`
--
-- WHERE:  created_by is not NULL

LOCK TABLES `sec_user_role` WRITE;
/*!40000 ALTER TABLE `sec_user_role` DISABLE KEYS */;
INSERT INTO `sec_user_role` VALUES ('027b14b72fa53057473ebd6ec3cc793b','2016-05-10 10:27:26.000','stark',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','739241240560fa3c49967961dbc730d6','ce3c39ab0b5adfc1e3b7918e2fee1c22'),('0e2cb90684f390b2b33a266237645fb8','2016-05-09 12:41:13.000','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','2c537f196039a63ed57fa46558dbb547','4082fc82562a346cfddb6cca3a88d500'),('3c43be6734318e5e1ce8862f317f0fa9','2016-05-09 11:32:51.000','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','7c0416f6b4dacb7b64e53a0c15bd40de','50e7762e624baa1d69752f773a735c15'),('7888e90068e902b00a17acdc8b41b57a','2016-05-10 10:27:26.000','stark',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','739241240560fa3c49967961dbc730d6','50e7762e624baa1d69752f773a735c15'),('7e7f7c8233532a08d8f3832495f96bdc','2016-05-09 11:32:43.000','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','2c537f196039a63ed57fa46558dbb547','50e7762e624baa1d69752f773a735c15'),('9b09a9cafbc2c2eb5c7ef8c864fc6cc7','2016-05-10 10:29:32.000','admin',1,NULL,NULL,NULL,NULL,'1000-01-01 00:00:00.000','7c0416f6b4dacb7b64e53a0c15bd40de','4082fc82562a346cfddb6cca3a88d500');
/*!40000 ALTER TABLE `sec_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sec_user_setting`
--
-- WHERE:  created_by is not NULL

LOCK TABLES `sec_user_setting` WRITE;
/*!40000 ALTER TABLE `sec_user_setting` DISABLE KEYS */;
/*!40000 ALTER TABLE `sec_user_setting` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-29 23:36:46
