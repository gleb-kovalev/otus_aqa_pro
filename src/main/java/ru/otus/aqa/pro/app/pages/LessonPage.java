package ru.otus.aqa.pro.app.pages;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;


public class LessonPage extends AbsBasePage<LessonPage> {
  public LessonPage(WebDriver driver) {
    super(driver);
  }


  public LessonPage verifyCourseDetails(String expectedCourseStartDate) {
    // Получаем HTML страницы
    String pageSource = driver.getPageSource();

    // Парсим HTML с помощью Jsoup
    Document doc = Jsoup.parse(pageSource);

    // Извлекаем все элементы <p> и проверяем их текст
    Elements paragraphs = doc.select("p");
    String actualCourseStartDate = "";
    for (Element paragraph : paragraphs) {
      if (paragraph.text().contains("Онлайн")) {
        // Ищем родительский элемент и его потомков
        Element parentDiv = paragraph.parent().parent(); // находим родителя
        actualCourseStartDate = parentDiv.select("div p").first().text(); // выбираем первый <p> в <div>
        break;
      }
    }

    assertThat(actualCourseStartDate)
        .as("Проверка даты старта курса")
        .isEqualTo(expectedCourseStartDate);

    return this;
  }

}
