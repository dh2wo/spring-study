DROP TABLE FRIEND;
CREATE TABLE FRIEND(
    FNAME VARCHAR2(30) PRIMARY KEY,
    AGE NUMBER(3) NOT NULL,
    PHONE VARCHAR2(30) NOT NULL,
    ADRESS VARCHAR2(100) NOT NULL,
    CATEGORY VARCHAR2(10),
    ISACTIVE VARCHAR2(5)DEFAULT 'false'
);

INSERT INTO FRIEND VALUES('홍길동', 20, '111-111', '광주', 'town', 'true');
INSERT INTO FRIEND VALUES('손오공', 30, '222-222', '서울', 'school', 'false');
INSERT INTO FRIEND VALUES('임꺽정', 40, '333-333', '부산', 'net', 'true');
INSERT INTO FRIEND VALUES('테스트', 11, '123-123', '미국', 'net', 'true');



COMMIT;
ROLLBACK;

SELECT * FROM FRIEND;

UPDATE 
    friend
SET
    age = 22,
    phone = '123-123',
    adress = '미국',
    category = 'net',
    isActive = 'false'
WHERE
    fname = '홍길동';

DELETE 
    friend
WHERE
    fname = '테스트';



    