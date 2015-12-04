package org.projectspinoza.twitterswissarmyknife;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import twitter4j.GeoLocation;
import twitter4j.GeoQuery;
import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.UserList;

@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest({ CLIDriver.class, Paging.class, Query.class,
		QueryResult.class, GeoLocation.class, GeoQuery.class })
public class TestCLIDriver {
	CLIDriver cliDriver;
	long uId = 101010111;
	long sId = 101010111;
	String sName = "bit-whacker";
	String outputFileName = "testOutput.txt";

	@Mock
	Twitter twitter;
	IDs ids;
	ResponseList<Status> statuses;
	Paging page;
	PagableResponseList<User> userPagableResponseList;
	RateLimitStatus rateLimitStatus;
	ResponseList<UserList> userlist;
	QueryResult queryResult;
	Query query;
	List<Status> tweets;
	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		cliDriver = new CLIDriver();
		twitter = Mockito.mock(Twitter.class);
		ids = Mockito.mock(IDs.class);
		rateLimitStatus = Mockito.mock(RateLimitStatus.class);
		statuses = Mockito.mock(ResponseList.class);
		page = Mockito.mock(Paging.class);
		userPagableResponseList = Mockito.mock(PagableResponseList.class);
		userlist = Mockito.mock(ResponseList.class);
		queryResult = Mockito.mock(QueryResult.class);
		query = Mockito.mock(Query.class);
		tweets = Mockito.mock(List.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void dumpFollowersIDs() throws TwitterException {
		CommandDumpFollowerIDs subCommand = new CommandDumpFollowerIDs();
		subCommand.screenName = sName;
		subCommand.outputFile = outputFileName;
		subCommand.limit = 1;
		List<IDs> expected = new ArrayList<IDs>(Arrays.asList(ids));

		Mockito.when(twitter.getFollowersIDs(sName, -1)).thenReturn(ids);
		Mockito.when(ids.getNextCursor()).thenReturn(0L);
		Mockito.when(ids.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		List<IDs> result = (List<IDs>) cliDriver.getFollowersIDs(twitter, subCommand);
		
		assertEquals(expected, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void dumpUserTimeLine() throws Exception {
		CommandDumpUserTimeLine subCommand = new CommandDumpUserTimeLine();
		subCommand.userid = uId;
		subCommand.outputFile = outputFileName;
		subCommand.limit = 1;
		List<ResponseList<Status>> expected = new ArrayList<ResponseList<Status>>();
		expected.add(statuses);
		
		PowerMockito.whenNew(Paging.class).withArguments(1, 200).thenReturn(page);
		Mockito.when(twitter.getUserTimeline(uId, page)).thenReturn(statuses);
		Mockito.when(statuses.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		List<Status> result = (List<Status>) cliDriver.getUserTimeLine(twitter,	subCommand);
		
		assertEquals(expected, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDumpFriendList() throws TwitterException, IOException {
		CommandDumpFriendsList subCommand = new CommandDumpFriendsList();
		subCommand.userid = uId;
		subCommand.outputFile = outputFileName;
		subCommand.limit = 1;
		List<PagableResponseList<User>> expected = new ArrayList<PagableResponseList<User>>();
		expected.add(userPagableResponseList);
		
		Mockito.when(twitter.getFriendsList(uId, -1)).thenReturn(userPagableResponseList);
		Mockito.when(userPagableResponseList.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		List<PagableResponseList<User>> result = (List<PagableResponseList<User>>) cliDriver
				.getFriendsList(twitter, subCommand);
		
		assertEquals(expected, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testdumpUserLists() throws Exception {
		CommandDumpUserLists subCommand = new CommandDumpUserLists();
		subCommand.userid = uId;
		subCommand.outputFile = outputFileName;
		ResponseList<UserList> expected = userlist;
		
		Mockito.when(twitter.getUserLists(uId)).thenReturn(userlist);
		ResponseList<UserList> result = (ResponseList<UserList>) cliDriver
				.getUserLists(twitter, subCommand);

		assertEquals(expected, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDumpTweets() throws Exception {
		String keyWord = "keyword";
		CommandDumpTweets subCommand = new CommandDumpTweets();
		subCommand.keywords = keyWord;
		subCommand.outputFile = outputFileName;
		subCommand.limit = 1;
		List<List<Status>> expected = new ArrayList<List<Status>>();
		expected.add(tweets);

		PowerMockito.whenNew(Query.class).withArguments(keyWord).thenReturn(query);
		Mockito.when(twitter.search(query)).thenReturn(queryResult);
		Mockito.when(queryResult.getTweets()).thenReturn(tweets);
		Mockito.when(queryResult.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);

		List<List<Status>> result = (List<List<Status>>) cliDriver.getTweets(twitter, subCommand);

		assertEquals(expected, result);
	}

}
