package com.registration.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/registration.feature",
        glue = {"com.registration.stepdefinitions", "com.registration.hooks"},
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        tags = "@Negative1"
)
public class TestRunner extends AbstractTestNGCucumberTests {
}