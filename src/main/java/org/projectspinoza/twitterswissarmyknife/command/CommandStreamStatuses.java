package org.projectspinoza.twitterswissarmyknife.command;

import java.io.BufferedWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.projectspinoza.twitterswissarmyknife.streaming.TwitterStatusStreams;
import org.projectspinoza.twitterswissarmyknife.util.TsakResponse;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import jline.console.ConsoleReader;
import twitter4j.FilterQuery;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

@Parameters(commandNames = "streamStatuses", commandDescription = "Stream Statuses on specified keywords.")
public class CommandStreamStatuses extends BaseCommand {
    private static Logger log = LogManager.getRootLogger();
    
    @Parameter(names = "-keywords", description = "Status containing comma separated Keywords.", required = true)
    private String keywords;
    @Parameter(names = "-show", description = "use parameter [-show] to display streaming on console")
    private boolean showStreaming = false;
    
    TwitterStream twitterStream;
    TwitterStatusStreams statusStreams;
    
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public boolean showStreaming() {
        return showStreaming;
    }

    public void showStreaming(boolean showStreaming) {
        this.showStreaming = showStreaming;
    }

    @Override
    public TsakResponse execute(Twitter twitter) throws TwitterException {
        twitterStream = new TwitterStreamFactory(twitter.getConfiguration()).getInstance();
        TsakResponse tsakResponse = new TsakResponse(15, null);
        tsakResponse.setCommandDetails(this.toString());
        return tsakResponse;
    }

    @Override
    public void write(TsakResponse tsakResponse, BufferedWriter writer) throws IOException {
        String keywords = getKeywords();
        if(keywords == null){
            log.error("missing -keywords {}", keywords);
            return;
        }
        keywords = keywords.trim();
        String keywordsArray[] = keywords.split(",");
        statusStreams = new TwitterStatusStreams(keywordsArray, showStreaming(), writer);

        FilterQuery filterQuery = new FilterQuery();
        filterQuery.track(keywordsArray);
        twitterStream.addListener(statusStreams);
        twitterStream.filter(filterQuery);
        
        log.info("Enter exit to terminate streaming!!!");
        waitForUserExitCommand();
    }
    private void waitForUserExitCommand() throws IOException{
        final ConsoleReader reader = new ConsoleReader();
        boolean run = true;
        while (run) {
            String commandLine = reader.readLine();
            if (commandLine.trim().equals("exit")) {
                run = false;
                twitterStream.clearListeners();
                twitterStream.shutdown();
            }
        }
    }

    @Override
    public String toString() {
        return "CommandStreamStatuses [keywords=" + keywords + ", storeStreamingStatus=" + showStreaming + "]";
    }
    
    

}