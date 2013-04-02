package lifegame;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

/* ColorChooserDemo.java requires no other files. */
public class ColorChooser extends JPanel
                              implements ChangeListener {

    protected JColorChooser tcc;
    private Color newColor;
    
    private Board theBoard;
    private BoardView theBoardView;
    
    public ColorChooser() {
        super(new BorderLayout());

        //Set up color chooser for setting text color
        tcc = new JColorChooser();
        tcc.getSelectionModel().addChangeListener(this);
        tcc.setBorder(BorderFactory.createTitledBorder(
                                             "Choose Text Color"));

        add(tcc, BorderLayout.PAGE_END);
    }
    public void setBoard(Board theBoard){
    	this.theBoard = theBoard;
    }
    public void setBoardView(BoardView theBoardView){
    	this.theBoardView = theBoardView;
    }
    public void stateChanged(ChangeEvent e) {
        this.newColor = tcc.getColor();
      
        if(this.theBoard.getWhichRadioButton().equals("dead")){
        	this.theBoardView.setDeadColor(this.newColor);
        	JRadioButton rdb = new JRadioButton();
        	rdb = this.theBoard.getRadioButton(1);
        	rdb.setForeground(this.newColor);
        }else if(this.theBoard.getWhichRadioButton().equals("alive")){
        	this.theBoardView.setAliveColor(this.newColor);
        	JRadioButton rdb = new JRadioButton();
        	rdb = this.theBoard.getRadioButton(2);
        	rdb.setForeground(this.newColor);
        }
    }
    
    public Color getNewColor(){
    	return this.newColor;
    } 
    
}
