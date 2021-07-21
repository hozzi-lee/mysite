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
    '제목123',
    '내용123',
    0,
    sysdate,
    6
);

COMMIT;

SELECT
    board.no,
    board.title,
    users.name,
    board.hit,
    board.reg_date,
    board.user_no
FROM
    users,
    board
WHERE
    users.no = board.user_no
ORDER BY
    board.no DESC;

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
    AND board.no = 10;

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

UPDATE
    board
SET
    hit = (hit + 1)
WHERE
    no = 14;

DELETE FROM
    board
WHERE
    no = 10;

SELECT
    board.no,
    board.title,
    users.name,
    board.hit,
    board.reg_date,
    board.user_no
FROM
    users FULL OUTER JOIN board
    ON users.no = board.user_no
WHERE
    board.no LIKE '%호%'
    OR board.title LIKE '%호%'
    OR users.name LIKE '%호%'
ORDER BY
    board.no DESC;

SELECT
    COUNT(no)
FROM
    (
        SELECT
            no
        FROM
            board
    );