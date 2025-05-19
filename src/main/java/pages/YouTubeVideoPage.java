package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.logging.Logger;

import java.time.Duration;
import java.util.List;

public class YouTubeVideoPage {
    private static final Logger LOGGER = Logger.getLogger(YouTubeVideoPage.class.getName());
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы плейлиста/списка видео на странице видео
    private final By videosTab = By.xpath("//yt-chip-cloud-chip-renderer//yt-formatted-string[text()='Видео']");
    private final By channelAvatar = By.xpath("//yt-img-shadow[@id='avatar']//img[@id='img' and contains(@src, 'https://yt3.ggpht.com')]");
    private final By subscribeButton = By.xpath("//button[contains(@class, 'yt-spec-button-shape-next') and contains(@class, 'yt-spec-button-shape-next--filled')]");
    private final By signInButton = By.xpath("//div[@class='buttons style-scope ytd-modal-with-title-and-button-renderer']//span[@class='yt-core-attributed-string yt-core-attributed-string--white-space-no-wrap' and text()='Войти']");

    public YouTubeVideoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void waitForChannelAvatar() {
        LOGGER.info("Ожидаем аватар канала отправителя видео");

        try {
            // Ожидаем, пока аватар станет доступным
            WebElement avatar = wait.until(ExpectedConditions.visibilityOfElementLocated(channelAvatar));
            LOGGER.info("Аватар найден: " + avatar.getAttribute("outerHTML"));
        } catch (Exception e) {
            LOGGER.severe("Не удалось найти аватар канала: " + e.getMessage());
            throw new RuntimeException("Не удалось найти аватар канала", e);
        }
    }

    public void clickVideosTab() {
        LOGGER.info("Проверяем наличие вкладки 'Видео'");

        List<WebElement> videoTabElements = driver.findElements(videosTab);

        if (videoTabElements.isEmpty()) {
            LOGGER.warning("Вкладка 'Видео' не найдена. Продолжаем без фильтрации.");
            return;
        }

        try {
            WebElement videoTab = wait.until(ExpectedConditions.elementToBeClickable(videosTab));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", videoTab);
            videoTab.click();
            LOGGER.info("Кликнули по вкладке 'Видео'");
        } catch (Exception e) {
            LOGGER.warning("Вкладка 'Видео' найдена, но не удалось кликнуть: " + e.getMessage());
            LOGGER.warning("Продолжаем выполнение теста без фильтрации по 'Видео'");
        }
    }


    public void clickChannelAvatar() {
        try {
            LOGGER.info("Попытка кликнуть на аватар канала");
            WebElement avatar = wait.until(ExpectedConditions.elementToBeClickable(channelAvatar));

            // Прокручиваем страницу до аватара с улучшенной прокруткой
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", avatar);
            LOGGER.info("Прокручиваем страницу до аватара с улучшенной прокруткой");

            // Добавим ожидание, чтобы элемент был полностью видим
            wait.until(ExpectedConditions.visibilityOf(avatar));

            // После прокрутки пытаемся кликнуть
            avatar.click();
            LOGGER.info("Кликнули на аватар канала");
        } catch (Exception e) {
            LOGGER.severe("Не удалось кликнуть на аватар: " + e.getMessage());
            throw new RuntimeException("Не удалось кликнуть на аватар", e);
        }
    }

    public void clickSubscribeButton() {
        LOGGER.info("Кликаем на кнопку 'ПОДПИСАТЬСЯ'");
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(subscribeButton));
        button.click();
    }

    public void clickFourthVideoFromSearchResults() {
        LOGGER.info("Ожидаем список видео и кликаем по четвёртому видео из результатов поиска");

        By fourthVideoLocator = By.xpath("(//ytd-video-renderer//a[@id='thumbnail'])[4]");

        try {
            LOGGER.info("Ожидаем, пока видео станет кликабельным");
            WebElement fourthVideo = wait.until(ExpectedConditions.elementToBeClickable(fourthVideoLocator));

            LOGGER.info("Прокручиваем страницу, чтобы элемент был видим");
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", fourthVideo);
            wait.until(ExpectedConditions.visibilityOf(fourthVideo));

            LOGGER.info("Кликаем по элементу через JavaScript");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", fourthVideo);

            LOGGER.info("Кликнули по четвёртому видео из результатов поиска");
        } catch (Exception e) {
            LOGGER.severe("Ошибка при попытке кликнуть по 4-му видео: " + e.getMessage());
            throw new RuntimeException("Не удалось кликнуть по четвёртому видео", e);
        }
    }

    public void checkSignInButtonPresence() {
        LOGGER.info("Проверяем наличие кнопки 'Войти'");

        try {
            // Ожидаем, пока кнопка 'Войти' станет видимой
            WebElement signInButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(signInButton));

            if (signInButtonElement != null && signInButtonElement.isDisplayed()) {
                LOGGER.info("Кнопка 'Войти' найдена");
            } else {
                LOGGER.warning("Кнопка 'Войти' не найдена на странице");
            }
        } catch (Exception e) {
            LOGGER.severe("Ошибка при проверке кнопки 'Войти': " + e.getMessage());
            throw new RuntimeException("Ошибка при проверке кнопки 'Войти'", e);
        }
    }
}



