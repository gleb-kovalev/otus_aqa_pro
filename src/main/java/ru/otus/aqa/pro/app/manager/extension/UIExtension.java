package ru.otus.aqa.pro.app.manager.extension;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class UIExtension implements BeforeEachCallback, AfterEachCallback {

  private ApplicationContext context;

  @Override
  public void beforeEach(ExtensionContext context) {
    // Создание контекста (поиск в данных для контекста в конкретном пакете)
    this.context = new AnnotationConfigApplicationContext("ru.otus.aqa.pro.app");

    // Получение экземпляра теста и внедрение зависимости (теперь аннотация @Autowired в тест классе примет контекст)
    Object testInstance = context.getTestInstance().orElseThrow();
    this.context.getAutowireCapableBeanFactory().autowireBean(testInstance);
  }

  @Override
  public void afterEach(ExtensionContext context) {
    // Закрытие WebDriver после теста
    WebDriver driver = this.context.getBean(WebDriver.class);
    if (driver != null) {
      driver.quit();
    }
  }

}
