package base;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.google.common.io.Files;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTests {

    private EyesRunner runner;
    protected Eyes eyes;
    private BatchInfo batch;

    private WebDriver driver;
    protected LoginPage loginPage;

    private String url;
    private String adUrl;

    @BeforeClass
    public void setUp(){
        Properties props = System.getProperties();
        try {
            props.load(new FileInputStream(new File("src/main/resources/config.properties")));
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        if (props.getProperty("version").equalsIgnoreCase("v2")) {
            url = "https://demo.applitools.com/hackathonV2.html";
            adUrl = "https://demo.applitools.com/hackathonV2.html?showAd=true";
        } else {
            url = "https://demo.applitools.com/hackathon.html";
            adUrl = "https://demo.applitools.com/hackathon.html?showAd=true";
        }

        batch = new BatchInfo("Hackathon Dynamic Content Batch");
        runner = new ClassicRunner();
        eyes = new Eyes(runner);
        eyes.setApiKey(props.getProperty("applitools.api.key"));
        eyes.setBatch(batch);

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        eyes.open(driver, "Dynamic Content", "Dynamic Content Tests");
        goHome();
    }

    @BeforeMethod
    public void goHome(){
        driver.get(url);
        loginPage = new LoginPage(driver);
    }

    @AfterClass
    public void tearDown(){
        eyes.closeAsync();
        driver.quit();
        eyes.abortIfNotClosed();
    }

    @AfterMethod
    public void recordFailure(ITestResult result){
        if(ITestResult.FAILURE == result.getStatus())
        {
            var camera = (TakesScreenshot)driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            try{
                Files.move(screenshot, new File("src/main/resources/screenshots/" + result.getName() + ".png"));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public Eyes getEyes() {
        return eyes;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public BatchInfo getBatch() {
        return batch;
    }

}