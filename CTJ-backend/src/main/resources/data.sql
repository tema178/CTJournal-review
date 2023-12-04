--insert into GRADES (`french`, `uiaa`, `usa`, `norway`, `australian`, `south_africa`)
insert into GRADES (`french`)
values
('4'),
('5a'),
('5b'),
('5c'),
('6a'),
('6a+'),
('6b'),
('6b+'),
('6c'),
('6c+'),
('7a'),
('7a+'),
('7b'),
('7b+'),
('7c'),
('7c+'),
('8a'),
('8a+'),
('8b'),
('8b+'),
('8c'),
('8c+'),
('9a'),
('9a+'),
('9b'),
('9b+'),
('9c');


insert into users (username, password, enabled)
values ('user', '$2a$10$p6fZvEXrndwlIXrL1uJT3et2Hyb4YyqRsXwivJwct7pf8lYsCTc7q', true);

insert into authorities (username, authority)
values ('user', 'ROLE_USER');

insert into users (username, password, enabled)
values ('admin', '$2a$10$am/tOvXSUuV6wgosthrFGO6n8caIgNobipmr0ZhJ3Xf3q1q5LDR42', true);

insert into authorities (username, authority)
values ('admin', 'ROLE_ADMIN');

insert into users (username, password, enabled)
values ('manager', '$2a$10$am/tOvXSUuV6wgosthrFGO6n8caIgNobipmr0ZhJ3Xf3q1q5LDR42', true);

insert into authorities (username, authority)
values ('manager', 'ROLE_MANAGER');
