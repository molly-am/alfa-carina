package org.alfatest.login;

import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import org.alfatest.BaseAlfaTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPageBase;
import pages.SuccessResultPageBase;

import java.util.Random;
import java.util.stream.IntStream;

public class LoginTest extends BaseAlfaTest {

    private static final String LOGIN_TITLE = "Вход в Alfa-Test";
    private static final String LOGIN = "Login";
    private static final String PASSWORD = "Password";
    private static final String LOGIN_PLACEHOLDER = "Логин";
    private static final String PASSWORD_PLACEHOLDER = "Пароль";

    @Test(description = "Check password field is masking")
    @Owner("ykhudaleyeu")
    public void checkPasswordMask(){
        SoftAssert softAssert = new SoftAssert();
        LoginPageBase loginPage = initPage(getDriver(), LoginPageBase.class);
        Assert.assertEquals(LOGIN_TITLE, loginPage.getTitle(), "Title is not valid");
        String loginPlaceholder = loginPage.getLoginText();
        String passPlaceholder = loginPage.getPasswordFieldText();
        softAssert.assertEquals(loginPlaceholder,LOGIN_PLACEHOLDER, "Login placeholder text is invalid");
        softAssert.assertEquals(passPlaceholder, PASSWORD_PLACEHOLDER,"Password placeholder text is invalid");
        loginPage.typePassword(PASSWORD);
        softAssert.assertNotEquals(passPlaceholder, PASSWORD, "Password text wasn't changed from default");

        boolean isMaskedDefault = loginPage.isPasswordMasked();
        boolean isShowButtonDefault = loginPage.isShowPasswordButtonEnabled();
        loginPage.clickShowPasswordButton();
        softAssert.assertNotEquals(isMaskedDefault, loginPage.isPasswordMasked(), "Password is not unmasked [1]!");
        softAssert.assertNotEquals(isShowButtonDefault, loginPage.isShowPasswordButtonEnabled(), "Show more button is not disabled!");

        loginPage.clickShowPasswordButton();
        softAssert.assertEquals(isMaskedDefault, loginPage.isPasswordMasked(), "Password is not masked after second click [2]!");
        softAssert.assertEquals(isShowButtonDefault, loginPage.isShowPasswordButtonEnabled(), "Show more button is disabled but shouldn't be after second click!");

        loginPage.clickShowPasswordButton();
        softAssert.assertNotEquals(isMaskedDefault, loginPage.isPasswordMasked(), "Password is not unmasked after third click [3]!");
        softAssert.assertNotEquals(isShowButtonDefault, loginPage.isShowPasswordButtonEnabled(), "Show more button is not disabled after third click!");
        softAssert.assertAll();
    }

    @Test(description = "Check too long login|password name behaviour")
    @Issue("Username is longer than acceptable size")
    @Owner("ykhudaleyeu")
    public void checkTooLongLoginPassword() {
        SoftAssert softAssert = new SoftAssert();
        int acceptedCharLength = 50;
        String validUserData = generateRandomStringWithLength(60, true);
        String invalidUserData = generateRandomStringWithLength(60, false);
        LoginPageBase loginPage = initPage(getDriver(), LoginPageBase.class);
        Assert.assertEquals(loginPage.getTitle(), LOGIN_TITLE, "Title is not valid");

        loginPage.typeLogin(validUserData);
        String userNameInField = loginPage.getLoginText();
        softAssert.assertEquals(userNameInField.length(), acceptedCharLength, "[Login field] value should be truncated to 50");
        loginPage.typeLogin(invalidUserData);
        softAssert.assertTrue(loginPage.getLoginText().isEmpty(), "[Login field] It's possible to set unacceptable chars for Login field");
        loginPage.typeLogin(LOGIN);

        loginPage.typePassword(validUserData);
        String passwordInField = loginPage.getPasswordFieldText();
        softAssert.assertEquals(passwordInField.length(), acceptedCharLength, "[Password field] value should be truncated to 50");
        loginPage.typePassword(invalidUserData);
        softAssert.assertTrue(loginPage.getPasswordFieldText().isEmpty(), "[Login field] It's possible to set unacceptable chars for Login field");

        softAssert.assertAll();
    }

