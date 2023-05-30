-- ----------------------------------------------------------------------------
-- MySQL Workbench Migration
-- Migrated Schemata: tfipcarcare
-- Source Schemata: tfipcarcare
-- Created: Mon May 29 21:53:12 2023
-- Workbench Version: 8.0.27
-- ----------------------------------------------------------------------------

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------------------------------------------------------
-- Schema tfipcarcare
-- ----------------------------------------------------------------------------
DROP SCHEMA IF EXISTS `tfipcarcare` ;
CREATE SCHEMA IF NOT EXISTS `tfipcarcare` ;

-- ----------------------------------------------------------------------------
-- Table tfipcarcare.authorities
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `tfipcarcare`.`authorities` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) CHARACTER SET 'utf8' NOT NULL,
  `authority` VARCHAR(20) CHARACTER SET 'utf8' NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- Table tfipcarcare.car
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `tfipcarcare`.`car` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `carPlate` VARCHAR(50) CHARACTER SET 'utf8' NOT NULL,
  `brand` VARCHAR(50) CHARACTER SET 'utf8' NOT NULL,
  `model` VARCHAR(50) CHARACTER SET 'utf8' NOT NULL,
  `yearManufactured` INT NOT NULL,
  `customer_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_customer_car` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `fk_customer_car`
    FOREIGN KEY (`customer_id`)
    REFERENCES `tfipcarcare`.`customer` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- Table tfipcarcare.customer
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `tfipcarcare`.`customer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(50) CHARACTER SET 'utf8' NOT NULL,
  `pwd` VARCHAR(200) CHARACTER SET 'utf8' NOT NULL,
  `firstName` VARCHAR(50) CHARACTER SET 'utf8' NOT NULL,
  `lastName` VARCHAR(50) CHARACTER SET 'utf8' NOT NULL,
  `phoneNumber` INT NOT NULL,
  `address` VARCHAR(200) CHARACTER SET 'utf8' NOT NULL,
  `role` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- Table tfipcarcare.item
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `tfipcarcare`.`item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(80) CHARACTER SET 'utf8' NOT NULL,
  `price` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- Table tfipcarcare.maintenance
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `tfipcarcare`.`maintenance` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(200) CHARACTER SET 'utf8' NOT NULL,
  `date` DATE NULL DEFAULT NULL,
  `cost` DOUBLE NULL DEFAULT NULL,
  `status` VARCHAR(50) CHARACTER SET 'utf8' NOT NULL,
  `mileage` INT NULL DEFAULT NULL,
  `car_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_car_maintenance` (`car_id` ASC) VISIBLE,
  CONSTRAINT `fk_car_maintenance`
    FOREIGN KEY (`car_id`)
    REFERENCES `tfipcarcare`.`car` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- Table tfipcarcare.maintenanceitem
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `tfipcarcare`.`maintenanceitem` (
  `maintenance_id` INT NOT NULL,
  `item_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`maintenance_id`, `item_id`),
  CONSTRAINT `fk_item_maintenanceitem`
    FOREIGN KEY (`maintenance_id`)
    REFERENCES `tfipcarcare`.`maintenance` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_maintenance_maintenanceitem`
    FOREIGN KEY (`maintenance_id`)
    REFERENCES `tfipcarcare`.`maintenance` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- Table tfipcarcare.users
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `tfipcarcare`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) CHARACTER SET 'utf8' NOT NULL,
  `password` VARCHAR(20) CHARACTER SET 'utf8' NOT NULL,
  `enabled` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;
SET FOREIGN_KEY_CHECKS = 1;
