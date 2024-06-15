package tests.task2;

import front.managers.DriverManager;
import front.managers.ManagerInitiallization;
import front.task2.PolytechPage;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testng.annotations.AfterClass;

public class SheduleTest {
    private final DriverManager driverManager = DriverManager.getInstance();

    @BeforeClass
    public static void beforeClass(){
        ManagerInitiallization.initFramework();
    }

    @Before
    public void before(){
        driverManager.getDriver().get("https://mospolytech.ru/");
    }

    @Test
    @DisplayName("Тестирование страницы расписания на сайте Мосполитеха")
    public void test(){
        PolytechPage polytechPage = new PolytechPage();
        polytechPage.verifyPageTitle().
                clickHamburgerMenuButton()
                .clickOnMainMenuItem("Обучающимся")
                .clickOnMenuItem("Расписания")
                .checkOpenPageAndCorrectTitle()
                .clickOnButton()
                .checkOpenPageAndCorrectTitle()
                .inputGroupNumber("234-222")
                .clickOnGroup("234-222");
    }

    @AfterClass
    public static void after(){
        ManagerInitiallization.quitFramework();
    }
}
