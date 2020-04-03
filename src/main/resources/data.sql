INSERT INTO accounts (user_name, password, balance, is_activated, created_by, created_at, is_deleted)
VALUES ('matebence', '$2a$10$jbIi/RIYNm5xAW9M7IaE5.WPw6BZgD8wcpkZUg0jm8RHPtdfDcMgm', 200.00, TRUE, 1, CURRENT_TIMESTAMP, FALSE);
INSERT INTO logins (account_id, no_trys, last_login, ip_address)
VALUES (1, 0, CURRENT_TIMESTAMP, '192.168.99.100');
INSERT INTO roles (name, created_by, created_at, is_deleted)
VALUES ('ADMIN_ROLE', 1, CURRENT_TIMESTAMP, FALSE);
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
INSERT INTO account_role_items (user_id, role_id)
VALUES (1, 1);