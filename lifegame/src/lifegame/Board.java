package lifegame;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Color;
import java.io.*;

import javax.swing.JFileChooser;



public class Board implements Runnable{
	private JFrame frame;
	private JCheckBox checkBox;
	private JTextField survival_1txt;
	private JTextField survival_2txt;
	private JTextField newBorn_1txt;
	private JTextField newBorn_2txt;
	
	private JRadioButton deadRdb;
	private JRadioButton aliveRdb;
	
	private int survival_1;
	private int survival_2;
	private int newBorn_1;
	private int newBorn_2;
	
	private ColorChooser colorChooser;
	private Color deadColor;
	private Color aliveColor;
	
	private boolean chooseColorButtonFlag;
	private String whichRadioButton;
	
	private int leftX; // The top left corner x index from where the cell
						// painting starts.
	private int leftY; // The top left corner y index from where the cell
						// painting starts.
	private int width; // The width of the board
	private int height;// The height of the board
	private int cellWidth;// Width of each cell
	private int cellHeight;// Height of each cell
	private int cellNumber; // Number of cells
	private boolean[][] cellIsAlive;// Holds the boolean information of
									// dead-alive state of all the cells
	private int[][] tmpAroundCellAliveNumber;// Array which holds the
												// surrounding alive cell
												// numbers of each cell.
	private java.util.Stack<boolean[][]> history;
	private boolean autoOn; // Auto button on off flag
	private int interval; // Time interval between two states while changing to
							// next state automatically.
	private BoardView theBoardView; // the BoardView object
	private volatile Thread blinker;// The thread controller object

	//file Chooser
	private final JFileChooser fc = new JFileChooser();

	public Board(int Num){
		this.setcellNumber(Num);
		int N = this.cellNumber;
		this.cellIsAlive = new boolean[N][N];
		tmpAroundCellAliveNumber = new int[N][N];
		history = new java.util.Stack<boolean[][]>();
		
		//survival and new born cell number
		this.survival_1 = 2;
		this.survival_2 = 3;
		this.newBorn_1 = 3;
		this.newBorn_2 = -100;
		//Choose Color button flag is initially false
		this.chooseColorButtonFlag = false;
		
		//constructing color chooser
		this.colorChooser = new ColorChooser();
		this.colorChooser.setBoard(this);
		//Which Radio button to select
		this.whichRadioButton = new String("");
		// top left corner to start drawing cells
		this.leftX = 10;
		this.leftY = 20;
		// total width and height occupied by all the cells
		this.height = 700;
		this.width = 700-10;
		// setting each cell width and height according to the cell numbers
		this.setCellHeight(N);
		this.setCellWidth(N);

		// In the beginning auto is off
		autoOn = false;
		interval = 500; // 500 millisecond interval
	}
	public synchronized void setSurvivalNewBornCellNumber(int survival_1,int survival_2,int newBorn_1,int newBorn_2){
		this.survival_1 = survival_1;
		this.survival_2 = survival_2;
		this.newBorn_1 = newBorn_1;
		this.newBorn_1 = newBorn_2;
	}
	public synchronized ColorChooser getColorChooser(){
		return this.colorChooser;
	}
	public synchronized Color getDeadColor(){
		return this.deadColor;
	}
	public synchronized Color getAliveColor(){
		return this.aliveColor;
	}
	public synchronized String getWhichRadioButton(){
		return this.whichRadioButton;
	}
	public synchronized void setWhichRadioButton(String s){
		this.whichRadioButton = s;
	}
	public synchronized void setChooseColorButtonFlag(Boolean b){
		this.chooseColorButtonFlag = b;
	}
	public synchronized JCheckBox getCheckBox(){
		return this.checkBox;
	}
	public synchronized void setCheckBox(JCheckBox checkBox){
		this.checkBox = checkBox;
	}
	public synchronized void setTextField(JTextField textField,int identity){
		if(identity == 1){
			this.survival_1txt = textField;
		}else if(identity == 2){
			this.survival_2txt = textField;
		}else if(identity == 3){
			this.newBorn_1txt = textField;
		}else if(identity == 4){
			this.newBorn_2txt = textField;
		}
	}
	public synchronized JRadioButton getRadioButton(int identity){
		if(identity == 1){
			return this.deadRdb;
		}else {
			return this.aliveRdb;
		}
	}
	public synchronized void setRadioButton(JRadioButton rdb,int identity){
		if(identity == 1){
			this.deadRdb = rdb;
		}else if(identity == 2){
			this.aliveRdb = rdb;
		}
	}
	public synchronized JFrame getFrame(){
		return this.frame;
	}
	public synchronized void setFrame(JFrame frame){
		this.frame = frame;
	}
	public synchronized int getcellNumber() {
		return this.cellNumber;
	}