    @Test(description = "Check error message")
    @Issue("Error messages are invalid")
    @Owner("ykhudaleyeu")
    public void checkErrorMessage() {
        SoftAssert softAssert = new SoftAssert();
        LoginPageBase loginPage = initPage(getDriver(), LoginPageBase.class);
        Assert.assertEquals(loginPage.getTitle(), LOGIN_TITLE, "Title is not valid");

        String invalidString = generateRandomStringWithLength(10, false);
        loginPage.typeLogin(invalidString);
        loginPage.clickLoginButton();
        softAssert.assertEquals(loginPage.getErrorMessage(10), "ExceptValue", "[login field] Error message is absent via keyboard input");

        loginPage.typeLogin(LOGIN);
        loginPage.typePassword(invalidString);
        loginPage.clickLoginButton();
        softAssert.assertEquals(loginPage.getErrorMessage(10), "ExceptValue", "[password field] Error message is absent via keyboard input");

        softAssert.assertAll();
    }

    @Test(description = "Happy path test")
    @Owner("ykhudaleyeu")
    public void happyPathTest() {
        LoginPageBase loginPage = initPage(getDriver(), LoginPageBase.class);
        Assert.assertEquals(loginPage.getTitle(), LOGIN_TITLE, "Title is not valid");
        loginPage.typeLogin(LOGIN);
        loginPage.typePassword(PASSWORD);
        loginPage.clickLoginButton();
        SuccessResultPageBase successResultPage = initPage(getDriver(), SuccessResultPageBase.class);
        Assert.assertTrue(successResultPage.isPageOpened(), "Login wasn't performed!");
    }

    @Test(description = "Unhappy path test")
    @Owner("ykhudaleyeu")
    public void unhappyPathTest() {
        SoftAssert softAssert = new SoftAssert();
        LoginPageBase loginPage = initPage(getDriver(), LoginPageBase.class);
        Assert.assertEquals(loginPage.getTitle(), LOGIN_TITLE, "Title is not valid");
        loginPage.typeLogin(LOGIN);
        loginPage.typePassword(PASSWORD + generateRandomStringWithLength(5, true));
        loginPage.clickLoginButton();
        softAssert.assertTrue(loginPage.isErrorMessagePresent(10), "Error message is absent!");
        softAssert.assertTrue(loginPage.isOpened(10), "Login with invalid password was performed.");
        loginPage.typeLogin(LOGIN + generateRandomStringWithLength(5, true));
        loginPage.typePassword(PASSWORD);
        loginPage.clickLoginButton();
        softAssert.assertTrue(loginPage.isErrorMessagePresent(10), "Error message is absent!");
        softAssert.assertTrue(loginPage.isOpened(10), "Login with invalid username was performed.");
        loginPage.typeLogin(LOGIN + generateRandomStringWithLength(5, true));
        loginPage.typePassword(PASSWORD + generateRandomStringWithLength(5, true));
        loginPage.clickLoginButton();
        softAssert.assertTrue(loginPage.isErrorMessagePresent(10), "Error message is absent!");
        softAssert.assertTrue(loginPage.isOpened(10), "Login with invalid username + password was performed.");
        softAssert.assertAll();
    }

    private String generateRandomStringWithLength(int charsInString, boolean isAcceptableChars){
        String charsForLogin = isAcceptableChars ? "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ[.,/' _-]," : "!@$%^*()";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(charsInString);
        IntStream.range(0, charsInString).forEach(it -> stringBuilder.append(charsForLogin.charAt(random.nextInt(charsForLogin.length()))));
        return stringBuilder.toString();
    }
}
