package com.independentarabia.tests;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomeTest extends BaseTest{
    @Test
    public void testHomeSectionIsVisible() {
        //  تخطي شاشة التسجيل
        skipLogin();

        //  التحقق من ظهور "الرئيسية"
        WebElement homeElement = driver.findElement(AppiumBy.accessibilityId("الرئيسية"));

        Assert.assertNotNull(homeElement, " لم يتم العثور على قسم الرئيسية.");
    }

}
