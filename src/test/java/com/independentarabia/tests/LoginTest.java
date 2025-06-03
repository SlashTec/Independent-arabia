package com.independentarabia.tests;




import org.openqa.selenium.io.Zip;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

@Listeners(TestListener.class)
public class LoginTest extends BaseTest {


    // ✅ الحالة 1: تسجيل دخول ناجح
    @Test(priority = 1)
    public void testLoginSuccess() {
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
            // تسجيل الخروج إن وُجد "الرئيسية"
            if (driver.getPageSource().contains("الرئيسية")) {
                WebElement menuBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                        AppiumBy.accessibilityId("القائمة")));
                menuBtn.click();
                WebElement logoutBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                        AppiumBy.accessibilityId("تسجيل خروج")));
                logoutBtn.click();
                System.out.println("🚪 تم تسجيل الخروج قبل بدء الاختبار.");
                WebElement confirmLogoutBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                        AppiumBy.xpath("//android.widget.Button[@content-desc='تسجيل الخروج']")));
                confirmLogoutBtn.click();  // ⬅️ ثاني ضغطة

                System.out.println("🚪✅ تم تسجيل الخروج بنجاح من التنبيه.");
            }


        } catch (Exception e) {
            Assert.fail(" فشل في تسجيل الدخول الصحيح: " + e.getMessage());
        }

    }

    // ❌ الحالة 2: تسجيل ببيانات خاطئة
    @Test(priority = 2)
    public void testLoginInvalidCredentials() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement loginBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.accessibilityId("تسجيل الدخول")));
            loginBtn.click();

            WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']" +
                            "/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.widget.EditText[1]")));
            emailField.click();
            emailField.sendKeys("wrong@email.com");

            WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']" +
                            "/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.widget.EditText[2]")));
            passwordField.click();
            passwordField.sendKeys("wrongpass");

            WebElement submitLoginBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.xpath("//android.widget.Button[@content-desc='تسجيل الدخول ']")));
            submitLoginBtn.click();

            WebElement errorPopup = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//android.view.View[@content-desc='كلمة المرور أو البريد الإلكتروني خاطئ']")));
            Assert.assertTrue(errorPopup.isDisplayed(), "❌ لم تظهر رسالة الخطأ رغم إدخال بيانات خاطئة.");

            // إغلاق نافذة التنبيه إذا ظهرت
            try {
                WebElement closeBtn = driver.findElement(By.xpath("//android.widget.Button[@content-desc='إغلاق']"));
                closeBtn.click();
                System.out.println("🧹 تم إغلاق رسالة التنبيه السابقة.");
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            Assert.fail("❌ فشل في تسجيل الدخول ببيانات خاطئة: " + e.getMessage());
        }
    }

    // ⚠️ الحالة 3: تسجيل بدون إدخال بيانات (فارغة)
    @Test(priority = 3)
    public void testLoginWithEmptyFields() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // افتح شاشة تسجيل الدخول
            WebElement loginBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    AppiumBy.accessibilityId("تسجيل الدخول")));
            loginBtn.click();

            // الضغط على زر تسجيل الدخول بدون إدخال شيء
            WebElement submitLoginBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    AppiumBy.xpath("//android.widget.Button[@content-desc='تسجيل الدخول ']")));
            submitLoginBtn.click();

            // ✅ التحقق من ظهور الرسالة باستخدام contains بدل المطابقة التامة
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//android.view.View[contains(@content-desc, 'الرجاء تعبئة')]")));
            Assert.assertTrue(errorMsg.isDisplayed(), "❌ رسالة الخطأ لم تظهر أو لم تُعرض بالشكل المتوقع.");

            // ✅ التحقق من زر الإغلاق
            WebElement closeBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//android.widget.Button[@content-desc='إغلاق']")));
            Assert.assertTrue(closeBtn.isDisplayed(), "❌ لم يظهر زر 'إغلاق'.");

            // ✅ إغلاق الرسالة
            closeBtn.click();

            // ✅ تأكيد أنك ما زلت في صفحة تسجيل الدخول
            Assert.assertTrue(driver.getPageSource().contains("نسيت كلمة السر"), "❌ لم تبقَ على صفحة تسجيل الدخول بعد إغلاق التنبيه.");

            System.out.println("✅ اختبار الحقول الفارغة نجح بالكامل.");

        } catch (Exception e) {
            Assert.fail("❌ فشل في اختبار الحقول الفارغة: " + e.getMessage());
        }
    }

    @Test(priority = 4)
    public void testLoginWithInvalidEmailFormat() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // الدخول إلى شاشة تسجيل الدخول
            WebElement loginBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.accessibilityId("تسجيل الدخول")));
            loginBtn.click();

            // إدخال إيميل غير صحيح الصيغة (بدون @ أو .com)
            WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.xpath("//android.widget.EditText[1]")));
            emailField.click();
            emailField.sendKeys("wrongemail");

            // كلمة المرور أي قيمة
            WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.xpath("//android.widget.EditText[2]")));
            passwordField.click();
            passwordField.sendKeys("anyPass123");

            // انقر تسجيل الدخول
            WebElement submitLoginBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.xpath("//android.widget.Button[@content-desc='تسجيل الدخول ']")));
            submitLoginBtn.click();

            // تحقق من ظهور التنبيه الخاص بالبنية الخاطئة للإيميل
            WebElement formatError = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//android.view.View[contains(@content-desc,'يجب أن يكون البريد الالكتروني')]")));
            Assert.assertTrue(formatError.isDisplayed(), "❌ لم تظهر رسالة تنبيه تنسيق البريد.");

            // زر الإغلاق
            WebElement closeBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//android.widget.Button[@content-desc='إغلاق']")));
            closeBtn.click();
            System.out.println("✅ تم التحقق من تنسيق الإيميل الخاطئ بنجاح.");

        } catch (Exception e) {
            Assert.fail("❌ فشل في التحقق من تنسيق البريد الإلكتروني: " + e.getMessage());
        }
    }



    @Test(priority = 5)
    public void testLoginSuccess_Google() {
        try {
            // الدخول إلى شاشة تسجيل الدخول
            WebElement loginBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.accessibilityId("تسجيل الدخول")));

            loginBtn .click() ;

            // اضغط على "تسجيل الدخول عبر غوغل"
            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("تسجيل الدخول عبر غوغل"))).click();


            // الانتظار لظهور نافذة اختيار الحساب
            List<WebElement> accounts = driver.findElements(By.id("com.google.android.gms:id/account_display_name"));
            if (accounts.isEmpty()) {
                Assert.fail("❌ لم يتم العثور على نافذة اختيار حساب جوجل.");
            }

            // اختيار أول حساب
            accounts.get(0).click();


            // التأكد من الوصول للرئيسية
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            boolean loggedIn = wait.until(d -> d.getPageSource().contains("الرئيسية"));
            Assert.assertTrue(loggedIn, "❌ لم يتم الدخول للصفحة الرئيسية بالرغم من اختيار حساب جوجل.");

            System.out.println("✅ تسجيل الدخول بحساب جوجل تم بنجاح.");

            // تسجيل الخروج بعد التأكد
            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("القائمة"))).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("تسجيل خروج"))).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.Button[@content-desc='تسجيل الخروج']"))).click();

            System.out.println("🚪✅ تم تسجيل الخروج بنجاح بعد الاختبار.");

        } catch (Exception e) {
            Assert.fail("❌ فشل سيناريو تسجيل الدخول بحساب جوجل: " + e.getMessage());
        }
    }


    @Test(priority = 6)
    public void successfulFacebookLogin() throws InterruptedException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // 1️⃣ الانتقال إلى شاشة تسجيل الدخول مباشرة (إذا لم تكن ظاهرة تلقائيًا)
            WebElement facebookLoginBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.accessibilityId("تسجيل الدخول عبر فيسبوك")));
            facebookLoginBtn.click();
            System.out.println("✅ تم النقر على زر 'تسجيل الدخول عبر فيسبوك'");

            // 2️⃣ الانتظار حتى تظهر صفحة تسجيل دخول فيسبوك
            Thread.sleep(5000); // يمكن استبداله لاحقًا بـ انتظار عنصر من صفحة فيسبوك

            // 3️⃣ التحقق من وجود زر "متابعة باسم..." داخل صفحة فيسبوك
            WebElement continueBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//android.widget.Button[contains(@text,'متابعة')]")));
            Assert.assertTrue(continueBtn.isDisplayed(), "❌ لم يظهر زر 'متابعة باسم' داخل صفحة فيسبوك");

            System.out.println("✅ زر 'متابعة' ظهر");

            // 4️⃣ تنفيذ النقر عبر Touch Action إذا ما اشتغل عادي
            int x = (42 + 1039) / 2;
            int y = (1913 + 2021) / 2;

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 1);
            tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(List.of(tap));

            System.out.println("👆 تم تنفيذ النقر اليدوي على زر 'متابعة باسم ...'");

            Thread.sleep(5000); // ⏳ انتظار عودة التطبيق

            // 5️⃣ التحقق أن المستخدم لم يعد زائرًا
            boolean notGuest = !driver.getPageSource().contains("زائر");
            Assert.assertTrue(notGuest, "❌ لا يزال المستخدم زائرًا بعد محاولة تسجيل الدخول");

            System.out.println("✅ تسجيل الدخول عبر فيسبوك تم بنجاح");

        } catch (Exception e) {
            Assert.fail("❌ فشل تسجيل الدخول عبر فيسبوك: " + e.getMessage());
        }
    }
}































