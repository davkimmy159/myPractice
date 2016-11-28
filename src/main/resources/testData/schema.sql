DROP TABLE member IF EXISTS;
DROP TABLE board IF EXISTS;
DROP TABLE comment IF EXISTS;
DROP TABLE authority IF EXISTS;
DROP TABLE member_authority IF EXISTS;

CREATE TABLE IF NOT EXISTS member (
	member_id BIGINT AUTO_INCREMENT NOT NULL,
	email VARCHAR(100) NOT NULL,
	username VARCHAR(100) NOT NULL,
	password VARCHAR(200) NOT NULL,
	enabled BOOLEAN NOT NULL DEFAULT true,
	CONSTRAINT member_member_id_pk PRIMARY KEY(member_id),
	CONSTRAINT member_email_uk UNIQUE KEY(email),
	CONSTRAINT member_username_uk UNIQUE KEY(username)
);

CREATE TABLE IF NOT EXISTS authority (
	authority_id INT AUTO_INCREMENT NOT NULL,
	authority VARCHAR(50) NOT NULL,
	CONSTRAINT authority_authority_id_pk PRIMARY KEY(authority_id),
	CONSTRAINT authority_authority_uk UNIQUE KEY(authority)
);

CREATE TABLE IF NOT EXISTS member_authority (
	member_authority_id BIGINT AUTO_INCREMENT NOT NULL,
	member_id BIGINT NOT NULL,
	authority_id INT NOT NULL,
	CONSTRAINT member_authority_member_id_authority_id_uk UNIQUE KEY(member_id, authority_id),
	CONSTRAINT member_authority_member_id_fk FOREIGN KEY(member_id) REFERENCES member(member_id),
	CONSTRAINT member_authority_authority_id_fk FOREIGN KEY(authority_id) REFERENCES authority(authority_id)
);

CREATE TABLE IF NOT EXISTS board (
	board_id BIGINT AUTO_INCREMENT NOT NULL,
	title VARCHAR(600) NOT NULL,
	content CLOB NOT NULL,
	create_date TIMESTAMP NOT NULL,
	last_update_date TIMESTAMP NOT NULL,
	update_count BIGINT NOT NULL DEFAULT 0,
	hit_count BIGINT NOT NULL DEFAULT 0,
	member_id BIGINT NOT NULL,
	CONSTRAINT board_board_id_pk PRIMARY KEY(board_id),
	CONSTRAINT board_member_id_fk FOREIGN KEY(member_id) REFERENCES member(member_id)
);

CREATE TABLE IF NOT EXISTS memo (
	memo_id BIGINT AUTO_INCREMENT NOT NULL,
	title VARCHAR(600) NOT NULL,
	content CLOB NOT NULL,
	create_date TIMESTAMP NOT NULL,
	last_update_date TIMESTAMP NOT NULL,
	member_id BIGINT NOT NULL,
	board_id BIGINT NOT NULL,
	CONSTRAINT memo_memo_id_pk PRIMARY KEY(memo_id),
	CONSTRAINT memo_member_id_fk FOREIGN KEY(member_id) REFERENCES member(member_id),
	CONSTRAINT memo_board_id_fk FOREIGN KEY(board_id) REFERENCES board(board_id)
);
