package front.task3;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class XboxPage extends BasePage {

    private static final Logger logger = Logger.getLogger(XboxPage.class);

    @FindBy(xpath = "//div[@data-auto-themename='listDetailed']")
    private List<WebElement> productList;

    @FindBy(xpath = "//div[@class='_27kXM']")
    private WebElement addToFavoritesButton;

    @FindBy(xpath = "//div[@id='/content/header/header/wishlistButton']")
    private WebElement favoritesButton;

    @FindBy(xpath = "//div[@data-auto='notification']")
    private WebElement successNotification;

    private String firstProductTitle;
    private String firstProductPrice;

    @Step("Ввод в лог первых 5 найденных товаров")
    public XboxPage logProducts() {
        for (int i = 0; i < 5; i++) {
            WebElement product = productList.get(i);
            moveToElement(product);

            String name = product.findElement(By.xpath(".//h3")).getText();
            String price = product.findElement(By.xpath(".//span[@data-auto='snippet-price-current']/span[1]")).getText();
            logger.info("Название: " + name + ". Цена: " + price);
        }
        return this;
    }

    @Step("Запомнить первую позицию из списка товаров")
    public XboxPage rememberFirstProduct() {
        WebElement firstProduct = productList.get(0);
        firstProductTitle = firstProduct.findElement(By.xpath(".//h3")).getText();
        firstProductPrice = firstProduct.findElement(By.xpath(".//span[@data-auto='snippet-price-current']/span[1]")).getText();
        logger.info("Запомненный товар: Название: " + firstProductTitle + ". Цена: " + firstProductPrice);
        return this;
    }

    @Step("Нажать кнопку 'Добавить в избранное' для первого товара")
    public XboxPage addToFavorites() {
        moveToElement(addToFavoritesButton);
        addToFavoritesButton.click();
        return this;
    }

    @Step("Проверка, что всплывающее сообщение об успешном добавлении товара в избранное отображается")
    public XboxPage verifyAddToFavoritesSuccess() {
        Assert.assertTrue("Уведомление о добавлении товара в избранное не отображается",
                waitUntilElementToBeVisible(successNotification).isDisplayed());
        logger.info("Уведомление о добавлении товара в избранное отображается");
        return this;
    }

    @Step("Перейти на страницу избранного")
    public FavoritesPage goToFavorites() {
        waitUntilElementToBeClickable(favoritesButton).click();
        return PageManagerHelper.getInstance().getFavoritesPage(firstProductTitle, firstProductPrice);
    }

}
