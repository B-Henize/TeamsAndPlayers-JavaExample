// ----------------------------------------------------------------------
// Name: Package
// Abstract: Package location
// ----------------------------------------------------------------------
package Players;
// ----------------------------------------------------------------------
// Name: DEditForm
// Abstract: a basic windows modal form
// ----------------------------------------------------------------------


// ----------------------------------------------------------------------
// Imports
// ----------------------------------------------------------------------
import java.awt.*;						// Basic windows functionality
import java.awt.event.*;				// Event processing
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.swing.*;					// Supplemental windows functionality

import Utilities.*;
import Utilities.CMessageBox.enuIconType;
import Utilities.CUserDataTypes.udtPlayerType;

@SuppressWarnings({ "serial", "unused" })
public class DEditPlayer extends JDialog implements ActionListener,
											      	WindowListener
{
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Controls( never make public )
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Buttons 
	private JButton m_btnOK = null;
	private JButton m_btnCancel = null;
	
	// Text boxes
	private CTextBox m_txtFirstName = null;
	private CTextBox m_txtMiddleName = null;
	private CTextBox m_txtLastName = null;
	private CTextBox m_txtStreetAddress = null;
	private CTextBox m_txtCity = null;
	private CTextBox m_txtZipCode = null;
	private CTextBox m_txtPhoneNumber = null;
	private CTextBox m_txtSalary = null;
	private CTextBox m_txtDateOfBirth = null;
	private CTextBox m_txtEmailAddress = null;
	
	// Combo Boxes
	private CComboBox m_cboStates = null;
	
	// Labels
	private JLabel m_lblFirstName = null;
	private JLabel m_lblMiddleName = null;
	private JLabel m_lblLastName = null;
	private JLabel m_lblStreetAddress = null;
	private JLabel m_lblCity = null;
	private JLabel m_lblState = null;
	private JLabel m_lblZipCode = null;
	private JLabel m_lblPhoneNumber = null;
	private JLabel m_lblSalary = null;
	private JLabel m_lblDateOfBirth = null;
	private JLabel m_lblSex = null;
	private JLabel m_lblMostValuablePlayer = null;
	private JLabel m_lblEmailAddress = null;
	
	private JLabel m_lblRequiredField = null;
	private JLabel m_lblPhoneFormatting = null;
	private JLabel m_lblDateOfBirthFormatting = null;
	
	// Radio Buttons
	private ButtonGroup m_bgpSex = null;
	private JRadioButton m_radFemale = null;
	private JRadioButton m_radMale = null;
	
	// Check Button
	private JCheckBox m_chkMostValuablePlayer = null;
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Properties( never make public )
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Never make public properties.  
	// Make protected or private with public get/set methods
	private int m_intPlayerToEditID = 0;
	private boolean m_blnResult = false;
	
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Methods
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	
	// ----------------------------------------------------------------------
	// Name: DEditPlayer
	// Abstract: Constructor
	// ----------------------------------------------------------------------
	public DEditPlayer( JDialog dlgParent, int intPlayerToEditID )
	{
		super( dlgParent, true );	// true = modal
		
		try
		{
			// Save ID for loading and saving
			m_intPlayerToEditID = intPlayerToEditID;
			
			// Initialize the frame
			Initialize( );
			
			// Edit the controls to the frame
			AddControls( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: Initialize
	// Abstract: Set the form properties
	// ----------------------------------------------------------------------
	public void Initialize( )
	{

		try
		{
			int intHeight = 515;
			int intWidth = 370;

			// Title
			setTitle( "Edit Player" );
				
			// Size
			setSize( intWidth, intHeight );
			
			// Center screen
			CUtilities.CenterOwner( this );
			
			// No resize
			setResizable( false );
			
			// Listen for window events
			this.addWindowListener( this );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}

	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: paint
	// Abstract: Override the paint event to draw grid marks
	// ----------------------------------------------------------------------
	public void paint( Graphics g )
	{
		super.paint( g );
		try
		{
			// CUtilities.DrawGridMarks( this, g );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}

	}
	
	
	
	public void AddControls( )
	{
		float sngFontSize = (float) 0.8;
		
		try
		{
			// Clear layout manager so we can manually size and position controls
			CUtilities.ClearLayoutManager( this );
			
			// Labels 
			
				// m_lblFirstName
				m_lblFirstName = CUtilities.AddLabel( this, "First Name:*", 25, 20);
			
				// m_lblMiddleName
				m_lblMiddleName = CUtilities.AddLabel( this, "Middle Name:", 55, 20);
				
				// m_lblLastName
				m_lblLastName = CUtilities.AddLabel( this, "Last Name:*", 85, 20);
				
				// m_lblStreetAddress
				m_lblStreetAddress = CUtilities.AddLabel( this, "Street Address:", 115, 20);
				
				// m_lblCity
				m_lblCity = CUtilities.AddLabel( this, "City:", 145, 20);
				
				// m_lblState
				m_lblState = CUtilities.AddLabel( this, "State:", 175, 20);
				
				// m_lblZipCode
				m_lblZipCode = CUtilities.AddLabel( this, "Zip Code:", 205, 20);
				
				// m_lblPhoneNumber
				m_lblPhoneNumber = CUtilities.AddLabel( this, "Home Phone Number:", 235, 20);
				
				// m_lblSalary
				m_lblSalary = CUtilities.AddLabel( this, "Salary:", 265, 20);
				
				// m_lblDateOfBirth
				m_lblDateOfBirth = CUtilities.AddLabel( this, "Date Of Birth:", 295, 20);
				
				// m_lblSex
				m_lblSex = CUtilities.AddLabel( this, "Sex:", 325, 20);
				
				// m_lblMostValuablePlayer
				m_lblMostValuablePlayer = CUtilities.AddLabel( this, "Most Valuable Player:", 355, 20);
				
				// m_lblEmailAddress
				m_lblEmailAddress = CUtilities.AddLabel( this, "Email Address:", 385, 20);
				
				// m_lblRequiredField
				m_lblRequiredField = CUtilities.AddRequiredFieldLabel( this, 405, 160 );
				
				// m_lblPhoneFormatting
				m_lblPhoneFormatting = CUtilities.AddColoredSizedLabel( this, 250, 25,
																		"###-#### or ###-###-####",
																		"999999",
																		sngFontSize );
				// m_lblDateOfBirthFormatting
				m_lblDateOfBirthFormatting = CUtilities.AddColoredSizedLabel( this, 310, 25,
																		"yyyy-mm-dd",
																		"999999",
																		sngFontSize );
				
			// Text Boxes
				
				// m_txtFirstName
				m_txtFirstName = CUtilities.AddTextBox( this, 20, 160, 25, 185, 50);
				
				// m_txtMiddleName
				m_txtMiddleName = CUtilities.AddTextBox( this, 50, 160, 25, 185, 50);
				
				// m_txtLastName
				m_txtLastName = CUtilities.AddTextBox( this, 80, 160, 25, 185, 50);
				
				// m_txtStreetAddress
				m_txtStreetAddress = CUtilities.AddTextBox( this, 110, 160, 25, 185, 50);
				
				// m_txtCity
				m_txtCity = CUtilities.AddTextBox( this, 140, 160, 25, 185, 50);
				
				// m_txtZipCode
				m_txtZipCode = CUtilities.AddTextBox( this, 200, 160, 25, 185, 50);
				
				// m_txtPhoneNumber
				m_txtPhoneNumber = CUtilities.AddTextBox( this, 230, 160, 25, 185, 50);
				
				// m_txtSalary
				m_txtSalary = CUtilities.AddTextBox( this, 260, 160, 25, 185, 50);
				
				// m_txtDateOfBirth
				m_txtDateOfBirth = CUtilities.AddTextBox( this, 290, 160, 25, 185, 50);
				
				// m_txtEmailAddress
				m_txtEmailAddress = CUtilities.AddTextBox( this, 380, 160, 25, 185, 50);
				
			// Combo Box
				
				// m_cboState
				m_cboStates = CUtilities.AddComboBox( this, 170, 160, 25, 185);
				
			// Buttons	
				
				// m_btnOK
				m_btnOK = CUtilities.AddButton( this, this,
												"OK", 435, 70, 30, 100 );
				// m_btnCancel
				m_btnCancel = CUtilities.AddButton( this, this,
												"Cancel", 435, 200, 30, 100 );
				
			// Radio Buttons
				
				// ButtonGroup bgpSex
				ButtonGroup bpgSex = new ButtonGroup( );
			
				// m_radFemale
				m_radFemale = CUtilities.AddRadioButton( this, this, bpgSex, "Female", 320, 160, true );
				
				// m_radMale
				m_radMale = CUtilities.AddRadioButton( this, this, bpgSex, "Male", 320, 230 );
				
			// Check Box
				
				// m_chkMostValuablePlayer
				m_chkMostValuablePlayer = CUtilities.AddCheckBox( this, this, "", 350, 160, false );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}

	}
	
	
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// WindowListener
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	
	// ----------------------------------------------------------------------
	// Name: windowOpened
	// Abstract: Open a connection to the database.
	//			 Open the connection in this event because the form is visible
	//			 at this point. It's not visible in the constructor. 
	//			 We need the form visible so that the user can see the form and
	//			 the busy cursor while we are connecting so they know something
	//			 is happening. 
	//
	//			 If the database is unavailable or slow to process the request
	//			 it may appear to the user that after they double clicked on the 
	//			 shortcut or clicked on the menu item that nothing happened.
	//			 Since the connection may take 10+ seconds to timeout the user
	// 			 would probably try to start/run the application again unless
	//			 they see the form.
	// ----------------------------------------------------------------------
	public void windowOpened( WindowEvent weSource )
	{
		try
		{
			udtPlayerType udtPlayer = new CUserDataTypes( ).new udtPlayerType( );
			boolean blnResult = false;
			
			// Which team to load?
			udtPlayer.intPlayerID = m_intPlayerToEditID;
			
			// We are busy
			CUtilities.SetBusyCursor( this, true );
			
			// Load the States list to the combo box
			blnResult = CDatabaseUtilities.LoadComboBoxFromDatabase( "TStates",
																	"intStateID",
																	"strState",
																	m_cboStates );
			// Did it work?
			if( blnResult == true )
			{
				// Yes, get player information
				blnResult = CDatabaseUtilities.GetPlayerInformationFromDatabase( udtPlayer );
			
				// Did it work?
				if( blnResult == true )
				{
					// Yes, load form with values
					PutValuesOnForm( udtPlayer );
				}
				else
				{
					// No, warn the user and ...
					CMessageBox.Show( this,   "Unable to load player information.\n"
											+ "The form will now close.",
											  "Edit Player Error",
											  enuIconType.Error );
					// close
					setVisible( false );
				}
			}
			else
			{
				// No, warn the user and ...
				CMessageBox.Show( this,   "Unable to load states list.\n"
										+ "The form will now close.",
										  "Edit Player Error",
										  enuIconType.Error );
				// close
				setVisible( false );
			}
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		finally
		{
			// We are NOT busy
			CUtilities.SetBusyCursor( this, false );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: PutValuesOnForm
	// Abstract: Load the form controls with values for record from database.
	// ----------------------------------------------------------------------
	public void PutValuesOnForm( udtPlayerType udtPlayer )
	{
		try
		{
			String strSalary = "";
			String strDateOfBirth = "";

			m_txtFirstName.setText( 				udtPlayer.strFirstName );
			m_txtMiddleName.setText( 				udtPlayer.strMiddleName );
			m_txtLastName.setText(					udtPlayer.strLastName );
			m_txtStreetAddress.setText( 			udtPlayer.strStreetAddress );
			m_txtCity.setText( 						udtPlayer.strCity );
			m_cboStates.SetSelectedIndexByValue( 	udtPlayer.intStateID );
			m_txtZipCode.setText( 					udtPlayer.strZipCode );
			m_txtPhoneNumber.setText( 				udtPlayer.strPhoneNumber );
			
			// Salary
			strSalary = NumberFormat.getCurrencyInstance().format( udtPlayer.bdecSalary );
			m_txtSalary.setText( 				strSalary );
			
			// Date of Birth
			strDateOfBirth = CUtilities.ConvertSQLDateToString( udtPlayer.sdtmDateOfBirth );
			
				// Set only if not "zeroth" date
				if( strDateOfBirth.equals( "1800/01/01" ) == false )
				{
					m_txtDateOfBirth.setText( strDateOfBirth );
				}
				
			// Sex
			if( udtPlayer.intSexID == CConstants.intSEX_FEMALE ) m_radFemale.setSelected( true );
			if( udtPlayer.intSexID == CConstants.intSEX_MALE )   m_radMale.setSelected( true );

			// Most Valuable Player
			if( udtPlayer.blnMostValuablePlayer == true ) m_chkMostValuablePlayer.setSelected( true );
			
			// Email Address
			m_txtEmailAddress.setText( 			udtPlayer.strEmailAddress );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}

	
	// Don't care
	public void windowClosed( WindowEvent weSource ) { }
	public void windowClosing( WindowEvent weSource ) { }
    public void windowIconified( WindowEvent weSource ) { }
    public void windowDeiconified( WindowEvent weSource ) { }
    public void windowActivated( WindowEvent weSource ) { }
    public void windowDeactivated( WindowEvent weSource ) { }


	// ----------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Event handler for control click events
	// ----------------------------------------------------------------------
	@Override
	public void actionPerformed( ActionEvent aeSource )
	{
		try
		{
				 if( aeSource.getSource( ) == m_btnOK )		btnOK_Click( );
			else if( aeSource.getSource( ) == m_btnCancel )	btnCancel_Click( );				 
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}

	

	// ----------------------------------------------------------------------
	// Name: btnOK_Click
	// Abstract: Edit the team to the database
	// ----------------------------------------------------------------------
	private void btnOK_Click( )
	{
		try
		{
			// Is the form data good?
			if( IsValidData( ) == true )
			{
				// Did it save to the database?
				if( SaveData( ) == true )
				{
					// Yes, success
					m_blnResult = true;
					
					// All done
					setVisible( false );
				}
			}
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: IsValidData
	// Abstract: Check all the data and warn the user if it's bad
	// ----------------------------------------------------------------------
	private boolean IsValidData( )
	{
		// Assume data is good. Easier to code that way
		boolean blnIsValidData = true;
		
		try
		{
			String strErrorMessage = "Please correct the following error(s):\n";
			
			// Trim text boxes
			CUtilities.TrimAllFormTextBoxes( this );
			
			// First Name
			if( m_txtFirstName.getText( ).equals( "" ) == true )
			{
				strErrorMessage += "-First Name cannot be blank\n";
				blnIsValidData = false;
			}
			
			// Last Name
			if( m_txtLastName.getText( ).equals( "" ) == true )
			{
				strErrorMessage += "-Last Name cannot be blank\n";
				blnIsValidData = false;
			}
			
			// Zip Code blank?
			if( m_txtZipCode.getText( ).equals( "" ) == false )
			{
				// No, is it a valid format?
				if( CUtilities.IsValidZipCode( m_txtZipCode.getText( ) ) == false )
				{
					strErrorMessage += "-Zip Code is invalid.\n";
					blnIsValidData = false;
				}
			}
			
			// Phone Number blank?
			if( m_txtPhoneNumber.getText( ).equals( "" ) == false )
			{
				// No, is it a valid format?
				if( CUtilities.IsValidPhoneNumber( m_txtPhoneNumber.getText( ) ) == false )
				{
					strErrorMessage += "-Phone Number is invalid\n";
					blnIsValidData = false;
				}
			}
			
			// Salary blank?
			if( m_txtSalary.getText( ).equals( "" ) == false )
			{
				// No, is it a valid format?
				if( CUtilities.IsCurrency( m_txtSalary.getText( ) ) == false )
				{
					strErrorMessage += "-Salary is invalid\n";
					blnIsValidData = false;
				}
			}
			
			// Date of Birth blank?
			if( m_txtDateOfBirth.getText( ).equals( "" ) == false )
			{
				// No, is it a valid format?
				if( CUtilities.IsValidDate( m_txtDateOfBirth.getText( ) ) == false )
				{
					strErrorMessage += "-Date of Birth is invalid\n";
					blnIsValidData = false;
				}
			}
			
			// EmailAddress blank?
			if( m_txtEmailAddress.getText( ).equals( "" ) == false )
			{
				// No, is it a valid format?
				if( CUtilities.IsValidEmailAddress( m_txtEmailAddress.getText( ) ) == false )
				{
					strErrorMessage += "-Email Address is invalid\n";
					blnIsValidData = false;
				}
			}
			
			// Bad data?
			if( blnIsValidData == false )
			{
				// Yes, warn user
				CMessageBox.Show( this, strErrorMessage,
								  getTitle( ) + " Error", enuIconType.Warning );
			}
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return blnIsValidData;
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: SaveData
	// Abstract: Get the data off of the form and save it to the database
	// ----------------------------------------------------------------------
	private boolean SaveData( )
	{
		boolean blnResult = false;
		
		try
		{
			// Make a suitcase for moving data
			udtPlayerType udtNewPlayer = null;
			
			// Get the values from the form
			udtNewPlayer = GetValuesFromForm( );
			
			// We are busy
			CUtilities.SetBusyCursor( this, true );
			
			// Try to save the data
			blnResult = CDatabaseUtilities.EditPlayerInDatabase2( udtNewPlayer );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		finally
		{
			// We are NOT busy
			CUtilities.SetBusyCursor( this, false );
		}
		
		return blnResult;
	}
	
	
	// ----------------------------------------------------------------------
	// Name: GetValuesFromForm
	// Abstract: Load a structure suitcase with all the values from the form.
	// ----------------------------------------------------------------------
	private udtPlayerType GetValuesFromForm( )
	{
		udtPlayerType udtPlayer = null;
		
		try
		{
			// Make a suitcase instance
			udtPlayer = new CUserDataTypes( ).new udtPlayerType( );
			
			// Load suitcase with values from form
			udtPlayer.intPlayerID 			= m_intPlayerToEditID;
			udtPlayer.strFirstName 			= m_txtFirstName.getText( );
			udtPlayer.strMiddleName 		= m_txtMiddleName.getText( );
			udtPlayer.strLastName 			= m_txtLastName.getText( );
			udtPlayer.strStreetAddress 		= m_txtStreetAddress.getText( );
			udtPlayer.strCity 				= m_txtCity.getText( );
			udtPlayer.intStateID 			= m_cboStates.GetSelectedItemValue( );
			udtPlayer.strZipCode			= m_txtZipCode.getText( );
			udtPlayer.strPhoneNumber 		= m_txtPhoneNumber.getText( );
			udtPlayer.bdecSalary			= CUtilities.ConvertStringToBigDecimal( m_txtSalary.getText( ) );

			// Date of Birth
			if( m_txtDateOfBirth.getText().equals( "" ) == false )
			{
				udtPlayer.sdtmDateOfBirth = CUtilities.ConvertStringToSQLDate( m_txtDateOfBirth.getText( ) );
			}
			else
			{
				// Can't have null so substitute a "zeroth" date
				udtPlayer.sdtmDateOfBirth = CUtilities.ConvertStringToSQLDate( "1800/01/01" );	
			}

			// Sex
			if( m_radFemale.isSelected( ) == true ) udtPlayer.intSexID = CConstants.intSEX_FEMALE;
			if( m_radMale.isSelected( )   == true ) udtPlayer.intSexID = CConstants.intSEX_MALE;
			
			// Most Valuable Player
			if( m_chkMostValuablePlayer.isSelected( ) == true ) udtPlayer.blnMostValuablePlayer = true;
			
			// Email Address
			udtPlayer.strEmailAddress 		= m_txtEmailAddress.getText( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return udtPlayer;
	}

	
	
	// ----------------------------------------------------------------------
	// Name: btnCancel_Click
	// Abstract: Closes the Edit form without Editing anything
	// ----------------------------------------------------------------------
	private void btnCancel_Click( )
	{
		try
		{
			// hides the form
			setVisible( false );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: GetResult
	// Abstract: Get the result flag indicating if the edit was successful
	// ----------------------------------------------------------------------
	public boolean GetResult( )
	{
		try
		{
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return m_blnResult;
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: GetNewPlayerInformation
	// Abstract: Get the new information from the form.
	// ----------------------------------------------------------------------
	public udtPlayerType GetNewPlayerInformation( )
	{
		udtPlayerType udtPlayer = null;

		try
		{		
			udtPlayer = GetValuesFromForm( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return udtPlayer;
	}
	
}