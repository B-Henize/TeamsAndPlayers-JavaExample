-- --------------------------------------------------------------------------------
-- Name: Brandin Henize
-- Class: IT-161-400
-- Abstract: Homework 18 - Switching to SQL Server
-- --------------------------------------------------------------------------------
-- --------------------------------------------------------------------------------
-- Options
-- --------------------------------------------------------------------------------
USE dbTeamsAndPlayers;	-- Get out of the master database-
SET NOCOUNT ON;			-- Report only errors

-- IF OBJECT_ID ( 'TTeamPlayers' )	IS NOT NULL DROP TABLE  TTeamPlayers -- example for Table
-- IF OBJECT_ID ( 'uspAdd2Numbers' )					IS NOT NULL DROP PROCEDURE uspAdd2Numbers -- example for Procedure
-- --------------------------------------------------------------------------------
-- Drop statements
-- --------------------------------------------------------------------------------
-- Stored Procedures
	-- TTeams
IF OBJECT_ID ( 'uspAddTeam' )					IS NOT NULL DROP PROCEDURE uspAddTeam
IF OBJECT_ID ( 'uspEditTeam' )					IS NOT NULL DROP PROCEDURE uspEditTeam
IF OBJECT_ID ( 'uspSetTeamStatus' )				IS NOT NULL DROP PROCEDURE uspSetTeamStatus
	-- TPlayers
IF OBJECT_ID ( 'uspAddPlayer' )					IS NOT NULL DROP PROCEDURE uspAddPlayer
IF OBJECT_ID ( 'uspEditPlayer' )				IS NOT NULL DROP PROCEDURE uspEditPlayer
IF OBJECT_ID ( 'uspSetPlayerStatus' )			IS NOT NULL DROP PROCEDURE uspSetPlayerStatus
	-- TTeamPlayers
IF OBJECT_ID ( 'uspAddTeamPlayer' )				IS NOT NULL DROP PROCEDURE uspAddTeamPlayer
IF OBJECT_ID ( 'uspRemoveTeamPlayer' )			IS NOT NULL DROP PROCEDURE uspRemoveTeamPlayer

-- Extra credit
IF OBJECT_ID ( 'uspAddAllPlayersToTeam' )		IS NOT NULL DROP PROCEDURE uspAddAllPlayersToTeam
IF OBJECT_ID ( 'uspRemoveAllPlayersFromTeam' )	IS NOT NULL DROP PROCEDURE uspRemoveAllPlayersFromTeam

-- Views
IF OBJECT_ID( 'VActiveTeams' )					IS NOT NULL DROP VIEW  VActiveTeams
IF OBJECT_ID( 'VInactiveTeams' )				IS NOT NULL DROP VIEW  VInactiveTeams
IF OBJECT_ID( 'VActivePlayers' )				IS NOT NULL DROP VIEW  VActivePlayers
IF OBJECT_ID( 'VInactivePlayers' )				IS NOT NULL DROP VIEW  VInactivePlayers

-- Tables
IF OBJECT_ID ( 'TTeamPlayers' )					IS NOT NULL DROP TABLE  TTeamPlayers
IF OBJECT_ID ( 'TPlayers' )						IS NOT NULL DROP TABLE  TPlayers
IF OBJECT_ID ( 'TTeams' )						IS NOT NULL DROP TABLE  TTeams
IF OBJECT_ID ( 'TStates' )						IS NOT NULL DROP TABLE  TStates
IF OBJECT_ID ( 'TSexes' )						IS NOT NULL DROP TABLE  TSexes
IF OBJECT_ID ( 'TTeamStatuses' )				IS NOT NULL DROP TABLE  TTeamStatuses
IF OBJECT_ID ( 'TPlayerStatuses' )				IS NOT NULL DROP TABLE  TPlayerStatuses

-- --------------------------------------------------------------------------------
-- Step #2.3: Create Tables
-- --------------------------------------------------------------------------------
CREATE TABLE TTeamStatuses
(
	 intTeamStatusID			INTEGER			NOT NULL
	,strTeamStatus				VARCHAR(50)		NOT NULL
	,CONSTRAINT TTeamStatuses_PK PRIMARY KEY( intTeamStatusID )
);

