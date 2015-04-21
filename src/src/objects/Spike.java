package src.objects;

import java.awt.Rectangle;
import java.io.File;

public class Spike extends TexturedObject2D{

	final File file = new File("Rsc/Spike.png");
	public Spike(int xs, int ys, int widths, int heights, boolean filled) {
		super(xs, ys, widths, heights, filled);
		loadBasicImage(file);
		createRect();
	}
	
	public void createRect()
	{
		rect = new Rectangle(x,y,width,height);
	}
	
	public Rectangle getRect()
	{
		return rect;
	}
	
}
