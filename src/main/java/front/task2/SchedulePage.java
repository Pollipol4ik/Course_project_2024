package front.task2;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SchedulePage extends BasePage {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SchedulePage.class);

    @FindBy(xpath = "//h1")
    private WebElement title;

    @FindBy(xpath = "//a[@href='https://rasp.dmami.ru/']")
    private WebElement buttonSchedule;

    @Step("Проверка открытия страницы Расписания")
    public SchedulePage checkOpenPageAndCorrectTitle() {
        Assert.assertEquals("Заголовок не корректен! ",
                "Расписания",
                title.getText());
        logger.info("Проверка корректности открытия страницы 'Расписания'");
        return this;
    }

    @Step("Проверка нажатия на кнопку 'Смотреть на сайте'")
    public GroupPage clickOnButton() {
        scrollToElementJs(buttonSchedule);
        waitUntilElementToBeClickable(buttonSchedule).click();
        moveToNewTab();
        logger.info("Переход на страницу с расписанием");
        return pageManager.getGroupPage();
    }

}
