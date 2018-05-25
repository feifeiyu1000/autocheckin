package com.aruistar.autocheckin

import geb.Browser
import geb.Page
import groovy.util.logging.Slf4j
import org.openqa.selenium.firefox.FirefoxBinary
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions

@Slf4j
class Checkin {
    static def checkin(String username, String password, String text ) {


        System.setProperty("geb.build.baseUrl", "http://checkin2.longruan.com")

        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);

//        def browser = new Browser(driver: new FirefoxDriver())
        def browser = new Browser(driver: new FirefoxDriver(firefoxOptions))

        browser.with {
            log.info("打开登录页面...")
            to LoginPage
            log.info("登录页面打开成功，准备登录...")
            usernameInput.value(username)
            passwordInput.value(password)
            loginButton.click()
            log.info("登录成功")

            log.info("跳转至日志页面...")
            to CheckinPage
            log.info("跳转日志页面成功")

            sleep(5 * 1000)
            add.click()
            log.info("弹出新增日志窗口")

            sleep(5 * 1000)
            log.info("填写日志内容为：$text")
            textares.value(text)


            submit.click()
            log.info("日志填写完成")

        }
    }
}

class LoginPage extends Page {
    static content = {
        loginButton { $("#btnLogin") }
        usernameInput { $("input", name: "userName") }
        passwordInput { $("input", name: "userPass") }
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