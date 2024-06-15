package front.task3;

import front.task2.GroupPage;
import front.task2.PolytechPage;
import front.task2.SchedulePage;

public class PageManagerHelper {
    private static PageManagerHelper INSTANCE = null;
    private PolytechPage polytechPage;
    private SchedulePage schedulePage;
    private GroupPage groupPage;
    private XboxPage xboxPage;
    private FavoritesPage favoritesPage;

    private PageManagerHelper() {
    }

    public static PageManagerHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PageManagerHelper();
        }
        return INSTANCE;
    }

    public PolytechPage getStartPage() {
        if (polytechPage == null) {
            polytechPage = new PolytechPage();
        }
        return polytechPage;
    }

    public SchedulePage getSchedulePage() {
        if (schedulePage == null) {
            schedulePage = new SchedulePage();
        }
        return schedulePage;
    }

    public GroupPage getScheduleGroupPage() {
        if (groupPage == null) {
            groupPage = new GroupPage();
        }
        return groupPage;
    }

    public XboxPage getXboxPage() {
        if (xboxPage == null) {
            xboxPage = new XboxPage();
        }
        return xboxPage;
    }

    public FavoritesPage getFavoritesPage(String expectedName, String expectedPrice) {
        if (favoritesPage == null) {
            favoritesPage = new FavoritesPage(expectedName, expectedPrice);
        }
        return favoritesPage;
    }
}
