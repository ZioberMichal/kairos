INSERT INTO user VALUES (1, 'admin', '$2a$12$qITMWLA28LleM4nRI241Eeiccj1I0if5khLKwFNIIlJfwJaVlqD2G','admin', NOW(), 'admin', NOW());
INSERT INTO user VALUES (2, 'editor', '$2a$12$bSEUdVZ/VCGlOwcImLAUteq19dpSNe0ravnPjfCFc/Bn6WveUsq9q','admin', NOW(), 'admin', NOW());
INSERT INTO user VALUES (6, 'reader', '$2a$12$1kWyidTc.OGfkRkfYZUqaex9o1YmjH3wp7Sk/XgdBWYASz0Elg/nG','admin', NOW(), 'admin', NOW());

INSERT INTO user_role (user_id, role_id)
SELECT u.id, r.id
FROM user u, role r
WHERE u.username = 'admin' and r.code IN ('admin');

INSERT INTO user_role (user_id, role_id)
SELECT u.id, r.id
FROM user u, role r
WHERE u.username LIKE 'editor' and r.code IN ('editor');

INSERT INTO user_role (user_id, role_id)
SELECT u.id, r.id
FROM user u, role r
WHERE u.username LIKE 'reader' and r.code IN ('read_only');