	public synchronized void setcellNumber(int N) {
		this.cellNumber = N;
	}
	public synchronized int getLeftX() {
		return leftX;
	}

	public synchronized int getLeftY() {
		return leftY;
	}

	public synchronized int getSize() {
		return cellNumber;
	}

	public synchronized void setSize(int N) {
		this.cellNumber = N;
	}

	public synchronized int getCellWidth() {
		return cellWidth;
	}

	public synchronized void setCellWidth(int cellNumber) {
		this.cellWidth = this.width / cellNumber;
	}

	public synchronized int getCellHeight() {
		return cellHeight;
	}

	public synchronized void setCellHeight(int cellNumber) {
		this.cellHeight = this.height / cellNumber;
	}

	public synchronized boolean isAlive(int x, int y) {
		return this.cellIsAlive[x][y];
	}

	public synchronized void switchlife(int x, int y) {
		if (x >= 0 && x <= cellNumber - 1 && y >= 0 && y <= cellNumber - 1) {
			if (cellIsAlive[x][y]) {
				cellIsAlive[x][y] = false;
			} else {
				cellIsAlive[x][y] = true;
			}
		}
	}

	public synchronized java.util.Stack<boolean[][]> getHistory() {
		return this.history;
	}

	public void keepHistory(){
		boolean[][] tmpcellIsAlive = new boolean[cellNumber][cellNumber];
		int countChange = this.countChangeFromLastHistory(tmpcellIsAlive);
		int historySize = history.size();
		if(countChange != 0 || historySize <=1){
			history.push(tmpcellIsAlive);
		}
		
	}
	public int countChangeFromLastHistory(boolean[][] tmpcellIsAlive){
		//saving last history before changing to the next state
		int countChange = 0; 
		for(int i = 0 ; i < cellNumber; i++){
			for(int j=0; j < cellNumber; j++){
				tmpcellIsAlive[i][j] = cellIsAlive[i][j];
			}
		}
		//Counting change from last history state in the current state
		int historySize = history.size();
		if(historySize >= 1){
			for(int i = 0 ; i < cellNumber; i++){
				for(int j=0; j < cellNumber; j++){
					if(history.elementAt(historySize - 1)[i][j] != tmpcellIsAlive[i][j]){
						//Comparing with previous history
						countChange ++;
					}
				}
			}
		}
		
		return countChange;
	}

	// Next State will be determined by previous state of the board
	public synchronized void nextState() {
		//checking the checkBox
		this.changeRuleOrColor();
	
		this.keepHistory();
		while(this.history.size() > 16){
			this.history.remove(0);
		}
		// for all cells, check 8 surrounding cells live or dead state and
		// calculate new state

		for (int i = 0; i < cellNumber; i++) {
			for (int j = 0; j < cellNumber; j++) {
				tmpAroundCellAliveNumber[i][j] = countLiveCells(i, j);
			}
		}

		for (int i = 0; i < cellNumber; i++) {
			for (int j = 0; j < cellNumber; j++) {
				if (DetermineAliveOrDead(i, j))
					cellIsAlive[i][j] = true;
				else
					cellIsAlive[i][j] = false;
			}
		}
		//Counting changes from previous state
		boolean[][] tmpcellIsAlive = new boolean[cellNumber][cellNumber];
		int countChange = this.countChangeFromLastHistory(tmpcellIsAlive);
		if(countChange == 0){ //If theres nothing to change, then stop the thread
			this.history.pop();
		}
	}

	private boolean DetermineAliveOrDead(int i, int j) {
		if (cellIsAlive[i][j]) {
			if (tmpAroundCellAliveNumber[i][j] == survival_1
					|| tmpAroundCellAliveNumber[i][j] == survival_2) {
				return true;
			} else {
				return false;
			}
		} else {
			if (tmpAroundCellAliveNumber[i][j] == newBorn_1 || tmpAroundCellAliveNumber[i][j] == newBorn_2) {
				return true;
			} else {
				return false;
			}
		}
	}

	private int countLiveCells(int x, int y) {
		int dx[] = { -1, 0, 1, 1, 1, 0, -1, -1 }; // for x displacement
		int dy[] = { 1, 1, 1, 0, -1, -1, -1, 0 }; // for y displacement

		int count = 0; // counter for live cell

		for (int k = 0; k < 8; k++) {
			int xx = x + dx[k];
			int yy = y + dy[k];

			if (xx >= 0 && xx <= cellNumber - 1 && yy >= 0
					&& yy <= cellNumber - 1) {
				if (cellIsAlive[xx][yy]) {
					count++;
				}
			}
		}
		return count;
	}

	// undoState method definition (called by the Board View object)
	public synchronized void undoState() {
		if (history.size() >= 1) {
			cellIsAlive = history.pop();
			
		}
	}
	
