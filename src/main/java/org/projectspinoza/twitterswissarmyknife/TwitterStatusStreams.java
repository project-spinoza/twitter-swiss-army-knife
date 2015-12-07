package org.projectspinoza.twitterswissarmyknife;

import org.projectspinoza.twitterswissarmyknife.util.TsakResponseWriter;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class TwitterStatusStreams implements StatusListener {

    private boolean store_streaming_data;
    private TsakResponseWriter writer;

    public TwitterStatusStreams(String keywordsArray[],
            boolean store_stream_data, TsakResponseWriter writer) {
        this.setStore_streaming_data(store_stream_data);
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
        if (store_streaming_data) {
            writer.write(status, "streamStatuses", null);
            System.out.println("Writble@" + status.getUser().getScreenName()
                    + " - " + status.getText());
        } else {
            System.out.println("@" + status.getUser().getScreenName() + " - "
                    + status.getText());
        }
    }

    @Override
    public void onTrackLimitationNotice(int arg0) {
    }

    public boolean isStore_streaming_data() {
        return store_streaming_data;
    }

    public void setStore_streaming_data(boolean store_streaming_data) {
        this.store_streaming_data = store_streaming_data;
    }
}