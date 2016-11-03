//----------------------------------------------------------------------
// Name:	Brandin Henize
// Class:	Java 1
// Abstract:	Homework 18 - Switch to SQL Server
// ----------------------------------------------------------------------

// ----------------------------------------------------------------------
// Name: FMain
// Abstract: a basic windows modeless form
// ----------------------------------------------------------------------


// ----------------------------------------------------------------------
// Imports
// ----------------------------------------------------------------------
import java.awt.*;			// Basic windows functionality
import java.awt.event.*;	// Event processing
import javax.swing.*;		// Supplemental windows functionality

import Players.DManagePlayers;
import TeamPlayers.DAssignTeamPlayers;
import Teams.DManageTeams;
import Utilities.*;			// Import All Utilities for Controls/Events
import Utilities.CMessageBox.enuIconType;
import Utilities.CUserDataTypes.udtTeamType;

@SuppressWarnings({ "serial", "unused" })
public class FMain extends JFrame implements ActionListener,
											 WindowListener
{
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Controls( never make public )
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Main Menu
	private JMenuBar m_mbMainMenu = null;
	// File
	private JMenu m_mnuFile = null;
		private JMenuItem m_mniFileExit = null;
	// Manage
	private JMenu m_mnuManage = null;
		private JMenuItem m_mniManageTeams = null;
		private JMenuItem m_mniAssignTeamPlayers = null;
		private JMenuItem m_mniManagePlayers = null;
	// Help
	private JMenu m_mnuHelp = null;
		private JMenuItem m_mniHelpAbout = null;
	
	// Group Box (titled panel)
	private JPanel m_pnlManage = null;
	
	// Buttons
	private JButton m_btnManageTeams = null;
	private JButton m_btnAssignTeamPlayers = null;
	private JButton m_btnManagePlayers = null;
	
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
	// Name: main
	// Abstract: This is where the program starts
	// ----------------------------------------------------------------------
	public static void main( String astrCommandLine[] )
	{
		try
		{
			FMain frmMain = new FMain( );
			
			frmMain.setVisible( true );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: FMain
	// Abstract: the default constructor
	// ----------------------------------------------------------------------
	public FMain( )
	{
		try
		{
			// Initialize the frame
			Initialize( );
			
			// Build the menu
			BuildMenu( );
			
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
			int intWidth = 380;
			int intHeight = 330;
			
			// Listen for window events
			this.addWindowListener( this );
			
			// Title
			setTitle( "Homework 18 - Switch to SQL Server" );
				
			// Size
			setSize( intWidth, intHeight );
			
			// Center screen
			CUtilities.CenterScreen( this );
			
			// No resize
			setResizable( false );
				
			// Exit application close (instead of just hiding/visible = false)
			setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
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
	// Name: BuildMenu
	// Abstract: Build the menu for this frame.
	// ----------------------------------------------------------------------
	private void BuildMenu( )
	{

		try
		{
			// Main menu
			m_mbMainMenu = CUtilities.AddMenuBar( this );
			
			// File
			m_mnuFile = CUtilities.AddMenu( m_mbMainMenu , "File", 'F' );
				
				// Exit
				m_mniFileExit = CUtilities.AddMenuItem( m_mnuFile, this, "Exit", 'X', 'X' );
			
			// Tools
			m_mnuManage = CUtilities.AddMenu( m_mbMainMenu, "Tools", 'M' );
			
				// Manage Teams
				m_mniManageTeams = CUtilities.AddMenuItem( m_mnuManage,  this,  "Manage Teams", 'T' );
				
				// Assign Team Players
				m_mniAssignTeamPlayers = CUtilities.AddMenuItem( m_mnuManage, this, "Assign Team Players" );
				
				// Manage Players
				m_mniManagePlayers = CUtilities.AddMenuItem( m_mnuManage,  this,  "Manage Players", 'P' );
				
			// Help
			m_mnuHelp = CUtilities.AddMenu( m_mbMainMenu, "Help", 'H' );
			
				// About
				m_mniHelpAbout = CUtilities.AddMenuItem( m_mnuHelp, this, "About", 'A' );
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
			
			// m_btnManageTeams
			m_btnManageTeams = CUtilities.AddButton( this, this,
											"Manage Teams", 65, 80, 35, 220 );
			
			// m_btnAssignTeamPlayers 
			m_btnAssignTeamPlayers = CUtilities.AddButton( this, this,
											"Assign Team Players", 125, 80, 35, 220 );
			
			// m_btnManagePlayers
			m_btnManagePlayers = CUtilities.AddButton( this, this,
											"Manage Players", 185, 80, 35, 220 );
			
			// m_lblManage
			m_pnlManage = CUtilities.AddPanel( this,
											35, 40,
											210, 300, "Manage" );


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
	// Abstract: The window is opened. Triggered by setVisible( true ).
	// ----------------------------------------------------------------------
	public void windowOpened( WindowEvent weSource )
	{
		try
		{
			// We are busy
			CUtilities.SetBusyCursor( this, true );
			
			// Can we connect to the database?
			if( CDatabaseUtilities.OpenDatabaseConnectionSQLServer( ) == false )
			{
				// No, warn the user...
				CMessageBox.Show( this, "Database connection error\n" +
								  		"The application will close.\n",
								  		getTitle( ) + " Error",
								  		enuIconType.Error );
				
				// and close the form/application
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
	
	
	
	// ----------------------------------------------------------------------
	// Name: windowClosed
	// Abstract: Close the connection to the database
	//			 Triggered when this.dispose( ) but NOT by clicking X button
	//			 in the Window Title Bar
	// ----------------------------------------------------------------------
	public void windowClosed( WindowEvent weSource )
	{
		try
		{
			CleanUp( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: windowClosing
	// Abstract: Close the connection to the database
	//			 Triggered by clicking X button in the Window Title Bar
	//			 but NOT by this.dispose( )
	// ----------------------------------------------------------------------
	public void windowClosing( WindowEvent weSource )
	{
		try
		{
			CleanUp( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: CleanUp
	// Abstract: Close the connection to the database
	// ----------------------------------------------------------------------
	public void CleanUp( )
	{
		try
		{
			CDatabaseUtilities.CloseDatabaseConnection( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// Don't Care
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
			     if( aeSource.getSource( ) == m_btnManageTeams )		btnManageTeams_Click( );
			else if( aeSource.getSource( ) == m_btnAssignTeamPlayers )	btnAssignTeamPlayers_Click( );
			else if( aeSource.getSource( ) == m_btnManagePlayers ) 		btnManagePlayers_Click( );
			else if( aeSource.getSource( ) == m_mniFileExit )			mniFileExit_Click( ); 
			else if( aeSource.getSource( ) == m_mniManageTeams )		mniManageTeams_Click( );
			else if( aeSource.getSource( ) == m_mniAssignTeamPlayers )	mniAssignTeamPlayers_Click( );
			else if( aeSource.getSource( ) == m_mniManagePlayers )		mniManagePlayers_Click( );
			else if( aeSource.getSource( ) == m_mniHelpAbout )			mniHelpAbout_Click( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}

	
	
	// ----------------------------------------------------------------------
	// Name: mniManageTeams_Click
	// Abstract: Menu item to open form to add, edit, and delete teams. 
	// ----------------------------------------------------------------------
	private void mniManageTeams_Click( )
	{
		try
		{
			ManageTeams( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: btnManageTeams_Click
	// Abstract: Button to open form to add, edit, and delete teams.
	// ----------------------------------------------------------------------
	private void btnManageTeams_Click( )
	{
		try
		{
			ManageTeams( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: ManageTeams
	// Abstract: This is the actual form to add, edit, and delete teams.
	// ----------------------------------------------------------------------
	private void ManageTeams( )
	{
		try
		{
			DManageTeams dlgManageTeams = null;
			
			// Make instance
			dlgManageTeams = new DManageTeams( this );
			
			// Show modally
			dlgManageTeams.setVisible( true );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: mniAssignTeamPlayers
	// Abstract: Menu item to Assign Team Players
	// ----------------------------------------------------------------------
	private void mniAssignTeamPlayers_Click( )
	{
		try
		{
			AssignTeamPlayers( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: btnAssignTeamPlayers_Click
	// Abstract: Button to open form to add and remove players from teams.
	// ----------------------------------------------------------------------
	private void btnAssignTeamPlayers_Click( )
	{
		try
		{
			AssignTeamPlayers( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: AssignTeamPlayers
	// Abstract: This is the actual form to add, edit, and delete teams.
	// ----------------------------------------------------------------------
	private void AssignTeamPlayers( )
	{
		try
		{
			DAssignTeamPlayers dlgAssignTeamPlayers = null;
			
			// Make instance
			dlgAssignTeamPlayers = new DAssignTeamPlayers( this );
			
			// Show modally
			dlgAssignTeamPlayers.setVisible( true );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: mniManagePlayers_Click
	// Abstract: Menu item that opens form to add, edit, and delete teams.
	// ----------------------------------------------------------------------
	private void mniManagePlayers_Click( )
	{
		try
		{
			ManagePlayers( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: btnManagePlayers_Click
	// Abstract: Button that opens form to add, edit, and delete players.
	// ----------------------------------------------------------------------
	private void btnManagePlayers_Click( )
	{
		try
		{
			ManagePlayers( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: ManagePlayers
	// Abstract: Actual form to add, edit, and delete players.
	// ----------------------------------------------------------------------
	private void ManagePlayers( )
	{
		try
		{
			DManagePlayers dlgManagePlayers = null;
			
			// Make instance
			dlgManagePlayers = new DManagePlayers( this );
			
			// Show modally
			dlgManagePlayers.setVisible( true );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: mniFileExit_Click
	// Abstract: Menu file, Exits program.
	// ----------------------------------------------------------------------
	private void mniFileExit_Click( )
	{
		try
		{
			this.dispose( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// ----------------------------------------------------------------------
	// Name: mniHelpAbout_Click
	// Abstract: About the program
	// ----------------------------------------------------------------------
	private void mniHelpAbout_Click( )
	{
		try
		{
			CMessageBox.Show( this,
							  "My first Java database application\n" +
							  "by Brandin Henize\n\n" +
							  "Example of Java database programming.\n\n",
							  "About",
							  enuIconType.Information ); 
		
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
}