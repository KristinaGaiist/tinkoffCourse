-- task1
CREATE DATABASE postgres;

DROP TABLE IF EXISTS brands;
CREATE TABLE brands (
  id INT GENERATED ALWAYS AS IDENTITY,
  brand VARCHAR(50) NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS models;
CREATE TABLE models (
  id INT GENERATED ALWAYS AS IDENTITY,
  brand_id INT,
  model VARCHAR(100) NOT NULL,
  PRIMARY KEY(id),
  CONSTRAINT fk_brand
      FOREIGN KEY(brand_id)
	  REFERENCES brands(id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS cars;
CREATE TABLE cars (
  id INT GENERATED ALWAYS AS IDENTITY,
  state_number VARCHAR(20) NOT NULL,
  model_id INT,
  PRIMARY KEY(id),
  CONSTRAINT fk_model
      FOREIGN KEY(model_id)
	  REFERENCES models(id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS cities;
CREATE TABLE cities (
  id INT GENERATED ALWAYS AS IDENTITY,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS customers;
CREATE TABLE customers (
  id INT GENERATED ALWAYS AS IDENTITY,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  middle_name VARCHAR(100) NOT NULL,
  city_id INT,
  PRIMARY KEY(id),
  CONSTRAINT fk_city
      FOREIGN KEY(city_id)
	  REFERENCES cities(id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS customers_cars_relation;
CREATE TABLE customers_cars_relation (
  customer_id INT NOT NULL,
  car_id INT NOT NULL,
  PRIMARY KEY (customer_id, car_id),
   FOREIGN KEY (car_id)
      REFERENCES cars(id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (customer_id)
      REFERENCES customers(id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO brands (brand)
VALUES ('BMW'), ('Mercedes'), ('Audi'), ('Porsche'), ('Toyota'), ('Opel');

INSERT INTO models (model, brand_id)
VALUES ('X5', 1), ('X7', 1), ('i8', 1),
('GLA', 2), ('CLA', 2), ('GLE', 2), ('GLB', 2), ('GLC-Class', 2), ('E-Class', 2),
('TT', 3), ('Q8', 3), ('A3', 3),
('Panamera', 4), ('Cayenne', 4),
('Camry', 5), ('Corolla', 5), ('Civic', 5), ('RAV4', 5),
('Crosa', 6);

INSERT INTO cars (state_number, model_id)
VALUES ('x777vb', 1), ('h567jj', 2), ('j789kk', 3), ('p231mm', 3), ('x234sh', 3), ('k906nb', 2), ('v543pm', 1),
('w645vb', 1), ('p666pp', 4), ('i675nb', 4), ('o576fh', 5), ('n241cx', 6), ('n567df', 7), ('z645bb', 7),
('o098fg', 8), ('a456dn', 9), ('v657fg', 10), ('f546wd', 11), ('q345hy', 12), ('m970nm', 13), ('b674bg', 13),
('c546fg', 13), ('x658gh', 13), ('s657sr', 14), ('p094kj', 15), ('r302bg', 16), ('t651bg', 16), ('y732ng', 17),
('k210kk', 18), ('d758lm', 18), ('o888oo', 19);

INSERT INTO cities (name)
VALUES ('Moskow'), ('Novosibirsk'), ('Sochi'), ('Krasnoyarsk'), ('Ekaterenburg'), ('Omsk');

INSERT INTO customers (first_name, last_name, middle_name, city_id)
VALUES ('Kristina', 'Kirinyuk', 'Viktorovna', 1),
('Aleksey', 'Kirinyuk', 'Igorevich', 1),
('Anna', 'Frolova', 'Dmitrievna', 2),
('Michail', 'Korenev', 'Alekseevich', 3),
('Iliya', 'Shagdonov', 'Vladislavovich', 4),
('Dianya', 'Petrosyan', 'Samvelovna', 5),
('Vladislav', 'Petrosyan', 'Maksimovich', 5),
('Olga', 'Belskaya', 'Vladimirovna', 6),
('Egor', 'Kogdin', 'Timofeevich', 1),
('Margarita', 'Borisova', 'Aleksandrovna', 2),
('Anastasia', 'Parneva', 'Evgenevna', 3),
('Tatyana', 'Popova', 'Petrovna', 4);

INSERT INTO customers_cars_relation (customer_id, car_id)
VALUES (1, 1), (1, 17), (2, 1), (2, 2), (2, 3), (3, 4), (4, 5), (5, 6), (6, 7), (6, 8), (7, 8), (8, 19),
(9, 18), (10, 17), (11, 16), (12, 15), (12, 14);

-- task2
-- получение ФИО всех клиентов, у которых есть автомобиль марки BMW
SELECT first_name, last_name, middle_name
from customers
join customers_cars_relation on customers_cars_relation.customer_id = customers.customer_id
join cars on customers_cars_relation.car_id = cars.car_id
join models on cars.model_id = models.model_id
join brands on models.brand_id = brands.brand_id
where brands.brand = 'BMW';

-- получение автомобилей по данным клиента
SELECT DISTINCT cars.state_number, brands.brand
from cars
join customers_cars_relation on customers_cars_relation.car_id = cars.car_id
join customers on customers_cars_relation.customer_id = customers.customer_id
join models on cars.model_id = models.model_id
join brands on models.brand_id = brands.brand_id
where customers.first_name = 'Kristina';

--- удаление всех записей об автомобилях у определенного клиента
DELETE from cars
where car_id in (
SELECT customers_cars_relation.car_id
from customers_cars_relation
join cars on customers_cars_relation.car_id = cars.car_id
join customers on customers_cars_relation.customer_id = customers.customer_id
where customers.first_name = 'Tatyana'
);

-- изменение каких-либо данных в таблицах
UPDATE customers
SET middle_name  = 'Andreevna'
WHERE customer_id = 12;

-- получение количества автомобилей по каждому клиенту (на экран вывести id клиента, ФИО и количество автомобилей;
-- отсортировать результат по ФИО в алфавитном порядке)
SELECT customers.customer_id, first_name, last_name, middle_name, count(*) car_number
from customers
join customers_cars_relation on customers_cars_relation.customer_id = customers.customer_id
join cars on customers_cars_relation.car_id = cars.car_id
GROUP BY customers.customer_id
ORDER BY first_name;

-- переделать предыдущий запрос таким образом, чтобы на экран вывелись клиенты, у которых больше 2 машин
SELECT customers.customer_id, first_name, last_name, middle_name, count(*) car_number
from customers
join customers_cars_relation on customers_cars_relation.customer_id = customers.customer_id
join cars on customers_cars_relation.car_id = cars.car_id
GROUP BY customers.customer_id
HAVING count(*) > 2
ORDER BY first_name;

