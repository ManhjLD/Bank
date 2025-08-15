CREATE TABLE customer(
    id          INT PRIMARY KEY ,
    username    VARCHAR(100) NOT NULL ,
    password    VARCHAR(500) NOT NULL ,
    role        VARCHAR(50) NOT NULL ,
    name        VARCHAR(100) NOT NULL ,
    email       VARCHAR(100) NOT NULL ,
    birthday    VARCHAR(50) NOT NULL ,
    address     VARCHAR(200) NOT NULL ,
    pin         VARCHAR(6) NOT NULL
);
CREATE TABLE account(
    id              INT PRIMARY KEY ,
    accountNumber   VARCHAR(50) NOT NULL ,
    owner           VARCHAR(100) NOT NULL ,
    balance         INT DEFAULT 0,
    created_at      DATE NOT NULL
);
CREATE TABLE transaction(
    id              INT PRIMARY KEY ,
    fromAccount     VARCHAR(100) NOT NULL ,
    toAccount       VARCHAR(100) NOT NULL ,
    amount          INTEGER NOT NULL ,
    type            VARCHAR(50) ,
    description     VARCHAR(200) ,
    time            DATE NOT NULL
);