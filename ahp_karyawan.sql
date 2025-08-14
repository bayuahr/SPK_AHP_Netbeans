-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 14, 2025 at 11:37 PM
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
('K1', 'Kedisiplinan Waktu', 'Sangat Penting ke-1'),
('K2', 'Produktivitas Kerja', 'Penting ke-2'),
('K3', 'Kualitas Kerja & Ketelitian', 'Cukup Penting ke-3'),
('K4', 'Kerjasama Tim & Sikap Kerja', 'Biasa ke-4');

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

--
-- Dumping data for table `evaluations`
--

INSERT INTO `evaluations` (`id`, `karyawan_id`, `admin_id`, `selection_name`, `created_at`) VALUES
(1, 7, 1, 'Penetapan Tarif - BUDI', '2025-08-14 22:45:07'),
(2, 8, 1, 'Penetapan Tarif - ANDI ', '2025-08-14 22:45:12'),
(3, 10, 1, 'Penetapan Tarif - SITI', '2025-08-14 22:45:18'),
(4, 9, 1, 'Penetapan Tarif - REZA ', '2025-08-14 22:45:23');

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
