package dataCollector;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import constants.Position;
import pojo.BuliPlayer;

public class SeleniumDataCollector
{
	private WebDriver driver;
	
	public KickbaseData collectDataViaSelenium(String mailAddress, String passWord) throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver", "chromedriver");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);

		driver.get("https://kickbase.com/anmelden");

		List<WebElement> inputElements = driver.findElements(By.tagName("input"));

		fillMailAddress(inputElements, mailAddress);
		fillPassword(inputElements, passWord);
		clickLogin();
		Thread.sleep(2000);
		clickSidebarTransfermarkt();
		Thread.sleep(2000);
		clickKader();
		Thread.sleep(2000);
		KickbaseData result = new KickbaseData(collectPlayers(), findKontostand());
		
		driver.close();
		return result;
	}
	
	private int findKontostand()
	{
		WebElement element = driver.findElement(By.className("leagueBudget")).findElement(By.className("content"));
		String result = element.getText();
		return Integer.valueOf(result.substring(2).replace(".", ""));
	}
	
	private void clickKader()
	{
		List<WebElement> tabs = driver.findElements(By.cssSelector(".tabs > li > a"));
		for (WebElement element : tabs)
		{
			if (element.getText().equals("Kader"))
			{
				element.click();
			}
		}
	}
	
	private List<BuliPlayer> collectPlayers()
	{
		List<BuliPlayer> playerList = new ArrayList<>();
		List<WebElement> playerRows = driver.findElements(By.className("playerRow"));
		for (WebElement playerRow : playerRows)
		{
			String firstName = playerRow.findElement(By.className("firstName")).getText();
			String lastName = playerRow.findElement(By.className("lastName")).getText();
			String marktwert = playerRow.findElement(By.cssSelector(".price > strong")).getText();
			Position position = findPositionByPlayerRow(playerRow);
			
			playerList.add(new BuliPlayer(firstName, lastName, Integer.valueOf(marktwert.substring(2).replace(".", "")), position));
			System.out.println(firstName + " " + lastName + ", Marktwert: " + marktwert + ", Position: " + position.name());
		}
		System.out.println(playerList);
		return playerList;
	}
	
	private Position findPositionByPlayerRow(WebElement playerRow)
	{
		List<WebElement> elements = playerRow.findElements(By.cssSelector(".playerBadges > div > span"));
		for(WebElement element : elements)
		{
			for(Position pos : Position.values())
			{
				if(element.getText().equals(pos.name()))
				{
					return pos;
				}
			}
		}
		return null;
	}
	
	private void clickSidebarTransfermarkt()
	{
		List<WebElement> sideBarElements1 = driver.findElements(By.cssSelector(".sidebarNav > li"));
		for(WebElement element1 : sideBarElements1)
		{
			List<WebElement> sideBarElements = element1.findElements(By.className("activeImage"));
			for (WebElement element : sideBarElements)
			{
				if (element.getAttribute("style").contains("management_active.svg"))
				{
					element1.click();
				}
			}
		}
	}
	
	private void clickLogin()
	{
		WebElement login = driver.findElement(By.className("btn-login"));
		login.click();
	}

	private void fillPassword(List<WebElement> elements, String password)
	{
		for (WebElement element : elements)
		{
			if (element.getAttribute("name").equals("password"))
			{
				element.click();
				element.sendKeys(password);
			}
		}
	}

	private void fillMailAddress(List<WebElement> elements, String mailAddress)
	{
		for (WebElement element : elements)
		{
			if (element.getAttribute("name").equals("email"))
			{
				element.click();
				element.sendKeys(mailAddress);
			}
		}
	}

}
