package org.projectspinoza.twitterswissarmyknife.util;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class SentimentTagger {

    private StanfordCoreNLP pipeline;

    public SentimentTagger() {
        this(null);
    }

    public SentimentTagger(Properties props) {
        if (props == null) {
            props = new Properties();
            props.put("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment, ner,dcoref");
            props.put("ner.model", "edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz");
        }
        this.pipeline = new StanfordCoreNLP(props);
    }

    public SentimentTag getSentimentTag(String Sentense) {
        int positive = 0;
        int negative = 0;
        int neutral = 0;
        Annotation annotation = pipeline.process(Sentense);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            if (sentiment == null) {
                continue;
            }
            
            if (sentiment.equalsIgnoreCase("Positive")) {
                positive++;
            } else if (sentiment.equalsIgnoreCase("Negative")) {
                negative++;
            } else if (sentiment.equalsIgnoreCase("Neutral")) {
                neutral++;
            }
        }

        SentimentTag tag = getFinalSentimentTag(positive, negative, neutral);
        return tag;
    }

    public SentimentTag getFinalSentimentTag(int positive, int negative, int neutral) {
        SentimentTag tag = null;
        if (positive > negative) {
            tag = SentimentTag.POSITIVE;
            if (positive >= neutral) {
                if (positive > neutral) {
                    // . positive is the greatest of all
                    tag = SentimentTag.VERY_POSITIVE;
                } else {
                    tag = SentimentTag.POSITIVE;
                }
            } else {
                // . neutral is greater than all
                tag = SentimentTag.NEUTRAL;
            }
        } else if (negative >= neutral) {
            if (negative > neutral) {
                // . negative is the greatest
                tag = SentimentTag.VERY_NEGATIVE;
            } else {
                tag = SentimentTag.NEGATIVE;
            }

        } else {
            // . neutral is the greatest
            tag = SentimentTag.NEUTRAL;
        }
        return tag;
    }

    public static SentimentTag getNormailzeTag(SentimentTag tag) {
        if (tag.equals(SentimentTag.POSITIVE) || tag.equals(SentimentTag.VERY_POSITIVE)) {
            return SentimentTag.POSITIVE;
        }
        if (tag.equals(SentimentTag.NEGATIVE) || tag.equals(SentimentTag.VERY_NEGATIVE)) {
            return SentimentTag.NEGATIVE;
        }

        return tag;
    }
}
