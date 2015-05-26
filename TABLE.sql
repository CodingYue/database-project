CREATE TABLE Customer (
	login_name varchar(255) NOT NULL PRIMARY KEY,
	password varchar(255),
	full_name varchar(255),
	address varchar(255),
	phone_number varchar(255)
);

CREATE TABLE Book (
	ISBN varchar(255) NOT NULL PRIMARY KEY,
	Title varchar(255) NOT NULL,
	Authors varchar(255), NOT NULL,
	price float,
	format varchar(255),
	keywords varchar(255),
	year_of_publication varchar(255), 
	publisher varchar(255),
	court varchar(255)
);

CREATE TABLE Feedback (
	feedback_id int NOT NULL,
	login_name varchar(255),
	ISBN varchar(255),
	made_date date,
	rating int,
	CONSTRAINT PRIMARY KEY (login_name, ISBN)
#	FOREIGN KEY login_name REFERENCES Customer(login_name)
);

CREATE TABLE evaluate (
	login_name varchar(255),
	feedback_id int NOT NULL,
	score int NOT NULL,
	PRIMARY KEY (login_name, feedback_id)
);

CREATE TABLE trusting_records (
	login_name1 varchar(255),
	login_name2 varchar(255),
	trust int,
	CONSTRAINT record_id PRIMARY KEY (login_name1, login_name2)
);

CREATE TABLE Orders (
	login_name varchar(255),
	ISBN varchar(255),
	made_date date,
	amount int,
	CONSTRAINT PRIMARY KEY (login_name, ISBN, made_date)
);




