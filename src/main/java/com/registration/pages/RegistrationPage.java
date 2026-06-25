package com.registration.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrationPage {

    private static final Logger logger = LogManager.getLogger(RegistrationPage.class);

    WebDriver driver;

    @FindBy(xpath = "//div[@class='auth-input-wrapper relative']/select")
    WebElement registerAsDropdown;

    @FindBy(id = "auth-input-First-Name")
    WebElement firstNameField;

    @FindBy(id = "auth-input-Last-Name")
    WebElement lastNameField;

    @FindBy(id = "auth-input-Mail-ID")
    WebElement emailField;

    @FindBy(xpath = "//input[@*='Enter Phone Number']")
    WebElement phoneField;

    @FindBy(id = "auth-input-Designation")
    WebElement designationField;

    @FindBy(xpath = "//button[text()='Next']")
    WebElement nextButton;

    @FindBy(xpath="//div[text()='Email is required']")
    WebElement uiemailErrorMessage;

    public String getFirstNameValidationError() {
        WebElement errorMessageElement = driver.findElement(By.xpath("//div[text()='First name is required']"));
        return errorMessageElement.getText();
    }

    private WebDriverWait wait;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selectRegisterAs(String value) {
        wait.until(ExpectedConditions.visibilityOf(registerAsDropdown));
        Select dropdown = new Select(registerAsDropdown);
        boolean optionFound = false;

        // Log available options for debugging
        for (WebElement option : dropdown.getOptions()) {
            if (option.getText().equalsIgnoreCase(value)) {
                dropdown.selectByVisibleText(option.getText());
                optionFound = true;
                break;
            }
        }

        if (!optionFound) {
            throw new NoSuchElementException("Cannot locate option with text: " + value);
        }
    }

    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOf(firstNameField));
        firstNameField.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOf(lastNameField));
        lastNameField.sendKeys(lastName);
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.sendKeys(email);
    }

    public void enterPhone(String phone) {
        wait.until(ExpectedConditions.visibilityOf(phoneField));
        phoneField.sendKeys(phone);
    }

    public void enterDesignation(String designation) {
        wait.until(ExpectedConditions.visibilityOf(designationField));
        designationField.sendKeys(designation);
    }

    public void clickNextButton() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        nextButton.click();
    }

    public void validateErrorMessage(String expectedmsg) {
        try {
            logger.info("Validating error message: " + expectedmsg);
            WebElement errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='" + expectedmsg + "']")));
            String actualErrorMessage = errorMessageElement.getText();

            if (!actualErrorMessage.equals(expectedmsg)) {
                throw new AssertionError("Expected error message: '" + expectedmsg + "' but got: '" + actualErrorMessage + "'");
            } else {
                logger.info("Error message validation passed: " + actualErrorMessage);
            }
        } catch (NoSuchElementException e) {
            logger.error("Error message element not found: " + expectedmsg, e);
            throw new AssertionError("Error message element not found: " + expectedmsg);
        }
    }
}