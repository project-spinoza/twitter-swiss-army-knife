package org.projectspinoza.twitterswissarmyknife;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.projectspinoza.twitterswissarmyknife.CLIDriver;
import org.projectspinoza.twitterswissarmyknife.TsakCommand;

import twitter4j.Category;
import twitter4j.GeoLocation;
import twitter4j.GeoQuery;
import twitter4j.IDs;
import twitter4j.Location;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.Place;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Relationship;
import twitter4j.ResponseList;
import twitter4j.SavedSearch;
import twitter4j.Status;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.UserList;

import com.beust.jcommander.JCommander;

@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest({ CLIDriver.class, Paging.class, Query.class, QueryResult.class, GeoLocation.class, GeoQuery.class })
public class TestDriver {
	CLIDriver commandLineDriver;
	JCommander rootCommander = new JCommander();
	TsakCommand tsakCommand = new TsakCommand();
	String uName = "bit-whacker";
	long uId = 101010111;
	long sId = 101010111;
	String sName = "bit-whacker";
	int woeid =101010111;
	@Mock
	Twitter twitter;
	JCommander tsakCommander;
	Map<String, JCommander> getCommands;
	JCommander jcObj;
	List<Object> listObjects;
	IDs ids;
	RateLimitStatus rateLimitStatus;
	Paging page;
	ResponseList<Status> statuses;
	Query query;
	QueryResult queryResult;
	GeoLocation geoLocation;
	ResponseList<UserList> userlist;
	PagableResponseList<UserList> userlistPagableResponseList;
	ResponseList<SavedSearch> savedSearch;
	PagableResponseList<User> userPagableResponseList;
	Status status;
	ResponseList<User> user;
	Relationship relationship;
	ResponseList<Category> category;
	Place place;
	ResponseList<Place> placeResponslist;
	ResponseList<Location> locationResponslist;
	GeoQuery geoQuery;
	Trends trends;
	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		twitter = Mockito.mock(Twitter.class);
		ids = Mockito.mock(IDs.class);
		page = Mockito.mock(Paging.class);
		statuses = Mockito.mock(ResponseList.class);
		query = Mockito.mock(Query.class);
		queryResult = Mockito.mock(QueryResult.class);
		status = Mockito.mock(Status.class);
		
