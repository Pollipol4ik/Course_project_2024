package tests.task1;


import front.managers.DriverManager;
import front.managers.ManagerInitiallization;
import front.task1.TodoPage;
import io.qameta.allure.junit4.DisplayName;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TodoListTest {

    private final DriverManager driverManager = DriverManager.getInstance();

    @BeforeClass
    public static void beforeClass() {
        ManagerInitiallization.initFramework();
    }

    @Before
    public void before() {
        driverManager.getDriver().get("https://lambdatest.github.io/sample-todo-app/");
    }

    @AfterClass
    public static void after() {
        ManagerInitiallization.quitFramework();
    }

    @Test
    @DisplayName("Тестирование списка дел 'LambdaTest Sample App'")
    public void test() {
        TodoPage startPage = new TodoPage();
        startPage.checkText()
                .checkItemNotDone("First Item")
                .markItemAsDone("First Item")
                .checkItemNotDone("Second Item")
                .markItemAsDone("Second Item")
                .checkItemNotDone("Third Item")
                .markNewItemAsDone("Third Item")
                .checkItemNotDone("Fourth Item")
                .markNewItemAsDone("Fourth Item")
                .checkItemNotDone("Fifth Item")
                .markNewItemAsDone("Fifth Item")
                .addNewItem("Sixth Item")
                .checkItemNotDone("Sixth Item")
                .markNewItemAsDone("Sixth Item");
    }


}