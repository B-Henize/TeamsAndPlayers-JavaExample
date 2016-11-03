// ----------------------------------------------------------------------
// Name: Package
// Abstract: Package location
// ----------------------------------------------------------------------
package Teams;

// ----------------------------------------------------------------------
// Name: DManageTeams
// Abstract: a basic windows modeless form
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
public class DManageTeams extends JDialog implements ActionListener,
											 		 WindowListener
{
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Controls( never make public )
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Labels
	private JLabel m_lblTeams = null;
	
	// List Boxes
	private CListBox m_lstTeams = null; 
	
	// Buttons
	private JButton m_btnAdd = null;
	private JButton m_btnEdit = null;
	private JButton m_btnDelete = null;
	private JButton m_btnUndelete = null;
	private JButton m_btnClose = null;
	
	// Check Boxes
	private JCheckBox m_chkShowDeleted = null;

	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Properties( never make public )
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Never make public properties.  
	// Make protected or private with public get/set methods

	
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Methods
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	
	// ----------------------------------------------------------------------
	// Name: DManageTeams
	// Abstract: the default constructor
	// ----------------------------------------------------------------------
	public DManageTeams( JFrame frmParent )
	{
		super( frmParent, true );	// true = modal
		
		try
		{
			// Initialize the frame
			Initialize( );
			
			// Add the controls to the frame
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
			int intWidth = 515;
			int intHeight = 390;
			
			// Listen for window events
			this.addWindowListener( this );
			// Title
			setTitle( "Manage Teams" );
				
			// Size
			setSize( intWidth, intHeight );
			
			// Center screen
			CUtilities.CenterOwner( this );
			
			// No resize
			setResizable( false );
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
			//CUtilities.DrawGridMarks( this, g );
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
			
			// m_lblTeams
			m_lblTeams = CUtilities.AddLabel( this, "Teams:", 0, 20);
			
			// m_btnAdd
			m_btnAdd = CUtilities.AddButton( this, this,
											"Add", 40, 390, 35, 100 );
			// m_btnEdit
			m_btnEdit = CUtilities.AddButton( this, this,
											"Edit", 120, 390, 35, 100 );
			// m_btnDelete
			m_btnDelete = CUtilities.AddButton( this, this,
											"Delete", 'D', 200, 390, 35, 100 );
			// m_btnUndelete
			m_btnUndelete = CUtilities.AddButton( this, this,
											"Undelete", 'U', 200, 390, 35, 100 );
			m_btnUndelete.setVisible( false );
			
			// m_btnClose
			m_btnClose = CUtilities.AddButton( this, this,
											"Close", 310, 130, 30, 200 );
			// lstTeams
			m_lstTeams = CUtilities.AddListBox( this ,  20,  20,  265, 350 );
			
			// m_chkShowDeleted
			m_chkShowDeleted = CUtilities.AddCheckBox( this, this, "Show Deleted", 288, 16 );
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
			boolean blnResult = false;
			
			// Load the teams list
			blnResult = LoadTeamsList( true );
			
			// Did it work?
			if( blnResult == false )
			{
				// No, warn the user ...
				CMessageBox.Show( this,  "Unable to load the teams list.\n" +
										 "The form will close.\n",
										 getTitle( ) + " Error",
										 enuIconType.Error );
				// ... And close the form/Application
				setVisible( false );
			}
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// Don't Care
	public void windowClosing( WindowEvent weSource ){ }
	public void windowClosed( WindowEvent weSource ){ }
	public void windowActivated( WindowEvent weSource ) { }
	public void windowDeactivated( WindowEvent weSource ) { }
	public void windowIconified( WindowEvent weSource ) { }
	public void windowDeiconified( WindowEvent weSource ) { }

	
	
	// ----------------------------------------------------------------------
	// Name: LoadTeamsList
	// Abstract: Load the Teams list from the database.
	// ----------------------------------------------------------------------
	private boolean LoadTeamsList( boolean blnActive )
	{
		boolean blnResult = false;
		
		try
		{
			String strTable = "VActiveTeams";
			
			// Deleted Teams?
			if( blnActive == false )
			{
				// Yes
				strTable = "VInactiveTeams";
			}
			
			// We are busy
			CUtilities.SetBusyCursor( this, true );
			
			blnResult = CDatabaseUtilities.LoadListBoxFromDatabase( strTable,
																	"intTeamID",
																	"strTeam",
																	m_lstTeams );
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
	// Name: actionPerformed
	// Abstract: Event handler for control click events
	// ----------------------------------------------------------------------
	@Override
	public void actionPerformed( ActionEvent aeSource )
	{
		try
		{
			     if( aeSource.getSource( ) == m_btnAdd )			btnAdd_Click( );
			else if( aeSource.getSource( ) == m_btnEdit ) 			btnEdit_Click( );
			else if( aeSource.getSource( ) == m_btnDelete ) 		btnDelete_Click( );
			else if( aeSource.getSource( ) == m_btnUndelete )		btnUndelete_Click( );
			else if( aeSource.getSource( ) == m_btnClose ) 			btnClose_Click( );
			else if( aeSource.getSource( ) == m_chkShowDeleted )	chkShowDeleted_Click( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}

	
	
	// ----------------------------------------------------------------------
	// Name: btnAdd_Click
	// Abstract: Add a team
	// ----------------------------------------------------------------------
	private void btnAdd_Click( )
	{
		try
		{
			DAddTeam dlgAddTeam = null;
			udtTeamType udtNewTeam = null;
			
			// Make instance
			dlgAddTeam = new DAddTeam( this );
			
			// Show modally
			dlgAddTeam.setVisible( true );
			
			// Did it work?
			if( dlgAddTeam.GetResult( ) == true )
			{
				// Yes, get the new team information
				udtNewTeam = dlgAddTeam.GetNewTeamInformation( );
				
				// Add new record to the list box (true = select)
				m_lstTeams.AddItemToList( udtNewTeam.intTeamID,
										  udtNewTeam.strTeam, true );
			}
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: btnEdit_Click
	// Abstract: Edit a record
	// ----------------------------------------------------------------------
	private void btnEdit_Click( )
	{
		try
		{
			int intSelectedListIndex = 0;
			CListItem liSelectedTeam = null;
			int intSelectedTeamID = 0;
			DEditTeam dlgEditTeam = null;
			udtTeamType udtNewTeam = null;
			
			// Get the selected index from the list
			intSelectedListIndex = m_lstTeams.GetSelectedIndex( );
			
			// Is something selected?
			if( intSelectedListIndex < 0 )
			{
				// No, warn the user
				CMessageBox.Show( this, "You must select a Team to edit.",
										"Edit Team Error" );
			}
			else
			{
				// Yes, so get the selected list item ID and name
				liSelectedTeam = m_lstTeams.GetSelectedItem( );
				intSelectedTeamID = liSelectedTeam.GetValue( );
				
				// Make instance
				dlgEditTeam = new DEditTeam( this, intSelectedTeamID );
				
				// Display modally
				dlgEditTeam.setVisible( true );
				
				// Did it work?
				if( dlgEditTeam.GetResult( ) == true )
				{
					// Yes, get the new team information
					udtNewTeam = dlgEditTeam.GetNewTeamInformation( );
					
					// Remove old record ...
					m_lstTeams.RemoveAt( intSelectedListIndex );
					
					// and re-add so it gets sorted correctly (true = select)
					m_lstTeams.AddItemToList( udtNewTeam.intTeamID,
											  udtNewTeam.strTeam, true );
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
	// Name: btnDelete_Click
	// Abstract: Delete a record
	// ----------------------------------------------------------------------
	private void btnDelete_Click( )
	{
		try
		{
			int intSelectedListIndex = 0;
			CListItem liSelectedTeam = null;
			int intSelectedTeamID = 0;
			String strSelectedTeam = "";
			int intConfirm = 0;
			boolean blnResult = false;
			
			// Get the selected index from the list
			intSelectedListIndex = m_lstTeams.GetSelectedIndex( );
			
			// Is something selected?
			if( intSelectedListIndex < 0 )
			{
				// No, warn the user
				CMessageBox.Show( this,  "You must select a Team to delete.",
										 "Delect Team Error" );
			}
			else
			{
				// Yes, so get the selected list item ID and name
				liSelectedTeam = m_lstTeams.GetSelectedItem( );
				intSelectedTeamID = liSelectedTeam.GetValue( );
				strSelectedTeam = liSelectedTeam.GetName( );
				
				// Confirm delete
				intConfirm = CMessageBox.Confirm( this, "Are you sure?",
												  "Delete Team: " + strSelectedTeam );
				
				// Delete?
				if( intConfirm == CMessageBox.intRESULT_YES )
				{
					// Yes, we are busy
					CUtilities.SetBusyCursor( this, true );
					
					// Attempt to delete
					blnResult = CDatabaseUtilities.DeleteTeamFromTable( intSelectedTeamID );
					
					// Did it work?
					if( blnResult == true )
					{
						// Yes, remove record. Next closest record automatically selected.
						m_lstTeams.RemoveAt( intSelectedListIndex );
					}
				}
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
			CUtilities.SetBusyCursor( this,  false );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: chkShowDeleted_Click
	// Abstract: Toggle between active and inactive teams.
	// ----------------------------------------------------------------------
	private void chkShowDeleted_Click( )
	{
		try
		{
			// Show Deleted?
			if( m_chkShowDeleted.isSelected( ) == false )
			{
				// No
				LoadTeamsList( true );
				m_btnAdd.setEnabled( true );
				m_btnEdit.setEnabled( true );
				m_btnDelete.setVisible( true );
				m_btnUndelete.setVisible( false );
			}
			else
			{
				// Yes
				LoadTeamsList( false );
				m_btnAdd.setEnabled( false );
				m_btnEdit.setEnabled( false );
				m_btnDelete.setVisible( false );
				m_btnUndelete.setVisible( true );
			}
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: btnClose_Click
	// Abstract: Close the form
	// ----------------------------------------------------------------------
	private void btnClose_Click( )
	{
		try
		{
			// Close the form and exit the application
			setVisible( false );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: btnUndelete_Click
	// Abstract: Lazarus, come forth!
	// ----------------------------------------------------------------------
	private void btnUndelete_Click( )
	{
		try
		{
			int intSelectedListIndex = 0;
			CListItem liSelectedTeam = null;
			int intSelectedTeamID = 0;
			boolean blnResult = false;
			
			// Get the selected index from the list
			intSelectedListIndex = m_lstTeams.GetSelectedIndex( );
			
			// Is something selected?
			if( intSelectedListIndex < 0 )
			{
				// No, warn the user
				CMessageBox.Show( this, "You must select a Team to undelete.",
								  "Undelete Team Error",
								  enuIconType.Warning );
			}
			else
			{
				// Yes, so get the selected list item ID
				liSelectedTeam = m_lstTeams.GetSelectedItem( );
				intSelectedTeamID = liSelectedTeam.GetValue( );
				
				// We are busy
				CUtilities.SetBusyCursor( this, true );
				
				// Attempt to delete
				blnResult = CDatabaseUtilities.UndeleteTeamFromTable( intSelectedTeamID );
				
				// Did it work?
				if( blnResult == true )
				{
					// Yes, remove record. Next closest record automatically selected.
					m_lstTeams.RemoveAt( intSelectedListIndex );
				}
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
	
	
	
}