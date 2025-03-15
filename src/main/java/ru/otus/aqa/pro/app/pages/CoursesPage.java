package ru.otus.aqa.pro.app.pages;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.otus.aqa.pro.app.annotations.PathPage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@PathPage("/catalog/courses")
public class CoursesPage extends AbsBasePage<CoursesPage> {


  public CoursesPage(WebDriver driver) {
    super(driver);
  }


  private By coursesNameFromAvatarCourse = By.xpath(
      "./h6"
  );

  private By courseStartDateLocator = By.xpath(
      ".//div[contains(text(), 'месяц')]"
  );

  private By blockWithAllCategoriesAtCoursesPage = By.xpath(
      "//p[contains(text(), 'Направление')]/../following-sibling::div"
  );

  private By someCategoryFromBlockWithAllCategoriesAtCoursesPage = By.xpath(
      ".//label"
  );

  private By checkboxOfSomeCategoryFromBlockWithAllCategoriesAtCoursesPage = By.xpath(
      "./preceding-sibling::div/input"
  );


  public LessonPage openCourseCardByCourseName(String courseName) {
    var course = findCourseByName(courseName);
    clickViaWebElement(course);

    return lessonPage;
  }

  public String getRandomCourseName() {

    var allCourses = getAllCoursesVisibleInCatalogSection();

    Random rand = new Random();

    var randomCourse = allCourses.get(rand.nextInt(allCourses.size()));

    return getCourseName(randomCourse);

  }


  public WebElement getCourseWithEarliestStartDate() {
    HashMap<WebElement, LocalDate> courseAndStartDate = getCourseAndStartDate();

    WebElement earliestCourse = courseAndStartDate.entrySet().stream()
        .reduce((entry1, entry2) -> entry1.getValue().isBefore(entry2.getValue()) ? entry1 : entry2)
        .map(Map.Entry::getKey)
        .orElseThrow(() -> new NoSuchElementException("The list of course and start date is empty"));

    return earliestCourse;
  }

  public WebElement getCourseWithLatestStartDate() {
    HashMap<WebElement, LocalDate> courseAndStartDate = getCourseAndStartDate();

    WebElement latestCourse = courseAndStartDate.entrySet().stream()
        .reduce((entry1, entry2) -> entry1.getValue().isAfter(entry2.getValue()) ? entry1 : entry2)
        .map(entry -> entry.getKey())
        .orElseThrow(() -> new NoSuchElementException("The list of course and start date is empty"));

    return latestCourse;
  }

  public String getCourseName(WebElement course) {
    return course.findElement(coursesNameFromAvatarCourse).getText();
  }


  public String getStartDate(WebElement course) {
    String rawStartDate = course.findElement(courseStartDateLocator).getText();

    return rawStartDate.split(", ")[0];
  }

  public LessonPage openCourse(WebElement course) {
    clickViaWebElement(course);

    return lessonPage;
  }

  private List<WebElement> getAllCoursesVisibleInCatalogSection() {
    var courses = driver.findElements(By.xpath("//main//section//a[contains(@href, '/lessons/')]"));

    return courses;
  }

  private WebElement findCourseByName(String courseName) {
    List<WebElement> allAvatarsOfCourses = getAllCoursesVisibleInCatalogSection();

    return allAvatarsOfCourses.stream()
        .filter(course -> course
            .findElement(coursesNameFromAvatarCourse)
            .getText()
            .contains(courseName)
        )
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("Course with name " + courseName + " not found!"));
  }

  private LocalDate parseCourseStartDates(String rawDates) {
    DateTimeFormatter formatterForDateFromAvatarsOfCourses =
        DateTimeFormatter.ofPattern("d MMMM, yyyy", java.util.Locale.forLanguageTag("ru"));

    String datePart = rawDates.split(" · ")[0].trim();

    return LocalDate.parse(datePart, formatterForDateFromAvatarsOfCourses);
  }

  private HashMap<WebElement, LocalDate> getCourseAndStartDate() {
    HashMap<WebElement, LocalDate> courseAndStartDate = new HashMap<>();

    List<WebElement> courses = getAllCoursesVisibleInCatalogSection();

    for (WebElement course : courses) {
      WebElement dateElement = course.findElement(courseStartDateLocator);
      String rawDate = dateElement.getText();
      LocalDate parsedDate = parseCourseStartDates(rawDate);
      courseAndStartDate.put(course, parsedDate);
    }

    return courseAndStartDate;
  }


  public CoursesPage selectedCategoryShouldBeAs(String expectedCategoryName) {
    WebElement blockWithAllCategories =  driver.findElement(blockWithAllCategoriesAtCoursesPage);

    List<WebElement> allCategories = blockWithAllCategories.findElements(someCategoryFromBlockWithAllCategoriesAtCoursesPage);

    WebElement selectedCheckbox = allCategories.stream().filter(category -> category
        .findElement(checkboxOfSomeCategoryFromBlockWithAllCategoriesAtCoursesPage).isSelected())
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("The selected checkbox is not found"));

    // from checkbox go back to parent element to get category name
    String categoryName = selectedCheckbox.findElement(By.xpath("./..")).getText();

    var newExpectedCategoryName = expectedCategoryName.replaceAll("\\s*\\(\\d+\\)", "");
    assertThat(categoryName)
        .as("Category name should be ", newExpectedCategoryName)
        .isEqualTo(newExpectedCategoryName);

    return coursesPage;
  }


}

