package tests;

import base.BaseTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DenimPage;

public class DenimTest extends BaseTest {

    DenimPage denimPage;

    @BeforeMethod
    public void setUp() {

        denimPage = new DenimPage(driver);
    }

    @Test(testName = "Denim Page", description = "Verify Title & Print Text")
    public void test01() {

        extentTest.assignAuthor("Jackie Natt");
        extentTest.assignDevice("OS: Mac");
        denimPage.click(denimPage.denimBtn);
        denimPage.threadSleepMethod(3000);

        String expected = "Denim – BLUE IN GREEN SOHO";
        String actual = denimPage.getTitleMethod();

        if (actual.equals(expected)) {
            extentTest.pass(MarkupHelper.createLabel("PASSED: " + actual, ExtentColor.GREEN));
        } else {
            extentTest.fail(MarkupHelper.createLabel("FAILED: " + actual, ExtentColor.RED));
        }

        Assert.assertEquals(actual, expected);
        logScreenShot("Denim Page");

        System.out.println(denimPage.denimText.getText());
    }
}