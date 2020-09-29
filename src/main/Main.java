package main;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import dataCollector.KickbaseData;
import dataCollector.SeleniumDataCollector;

public class Main
{


	public static void main(String[] args) throws InterruptedException
	{
		JPasswordField passwordField = new JPasswordField(20);
		String mailAdress = JOptionPane.showInputDialog("bitte Kickbase Mailadresse eingeben");
		JOptionPane.showMessageDialog(null, passwordField, "bitte Kickbase Passwort eingeben",
				JOptionPane.INFORMATION_MESSAGE);
		String password = String.valueOf(passwordField.getPassword());

		if (password == null || password.isEmpty())
		{
			return;
		}
		
		SeleniumDataCollector collector = new SeleniumDataCollector();
		KickbaseData data = collector.collectDataViaSelenium(mailAdress, password);
		System.out.println("Kaderwert: " + data.getKaderwert());
		System.out.println("Kontostand: " + data.getKontostand());
		System.out.println("Kaderwert mit Kontostand: " + data.getKaderwertMitKontostand());
	}

}
