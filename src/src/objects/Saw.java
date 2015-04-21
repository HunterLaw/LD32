package src.objects;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.File;

public class Saw extends TexturedObject2D{

	final File file = new File("Rsc/Saw.png");
	
	int locx;
	int locy;
	double newangle;
	AffineTransform tx;
	AffineTransformOp op;
	
	public Saw(int xs, int ys, int widths, int heights, boolean filled) {
		super(xs, ys, widths, heights, filled);
		loadBasicImage(file);
		createRect();
	}
	
	public void setImageAngle(int angle)
	{
		locx = width/2;
		locy =height/2;
		newangle = Math.toRadians(angle);
		tx = AffineTransform.getRotateInstance(newangle,locx,locy);
		op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
		texture = op.filter(texture, null);
	}
	
	public void createRect()
	{
		rect = new Rectangle(x,y,width,height);
	}
	
	public void update()
	{
		
	}

	public Rectangle getRect()
	{
		return rect;
	}
	
}
