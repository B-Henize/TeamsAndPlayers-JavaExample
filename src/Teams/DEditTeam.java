// ----------------------------------------------------------------------
// Name: Package
// Abstract: Package location
// ----------------------------------------------------------------------
package Teams;

// ----------------------------------------------------------------------
// Name: DEditForm
// Abstract: a basic windows modal form
// ----------------------------------------------------------------------


// ----------------------------------------------------------------------
// Imports
// ----------------------------------------------------------------------
import java.awt.*;			// Basic windows functionality
import java.awt.event.*;	// Event processing\
import javax.swing.*;		// Supplemental windows functionality

import Utilities.*;			// Import All Utilities for Controls/Events
import Utilities.CMessageBox.enuIconType;
import Utilities.CUserDataTypes.udtTeamType;

@SuppressWarnings({ "serial", "unused" })
public class DEditTeam extends JDialog implements ActionListener,
											      WindowListener
{
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Controls( never make public )
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	private JButton m_btnOK = null;
	private JButton m_btnCancel = null;
	private CTextBox m_txtTeam = null;
	private CTextBox m_txtMascot = null;
	private JLabel m_lblTeam = null;			
	private JLabel m_lblMascot = null;
	private JLabel m_lblRequiredField = null;
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Properties( never make public )
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Never make public properties.  
	// Make protected or private with public get/set methods
	private int m_intTeamToEditID = 0;
	private boolean m_blnResult = false;
	
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Methods
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	
	// ----------------------------------------------------------------------
	// Name: DEditTeam
	// Abstract: Constructor
	// ----------------------------------------------------------------------
	public DEditTeam( JDialog dlgParent, int intTeamToEditID )
	{
		super( dlgParent, true );	// true = modal
		
		try
		{
			// Save ID for loading and saving
			m_intTeamToEditID = intTeamToEditID;
			
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
			int intWidth = 285;
			int intHeight = 180;
		
			// Title
			setTitle( "Edit Team" );
				
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
	
	
	
	// ----------------------------------------------------------------------
	// Name: AddControls
	// Abstract: Add all the controls to the frame
	// ----------------------------------------------------------------------
	public void AddControls( )
	{

		try
		{
			// Clear layout manager so we can manually size and position controls
			CUtilities.ClearLayoutManager( this );
			
			// m_lblTeam
			m_lblTeam = CUtilities.AddLabel( this, "Team:*", 25, 20);
			
			// m_lblMascot
			m_lblMascot = CUtilities.AddLabel( this, "Mascot:*", 55, 20);
			
			// m_lblRequiredField
			m_lblRequiredField = CUtilities.AddRequiredFieldLabel( this, 80, 75 );
			
			// m_txtTeam
			m_txtTeam = CUtilities.AddTextBox( this, 20, 75, 25, 185, 50);
			
			// m_txtMascot
			m_txtMascot = CUtilities.AddTextBox( this, 50, 75, 25, 185, 50);
			
			// m_btnOK
			m_btnOK = CUtilities.AddButton( this, this,
											"OK", 100, 30, 30, 100 );
			// m_btnCancel
			m_btnCancel = CUtilities.AddButton( this, this,
											"Cancel", 100, 150, 30, 100 );
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
			udtTeamType udtTeam = new CUserDataTypes( ).new udtTeamType( );
			boolean blnResult = false;
			
			// Which team to load?
			udtTeam.intTeamID = m_intTeamToEditID;
			
			// We are busy
			CUtilities.SetBusyCursor( this, true );
			
			// Get values
			blnResult = CDatabaseUtilities.GetTeamInformationFromDatabase( udtTeam );
			
			// Did it work?
			if( blnResult == true )
			{
				// Yes, load form with values
				m_txtTeam.setText( udtTeam.strTeam );
				m_txtMascot.setText( udtTeam.strMascot );
			}
			else
			{
				// No, warn the user and ...
				CMessageBox.Show( this,  "Unable to load team information.\n"
									   + "The form will now close.",
									   "Edit Team Error",
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
	
	
	
	// Don't Care
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
			
			// Team
			if( m_txtTeam.getText( ).equals( "" ) == true )
			{
				strErrorMessage += "-Team cannot be blank\n";
				blnIsValidData = false;
			}
			
			// Mascot
			if( m_txtMascot.getText( ).equals( "" ) == true )
			{
				strErrorMessage += "-Mascot cannot be blank\n";
				blnIsValidData = false;
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
			udtTeamType udtNewTeam = new CUserDataTypes( ).new udtTeamType( );
			
			// Load suitcase with data from form
			udtNewTeam.intTeamID = m_intTeamToEditID;	
			udtNewTeam.strTeam = m_txtTeam.getText( );
			udtNewTeam.strMascot = m_txtMascot.getText( );
			
			// We are busy
			CUtilities.SetBusyCursor( this, true );
			
			// Try to save the data
			blnResult = CDatabaseUtilities.EditTeamInDatabase2( udtNewTeam );
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
	// Name: btnCancel_Click
	// Abstract: Closes the Edit form without Editing anything
	// ----------------------------------------------------------------------
	private void btnCancel_Click( )
	{
		try
		{
			// hides the form
			this.setVisible( false );
			
			// closes the form without making changes.
			this.dispose( );
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
	// Name: GetNewTeamInformation
	// Abstract: Get the new information from the form.
	// ----------------------------------------------------------------------
	public udtTeamType GetNewTeamInformation( )
	{
		udtTeamType udtTeam = null;
		
		try
		{
			udtTeam = new CUserDataTypes( ).new udtTeamType( );
			
			udtTeam.intTeamID = m_intTeamToEditID;
			udtTeam.strTeam = m_txtTeam.getText( );
			udtTeam.strMascot = m_txtMascot.getText( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return udtTeam;
	}
	
	
	
}