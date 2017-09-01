package case1;

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

public class UrlTest extends SetUpUrlTest {

    private String filePath = System.getProperty("user.dir") + "\\screenshots\\urlTestScreenshots\\";
    private String extensionOverCase = "PNG";
    private String stageFile = "_STAGE.png";
    private String productionFile = "_PRODUCTION.png";
    private String resultFile = "_RESULT.png";
    private int scrollingTime = 1000;
    private int stageUrlIndex = 0;
    private int productionUrlIndex = 1;

    @Test(dataProvider = "urls")
    public void test(TestRecord data) throws IOException {

        String stageUrl = data.getData()[stageUrlIndex];
        String productionUrl = data.getData()[productionUrlIndex];
        String urlName = stageUrl.replace(new URL(stageUrl).getHost(), "").replace("https", "").replaceAll("[^a-zA-Z0-9.-]", "");

        AShot aShot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(scrollingTime));

        driver.get(stageUrl);
        Screenshot stageScreenshot = aShot.takeScreenshot(driver);
        BufferedImage stageImage = stageScreenshot.getImage();
        String stageFilePath = filePath + urlName + stageFile;

        driver.get(productionUrl);
        Screenshot productionScreenshot = aShot.takeScreenshot(driver);
        BufferedImage productionImage = productionScreenshot.getImage();
        String productionFilePath = filePath + urlName + productionFile;


        String resultFilePath = filePath + urlName + resultFile;

        if (!stageImage.equals(productionImage)) {
            ImageCompareResult compareResult = new Rainbow4J().compare(stageImage, productionImage, new ComparisonOptions());

            ImageIO.write(compareResult.getComparisonMap(), extensionOverCase, new File(resultFilePath));
            ImageIO.write(stageImage, extensionOverCase, new File(stageFilePath));
            ImageIO.write(productionImage, extensionOverCase, new File(productionFilePath));

            Assert.assertTrue(compareResult.getPercentage() < 1,
                    "ScreenShots '"+ urlName + "' are not equal. Pixel differences - " + compareResult.getPercentage() + ".\n" +
                            "stage URL: " + stageUrl + "\nproduction URL: " + productionUrl);
        }
    }
}

