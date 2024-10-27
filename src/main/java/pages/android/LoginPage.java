package pages.android;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.LoginPageBase;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = LoginPageBase.class)
public class LoginPage extends LoginPageBase {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[contains(@resource-id, 'tvTitle')]")
    private ExtendedWebElement title;

    @FindBy(id = "etUsername")
    private ExtendedWebElement loginField;

    @FindBy(id = "etPassword")
    private ExtendedWebElement passwordField;

    @FindBy(className = "android.widget.ImageButton")
    private ExtendedWebElement showPasswordButton;

    @FindBy(id = "btnConfirm")
    private ExtendedWebElement loginButton;

    @FindBy(id = "tvError")
    private ExtendedWebElement invalidUserDataError;

    @FindBy(className = "android.widget.ProgressBar")
    private ExtendedWebElement progressBar;

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);

    @Step("Check is element present")
    public boolean isTitlePresent() {
        makeAllureScreenshot();
        return title.isElementPresent(30);
    }

    @Step("Login page was opened!")
    public boolean isOpened(long timeout){
        LOGGER.info("Check page is opened...");
        return title.isElementPresent(timeout);
    }

    @Step("Get title value")
    public String getTitle(){
        LOGGER.info("Get title value...");
        makeAllureScreenshot();
        return title.getText();
    }

    @Step("Type password : {value}")
    public void typePassword(String value){
        passwordField.click();
        LOGGER.info("Paste " + value + "...");
        passwordField.type(value);
        makeAllureScreenshot();
    }

    @Step("Paste 'Login' value: {value}")
    public void typeLogin(String value) {
        LOGGER.info("Type" + value + " ...");
        loginField.type(value);
        makeAllureScreenshot();
    }

    @Step("Get 'Login' field value")
    public String getLoginText(){
        makeAllureScreenshot();
        return loginField.getText();
    }

    @Step("Get password field value")
    public String getPasswordFieldText(){
        makeAllureScreenshot();
        return passwordField.getText();
    }

    @Step("Check is 'Password' field masked")
    public boolean isPasswordMasked(){
        makeAllureScreenshot();
        return Boolean.parseBoolean(passwordField.getAttribute("password"));
    }

    @Step("Click 'Show button'")
    public void clickShowPasswordButton(){
        makeAllureScreenshot();
        showPasswordButton.click();
    }

    @Step("Check is 'Show button' enabled")
    public boolean isShowPasswordButtonEnabled(){
        makeAllureScreenshot();
        return Boolean.parseBoolean(showPasswordButton.getAttribute("checked"));
    }

    @Step("Click 'Login' button")
    public void clickLoginButton(){
        makeAllureScreenshot();
        loginButton.click();
    }

    @Step("Verify user sees error message")
    public String getErrorMessage(long timeout){
        progressBar.waitUntilElementDisappear(timeout);
        makeAllureScreenshot();
        return invalidUserDataError.getText();
    }

    @Step("Get error message")
    public boolean isErrorMessagePresent(long timeout){
        progressBar.waitUntilElementDisappear(timeout);
        makeAllureScreenshot();
        return invalidUserDataError.isElementPresent(timeout);
    }
}
