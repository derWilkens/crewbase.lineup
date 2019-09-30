-- MySQL dump 10.13  Distrib 5.7.25, for Win64 (x86_64)
--
-- Host: localhost    Database: lineup
-- ------------------------------------------------------
-- Server version	5.7.25-log

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
-- Dumping data for table `SEC_GROUP_HIERARCHY`
--
-- WHERE:  created_by is not NULL

LOCK TABLES `SEC_GROUP_HIERARCHY` WRITE;
/*!40000 ALTER TABLE `SEC_GROUP_HIERARCHY` DISABLE KEYS */;
INSERT INTO `SEC_GROUP_HIERARCHY` VALUES ('2b2a145726725e7dadb4463686a30fd0','2016-05-09 10:58:18.801','admin','1f02f531d6d99abdff0d4a64a47d5486','888ea8bbc4649d41fbdf7bc0f37855cb',1),('3a9b1754dc067219e9b0077e85f2a4c3','2019-05-07 14:20:01.182','admin','3fb8253858944520317fb13144db9eb4','0fa2b1a51d684d699fbddff348347f93',0),('3da08c2806215e7d3b94a66725927ce3','2019-05-07 14:20:01.183','admin','3fb8253858944520317fb13144db9eb4','888ea8bbc4649d41fbdf7bc0f37855cb',1),('5698abf8753bed7395b4f3f228496ceb','2016-05-09 10:58:01.186','admin','e571013b664d000572cf5af3dfa6d3e9','888ea8bbc4649d41fbdf7bc0f37855cb',1),('84974be1c7d8b743a6ea529a96cf27db','2016-05-09 10:57:39.984','admin','888ea8bbc4649d41fbdf7bc0f37855cb','0fa2b1a51d684d699fbddff348347f93',0),('c643a3d8c2fdd2f1612ad1717bc75ca7','2016-05-09 10:58:01.186','admin','e571013b664d000572cf5af3dfa6d3e9','0fa2b1a51d684d699fbddff348347f93',0),('d375c62e96a0cdc6f3047c42dffb86cc','2016-05-09 10:58:18.801','admin','1f02f531d6d99abdff0d4a64a47d5486','0fa2b1a51d684d699fbddff348347f93',0);
/*!40000 ALTER TABLE `SEC_GROUP_HIERARCHY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `SEC_USER_SETTING`
--
-- WHERE:  created_by is not NULL

LOCK TABLES `SEC_USER_SETTING` WRITE;
/*!40000 ALTER TABLE `SEC_USER_SETTING` DISABLE KEYS */;
INSERT INTO `SEC_USER_SETTING` VALUES ('007f23ab7e82e258a7219246114e9bd1','2018-12-30 21:42:59.955','admin','608859871b61424794c7dff348347f93','W','lineup$SiteCategory.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"filter\"> \n      <defaultFilter/>  \n      <groupBoxExpanded>true</groupBoxExpanded>  \n      <maxResults>50</maxResults> \n    </component>  \n    <component name=\"siteCategoriesTable\"> \n      <columns>\n        <columns id=\"name\" visible=\"true\"/>\n      </columns>\n      <groupProperties/>\n    </component> \n  </components> \n</settings>\n'),('043f51c5178889b86c2bb2d95656f615','2019-01-01 14:11:19.142','qwe','0a5104fda2a77c48af601cd45771b21f','W','appProperties','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings>\n  <components>\n    <component name=\"paramsTable\">\n      <columns sortProperty=\"name\" sortAscending=\"true\">\n        <columns id=\"name\" visible=\"true\"/>\n        <columns id=\"displayedCurrentValue\" visible=\"true\"/>\n        <columns id=\"updateTs\" visible=\"true\"/>\n        <columns id=\"updatedBy\" visible=\"true\"/>\n      </columns>\n    </component>\n    <component name=\"hintBox\"/>\n  </components>\n</settings>\n'),('045f95ca095e3476f3ae6e24deb397f4','2019-09-24 22:07:32.429','admin','608859871b61424794c7dff348347f93','W','appProperties','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings>\n  <components>\n    <component name=\"paramsTable\">\n      <columns sortProperty=\"name\" sortAscending=\"true\">\n        <columns id=\"name\" visible=\"true\"/>\n        <columns id=\"displayedCurrentValue\" visible=\"true\"/>\n        <columns id=\"updateTs\" visible=\"true\"/>\n        <columns id=\"updatedBy\" visible=\"true\"/>\n      </columns>\n    </component>\n    <component name=\"hintBox\"/>\n  </components>\n</settings>\n'),('188f5ea48051a2f24d6b8b927a4b9fbb','2018-12-30 22:19:49.775','admin','608859871b61424794c7dff348347f93','W','sec$User.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"genericFilter\"> \n      <defaultFilter/>  \n      <groupBoxExpanded>true</groupBoxExpanded>  \n      <maxResults>50</maxResults> \n    </component>  \n    <component name=\"usersTable\" textSelection=\"false\" presentation=\"\"> \n      <columns> \n        <columns id=\"login\" visible=\"true\"/>  \n        <columns id=\"name\" visible=\"true\"/>  \n        <columns id=\"position\" visible=\"true\"/>  \n        <columns id=\"group\" visible=\"true\"/>  \n        <columns id=\"email\" visible=\"true\"/>  \n        <columns id=\"timeZone\" visible=\"true\"/>  \n        <columns id=\"active\" visible=\"true\"/>  \n        <columns id=\"changePasswordAtNextLogon\" visible=\"true\"/> \n      </columns>  \n      <groupProperties/> \n    </component> \n  </components> \n</settings>\n'),('18e61519f2997b177e8a0ab178710602','2019-09-24 22:07:31.134','admin','608859871b61424794c7dff348347f93','W','sys$SendingMessage.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings>\n  <components>\n    <component name=\"genericFilter\">\n      <defaultFilter/>\n    </component>\n    <component name=\"table\"/>\n  </components>\n</settings>\n'),('19408166eac0b9709765edfc17e633fd','2019-05-07 14:34:34.538','WindMW','4aaf5eaaff665c9b229b9016eb961834','W','lineup$FavoriteTrip.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"favoriteTripsTable\" presentation=\"\"> \n      <columns sortProperty=\"destination\" sortAscending=\"true\"> \n        <columns id=\"startSite\" visible=\"true\"/>  \n        <columns id=\"destination\" visible=\"true\"/>  \n        <columns id=\"emailNotification\" visible=\"true\"/>  \n        <columns id=\"sendSummery\" visible=\"true\"/> \n      </columns> \n    </component> \n  </components> \n</settings>\n'),('21eec522ea1ed30f1fd491b89f87e4a9','2018-12-30 22:39:56.625','qwe','0a5104fda2a77c48af601cd45771b21f','W','lineup$Site.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"filter\"> \n      <defaultFilter/>  \n      <groupBoxExpanded>true</groupBoxExpanded>  \n      <maxResults>50</maxResults> \n    </component>  \n    <component name=\"sitesTable\"> \n      <columns>\n        <columns id=\"siteName\" visible=\"true\"/>\n        <columns id=\"latitude\" visible=\"true\"/>\n        <columns id=\"longitude\" visible=\"true\"/>\n        <columns id=\"itemDesignation\" visible=\"true\"/>\n        <columns id=\"siteCategory\" visible=\"true\"/>\n      </columns>\n      <groupProperties/>\n    </component> \n  </components> \n</settings>\n'),('25145275a5c25612abcba4e70314d59b','2018-12-30 22:39:56.596','qwe','0a5104fda2a77c48af601cd45771b21f','W','lineup$ModeOfTransfer.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"filter\"> \n      <defaultFilter/>  \n      <groupBoxExpanded>true</groupBoxExpanded>  \n      <maxResults>50</maxResults> \n    </component>  \n    <component name=\"modeOfTransfersTable\"> \n      <columns>\n        <columns id=\"mode\" visible=\"true\"/>\n      </columns>\n      <groupProperties/>\n    </component> \n  </components> \n</settings>\n'),('2ebc5fccf25b0d0f3e5b5c411048a70e','2019-02-25 19:52:53.256','qwe','0a5104fda2a77c48af601cd45771b21f','W','sec$User.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings>\n  <components>\n    <component name=\"genericFilter\">\n      <defaultFilter/>\n      <groupBoxExpanded>true</groupBoxExpanded>\n      <maxResults>50</maxResults>\n    </component>\n    <component name=\"usersTable\" textSelection=\"false\" presentation=\"\">\n      <columns>\n        <columns id=\"login\" visible=\"true\"/>\n        <columns id=\"name\" visible=\"true\"/>\n        <columns id=\"position\" visible=\"true\"/>\n        <columns id=\"group\" visible=\"true\"/>\n        <columns id=\"email\" visible=\"true\"/>\n        <columns id=\"timeZone\" visible=\"true\"/>\n        <columns id=\"active\" visible=\"true\"/>\n        <columns id=\"changePasswordAtNextLogon\" visible=\"true\"/>\n      </columns>\n      <groupProperties/>\n    </component>\n  </components>\n</settings>\n'),('3d41681da2724df92839c592f0e98f22','2018-12-30 22:21:37.385','qwe','0a5104fda2a77c48af601cd45771b21f','W','sec$Group.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings>\n  <components>\n    <component name=\"split\">\n      <position value=\"30.0\" unit=\"%\"/>\n    </component>\n    <component name=\"filterWithoutId\">\n      <defaultFilter/>\n      <groupBoxExpanded/>\n      <maxResults>50</maxResults>\n    </component>\n    <component name=\"usersTable\">\n      <columns>\n        <columns id=\"name\" visible=\"true\"/>\n        <columns id=\"login\" visible=\"true\"/>\n      </columns>\n    </component>\n    <component name=\"constraintsTable\">\n      <columns>\n        <columns id=\"entityName\" visible=\"true\"/>\n        <columns id=\"isActive\" visible=\"true\"/>\n        <columns id=\"operationType\" visible=\"true\"/>\n        <columns id=\"joinClause\" width=\"200\" visible=\"true\"/>\n        <columns id=\"whereClause\" width=\"200\" visible=\"true\"/>\n        <columns id=\"groovyScript\" width=\"200\" visible=\"true\"/>\n      </columns>\n    </component>\n    <component name=\"attributesTable\">\n      <columns>\n        <columns id=\"name\" visible=\"true\"/>\n        <columns id=\"datatypeCaption\" visible=\"true\"/>\n        <columns id=\"stringValue\" visible=\"true\"/>\n      </columns>\n    </component>\n  </components>\n</settings>\n'),('3d7e0019561768b2ff10abff3b6946bc','2019-02-24 21:37:16.828','admin','608859871b61424794c7dff348347f93','W','sec$Role.edit','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"roleGroupBox\"> \n      <groupBox expanded=\"true\"/>\n    </component>  \n    <component name=\"description\"/>  \n    <component name=\"screensTabFrame.screenPermissionsTree\"> \n      <columns>\n        <columns id=\"caption\" visible=\"true\"/>\n        <columns id=\"permissionVariant\" visible=\"true\"/>\n      </columns>\n    </component>  \n    <component name=\"screensTabFrame.screensEditPane\"> \n      <groupBox expanded=\"true\"/>\n    </component>  \n    <component name=\"entitiesTabFrame.contentSplit\"> \n      <position value=\"80.0\" unit=\"%\"/> \n    </component>  \n    <component name=\"entitiesTabFrame.entityPermissionsTable\"> \n      <columns> \n        <columns id=\"localName\" width=\"300\" visible=\"true\"/>  \n        <columns id=\"entityMetaClassName\" width=\"300\" visible=\"true\"/>  \n        <columns id=\"createPermissionVariant\" visible=\"true\"/>  \n        <columns id=\"readPermissionVariant\" visible=\"true\"/>  \n        <columns id=\"updatePermissionVariant\" visible=\"true\"/>  \n        <columns id=\"deletePermissionVariant\" visible=\"true\"/> \n      </columns> \n    </component>  \n    <component name=\"entitiesTabFrame.editPane\"> \n      <groupBox expanded=\"true\"/> \n    </component>  \n    <component name=\"attributesTabFrame.contentSplit\"> \n      <position value=\"60.0\" unit=\"%\"/> \n    </component>  \n    <component name=\"attributesTabFrame.propertyPermissionsTable\"> \n      <columns> \n        <columns id=\"localName\" width=\"300\" visible=\"true\"/>  \n        <columns id=\"entityMetaClassName\" width=\"300\" visible=\"true\"/>  \n        <columns id=\"permissionsInfo\" visible=\"true\"/> \n      </columns> \n    </component>  \n    <component name=\"attributesTabFrame.editPane\"> \n      <groupBox expanded=\"true\"/> \n    </component>  \n    <component name=\"uiTabFrame.contentSplit\"> \n      <position value=\"80.0\" unit=\"%\"/> \n    </component>  \n    <component name=\"uiTabFrame.uiPermissionsTable\"> \n      <columns> \n        <columns id=\"screen\" visible=\"true\"/>  \n        <columns id=\"component\" visible=\"true\"/>  \n        <columns id=\"permissionVariant\" visible=\"true\"/> \n      </columns>  \n      <groupProperties> \n        <property id=\"screen\"/> \n      </groupProperties> \n    </component>  \n    <component name=\"uiTabFrame.editPane\"> \n      <groupBox expanded=\"true\"/> \n    </component>  \n    <component name=\"specificTabFrame.contentSplit\"> \n      <position value=\"80.0\" unit=\"%\"/> \n    </component>  \n    <component name=\"specificTabFrame.specificPermissionsTree\"> \n      <columns> \n        <columns id=\"caption\" visible=\"true\"/>  \n        <columns id=\"permissionVariant\" visible=\"true\"/> \n      </columns> \n    </component>  \n    <component name=\"specificTabFrame.specificEditPane\"> \n      <groupBox expanded=\"true\"/> \n    </component> \n  </components> \n</settings>\n'),('3fde8df6b7b3cd409cc02470368d65c6','2018-12-30 21:31:32.811','admin','608859871b61424794c7dff348347f93','W','lineup$Site.tmp.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"filter\"> \n      <defaultFilter/>  \n      <groupBoxExpanded>true</groupBoxExpanded>  \n      <maxResults>50</maxResults> \n    </component>  \n    <component name=\"sitesTable\"> \n      <columns>\n        <columns id=\"siteName\" visible=\"true\"/>\n        <columns id=\"latitude\" visible=\"true\"/>\n        <columns id=\"longitude\" visible=\"true\"/>\n        <columns id=\"itemDesignation\" visible=\"true\"/>\n        <columns id=\"shortItemDesignation\" visible=\"true\"/>\n        <columns id=\"category\" visible=\"true\"/>\n      </columns>\n      <groupProperties/>\n    </component> \n  </components> \n</settings>\n'),('41b1d7ed62d205fb594cde5074f2a6cf','2018-12-30 22:19:49.756','admin','608859871b61424794c7dff348347f93','W','lineup$ModeOfTransfer.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings>\n  <components>\n    <component name=\"filter\">\n      <defaultFilter/>\n      <groupBoxExpanded>true</groupBoxExpanded>\n      <maxResults>50</maxResults>\n    </component>\n    <component name=\"modeOfTransfersTable\">\n      <columns>\n        <columns id=\"mode\" visible=\"true\"/>\n      </columns>\n      <groupProperties/>\n    </component>\n  </components>\n</settings>\n'),('528313d57d83ef417045f613ad7aa020','2019-02-25 20:07:17.026','admin','608859871b61424794c7dff348347f93','W','sec$ScreenHistory.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"historyTable\"> \n      <columns>\n        <columns id=\"caption\" visible=\"true\"/>\n        <columns id=\"createTs\" visible=\"true\"/>\n      </columns>\n    </component> \n  </components> \n</settings>\n'),('641188c0eb9c62d7af8d2df190f7e5bb','2019-02-25 20:04:46.047','dent','7c0416f6b4dacb7b64e53a0c15bd40de','W','userDefaultScreen',NULL),('6a04a1b542a01f9ae910f4fec3888e44','2018-12-30 21:43:39.163','admin','608859871b61424794c7dff348347f93','W','lineup$Site.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"filter\"> \n      <defaultFilter/>  \n      <groupBoxExpanded>true</groupBoxExpanded>  \n      <maxResults>50</maxResults> \n    </component>  \n    <component name=\"sitesTable\"> \n      <groupProperties/>  \n      <columns sortProperty=\"\" sortAscending=\"true\">\n        <columns id=\"siteName\" visible=\"true\"/>\n        <columns id=\"siteCategory\" visible=\"true\"/>\n        <columns id=\"latitude\" visible=\"true\"/>\n        <columns id=\"longitude\" visible=\"true\"/>\n        <columns id=\"itemDesignation\" visible=\"true\"/>\n      </columns>\n    </component> \n  </components> \n</settings>\n'),('6a0db65282d50525b4c0c3f22d7822ac','2019-09-24 22:07:29.984','admin','608859871b61424794c7dff348347f93','W','serverLog','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings>\n  <components>\n    <component name=\"logFieldBox\"/>\n  </components>\n  <window/>\n</settings>\n'),('748a49e6af54f22512f9a99181aeb0d2','2019-02-25 19:48:26.507','qwe','0a5104fda2a77c48af601cd45771b21f','W','lineup$TravelOption.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"travelOptionsTable\"> \n      <columns>\n        <columns id=\"client\" visible=\"true\"/>\n        <columns id=\"transfer.client\" visible=\"true\"/>\n        <columns id=\"transferDate\" visible=\"true\"/>\n        <columns id=\"transferTakeOff\" visible=\"true\"/>\n        <columns id=\"favoriteTrip\" visible=\"true\"/>\n        <columns id=\"availableSeats\" visible=\"true\"/>\n        <columns id=\"bookedSeats\" visible=\"true\"/>\n        <columns id=\"status\" visible=\"true\"/>\n        <columns id=\"bookTransfer\" visible=\"true\"/>\n      </columns>\n      <groupProperties/>\n    </component> \n  </components> \n</settings>\n'),('7e87d4158a5a2fa9a9c20e4b0b64adf8','2018-12-30 22:23:40.817','admin','608859871b61424794c7dff348347f93','W','sec$Role.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"filterWithoutId\"> \n      <defaultFilter/>  \n      <groupBoxExpanded>true</groupBoxExpanded>  \n      <maxResults>50</maxResults> \n    </component>  \n    <component name=\"rolesTable\"> \n      <columns>\n        <columns id=\"name\" visible=\"true\"/>\n        <columns id=\"locName\" visible=\"true\"/>\n        <columns id=\"description\" visible=\"true\"/>\n        <columns id=\"defaultRole\" visible=\"true\"/>\n        <columns id=\"type\" visible=\"true\"/>\n      </columns>\n    </component> \n  </components> \n</settings>\n'),('8c3ef64806f3725a948d8157df3f65ee','2019-02-25 19:48:26.537','qwe','0a5104fda2a77c48af601cd45771b21f','W','lineup$FavoriteTrip.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings>\n  <components>\n    <component name=\"filter\">\n      <defaultFilter/>\n      <groupBoxExpanded>true</groupBoxExpanded>\n      <maxResults>50</maxResults>\n    </component>\n    <component name=\"favoriteTripsTable\">\n      <columns>\n        <columns id=\"startSite\" visible=\"true\"/>\n        <columns id=\"destination\" visible=\"true\"/>\n        <columns id=\"emailNotification\" visible=\"true\"/>\n        <columns id=\"sendSummery\" visible=\"true\"/>\n      </columns>\n      <groupProperties/>\n    </component>\n  </components>\n</settings>\n'),('96a0ae4c1d89912e80dde1ec0d679243','2018-12-30 22:19:49.766','admin','608859871b61424794c7dff348347f93','W','lineup$CraftType.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings>\n  <components>\n    <component name=\"filter\">\n      <defaultFilter/>\n      <groupBoxExpanded>true</groupBoxExpanded>\n      <maxResults>50</maxResults>\n    </component>\n    <component name=\"craftTypesTable\">\n      <columns>\n        <columns id=\"name\" visible=\"true\"/>\n        <columns id=\"seats\" visible=\"true\"/>\n        <columns id=\"maxRange\" visible=\"true\"/>\n        <columns id=\"payloadOutbound\" visible=\"true\"/>\n        <columns id=\"payloadInbound\" visible=\"true\"/>\n        <columns id=\"modeOfTransfer\" visible=\"true\"/>\n      </columns>\n      <groupProperties/>\n    </component>\n  </components>\n</settings>\n'),('97b4f5cfc26796e77b56def645aecebd','2019-09-20 22:43:09.196','baer','7c0416f6b4dacb7b64e53a0c15bd40de','W','lineup$TravelOption.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings>\n  <components>\n    <component name=\"travelOptionsTable\">\n      <columns sortProperty=\"\" sortAscending=\"true\">\n        <columns id=\"client\" width=\"55\" visible=\"false\"/>\n        <columns id=\"transfer.client\" width=\"55\" visible=\"false\"/>\n        <columns id=\"transferDate\" width=\"88\" visible=\"true\"/>\n        <columns id=\"transferTakeOff\" width=\"72\" visible=\"true\"/>\n        <columns id=\"favoriteTrip\" width=\"200\" visible=\"true\"/>\n        <columns id=\"availableSeats\" width=\"78\" visible=\"true\"/>\n        <columns id=\"bookedSeats\" width=\"66\" visible=\"true\"/>\n        <columns id=\"status\" width=\"118\" visible=\"true\"/>\n        <columns id=\"bookTransfer\" width=\"155\" visible=\"true\"/>\n      </columns>\n    </component>\n  </components>\n</settings>\n'),('9f1bfa8d940a4ad609ca7a3b21b8c3ed','2018-12-30 21:43:15.323','admin','608859871b61424794c7dff348347f93','W','lineup$Airport.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"filter\"> \n      <defaultFilter/>  \n      <groupBoxExpanded>true</groupBoxExpanded>  \n      <maxResults>50</maxResults> \n    </component>  \n    <component name=\"airportsTable\"> \n      <columns>\n        <columns id=\"siteName\" visible=\"true\"/>\n        <columns id=\"latitude\" visible=\"true\"/>\n        <columns id=\"longitude\" visible=\"true\"/>\n        <columns id=\"itemDesignation\" visible=\"true\"/>\n        <columns id=\"icaoCode\" visible=\"true\"/>\n        <columns id=\"iataCode\" visible=\"true\"/>\n      </columns>\n      <groupProperties/>\n    </component> \n  </components> \n</settings>\n'),('a087adf0eaa135ea4468634785e1aedc','2018-12-30 22:39:56.608','qwe','0a5104fda2a77c48af601cd45771b21f','W','lineup$CraftType.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"filter\"> \n      <defaultFilter/>  \n      <groupBoxExpanded>true</groupBoxExpanded>  \n      <maxResults>50</maxResults> \n    </component>  \n    <component name=\"craftTypesTable\"> \n      <columns>\n        <columns id=\"name\" visible=\"true\"/>\n        <columns id=\"seats\" visible=\"true\"/>\n        <columns id=\"maxRange\" visible=\"true\"/>\n        <columns id=\"payloadOutbound\" visible=\"true\"/>\n        <columns id=\"payloadInbound\" visible=\"true\"/>\n        <columns id=\"modeOfTransfer\" visible=\"true\"/>\n      </columns>\n      <groupProperties/>\n    </component> \n  </components> \n</settings>\n'),('a2147d81725201cea617d51cb4119b20','2018-12-30 22:38:37.872','qwe','0a5104fda2a77c48af601cd45771b21f','W','lineup$SiteCategory.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"filter\"> \n      <defaultFilter/>  \n      <groupBoxExpanded>true</groupBoxExpanded>  \n      <maxResults>50</maxResults> \n    </component>  \n    <component name=\"siteCategoriesTable\"> \n      <columns>\n        <columns id=\"categoryName\" visible=\"true\"/>\n      </columns>\n      <groupProperties/>\n    </component> \n  </components> \n</settings>\n'),('ab1488bff9f9ed7d15b08fd5eb72493f','2019-02-24 21:40:43.398','stark','2c537f196039a63ed57fa46558dbb547','W','lineup$FavoriteTrip.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"filter\"> \n      <defaultFilter/>  \n      <groupBoxExpanded>true</groupBoxExpanded>  \n      <maxResults>50</maxResults> \n    </component>  \n    <component name=\"favoriteTripsTable\" presentation=\"\"> \n      <groupProperties/>  \n      <columns sortProperty=\"\" sortAscending=\"true\">\n        <columns id=\"startSite\" width=\"171\" visible=\"true\"/>\n        <columns id=\"destination\" width=\"176\" visible=\"true\"/>\n        <columns id=\"emailNotification\" width=\"149\" visible=\"true\"/>\n        <columns id=\"sendSummery\" width=\"409\" visible=\"true\"/>\n      </columns>\n    </component> \n  </components> \n</settings>\n'),('b4f0d1b70c1c2bbba8c61d66fb8ddab5','2019-02-25 19:47:26.019','stark','2c537f196039a63ed57fa46558dbb547','W','lineup$TravelOption.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"travelOptionsTable\"> \n      <groupProperties/>  \n      <columns sortProperty=\"\" sortAscending=\"true\">\n        <columns id=\"client\" visible=\"false\"/>\n        <columns id=\"transfer.client\" visible=\"false\"/>\n        <columns id=\"transferDate\" width=\"101\" visible=\"true\"/>\n        <columns id=\"transferTakeOff\" width=\"82\" visible=\"true\"/>\n        <columns id=\"favoriteTrip\" width=\"217\" visible=\"true\"/>\n        <columns id=\"availableSeats\" width=\"126\" visible=\"true\"/>\n        <columns id=\"bookedSeats\" width=\"108\" visible=\"true\"/>\n        <columns id=\"status\" width=\"135\" visible=\"true\"/>\n        <columns id=\"bookTransfer\" visible=\"false\"/>\n      </columns>\n    </component> \n  </components> \n</settings>\n'),('b7c2181c3561cfac728653de18fe08fe','2019-02-24 21:32:33.374','qwe','0a5104fda2a77c48af601cd45771b21f','W','sec$Role.edit','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings>\n  <components>\n    <component name=\"roleGroupBox\">\n      <groupBox expanded=\"true\"/>\n    </component>\n    <component name=\"description\"/>\n    <component name=\"screensTabFrame.screenPermissionsTree\">\n      <columns>\n        <columns id=\"caption\" visible=\"true\"/>\n        <columns id=\"permissionVariant\" visible=\"true\"/>\n      </columns>\n    </component>\n    <component name=\"screensTabFrame.screensEditPane\">\n      <groupBox expanded=\"true\"/>\n    </component>\n  </components>\n</settings>\n'),('be311d65633e5d9341d2e3c6dd7c203b','2019-02-25 20:04:46.022','dent','7c0416f6b4dacb7b64e53a0c15bd40de','W','appWindowMode','TABBED'),('bfa51c19df557827698590b2c0dbc94a','2019-09-20 21:33:03.154','admin','608859871b61424794c7dff348347f93','W','entityInspector.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings>\n  <components>\n    <component name=\"filter\">\n      <defaultFilter/>\n    </component>\n  </components>\n</settings>\n'),('c293c102bc53668b5d45252723f6a80f','2018-12-30 22:21:37.366','qwe','0a5104fda2a77c48af601cd45771b21f','W','lineup$Airport.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"filter\"> \n      <defaultFilter/>  \n      <groupBoxExpanded>true</groupBoxExpanded>  \n      <maxResults>50</maxResults> \n    </component>  \n    <component name=\"airportsTable\"> \n      <columns>\n        <columns id=\"siteName\" visible=\"true\"/>\n        <columns id=\"siteCategory\" visible=\"true\"/>\n        <columns id=\"latitude\" visible=\"true\"/>\n        <columns id=\"longitude\" visible=\"true\"/>\n        <columns id=\"iataCode\" visible=\"true\"/>\n        <columns id=\"icaoCode\" visible=\"true\"/>\n      </columns>\n      <groupProperties/>\n    </component> \n  </components> \n</settings>\n'),('cb9cf07002088f168d47812076ec1f30','2018-12-30 21:41:04.386','admin','608859871b61424794c7dff348347f93','W','lineup$CrewChange.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings>\n  <components>\n    <component name=\"filter\">\n      <defaultFilter/>\n      <groupBoxExpanded>true</groupBoxExpanded>\n      <maxResults>50</maxResults>\n    </component>\n    <component name=\"crewChangesTable\">\n      <columns>\n        <columns id=\"client\" visible=\"true\"/>\n        <columns id=\"startDate\" visible=\"true\"/>\n      </columns>\n      <groupProperties/>\n    </component>\n  </components>\n</settings>\n'),('cd8a96af1a3bcb78c1afb3b031a79b2d','2018-12-30 22:23:40.835','admin','608859871b61424794c7dff348347f93','W','sec$Group.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"split\"> \n      <position value=\"30.0\" unit=\"%\"/> \n    </component>  \n    <component name=\"filterWithoutId\"> \n      <defaultFilter/>  \n      <groupBoxExpanded/>  \n      <maxResults>50</maxResults> \n    </component>  \n    <component name=\"usersTable\"> \n      <columns>\n        <columns id=\"name\" visible=\"true\"/>\n        <columns id=\"login\" visible=\"true\"/>\n      </columns>\n    </component>  \n    <component name=\"constraintsTable\"> \n      <columns>\n        <columns id=\"entityName\" visible=\"true\"/>\n        <columns id=\"isActive\" visible=\"true\"/>\n        <columns id=\"operationType\" visible=\"true\"/>\n        <columns id=\"joinClause\" width=\"200\" visible=\"true\"/>\n        <columns id=\"whereClause\" width=\"200\" visible=\"true\"/>\n        <columns id=\"groovyScript\" width=\"200\" visible=\"true\"/>\n      </columns>\n    </component>  \n    <component name=\"attributesTable\"> \n      <columns>\n        <columns id=\"name\" visible=\"true\"/>\n        <columns id=\"datatypeCaption\" visible=\"true\"/>\n        <columns id=\"stringValue\" visible=\"true\"/>\n      </columns>\n    </component> \n  </components> \n</settings>\n'),('d199d2fb83a5c30a16f1311e396a91f1','2019-05-07 14:27:03.021','admin','608859871b61424794c7dff348347f93','W','sys$Category.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings>\n  <components>\n    <component name=\"categoriesFilter\">\n      <defaultFilter/>\n    </component>\n    <component name=\"split\"/>\n    <component name=\"categoryTable\"/>\n    <component name=\"attributesTable\"/>\n  </components>\n</settings>\n'),('d94227edc747b1ef2f0e0724f526a9c8','2018-12-30 22:19:44.475','admin','608859871b61424794c7dff348347f93','W','sec$Role.lookup','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"filterWithoutId\"> \n      <defaultFilter/>  \n      <groupBoxExpanded>true</groupBoxExpanded>  \n      <maxResults>50</maxResults> \n    </component>  \n    <component name=\"rolesTable\"> \n      <columns>\n        <columns id=\"name\" visible=\"true\"/>\n        <columns id=\"locName\" visible=\"true\"/>\n        <columns id=\"description\" visible=\"true\"/>\n        <columns id=\"defaultRole\" visible=\"true\"/>\n        <columns id=\"type\" visible=\"true\"/>\n      </columns>\n    </component> \n  </components> \n</settings>\n'),('e0a9498056de518d8a84af86e4a23183','2018-12-30 22:21:37.375','qwe','0a5104fda2a77c48af601cd45771b21f','W','sec$Role.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"filterWithoutId\"> \n      <defaultFilter/>  \n      <groupBoxExpanded>true</groupBoxExpanded>  \n      <maxResults>50</maxResults> \n    </component>  \n    <component name=\"rolesTable\"> \n      <columns>\n        <columns id=\"name\" visible=\"true\"/>\n        <columns id=\"locName\" visible=\"true\"/>\n        <columns id=\"description\" visible=\"true\"/>\n        <columns id=\"defaultRole\" visible=\"true\"/>\n        <columns id=\"type\" visible=\"true\"/>\n      </columns>\n    </component> \n  </components> \n</settings>\n'),('e5126ccad86013f8fdfdb8f8b7e10d8e','2018-12-30 22:19:46.697','admin','608859871b61424794c7dff348347f93','W','sec$User.edit','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"propertiesBox\"> \n      <groupBox expanded=\"true\"/>\n    </component>  \n    <component name=\"split\"> \n      <position value=\"50.0\" unit=\"%\"/> \n    </component>  \n    <component name=\"rolesTable\"> \n      <columns>\n        <columns id=\"role.name\" visible=\"true\"/>\n        <columns id=\"role.locName\" visible=\"true\"/>\n      </columns>\n    </component>  \n    <component name=\"substTable\"> \n      <columns>\n        <columns id=\"substitutedUser.login\" visible=\"true\"/>\n        <columns id=\"substitutedUser.name\" visible=\"true\"/>\n        <columns id=\"startDate\" visible=\"true\"/>\n        <columns id=\"endDate\" visible=\"true\"/>\n      </columns>\n    </component> \n  </components> \n</settings>\n'),('ed0d4c7933480e7329579854be09b8af','2019-02-24 21:24:32.090','admin','608859871b61424794c7dff348347f93','W','lineup$FavoriteTrip.browse','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<settings> \n  <components> \n    <component name=\"filter\"> \n      <defaultFilter/>  \n      <groupBoxExpanded>true</groupBoxExpanded>  \n      <maxResults>50</maxResults> \n    </component>  \n    <component name=\"favoriteTripsTable\" presentation=\"\"> \n      <columns> \n        <columns id=\"startSite\" visible=\"true\"/>  \n        <columns id=\"destination\" visible=\"true\"/>  \n        <columns id=\"emailNotification\" visible=\"true\"/>  \n        <columns id=\"sendSummery\" visible=\"true\"/> \n      </columns>  \n      <groupProperties/> \n    </component> \n  </components> \n</settings>\n'),('f6b569a0a349498dfb8b0dc0f60bfb39','2019-02-25 20:04:46.011','dent','7c0416f6b4dacb7b64e53a0c15bd40de','W','appWindowTheme','hover');
/*!40000 ALTER TABLE `SEC_USER_SETTING` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-25 22:06:04
