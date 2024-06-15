package front.task4;

public class PageManagerHelper {
    private static PageManagerHelper INSTANCE = null;
    private AvitoPage avitoPage;
    private TelephonePage telephonePage;
    private FavoritesPage favoritesPage;

    private DetailPage detailPage;

    private PageManagerHelper() {
    }

    public static PageManagerHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PageManagerHelper();
        }
        return INSTANCE;
    }

    public AvitoPage getAvitoPage() {
        if (avitoPage == null) {
            avitoPage = new AvitoPage();
        }
        return avitoPage;
    }

    public TelephonePage getTelephonePage() {
        if (telephonePage == null) {
            telephonePage = new TelephonePage();
        }
        return telephonePage;
    }

    public DetailPage getDetailPage() {
        if (detailPage == null) {
            detailPage = new DetailPage();
        }
        return detailPage;
    }

    public FavoritesPage getFavoritesPage(String expectedName, String expectedPrice) {
        if (favoritesPage == null) {
            favoritesPage = new FavoritesPage(expectedName, expectedPrice);
        }
        return favoritesPage;
    }

}
