-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 10, 2025 at 11:21 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ahp_karyawan`
--

-- --------------------------------------------------------

--
-- Table structure for table `alternatives`
--

CREATE TABLE `alternatives` (
  `id` int(11) NOT NULL,
  `karyawan_id` int(11) NOT NULL,
  `k1_score` int(11) NOT NULL,
  `k2_score` int(11) NOT NULL,
  `k3_score` int(11) NOT NULL,
  `k4_score` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `alternatives`
--

INSERT INTO `alternatives` (`id`, `karyawan_id`, `k1_score`, `k2_score`, `k3_score`, `k4_score`) VALUES
(9, 7, 5, 5, 5, 5),
(10, 8, 10, 10, 10, 10),
(11, 9, 5, 5, 5, 5),
(12, 10, 1, 2, 3, 4);

-- --------------------------------------------------------

--
-- Table structure for table `bobot_kriteria`
--

CREATE TABLE `bobot_kriteria` (
  `id` int(11) NOT NULL,
  `k1` varchar(10) DEFAULT NULL,
  `k2` varchar(10) DEFAULT NULL,
  `bobot` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bobot_kriteria`
--

INSERT INTO `bobot_kriteria` (`id`, `k1`, `k2`, `bobot`) VALUES
(1, 'K1', 'K1', 1),
(2, 'K1', 'K2', 3),
(3, 'K1', 'K3', 5),
(4, 'K1', 'K4', 4),
(5, 'K2', 'K1', 0.3333333333333333),
(6, 'K2', 'K2', 1),
(7, 'K2', 'K3', 2),
(8, 'K2', 'K4', 2),
(9, 'K2', 'K5', 1),
(10, 'K3', 'K1', 0.2),
(11, 'K3', 'K2', 0.5),
(12, 'K3', 'K3', 1),
(13, 'K3', 'K4', 1),
(14, 'K4', 'K1', 0.25),
(15, 'K4', 'K2', 0.5),
(16, 'K4', 'K3', 1),
(17, 'K4', 'K4', 1);

-- --------------------------------------------------------

--
-- Table structure for table `bobot_subkriteria`
--

