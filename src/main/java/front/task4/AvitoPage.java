package front.task4;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class AvitoPage extends BasePage {
    private static final Logger logger = Logger.getLogger(AvitoPage.class);

    @FindBy(xpath = "//button[@class='desktop-lqkwz0']")
    private WebElement catalogButton;

    @FindBy(xpath = "//input[@data-marker='search-form/suggest']")
    private WebElement searchInput;

    @FindBy(xpath = "//div[@class='new-rubricator-content-leftcontent-_hhyV']/div/div")
    private List<WebElement> categoryList;

    @FindBy(xpath = "//div[@class = 'new-rubricator-content-child__item-_bubk']/a")
    private List<WebElement> menuItemList;

    private String searchQuery;

    @Step("Проверка открытия главной страницы")
    public AvitoPage checkOpenPage() {
        logger.info("Проверка открытия страницы");
        return this;
    }

    @Step("Нажать на каталог")
    public AvitoPage clickOnCatalog() {
        waitUntilElementToBeClickable(catalogButton).click();
        logger.info("Клик на каталог");
        return this;
    }

    @Step("Навести на категорию '{category}'")
    public AvitoPage moveToCategory(String category) {
        for (WebElement element : categoryList) {
            waitUntilElementToBeVisible(element);
            moveToElement(element);
            if (element.findElement(By.xpath("./p")).getText().equals(category)) {
                logger.info("Навести на категорию '" + category + "'");
                return this;
            }
        }
        Assert.fail("Категория '" + category + "' не найдена");
        return this;
    }

    @Step("Нажать на пункт меню '{menuItem}'")
    public TelephonePage clickOnMenuItem(String menuItem) {
        for (WebElement item : menuItemList) {
            waitUntilElementToBeVisible(item);
            if (item.findElement(By.xpath("./span")).getText().equals(menuItem)) {
                moveToElement(item);
                item.click();
                logger.info("Нажатие на пункт меню '" + menuItem + "'");
                return PageManagerHelper.getInstance().getTelephonePage();
            }
        }
        Assert.fail("Пункт меню '" + menuItem + "' не найден");
        return PageManagerHelper.getInstance().getTelephonePage();
    }


    @Step("Ввести текст '{searchText}' в поисковую строку и выбрать первый результат из выпадающего списка")
    public DetailPage enterSearchTextAndSelectFirstResult(String searchText) {
        waitUntilElementToBeVisible(searchInput);
        searchInput.sendKeys(searchText);
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@data-popper-placement, 'bottom-start')]/child::div")));
        WebElement firstResult = dropdown.findElement(By.xpath(".//span"));
        firstResult.click();

        this.searchQuery = searchText;

        logger.info("Введен текст в поисковую строку: " + searchText + ", выбран первый результат из выпадающего списка");
        return PageManagerHelper.getInstance().getDetailPage().setSearchQuery(this.searchQuery);
    }
}
