package ru.otus.aqa.pro.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.aqa.pro.app.manager.extension.UIExtension;
import ru.otus.aqa.pro.app.pages.CommonHeaderPage;
import ru.otus.aqa.pro.app.pages.CoursesPage;
import ru.otus.aqa.pro.app.pages.MainPage;


@ExtendWith(UIExtension.class)
public class CoursesCatalogTest {

  @Autowired
  private CoursesPage coursesPage;

  @Autowired
  private MainPage mainPage;

  @Autowired
  private CommonHeaderPage commonHeaderPage;


  @Test
  public void nameCourseAtAvatarTheSameAsAtCourseCard() {

    //TODO создать метод который будет собирать все названия курсов и возвращать рандомное название
    String testData = "QA Lead";

    coursesPage
        .open()
        .openCourseCardByCourseName(testData)
        .pageHeaderShouldBeSameAs(testData);
  }

  @Test
  public void earliestCourseHasCorrectNameAndStartDateAtCourseCard() {

    coursesPage.open();

    var earliestCourse = coursesPage.getCourseWithEarliestStartDate();
    var earliestCourseName = coursesPage.getCourseName(earliestCourse);
    var earliestCourseStartDate = coursesPage.getStartDate(earliestCourse);

    coursesPage
        .openCourse(earliestCourse)
        .pageHeaderShouldBeSameAs(earliestCourseName)
        .verifyCourseDetails(earliestCourseStartDate);
  }

  @Test
  public void latestCourseHasCorrectNameAndStartDateAtCourseCard() {

    coursesPage.open();
    var latestCourse = coursesPage.getCourseWithLatestStartDate();
    var latestCourseName = coursesPage.getCourseName(latestCourse);
    var latestCourseStartDate = coursesPage.getStartDate(latestCourse);

    coursesPage
        .openCourse(latestCourse)
        .pageHeaderShouldBeSameAs(latestCourseName)
        .verifyCourseDetails(latestCourseStartDate);
  }

  @Test
  public void openCorrectCourseCategory() {

    mainPage
        .open()
        .openEducationMenuAtHeader();

    var anyCategory = commonHeaderPage.selectRandomCategoryFromEducationMenuAtHeader();
    var anyCategoryName = anyCategory.getText();

    commonHeaderPage
        .openRandomCategory(anyCategory)
        .selectedCategoryShouldBeAs(anyCategoryName);
  }

}
