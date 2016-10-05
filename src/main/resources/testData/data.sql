INSERT INTO member(email, username, password) VALUES('1@mail.com', 'user_1', 'password');
INSERT INTO member(email, username, password) VALUES('2@mail.com', 'user_2', 'password');
INSERT INTO member(email, username, password) VALUES('3@mail.com', 'user_3', 'password');
INSERT INTO member(email, username, password) VALUES('4@mail.com', 'user_4', 'password');

INSERT INTO board(title, content, create_date, member_id) VALUES('title_1', 'content', PARSEDATETIME('2000-12-12 11:34:24','yyyy-MM-dd hh:mm:ss'), 1);
INSERT INTO board(title, content, create_date, member_id) VALUES('title_2', 'content', PARSEDATETIME('2000-12-12 11:34:24','yyyy-MM-dd hh:mm:ss'), 2);
INSERT INTO board(title, content, create_date, member_id) VALUES('title_3', 'content', {ts '2000-12-12 11:22:33'}, 3);
INSERT INTO board(title, content, create_date, member_id) VALUES('title_4', 'content', {ts '2000-12-12 11:22:33'}, 4);

INSERT INTO comment(content, create_date, board_id) VALUES('content_1', PARSEDATETIME('2000-12-12 11:34:24','yyyy-MM-dd hh:mm:ss'), 1);
INSERT INTO comment(content, create_date, board_id) VALUES('content_2', PARSEDATETIME('2000-12-12 11:34:24','yyyy-MM-dd hh:mm:ss'), 2);
INSERT INTO comment(content, create_date, board_id) VALUES('content_3', {ts '2000-12-12 11:22:33'}, 3);
INSERT INTO comment(content, create_date, board_id) VALUES('content_4', {ts '2000-12-12 11:22:33'}, 4);