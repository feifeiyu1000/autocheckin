package com.aruistar.autocheckin

import geb.Browser
import geb.Page
import org.openqa.selenium.firefox.FirefoxDriver

class Application {
    static void main(String[] args) {

        //change it
        def USER_NAME = ""
        def PASS_WORD = ""

        //change it,and  you can download the driver from here https://github.com/mozilla/geckodriver/releases
        System.setProperty("webdriver.gecko.driver", "/Users/liurui/Documents/develop/webdriver/geckodriver")

        def browser = new Browser(driver: new FirefoxDriver())

        browser.with {
            to LoginPage
            username.value(USER_NAME)
            password.value(PASS_WORD)
            loginButton.click()
            waitFor(10, 2) { rizhiguanli.click() }
            waitFor(15, 2) { treeNodeLevel1.click() }
            treeNodeLevel2.click()

            sleep(5 * 1000)
            withFrame(myFrame) {
                add.click()
            }

            sleep(3 * 1000)
            textares.value("今天日志........................")

            submit.click()


        }
    }
}

class LoginPage extends Page {
    static url = "http://checkin2.longruan.com"

    static content = {
        loginButton(to: AdminPage) { $("#btnLogin") }
        username { $("input", name: "userName") }
        password { $("input", name: "userPass") }
    }
}

class AdminPage extends Page {
    static content = {

        rizhiguanli { $("a", text: "日志管理") }
        treeNodeLevel1 { $("span", text: "日志管理") }
        treeNodeLevel2 { $("span", text: "个人日志管理") }


        myFrame(page: FrameDescribingPage) { $("iframe", name: "个人日志管理") }

        textares { $("#WorkLog1") }
        submit { $("span", text: "确定", class: "l-btn-text icon-ok l-btn-icon-left") }
    }
}

class FrameDescribingPage extends Page {
    static content = {
        add { $("#link-Add") }

    }
}
