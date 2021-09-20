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

        denimPage = new DenimPage(getDriver());
        getExtentTest().assignAuthor("Jackie Natt");
        getExtentTest().assignDevice("OS: Mac");
    }

    @Test(testName = "Verify Denim Page", description = "Verify Title & Print Text")
    public void test01() {

        denimPage.click(denimPage.denimBtn);
        denimPage.threadSleepMethod(3000);

        String expected = "Denim – BLUE IN GREEN SOHO";
        String actual = denimPage.getTitleMethod();

        if (actual.equals(expected)) {
            getExtentTest().pass(MarkupHelper.createLabel("PASSED: " + actual, ExtentColor.GREEN));
        } else {
            getExtentTest().fail(MarkupHelper.createLabel("FAILED: " + actual, ExtentColor.RED));
        }

        Assert.assertEquals(actual, expected);
        logScreenshotPic("Verify Denim Page");

        System.out.println(denimPage.denimText.getText());
    }

    @Test(testName = "Verify Checkbox", description = "Verify All Checkboxes")
    public void test02() {

        denimPage.click(denimPage.denimBtn);
        denimPage.threadSleepMethod(3000);

        for (int ch = 0; ch < denimPage.checkBoxes.size(); ch++) {

            Assert.assertTrue(denimPage.checkBoxes.get(ch).isDisplayed());
            System.out.println(denimPage.checkBoxes.get(ch).getText());
        }

        denimPage.scroll(0, 400);
        logScreenshotPic("Verify Checkbox");
    }
}