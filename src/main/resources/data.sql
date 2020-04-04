INSERT INTO accounts (user_name, password, is_activated, created_by, created_at, is_deleted)
VALUES ('system', '$2a$10$xbZxaa42MsALegA6lD78COWz.sBYCZYLZVjb904f0y42TEm1JG/Me', TRUE, 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO accounts (user_name, password, is_activated, created_by, created_at, is_deleted)
VALUES ('admin', '$2a$10$yglygEX/oNZuex6lh.1QTOeIwR5lbBqeROU.F7N9D8XSmrU9ReFYS', TRUE, 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO accounts (user_name, password, is_activated, created_by, created_at, is_deleted)
VALUES ('jansiroky', '$2a$10$Vws9nU1mqlbo1KLVXaq7dOQUGd2BfSILJETt2zb50rCR0cRVdl5xu', TRUE, 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO accounts (user_name, password, is_activated, created_by, created_at, is_deleted)
VALUES ('petervarga', '$2a$10$1oyamJIm8aSLaXnm/xhaRe6yxAWEKZeFjsMQ/wWXoINsGeuHd.c2S', TRUE, 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO accounts (user_name, password, is_activated, created_by, created_at, is_deleted)
VALUES ('michalvelky', '$2a$10$Wi7MdTNoKKK7Iaf262bWLuVdzOViFXHRgR7siBaq8YRWkTpJtd5J.', TRUE, 1, CURRENT_TIMESTAMP, FALSE);

INSERT INTO logins (account_id, no_trys, last_login, ip_address)
VALUES (1, 0, CURRENT_TIMESTAMP, '0.0.0.0');
INSERT INTO logins (account_id, no_trys, last_login, ip_address)
VALUES (2, 0, CURRENT_TIMESTAMP, '0.0.0.0');
INSERT INTO logins (account_id, no_trys, last_login, ip_address)
VALUES (3, 0, CURRENT_TIMESTAMP, '0.0.0.0');
INSERT INTO logins (account_id, no_trys, last_login, ip_address)
VALUES (4, 0, CURRENT_TIMESTAMP, '0.0.0.0');
INSERT INTO logins (account_id, no_trys, last_login, ip_address)
VALUES (5, 0, CURRENT_TIMESTAMP, '0.0.0.0');

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
VALUES ('CREATE_TABLE_NAME', 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name, created_by, created_at, is_deleted)
VALUES ('READ_TABLE_NAME', 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name, created_by, created_at, is_deleted)
VALUES ('UPDATE_TABLE_NAME', 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name, created_by, created_at, is_deleted)
VALUES ('DELETE_TABLE_NAME', 1, CURRENT_TIMESTAMP, FALSE);

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

INSERT INTO account_role_items (user_id, role_id)
VALUES (1, 1);
INSERT INTO account_role_items (user_id, role_id)
VALUES (2, 2);