CREATE TABLE TPlayerStatuses
(
	 intPlayerStatusID			INTEGER			NOT NULL
	,strPlayerStatus			VARCHAR(50)		NOT NULL
	,CONSTRAINT TPlayerStatuses_PK PRIMARY KEY( intPlayerStatusID )
);

CREATE TABLE TStates
(
	 intStateID					INTEGER			NOT NULL
	,strState					VARCHAR(50)		NOT NULL
	,strStateAbbreviation		VARCHAR(50)		NOT NULL
	,CONSTRAINT TStates_PK PRIMARY KEY( intStateID )
);

CREATE TABLE TSexes
(
	 intSexID					INTEGER			NOT NULL
	,strSex						VARCHAR(50)		NOT NULL
	,CONSTRAINT TSex_PK PRIMARY KEY( intSexID )
);

CREATE TABLE TTeams
(	
	 intTeamID					INTEGER			NOT NULL
	,strTeam					VARCHAR(50)		NOT NULL
	,strMascot					VARCHAR(50)		NOT NULL
	,intTeamStatusID			INTEGER			NOT NULL
	,CONSTRAINT TTeams_PK PRIMARY KEY( intTeamID )
);

CREATE TABLE TPlayers
(
	 intPlayerID				INTEGER			NOT NULL
	,strFirstName				VARCHAR(50)		NOT NULL
	,strMiddleName				VARCHAR(50)		NOT NULL
	,strLastName				VARCHAR(50)		NOT NULL
	,strStreetAddress			VARCHAR(50)		NOT NULL
	,strCity					VARCHAR(50)		NOT NULL
	,intStateID					INTEGER			NOT NULL
	,strZipCode					VARCHAR(50)		NOT NULL
	,strPhoneNumber				VARCHAR(50)		NOT NULL
	,curSalary					MONEY			NOT NULL
	,dtmDateOfBirth				DATE			NOT NULL
	,intSexID					INTEGER			NOT NULL
	,blnMostValuablePlayer		BIT				NOT NULL
	,strEmailAddress			VARCHAR(50)		NOT NULL
	,intPlayerStatusID			INTEGER			NOT NULL
	,CONSTRAINT TPlayers_PK PRIMARY KEY ( intPlayerID )
);

CREATE TABLE TTeamPlayers
(
	 intTeamID					INTEGER			NOT NULL
	,intPlayerID				INTEGER			NOT NULL
	,CONSTRAINT TTeamPlayers_PK PRIMARY KEY ( intTeamID, intPlayerID )
);


-- --------------------------------------------------------------------------------
-- Step #2.6: Identify and create foreign keys
-- --------------------------------------------------------------------------------
-- # - Child							Parent							Column(s)
--	   -----							------							---------
-- 1 - TTeams							TTeamStatuses					intTeamStatusID
-- 2 - TPlayers							TPlayerStatuses					intPlayerStatusID
-- 3 - TPlayers							TStates							intStateID	
-- 4 - TPlayers							TSexes							intSexID		
-- 5 - TTeamPlayers						TTeams							intTeamID
-- 6 - TTeamPlayers						TPlayers						intPlayerID

-- 1
ALTER TABLE TTeams ADD CONSTRAINT TTeams_TTeamStatuses_FK
FOREIGN KEY ( intTeamStatusID ) REFERENCES TTeamStatuses( intTeamStatusID )

-- 2
ALTER TABLE TPlayers ADD CONSTRAINT TPlayers_TPlayerStatuses_FK
FOREIGN KEY ( intPlayerStatusID ) REFERENCES TPlayerStatuses( intPlayerStatusID )

-- 3
ALTER TABLE TPlayers ADD CONSTRAINT TPlayers_TStates_FK
FOREIGN KEY ( intStateID ) REFERENCES TStates( intStateID )

-- 4
ALTER TABLE TPlayers ADD CONSTRAINT TPlayers_TSexes_FK
FOREIGN KEY ( intSexID ) REFERENCES TSexes( intSexID )

