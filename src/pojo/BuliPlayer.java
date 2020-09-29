package pojo;

import constants.Position;

public class BuliPlayer
{

	private String firstName;
	private String lastName;
	private int marktwert;
	private Position position;

	public BuliPlayer(String firstname, String lastName, int marktwert, Position position)
	{
		this.firstName = firstname;
		this.lastName = lastName;
		this.marktwert = marktwert;
		this.position = position;
	}
	
	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public int getMarktwert()
	{
		return marktwert;
	}

	public Position getPosition()
	{
		return position;
	}
}
