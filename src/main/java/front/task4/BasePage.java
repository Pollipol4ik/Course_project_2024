package front.task4;

import front.managers.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected DriverManager driver = DriverManager.getInstance();
    protected WebDriverWait wait = new WebDriverWait(driver.getDriver(), Duration.ofSeconds(1000));

    public BasePage() {
        PageFactory.initElements(driver.getDriver(), this);
    }

    protected WebElement waitUntilElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitUntilElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void moveToElement(WebElement element) {
        Actions actions = new Actions(driver.getDriver());
        actions.moveToElement(element).perform();
    }

}

