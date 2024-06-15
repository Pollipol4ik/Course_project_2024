package front.task2;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PolytechPage extends BasePage {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(PolytechPage.class);

    @FindBy(xpath = "//h1")
    private WebElement title;

    @FindBy(xpath = "//button[@class='hamburger']")
    private WebElement hamburgerMenu;

    @FindBy(xpath = "//a[contains(@class, 'main-nav')]")
    private List<WebElement> itemList;

    @Step("Проверка заголовка главной страницы")
    public PolytechPage verifyPageTitle() {
        String expectedTitle = "Московский Политех";
        Assert.assertEquals("Заголовок не соответствует ожидаемому!", expectedTitle, title.getText());
        logger.info("Проверка корректности заголовка главной страницы");
        return this;
    }

    @Step("Клик на кнопку гамбургер-меню")
    public PolytechPage clickHamburgerMenuButton() {
        waitUntilElementToBeClickable(hamburgerMenu).click();
        logger.info("Открытие гамбургер-меню");
        return this;
    }

    @Step("Клик на пункт главного меню")
    public PolytechPage clickOnMainMenuItem(String menuItemName) {
        for (WebElement element : itemList) {
            if (element.getAttribute("title").equalsIgnoreCase(menuItemName)) {
                waitUntilElementToBeVisible(element);
                moveToElement(element);
                logger.info("Наведение курсора на пункт главного меню: " + menuItemName);
                return this;
            }
        }
        Assert.fail("Пункт меню не найден!");
        return this;
    }

    @Step("Нажатие на выбранный пункт меню: '{menuItemName}'")
    public SchedulePage clickOnMenuItem(String menuItemName) {
        for (WebElement element : itemList) {
            if (element.getAttribute("title").equalsIgnoreCase(menuItemName)) {
                waitUntilElementToBeClickable(element).click();
                logger.info("Переход на страницу с расписанием");
                return pageManager.getSchedulePage();
            }
        }
        Assert.fail("Ошибка при нажатии на пункт меню!");
        return pageManager.getSchedulePage();
    }
}
