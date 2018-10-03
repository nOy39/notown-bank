
create table roles (
  id  bigserial not null,
  name varchar(60),
  primary key (id)
);

create table user_roles (
  user_id int8 not null,
  role_id int8 not null,
  primary key (user_id, role_id)
);

create table usr (
  id  bigserial not null,
  created_at timestamp not null,
  updated_at timestamp not null,
  email varchar(40),
  password varchar(100),
  username varchar(15),
  primary key (id)
);
create sequence hibernate_sequence start 1 increment 1;
alter table if
  exists roles drop constraint
  if exists roles_constraint;
alter table if
  exists roles add constraint
  roles_constraint unique (name);

alter table if
  exists usr drop constraint
  if exists usr_username_constraint;
alter table if
  exists usr add constraint
  usr_username_constrant unique (username);

alter table if
  exists usr drop constraint
  if exists usr_email_constraint;
alter table if
  exists usr add constraint
  usr_email_constrant unique (email);

alter table if
  exists user_roles
  add constraint user_roles_role_id_constraint
  foreign key (role_id)
  references roles;

alter table if
  exists user_roles
  add constraint user_roles_user_id_constraint
  foreign key (user_id)
  references usr;
