package tr.org.kamp.linux.agarioclone;

import java.awt.Color;

import tr.org.kamp.linux.agarioclone.logic.GameLogic;
import tr.org.kamp.linux.agarioclone.model.Difficulty;
/**
 * 
 * @author merve güllüdağ
 * @version 1.0
 * 
 * Oyunu başlatan application dosyası
 *
 */
public class AgarioCloneApp {
	public static void main(String[] args) {
		GameLogic gameLogic=new GameLogic("merve",Color.BLUE,Difficulty.EASY);
		gameLogic.startApplication();
		
	}
}
