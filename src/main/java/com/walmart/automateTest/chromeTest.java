package com.walmart.automateTest;

import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;


public class chromeTest {
	
	WebDriver driver;
	JavascriptExecutor jse;
	
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		driver = new ChromeDriver();
		driver.get("http://www.walmart.com/");
		Dimension d = new Dimension(1200,800);
		//Resize the current window to the given dimension
		driver.manage().window().setSize(d);
		jse = (JavascriptExecutor) driver;
	}//end setUp
	
	
	@Test
	public void testSignIn() throws Exception {
		//find sign in button
		WebElement signInBtn = driver.findElement(By.xpath("//*[@id=\"top\"]/div[3]/div/div/div/div/div[4]/div/div[1]/div[1]/p/span[2]/a"));
		signInBtn.click();
		sleep(5000);
		//enter username and password, then login.
		WebElement userField = driver.findElement(By.xpath("//input[@id=\"login-username\"]"));
		WebElement passField = driver.findElement(By.xpath("//input[@id=\"login-password\"]"));
		WebElement logInBtn  = driver.findElement(By.xpath("/html/body/div[1]/section/section[4]/div/div/div/div/div/div/div/form/div/button"));
		
		
		jse.executeScript("document.getElementById('login-username').focus();");
		userField.sendKeys("ann827827@gmail.com");
		
		jse.executeScript("document.getElementById('login-password').focus();");
		passField.sendKeys("1987827axr");
		logInBtn.click();
		
		sleep(5000);
		
		WebElement welcomeHeader = driver.findElement(By.xpath("/html/body/div[1]/section/section[4]/div/div/div/div/div[2]/div/h1"));
		assertEquals("Welcome to your Walmart account!",welcomeHeader.getText());
		
		//go back to homePage
		goToHomePage();
		
		//test shopping cart
		testShoppingCart();  
		
	}//end testSignIn
	
	public void testShoppingCart() throws Exception {
		validateItems();
		
	}//end testShoppingCart
	
	
	private void validateItems() {
		
		for (int i = 0; i < 5; i++) {
		
			WebElement searchField = driver.findElement(By.xpath("//*[@id=\"top\"]/div[3]/div/div/div/div/div[3]/form/div/div[2]/span/input"));
			switch(i) {
				case 0:
					searchField.sendKeys("tv");
					break;
				case 1:
					searchField.sendKeys("socks");
					break;
				case 2:
					searchField.sendKeys("dvd");
					break;
				case 3:
					searchField.sendKeys("toys");
					break;
				case 4:
					searchField.sendKeys("iphone");
					break;
			}//end switch
			
			WebElement searchBtn   = driver.findElement(By.xpath("//*[@id=\"top\"]/div[3]/div/div/div/div/div[3]/form/div/div[3]/button"));
			searchBtn.click();
			sleep(5000);
		
			WebElement oneItem = null;
			if (i == 2) {
				oneItem = driver.findElement(By.xpath("//*[@id=\"tile-container\"]/div[2]/div/div/h4/a"));
			}else if (i == 3) {
				oneItem = driver.findElement(By.xpath("//*[@id=\"sponsored-container-middle-2\"]/div/div/div[2]/ol/div/div/li[2]/div/div[2]/a/span/span"));
			}else {
				oneItem = driver.findElement(By.xpath("//*[@id=\"tile-container\"]/div[1]/div/div/h4/a"));
			}
			String oneItemTitle = oneItem.getText();
			oneItem.click();
			sleep(5000);
		
			WebElement addToCartOneItem = driver.findElement(By.xpath("//*[@id=\"WMItemAddToCartBtn\"]"));
			addToCartOneItem.click();
			sleep(5000);
		
			WebElement viewCartOneItem = driver.findElement(By.xpath("//*[@id=\"PACViewCartBtn\"]"));
			viewCartOneItem.click();
			sleep(5000);
		
			//test quantity, test title of the product.
			WebElement quantityOneTV = driver.findElement(By.xpath("//*[@id=\"spa-layout\"]/div/div/div[1]/div/h3/span"));
			assertEquals("1 item", quantityOneTV.getText());
		
			WebElement oneCartItem = driver.findElement(By.xpath("//*[@id=\"CartItemInfo\"]/span"));
			//click one TVCartItem
			oneCartItem.click();
			sleep(5000);
		
			//validate item name
			WebElement oneItemTitle1 = driver.findElement(By.xpath("/html/body/div[1]/section/section[4]/div/div[2]/div[1]/div[3]/div/h1/span"));
			assertEquals(oneItemTitle, oneItemTitle1.getText());
		
			//go back to shopping cart
			WebElement oneTVShoppingCart = driver.findElement(By.xpath("//*[@id=\"top\"]/div[3]/div/div/div/div/div[4]/div/div[2]/div/a"));
			oneTVShoppingCart.click();
			sleep(5000);
		
			//remove one item from shopping cart
			WebElement shoppingCartRemove = driver.findElement(By.xpath("//*[@id=\"CartRemItemBtn\"]"));
			shoppingCartRemove.click();
			sleep(5000);     
		
			driver.get("https://www.walmart.com/account/");  
			sleep(4000);   
			//goToHomePage();
			
		}
	}//end validate
	
	@After
	public void tearDown() throws Exception {
		sleep(5000);
		driver.quit();
	}
	
	private void sleep(int x) {
		try {
			Thread.sleep(x);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void goToHomePage(){
		//go back to homePage
		WebElement logo = driver.findElement(By.xpath("//*[@id=\"top\"]/div[3]/div/div/div/div/div[2]/a[1]"));
		logo.click();
		sleep(2000);
	}
	
	
}//end class ChromeTest





