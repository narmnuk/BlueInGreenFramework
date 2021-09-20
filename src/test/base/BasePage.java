package base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

    private WebDriver driver;

    public BasePage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getTitleMethod() {

        return driver.getTitle();
    }

    public void scroll(int number) {

        ((JavascriptExecutor)driver).executeScript("window.scrollBy(0, " + number + ")");
    }

    public void click(WebElement element) {

        highLightElement(element);
        element.click();
    }

    public void threadSleepMethod(int milliSeconds) {

        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void highLightElement(WebElement element) {

        JavascriptExecutor javaEx = (JavascriptExecutor) driver;
        for (int hL = 0; hL < 4; hL++) {
            try {
                if (hL % 2 == 0) {
                    javaEx.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: black;" + "border: 3px solid orange; background: pink");
                } else {
                    threadSleepMethod(1000);
                    javaEx.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}