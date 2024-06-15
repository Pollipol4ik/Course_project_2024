package front.task3;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class FavoritesPage extends BasePage {

    private static final Logger logger = Logger.getLogger(FavoritesPage.class);

    @FindBy(xpath = "//div[@class='_2rw4E _2O5qi']")
    private List<WebElement> favoriteProduct;

    @FindBy(xpath = "//button[@title='Удалить из избранного']")
    private WebElement removeFromFavoritesButton;

    @FindBy(xpath = "//div[@data-auto='notification']")
    private WebElement successNotification;

    @FindBy(xpath = "//div[@data-auto='emptyState']//span")
    private WebElement loginMessage;

    private String firstProductTitle;
    private String firstProductPrice;


    public FavoritesPage(String title, String price) {
        this.firstProductTitle = title;
        this.firstProductPrice = price;

    }

    @Step("Проверка отображения добавленного товара в избранном")
    public FavoritesPage verifyAddedProductInFavorites() {
        WebElement firstProduct = favoriteProduct.get(0);
        String title = firstProduct.findElement(By.xpath(".//h3")).getText();
        String price = firstProduct.findElement(By.xpath(".//span[@data-auto='snippet-price-current']/span[1]")).getText();
        Assert.assertEquals("Название товара в избранном не соответствует ожидаемому", firstProductTitle, title);
        Assert.assertEquals("Цена товара в избранном не соответствует ожидаемой", firstProductPrice, price);
        logger.info("Товар " + firstProductTitle + " отображается в избранном с ценой " + firstProductPrice);
        return this;
    }

    @Step("Нажать кнопку 'Удалить из избранного' для добавленного товара")
    public FavoritesPage removeFromFavorites() {
        moveToElement(removeFromFavoritesButton);
        removeFromFavoritesButton.click();
        logger.info("Клик по кнопке 'Удалить из избранного'");
        wait.until(ExpectedConditions.visibilityOf(successNotification));
        return this;
    }

    @Step("Проверка удаления товара из избранного")
    public FavoritesPage verifyRemovedFromFavorites() {
        Assert.assertTrue("Уведомление о удалении товара из избранного не отображается",
                waitUntilElementToBeVisible(successNotification).isDisplayed());
        logger.info("Уведомление о удалении товара из избранного отображается");
        refreshPageWithoutInterrupt(); // Обновление страницы без перебивания
        Assert.assertFalse("Товар не был удален из списка избранных",
                favoriteProduct.stream()
                        .anyMatch(product -> product.getText().contains(firstProductTitle)));
        logger.info("Товар успешно удален из списка избранных");
        return this;
    }


    @Step("Обновление страницы без перебивания текущего действия")
    public FavoritesPage refreshPageWithoutInterrupt() {
        driver.navigate().refresh();
        return this;
    }

    @Step("Проверка отображения сообщения 'Войдите в учётную запись'")
    public FavoritesPage verifyLoginMessage() {
        Assert.assertTrue("Сообщение 'Войдите в учётную запись' не отображается",
                loginMessage.isDisplayed());
        logger.info("Сообщение 'Войдите в учётную запись' отображается");
        return this;
    }
}
