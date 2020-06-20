DELETE FROM vote;
DELETE FROM dish;
DELETE FROM restaurant;
DELETE FROM user;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO user (name, email, password, role) VALUES
('User', 'user@yandex.ru', 'password', 'ROLE_USER'),
('User2', 'user2@mail.ru', 'pass', 'ROLE_USER'),
('Admin', 'admin@gmail.com', 'admin', 'ROLE_ADMIN');

INSERT INTO restaurant (date, name) VALUES
('2020-06-20', 'mumu'),
('2020-06-20', 'bk'),
('2020-06-20', 'tanuki');

INSERT INTO dish (name, rest_id, price)
VALUES ('borsch', 100003, 220),
       ('kartoha', 100003, 300),
       ('kompot', 100003, 70),
       ('burger', 100004, 230),
       ('cezar', 100004, 279),
       ('pepsi', 100004, 90),
       ('fila', 100005, 380),
       ('gunkan', 100005, 120),
       ('sake', 100005, 130);

INSERT INTO vote (user_id, date, time, menu_id)
VALUES  (100000, '2020-06-20', '10:00:00', 100003),
        (100001, '2020-06-20', '9:00:00', 100003);