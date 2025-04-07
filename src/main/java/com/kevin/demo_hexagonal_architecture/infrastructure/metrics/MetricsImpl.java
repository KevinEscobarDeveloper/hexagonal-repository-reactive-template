package com.kevin.demo_hexagonal_architecture.infrastructure.metrics;

import com.kevin.demo_hexagonal_architecture.application.usecases.MetricsLogger;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class MetricsImpl implements MetricsLogger {
    private final MeterRegistry meterRegistry;
    @Override
    public void logMetric(String metricName, double value) {

    }

    @Override
    public void logInfo(String message) {

    }

    @Override
    public void logError(String message, Throwable ex) {

    }
}
