package front.task4;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FavoritesPage extends BasePage {

    private static final Logger logger = Logger.getLogger(FavoritesPage.class);

    @FindBy(xpath = "//div[@class='favorite-snippet-root-xE9AV']")
    private WebElement favoriteProduct;

    @FindBy(xpath = ".//strong[contains(@class, 'styles-module-root-hwVld')]")
    private WebElement productName;

    @FindBy(xpath = ".//h4[contains(@class, 'styles-module-root-uSima')]")
    private WebElement productPrice;

    @FindBy(xpath = "//div[@data-marker='toggle-favorite-icon']")
    private WebElement removeFromFavoritesButton;

    @FindBy(xpath = "//button[@data-marker='favorites-rubricator/item-0/selected']")
    private WebElement allItemsButton;

    @FindBy(xpath = ".//span[@class='category-item-content-count-sXWZ1']")
    private WebElement allItemsCount;

    @FindBy(xpath = "//div[@data-marker='favorites-empty']")
    private WebElement emptyFavoritesMessage;

    @FindBy(xpath = ".//h3[contains(@class, 'styles-module-root-xvjz8')]")
    private WebElement emptyFavoritesHeader;

    private String expectedName;
    private String expectedPrice;

    public FavoritesPage(String expectedName, String expectedPrice) {
        this.expectedName = expectedName;
        this.expectedPrice = expectedPrice;
    }

    @Step("Проверить наличие добавленного товара в избранном")
    public FavoritesPage verifyProductInFavorites() {
        String actualName = favoriteProduct.findElement(By.xpath(".//strong[contains(@class, 'styles-module-root-hwVld')]")).getText();
        String actualPrice = favoriteProduct.findElement(By.xpath(".//h4[contains(@class, 'styles-module-root-uSima')]")).getText();

        Assert.assertEquals("Название товара не совпадает", expectedName, actualName);
        Assert.assertEquals("Цена товара не совпадает", expectedPrice, actualPrice);

        logger.info("Товар в избранном соответствует запомненному: Название - " + expectedName + ", Цена - " + expectedPrice);
        return this;
    }

    @Step("Удалить товар из избранного")
    public FavoritesPage removeFromFavorites() {
        if (isFavoritesEmpty()) {
            logger.warn("Избранное пусто.");
            return this;
        }

        waitUntilElementToBeClickable(removeFromFavoritesButton).click();
        logger.info("Товар удален из избранного");
        return this;
    }

    @Step("Проверить, что после удаления товара отображается 'Все 0'")
    public FavoritesPage verifyAllItemsZero() {
        waitUntilElementToBeVisible(allItemsButton);
        String itemCount = allItemsCount.getText();
        Assert.assertEquals("Количество товаров не равно нулю", "0", itemCount);
        logger.info("Проверка, что после удаления товара отображается 'Все 0'");
        return this;
    }

    @Step("Обновить страницу")
    public FavoritesPage refreshPage() {
        driver.getDriver().navigate().refresh();
        return this;
    }

    @Step("Проверить, что после обновления страницы отображается сообщение 'Сохраняйте объявления'")
    public FavoritesPage verifyEmptyFavoritesMessage() {
        waitUntilElementToBeVisible(emptyFavoritesMessage);
        Assert.assertTrue("Сообщение 'Сохраняйте объявления' не отображается", emptyFavoritesHeader.isDisplayed());
        logger.info("Сообщение 'Сохраняйте объявления' отображается");
        return this;
    }

    private boolean isFavoritesEmpty() {
        try {
            WebElement emptyFavoritesMessage = driver.getDriver().findElement(By.xpath("//div[@data-marker='favorites-empty']"));
            WebElement header = emptyFavoritesMessage.findElement(By.xpath(".//h3[contains(@class, 'styles-module-root-xvjz8')]"));
            return header.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
}
