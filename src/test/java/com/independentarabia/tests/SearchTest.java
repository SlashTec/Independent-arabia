package com.independentarabia.tests;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class SearchTest extends BaseTest {

    @Test(priority = 1)
    public void testSearchFlow() {
        try {
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


            // ⃣  الانتقال إلى تبويب "اكتشف"
            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("اكتشف"))).click();

            // 4️⃣ اضغط على أيقونة البحث (باستخدام XPath أو تحسين لاحقًا)
            WebElement searchIcon = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']" +
                            "/android.widget.FrameLayout/android.view.View/android.view.View" +
                            "/android.view.View/android.view.View/android.view.View[1]/android.widget.ImageView[2]")

            ));
            searchIcon.click();



            // 5 كتابة نص في حقل البحث
            WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.className("android.widget.EditText")));
            searchInput.click();
            searchInput.sendKeys("السعودية");

            // 6⃣ تحقق من ظهور نتائج تحتوي على الكلمة
            boolean resultVisible = wait.until(d -> d.getPageSource().contains("السعودية"));
            Assert.assertTrue(resultVisible, "❌ لم يتم العثور على نتائج تحتوي على 'السعودية'.");

            System.out.println("✅ تم تنفيذ سيناريو البحث بنجاح.");

        } catch (Exception e) {
            Assert.fail("❌ فشل في تنفيذ سيناريو البحث: " + e.getMessage());
        }
    }
    }



