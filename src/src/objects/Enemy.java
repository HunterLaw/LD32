package src.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import src.Direction;
import src.main.Parameters;
import src.main.Score;
import src.panes.LevelPane;

public class Enemy extends TexturedObject2D{

	int movespeed = 1;
	int runspeed = 2;
	int gravity = 4;
	int sightrange = 300;
	boolean canfire = true;
	boolean chardead = false;
	final File loc = new File("Rsc/Enemy.png");
	Floor floor;
	Direction lorr = Direction.none;
	Direction uord = Direction.none;
	Rectangle urect;
	Rectangle sight;
	Bullet bullet;
	Timer timer;
	TimerTask timertask;
	Random rand = new Random();
//	int[] corner 
	
	public Enemy(int xs, int ys, Dimension sizes, boolean filleds) {
		super(xs, ys, sizes, filleds);
		setColor(Color.BLUE);
	}

	public Enemy(int xs, int ys, int widths, int heights, boolean filleds) {
		super(xs, ys, widths, heights, filleds);
		setColor(Color.BLUE);
		loadBasicImage(loc);
		bullet = new Bullet(200,200,5,5,true);
	}

	public void createRect()
	{
		rect = new Rectangle(x,y,width,height);
	}
	
	public double angle(int x,int y)
	{
		return Math.atan2(y,x);
	}
	
	public void update(Direction pulllorr, Direction pulluord, User user)
	{
		createRect();
		if(enabled)
		{
			if(pulllorr != Direction.none && pulluord != Direction.none)
			{
				if(!LevelPane.collisionWithFloor(rect))
				{
					if(pulllorr == Direction.left)
						x-=runspeed;
					else if(pulllorr == Direction.right)
						x+=runspeed;
					if(pulluord == Direction.up)
						y-=runspeed;
					else if(pulluord == Direction.down)
						y+=runspeed;
				}
				else
				{
					floor = LevelPane.returnfloor(rect);
					if(pulllorr == Direction.left )
						x-=runspeed;
					else if(pulllorr == Direction.right)
						x+=runspeed;
					if(pulluord == Direction.up && floor.getY() >= y)
						y-=runspeed;
					else if(pulluord == Direction.down && floor.getY() <= y)
						y+=runspeed;
				}
			}
			else
			{
				if(!LevelPane.collisionWithFloor(rect))
				{
					y+=gravity;
					if(LevelPane.collisionWithFloor(rect))
					{
						y-=gravity;
					}
				}
				else
				{
					floor = LevelPane.returnfloor(rect);
					urect = user.getRect();
					sight = new Rectangle(x-sightrange,y-32,(sightrange*2)+32,height*2);
					if(urect.intersects(sight))
					{
						if(urect.getX() < x && floor.getX() <= x)
						{
							x-=runspeed;
						}
						if(urect.getX() > x && floor.getX()+floor.getWidth() >= x+width)
						{
							x+=runspeed;
						}
						if(!bullet.isEnabled() && canfire && pulllorr == Direction.none && pulluord == Direction.none)
						{
							bullet.enable(x, y, angle(user.getX()-x,user.getY()-y));
							canfire = false;
							timer = new Timer();
							timer.schedule(new time(), 1000);
//							System.out.println(user.getX()+":"+user.getY());
//							System.out.println(x+":"+y);
						}
					}
					else if(floor.getY() <= y)
					{
						y+=gravity;
					}
					else if(floor.getY() >= y)
					{
						sight = new Rectangle(x-32,y,32*3,height);
						if(LevelPane.collisionWithSaw(sight))
						{
							if(lorr == Direction.right)
							{
								lorr = Direction.left;
							}
							else if(lorr == Direction.left)
							{
								lorr = Direction.right;
							}
						}
						if(floor.getX() >= x)
						{
							lorr = Direction.right;
						}
						else if(floor.getX()+floor.getWidth() <= x+width)
						{
							lorr = Direction.left;
						}
						else if(lorr == Direction.none)
						{
							lorr = Direction.left;
						}
						if(lorr == Direction.left)
							x-=movespeed;
						else if(lorr == Direction.right)
							x+=movespeed;
					}
					
				}
			}
			if(LevelPane.collisionWithSaw(rect))
			{
				Score.addScore(150);
				enabled = false;
			}
			else if(LevelPane.collisionWithSpike(rect))
			{
				Score.addScore(200);
				enabled = false;
			}
			
		}
		bullet.update(user);
		if(bullet.getHit())
		{
			chardead = true;
		}
	}
	
	public void reset()
	{
		bullet.disable();
		chardead = false;
		enabled = true;
		bullet.reset();
		x = rand.nextInt(((Parameters.width-32) - 32) + 1) + 32;
		y = Parameters.height-(250+32);
	}
	
	public boolean isCharDead()
	{
		return chardead;
	}
	
	public Bullet getBullet()
	{
		return bullet;
	}
	
	private class time extends TimerTask
	{
		public void run()
		{
			canfire = true;
		}
	}
}
