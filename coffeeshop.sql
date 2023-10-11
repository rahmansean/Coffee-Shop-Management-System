CREATE DATABASE COFFEESHOP;

USE COFFEESHOP;



CREATE TABLE AdminTable(
   adminID int NOT NULL PRIMARY KEY,
   adminName varchar(50) NOT NULL,
   adminEmail varchar(30) NOT NULL UNIQUE,
   adminPassword varchar(30) NOT NULL
);

/*Admin*/
SELECT * FROM AdminTable;
drop table AdminTable;
TRUNCATE TABLE AdminTable;

INSERT INTO AdminTable(adminID,adminName,adminEmail,adminPassword) 
VALUES (190104106,'Md.Rasel','190104106@aust.edu','190104106');


CREATE TABLE EmployeeTable(
  employeeID int NOT NULL IDENTITY(1,1) PRIMARY KEY,
  employeeName VARCHAR(20) NOT NULL,
  employeeEmail VARCHAR(20) NOT NULL,
  employeePassword VARCHAR(20) NOT NULL,
  employeeAddress VARCHAR(20) NOT NULL,
  employeePhone VARCHAR(11) NOT NULL,
  employeeGender VARCHAR(10) NOT NULL,
  CONSTRAINT UK_Employee UNIQUE (employeeEmail,employeePhone)
);


Insert into EmployeeTable(employeeName,employeeEmail,employeePassword,employeeaddress,employeePhone,employeeGender) 
values ('Sean Rahman','seanrahman@gmail.com','123456789','Dhaka','01912043752','Male');
Insert into EmployeeTable(employeeName,employeeEmail,employeePassword,employeeaddress,employeePhone,employeeGender) 
values ('Farhan','farhan@gmail.com','123456','Dhaka','12346792005','Male');
Insert into EmployeeTable(employeeName,employeeEmail,employeePassword,employeeaddress,employeePhone,employeeGender) 
values ('Sabbir','sabbir@gmail.com','123456','Malibag','78945612301','Female');

SELECT * FROM EmployeeTable;
TRUNCATE TABLE EmployeeTable;
DROP TABLE EmployeeTable;


Create table ItemsTable
(
itemID int Not null primary key IDENTITY (10,1),
itemName VARCHAR(100) NOT NULL,
itemCost int not null,
itemQuantity int Not null,
itemCategory VARCHAR(100) NOT NULL,
itemImage varchar(100),
itemdescription VARCHAR(100) NOT NULL,
);

Insert into ItemsTable(itemName,itemCost,itemQuantity,itemCategory,itemImage,itemdescription)
values('Big MAc',10,1,'Cold Coffee','E:\DATABASE Project\CoffeeShop\src\image\cafe.png','Big');




/*Item*/

SELECT * FROM ItemsTable;
TRUNCATE TABLE ItemsTable;
DROP TABLE ItemsTable;



Create table OrderTable
(
orderId int primary key Not null,
orderDate dateTime,
discount float null,
quantity int not null,
recieveTime dateTime,
deliverTime dateTime,
totalCost float Not null,
paymentMethod VARCHAR(10) NOT NULL,
itemID int ,
CONSTRAINT FK_Order FOREIGN KEY (itemID)
    REFERENCES ItemsTable(itemID)

);

/*Order*/
SELECT * FROM OrderTable;
TRUNCATE TABLE OrderTable;
DROP TABLE OrderTable;


Create table CustomerTable
(
customerID int primary key Not null IDENTITY (101,1),
customerName VARCHAR(20) NOT NULL,
customerPhone VARCHAR(11) NOT NULL unique,

);

/*Customer*/
SELECT * FROM CustomerTable;
TRUNCATE TABLE CustomerTable;
DROP TABLE CustomerTable;


Create table Sales_infoTable
(
salesID  int IDENTITY(2000,1) Not null primary key,
orderID int FOREIGN KEY REFERENCES OrderTable(orderID),
employeeID int FOREIGN KEY REFERENCES EmployeeTable(employeeID),
customerID int FOREIGN KEY REFERENCES CustomerTable(customerID),
itemsID int FOREIGN KEY REFERENCES ItemsTable(itemID),
dailySales float not null,
paymentStatus VARCHAR(20) NOT NULL

);

/*Sales_infoTable*/
SELECT * FROM Sales_infoTable;
TRUNCATE TABLE Sales_infoTable;
DROP TABLE Sales_infoTable;


