package main;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import constants.Position;
import pojo.BuliPlayer;

public class Main
{

	private static WebDriver driver;
	private static String mailAdress;
	private static String password;
	private static List<BuliPlayer> playerList = new ArrayList<>();

	public static void main(String[] args) throws InterruptedException
	{
		JPasswordField passwordField = new JPasswordField(20);
		mailAdress = JOptionPane.showInputDialog("bitte Kickbase Mailadresse eingeben");
		JOptionPane.showMessageDialog(null, passwordField, "bitte Kickbase Passwort eingeben", JOptionPane.OK_OPTION);
		password = String.valueOf(passwordField.getPassword());
		
		System.setProperty("webdriver.chrome.driver", "chromedriver");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);

		driver.get("https://kickbase.com/anmelden");

		List<WebElement> inputElements = driver.findElements(By.tagName("input"));

		fillUsername(inputElements);
		fillPassword(inputElements);
		clickLogin();
		Thread.sleep(2000);
		clickSidebarTransfermarkt();
		Thread.sleep(2000);
		clickKader();
		Thread.sleep(2000);
		collectPlayers();
		
		System.out.println(playerList);
		
		driver.close();
	}

	private static void clickKader()
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
	
	private static void collectPlayers()
	{
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
	}
	
	private static Position findPositionByPlayerRow(WebElement playerRow)
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

	private static void clickSidebarTransfermarkt()
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

	private static void clickLogin()
	{
		WebElement login = driver.findElement(By.className("btn-login"));
		login.click();
	}

	private static void fillPassword(List<WebElement> elements)
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

	private static void fillUsername(List<WebElement> elements)
	{
		for (WebElement element : elements)
		{
			if (element.getAttribute("name").equals("email"))
			{
				element.click();
				element.sendKeys(mailAdress);
			}
		}
	}

}
