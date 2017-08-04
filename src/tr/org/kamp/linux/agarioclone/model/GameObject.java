package tr.org.kamp.linux.agarioclone.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
/**
 * 
 * @author merve güllüdağ
 * @version 10.0
 * Bu class oluşturacağımız modellerin ortak özellik özelliklerinden oluşmaktadır.
 *
 */
public class GameObject {
	
	private int x;
	private int y;
	private int radius;
	private Color color;
	/**
	 * 
	 * @param x  x kordinatı
	 * @param y  y koordinatı
	 * @param radius  yarıçap değeri
	 * @param color   renk
	 */
	
	
	public GameObject(int x, int y, int radius, Color color) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.color = color;
	}
	
	/**
	 * 
	 * @param g2d Graphics2D kütüphanesinden oluşturduğumuz değişken 
	 * Modellerin panael üzerinde çizilmesi için oluşturuldu.
	 * 
	 */
	public synchronized void draw(Graphics2D g2d) {
		g2d.setColor(getColor());
		g2d.fillOval(getX(), getY(), getRadius(), getRadius());
		
	}
	
	/**
	 * 
	 * @return 
	 * Keşisme durumunun kontrolü
	 */
	public Rectangle getRectangle() {//keşişme olayı 
		Rectangle rect =new Rectangle(getX(),getY(),getRadius(),getRadius());
		return rect;
		
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
