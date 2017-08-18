-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 03, 2017 at 07:10 PM
-- Server version: 5.5.54-0ubuntu0.14.04.1
-- PHP Version: 5.6.30-10+deb.sury.org~trusty+2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `baojili`
--

-- --------------------------------------------------------

--
-- Table structure for table `t_item`
--

CREATE TABLE IF NOT EXISTS `t_item` (
  `ItemID` int(11) NOT NULL AUTO_INCREMENT,
  `Type` enum('Produksi','Pasokan','','') NOT NULL,
  `Code` varchar(15) NOT NULL,
  `Size` varchar(5) NOT NULL,
  `Price` decimal(10,2) NOT NULL,
  `Cost` decimal(10,2) NOT NULL,
  `Photo` varchar(255) NOT NULL,
  PRIMARY KEY (`ItemID`),
  KEY `ItemID` (`ItemID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `t_item_in`
--

CREATE TABLE IF NOT EXISTS `t_item_in` (
  `ItemInID` int(11) NOT NULL AUTO_INCREMENT,
  `UserID` varchar(10) NOT NULL,
  `StockID` int(11) NOT NULL,
  `InType` enum('Penambahan','Pemindahan','','') NOT NULL,
  `Quantity` int(10) unsigned NOT NULL,
  `Datetime` datetime NOT NULL,
  PRIMARY KEY (`ItemInID`),
  KEY `FK_USER_ID` (`UserID`),
  KEY `FK_STOCK_ID` (`StockID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `t_item_out`
--

CREATE TABLE IF NOT EXISTS `t_item_out` (
  `ItemOutID` int(11) NOT NULL AUTO_INCREMENT,
  `UserID` varchar(10) NOT NULL,
  `StockID` int(11) NOT NULL,
  `NextStockID` enum('Pemindahan') NOT NULL,
  `OutType` enum('Terjual','Cacat','Pemindahan','') NOT NULL,
  `Quantity` int(10) unsigned NOT NULL,
  `Datetime` datetime NOT NULL,
  PRIMARY KEY (`ItemOutID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `t_location`
--

CREATE TABLE IF NOT EXISTS `t_location` (
  `LocationID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(15) NOT NULL,
  `Type` enum('Pabrik','Gudang','Toko','Pemasok') NOT NULL,
  `Address` varchar(40) NOT NULL,
  `Latitude` decimal(8,6) NOT NULL,
  `Longitude` decimal(9,6) NOT NULL,
  PRIMARY KEY (`LocationID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `t_stock`
--

CREATE TABLE IF NOT EXISTS `t_stock` (
  `StockID` int(11) NOT NULL AUTO_INCREMENT,
  `ItemID` int(11) NOT NULL,
  `LocationID` int(11) NOT NULL,
  `Stock` int(10) unsigned NOT NULL,
  PRIMARY KEY (`StockID`),
  KEY `FK_ITEM_ID` (`ItemID`),
  KEY `FK_LOCATION_ID` (`LocationID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `t_user`
--

CREATE TABLE IF NOT EXISTS `t_user` (
  `UserID` varchar(10) NOT NULL,
  `Name` varchar(15) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `Password` varchar(10) NOT NULL,
  `Role` enum('Manajer','Karyawan','','') NOT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

--
-- Dumping data for table `t_user`
--

INSERT INTO `t_user` (`UserID`, `Name`, `Email`, `Password`, `Role`) VALUES
('ahmad', 'Ahmad', 'ahmad@gmail.com', '123456', 'Manajer'),
('bayu', 'Bayu', 'bayu@gmail.com', '123456', 'Karyawan'),
('dekikurnia', 'Deki Kurnia', 'dekikurnia@gmail.com', 'madrasah', 'Manajer');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `t_item_in`
--
ALTER TABLE `t_item_in`
  ADD CONSTRAINT `FK_STOCK_ID` FOREIGN KEY (`StockID`) REFERENCES `t_stock` (`StockID`),
  ADD CONSTRAINT `FK_USER_ID` FOREIGN KEY (`UserID`) REFERENCES `t_user` (`UserID`);

--
-- Constraints for table `t_stock`
--
ALTER TABLE `t_stock`
  ADD CONSTRAINT `FK_ITEM_ID` FOREIGN KEY (`ItemID`) REFERENCES `t_item` (`ItemID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_LOCATION_ID` FOREIGN KEY (`LocationID`) REFERENCES `t_location` (`LocationID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
