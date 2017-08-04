package tr.org.kamp.linux.agarioclone.model;

import java.awt.Color;
/**
 * 
 * @author merve güllüdağ
 * @version 1.0
 * Düşman karakterleri için gerekli metotlar.
 *
 */
public class Enemy extends GameObject {
	private int speed;
	/**
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 * @param color
	 * @param speed  spedd düşmanın hızı
	 */
	public Enemy(int x, int y, int radius, Color color,int speed) {
		super(x, y, radius, color);
		this.speed=speed;
	}
	
	/**
	 * Düşman karakterlerinin 5'den küçük 250'den büyük olmamasını garanti ettik.
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

}
