package org.uaic.taip.spark.strategy;

import org.apache.spark.streaming.api.java.JavaDStream;

import java.util.function.Function;


public enum StepType {
    FILTER {
        @Override
        JavaDStream apply(JavaDStream dStream, Function fn) {
            return dStream.filter(fn::apply);
        }
    },
    MAP {
        @Override
        JavaDStream apply(JavaDStream dStream, Function fn) {
            return dStream.map(fn::apply);
        }
    };

    abstract JavaDStream apply(JavaDStream dStream, Function fn);
}
