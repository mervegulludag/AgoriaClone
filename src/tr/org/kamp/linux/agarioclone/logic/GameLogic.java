package tr.org.kamp.linux.agarioclone.logic;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

import tr.org.kamp.linux.agarioclone.model.Chip;
import tr.org.kamp.linux.agarioclone.model.Difficulty;
import tr.org.kamp.linux.agarioclone.model.Enemy;
import tr.org.kamp.linux.agarioclone.model.GameObject;
import tr.org.kamp.linux.agarioclone.model.Mine;
import tr.org.kamp.linux.agarioclone.model.Player;
import tr.org.kamp.linux.agarioclone.view.GameFrame;
import tr.org.kamp.linux.agarioclone.view.GamePanel;
/**
 * 
 * @author merve güllüdağ
 * @version 1.0
 * 
 * Panel üzerinde oyuncu, düşman, yem ve mayınların haketlerini ,çarpışma durumlarını, yeniden oluşma durumlarını tanımladık
 *
 */
public class GameLogic {
	
	private Player player;
	private ArrayList<GameObject> gameObjects;
	private ArrayList<GameObject> chipsToRemove;
	private ArrayList<GameObject> minesToRemove;
	private ArrayList<GameObject> enemiesToRemove;
	
	private GameFrame gameFrame;
	private GamePanel gamePanel;
	private Random random;
	
	private boolean isGameRunning;
	private int xTarget;
	private int yTarget;
	
	/**
	 * 
	 * @param playerName oyuncunun adı
	 * @param selectedColor oyuncunun rengi
	 * @param difficulty oyunun zorluk derecesi Easy,Normal,Hard
	 * 
	 * Girilen değerlere göre oyuncuyu ,yenme durumlarında silinmeleri için yem,mayın ve düşman karaketerleri için
	 * arraylist oluşturuyoruz ve zorluk durumuna göre oluşturulacak karakterlerin sayısını belirliyoruz.
	 *  
	 */
	
	public GameLogic(String playerName,Color selectedColor,Difficulty difficulty){
		player =new Player(10,10,20,selectedColor,1,playerName);
		gameObjects = new ArrayList<GameObject>();
		gameObjects.add(player);
		chipsToRemove=new ArrayList<>();
		minesToRemove=new ArrayList<>();
		enemiesToRemove=new ArrayList<>();
		
		gameFrame =new GameFrame();
		gamePanel =new GamePanel(gameObjects);
		random =new Random();
		gamePanel.setSize(640,480);
		switch (difficulty) {
		case EASY:
			fillChips(25);
			fillMines(7);
			fillEnemies(5);
			break;
		case NORMAL:
			fillChips(25);
			fillMines(10);
			fillEnemies(9);
			break;
		case HARD:
			fillChips(40);
			fillMines(20);
			fillEnemies(15);
			break;
		}
		fillChips(25);
		fillMines(10);
		fillEnemies(5);
		addMouseEvents();
	}
	
	/**
	 * Oyun içinde hareketli olan oyuncunun yem,mayın ve düşman karakterlerine çarpması durumunda boyutundaki 
	 * değişimleri veya oyunun bitip bitmeyeceğini, çarpılan karakterinde silinecekler listesine eklenmesini ve sonunda silinmesini sağlar.
	 * Aynı şekilde hareketli olan düşman karakteri içinde mayın ve yeme çarpma durumundaki boyut,
	 * listeye eklenme ve listeden silinmesi durumlarını içerir. 
	 *
	 */
	private void checkColllisions() {
		
		for (GameObject gameObject : gameObjects) {//foreach
			if(player.getRectangle().intersects(gameObject.getRectangle())) {//player ile chis kesişiyormu
				if(gameObject instanceof Chip) {
					player.setRadius(player.getRadius()+gameObject.getRadius());//yenilen obje kadr büyüt
					//gameObjects.remove(gameObject);//aynı anda silmeye .alıştığı için hata aldık listeye ekliyoruz sonra slilicez
					chipsToRemove.add(gameObject);//silinecekler listesine al
				}
				if(gameObject instanceof Mine) {
					player.setRadius((int)player.getRadius()/2);//yenilen obje kadr büyüt
					minesToRemove.add(gameObject);
				}
				if(gameObject instanceof Enemy) {
					if(player.getRadius()>gameObject.getRadius()) {
						player.setRadius(player.getRadius()+gameObject.getRadius());
						enemiesToRemove.add(gameObject);
						
					}else if(player.getRadius()<gameObject.getRadius()){
						gameObject.setRadius(gameObject.getRadius()+player.getRadius());
						isGameRunning =false;
					}
				}
			}
			if(gameObject instanceof Enemy) {
				Enemy enemy=(Enemy) gameObject;
				
				for (GameObject gameObject2 : gameObjects) {
					if(enemy.getRectangle().intersects(gameObject2.getRectangle())) {
						if(gameObject2 instanceof Chip) {
							enemy.setRadius(enemy.getRadius()+gameObject2.getRadius());
							chipsToRemove.add(gameObject2);
						}
						if(gameObject2 instanceof Mine) {
							enemy.setRadius((int)enemy.getRadius()/2);
							minesToRemove.add(gameObject2);
						
						}
				}
					
			}
				
				
			}
		}
		gameObjects.removeAll(chipsToRemove);//slinecekler listesindekilerin hepsini sil
		gameObjects.removeAll(minesToRemove);//slinecekler listesindekilerin hepsini sil
		gameObjects.removeAll(enemiesToRemove);

	}
	
