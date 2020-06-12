drop database if exists poseidon_prod;
create database poseidon_prod;
use poseidon_prod;

CREATE TABLE `bid` (
  `bid_id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(30) DEFAULT NULL,
  `ask_amount` decimal(8,2) DEFAULT NULL,
  `ask_quantity` decimal(8,1) DEFAULT NULL,
  `benchmark` varchar(125) DEFAULT NULL,
  `bid_amount` decimal(8,2) DEFAULT NULL,
  `bid_quantity` decimal(8,1) DEFAULT NULL,
  `book` varchar(125) DEFAULT NULL,
  `comment` varchar(125) DEFAULT NULL,
  `creation_date` timestamp NULL DEFAULT NULL,
  `creation_name` varchar(125) DEFAULT NULL,
  `deal_name` varchar(125) DEFAULT NULL,
  `deal_type` varchar(125) DEFAULT NULL,
  `revision_date` timestamp NULL DEFAULT NULL,
  `revision_name` varchar(125) DEFAULT NULL,
  `security` varchar(125) DEFAULT NULL,
  `side` varchar(125) DEFAULT NULL,
  `source_list_id` int DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `trader` varchar(125) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`bid_id`)
);

CREATE TABLE `curve_point` (
  `curve_point_id` int NOT NULL AUTO_INCREMENT,
  `creation_date` timestamp NULL DEFAULT NULL,
  `curve_id` int DEFAULT NULL,
  `revision_date` timestamp NULL DEFAULT NULL,
  `term` decimal(8,1) DEFAULT NULL,
  `value` decimal(8,1) DEFAULT NULL,
  PRIMARY KEY (`curve_point_id`)
);

CREATE TABLE `rating` (
  `rating_id` int NOT NULL AUTO_INCREMENT,
  `fitch_rating` varchar(125) DEFAULT NULL,
  `moodys_rating` varchar(125) DEFAULT NULL,
  `order_number` int NOT NULL,
  `stand_poor_rating` varchar(125) DEFAULT NULL,
  PRIMARY KEY (`rating_id`)
);

CREATE TABLE `rule` (
  `rule_id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(125) DEFAULT NULL,
  `json` varchar(125) DEFAULT NULL,
  `name` varchar(125) DEFAULT NULL,
  `sql_part` varchar(125) DEFAULT NULL,
  `sql_str` varchar(125) DEFAULT NULL,
  `template` varchar(125) DEFAULT NULL,
  PRIMARY KEY (`rule_id`)
);

CREATE TABLE `trade` (
  `trade_id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(30) DEFAULT NULL,
  `benchmark` varchar(125) DEFAULT NULL,
  `book` varchar(125) DEFAULT NULL,
  `buy_price` decimal(8,2) DEFAULT NULL,
  `buy_quantity` decimal(8,1) DEFAULT NULL,
  `creation_date` timestamp NULL DEFAULT NULL,
  `creation_name` varchar(125) DEFAULT NULL,
  `deal_name` varchar(125) DEFAULT NULL,
  `deal_type` varchar(125) DEFAULT NULL,
  `revision_date` timestamp NULL DEFAULT NULL,
  `revision_name` varchar(125) DEFAULT NULL,
  `security` varchar(125) DEFAULT NULL,
  `sell_price` decimal(8,2) DEFAULT NULL,
  `sell_quantity` decimal(8,1) DEFAULT NULL,
  `side` varchar(125) DEFAULT NULL,
  `source_list_id` int DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `trade_date` datetime(6) DEFAULT NULL,
  `trader` varchar(125) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`trade_id`)
);

CREATE TABLE `user` (
  `user_id` int NOT NULL,
  `fullname` varchar(125) DEFAULT NULL,
  `password` varchar(125) DEFAULT NULL,
  `role` tinyint DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `I_username` (`username`)
);

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
);

INSERT INTO `poseidon_prod`.`hibernate_sequence`
(`next_val`)
VALUES (1);

drop database if exists poseidon_test;
create database poseidon_test;
use poseidon_test;

CREATE TABLE `bid` (
  `bid_id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(30) DEFAULT NULL,
  `ask_amount` decimal(8,2) DEFAULT NULL,
  `ask_quantity` decimal(8,1) DEFAULT NULL,
  `benchmark` varchar(125) DEFAULT NULL,
  `bid_amount` decimal(8,2) DEFAULT NULL,
  `bid_quantity` decimal(8,1) DEFAULT NULL,
  `book` varchar(125) DEFAULT NULL,
  `comment` varchar(125) DEFAULT NULL,
  `creation_date` timestamp NULL DEFAULT NULL,
  `creation_name` varchar(125) DEFAULT NULL,
  `deal_name` varchar(125) DEFAULT NULL,
  `deal_type` varchar(125) DEFAULT NULL,
  `revision_date` timestamp NULL DEFAULT NULL,
  `revision_name` varchar(125) DEFAULT NULL,
  `security` varchar(125) DEFAULT NULL,
  `side` varchar(125) DEFAULT NULL,
  `source_list_id` int DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `trader` varchar(125) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`bid_id`)
);

