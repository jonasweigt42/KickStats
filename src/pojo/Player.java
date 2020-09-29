package pojo;

public class Player
{

	private String name;
	private int marktwert;

	public Player(String name, int marktwert)
	{
		super();
		this.name = name;
		this.marktwert = marktwert;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getMarktwert()
	{
		return marktwert;
	}

	public void setMarktwert(int marktwert)
	{
		this.marktwert = marktwert;
	}
}
