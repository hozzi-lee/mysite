DROP TABLE users;
DROP SEQUENCE sqc_users_no;

CREATE TABLE users (
    no NUMBER,
    id VARCHAR2(20) UNIQUE NOT NULL,
    password VARCHAR2(20) NOT NULL,
    name VARCHAR2(20),
    gender VARCHAR2(10),
    PRIMARY KEY(no)
);

CREATE SEQUENCE sqc_users_no
START WITH 1
INCREMENT BY 1
NOCACHE;

-- INSERT TEST //
/*
INSERT INTO
    users
VALUES
    (
    sqc_users_no.NEXTVAL,
    'leehojin',
    '1234',
    'hojin lee',
    'male'
);

COMMIT;
*/

SELECT
    no,
    id,
    password,
    name,
    gender
FROM
    users;