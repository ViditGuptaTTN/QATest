package qa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;


    public class One {
        public static void main(String[] args) {

            String tableTitleColData="Quality Assurance Engineer";
            System.setProperty("webdriver.chrome.driver","/home/ttn/Downloads/intellij backup/IdeaProjects/QATest/drivers/chromedriver");
            WebDriver driver = new ChromeDriver();

            driver.get("https://www.ultimateqa.com/simple-html-elements-for-automation/");
            for(int i=2;i<=4;i++){
                List<WebElement> tableData=driver.findElements(By.xpath("//h2[text()='HTML Table with no id']//following::table//tr["+i+"]//td"));
                if(tableTitleColData.equals(tableData.get(0).getText())){
                    System.out.println("The salary of "+tableTitleColData+" is:"+tableData.get(2).getText());
                }
            }

            driver.close();
        }
    }