CREATE TABLE `curve_point` (
  `curve_point_id` int NOT NULL AUTO_INCREMENT,
  `creation_date` timestamp NULL DEFAULT NULL,
  `curve_id` int DEFAULT NULL,
  `revision_date` timestamp NULL DEFAULT NULL,
  `term` decimal(8,1) DEFAULT NULL,
  `value` decimal(8,1) DEFAULT NULL,
  PRIMARY KEY (`curve_point_id`)
);

CREATE TABLE `rating` (
  `rating_id` int NOT NULL AUTO_INCREMENT,
  `fitch_rating` varchar(125) DEFAULT NULL,
  `moodys_rating` varchar(125) DEFAULT NULL,
  `order_number` int NOT NULL,
  `stand_poor_rating` varchar(125) DEFAULT NULL,
  PRIMARY KEY (`rating_id`)
);

CREATE TABLE `rule` (
  `rule_id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(125) DEFAULT NULL,
  `json` varchar(125) DEFAULT NULL,
  `name` varchar(125) DEFAULT NULL,
  `sql_part` varchar(125) DEFAULT NULL,
  `sql_str` varchar(125) DEFAULT NULL,
  `template` varchar(125) DEFAULT NULL,
  PRIMARY KEY (`rule_id`)
);

CREATE TABLE `trade` (
  `trade_id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(30) DEFAULT NULL,
  `benchmark` varchar(125) DEFAULT NULL,
  `book` varchar(125) DEFAULT NULL,
  `buy_price` decimal(8,2) DEFAULT NULL,
  `buy_quantity` decimal(8,1) DEFAULT NULL,
  `creation_date` timestamp NULL DEFAULT NULL,
  `creation_name` varchar(125) DEFAULT NULL,
  `deal_name` varchar(125) DEFAULT NULL,
  `deal_type` varchar(125) DEFAULT NULL,
  `revision_date` timestamp NULL DEFAULT NULL,
  `revision_name` varchar(125) DEFAULT NULL,
  `security` varchar(125) DEFAULT NULL,
  `sell_price` decimal(8,2) DEFAULT NULL,
  `sell_quantity` decimal(8,1) DEFAULT NULL,
  `side` varchar(125) DEFAULT NULL,
  `source_list_id` int DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `trade_date` datetime(6) DEFAULT NULL,
  `trader` varchar(125) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`trade_id`)
);

CREATE TABLE `user` (
  `user_id` int NOT NULL,
  `fullname` varchar(125) DEFAULT NULL,
  `password` varchar(125) DEFAULT NULL,
  `role` tinyint DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `I_username` (`username`)
);

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
);

INSERT INTO `poseidon_prod`.`hibernate_sequence`
(`next_val`)
VALUES (1);

commit;