package org.projectspinoza.twitterswissarmyknife;

import java.io.IOException;

import jline.console.ConsoleReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.projectspinoza.twitterswissarmyknife.util.TsakResponseWriter;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * TwitterStreamingExcecutor
 * contains logic for handling and executing twitter streaming API commands.
 * 
 * @author org.projectspinoza
 * @version v1.0
 *
 */
public class TwitterStreamingExcecutor {
    private static Logger log = LogManager.getRootLogger();
    private TwitterStream twitterStream;
    /**
     * executes twitter streaming command.
     * 
     * @param configurationBuilder
     * @param streamStatuses
     * @throws IOException
     */
    public void execute(ConfigurationBuilder configurationBuilder,
            CommandStreamStatuses streamStatuses) throws IOException {

        TsakResponseWriter responseWriter = new TsakResponseWriter(
                streamStatuses.outputFile);
        streamedStatuses(configurationBuilder, streamStatuses, responseWriter);
        final ConsoleReader reader = new ConsoleReader();
        while (true) {
            String commandLine = reader.readLine();
            if (commandLine.trim().equals("exit")) {
                twitterStream.clearListeners();
                twitterStream.shutdown();
            }
        }
    }
    /**
     * dump twitter streaming statuses.
     * 
     * @param configurationBuilder
     * @param streamStatuses
     * @param responseWriter
     */
    private void streamedStatuses(ConfigurationBuilder configurationBuilder,
            CommandStreamStatuses streamStatuses,
            TsakResponseWriter responseWriter) {

        String keywords = streamStatuses.keywords;
        String keywordsArray[] = keywords.split(",");
        if (streamStatuses.store_streaming_data.equals("true")
                || streamStatuses.store_streaming_data.equals("false")) {
            twitterStream = new TwitterStreamFactory(
                    configurationBuilder.build()).getInstance();
            TwitterStatusStreams statusStreams = new TwitterStatusStreams(
                    keywordsArray,
                    Boolean.parseBoolean(streamStatuses.store_streaming_data),
                    responseWriter);
            FilterQuery filterQuery = new FilterQuery();
            filterQuery.track(keywordsArray);
            twitterStream.addListener(statusStreams);
            twitterStream.filter(filterQuery);
        } else {
            log.error("Error command argument. -store expects true|false found "
                    + streamStatuses.store_streaming_data);
        }
    }
}
