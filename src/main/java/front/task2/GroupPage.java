package front.task2;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class GroupPage extends BasePage {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(GroupPage.class);

    @FindBy(xpath = "//h4")
    private WebElement title;

    @FindBy(xpath = "//input[@placeholder='группа ...']")
    private WebElement inputGroup;

    @FindBy(xpath = "//div[contains(@class, 'found-groups')]/*")
    private List<WebElement> listGroup;

    @FindBy(xpath = "//div[contains(@class, 'schedule-day_today')]/div[contains(@class, 'title')]")
    private WebElement dayToday;

    @Step("Проверка корректности открытия страницы 'Расписание занятий'")
    public GroupPage checkOpenPageAndCorrectTitle() {
        String expectedTitle = "Расписание занятий";
        Assert.assertEquals("Заголовок не корректен! ", expectedTitle, title.getText());
        logger.info("Проверка корректности открытия страницы 'Расписание занятий'");
        return this;
    }

    @Step("Ввод группы {numberOfGroup}")
    public GroupPage inputGroupNumber(String numberOfGroup) {
        waitUntilElementToBeClickable(inputGroup).click();
        inputGroup.sendKeys(numberOfGroup);
        inputGroup.sendKeys(Keys.ENTER);
        waitForResults();
        assertOnlyDesiredGroupIsDisplayed(numberOfGroup);
        logger.info("Ввод группы " + numberOfGroup);
        return this;
    }

    @Step("Выбор расписания для группы {numberOfGroup}")
    public GroupPage clickOnGroup(String numberOfGroup) {
        for (WebElement group : listGroup) {
            if (group.getAttribute("id").equals(numberOfGroup)) {
                group.click();
                waitForResults();
                assertScheduleOpened(numberOfGroup);
                return this;
            }
        }
        Assert.fail("Ошибка: группа не найдена");
        return this;
    }

    private void waitForResults() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void assertOnlyDesiredGroupIsDisplayed(String numberOfGroup) {
        Assert.assertEquals("В результатах поиска отображается не только искомая группа", 1, listGroup.size());
        Assert.assertEquals("В результатах поиска не отображается искомая группа", numberOfGroup, findGroup(listGroup, numberOfGroup));
    }

    private void assertScheduleOpened(String numberOfGroup) {
        String expectedTitle = "Расписание " + numberOfGroup;
        Assert.assertEquals("Расписание не открылось ", expectedTitle, driverManager.getDriver().getTitle());
        if (!getCurrentDayOfWeek().equals("Воскресенье")) {
            Assert.assertEquals("Текущий день недели не выделен цветом", getCurrentDayOfWeek(), dayToday.getText());
        }
        logger.info("Выбор расписания для группы " + numberOfGroup);
    }
}
