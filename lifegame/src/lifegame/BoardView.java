package lifegame;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

                                        
public class BoardView extends JPanel
	implements MouseListener,MouseMotionListener,KeyListener{
	
	private Board theBoard;
	private int patternWidth;
	private int patternHeight;
	private int oldpatternWidth;
	private int oldpatternHeight;
	private int patternStartX;
	private int patternStartY;
	private int patternEndX;
	private int patternEndY;
	private int oldpatternStartX;
	private int oldpatternStartY;
	private int oldpatternEndX;
	private int oldpatternEndY;
	private boolean[][] copiedPattern;
	private int detX;	//the x index of the cell on which the mouse pointer is located
	private int detY;	//the y index of the cell on which the mouse pointer is located
	private int justBeforeX; // the x index of the cell the condition of which has been changed just before
	private int justBeforeY; // the y index of the cell the condition of which has been changed just before
	private boolean mouseDraggedFlag;
	private boolean shiftIsPressed;
	private boolean drawPatternFlag;
	private boolean glideflag;
	private boolean copyFlag;
	private Color deadColor;
	private Color aliveColor;
	
	public BoardView(){
		this.requestFocus();
		this.deadColor = Color.white;
		this.aliveColor = Color.red;
		int defaultCellNumber = 10;
		this.theBoard = new Board(defaultCellNumber);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.justBeforeX = -1; // there's no x index for a cell the condition of which has been changed during initialization
		this.justBeforeY = -1; // there's no y index for a cell the condition of which has been changed during initialization
		this.mouseDraggedFlag = false; //Whether mouse dragged or not
		this.drawPatternFlag = false;
		this.glideflag = false;
		this.copyFlag =true;
	}
	
	public Board getBoard(){
		return this.theBoard;
	}
	public void setBoard(Board newBoard){
		this.theBoard = newBoard;
	}
	public void setDeadColor(Color color){
		this.deadColor = color;
	}
	public void setAliveColor(Color color){
		this.aliveColor = color;
	}
	@Override
	public void paint(Graphics g){
		super.paint(g);
		
		int cellNumber = theBoard.getcellNumber();
		int cellWidth = theBoard.getCellWidth();
		int cellHeight = theBoard.getCellHeight();
		int leftX = theBoard.getLeftX();
		int leftY = theBoard.getLeftY();
		
		
		for(int i = 0 ; i < cellNumber  ; i++)
		{
			for(int j= 0 ; j < cellNumber; j ++)
			{
				int x = i * cellWidth;
				int y = j * cellHeight;
				g.setColor(Color.black);
				g.drawRect(leftX+x,leftY+y,cellWidth ,cellHeight);
				if(theBoard.isAlive(i, j)){
					g.setColor(aliveColor);
					g.fillRect(leftX + x+5, leftY + y+5, cellWidth-10, cellHeight-10);	
				}else{
					g.setColor(deadColor);
					g.fillRect(leftX + x+5, leftY + y+5, cellWidth-10, cellHeight-10);
				}
			}
		}
		
		if(this.drawPatternFlag){
			this.drawPatternFlag = false;
			g.setColor(Color.red);
			
			int x = this.patternStartX * cellWidth;
			int y = this.patternStartY * cellHeight;
			int dx = Math.abs(this.patternStartX - this.patternEndX) + 1;
			int dy = Math.abs(this.patternStartY - this.patternEndY) + 1;
			this.patternWidth = dx;
			this.patternHeight = dy;
			int w = dx * cellWidth;
			int h = dy * cellHeight;
			for(int i = 0;i<=5;i += 1){
				g.drawRect(leftX + x + i, leftY + y + i,w - 2*i,h - 2*i);
			}
		}
	}
	private void determineMousePosition(MouseEvent e){
		int cellWidth = theBoard.getCellWidth();
		int cellHeight = theBoard.getCellHeight();
		
		detX = (e.getX() - 10) / cellWidth;
		detY = (e.getY() - 20) / cellHeight;
	}
	
	public void nextState(){
		theBoard.nextState();
		this.repaint();
	}
	public void undoState(){
		theBoard.undoState();
		this.repaint();
	}
	public void auto(){
		theBoard.auto(this);
		this.repaint();
	}
	public void clearState(){
		theBoard.clearState();
		this.repaint();
	}
	public void SaveAsToFile() {
		theBoard.SaveAsToFile();
	}
	public void OpenFile(){
		theBoard.OpenFile();
		this.repaint();
	}
	//change Rule
	public void changeRuleOrColor(){
		theBoard.getColorChooser().setBoardView(this);
		theBoard.changeRuleOrColor();
	}
	//copy pattern
	public void copyPattern(){
		if(this.copyFlag){	
			int dx = Math.abs(this.patternStartX - this.patternEndX) + 1;
			int dy = Math.abs(this.patternStartY - this.patternEndY) + 1;

			this.copiedPattern = new boolean[dx][dy];
			for(int m = 0, i = this.patternStartX;i <= this.patternEndX;i++,m++)
				for(int n=0, j=this.patternStartY ;j <= this.patternEndY;j++,n++){
					copiedPattern[m][n] = theBoard.isAlive(i, j);
				}
			for(int m = 0,i=this.patternStartX;i <= this.patternEndX;i++,m++){
				for(int n=0,j=this.patternStartY ;j <= this.patternEndY;j++,n++){
					System.out.print(copiedPattern[m][n] + " ");
				}
				System.out.print("\n");
			}
			this.repaint();
	   		this.oldpatternStartX = this.patternStartX;
    		this.oldpatternStartY = this.patternStartY;
    		this.oldpatternEndX = this.patternEndX;
    		this.oldpatternEndY = this.patternEndY;
    		this.oldpatternWidth = dx;
    		this.oldpatternHeight = dy;
		}
		
	}
	
	public void pastePattern(){
		if(this.copyFlag){	
			int dx = Math.abs(this.oldpatternStartX - this.oldpatternEndX) + 1;
			int dy = Math.abs(this.oldpatternStartY - this.oldpatternEndY) + 1;
			
			int cellNumber  = theBoard.getcellNumber();
			
			int xx = this.patternStartX + dx - 1;
			int yy = this.patternStartY + dy - 1;
			
			if (xx >= 0 && xx <= cellNumber - 1 && yy >= 0
					&& yy <= cellNumber - 1) {
				for(int m = 0, i = this.patternStartX;i < this.patternStartX + dx;i++,m++)
					for(int n=0, j=this.patternStartY;j < this.patternStartY + dy;j++,n++){
						if(copiedPattern[m][n] != theBoard.isAlive(i,j )){
								theBoard.switchlife(i,j);
						}
					}
				
				this.repaint();
			}
			
		}
		
	}
	public void glidePattern(){
		if(this.glideflag && this.copyFlag){
			this.pastePattern();
			this.glideflag = false;
		}
	}
	
	public void mouseClicked(MouseEvent e){
		this.theBoard.getFrame().requestFocus();
		if(!this.shiftIsPressed){
			theBoard.keepHistory();
			if(this.mouseDraggedFlag == false){
				while(theBoard.getHistory().size() > 16){
					theBoard.getHistory().remove(0);
				}
				System.out.println("size = "+theBoard.getHistory().size());
			}
			this.determineMousePosition(e);
			theBoard.switchlife(detX, detY);
			justBeforeX = detX;
			justBeforeY = detY;
			this.repaint();
		}
	}
	public void mouseEntered(MouseEvent e){

	}
	public void mouseExited(MouseEvent e){

	}
	public void mousePressed(MouseEvent e){
		this.theBoard.getFrame().requestFocus();
		if(this.shiftIsPressed){
			this.determineMousePosition(e);
			this.patternStartX = this.detX;
			this.patternStartY = this.detY;
		}
	}
	public void mouseReleased(MouseEvent e){
		if(!this.shiftIsPressed){
			if(this.mouseDraggedFlag){
				theBoard.getHistory().pop();
				while(theBoard.getHistory().size() > 16){
					theBoard.getHistory().remove(0);
				}
				System.out.println("size = "+theBoard.getHistory().size());
				this.mouseDraggedFlag = false;
			}
		}else{
			if(this.shiftIsPressed){
				this.determineMousePosition(e);
				this.patternEndX = this.detX;
				this.patternEndY = this.detY;
				this.drawPatternFlag = true;
			}
		}
	}
	public void mouseDragged(MouseEvent e){
		if(!this.shiftIsPressed){
			this.mouseDraggedFlag = true;
			theBoard.keepHistory();
			determineMousePosition(e);
			if(detX == justBeforeX && detY == justBeforeY){
				justBeforeX = detX;
				justBeforeY = detY;
			}
			else{
				theBoard.switchlife(detX, detY);
				justBeforeX = detX;
				justBeforeY = detY;
				this.repaint();
			}
		}
	}
	public void mouseMoved(MouseEvent e){
		
	}
	/** Handle the key typed event from the text field. */
    public void keyTyped(KeyEvent e) {
    }
    
    /** Handle the key pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
    	if(e.getKeyCode() == 16){
    		this.shiftIsPressed = true;
    	}
    	if(e.getKeyChar() == 'p'){
    		this.shiftIsPressed = true;
    		System.out.println("p");
    		this.glideflag = true;
    	}
    }
    
    /** Handle the key released event from the text field. */
    public void keyReleased(KeyEvent e) {
    	if(e.getKeyCode() == 16 || e.getKeyChar() == 'p'){
    		this.oldpatternWidth = Math.abs(this.patternStartX - this.patternEndX) + 1;
			this.oldpatternHeight = Math.abs(this.patternStartY - this.patternEndY) + 1;
    		System.out.println("p");
    		this.shiftIsPressed = false;
    		this.drawPatternFlag = true;
    		this.repaint();
    	}
    }
	
}
