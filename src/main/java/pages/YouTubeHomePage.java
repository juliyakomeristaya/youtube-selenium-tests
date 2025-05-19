package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.logging.Logger;

import java.time.Duration;

public class YouTubeHomePage {
    private static final Logger LOGGER = Logger.getLogger(YouTubeHomePage.class.getName());
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By searchInput = By.name("search_query");
    private final By searchButton = By.id("search-icon-legacy");

    public YouTubeHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        LOGGER.info("Открываем главную страницу YouTube");
        driver.get("https://www.youtube.com/");
    }

    public String getTitle() {
        String title = driver.getTitle();
        LOGGER.info("Текущий заголовок страницы: " + title);
        return title;
    }

    public void maximizeWindow() {
        LOGGER.info("Разворачиваем браузер на весь экран");
        driver.manage().window().maximize();
    }

    public void enterSearchQuery(String query) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.clear();
        input.sendKeys(query);
        LOGGER.info("Вводим запрос в строку поиска: " + query);
    }

    public void clickSearch() {
        LOGGER.info("Нажимаем кнопку поиска");
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        button.click();
    }
}



