package practice;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class RegSuccess {
WebDriver driver;
    @BeforeSuite
    public  void setDriver(){
        System.setProperty("webdriver.chrome.driver","/home/ttn/Downloads/intellij backup/IdeaProjects/QATest/drivers/chromedriver");
         driver =new ChromeDriver();
//        return(driver);
    }

    @Test
    public void register(){
//        driver = setDriver();
        Properties prop = new Properties();
        File filePath = new File("/home/ttn/Downloads/intellij backup/IdeaProjects/QATest/src/test/java/practice/registerDetails");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            prop.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.get("http://newtours.demoaut.com/");
        driver.findElement(By.xpath("//a[text()='REGISTER']")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.name("firstName")).sendKeys(prop.getProperty("FirstName"));
        driver.findElement(By.name("lastName")).sendKeys(prop.getProperty("LastName"));
        driver.findElement(By.name("phone")).sendKeys(prop.getProperty("Phone"));
        driver.findElement(By.name("userName")).sendKeys(prop.getProperty("UserName"));
        driver.findElement(By.name("address1")).sendKeys(prop.getProperty("Address"));
        driver.findElement(By.name("city")).sendKeys(prop.getProperty("City"));
        driver.findElement(By.name("state")).sendKeys(prop.getProperty("State"));
        driver.findElement(By.name("postalCode")).sendKeys(prop.getProperty("PIN"));
        WebElement country= driver.findElement(By.name("country"));
        Select select = new Select(country);
        select.selectByVisibleText(prop.getProperty("Country"));
        driver.findElement(By.name("email")).sendKeys(prop.getProperty("Email"));
        driver.findElement(By.name("password")).sendKeys(prop.getProperty("Password"));
        driver.findElement(By.name("confirmPassword")).sendKeys(prop.getProperty("Password"));
        driver.findElement(By.name("register")).click();

    }

    @Test()
    public void login(){
//         driver = setDriver();
        Properties prop = new Properties();
        File filePath = new File("/home/ttn/Downloads/intellij backup/IdeaProjects/QATest/src/test/java/practice/registerDetails");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            prop.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.get("http://newtours.demoaut.com/");
        driver.findElement(By.name("userName")).sendKeys(prop.getProperty("Email"));
        driver.findElement(By.name("password")).sendKeys(prop.getProperty("Password"));
        driver.findElement(By.name("login")).click();
    }


    @Test(description = "departure and arival city negative test case",dependsOnMethods = {"login"},priority = 2)
    public void city() throws InterruptedException {
       // driver.findElements(By.name("tripType")).get(0).click();
        //Passengers
        WebElement passengers = driver.findElement(By.name("passCount"));
        Select selectpassenger = new Select(passengers);
        selectpassenger.selectByVisibleText("1");

        //Departing

        WebElement depart = driver.findElement(By.name("fromPort"));
        Select departdrop = new Select(depart);

        departdrop.selectByVisibleText("Acapulco");

        //departing(date)
        WebElement month = driver.findElement(By.name("fromMonth"));
        WebElement day = driver.findElement(By.name("fromDay"));
        new Select(month).selectByVisibleText("May");
        new Select(day).selectByVisibleText("2");


        // ARRIVING IN
        WebElement arrive = driver.findElement(By.name("toPort"));
        new Select(arrive).selectByVisibleText("Acapulco");

        //Returning date
        WebElement month1 = driver.findElement(By.name("toMonth"));
        WebElement day1 = driver.findElement(By.name("toDay"));
        new Select(month1).selectByVisibleText("May");
        new Select(day1).selectByVisibleText("10");

        //Service class
        driver.findElements(By.xpath("//input[@name='servClass']")).get(2).click();

        //Airline
        WebElement prefrence = driver.findElement(By.name("airline"));
        new Select(prefrence).selectByVisibleText("Unified Airlines");


        //continue button
        driver.findElement(By.name("findFlights")).submit();

        //Depart to
        driver.findElements(By.name("outFlight")).get(2).click();
        //Arrive to
        driver.findElements(By.name("outFlight")).get(2).click();

        //continue button clicked
        driver.findElement(By.name("reserveFlights")).submit();




        try {
            // Making the test fail
            Assert.assertEquals(depart.getAttribute("value"), arrive.getAttribute("value"));

        } catch (StaleElementReferenceException e) {

            depart = driver.findElement(By.name("fromPort"));
            arrive = driver.findElement(By.name("toPort"));
            System.out.println(depart.getAttribute("value"));
            System.out.println(arrive.getAttribute("value"));
            Assert.assertNotEquals(depart.getAttribute("value"), arrive.getAttribute("value"));


            // Following lines will be printed when the assert condition fails
            System.out.println("test case");
            System.out.println("Error message: " + e.toString());

            Thread.sleep(300);
            System.out.println("city end ");
        }
    }




    //*******Arrival and Departure date


    @Test(description = "Departure date should be less than arrival date ",dependsOnMethods = {"register","login"} ,priority = 5)
    public void date() throws InterruptedException {
         driver.findElement(By.xpath("//input[@value='roundtrip']")).click();
        //Passengers
        WebElement passengers = driver.findElement(By.name("passCount"));
        Select selectpassenger = new Select(passengers);
        selectpassenger.selectByVisibleText("1");

        //Departing

        WebElement depart = driver.findElement(By.name("fromPort"));
        Select departdrop = new Select(depart);

        departdrop.selectByVisibleText("Acapulco");

        //departing(date)
        WebElement month = driver.findElement(By.name("fromMonth"));
        WebElement day = driver.findElement(By.name("fromDay"));
        new Select(month).selectByVisibleText("May");
        new Select(day).selectByVisibleText("2");

        String month_depart = month.getAttribute("value");
        String day_depart = day.getAttribute("value");

        // ARRIVING IN
        WebElement arrive = driver.findElement(By.name("toPort"));
        new Select(arrive).selectByVisibleText("London");

        //Returning date
        WebElement month1 = driver.findElement(By.name("toMonth"));
        WebElement day1 = driver.findElement(By.name("toDay"));
        new Select(month1).selectByVisibleText("May");
        new Select(day1).selectByVisibleText("2");

        String month_arrive = month.getAttribute("value");
        String day_arrive = day.getAttribute("value");
        //Service class
        driver.findElements(By.xpath("//input[@name='servClass']")).get(2).click();

        //Airline
        WebElement prefrence = driver.findElement(By.name("airline"));
        new Select(prefrence).selectByVisibleText("Unified Airlines");


        //continue button
        driver.findElement(By.name("findFlights")).submit();

        //Depart to
        driver.findElements(By.name("outFlight")).get(2).click();
        //Arrive to
        driver.findElements(By.name("outFlight")).get(2).click();

        //continue button clicked
        driver.findElement(By.name("reserveFlights")).submit();


        String act_url = driver.getCurrentUrl();
        String Exp_url = "http://newtours.demoaut.com/mercuryreservation.php";
        Assert.assertEquals(act_url, Exp_url);


    }





    @Test(description = "end to end",priority = 3,dependsOnMethods = {"login"})

    public void flightFind() throws IOException, InterruptedException {


        driver.findElements(By.name("tripType")).get(0).click();
        //Passengers
        WebElement passengers = driver.findElement(By.name("passCount"));
        Select selectpassenger = new Select(passengers);
        selectpassenger.selectByVisibleText("1");

        //Departing

        WebElement depart = driver.findElement(By.name("fromPort"));
        Select departdrop = new Select(depart);

        departdrop.selectByVisibleText("Acapulco");

        //departing(date)
        WebElement month = driver.findElement(By.name("fromMonth"));
        WebElement day = driver.findElement(By.name("fromDay"));
        new Select(month).selectByVisibleText("May");
        new Select(day).selectByVisibleText("2");


        // ARRIVING IN
        WebElement arrive = driver.findElement(By.name("toPort"));
        new Select(arrive).selectByVisibleText("London");

        //Returning date
        WebElement month1 = driver.findElement(By.name("toMonth"));
        WebElement day1 = driver.findElement(By.name("toDay"));
        new Select(month1).selectByVisibleText("May");
        new Select(day1).selectByVisibleText("10");

        //Service class
        driver.findElements(By.xpath("//input[@name='servClass']")).get(2).click();

        //Airline
        WebElement prefrence = driver.findElement(By.name("airline"));
        new Select(prefrence).selectByVisibleText("Unified Airlines");


        //continue button
        driver.findElement(By.name("findFlights")).submit();

        //Depart to
        driver.findElements(By.name("outFlight")).get(2).click();
        //Arrive to
        driver.findElements(By.name("outFlight")).get(2).click();

        //continue button clicked
        driver.findElement(By.name("reserveFlights")).submit();
/*   ***original

        String depart_text;

        driver.navigate().refresh();

        try {

            depart_text = depart.getText();


        }
        catch (StaleElementReferenceException e)
        {
            depart = driver.findElement(By.name("fromPort"));

            depart_text = depart.getText();
            Thread.sleep(300);

            //  depart=driver.findElement(By.name("fromPort"));
            //departdrop.selectByVisibleText("Acapulco");
        }
       driver.navigate().refresh();
        // driver.findElement(By.xpath("xpath here")).





        //driver.navigate().refresh();
        String arrive_text;

        try {

           arrive_text=arrive.getText();


        }
        catch (StaleElementReferenceException e)
        {
            arrive = driver.findElement(By.name("toPort"));

            arrive_text = arrive.getText();
Thread.sleep(300);
            //  depart=driver.findElement(By.name("fromPort"));
            //departdrop.selectByVisibleText("Acapulco");
        }
*/

        // driver.navigate().refresh();

/*     *****original
        try {
            // Making the test fail
            Assert.assertNotEquals(depart_text, arrive_text);

        } catch(Error e){
            // Following lines will be printed when the assert condition fails
            System.out.println("test case");
            System.out.println("Error message: " + e.toString());
        }
*/



        try {
            // Making the test fail
            Assert.assertEquals(depart.getAttribute("value"), arrive.getAttribute("value"));

        } catch(StaleElementReferenceException e){

            depart = driver.findElement(By.name("fromPort"));
            arrive = driver.findElement(By.name("toPort"));
            System.out.println(depart.getAttribute("value"));
            System.out.println(arrive.getAttribute("value"));
            Assert.assertNotEquals(depart.getAttribute("value"), arrive.getAttribute("value"));


            // Following lines will be printed when the assert condition fails
            System.out.println("test case");
            System.out.println("Error message: " + e.toString());
        }


        // Book a fight
        //driver.findElement(By.xpath("//font"))
        driver.findElement(By.name("passFirst0")).sendKeys("Vidit");
        driver.findElement(By.name("passLast0")).sendKeys("Gupta");
        WebElement meal = driver.findElement(By.name("pass.0.meal"));
        new Select(meal).selectByVisibleText("Vegetarian");
        WebElement cardtype = driver.findElement(By.name("creditCard"));
        new Select(cardtype).selectByVisibleText("Visa");
        driver.findElement(By.name("creditnumber")).sendKeys("123456789");
        WebElement expmnth = driver.findElement(By.name("cc_exp_dt_mn"));
        new Select(expmnth).selectByVisibleText("02");
        WebElement expyear = driver.findElement(By.name("cc_exp_dt_yr"));
        new Select(expyear).selectByVisibleText("2010");
        driver.findElement(By.name("cc_frst_name")).sendKeys("Vidit");
        driver.findElement(By.name("cc_mid_name")).sendKeys("" +
                "");
        driver.findElement(By.name("cc_last_name")).sendKeys("Gupta");
        driver.findElement(By.name("ticketLess")).click();
        driver.findElement(By.name("billAddress1")).sendKeys("Laxmi nagar");
        driver.findElement(By.name("billCity")).sendKeys("Delhi");
        driver.findElement(By.name("billState")).sendKeys("Delhi");
        driver.findElement(By.name("billZip")).sendKeys("110092");
        WebElement Billcountry=driver.findElement(By.name("billCountry"));
        new Select(Billcountry).selectByVisibleText("UNITED STATES");

        driver.findElement(By.name("delAddress1")).sendKeys("Laxmi nagar");
        driver.findElement(By.name("delCity")).sendKeys("Delhi");
        driver.findElement(By.name("delState")).sendKeys("Delhi");
        driver.findElement(By.name("delZip")).sendKeys("110092");
        WebElement delcountry=driver.findElement(By.name("delCountry"));
        new Select(delcountry).selectByVisibleText("UNITED STATES");
        try {
            driver.findElement(By.name("buyFlights")).click();
        }

        catch (StaleElementReferenceException e) {

            driver.findElement(By.name("buyFlights")).click();
        }
        driver.findElement(By.xpath("//img[@src='/images/forms/Logout.gif']")).click();

        Thread.sleep(300);
        System.out.println("end to end end ");
    }





    @Test(description = "empty passengers field",dependsOnMethods = {"register","login"},priority = 4)

    public void book_flight() throws IOException, InterruptedException {



        driver.findElements(By.name("tripType")).get(0).click();
        //Passengers
        WebElement passengers = driver.findElement(By.name("passCount"));
        Select selectpassenger = new Select(passengers);
        selectpassenger.selectByVisibleText("1");

        //Departing

        WebElement depart = driver.findElement(By.name("fromPort"));
        Select departdrop = new Select(depart);

        departdrop.selectByVisibleText("Acapulco");

        //departing(date)
        WebElement month = driver.findElement(By.name("fromMonth"));
        WebElement day = driver.findElement(By.name("fromDay"));
        new Select(month).selectByVisibleText("May");
        new Select(day).selectByVisibleText("2");


        // ARRIVING IN
        WebElement arrive = driver.findElement(By.name("toPort"));
        new Select(arrive).selectByVisibleText("London");

        //Returning date
        WebElement month1 = driver.findElement(By.name("toMonth"));
        WebElement day1 = driver.findElement(By.name("toDay"));
        new Select(month1).selectByVisibleText("May");
        new Select(day1).selectByVisibleText("10");

        //Service class
        driver.findElements(By.xpath("//input[@name='servClass']")).get(2).click();

        //Airline
        WebElement prefrence = driver.findElement(By.name("airline"));
        new Select(prefrence).selectByVisibleText("Unified Airlines");


        //continue button
        driver.findElement(By.name("findFlights")).submit();

        //Depart to
        driver.findElements(By.name("outFlight")).get(2).click();
        //Arrive to
        driver.findElements(By.name("outFlight")).get(2).click();

        //continue button clicked
        driver.findElement(By.name("reserveFlights")).submit();


        // Book a fight

        try {
            driver.findElement(By.name("buyFlights")).click();
        }

        catch (StaleElementReferenceException e) {

            driver.findElement(By.name("buyFlights")).click();
        }

        String expected_result=driver.getCurrentUrl();

        String actual_result = "http://newtours.demoaut.com/mercurypurchase.php";


        Assert.assertEquals(expected_result,actual_result);



        System.out.println("last end");

    }



}


