package ru.otus.aqa.pro.app.common.waiters;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Waiters {
  private WebDriverWait webDriverWait;

  public Waiters(WebDriver driver) {
    this.webDriverWait = new WebDriverWait(
        driver,
        Duration.ofSeconds(Integer.parseInt(System.getProperty("webdriver.waiter.timeout")))
    );
  }

  private boolean waitForCondition(ExpectedCondition condition) {
    try {
      webDriverWait.until(condition);
      return true;
    } catch (TimeoutException ignored) {
      return false;
    }
  }

  public boolean waitForElementToBeClickable(WebElement element) {
    return this.waitForCondition(ExpectedConditions.elementToBeClickable(element));
  }

  public boolean waitForElementVisibleByElement(WebElement element) {
    return this.waitForCondition(ExpectedConditions.visibilityOf(element));
  }


}
