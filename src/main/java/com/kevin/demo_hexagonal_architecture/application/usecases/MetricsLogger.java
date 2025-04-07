package com.kevin.demo_hexagonal_architecture.application.usecases;

public interface MetricsLogger {
    void logMetric(String metricName, double value);
    void logInfo(String message);
    void logError(String message, Throwable ex);
}
