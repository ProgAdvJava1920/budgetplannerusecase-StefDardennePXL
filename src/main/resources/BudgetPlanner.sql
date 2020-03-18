-- MySQL Script generated by MySQL Workbench
-- Tue Mar  3 09:13:57 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema budgetplanner
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema budgetplanner
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `budgetplanner` DEFAULT CHARACTER SET utf8;
USE `budgetplanner`;

-- -----------------------------------------------------
-- Table `budgetplanner`.`Account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `budgetplanner`.`Account`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `IBAN` VARCHAR(34) NULL DEFAULT NULL,
    `name` VARCHAR(45) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `budgetplanner`.`Label`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `budgetplanner`.`Label`
(
    `id`          INT          NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(45)  NULL DEFAULT NULL,
    `description` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `budgetplanner`.`Payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `budgetplanner`.`Payment`
(
    `id`               INT          NOT NULL AUTO_INCREMENT,
    `date`             DATETIME     NULL DEFAULT NULL,
    `amount`           FLOAT        NULL DEFAULT NULL,
    `currency`         VARCHAR(45)  NULL DEFAULT NULL,
    `detail`           VARCHAR(255) NULL DEFAULT NULL,
    `accountId`        INT          NOT NULL,
    `counterAccountId` INT          NOT NULL,
    `labelId`          INT          NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_Payment_Account_idx` (`accountId` ASC),
    INDEX `fk_Payment_Counter_Account_idx` (`counterAccountId` ASC),
    INDEX `fk_Payment_Label1_idx` (`labelId` ASC),
    CONSTRAINT `fk_Payment_Account_Id`
        FOREIGN KEY (`accountId`)
            REFERENCES `budgetplanner`.`Account` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_Payment_CounterAccount_Id`
        FOREIGN KEY (`counterAccountId`)
            REFERENCES `budgetplanner`.`Account` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_Payment_Label1`
        FOREIGN KEY (`labelId`)
            REFERENCES `budgetplanner`.`Label` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
