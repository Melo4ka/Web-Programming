package ru.meldren.weblab2.database;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.domain.Bucket;
import com.influxdb.client.domain.BucketRetentionRules;
import com.influxdb.client.domain.WritePrecision;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.meldren.weblab2.Constant;

import java.time.Instant;
import java.util.List;

import static ru.meldren.weblab2.Constant.*;

/**
 * Created by Meldren on 22/10/2022
 */
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InfluxDatabase {

    InfluxDBClient client;
    @Getter
    String organization, bucket;

    public InfluxDatabase(String serverURL, String token, String organization, String bucket) {
        this.organization = organization;
        this.bucket = bucket;
        // Connect and create database
        client = InfluxDBClientFactory.create(serverURL, token.toCharArray(), organization, bucket);

        // Create bucket if it doesn't exist
        if (client.getBucketsApi().findBucketByName(bucket) == null) {
            client.getOrganizationsApi().findOrganizations()
                    .stream().filter(org -> org.getName().equals(organization))
                    .findAny().ifPresent(org -> client.getBucketsApi().createBucket(new Bucket()
                            .name(bucket)
                            .addRetentionRulesItem(new BucketRetentionRules()
                                    .everySeconds(0))
                            .orgID(org.getId())
                    ));
        }

        // Close connection if application is terminating
        Runtime.getRuntime().addShutdownHook(new Thread(client::close));
    }

    public long getElementsCount() {
        try {
            return (long) client.getQueryApi().query(String.format("""
                    from(bucket:"%s")
                    |> range(start: 0)
                    |> filter(fn: (r) => r._measurement == "%s" and r._field == "x")
                    |> count()
                    """, bucket, Constant.MEASUREMENT
            )).get(0).getRecords().get(0).getValueByKey("_value");
        } catch (Exception ex) {
            return 0;
        }
    }

    public void addElement(double x, double y, double r, long startTime) {
        Result result = new Result();
        result.x = x;
        result.y = y;
        result.r = r;
        result.executionTimeInMicros = (System.nanoTime() - startTime) / 1000;
        result.time = Instant.now();
        client.getWriteApiBlocking().writeMeasurement(WritePrecision.NS, result);
    }

    public List<Result> getElementsByPage(int page) {
        List<Result> results = client.getQueryApi().query(String.format("""
                        from(bucket:"%s")
                        |> range(start: 0)
                        |> filter(fn: (r) => r._measurement == "%s")
                        |> limit(n: %d, offset: %d)
                        |> pivot(rowKey: ["_time"], columnKey: ["_field"], valueColumn: "_value")
                        """,
                bucket, MEASUREMENT, ROWS_PER_PAGE,
                ROWS_PER_PAGE * (page - 1)
        ), Result.class);
        int startIndex = (page - 1) * ROWS_PER_PAGE + 1;
        for (int i = 0; i < results.size(); ++i) {
            Result result = results.get(i);
            result.number = startIndex + i;
            result.successful = isOnPlot(result.x, result.y, result.r);
        }
        return results;
    }
}
