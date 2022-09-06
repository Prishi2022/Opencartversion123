package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage {
WebDriver driver; //webdriver instance
	
	public MyAccountPage(WebDriver driver) //constructor created
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//div[@class='list-group']//a[text()='Logout']")  //webelement
	WebElement lnkLogout;
	
	//action method
	public void clickLogout()
	{
		lnkLogout.click();
	}
		
}
