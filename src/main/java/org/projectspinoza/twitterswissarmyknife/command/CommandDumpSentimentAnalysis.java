package org.projectspinoza.twitterswissarmyknife.command;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jline.internal.Log;

import org.projectspinoza.twitterswissarmyknife.util.SentimentTag;
import org.projectspinoza.twitterswissarmyknife.util.SentimentTagger;
import org.projectspinoza.twitterswissarmyknife.util.TsakResponse;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.detectlanguage.DetectLanguage;
import com.detectlanguage.Result;
import com.detectlanguage.errors.APIError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



@Parameters(commandNames = "dumpSentimentTweets", commandDescription = "filer tweets based on sentiment tweets")
public class CommandDumpSentimentAnalysis extends BaseCommand {

	@Parameter(names = "-keywords", description = "keywords to be search for tweets", required = true)
	private String keywords;
	@Parameter(names = "-sentiment", description = "sentiment to be search for tweets", required = true)
	private String sentiment;
	@Parameter(names = "-limit", description = "Authenticated user api calls limit")
	private int limit = 1;

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public TsakResponse execute(Twitter twitter) throws TwitterException {
		List<List<Status>> tweetsCollection = new ArrayList<List<Status>>();
		SentimentTagger tagger = new SentimentTagger();
		
		int userLimit = this.limit;
		int remApiLimits = 1;
		Query query = new Query(this.keywords);
		QueryResult queryResult;
		do {
			List<Status> tweets = new ArrayList<Status>();
			queryResult = twitter.search(query);
			tweets = getSentimentTweets(this.sentiment, queryResult, tagger);
			tweetsCollection.add(tweets);
			remApiLimits = queryResult.getRateLimitStatus().getRemaining();
			query = queryResult.nextQuery();
		} while (query != null && remApiLimits != 0 && --userLimit > 0);
		TsakResponse tsakResponse = new TsakResponse(remApiLimits,
				tweetsCollection);
		tsakResponse.setCommandDetails(this.toString());
		return tsakResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(TsakResponse tsakResponse, BufferedWriter writer)
			throws IOException {
		List<List<Status>> tweetsCollection = (List<List<Status>>) tsakResponse
				.getResponseData();
		for (List<Status> statuses : tweetsCollection) {
			for (Status status : statuses) {
				String statusJson = new Gson().toJson(status);
				writer.append(statusJson);
				writer.newLine();
			}
		}
	}

	@Override
	public String toString() {
		return "dumpSentimentTweets [keywords=" + keywords + "sentiment = "
				+ sentiment + " limit=" + limit + "]";
	}

	public List<Status> getSentimentTweets(String sentiment, QueryResult queryResult, SentimentTagger tagger) {
		List<Status> tweets = new ArrayList<Status>();
		
		JsonParser parser = new JsonParser();
		DetectLanguage.apiKey = "94dee435278818692aa51b64d2764df6";
		SentimentTag inputTag = SentimentTag.getTag(sentiment);
		if(inputTag == null){
			Log.error("Invalid SentimentTag: {,  ", sentiment, "  } ","expected {"," positive, negative or neutral","}");
			return null;
		}
		for (Status status : queryResult.getTweets()) {
			String tweet = gettweetfromJsonObject(status, parser);
			List<Result> results = null;
			try {
				results = DetectLanguage.detect(tweet);
				Result result = results.get(0);
				if (result.language.equalsIgnoreCase("en")) {
					tweet = getTweetText(tweet);
					SentimentTag tag = tagger.getSentimentTag(tweet);
					tag = SentimentTagger.getNormailzeTag(tag);
					if (tag.equals(inputTag)) {
						tweets.add(status);
					}
				}
			} catch (APIError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return tweets;
	}

	public String gettweetfromJsonObject(Status status, JsonParser parser) {
		String tweetObject = new Gson().toJson(status);
		JsonObject tweetobject = (JsonObject) (parser.parse(tweetObject));
		String tweet = tweetobject.get("text").toString();
		return tweet;
	}
	
	public String getTweetText(String tweet){
		tweet = tweet.replaceAll("https?://\\S+\\s?", "").trim(); 
		tweet = tweet.replaceAll("[^a-zA-Z0-9_-]", " ").trim();
		return tweet;
	}
}
