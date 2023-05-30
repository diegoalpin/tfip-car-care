-- drop table customer;

CREATE TABLE customer (
  id int NOT NULL AUTO_INCREMENT,
  email NVARCHAR(50) NOT NULL unique,
  pwd NVARCHAR(200) NOT NULL,
  firstName NVARCHAR(50) NOT NULL,
  lastName NVARCHAR(50) NOT NULL,
  phoneNumber int NOT NULL,
  address NVARCHAR(200) NOT NULL,
  role NVARCHAR(45) NOT NULL,
  constraint pk_customer_id PRIMARY KEY (id)
);

CREATE TABLE car (
  id int NOT NULL AUTO_INCREMENT,
  carPlate NVARCHAR(50) NOT NULL,
  brand NVARCHAR(50) NOT NULL,
  model NVARCHAR(50) NOT NULL,
  yearManufactured int NOT NULL,
  customer_id int not null,
  constraint pk_car_id PRIMARY KEY (id),
  constraint fk_customer_car FOREIGN KEY(customer_id) REFERENCES customer(id) on delete CASCADE on UPDATE CASCADE
);

CREATE TABLE maintenance (
  id int NOT NULL AUTO_INCREMENT,
  description NVARCHAR(200) NOT NULL,
  date DATE NOT NULL,
  cost DOUBLE,
  status NVARCHAR(50) NOT NULL,
  mileage int,
  car_id int not null,
  constraint pk_maintenance_id PRIMARY KEY (id),
  constraint fk_car_maintenance FOREIGN KEY(car_id) REFERENCES car(id) on delete CASCADE on UPDATE CASCADE
);
CREATE TABLE item (
  id int NOT NULL AUTO_INCREMENT,
  name NVARCHAR(80) NOT NULL,
  price DOUBLE,
  constraint pk_item_id PRIMARY KEY (id)
);

insert into item (name, price) values ('Service Fee', 200);
insert into item (name, price) values ('Diagnostics Fee', 200);
insert into item (name, price) values ('Spark plugs', 200);
insert into item (name, price) values ('Oil 1L', 200);
insert into item (name, price) values ('Brake pad', 200);
insert into item (name, price) values ('Brake rotor', 200);
insert into item (name, price) values ('Air filter', 200);
insert into item (name, price) values ('Battery', 200);
insert into item (name, price) values ('Coolant', 200);
insert into item (name, price) values ('Transmission fluid', 200);
insert into item (name, price) values ('Cleaning solution', 200);
insert into item (name, price) values ('Timing belt', 200);
insert into item (name, price) values ('Suspension parts', 200);
insert into item (name, price) values ('Exhaust system parts', 200);
insert into item (name, price) values ('HVAC System parts', 200);
insert into item (name, price) values ('Ignition coils', 200);

CREATE TABLE maintenanceitem (
  maintenance_id int NOT NULL,
  item_id int NOT NULL,
  constraint pk_maintenance_id_item_id primary KEY(maintenance_id,item_id),
  constraint fk_maintenance_maintenanceitem FOREIGN KEY(maintenance_id) REFERENCES maintenance(id) on delete CASCADE on UPDATE CASCADE,
  constraint fk_item_maintenanceitem FOREIGN KEY(maintenance_id) REFERENCES maintenance(id) on delete CASCADE on UPDATE CASCADE
);