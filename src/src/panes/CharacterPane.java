package src.panes;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import src.Direction;
import src.UI.Panel;
import src.UI.Renderer2D;
import src.main.LD32_Main;
import src.main.Parameters;
import src.main.Score;
import src.objects.Enemy;
import src.objects.User;

public class CharacterPane extends Panel{

	static BufferedImage image;
	static BufferedImage bg;
	
	static File userf = new File("Rsc/Char.png");
	
	static User user;
	static Enemy enemy;
	
	static ArrayList<Enemy> enemies;
	
	static Direction pulllorr = Direction.none;
	static Direction pulluord = Direction.none;
	
	static Renderer2D renderer;
	
	static boolean pushing = false;
	static boolean pulling = false;
	
	Random rand = new Random();
	
	public CharacterPane(Dimension sizes) {
		super(sizes);
		initObjects();
	}
	
	public void initObjects()
	{
		enemies = new ArrayList<Enemy>();
		renderer = new Renderer2D(Parameters.width,Parameters.height,BufferedImage.TYPE_INT_ARGB);
		
		user = new User(200,Parameters.height-(16+32),32,32,true);
		user.loadBasicImage(userf);
		user.enable();
		
		enemy = new Enemy(rand.nextInt(((Parameters.width-64) - 64) + 1) + 64,Parameters.height-(250+32),32,32,true);
		enemy.enable();
		
		enemies.add(enemy);
		
		renderer.addObject(user);
		renderer.addObject(enemy);
		renderer.addObject(enemy.getBullet());
	}
	
	public void reset(boolean chardead)
	{
		pulllorr = Direction.none;
		pulluord = Direction.none;
		LD32_Main.reset(chardead);
		user.reset();
		for(int x =0;x<enemies.size();x++)
		{
			enemies.get(x).reset();
			enemies.get(x).enable();
			System.out.println("Enemy "+x+" :"+enemies.get(x).getX()+"/"+enemies.get(x).getY());
		}
		if(!user.isDead())
		{
			enemy = new Enemy(rand.nextInt(Parameters.width-128) + 32,Parameters.height-(250+32),32,32,true);
			enemy.enable();
			enemies.add(enemy);
			System.out.println(enemies.size());
			renderer.addObject(enemy);
			renderer.addObject(enemy.getBullet());
			System.out.println("Enemy "+(enemies.size()-1)+" :"+enemy.getX()+"/"+enemy.getY());
		}
	}
	
	public void update(Direction lorr, boolean pushings, boolean pullings)
	{
		pushing = pushings;
		pulling = pullings;
		user.update(lorr);
		int disabled=0;
		for(int x = 0;x<enemies.size();x++)
		{
			enemy = enemies.get(x);
			pushpull();
			enemies.get(x).update(pulllorr, pulllorr, user);
			if(!enemies.get(x).isEnabled())
			{
				disabled++;
			}
			if(enemies.get(x).isCharDead())
			{
				JOptionPane.showMessageDialog(null, "Score: "+Score.getScore());
				reset(true);
			}
		}
		if(disabled == enemies.size())
		{
			reset(false);
			disabled = 0;
		}
		if(user.isDead())
		{
			JOptionPane.showMessageDialog(null, "Score: "+Score.getScore());
			reset(true);
		}
	}
	
	public void pushpull()
	{
			int range = 100;
			int charx = user.getX();
			int chary = user.getY();
			int enemyx = enemy.getX();
			int enemyy = enemy.getY();
			if(charx - range < enemyx &&
			   charx + range > enemyx &&
			   chary - range < enemyy &&
			   chary + range > enemyy)
			{
				if(pulling)
				{
					if(charx > enemyx)
						pulllorr = Direction.right;
					else if(charx < enemyx)
						pulllorr = Direction.left;
					else
						pulllorr = Direction.none;
					
					if(chary > enemyy)
						pulluord = Direction.down;
					else if(chary < enemyy)
						pulluord = Direction.up;
					else
						pulluord = Direction.none;
				}
				else if(pushing)
				{
					if(charx > enemyx)
						pulllorr = Direction.left;
					else if(charx < enemyx)
						pulllorr = Direction.right;
					else
						pulllorr = Direction.none;
					
					if(chary > enemyy)
						pulluord = Direction.up;
					else if(chary < enemyy)
						pulluord = Direction.down;
					else
						pulluord = Direction.none;
				}
			}
			else
			{
				resetPushPull();
			}
	}
	
	public void resetPushPull()
	{
		pulllorr = Direction.none;
		pulluord = Direction.none;
	}
	
	public void charJump()
	{
		if(!user.isJumping())
			user.jumps();
	}
	
	public void render()
	{
		image = renderer.render();
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
}
