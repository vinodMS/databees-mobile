-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 02, 2014 at 02:00 PM
-- Server version: 5.5.22
-- PHP Version: 5.3.10-1ubuntu3.9

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `databees`
--

-- --------------------------------------------------------

--
-- Table structure for table `apiary`
--

CREATE TABLE IF NOT EXISTS `apiary` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `geo_lat` double DEFAULT NULL,
  `geo_long` double DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `notes` text,
  `photo_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`,`user_id`),
  KEY `user_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `apiary`
--

INSERT INTO `apiary` (`id`, `user_id`, `geo_lat`, `geo_long`, `name`, `notes`, `photo_id`) VALUES
(0, 25, -33.457217736949, 25.335942171514, 'test the apiary', 'testing the alias', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `colony`
--

CREATE TABLE IF NOT EXISTS `colony` (
  `id` int(11) NOT NULL,
  `hive_id` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`hive_id`),
  KEY `parent_idx` (`parent_id`),
  KEY `hive_idx` (`hive_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='							';

-- --------------------------------------------------------

--
-- Table structure for table `hive`
--

CREATE TABLE IF NOT EXISTS `hive` (
  `id` int(11) NOT NULL,
  `apiary_id` int(11) NOT NULL,
  `type` enum('tp1','tp2') DEFAULT NULL,
  `number_of_supers` int(11) DEFAULT NULL,
  `notes` text,
  `photo_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`,`apiary_id`),
  KEY `apiary_idx` (`apiary_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `inspection`
--

CREATE TABLE IF NOT EXISTS `inspection` (
  `id` int(11) NOT NULL,
  `colony_id` int(11) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `activity` varchar(45) DEFAULT NULL,
  `pollen_in` tinyint(1) DEFAULT NULL,
  `temper` varchar(45) DEFAULT NULL,
  `eggs_present` tinyint(1) DEFAULT NULL,
  `all_stages_brood` tinyint(1) DEFAULT NULL,
  `sealed_brood` tinyint(1) DEFAULT NULL,
  `honey_in_supers` tinyint(1) DEFAULT NULL,
  `frames_of_stores` int(11) DEFAULT NULL,
  `queen_seen` tinyint(1) DEFAULT NULL,
  `origin` varchar(45) DEFAULT NULL,
  `queen_marked` tinyint(1) DEFAULT NULL,
  `queen_age` int(11) DEFAULT NULL,
  `pests_present` tinyint(1) DEFAULT NULL,
  `disease_present` tinyint(1) DEFAULT NULL,
  `disorders_present` tinyint(1) DEFAULT NULL,
  `notes` text,
  `photo_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `colony_idx` (`colony_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `task`
--

CREATE TABLE IF NOT EXISTS `task` (
  `id` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `static` tinyint(1) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `text` text,
  `hive_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `hive_idx` (`hive_id`),
  KEY `user_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `api_key` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `api_key_UNIQUE` (`api_key`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=51 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `email`, `password`, `api_key`) VALUES
(1, 'testicle', 'testy@asd.ad', 'p564km6l47m456', '34lkm53l76km47'),
(2, 'Test Test 123', 'test1@test2.test3', '0c31314e20af390ebcfd46da84ffa9cc', '84f74170e5a4d78ccc75f1f7af1ee6ff6878f532'),
(3, '{', '{', 'f88128a374f22fc4a5a438db1ae82e09', '4010b37cc558b5cd0fef3727c2a165a46161a94f'),
(5, 'Testers Group', 'test@isdl.databees.nl', '8f7f2c8e0139f1bcb298b9c8599a0602', 'a228dd3e0e5fcdc6f7f5a6ff286b1c55d2302755'),
(12, '1111', 'dummy@email.com', 'cd794d2f67067189b396ca7d191eb5f1', 'ee7bd3064dfad52a4941dd99bb01ff4ee67c5543'),
(13, 'qwert', 'qwert@email.com', 'e1cadb81d35df0ee438cbf4ce4920e82', '2d37d59f32e6f22ffc8ff1b1bc006bac31024bb7'),
(17, 'qwertyuiop123', 'qwertyuiop123@email.com', '78ca719336e690616c391632de5c0857', '7a7742d3cc3f3ac7269850c98c051779fcb3d61c'),
(21, 'a', 'a@email', '8ac762c3d8368d6eb2d9c7594e1edbeb', '0dfd0675ed09c0064ea52cecd7caf926d56153dc'),
(22, 'b', 'b@email', 'd4a3dd7c9184071f4a521d7eb8947b86', '2617b36806b62ff2ffceaf4d97a87371a01a9ae4'),
(23, 'c', 'c@email.com', 'f7269a38440caabc32c0fe7e4665d57e', '200c1879017e76e60a0a90ccd27eb7bcfb39e644'),
(25, 'John', 'john.bracciano@hotmail.gr', 'be9eb3b5d06671d7cfc9a848d3786f78', '7b8955095b4d063c15c2bcc95f3065061f3252d6'),
(26, 'h', 'h', '4a1ff32bc17648e445b5b25e87981e8a', 'b520ff6f4fe77b09e47f6017dc103ee8c13cec35'),
(30, 't', 't', '7b1669499309ab609b7324678a15f93b', '112c1405fe29cff797dbf410705af42fb8896496');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `apiary`
--
ALTER TABLE `apiary`
  ADD CONSTRAINT `belongs_to_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `colony`
--
ALTER TABLE `colony`
  ADD CONSTRAINT `has_parent` FOREIGN KEY (`parent_id`) REFERENCES `colony` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `belongs_to_hive` FOREIGN KEY (`hive_id`) REFERENCES `hive` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `hive`
--
ALTER TABLE `hive`
  ADD CONSTRAINT `belongs_to_apiary` FOREIGN KEY (`apiary_id`) REFERENCES `apiary` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `inspection`
--
ALTER TABLE `inspection`
  ADD CONSTRAINT `belongs_to_colony` FOREIGN KEY (`colony_id`) REFERENCES `colony` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `task`
--
ALTER TABLE `task`
  ADD CONSTRAINT `has_hive` FOREIGN KEY (`hive_id`) REFERENCES `hive` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `has_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
