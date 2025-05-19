package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.logging.Logger;

import java.time.Duration;

public class YouTubeSearchResultsPage {
    private static final Logger LOGGER = Logger.getLogger(YouTubeSearchResultsPage.class.getName());
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By searchResults = By.xpath("//ytd-video-renderer//a[@id='thumbnail']");

    public YouTubeSearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickSecondSearchResult() {
        LOGGER.info("Ожидаем и кликаем по второму результату поиска");
        By secondResult = By.xpath("(//div[@role='presentation'])[2]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(secondResult));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
        LOGGER.info("Кликнули по второму результату поиска");
    }
}

