package lifegame;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Button;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.Color;

public class Main{
	private static JTextField textField;
	private static JTextField textField_1;
	private static JTextField textField_2;
	private static JTextField textField_3;
	
	
	
	public static void main(String[] args) {
		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Lifegame");
	    
		final JPanel base = new JPanel();
		frame.setContentPane(base);
		base.setPreferredSize(new Dimension(1000,740));
		frame.setMinimumSize(new Dimension(740,740));
		
		base.setLayout(new BorderLayout());
		
		final BoardView view = new BoardView();
		view.setBackground(new Color(204, 255, 255));
		base.add(view, BorderLayout.CENTER);
		
		//setting the JFrame to the board class
		view.getBoard().setFrame(frame);
		
		//add keyListener
		frame.setFocusable(true);
		frame.addKeyListener(view);
		
		final MenuFrame menuFrame = new MenuFrame(view); // MenuFrame object creation
		frame.setJMenuBar(menuFrame.bar);      //setting the menu bar to the frame
		
		final JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(204, 255, 204));
		buttonPanel.setForeground(new Color(255, 240, 245));
		
		
		base.add(buttonPanel,BorderLayout.EAST);
		
		Button NextButton = new Button("Next"); // Next Button object
		NextButton.setForeground(Color.BLUE);
		NextButton.setBackground(new Color(204, 255, 102));
		Button UndoButton = new Button("Undo"); // Undo Button object
		UndoButton.setBackground(new Color(0, 0, 128));
		UndoButton.setForeground(new Color(47, 79, 79));
		Button AutoButton = new Button("Auto On/Off"); // Auto Button object
		JButton btnClear = new JButton("Clear");// Clear Button object
		
		NextButtonListener NextButtonlistener = new NextButtonListener(view);//NextButtonListener class object initiation
		UndoButtonListener UndoButtonlistener = new UndoButtonListener(view);//UndoButtonListener class object initiation
		AutoButtonListener AutoButtonlistener = new AutoButtonListener(view);//AutoButtonListener class object initiation
		ClearButtonListener ClearButtonlistener = new ClearButtonListener(view);//ClearButtonListener class object initiation
		CheckBoxRadioButtonListener CheckBoxRadioButtonlistener = new CheckBoxRadioButtonListener(view);
		
		NextButton.addActionListener(NextButtonlistener); //Class which will perform action for Next Button
		UndoButton.addActionListener(UndoButtonlistener); //Class which will perform action for Undo Button
		AutoButton.addActionListener(AutoButtonlistener); //Class which will perform action for Clear Button
		btnClear.addActionListener(ClearButtonlistener);
		
		JLabel lblNewRule = new JLabel("New Rule");
		
		JCheckBox checkBox = new JCheckBox("");
		view.getBoard().setCheckBox(checkBox);
		checkBox.addActionListener(CheckBoxRadioButtonlistener);
		
		textField = new JTextField();
		textField.setColumns(10);
		view.getBoard().setTextField(textField, 1); // setting survival_1 text field
		
		JLabel lblSurvival = new JLabel("Survival");
		
		JLabel lblOr = new JLabel("or");
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		view.getBoard().setTextField(textField_1, 2);// setting survival_2 text field
		
		JLabel label = new JLabel("or");
		
		JLabel label_1 = new JLabel("Survival");
		
		JLabel lblNewBorn = new JLabel("    Birth");
		
		JLabel label_2 = new JLabel("or");
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		view.getBoard().setTextField(textField_2, 3); // setting newBorn_1 text field
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		view.getBoard().setTextField(textField_3, 4); // setting newBorn_2 text field
		
		JButton btnChooseColor = new JButton("Choose Color");
		btnChooseColor.addActionListener(CheckBoxRadioButtonlistener);
		
		JRadioButton rdbtnDeadCellColor = new JRadioButton("Dead Cell Color");
		rdbtnDeadCellColor.setForeground(new Color(0, 0, 0));
		rdbtnDeadCellColor.addActionListener(CheckBoxRadioButtonlistener);
		view.getBoard().setRadioButton(rdbtnDeadCellColor, 1);
		
		JRadioButton rdbtnAliveCellColor = new JRadioButton("Alive Cell Color");
		rdbtnAliveCellColor.setForeground(new Color(204, 0, 0));
		rdbtnAliveCellColor.addActionListener(CheckBoxRadioButtonlistener);
		view.getBoard().setRadioButton(rdbtnAliveCellColor, 2);
		
		GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
		gl_buttonPanel.setHorizontalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGap(44)
					.addComponent(NextButton, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(UndoButton, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					.addGap(45))
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGap(87)
					.addComponent(btnClear)
					.addContainerGap(134, Short.MAX_VALUE))
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGap(73)
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(AutoButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewRule)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(checkBox)))
					.addContainerGap(117, Short.MAX_VALUE))
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
							.addGap(72)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
							.addGap(67))
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addGroup(gl_buttonPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblSurvival)
								.addComponent(lblNewBorn, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_buttonPanel.createSequentialGroup()
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblOr)
									.addGap(18)
									.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_buttonPanel.createSequentialGroup()
									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)))
							.addGap(24)))
					.addContainerGap(81, Short.MAX_VALUE))
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnDeadCellColor)
						.addComponent(rdbtnAliveCellColor))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnChooseColor)
					.addContainerGap(20, Short.MAX_VALUE))
		);
		gl_buttonPanel.setVerticalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGap(114)
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(UndoButton, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
						.addComponent(NextButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(AutoButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(42)
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(checkBox)
						.addComponent(lblNewRule))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOr)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSurvival))
					.addGap(27)
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_2)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewBorn))
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addGap(46)
							.addComponent(rdbtnDeadCellColor)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(rdbtnAliveCellColor))
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addGap(67)
							.addComponent(btnChooseColor)))
					.addGap(45)
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addGap(182)
							.addComponent(label))
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addGap(194)
							.addComponent(label_1)))
					.addGap(345))
		);
		buttonPanel.setLayout(gl_buttonPanel);
		
		frame.pack();
		frame.setVisible(true);
	}
}
