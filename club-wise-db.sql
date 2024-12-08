DROP DATABASE IF EXISTS club_wise;
CREATE DATABASE IF NOT EXISTS club_wise;

USE club_wise;

CREATE TABLE club
(
    club_name VARCHAR(64) PRIMARY KEY,
    club_description VARCHAR(500) NOT NULL,
    active BOOL NOT NULL,
    category enum("STEM", 
				"Arts and Culture", 
                "Sports and Recreation", 
                "Community Service and Social Impact", 
                "Social and Special Interests",
                "Media and Communications",
                "Religious and Spiritual") NOT NULL
);

CREATE TABLE role
(
	role_name VARCHAR(64) PRIMARY KEY
);

CREATE TABLE member_of_club
(
	member_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
	join_date DATE NOT NULL,
    club_name VARCHAR(64) NOT NULL,
    role_name VARCHAR(64) NOT NULL,
    
    FOREIGN KEY (club_name) REFERENCES club(club_name),
    FOREIGN KEY (role_name) REFERENCES role(role_name)
);

CREATE TABLE purchase
(
	purchase_id INT PRIMARY KEY AUTO_INCREMENT,
    purhcase_title VARCHAR(64) NOT NULL,
    purchase_description VARCHAR(500),
    cost DECIMAL NOT NULL,
    purchase_date DATE NOT NULL,
    club_name VARCHAR(64) NOT NULL,
    
    FOREIGN KEY (club_name) REFERENCES club(club_name)
);

CREATE TABLE social_media
(
	platform VARCHAR(64) NOT NULL,
    username VARCHAR(64) NOT NULL,
    club_name VARCHAR(64) NOT NULL,
    
    PRIMARY KEY (platform, username),
    FOREIGN KEY (club_name) REFERENCES club(club_name)
);

CREATE TABLE event
(
	event_title VARCHAR(64) NOT NULL,
    event_description VARCHAR(500) NOT NULL,
    event_date DATE NOT NULL,
    club_name VARCHAR(64) NOT NULL,
    
    PRIMARY KEY (event_title, event_date, club_name),
    FOREIGN KEY (club_name) REFERENCES club(club_name)
);

CREATE TABLE member_attends_event
(
	event_title VARCHAR(64) NOT NULL,
    event_date DATE NOT NULL,
    club_name VARCHAR(64) NOT NULL,
    member_id INT NOT NULL,
    
    PRIMARY KEY (event_title, event_date, club_name, member_id),
    FOREIGN KEY (event_title, event_date, club_name) REFERENCES event(event_title, event_date, club_name),
    FOREIGN KEY (member_id) REFERENCES member_of_club(member_id)
);

DELIMITER $$
CREATE PROCEDURE get_clubs_by_category( 
IN category_p enum("STEM", 
				"Arts and Culture", 
                "Sports and Recreation", 
                "Community Service and Social Impact", 
                "Social and Special Interests",
                "Media and Communications",
                "Religious and Spiritual") )
BEGIN
SELECT * FROM club WHERE category = category_p;
END $$
DELIMITER ;

-- For testing
INSERT INTO club ( club_name, club_description, active, category )
		VALUES ( "NER", "Make cool electric cars!", TRUE, "STEM" ),
        ("Cooking Club", "Make food", FALSE, "Social and Special Interests"),
        ("Generate", "Make prototypes", TRUE, "STEM");
CALL get_clubs_by_category("Social and Special Interests");
CALL get_clubs_by_category("STEM");

DELIMITER $$
CREATE PROCEDURE create_club( 
name_p VARCHAR(64),
description_p VARCHAR(500),
active_p BOOL,
category_p enum("STEM", 
				"Arts and Culture", 
                "Sports and Recreation", 
                "Community Service and Social Impact", 
                "Social and Special Interests",
                "Media and Communications",
                "Religious and Spiritual") )
BEGIN
INSERT INTO club ( club_name, club_description, active, category )
VALUES ( name_p, description_p, active_p, category_p );
END $$
DELIMITER ;

-- For testing
CALL create_club("Cheese Club", "Eat cheese", FALSE, "Social and Special Interests");
CALL get_clubs_by_category("Social and Special Interests");

DELIMITER $$
CREATE PROCEDURE get_clubs_by_active_status( active_p BOOL )
BEGIN
SELECT * FROM club WHERE active = active_p;
END $$
DELIMITER ;

-- For testing
CALL get_clubs_by_active_status( TRUE );
CALL get_clubs_by_active_status( FALSE );

DELIMITER $$
CREATE PROCEDURE get_members_by_club_name( club_name_p VARCHAR(64) )
BEGIN
SELECT * FROM member_of_club WHERE club_name = club_name_p;
END $$
DELIMITER ;
    
-- For testing
CALL get_members_by_club_name( "Cheese Club" );
CALL get_members_by_club_name( "NER" );

INSERT INTO role ( role_name )
VALUES ( "President" ), ( "Member" );
    
INSERT INTO member_of_club ( first_name, last_name, club_name, role_name, join_date )
VALUES ("Bob", "Smith", "NER", "President" , '2023-8-10'),
("Sally", "Parker", "NER", "Member", '2024-8-21'),
("Bertha", "Dillon", "Cooking Club", "President", '2022-9-1' );

INSERT INTO member_of_club ( first_name, last_name, club_name, role_name, join_date )
VALUES ( "Jill", "Brown", "NER", "Member" , '2023-8-10'),
("Robby", "Lloyd", "NER", "Member", '2024-8-21'),
("Jackson", "Dumont", "NER", "Member", '2022-9-1' );

-- Create procedure to add a member to the database

-- Create procedure to remove a member from the database

-- Create procedure to edit a member's role

-- Create procedure to edit an event in the database

-- Create procedure to remove an event from the database

-- Create procedure to add an event to the database

-- Create procedure for determining the number of members in a club
