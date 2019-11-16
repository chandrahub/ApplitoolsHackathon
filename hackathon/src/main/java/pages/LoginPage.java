package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class LoginPage {
    private WebDriver driver;
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.id("log-in");
    private By usernameLabel = By.xpath("//html/body/div/div/form/div[1]/label");
    private By passwordLabel = By.xpath("//html/body/div/div/form/div[2]/label");
    private By authHeader = By.className("auth-header");
    private By rememberMeCheckBox = By.className("form-check-label");
    private By twitterLogo = By.cssSelector("img[src='img/social-icons/twitter.png']");
    private By facebookLogo = By.cssSelector("img[src='img/social-icons/facebook.png']");
    private By linkedInLogo = By.cssSelector("img[src='img/social-icons/linkedin.png']");
    private By logo = By.cssSelector("img[src='img/logo-big.png']");
    private By alertWarning = By.cssSelector("div[class='alert alert-warning']");


    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    public boolean isUsernameFieldPresent() {
        return driver.findElement(usernameField).isDisplayed();
    }

    public boolean isPasswordFieldPresent() {
        return driver.findElement(passwordField).isDisplayed();
    }

    public boolean isLoginButtonPresent() {
        return driver.findElement(loginButton).isDisplayed();
    }

    public boolean isUsernameLabelPresent() {
        return driver.findElement(usernameLabel).isDisplayed();
    }

    public boolean isPasswordLabelPresent() {
        return driver.findElement(passwordLabel).isDisplayed();
    }

    public boolean isAuthHeaderPresent() {
        return driver.findElement(authHeader).isDisplayed();
    }

    public boolean isRememberMeCheckBoxPresent() {
        return driver.findElement(rememberMeCheckBox).isDisplayed();
    }

    public boolean isTwitterLogoPresent() {
        return driver.findElement(twitterLogo).isDisplayed();
    }

    public boolean isFacebookLogoPresent() {
        return driver.findElement(facebookLogo).isDisplayed();
    }

    public boolean isLinkedInLogoPresent() {
        return driver.findElement(linkedInLogo).isDisplayed();
    }

    public boolean isLogoPresent() {
        return driver.findElement(logo).isDisplayed();
    }

    public String getAuthHeaderText() {
        return driver.findElement(authHeader).getText();
    }

    public String getUsernamePlaceholder() {
        return driver.findElement(usernameField).getAttribute("placeholder");
    }

    public String getPasswordPlaceholder() {
        return driver.findElement(passwordField).getAttribute("placeholder");
    }

    public String getRememberMeText() {
        return driver.findElement(rememberMeCheckBox).getText();
    }


    public AppPage doLoginSuccess(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
        return  new AppPage(driver);
    }

    public String doLoginFailure(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
        return  driver.findElement(alertWarning).getText();
    }


    public AdsPage gotoAdsPage(String url) throws IOException {
        driver.get(url);
        doLoginSuccess("chandra", "secret!");
        return new AdsPage(driver);
    }

}
