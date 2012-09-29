/*
SQLyog Community Edition- MySQL GUI v5.25
Host - 5.0.45-community-nt : Database - auction
*********************************************************************
Server version : 5.0.45-community-nt
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

create database if not exists `auction`;

USE `auction`;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*Table structure for table `auction_user` */

DROP TABLE IF EXISTS `auction_user`;

CREATE TABLE `auction_user` (
  `user_id` int(11) NOT NULL auto_increment,
  `username` varchar(50) NOT NULL,
  `userpass` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY  (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `auction_user` */

insert  into `auction_user`(`user_id`,`username`,`userpass`,`email`) values (1,'tomcat','tomcat','spring_test@163.com'),(2,'mysql','mysql','spring_test@163.com');

/*Table structure for table `bid` */

DROP TABLE IF EXISTS `bid`;

CREATE TABLE `bid` (
  `bid_id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `bid_price` double NOT NULL,
  `bid_date` date NOT NULL,
  PRIMARY KEY  (`bid_id`),
  UNIQUE KEY `item_id` (`item_id`,`bid_price`),
  KEY `FK17CFD859ED8BD` (`item_id`),
  KEY `FK17CFD73B4197A` (`user_id`),
  CONSTRAINT `bid_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `auction_user` (`user_id`),
  CONSTRAINT `bid_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`),
  CONSTRAINT `FK17CFD73B4197A` FOREIGN KEY (`user_id`) REFERENCES `auction_user` (`user_id`),
  CONSTRAINT `FK17CFD859ED8BD` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `bid` */

insert  into `bid`(`bid_id`,`user_id`,`item_id`,`bid_price`,`bid_date`) values (1,2,1,250,'2006-07-09'),(2,1,3,25000,'2006-07-05');

/*Table structure for table `item` */

DROP TABLE IF EXISTS `item`;

CREATE TABLE `item` (
  `item_id` int(11) NOT NULL auto_increment,
  `item_name` varchar(255) NOT NULL,
  `item_remark` varchar(255) NOT NULL,
  `item_desc` varchar(255) default NULL,
  `kind_id` int(11) NOT NULL,
  `addtime` date NOT NULL,
  `endtime` date NOT NULL,
  `init_price` double NOT NULL,
  `max_price` double NOT NULL,
  `owner_id` int(11) NOT NULL,
  `winer_id` int(11) default NULL,
  `state_id` int(11) NOT NULL,
  PRIMARY KEY  (`item_id`),
  KEY `FK317B133726B497` (`state_id`),
  KEY `FK317B133A0CF2FC` (`winer_id`),
  KEY `FK317B13DD217D1D` (`kind_id`),
  KEY `FK317B13DF9AC992` (`owner_id`),
  CONSTRAINT `FK317B133726B497` FOREIGN KEY (`state_id`) REFERENCES `state` (`state_id`),
  CONSTRAINT `FK317B133A0CF2FC` FOREIGN KEY (`winer_id`) REFERENCES `auction_user` (`user_id`),
  CONSTRAINT `FK317B13DD217D1D` FOREIGN KEY (`kind_id`) REFERENCES `kind` (`kind_id`),
  CONSTRAINT `FK317B13DF9AC992` FOREIGN KEY (`owner_id`) REFERENCES `auction_user` (`user_id`),
  CONSTRAINT `item_ibfk_1` FOREIGN KEY (`kind_id`) REFERENCES `kind` (`kind_id`),
  CONSTRAINT `item_ibfk_2` FOREIGN KEY (`owner_id`) REFERENCES `auction_user` (`user_id`),
  CONSTRAINT `item_ibfk_3` FOREIGN KEY (`winer_id`) REFERENCES `auction_user` (`user_id`),
  CONSTRAINT `item_ibfk_4` FOREIGN KEY (`state_id`) REFERENCES `state` (`state_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `item` */

insert  into `item`(`item_id`,`item_name`,`item_remark`,`item_desc`,`kind_id`,`addtime`,`endtime`,`init_price`,`max_price`,`owner_id`,`winer_id`,`state_id`) values (1,'主板','老式主板','老主板，还可以用',1,'2006-07-06','2008-07-10',230,250,1,2,2),(2,'显卡','老式显卡','老显卡，还可以用',1,'2006-07-03','2008-07-05',210,210,2,NULL,3),(3,'老房子','老式房子','40年的老房子',2,'2006-07-03','2008-07-06',21000,25000,2,1,2),(4,'内存','用于笔记本KingStone','1G容量',1,'2008-07-20','2008-07-21',250,250,1,NULL,3),(5,'CPU','用于PC','Intel',1,'2008-07-20','2008-07-22',2000,2000,1,NULL,3);

/*Table structure for table `kind` */

DROP TABLE IF EXISTS `kind`;

CREATE TABLE `kind` (
  `kind_id` int(11) NOT NULL auto_increment,
  `kind_name` varchar(50) NOT NULL,
  `kind_desc` varchar(255) NOT NULL,
  PRIMARY KEY  (`kind_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `kind` */

insert  into `kind`(`kind_id`,`kind_name`,`kind_desc`) values (1,'电脑硬件','这里并不是很主流的产品，但价格绝对令你心动'),(2,'房产','提供非常稀缺的房源'),(3,'书','普通商品'),(4,'电器','热门商品'),(5,'首饰','贵重商品');

/*Table structure for table `state` */

DROP TABLE IF EXISTS `state`;

CREATE TABLE `state` (
  `state_id` int(11) NOT NULL auto_increment,
  `state_name` varchar(10) default NULL,
  PRIMARY KEY  (`state_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `state` */

insert  into `state`(`state_id`,`state_name`) values (1,'拍卖中'),(2,'拍卖成功'),(3,'流拍');

/*Table structure for table `tree_category` */

DROP TABLE IF EXISTS `tree_category`;

CREATE TABLE `tree_category` (
  `category_id` int(11) NOT NULL auto_increment,
  `parent_id` int(11) default '-1',
  `level` smallint(6) default NULL,
  `is_leaf` tinyint(1) default NULL,
  `category_title` varchar(100) default NULL,
  `category_name` varchar(100) default NULL,
  `category_code` varchar(100) default NULL,
  `category_type` varchar(30) default NULL,
  `image` varchar(255) default NULL,
  `status` varchar(20) default NULL,
  `creator` varchar(50) default NULL,
  `create_date` datetime default NULL,
  `modify_user` varchar(50) default NULL,
  `modify_date` datetime default NULL,
  `description` text,
  PRIMARY KEY  (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `tree_category` */

insert  into `tree_category`(`category_id`,`parent_id`,`level`,`is_leaf`,`category_title`,`category_name`,`category_code`,`category_type`,`image`,`status`,`creator`,`create_date`,`modify_user`,`modify_date`,`description`) values (1,-1,1,0,'root','root','root','product_type',NULL,'1',NULL,NULL,NULL,NULL,NULL),(2,1,2,0,'<input type=checkbox name=product_type1 id=product_type1/><b>product_type1</b>','product_type1','product_type1','product_type',NULL,'1',NULL,NULL,NULL,NULL,NULL),(3,1,2,0,'<input type=checkbox name=product_type2 id=product_type2 class=treeCheckBox/><b>product_type2</b>','product_type2','product_type2','product_type',NULL,'1',NULL,NULL,NULL,NULL,NULL),(4,3,3,0,'<input type=checkbox name=product_type21 id=product_type21/><b>product_type21</b>','product_type21','product_type21','product_type',NULL,'1',NULL,NULL,NULL,NULL,NULL),(5,2,3,0,'<input type=checkbox name=product_type11 id=product_type11/><b>product_type11</b>','product_type11','product_type11','product_type',NULL,'1',NULL,NULL,NULL,NULL,NULL),(6,5,4,1,'<input type=checkbox name=product_type111 id=product_type111/><b>product_type111</b>','product_type111','product_type111','product_type',NULL,'1',NULL,NULL,NULL,NULL,NULL),(7,3,2,0,'<input type=checkbox name=product_type22 id=product_type22/><b>product_type22</b>','product_type22','product_type22','product_type',NULL,'1',NULL,NULL,NULL,NULL,NULL),(8,2,3,0,'<input type=checkbox name=product_type12 id=product_type12/><b>product_type12</b>','product_type12','product_type12','product_type',NULL,'1',NULL,NULL,NULL,NULL,NULL),(9,4,4,1,'<input type=checkbox name=product_type211 id=product_type211/><b>product_type211</b>','product_type211','product_type211','product_type',NULL,'1',NULL,NULL,NULL,NULL,NULL),(10,7,4,1,'<input type=checkbox name=product_type221 id=product_type221/><b>product_type221</b>','product_type221','product_type221','product_type',NULL,'1',NULL,NULL,NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
