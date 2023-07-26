import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class finalProject {
    WebDriver driver;
    Actions action;
    JavascriptExecutor js;

    WebDriverWait wait;

    @BeforeMethod
    public void beforMethod() {
        driver = new ChromeDriver();
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
        //Navigate to the swoop.ge
        driver.get("https://www.swoop.ge/");

        //Go to 'კინო'
        //driver.findElement(By.xpath("//label[text()='კინო']")).click();
       driver.findElement(By.cssSelector("img[src='https://cdn.swoop.ge/images/icons/_block1.svg']")).click();


        //Select the first movie in the returned list and click on ‘ყიდვა’ button
        List<WebElement> moviesList = driver.findElements(By.cssSelector("div.movies-deal"));
        WebElement firstMovie = moviesList.get(0);
        action.moveToElement(firstMovie).perform();
        List<WebElement> buyEl = driver.findElements(By.xpath("//div[@class='info-cinema-ticket']/p"));
        buyEl.get(0).click();

        //Scroll vertically (if necessary), and horizontally and choose ‘კავეა ისთ ფოინთი’
        WebElement caveaEastPoint = driver.findElement(By.id("ui-id-7"));
        js.executeScript("arguments[0].scrollIntoView();", caveaEastPoint);
        js.executeScript("arguments[0].click()", caveaEastPoint);

        // Check that only ‘კავეა ისთ ფოინთი’ options are returned
        List<WebElement> caveaEastPointTitle = driver.findElements(By.xpath("//div[@id='384933']//div[@aria-hidden='false']//a//p[text()='კავეა ისთ ფოინთი']"));
        String expectedCinemaName = "კავეა ისთ ფოინთი";
        for (WebElement cinemaTitleVal : caveaEastPointTitle) {
            Assert.assertEquals(cinemaTitleVal.getText(), expectedCinemaName);
        }

        // Click on last date and then click on last option
        List<WebElement> dateTimeList = driver.findElements(By.xpath("//div[@id='384933']//div//ul//li//a"));
        WebElement lastDateEl = dateTimeList.get(dateTimeList.size() - 1);
        lastDateEl.click();

        List<WebElement> filmList = driver.findElements(By.xpath("//div[@id='384933']//div[@aria-hidden='false']//a//p[1]"));
        WebElement lastFilmEl = filmList.get(filmList.size() - 1);
        lastFilmEl.click();

        //Check in opened popup that movie name, cinema and datetime is valid
        String lastDateElText = js.executeScript("return arguments[0].innerHTML;", lastDateEl).toString();
        String date = lastDateElText.replaceAll("[^\\d]", "");
        String hours = js.executeScript("return arguments[0].innerHTML;", lastFilmEl).toString();

        String expectedFilmDate = date + " " + generateCurrentMonth() + " " + hours;
        String expectedMovieName = driver.findElement(By.className("name")).getText();


        driver.getWindowHandle();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("movie-title")));
        String actualFilmName = driver.findElement(By.className("movie-title")).getText();
        String actualCinemaName = driver.findElements(By.className("movie-cinema")).get(0).getText();
        String actualFilmDate = driver.findElements(By.className("movie-cinema")).get(1).getText();
        Assert.assertEquals(actualFilmName, expectedMovieName);
        Assert.assertEquals(actualCinemaName, expectedCinemaName);
        Assert.assertEquals(actualFilmDate, expectedFilmDate);

        //Choose any vacant place
        List<WebElement> cinemaSeats = driver.findElements(By.cssSelector("div.seat"));
        for (WebElement singleSeat : cinemaSeats) {
            if (singleSeat.getAttribute("class").equals("seat free")) {
                js.executeScript("arguments[0].click();", singleSeat);
                break;
            }
        }

        //Register for a new account
        WebElement register = driver.findElement(By.className("register"));
        js.executeScript("arguments[0].click();", register);

        //Choose ‘იურიდიული პირი’
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//p[text()='იურიდიული პირი']")));

        //Fill all mandatory with not valid data and optional fields, in case of dropdowns choose any non-default option
        Select dropDown = new Select(driver.findElement(By.id("lLegalForm")));
        dropDown.selectByIndex(1);
        driver.findElement(By.id("lName")).sendKeys("Lasha inc");
        driver.findElement(By.id("lTaxCode")).sendKeys("8888");
        driver.findElement(By.id("registred")).sendKeys("09/22/1996");
        driver.findElement(By.id("lAddress")).sendKeys("Varketili");
        Select countryDropDown = new Select(driver.findElement(By.id("lCountryCode")));
        countryDropDown.selectByIndex(20);
        driver.findElement(By.id("lCity")).sendKeys("Tbilisi");
        driver.findElement(By.id("lPostalCode")).sendKeys("0162");
        driver.findElement(By.id("lWebSite")).sendKeys("Shemodirasilodebi.org");
        driver.findElement(By.id("lBank")).sendKeys("TBC");
        driver.findElement(By.id("lBankAccount")).sendKeys("888888");
        driver.findElement(By.id("lContactPersonEmail")).sendKeys("lasha@gmail.com");
        driver.findElement(By.id("lContactPersonPassword")).sendKeys("Shextadasheikuntrusha");
        driver.findElement(By.id("lContactPersonConfirmPassword")).sendKeys("Shextadasheikuntrusha");
        driver.findElement(By.id("lContactPersonName")).sendKeys("Lasha lashante");
        Select genderDropDown = new Select(driver.findElement(By.id("lContactPersonGender")));
        genderDropDown.selectByIndex(1);
        driver.findElement(By.id("birthday")).sendKeys("09/22/1996");
        Select citizenshipDropDown = new Select(driver.findElement(By.id("lContactPersonCountryCode")));
        citizenshipDropDown.selectByIndex(10);
        driver.findElement(By.id("lContactPersonPersonalID")).sendKeys("2020202020");
        driver.findElement(By.id("lContactPersonPhone")).sendKeys("555555555");
        driver.findElement(By.id("IsLegalAgreedTerms")).click();

        //Scroll top and check that error message ‘წითლად მონიშნული ველების შევსება სავალდებულოა ' is appear
        js.executeScript("arguments[0].scrollIntoView();", register);
        String errorText = driver.findElement(By.cssSelector("#register-content-2 h4")).getText();
        String expectedErrorText = "წითლად მონიშნული ველების შევსება სავალდებულოა";
        Assert.assertEquals(errorText, expectedErrorText);
    }

    public String generateCurrentMonth() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", new Locale("ka", "GE"));
        return now.format(formatter);
    }
}







