package src.objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Floor extends TexturedObject2D{

	BufferedImage repeat;
	
	public Floor(int xs, int ys, int width, int height, boolean filleds) {
		super(xs, ys, width, height, filleds);
	}
	
	public void setRepeatingImage(File loc)
	{
		texture = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		try{
			repeat = ImageIO.read(loc);
		}catch (IOException e)
		{
			e.printStackTrace();
		}
		Graphics2D g = (Graphics2D)texture.getGraphics();
		for(int x = 0;x<width;x+=16)
		{
			g.drawImage(repeat,x,0,null);
		}
		g.dispose();
	}
	
}
