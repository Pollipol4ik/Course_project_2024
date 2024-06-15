package tests.task4;

import front.managers.DriverManager;
import front.managers.ManagerInitiallization;
import front.task4.AvitoPage;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

public class FavoriteTest {
    private final DriverManager driverManager = DriverManager.getInstance();

    @BeforeClass
    public static void beforeClass() {
        ManagerInitiallization.initFramework();
    }

    @Before
    public void before() {
        driverManager.getDriver().get("https://www.avito.ru/");
    }

    @AfterClass
    public static void after() {
        ManagerInitiallization.quitFramework();
    }

    @Test
    @DisplayName("Проверка корректного добавления товаров в избранное ")
    public void test() throws InterruptedException {
        AvitoPage avitoPage = new AvitoPage();
        avitoPage.checkOpenPage()
                .clickOnCatalog()
                .moveToCategory("Электроника")
                .clickOnMenuItem("Мобильные телефоны")
                .logFirstFiveProducts()
                .addFirstProductToFavorites()
                .verifyAddToFavoritesSuccess()
                .rememberFirstProduct()
                .goToFavorites()
                .verifyProductInFavorites()
                .removeFromFavorites()
                .verifyAllItemsZero()
                .refreshPage()
                .verifyEmptyFavoritesMessage();
    }
}