CREATE TABLE `bobot_subkriteria` (
  `id` int(11) NOT NULL,
  `sk1` varchar(10) DEFAULT NULL,
  `sk2` varchar(10) DEFAULT NULL,
  `bobot` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bobot_subkriteria`
--

INSERT INTO `bobot_subkriteria` (`id`, `sk1`, `sk2`, `bobot`) VALUES
(1, 'K11', 'K11', 1),
(2, 'K11', 'K12', 2),
(3, 'K11', 'K13', 3),
(4, 'K11', 'K14', 4),
(5, 'K11', 'K15', 5),
(6, 'K12', 'K11', 0.5),
(7, 'K12', 'K12', 1),
(8, 'K12', 'K13', 2),
(9, 'K12', 'K14', 3),
(10, 'K12', 'K15', 4),
(11, 'K13', 'K11', 0.33),
(12, 'K13', 'K12', 0.5),
(13, 'K13', 'K13', 1),
(14, 'K13', 'K14', 2),
(15, 'K13', 'K15', 3),
(16, 'K14', 'K11', 0.25),
(17, 'K14', 'K12', 0.33),
(18, 'K14', 'K13', 0.5),
(19, 'K14', 'K14', 1),
(20, 'K14', 'K15', 2),
(21, 'K15', 'K11', 0.2),
(22, 'K15', 'K12', 0.25),
(23, 'K15', 'K13', 0.33),
(24, 'K15', 'K14', 0.5),
(25, 'K15', 'K15', 1),
(26, 'K21', 'K21', 1),
(27, 'K21', 'K22', 2),
(28, 'K21', 'K23', 3),
(29, 'K21', 'K24', 4),
(30, 'K21', 'K25', 5),
(31, 'K22', 'K21', 0.5),
(32, 'K22', 'K22', 1),
(33, 'K22', 'K23', 2),
(34, 'K22', 'K24', 3),
(35, 'K22', 'K25', 4),
(36, 'K23', 'K21', 0.33),
(37, 'K23', 'K22', 0.5),
(38, 'K23', 'K23', 1),
(39, 'K23', 'K24', 2),
(40, 'K23', 'K25', 3),
(41, 'K24', 'K21', 0.25),
(42, 'K24', 'K22', 0.33),
(43, 'K24', 'K23', 0.5),
(44, 'K24', 'K24', 1),
(45, 'K24', 'K25', 2),
(46, 'K25', 'K21', 0.2),
(47, 'K25', 'K22', 0.25),
(48, 'K25', 'K23', 0.33),
(49, 'K25', 'K24', 0.5),
(50, 'K25', 'K25', 1),
(51, 'K31', 'K31', 1),
(52, 'K31', 'K32', 2),
(53, 'K31', 'K33', 3),
(54, 'K31', 'K34', 4),
(55, 'K31', 'K35', 5),
(56, 'K32', 'K31', 0.5),
(57, 'K32', 'K32', 1),
(58, 'K32', 'K33', 2),
(59, 'K32', 'K34', 3),
(60, 'K32', 'K35', 4),
(61, 'K33', 'K31', 0.33),
(62, 'K33', 'K32', 0.5),
(63, 'K33', 'K33', 1),
(64, 'K33', 'K34', 2),
(65, 'K33', 'K35', 3),
(66, 'K34', 'K31', 0.25),
(67, 'K34', 'K32', 0.33),
(68, 'K34', 'K33', 0.5),
(69, 'K34', 'K34', 1),
(70, 'K34', 'K35', 2),
(71, 'K35', 'K31', 0.2),
(72, 'K35', 'K32', 0.25),
(73, 'K35', 'K33', 0.33),
(74, 'K35', 'K34', 0.5),
(75, 'K35', 'K35', 1),
(76, 'K41', 'K41', 1),
(77, 'K41', 'K42', 2),
(78, 'K41', 'K43', 3),
(79, 'K41', 'K44', 4),
(80, 'K41', 'K45', 5),
(81, 'K42', 'K41', 0.5),
(82, 'K42', 'K42', 1),
(83, 'K42', 'K43', 2),
(84, 'K42', 'K44', 3),
(85, 'K42', 'K45', 4),
(86, 'K43', 'K41', 0.33),
(87, 'K43', 'K42', 0.5),
(88, 'K43', 'K43', 1),
(89, 'K43', 'K44', 2),
(90, 'K43', 'K45', 3),
(91, 'K44', 'K41', 0.25),
(92, 'K44', 'K42', 0.33),
(93, 'K44', 'K43', 0.5),
(94, 'K44', 'K44', 1),
(95, 'K44', 'K45', 2),
(96, 'K45', 'K41', 0.2),
(97, 'K45', 'K42', 0.25),
(98, 'K45', 'K43', 0.33),
(99, 'K45', 'K44', 0.5),
(100, 'K45', 'K45', 1);

-- --------------------------------------------------------

--
-- Table structure for table `criterias`
--

CREATE TABLE `criterias` (
  `code` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `priority` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `criterias`
--

INSERT INTO `criterias` (`code`, `name`, `priority`) VALUES
('K1', 'Kepatuhan Prosedur & SOP', 'Sangat Penting ke-1'),
('K2', 'Kuantitas Kerja', 'Penting ke-2'),
('K3', 'Kualitas Kerja', 'Cukup Penting ke-3'),
('K4', 'Disiplin', 'Biasa ke-4');

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `id` int(11) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `jenis_kelamin` varchar(50) DEFAULT NULL,
  `posisi` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`id`, `nama`, `jenis_kelamin`, `posisi`) VALUES
(7, 'BUDI', 'Laki-laki', 'SPV'),
(8, 'ANDI ', 'Laki-laki', 'SPV'),
(9, 'REZA ', 'Laki-laki', 'SPV'),
(10, 'SITI', 'Laki-laki', 'ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `evaluations`
--

CREATE TABLE `evaluations` (
  `id` int(11) NOT NULL,
  `karyawan_id` int(11) NOT NULL,
  `admin_id` int(11) NOT NULL,
  `selection_name` varchar(150) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `results`
--

CREATE TABLE `results` (
  `id` int(11) NOT NULL,
  `evaluation_id` int(11) DEFAULT NULL,
  `alternative_id` int(11) NOT NULL,
  `score` double NOT NULL,
  `rank` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `results`
--

INSERT INTO `results` (`id`, `evaluation_id`, `alternative_id`, `score`, `rank`) VALUES
(1, 1, 9, 1, 1),
(2, 2, 10, 1, 1),
(3, 3, 12, 1, 1),
(4, 4, 11, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `sub_criterias`
--

CREATE TABLE `sub_criterias` (
  `kode` varchar(10) NOT NULL,
  `kriteria` varchar(10) DEFAULT NULL,
  `nama_subkriteria` varchar(45) DEFAULT NULL,
  `bobot` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sub_criterias`
--

INSERT INTO `sub_criterias` (`kode`, `kriteria`, `nama_subkriteria`, `bobot`) VALUES
('K11', 'K1', 'Sangat Buruk', 0.06238),
('K12', 'K1', 'Buruk', 0.09857),
('K13', 'K1', 'Cukup', 0.16105),
('K14', 'K1', 'Baik', 0.26179),
('K15', 'K1', 'Sangat Baik', 0.41621),
('K21', 'K2', 'Sangat Buruk', 0.06238),
('K22', 'K2', 'Buruk', 0.09857),
('K23', 'K2', 'Cukup', 0.16105),
('K24', 'K2', 'Baik', 0.26179),
('K25', 'K2', 'Sangat Baik', 0.41621),
('K31', 'K4', 'Sangat Buruk', 0.06238),
('K32', 'K4', 'Buruk', 0.09857),
('K33', 'K4', 'Cukup', 0.16105),
('K34', 'K4', 'Baik', 0.26179),
('K35', 'K4', 'Sangat Baik', 0.41621),
('K41', 'K4', 'Sangat Buruk', 0.06238),
('K42', 'K4', 'Buruk', 0.09857),
('K43', 'K4', 'Cukup', 0.16105),
('K44', 'K4', 'Baik', 0.26179),
('K45', 'K4', 'Sangat Baik', 0.41621);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `gender` varchar(15) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `place_of_birth` varchar(15) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `address` text DEFAULT NULL,
  `religion` varchar(255) DEFAULT NULL,
  `status` varchar(15) DEFAULT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `join_date` datetime DEFAULT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `gender`, `email`, `place_of_birth`, `date_of_birth`, `address`, `religion`, `status`, `phone_number`, `join_date`, `username`, `password`, `role_id`, `created_at`, `updated_at`) VALUES