-- 5
ALTER TABLE TTeamPlayers ADD CONSTRAINT TTeamPlayers_TTeams_FK
FOREIGN KEY ( intTeamID ) REFERENCES TTeams( intTeamID )

-- 6
ALTER TABLE TTeamPlayers ADD CONSTRAINT TTeamPlayers_TPlayers_FK
FOREIGN KEY ( intPlayerID ) REFERENCES TPlayers( intPlayerID )



-- --------------------------------------------------------------------------------
-- Step 2.11: Insert data for TTeamStatuses, TPlayerStatuses, TStates, TSexes
-- --------------------------------------------------------------------------------
INSERT INTO TTeamStatuses( intTeamStatusID, strTeamStatus )
VALUES	 ( 1, 'Active' )
		,( 2, 'Inactive' )

INSERT INTO TPlayerStatuses( intPlayerStatusID, strPlayerStatus )
VALUES	 ( 1, 'Active' )
		,( 2, 'Inactive' )

INSERT INTO TSexes( intSexID, strSex )
VALUES	 ( 1, 'Female' )
		,( 2, 'Male' )

INSERT INTO TStates( intStateID, strState, strStateAbbreviation )
VALUES	 ( 1,	'Alabama',							'AL' )
		,( 2,	'Alaska',							'AK' )
		,( 3,	'Arizona',							'AZ' )
		,( 4,	'Arkansas',							'AR' )
		,( 5,	'California',						'CA' )
		,( 6,	'Colorado',							'CO' )
		,( 7,	'Connecticut',						'CT' )
		,( 8,	'Delaware',							'DE' )
		,( 9,	'Florida',							'FL' )
		,( 10,	'Georgia',							'GA' )
		,( 11,	'Hawaii',							'HI' )
		,( 12,	'Idaho',							'ID' )
		,( 13,	'Illinois',							'IL' )
		,( 14,	'Indiana',							'IN' )
		,( 15,	'Iowa',								'IA' )
		,( 16,	'Kansas',							'KS' )
		,( 17,	'Kentucky',							'KY' )
		,( 18,	'Louisiana',						'LA' )
		,( 19,	'Maine',							'ME' )
		,( 20,	'Maryland',							'MD' )
		,( 21,	'Massachusetts',					'MA' )
		,( 22,	'Michigan',							'MI' )
		,( 23,	'Minnesota',						'MN' )
		,( 24,	'Mississippi',						'MS' )
		,( 25,	'Missouri',							'MO' )
		,( 26,	'Montana',							'MT' )
		,( 27,	'Nebraska',							'NE' )
		,( 28,	'Nevada',							'NV' )
		,( 29,	'New Hampshire',					'NH' )
		,( 30,	'New Jersey',						'NJ' )
		,( 31,	'New Mexico',						'NM' )
		,( 32,	'New York',							'NY' )
		,( 33,	'North Carolina',					'NC' )
		,( 34,	'North Dakota',						'ND' )
		,( 35,	'Ohio',								'OH' )
		,( 36,	'Oklahoma',							'OK' )
		,( 37,	'Oregon',							'OR' )
		,( 38,	'Pennsylvania',						'PA' )
		,( 39,	'Rhode Island',						'RI' )
		,( 40,	'South Carolina',					'SC' )
		,( 41,	'South Dakota',						'SD' )
		,( 42,	'Tennessee',						'TN' )
		,( 43,	'Texas',							'TX' )
		,( 44,	'Utah',								'UT' )
		,( 45,	'Vermont',							'VT' )
		,( 46,	'Virginia',							'VA' )
		,( 47,	'Washington',						'WA' )
		,( 48,	'West Virginia',					'WV' )
		,( 49,	'Wisconsin',						'WI' )
		,( 50,	'Wyoming',							'WY' )
		,( 51,	'American Samoa',					'AS' )
		,( 52,	'District of Columbia',				'DC' )
		,( 53,	'Federated States of Micronesia',	'FM' )
		,( 54,	'Guam',								'GU' )
		,( 55,	'Marshall Islands',					'MH' )
		,( 56,	'Northern Mariana Islands',			'MP' )
		,( 57,	'Palau',							'PW' )
		,( 58,	'Puerto Rico',						'PR' )
		,( 59,	'Virgin Islands',					'VI' )


