INSERT INTO accounts (user_name, email, password, is_activated, created_at, is_deleted)
VALUES ('system', 'system@blesk.sk', '$2y$10$2V6Si2mzFJd9Q4x2LkUqgeEMmJKAbg6/bd9TjUWSnxltyemWBxJh6', TRUE, CURRENT_TIMESTAMP, FALSE);
INSERT INTO accounts (user_name, email, password, is_activated, created_at, is_deleted)
VALUES ('admin', 'admin@blesk.sk', '$2a$10$yglygEX/oNZuex6lh.1QTOeIwR5lbBqeROU.F7N9D8XSmrU9ReFYS', TRUE, CURRENT_TIMESTAMP, FALSE);
INSERT INTO accounts (user_name, email, password, is_activated, created_at, is_deleted)
VALUES ('jansiroky', 'jan.siroky@blesk.sk', '$2a$10$Vws9nU1mqlbo1KLVXaq7dOQUGd2BfSILJETt2zb50rCR0cRVdl5xu', TRUE, CURRENT_TIMESTAMP, FALSE);
INSERT INTO accounts (user_name, email, password, is_activated, created_at, is_deleted)
VALUES ('petervarga','peter.varga@gmail.com', '$2a$10$1oyamJIm8aSLaXnm/xhaRe6yxAWEKZeFjsMQ/wWXoINsGeuHd.c2S', TRUE, CURRENT_TIMESTAMP, FALSE);
INSERT INTO accounts (user_name, email, password, is_activated, created_at, is_deleted)
VALUES ('michalvelky', 'michal.velky@gmail.com', '$2a$10$Wi7MdTNoKKK7Iaf262bWLuVdzOViFXHRgR7siBaq8YRWkTpJtd5J.', TRUE, CURRENT_TIMESTAMP, FALSE);


INSERT INTO logins (account_id, last_login, ip_address)
VALUES (1, CURRENT_TIMESTAMP, '0:0:0:0:0:0:0:0');
INSERT INTO logins (account_id, last_login, ip_address)
VALUES (2, CURRENT_TIMESTAMP, '0:0:0:0:0:0:0:0');
INSERT INTO logins (account_id, last_login, ip_address)
VALUES (3, CURRENT_TIMESTAMP, '0:0:0:0:0:0:0:0');
INSERT INTO logins (account_id, last_login, ip_address)
VALUES (4, CURRENT_TIMESTAMP, '0:0:0:0:0:0:0:0');
INSERT INTO logins (account_id, last_login, ip_address)
VALUES (5, CURRENT_TIMESTAMP, '0:0:0:0:0:0:0:0');


INSERT INTO roles (name,  created_at, is_deleted)
VALUES ('ROLE_SYSTEM',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO roles (name,  created_at, is_deleted)
VALUES ('ROLE_ADMIN',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO roles (name,  created_at, is_deleted)
VALUES ('ROLE_MANAGER',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO roles (name,  created_at, is_deleted)
VALUES ('ROLE_CLIENT',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO roles (name,  created_at, is_deleted)
VALUES ('ROLE_COURIER',  CURRENT_TIMESTAMP, FALSE);


INSERT INTO privileges (name, created_at ,is_deleted)
VALUES ('VIEW_ALL_ACCOUNTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name, created_at ,is_deleted)
VALUES ('JOIN_ACCOUNTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name, created_at, is_deleted)
VALUES ('VIEW_ACCOUNTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_ACCOUNTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_ACCOUNTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_ACCOUNTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at ,is_deleted)
VALUES ('VIEW_ALL_PREFERENCES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_PREFERENCES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_PREFERENCES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_PREFERENCES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_PREFERENCES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at ,is_deleted)
VALUES ('VIEW_ALL_PRIVILEGES', CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_PRIVILEGES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_PRIVILEGES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_PRIVILEGES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_PRIVILEGES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at ,is_deleted)
VALUES ('VIEW_ALL_ROLES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_ROLES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_ROLES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_ROLES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_ROLES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at ,is_deleted)
VALUES ('VIEW_ALL_USERS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_USERS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_USERS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_USERS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_USERS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at ,is_deleted)
VALUES ('VIEW_ALL_GENDERS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_GENDERS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_GENDERS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_GENDERS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_GENDERS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at ,is_deleted)
VALUES ('VIEW_ALL_PAYOUTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_PAYOUTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_PAYOUTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_PAYOUTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_PAYOUTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at ,is_deleted)
VALUES ('VIEW_ALL_PAYMENTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_PAYMENTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_PAYMENTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_PAYMENTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_REFUNDS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_PAYMENTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at ,is_deleted)
VALUES ('VIEW_ALL_COMMUNICATIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_COMMUNICATIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_COMMUNICATIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_COMMUNICATIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_COMMUNICATIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at ,is_deleted)
VALUES ('VIEW_ALL_CONVERSATIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_CONVERSATIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_CONVERSATIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_CONVERSATIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_CONVERSATIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at ,is_deleted)
VALUES ('VIEW_ALL_STATUSES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_STATUS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_STATUS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_STATUS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_STATUS',  CURRENT_TIMESTAMP, FALSE);


INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 1);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 2);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 3);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 4);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 5);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 6);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 7);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 8);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 9);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 10);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 11);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 12);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 13);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 14);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 15);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 16);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 17);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 18);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 19);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 20);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 21);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 22);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 23);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 24);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 25);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 26);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 27);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 28);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 29);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 30);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 31);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 32);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 33);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 34);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 35);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 36);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 37);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 38);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 39);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 40);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 41);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 42);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 43);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 44);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 45);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 46);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 47);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 48);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 49);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 50);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 51);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 52);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 53);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 54);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 55);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 56);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 57);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 1);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 2);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 3);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 4);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 5);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 6);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 7);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 8);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 9);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 10);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 11);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 12);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 13);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 14);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 15);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 16);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 17);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 18);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 19);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 20);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 21);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 22);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 23);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 24);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 25);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 26);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 27);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 28);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 29);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 30);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 31);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 32);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 33);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 34);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 35);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 36);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 37);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 38);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 39);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 40);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 41);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 42);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 43);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 44);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 45);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 46);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 47);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 48);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 49);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 50);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 51);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 52);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 53);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 54);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 55);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 56);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 57);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 1);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 2);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 3);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 4);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 5);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 6);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 7);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 8);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 9);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 10);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 11);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 22);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 23);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 24);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 25);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 26);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 27);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 28);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 32);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 33);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 34);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 35);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 36);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 37);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 38);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 39);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 40);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 41);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 42);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 43);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 44);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 45);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 46);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 47);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 48);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 49);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 50);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 51);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (3, 52);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 2);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 7);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 8);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 11);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 22);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 23);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 24);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 25);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 26);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 27);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 28);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 32);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 33);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 34);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 35);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 36);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 37);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 38);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 39);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 40);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 41);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 42);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 43);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 44);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 45);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 46);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 47);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 48);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 49);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 50);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 51);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (4, 52);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 2);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 7);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 8);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 11);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 22);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 23);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 24);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 25);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 26);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 27);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 28);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 32);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 33);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 34);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 35);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 36);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 43);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 44);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 45);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 46);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 47);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 48);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 49);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 50);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 51);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (5, 52);


INSERT INTO account_role_items (account_id, role_id)
VALUES (1, 1);
INSERT INTO account_role_items (account_id, role_id)
VALUES (2, 2);
INSERT INTO account_role_items (account_id, role_id)
VALUES (3, 3);
INSERT INTO account_role_items (account_id, role_id)
VALUES (4, 4);
INSERT INTO account_role_items (account_id, role_id)
VALUES (5, 5);