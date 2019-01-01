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
-- Dumping data for table `lineup_mode_of_transfer`
--
-- WHERE:  created_by is not NULL and delete_ts is null

LOCK TABLES `lineup_mode_of_transfer` WRITE;
/*!40000 ALTER TABLE `lineup_mode_of_transfer` DISABLE KEYS */;
INSERT INTO `lineup_mode_of_transfer` VALUES ('60db4f287597ef51fbf2acb1e2688f2f',1,'2018-12-30 22:18:51.414','admin','2018-12-30 22:18:51.414',NULL,NULL,NULL,'CTV'),('e8366f67f3ebc517e9155fd539d121bc',1,'2018-12-30 22:18:47.024','admin','2018-12-30 22:18:47.024',NULL,NULL,NULL,'Heli');
/*!40000 ALTER TABLE `lineup_mode_of_transfer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `lineup_craft_type`
--
-- WHERE:  created_by is not NULL and delete_ts is null

LOCK TABLES `lineup_craft_type` WRITE;
/*!40000 ALTER TABLE `lineup_craft_type` DISABLE KEYS */;
INSERT INTO `lineup_craft_type` VALUES ('0da93a67f751af3a0523f09087cd0a93',2,'2018-12-30 22:35:29.115','qwe','2019-01-01 14:18:27.079','qwe',NULL,NULL,2,'AW139',12,NULL,NULL,NULL,'e8366f67f3ebc517e9155fd539d121bc'),('639f7d2a6f25c112eef75a07f7a9cf45',1,'2018-12-30 22:36:30.272','qwe','2018-12-30 22:36:30.272',NULL,NULL,NULL,2,'Damen CTV',18,2000,NULL,NULL,'60db4f287597ef51fbf2acb1e2688f2f'),('cac9ed9a115cf0848d5fb736f730a451',1,'2018-12-30 22:36:03.262','qwe','2018-12-30 22:36:03.262',NULL,NULL,NULL,2,'AW76',10,500,1000,1200,'e8366f67f3ebc517e9155fd539d121bc');
/*!40000 ALTER TABLE `lineup_craft_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `lineup_site_category`
--
-- WHERE:  created_by is not NULL and delete_ts is null

LOCK TABLES `lineup_site_category` WRITE;
/*!40000 ALTER TABLE `lineup_site_category` DISABLE KEYS */;
INSERT INTO `lineup_site_category` VALUES ('5dbb2eae4bc75e0ba3699b5c82170099',1,'2018-12-31 10:08:58.364','qwe','2018-12-31 10:08:58.364',NULL,NULL,NULL,'Airport'),('9ca9707879b0ed45262ee28d4500634e',1,'2018-12-31 10:08:51.850','qwe','2018-12-31 10:08:51.850',NULL,NULL,NULL,'Helideck');
/*!40000 ALTER TABLE `lineup_site_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `lineup_site`
--
-- WHERE:  created_by is not NULL and delete_ts is null

LOCK TABLES `lineup_site` WRITE;
/*!40000 ALTER TABLE `lineup_site` DISABLE KEYS */;
INSERT INTO `lineup_site` VALUES ('1db364665ac81c55d52837b074a3dff1',1,'2019-01-01 13:50:07.096','qwe','2019-01-01 13:50:07.096',NULL,NULL,NULL,'DolWin beta','9ca9707879b0ed45262ee28d4500634e',53.9858,6.9319,'DWBE',NULL,NULL,NULL),('43253600ed87943101f7ce5e4fbe5ec3',1,'2019-01-01 13:42:12.806','qwe','2019-01-01 13:42:12.806',NULL,NULL,NULL,'Husum','5dbb2eae4bc75e0ba3699b5c82170099',54.5083452,9.1303844,'EDXJ',NULL,NULL,NULL),('960e79a3d9a467639a825bdcf5b21085',3,'2019-01-01 13:39:19.985','qwe','2019-01-01 14:27:37.406','qwe',NULL,NULL,'Emden','5dbb2eae4bc75e0ba3699b5c82170099',53.38893,7.2263314,'EMDE',NULL,NULL,NULL),('abf41c266717dd74ef47d6dc9788a018',8,'2018-12-30 22:38:41.954','qwe','2019-01-01 13:42:22.981','qwe',NULL,NULL,'BorWin beta','9ca9707879b0ed45262ee28d4500634e',54.3586,6.0169,'BWBE',NULL,NULL,'9ca9707879b0ed45262ee28d4500634e'),('d7b14c9660f3940763a6621a483589e1',2,'2019-01-01 13:49:25.912','qwe','2019-01-01 14:27:24.887','qwe',NULL,NULL,'DolWin alpha','9ca9707879b0ed45262ee28d4500634e',54.0061,6.4236,'DWAL',NULL,NULL,NULL);
/*!40000 ALTER TABLE `lineup_site` ENABLE KEYS */;
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
