-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 25, 2022 at 07:41 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `billingmanagement`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `accountID` int(10) NOT NULL,
  `accountNo` varchar(15) NOT NULL,
  `areaOffice` varchar(20) NOT NULL,
  `customerID` int(10) NOT NULL,
  `accountTypeID` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`accountID`, `accountNo`, `areaOffice`, `customerID`, `accountTypeID`) VALUES
(1, '4889012516', 'Kelaniya', 1, 2),
(2, '4469012791', 'Palanwaththa', 2, 3),
(3, '4889012917', 'Kelaniya', 1, 1),
(4, '5493110253', 'Gurudeniya', 4, 1);

-- --------------------------------------------------------

--
-- Table structure for table `accounttypes`
--

CREATE TABLE `accounttypes` (
  `accountTypeID` int(5) NOT NULL,
  `type` varchar(15) NOT NULL,
  `fixedCharge` decimal(7,2) NOT NULL,
  `chargeForBlock1` decimal(5,2) NOT NULL,
  `chargeForBlock2` decimal(5,2) NOT NULL,
  `chargeForBlock3` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `accounttypes`
--

INSERT INTO `accounttypes` (`accountTypeID`, `type`, `fixedCharge`, `chargeForBlock1`, `chargeForBlock2`, `chargeForBlock3`) VALUES
(1, 'Domestic', '60.00', '7.85', '7.85', '10.00'),
(2, 'Industry', '600.00', '10.80', '0.00', '0.00'),
(3, 'Business', '600.00', '18.80', '0.00', '0.00');

-- --------------------------------------------------------

--
-- Table structure for table `bills`
--

CREATE TABLE `bills` (
  `billID` int(20) NOT NULL,
  `accountID` int(10) NOT NULL,
  `accountTypeID` int(10) NOT NULL,
  `date` date NOT NULL,
  `units` int(5) NOT NULL,
  `chargeForUnits` decimal(10,2) NOT NULL,
  `previousBalance` decimal(6,2) NOT NULL,
  `totalAmount` decimal(10,2) NOT NULL,
  `paymentStatus` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bills`
--

INSERT INTO `bills` (`billID`, `accountID`, `accountTypeID`, `date`, `units`, `chargeForUnits`, `previousBalance`, `totalAmount`, `paymentStatus`) VALUES
(1, 1, 2, '2022-02-28', 289, '3121.20', '3685.47', '7406.67', 'paid'),
(2, 2, 3, '2022-02-28', 250, '4700.00', '0.00', '5300.00', 'paid'),
(3, 3, 1, '2022-02-28', 61, '223.00', '0.00', '283.00', 'unpaid'),
(4, 4, 1, '2022-02-28', 66, '531.00', '0.00', '591.00', 'paid'),
(5, 1, 2, '2022-03-29', 236, '2548.80', '0.00', '3148.80', 'paid'),
(6, 2, 3, '2022-03-29', 350, '6580.00', '0.00', '7180.00', 'paid'),
(7, 3, 1, '2022-03-29', 66, '531.00', '283.00', '814.00', 'paid'),
(8, 4, 1, '2022-03-29', 60, '471.00', '0.00', '531.00', 'unpaid');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `customerID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`customerID`) VALUES
(1),
(2),
(3),
(4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`accountID`),
  ADD KEY `acctype_fk` (`accountTypeID`),
  ADD KEY `cus_fk` (`customerID`);

--
-- Indexes for table `accounttypes`
--
ALTER TABLE `accounttypes`
  ADD PRIMARY KEY (`accountTypeID`);

--
-- Indexes for table `bills`
--
ALTER TABLE `bills`
  ADD PRIMARY KEY (`billID`),
  ADD KEY `acc_fk` (`accountID`),
  ADD KEY `acctypebill_fk` (`accountTypeID`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`customerID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
  MODIFY `accountID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `accounttypes`
--
ALTER TABLE `accounttypes`
  MODIFY `accountTypeID` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `bills`
--
ALTER TABLE `bills`
  MODIFY `billID` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `accounts`
--
ALTER TABLE `accounts`
  ADD CONSTRAINT `acctype_fk` FOREIGN KEY (`accountTypeID`) REFERENCES `accounttypes` (`accountTypeID`),
  ADD CONSTRAINT `cus_fk` FOREIGN KEY (`customerID`) REFERENCES `customers` (`customerID`);

--
-- Constraints for table `bills`
--
ALTER TABLE `bills`
  ADD CONSTRAINT `acc_fk` FOREIGN KEY (`accountID`) REFERENCES `accounts` (`accountID`),
  ADD CONSTRAINT `acctypebill_fk` FOREIGN KEY (`accountTypeID`) REFERENCES `accounttypes` (`accountTypeID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
