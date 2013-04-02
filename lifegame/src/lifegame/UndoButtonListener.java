package lifegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UndoButtonListener implements ActionListener {
	private BoardView boardViewInstance;

	public UndoButtonListener(BoardView view){
		this.boardViewInstance = view;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		boardViewInstance.undoState();
	}

}
