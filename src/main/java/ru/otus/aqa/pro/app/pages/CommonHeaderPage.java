package ru.otus.aqa.pro.app.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class CommonHeaderPage extends AbsBasePage<CommonHeaderPage> {

  public CommonHeaderPage(WebDriver driver) {
    super(driver);
  }

  private By blockWithAllCategoriesFromEducationButtonAtHeader = By.xpath(
      "//p[contains(text(), 'Все курсы')]/following-sibling::div"
  );

  private By someCategoryFromBlockWithAllCoursesFromEducationButtonAtHeader = By.xpath(
      ".//a[contains(@href, 'categories')]"
  );

  public WebElement selectRandomCategoryFromEducationMenuAtHeader() {
    WebElement blockWithAllCategories = driver.findElement(blockWithAllCategoriesFromEducationButtonAtHeader);

    List<WebElement> allCategories = blockWithAllCategories.findElements(someCategoryFromBlockWithAllCoursesFromEducationButtonAtHeader);

    // Exclude category "Специализация" from list
    List<WebElement> filteredCategories = allCategories.stream()
        .filter(category -> !category.getAttribute("href").contains("/spec"))
        .collect(Collectors.toList());

    if (filteredCategories.isEmpty()) {
      throw new NoSuchElementException("No one category is not found");
    }

    WebElement randomCategory = filteredCategories.get(ThreadLocalRandom.current().nextInt(filteredCategories.size()));

    return randomCategory;
  }

  public CoursesPage openRandomCategory(WebElement someCategory) {

    clickViaWebElement(someCategory);

    return coursesPage;
  }









}
