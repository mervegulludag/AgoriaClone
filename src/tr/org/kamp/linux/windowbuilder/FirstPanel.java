package tr.org.kamp.linux.windowbuilder;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import tr.org.kamp.linux.agarioclone.logic.GameLogic;
import tr.org.kamp.linux.agarioclone.model.Difficulty;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

public class FirstPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	
	private ButtonGroup buttonGroup;//aynı anda easy,hard,normal seçmesin diye grup haline getirdik artık sadece bir tane seçer.

	/**
	 * Create the panel.
	 */
	public FirstPanel() {
		
		JLabel lblUserName = new JLabel("User Name:");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		
		
		JLabel lblSelectColor = new JLabel("Select Color:");
		
		JLabel lblDifficulty = new JLabel("Difficulty:");
		
		JRadioButton rdbtnEasy = new JRadioButton("Easy");
		rdbtnEasy.setSelected(true);
		
		JRadioButton rdbtnNormal = new JRadioButton("Normal");
		
		JRadioButton rdbtnHard = new JRadioButton("Hard");
		
		buttonGroup=new ButtonGroup();
		buttonGroup.add(rdbtnEasy);
		buttonGroup.add(rdbtnNormal);
		buttonGroup.add(rdbtnHard);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.addItem("RED");
		comboBox_1.addItem("BLUE");
		comboBox_1.addItem("GREEN");
		comboBox_1.addItem("MAGENTA");

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Color selectedColor=Color.BLACK;
				
				switch(comboBox_1.getSelectedItem().toString()){
					case "RED":
						selectedColor=Color.RED;
						break;
					case "BLUE":
						selectedColor=Color.BLUE;
						break;
					case "GREEN":
						selectedColor=Color.GREEN;
						break;
					case "MAGENTA":
						selectedColor=Color.MAGENTA;
						break;
				}
				Difficulty difficulty=Difficulty.EASY;
				
				if(rdbtnEasy.isSelected()) {
					difficulty=Difficulty.EASY;
				}
				else if(rdbtnNormal.isSelected()) {
					difficulty=Difficulty.NORMAL;
					
				}else if(rdbtnHard.isSelected()) {
					difficulty=Difficulty.HARD;
				}
				
				GameLogic gameLogic = new GameLogic(textField.getText(),selectedColor,difficulty);
				gameLogic.startApplication();
			}
		});
		
		JButton btnAbout = new JButton("About");
		btnAbout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(FirstPanel.this,"bu yazılım Gpl lisansı altındadır,\n özgür bir ortamda gerçekleşmiştir.","About",JOptionPane.DEFAULT_OPTION);				
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(lblSelectColor)
							.addGap(8)
							.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 324, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(lblDifficulty)
							.addGap(29)
							.addComponent(rdbtnEasy))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(102)
							.addComponent(rdbtnNormal))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(14)
									.addComponent(lblUserName))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(21)
									.addComponent(lblPassword)))
							.addGap(6)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(textField_1)
								.addComponent(textField, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(102)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnStart)
								.addComponent(rdbtnHard)
								.addComponent(btnAbout))))
					.addGap(537))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(39)
							.addComponent(lblUserName))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(37)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSelectColor)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
					.addGap(9)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(lblDifficulty))
						.addComponent(rdbtnEasy))
					.addGap(6)
					.addComponent(rdbtnNormal)
					.addGap(6)
					.addComponent(rdbtnHard)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnStart)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnAbout)
					.addContainerGap(99, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
}