	// clearState method definition (called by the Board View object)
	public synchronized void clearState(){
		if (autoOn == true) {
			this.stopThread();
			autoOn = false;
		}
		for (int i = 0; i < cellNumber; i++) {
			for (int j = 0; j < cellNumber; j++) {
					cellIsAlive[i][j] = false;
			}
		}
	}

	private void stopThread() {
		Thread tmpBlinker = blinker;
		blinker = null;
		if (tmpBlinker != null) { // This is necessary when thread is in
									// non-runnable state due to sleep,wait
									// method call
			tmpBlinker.interrupt();
		}
		System.out.println("Thread stopped.");
	}

	// auto method to implement animation (called by the Board View object)
	public synchronized void auto(BoardView thisBoardView) {
		theBoardView = thisBoardView;
		if (autoOn == false) {
			this.startThread();
			autoOn = true;
		} else {
			this.stopThread();
			autoOn = false;
		}
	}

	private void startThread() {
		if (blinker == null) {
			blinker = new Thread(this);
			blinker.start();
			System.out.println("Thread started.");
		}
	}

	public void run() {
		int count = 0;
		Thread thisThread = Thread.currentThread();
		while (blinker == thisThread) {
			try {
				this.changeRuleOrColor();//checking the rule
				this.nextState();
				theBoardView.repaint();
				Thread.sleep(interval + 500);
				
				//Counting changes from previous state
				boolean[][] tmpcellIsAlive = new boolean[cellNumber][cellNumber];
				int countChange = this.countChangeFromLastHistory(tmpcellIsAlive);
				System.out.println(countChange);
				if(countChange == 0){ //If theres nothing to change, then stop the thread
					this.stopThread();
				}
				System.out.println("Thread run count:" + count++);
			} catch (InterruptedException e) {
				System.out.println("Thread run error.\n"+e);
			}
		}
	}
	
	
	//Save as to file
	public synchronized void SaveAsToFile() {
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			fc.addChoosableFileFilter(new MyFileFilter());
			//fc.setFileFilter(new MyFileFilter());
			
			int retVal = fc.showSaveDialog(this.frame);
			
			if(retVal == JFileChooser.APPROVE_OPTION){
				File outFile = new File(fc.getSelectedFile().toString());
				String contents = makeStringFromboardPattern();
				
				try {
					this.setContents(outFile, contents);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println(e.toString());
					String tmpString = e.toString();
					
					tmpString = tmpString.replaceAll("java.io.FileNotFoundException: File does not exist: ", "");
					System.out.println("File doesn't exist.\n New File has been created with that name.");
					
					
					File newFile = new File(tmpString); // the new file with the name given by the user
					
					try{
						newFile.createNewFile();
						newFile.setWritable(true);
						this.setContents(newFile,contents);
					}catch(FileNotFoundException ee){
						ee.printStackTrace(); // If there is no file found
					}catch(IOException ee){
						ee.printStackTrace();
					}catch(SecurityException ee){
						ee.printStackTrace(); // If there is no write access
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				System.out.println("Save As command cancelled by user.\n");
			}
				
	}
	private String makeStringFromboardPattern(){
		StringBuilder contents = new StringBuilder();
		contents.append("lifegame\n");
		contents.append(this.cellNumber+"\n");
		
		for(int i = 0 ; i < cellNumber; i++){
			for(int j=0; j < cellNumber; j++){
				if(cellIsAlive[i][j]){
					contents.append(1);
				}else{
					contents.append(0);
				}
			}
			contents.append("\n");
		}

		return contents.toString();
	}
	
	//Open a file
	public synchronized void OpenFile(){
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int retVal = fc.showOpenDialog(this.frame);
		
		if(retVal == JFileChooser.APPROVE_OPTION){
			File inFile = new File(fc.getSelectedFile().toString());
			try {
				BufferedReader input = new BufferedReader(new FileReader(inFile));
				String line = new String(input.readLine());
				if(line.equals("lifegame")){
					line = input.readLine();
					int cellNum = Integer.parseInt(line);
				    int i = 0;
				    while (( line = input.readLine()) != null){
						char[] str = line.toCharArray();
					  for(int j=0; j<cellNum; j++){
						  if(str[j] == '0'){
							  cellIsAlive[i][j] = false;
						  }else{
							  cellIsAlive[i][j] = true;
						  }
					  }
					  i++;
					}
				}else{
					JOptionPane.showMessageDialog(this.frame, new String("File is Incorrect or File format is not allowed."));
				}
			    
			    input.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("Save As command cancelled by user.\n");
		}
	}
	
	/**
	  * Fetch the entire contents of a text file, and return it in a String.
	  * This style of implementation does not throw Exceptions to the caller.
	  *
	  * @param aFile is a file which already exists and can be read.
	  */
	  public String getContents(File aFile) {
	    //...checks on aFile are elided(omited)
	    StringBuilder contents = new StringBuilder();
	    
	    try {
	      //use buffering, reading one line at a time
	      //FileReader always assumes default encoding is OK!
	      BufferedReader input = new BufferedReader(new FileReader(aFile));
	      try {
	        String line = null; //not declared within while loop
	        /*
	        * readLine is a bit quirky :
	        * it returns the content of a line MINUS the newline.
	        * it returns null only for the END of the stream.
	        * it returns an empty String if two newlines appear in a row.
	        */
	        while (( line = input.readLine()) != null){
	          contents.append(line);
	          contents.append(System.getProperty("line.separator"));
	        }
	      }
	      finally {
	        input.close();
	      }
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    
	    return contents.toString();
	  }

	  /**
	  * Change the contents of text file in its entirety, overwriting any
	  * existing text.
	  *
	  * This style of implementation throws all exceptions to the caller.
	  *
	  * @param aFile is an existing file which can be written to.
	  * @throws IllegalArgumentException if param does not comply.
	  * @throws FileNotFoundException if the file does not exist.
	  * @throws IOException if problem encountered during write.
	  */
	  public void setContents(File aFile, String aContents)throws FileNotFoundException, IOException {
	    if (aFile == null) {
	      throw new IllegalArgumentException("File should not be null.");
	    }
	    if (!aFile.exists()) {
	      throw new FileNotFoundException ("File does not exist: " + aFile);
	    }
	    if (!aFile.isFile()) {
	      throw new IllegalArgumentException("Should not be a directory: " + aFile);
	    }
	    if (!aFile.canWrite()) {
	      throw new IllegalArgumentException("File cannot be written: " + aFile);
	    }

	    //use buffering
	    Writer output = new BufferedWriter(new FileWriter(aFile));
	    try {
	      //FileWriter always assumes default encoding is OK!
	      output.write( aContents );
	    }
	    finally {
	      output.close();
	    }
	  }
	  
	  //Change rule
	  public synchronized void changeRuleOrColor(){
		  if(this.chooseColorButtonFlag == false){
			  if(checkBox.isSelected()){
					//the cell will survive if there is survival_1 or survival_2 alive cells around the alive cell
					  if(!this.survival_1txt.getText().equals("")){
						  this.survival_1 = Integer.parseInt(this.survival_1txt.getText());
					  }
					  if(!this.survival_2txt.getText().equals("")){
						  this.survival_2 = Integer.parseInt(this.survival_2txt.getText());
					  }
					 //new cell will be born if there is newBorn_1 or newBorn_2 alive cells around the dead cell
					  if(!this.newBorn_1txt.getText().equals("")){
						  this.newBorn_1 = Integer.parseInt(this.newBorn_1txt.getText());
					  }
					  if(!this.newBorn_2txt.getText().equals("")){
						  this.newBorn_2 = Integer.parseInt(this.newBorn_2txt.getText());
					  }
				  }else{
					  //the cell will survive if there is 2 or 3 alive cells around the alive cell
					  this.survival_1 = 2;
					  this.survival_2 = 3;
					  //new cell will be born if there is 3 alive cells around the dead cell
					  this.newBorn_1 = 3; 
					  this.newBorn_2 = -100; 
				  }
		  }else{
			  if(this.deadRdb.isSelected() || this.aliveRdb.isSelected()){
				  this.chooseColorButtonFlag = false;
				  //showing the GUI of the color chooser
				  try{
					  javax.swing.SwingUtilities.invokeLater(new Runnable() {
				            public void run() {
				                createAndShowGUIForColorChooser();
				            }
				        });
				  }catch(NullPointerException e){
					  e.printStackTrace();
				  } 
			  }
			  
		  }
		  if(this.whichRadioButton.equals("dead")){
			  this.aliveRdb.setFocusable(false);
			  this.aliveRdb.setSelected(false);
		  }else if(this.whichRadioButton.equals("alive")){
			  this.deadRdb.setFocusable(false);
			  this.deadRdb.setSelected(false);
		  }
		  
	  }
	  /**
	     * Create the GUI and show it.  For thread safety,
	     * this method should be invoked from the
	     * event-dispatching thread.
	     */
	    private void createAndShowGUIForColorChooser() {
	        //Create and set up the window.
	        JFrame frame = new JFrame("ColorChooser");

	        //Create and set up the content pane.
	        JComponent newContentPane = this.colorChooser;
	        newContentPane.setOpaque(true); //content panes must be opaque
	        frame.setContentPane(newContentPane);
	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	    }
	    
	  
}
