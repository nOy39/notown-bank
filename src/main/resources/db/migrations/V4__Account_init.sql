create table account (
  id             int8         not null,
  created_at     timestamp    not null,
  updated_at     timestamp    not null,
  created_by     int8,
  update_by      int8,
  account_number varchar(255) not null,
  bank_code      varchar(255),
  is_blocked     boolean      not null,
  is_default     boolean      not null,
  personal_code  varchar(255),
  secret_code    varchar(255),
  sum            numeric(19, 2),
  client_id      int8         not null,
  currency_id    int8,
  sub_type_id    int8,
  type_id        int8,
  primary key (id)
);

create table cards (
  id          int8         not null,
  card_number int8         not null,
  created     timestamp    not null,
  cvv         varchar(255) not null,
  expired     timestamp    not null,
  secret_pin  varchar(255) not null,
  account_id  int8,
  primary key (id)
);

create table eav_currency (
  id            int8 not null,
  currency_code varchar(255),
  currency_name varchar(255),
  primary key (id)
);

create table eav_subtype (
  id            int8 not null,
  sub_type_code varchar(255),
  sub_type_name varchar(255),
  primary key (id)
);

create table eav_type (
  id        int8 not null,
  type_code varchar(255),
  type_name varchar(255),
  primary key (id)
);

create table history (
  id                int8 not null,
  created_at        timestamp,
  incoming_sum      numeric(19, 2),
  outgoing_sum      numeric(19, 2),
  main_account      int8 not null,
  secondary_account int8 not null,
  user_id           int8,
  primary key (id)
);

alter table if exists account
  drop constraint if exists account_account_number_c;
alter table if exists account
  add constraint account_account_number_c unique (account_number);

alter table if exists cards
  drop constraint if exists cards_card_number_c;
alter table if exists cards
  add constraint cards_card_number_c unique (card_number);

alter table if exists eav_currency
  drop constraint if exists eav_currency_curr_name_c;
alter table if exists eav_currency
  add constraint eav_currency_curr_name_c unique (currency_name);

alter table if exists eav_currency
  drop constraint if exists eav_currency_curr_code_c;
alter table if exists eav_currency
  add constraint eav_currency_curr_code_c unique (currency_code);

alter table if exists account
  add constraint acc_client_id_fk foreign key (client_id) references clients;

alter table if exists account
  add constraint acc_curr_id_fk foreign key (currency_id) references eav_currency;

alter table if exists account
  add constraint acc_sub_type_id_fk foreign key (sub_type_id) references eav_subtype;

alter table if exists account
  add constraint acc_type_id_fk foreign key (type_id) references eav_type;

alter table if exists cards
  add constraint cards_acc_id_fk foreign key (account_id) references account;

alter table if exists history
  add constraint history_acc_id_fk foreign key (main_account) references account;

alter table if exists history
  add constraint history_secondary_account_fk foreign key (secondary_account) references account;

alter table if exists history
  add constraint history_user_id_fk foreign key (user_id) references usr;
