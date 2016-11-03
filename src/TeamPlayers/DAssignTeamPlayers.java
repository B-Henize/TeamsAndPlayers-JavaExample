// ----------------------------------------------------------------------
// Name: Package
// Abstract: Package location
// ----------------------------------------------------------------------
package TeamPlayers;

//----------------------------------------------------------------------
//Name: DManagePlayers
//Abstract: a basic windows modeless form
//----------------------------------------------------------------------


//----------------------------------------------------------------------
//Imports
//----------------------------------------------------------------------
import java.awt.*;			// Basic windows functionality
import java.awt.event.*;	// Event processing\
import javax.swing.*;		// Supplemental windows functionality

import Utilities.*;			// Import All Utilities for Controls/Events
import Utilities.CMessageBox.enuIconType;
import Utilities.CUserDataTypes.udtPlayerType;
import Utilities.CUserDataTypes.udtTeamType;

@SuppressWarnings({ "serial", "unused" })
public class DAssignTeamPlayers extends JDialog implements ActionListener,
											 		   	   WindowListener,
											 		   	   MouseListener,
											 		   	   ItemListener
{
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Controls( never make public )
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Labels
	private JLabel m_lblPlayers = null;
	private JLabel m_lblTeam = null;
	private JLabel m_lblSelectedPlayers = null;
	private JLabel m_lblAvailablePlayers = null;
	
	// List Boxes
	private CListBox m_lstSelectedPlayers = null;
	private CListBox m_lstAvailablePlayers = null;
	
	// Combo Boxes
	private CComboBox m_cboTeam = null;
	
	// Group Box (titled panel)
	private JPanel m_pnlPlayers = null;
	
	// Buttons 
	private JButton m_btnAll = null;
	private JButton m_btnAdd = null;
	private JButton m_btnRemove = null;
	private JButton m_btnNone = null;
	private JButton m_btnClose = null;
	
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
	// Name: DAssignTeamPlayers
	// Abstract: the default constructor
	// ----------------------------------------------------------------------
	public DAssignTeamPlayers( JFrame frmParent )
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
			int intWidth = 635;
			int intHeight = 420;
			
			// Listen for window events
			this.addWindowListener( this );
			// Title
			setTitle( "Assign Team Players" );
				
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
			
			// Height: 420, Width: 635
			// Team
			m_lblTeam = CUtilities.AddLabel( this, "Team:", 19, 20 );
			m_cboTeam = CUtilities.AddComboBox( this, this, 18, 60, 20, 200 );
			
			// Group Box (titled panel)
			m_pnlPlayers = CUtilities.AddPanel( this, 50, 20, 275, 590, "Players" );
			
			// Selected Players
			m_lblSelectedPlayers = CUtilities.AddLabel( m_pnlPlayers, "Selected:", 25, 25 );
			m_lstSelectedPlayers = CUtilities.AddListBox( m_pnlPlayers, this, 42, 25, 205, 200 );
			
			// m_btnAll
			m_btnAll = CUtilities.AddButton( m_pnlPlayers, this, "<< All", 'A', 65, 245, 25, 100 );
			
			// m_btnAdd
			m_btnAdd = CUtilities.AddButton( m_pnlPlayers, this, "< Add", 'D', 110, 245, 25, 100 );
			
			// m_btnRemove
			m_btnRemove = CUtilities.AddButton( m_pnlPlayers, this, "Remove >", 'R', 155, 245, 25, 100 );
			
			// m_btnNone
			m_btnNone = CUtilities.AddButton( m_pnlPlayers, this, "None >>", 200, 245, 25, 100 );
			
			// Available Players
			m_lblAvailablePlayers = CUtilities.AddLabel( m_pnlPlayers, "Available:", 25, 365 );
			m_lstAvailablePlayers = CUtilities.AddListBox( m_pnlPlayers, this, 42, 365, 205, 200 );
			
			// m_btnClose
			m_btnClose = CUtilities.AddButton( this, this, "Close", 'C', 345, 217, 30, 200 );				
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
			
			// We are busy
			CUtilities.SetBusyCursor( this, true );
			
			// Load the team combo box. 
			blnResult = CDatabaseUtilities.LoadComboBoxFromDatabase( "TTeams",
																	 "intTeamID",
																	 "strTeam",
																	 m_cboTeam );
			// Did it work?
			if( blnResult == false )
			{
				// No, warn the user ...
				CMessageBox.Show( this,  "Unable to load the Team list.\n" +
										 "The form will close.\n",
										 getTitle( ) + " Error",
										 enuIconType.Error );
				// ... And close the form/Application
				this.dispose( );
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
	
	
	
	// Don't Care
	public void windowClosing( WindowEvent weSource ){ }
	public void windowClosed( WindowEvent weSource ){ }
	public void windowActivated( WindowEvent weSource ) { }
	public void windowDeactivated( WindowEvent weSource ) { }
	public void windowIconified( WindowEvent weSource ) { }
	public void windowDeiconified( WindowEvent weSource ) { }

	
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// ItemListener
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	
	// ----------------------------------------------------------------------
	// Name: itemStateChanged
	// Abstract: Selected item index in combo box changed.
	// ----------------------------------------------------------------------
	public void itemStateChanged( ItemEvent ieSource )
	{
		try
		{
			// cboTeam?
			if( ieSource.getSource( ) == m_cboTeam )
			{
				// Selected event?
				if( ieSource.getStateChange( ) == ItemEvent.SELECTED )
				{
					LoadTeamPlayers( );
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
	// ----------------------------------------------------------------------
	// MouseListener
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------

	// ----------------------------------------------------------------------
	// Name: mouseClicked
	// Abstract: Handle mouseListener events
	// ----------------------------------------------------------------------
	public void mouseClicked( MouseEvent meSource )
	{
		try
		{		
			// Double click?
			if( meSource.getClickCount( ) == 2 )
			{
				// Yes
				     if( meSource.getSource( ) == m_lstAvailablePlayers ) btnAdd_Click( );
				else if( meSource.getSource( ) == m_lstSelectedPlayers )  btnRemove_Click( );
			}
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	// Don't care
	public void mousePressed( MouseEvent meSource ) { }
	public void mouseReleased( MouseEvent meSource ) { }
	public void mouseEntered( MouseEvent meSource ) { }
	public void mouseExited( MouseEvent meSource ) { }
	
	
	// ----------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Event handler for control click events
	// ----------------------------------------------------------------------
	@Override
	public void actionPerformed( ActionEvent aeSource )
	{
		try
		{
			     if( aeSource.getSource( ) == m_btnAll )			btnAll_Click( );
			else if( aeSource.getSource( ) == m_btnAdd ) 			btnAdd_Click( );
			else if( aeSource.getSource( ) == m_btnRemove ) 		btnRemove_Click( );
			else if( aeSource.getSource( ) == m_btnNone )			btnNone_Click( );
			else if( aeSource.getSource( ) == m_btnClose ) 			btnClose_Click( );					 
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}

	
	
	// ----------------------------------------------------------------------
	// Name: btnAll_Click
	// Abstract: Add all players to the team
	// ----------------------------------------------------------------------
	private void btnAll_Click( )
	{
		try
		{
            int intTeamID = 0;
            int intIndex = 0;
            CListItem liCurrentPlayer = null;

            // We are busy
			CUtilities.SetBusyCursor( this, true );

            // Get Team ID
            intTeamID = m_cboTeam.GetSelectedItemValue( );

            // Add all the players
            if( CDatabaseUtilities.AddAllPlayersToTeamInDatabase2( intTeamID ) == true )
			{
            	// Move all the players from available to selected
            	for( intIndex = 0; intIndex < m_lstAvailablePlayers.Length( ); intIndex += 1 )
            	{
            		// Get next player
            		liCurrentPlayer = m_lstAvailablePlayers.GetItem( intIndex );
            	
            		// Copy
            		m_lstSelectedPlayers.AddItemToList( liCurrentPlayer );
            	}
            	
            	// Clear available
            	m_lstAvailablePlayers.Clear( );

            	// Select first item in list
            	m_lstSelectedPlayers.SetSelectedIndex( 0 );
            }

            // Enable/disable  add/remove buttons
            EnableButtons( );
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
	// Name: btnAdd_Click
	// Abstract: Add a team
	// ----------------------------------------------------------------------
	private void btnAdd_Click( )
	{
		try
		{
			int intTeamID = 0;
			int intPlayerID = 0;
			boolean blnResult = false;
			
			// We are busy
			CUtilities.SetBusyCursor( this, true );
			
			// Get Team and Player IDs
			intTeamID = m_cboTeam.GetSelectedItemValue( );
			intPlayerID = m_lstAvailablePlayers.GetSelectedItemValue( );
			
			// Add the player to the team
			blnResult = CDatabaseUtilities.AddPlayerToTeamInDatabase2( intTeamID, intPlayerID );
			
			// Did it work?
			if( blnResult == true )
			{
				// Yes, add player to selected list
				m_lstSelectedPlayers.AddItemToList( m_lstAvailablePlayers.GetSelectedItem( ) );
				
				// Remove from available list
				m_lstAvailablePlayers.RemoveAt( m_lstAvailablePlayers.GetSelectedIndex( ) );
			}
			
			// Enable/disable buttons
			EnableButtons( );
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
	// Name: btnRemove_Click
	// Abstract: Delete a record
	// ----------------------------------------------------------------------
	private void btnRemove_Click( )
	{
		try
		{
			int intTeamID = 0;
			int intPlayerID = 0;
			boolean blnResult = false;
			
			// We are busy
			CUtilities.SetBusyCursor( this, true );
			
			// Get Team and Player IDs
			intTeamID = m_cboTeam.GetSelectedItemValue( );
			intPlayerID = m_lstSelectedPlayers.GetSelectedItemValue( );
			
			// Remove the player from the team
			blnResult = CDatabaseUtilities.RemovePlayerFromTeamInDatabase2( intTeamID, intPlayerID );
			
			// Did it work?
			if( blnResult == true )
			{
				// Yes, add player to available list
				m_lstAvailablePlayers.AddItemToList( m_lstSelectedPlayers.GetSelectedItem( ) );
							
				// Remove from selected list
				m_lstSelectedPlayers.RemoveAt( m_lstSelectedPlayers.GetSelectedIndex( ) );
			}
			
			// Enable/disable buttons
			EnableButtons( );
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
	// Name: btnNone_Click
	// Abstract: Remove all players from the team
	// ----------------------------------------------------------------------
	private void btnNone_Click( )
	{
		try
		{
            int intTeamID = 0;
            int intIndex = 0;
            CListItem liCurrentPlayer = null;

            // We are busy
			CUtilities.SetBusyCursor( this, true );

            // Get Team ID
            intTeamID = m_cboTeam.GetSelectedItemValue( );

            // Add all the players
            if( CDatabaseUtilities.RemoveAllPlayersFromTeamInDatabase2( intTeamID ) == true )
			{
            	// Move all the players from selected to selected
            	for( intIndex = 0; intIndex < m_lstSelectedPlayers.Length( ); intIndex += 1 )
            	{
            		// Get next player
            		liCurrentPlayer = m_lstSelectedPlayers.GetItem( intIndex );
            	
            		// Copy
            		m_lstAvailablePlayers.AddItemToList( liCurrentPlayer );
            	}
            	
            	// Clear available
            	m_lstSelectedPlayers.Clear( );
            	
            	// Select first item in list
            	m_lstAvailablePlayers.SetSelectedIndex( 0 );
            }

            // Enable/disable  add/remove buttons
            EnableButtons( );
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
	// Name: LoadTeamPlayers
	// Abstract: The selected index for the team combo box changed
	// ----------------------------------------------------------------------
	private void LoadTeamPlayers( )
	{
		try
		{
			int intTeamID = 0;
			
			// Get the selected team ID
			intTeamID = m_cboTeam.GetSelectedItemValue( );
			
			// Is a team selected (list could be empty)?
			if( intTeamID > 0 )
			{
				// Yes, we are busy
				CUtilities.SetBusyCursor( this, true );
				
				// Selected Players
				CDatabaseUtilities.LoadListWithPlayersFromDatabase( intTeamID,
																m_lstSelectedPlayers, 
																true );
				// Available Players
				CDatabaseUtilities.LoadListWithPlayersFromDatabase( intTeamID,
																m_lstAvailablePlayers, 
																false );
				// Enable/disable buttons
				EnableButtons( );
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
	// Name: EnableButtons
	// Abstract: Enable/disable the add/remove buttons.
	// ----------------------------------------------------------------------
	private void EnableButtons( )
	{
		try
		{
			// All
			if( m_lstAvailablePlayers.Length( ) > 0 ) m_btnAll.setEnabled( true );
			else									  m_btnAll.setEnabled( false );
			
			// Add
			if( m_lstAvailablePlayers.Length( ) > 0 ) m_btnAdd.setEnabled( true );
			else									  m_btnAdd.setEnabled( false );
			
			// Remove
			if( m_lstSelectedPlayers.Length( ) > 0 )  m_btnRemove.setEnabled( true );
			else									  m_btnRemove.setEnabled( false );
			
			// None
			if( m_lstSelectedPlayers.Length( ) > 0 )  m_btnNone.setEnabled( true );
			else									  m_btnNone.setEnabled( false );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
}