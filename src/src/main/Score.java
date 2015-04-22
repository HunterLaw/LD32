package src.main;

import javax.swing.JFrame;

public class Score 
{
	static int score = 0;
	
	public void update(JFrame frame,String title)
	{
		frame.setTitle(title+"                   Score:"+score);
	}
	
	public static void reset()
	{
		score = 0;
	}
	
	public static void addScore(int add)
	{
		score+=add;
	}
	
	public static void subScore(int diff)
	{
		score-=diff;
	}
	
	public static int getScore()
	{
		return score;
	}
}
