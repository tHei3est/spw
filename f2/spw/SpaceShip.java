package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpaceShip extends Sprite{

	BufferedImage im;
	int step = 8;
	Color c; 
	
	public SpaceShip(int x, int y, int width, int height, Color c) {
		super(x, y, width, height);
		this.c = c;
	}

	@Override
	public void draw(Graphics2D g) {
		/*g.setColor(c);
		g.fillRect(x, y, width, height);*/
		try {
			im = ImageIO.read(new File("f2//spw//ship.png"));
			 
		} catch (IOException exp) {
				
		}
		g.drawImage(im, x, y, width, height, null);
	}

	public void move(int direction){
		x += (step * direction);
		if(x < 0)
			x = 0;
		if(x > 400 - width)
			x = 400 - width;
	}

}