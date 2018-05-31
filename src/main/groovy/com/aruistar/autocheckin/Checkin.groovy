package com.aruistar.autocheckin

import geb.Browser
import geb.Page
import groovy.util.logging.Slf4j
import org.openqa.selenium.firefox.FirefoxBinary
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions

@Slf4j
class Checkin {
    static def checkin(String username, String password, String text) {

//        System.setProperty("geb.build.baseUrl", "https://m.jd.com/")

        FirefoxBinary firefoxBinary = new FirefoxBinary();
//        firefoxBinary.addCommandLineOptions("--headless");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);

//        def browser = new Browser(driver: new FirefoxDriver())
        def browser = new Browser(driver: new FirefoxDriver(firefoxOptions))

        browser.with {

            go "https://m.jd.com"

            $(".jd-search-icon-login").click()

            $("#username").value(username)
            $("#password").value(password)

            $("#loginBtn").click()

            sleep(5000)

            go "https://pro.m.jd.com/mall/active/qKRVTAJL7v93L71TkJebPv5GJnE/index.html"


            def target = new Date(2018 - 1900, 4, 31, 14, 0)

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(target);
            calendar.add(Calendar.SECOND, 10)
            def after1 = calendar.getTime()

            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(target);
            calendar2.add(Calendar.SECOND, -2)
            def before1 = calendar2.getTime()

            while (new Date() < after1 && new Date() > before1) {

                $("#m_1_14").children().each {
                    it.click()
                    sleep(500)
                }

                sleep(500)
            }


        }
    }
}

