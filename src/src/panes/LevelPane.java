package src.panes;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import src.UI.Panel;
import src.UI.Renderer2D;
import src.main.Parameters;
import src.objects.Floor;
import src.objects.Saw;
import src.objects.Spike;

public class LevelPane extends Panel{

	static BufferedImage image;
	static BufferedImage bg;
	
	static Renderer2D renderer;
	
	static Floor base;
	static Floor fl_1;
	static Floor fl_12;
	static Floor fl_2;
	static Saw saw1;
	static Saw saw2;
	static Saw saw3;
	static Spike spike1;
	static Spike spike2;
	static Spike spike3;
	static Spike spike4;
		
	static ArrayList<Floor> floors;
	static ArrayList<Saw> saws;
	static ArrayList<Spike> spikes;
	
	final File box = new File("Rsc/Box.png");
	final File bgs = new File("Rsc/Bg.png");
	
	public LevelPane(Dimension sizes) {
		super(sizes);
		init();
	}
	
	public void init()
	{
		renderer = new Renderer2D(Parameters.width, Parameters.height, BufferedImage.TYPE_INT_RGB);
		
		floors = new ArrayList<Floor>();
		saws = new ArrayList<Saw>();
		spikes = new ArrayList<Spike>();
		
		try{
		bg = ImageIO.read(bgs);
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		base = new Floor(0, Parameters.height-16, Parameters.width, 16, false);
		base.enable();
		base.setRepeatingImage(box);
		
		fl_1 = new Floor(0, Parameters.height-(16+75), 16*20, 16, false);
		fl_1.enable();
		fl_1.setRepeatingImage(box);
		
		fl_12 = new Floor(Parameters.width-(16*20), Parameters.height-(16+75), 16*20, 16, false);
		fl_12.enable();
		fl_12.setRepeatingImage(box);
		
		fl_2 = new Floor(Parameters.width-(16*41), Parameters.height-(16+150), 16*20, 16, false);
		fl_2.enable();
		fl_2.setRepeatingImage(box);
		
		saw1 = new Saw(Parameters.width-16,Parameters.height-(64+16+75),16,64,false);
		saw1.enable();
		
		saw2 = new Saw(0,Parameters.height-(64+16+75),16,64,false);
		saw2.setImageAngle(180);
		saw2.enable();
		
		spike1 = new Spike((Parameters.width/2)-32,Parameters.height-32,16,16,false);
		spike1.enable();
		
		spike2 = new Spike((Parameters.width/2)-16,Parameters.height-32,16,16,false);
		spike2.enable();
		
		spike3 = new Spike((Parameters.width/2),Parameters.height-32,16,16,false);
		spike3.enable();
		
		spike4 = new Spike((Parameters.width/2)+16,Parameters.height-32,16,16,false);
		spike4.enable();
		
		floors.add(base);
		floors.add(fl_1);
		floors.add(fl_12);
		floors.add(fl_2);
		
		saws.add(saw1);
		saws.add(saw2);
		
		spikes.add(spike1);
		spikes.add(spike2);
		spikes.add(spike3);
		spikes.add(spike4);
		
		renderer.setBgObject(bg,Parameters.width,Parameters.height);
		renderer.addObject(base);
		renderer.addObject(fl_1);
		renderer.addObject(fl_12);
		renderer.addObject(fl_2);
		renderer.addObject(saw1);
		renderer.addObject(saw2);
		renderer.addObject(spike1);
		renderer.addObject(spike2);
		renderer.addObject(spike3);
		renderer.addObject(spike4);
	}
	
	public static boolean collisionWithFloor(Rectangle rect)
	{
		Rectangle fl; 
		for(int x =0;x< floors.size();x++)
		{
			fl = new Rectangle(floors.get(x).getX(),floors.get(x).getY(),floors.get(x).getWidth(),floors.get(x).getHeight());
			if(rect.intersects(fl))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean collisionWithSaw(Rectangle rect)
	{
		Rectangle saw;
		for(int x=0;x < saws.size();x++)
		{
			saw = saws.get(x).getRect();
			if(rect.intersects(saw))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean collisionWithSpike(Rectangle rect)
	{
		Rectangle spike;
		for(int x=0;x < spikes.size();x++)
		{
			spike = spikes.get(x).getRect();
			if(rect.intersects(spike))
			{
				return true;
			}
		}
		return false;
	}
	
	public static Floor returnfloor(Rectangle rect)
	{
		Rectangle fl; 
		for(int x =0;x< floors.size();x++)
		{
			fl = new Rectangle(floors.get(x).getX(),floors.get(x).getY(),floors.get(x).getWidth(),floors.get(x).getHeight());
			if(rect.intersects(fl))
			{
				return floors.get(x);
			}
		}
		return null;
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
