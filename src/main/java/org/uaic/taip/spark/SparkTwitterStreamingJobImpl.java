package org.uaic.taip.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.twitter.TwitterUtils;
import org.uaic.taip.spark.strategy.StreamingJobStrategy;
import twitter4j.Status;

import static org.apache.commons.crypto.utils.ReflectionUtils.getClassByName;

public class SparkTwitterStreamingJobImpl {
    public static void main(String args[]) {
        if(args.length < 5) {
            System.err.println("Invalid number of arguments provided");
            System.exit(1);
        }

        String consumerKey = args[0];
        String consumerSecret = args[1];
        String accessToken = args[2];
        String accessTokenSecret = args[3];

        // Set the system properties so that Twitter4j library used by Twitter stream
        // can use them to generate OAuth credentials
        System.setProperty("twitter4j.oauth.consumerKey", consumerKey);
        System.setProperty("twitter4j.oauth.consumerSecret", consumerSecret);
        System.setProperty("twitter4j.oauth.accessToken", accessToken);
        System.setProperty("twitter4j.oauth.accessTokenSecret", accessTokenSecret);
    }

    public void run(String args[]) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String jobStrategyClass = args[4];
        StreamingJobStrategy strategy = (StreamingJobStrategy) getClassByName(jobStrategyClass).newInstance();

        SparkConf sparkConf = new SparkConf().setAppName("TwitterStreaming");

        JavaStreamingContext jsc = new JavaStreamingContext(sparkConf, new Duration(strategy.getDimeansion()));

        JavaDStream<Status> statuses = TwitterUtils.createStream(jsc);

        statuses = strategy.apply(statuses);

        jsc.start();
    }
}
