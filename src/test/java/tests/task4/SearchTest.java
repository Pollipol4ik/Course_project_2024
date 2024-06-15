package tests.task4;

import front.managers.DriverManager;
import front.managers.ManagerInitiallization;
import front.task4.AvitoPage;
import io.qameta.allure.junit4.DisplayName;
import org.checkerframework.checker.units.qual.A;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

public class SearchTest {
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
        avitoPage.enterSearchTextAndSelectFirstResult("iphone 11")
                .setPriceRange(10000, 30000).showMoreFilters().selectMemory256GB()
        .applyFilters()
                .verifySearchResults();

    }
}
