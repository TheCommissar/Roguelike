package map;

import java.awt.Graphics2D;

import graphics.Sprite;

enum OverworldEntityState { Default, Traveling };

public class OverworldEntity
// e.g. the player, wandering bandits
{
	protected Sprite pic;
	protected Node loc;
	protected OverworldEntityState state;
	
	public OverworldEntity(Sprite _pic)
	{
		pic = _pic;
		state = OverworldEntityState.Default;
	}
	
	public void render(Graphics2D g)
	{
		pic.setPosition(loc.getLoc().x, loc.getLoc().y);
		pic.draw(g);
	}
	
	public void setNode(Node n)
	{
		loc = n;
	}
	
	public Node getNode()
	{
		return loc;
	}
}
