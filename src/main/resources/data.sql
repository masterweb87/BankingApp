INSERT INTO account VALUES(100, 1200);
INSERT INTO account VALUES(101, 3500);

INSERT INTO user VALUES(1, 'daniel87', 'Daniel', 'Jones', 7070, 100);
INSERT INTO user VALUES(2, 'sarah90', 'Sarah', 'Smith', 1515, 101);

INSERT INTO transaction VALUES(1, 2000, sysdate(), 'Transfer done from HSBC', 'DEBIT_TRANSFER', 100);
INSERT INTO transaction VALUES(2, 4000, sysdate(), 'Withdram from BBVA', 'WITHDRAW', 100);