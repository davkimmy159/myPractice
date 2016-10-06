INSERT INTO member(email, username, password, enabled) VALUES('mail_1@mail.com', 'user_1', 'password', true);
INSERT INTO member(email, username, password, enabled) VALUES('mail_2@mail.com', 'user_2', 'password', true);

INSERT INTO board(title, content, create_date, member_id) VALUES('title_1', 'content', PARSEDATETIME('2000-12-12 11:34:24','yyyy-MM-dd hh:mm:ss'), 1);
INSERT INTO board(title, content, create_date, member_id) VALUES('title_2', 'content', PARSEDATETIME('2000-12-12 11:34:24','yyyy-MM-dd hh:mm:ss'), 1);
INSERT INTO board(title, content, create_date, member_id) VALUES('title_3', 'content', {ts '2000-12-12 11:22:33'}, 2);
INSERT INTO board(title, content, create_date, member_id) VALUES('title_4', 'content', {ts '2000-12-12 11:22:33'}, 2);

INSERT INTO comment(content, create_date, board_id) VALUES('content_1', PARSEDATETIME('2000-12-12 11:34:24','yyyy-MM-dd hh:mm:ss'), 1);
INSERT INTO comment(content, create_date, board_id) VALUES('content_2', PARSEDATETIME('2000-12-12 11:34:24','yyyy-MM-dd hh:mm:ss'), 1);
INSERT INTO comment(content, create_date, board_id) VALUES('content_3', {ts '2000-12-12 11:22:33'}, 2);
INSERT INTO comment(content, create_date, board_id) VALUES('content_4', {ts '2000-12-12 11:22:33'}, 2);

INSERT INTO authority(authority) VALUES('role_member');
INSERT INTO authority(authority) VALUES('role_admin');

INSERT INTO member_authority(member_id, authority_id) VALUES(1, 1);
INSERT INTO member_authority(member_id, authority_id) VALUES(1, 2);
INSERT INTO member_authority(member_id, authority_id) VALUES(2, 1);
