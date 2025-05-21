# 🎯 YouTube Selenium Automation Tests

Автоматизированный UI-тест пользовательского сценария на сайте [YouTube](https://www.youtube.com/), реализованный с использованием Java, Selenium и TestNG по паттерну Page Object.

## 🧪 Что проверяет тест

1. Открытие главной страницы YouTube
2. Проверка заголовка вкладки "YouTube"
3. Ввод случайного поискового запроса (от 2 до 4 цифр)
4. Клик по второму элементу в списке результатов поиска (не видео)
5. Клик по четвёртому видео из результатов поиска
6. Клик по аватару отправителя видео
7. Клик на кнопку “Подписаться”
8. Проверка появления текста “ВОЙТИ”
9. Закрытие вкладки браузера

## 💻 Стек технологий

- Java 17+
- Selenium WebDriver
- TestNG
- Maven
- WebDriverManager
- Page Object Pattern

## 📦 Установка и запуск

1. Клонируй репозиторий:

```bash
git clone https://github.com/juliyakomeristaya/youtube-selenium-tests.git
cd youtube-selenium-tests

mvn clean install

mvn test

Структура проекта
pages/ — Page Object классы (YouTubeHomePage, SearchResultsPage, VideoPage)

tests/ — Тестовый класс YouTubeTest

testng.xml — Конфигурация для запуска с TestNG

pom.xml — Maven зависимости (Selenium, TestNG, WebDriverManager и т.д.)

🧰 Зависимости
Selenium

TestNG

WebDriverManager

Maven

📌 Примечания
Браузер по умолчанию — Google Chrome

WebDriverManager автоматически подтягивает нужную версию ChromeDriver

Перед запуском убедись, что Chrome установлен и обновлён


👩‍💻 Автор
Juliyakomeristaya

