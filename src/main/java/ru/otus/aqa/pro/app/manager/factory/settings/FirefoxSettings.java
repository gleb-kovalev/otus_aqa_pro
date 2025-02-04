package ru.otus.aqa.pro.app.manager.factory.settings;

import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

public class FirefoxSettings implements IBrowserSettings{


  @Override
  public AbstractDriverOptions settings() {
    FirefoxOptions firefoxOptions = new FirefoxOptions();

    //firefoxOptions.addArguments("-headless");

    return firefoxOptions;
  }
}
