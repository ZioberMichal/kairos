-- Flyway migration: create password_reset_token table

CREATE TABLE IF NOT EXISTS password_reset_token (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  token VARCHAR(255) NOT NULL UNIQUE,
  user_id BIGINT NOT NULL,
  expires_at DATETIME NOT NULL,
  used BOOLEAN NOT NULL DEFAULT FALSE,
  INDEX idx_password_reset_token_token (token),
  CONSTRAINT fk_password_reset_user FOREIGN KEY (user_id) REFERENCES `user` (id)
);
