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
VALUES ('VIEW_COMMUNICATION_STATUS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_COMMUNICATION_STATUS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_COMMUNICATION_STATUS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_COMMUNICATION_STATUS',  CURRENT_TIMESTAMP, FALSE);
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
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_WAREHOUSES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_WAREHOUSES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_WAREHOUSES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_WAREHOUSES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_TYPES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_TYPES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_TYPES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_TYPES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_VEHICLES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_VEHICLES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_VEHICLES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_VEHICLES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_SHIPMENT_STATUS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_SHIPMENT_STATUS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_SHIPMENT_STATUS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_SHIPMENT_STATUS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_SHIPMENTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_SHIPMENTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_SHIPMENTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_SHIPMENTS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_RATINGS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_RATINGS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_RATINGS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_RATINGS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_PARCELS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_PARCELS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_PARCELS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_PARCELS',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_INVOICES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_INVOICES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_INVOICES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_INVOICES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('VIEW_CATEGORIES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('DELETE_CATEGORIES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('CREATE_CATEGORIES',  CURRENT_TIMESTAMP, FALSE);
INSERT INTO privileges (name,  created_at, is_deleted)
VALUES ('UPDATE_CATEGORIES',  CURRENT_TIMESTAMP, FALSE);


INSERT INTO role_privilege_items (privilege_id, role_id)
SELECT privilege_id, (SELECT role_id FROM roles WHERE name = 'ROLE_ADMIN') AS role_id FROM privileges;
INSERT INTO role_privilege_items (privilege_id, role_id)
SELECT privilege_id, (SELECT role_id FROM roles WHERE name = 'ROLE_MANAGER') AS role_id FROM privileges
WHERE name IN ('CREATE_ACCOUNTS', 'DELETE_ACCOUNTS', 'UPDATE_ACCOUNTS', 'VIEW_ACCOUNTS', 'JOIN_ACCOUNTS',
	'CREATE_PREFERENCES', 'DELETE_PREFERENCES', 'UPDATE_PREFERENCES', 'VIEW_PREFERENCES', 'VIEW_GENDERS',
	'CREATE_PAYMENTS', 'CREATE_REFUNDS', 'DELETE_PAYMENTS', 'UPDATE_PAYMENTS', 'VIEW_PAYMENTS', 
	'CREATE_PAYOUTS', 'DELETE_PAYOUTS', 'UPDATE_PAYOUTS', 'VIEW_PAYOUTS', 'CREATE_USERS', 'DELETE_USERS',
	'UPDATE_USERS', 'VIEW_USERS', 'CREATE_COMMUNICATIONS', 'DELETE_COMMUNICATIONS', 'UPDATE_COMMUNICATIONS',
	'VIEW_COMMUNICATIONS', 'CREATE_CONVERSATIONS', 'DELETE_CONVERSATIONS', 'UPDATE_CONVERSATIONS',
	'VIEW_CONVERSATIONS', 'CREATE_COMMUNICATION_STATUS', 'UPDATE_COMMUNICATION_STATUS', 'VIEW_COMMUNICATION_STATUS',
	'CREATE_DISTRICTS', 'DELETE_DISTRICTS', 'UPDATE_DISTRICTS', 'VIEW_DISTRICTS', 'CREATE_REGIONS',
	'DELETE_REGIONS', 'UPDATE_REGIONS', 'VIEW_REGIONS', 'CREATE_VILLAGES', 'DELETE_VILLAGES',
	'UPDATE_VILLAGES', 'VIEW_VILLAGES', 'CREATE_WAREHOUSES', 'DELETE_WAREHOUSES', 'UPDATE_WAREHOUSES',
	'VIEW_WAREHOUSES', 'VIEW_TYPES', 'DELETE_TYPES', 'CREATE_TYPES', 'UPDATE_TYPES', 'VIEW_VEHICLES',
	'DELETE_VEHICLES', 'CREATE_VEHICLES', 'UPDATE_VEHICLES', 'VIEW_SHIPMENT_STATUS', 'DELETE_STATUS',
	'CREATE_STATUS', 'UPDATE_STATUS', 'VIEW_SHIPMENTS', 'DELETE_SHIPMENTS', 'CREATE_SHIPMENTS','UPDATE_SHIPMENTS',
	'VIEW_RATINGS', 'DELETE_RATINGS', 'CREATE_RATINGS','UPDATE_RATINGS', 'VIEW_PARCELS', 'DELETE_PARCELS', 
	'CREATE_PARCELS','UPDATE_PARCELS', 'VIEW_CATEGORIES', 'DELETE_CATEGORIES', 'CREATE_CATEGORIES','UPDATE_CATEGORIES',
	'VIEW_INVOICES', 'DELETE_INVOICES', 'CREATE_INVOICES','UPDATE_INVOICES');
INSERT INTO role_privilege_items (privilege_id, role_id)
SELECT privilege_id, (SELECT role_id FROM roles WHERE name = 'ROLE_CLIENT') AS role_id FROM privileges
WHERE name IN ('JOIN_ACCOUNTS', 'UPDATE_PREFERENCES', 'VIEW_PREFERENCES', 'VIEW_GENDERS', 'CREATE_PAYMENTS',
	'VIEW_PAYMENTS', 'CREATE_PAYOUTS', 'VIEW_PAYOUTS', 'CREATE_USERS', 'UPDATE_USERS', 'VIEW_USERS',
	'CREATE_COMMUNICATIONS', 'VIEW_COMMUNICATIONS', 'CREATE_CONVERSATIONS', 'DELETE_CONVERSATIONS',
	'CREATE_COMMUNICATION_STATUS', 'UPDATE_COMMUNICATION_STATUS', 'VIEW_COMMUNICATION_STATUS', 
	'UPDATE_CONVERSATIONS', 'VIEW_CONVERSATIONS', 'VIEW_DISTRICTS', 'VIEW_REGIONS', 'VIEW_VILLAGES',
	'VIEW_WAREHOUSES', 'VIEW_SHIPMENT_STATUS', 'CREATE_SHIPMENTS', 'UPDATE_SHIPMENTS', 'VIEW_SHIPMENTS',
	'VIEW_RATINGS', 'CREATE_RATINGS','UPDATE_RATINGS', 'VIEW_PARCELS', 'CREATE_PARCELS','UPDATE_PARCELS',
	'VIEW_CATEGORIES', 'CREATE_INVOICES', 'VIEW_INVOICES');
INSERT INTO role_privilege_items (privilege_id, role_id)
SELECT privilege_id, (SELECT role_id FROM roles WHERE name = 'ROLE_COURIER') AS role_id FROM privileges
WHERE name IN ('JOIN_ACCOUNTS', 'UPDATE_PREFERENCES', 'VIEW_PREFERENCES', 'VIEW_GENDERS', 'CREATE_PAYOUTS',
	'VIEW_PAYOUTS', 'CREATE_USERS', 'UPDATE_USERS', 'VIEW_USERS', 'CREATE_COMMUNICATIONS', 'VIEW_COMMUNICATIONS',
	'CREATE_CONVERSATIONS', 'DELETE_CONVERSATIONS', 'UPDATE_CONVERSATIONS', 'VIEW_CONVERSATIONS', 
	'CREATE_COMMUNICATION_STATUS', 'UPDATE_COMMUNICATION_STATUS', 'VIEW_COMMUNICATION_STATUS', 'VIEW_DISTRICTS',
	'VIEW_REGIONS', 'VIEW_VILLAGES', 'VIEW_TYPES', 'VIEW_VEHICLES', 'DELETE_VEHICLES', 'CREATE_VEHICLES',
	'UPDATE_VEHICLES', 'VIEW_SHIPMENT_STATUS', 'UPDATE_SHIPMENTS', 'VIEW_SHIPMENTS');


INSERT INTO account_role_items (account_id, role_id)
VALUES (1, 1);
INSERT INTO account_role_items (account_id, role_id)
VALUES (2, 2);
INSERT INTO account_role_items (account_id, role_id)
VALUES (3, 3);
INSERT INTO account_role_items (account_id, role_id)
VALUES (4, 4);


INSERT INTO preferences (name)
VALUES ('Cena prepravy (eur/1km)');


INSERT INTO account_preference_items (account_id, preference_id, value, created_at, is_deleted)
VALUES (5, 1, 3.50, CURRENT_TIMESTAMP, FALSE);