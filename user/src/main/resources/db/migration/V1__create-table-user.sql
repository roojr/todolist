-- V1__create-table-user.sql

-- # Create table User
CREATE TABLE tb_user(
    id BIGINT NOT NULL AUTO_INCREMENT,
    login VARCHAR(30) NOT NULL,
    password TEXT NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    in_active BOOLEAN NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX `login_UNIQUE` (login ASC) VISIBLE,
    UNIQUE INDEX `cpf_UNIQUE` (cpf ASC) VISIBLE
);