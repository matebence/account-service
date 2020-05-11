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


INSERT INTO account_role_items (account_id, role_id)
VALUES (1, 1);
INSERT INTO account_role_items (account_id, role_id)
VALUES (2, 2);