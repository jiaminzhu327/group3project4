-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 10, 2018 at 01:06 AM
-- Server version: 5.7.23
-- PHP Version: 7.2.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `COMP585`
--

-- --------------------------------------------------------

--
-- Table structure for table `Authorization`
--

CREATE TABLE `Authorization` (
  `UserID` int(16) NOT NULL,
  `Password` varchar(40) NOT NULL,
  `Date_Created` date NOT NULL,
  `Date_Modified` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Friends`
--

CREATE TABLE `Friends` (
  `UserID` int(16) DEFAULT NULL,
  `FriendID` int(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Posts`
--

CREATE TABLE `Posts` (
  `UserID` int(16) NOT NULL,
  `Post` text NOT NULL,
  `Date_Posted` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
  `UserID` int(16) NOT NULL,
  `Username` varchar(40) NOT NULL,
  `Date_Created` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `UserInfo`
--

CREATE TABLE `UserInfo` (
  `UserID` int(16) NOT NULL,
  `FirstName` varchar(20) NOT NULL,
  `LastName` varchar(20) NOT NULL,
  `Age` smallint(2) NOT NULL,
  `Email` varchar(60) NOT NULL,
  `Picture` mediumblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Authorization`
--
ALTER TABLE `Authorization`
  ADD KEY `UserID` (`UserID`);

--
-- Indexes for table `Friends`
--
ALTER TABLE `Friends`
  ADD KEY `UserID` (`UserID`);

--
-- Indexes for table `Posts`
--
ALTER TABLE `Posts`
  ADD KEY `UserID` (`UserID`);

--
-- Indexes for table `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`UserID`);

--
-- Indexes for table `UserInfo`
--
ALTER TABLE `UserInfo`
  ADD KEY `UserID` (`UserID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `User`
--
ALTER TABLE `User`
  MODIFY `UserID` int(16) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1955898026;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Authorization`
--
ALTER TABLE `Authorization`
  ADD CONSTRAINT `authorization_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Friends`
--
ALTER TABLE `Friends`
  ADD CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Posts`
--
ALTER TABLE `Posts`
  ADD CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `UserInfo`
--
ALTER TABLE `UserInfo`
  ADD CONSTRAINT `userinfo_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;