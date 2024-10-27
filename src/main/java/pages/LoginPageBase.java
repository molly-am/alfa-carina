package pages;

import com.google.common.io.Files;
import com.zebrunner.carina.utils.mobile.IMobileUtils;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public abstract class LoginPageBase extends AlfaAbstractPage{

    public LoginPageBase(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public abstract boolean isTitlePresent();

    public abstract boolean isOpened(long timeout);

    public abstract String getTitle();

    public abstract void typePassword(String value);

    public abstract void typeLogin(String value);

    public abstract String getLoginText();

    public abstract String getPasswordFieldText();

    public abstract boolean isPasswordMasked();

    public abstract void clickShowPasswordButton();

    public abstract boolean isShowPasswordButtonEnabled();

    public abstract void clickLoginButton();

    public abstract String getErrorMessage(long timeout);

    public abstract boolean isErrorMessagePresent(long timeout);

}
