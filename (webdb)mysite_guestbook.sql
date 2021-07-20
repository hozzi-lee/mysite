DROP TABLE guestbook;

DROP SEQUENCE sqc_no;

CREATE TABLE guestbook (
    no        NUMBER(5),
    name      VARCHAR2(100),
    password  VARCHAR2(100),
    content   VARCHAR2(4000),
    reg_date  DATE,
    PRIMARY KEY ( no )
);

CREATE SEQUENCE sqc_no START WITH 1 INCREMENT BY 1 NOCACHE;

INSERT INTO
    guestbook
VALUES (
    sqc_no.NEXTVAL,
    '이정재',
    '1',
    '첫번째 방명록 내용 안녕하세요',
    sysdate );

INSERT INTO
    guestbook
VALUES (
    sqc_no.NEXTVAL,
    '이정재',
    '2',
    '첫번째 방명록 내용 hello',
    sysdate );

COMMIT;

SELECT
    no,
    name,
    password,
    content,
    to_char(reg_date, 'yyyy-mm-dd hh24-mi-ss') rd
FROM
    guestbook
ORDER BY
    no ASC;

INSERT INTO guestbook VALUES (
    sqc_no.NEXTVAL,
    '이름TEST123',
    '패스워드TEST123',
    '본문TEST123',
    sysdate
);

INSERT INTO guestbook VALUES (
    sqc_no.NEXTVAL,
    '이름TEST456',
    '패스워드TEST456',
    '본문TEST456',
    sysdate
);

INSERT INTO guestbook VALUES (
    sqc_no.NEXTVAL,
    'asd',
    'asd',
    'asd',
    sysdate
);

INSERT INTO guestbook VALUES (
    sqc_no.NEXTVAL,
    '한글',
    '한글',
    '한글',
    sysdate
);

COMMIT;

SELECT
    no,
    name,
    password,
    content,
    to_char(reg_date, 'yyyy-mm-dd hh24-mi-ss') rd
FROM
    guestbook
ORDER BY
    no ASC;

SELECT
    no,
    name,
    content,
    reg_date
FROM
    guestbook;

DELETE FROM
    guestbook
WHERE
    no = 5
    AND password = '패스워드TEST456';