-- --------------------------------------------------------------------------------
-- Step 2.12: Insert data for TTeams
-- --------------------------------------------------------------------------------
INSERT INTO TTeams( intTeamID, strTeam, strMascot, intTeamStatusID )
VALUES	 ( 1, 'Curling', 'Lion', 1 )
		,( 2, 'Football', 'Tiger', 1 )
		,( 3, 'Soccer', 'Dragon', 1 )


		
-- --------------------------------------------------------------------------------
-- Step 2.12: Insert data for TPlayers
-- --------------------------------------------------------------------------------
INSERT INTO TPlayers( intPlayerID, strFirstName, strMiddleName, strLastName, strStreetAddress, strCity, intStateID, strZipCode, strPhoneNumber, curSalary, dtmDateOfBirth, intSexID, blnMostValuablePlayer, strEmailAddress, intPlayerStatusID )
VALUES	 ( 1, 'Brandin', 'William', 'Henize', '634 Easter Rd.', 'Bethel', 35, '45106', '937-515-6289', 44999.99, '03/22/1994', 2, 'FALSE', 'bwhenize@cincinnatistate.edu', 2 ) 
		,( 2, 'Adam', '', 'Savage', '34 Main St.', 'Los Angeles', 5, '34353', '555-345-3467', 125000, '07/15/1967', 2, 'FALSE', 'savageadam@discovery.com', 1 ) 
		,( 3, 'Sigourney', 'Alexandra', 'Weaver', '23 Main St.', 'New York City', 32, '35354', '245-343-2546', 145000, '10/08/1949', 1, 'TRUE', 'sweaver@gmail.com', 1 ) 

-- --------------------------------------------------------------------------------
-- Step 2.7: Create Views for Active and Inactive Teams/Players
-- --------------------------------------------------------------------------------

-- VActiveTeams
GO
CREATE VIEW VActiveTeams
AS
SELECT
	 TT.intTeamID
	,TT.strTeam
FROM
	 TTeams				AS	TT
WHERE
	 intTeamStatusID	=	1	-- Active
GO

-- VInactiveTeams
GO
CREATE VIEW VInactiveTeams
AS
SELECT
	 TT.intTeamID
	,TT.strTeam
FROM
	 TTeams				AS	TT
WHERE
	 intTeamStatusID	=	2	-- Inactive
GO

-- VActivePlayers
GO
CREATE VIEW VActivePlayers
AS
SELECT
	 TP.intPlayerID
	,TP.strFirstName
	,TP.strLastName
FROM
	 TPlayers			AS	TP
WHERE
	 intPlayerStatusID	=	1	-- Active
GO

-- VInactivePlayers
GO
CREATE VIEW VInactivePlayers
AS
SELECT
	 TP.intPlayerID
	,TP.strFirstName
	,TP.strLastName
FROM
	 TPlayers			AS	TP
WHERE
	 intPlayerStatusID	=	2 -- Inactive
GO



-- --------------------------------------------------------------------------------
-- Step 2.8: Make three Stored procedures for the TTeams table
-- --------------------------------------------------------------------------------

-- uspAddTeam
CREATE PROCEDURE uspAddTeam
	 @strTeam					VARCHAR(50)
	,@strMascot					VARCHAR(50)
AS
SET NOCOUNT ON		-- Report only errors
SET XACT_ABORT ON	-- Rollback transaction on error

BEGIN TRANSACTION
	
	DECLARE @intTeamID			INTEGER

	-- Get the next highest ID and lock the table until the end of the transaction
	SELECT @intTeamID = MAX( intTeamID ) + 1 FROM TTeams (TABLOCKX)

	-- Default to 1 if the table is empty
	SELECT @intTeamID = COALESCE( @intTeamID, 1 )

	-- Create new record
	INSERT INTO TTeams( intTeamID, strTeam, strMascot, intTeamStatusID )
	VALUES( @intTeamID, @strTeam, @strMascot, 1 ) -- 1 = Active

	-- Return ID to calling program
	SELECT @intTeamID AS intTeamID

