create table manager (
  id            int8         not null,
  created_at    timestamp    not null,
  updated_at    timestamp    not null,
  created_by    int8,
  update_by     int8,
  blocked_at    timestamp,
  first_name    varchar(255),
  is_active     boolean      not null,
  last_name     varchar(255),
  personal_page varchar(255),
  uniq_id       varchar(255) not null,
  blocked_by    int8,
  user_id       int8         not null,
  primary key (id)
);

create table message (
  id              int8    not null,
  close           timestamp,
  created         timestamp,
  is_active       boolean not null,
  message         varchar(2048),
  service_comment varchar(255),
  primary key (id)
);

create table roles (
  id   bigserial not null,
  name varchar(60),
  primary key (id)
);

create table todo (
  id         int8      not null,
  created_at timestamp not null,
  updated_at timestamp not null,
  created_by int8,
  update_by  int8,
  is_done    boolean   not null,
  message    varchar(255),
  parent_id  int8,
  primary key (id)
);

create table user_roles (
  user_id int8 not null,
  role_id int8 not null,
  primary key (user_id, role_id)
);

create table usr (
  id         bigserial not null,
  created_at timestamp not null,
  updated_at timestamp not null,
  email      varchar(40),
  password   varchar(100),
  username   varchar(15),
  primary key (id)
);

alter table if exists manager
  drop constraint if exists manager_user_id_constraints;

alter table if exists manager
  add constraint manager_user_id_constraints unique (user_id);

alter table if exists manager
  drop constraint if exists manager_uniq_id_constraints;

alter table if exists manager
  add constraint manager_uniq_id_constraints unique (uniq_id);

alter table if exists roles
  drop constraint if exists roles_name_constraints;

alter table if exists roles
  add constraint roles_name_constraints unique (name);

alter table if exists usr
  drop constraint if exists usr_username_constraint;

alter table if exists usr
  add constraint usr_username_constraint unique (username);

alter table if exists usr
  drop constraint if exists usr_email_constraint;

alter table if exists usr
  add constraint usr_email_constraint unique (email);

create sequence hibernate_sequence
  start 1
  increment 1;

alter table if exists manager
  add constraint manager_blocked_by_fk foreign key (blocked_by) references usr;

alter table if exists manager
  add constraint manager_user_id_fk foreign key (user_id) references usr;

alter table if exists todo
  add constraint todo_parent_id_fk foreign key (parent_id) references todo;

alter table if exists user_roles
  add constraint user_role_id_fk foreign key (role_id) references roles;

alter table if exists user_roles
  add constraint user_roles_user_id_fk foreign key (user_id) references usr;
