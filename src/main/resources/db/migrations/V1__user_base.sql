Hibernate: create table clients (id int8 not null, created_at timestamp not null, updated_at timestamp not null, created_by int8, update_by int8, date_of_birth timestamp, date_of_registration timestamp, email varchar(40), first_name varchar(255), is_active boolean not null, last_name varchar(255), phone int8 not null, user_id int8 not null, primary key (id))
  Hibernate: create table manager (id int8 not null, created_at timestamp not null, updated_at timestamp not null, created_by int8, update_by int8, blocked_at timestamp, first_name varchar(255), is_active boolean not null, last_name varchar(255), personal_page varchar(255), uniq_id varchar(255) not null, blocked_by int8, user_id int8 not null, primary key (id))
  Hibernate: create table message (id int8 not null, close timestamp, created timestamp, is_active boolean not null, message varchar(2048), service_comment varchar(255), primary key (id))
  Hibernate: create table roles (id  bigserial not null, name varchar(60), primary key (id))
  Hibernate: create table todo (id int8 not null, created_at timestamp not null, updated_at timestamp not null, created_by int8, update_by int8, is_done boolean not null, message varchar(255), parent_id int8, primary key (id))
  Hibernate: create table user_roles (user_id int8 not null, role_id int8 not null, primary key (user_id, role_id))
  Hibernate: create table usr (id  bigserial not null, created_at timestamp not null, updated_at timestamp not null, email varchar(40), password varchar(100), username varchar(15), primary key (id))
  Hibernate: alter table if exists clients drop constraint if exists UKe3it7h0veoeergtkfqxhos5qj
2018-10-10 16:33:52.290  WARN 26018 --- [ost-startStop-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Warning Code: 0, SQLState: 00000
2018-10-10 16:33:52.290  WARN 26018 --- [ost-startStop-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : constraint "uke3it7h0veoeergtkfqxhos5qj" of relation "clients" does not exist, skipping
Hibernate: alter table if exists clients add constraint UKe3it7h0veoeergtkfqxhos5qj unique (phone)
Hibernate: alter table if exists clients drop constraint if exists UKsrv16ica2c1csub334bxjjb59
2018-10-10 16:33:52.345  WARN 26018 --- [ost-startStop-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Warning Code: 0, SQLState: 00000
2018-10-10 16:33:52.346  WARN 26018 --- [ost-startStop-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : constraint "uksrv16ica2c1csub334bxjjb59" of relation "clients" does not exist, skipping
Hibernate: alter table if exists clients add constraint UKsrv16ica2c1csub334bxjjb59 unique (email)
Hibernate: alter table if exists clients drop constraint if exists UKsmrp6gi0tckq1w5rnd7boyowu
2018-10-10 16:33:52.401  WARN 26018 --- [ost-startStop-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Warning Code: 0, SQLState: 00000
2018-10-10 16:33:52.401  WARN 26018 --- [ost-startStop-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : constraint "uksmrp6gi0tckq1w5rnd7boyowu" of relation "clients" does not exist, skipping
Hibernate: alter table if exists clients add constraint UKsmrp6gi0tckq1w5rnd7boyowu unique (user_id)
Hibernate: alter table if exists manager drop constraint if exists UK4vbgsjl6mcxrqyvts0hlilhob
2018-10-10 16:33:52.446  WARN 26018 --- [ost-startStop-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Warning Code: 0, SQLState: 00000
2018-10-10 16:33:52.447  WARN 26018 --- [ost-startStop-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : constraint "uk4vbgsjl6mcxrqyvts0hlilhob" of relation "manager" does not exist, skipping
Hibernate: alter table if exists manager add constraint UK4vbgsjl6mcxrqyvts0hlilhob unique (user_id)
Hibernate: alter table if exists manager drop constraint if exists UKjfojjgivblwwpkxeml4p74vg6
2018-10-10 16:33:52.501  WARN 26018 --- [ost-startStop-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Warning Code: 0, SQLState: 00000
2018-10-10 16:33:52.502  WARN 26018 --- [ost-startStop-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : constraint "ukjfojjgivblwwpkxeml4p74vg6" of relation "manager" does not exist, skipping
Hibernate: alter table if exists manager add constraint UKjfojjgivblwwpkxeml4p74vg6 unique (uniq_id)
Hibernate: alter table if exists roles drop constraint if exists UK_nb4h0p6txrmfc0xbrd1kglp9t
2018-10-10 16:33:52.544  WARN 26018 --- [ost-startStop-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Warning Code: 0, SQLState: 00000
2018-10-10 16:33:52.545  WARN 26018 --- [ost-startStop-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : constraint "uk_nb4h0p6txrmfc0xbrd1kglp9t" of relation "roles" does not exist, skipping
Hibernate: alter table if exists roles add constraint UK_nb4h0p6txrmfc0xbrd1kglp9t unique (name)
Hibernate: alter table if exists usr drop constraint if exists UKdfui7gxngrgwn9ewee3ogtgym
2018-10-10 16:33:52.589  WARN 26018 --- [ost-startStop-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Warning Code: 0, SQLState: 00000
2018-10-10 16:33:52.589  WARN 26018 --- [ost-startStop-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : constraint "ukdfui7gxngrgwn9ewee3ogtgym" of relation "usr" does not exist, skipping
Hibernate: alter table if exists usr add constraint UKdfui7gxngrgwn9ewee3ogtgym unique (username)
Hibernate: alter table if exists usr drop constraint if exists UKg9l96r670qkidthshajdtxrqf
2018-10-10 16:33:52.645  WARN 26018 --- [ost-startStop-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Warning Code: 0, SQLState: 00000
2018-10-10 16:33:52.646  WARN 26018 --- [ost-startStop-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : constraint "ukg9l96r670qkidthshajdtxrqf" of relation "usr" does not exist, skipping
Hibernate: alter table if exists usr add constraint UKg9l96r670qkidthshajdtxrqf unique (email)
Hibernate: create sequence hibernate_sequence start 1 increment 1
  Hibernate: alter table if exists clients add constraint FKftumenks1hgnvw1s28buhigv1 foreign key (user_id) references usr
Hibernate: alter table if exists manager add constraint FK8xkma12g8r2dtjk72e4kgo9s3 foreign key (blocked_by) references usr
Hibernate: alter table if exists manager add constraint FKtaeclae02lgoouc2bvbe9npvm foreign key (user_id) references usr
Hibernate: alter table if exists todo add constraint FKr77ihun6awhsbvcmtvuauuk0r foreign key (parent_id) references todo
Hibernate: alter table if exists user_roles add constraint FKh8ciramu9cc9q3qcqiv4ue8a6 foreign key (role_id) references roles
Hibernate: alter table if exists user_roles add constraint FKg6agnwreityp2vf23bm2jgjm3 foreign key (user_id) references usr
