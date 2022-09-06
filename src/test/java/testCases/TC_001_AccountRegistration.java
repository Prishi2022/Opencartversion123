package testCases;
import org.testng.Assert;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.AccountRegistrationPage;  //register page & testcase is in the different packages, to access we need to import this, this is a user defined package.
import pageObjects.HomePage; // home page & testcase is in the different packages, to access we need to import this, this is a user defined package.
import testBase.BaseClass;

public class TC_001_AccountRegistration extends BaseClass // Inheritance concept, Test base class is a parent class of each testcase
{ 
	// create a object for HomePage object class and through that object will be able to access clickMyAccount(),clickRegister(), clickLogin () action methods
	// then also create object for AccountRegistrationPage
	@Test (groups={"regression","master"})
	public void test_account_registration()
	{	
		logger.info("Starting TC_001_AccountRegistration");
		
		// we can get exceptions here, like some element is not found properly, something is not able to interact 
		//so better to use exception handling here, keep inside the try and then write catch block
		//if any exception occur then catch block will execute, in this case test case will fail
		try       
		{
	    logger.info("Launching the application");  //mentioning like a adding a comment
	    //how we read the values from the properties file in the test case?
	    driver.get(rb.getString("appURL")); // instead driver.get("http://localhost/opencart/upload/");
	    // rb variable we have defined in the baseclass, appURL is mentioned in the config.properties
	    driver.manage().window().maximize();
		
		//Creating object for home page
		logger.info("Clicking on MyInfo Registration link");
		HomePage hp=new HomePage(driver);  //  Whenever created page object class's object have to pass the driver instance, because every page object class has constructor, constructor expects the driver
		hp.clickMyAccount();   // click on home page
		hp.clickRegister(); //click on register page
		//after we click on the above both then we will get register page
		
		logger.info("Providing customer details");
		//Creating object for Registration page
		//through these objects we will be able to invoke action methods, which are created in the accounts registration page
		AccountRegistrationPage regpage=new AccountRegistrationPage(driver); 
		
		regpage.setFirstName("John");
		logger.info("Provided customer first name");
		
		regpage.setLastName("Canedy");
		logger.info("Provided customer last name"); 
		
	/* unique email required, if not given this will work for the first time but not the next time, that's why have to make random or dynamic data
		how to generate random data -> In java we have to create a mothods, reusable method we have to create intially, 
		& that method will give the random string & once we get the radom string, we will add to the gmail to tha gmail format
		& then we will pass it to the application
		That's why not hardcoding the value here, that's why passing this email address radomly. */
	
		regpage.setEmail(randomestring()+"@gmail.com"); 
		logger.info("Provided customer email id");
		
		regpage.setTelephone("65656565");
		logger.info("Provided phone number");
		
		regpage.setPassword("abcxyz");
		logger.info("Provided customer password");
		
		regpage.setConfirmPassword("abcxyz");
		logger.info("Provided customer confirmed password");
		
		regpage.setPrivacyPolicy();
		
 		regpage.clickContinue();
 		logger.info("Click on continue button");
		
		//validating the confirmation message
 		// when we call getConfirmationMsg method from the Account registration which will return the string
String confmsg=regpage.getConfirmationMsg();

logger.info("Validation Started");
		if(confmsg.equals("Your Account Has Been Created!"))
		{
			logger.info("Registration test passed");
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Registration error, failed");
			captureScreen(driver, "test_accout_Registration"); //Capturing screenshot
			Assert.assertTrue(false);
		}
}
	catch(Exception e)
	{
		
		logger.fatal("blocker"); //fatal means completely blocker
		Assert.fail();
	}
	}
}
 
