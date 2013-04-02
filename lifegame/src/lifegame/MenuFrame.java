package lifegame;
/* -------------------------------------------------------------------------- */
/*                                                                            */
/*                            MENU EXAMPLW FRAME                              */
/*                               Frans Coenen                                 */
/*                          Tuesday 23 August 2005                            */
/*                                                                            */
/*                                                                            */
/* -------------------------------------------------------------------------- */

/* Based on example given in: Deitel and Deitel(2005), "Java Hoe to Program", 
6th (International) Edition, Pearson/Prentice-Hall. */

//import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.Color;

public class MenuFrame extends JFrame implements ActionListener {

    /* ------ FIELDS ------ */
    
    // Menues
    
    /** File menu. */
    private JMenu fileMenu;
    private JMenu editMenu;
    
    //Board view object
    private BoardView theBoardView;

    // Other components
    
    /** Menu Bar */
    public JMenuBar bar;
    
   /* --------------------------------------------------- */
    /*                                                     */
    /*                    CONSTRUCTORS                     */
    /*                                                     */
    /* --------------------------------------------------- */
    
    public MenuFrame(BoardView view) {
    	this.theBoardView = view;

	
    	// Create menus
    	createFileMenu();
    	createEditMenu();
	
    	// Create menu bar
    	bar = new JMenuBar();
    	bar.setBackground(new Color(255, 51, 153));
    	bar.add(fileMenu);
    	bar.add(editMenu);
    	
 	}
    /* CREATE EDIT MENU */
    private void createEditMenu(){
    	JMenu mnEdit = new JMenu("Edit");
    	editMenu = mnEdit;
    	editMenu.setMnemonic('F');
    	
    	JMenuItem mntmCopy = new JMenuItem("Copy");
    	mntmCopy.setMnemonic('C');
    	mntmCopy.setEnabled(true);
    	mntmCopy.addActionListener(this);
    	mnEdit.add(mntmCopy);
    	
    	JMenuItem mntmPaste = new JMenuItem("Paste");
    	mntmPaste.setMnemonic('C');
    	mntmPaste.setEnabled(true);
    	mntmPaste.addActionListener(this);
    	mnEdit.add(mntmPaste);
    	
    	JMenuItem mntmGlider = new JMenuItem("Glider");
    	mntmGlider.setMnemonic('G');
    	mntmGlider.setEnabled(true);
    	mntmGlider.addActionListener(this);
    	mnEdit.add(mntmGlider);
    }
    
    /* CREATE FILE MENU */
    
    /** Creates file menu */
	
    private void createFileMenu() {
        // Create file menu
    	fileMenu = new JMenu("File");
    	fileMenu.setMnemonic('F');
	
	// Create file menu items
	  //new menu item
    	JMenuItem newItem = new JMenuItem("New");
    	newItem.setMnemonic('N');
    	newItem.setEnabled(true);
    	newItem.addActionListener(this);
	
    //save as menu item
    	JMenuItem mntmSaveAs = new JMenuItem("Save As");
    	mntmSaveAs.setMnemonic('S');
    	mntmSaveAs.setEnabled(true);
    	mntmSaveAs.addActionListener(this);
    	
    //open menu item
    	JMenuItem mntmOpen = new JMenuItem("Open");
    	mntmOpen.setMnemonic('O');
    	mntmOpen.setEnabled(true);
    	mntmOpen.addActionListener(this);
    	
		
        //exit menu item
      	JMenuItem exitItem = new JMenuItem("Exit");
      	exitItem.setMnemonic('x');
      	exitItem.setEnabled(true);
      	exitItem.addActionListener(this);
      	
	// Add to menu
    	fileMenu.add(newItem);	
    	fileMenu.add(mntmSaveAs);
    	fileMenu.add(mntmOpen);
    	fileMenu.addSeparator();
    	fileMenu.add(exitItem);
	}

    /* ------ METHODS ------ */
    
    /* ATION PERFORMED */
    
    /** Item handlers.
    @param event the triggered event. */
    
    public void actionPerformed(ActionEvent event) {
    	if(event.getActionCommand().equals("New")){
    		New();
    	}
    	else if (event.getActionCommand().equals("About ...")) 
        	about();
        else if (event.getActionCommand().equals("Exit"))
        	exitSystem();
        else if (event.getActionCommand().equals("Save As")) 
        	this.SaveAsToFile();
        else if (event.getActionCommand().equals("Open")) 
        	this.OpenFile();
        else if (event.getActionCommand().equals("Copy")) 
        	this.copyPattern();
        else if(event.getActionCommand().equals("Paste"))
        	this.pastePattern();
        else if(event.getActionCommand().equals("Glider"))
        	this.glidePattern();
        else 
        	JOptionPane.showMessageDialog(this,"Error in event handler",
			                  "Error: ",JOptionPane.ERROR_MESSAGE);
	}	 
    /* New */
    private void New(){
    	String s = (String)JOptionPane.showInputDialog(this,"Enter Cell Number:","New Board",JOptionPane.PLAIN_MESSAGE);


    	int cellNumber;// acquiring the cellNumbers from the user
    	if(s.equals(null)){
    		cellNumber = 10;
    	}else{
    		cellNumber = Integer.parseInt(s);
    	}

    	Board theBoard = this.theBoardView.getBoard();
    	theBoard = new Board(cellNumber);
    	//setting cell number, cell width and height
    	this.theBoardView.setBoard(theBoard);
    	
    	//painting the new boardview
    	this.theBoardView.repaint();
    }
    
    /* ABOUT */
    
    /** Outputs JOption pane if about menu item selected. */
    
    private void about() {   
    	//	textArea.append("Code example illustrating use of JMenus\n");
    	
	}
    /* Save as to file */
    private void SaveAsToFile() {
    	this.theBoardView.SaveAsToFile();
    }
    
    /* Open file */
    private void OpenFile(){
    	this.theBoardView.OpenFile();
    }
    
    /* EXIT */
    
    /** Exits system */
    
    private void exitSystem() {
        System.exit(0);
	}
	
    //Copy pattern
    private void copyPattern(){
    	this.theBoardView.copyPattern();
    }
    private void pastePattern(){
    	this.theBoardView.pastePattern();
    }
    private void glidePattern(){
    	this.theBoardView.glidePattern();
    }
 }

