package TestPackage;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class TestApp {

    WebDriver driver = new ChromeDriver();

    @BeforeAll
    public static void beforeALl ()
    {System.setProperty("webdriver.chrome.driver", "C:\\Users\\cs\\Desktop\\chromedriver_win32(1)\\chromedriver.exe");
     ChromeOptions options = new ChromeOptions();
     options.setExperimentalOption("useAutomationExtension", false);
    }

    @Test
    public void addNewRecipe () {

        //Adding New Recipe//
        driver.manage().window().maximize();
        driver.get("https://codepen.io/SedatUygur/pen/jWgJdv");
        driver.switchTo().frame("CodePen");
        driver.findElement(By.xpath("//button[@id='show']")).click();
        driver.findElement(By.xpath("//input[@type='text']")).sendKeys("Koushary");
        driver.findElement(By.xpath("//textarea[@type='textarea']")).sendKeys("Rice,Pasta,Lentil,Tomato Sauce,Fried Onions");
        driver.findElement(By.xpath("//button[@id='addButton']")).click();

        //AssertThatTheRecipeWasAddedSuccessfully//
        WebElement AddedRecipe = driver.findElement(By.linkText("Koushary"));
        Assert.assertEquals(true, AddedRecipe.isDisplayed());

        //DeleteRecipe//
        driver.findElement(By.xpath("//*[@data-reactid='.1.0.$3/=13.0.0.0']")).click();
        WebElement Delete = new WebDriverWait(driver,10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-reactid='.1.0.$3/=13.1.$0.0.3.0']")));
        Delete.click();

        //AssertThatTheAddedRecipeWasDeletedSuccessfully//
        Assert.assertEquals(0, driver.findElements(By.xpath("//*[@data-reactid='.1.0.$3/=13.0.0.0']")).size());

        //ClosingTheDriver//
        driver.close();
    }

    @Test
    public void EditRecipe () throws InterruptedException {
        driver.manage().window().maximize();
        driver.get("https://codepen.io/SedatUygur/pen/jWgJdv");
        driver.switchTo().frame("CodePen");
        driver.findElement(By.xpath("//*[@data-reactid='.1.0.$0/=10.0.0.0']")).click();
        WebElement Edit = new WebDriverWait(driver,10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-reactid='.1.0.$0/=10.1.$0.0.3.1']")));
        Edit.click();

        //EditTheRecipeTitle//
        driver.findElement(By.xpath("//input[@id='title']")).clear();
        driver.findElement(By.xpath("//input[@id='title']")).sendKeys("Pasta");

        //EditTheRecipeIngredients//
        driver.findElement(By.xpath("//textarea[@type='textarea']")).sendKeys(",Hot Sauce");
        driver.findElement(By.xpath("//button[@id='addButton']")).click();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,250)");

        WebElement EditRecipe = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-reactid='.1.0.$3/=13.0.0.0']")));
        EditRecipe.click();

        //AssertThatTheRecipeTitleWasEditedSuccessfully//
        WebElement EditedRecipeTitle = driver.findElement(By.linkText("Pasta"));
        Assert.assertEquals(true, EditedRecipeTitle.isDisplayed());

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


        //AssertThatTheRecipeIngredientsWasEditedSuccessfully//
        Assert.assertEquals(1, driver.findElements(By.xpath("//*[@data-reactid='.1.0.$3/=13.1.$0.0.2.$5//=15/=1$5/=015']")).size());

        //ClosingTheDriver//
        driver.close();
    }
}