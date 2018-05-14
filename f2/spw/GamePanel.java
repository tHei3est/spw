package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	public GamePanel() {
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		big.setBackground(Color.GRAY);
	}

	public void updateGameUI(GameEngine engine){
		big.clearRect(0, 0, 400, 600);
		drawMessage(engine);
		for(Sprite s : sprites){
			s.draw(big);
		}
		repaint();
	}

	public void drawMessage(GameEngine engine){
		//score
		big.setColor(Color.WHITE);
		big.setFont(big.getFont().deriveFont(12.0f));
		big.drawString(String.format("Score: %07d", engine.getScore()), 310, 20);

		//text alert
		if(engine.getMessages() != null){
			String[] messages =  engine.getMessages();
			big.setColor(Color.YELLOW);		
			for(int i = 0; i<messages.length ; i++){
				float fontSize = 25.0f-5.0f*i;
				big.setFont(big.getFont().deriveFont(fontSize));
				big.drawString(messages[i], 
				400/2 - messages[i].length()*fontSize*0.35f+(25-fontSize)*5, 600/3+i*(fontSize+10));
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}
	
}