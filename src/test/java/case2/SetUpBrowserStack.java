package case2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import utils.DataProviderUtility;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SetUpBrowserStack extends BrowserStackTestNGTest{
    private String systemPropertyName = "webdriver.gecko.driver";
    private String geckoDriverPath = System.getProperty("user.dir") + "\\geckodriver.exe";
    public Map<String, BufferedImage> screenshots = new HashMap<>();

    private int scrollingTime = 1000;

    @BeforeClass(alwaysRun=true)
    @org.testng.annotations.Parameters(value={"environment"})
    public void setUp(String environment)throws Exception {
        takeScreenshots();
        setUpBrowserStack(environment);
    }

    private void takeScreenshots() throws Exception {
        System.setProperty(systemPropertyName, geckoDriverPath);
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();

        List<String> urls = DataProviderUtility.readFile("browserStackUrls", true);
        for (String url : urls) {
            AShot aShot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(scrollingTime));
            String urlName = url.replace(new URL(url).getHost(), "").replace("http", "").replaceAll("[^a-zA-Z0-9.-]", "");

            driver.get(url);
            Screenshot screenshot = aShot.takeScreenshot(driver);
            BufferedImage image = screenshot.getImage();
            screenshots.put(urlName, image);
        }
        driver.quit();
    }

    @AfterClass(alwaysRun=true)
    public void tearDown() throws Exception {
        driver.quit();
        if(l != null) l.stop();
    }

    @DataProvider(name = "browserStackUrls")
    public Iterator<Object[]> createData() {
        return new DataProviderUtility().getTestDataFromCsvFile("browserStackUrls", true);
    }
}
