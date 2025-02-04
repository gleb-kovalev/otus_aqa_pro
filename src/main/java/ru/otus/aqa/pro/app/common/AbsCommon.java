package ru.otus.aqa.pro.app.common;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ru.otus.aqa.pro.app.common.waiters.Waiters;
import java.time.Duration;

public abstract class AbsCommon {

  protected WebDriver driver;
  protected Waiters waiters;


  public AbsCommon(WebDriver driver) {
    this.driver = driver;
    this.waiters = new Waiters(driver);

    PageFactory.initElements(driver, this);
  }

  public void clickViaWebElement(WebElement webElement) {
    int attempts = 0;
    int maxAttempts = 20;
    Duration waitTime = Duration.ofMillis(500);

    while (attempts < maxAttempts) {
      try {
        webElement.click();
        return; // Клик успешен, выходим из метода
      } catch (ElementClickInterceptedException e) {
        attempts++;
        if (attempts >= maxAttempts) {
          throw new RuntimeException("Failed to click on the element after " + maxAttempts + " attempts", e);
        }
        try {
          Thread.sleep(waitTime.toMillis()); // Ожидание перед следующей попыткой
        } catch (InterruptedException ie) {
          Thread.currentThread().interrupt(); // Восстановить статус прерывания
          throw new RuntimeException("Thread interrupted while attempting to click", ie);
        }
      }
    }
  }

}
