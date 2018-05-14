package f2.spw;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import java.awt.Color;

public class Main {
	public static void main(String[] args){
		JFrame frame = new JFrame("Space War 2 Player");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(416, 639);
		frame.getContentPane().setLayout(new BorderLayout());
		
		SpaceShip v = new SpaceShip(180, 550, 20, 20, new Color(0, 255, 0));
		SpaceShip v2 = new SpaceShip(180, 550, 20, 20, new Color(0, 0, 255));
		GamePanel gp = new GamePanel();
		GameEngine engine = new GameEngine(gp, v, v2);

		frame.addKeyListener(engine);
		frame.getContentPane().add(gp, BorderLayout.CENTER);
		frame.setVisible(true);
		
		engine.start();
	}
}