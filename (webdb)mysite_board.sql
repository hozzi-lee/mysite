DROP TABLE board;

DROP SEQUENCE sqc_board_no;

CREATE TABLE board (
    no        NUMBER,
    title     VARCHAR2(500) NOT NULL,
    content   VARCHAR2(4000),
    hit       NUMBER,
    reg_date  DATE NOT NULL,
    user_no   NUMBER NOT NULL,
    PRIMARY KEY ( no ),
    CONSTRAINT board_fk FOREIGN KEY ( user_no )
        REFERENCES users ( no )
);

CREATE SEQUENCE sqc_board_no START WITH 1 INCREMENT BY 1 NOCACHE;

SELECT
    no,
    title,
    content,
    hit,
    reg_date,
    user_no
FROM
    board;

INSERT INTO board VALUES (
    sqc_board_no.NEXTVAL,
    '제목',
    '내용',
    1,
    sysdate,
    1
);

COMMIT;

SELECT
    board.no,
    board.title,
    users.name,
    board.hit,
    board.reg_date
FROM
    users,
    board
WHERE
    users.no = board.user_no;

SELECT
    users.name,
    board.hit,
    board.reg_date,
    board.title,
    board.content,
    board.user_no
FROM
    users,
    board
WHERE
        users.no = board.user_no
    AND board.no = 2;

SELECT
    users.name,
    board.hit,
    board.reg_date,
    board.title,
    board.content,
    board.user_no
FROM
    users,
    board
WHERE
        users.no = board.user_no
    AND users.no = 2
    AND board.user_no = 2;

SELECT
    hit
FROM
    board
WHERE
    no = 2;