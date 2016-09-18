INSERT INTO member_user(email, username) VALUES('mail.com', 'user_1');
INSERT INTO member_user(email, username) VALUES('mail.com', 'user_2');
INSERT INTO member_user(email, username) VALUES('mail.com', 'user_3');
INSERT INTO member_user(email, username) VALUES('mail.com', 'user_4');

INSERT INTO member(email, username, password) VALUES('mail_1.com', 'user_1', 'password');
INSERT INTO member(email, username, password) VALUES('mail_2.com', 'user_2', 'password');
INSERT INTO member(email, username, password) VALUES('mail_3.com', 'user_3', 'password');
INSERT INTO member(email, username, password) VALUES('mail_4.com', 'user_4', 'password');

INSERT INTO board(title, content, create_date, member_id) VALUES('title_1', 'content', PARSEDATETIME('2000-12-12 11:34:24','yyyy-MM-dd hh:mm:ss'), 1);
INSERT INTO board(title, content, create_date, member_id) VALUES('title_2', 'content', PARSEDATETIME('2000-12-12 11:34:24','yyyy-MM-dd hh:mm:ss'), 2);
INSERT INTO board(title, content, create_date, member_id) VALUES('title_3', 'content', {ts '2000-12-12 11:22:33'}, 3);
INSERT INTO board(title, content, create_date, member_id) VALUES('title_4', 'content', {ts '2000-12-12 11:22:33'}, 4);

INSERT INTO comment(content, create_date, board_id) VALUES('content_1', PARSEDATETIME('2000-12-12 11:34:24','yyyy-MM-dd hh:mm:ss'), 1);
INSERT INTO comment(content, create_date, board_id) VALUES('content_2', PARSEDATETIME('2000-12-12 11:34:24','yyyy-MM-dd hh:mm:ss'), 2);
INSERT INTO comment(content, create_date, board_id) VALUES('content_3', {ts '2000-12-12 11:22:33'}, 3);
INSERT INTO comment(content, create_date, board_id) VALUES('content_4', {ts '2000-12-12 11:22:33'}, 4);