COMMIT TRANSACTION
GO

-- uspEditTeam
CREATE PROCEDURE uspEditTeam
	 @intTeamID					INTEGER
	,@strTeam					VARCHAR(50)
	,@strMascot					VARCHAR(50)
AS
SET NOCOUNT ON		-- Report only errors
SET XACT_ABORT ON	-- Rollback transaction on error

BEGIN TRANSACTION

	-- Update the record
	UPDATE
		TTeams
	SET
		 strTeam	= @strTeam
		,strMascot	= @strMascot
	WHERE
		intTeamID = @intTeamID

COMMIT TRANSACTION
GO

-- uspSetTeamStatus
CREATE PROCEDURE uspSetTeamStatus
	 @intTeamID					INTEGER
	,@intTeamStatusID			INTEGER
AS
SET NOCOUNT ON		-- Report only errors
SET XACT_ABORT ON	-- Rollback transaction on error

BEGIN TRANSACTION

	-- Update the record
	UPDATE
		TTeams
	SET
		 intTeamStatusID = @intTeamStatusID
	WHERE
		intTeamID = @intTeamID

COMMIT TRANSACTION
GO


-- --------------------------------------------------------------------------------
-- Step 2.9: Make three Stored procedures for the TPlayers table
-- --------------------------------------------------------------------------------

-- uspAddPlayer
CREATE PROCEDURE uspAddPlayer
	 @strFirstName				VARCHAR(50)
	,@strMiddleName				VARCHAR(50)
	,@strLastName				VARCHAR(50)
	,@strStreetAddress			VARCHAR(50)
	,@strCity					VARCHAR(50)
	,@intStateID				INTEGER
	,@strZipCode				VARCHAR(50)
	,@strPhoneNumber			VARCHAR(50)	
	,@curSalary					MONEY			
	,@dtmDateOfBirth			DATE			
	,@intSexID					INTEGER			
	,@blnMostValuablePlayer		BIT				
	,@strEmailAddress			VARCHAR(50)		
AS
SET NOCOUNT ON		-- Report only errors
SET XACT_ABORT ON	-- Rollback transaction on error

BEGIN TRANSACTION
	
	DECLARE @intPlayerID			INTEGER

	-- Get the next highest ID and lock the table until the end of the transaction
	SELECT @intPlayerID = MAX( intPlayerID ) + 1 FROM TPlayers (TABLOCKX)

	-- Default to 1 if the table is empty
	SELECT @intPlayerID = COALESCE( @intPlayerID, 1 )

	-- Create new record
	INSERT INTO TPlayers( intPlayerID, strFirstName, strMiddleName, strLastName, strStreetAddress, strCity, intStateID, strZipCode, strPhoneNumber, curSalary, dtmDateOfBirth, intSexID, blnMostValuablePlayer, strEmailAddress, intPlayerStatusID )
	VALUES( @intPlayerID, @strFirstName, @strMiddleName, @strLastName, @strStreetAddress, @strCity, @intStateID, @strZipCode, @strPhoneNumber, @curSalary, @dtmDateOfBirth, @intSexID, @blnMostValuablePlayer, @strEmailAddress, 1 ) -- 1 = Active

	-- Return ID to calling program
	SELECT @intPlayerID AS intPlayerID

COMMIT TRANSACTION
GO

-- uspEditPlayer
CREATE PROCEDURE uspEditPlayer
	 @intPlayerID				INTEGER
	,@strFirstName				VARCHAR(50)
	,@strMiddleName				VARCHAR(50)
	,@strLastName				VARCHAR(50)
	,@strStreetAddress			VARCHAR(50)
	,@strCity					VARCHAR(50)
	,@intStateID				INTEGER
	,@strZipCode				VARCHAR(50)
	,@strPhoneNumber			VARCHAR(50)	
	,@curSalary					MONEY			
	,@dtmDateOfBirth			DATE			
	,@intSexID					INTEGER			
	,@blnMostValuablePlayer		BIT				
	,@strEmailAddress			VARCHAR(50)		
AS
SET NOCOUNT ON		-- Report only errors
SET XACT_ABORT ON	-- Rollback transaction on error

