package com.registration.stepdefinitions;

import com.registration.pages.RegistrationPage;
import com.registration.hooks.TestHooks;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import org.openqa.selenium.By;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrationSteps {

    private static final Logger logger = LogManager.getLogger(RegistrationSteps.class);

    private void log(String message) {
        logger.info(message);
    }

    WebDriver driver = TestHooks.driver;
    RegistrationPage registrationPage;

    @Given("I navigate to the registration page")
    public void iNavigateToTheRegistrationPage() {
        log("Navigating to the registration page");
        driver.get("https://demo.aviz.aero/");
        log("Navigated to the URL: https://demo.aviz.aero/");
        driver.findElement(By.linkText("Register Now!")).click();
        log("Clicked on 'Register Now!' link.");
    }

    @When("I fill in all mandatory fields with valid data")
    public void iFillInAllMandatoryFieldsWithValidData() {
        log("Filling in mandatory fields with valid data");
        registrationPage.selectRegisterAs("Broker");
        registrationPage.enterFirstName("John");
        registrationPage.enterLastName("Doe");
        registrationPage.enterEmail("john.doe@example.com");
        registrationPage.enterPhone("1234567890");
        registrationPage.enterDesignation("Engineer");
    }

    @When("I submit the registration form")
    public void iSubmitTheRegistrationForm() {
        log("Submitting the registration form");
        try {
            Thread.sleep(2000); // Wait for 2 seconds after clicking the Next button
        } catch (InterruptedException e) {
            log("Error during thread sleep: " + e.getMessage());
            e.printStackTrace();
        }
        registrationPage.clickNextButton();
        try {
            Thread.sleep(2000); // Wait for 2 seconds after clicking the Next button
        } catch (InterruptedException e) {
            log("Error during thread sleep: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @When("I enter an invalid email format")
    public void iEnterAnInvalidEmailFormat() {
        log("Entering an invalid email format");
        registrationPage.selectRegisterAs("Broker");
        registrationPage.enterFirstName("John");
        registrationPage.enterLastName("Doe");
        registrationPage.enterEmail("abcd123"); 
        registrationPage.enterPhone("1234567890");
        registrationPage.enterDesignation("Engineer");
    }

    @When("I leave mandatory fields blank")
    public void iLeaveMandatoryFieldsBlank() {
        log("Leaving all mandatory fields blank");
        // Do not fill any fields on the registration page
    }

    //I want to validate the all fields error message when I click on next button without entering any data in the fields
    @When("I click on next button without entering any data in the fields")
    public void iClickOnNextButtonWithoutEnteringAnyDataInTheFields() {
        registrationPage.clickNextButton();
        try {
            Thread.sleep(2000); // Wait for 2 seconds after clicking the Next button
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Then("I should see an error message for invalid email")
    public void iShouldSeeAnErrorMessageForInvalidEmail() {
        boolean isErrorMessageVisible = driver.findElement(By.xpath("//div[@role='status']"))
                .isDisplayed();
        WebElement invalidEmailMessage = driver.findElement(By.xpath("//div[@role='status']"));
        System.out.println(invalidEmailMessage.getText());
        if (!isErrorMessageVisible) {
            throw new AssertionError("Error message for invalid email is not visible.");
        }
        try {
            Thread.sleep(2000); // Wait for 2 seconds after capturing the error text
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("I should see a success message")
    public void iShouldSeeASuccessMessage() {
        // Add assertion for success message
    }

    @Then("I validate the Step 2 label is visible")
    public void iValidateTheStep2LabelIsVisible() {
        boolean isStep2LabelVisible = driver.findElement(By.xpath("//div[contains(@class,'flex')]/span")).isDisplayed();
        System.out.println("Is Step 2 label visible: " + isStep2LabelVisible);
        if (!isStep2LabelVisible) {
            throw new AssertionError("Step 2 label is not visible on the registration page.");
        }
    }

    @Then("I should see error messages for all mandatory fields")
    public void iShouldSeeErrorMessagesForAllMandatoryFields() {
        log("Validating error messages for all mandatory fields");
        String[] expectedErrorMessages = {
            "Please select registration type",
            "First name is required",
            "Last name is required",
            "Email is required",
            "Phone number is required",
            "Designation is required"
        };

        for (String errorMessage : expectedErrorMessages) {
            registrationPage.validateErrorMessage(errorMessage);
            log("Validated error message: " + errorMessage);
        }

        // Capture all error messages displayed on the UI
        List<WebElement> errorMessages = driver.findElements(By.cssSelector(".error-message-selector")); // Update with actual selector
        log("Scenario: Missing Mandatory Fields");
        for (WebElement error : errorMessages) {
            log("Error message displayed on UI: " + error.getText());
        }
    }

    @Then("I should remain on the same page and see error messages for all mandatory fields")
    public void iShouldRemainOnTheSamePageAndSeeErrorMessagesForAllMandatoryFields() {
        log("Verifying that the page URL has not changed");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("registration"), "The page navigated to a different URL: " + currentUrl);
        log("Current URL: " + currentUrl);

        log("Validating error messages for all mandatory fields");
        String[] mandatoryFields = {"First Name", "Last Name", "Email", "Phone", "Designation"};
        for (String field : mandatoryFields) {
            String errorMessage = field + " is required";
            registrationPage.validateErrorMessage(errorMessage);
            log("Error message validated: " + errorMessage);
        }
    }

    @When("the broker enters a {string} with more than {int} characters")
    public void theBrokerEntersAFieldWithMoreThanCharacters(String fieldName, int charLimit) {
        System.out.println("Entering a " + fieldName + " exceeding the character limit of " + charLimit);
        String longInput = "A".repeat(charLimit + 1); // Generate a string exceeding the character limit
        if (fieldName.equalsIgnoreCase("First Name")) {
            registrationPage.enterFirstName(longInput);
        } else if (fieldName.equalsIgnoreCase("Last Name")) {
            registrationPage.enterLastName(longInput);
        } else {
            throw new IllegalArgumentException("Unsupported field: " + fieldName);
        }
    }

    @Then("a field-level validation error should be displayed for {string}")
    public void aFieldLevelValidationErrorShouldBeDisplayedForField(String fieldName) {
        System.out.println("Checking for field-level validation error for " + fieldName);
        String expectedErrorMessage = fieldName + " exceeds the character limit";
        registrationPage.validateErrorMessage(expectedErrorMessage);
    }

    @Given("the broker enters a First Name with more than {int} characters")
    public void the_broker_enters_a_first_name_with_more_than_characters(Integer charLimit) {
        // Generate a string exceeding the character limit
        String longFirstName = "A".repeat(charLimit + 1);
        // Enter the generated string into the First Name field
        registrationPage.enterFirstName(longFirstName);
    }

    @Given("a field-level validation error should be displayed for First Name")
    public void a_field_level_validation_error_should_be_displayed_for_first_name() {
        // Verify that the validation error message is displayed for the First Name field
        String errorMessage = registrationPage.getFirstNameValidationError();
        Assert.assertEquals(errorMessage, "First Name cannot exceed 255 characters", "Validation error message mismatch");
    }

    @io.cucumber.java.After
    public void tearDown() {
        log("Tearing down the WebDriver instance");
        if (driver != null) {
            driver.quit();
        }
    }
}