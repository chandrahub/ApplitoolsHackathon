package base;

import com.applitools.eyes.selenium.fluent.Target;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AdsPage;
import pages.AppPage;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class VisualAITests extends BaseTests {

    //Login Page UI Elements Test

    @Test
    public void testLoginPageWithEyes() {
        Assert.assertNotNull(loginPage);
        getEyes().checkWindow("Login Window");
    }

    //Data-Driven Test

    @DataProvider
    public static Object[][] testDataForFailure() {
        return new Object[][] {
                {"", "", "Data Driven Tests"},
                {"chandra", "", "Data Driven Tests"},
                {"", "secret!", "Data Driven Tests"}
        };
    }

    @DataProvider
    public  static Object[][] testDataForSuccess() {
        return new Object[][] {
                {"chandra", "secret!", "Data Driven Tests"}
        };
    }

    @Test(dataProvider = "testDataForFailure")
    public void testLoginFailure(String username, String password, String testName) {
        String text = loginPage.doLoginFailure(username, password);
        eyes.check(testName, Target.window().ignoreDisplacements());
    }

    @Test(dataProvider = "testDataForSuccess")
    public void testLoginSuccess(String username, String password, String testName) {
        AppPage appPage = loginPage.doLoginSuccess(username, password);
        eyes.check(testName, Target.window().ignoreDisplacements());
    }


    //Table Sort Test

    @Test
    public void testAscendingSortedAmountListAndDataIntact() {
        var appPage = loginPage.doLoginSuccess("chandra", "secret!");
        appPage.clickOnAmount();
        eyes.checkWindow("Window with table");
    }


    //Canvas Chart Test

    @Test
    public void testBarChartAndData() throws IOException, InterruptedException {
        var appPage = loginPage.doLoginSuccess("chandra", "secret!");
        var chartPage = appPage.clickCompareExpensesLink();
        chartPage.addWait(2000);
        eyes.checkWindow("Canvas chart");
    }

    @Test
    public void testAddNextYearData() throws IOException, InterruptedException {
        var appPage = loginPage.doLoginSuccess("chandra", "secret!");
        var chartPage = appPage.clickCompareExpensesLink();
        chartPage.clickShowDataForNextYearButton();
        chartPage.addWait(2000);
        eyes.checkWindow("Canvas chart with next year data");
    }


    //Dynamic Content Test

    @Test
    public void testDisplayAdsPresence() throws IOException {
        String url = getAdUrl();
        AdsPage adsPage = loginPage.gotoAdsPage(url);
        eyes.checkWindow("Dynamic content");
    }


}
