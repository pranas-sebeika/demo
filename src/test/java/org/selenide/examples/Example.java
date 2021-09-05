package org.selenide.examples;

import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.junit.*;
import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class Example {

  @After
  public void influxDb() {
    final long timestamp = System.currentTimeMillis();
    final String serverURL = "http://127.0.0.1:8086", username = "user", password = "user";
    final InfluxDB influxDB = InfluxDBFactory.connect(serverURL, username, password);

    // Enable batch writes to get better performance.
    influxDB.enableBatch(BatchOptions.DEFAULTS);

    influxDB.setDatabase("grafana");

    // Write points to InfluxDB.
    influxDB.write(Point.measurement("java_test_6")
            .time(timestamp, TimeUnit.MILLISECONDS)
            .tag("name", "test1")
            .tag("status", "passed")
            .addField("duration", "3s")
            .addField("status", 1)
            .build());

    influxDB.write(Point.measurement("java_test_6")
            .time(timestamp, TimeUnit.MILLISECONDS)
            .tag("name", "test2")
            .tag("status", "passed")
            .addField("duration", "55")
            .addField("status", 0)
            .build());

    influxDB.write(Point.measurement("java_test_6")
            .time(timestamp, TimeUnit.MILLISECONDS)
            .tag("name", "test3")
            .tag("status", "failed")
            .addField("duration", "13s")
            .addField("status", 0)
            .build());

    influxDB.write(Point.measurement("java_test_6")
            .time(timestamp, TimeUnit.MILLISECONDS)
            .tag("name", "test4")
            .tag("status", "passed")
            .addField("duration", "1123s")
            .addField("status", 1)
            .build());

    influxDB.write(Point.measurement("java_test_6")
            .time(timestamp, TimeUnit.MILLISECONDS)
            .tag("name", "test5")
            .tag("status", "passed")
            .addField("duration", "123s")
            .addField("status", 1)
            .build());

    influxDB.write(Point.measurement("java_test_6")
            .time(timestamp, TimeUnit.MILLISECONDS)
            .tag("name", "test6")
            .tag("status", "passed")
            .addField("duration", "45")
            .addField("status", 1)
            .build());

    influxDB.close();
  }

  @Test
  public void exampleTest() {
    open("https://duckduckgo.com/");
    $(By.name("q")).val("selenide").pressEnter();
    $$(".js-results").shouldHave(size(1));
    $$(".js-results .result").shouldHave(sizeGreaterThan(5));
    $(".js-results .result").shouldHave(text("selenide.org"));
  }
}
