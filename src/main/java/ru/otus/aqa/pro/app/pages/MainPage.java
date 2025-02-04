package ru.otus.aqa.pro.app.pages;

import org.openqa.selenium.WebDriver;
import ru.otus.aqa.pro.app.annotations.PathPage;

@PathPage("/")
public class MainPage extends AbsBasePage<MainPage> {

  public MainPage(WebDriver driver) {
    super(driver);
  }


}
