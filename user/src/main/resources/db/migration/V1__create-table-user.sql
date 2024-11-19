-- V1__create-table-user.sql

-- # Create table User
CREATE TABLE tb_user(
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(30) NOT NULL,
    password TEXT NOT NULL,
    email varchar(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE,
    cpf VARCHAR(14) NOT NULL,
    in_active BOOLEAN NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX `username_UNIQUE` (username ASC) VISIBLE,
    UNIQUE INDEX `cpf_UNIQUE` (cpf ASC) VISIBLE
);