(1, 'Admin Utama', 'Male', 'admin@example.com', 'Jakarta', '1990-01-01', 'Jl. Merdeka No. 1, Jakarta', 'Islam', 'Active', '081234567890', '2025-05-22 00:28:50', 'admin', 'admin', 1, '2025-05-22 00:28:50', '2025-05-22 00:28:50');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `alternatives`
--
ALTER TABLE `alternatives`
  ADD PRIMARY KEY (`id`),
  ADD KEY `product_id` (`karyawan_id`);

--
-- Indexes for table `bobot_kriteria`
--
ALTER TABLE `bobot_kriteria`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `bobot_subkriteria`
--
ALTER TABLE `bobot_subkriteria`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `criterias`
--
ALTER TABLE `criterias`
  ADD UNIQUE KEY `unique_code` (`code`);

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `evaluations`
--
ALTER TABLE `evaluations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `evaluations_ibfk_1` (`karyawan_id`),
  ADD KEY `evaluations_ibfk_2` (`admin_id`);

--
-- Indexes for table `results`
--
ALTER TABLE `results`
  ADD PRIMARY KEY (`id`),
  ADD KEY `evaluation_id` (`evaluation_id`),
  ADD KEY `results_ibfk_1` (`alternative_id`);

--
-- Indexes for table `sub_criterias`
--
ALTER TABLE `sub_criterias`
  ADD PRIMARY KEY (`kode`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `alternatives`
--
ALTER TABLE `alternatives`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `bobot_kriteria`
--
ALTER TABLE `bobot_kriteria`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `bobot_subkriteria`
--
ALTER TABLE `bobot_subkriteria`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `evaluations`
--
ALTER TABLE `evaluations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `results`
--
ALTER TABLE `results`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `alternatives`
--
ALTER TABLE `alternatives`
  ADD CONSTRAINT `alternatives_ibfk_1` FOREIGN KEY (`karyawan_id`) REFERENCES `employees` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `evaluations`
--
ALTER TABLE `evaluations`
  ADD CONSTRAINT `evaluations_ibfk_1` FOREIGN KEY (`karyawan_id`) REFERENCES `employees` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `evaluations_ibfk_2` FOREIGN KEY (`admin_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `results`
--
ALTER TABLE `results`
  ADD CONSTRAINT `results_ibfk_1` FOREIGN KEY (`alternative_id`) REFERENCES `alternatives` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `results_ibfk_2` FOREIGN KEY (`alternative_id`) REFERENCES `alternatives` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
