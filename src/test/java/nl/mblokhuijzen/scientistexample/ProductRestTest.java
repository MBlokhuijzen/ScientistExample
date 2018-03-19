package nl.mblokhuijzen.scientistexample;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.github.rawls238.scientist4j.Experiment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ProductRestTest {
    ProductRest productRest = new ProductRest();
    private Experiment<Product> experiment;
    private ConsoleReporter reporter;


    @BeforeEach
    public void setup() {
        experiment = new Experiment<>("product", true);
        MetricRegistry metrics = experiment.getMetrics(null);

        reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    @Test
    void scientistExperiment() {
        for (int i = 0; i < 100; i++) {
            int randomNumber = (int) (Math.random() * 5 + 1);

            Supplier<Product> oldCodePath = () -> productRest.getProductsV1(randomNumber);
            Supplier<Product> newCodePath = () -> productRest.getProductsV2(randomNumber);

            try {
                Product experimentResult = experiment.runAsync(oldCodePath, newCodePath);
                assertEquals(experimentResult, productRest.getProductsV1(randomNumber));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        reporter.report();
    }
}