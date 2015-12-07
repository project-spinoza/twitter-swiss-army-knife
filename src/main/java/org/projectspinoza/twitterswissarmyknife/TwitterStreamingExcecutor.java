package org.projectspinoza.twitterswissarmyknife;

import java.io.IOException;
import jline.console.ConsoleReader;
import org.projectspinoza.twitterswissarmyknife.util.TsakResponseWriter;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterStreamingExcecutor {
    private TwitterStream twitterStream;
    public void execute(ConfigurationBuilder configurationBuilder,
            CommandStreamStatuses streamStatuses) throws IOException{
        
        TsakResponseWriter responseWriter = new TsakResponseWriter(streamStatuses.outputFile);
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
    private void streamedStatuses(ConfigurationBuilder configurationBuilder,
            CommandStreamStatuses streamStatuses, TsakResponseWriter responseWriter) {

        String keywords = streamStatuses.keywords;
        String keywordsArray[] = keywords.split(",");
        if (streamStatuses.store_streaming_data.equals("true")|| streamStatuses.store_streaming_data.equals("false")) {
            twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();
            TwitterStatusStreams statusStreams = new TwitterStatusStreams(keywordsArray, Boolean.parseBoolean(streamStatuses.store_streaming_data), responseWriter);
            FilterQuery filterQuery = new FilterQuery();
            filterQuery.track(keywordsArray);
            twitterStream.addListener(statusStreams);
            twitterStream.filter(filterQuery);
        }else {
        	System.out.println("Error command argument.");
        }
    }
}
