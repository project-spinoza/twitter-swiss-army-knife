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

import twitter4j.AccountSettings;
import twitter4j.Category;
import twitter4j.GeoLocation;
import twitter4j.GeoQuery;
import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.SavedSearch;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.UserList;

@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest({ CLIDriver.class, Paging.class, Query.class, QueryResult.class, GeoLocation.class, GeoQuery.class })
public class TestCLIDriver {
    CLIDriver cliDriver;
    long uId = 101010111;
    long sId = 101010111;
    long lId = 101010111;
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
    AccountSettings accountSettings;
    Status status;
    ResponseList<Category> categoryResponseList;
    ResponseList<User> user;
    ResponseList<SavedSearch> savedSearch;

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
        accountSettings = Mockito.mock(AccountSettings.class);
        status = Mockito.mock(Status.class);
        categoryResponseList = Mockito.mock(ResponseList.class);
        user = Mockito.mock(ResponseList.class);
        savedSearch = Mockito.mock(ResponseList.class);
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
        List<IDs> result = (List<IDs>) cliDriver.getFollowersIDs(twitter,
                subCommand);

        assertEquals(expected, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void dumpFriendIDs() throws TwitterException {
        CommandDumpFriendIDs subCommand = new CommandDumpFriendIDs();
        subCommand.screenName = sName;
        subCommand.outputFile = outputFileName;
        subCommand.limit = 1;
        List<IDs> expected = new ArrayList<IDs>(Arrays.asList(ids));

        Mockito.when(twitter.getFriendsIDs(sName, -1)).thenReturn(ids);
        Mockito.when(ids.getNextCursor()).thenReturn(0L);
        Mockito.when(ids.getRateLimitStatus()).thenReturn(rateLimitStatus);
        Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
        List<IDs> result = (List<IDs>) cliDriver.getFriendsIDs(twitter,
                subCommand);

        assertEquals(expected, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDumpHomeTimeLine() throws Exception {
        CommandDumpHomeTimeLine subCommand = new CommandDumpHomeTimeLine();
        subCommand.outputFile = outputFileName;
        ResponseList<Status> expected = statuses;

        Mockito.when(twitter.getHomeTimeline()).thenReturn(statuses);
        ResponseList<Status> result = (ResponseList<Status>) cliDriver.getHomeTimeLine(twitter, subCommand);

        assertEquals(expected, result);
    }

    @Test
    public void testDumpAccountSettings() throws Exception {
        CommandDumpAccountSettings subCommand = new CommandDumpAccountSettings();
        subCommand.outputFile = outputFileName;
        AccountSettings expected = accountSettings;

        Mockito.when(twitter.getAccountSettings()).thenReturn(accountSettings);
        AccountSettings result = (AccountSettings) cliDriver.getAccountSettings(twitter, subCommand);

        assertEquals(expected, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDumpOwnRetweets() throws Exception {
        CommandDumpOwnRetweets subCommand = new CommandDumpOwnRetweets();
        subCommand.outputFile = outputFileName;
        ResponseList<Status> expected = statuses;

        Mockito.when(twitter.getRetweetsOfMe()).thenReturn(statuses);
        ResponseList<Status> result = (ResponseList<Status>) cliDriver.getOwnRetweets(twitter, subCommand);

        assertEquals(expected, result);
    }

    @Test
    public void testDumpStatus() throws Exception {
        CommandDumpStatus subCommand = new CommandDumpStatus();
        subCommand.outputFile = outputFileName;
        subCommand.status_id = sId;
        Status expected = status;

        Mockito.when(twitter.showStatus(sId)).thenReturn(status);
        Status result = (Status) cliDriver.getStatus(twitter, subCommand);

        assertEquals(expected, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDumpMentionsTimeLine() throws Exception {
        CommandDumpMentionsTimeLine subCommand = new CommandDumpMentionsTimeLine();
        subCommand.outputFile = outputFileName;
        ResponseList<Status> expected = statuses;

        Mockito.when(twitter.getMentionsTimeline()).thenReturn(statuses);
        ResponseList<Status> result = (ResponseList<Status>) cliDriver.getMentionsTimeLine(twitter, subCommand);

        assertEquals(expected, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDumpBlockList() throws Exception {
        CommandDumpBlockList subCommand = new CommandDumpBlockList();
        subCommand.outputFile = outputFileName;
        PagableResponseList<User> expected = userPagableResponseList;

        Mockito.when(twitter.getBlocksList()).thenReturn(userPagableResponseList);
        PagableResponseList<User> result = (PagableResponseList<User>) cliDriver.getBlockList(twitter, subCommand);

        assertEquals(expected, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDumpUserTimeLine() throws Exception {
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
        List<Status> result = (List<Status>) cliDriver.getUserTimeLine(twitter, subCommand);

        assertEquals(expected, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void searchUsers() throws Exception {
        String keywords = "keyword";
        CommandSearchUsers subCommand = new CommandSearchUsers();
        subCommand.outputFile = outputFileName;
        subCommand.keywords = keywords;

        List<ResponseList<User>> expected = new ArrayList<ResponseList<User>>();
        expected.add(userPagableResponseList);

        Mockito.when(twitter.searchUsers(keywords, 1)).thenReturn(userPagableResponseList);
        Mockito.when(userPagableResponseList.getRateLimitStatus()).thenReturn(rateLimitStatus);
        Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
        List<ResponseList<User>> result = (List<ResponseList<User>>) cliDriver.searchUsers(twitter, subCommand);

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
        List<PagableResponseList<User>> result = (List<PagableResponseList<User>>) cliDriver.getFriendsList(twitter, subCommand);

        assertEquals(expected, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDumpListStatuses() throws Exception {
        CommandDumpListStatuses subCommand = new CommandDumpListStatuses();
        subCommand.list_id = lId;
        subCommand.outputFile = outputFileName;
        List<ResponseList<Status>> expected = new ArrayList<ResponseList<Status>>();
        expected.add(statuses);

        PowerMockito.whenNew(Paging.class).withArguments(1, 50).thenReturn(page);
        Mockito.when(twitter.getUserListStatuses(lId, page)).thenReturn(statuses);
        Mockito.when(page.getPage()).thenReturn(181);

        List<Status> result = (List<Status>) cliDriver.getListStatuses(twitter, subCommand);

        assertEquals(expected, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDumpFollowersList() throws TwitterException, IOException {
        CommandDumpFollowersList subCommand = new CommandDumpFollowersList();
        subCommand.userid = uId;
        subCommand.outputFile = outputFileName;
        subCommand.limit = 1;
        List<PagableResponseList<User>> expected = new ArrayList<PagableResponseList<User>>();
        expected.add(userPagableResponseList);

        Mockito.when(twitter.getFollowersList(uId, -1)).thenReturn(userPagableResponseList);
        Mockito.when(userPagableResponseList.getRateLimitStatus()).thenReturn(rateLimitStatus);
        Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
        List<PagableResponseList<User>> result = (List<PagableResponseList<User>>) cliDriver.getFollowersList(twitter, subCommand);

        assertEquals(expected, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDumpFavourites() throws Exception {
        CommandDumpFavourites subCommand = new CommandDumpFavourites();
        subCommand.outputFile = outputFileName;
        ResponseList<Status> expected = statuses;

        Mockito.when(twitter.getFavorites()).thenReturn(statuses);
        ResponseList<Status> result = (ResponseList<Status>) cliDriver.getFavourites(twitter, subCommand);

        assertEquals(expected, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDumpSavedSearches() throws Exception {
        CommandDumpSavedSearches subCommand = new CommandDumpSavedSearches();
        subCommand.outputFile = outputFileName;
        ResponseList<SavedSearch> expected = savedSearch;

        Mockito.when(twitter.getSavedSearches()).thenReturn(savedSearch);
        ResponseList<SavedSearch> result = (ResponseList<SavedSearch>) cliDriver.getSavedSearches(twitter, subCommand);

        assertEquals(expected, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDumpUserSuggestions() throws Exception {
        CommandDumpUserSuggestions subCommand = new CommandDumpUserSuggestions();
        String slug = "family";
        subCommand.outputFile = outputFileName;
        subCommand.slug = slug;
        ResponseList<User> expected = user;

        Mockito.when(twitter.getUserSuggestions(slug)).thenReturn(user);
        ResponseList<User> result = (ResponseList<User>) cliDriver.getUserSuggestions(twitter, subCommand);

        assertEquals(expected, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDumpMemberSuggestions() throws Exception {
        CommandDumpMemberSuggestions subCommand = new CommandDumpMemberSuggestions();
        String slug = "family";
        subCommand.outputFile = outputFileName;
        subCommand.slug = slug;
        ResponseList<User> expected = user;

        Mockito.when(twitter.getMemberSuggestions(slug)).thenReturn(user);
        ResponseList<User> result = (ResponseList<User>) cliDriver.getMemberSuggestions(twitter, subCommand);

        assertEquals(expected, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDumpSugeestedUserCats() throws Exception {
        CommandDumpSugeestedUserCats subCommand = new CommandDumpSugeestedUserCats();
        subCommand.outputFile = outputFileName;
        ResponseList<Category> expected = categoryResponseList;

        Mockito.when(twitter.getSuggestedUserCategories()).thenReturn(categoryResponseList);
        ResponseList<Category> result = (ResponseList<Category>) cliDriver.getSugeestedUserCats(twitter, subCommand);

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
        ResponseList<UserList> result = (ResponseList<UserList>) cliDriver.getUserLists(twitter, subCommand);

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
