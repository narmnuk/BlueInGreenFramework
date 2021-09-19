package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DenimPage extends BasePage {

    public DenimPage(WebDriver driver) {

        super(driver);
    }

    @FindBy(css = "li:nth-child(4) > a#navigation-denim")
    public WebElement denimBtn;
    git push -u origin main
    @FindBy(css = "div[class$='rte'] > div:nth-child(2)")
    public WebElement denimText;

    }