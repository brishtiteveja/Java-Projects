package lifegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckBoxRadioButtonListener implements ActionListener {
	private BoardView boardViewInstance;
	public CheckBoxRadioButtonListener(BoardView view){
		this.boardViewInstance = view;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Choose Color")){
			if(!boardViewInstance.getBoard().getWhichRadioButton().equals("")){
				boardViewInstance.getBoard().setChooseColorButtonFlag(true);
				boardViewInstance.clearState();
			}
		}
		else if(e.getActionCommand().equals("Dead Cell Color")){
			boardViewInstance.getBoard().setWhichRadioButton("dead");
		}else if(e.getActionCommand().equals("Alive Cell Color")){
			boardViewInstance.getBoard().setWhichRadioButton("alive");
		}
		
		boardViewInstance.changeRuleOrColor();
		boardViewInstance.repaint();
	}

}
