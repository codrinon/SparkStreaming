package org.uaic.taip.spark.strategy;

import org.apache.spark.streaming.api.java.JavaDStream;


public interface StreamingJobStrategy {

    int getDimeansion();

    JavaDStream apply(JavaDStream stream);
}
