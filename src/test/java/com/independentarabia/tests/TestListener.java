package com.independentarabia.tests;

import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TestListener implements ITestListener {



    @Override public void onTestStart(ITestResult result) {
        System.out.println(" Starting Test: " + result.getName());
    }


    @Override public void onTestSuccess(ITestResult result) {
        System.out.println(" Test Passed: " + result.getName());
    }


    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(" Test Failed: " + result.getName());

        if (BaseTest.driver != null) {
            saveScreenshotToAllure(); // صورة لتقرير Allure
            saveScreenshotLocally(result.getName()); // صورة محفوظة حسب رقم البناء
        } else {
            System.out.println(" Driver is null. No screenshot taken.");
        }
    }



    public void saveScreenshotLocally(String testName) {
            File src = ((TakesScreenshot) BaseTest.driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

            // رقم البناء من Jenkins أو local إذا كنت على جهازك
            String buildNumber = System.getenv("BUILD_NUMBER") != null ? System.getenv("BUILD_NUMBER") : "local";

            File destDir = new File("screenshots/build_" + buildNumber);
            destDir.mkdirs();

            File dest = new File(destDir, testName + "_" + timestamp + ".png");

            try {
                FileUtils.copyFile(src, dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    @Attachment(value = "Failure Screenshot", type = "image/png")
    public byte[] saveScreenshotToAllure() {
        return ((TakesScreenshot) BaseTest.driver).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }


}
