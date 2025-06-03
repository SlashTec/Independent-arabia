package com.independentarabia.tests;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class ArticleInteractionTest extends BaseTest {


    @Test
    public void testArticleFullFlow() throws InterruptedException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // ⏩ تسجيل الدخول
            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("تسجيل الدخول"))).click();

            WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.EditText[1]")));
            emailField.click();
            emailField.sendKeys("asilyacoub66@gmail.com");

            WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.EditText[2]")));
            passwordField.click();
            passwordField.sendKeys("123456789");

            wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.xpath("//android.widget.Button[@content-desc='تسجيل الدخول ']"))).click();

            // ⬇ تخطّي نافذة "في وقت لاحق"
            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("في وقت لاحق"))).click();

            // ⬇ تأكيد الدخول للصفحة الرئيسية
            Assert.assertTrue(driver.getPageSource().contains("الرئيسية"), " لم يتم الوصول إلى الرئيسية");

            Thread.sleep(2000);

            //  حفظ حالة الصفحة قبل النقر (عشان نقارن بعدها)
            String homePageSource = driver.getPageSource();

            Thread.sleep(2000); // انتظار استقرار العناصر

            //  البحث عن أول مقالة لا تحتوي على بودكاست
            List<WebElement> articles = driver.findElements(By.xpath("//android.widget.ScrollView//android.view.View"));
            boolean articleClicked = false;

            for (WebElement article : articles) {
                String desc = article.getAttribute("content-desc");
                if (desc != null && !desc.toLowerCase().contains("بودكاست")) {
                    article.click();
                    articleClicked = true;
                    break;
                }
            }

            Assert.assertTrue(articleClicked, "❌ لم يتم العثور على مقالة غير بودكاست");

            // ✅ بعد النقر، ننتظر شوي ونتأكد إن الصفحة تغيّرت
            Thread.sleep(3000);
            String currentPageSource = driver.getPageSource();
            Assert.assertNotEquals(currentPageSource, homePageSource, "❌ لم يتم الانتقال لصفحة المقالة");

            System.out.println("✅ تم فتح مقالة بنجاح والتأكد من الانتقال");

        }catch(Exception e){
                e.printStackTrace();
                Assert.fail("❌ فشل تنفيذ سيناريو فتح المقالة: " + e.getMessage());
            }


        }
    }















