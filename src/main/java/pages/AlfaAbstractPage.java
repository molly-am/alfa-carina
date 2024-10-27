package pages;

import com.google.common.io.Files;
import com.zebrunner.carina.utils.mobile.IMobileUtils;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AlfaAbstractPage extends AbstractPage implements IMobileUtils {

    private static String pathScreenshots = "./build/classes/screenshots/";

    public AlfaAbstractPage(WebDriver driver) {
        super(driver);
    }

    private File screenshot() {
        File srcFile=((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
        String filename= UUID.randomUUID().toString();
        File targetFile = new File(pathScreenshots + filename +".jpg");
        try {
            FileUtils.copyFile(srcFile,targetFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return targetFile;
    }

    @Attachment(type = "image/png")
    private static byte[] attachScreen(File screenshot) {
        try {
            return screenshot == null ? null : Files.toByteArray(screenshot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeAllureScreenshot(){
        attachScreen(screenshot());
    }
}