BEGIN TRANSACTION

	-- Update the record
	UPDATE
		TPlayers
	SET
		 strFirstName			= @strFirstName
		,strMiddleName			= @strMiddleName
		,strLastName			= @strLastName
		,strStreetAddress		= @strStreetAddress
		,strCity				= @strCity
		,intStateID				= @intStateID
		,strZipCode				= @strZipCode
		,strPhoneNumber			= @strPhoneNumber
		,curSalary				= @curSalary
		,dtmDateOfBirth			= @dtmDateOfBirth
		,intSexID				= @intSexID
		,blnMostValuablePlayer	= @blnMostValuablePlayer
		,strEmailAddress		= @strEmailAddress
	WHERE
		intPlayerID = @intPlayerID

COMMIT TRANSACTION
GO

-- uspSetPlayerStatus
CREATE PROCEDURE uspSetPlayerStatus
	 @intPlayerID				INTEGER
	,@intPlayerStatusID			INTEGER
AS
SET NOCOUNT ON		-- Report only errors
SET XACT_ABORT ON	-- Rollback transaction on error

BEGIN TRANSACTION

	-- Update the record
	UPDATE
		TPlayers
	SET
		 intPlayerStatusID = @intPlayerStatusID
	WHERE
		intPlayerID = @intPlayerID

COMMIT TRANSACTION
GO



-- --------------------------------------------------------------------------------
-- Step 2.10: Make two stored procedures for the TTeamPlayers table:
--			  uspAddTeamPlayer and uspRemoveTeamPlayer
-- --------------------------------------------------------------------------------

-- uspAddTeamPlayer
CREATE PROCEDURE uspAddTeamPlayer
	 @intTeamID					INTEGER
	,@intPlayerID				INTEGER
AS
SET NOCOUNT ON		-- Report only errors
SET XACT_ABORT ON	-- Rollback transaction on error

BEGIN TRANSACTION

	-- Create new record
	INSERT INTO TTeamPlayers( intTeamID, intPlayerID )
	VALUES( @intTeamID, @intPlayerID )

COMMIT TRANSACTION
GO

-- uspRemoveTeamPlayer
CREATE PROCEDURE uspRemoveTeamPlayer
	 @intTeamID					INTEGER
	,@intPlayerID				INTEGER
AS
SET NOCOUNT ON		-- Report only errors
SET XACT_ABORT ON	-- Rollback transaction on error

BEGIN TRANSACTION

	-- Remove specified record
	DELETE FROM
		 TTeamPlayers
	WHERE	
		 intTeamID		= @intTeamID
	AND	 intPlayerID	= @intPlayerID	

COMMIT TRANSACTION
GO



-- --------------------------------------------------------------------------------
-- Step 2.10: Extra Credit: Add the following stored procedures:
--			  uspAddAllPlayersToTeam and uspRemoveAllPlayersFromTeam
-- --------------------------------------------------------------------------------

-- uspAddAllPlayersToTeam
CREATE PROCEDURE uspAddAllPlayersToTeam
	@intTeamID				INTEGER
AS
SET NOCOUNT ON		-- Report only errors
SET XACT_ABORT ON	-- Rollback transaction on error

BEGIN TRANSACTION

	-- Insert all players into specified team
	INSERT INTO TTeamPlayers
	SELECT
		 @intTeamID
		,intPlayerID
	FROM
		 TPlayers
	WHERE intPlayerID NOT IN
		  ( SELECT
				intPlayerID 
			FROM
				TTeamPlayers 
			WHERE
				intTeamID = @intTeamID 
		  )

COMMIT TRANSACTION 
GO

-- uspRemoveAllPlayersFromTeam
CREATE PROCEDURE uspRemoveAllPlayersFromTeam
	@intTeamID				INTEGER
AS
SET NOCOUNT ON		-- Report only errors
SET XACT_ABORT ON	-- Rollback transaction on error

BEGIN TRANSACTION

	-- Remove all players from a specified team
	DELETE FROM TTeamPlayers
	WHERE	
		 intTeamID = @intTeamID

COMMIT TRANSACTION 
GO