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


INSERT INTO logins (account_id, last_login, ip_address, created_at, is_deleted)
VALUES (1, CURRENT_TIMESTAMP, '0:0:0:0:0:0:0:0', CURRENT_TIMESTAMP, FALSE);
INSERT INTO logins (account_id, last_login, ip_address, created_at, is_deleted)
VALUES (2, CURRENT_TIMESTAMP, '0:0:0:0:0:0:0:0', CURRENT_TIMESTAMP, FALSE);
INSERT INTO logins (account_id, last_login, ip_address, created_at, is_deleted)
VALUES (3, CURRENT_TIMESTAMP, '0:0:0:0:0:0:0:0', CURRENT_TIMESTAMP, FALSE);
INSERT INTO logins (account_id, last_login, ip_address, created_at, is_deleted)
VALUES (4, CURRENT_TIMESTAMP, '0:0:0:0:0:0:0:0', CURRENT_TIMESTAMP, FALSE);
INSERT INTO logins (account_id, last_login, ip_address, created_at, is_deleted)
VALUES (5, CURRENT_TIMESTAMP, '0:0:0:0:0:0:0:0', CURRENT_TIMESTAMP, FALSE);


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
VALUES ('JOIN_ACCOUNTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name, created_at, is_deleted)
VALUES ('VIEW_ACCOUNTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_ACCOUNTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_ACCOUNTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_ACCOUNTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_PREFERENCES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_PREFERENCES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_PREFERENCES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_PREFERENCES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_PRIVILEGES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_PRIVILEGES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_PRIVILEGES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_PRIVILEGES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_ROLES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_ROLES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_ROLES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_ROLES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_USERS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_USERS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_USERS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_USERS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_GENDERS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_GENDERS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_GENDERS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_GENDERS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_PAYOUTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_PAYOUTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_PAYOUTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_PAYOUTS',  CURRENT_TIMESTAMP, FALSE);
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
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_COMMUNICATIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_COMMUNICATIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_COMMUNICATIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_COMMUNICATIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_CONVERSATIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_CONVERSATIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_CONVERSATIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_CONVERSATIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_STATUS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_STATUS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_STATUS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_STATUS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_REGIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_REGIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_REGIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_REGIONS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_VILLAGES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_VILLAGES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_VILLAGES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_VILLAGES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_DISTRICTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_DISTRICTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_DISTRICTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_DISTRICTS',  CURRENT_TIMESTAMP, FALSE);


INSERT INTO role_privilege_items (privilege_id, role_id)
SELECT privilege_id, (SELECT role_id FROM roles WHERE name = 'ROLE_SYSTEM') AS role_id FROM privileges;
INSERT INTO role_privilege_items (privilege_id, role_id)
SELECT privilege_id, (SELECT role_id FROM roles WHERE name = 'ROLE_ADMIN') AS role_id FROM privileges;
INSERT INTO role_privilege_items (privilege_id, role_id)
SELECT privilege_id, (SELECT role_id FROM roles WHERE name = 'ROLE_MANAGER') AS role_id FROM privileges WHERE name IN ('CREATE_ACCOUNTS', 'DELETE_ACCOUNTS', 'UPDATE_ACCOUNTS', 'VIEW_ACCOUNTS', 'JOIN_ACCOUNTS', 'CREATE_PREFERENCES', 'DELETE_PREFERENCES', 'UPDATE_PREFERENCES', 'VIEW_PREFERENCES', 'VIEW_GENDERS', 'CREATE_PAYMENTS', 'CREATE_REFUNDS', 'DELETE_PAYMENTS', 'UPDATE_PAYMENTS', 'VIEW_PAYMENTS', 'CREATE_PAYOUTS', 'DELETE_PAYOUTS', 'UPDATE_PAYOUTS', 'VIEW_PAYOUTS', 'CREATE_USERS', 'DELETE_USERS', 'UPDATE_USERS', 'VIEW_USERS', 'CREATE_COMMUNICATIONS', 'DELETE_COMMUNICATIONS', 'UPDATE_COMMUNICATIONS', 'VIEW_COMMUNICATIONS', 'CREATE_CONVERSATIONS', 'DELETE_CONVERSATIONS', 'UPDATE_CONVERSATIONS', 'VIEW_CONVERSATIONS', 'CREATE_DISTRICTS', 'DELETE_DISTRICTS', 'UPDATE_DISTRICTS', 'VIEW_DISTRICTS', 'CREATE_REGIONS', 'DELETE_REGIONS', 'UPDATE_REGIONS', 'VIEW_REGIONS', 'CREATE_VILLAGES', 'DELETE_VILLAGES', 'UPDATE_VILLAGES', 'VIEW_VILLAGES', 'CREATE_WAREHOUSES', 'DELETE_WAREHOUSES', 'UPDATE_WAREHOUSES', 'VIEW_WAREHOUSES');
INSERT INTO role_privilege_items (privilege_id, role_id)
SELECT privilege_id, (SELECT role_id FROM roles WHERE name = 'ROLE_CLIENT') AS role_id FROM privileges WHERE name IN ('JOIN_ACCOUNTS', 'UPDATE_PREFERENCES', 'VIEW_PREFERENCES', 'VIEW_GENDERS', 'CREATE_PAYMENTS', 'VIEW_PAYMENTS', 'CREATE_PAYOUTS', 'VIEW_PAYOUTS', 'CREATE_USERS', 'UPDATE_USERS', 'VIEW_USERS', 'CREATE_COMMUNICATIONS', 'VIEW_COMMUNICATIONS', 'CREATE_CONVERSATIONS', 'DELETE_CONVERSATIONS', 'UPDATE_CONVERSATIONS', 'VIEW_CONVERSATIONS', 'VIEW_DISTRICTS', 'VIEW_REGIONS', 'VIEW_VILLAGES', 'VIEW_WAREHOUSES');
INSERT INTO role_privilege_items (privilege_id, role_id)
SELECT privilege_id, (SELECT role_id FROM roles WHERE name = 'ROLE_COURIER') AS role_id FROM privileges WHERE name IN ('JOIN_ACCOUNTS', 'UPDATE_PREFERENCES', 'VIEW_PREFERENCES', 'VIEW_GENDERS', 'CREATE_PAYOUTS', 'VIEW_PAYOUTS', 'CREATE_USERS', 'UPDATE_USERS', 'VIEW_USERS', 'CREATE_COMMUNICATIONS', 'VIEW_COMMUNICATIONS', 'CREATE_CONVERSATIONS', 'DELETE_CONVERSATIONS', 'UPDATE_CONVERSATIONS', 'VIEW_CONVERSATIONS', 'VIEW_DISTRICTS', 'VIEW_REGIONS', 'VIEW_VILLAGES');


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