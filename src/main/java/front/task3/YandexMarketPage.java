package front.task3;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class YandexMarketPage extends BasePage {

    private static final Logger logger = Logger.getLogger(YandexMarketPage.class);

    @FindBy(xpath = "//h2[@class='A6UH0']")
    private WebElement title;

    @FindBy(xpath = "//div[@data-baobab-name='catalog']/button")
    private WebElement catalogButton;

    @FindBy(xpath = "//li[@data-zone-name='category-link']/a")
    private List<WebElement> categoryList;
    @FindBy(xpath = "//div[@data-zone-name='link']/a")
    private List<WebElement> subcategoryList;

    @FindBy(xpath = "//ul[@data-autotest-id]//li//a")
    private List<WebElement> menuItemList;


    @Step("Проверка открытия главной страницы")
    public YandexMarketPage checkOpenPage() {
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Рекомендованные товары",
                title.getText());
        logger.info("Проверка открытия страницы");
        return this;
    }

    @Step("Нажать на каталог")
    public YandexMarketPage clickOnCatalog() {
        waitUntilElementToBeClickable(catalogButton).click();
        logger.info("Клик на каталог");
        return this;
    }

    @Step("Навести на категорию '{category}'")
    public YandexMarketPage moveToCategory(String category) {
        for (WebElement element : categoryList) {
            waitUntilElementToBeVisible(element);
            moveToElement(element);
            if (element.findElement(By.xpath("./span")).getText().equals(category)) {
                logger.info("Навести на категорию '" + category + "'");
                return this;
            }
        }
        Assert.fail("Нет категории '" + category + "'");
        return this;
    }

    @Step("Навести на подкатегорию '{subcategory}' и выбрать пункт меню '{menuItem}'")
    public XboxPage moveToSubcategoryAndClickMenuItem(String subcategory, String menuItem) {
        for (WebElement element : subcategoryList) {
            waitUntilElementToBeVisible(element);
            moveToElement(element);
            if (element.getText().equals(subcategory)) {
                logger.info("Наведение на подкатегорию '" + subcategory + "'");
                int count = 0;
                for (WebElement item : menuItemList) {
                    if (waitUntilElementToBeVisible(item).getText().equals(menuItem)) {
                        count++;
                        if (count == 2) {
                            moveToElement(item);
                            item.click();
                            logger.info("Пункт меню '" + menuItem + "' выбран");
                            return pageManager.getXboxPage();
                        }
                    }
                }
                Assert.fail("Не найдено второе вхождение пункта меню '" + menuItem + "' в подкатегории '" + subcategory + "'");
            }
        }
        Assert.fail("Нет подкатегории '" + subcategory + "'");
        return pageManager.getXboxPage();
    }


}
