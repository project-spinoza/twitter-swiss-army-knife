package org.projectspinoza.twitterswissarmyknife;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.projectspinoza.twitterswissarmyknife.util.TsakResponse;

import com.beust.jcommander.ParameterException;

import twitter4j.AccountSettings;
import twitter4j.IDs;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest()
public class TsakIntegrationTest {

    long testUserId = 101010111;
    long testSlugId = 101010111;
    long testListId = 101010111;
    String testUserName = "bit-whacker";
    String testOutput = "testOutput.txt";
    
    TwitterSwissArmyKnife tsak;

    @Mock
    Twitter twitter;
    IDs ids;
    RateLimitStatus rateLimitStatus;
    ResponseList<Status> statuses;
    List<Status> tweets;
    AccountSettings accountSettings;
    Status status;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        twitter = Mockito.mock(Twitter.class);
        ids = Mockito.mock(IDs.class);
        rateLimitStatus = Mockito.mock(RateLimitStatus.class);
        statuses = Mockito.mock(ResponseList.class);
        tweets = Mockito.mock(List.class);
        accountSettings = Mockito.mock(AccountSettings.class);
        status = Mockito.mock(Status.class);
        
        tsak = Mockito.spy(new TwitterSwissArmyKnife(twitter));
        
        Mockito.when(tsak.isAuthorized()).thenReturn(true);    
        Mockito.when(ids.getRateLimitStatus()).thenReturn(rateLimitStatus);
        Mockito.when(ids.getNextCursor()).thenReturn(0L);
        Mockito.when(statuses.getRateLimitStatus()).thenReturn(rateLimitStatus);
        Mockito.when(accountSettings.getRateLimitStatus()).thenReturn(rateLimitStatus);
        Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
    }

    @Test
    public void testCase_1() throws ParameterException, InstantiationException, IllegalAccessException, TwitterException, IOException {
        String testCommand = "tsak dumpFollowerIDs -uname " + testUserName + " -limit 1 -o " + testOutput;
        TsakResponse expected = new TsakResponse(0, new ArrayList<IDs>(Arrays.asList(ids)));

        Mockito.when(twitter.getFollowersIDs(testUserName, -1)).thenReturn(ids);

        tsak.executeCommand(testCommand.split(" "));
        TsakResponse result = tsak.getResult();

        assertEquals(expected.getRemApiLimits(), result.getRemApiLimits());
        assertEquals(expected.getResponseData(), result.getResponseData());
    }

    @Test
    public void testCase_2() throws TwitterException, ParameterException, InstantiationException, IllegalAccessException, IOException {
        String testCommand = "tsak dumpFriendIDs -uname " + testUserName + " -limit 1 -o " + testOutput;
        TsakResponse expected = new TsakResponse(0, new ArrayList<IDs>(Arrays.asList(ids)));
        
        Mockito.when(twitter.getFriendsIDs(testUserName, -1)).thenReturn(ids);

        tsak.executeCommand(testCommand.split(" "));
        TsakResponse result = tsak.getResult();

        assertEquals(expected.getRemApiLimits(), result.getRemApiLimits());
        assertEquals(expected.getResponseData(), result.getResponseData());
    }

    @Test
    public void testCase_3() throws TwitterException, ParameterException, InstantiationException, IllegalAccessException, IOException {
        String testCommand = "tsak dumpHomeTimeLine -o " + testOutput;
        TsakResponse expected = new TsakResponse(0, statuses);

        Mockito.when(twitter.getHomeTimeline()).thenReturn(statuses);

        tsak.executeCommand(testCommand.split(" "));
        TsakResponse result = tsak.getResult();

        assertEquals(expected.getRemApiLimits(), result.getRemApiLimits());
        assertEquals(expected.getResponseData(), result.getResponseData());
    }

    @Test
    public void testCase_4() throws TwitterException, ParameterException, InstantiationException, IllegalAccessException, IOException {
        String testCommand = "tsak dumpAccountSettings -o " + testOutput;
        TsakResponse expected = new TsakResponse(0, accountSettings);

        Mockito.when(twitter.getAccountSettings()).thenReturn(accountSettings);

        tsak.executeCommand(testCommand.split(" "));
        TsakResponse result = tsak.getResult();

        assertEquals(expected.getRemApiLimits(), result.getRemApiLimits());
        assertEquals(expected.getResponseData(), result.getResponseData());
    }

    @Test
    public void testCase_5() throws TwitterException, ParameterException, InstantiationException, IllegalAccessException, IOException {
        String testCommand = "tsak dumpOwnRetweets -o " + testOutput;
        TsakResponse expected = new TsakResponse(0, statuses);

        Mockito.when(twitter.getRetweetsOfMe()).thenReturn(statuses);

        tsak.executeCommand(testCommand.split(" "));
        TsakResponse result = tsak.getResult();

        assertEquals(expected.getRemApiLimits(), result.getRemApiLimits());
        assertEquals(expected.getResponseData(), result.getResponseData());
    }

    @Test
    public void testCase_6() throws ParameterException, InstantiationException, IllegalAccessException, TwitterException, IOException {
        String testCommand = "tsak math -squareOf 4 -o " + testOutput;
        
        tsak.executeCommand(testCommand.split(" "));
        TsakResponse result = tsak.getResult();

        int square = (int) result.getResponseData();
        assertEquals(square, 16);
    }

    @Test
    public void testCase_7() throws ParameterException, InstantiationException, IllegalAccessException, TwitterException, IOException {
        String testCommand = "tsak math -squareOf 3 -o " + testOutput;
        
        tsak.executeCommand(testCommand.split(" "));
        TsakResponse result = tsak.getResult();

        int square = (int) result.getResponseData();
        assertFalse(5 == square);
    }

}