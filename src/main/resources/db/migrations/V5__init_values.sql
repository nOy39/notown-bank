INSERT INTO "public"."eav_type" ("id", "type_code", "type_name")
VALUES (1, '408', 'DEBIT');
INSERT INTO "public"."eav_type" ("id", "type_code", "type_name")
VALUES (2, '423', 'ACCUM');
INSERT INTO "public"."eav_type" ("id", "type_code", "type_name")
VALUES (3, '454', 'CREDIT_COMMERCE_CLIENT');
INSERT INTO "public"."eav_type" ("id", "type_code", "type_name")
VALUES (4, '455', 'CREDIT_PHYSICAL_CLIENT');
INSERT INTO "public"."eav_type" ("id", "type_code", "type_name")
VALUES (5, '474', 'BANK_ACCOUNT');

INSERT INTO "public"."eav_subtype" ("id", "sub_type_code", "sub_type_name", "type_id")
VALUES (1, '02', 'COMMERCE', 1);
INSERT INTO "public"."eav_subtype" ("id", "sub_type_code", "sub_type_name", "type_id")
VALUES (2, '17', 'PHYSYCAL', 1);
INSERT INTO "public"."eav_subtype" ("id", "sub_type_code", "sub_type_name", "type_id")
VALUES (3, '19', 'CURRENCY', 1);
INSERT INTO "public"."eav_subtype" ("id", "sub_type_code", "sub_type_name", "type_id")
VALUES (4, '01', 'ON_EXPAND', 2);
INSERT INTO "public"."eav_subtype" ("id", "sub_type_code", "sub_type_name", "type_id")
VALUES (5, '06', 'UP_TO_THREE_YEAR', 2);
INSERT INTO "public"."eav_subtype" ("id", "sub_type_code", "sub_type_name", "type_id")
VALUES (6, '05', 'UP_TO_ONE_YEAR', 2);
INSERT INTO "public"."eav_subtype" ("id", "sub_type_code", "sub_type_name", "type_id")
VALUES (7, '07', 'UPPER_THREE_YEAR', 2);
INSERT INTO "public"."eav_subtype" ("id", "sub_type_code", "sub_type_name", "type_id")
VALUES (8, '07', 'UP_TO_THREE_YEAR', 3);
INSERT INTO "public"."eav_subtype" ("id", "sub_type_code", "sub_type_name", "type_id")
VALUES (9, '06', 'UP_TO_ONE_YEAR', 3);
INSERT INTO "public"."eav_subtype" ("id", "sub_type_code", "sub_type_name", "type_id")
VALUES (10, '08', 'UPPER_THREE_YEAR', 3);
INSERT INTO "public"."eav_subtype" ("id", "sub_type_code", "sub_type_name", "type_id")
VALUES (11, '05', 'UP_TO_THREE_YEAR', 4);
INSERT INTO "public"."eav_subtype" ("id", "sub_type_code", "sub_type_name", "type_id")
VALUES (12, '04', 'UP_TO_ONE_YEAR', 4);
INSERT INTO "public"."eav_subtype" ("id", "sub_type_code", "sub_type_name", "type_id")
VALUES (13, '06', 'UPPER_THREE_YEAR', 4);
INSERT INTO "public"."eav_subtype" ("id", "sub_type_code", "sub_type_name", "type_id")
VALUES (14, '05', 'CURRENCY_ACCOUNT_TO_SOLD', 5);
INSERT INTO "public"."eav_subtype" ("id", "sub_type_code", "sub_type_name", "type_id")
VALUES (15, '06', 'CURRENCY_ACCOUNT_TO_BUY', 5);
INSERT INTO "public"."eav_subtype" ("id", "sub_type_code", "sub_type_name", "type_id")
VALUES (16, '11', 'COMMISSION', 5);

INSERT INTO "public"."eav_currency" ("id", "currency_code", "currency_name")
VALUES (1, '840', 'USD');
INSERT INTO "public"."eav_currency" ("id", "currency_code", "currency_name")
VALUES (2, '978', 'EUR');
INSERT INTO "public"."eav_currency" ("id", "currency_code", "currency_name")
VALUES (3, '643', 'RUB');
