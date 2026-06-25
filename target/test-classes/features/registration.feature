Feature: Registration Page Automation

  @RunThis @Positive1
  Scenario: Successful Registration
    Given I navigate to the registration page
    When I fill in all mandatory fields with valid data
    And I submit the registration form
    Then I validate the Step 2 label is visible

@Negative1
  Scenario: Invalid Email Format
    Given I navigate to the registration page
    When I enter an invalid email format
    And I submit the registration form
    Then I should see an error message for invalid email

@Negative2
  Scenario: Missing Mandatory Fields
    Given I navigate to the registration page
    When I leave mandatory fields blank
    And I submit the registration form
    Then I should see error messages for all mandatory fields

@Negative3
  Scenario: Broker enters a First Name exceeding the character limit
    Given I navigate to the registration page
    And the broker enters a First Name with more than 255 characters
    And I submit the registration form
    And a field-level validation error should be displayed for First Name

@Negative4
  Scenario: Broker enters a Last Name exceeding the character limit
    Given I navigate to the registration page
    And the broker enters a Last Name with more than 250 characters
    And I submit the registration form
    And a field-level validation error should be displayed for Last Name

@Negative5
  Scenario: Broker enters a Designation exceeding the character limit
  Given I navigate to the registration page
    And the broker enters a Designation with more than 250 characters
    And I submit the registration form
    And a field-level validation error should be displayed for Designation
    
@Negative6
  Scenario: Broker enters an email address already registered in the system
    Given I navigate to the registration page
    And the registration form is loaded and accessible
    And an account with the email "existing@example.com" already exists in the system
    When the broker selects "Broker" from the "Register as" dropdown
    And the broker fills in valid First Name, Last Name, Mobile Number and Designation
    And the broker enters "existing@example.com" in the Email field
    And the broker clicks the Submit button
    Then the system should block form submission
    And an error message "Email already exists" should be displayed

@Negative7
  Scenario: Broker enters a mobile number already registered in the system
    Given I navigate to the registration page
    And the registration form is loaded and accessible
    And an account with the mobile number "+91-9876543210" already exists in the system
    When the broker selects "Broker" from the "Register as" dropdown
    And the broker fills in valid First Name, Last Name, Email and Designation
    And the broker enters "+91-9876543210" in the Mobile Number field
    And the broker clicks the Submit button
    Then the system should block form submission
    And an error message "Mobile number already in use" should be displayed

