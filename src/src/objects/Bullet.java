package src.objects;

import java.awt.Color;
import java.awt.Rectangle;

import src.main.Parameters;
import src.panes.LevelPane;

public class Bullet extends NonTexturedObject2D{

	double angle=0;
	double x;
	double y;
	int movespeed = 4;
	boolean hit = false;
	Rectangle rect;
	
	public Bullet(int xs, int ys, int width, int height, boolean filleds) {
		super(xs, ys, width, height, filleds);
		setColor(Color.YELLOW);
	}

	public void enable(int xs, int ys,double angles)
	{
		x = xs;
		y = ys;
		angle = angles;
//		System.out.println(angle);
		enabled = true;
	}
	
	public void reset()
	{
		hit = false;
	}
	
	public void createRect()
	{
		rect = new Rectangle((int)x,(int)y,width,height);
	}
	
	public void update(User user)
	{
		if(enabled)
		{
			x += (Math.cos(angle)*movespeed);
			y += (Math.sin(angle)*movespeed);
//			System.out.println(x+":"+y);
//			System.out.println(Math.cos(Math.toRadians(angle)));
			createRect();
			if(LevelPane.collisionWithFloor(rect))
			{
				enabled = false;
			}
			else if(x >= Parameters.width || x <= 0 || y >= Parameters.height || y <= 0)
			{
				enabled = false;
			}
			if(rect.intersects(user.getRect()))
			{
				enabled = false;
				hit = true;
			}
				
		}
	}
	
	public boolean getHit()
	{
		return hit;
	}
	
	public int getX()
	{
		return (int)x;
	}
	
	public int getY()
	{
		return (int)y;
	}
}
