package tr.org.kamp.linux.agarioclone.model;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
/**
 * 
 * @author merve güllüdağ
 * @version 1.0
 * Oyuncu için gerekli metotlar
 * 
 */
public class Player extends GameObject {
	private int speed;
	private String playerName;
	/**
	 * 
	 * @param x
	 * @param y
	 * @param r
	 * @param color
	 * @param speed  speed oyuncunun hızı
	 * @param playerName  playerName oyuncunun adı
	 */
	
	public Player(int x, int y, int r, Color color, int speed,String playerName) {
		
		super(x, y, r, color);
		this.speed=speed;
		this.playerName=playerName;
	}
	/**
	 * oyuncunun paneldeki görüntüsünün üzerine adını ortalanmış bir şekilde yazmak için
	 * 
	 */
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		FontMetrics fontMetrics =g2d.getFontMetrics(g2d.getFont());
		int width=fontMetrics.stringWidth(playerName);//yaznının genişliğini biliyoruz
		int nameX=getX()+(getRadius()-width)/2;
		int nameY=getY()-fontMetrics.getHeight();
		g2d.drawString(playerName, nameX,nameY);
	}
	
	/**
	 * Oyuncunu boyutunun 5'den daha küçük 250'den daha büyük olmamasını  garantiledik.
	 */
	@Override
	public void setRadius(int radius) {
		// TODO Auto-generated method stub
		super.setRadius(radius);
		if(getRadius()<5) {
			setRadius(5);
		}else if (getRadius()>250) {
			setRadius(250);
		}
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}


	
}
