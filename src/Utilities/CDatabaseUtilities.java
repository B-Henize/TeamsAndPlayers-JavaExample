//----------------------------------------------------------------------
// Name:	Brandin Henize
// Class:	Java 1
// Abstract:	CDatabaseUtilities
// ----------------------------------------------------------------------

//----------------------------------------------------------------------
// Name: CDatabaseUtilities
// Abstract: All procedures that directly interact with the database
//----------------------------------------------------------------------

//----------------------------------------------------------------------
// Package
//----------------------------------------------------------------------
package Utilities;


//----------------------------------------------------------------------
// Imports
//----------------------------------------------------------------------
import java.sql.*;			// SQL stuff

//import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.*; // Recommended in OpenDatabaseConnectionSQLServer( ) (explanation there)

import Utilities.CUserDataTypes.udtPlayerType;
import Utilities.CUserDataTypes.udtTeamType;

public class CDatabaseUtilities
{
		
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Properties
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Never make public properties.  
	// Make protected or private with public get/set methods
	
	private static Connection m_conAdministrator = null;
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Methods
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	
	// ----------------------------------------------------------------------
	// Name: OpenDatabaseConnection
	// Abstract: Open a connection to the database for JDK 1.8 or JRE8
	// 
	// NOTE: NOT USED IN THIS VERSION, PURELY FOR EXAMPLE.
	// ----------------------------------------------------------------------
	/*
	public static boolean OpenDatabaseConnection( )
	{
		boolean blnResult = false;
		
		try
		{
			String strConnectionString = "";
			
			// Register special driver
			Class.forName( "net.ucanaccess.jdbc.UcanaccessDriver" );
			
			// Server name/port, IP address/port or path for file based databases like MS Access
			// system.getProperty( "user.dir" ) => Current working directory from where
			// application was started
			strConnectionString += "jdbc:ucanaccess://" + System.getProperty( "user.dir" )
								 + "\\Database\\TeamsAndPlayers4.accdb;";
			
			// Special driver requires fore slashes(/) instead of backslashes(\)
			strConnectionString = strConnectionString.replace( '\\', '/' );
			
			// Open a connection to the database
			m_conAdministrator = DriverManager.getConnection( strConnectionString );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return blnResult;
	}
	*/
	
	
	// ----------------------------------------------------------------------
	// Name: OpenDatabaseConnectionSQLServer
	// Abstract: Open a connection to the database.
	//
	// Download the drivers. Search the Internet for "Microsoft JDBC Driver 4.0 for SQL Server".
	// One the links should be to the Microsoft Site. For example,
	// http://download.microsoft.com/download/0/2/A/02AAE597-3865-456C-AE7F-
	// 613F99F850A8/sqljdbc_4.0.2206.100_enu.exe
	// It's a self-extracting zip file. All it does is unzip the files. It doesn't actually
	// install anything.
	// -Extract the files somewhere
	// -Add a "Database/SQLServer" directory to your project
	// -Copy the sqljdbc4.jar file from the enu directory into "Database/SQLServer" directory in
	// your project.
	// You'll need this file later.
	// -Copy the sqljdbc_auth.dll file from enu\auth\x86 directory into the "Database/SQLServer"
	// directory in your project.
	// You'll need this file later.
	// -You can delete all the other files. You might want to save the original download file
	// in "Database/SQLServer" directory.
	//
	// How to configure Eclipse to use the drivers for just your project:
	// -Select/open your project in Eclipse
	// -Choose Project/Properties from the menu
	// -Select "Java Build Path" in the menu on the left
	// -Click on the "Libraries" tab on the right
	// -Click "Add External Jars..."
	// -Browse to your "sqljdbc4.jar" file which should be in the "Database/SQLServer" folder
	// in your project. If you move your project you'll need to remove the old
	// jar file and re-add it because Eclipse uses absolute paths instead of relative.
	// -Click OK
	// -Click OK
	// -Add "import com.microsoft.sqlserver.jdbc.*; // SQL Server specific drivers"
	//
	// How to configure Java to use "Integrated Security" for SQL Server Login
	// -Copy the sqljdbc_auth.dll from the download above to the
	// C:\Program Files (x86)\Java\JDK1.7.???\bin and C:\Program Files (x86)\Java\JRE7\bin
	// directories
	// -Restart Eclipse.
	//
	// You may have to enable TCP connections for SQL Server.
	// Start/All Programs/SQL Server/Configuration Tools/SQL Server Configuration Manager
	// SQL Server Network Configuration
	// Protocols for MSSQLServer
	// TCP/IP -> Enabled
	// Stop and restart the SQL Server Service (control panel/admin tools/services/SQL Server) for
	// changes to take affect.
	//
	// http://stackoverflow.com/questions/6662577/connect-sql-2008-r2-with-java-in-eclipse
	// ----------------------------------------------------------------------
	public static boolean OpenDatabaseConnectionSQLServer( )
	{
		boolean blnResult = false;
		
		try
		{
			SQLServerDataSource sdsTeamsAndPlayers = new SQLServerDataSource( );
			sdsTeamsAndPlayers.setServerName( "localhost" ); // localhost or IP or server name
			// sdsTeamsAndPlayers.setServerName( "localhost\\SQLExpress" ); // SQL Express version
			sdsTeamsAndPlayers.setPortNumber( 1433 );
			sdsTeamsAndPlayers.setDatabaseName( "dbTeamsAndPlayers" );
			
			// Login Type: 
				
				// Windows Integrated 
			sdsTeamsAndPlayers.setIntegratedSecurity( true );
			
				// OR
			
				// SQL Server
				// sdsTeamsAndPlayers.setUser( "admin" );
				// sdsTeamsAndPlayers.setPassword( "***" ); // Empty String "" for blank password
			
			// Open a connection to the database
			m_conAdministrator = sdsTeamsAndPlayers.getConnection( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
			
			// Warn about the SQL Server JDBC Drivers
			CMessageBox.Show( "Make sure you install the MS SQL Server JDBC drivers.",
							  "CDatabaseUtilitie::OpenDatabaseConnectionSQLServer( )" );
		}
		
		return blnResult; 
	}
	
	// ----------------------------------------------------------------------
	// Name: CloseDatabaseConnection
	// Abstract: Close the connection to the database
	// ----------------------------------------------------------------------
	public static boolean CloseDatabaseConnection( )
	{
		boolean blnResult = false;
		
		try
		{
			// Is there a connection object?
			if ( m_conAdministrator != null )
			{
				// Yes, close the connection if not closed already
				if( m_conAdministrator.isClosed( ) == false )
				{
					m_conAdministrator.close( );
					
					// Prevent JVM from crashing
					m_conAdministrator = null;
				}
			}
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return blnResult;
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: LoadListBoxFromDatabase
	// Abstract: Load the target list box with all name column values from
	// 			 the specified table.
	// ----------------------------------------------------------------------
	public static boolean LoadListBoxFromDatabase( String strTable, String strPrimaryKeyColumn,
												   String strNameColumn, CListBox lstTarget )
	{
		boolean blnResult = false;
		
		try
		{
			String strSelect = "";
			Statement sqlCommand = null;
			ResultSet rstTSource = null;
			int intID = 0;
			String strName = "";
			
			// Clear list
			lstTarget.Clear( );
			
			// Build the SQL string
			strSelect = "SELECT " + strPrimaryKeyColumn + ", " + strNameColumn
					  + " FROM " + strTable
					  + " ORDER BY " + strNameColumn;
			
			// Retrieve all the records
			sqlCommand = m_conAdministrator.createStatement( );
			rstTSource = sqlCommand.executeQuery( strSelect );
			
			// Loop through all the records
			while( rstTSource.next( ) == true )
			{
				// Get ID and Name from current row
				intID = rstTSource.getInt( 1 );
				strName = rstTSource.getString( 2 );
				
				// Add the item to the list (0 = id, strTeam = text to display, false = don't select)
				lstTarget.AddItemToList( intID, strName, false );
			}
			
			// Select first item in list by default
			if( lstTarget.Length( ) > 0 ) lstTarget.SetSelectedIndex( 0 );
			
			// Clean up
			rstTSource.close( );
			sqlCommand.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return blnResult;
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: LoadComboBoxFromDatabase
	// Abstract: Load the target list with the values from the specified table
	// ----------------------------------------------------------------------
	public static boolean LoadComboBoxFromDatabase( String strTable, String strPrimaryKeyColumn, 
												    String strNameColumn, CComboBox cmbTarget )
	{
		boolean blnResult = false;
		
		try
		{
			String strSelect = "";
			Statement sqlCommand = null;
			ResultSet rstTSource = null;
			int intID = 0;
			String strName = "";
			
			// Clear list
			cmbTarget.Clear( );
			
			// Build the SQL string
			strSelect = "SELECT " + strPrimaryKeyColumn + ", " + strNameColumn 
					  + " FROM " + strTable 
					  + " ORDER BY " + strNameColumn;

			// Retrieve the all the records
			sqlCommand = m_conAdministrator.createStatement( );
			rstTSource = sqlCommand.executeQuery( strSelect );

			// Loop through all the records
			while( rstTSource.next( ) == true )
			{
				// Get ID and Name from current row
				intID = rstTSource.getInt( 1 );
				strName = rstTSource.getString( 2 );

				// Add to list box (automatically makes a CListItem instance)
				cmbTarget.AddItemToList( intID, strName, false );
			}
			
			// Select first item in list by default
			if( cmbTarget.Length( ) > 0 ) cmbTarget.SetSelectedIndex( 0 );
			
			// Clean up
			rstTSource.close( );
			sqlCommand.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return blnResult;
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: GetNextHighestRecordID
	// Abstract: Get the next highest ID from the table in the database
	// ----------------------------------------------------------------------
	public static int GetNextHighestRecordID( String strPrimaryKey, String strTable )
	{
		int intNextHighestRecordID = 0;
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTable = null;
			
			// Build command
			strSQL = "SELECT MAX( " + strPrimaryKey + " ) + 1 AS intHighestRecordID"
				   + " FROM " + strTable;
			
			// Execute command
			sqlCommand = m_conAdministrator.createStatement( );
			rstTable = sqlCommand.executeQuery( strSQL );
			
			// Read result (next highest ID)
			if( rstTable.next( ) )
			{
				// Next highest ID
				intNextHighestRecordID = rstTable.getInt( "intHighestRecordID" );
			}
			else
			{
				// Table empty. Start at 1. NEVER start at 0.
				intNextHighestRecordID = 1;
			}
			
			// Clean Up
			rstTable.close( );
			sqlCommand.close( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		// Return result
		return intNextHighestRecordID;
	}
	
	
	
	// -------------------------------------------------------------------------
	// Name: DeleteRecordsFromTable
	// Abstract: Delete all records from table that match the ID.
	// -------------------------------------------------------------------------
	private static boolean DeleteRecordsFromTable( int intRecordID,
												   String strPrimaryKeyColumn,
												   String strTable )
	{
		boolean blnResult = false;
			
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;

			// Build the SQL String
			strSQL = "DELETE FROM " + strTable 
				   + " WHERE " + strPrimaryKeyColumn + " = " + intRecordID;

			// Do it
			sqlCommand = m_conAdministrator.createStatement( );
			sqlCommand.execute( strSQL );
				
			// Clean up
			sqlCommand.close( );

			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}

		return blnResult;
	}
		
		
		
	// ----------------------------------------------------------------------
	// Name: DeleteTeamFromDatabase
	// Abstract: Delete all records from table that match the ID.
	// ----------------------------------------------------------------------
	public static boolean DeleteTeamFromDatabase( int intTeamID )
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTTeams = null;
			
			// Build the SQL string
			strSQL = "SELECT * FROM TTeams WHERE intTeamID = " + intTeamID;
			
			// Retrieve the record
			sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE,
															 ResultSet.CONCUR_UPDATABLE );
			rstTTeams = sqlCommand.executeQuery( strSQL );
			
			// Edit the Team's information (should be 1 and only 1 row)
			rstTTeams.next( );
			rstTTeams.updateInt( "intTeamStatusID", CConstants.intTEAM_STATUS_INACTIVE );
			
			// Make the changes stick
			rstTTeams.updateRow( );
			
			// Clean up
			rstTTeams.close( );
			sqlCommand.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		// Return result
		return blnResult;
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: SetTeamStatusInDatabase
	// Abstract: Mark the specified team as inactive.
	// ----------------------------------------------------------------------
	@SuppressWarnings("unused")
	private static boolean SetTeamStatusInDatabase( int intTeamID, int intTeamStatusID )
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTTeams = null;
			
			// Build the SQL string
			strSQL = "SELECT * FROM TTeams WHERE intTeamID = " + intTeamID;
			
			// Retrieve the record
			sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE,
															 ResultSet.CONCUR_UPDATABLE );
			rstTTeams = sqlCommand.executeQuery( strSQL );
			
			// Edit the Team's information (should be 1 and only 1 row)
			rstTTeams.next( );
			rstTTeams.updateInt( "intTeamStatusID", intTeamStatusID );
			
			// Make the changes stick
			rstTTeams.updateRow( );
			
			// Clean up
			rstTTeams.close( );
			sqlCommand.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		// Return result
		return blnResult;
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: SetTeamStatusInDatabase2
	// Abstract: Set the team status using a stored procedure.
	// ----------------------------------------------------------------------
	private static boolean SetTeamStatusInDatabase2( int intTeamID, int intTeamStatusID )
	{
		boolean blnResult = false;
		
		try
		{
			CallableStatement cstSetTeamStatus = null;
			
			// Prepare stored procedure call
			cstSetTeamStatus = m_conAdministrator.prepareCall( "{ Call uspSetTeamStatus( ?,? ) }" );
			cstSetTeamStatus.setInt( 1, intTeamID );
			cstSetTeamStatus.setInt( 2, intTeamStatusID );
			
			// Execute command
			cstSetTeamStatus.execute( );
			
			// Clean up
			cstSetTeamStatus.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		// Return result
		return blnResult;
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: AddTeamToDatabase
	// Abstract: Add the team to the database
	// ----------------------------------------------------------------------
	public static boolean AddTeamToDatabase( udtTeamType udtTeam )
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTTeams = null;
			
			// Get the next highest team ID and save in suitcase so it's return to Add form
			udtTeam.intTeamID = GetNextHighestRecordID( "intTeamID", "TTeams" );
			
			// Race condition. MS Access doesn't support locking or stored procedures.
			
			// Build the select string (select no records)
			strSQL = "SELECT * FROM TTeams WHERE intTeamID = -1";
			
			// Retrieve all the records (should be none but we get table structure back)
			sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE,
															 ResultSet.CONCUR_UPDATABLE );
			rstTTeams = sqlCommand.executeQuery( strSQL );
			
			// New row using table structure returned from empty select.
			rstTTeams.moveToInsertRow( );
			rstTTeams.updateInt( "intTeamID",  udtTeam.intTeamID );
			rstTTeams.updateString( "strTeam",  udtTeam.strTeam );
			rstTTeams.updateString( "strMascot",  udtTeam.strMascot );
			rstTTeams.updateInt( "intTeamStatusID", CConstants.intTEAM_STATUS_ACTIVE );
			
			// Make the changes stick
			rstTTeams.insertRow( );
			
			// Clean up
			rstTTeams.close( );
			sqlCommand.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		// Return result
		return blnResult;
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: AddTeamToDatabase2
	// Abstract: Add the team to the database. For SQL Server with stored procedures.
	// 			 See stored procedure examples in database directory. 
	// ----------------------------------------------------------------------
	public static boolean AddTeamToDatabase2( udtTeamType udtTeam )
	{
		boolean blnResult = false;
		
		try
		{
			CallableStatement cstAddTeam = null;
			ResultSet rstTTeams = null;
			
			// Prepare stored procedure call
			cstAddTeam = m_conAdministrator.prepareCall( "{ Call uspAddTeam( ?,? ) }" );
			cstAddTeam.setString( 1, udtTeam.strTeam );
			cstAddTeam.setString( 2, udtTeam.strMascot );
			
			// Execute command
			cstAddTeam.execute( );
			
			// Read result( Team ID )
			rstTTeams = cstAddTeam.getResultSet( );
			rstTTeams.next( );
			udtTeam.intTeamID = rstTTeams.getInt( "intTeamID" );
			
			// Clean up
			rstTTeams.close( );
			cstAddTeam.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		// Return result
		return blnResult;
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: GetTeamInformationFromDatabase
	// Abstract: Get data for the specified team from the database
	// ----------------------------------------------------------------------
	public static boolean GetTeamInformationFromDatabase( udtTeamType udtTeam )
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTTeams = null;
			
			// Build the select string
			strSQL = "SELECT * FROM TTeams WHERE intTeamID = " + udtTeam.intTeamID;

			// Retrieve the record)
			sqlCommand = m_conAdministrator.createStatement( );
			rstTTeams = sqlCommand.executeQuery( strSQL );
			
			// Get the Team's information (should be 1 and only 1 row)
			rstTTeams.next( );
			udtTeam.strTeam = rstTTeams.getString( "strTeam" );
			udtTeam.strMascot = rstTTeams.getString( "strMascot" );
			
			// Clean up
			rstTTeams.close( );
			sqlCommand.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		// Return result
		return blnResult;
		
		
		
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: EditTeamInDatabase
	// Abstract: Update the information for the specified team in the database
	// ----------------------------------------------------------------------
	public static boolean EditTeamInDatabase( udtTeamType udtTeam )
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTTeams = null;
			
			// Build the select string
			strSQL = "SELECT * FROM TTeams WHERE intTeamID = " + udtTeam.intTeamID;

			// Retrieve the record)
			sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, 
															 ResultSet.CONCUR_UPDATABLE );
			rstTTeams = sqlCommand.executeQuery( strSQL );
			
			// Get the Team's information (should be 1 and only 1 row)
			rstTTeams.next( );
			rstTTeams.updateString( "strTeam",  udtTeam.strTeam );
			rstTTeams.updateString( "strMascot", udtTeam.strMascot );
			
			// Make the changes stick
			rstTTeams.updateRow( );
			
			// Clean up
			rstTTeams.close( );
			sqlCommand.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		// Return result
		return blnResult;
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: EditTeamInDatabase2
	// Abstract: Edit the team in the database using a stored procedure.
	// ----------------------------------------------------------------------
	public static boolean EditTeamInDatabase2( udtTeamType udtTeam )
	{
		boolean blnResult = false;
		
		try
		{
			CallableStatement cstEditTeam = null;
			
			// Prepare stored procedure call
			cstEditTeam = m_conAdministrator.prepareCall( "{ Call uspEditTeam( ?,?,? ) }" );
			cstEditTeam.setInt(		1, udtTeam.intTeamID );
			cstEditTeam.setString(	2, udtTeam.strTeam );
			cstEditTeam.setString(	3, udtTeam.strMascot );
			
			// Execute command
			cstEditTeam.execute( );
			
			// Clean up
			cstEditTeam.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		// Return result
		return blnResult;
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: DeleteTeamFromTable
	// Abstract: Delete the specified team from the database.
	// ----------------------------------------------------------------------
	public static boolean DeleteTeamFromTable( int intTeamID )
	{
		boolean blnResult = false;
		
		try
		{
			blnResult = SetTeamStatusInDatabase2 ( intTeamID, CConstants.intTEAM_STATUS_INACTIVE );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		// Return result
		return blnResult;
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: UndeleteTeamFromTable
	// Abstract: Delete the specified team from the database.
	// ----------------------------------------------------------------------
	public static boolean UndeleteTeamFromTable( int intTeamID )
	{
		boolean blnResult = false;
		
		try
		{
			blnResult = SetTeamStatusInDatabase2 ( intTeamID, CConstants.intTEAM_STATUS_ACTIVE );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		// Return result
		return blnResult;
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: LoadListWithPlayersFromDatabase
	// Abstract: Load all the players on/not on the specified team.
	// ----------------------------------------------------------------------
	public static boolean LoadListWithPlayersFromDatabase( int intTeamID, 
														   CListBox lstTarget,
														   boolean blnPlayersOnTeam )
	{
		boolean blnResult = false;
		
		try
		{
			String strSelect = "";
			Statement sqlCommand = null;
			ResultSet rstTTeamPlayers = null;
			String strNot = "NOT";
			int intID = 0;
			String strName = "";
			
			// Clear list
			lstTarget.Clear( );
			
			// Selected Players? Yes
			if( blnPlayersOnTeam == true ) strNot = "";
			
			// Build the SQL string. See chapter on sub-queries in SQL Server for Programmers
					  // Load all players... 
			strSelect = "SELECT intPlayerID, strLastName + ', ' + strFirstName"
					  + " FROM VActivePlayers"
					  // ... that are/are not ...
					  + " WHERE intPlayerID " + strNot + " IN "
					  // ... on the team
					  + "	( SELECT intPlayerID FROM TTeamPlayers WHERE intTeamID = " + intTeamID + " ) "
					  + " ORDER BY strLastName, strFirstName";
			
			// Retrieve all the records
			sqlCommand = m_conAdministrator.createStatement( );
			rstTTeamPlayers = sqlCommand.executeQuery( strSelect );
			
			// Loop through all the records
			while( rstTTeamPlayers.next( ) == true )
			{
				// Get the ID and Name from the current row
				intID = rstTTeamPlayers.getInt( 1 );
				strName = rstTTeamPlayers.getString( 2 );
				
				// Add the item to the list
				lstTarget.AddItemToList( intID, strName, false );
			}
			
			// Select first item in list by default
			if( lstTarget.Length( ) > 0 ) lstTarget.SetSelectedIndex( 0 );
			
			// Clean Up
			rstTTeamPlayers.close( );
			sqlCommand.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		// Return result
		return blnResult;
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: AddAllPlayersToTeamInDatabase
	// Abstract: Add all the players to the specified team
	// ----------------------------------------------------------------------
	public static boolean AddAllPlayersToTeamInDatabase( int intTeamID )
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;

			// Build the SQL String
			// Add all players to the team that are not already on the team
			strSQL = "INSERT INTO TTeamPlayers "
				   + "  SELECT " + intTeamID + ", intPlayerID "
				   + "  FROM TPlayers"
				   + "  WHERE intPlayerID NOT IN"
				   + "  ( SELECT intPlayerID "
				   + "     FROM TTeamPlayers "
				   + "     WHERE intTeamID = " + intTeamID 
				   + "  )";

			// Do it
			sqlCommand = m_conAdministrator.createStatement( );
			sqlCommand.execute( strSQL );
			
			// Clean up
			sqlCommand.close( );

			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}

		return blnResult;
	}


	// ----------------------------------------------------------------------
	// Name: AddAllPlayersToTeamInDatabase2
	// Abstract: Add all the players to the specified team with a stored procedure
	// ----------------------------------------------------------------------
	public static boolean AddAllPlayersToTeamInDatabase2( int intTeamID )
	{
		boolean blnResult = false;
		
		try
		{
			CallableStatement cstAddAllPlayersToTeam = null; 

			// Prepare stored procedure call
			cstAddAllPlayersToTeam = m_conAdministrator.prepareCall( "{ Call uspAddAllPlayersToTeam( ? ) }" ); 
			cstAddAllPlayersToTeam.setInt( 1, intTeamID );
			
			// Execute command
			cstAddAllPlayersToTeam.execute( );

			// Clean up
			cstAddAllPlayersToTeam.close( );

			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}

		return blnResult;
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: AddPlayerToTeamInDatabase
	// Abstract: Add the player to the specified team.
	// ----------------------------------------------------------------------
	public static boolean AddPlayerToTeamInDatabase( int intTeamID, int intPlayerID )
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTTeamPlayers = null;
			
			
			// Build the select string (select no records)
			strSQL = "SELECT * FROM TTeamPlayers WHERE intTeamID = -1";
			
			// Retrieve all the records (should be none but we get table structure back)
			sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE,
															 ResultSet.CONCUR_UPDATABLE );
			rstTTeamPlayers = sqlCommand.executeQuery( strSQL );
			
			// New row using the table structure returned from the empty select
			rstTTeamPlayers.moveToInsertRow( );
			rstTTeamPlayers.updateInt( "intTeamID", intTeamID );
			rstTTeamPlayers.updateInt( "intPlayerID", intPlayerID );
			
			// Make the changes stick
			rstTTeamPlayers.insertRow( );
			
			// Clean up
			rstTTeamPlayers.close( );
			sqlCommand.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		// Return result
		return blnResult;	
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: AddPlayerToTeamInDatabase2
	// Abstract: Add the player to the specified team using a stored procedure.
	// ----------------------------------------------------------------------
	public static boolean AddPlayerToTeamInDatabase2( int intTeamID, int intPlayerID )
	{
		boolean blnResult = false;
		
		try
		{
			CallableStatement cstAddPlayerToTeam = null;
			
			// Prepare stored procedure call
			cstAddPlayerToTeam = m_conAdministrator.prepareCall( "{ Call uspAddTeamPlayer( ?,? ) }" );
			cstAddPlayerToTeam.setInt( 1, intTeamID );
			cstAddPlayerToTeam.setInt( 2, intPlayerID );
			
			// Execute command
			cstAddPlayerToTeam.execute( );
			
			// Clean up
			cstAddPlayerToTeam.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		// Return result
		return blnResult;	
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: RemovePlayerFromTeamInDatabase
	// Abstract: Remove a player from the specified team.
	// ----------------------------------------------------------------------
	public static boolean RemovePlayerFromTeamInDatabase( int intTeamID, int intPlayerID )
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTTeamPlayers = null;
			
			
			// Build the select string
			strSQL = "SELECT * FROM TTeamPlayers" +
					 " WHERE intTeamID = " + intTeamID +
					 " AND intPlayerID = " + intPlayerID;
			
			// Retrieve all the records
			sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE,
															 ResultSet.CONCUR_UPDATABLE );
			rstTTeamPlayers = sqlCommand.executeQuery( strSQL );
			
			// Should be 1 and only 1 row
			rstTTeamPlayers.next( );
			rstTTeamPlayers.deleteRow( );
			
			// Clean up
			rstTTeamPlayers.close( );
			sqlCommand.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		// Return result
		return blnResult;	
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: RemovePlayerFromTeamInDatabase2
	// Abstract: Remove the player from the specified team using a stored procedure.
	// ----------------------------------------------------------------------
	public static boolean RemovePlayerFromTeamInDatabase2( int intTeamID, int intPlayerID )
	{
		boolean blnResult = false;
		
		try
		{
			CallableStatement cstRemovePlayerToTeam = null;
			
			// Prepare stored procedure call
			cstRemovePlayerToTeam = m_conAdministrator.prepareCall( "{ Call uspRemoveTeamPlayer( ?,? ) }" );
			cstRemovePlayerToTeam.setInt( 1, intTeamID );
			cstRemovePlayerToTeam.setInt( 2, intPlayerID );
			
			// Execute command
			cstRemovePlayerToTeam.execute( );
			
			// Clean up
			cstRemovePlayerToTeam.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		// Return result
		return blnResult;	
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: RemoveAllPlayersFromTeamInDatabase
	// Abstract: Remove all the players from the specified team
	// ----------------------------------------------------------------------
	public static boolean RemoveAllPlayersFromTeamInDatabase( int intTeamID )
	{
		boolean blnResult = false;
		
		try
		{
			blnResult = DeleteRecordsFromTable( intTeamID, "intTeamID", "TTeamPlayers" );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}

		return blnResult;
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: RemoveAllPlayersFromTeamInDatabase2
	// Abstract: Remove all the players from the specified team with a stored procedure
	// ----------------------------------------------------------------------
	public static boolean RemoveAllPlayersFromTeamInDatabase2( int intTeamID )
	{
		boolean blnResult = false;
		
		try
		{
			CallableStatement cstRemoveAllPlayersFromTeam = null; 

			// Prepare stored procedure call
			cstRemoveAllPlayersFromTeam = m_conAdministrator.prepareCall( "{ Call uspRemoveAllPlayersFromTeam( ? ) }" ); 
			cstRemoveAllPlayersFromTeam.setInt( 1, intTeamID );
			
			// Execute command
			cstRemoveAllPlayersFromTeam.execute( );

			// Clean up
			cstRemoveAllPlayersFromTeam.close( );

			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}

		return blnResult;
	}
	
	
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Players
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	
	// ----------------------------------------------------------------------
	// Name: AddPlayerToDatabase
	// Abstract: Add the player to the database
	// ----------------------------------------------------------------------
	public static boolean AddPlayerToDatabase( udtPlayerType udtPlayer )
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTPlayers = null;
			
			// Get the next highest Player ID and save in suitcase so it's return to Add form
			udtPlayer.intPlayerID = GetNextHighestRecordID( "intPlayerID", "TPlayers" );
			
			// Race condition. MS Access doesn't support locking or stored procedures.
			
			// Build the select string (select no records)
			strSQL = "SELECT * FROM TPlayers WHERE intPlayerID = -1";
			
			// Retrieve all the records (should be none but we get table structure back)
			sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE,
															 ResultSet.CONCUR_UPDATABLE );
			rstTPlayers = sqlCommand.executeQuery( strSQL );
			
			// New row using table structure returned from empty select.
			rstTPlayers.moveToInsertRow( );
			rstTPlayers.updateInt( 			"intPlayerID",				udtPlayer.intPlayerID );
			rstTPlayers.updateString( 		"strFirstName",				udtPlayer.strFirstName );
			rstTPlayers.updateString( 		"strMiddleName",			udtPlayer.strMiddleName );
			rstTPlayers.updateString( 		"strLastName",				udtPlayer.strLastName );
			rstTPlayers.updateString( 		"strStreetAddress", 		udtPlayer.strStreetAddress );
			rstTPlayers.updateString( 		"strCity", 					udtPlayer.strCity );
			rstTPlayers.updateInt( 			"intStateID", 				udtPlayer.intStateID );
			rstTPlayers.updateString( 		"strZipCode", 				udtPlayer.strZipCode );
			rstTPlayers.updateString( 		"strPhoneNumber", 			udtPlayer.strPhoneNumber );
			rstTPlayers.updateBigDecimal( 	"curSalary", 				udtPlayer.bdecSalary );
			rstTPlayers.updateDate( 		"dtmDateOfBirth", 			udtPlayer.sdtmDateOfBirth );
			rstTPlayers.updateInt( 			"intSexID", 				udtPlayer.intSexID );
			rstTPlayers.updateBoolean( 		"blnMostValuablePlayer", 	udtPlayer.blnMostValuablePlayer );
			rstTPlayers.updateString( 		"strEmailAddress", 			udtPlayer.strEmailAddress );
			rstTPlayers.updateInt( 			"intPlayerStatusID", 		CConstants.intPLAYER_STATUS_ACTIVE );
			
			// Make the changes stick
			rstTPlayers.insertRow( );
			
			// Clean up
			rstTPlayers.close( );
			sqlCommand.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		// Return result
		return blnResult;
		
		
		
	}
	

	
	// -------------------------------------------------------------------------
	// Name: AddPlayerToDatabase2
	// Abstract: Add the player to the database using a stored procedure.  
	// -------------------------------------------------------------------------
	public static boolean AddPlayerToDatabase2( udtPlayerType udtPlayer )
	{
		boolean blnResult = false;
		
		try
		{
			CallableStatement cstAddPlayer = null; 
			ResultSet rstTPlayers = null;

			// Prepare stored procedure call
			cstAddPlayer = m_conAdministrator.prepareCall( "{ Call uspAddPlayer( ?,?,?,?,?,?,?,?,?,?,?,?,? ) }" ); 
			cstAddPlayer.setString(		  1, udtPlayer.strFirstName );
			cstAddPlayer.setString(		  2, udtPlayer.strMiddleName );
			cstAddPlayer.setString(		  3, udtPlayer.strLastName );
			cstAddPlayer.setString(	 	  4, udtPlayer.strStreetAddress );
			cstAddPlayer.setString( 	  5, udtPlayer.strCity );
			cstAddPlayer.setInt(		  6, udtPlayer.intStateID );
			cstAddPlayer.setString(		  7, udtPlayer.strZipCode );
			cstAddPlayer.setString(		  8, udtPlayer.strPhoneNumber );
			cstAddPlayer.setBigDecimal(   9, udtPlayer.bdecSalary );
			cstAddPlayer.setDate(		 10, udtPlayer.sdtmDateOfBirth );
			cstAddPlayer.setInt(		 11, udtPlayer.intSexID );
			cstAddPlayer.setBoolean(	 12, udtPlayer.blnMostValuablePlayer );
			cstAddPlayer.setString(	 	 13, udtPlayer.strEmailAddress );
 
			// Execute command
			cstAddPlayer.execute( );

			// Read result( Player ID )
			rstTPlayers = cstAddPlayer.getResultSet( );
			rstTPlayers.next( );
			udtPlayer.intPlayerID = rstTPlayers.getInt( "intPlayerID" );

			// Clean up
			rstTPlayers.close( );
			cstAddPlayer.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}

		return blnResult;
	}


	
	// ----------------------------------------------------------------------
	// Name: GetPlayerInformationFromDatabase
	// Abstract: Get data for the specified player from the database
	// ----------------------------------------------------------------------
	public static boolean GetPlayerInformationFromDatabase( udtPlayerType udtPlayer )
	{
		boolean blnResult = false;
			
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTPlayers = null;
				
			// Build the select string
			strSQL = "SELECT * FROM TPlayers WHERE intPlayerID = " + udtPlayer.intPlayerID;

			// Retrieve the record)
			sqlCommand = m_conAdministrator.createStatement( );
			rstTPlayers = sqlCommand.executeQuery( strSQL );
				
			// Get the Player's information (should be 1 and only 1 row)
			rstTPlayers.next( );
				
			// suitcase <- database
			udtPlayer.strFirstName 			= rstTPlayers.getString( 		"strFirstName" );
			udtPlayer.strMiddleName 		= rstTPlayers.getString( 		"strMiddleName" );
			udtPlayer.strLastName 			= rstTPlayers.getString( 		"strLastName" );
			udtPlayer.strStreetAddress 		= rstTPlayers.getString( 		"strStreetAddress" );
			udtPlayer.strCity 				= rstTPlayers.getString( 		"strCity" );
			udtPlayer.intStateID 			= rstTPlayers.getInt( 			"intStateID" );
			udtPlayer.strZipCode 			= rstTPlayers.getString( 		"strZipCode" );
			udtPlayer.strPhoneNumber 		= rstTPlayers.getString( 		"strPhoneNumber" );
			udtPlayer.bdecSalary 			= rstTPlayers.getBigDecimal( 	"curSalary" );
			udtPlayer.sdtmDateOfBirth 		= rstTPlayers.getDate( 			"dtmDateOfBirth" );
			udtPlayer.intSexID 				= rstTPlayers.getInt( 			"intSexID" );
			udtPlayer.blnMostValuablePlayer = rstTPlayers.getBoolean( 		"blnMostValuablePlayer" );
			udtPlayer.strEmailAddress		= rstTPlayers.getString( 		"strEmailAddress" );
				
			// Clean up
			rstTPlayers.close( );
			sqlCommand.close( );
				
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		// Return result
		return blnResult;
	}
		
		
		
	// -------------------------------------------------------------------------
	// Name: EditPlayerInDatabase
	// Abstract: Update the information for the specified Player in the database
	// -------------------------------------------------------------------------
	public static boolean EditPlayerInDatabase( udtPlayerType udtPlayer )
	{
		boolean blnResult = false;
			
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTPlayers = null;
			
			// Build the select string
			strSQL = "SELECT * FROM TPlayers WHERE intPlayerID = " + udtPlayer.intPlayerID;

			// Retrieve the record
			sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, 
																 ResultSet.CONCUR_UPDATABLE );
			rstTPlayers = sqlCommand.executeQuery( strSQL );

			// Edit the Player's information (should be 1 and only 1 row) 
			rstTPlayers.next( );
			rstTPlayers.updateString(	  "strFirstName", 			udtPlayer.strFirstName );
			rstTPlayers.updateString(	  "strMiddleName", 			udtPlayer.strMiddleName );
			rstTPlayers.updateString(	  "strLastName", 			udtPlayer.strLastName );
			rstTPlayers.updateString(	  "strStreetAddress", 		udtPlayer.strStreetAddress );
			rstTPlayers.updateString(	  "strCity", 				udtPlayer.strCity );
			rstTPlayers.updateInt( 	 	  "intStateID", 			udtPlayer.intStateID );
			rstTPlayers.updateString( 	  "strZipCode", 			udtPlayer.strZipCode );
			rstTPlayers.updateString( 	  "strPhoneNumber",			udtPlayer.strPhoneNumber );
			rstTPlayers.updateBigDecimal( "curSalary",				udtPlayer.bdecSalary );
			rstTPlayers.updateDate( 	  "dtmDateOfBirth", 		udtPlayer.sdtmDateOfBirth );
			rstTPlayers.updateInt( 	  	  "intSexID", 				udtPlayer.intSexID );
			rstTPlayers.updateBoolean( 	  "blnMostValuablePlayer", 	udtPlayer.blnMostValuablePlayer );
			rstTPlayers.updateString(	  "strEmailAddress", 		udtPlayer.strEmailAddress );
				
			// Make the changes stick
			rstTPlayers.updateRow( );

			// Clean up
			rstTPlayers.close( );
			sqlCommand.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}

		return blnResult;
	}
		
		
	// -------------------------------------------------------------------------
	// Name: EditPlayerInDatabase2
	// Abstract: Edit the player in the database using a stored procedure.
	// -------------------------------------------------------------------------
	public static boolean EditPlayerInDatabase2( udtPlayerType udtPlayer )
	{
		boolean blnResult = false;

		try
		{
			CallableStatement cstEditPlayer = null; 
				
			// Prepare stored procedure call
			cstEditPlayer = m_conAdministrator.prepareCall( "{ Call uspEditPlayer( ?,?,?,?,?,?,?,?,?,?,?,?,?,? ) }" ); 
			cstEditPlayer.setInt( 		  1, udtPlayer.intPlayerID );
			cstEditPlayer.setString(	  2, udtPlayer.strFirstName );
			cstEditPlayer.setString(	  3, udtPlayer.strMiddleName );
			cstEditPlayer.setString(	  4, udtPlayer.strLastName );
			cstEditPlayer.setString(	  5, udtPlayer.strStreetAddress );
			cstEditPlayer.setString( 	  6, udtPlayer.strCity );
			cstEditPlayer.setInt(		  7, udtPlayer.intStateID );
			cstEditPlayer.setString(	  8, udtPlayer.strZipCode );
			cstEditPlayer.setString(	  9, udtPlayer.strPhoneNumber );
			cstEditPlayer.setBigDecimal( 10, udtPlayer.bdecSalary );
			cstEditPlayer.setDate(		 11, udtPlayer.sdtmDateOfBirth );
			cstEditPlayer.setInt(		 12, udtPlayer.intSexID );
			cstEditPlayer.setBoolean(	 13, udtPlayer.blnMostValuablePlayer );
			cstEditPlayer.setString(	 14, udtPlayer.strEmailAddress );
				
			// Execute command
			cstEditPlayer.execute( );

			// Clean up
			cstEditPlayer.close( );
				
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}

		return blnResult;
	}

		
	
	
	
	// ----------------------------------------------------------------------
	// Name: DeletePlayerFromDatabase
	// Abstract: Delete all records from table that match the ID.
	// ----------------------------------------------------------------------
//	public static boolean DeletePlayerFromDatabase( int intPlayerID )
//	{
//		boolean blnResult = false;
//		
//		try
//		{
//			String strSQL = "";
//			Statement sqlCommand = null;
//			ResultSet rstTPlayers = null;
//			
//			// Build the SQL string
//			strSQL = "SELECT * FROM TPlayers WHERE intPlayerID = " + intPlayerID;
//			
//			// Retrieve the record
//			sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE,
//															 ResultSet.CONCUR_UPDATABLE );
//			rstTPlayers = sqlCommand.executeQuery( strSQL );
//			
//			// Edit the Player's information (should be 1 and only 1 row)
//			rstTPlayers.next( );
//			rstTPlayers.updateInt( "intPlayerStatusID", CConstants.intPLAYER_STATUS_INACTIVE );
//			
//			// Make the changes stick
//			rstTPlayers.updateRow( );
//			
//			// Clean up
//			rstTPlayers.close( );
//			sqlCommand.close( );
//			
//			// Success
//			blnResult = true;
//		}
//		catch( Exception excError )
//		{
//			// Display Error Message
//			CUtilities.WriteLog( excError );
//		}
//		// Return result
//		return blnResult;
//	}
	
	
	
	// -------------------------------------------------------------------------
	// Name: DeletePlayerFromDatabase
	// Abstract: Mark the specified Player as inactive.
	// -------------------------------------------------------------------------
	public static boolean DeletePlayerFromDatabase( int intPlayerID )
	{
		boolean blnResult = false;
		
		try
		{
			blnResult = SetPlayerStatusInDatabase( intPlayerID, CConstants.intPLAYER_STATUS_INACTIVE );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}

		return blnResult;
	}	
	
	
	
	// ----------------------------------------------------------------------
	// Name: DeletePlayerFromTable
	// Abstract: Delete the specified team from the database.
	// ----------------------------------------------------------------------
	public static boolean DeletePlayerFromTable( int intPlayerID )
	{
		boolean blnResult = false;
		
		try
		{
			blnResult = SetPlayerStatusInDatabase2 ( intPlayerID, CConstants.intPLAYER_STATUS_INACTIVE );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		// Return result
		return blnResult;
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: UndeletePlayerFromTable
	// Abstract: Delete the specified team from the database.
	// ----------------------------------------------------------------------
	public static boolean UndeletePlayerFromTable( int intPlayerID )
	{
		boolean blnResult = false;
		
		try
		{
			blnResult = SetPlayerStatusInDatabase2 ( intPlayerID, CConstants.intPLAYER_STATUS_ACTIVE );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		// Return result
		return blnResult;
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: SetPlayerStatusInDatabase
	// Abstract: Mark the specified player as inactive.
	// ----------------------------------------------------------------------
	private static boolean SetPlayerStatusInDatabase( int intPlayerID, int intPlayerStatusID )
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTPlayers = null;
			
			// Build the SQL string
			strSQL = "SELECT * FROM TPlayers WHERE intPlayerID = " + intPlayerID;
			
			// Retrieve the record
			sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE,
															 ResultSet.CONCUR_UPDATABLE );
			rstTPlayers = sqlCommand.executeQuery( strSQL );
			
			// Edit the Player's information (should be 1 and only 1 row)
			rstTPlayers.next( );
			rstTPlayers.updateInt( "intPlayerStatusID", intPlayerStatusID );
			
			// Make the changes stick
			rstTPlayers.updateRow( );
			
			// Clean up
			rstTPlayers.close( );
			sqlCommand.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		// Return result
		return blnResult;
	}
	
	
	
	// -------------------------------------------------------------------------
	// Name: SetPlayerStatusInDatabase2
	// Abstract: Set the Player status using a stored procedure.
	// -------------------------------------------------------------------------
	private static boolean SetPlayerStatusInDatabase2( int intPlayerID, int intPlayerStatusID )
	{
		boolean blnResult = false;
		
		try
		{
			CallableStatement cstSetPlayerStatus = null; 

			// Prepare stored procedure call
			cstSetPlayerStatus = m_conAdministrator.prepareCall( "{ Call uspSetPlayerStatus( ?,? ) }" ); 
			cstSetPlayerStatus.setInt( 1, intPlayerID );
			cstSetPlayerStatus.setInt( 2, intPlayerStatusID );
			
			// Execute command
			cstSetPlayerStatus.execute( );

			// Clean up
			cstSetPlayerStatus.close( );
			
			// Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}

		return blnResult;
	}
}