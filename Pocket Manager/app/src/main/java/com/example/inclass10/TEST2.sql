DROP DATABASE IF EXISTS DbProject;
CREATE DATABASE DbProject;
USE DbProject;


Create Table Employees(
eID Integer,
eName varchar(30),
age Integer,
salary real,
primary key (eID));


Create Table Departments(
dName varchar(50),
budget real,
managerEID integer,
primary key (dName,managerEID),
FOREIGN KEY(managerEID) REFERENCES Employees(eID)
);

Create Table  Works(
eID integer,
dName varchar(50),
startedDate Date,
ended Date,
primary key(eID,dName),
FOREIGN KEY(dName) REFERENCES Departments(dName)
);


insert into Employees values(123,'santhosh',22,22222),(124,'Krishna',24,20000),
(125,'Kiran',24,25000),(126,'Ram',22,24000),(127,'Pranathi',20,19000),(128,'Yamuna',20,20000);

insert into Departments values('Information Technology',2000000,123),('cs',3000,124),('Information Technology',2000000,124),
('Mec',500001,126),('BigData',600000,124),('EE',25000,125);

insert into Works values(123,'Information Technology','1900-12-02','2014-01-02'),
(124,'Information Technology','2012-12-02','2016-01-01'),(126,'cs','2014-12-02','2016-01-01');