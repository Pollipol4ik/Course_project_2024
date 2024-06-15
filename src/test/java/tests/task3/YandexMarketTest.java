package tests.task3;

import front.managers.DriverManager;
import front.managers.ManagerInitiallization;
import front.task3.YandexMarketPage;
import io.qameta.allure.junit4.DisplayName;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class YandexMarketTest {

    private final DriverManager driverManager = DriverManager.getInstance();

    @BeforeClass
    public static void beforeClass() {
        ManagerInitiallization.initFramework();
    }

    @Before
    public void before() {
        driverManager.getDriver().get("https://market.yandex.ru/");
    }

    @AfterClass
    public static void after() {
        ManagerInitiallization.quitFramework();
    }


    @Test
    @DisplayName("Яндекс Маркет: проверка сортировки товаров по цене")
    public void test() throws InterruptedException {
        YandexMarketPage yandexMarketPage = new YandexMarketPage();
        yandexMarketPage.checkOpenPage()
                .clickOnCatalog()
                .moveToCategory("Все для гейминга")
                .moveToSubcategoryAndClickMenuItem("Xbox", "Игровые приставки")
                .logProducts()
                .rememberFirstProduct()
                .addToFavorites()
                .verifyAddToFavoritesSuccess()
                .goToFavorites()
                .verifyAddedProductInFavorites()
                .removeFromFavorites()
                .verifyRemovedFromFavorites()
                .verifyLoginMessage();
    }
}
