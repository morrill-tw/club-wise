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

INSERT INTO role (role_name)
VALUES ('President'), ('Vice President'), ('Secretary'), ('Treasurer'), ('Member');
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


INSERT INTO member_of_club ( first_name, last_name, club_name, role_name, join_date )
VALUES ("Bob", "Smith", "NER", "President" , '2023-8-10'),
       ("Sally", "Parker", "NER", "Member", '2024-8-21'),
       ("Bertha", "Dillon", "Cooking Club", "President", '2022-9-1' );

INSERT INTO member_of_club ( first_name, last_name, club_name, role_name, join_date )
VALUES ( "Jill", "Brown", "NER", "Member" , '2023-8-10'),
       ("Robby", "Lloyd", "NER", "Member", '2024-8-21'),
       ("Jackson", "Dumont", "NER", "Member", '2022-9-1' );

-- Create procedure to add a member to the database
DELIMITER $$
CREATE PROCEDURE add_member_to_club(
    IN p_first_name VARCHAR(64),
    IN p_last_name VARCHAR(64),
    IN p_join_date DATE,
    IN p_club_name VARCHAR(64),
    IN p_role_name VARCHAR(64)
)
BEGIN
    DECLARE v_club_exists INT;
    DECLARE v_role_exists INT;

    -- Check if the club exists
SELECT COUNT(*) INTO v_club_exists
FROM club
WHERE club_name = p_club_name;

-- Check if the role exists
SELECT COUNT(*) INTO v_role_exists
FROM role
WHERE role_name = p_role_name;

-- If the club exists
IF v_club_exists > 0 THEN
        -- If the role doesn't exist, insert it into the role table
        IF v_role_exists = 0 THEN
            INSERT INTO role (role_name)
            VALUES (p_role_name);
END IF;
INSERT INTO member_of_club (first_name, last_name, join_date, club_name, role_name)
VALUES (p_first_name, p_last_name, p_join_date, p_club_name, p_role_name);
ELSE
SELECT 'Failed to add member: Club does not exist.' AS ErrorMessage;
END IF;
END$$
DELIMITER ;


-- Create procedure to remove a member from the database
DELIMITER $$
CREATE PROCEDURE remove_member_from_club(
    IN p_member_id INT
)
BEGIN
    IF EXISTS (SELECT 1 FROM member_of_club WHERE member_id = p_member_id) THEN
DELETE FROM member_of_club WHERE member_id = p_member_id;
SELECT CONCAT('Member with ID ', p_member_id, ' has been removed successfully.') AS Message;

ELSE
SELECT CONCAT('Member with ID ', p_member_id, ' does not exist in the database.') AS ErrorMessage;
END IF;
END$$
DELIMITER;

-- Again use a real ID number, 123 is just a placeholder
CALL remove_member_from_club(123);

-- Create procedure to edit a member's role
DELIMITER $$
CREATE PROCEDURE edit_member_role(
    IN p_member_id INT,
    IN p_new_role_name VARCHAR(64)
)
BEGIN
    IF EXISTS (SELECT 1 FROM member_of_club WHERE member_id = p_member_id) THEN
UPDATE member_of_club
SET role_name = p_new_role_name
WHERE member_id = p_member_id;

ELSE
SELECT CONCAT('Member with ID ', p_member_id, ' does not exist in the database.') AS ErrorMessage;
END IF;
END$$
DELIMITER ;

-- use a real Id though 123 is a placeholder, but works!
-- CALL edit_member_role(123, 'Treasurer');

-- Create procedure to edit an event in the database
DELIMITER $$
CREATE PROCEDURE edit_event(
    IN p_event_title VARCHAR(64),
    IN p_event_date DATE,
    IN p_club_name VARCHAR(64),
    IN p_new_event_description VARCHAR(500)
)
BEGIN
    IF EXISTS (
        SELECT 1
        FROM event
        WHERE event_title = p_event_title
          AND event_date = p_event_date
          AND club_name = p_club_name
    ) THEN
UPDATE event
SET
    event_description = p_new_event_description
WHERE event_title = p_event_title
  AND event_date = p_event_date
  AND club_name = p_club_name;

ELSE
SELECT CONCAT('Event "', p_event_title, '" on "', p_event_date, '" for club "', p_club_name, '" does not exist.') AS ErrorMessage;
END IF;
END$$
DELIMITER ;

CALL edit_event(
    'Tech Talk',
    '2024-12-15',
    'Generate',
    'Updated description for the Tech Talk event.'
);