		userPagableResponseList = Mockito.mock(PagableResponseList.class);
		userlistPagableResponseList = Mockito.mock(PagableResponseList.class);
		user = Mockito.mock(ResponseList.class);
		relationship = Mockito.mock(Relationship.class);
		category = Mockito.mock(ResponseList.class);
		userlist = Mockito.mock(ResponseList.class);
		savedSearch = Mockito.mock(ResponseList.class);
		place = Mockito.mock(Place.class);
		geoLocation = Mockito.mock(GeoLocation.class);
		rootCommander.addCommand("tsak", tsakCommand);
		JCommander subCommander = rootCommander.getCommands().get("tsak");
		commandLineDriver = new CLIDriver();
		commandLineDriver.activateSubCommands(subCommander);
		rateLimitStatus = Mockito.mock(RateLimitStatus.class);
		geoQuery = Mockito.mock(GeoQuery.class);
		locationResponslist = Mockito.mock(ResponseList.class);
		trends = Mockito.mock(Trends.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDumpFollowerIDsByScreenName() throws TwitterException, IOException {
		List<IDs> expected = new ArrayList<IDs>(Arrays.asList(ids));
		String args[] = { "tsak", "dumpFollowerIDs", "-uname", uName, "-o",
				"followers.txt" };
		rootCommander.parse(args);
		Mockito.when(twitter.getFollowersIDs(uName, -1)).thenReturn(ids);
		Mockito.when(ids.getNextCursor()).thenReturn(0L);
		Mockito.when(ids.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		List<IDs> result = (List<IDs>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDumpFollowerIDsById() throws TwitterException, IOException {
		List<IDs> expected = new ArrayList<IDs>(Arrays.asList(ids));
		String args[] = { "tsak", "dumpFollowerIDs", "-uid",
				String.valueOf(uId), "-o", "followers.txt" };
		rootCommander.parse(args);
		Mockito.when(twitter.getFollowersIDs(uId, -1)).thenReturn(ids);
		Mockito.when(ids.getNextCursor()).thenReturn(0L);
		Mockito.when(ids.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		List<IDs> result = (List<IDs>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDumpFriendIDsByScreenName() throws TwitterException, IOException {
		List<IDs> expected = new ArrayList<IDs>(Arrays.asList(ids));
		String args[] = { "tsak", "dumpFriendIDs", "-uname", uName, "-o",
				"friends.txt" };
		rootCommander.parse(args);
		Mockito.when(twitter.getFriendsIDs(uName, -1)).thenReturn(ids);
		Mockito.when(ids.getNextCursor()).thenReturn(0L);
		Mockito.when(ids.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		List<IDs> result = (List<IDs>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDumpFriendIDsById() throws TwitterException, IOException {
		List<IDs> expected = new ArrayList<IDs>(Arrays.asList(ids));
		String args[] = { "tsak", "dumpFriendIDs", "-uid", String.valueOf(uId),
				"-o", "friends.txt" };
		rootCommander.parse(args);
		Mockito.when(twitter.getFriendsIDs(uId, -1)).thenReturn(ids);
		Mockito.when(ids.getNextCursor()).thenReturn(0L);
		Mockito.when(ids.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		List<IDs> result = (List<IDs>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void TestGetUserTimeLineByScreenName() throws Exception {
		List<ResponseList<Status>> expected = new ArrayList<ResponseList<Status>>();
		expected.add(statuses);
		String args[] = { "tsak", "dumpUserTimeLine", "-uname", uName, "-o",
				"timeline.txt" };
		rootCommander.parse(args);
		PowerMockito.whenNew(Paging.class).withArguments(1, 200)
				.thenReturn(page);
		Mockito.when(twitter.getUserTimeline(uName, page)).thenReturn(statuses);
		Mockito.when(statuses.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		List<ResponseList<Status>> result = (List<ResponseList<Status>>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void TestGetUserTimeLineById() throws Exception {
		List<ResponseList<Status>> expected = new ArrayList<ResponseList<Status>>();
		expected.add(statuses);
		String args[] = { "tsak", "dumpUserTimeLine", "-uid",
				String.valueOf(uId), "-o", "timeline.txt" };
		rootCommander.parse(args);
		PowerMockito.whenNew(Paging.class).withArguments(1, 200)
				.thenReturn(page);
		Mockito.when(twitter.getUserTimeline(uId, page)).thenReturn(statuses);
		Mockito.when(statuses.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		List<Status> result = (List<Status>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	
//	@Test
//	public void TestGetTweets() throws Exception {
//		String keyWord = "keyword";
//		QueryResult expected = queryResult;
//		String args[] = { "tsak", "dumpTweets","-limit","10", "-keywords", keyWord, "-o",
//				"tweets.txt" };
//
//		rootCommander.parse(args);
//		PowerMockito.whenNew(Query.class).withArguments(keyWord)
//				.thenReturn(query);
//		Mockito.when(twitter.search(query)).thenReturn(queryResult);
//		QueryResult result = (QueryResult) commandLineDriver
//				.executeCommand(twitter);
//		assertEquals(expected, result);
//	}

	@SuppressWarnings("unchecked")
	@Test
	public void TestGetOwnRetweets() throws Exception {
		ResponseList<Status> expected = statuses;
		String args[] = { "tsak", "dumpOwnRetweets", "-o", "OwnRetweets.txt" };
		rootCommander.parse(args);
		Mockito.when(twitter.getRetweetsOfMe()).thenReturn(statuses);
		ResponseList<Status> result = (ResponseList<Status>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}

	
	@Test
	public void TestGetStatus() throws Exception {
		Status expected = status;

		String args[] = { "tsak", "dumpStatus", "-sid", String.valueOf(sId),
				"-o", "Statuses.txt" };
		rootCommander.parse(args);
		Mockito.when(twitter.showStatus(sId)).thenReturn(status);
		Status result = (Status) commandLineDriver.executeCommand(twitter);
		assertEquals(expected, result);
	}

	@Test
	public void TestGetRetweeters() throws Exception {
		IDs expected = ids;
		String args[] = { "tsak", "dumpRetweeters", "-sid",
				String.valueOf(sId), "-o", "Retweeters.txt" };
		rootCommander.parse(args);
		Mockito.when(twitter.getRetweeterIds(sId, -1)).thenReturn(ids);
		IDs result = (IDs) commandLineDriver.executeCommand(twitter);
		assertEquals(expected, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void TestGetMentionsTimeLine() throws Exception {
		ResponseList<Status> expected = statuses;
		String args[] = { "tsak", "dumpMentionsTimeLine", "-o",
				"MentionTimeline.txt" };
		rootCommander.parse(args);
		Mockito.when(twitter.getMentionsTimeline()).thenReturn(
				statuses);
		ResponseList<Status> result = (ResponseList<Status>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void TestGetBlockList() throws Exception {
		PagableResponseList<User> expected = userPagableResponseList;
		String args[] = { "tsak", "dumpBlockList", "-o", "blocklists.txt" };
		rootCommander.parse(args);
		Mockito.when(twitter.getBlocksList()).thenReturn(
				userPagableResponseList);
		ResponseList<Status> result = (ResponseList<Status>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void TestSearchUsers() throws Exception {
		String keyword = "keyword";
		List<ResponseList<User>> expected = new ArrayList<ResponseList<User>>();
		expected.add(user);
		String args[] = { "tsak", "searchUsers", "-keywords", keyword, "-o",
				"users.txt" };
		rootCommander.parse(args);
		Mockito.when(twitter.searchUsers(keyword, 1)).thenReturn(user);
		Mockito.when(user.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		List<ResponseList<User>> result = (List<ResponseList<User>>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}

	@Test
	public void TestShowFriendShip() throws Exception {

		Relationship expected = relationship;
		String source = "amir";
		String target = "sadiq";
		String args[] = { "tsak", "showFriendShip", "-suser", source, "-tuser",
				target, "-o", "friendship.txt" };
		rootCommander.parse(args);
		Mockito.when(twitter.showFriendship(source, target)).thenReturn(
				relationship);
		Relationship result = (Relationship) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetFriendsListByScreenName() throws Exception {

		List<PagableResponseList<User>> expected = new ArrayList<PagableResponseList<User>>();
		expected.add(userPagableResponseList);
		String args[] = { "tsak", "dumpFriendsList", "-uname", uName,"-o", "friendlist.txt" };
		rootCommander.parse(args);
		Mockito.when( twitter
				.getFriendsList(uName, -1)).thenReturn(
				userPagableResponseList);
		Mockito.when(userPagableResponseList.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		List<PagableResponseList<User>> result = (List<PagableResponseList<User>>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetFriendsListByID() throws Exception {

		List<PagableResponseList<User>> expected = new ArrayList<PagableResponseList<User>>();
		expected.add(userPagableResponseList);
		String args[] = { "tsak", "dumpFriendsList", "-uid", String.valueOf(uId),"-o", "friendlist.txt" };
		rootCommander.parse(args);
		Mockito.when( twitter
				.getFriendsList(uId, -1)).thenReturn(
				userPagableResponseList);
		Mockito.when(userPagableResponseList.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		List<PagableResponseList<User>> result = (List<PagableResponseList<User>>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetFollowersListByScreenName() throws Exception {

		List<PagableResponseList<User>> expected = new ArrayList<PagableResponseList<User>>();
		expected.add(userPagableResponseList);
		String args[] = { "tsak", "dumpFollowersList", "-uname", uName,"-o", "followerslist.txt" };
		rootCommander.parse(args);
		Mockito.when( twitter
				.getFollowersList(uName, -1)).thenReturn(
				userPagableResponseList);
		Mockito.when(userPagableResponseList.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		List<PagableResponseList<User>> result = (List<PagableResponseList<User>>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetFollowersListByID() throws Exception {
		List<PagableResponseList<User>> expected = new ArrayList<PagableResponseList<User>>();
		expected.add(userPagableResponseList);
		String args[] = { "tsak", "dumpFollowersList", "-uid", String.valueOf(uId),"-o", "followerslist.txt" };
		rootCommander.parse(args);
		Mockito.when( twitter
				.getFollowersList(uId, -1)).thenReturn(
				userPagableResponseList);
		Mockito.when(userPagableResponseList.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);

		List<PagableResponseList<User>> result = (List<PagableResponseList<User>>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetgetFavourites() throws Exception {

		ResponseList<Status> expected= statuses;
		String args[] = { "tsak", "dumpFavourites", "-o",  "favourites.txt" };
		rootCommander.parse(args);
		Mockito.when( twitter.getFavorites() ).thenReturn(
				statuses);
		ResponseList<Status>  result = (ResponseList<Status> )commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetSugeestedUserCats() throws Exception {

		ResponseList<Category> expected= category;
		String args[] = { "tsak", "dumpSugeestedUserCats", "-o",  "catagories.txt" };
		rootCommander.parse(args);
		Mockito.when( twitter.getSuggestedUserCategories() ).thenReturn(
				category);
		ResponseList<Category>  result = (ResponseList<Category>)commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetUserSuggestions() throws Exception {
       String family = "family";
		ResponseList<User> expected= user;
		String args[] = { "tsak", "dumpUserSuggestions", "-slug",family, "-o",  "suggestions.txt" };
		rootCommander.parse(args);
		Mockito.when( twitter.getUserSuggestions(family) ).thenReturn(
				user);
		ResponseList<User>  result = (ResponseList<User>)commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetMemberSuggestions() throws Exception {
       String family = "family";
		ResponseList<User> expected= user;
		String args[] = { "tsak", "dumpMemberSuggestions", "-slug",family, "-o",  "members.txt" };
		rootCommander.parse(args);
		Mockito.when( twitter.getMemberSuggestions(family) ).thenReturn(
				user);
		ResponseList<User>  result = (ResponseList<User>)commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetUserLists() throws Exception {
       
		ResponseList<UserList> expected= userlist;
		String args[] = { "tsak", "dumpUserLists", "-uid",String.valueOf(uId), "-o",  "lists.txt" };
		rootCommander.parse(args);
		Mockito.when( twitter
				.getUserLists(uId)).thenReturn(
				userlist);
		ResponseList<UserList>  result = (ResponseList<UserList>)commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetListStatuses() throws Exception {
		List<ResponseList<Status>> expected = new ArrayList<ResponseList<Status>>();
		expected.add(statuses);
		String args[] = { "tsak", "dumpListStatuses", "-lid", String.valueOf(uId), "-o",
				"liststatuses.txt" };
		rootCommander.parse(args);
		PowerMockito.whenNew(Paging.class).withArguments(1, 50)
				.thenReturn(page);
		Mockito.when(twitter.getUserListStatuses(uId,page)).thenReturn(statuses);
		Mockito.when(page.getPage()).thenReturn(181);
		List<ResponseList<Status>> result = (List<ResponseList<Status>>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetSavedSearches() throws Exception {     
		ResponseList<SavedSearch> expected= savedSearch;	
		String args[] = { "tsak", "dumpSavedSearches", "-o",  "searches.txt" };
		rootCommander.parse(args);
		Mockito.when( twitter.getSavedSearches()).thenReturn(
				savedSearch);
		ResponseList<SavedSearch>  result = (ResponseList<SavedSearch>)commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetIncomingFriendships() throws Exception {

		List<IDs> expected = new ArrayList<IDs>();
		expected.add(ids);
		String args[] = { "tsak", "dumpIncomingFriendships","-o", "frndships.txt" };
		rootCommander.parse(args);
		Mockito.when( twitter.getIncomingFriendships(-1)).thenReturn(
				ids);
		Mockito.when(ids.getNextCursor()).thenReturn(0L);
		Mockito.when(ids.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		List<IDs> result = (List<IDs>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetOutgoingFriendships() throws Exception {

		List<IDs> expected = new ArrayList<IDs>();
		expected.add(ids);
		String args[] = { "tsak", "dumpOutgoingFriendships","-o", "frndships.txt" };
		rootCommander.parse(args);
		Mockito.when( twitter.getOutgoingFriendships(-1)).thenReturn(
				ids);
		Mockito.when(ids.getNextCursor()).thenReturn(0L);
		Mockito.when(ids.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		List<IDs> result = (List<IDs>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@Test
	public void TestGetGeoDetails() throws Exception {
       String pId = "00685eca27fbd26b";
		Place expected= place;	
		String args[] = { "tsak", "dumpGeoDetails", "-pid",pId, "-o",  "ginfo.txt" };
		rootCommander.parse(args);
		Mockito.when( twitter.getGeoDetails(pId)).thenReturn(
				place);
		Place  result = (Place)commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetSimilarPlaces() throws Exception {
         double lat = 51.5072;
         double longitude = 0.1275;
         String placeName = "london";
         ResponseList<Place> expected= placeResponslist;
	       
		String args[] = { "tsak", "dumpSimilarPlaces", "-lat",String.valueOf(lat),"-long",String.valueOf(longitude),"-pname",placeName,"-o",  "places.txt" };

		rootCommander.parse(args);
		PowerMockito.whenNew(GeoLocation.class).withArguments(lat,longitude)
		.thenReturn(geoLocation);
		Mockito.when(twitter.getSimilarPlaces(geoLocation, placeName, null, null)).thenReturn(
				placeResponslist);
	


		ResponseList<Place>  result = (ResponseList<Place>)commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestsearchPlace() throws Exception {
         double lat = 51.5072;
         double longitude = 0.1275;
         ResponseList<Place> expected= placeResponslist;
	       
		String args[] = { "tsak", "searchPlace", "-lat",String.valueOf(lat),"-long",String.valueOf(longitude),"-o",  "places.txt" };

		rootCommander.parse(args);
		PowerMockito.whenNew(GeoLocation.class).withArguments(lat,longitude)
		.thenReturn(geoLocation);
		PowerMockito.whenNew(GeoQuery.class).withArguments(geoLocation)
		.thenReturn(geoQuery);
		Mockito.when(twitter.searchPlaces(geoQuery)).thenReturn(
				placeResponslist);
	


		ResponseList<Place>  result = (ResponseList<Place>)commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetAvailableTrends() throws Exception {
       ResponseList<Location> expected= locationResponslist;
	
		String args[] = { "tsak", "dumpAvailableTrends", "-o",  "output.txt" };

		rootCommander.parse(args);
		Mockito.when( twitter.getAvailableTrends()).thenReturn(
				locationResponslist);
	


		ResponseList<Location>  result = (ResponseList<Location>)commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@Test
	public void TestGetPlaceTrends() throws Exception {
       Trends expected= trends;
	
		String args[] = { "tsak", "dumpPlaceTrends","-woeid",String.valueOf(woeid), "-o",  "trends.txt" };

		rootCommander.parse(args);
		Mockito.when( twitter.getPlaceTrends(woeid)).thenReturn(
				trends);
	


		Trends  result = (Trends)commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetClosestTrends() throws Exception {
         double lat = 0.0;
         double longitude = 0.0;
         ResponseList<Location> expected= locationResponslist;
	       
		String args[] = { "tsak", "dumpClosestTrends", "-lat",String.valueOf(lat),"-long",String.valueOf(longitude),"-o",  "ClosestTrends.txt" };

		rootCommander.parse(args);
		PowerMockito.whenNew(GeoLocation.class).withArguments(lat,longitude)
		.thenReturn(geoLocation);
		
		Mockito.when(twitter.getClosestTrends(geoLocation)).thenReturn(
				locationResponslist);
	


		ResponseList<Location>  result = (ResponseList<Location>)commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetMutesIDs() throws Exception {

		List<IDs> expected = new ArrayList<IDs>();
		expected.add(ids);
		String args[] = { "tsak", "dumpMutesIDs","-o", "mutes.txt" };

		rootCommander.parse(args);
		Mockito.when( twitter.getMutesIDs(-1)).thenReturn(
				ids);
		Mockito.when(ids.getNextCursor()).thenReturn(0L);
		Mockito.when(ids.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		

		List<IDs> result = (List<IDs>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetMutesList() throws Exception {

		List<PagableResponseList<User>> expected = new ArrayList<PagableResponseList<User>>();
		expected.add(userPagableResponseList);
		String args[] = { "tsak", "dumpMutesList","-o", "mutes.txt" };

		rootCommander.parse(args);
		Mockito.when( twitter.getMutesList(-1)).thenReturn(
				userPagableResponseList);
		Mockito.when(userPagableResponseList.getNextCursor()).thenReturn(0L);
		Mockito.when(userPagableResponseList.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		

		List<PagableResponseList<User>> result = (List<PagableResponseList<User>>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void TestUserListMembershipsByScreenName() throws Exception {

		List<PagableResponseList<UserList>> expected = new ArrayList<PagableResponseList<UserList>>();
		expected.add(userlistPagableResponseList);
		String args[] = { "tsak", "dumpUserListMemberships","-uname",sName,"-o", "memberships.txt" };

		rootCommander.parse(args);
		Mockito.when( twitter.getUserListMemberships(sName, -1)).thenReturn(
				userlistPagableResponseList);
		Mockito.when(userlistPagableResponseList.getNextCursor()).thenReturn(0L);
		Mockito.when(userlistPagableResponseList.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		

		List<PagableResponseList<UserList>> result = (List<PagableResponseList<UserList>>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestUserListMembershipsById() throws Exception {

		List<PagableResponseList<UserList>> expected = new ArrayList<PagableResponseList<UserList>>();
		expected.add(userlistPagableResponseList);
		String args[] = { "tsak", "dumpUserListMemberships","-uid ",String.valueOf(uId),"-o", "memberships.txt" };

		rootCommander.parse(args);
		Mockito.when( twitter.getUserListMemberships(uId, -1)).thenReturn(
				userlistPagableResponseList);
		Mockito.when(userlistPagableResponseList.getNextCursor()).thenReturn(0L);
		Mockito.when(userlistPagableResponseList.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		

		List<PagableResponseList<UserList>> result = (List<PagableResponseList<UserList>>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetUserListSubscribers() throws Exception {
        long lid = 02020202;
		List<PagableResponseList<User>> expected = new ArrayList<PagableResponseList<User>>();
		expected.add(userPagableResponseList);
		String args[] = { "tsak", "dumpUserListSubscribers","-lid ",String.valueOf(lid),"-o", "subscribers.txt" };

		rootCommander.parse(args);
		Mockito.when( twitter.getUserListSubscribers(lid, -1)).thenReturn(
				userPagableResponseList);
		Mockito.when(userPagableResponseList.getNextCursor()).thenReturn(0L);
		Mockito.when(userPagableResponseList.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		

		List<PagableResponseList<User>> result = (List<PagableResponseList<User>>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetUserListMembers() throws Exception {
        long lid = 02020202;
		List<PagableResponseList<User>> expected = new ArrayList<PagableResponseList<User>>();
		expected.add(userPagableResponseList);
		String args[] = { "tsak", "dumpUserListMembers","-lid ",String.valueOf(lid),"-o", "output.txt" };

		rootCommander.parse(args);
		Mockito.when( twitter.getUserListMembers(lid, -1L)).thenReturn(
				userPagableResponseList);
		Mockito.when(userPagableResponseList.getNextCursor()).thenReturn(0L);
		Mockito.when(userPagableResponseList.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		

		List<PagableResponseList<User>> result = (List<PagableResponseList<User>>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TestGetUserListSubscriptions() throws Exception {
       
		List<PagableResponseList<UserList>> expected = new ArrayList<PagableResponseList<UserList>>();
		expected.add(userlistPagableResponseList);
		String args[] = { "tsak", "dumpUserListSubscriptions","-uname ",sName,"-o", "subscription.txt" };

		rootCommander.parse(args);
		Mockito.when( twitter
				.getUserListSubscriptions(sName, -1L)).thenReturn(
						userlistPagableResponseList);
		Mockito.when(userlistPagableResponseList.getNextCursor()).thenReturn(0L);
		Mockito.when(userlistPagableResponseList.getRateLimitStatus()).thenReturn(rateLimitStatus);
		Mockito.when(rateLimitStatus.getRemaining()).thenReturn(0);
		

		List<PagableResponseList<UserList>> result = (List<PagableResponseList<UserList>>) commandLineDriver
				.executeCommand(twitter);
		assertEquals(expected, result);
	}
}
