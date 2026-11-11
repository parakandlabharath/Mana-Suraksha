-- Mana Suraksha initial MySQL schema proposal.
-- Review and version with migrations before production use.
CREATE DATABASE IF NOT EXISTS mana_suraksha
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE mana_suraksha;

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    display_name VARCHAR(100) NOT NULL,
    preferred_language VARCHAR(5) NOT NULL DEFAULT 'te',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE analysis_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NULL,
    input_type VARCHAR(20) NOT NULL,
    risk_level VARCHAR(20) NOT NULL,
    risk_score TINYINT UNSIGNED NULL,
    policy_version VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_analysis_user_created (user_id, created_at)
);

CREATE TABLE analysis_findings (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    analysis_id BIGINT NOT NULL,
    rule_code VARCHAR(80) NOT NULL,
    severity TINYINT UNSIGNED NOT NULL,
    explanation_code VARCHAR(100) NOT NULL,
    FOREIGN KEY (analysis_id) REFERENCES analysis_history(id) ON DELETE CASCADE,
    INDEX idx_finding_analysis (analysis_id)
);

CREATE TABLE threat_indicators (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    indicator_type VARCHAR(30) NOT NULL,
    indicator_hash CHAR(64) NOT NULL,
    verification_status VARCHAR(30) NOT NULL,
    source_type VARCHAR(30) NOT NULL,
    source_reference VARCHAR(255) NULL,
    first_seen_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_verified_at TIMESTAMP NULL,
    expires_at TIMESTAMP NULL,
    UNIQUE KEY uq_indicator_type_hash (indicator_type, indicator_hash),
    INDEX idx_indicator_expiry (expires_at)
);

CREATE TABLE fraud_reports (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    reporter_user_id BIGINT NOT NULL,
    category VARCHAR(40) NOT NULL,
    description_redacted TEXT NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'SUBMITTED',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (reporter_user_id) REFERENCES users(id),
    INDEX idx_report_status_created (status, created_at),
    INDEX idx_report_user_created (reporter_user_id, created_at)
);

CREATE TABLE report_evidence (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    report_id BIGINT NOT NULL,
    evidence_type VARCHAR(30) NOT NULL,
    secure_object_reference VARCHAR(500) NOT NULL,
    content_hash CHAR(64) NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (report_id) REFERENCES fraud_reports(id) ON DELETE CASCADE
);

CREATE TABLE report_votes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    report_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    vote_type VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (report_id) REFERENCES fraud_reports(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id),
    UNIQUE KEY uq_report_user_vote (report_id, user_id)
);

CREATE TABLE moderation_actions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    report_id BIGINT NOT NULL,
    moderator_user_id BIGINT NOT NULL,
    action VARCHAR(30) NOT NULL,
    reason_code VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (report_id) REFERENCES fraud_reports(id),
    FOREIGN KEY (moderator_user_id) REFERENCES users(id),
    INDEX idx_moderation_report_created (report_id, created_at)
);

CREATE TABLE audit_events (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    actor_user_id BIGINT NULL,
    event_type VARCHAR(80) NOT NULL,
    target_type VARCHAR(40) NULL,
    target_id VARCHAR(100) NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (actor_user_id) REFERENCES users(id),
    INDEX idx_audit_created (created_at),
    INDEX idx_audit_target (target_type, target_id)
);

INSERT INTO roles (role_name) VALUES ('CITIZEN'), ('MODERATOR'), ('ADMIN');
