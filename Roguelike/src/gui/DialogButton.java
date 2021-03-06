package gui;

import game.Game;
import graphics.Sprite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class DialogButton extends FrameInterface
{
	private String text;
	public Point pos;
	public boolean clicked;
	
	public DialogButton(int _width, int _height, Point _pos, String frameName, String _text)
	{
		super(_width, _height, frameName);
		pos = _pos;
		clicked = false;
		createText(_text, Color.BLACK);
		text = _text;
	}
	
	public DialogButton(int _width, int _height, Point _pos, String frameName, String _text, Color c)
	{
		super(_width, _height, frameName);
		pos = _pos;
		clicked = false;
		createText(_text, c);
		text = _text;
	}
	
	private void createText(String txt, Color c)
	{
		BufferedImage text = new BufferedImage(width - 20, 16, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D t = (Graphics2D) text.getGraphics();
		t.setColor(c);
		t.setFont(Game.FONT_TEXT);
		t.drawString(txt, 0, t.getFontMetrics().getAscent());
		t.dispose();
		
		Sprite textSprite = new Sprite(text);
		
		int x = (width+side.getWidth())/2 - t.getFontMetrics().stringWidth(txt)/2;
		int y = height/2 - t.getFontMetrics().getAscent()/2;
		
		textSprite.move(x, y);
		
		picArray.add(textSprite);
	}

	public void draw(Graphics2D g, Point c)
	{
		super.draw(g, c);
	}
	
	public void mousePressHandle(MouseEvent e)
	{
		
	}
	
	public boolean mouseReleaseHandle(MouseEvent e, Point parentPos)
	{
		if ((pos.x + parentPos.x <= Game.mousePos.x) && (Game.mousePos.x <= parentPos.x + pos.x + width) && (pos.y + parentPos.y <= Game.mousePos.y) && (Game.mousePos.y <= parentPos.y + pos.y + height))
		{
			clicked = true;
			System.out.println(text + " button pressed.");
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String name()
	{
		return text;
	}
}
