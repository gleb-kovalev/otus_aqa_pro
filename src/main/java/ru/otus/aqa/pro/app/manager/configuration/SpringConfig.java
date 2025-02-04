package ru.otus.aqa.pro.app.manager.configuration;

import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.otus.aqa.pro.app.manager.factory.WebDriverFactory;
import ru.otus.aqa.pro.app.pages.CommonHeaderPage;
import ru.otus.aqa.pro.app.pages.CoursesPage;
import ru.otus.aqa.pro.app.pages.LessonPage;
import ru.otus.aqa.pro.app.pages.MainPage;


@Configuration
public class SpringConfig {

  private WebDriver driver;

  public SpringConfig() {
    driver = new WebDriverFactory().createDriver();
  }

  @Bean
  @Scope("prototype")
  public WebDriver getDriver() {
    return this.driver;
  }

  @Bean
  public CoursesPage coursesPage() {
    return new CoursesPage(driver);
  }

  @Bean
  public LessonPage lessonPage() {
    return new LessonPage(driver);
  }

  @Bean
  public MainPage mainPage() {
    return new MainPage(driver);
  }

  @Bean
  public CommonHeaderPage commonHeaderPage() {
    return new CommonHeaderPage(driver);
  }


}
