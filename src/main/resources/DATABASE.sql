CREATE TABLE account(
    accountNumber VARCHAR(50),
    owner         VARCHAR(100),
    balance       INTEGER,
    createdAt     DATE
);

CREATE TABLE transaction(
    id              INT,
    fromAccount     VARCHAR(100),
    toAccount       VARCHAR(100),
    amount          FLOAT,
    type            VARCHAR(200),
    timeStamp       DATE
);