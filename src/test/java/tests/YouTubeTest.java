package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.YouTubeHomePage;
import pages.YouTubeSearchResultsPage;
import pages.YouTubeVideoPage;

import java.util.Set;
import java.util.logging.Logger;
import java.util.Random;

public class YouTubeTest {
    private static final Logger LOGGER = Logger.getLogger(YouTubeTest.class.getName());

    private WebDriver driver;
    private YouTubeHomePage homePage;
    private YouTubeSearchResultsPage searchResultsPage;
    private YouTubeVideoPage videoPage;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        homePage = new YouTubeHomePage(driver);
        searchResultsPage = new YouTubeSearchResultsPage(driver);
        videoPage = new YouTubeVideoPage(driver);
    }

    @Test
    public void testUserFlow() throws InterruptedException {
        // 1. Открыть YouTube
        homePage.open();
        LOGGER.info("Тест начался");

        // 2. Проверить, что вкладка браузера называется “YouTube”
        Assert.assertEquals(homePage.getTitle(), "YouTube");

        // 3. Ввести запрос из 2-4 случайных цифр
        String query = generateRandomDigits(2 + new Random().nextInt(3));
        homePage.enterSearchQuery(query);

        // 4. Сделать браузер во весь экран
        homePage.maximizeWindow();

        // 5. Кликнуть по второму элементу в списке результатов поиска
        searchResultsPage.clickSecondSearchResult();

        // 6. На странице видео кликнуть по четвёртому видео из списка видео
        videoPage.clickFourthVideoFromSearchResults();

        // 7. Кликнуть на аватар отправителя видео
        videoPage.clickChannelAvatar();

        // 8. Кликнуть на кнопку “ПОДПИСАТЬСЯ”
        videoPage.clickSubscribeButton();
        videoPage.closeAllExceptYouTubeTab();

        // 9. Проверить, что текст “ВОЙТИ“ отображается
        videoPage.checkSignInButtonPresence();
        LOGGER.info("Тест успешно пройден");
    }

    private String generateRandomDigits(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            try {
                Set<String> windowHandles = driver.getWindowHandles();
                for (String handle : windowHandles) {
                    driver.switchTo().window(handle);
                    driver.close();
                }
            } catch (Exception e) {
                System.out.println("Ошибка при закрытии окон: " + e.getMessage());
            } finally {
                driver.quit();
            }
        }
    }
}
