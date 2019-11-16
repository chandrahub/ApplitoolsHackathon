package base;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AdsPage;
import pages.AppPage;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TraditionalTests extends BaseTests {

    //Login Page UI Elements Test

    @Test
    public void testLogoPresent() {
        assertTrue(loginPage.isLogoPresent(), "Logo is not present");
    }

    @Test
    public void testAuthHeaderPresent() {
        assertTrue(loginPage.isAuthHeaderPresent(), "Logo is not present");
    }

    @Test
    public void testAuthHeaderText() {
        assertEquals(loginPage.getAuthHeaderText(), "Login Form", "Login Form text is not present");
    }

    @Test
    public void testUsernameFieldPresent() {
        assertTrue(loginPage.isUsernameFieldPresent(), "Username field is not present");
    }

    @Test
    public void testUsernameLabelPresent() {
        assertTrue(loginPage.isUsernameLabelPresent(), "Username label is not present");
    }

    @Test
    public void testUsernamePlaceholderText() {
        assertEquals(loginPage.getUsernamePlaceholder(), "Enter your username", "Username placeholder not present");
    }

    @Test
    public void testPasswordFieldPresent() {
        assertTrue(loginPage.isPasswordFieldPresent(), "Password field is not present");
    }

    @Test
    public void testPasswordLabelPresent() {
        assertTrue(loginPage.isPasswordLabelPresent(), "Password label is not present");
    }

    @Test
    public void testPasswordPlaceholderText() {
        assertEquals(loginPage.getPasswordPlaceholder(), "Enter your password", "Password placeholder not present");
    }

    @Test
    public void testLoginButtonPresent() {
        assertTrue(loginPage.isLoginButtonPresent(), "Login button is not present");
    }

    @Test
    public void testRememberMeCheckBoxPresent() {
        assertTrue(loginPage.isRememberMeCheckBoxPresent(), "Remember Me check box is not present");
    }

    @Test
    public void testRememberMeCheckBoxText() {
        assertEquals(loginPage.getRememberMeText(), "Remember Me", "Remember Me text is not present");
    }

    @Test
    public void testTwitterLogoPresent() {
        assertTrue(loginPage.isTwitterLogoPresent(), "Twitter logo is not present");
    }

    @Test
    public void testFacebookLogoPresent() {
        assertTrue(loginPage.isFacebookLogoPresent(), "Facebook logo is not present");
    }

    @Test
    public void testLinkedInLogoPresent() {
        assertTrue(loginPage.isLinkedInLogoPresent(), "LinkedIn logo is not present");
    }

    //Data-Driven Test

    @DataProvider
    public static Object[][] testDataForFailure() {
        return new Object[][] {
                {"", "", "Both Username and Password must be present", "Alert warning does not show up if username and password are empty"},
                {"chandra", "", "Password must be present", "Alert warning does not show up if password is empty"},
                {"", "secret!", "Username must be present", "Alert warning does not show up if username is empty"}
        };
    }

    @DataProvider
    public  static Object[][] testDataForSuccess() {
        return new Object[][] {
                {"chandra", "secret!", "ACME demo app", "ACME demo app title not present"}
        };
    }

    @Test(dataProvider = "testDataForFailure")
    public void testLoginFailure(String username, String password, String expected, String message) {
        String text = loginPage.doLoginFailure(username, password);
        assertEquals(text, expected, message);
    }

    @Test(dataProvider = "testDataForSuccess")
    public void testLoginSuccess(String username, String password, String expected, String message) {
        AppPage appPage = loginPage.doLoginSuccess(username, password);
        assertEquals(appPage.getAppPageTitle(), expected, message);
    }

    //Table Sort Test
    @Test
    public void testAscendingSortedAmountList() {
        var appPage = loginPage.doLoginSuccess("chandra", "secret!");
        assertTrue(appPage.isAmountAscendingSorted(),"Recent transactions table is not sorted in ascending order");
    }

    @Test
    public void testDataIntactInTable() {
        var appPage = loginPage.doLoginSuccess("chandra", "secret!");
        assertTrue(appPage.isDataIntactInTable(), "Table data is not intact after sorting");
    }

    //Canvas Chart Test
    @Test
    public void testBarChartAndData() throws IOException, InterruptedException {
        var appPage = loginPage.doLoginSuccess("chandra", "secret!");
        var chartPage = appPage.clickCompareExpensesLink();
        chartPage.getChartImage("newChart");
        assertTrue(chartPage.compareChartWithBaseImage("newChart", "baseChart"), "Chart data is not correct");
    }

    @Test
    public void testAddNextYearData() throws IOException, InterruptedException {
        var appPage = loginPage.doLoginSuccess("chandra", "secret!");
        var chartPage = appPage.clickCompareExpensesLink();
        chartPage.clickShowDataForNextYearButton();
        chartPage.getChartImage("addYearChart");
        assertTrue(chartPage.compareChartWithBaseImage("addYearChart", "addYearBaseChart"), "Chart data is not correct");
    }

    //Dynamic Content Test
    @Test
    public void testDisplayAdsPresence() throws IOException {
        String url = getAdUrl();
        AdsPage adsPage = loginPage.gotoAdsPage(url);
        assertTrue(adsPage.checkAdsPresence(), "Either Cyber monday ad or flash sale ad or both is/are not present");
    }

}
