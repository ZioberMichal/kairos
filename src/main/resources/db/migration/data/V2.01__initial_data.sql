INSERT INTO permission VALUES (1, 'assets_read','admin', NOW(), 'admin', NOW());
INSERT INTO permission VALUES (4, 'assets_update','admin', NOW(), 'admin', NOW());
INSERT INTO permission VALUES (7, 'assets_delete','admin', NOW(), 'admin', NOW());

INSERT INTO permission VALUES (3, 'departments_read','admin', NOW(), 'admin', NOW());
INSERT INTO permission VALUES (6, 'departments_update','admin', NOW(), 'admin', NOW());
INSERT INTO permission VALUES (9, 'departments_delete','admin', NOW(), 'admin', NOW());

INSERT INTO permission VALUES (17, 'employees_read','admin', NOW(), 'admin', NOW());
INSERT INTO permission VALUES (18, 'employees_update','admin', NOW(), 'admin', NOW());
INSERT INTO permission VALUES (19, 'employees_delete','admin', NOW(), 'admin', NOW());

INSERT INTO permission VALUES (20, 'admin_only','admin', NOW(), 'admin', NOW());

INSERT INTO role VALUES (1, 'Admin','admin','admin', NOW(), 'admin', NOW());
INSERT INTO role VALUES (3, 'Editor','editor', 'admin', NOW(), 'admin', NOW());
INSERT INTO role VALUES (4, 'Reader','read_only','admin', NOW(), 'admin', NOW());

INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r, permission p
WHERE r.code = 'admin';

INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r, permission p
WHERE r.code = 'editor' and p.name != 'admin_only';

INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r, permission p
WHERE r.code = 'read_only' AND p.name LIKE '%_read';