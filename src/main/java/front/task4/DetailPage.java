package front.task4;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DetailPage extends BasePage {

    private static final Logger logger = Logger.getLogger(DetailPage.class);

    @FindBy(xpath = "//label[@data-marker='price-from']//input")
    private WebElement priceFromInput;

    @FindBy(xpath = "//label[@data-marker='price-to']//input")
    private WebElement priceToInput;

    @FindBy(xpath = "//div[@class='expand-list-expandButton-pW0cQ']/a")
    private WebElement showMoreButton;

    @FindBy(xpath = "//label[@data-marker='params[112691]/checkbox/757884']")
    private WebElement memory256GBCheckbox;

    @FindBy(xpath = "//button[@data-marker='search-filters/submit-button']")
    private WebElement applyFiltersButton;

    @FindBy(xpath = "//div[@data-marker='item']")
    private List<WebElement> searchResults;

    private String searchQuery;
    private String priceFrom;
    private String priceTo;
    private String memory;

    public DetailPage setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
        return this;
    }


    @Step("Установить цену от {priceFrom} до {priceTo}")
    public DetailPage setPriceRange(int priceFrom, int priceTo) {
        this.priceFrom = String.valueOf(priceFrom);
        this.priceTo = String.valueOf(priceTo);

        WebElement priceFromElem = waitUntilElementToBeVisible(priceFromInput);
        WebElement priceToElem = waitUntilElementToBeVisible(priceToInput);

        priceFromElem.clear();
        priceToElem.clear();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (char ch : this.priceFrom.toCharArray()) {
            priceFromElem.sendKeys(String.valueOf(ch));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (char ch : this.priceTo.toCharArray()) {
            priceToElem.sendKeys(String.valueOf(ch));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        logger.info("Установлен диапазон цен: от " + priceFrom + " до " + priceTo);
        return this;
    }

    @Step("Показать дополнительные фильтры")
    public DetailPage showMoreFilters() {
        waitUntilElementToBeClickable(showMoreButton).click();
        logger.info("Показаны дополнительные фильтры");
        return this;
    }

    @Step("Выбрать фильтр '256 ГБ'")
    public DetailPage selectMemory256GB() {
        this.memory = "256 ГБ";
        showMoreFilters();
        WebDriverWait wait = new WebDriverWait(driver.getDriver(), Duration.ofSeconds(10));
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(memory256GBCheckbox));
        checkbox.click();
        logger.info("Выбран фильтр '256 ГБ'");
        return this;
    }

    @Step("Применить фильтры")
    public DetailPage applyFilters() {
        waitUntilElementToBeClickable(applyFiltersButton).click();
        logger.info("Применены фильтры");
        return this;
    }

    @Step("Проверить результаты поиска")
    public DetailPage verifySearchResults() {
        String expectedText = this.searchQuery.toLowerCase();
        double minPrice = Double.parseDouble(this.priceFrom);
        double maxPrice = Double.parseDouble(this.priceTo);

        int checkedItems = 0;
        for (WebElement result : searchResults) {
            if (checkedItems >= 15) {
                break;
            }

            String resultText = result.getText().toLowerCase();
            Assert.assertTrue("Результат не содержит ожидаемый текст: " + expectedText, resultText.contains(expectedText));
            Assert.assertTrue("Результат не содержит информацию о памяти: " + memory, resultText.contains(memory.toLowerCase()));

            String priceText = result.findElement(By.xpath(".//span[contains(@class, 'price')]")).getText().replaceAll("\\D+", "");
            double price = Double.parseDouble(priceText);
            Assert.assertTrue("Цена вне ожидаемого диапазона: " + price, price >= minPrice && price <= maxPrice);

            checkedItems++;
        }
        logger.info("Все результаты соответствуют ожиданиям");
        return this;
    }
}
