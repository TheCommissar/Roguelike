package hero;

public class Skill 
{
	protected int level;
	protected int experience;
	
	public Skill()
	{
		level = 0;
		experience = 0;
	}
	
	public int expFor(int lvl)
	{
		return ((1 + lvl) * (1 + lvl)) + 3;
	}
	
	protected void addExp(int exp)
	{
		if (level == 10)
			return;
		
		if (exp + experience >= expFor(level + 1))
			level++;
		
		experience = (experience + exp) % expFor(level + 1);
	}
	
	protected int getLevel()
	{
		return level; 
	}
	
	public String getName()
	{
		return "lol";
	}
}
