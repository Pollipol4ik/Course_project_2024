package front.task1;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class TodoPage extends BasePage {
    private static final Logger logger = Logger.getLogger(TodoPage.class);

    @FindBy(xpath = "//span[@class='ng-binding']")
    private WebElement numberOfRemainingItems;

    @FindBy(xpath = "//ul[@class='list-unstyled']/li/input[@type='checkbox']")
    private List<WebElement> listOfCheckboxes;

    @FindBy(xpath = "//ul[@class='list-unstyled']/li/span")
    private List<WebElement> todoItems;

    @FindBy(xpath = "//input[@type='text' and @placeholder='Want to add more']")
    private WebElement inputAddElement;

    @FindBy(xpath = "//input[@type='submit' and @value='add']")
    private WebElement buttonAddElement;

    private int remaining = 5;
    private int total = 5;


    @Step("Проверка наличия текста '5 of 5 remaining'")
    public TodoPage checkText() {
        waitForElementToBeVisible(numberOfRemainingItems);
        String text = String.format("%s of %s remaining", remaining, total);
        Assert.assertEquals("Текст " + text + " не присутствует на странице", text, numberOfRemainingItems.getText().trim());
        logger.info("Проверка наличия текста '5 of 5 remaining'");
        return this;
    }

    @Step("Проверка, что элемент списка не зачеркнут")
    public TodoPage checkItemNotDone(String nameOfItem) {
        for (WebElement item : todoItems) {
            if (item.getText().trim().equals(nameOfItem)) {
                Assert.assertTrue("Элемент списка не должен быть зачеркнут", item.getAttribute("class").contains("done-false"));
                logger.info("Проверка того, что элемент списка не зачеркнут");
                return this;
            }
        }
        Assert.fail("Элемент '" + nameOfItem + "' не присутствует на странице");
        return this;
    }

    @Step("Отметка элемента списка как выполненного")
    public TodoPage markItemAsDone(String nameOfItem) {
        for (WebElement checkbox : listOfCheckboxes) {
            WebElement item = checkbox.findElement(By.xpath("./../span"));
            if (item.getText().trim().equals(nameOfItem)) {
                checkbox.click();
                remaining -= 1;
                String text = String.format("%s of %s remaining", remaining, total);
                Assert.assertTrue("Элемент списка должен быть зачеркнут", item.getAttribute("class").contains("done-true"));
                Assert.assertEquals("Число оставшихся элементов не уменьшилось на 1", text, numberOfRemainingItems.getText().trim());
                logger.info("Поставить галочку у элемента списка");
                return this;
            }
        }
        Assert.fail("Элемент '" + nameOfItem + "' не присутствует на странице");
        return this;
    }

    @Step("Добавление нового элемента списка")
    public TodoPage addNewItem(String nameOfItem) {
        inputAddElement.click();
        inputAddElement.sendKeys(nameOfItem);
        buttonAddElement.click();
        total += 1;
        remaining += 1;
        waitForElementToBeVisible(numberOfRemainingItems);
        String text = String.format("%s of %s remaining", remaining, total);
        Assert.assertEquals("Число элементов не увеличилось на 1", text, numberOfRemainingItems.getText().trim());
        logger.info("Добавление нового элемента '" + nameOfItem + "'");
        return this;
    }

    @Step("Отметка нового элемента списка как выполненного")
    public TodoPage markNewItemAsDone(String nameOfItem) {
        for (WebElement item : todoItems) {
            if (item.getText().trim().equals(nameOfItem)) {
                markItemAsDone(nameOfItem);
                return this;
            }
        }
        Assert.fail("Элемент '" + nameOfItem + "' не присутствует на странице");
        return this;
    }
}
