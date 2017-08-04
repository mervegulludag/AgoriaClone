package tr.org.kamp.linux.agarioclone.view;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * 
 * @author merve güllüdağ
 * @version 1.0
 * 
 * Oyun için gerekli çerçeve
 *
 */
public class GameFrame extends JFrame{
	
	public GameFrame() {

		setTitle("Agario Clone");
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setLocationRelativeTo(null);
		setVisible(true);

	}

}
