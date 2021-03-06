package com.bars.tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.junit.ScreenShooter;
import com.codeborne.selenide.junit.TextReport;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.stream.Collectors;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;


public class ACalculationChKOTest {
    @Rule
    public ScreenShooter screenShooter = ScreenShooter.failedTests().to("test-results/reports");
    @Rule
    public TestRule report = new TextReport().onFailedTest(true).onSucceededTest(false);

    @BeforeClass
    public static void setup() {
        timeout = 80000;
//        baseUrl = "http://10.10.17.22:8080/barsroot/account/login";
//        baseUrl = "http://10.10.17.24:8080/barsroot/account/login";
//        baseUrl = "http://10.10.17.50:8080/barsroot/account/login";
//        baseUrl = "http://10.10.17.40:8080/barsroot/account/login";
//        baseUrl = "http://10.10.17.40:8082/barsroot/account/login";
//        baseUrl = "http://10.10.10.198:11111/barsroot/account/login";
//        browser = "chrome";
        browser = "ie";
        startMaximized = true;
//        System.setProperty("webdriver.ie.driver", ".\\IEDriverServer.exe");
        InternetExplorerDriverManager.getInstance(DriverManagerType.IEXPLORER).arch32().setup();
//        ChromeDriverManager.getInstance(DriverManagerType.CHROME).setup();
        open("/");

    }
    @Test


    public void ACalculationChKO() {
        $("#txtUserName").setValue("absadm01");
        $("#txtPassword").setValue("qwerty").pressEnter();
//        $("#btLogIn").click();
        $(By.xpath("//input[@value = 'Продовжити']")).click();
        switchTo().frame($("#mainFrame"));
        $(By.tagName("h1")).shouldHave(text("Оголошення"));
        switchTo().defaultContent();
        $(".btn_branches").shouldBe(visible).click();
        $(byXpath("//div[@id='treeview']/ul/li/ul/li/div/span[2]/span")).shouldBe(visible).shouldHave(text("300465")).click();
        getWebDriver().navigate().refresh();
        $("#findOpersText").setValue("Ліміти.").pressEnter();
        $(By.xpath("//*[text()='Ліміти.']")).shouldBe(visible).click();
        switchTo().frame($("#mainFrame"));
        $(By.xpath("(//*[@class='x-grid-cell-inner x-grid-cell-inner-row-numberer'])[text()='2']")).shouldBe(visible).click();
        String OverdraftLimWindow = getWebDriver().getWindowHandle();
        $(By.xpath("//*[@class='x-btn-icon-el INSERT ']")).shouldBe(visible).click();
        for(String windowsHandls : getWebDriver().getWindowHandles()) {
            if(!windowsHandls.equals(OverdraftLimWindow)){
                getWebDriver().switchTo().window(windowsHandls);
                getWebDriver().manage().window().maximize();
            }
        }
        $(By.xpath("//input[@name='X']")).shouldBe(visible).setValue("01.01.2018");
        $(byText("Виконати")).click();
        $(byText("Далі")).click();
        $$(By.xpath("//*[@class='x-grid-cell-inner ']")).shouldBe(CollectionCondition.sizeGreaterThanOrEqual(12));
        String Result = $(By.xpath("(//tfoot/tr/td/div)[10]")).shouldBe(not(empty)).getText();
        System.out.println("Ркзультат: " + Result);
        List<SelenideElement> CHKO = $$(By.xpath("//*[@class='x-grid-cell-inner ']"));
        List<String> List = CHKO.stream().map(WebElement::getText).collect(Collectors.toList());
        System.out.println("Список ЧКО: " + List);
    }
}