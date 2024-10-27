package pages.android;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.SuccessResultPageBase;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = SuccessResultPageBase.class)
public class SuccessResultPage extends SuccessResultPageBase {

    @AndroidFindBy(xpath = "//*[@text='Вход в Alfa-Test выполнен']")
    private ExtendedWebElement successPageTitle;

    private final static Logger LOGGER = LoggerFactory.getLogger(SuccessResultPageBase.class);

    public SuccessResultPage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify page is opened")
    public boolean isPageOpened(){
        return successPageTitle.isElementPresent(30);
    }
}
