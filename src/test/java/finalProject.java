import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class finalProject {
    WebDriver driver;
    Actions action;
    JavascriptExecutor js;

    WebDriverWait wait;

    @BeforeMethod
    public void beforMethod() {
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        driver = new ChromeDriver(options);
        action = new Actions(driver);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }

    @Test
    public void swoopRegister() {

        driver.get("https://www.swoop.ge/");


        driver.findElement(By.xpath("//*[@id=\"body\"]/header/div[2]/div/div[1]/ul/li[1]/a")).click();


        List<WebElement> moviesList = driver.findElements(By.cssSelector("div.movies-deal"));
        WebElement firstMovie = moviesList.get(0);
        action.moveToElement(firstMovie).perform();
        List<WebElement> buyEl = driver.findElements(By.xpath("//div[@class='info-cinema-ticket']/p"));
        buyEl.get(0).click();


        WebElement caveaEastPoint = driver.findElement(By.id("ui-id-7"));
        js.executeScript("arguments[0].scrollIntoView();", caveaEastPoint);
        js.executeScript("arguments[0].click()", caveaEastPoint);


    }
}







