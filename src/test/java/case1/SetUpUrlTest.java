package case1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import utils.DataProviderUtility;

import java.util.Iterator;

public class SetUpUrlTest {
    private String systemPropertyName = "webdriver.gecko.driver";
    private String geckoDriverPath = System.getProperty("user.dir") + "\\geckodriver.exe";
    public WebDriver driver;

    @DataProvider(name = "urls")
    public Iterator<Object[]> createData() {
        return new DataProviderUtility().getTestDataFromCsvFile("urls", true);
    }

    @BeforeClass
    public void setUp() {
        System.setProperty(systemPropertyName, geckoDriverPath);
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    @AfterClass
    public void downTear() {
        driver.close();
    }
}
