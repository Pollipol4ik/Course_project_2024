package tests.task4;

import front.managers.DriverManager;
import front.managers.ManagerInitiallization;
import front.task4.AvitoPage;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testng.annotations.AfterClass;

public class CatalogTest {
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
    @DisplayName("Проверка открытия и навигации по категории товаров")
    public void test() throws InterruptedException {
        AvitoPage avitoPage = new AvitoPage();
        avitoPage.checkOpenPage()
                .clickOnCatalog()
                .moveToCategory("Электроника")
                .clickOnMenuItem("Мобильные телефоны")
                .logFirstFiveProducts().setSortingByPriceAscending()
                .verifyProductsSortedByPriceAscending(15);

    }
}
