package pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ChartPage {
    private WebDriver driver;
    private JavascriptExecutor jsExec;
    private WebDriverWait jsWait;

    private By showDataForNextYear = By.id("addDataset");

    public ChartPage(WebDriver driver) {
        this.driver = driver;
        jsExec = (JavascriptExecutor) driver;
        jsWait = new WebDriverWait(driver, 10);
    }

    public void getChartImage(String chartName) throws IOException {
        waitForJQueryLoad(); //wait for chart to load is not working
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String script = "return document.getElementById('canvas').toDataURL('image/png');";
        var image = ((JavascriptExecutor)driver).executeScript(script);
        String[] str_base64 = image.toString().split(",");
        byte[] decodedBytes = Base64.getDecoder().decode(str_base64[1]);
        FileUtils.writeByteArrayToFile(new File("src/main/resources/images/" + chartName + ".png"), decodedBytes);
    }

    public boolean compareChartWithBaseImage(String chartName, String baseChartName) {
        String file1 = "src/main/resources/images/" + baseChartName + ".png";
        String file2 = "src/main/resources/images/" + chartName + ".png";
        // Load the images
        Image image1 = Toolkit.getDefaultToolkit().getImage(file1);
        Image image2 = Toolkit.getDefaultToolkit().getImage(file2);

        try {

            PixelGrabber grabImage1Pixels = new PixelGrabber(image1, 0, 0, -1,
                    -1, false);
            PixelGrabber grabImage2Pixels = new PixelGrabber(image2, 0, 0, -1,
                    -1, false);
            int[] image1Data = null;
            if (grabImage1Pixels.grabPixels()) {
                image1Data = (int[]) grabImage1Pixels.getPixels();
            }
            int[] image2Data = null;
            if (grabImage2Pixels.grabPixels()) {
                image2Data = (int[]) grabImage2Pixels.getPixels();
            }
            return java.util.Arrays.equals(image1Data, image2Data);

        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    private void waitForJQueryLoad() {
        try {
            ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) this.driver)
                    .executeScript("return jQuery.active") == 0);
            boolean jqueryReady = (Boolean) jsExec.executeScript("return jQuery.active==0");
            if (!jqueryReady) {
                jsWait.until(jQueryLoad);
            }
        } catch (WebDriverException ignored) {
        }
    }

    public void clickShowDataForNextYearButton()  {
        driver.findElement(showDataForNextYear).click();
        addWait(1000);
    }

    public void addWait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
