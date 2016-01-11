package org.projectspinoza.twitterswissarmyknife.streaming;

import java.io.BufferedWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

/**
 * TwitterStatusStreams
 * 
 * @author org.projectspinoza
 * @version v1.0
 *
 */
public class TwitterStatusStreams implements StatusListener {
    private static Logger log = LogManager.getRootLogger();
    
    private boolean showStreaming;
    private BufferedWriter writer;

    public TwitterStatusStreams(String keywordsArray[], boolean showStreaming, BufferedWriter writer) {
        this.showStreaming(showStreaming);
        this.writer = writer;
    }

    @Override
    public void onException(Exception arg0) {
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice arg0) {
    }

    @Override
    public void onScrubGeo(long arg0, long arg1) {
    }

    @Override
    public void onStallWarning(StallWarning arg0) {
    }

    @Override
    public void onStatus(Status status) {
        if(!status.getLang().equals("en")){
            return;
        }
        try {
            String jsonTrend = new Gson().toJson(status);
            if (showStreaming) {
                log.info("Writble@{} - {}", status.getUser().getScreenName(), status.getText());
            }
            writer.write(jsonTrend);
            writer.newLine();
            writer.flush();
        } catch (IOException ioex) {
            log.error("Cannot write streaming statuses: {}", ioex.getMessage());
        }
    }

    @Override
    public void onTrackLimitationNotice(int arg0) {
    }

    public boolean showStreaming() {
        return showStreaming;
    }

    public void showStreaming(boolean showStreaming) {
        this.showStreaming = showStreaming;
    }
}