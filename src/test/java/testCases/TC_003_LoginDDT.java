package testCases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.XLUtility;

public class TC_003_LoginDDT extends BaseClass{
	
	
	@Test(dataProvider="LoginData",groups={"ddt"})
	public void test_LoginDDT(String email,String pwd,String exp) //because data provider is passing 3 parameters, exp is expected result
	{
		logger.info(" Starting TC_003_LoginDDT ");
		
		try
		{
			driver.get(rb.getString("appURL")); //launch application URL, getting from config.properties file
			logger.info("Home Page Displayed ");
			
			driver.manage().window().maximize();
			
			//from homepage click on My account and Login
			HomePage hp=new HomePage(driver);
			hp.clickMyAccount();
			logger.info("Clicked on My Account ");
			hp.clickLogin();
			logger.info("Clicked on Login ");
			
			//then gets the login login page
			LoginPage lp=new LoginPage(driver);
			
			//sending the parameter of email & pwd then clicking on the login 
			lp.setEmail(email);
			logger.info("Provided Email ");
			
			lp.setPassword(pwd);
			logger.info("Provided Password ");
			
			lp.clickLogin();
			logger.info("Clicked on Login");
			
			//validation part, 
			boolean targetpage=lp.isMyAccountPageExists(); //isMyAccountPageExists() method is calling from page object class
			
//if we pass the valid data, login should be successful, we will get the target page(My account), then test will be passed.
//if we pass the invalid data, then not getting the target page, then also a pass case.
// when we pass invalid data then getting the target page, then fail testcase
			
//Testcase 1: Valid data and displayed target page - pass
//Testcase 2: Valid data and not displayed target page - Fail
			if(exp.equals("Valid")) //if expected result is valid, 
			{
				if(targetpage==true) 
				{
					logger.info("Login Success "); //if login is successful then we have to logout because we have to take another input
// But before that we have to go to my account page object class object 
//then from this we have to click on clicklogout method and this will logout from the application then make test is pass Assert.assertTrue(true);
					
					MyAccountPage myaccpage=new MyAccountPage(driver);
					myaccpage.clickLogout();
					Assert.assertTrue(true);
				}
//if data is valid but not getting target page then else will execute
				else
				{
					logger.error("Login Failed ");
					Assert.assertTrue(false);
				}
			}
			
			//if we pass the invalid data, then not getting the target page, then also a pass case.
			// when we pass invalid data then getting the target page, then fail testcase
			if(exp.equals("Invalid"))
			{
				if(targetpage==true)
				{
					MyAccountPage myaccpage=new MyAccountPage(driver);
					myaccpage.clickLogout();
					Assert.assertTrue(false);
				}
				else
				{		
					logger.error("Login Failed ");
					Assert.assertTrue(true);
				}
			}
			
			
		}catch(Exception e)
		{
			logger.fatal("Login Failed ");
			Assert.fail();
		}
		
		logger.info(" Finished TC_003_LoginDDT ");
		
	}
	
	
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		String path=".\\testData\\Opencart_LoginData.xlsx"; //specified the location of the excel sheet, .// is representing current project location
		
		XLUtility xlutil=new XLUtility(path); //created object for XLUtility class, XLUtility contains constructor, in that we have to pass the location
		//in above line we are passing the path of excel sheet
		
		//Then below counting number of rows and columns from the excel sheet, by calling getRowCount and getCellCount from the utilities class 
		//getRowcount and getcellcount have called and then finding out total rows and total columns
		//now read entire data from the excel and then keep that in the 2 dimensional array
		int totalrows=xlutil.getRowCount("Sheet1");	
		int totalcols=xlutil.getCellCount("Sheet1",1);
				
		//have read the data from the excel sheet and put the same data in the login data array
		String logindata[][]=new String[totalrows][totalcols];
		
		for(int i=1;i<=totalrows;i++)  //1,  i is representing row number
		{		
			//inner for loop will represent the columns, inner for loop will repeat multiple times
			//once it reads all the columns from the first row, and login data will be stored
			//and once inner for loop is completed then it will go to outter for loop, again it will increment the row
			// and it will get the all columns from the 2nd row and store into the 2 dimensional array.
			//once the loop is completed, all excel data will be available in the login data 2 dimensional array
			for(int j=0;j<totalcols;j++)  //0  j is representing the column number
			{
				logindata[i-1][j]= xlutil.getCellData("Sheet1",i, j);  //1,0  (sheet name, row number, column number), this value is stored in 2 dimensional array
			// eliminating the 1st row which has column names, that's in array we say -1 that's given i-1
				// because in excel sheet if we take 1st row in the array we have to store in the 0 row
				// and if in the excel sheet we take 2nd row  in the array we have to store in the 1st row
				// and if in the excel sheet we take 3rd row  in the array we have to store in the 2nd row
				}
		}
	return logindata;  //finally returning the 2 dimensional array
				
	}
	
	
	
	
}
