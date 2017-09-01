package case2;

import com.galenframework.rainbow4j.ComparisonOptions;
import com.galenframework.rainbow4j.ImageCompareResult;
import com.galenframework.rainbow4j.Rainbow4J;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import utils.TestRecord;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class BrowserTest extends SetUpBrowserStack {

    private String filePath = System.getProperty("user.dir") + "\\screenshots\\browserStackTestScreenshots\\";
    private String extensionOverCase = "PNG";
    private String verifiedFile = "_VERIFIED.png";
    private String browserStackFile = "_BROWSER_STACK.png";
    private String resultFile = "_RESULT.png";
    private int scrollingTime = 1000;
    private int urlIndex = 0;

    @Test(dataProvider = "browserStackUrls")
    public void test(TestRecord data) throws IOException {

        String url = data.getData()[urlIndex];
        String urlName = url.replace(new URL(url).getHost(), "").replace("http", "").replaceAll("[^a-zA-Z0-9.-]", "");
        AShot aShot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(scrollingTime));

        driver.get(url);
        Screenshot screenshot = aShot.takeScreenshot(driver);
        BufferedImage image = screenshot.getImage();
        String browserStackFileName = filePath + urlName + browserStackFile;

        String resultFilePath = filePath + urlName + resultFile;
        String verifiedFilePath = filePath + urlName + verifiedFile;


        BufferedImage verifiedImage = screenshots.get(urlName);


        if (!verifiedImage.equals(image)) {
            ImageCompareResult compareResult = new Rainbow4J().compare(verifiedImage, image, new ComparisonOptions());

            ImageIO.write(compareResult.getComparisonMap(), extensionOverCase, new File(resultFilePath));
            ImageIO.write(verifiedImage, extensionOverCase, new File(verifiedFilePath));
            ImageIO.write(image, extensionOverCase, new File(browserStackFileName));

            Assert.assertTrue(compareResult.getPercentage() < 1,
                    "ScreenShots '"+ urlName + "' are not equal. Pixel differences - " + compareResult.getPercentage() +
                            ".\nURL: " + url);
        }

    }

}
