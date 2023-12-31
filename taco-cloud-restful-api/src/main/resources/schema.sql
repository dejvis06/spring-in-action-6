create table if not exists Taco_Order (
  id identity,
  delivery_name varchar(50) not null,
  delivery_street varchar(50) not null,
  delivery_city varchar(50) not null,
  delivery_state varchar(2) not null,
  delivery_zip varchar(10) not null,
  cc_number varchar(16) not null,
  cc_expiration varchar(5) not null,
  cc_cvv varchar(3) not null,
  username varchar not null,
  taco_ids INTEGER array
);

create table if not exists Taco (
  id identity,
  name varchar(50) not null,
  ingredient_ids INTEGER array
);

create table if not exists Ingredient (
  id identity,
  name varchar(25) not null,
  type varchar(10) not null
);