package org.uaic.taip.spark.strategy;

import org.apache.spark.streaming.api.java.JavaDStream;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ConfigurableStreamingJobStrategyImpl implements StreamingJobStrategy {
    private static int DIMENSION_MILLIS = 1000;
    private List<Tuple2<StepType, Function>> steps;

    private ConfigurableStreamingJobStrategyImpl(List<Tuple2<StepType, Function>> steps){
        this.steps = steps;
    }

    public int getDimeansion() {
        return DIMENSION_MILLIS;
    }

    public JavaDStream apply(JavaDStream stream) {
        for (Tuple2<StepType, Function> tuple2: steps) {
            StepType type = tuple2._1();
            Function fn = tuple2._2();
            stream = type.apply(stream, fn);
        }

        return stream;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {

        private List<Tuple2<StepType, Function>> steps;

        private Builder() {this.steps = new ArrayList<>();}

        Builder withMapStep(Function fn) {
            this.steps.add(Tuple2.apply(StepType.MAP, fn));
            return this;
        }
        Builder withFilterStep(Function fn) {
            this.steps.add(Tuple2.apply(StepType.FILTER, fn));
            return this;
        }

        ConfigurableStreamingJobStrategyImpl build() {
            return new ConfigurableStreamingJobStrategyImpl(steps);
        }
    }
}
