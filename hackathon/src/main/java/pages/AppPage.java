package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppPage {
    private WebDriver driver;
    private By amount = By.id("amount");
    private By rowAmounts = By.xpath("//*[@id='transactionsTable']/tbody/tr[*]/td[5]/span");
    private By tableRows = By.cssSelector("table tbody tr");

    private By compareExpenses = By.id("showExpensesChart");
    private By canvas = By.id("canvas");


    public AppPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getAppPageTitle() {
        return driver.getTitle();
    }

    public void clickOnAmount() {
        driver.findElement(amount).click();
    }

    public boolean isAmountAscendingSorted() {
        clickOnAmount();
        List<Double> obtainedList = new ArrayList<>();
        List<WebElement> rowAmountsList= driver.findElements(rowAmounts);
        for(WebElement we:rowAmountsList){
            String[] amt = we.getText().split(" ");
            String amtStr = (amt[0].equals("-"))? amt[0] + amt[1]:amt[1];
            obtainedList.add(Double.parseDouble(amtStr.replace(",", "")));
        }
        ArrayList<Double> sortedList = new ArrayList<>(obtainedList);
        Collections.sort(sortedList);
        return sortedList.equals(obtainedList);
    }

    public boolean isDataIntactInTable() {
        List<String> dataBeforeSorting = getTableData();
        Collections.sort(dataBeforeSorting);
        clickOnAmount();
        List<String> dataAfterSorting = getTableData();
        Collections.sort(dataAfterSorting);
        return dataBeforeSorting.equals(dataAfterSorting);
    }

    private List<String> getTableData() {
        List<String> rowData = new ArrayList<>();
        List<WebElement> rows = driver.findElements(tableRows);
        for (WebElement we: rows) {
            rowData.add(we.getText());
        }
        return rowData;
    }

    public ChartPage clickCompareExpensesLink() throws InterruptedException {
        driver.findElement(compareExpenses).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(canvas)));
        return new ChartPage(driver);
    }
}
