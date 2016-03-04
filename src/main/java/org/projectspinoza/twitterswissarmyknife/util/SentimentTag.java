package org.projectspinoza.twitterswissarmyknife.util;

public enum SentimentTag {
    POSITIVE, NEGATIVE, NEUTRAL, VERY_POSITIVE, VERY_NEGATIVE;

    public static SentimentTag getTag(String sentiment) {
        for (SentimentTag tag : SentimentTag.values()) {
            if (sentiment.equalsIgnoreCase(tag.name())) {
                return tag;
            }
        }
        return null;
    }
}
