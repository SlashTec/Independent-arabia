package com.independentarabia.tests;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LogoutTest  extends BaseTest {
    @Test(priority = 1)
    public void testLogoutFlow() {
        try {


            // 1️⃣ تسجيل الدخول
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement loginBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.accessibilityId("تسجيل الدخول")));

            loginBtn.click();

            WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.widget.EditText[1]")));
            emailField.click();
            emailField.sendKeys("asilyacoub66@gmail.com");

            WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.widget.EditText[2]")));
            passwordField.click();
            passwordField.sendKeys("123456789");

            WebElement submitLoginBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.xpath("//android.widget.Button[@content-desc='تسجيل الدخول ']")));
            submitLoginBtn.click();

            WebElement laterBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.accessibilityId("في وقت لاحق")));
            laterBtn.click();

            //  وجود كلمة "الرئيسية" في الصفحة يدل على نجاح الدخول
            boolean loggedIn = driver.getPageSource().contains("الرئيسية");
            Assert.assertTrue(loggedIn, " لم يتم الدخول للصفحة الرئيسية بالرغم من صحة البيانات.");



            // 2️⃣ فتح القائمة
            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("القائمة"))).click();

            // 3️⃣ الضغط على "تسجيل خروج"
            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("تسجيل خروج"))).click();

            // 4️⃣ نافذة التأكيد "هل تريد بالتأكيد تسجيل الخروج؟"
            WebElement confirmLogoutBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.accessibilityId("تسجيل الخروج")
            ));
            confirmLogoutBtn.click();

            // 5️⃣ تحقق أن المستخدم عاد إلى الحالة الأولية (زائر)
            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("القائمة"))).click();

            WebElement userStatus = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//android.view.View[contains(@content-desc,'زائر')]")
            ));

            Assert.assertTrue(userStatus.getAttribute("content-desc").contains("زائر"),
                    "❌ لم يتم تسجيل الخروج بنجاح، ما زال المستخدم مسجلاً.");

            System.out.println("🚪✅ تم تنفيذ سيناريو تسجيل الخروج بنجاح.");

        } catch (Exception e) {
            Assert.fail("❌ فشل في تنفيذ سيناريو تسجيل الخروج: " + e.getMessage());
        }
    }
}
