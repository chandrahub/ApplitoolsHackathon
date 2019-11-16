package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdsPage {
    public WebDriver driver;

    public AdsPage(WebDriver driver) {
        this.driver = driver;
    }

    private By cyberMondaySale = By.cssSelector("img[src='img/flashSale.gif']");
    private By flashSale = By.cssSelector("img[src='img/flashSale2.gif']");

    public boolean checkAdsPresence() {
        return  driver.findElement(cyberMondaySale).isDisplayed() && driver.findElement(flashSale).isDisplayed();
    }
}
