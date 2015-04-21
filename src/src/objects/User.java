package src.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import src.Direction;
import src.main.Parameters;
import src.panes.LevelPane;

public class User extends TexturedObject2D
{
	
	int movespeed = 4;
	int jumpspeed = 4;
	int gravity = 4;
	int jumpy;
	
	Rectangle rect;
	
	Floor floor;
	
	boolean jumping = false;
	boolean jumped = false;
	boolean canjump = true;
	boolean dead = false;
	boolean up;
	boolean down;
	
	public User(int xs, int ys, Dimension sizes, boolean filleds) {
		super(xs, ys, sizes, filleds);
		setColor(Color.RED);
	}

	public User(int xs, int ys, int width, int height, boolean filleds) {
		super(xs, ys, width, height, filleds);
		setColor(Color.RED);
	}

	public void createRect()
	{
		rect = new Rectangle(x,y,width,height);
	}
	
	public void update(Direction lorr)
	{
		createRect();
		if(!isXCollision())
		{
			if(lorr == Direction.left)
				x-=movespeed;
			else if(lorr == Direction.right)
				x+=movespeed;
		}
		else
		{
			if(lorr == Direction.right && x < 0)
				x+=movespeed;
			else if(lorr == Direction.left && x+width>Parameters.width)
				x-=movespeed;
		}
		jump();
		if(LevelPane.collisionWithSaw(rect) || LevelPane.collisionWithSpike(rect))
		{
			dead = true;
		}
	}
	
	private void jump()
	{
		if(jumping && canjump)
		{
				if(!jumped)
				{
					jumpy = y;
					jumped = true;
					up = true;
				}
				if(y < jumpy - 100)
				{
					jumped = false;
					jumping = false;
					canjump = false;
				}
				
				if(up)
				{
					if(LevelPane.collisionWithFloor(rect))
					{
						floor = LevelPane.returnfloor(rect);
						if(floor.getY() <= y)
						{
							jumped = false;
							jumping = false; 
							canjump = false;
						}
					}
					y -= jumpspeed;
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
				canjump = true;
				floor = LevelPane.returnfloor(rect);
				if(floor.getY() <= y)
				{
					y+=gravity;
				}
			}
		}
		
	}
	
	public void reset()
	{
		x = 200;
		y = Parameters.height-(16+32);
		dead = false;
	}
	
	public void jumps()
	{
		jumping = true;
		jumped = false;
	}
	
	public boolean isDead()
	{
		return dead;
	}
	
	public Rectangle getRect()
	{
		return rect;
	}
	
	public boolean isJumping()
	{
		return jumping;
	}
	
	public boolean isXCollision()
	{
		if((x+width)<=Parameters.width && x >= 0)
			return false;
		else
			return true;
	}
	
	public boolean isYCollision()
	{
		if(y+height<=Parameters.height && y >= 0)
			return false;
		else
			return true;
	}
}
