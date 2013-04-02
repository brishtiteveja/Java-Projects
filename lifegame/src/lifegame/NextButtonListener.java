package lifegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextButtonListener implements ActionListener {
	private BoardView boardViewInstance;
	public NextButtonListener(BoardView view){
		this.boardViewInstance = view;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		boardViewInstance.nextState();
	}

}
