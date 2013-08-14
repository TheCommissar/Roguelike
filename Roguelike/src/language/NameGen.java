package language;

public class NameGen
{
	protected static String[] townPrefix = 
		{	"kings",
			"queens",
			"new",
			"liver",
			"ips",
			"scar",
			"war",
			"nor",
			"suf",
			"christ",
			"shef",
			"middles",
			"south",
			"man",
			"bed",
			"wat",
			"wor",
			"shrews",
			"wald"	};
	
	protected static String[] townSuffix = 
		{
			"ex",
			"wich",
			"ing",
			"wick", 
			"ton", 
			"castle", 
			"shire", 
			"borough", 
			"ham", 
			"keep", 
			"bury", 
			"forth", 
			"ford", 
			"field",
			"pool",
			"folk",
			"cester",
			"try",
			"by",
			"port",
			"bridge",
			"mouth"	};
	
	public static String getTownName()
	{
		String s = townPrefix[(int)(Math.random() * townPrefix.length)] + townSuffix[(int)(Math.random() * townSuffix.length)];
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
}