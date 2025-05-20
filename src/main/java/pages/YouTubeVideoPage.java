package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;
import java.util.logging.Logger;

import java.time.Duration;

public class YouTubeVideoPage {
    private static final Logger LOGGER = Logger.getLogger(YouTubeVideoPage.class.getName());
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By signInButton = By.xpath("//div[@class='buttons style-scope ytd-modal-with-title-and-button-renderer']//span[@class='yt-core-attributed-string yt-core-attributed-string--white-space-no-wrap' and text()='Войти']");
    private final By fourthVideoLocator = By.xpath("(//ytd-video-renderer//a[@id='thumbnail'])[4]");

    public YouTubeVideoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void clickFourthVideoFromSearchResults() {
        LOGGER.info("Ожидаем список видео и кликаем по четвёртому видео из результатов поиска");
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

    public void clickChannelAvatar() {
        LOGGER.info("Пытаемся кликнуть на аватар (с учётом Shorts)");
        try {
            String url = driver.getCurrentUrl();
            By avatarLocator;

            if (url.contains("/shorts/")) {
                LOGGER.info("Shorts: ищем аватар с кнопкой");
                avatarLocator = By.xpath("//yt-reel-channel-bar-view-model//div[@role='button' and contains(@class,'yt-spec-avatar-shape__button')]");
            } else {
                LOGGER.info("Обычное видео: ссылка на канал");
                avatarLocator = By.xpath("//ytd-video-owner-renderer//a[contains(@href, '/@')]");
            }
            WebElement avatar = wait.until(ExpectedConditions.presenceOfElementLocated(avatarLocator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", avatar);
            wait.until(ExpectedConditions.elementToBeClickable(avatar));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", avatar);
            LOGGER.info("Клик по аватару выполнен");
        } catch (Exception e) {
            LOGGER.severe("Ошибка при клике на аватар: " + e.getMessage());
            throw new RuntimeException("Клик по аватару не удался", e);
        }
    }

    public void clickSubscribeButton() {
        LOGGER.info("Пытаемся кликнуть на кнопку 'ПОДПИСАТЬСЯ'");
        try {
            // Переключаемся на активную вкладку YouTube
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
                if (driver.getCurrentUrl().contains("youtube.com")) break;
            }

            String url = driver.getCurrentUrl();
            LOGGER.info("Текущий URL: " + url);

            By subscribeLocator;

            if (url.contains("/shorts/")) {
                LOGGER.info("Shorts: ищем кнопку 'Подписаться на канал'");
                subscribeLocator = By.xpath("//button[contains(@aria-label, 'Подписаться на канал')]");
            } else if (url.contains("/watch")) {
                LOGGER.info("Обычное видео: ищем кнопку 'Оформить подписку'");
                subscribeLocator = By.xpath("//button[contains(@aria-label, 'Оформить подписку')]");
            } else if (url.contains("/@")) {
                LOGGER.info("Страница канала: ищем кнопку 'Подписаться'");
                subscribeLocator = By.xpath("//button//div[contains(text(), 'Подписаться')]");
            } else {
                throw new RuntimeException("Неизвестный тип страницы: не можем определить кнопку 'Подписаться'");
            }

            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(subscribeLocator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);

            LOGGER.info("Клик по кнопке 'Подписаться' выполнен");
        } catch (Exception e) {
            LOGGER.severe("Не удалось кликнуть по кнопке 'ПОДПИСАТЬСЯ': " + e.getMessage());
            throw new RuntimeException("Ошибка при клике на кнопку 'Подписаться'", e);
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
    public void closeAllExceptYouTubeTab() {
        Set<String> windowHandles = driver.getWindowHandles();
        String youtubeHandle = null;

        for (String handle : windowHandles) {
            driver.switchTo().window(handle);
            String url = driver.getCurrentUrl();

            if (url.contains("youtube.com")) {
                youtubeHandle = handle;
            }
        }
        // Если нашли YouTube-вкладку — закрываем все остальные
        for (String handle : windowHandles) {
            if (!handle.equals(youtubeHandle)) {
                driver.switchTo().window(handle);
                LOGGER.info("Закрываем постороннюю вкладку: " + driver.getCurrentUrl());
                driver.close();
            }
        }
        // Переключаемся обратно на YouTube
        if (youtubeHandle != null) {
            driver.switchTo().window(youtubeHandle);
            LOGGER.info("Вернулись к вкладке YouTube: " + driver.getCurrentUrl());
        } else {
            LOGGER.severe("Не найдено ни одной вкладки с YouTube");
        }
    }
}



