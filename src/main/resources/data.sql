INSERT INTO accounts (user_name, email, password, is_activated, created_by, created_at, is_deleted)
VALUES ('system', 'system@blesk.sk', '$2y$10$2V6Si2mzFJd9Q4x2LkUqgeEMmJKAbg6/bd9TjUWSnxltyemWBxJh6', TRUE, 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO accounts (user_name, email, password, is_activated, created_by, created_at, is_deleted)
VALUES ('admin', 'admin@blesk.sk', '$2a$10$yglygEX/oNZuex6lh.1QTOeIwR5lbBqeROU.F7N9D8XSmrU9ReFYS', TRUE, 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO accounts (user_name, email, password, is_activated, created_by, created_at, is_deleted)
VALUES ('jansiroky', 'jan.siroky@blesk.sk', '$2a$10$Vws9nU1mqlbo1KLVXaq7dOQUGd2BfSILJETt2zb50rCR0cRVdl5xu', TRUE, 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO accounts (user_name, email, password, is_activated, created_by, created_at, is_deleted)
VALUES ('petervarga','peter.varga@gmail.com', '$2a$10$1oyamJIm8aSLaXnm/xhaRe6yxAWEKZeFjsMQ/wWXoINsGeuHd.c2S', TRUE, 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO accounts (user_name, email, password, is_activated, created_by, created_at, is_deleted)
VALUES ('michalvelky', 'michal.velky@gmail.com', '$2a$10$Wi7MdTNoKKK7Iaf262bWLuVdzOViFXHRgR7siBaq8YRWkTpJtd5J.', TRUE, 1, CURRENT_TIMESTAMP, FALSE);

INSERT INTO logins (account_id, last_login, ip_address)
VALUES (1, CURRENT_TIMESTAMP, '0.0.0.0');
INSERT INTO logins (account_id, last_login, ip_address)
VALUES (2, CURRENT_TIMESTAMP, '0.0.0.0');
INSERT INTO logins (account_id, last_login, ip_address)
VALUES (3, CURRENT_TIMESTAMP, '0.0.0.0');
INSERT INTO logins (account_id, last_login, ip_address)
VALUES (4, CURRENT_TIMESTAMP, '0.0.0.0');
INSERT INTO logins (account_id, last_login, ip_address)
VALUES (5, CURRENT_TIMESTAMP, '0.0.0.0');

INSERT INTO roles (name, created_by, created_at, is_deleted)
VALUES ('SYSTEM_ROLE', 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO roles (name, created_by, created_at, is_deleted)
VALUES ('ADMIN_ROLE', 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO roles (name, created_by, created_at, is_deleted)
VALUES ('MANAGER_ROLE', 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO roles (name, created_by, created_at, is_deleted)
VALUES ('CLIENT_ROLE', 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO roles (name, created_by, created_at, is_deleted)
VALUES ('COURIER_ROLE', 1, CURRENT_TIMESTAMP, FALSE);

INSERT INTO privileges (name, created_by, created_at ,is_deleted)
VALUES ('VIEW_ALL_TABLE_NAME', 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name, created_by, created_at, is_deleted)
VALUES ('VIEW_TABLE_NAME', 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name, created_by, created_at, is_deleted)
VALUES ('DELETE_TABLE_NAME', 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name, created_by, created_at, is_deleted)
VALUES ('CREATE_TABLE_NAME', 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name, created_by, created_at, is_deleted)
VALUES ('UPDATE_TABLE_NAME', 1, CURRENT_TIMESTAMP, FALSE);


INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 1);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 2);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 3);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (1, 4);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 1);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 2);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 3);
INSERT INTO role_privilege_items (role_id, privilege_id)
VALUES (2, 4);

INSERT INTO account_role_items (account_id, role_id)
VALUES (1, 1);
INSERT INTO account_role_items (account_id, role_id)
VALUES (2, 2);