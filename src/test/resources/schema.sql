CREATE table Customer (
    id identity,
    name VARCHAR,
    created_by VARCHAR,
    gender VARCHAR
);

CREATE table ADDRESS (
    CUSTOMER identity,
    street VARCHAR,
    city VARCHAR,
    house INTEGER,
    CUSTOMER_KEY INTEGER
    /*,constraint CUSTOMER_KEY foreign key(CUSTOMER_KEY) references Customer*/
);

