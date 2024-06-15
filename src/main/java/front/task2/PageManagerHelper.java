package front.task2;


public class PageManagerHelper {
    private static PageManagerHelper INSTANCE = null;
    private PolytechPage polytechPage;
    private SchedulePage schedulePage;
    private GroupPage groupPage;


    private PageManagerHelper() {
    }

    public static PageManagerHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PageManagerHelper();
        }
        return INSTANCE;
    }

    public PolytechPage getPolytechPage() {
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

    public GroupPage getGroupPage() {
        if (groupPage == null) {
            groupPage = new GroupPage();
        }
        return groupPage;
    }
}