	/**
	 * Mouse hareketleri ile oyuncu karakterinin senkronize bir şekilde hareket etmesini sağlar.
	 * xTarget ve yTarget mousenin o andaki koordinatlarını içerir.
	 */
	private synchronized void movePlayer() {//playerın fareye doğru hareket etmesini sağlıyor.
		if (xTarget > player.getX())
			player.setX((player.getX() + player.getSpeed()));
		else if (xTarget < player.getX())
			player.setX((player.getX() - player.getSpeed()));
		
		if (yTarget > player.getY())
			player.setY((player.getY() + player.getSpeed()));
		else if (yTarget < player.getY())
			player.setY((player.getY() - player.getSpeed()));

	}
	
	/**
	 * Düşmanın boyutu oyuncudan küçük ise kaçması gerekli, ilk önce o an ki düşman ile oyuncu arasındaki mesafeyi alıyorz sonrasında 
	 * ++,+-,-+,-- ile düşmanın konumunu çapraz olarak değiştiriyoruz ve bu olabilecek 4 durum içinde aradaki mesafeyi ölçmek için
	 * calculateNewDistanceToEnemy metodunu kullanıyoruz.Bu metot düşmanı oyuncudan uzaklaşabileceği en uzak mesafeye konumlandırır.
	 * 
	 * Düşmanın boyutu oyuncudan büyük ise kovalaması gerek bu durum oyuncunun mouseyi takip etmesi ile aynı durum.
	 * Tüm bu durumlar senkronize ilerlemekte
	 */
	private synchronized void moveEnemyPlayer() {//fareye doğru hareket etmesini sağlıyor.
		for (GameObject gameObject : gameObjects) {
			if (gameObject instanceof Enemy) {
				Enemy enemy = (Enemy) gameObject;
				if (enemy.getRadius() < player.getRadius()) {
					// Kacacak
					int distance = (int) Point.distance(player.getX(), player.getY(), enemy.getX(), enemy.getY());

					int newX = enemy.getX() + enemy.getSpeed();
					int newY = enemy.getY() + enemy.getSpeed();
					if (calculateNewDistanceToEnemy(enemy, distance, newX, newY)) {
						continue;
					}

					newX = enemy.getX() + enemy.getSpeed();
					newY = enemy.getY() - enemy.getSpeed();
					if (calculateNewDistanceToEnemy(enemy, distance, newX, newY)) {
						continue;
					}

					newX = enemy.getX() - enemy.getSpeed();
					newY = enemy.getY() + enemy.getSpeed();
					if (calculateNewDistanceToEnemy(enemy, distance, newX, newY)) {
						continue;
					}

					newX = enemy.getX() - enemy.getSpeed();
					newY = enemy.getY() - enemy.getSpeed();
					if (calculateNewDistanceToEnemy(enemy, distance, newX, newY)) {
						continue;
					}

				} else {
					// Yiyecek
					if (player.getX() > enemy.getX()) {
						enemy.setX(enemy.getX() + enemy.getSpeed());
					} else if (player.getX() < enemy.getX()) {
						enemy.setX(enemy.getX() - enemy.getSpeed());
					}

					if (player.getY() > enemy.getY()) {
						enemy.setY(enemy.getY() + enemy.getSpeed());
					} else if (player.getY() < enemy.getY()) {
						enemy.setY(enemy.getY() - enemy.getSpeed());
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param enemy
	 * @param distance
	 * @param x
	 * @param y
	 * @return
	 * Düşmanın kaçma durumunda yeni konumu ile eski konumunu kıyaslayıp düşmanı oyuncudan en uzağa konumlandırır.
	 */
	private boolean calculateNewDistanceToEnemy(Enemy enemy, int distance, int x, int y) {
		int newDistance = (int) Point.distance(player.getX(), player.getY(), x, y);
		if (newDistance > distance) {
			enemy.setX(x);
			enemy.setY(y);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param n n kaç tane yem oluştuurulayacağı
	 * Yemleri panel içerisinde oluşturmak için 
	 */
	private void fillChips(int n) {//yem oluşturuduk.
		for (int i = 0; i < n; i++) {
			int x = random.nextInt(gamePanel.getWidth());
			int y = random.nextInt(gamePanel.getHeight());
			if (x >= gamePanel.getWidth()) {
				x -= 15;
			}
			if (y >= gamePanel.getHeight()) {
				y -= 15;
			}
			gameObjects.add(new Chip(x, y, 10, Color.ORANGE));
		}
	}
	
	/**
	 * 
	 * @param n n oluşturulacakmayın sayısı
	 * Mayınları panel içinde oluşturulmasını ve yeni oluşan mayınların oyuncu karakterinin üzerinde oluşmamasını garanti eder.
	 */
	private void fillMines(int n) {
		for (int i = 0; i < n; i++) {

			int x = random.nextInt(gamePanel.getWidth());
			int y = random.nextInt(gamePanel.getHeight());
			if (x >= gamePanel.getWidth()) {
				x -= 15;
			}
			if (y >= gamePanel.getHeight()) {
				y -= 15;
			}

			Mine mine = new Mine(x, y, 20, Color.GREEN);

			while (player.getRectangle().intersects(mine.getRectangle())) {
				x = random.nextInt(gamePanel.getWidth());
				y = random.nextInt(gamePanel.getHeight());
				if (x >= gamePanel.getWidth()) {
					x -= 15;
				}
				if (y >= gamePanel.getHeight()) {
					y -= 15;
				}
				mine.setX(x);
				mine.setY(y);
			}

			gameObjects.add(mine);
		}
	}
	
	/**
	 * 
	 * @param n n oluşturulacak düşman karakteri sayısı
	 * Düşmanların panel içinde oluşturulmasını ve yeni oluşan düşmanların oyuncu karakterinin üzerinde oluşmamasını garanti eder.

	 */
	private void fillEnemies(int n) {
		for (int i = 0; i < n; i++) {
			int x = random.nextInt(gamePanel.getWidth());
			int y = random.nextInt(gamePanel.getHeight());
			if (x >= gamePanel.getWidth()) {
				x -= 15;
			}
			if (y >= gamePanel.getHeight()) {
				y -= 15;
			}
			Enemy enemy = new Enemy(x, y, (random.nextInt(10) + 25), Color.RED,1);
			while (player.getRectangle().intersects(enemy.getRectangle())) {
				x = random.nextInt(gamePanel.getWidth());
				y = random.nextInt(gamePanel.getHeight());
				if (x >= gamePanel.getWidth()) {
					x -= 15;
				}
				if (y >= gamePanel.getHeight()) {
					y -= 15;
				}
				enemy.setX(x);
				enemy.setY(y);
			}

			gameObjects.add(enemy);
		}
	}
	/**
	 * senkronize bir şekilde yenilen yem,mayın ve düşmaların yeniden oluşmasını sağlar
	 */
	private synchronized void addNewObject() {
		fillChips(chipsToRemove.size());
		fillMines(minesToRemove.size());
		fillEnemies(enemiesToRemove.size());
		
		chipsToRemove.clear();
		minesToRemove.clear();
		enemiesToRemove.clear();
		
	}
	/**
	 * isGameRunning değişkeni false gelmedikçe, yani oyun bitmedikçe oyunun devam etmesini sağlar.
	 */
	private void startGame() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
              while(isGameRunning) {
				  movePlayer();
				  moveEnemyPlayer();
				  checkColllisions();
				  addNewObject();
            	  gamePanel.repaint();
            	  
            	  try {
            		  Thread.sleep(20);
            	  }catch(InterruptedException e){
            		  e.printStackTrace();
            	  }
              }
			}
		}).start();
	}
	
	/**
	 * Paneli oluşturuyoruz ve oyunu başlatıyoruz.
	 */
	public void startApplication() {
		gameFrame.setContentPane(gamePanel);
		gamePanel.setVisible(true);
		isGameRunning=true;
		startGame();
	}

	/**
	 * mouse hareketlerini dinleyip, mousenin konumunu xTaget ve yTarget değişkenlerine atıyoruz.
	 */
	private void addMouseEvents() {
		gameFrame.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		gamePanel.addMouseMotionListener(new MouseMotionListener() {

		@Override
		public void mouseMoved(MouseEvent e) {
			xTarget = e.getX();
			yTarget = e.getY();
				
		}

		@Override
		public void mouseDragged(MouseEvent e) {

		}
		});
	}
}