-- Create procedure to remove an event from the database
DELIMITER $$
CREATE PROCEDURE remove_event(
    IN p_event_title VARCHAR(64),
    IN p_event_date DATE,
    IN p_club_name VARCHAR(64)
)
BEGIN
    IF EXISTS (
        SELECT 1
        FROM event
        WHERE event_title = p_event_title
          AND event_date = p_event_date
          AND club_name = p_club_name
    ) THEN
DELETE FROM event
WHERE event_title = p_event_title
  AND event_date = p_event_date
  AND club_name = p_club_name;
ELSE
SELECT CONCAT('Event "', p_event_title, '" on "', p_event_date, '" for club "', p_club_name, '" does not exist.') AS ErrorMessage;
END IF;
END$$
DELIMITER ;

CALL remove_event('Tech Talk', '2024-12-15', 'Generate');

-- Create procedure to add an event to the database

DELIMITER $$
CREATE PROCEDURE add_event(
    IN p_event_title VARCHAR(64),
    IN p_event_description VARCHAR(500),
    IN p_event_date DATE,
    IN p_club_name VARCHAR(64)
)
BEGIN
    IF EXISTS (
        SELECT 1
        FROM club
        WHERE club_name = p_club_name
    ) THEN
        INSERT INTO event (event_title, event_description, event_date, club_name)
        VALUES (p_event_title, p_event_description, p_event_date, p_club_name);
ELSE
SELECT CONCAT('Club "', p_club_name, '" does not exist.') AS ErrorMessage;
END IF;
END$$
DELIMITER ;

CALL add_event('Tech Talk', 'An informative event about emerging technologies.', '2024-12-15', 'Generate');

-- Create procedure for determining the number of members in a club
DELIMITER $$
CREATE PROCEDURE get_number_of_members(
    IN p_club_name VARCHAR(64)
)
BEGIN
SELECT COUNT(*) AS NumberOfMembers
FROM member_of_club
WHERE club_name = p_club_name;
END$$
DELIMITER ;

CALL get_number_of_members('Generate');

-- get events for a specific club
DELIMITER $$
CREATE PROCEDURE get_events_by_club(
    IN p_club_name VARCHAR(64)
)
BEGIN
SELECT
    event_title,
    event_description,
    event_date
FROM
    event
WHERE
    club_name = p_club_name
ORDER BY
    event_date ASC;
END$$
DELIMITER ;

call get_events_by_club("Generate");

-- add an event to a club

DELIMITER $$

CREATE PROCEDURE add_event_to_club(
    IN p_event_title VARCHAR(64),
    IN p_event_description VARCHAR(500),
    IN p_event_date DATE,
    IN p_club_name VARCHAR(64)
)
BEGIN
    DECLARE v_club_exists INT;
    DECLARE v_event_exists INT;
    DECLARE v_message VARCHAR(255);

    -- Check if the club exists
SELECT COUNT(*) INTO v_club_exists
FROM club
WHERE club_name = p_club_name;

-- Check if the event already exists for the given club and date
SELECT COUNT(*) INTO v_event_exists
FROM event
WHERE event_title = p_event_title
  AND event_date = p_event_date
  AND club_name = p_club_name;

-- If the club exists and event doesn't exist, insert the event
IF v_club_exists > 0 AND v_event_exists = 0 THEN
        INSERT INTO event (event_title, event_description, event_date, club_name)
        VALUES (p_event_title, p_event_description, p_event_date, p_club_name);
        SET v_message = 'Event added successfully.';
    ELSEIF v_club_exists = 0 THEN
        SET v_message = 'Club does not exist.';
ELSE
        SET v_message = 'Event already exists for this club on the given date.';
END IF;

    -- Output the message
SELECT v_message AS ResultMessage;
END$$

DELIMITER ;

CALL add_event_to_club('Hackathon', 'Coding competition for developers', '2024-01-15', 'Generate');

-- create procedure to delete Events from a specific club
DELIMITER $$

CREATE PROCEDURE delete_events_from_club(
    IN p_club_name VARCHAR(64),
    IN p_event_title VARCHAR(64),
    IN p_event_date DATE
)
BEGIN
    DECLARE v_club_exists INT;

    -- Check if the club exists
SELECT COUNT(*) INTO v_club_exists
FROM club
WHERE club_name = p_club_name;

-- If the club exists
IF v_club_exists > 0 THEN
        -- If both event title and event date are provided, delete the specific event
DELETE FROM event
WHERE club_name = p_club_name
  AND event_title = p_event_title
  AND event_date = p_event_date;

SELECT 'Event(s) deleted successfully.' AS ResultMessage;

ELSE
        -- If the club does not exist, return an error message
SELECT 'Failed to delete events: Club does not exist.' AS ErrorMessage;
END IF;
END$$
DELIMITER ;

