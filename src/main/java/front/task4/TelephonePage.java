package front.task4;

import front.task3.XboxPage;
import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class TelephonePage extends BasePage {
    private static final Logger logger = Logger.getLogger(TelephonePage.class);

    @FindBy(xpath = "//div[@data-marker='item']")
    private List<WebElement> productList;

    @FindBy(xpath = "//div[contains(@class, 'sort-sort-klm3E')]")
    private WebElement sortDropdown;

    @FindBy(xpath = "//button[@data-marker='sort/custom-option(1)']")
    private WebElement sortAscendingPriceOption;

    @FindBy(xpath = "//div[@data-marker='favorites-add']")
    private WebElement addToFavoritesButton;

    @FindBy(xpath = "//a[@data-marker='header/favorites']")
    private WebElement favoritesButtonInPanel;

    @FindBy(xpath = "//div[@class='desktop-1h3ki5d']")
    private WebElement successNotification;

    private String firstProductName;
    private String firstProductPrice;
    @Step("Вывести в лог первые 5 найденных товаров (название и цену)")
    public TelephonePage logFirstFiveProducts() {

        for (int i = 0; i < 5; i++) {
            WebElement product = productList.get(i);
            moveToElement(product);
            String title = product.findElement(By.xpath(".//h3")).getText();
            String price = product.findElement(By.xpath(".//span[@class='']")).getText();

            logger.info("Название: " + title + ". Цена: " + price);
        }
        return this;
    }

    @Step("Установить сортировку: дешевле")
    public synchronized TelephonePage setSortingByPriceAscending() {
        JavascriptExecutor js = (JavascriptExecutor) driver.getDriver();
        js.executeScript("window.scrollTo(0,0)");
        waitUntilElementToBeClickable(sortDropdown).click();
        waitUntilElementToBeClickable(sortAscendingPriceOption).click();
        logger.info("Установлена сортировка по возрастанию цены.");
        return this;
    }

    @Step("Проверить, что товары отсортированы по возрастанию цены")
    public synchronized TelephonePage verifyProductsSortedByPriceAscending(int numberOfProductsToCheck) throws InterruptedException {
        Thread.sleep(3000);
        List<WebElement> productListSorted = driver.getDriver().findElements(By.xpath("//div[@data-marker='item']"));

        boolean isSorted = true;
        for (int i = 1; i < numberOfProductsToCheck; i++) {
            String currentProductPriceText = productListSorted.get(i).findElement(By.xpath(".//span[@class='']")).getText().trim();
            String previousProductPriceText = productListSorted.get(i - 1).findElement(By.xpath(".//span[@class='']")).getText().trim();
            double currentProductPrice = extractPrice(currentProductPriceText);
            double previousProductPrice = extractPrice(previousProductPriceText);

            if (currentProductPrice < previousProductPrice) {
                isSorted = false;
                break;
            }
        }

        Assert.assertTrue("Товары не отсортированы по возрастанию цены", isSorted);
        logger.info("Товары отсортированы по возрастанию цены");
        return this;
    }

    private double extractPrice(String priceText) {
        String priceDigits = priceText.replaceAll("[^\\d,\\.]", "");
        priceDigits = priceDigits.replace(',', '.');

        return Double.parseDouble(priceDigits);
    }
    @Step("Добавить первый товар в избранное")
    public TelephonePage addFirstProductToFavorites() {
        WebElement firstProduct = productList.get(0);
        moveToElement(firstProduct);
        addToFavoritesButton.click();
        logger.info("Добавлен первый товар в избранное.");
        return this;
    }
    @Step("Запомнить первый товар из списка (название и цену)")
    public TelephonePage rememberFirstProduct() {
        WebElement firstProduct = productList.get(0);
        this.firstProductName = firstProduct.findElement(By.xpath(".//h3")).getText();
        this.firstProductPrice = firstProduct.findElement(By.xpath(".//span[@class='']")).getText();
        logger.info("Запомнен первый товар: Название - " + firstProductName + ", Цена - " + firstProductPrice);
        return this;
    }

    @Step("Проверка, что всплывающее сообщение об успешном добавлении товара в избранное отображается")
    public TelephonePage verifyAddToFavoritesSuccess() {
        Assert.assertTrue("Уведомление о добавлении товара в избранное не отображается",
                waitUntilElementToBeVisible(successNotification).isDisplayed());
        logger.info("Уведомление о добавлении товара в избранное отображается");
        return this;
    }

    @Step("Перейти в избранное")
    public FavoritesPage goToFavorites() {
        waitUntilElementToBeClickable(favoritesButtonInPanel).click();
        logger.info("Переход в избранное");
        return new FavoritesPage(firstProductName, firstProductPrice);
    }


}

