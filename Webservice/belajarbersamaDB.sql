/*
SQLyog Ultimate v8.55 
MySQL - 5.5.5-10.1.38-MariaDB : Database - proyek
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`proyek` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `proyek`;

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `ID` double NOT NULL AUTO_INCREMENT,
  `username` blob,
  `email` varchar(300) DEFAULT NULL,
  `password` varchar(300) DEFAULT NULL,
  `mobile` blob,
  `gender` blob,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `users` */

insert  into `users`(`ID`,`username`,`email`,`password`,`mobile`,`gender`) values (1,'novencus','saya@gmail.com','1234567','12345654323456','Laki laki'),(2,'ginanjar','saya12@gmail.com','12345678','5678976543456','Laki laki'),(3,'yogi','yogi@gmail.com','sukasuka','1234567654','Laki laki'),(4,'lestari','lestari@gmail.com','lestari123','76556789','Perempuan'),(5,'noname','noname@gmail.com','suka2saua','987656789','Laki laki');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
