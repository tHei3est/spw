package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;

public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Enemy2> enemies2 = new ArrayList<Enemy2>();
	private SpaceShip v;
	private SpaceShip v2;
	
	private Timer timer;
	private boolean isPause;
	
	private long score = 0;
	private String[] messages;
	private double difficulty = 0.1;
	
	public GameEngine(GamePanel gp, SpaceShip v, SpaceShip v2) {
		this.gp = gp;
		this.v = v;	
		this.v2 = v2;
		
		gp.sprites.add(v);
		gp.sprites.add(v2);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);

		init();
	}

	public void init(){
		messages = null;
		isPause = false;
		score = 0;
		difficulty = 0.1;
		enemies = new ArrayList<Enemy>();
		enemies2 = new ArrayList<Enemy2>();
		gp.sprites = new ArrayList<Sprite>();
		gp.sprites.add(v);
		gp.sprites.add(v2);

		if(timer.isRunning()){
			timer.stop();
		}
	}
	
	public void start(){
		timer.start();
	}
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}

	private void generateEnemy2(){
		Enemy2 e2 = new Enemy2((int)(Math.random()*390), 30);
		gp.sprites.add(e2);
		enemies2.add(e2);
	}
	
	private void process(){
		if(Math.random() < difficulty){
			generateEnemy();
		}

		if(Math.random() < difficulty){
			generateEnemy2();
		}

		difficulty += 0.0001;
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 200;
			}
		}

		Iterator<Enemy2> e_iter2 = enemies2.iterator();
		while(e_iter2.hasNext()){
			Enemy2 e2 = e_iter2.next();
			e2.proceed();
			
			if(!e2.isAlive()){
				e_iter2.remove();
				gp.sprites.remove(e2);
				score += 200;
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double vr2 = v2.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr) || er.intersects(vr2)){
				die();
				return;
			}
		}

		Rectangle2D.Double er2;
		for(Enemy2 e2 : enemies2){
			er2 = e2.getRectangle();
			if(er2.intersects(vr) || er2.intersects(vr2)){
				die();
				return;
			}
		}
	}
	
	public void die(){
		timer.stop();
		messages = new String[]{
			"GAME OVER!",
			String.format("YOUR SCORE is %07d", score),
			"please 'R' to retry"
		};
		gp.updateGameUI(this);
	}

	public void pause(){
		if(!isPause){
			timer.stop();
			messages = new String[]{
				"PAUSE", "",
				"Press 'P' to Continue"
			};
		}
		else{
			timer.start();
			messages = null;
		}
		gp.updateGameUI(this);
		isPause = !isPause;
	}

	public long getScore(){
		return score;
	}
	
	public String[] getMessages(){
		return messages;
	}

	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.move(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v.move(1);
			break;
		case KeyEvent.VK_A:
      		v2.move(-1);
      		break;
    	case KeyEvent.VK_D:
      		v2.move(1);
			break;
		case KeyEvent.VK_R:
			init();
			start();
			break;
		case KeyEvent.VK_P:
			pause();
			break;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}