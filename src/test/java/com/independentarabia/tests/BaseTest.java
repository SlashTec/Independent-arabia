package com.independentarabia.tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTest {

    protected static AppiumDriver driver ;

    protected WebDriverWait wait;
    @BeforeMethod
    public void setUp () throws MalformedURLException {
        // إعداد خيارات Appium

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");  //Android Automation Engine
        options.setDeviceName("Pixel_7a"); // اسم ال emulator
        options.setApp("C:\\Users\\user\\Downloads\\app-release (7).apk");  // مسار ملف Apk
        options.setAppWaitDuration(Duration.ofSeconds(60));  // المدة التي ينتظرها Appium بعد تثبيت التطبيق
        options.setCapability("autoGrantPermissions", true);  // تفعيل الصلاحيات تلقائيا
        URL serverURL = new URL("  https://a99c-109-107-229-80.ngrok-free.app /wd/hub");
      //  URL serverUrl = new URL("http://127.0.0.1:4723/wd/hub");  //الاتصال ب Appium Server
        System.out.println(" جاري إنشاء الجلسة...");
        options.setCapability("uiautomator2ServerLaunchTimeout", 60000);  // 60 ثانية انتظار خادم UiAutomator2 يشتغل على الجهاز
        options.setCapability("adbExecTimeout", 60000); // انتظار تنفيذ أوامر ADB قبل ما تعتبر العملية فشلت
        driver = new AndroidDriver(serverURL, options); // انشاء driver
        System.out.println(" تم إنشاء الجلسة بنجاح: " + driver.getSessionId());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));


    }

    @AfterMethod
    public void tearDowen(){
        // إغلاق الجلسة بعد كل اختبار
        if(driver != null) {
            driver.quit();
        }

    }
    protected WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void skipLogin() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement skipButton = wait.until(ExpectedConditions.elementToBeClickable(
                    AppiumBy.accessibilityId("تخطي التسجيل")));

            skipButton.click();
            System.out.println(" تم الضغط على زر تخطي التسجيل.");
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println(" لا يوجد زر تخطي التسجيل .");
        } catch (Exception e) {
            System.out.println(" حدث خطأ أثناء محاولة تخطي التسجيل: " + e.getMessage());
        }
    }

    protected void loginWithGoogle() throws InterruptedException {
        waitForElement(AppiumBy.accessibilityId("القائمة")).click();
        waitForElement(By.xpath("//android.view.View[@content-desc='تسجيل دخول']")).click();
        waitForElement(AppiumBy.accessibilityId("تسجيل الدخول عبر غوغل")).click();
        Thread.sleep(3000);
        driver.findElements(By.id("com.google.android.gms:id/account_display_name")).get(0).click();
        waitForElement(AppiumBy.accessibilityId("القائمة")).click();
        String userStatus = waitForElement(By.xpath("//android.view.View[@content-desc]"))
                .getAttribute("content-desc");
        assert !userStatus.contains("زائر") : "❌ المستخدم ما زال زائرًا بعد تسجيل الدخول";
        driver.navigate().back();
    }









}


