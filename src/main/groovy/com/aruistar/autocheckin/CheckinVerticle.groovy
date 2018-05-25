package com.aruistar.autocheckin

import com.diabolicallabs.vertx.cron.CronEventSchedulerVertical
import groovy.util.logging.Slf4j
import io.vertx.core.AbstractVerticle
import io.vertx.core.json.JsonObject

@Slf4j
class CheckinVerticle extends AbstractVerticle {
    @Override
    void start() throws Exception {
        log.info(config().toString())


        JsonObject event = new JsonObject()
                .put("cron_expression", config().getString("cron"))
                .put("address", "scheduled.address")
                .put("message", "squid")
                .put("action", "publish")
                .put("timezone_name", "Asia/Shanghai")

        vertx.deployVerticle(CronEventSchedulerVertical.class.getName(), { result ->
            if (result.succeeded()) {
                log.info("deploy CronEventSchedulerVertical OK")

                vertx.eventBus().send("cron.schedule", event, { handler ->
                    log.info(handler.succeeded().toString())
                })

            }

        })

        vertx.eventBus().consumer("scheduled.address", { handler ->

            String username = config().getString("username")
            String password = config().getString("password")
            String content = config().getString("content")

            System.setProperty("webdriver.gecko.driver", config().getString("driver"))

            log.info("准备为：$username 编写日志")
            vertx.executeBlocking({ future ->
                Checkin.checkin(username, password, content)
                future.complete()
            }, false, {
            })

        })


    }
}
