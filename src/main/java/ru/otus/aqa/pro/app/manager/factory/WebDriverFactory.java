package ru.otus.aqa.pro.app.manager.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import ru.otus.aqa.pro.app.manager.factory.settings.ChromeSettings;
import ru.otus.aqa.pro.app.manager.factory.settings.FirefoxSettings;
import ru.otus.aqa.pro.app.listener.MouseListener;

public class WebDriverFactory {
  private String browserName = System.getProperty("browser.name");

  public WebDriver createDriver() {
    WebDriver driver;

    switch (browserName) {
      case "chrome": {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver((ChromeOptions) new ChromeSettings().settings());
        break;
      }
      case "firefox": {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver((FirefoxOptions) new FirefoxSettings().settings());
        break;
      }
      default:
        throw new IllegalArgumentException(String.format("Unknown browser %s", browserName));
    }

    return new EventFiringDecorator<>(new MouseListener()).decorate(driver);
  }


}
