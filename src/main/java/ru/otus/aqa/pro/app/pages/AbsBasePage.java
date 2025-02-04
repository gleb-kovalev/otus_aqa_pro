package ru.otus.aqa.pro.app.pages;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.aqa.pro.app.annotations.PathPage;
import ru.otus.aqa.pro.app.common.AbsCommon;


public abstract class AbsBasePage<T> extends AbsCommon {

  @Autowired
  protected LessonPage lessonPage;

  @Autowired
  protected CoursesPage coursesPage;

  @Autowired
  protected CommonHeaderPage commonHeaderPage;

  private String baseUrl = System.getProperty("base.url");

  public AbsBasePage(WebDriver driver) {
    super(driver);

    baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
  }


  @FindBy(xpath = "//a[contains(@href, '/cookie')]/parent::span/following-sibling::div//button")
  private WebElement cookieAcceptButton;

  @FindBy(tagName = "h1")
  private WebElement header;

  @FindBy(xpath = "//span[contains(@title, 'Обучение')]")
  private WebElement educationButtonAtHeader;



  public T openEducationMenuAtHeader() {
    clickViaWebElement(educationButtonAtHeader);
    return (T) this;
  }

  public T pageHeaderShouldBeSameAs(String expectedHeader) {
    waiters.waitForElementVisibleByElement(header);

    assertThat(header.getText())
        .as("Header should be", expectedHeader)
        .isEqualTo(expectedHeader);
    return (T) this;
  }

  public T open() {
    String path = getPath();
    if (path.isEmpty()) {
      throw new IllegalArgumentException(
          "The PathPage annotation for page is empty or doesn't exist"
      );
    }
    driver.get(baseUrl + path);

    waiters.waitForElementToBeClickable(cookieAcceptButton);

    clickViaWebElement(cookieAcceptButton);


    // TODO добавить ожидание полной загрузки страницы
    // TODO сейчас если аннотация PathPage пустая то метод возвращает "/" вместо исключения
    //  решить это

    return (T) this;
  }

  private String getPath() {
    Class<? extends AbsBasePage> clazz = this.getClass();

    if (clazz.isAnnotationPresent(PathPage.class)) {
      PathPage path = clazz.getDeclaredAnnotation(PathPage.class);
      return path.value().startsWith("/") ? path.value() : "/" + path.value();
    }
    return "";
  }


}
