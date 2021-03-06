package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DialogBox extends MessageBox
{
	protected ArrayList<DialogButton> choices;
	protected String exitChoice;
	protected int exitIndex;
	
	protected DialogBox(int _width, int _height, String frameName, String _text, Point _pos)
	{
		super(_width, _height, frameName, _text, _pos);
	}
	
	public DialogBox(int _width, int _height, String frameName, String choiceFrameName, String _text, Point _pos, String[] options)
	{
		super(_width, _height, frameName, _text, _pos);
		
		choices = new ArrayList<DialogButton>();
		
		for (int q = 0; q < options.length; q++)
		{
			choices.add(new DialogButton(width - 20, 30, new Point(15, height - 40*(q+1)), choiceFrameName, options[q]));
		}
		
		exitChoice = "";
		exitIndex = -1;
	}
	
	public DialogBox(int _width, int _height, String frameName, String choiceFrameName, String _text, Point _pos, String[] options, Color c)
	{
		super(_width, _height, frameName, _text, _pos, c);
		
		choices = new ArrayList<DialogButton>();
		
		for (int q = 0; q < options.length; q++)
		{
			choices.add(new DialogButton(width - 20, 30, new Point(15, height - 40*(q+1)), choiceFrameName, options[q], c));
		}
		
		exitChoice = "";
		exitIndex = -1;
	}
	
	public void draw(Graphics2D g)
	{
		super.draw(g);
		
		int z = 0;
		
		for (DialogButton q : choices)
		{
			q.draw(g, new Point(pos.x + width/2 - (q.width - q.side.getWidth())/2, pos.y + height - end.getHeight()*2 - (q.end.getHeight() + q.height) * z));
			z++;
		}
	}
	
	public void update()
	{
		for (DialogButton q : choices)
		{
			if (q.clicked)
			{
				exitChoice = q.name();
				exitIndex = choices.indexOf(q);
				q.clicked = false;
				close = true;
				open = false;
			}
		}
	}
	
	public String getExitChoice()
	{
		return exitChoice;
	}
	
	public int getExitIndex()
	{
		return exitIndex;
	}
	
	public void reset()
	{
		exitChoice = "";
		close = false;
		open = false;
	}
	
	
	public boolean keyReleaseHandle(KeyEvent e) 
	{
		// Do nothing
		return true;
	}
	
	public boolean mouseReleaseHandle(MouseEvent e)
	{
		pressed = false;
		
		if (open)
		{
			for (DialogButton o : choices)
			{
				if (o.mouseReleaseHandle(e, pos))
				{
					return true;
				}
			}
			
			if (open && !close && (e.getX() >= pos.x && e.getX() <= pos.x + width && e.getY() >= pos.y && e.getY() <= pos.y + height))
				return true;
			
			return false;
		}
		
		
		
		return false;
	}
}

