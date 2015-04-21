package src.main;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import src.Direction;
import src.UI.FPS;
import src.UI.Panel;
import src.UI.Renderer2D;
import src.UI.Window;
import src.objects.Enemy;
import src.objects.Floor;
import src.objects.User;
import src.panes.CharacterPane;
import src.panes.LevelPane;

public class LD32_Main implements Runnable, Parameters, KeyListener
{
	static Window window;
	static Panel back;
	static CharacterPane panel;
	static LevelPane panel2;
	static FPS fps = new FPS(60);
	static Renderer2D renderer;
	static BufferedImage image;
	
	static User chars;
	static Enemy enemy;
	static Score score;
	
	static String title = "Unconventional Odds";
	
	static Direction lorr = Direction.none;
	static Direction uord = Direction.none;
	
	static boolean running = false;
	static boolean pulling = false;
	static boolean pushing = false;
	
	public LD32_Main()
	{
		
	}
	
	public static void main(String[] args)
	{
		init();
	}
	
	public static void init()
	{
		initUI();
		initListeners();
		start();
	}
	
	public static void initUI()
	{
		panel = new CharacterPane(new Dimension(width,height));
		panel2 = new LevelPane(new Dimension(width,height));
		back = new Panel(new Dimension(width,height));
		window = new Window(back,title);
		score = new Score();
	}
	
	public static void initListeners()
	{
		window.addKeyListener(new LD32_Main());
	}
	
	public static void start()
	{
		running = true;
		panel.setRunMethod(new LD32_Main());
	}
	
	
	public void update()
	{
		panel.update(lorr, pushing, pulling);
		score.update(window, title);
	}
	
	public void render()
	{
		image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		panel.render();
		panel2.render();
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.drawImage(panel2.getImage(),0,0,null);
		g.drawImage(panel.getImage(), 0, 0, null);
		g.dispose();
	}

	public void draw()
	{
		Graphics2D g = (Graphics2D) back.getGraphics();
		if(g != null)
		{
			g.drawImage(image,0, 0, null);
			g.dispose();
		}
	}
	
	public void run()
	{
		while(running)
		{
			fps.beginTime();
			update();
			render();
			draw();
			fps.endTime();
			try{ Thread.sleep(fps.getWaitTime());} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		int key = arg0.getKeyCode();
		switch(key)
		{
		case KeyEvent.VK_LEFT:
			lorr = Direction.left;
			break;
		
		case KeyEvent.VK_RIGHT:
			lorr = Direction.right;
			break;
			
		case KeyEvent.VK_SPACE:
			panel.charJump();
			break;
		case KeyEvent.VK_Q:
			pushing = false;
			pulling = true;
			break;
		case KeyEvent.VK_E:
			pulling = false;
			pushing = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int key = arg0.getKeyCode();
		switch(key)
		{
		case KeyEvent.VK_LEFT:
			lorr = Direction.none;
			break;
		
		case KeyEvent.VK_RIGHT:
			lorr = Direction.none;
			break;
			
		case KeyEvent.VK_UP:
			uord = Direction.none;
			break;
			
		case KeyEvent.VK_DOWN:
			uord = Direction.none;
			break;
			
		case KeyEvent.VK_Q:
			panel.resetPushPull();
			pulling = false;
			break;
			
		case KeyEvent.VK_E:
			panel.resetPushPull();
			pushing = false;
			break;
			
		case KeyEvent.VK_R:
			panel.reset();
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}
