//----------------------------------------------------------------------
// Name:	Brandin Henize
// Class:	Java 1
// Abstract:	CUserDataTypes
// ----------------------------------------------------------------------

//----------------------------------------------------------------------
// Name: CUserDataTypes
// Abstract: A collection of user defined types (e.g. structures)
//----------------------------------------------------------------------

//----------------------------------------------------------------------
// Package
//----------------------------------------------------------------------
package Utilities;

//----------------------------------------------------------------------
//Imports
//----------------------------------------------------------------------
import java.math.BigDecimal;

public class CUserDataTypes
{
	// Declare and make instance example:
	// udtTeamType udtNewTeam = new CUserDataTypes( ).new udtTeamType( );
	
	// Team structure
	public class udtTeamType
	{
		public int intTeamID = 0;
		public String strTeam = null;
		public String strMascot = null;
	}
	
	// Player structure
	public class udtPlayerType
	{
		public int intPlayerID = 0;
		public String strFirstName = null;
		public String strMiddleName = null;
		public String strLastName = null;
		public String strStreetAddress = null;
		public String strCity = null;
		public int intStateID = 0;
		public String strZipCode = null;
		public String strPhoneNumber = null;
		public BigDecimal bdecSalary = null;
		public java.sql.Date sdtmDateOfBirth = null;
		public int intSexID = 0;
		public boolean blnMostValuablePlayer = false;
		public String strEmailAddress = null;
	}
}


