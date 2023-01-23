set client_encoding to 'utf8';

insert into roles (id, name, date_created, date_modified)
values (1, 'ROLE_USER', '2022-10-20 15:14:05.170022', '2022-10-20 15:14:05.170022'),
       (2, 'ROLE_ADMIN', '2022-10-20 15:14:05.170022', '2022-10-20 15:14:05.170022'),
       (3, 'ROLE_MODERATOR', '2022-10-20 15:14:05.170022', '2022-10-20 15:14:05.170022'),
       (4, 'ROLE_ANONYMOUS', '2022-10-20 15:14:05.170022', '2022-10-20 15:14:05.170022');
select setval('roles_id_seq', 4);

insert into instruments (id, name)
values (1, 'VOCALS'),
       (2, 'GUITAR'),
       (3, 'BASS'),
       (4, 'DRUMS'),
       (5, 'SYNTH');
select setval('instruments_id_seq', 5);

insert into experience (id, name)
values (1, 'BEGINNER'),
       (2, 'AMATEUR'),
       (3, 'PRO');
select setval('experience_id_seq', 3);

insert into reaction_type (id, name)
values (1, 'NOT_SPECFIED'),
       (2, 'LIKE'),
       (3, 'DISLIKE');
select setval('reaction_type_id_seq', 3);

insert into accounts (id, email, password, is_locked, date_created, date_modified)
values (1,'test@gmail.com','$2a$06$8VfqUJlk1xdJJkzsZ8fmXOL7vr5o7XUgz0kHqwU/IsNDTYk84glOy',false,'2023-01-20 20:43:48.699000','2023-01-20 20:43:48.699000'),
       (2,'testcontainer@gmail.com','$2a$06$8VfqUJlk1xdJJkzsZ8fmXOL7vr5o7XUgz0kHqwU/IsNDTYk84glOy',false,'2023-01-20 20:43:48.699000','2023-01-20 20:43:48.699000');
select setval('accounts_id_seq', 2);

insert into l_account_roles (id, account_id, role_id)
values (1,1,1),
       (2,1,2),
       (3,1,4),
       (4,2,1),
       (5,2,2),
       (6,2,4);
select setval('l_account_roles_id_seq', 6);
