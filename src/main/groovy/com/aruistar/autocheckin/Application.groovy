package com.aruistar.autocheckin

import geb.Browser
import geb.Page
import org.openqa.selenium.firefox.FirefoxBinary
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions

class Application {
    static void main(String[] args) {

        //change it
        def USER_NAME = ""
        def PASS_WORD = ""

        //change it,and  you can download the driver from here https://github.com/mozilla/geckodriver/releases
        System.setProperty("webdriver.gecko.driver", "/Users/liurui/Documents/develop/webdriver/geckodriver")
        System.setProperty("geb.build.baseUrl", "http://checkin2.longruan.com")

        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);

//        def browser = new Browser(driver: new FirefoxDriver())
        def browser = new Browser(driver: new FirefoxDriver(firefoxOptions))

        browser.with {
            to LoginPage
            username.value(USER_NAME)
            password.value(PASS_WORD)
            loginButton.click()


            to CheckinPage

            sleep(5 * 1000)
            add.click()

            sleep(5 * 1000)
            textares.value("""xxxxxxxxx
""")

            submit.click()


        }
    }
}

class LoginPage extends Page {
    static content = {
        loginButton { $("#btnLogin") }
        username { $("input", name: "userName") }
        password { $("input", name: "userPass") }
    }
}


class CheckinPage extends Page {

    static url = "/worklog/RZXX/GRRZ/Index"

    static content = {
        add { $("#link-Add") }
        textares { $("#WorkLog1") }
        submit { $("span", text: "确定", class: "l-btn-text icon-ok l-btn-icon-left") }
    }
}