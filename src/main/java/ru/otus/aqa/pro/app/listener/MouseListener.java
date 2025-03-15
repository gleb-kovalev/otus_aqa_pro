package ru.otus.aqa.pro.app.listener;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.events.WebDriverListener;

public class MouseListener implements WebDriverListener {

  private void highlightElement(WebElement element, WebDriver driver) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].style.border='3px solid red'", element);
  }

  @Override
  public void beforeClick(WebElement element) {
    highlightElement(element, ((RemoteWebElement) element).getWrappedDriver());
  }

  @Override
  public void beforeFindElement(WebElement element, By locator) {
    highlightElement(element, ((RemoteWebElement) element).getWrappedDriver());
  }


}
