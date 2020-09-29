package dataCollector;

import java.util.List;

import pojo.BuliPlayer;

public class KickbaseData
{

	private List<BuliPlayer> ownPlayers;
	private int kontostand;
	
	public KickbaseData(List<BuliPlayer> list, int kontostand)
	{
		this.kontostand = kontostand;
		this.ownPlayers = list;
	}
	
	public int getKaderwert()
	{
		int result = 0;
		for(BuliPlayer player : ownPlayers)
		{
			result += player.getMarktwert();
		}
		return result;
	}
	
	public int getKaderwertMitKontostand()
	{
		return getKaderwert() + kontostand;
	}
	
	public List<BuliPlayer> getOwnPlayers()
	{
		return ownPlayers;
	}
	
	public int getKontostand()
	{
		return kontostand;
	}
}
