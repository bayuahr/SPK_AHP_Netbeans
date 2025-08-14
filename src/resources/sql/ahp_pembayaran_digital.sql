-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jul 24, 2025 at 08:59 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ahp_pembayaran_digital`
--

-- --------------------------------------------------------

--
-- Table structure for table `alternatives`
--

CREATE TABLE `alternatives` (
  `id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `tariff_value` decimal(10,2) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `k1_score` int(11) NOT NULL,
  `k2_score` int(11) NOT NULL,
  `k3_score` int(11) NOT NULL,
  `k4_score` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `alternatives`
--

INSERT INTO `alternatives` (`id`, `product_id`, `tariff_value`, `description`, `k1_score`, `k2_score`, `k3_score`, `k4_score`) VALUES
(1, 1, 1000.00, 'Alternatif harga Telkom 1', 10, 9, 8, 7),
(2, 1, 2000.00, 'Alternatif harga Telkom 2', 8, 8, 8, 8),
(3, 2, 2500.00, 'Alternatif harga PLN 1', 10, 10, 10, 10),
(4, 2, 3000.00, 'Alternatif harga PLN 2', 10, 9, 9, 9),
(7, 2, 3500.00, 'Alternatif harga PLN 3', 10, 10, 8, 10),
(8, 2, 4000.00, 'Alternatif harga PLN 4', 10, 7, 7, 10);

-- --------------------------------------------------------

--
-- Table structure for table `candidates`
--

CREATE TABLE `candidates` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `gender` varchar(15) NOT NULL,
  `last_education` varchar(10) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  `address` text NOT NULL,
  `leadership_score` int(11) NOT NULL,
  `knowledge_score` int(11) NOT NULL,
  `technical_skill_score` int(11) NOT NULL,
  `advanced_skill_score` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `candidates`
--

INSERT INTO `candidates` (`id`, `name`, `gender`, `last_education`, `phone_number`, `address`, `leadership_score`, `knowledge_score`, `technical_skill_score`, `advanced_skill_score`) VALUES
(1, 'Rp1000', 'Perempuan', 'S1', '081234567890', 'Jl. Kenanga No. 1, Jakarta', 9, 10, 9, 10),
(2, 'Rp2000', 'Laki-Laki', 'S1', '082345678901', 'Jl. Melati No. 2, Bandung', 10, 9, 10, 9),
(3, 'Rp3000', 'Laki-Laki', 'D3', '083456789012', 'Jl. Mawar No. 3, Surabaya', 8, 7, 8, 7),
(4, 'Rp4000', 'Laki-Laki', 'SMA', '084567890123', 'Jl. Anggrek No. 4, Yogyakarta', 10, 9, 10, 9),
(5, 'Rp 5000', 'Perempuan', 'D3', '085678901234', 'Jl. Flamboyan No. 5, Medan', 9, 10, 9, 10);

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
('K1', 'Profitabilitas', 'Sangat Penting ke-1'),
('K2', 'Akseptasi Pelanggan', 'Penting ke-2'),
('K3', 'Daya Saing Pasar', 'Cukup Penting ke-3'),
('K4', 'Pemulihan Biaya', 'Biasa ke-4');

-- --------------------------------------------------------

--
-- Table structure for table `evaluations`
--

CREATE TABLE `evaluations` (
  `id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `admin_id` int(11) NOT NULL,
  `selection_name` varchar(150) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `evaluations`
--

INSERT INTO `evaluations` (`id`, `product_id`, `admin_id`, `selection_name`, `created_at`) VALUES
(3, 2, 1, 'Penetapan Tarif - PLN', '2025-07-20 14:13:01'),
(4, 1, 1, 'Penetapan Tarif - Telkom', '2025-07-20 19:41:52'),
(5, 2, 1, 'Penetapan Tarif - PLN', '2025-07-22 18:35:09'),
(6, 2, 1, 'Penetapan Tarif - PLN', '2025-07-22 18:45:35'),
(7, 2, 1, 'Penetapan Tarif - PLN', '2025-07-23 20:18:18');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `code` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` text DEFAULT NULL,
  `admin_fee` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `code`, `name`, `description`, `admin_fee`) VALUES
(1, 'telkom', 'Telkom', 'Tagihan Internet Kabel Telkom', 1000.00),
(2, 'pln', 'PLN', 'Tagihan Listrik PLN', 2500.00);

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
(1, 2, 3, 0.5142167124697535, 1),
(2, 2, 4, 0.4857832875302466, 2),
(3, 3, 3, 0.5142167124697535, 1),
(4, 3, 4, 0.4857832875302466, 2),
(5, 4, 1, 0.5306318794350832, 1),
(6, 4, 2, 0.469368120564917, 2),
(7, 5, 3, 0.27282003981122305, 1),
(8, 5, 7, 0.2622162634556631, 2),
(9, 5, 4, 0.25703215898383486, 3),
(10, 5, 8, 0.20793153774927906, 4),
(11, 6, 3, 0.27282003981122305, 1),
(12, 6, 7, 0.2622162634556631, 2),
(13, 6, 4, 0.25703215898383486, 3),
(14, 6, 8, 0.20793153774927906, 4),
(15, 7, 3, 0.26607970920361557, 1),
(16, 7, 7, 0.25547593284805564, 2),
(17, 7, 4, 0.2509658614369881, 3),
(18, 7, 8, 0.2274784965113408, 4);

-- --------------------------------------------------------

--
-- Table structure for table `selections`
--

CREATE TABLE `selections` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `score` decimal(15,10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `selections`
--

INSERT INTO `selections` (`id`, `user_id`, `score`) VALUES
(156, 1, 0.2052166175),
(157, 2, 0.2111309413),
(158, 3, 0.1673048825),
(159, 4, 0.2111309413);

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
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `candidates`
--
ALTER TABLE `candidates`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `criterias`
--
ALTER TABLE `criterias`
  ADD UNIQUE KEY `unique_code` (`code`);

--
-- Indexes for table `evaluations`
--
ALTER TABLE `evaluations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `evaluations_ibfk_1` (`product_id`),
  ADD KEY `evaluations_ibfk_2` (`admin_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code` (`code`);

--
-- Indexes for table `results`
--
ALTER TABLE `results`
  ADD PRIMARY KEY (`id`),
  ADD KEY `evaluation_id` (`evaluation_id`),
  ADD KEY `results_ibfk_1` (`alternative_id`);

--
-- Indexes for table `selections`
--
ALTER TABLE `selections`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_id` (`user_id`);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `candidates`
--
ALTER TABLE `candidates`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `evaluations`
--
ALTER TABLE `evaluations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `results`
--
ALTER TABLE `results`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `selections`
--
ALTER TABLE `selections`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=161;

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
  ADD CONSTRAINT `alternatives_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `evaluations`
--
ALTER TABLE `evaluations`
  ADD CONSTRAINT `evaluations_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE,
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
