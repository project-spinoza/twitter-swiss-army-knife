package org.projectspinoza.twitterswissarmyknife;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import twitter4j.Friendship;
import twitter4j.GeoLocation;
import twitter4j.GeoQuery;
import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.UserList;

public class CLIDriver {
    private static Logger log = LogManager.getRootLogger();

    protected String outputFile;

    private Object result = null;
    int remApiLimits = 0;

    public Object executeCommand(Twitter twitter, String parsedCommand, Object subCommand) throws TwitterException,
            IOException {
        
        log.info("Executing Command [" + parsedCommand + "]");

        if (parsedCommand.equals("dumpFollowerIDs")) {
            result = getFollowersIDs(twitter, subCommand);
        } else if (parsedCommand.equals("dumpFriendIDs")) {
            result = getFriendsIDs(twitter, subCommand);
        } else if (parsedCommand.equals("dumpHomeTimeLine")) {
            result = getHomeTimeLine(twitter, subCommand);
        } else if (parsedCommand.equals("dumpAccountSettings")) {
            result = getAccountSettings(twitter, subCommand);
        } else if (parsedCommand.equals("dumpUserTimeLine")) {
            result = getUserTimeLine(twitter, subCommand);
        } else if (parsedCommand.equals("dumpTweets")) {
            result = getTweets(twitter, subCommand);
        } else if (parsedCommand.equals("dumpOwnRetweets")) {
            result = getOwnRetweets(twitter, subCommand);
        } else if (parsedCommand.equals("dumpStatus")) {
            result = getStatus(twitter, subCommand);
        } else if (parsedCommand.equals("dumpRetweeters")) {
            result = getRetweeters(twitter, subCommand);
        } else if (parsedCommand.equals("dumpMentionsTimeLine")) {
            result = getMentionsTimeLine(twitter, subCommand);
        } else if (parsedCommand.equals("dumpUsersLookup")) {
            result = getUsersLookup(twitter, subCommand);
        } else if (parsedCommand.equals("dumpBlockList")) {
            result = getBlockList(twitter, subCommand);
        } else if (parsedCommand.equals("searchUsers")) {
            result = searchUsers(twitter, subCommand);
        } else if (parsedCommand.equals("showFriendShip")) {
            result = showFriendShip(twitter, subCommand);
        } else if (parsedCommand.equals("dumpFriendsList")) {
            result = getFriendsList(twitter, subCommand);
        } else if (parsedCommand.equals("dumpFollowersList")) {
            result = getFollowersList(twitter, subCommand);
        } else if (parsedCommand.equals("dumpFavourites")) {
            result = getFavourites(twitter, subCommand);
        } else if (parsedCommand.equals("dumpSugeestedUserCats")) {
            result = getSugeestedUserCats(twitter, subCommand);
        } else if (parsedCommand.equals("dumpUserSuggestions")) {
            result = getUserSuggestions(twitter, subCommand);
        } else if (parsedCommand.equals("dumpMemberSuggestions")) {
            result = getMemberSuggestions(twitter, subCommand);
        } else if (parsedCommand.equals("dumpUserLists")) {
            result = getUserLists(twitter, subCommand);
        } else if (parsedCommand.equals("dumpListStatuses")) {
            result = getListStatuses(twitter, subCommand);
        } else if (parsedCommand.equals("dumpSavedSearches")) {
            result = getSavedSearches(twitter, subCommand);
        } else if (parsedCommand.equals("lookupFriendShip")) {
            result = lookupFriendShip(twitter, subCommand);
        } else if (parsedCommand.equals("dumpIncomingFriendships")) {
            result = getIncomingFriendships(twitter, subCommand);
        } else if (parsedCommand.equals("dumpOutgoingFriendships")) {
            result = getOutgoingFriendships(twitter, subCommand);
        } else if (parsedCommand.equals("dumpGeoDetails")) {
            result = getGeoDetails(twitter, subCommand);
        } else if (parsedCommand.equals("dumpSimilarPlaces")) {
            result = getSimilarPlaces(twitter, subCommand);
        } else if (parsedCommand.equals("searchPlace")) {
            result = searchPlace(twitter, subCommand);
        } else if (parsedCommand.equals("dumpAvailableTrends")) {
            result = getAvailableTrends(twitter, subCommand);
        } else if (parsedCommand.equals("dumpPlaceTrends")) {
            result = getPlaceTrends(twitter, subCommand);
        } else if (parsedCommand.equals("dumpClosestTrends")) {
            result = getClosestTrends(twitter, subCommand);
        } else if (parsedCommand.equals("dumpMutesIDs")) {
            result = getMutesIDs(twitter, subCommand);
        } else if (parsedCommand.equals("dumpMutesList")) {
            result = getMutesList(twitter, subCommand);
        } else if (parsedCommand.equals("dumpUserListMemberships")) {
            result = getUserListMemberships(twitter, subCommand);
        } else if (parsedCommand.equals("dumpUserListSubscribers")) {
            result = getUserListSubscribers(twitter, subCommand);
        } else if (parsedCommand.equals("dumpUserListMembers")) {
            result = getUserListMembers(twitter, subCommand);
        } else if (parsedCommand.equals("dumpUserListSubscriptions")) {
            result = getUserListSubscriptions(twitter, subCommand);
        }

        log.info("GENERATED ["+outputFile+"]");
        showRateLimitStatus();
        return result;
    }

    public Object getAccountSettings(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpAccountSettings dumpAccountSettings = (CommandDumpAccountSettings) subCommand;
        outputFile = dumpAccountSettings.outputFile;
        return twitter.getAccountSettings();
    }

    public Object getFollowersList(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpFollowersList dumpFollowersList = (CommandDumpFollowersList) subCommand;
        outputFile = dumpFollowersList.outputFile;
        int userLimit = dumpFollowersList.limit;
        long cursor = -1;
        List<PagableResponseList<User>> followersListCollection = new ArrayList<PagableResponseList<User>>();
        do {
            PagableResponseList<User> pagableUser = dumpFollowersList.screenName == null ? twitter
                    .getFollowersList(dumpFollowersList.userid, cursor)
                    : twitter.getFollowersList(dumpFollowersList.screenName,
                            cursor);
            followersListCollection.add(pagableUser);
            cursor = pagableUser.getNextCursor();
            remApiLimits = pagableUser.getRateLimitStatus().getRemaining();
        } while ((cursor != 0) && (remApiLimits != 0) && (--userLimit > 0));
        return followersListCollection;
    }

    public Object getFriendsList(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpFriendsList dumpFriendList = (CommandDumpFriendsList) subCommand;
        outputFile = dumpFriendList.outputFile;
        int userLimit = dumpFriendList.limit;
        long cursor = -1;
        List<PagableResponseList<User>> friendListCollection = new ArrayList<PagableResponseList<User>>();
        do {
            PagableResponseList<User> pagableUser = dumpFriendList.screenName == null ? twitter
                    .getFriendsList(dumpFriendList.userid, cursor) : twitter
                    .getFriendsList(dumpFriendList.screenName, cursor);
            friendListCollection.add(pagableUser);
            cursor = pagableUser.getNextCursor();
            remApiLimits = pagableUser.getRateLimitStatus().getRemaining();
        } while ((cursor != 0) && (remApiLimits != 0) && (--userLimit > 0));
        return friendListCollection;
    }

    public Object getFollowersIDs(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpFollowerIDs dumpFollowerIDs = (CommandDumpFollowerIDs) subCommand;
        List<IDs> followerIDsCollection = new ArrayList<IDs>();
        outputFile = dumpFollowerIDs.outputFile;
        String uName = dumpFollowerIDs.screenName;
        long uId = dumpFollowerIDs.userid;
        int userLimit = dumpFollowerIDs.limit;
        long cursor = -1;
        do {
            IDs ids = uName != null ? twitter.getFollowersIDs(uName, cursor)
                    : twitter.getFollowersIDs(uId, cursor);
            followerIDsCollection.add(ids);
            cursor = ids.getNextCursor();
            remApiLimits = ids.getRateLimitStatus().getRemaining();
        } while ((cursor != 0) && (remApiLimits != 0) && (--userLimit > 0));
        return followerIDsCollection;
    }

    public Object getFriendsIDs(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpFriendIDs dumpFriendIDs = (CommandDumpFriendIDs) subCommand;
        List<IDs> friendIDsCollection = new ArrayList<IDs>();
        outputFile = dumpFriendIDs.outputFile;
        String uName = dumpFriendIDs.screenName;
        long uId = dumpFriendIDs.userid;
        int userLimit = dumpFriendIDs.limit;
        long cursor = -1;
        do {
            IDs ids = uName != null ? twitter.getFriendsIDs(uName, cursor)
                    : twitter.getFriendsIDs(uId, cursor);
            friendIDsCollection.add(ids);
            cursor = ids.getNextCursor();
            remApiLimits = ids.getRateLimitStatus().getRemaining();
        } while ((cursor != 0) && (remApiLimits != 0) && (--userLimit > 0));
        return friendIDsCollection;
    }

    public Object getUserTimeLine(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpUserTimeLine dumpUserTimeLine = (CommandDumpUserTimeLine) subCommand;
        outputFile = dumpUserTimeLine.outputFile;
        List<ResponseList<Status>> userTimelineCollection = new ArrayList<ResponseList<Status>>();
        int pagecounter = 1;
        int userLimit = dumpUserTimeLine.limit;
        Paging page = new Paging(pagecounter, 200);
        do {
            ResponseList<Status> statuses = dumpUserTimeLine.screenName == null ? twitter
                    .getUserTimeline(dumpUserTimeLine.userid, page) : twitter
                    .getUserTimeline(dumpUserTimeLine.screenName, page);
            userTimelineCollection.add(statuses);
            pagecounter++;
            page.setPage(pagecounter);
            remApiLimits = statuses.getRateLimitStatus().getRemaining();
        } while ((remApiLimits != 0) && --userLimit > 0);
        return userTimelineCollection;
    }

    public Object getHomeTimeLine(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpHomeTimeLine dumpHomeTimeLine = (CommandDumpHomeTimeLine) subCommand;
        outputFile = dumpHomeTimeLine.outputFile;
        return twitter.getHomeTimeline();
    }

    public Object getTweets(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpTweets dumpTweets = (CommandDumpTweets) subCommand;
        List<List<Status>> tweetsCollection = new ArrayList<List<Status>>();
        int userLimit = dumpTweets.limit;
        Query query = new Query(dumpTweets.keywords);
        QueryResult queryResult;
        do {
            queryResult = twitter.search(query);
            tweetsCollection.add(queryResult.getTweets());
            remApiLimits = queryResult.getRateLimitStatus().getRemaining();
            query = queryResult.nextQuery();
        } while (query != null && remApiLimits != 0 && --userLimit > 0);
        outputFile = dumpTweets.outputFile;
        return tweetsCollection;
    }

    public Object getOwnRetweets(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpOwnRetweets dumpOwnRetweets = (CommandDumpOwnRetweets) subCommand;
        outputFile = dumpOwnRetweets.outputFile;
        return twitter.getRetweetsOfMe();
    }

    public Object getStatus(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpStatus dumpStatus = (CommandDumpStatus) subCommand;
        outputFile = dumpStatus.outputFile;
        return twitter.showStatus(dumpStatus.status_id);
    }

    public Object getRetweeters(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpRetweeters dumpRetweeters = (CommandDumpRetweeters) subCommand;
        outputFile = dumpRetweeters.outputFile;
        return twitter.getRetweeterIds(dumpRetweeters.status_id, -1);
    }

    public Object getMentionsTimeLine(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpMentionsTimeLine dumpMentionsTimeLine = (CommandDumpMentionsTimeLine) subCommand;
        outputFile = dumpMentionsTimeLine.outputFile;
        return twitter.getMentionsTimeline();
    }

    public Object getUsersLookup(Twitter twitter, Object subCommand)
            throws IOException, TwitterException {
        CommandDumpUsersLookup dumpUsersLookup = (CommandDumpUsersLookup) subCommand;
        outputFile = dumpUsersLookup.outputFile;
        BufferedReader bReader = new BufferedReader(new FileReader(new File(
                dumpUsersLookup.input_file)));
        String line;
        List<String> screenNames = new ArrayList<String>();
        List<Long> ids = new ArrayList<Long>();
        ids = new ArrayList<Long>();
        while ((line = bReader.readLine()) != null) {
            if (!line.isEmpty()) {
                if (isLong(line)) {
                    ids.add(Long.parseLong(line));
                } else {
                    screenNames.add(line);
                }
            }
        }
        bReader.close();
        long[] Ids = new long[ids.size()];
        String[] names = new String[screenNames.size()];
        List<ResponseList<User>> friendListCollection = new ArrayList<ResponseList<User>>();
        if (!screenNames.isEmpty()) {
            for (int i = 0; i < screenNames.size(); i++) {
                names[i] = screenNames.get(i);
            }
            friendListCollection.add(twitter.lookupUsers(names));
        }
        if (!ids.isEmpty()) {
            for (int i = 0; i < ids.size(); i++) {
                Ids[i] = ids.get(i);
            }
            friendListCollection.add(twitter.lookupUsers(Ids));
        }
        return friendListCollection;
    }

    public Object getBlockList(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpBlockList dumpBlockList = (CommandDumpBlockList) subCommand;
        outputFile = dumpBlockList.outputFile;
        return twitter.getBlocksList();
    }

    public Object searchUsers(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandSearchUsers searchUsers = (CommandSearchUsers) subCommand;
        outputFile = searchUsers.outputFile;
        int page = 1;
        List<ResponseList<User>> usersCollection = new ArrayList<ResponseList<User>>();
        do {
            ResponseList<User> user = twitter.searchUsers(searchUsers.keywords,
                    page++);
            usersCollection.add(user);
            remApiLimits = user.getRateLimitStatus().getRemaining();
        } while ((remApiLimits != 0) && (page < 50));
        return usersCollection;
    }

    public Object showFriendShip(Twitter twitter, Object subCommand)
            throws NumberFormatException, TwitterException {
        CommandShowFriendShip showFriendShip = (CommandShowFriendShip) subCommand;
        outputFile = showFriendShip.outputFile;
        if (isLong(showFriendShip.source) && isLong(showFriendShip.target)) {
            return twitter.showFriendship(
                    Long.parseLong(showFriendShip.source),
                    Long.parseLong(showFriendShip.target));
        } else {
            return twitter.showFriendship(showFriendShip.source,
                    showFriendShip.target);
        }
    }

    public Object getFavourites(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpFavourites dumpFavourites = (CommandDumpFavourites) subCommand;
        outputFile = dumpFavourites.outputFile;
        return twitter.getFavorites();
    }

    public Object getSugeestedUserCats(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpSugeestedUserCats dumpSugeestedUserCats = (CommandDumpSugeestedUserCats) subCommand;
        outputFile = dumpSugeestedUserCats.outputFile;
        return twitter.getSuggestedUserCategories();
    }

    public Object getUserSuggestions(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpUserSuggestions dumpUserSuggestions = (CommandDumpUserSuggestions) subCommand;
        outputFile = dumpUserSuggestions.outputFile;
        return twitter.getUserSuggestions(dumpUserSuggestions.slug);
    }

    public Object getMemberSuggestions(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpMemberSuggestions dumpMemberSuggestions = (CommandDumpMemberSuggestions) subCommand;
        outputFile = dumpMemberSuggestions.outputFile;
        return twitter.getMemberSuggestions(dumpMemberSuggestions.slug);
    }

    public Object getUserLists(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpUserLists dumpUserLists = (CommandDumpUserLists) subCommand;
        outputFile = dumpUserLists.outputFile;
        return dumpUserLists.screenName == null ? twitter
                .getUserLists(dumpUserLists.userid) : twitter
                .getUserLists(dumpUserLists.screenName);
    }

    public Object getListStatuses(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpListStatuses dumpListStatuses = (CommandDumpListStatuses) subCommand;
        outputFile = dumpListStatuses.outputFile;
        List<ResponseList<Status>> listStatusesCollection = new ArrayList<ResponseList<Status>>();
        Paging page = new Paging(1, 50);
        do {
            listStatusesCollection.add(twitter.getUserListStatuses(
                    dumpListStatuses.list_id, page));
            page.setPage(page.getPage() + 1);
        } while (page.getPage() < 180);
        return listStatusesCollection;
    }

    public Object getSavedSearches(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpSavedSearches dumpSavedSearches = (CommandDumpSavedSearches) subCommand;
        outputFile = dumpSavedSearches.outputFile;
        return twitter.getSavedSearches();
    }

    public Object lookupFriendShip(Twitter twitter, Object subCommand)
            throws IOException, TwitterException {
        CommandLookupFriendShip lookupFriendShip = (CommandLookupFriendShip) subCommand;
        outputFile = lookupFriendShip.outputFile;
        List<ResponseList<Friendship>> friendShipCollection = new ArrayList<ResponseList<Friendship>>();
        BufferedReader bReader = new BufferedReader(new FileReader(new File(
                lookupFriendShip.input_file)));
        String line;
        ArrayList<String> screenNames = new ArrayList<String>();
        ArrayList<Long> ids = new ArrayList<Long>();
        while ((line = bReader.readLine()) != null) {
            if (!line.isEmpty()) {
                if (isLong(line)) {
                    ids.add(Long.parseLong(line));
                } else {
                    screenNames.add(line);
                }
            }
        }
        bReader.close();
        long[] Ids = new long[ids.size()];
        String[] names = new String[screenNames.size()];

        if (!screenNames.isEmpty()) {
            for (int i = 0; i < screenNames.size(); i++) {
                names[i] = screenNames.get(i);
            }
            friendShipCollection.add(twitter.lookupFriendships(names));
        }
        if (!ids.isEmpty()) {
            for (int i = 0; i < ids.size(); i++) {
                Ids[i] = ids.get(i);
            }
            friendShipCollection.add(twitter.lookupFriendships(Ids));
        }
        return friendShipCollection;
    }

    public Object getIncomingFriendships(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpIncomingFriendships dumpIncomingFriendships = (CommandDumpIncomingFriendships) subCommand;
        outputFile = dumpIncomingFriendships.outputFile;
        int userLimit = dumpIncomingFriendships.limit;
        long cursor = -1;
        List<IDs> IncomingFriendshipsCollection = new ArrayList<IDs>();
        do {
            IDs ids = twitter.getIncomingFriendships(cursor);
            IncomingFriendshipsCollection.add(ids);
            remApiLimits = ids.getRateLimitStatus().getRemaining();
            cursor = ids.getNextCursor();
        } while ((cursor != 0) && (remApiLimits < 0) && (--userLimit > 0));
        return IncomingFriendshipsCollection;
    }

    public Object getOutgoingFriendships(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpOutgoingFriendships dumpOutgoingFriendships = (CommandDumpOutgoingFriendships) subCommand;
        outputFile = dumpOutgoingFriendships.outputFile;
        int userLimit = dumpOutgoingFriendships.limit;
        long cursor = -1;
        List<IDs> OutgoingFriendshipsCollection = new ArrayList<IDs>();
        do {
            IDs ids = twitter.getOutgoingFriendships(cursor);
            OutgoingFriendshipsCollection.add(ids);
            remApiLimits = ids.getRateLimitStatus().getRemaining();
            cursor = ids.getNextCursor();
        } while ((cursor != 0) && (remApiLimits < 0) && (--userLimit > 0));
        return OutgoingFriendshipsCollection;
    }

    public Object getGeoDetails(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpGeoDetails dumpGeoDetails = (CommandDumpGeoDetails) subCommand;
        outputFile = dumpGeoDetails.outputFile;
        return twitter.getGeoDetails(dumpGeoDetails.place_id);
    }

    public Object getSimilarPlaces(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpSimilarPlaces dumpSimilarPlaces = (CommandDumpSimilarPlaces) subCommand;
        outputFile = dumpSimilarPlaces.outputFile;
        return twitter.getSimilarPlaces(new GeoLocation(
                dumpSimilarPlaces.latitude, dumpSimilarPlaces.longitude),
                dumpSimilarPlaces.place_name, null, null);
    }

    public Object searchPlace(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandSearchPlace searchPlace = (CommandSearchPlace) subCommand;
        outputFile = searchPlace.outputFile;
        return twitter.searchPlaces(new GeoQuery(new GeoLocation(
                searchPlace.latitude, searchPlace.longitude)));
    }

    public Object getAvailableTrends(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpAvailableTrends dumpAvailableTrends = (CommandDumpAvailableTrends) subCommand;
        outputFile = dumpAvailableTrends.outputFile;
        return twitter.getAvailableTrends();
    }

    public Object getPlaceTrends(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpPlaceTrends dumpPlaceTrends = (CommandDumpPlaceTrends) subCommand;
        outputFile = dumpPlaceTrends.outputFile;
        return twitter.getPlaceTrends(dumpPlaceTrends.woeid);
    }

    public Object getClosestTrends(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpClosestTrends dumpClosestTrends = (CommandDumpClosestTrends) subCommand;
        outputFile = dumpClosestTrends.outputFile;
        return twitter.getClosestTrends(new GeoLocation(
                dumpClosestTrends.latitude, dumpClosestTrends.longitude));
    }

    public Object getMutesIDs(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpMutesIDs dumpMutesIDs = (CommandDumpMutesIDs) subCommand;
        outputFile = dumpMutesIDs.outputFile;
        long cursor = -1;
        int userLimit = dumpMutesIDs.limit;
        List<IDs> MutesIDsCollection = new ArrayList<IDs>();
        cursor = -1;
        do {
            IDs ids = twitter.getMutesIDs(cursor);
            MutesIDsCollection.add(ids);
            remApiLimits = ids.getRateLimitStatus().getRemaining();
            cursor = ids.getNextCursor();
        } while ((cursor != 0) && (remApiLimits < 0) && (--userLimit > 0));
        return MutesIDsCollection;
    }

    public Object getMutesList(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpMutesList dumpMutesList = (CommandDumpMutesList) subCommand;
        outputFile = dumpMutesList.outputFile;
        int userLimit = dumpMutesList.limit;
        long cursor = -1;
        List<PagableResponseList<User>> MutesListCollection = new ArrayList<PagableResponseList<User>>();
        do {
            PagableResponseList<User> user = twitter.getMutesList(cursor);
            MutesListCollection.add(user);
            cursor = user.getNextCursor();
            remApiLimits = user.getRateLimitStatus().getRemaining();
        } while ((cursor != 0) && (remApiLimits != 0) && (--userLimit > 0));
        return MutesListCollection;
    }

    public Object getUserListMemberships(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpUserListMemberships dumpUserListMemberships = (CommandDumpUserListMemberships) subCommand;
        outputFile = dumpUserListMemberships.outputFile;
        int userLimit = dumpUserListMemberships.limit;
        long cursor = -1;
        List<PagableResponseList<UserList>> ListMembershipsCollection = new ArrayList<PagableResponseList<UserList>>();
        do {
            PagableResponseList<UserList> userList = dumpUserListMemberships.screenName == null ? twitter
                    .getUserListMemberships(dumpUserListMemberships.userid,
                            cursor) : twitter.getUserListMemberships(
                    dumpUserListMemberships.screenName, cursor);
            ListMembershipsCollection.add(userList);
            cursor = userList.getNextCursor();
            remApiLimits = userList.getRateLimitStatus().getRemaining();
        } while ((cursor != 0) && (remApiLimits != 0) && (--userLimit > 0));
        return ListMembershipsCollection;
    }

    public Object getUserListSubscribers(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpUserListSubscribers dumpUserListSubscribers = (CommandDumpUserListSubscribers) subCommand;
        outputFile = dumpUserListSubscribers.outputFile;
        int userLimit = dumpUserListSubscribers.limit;
        long cursor = -1;
        List<PagableResponseList<User>> ListSubscribersCollection = new ArrayList<PagableResponseList<User>>();
        do {
            PagableResponseList<User> user = twitter.getUserListSubscribers(
                    dumpUserListSubscribers.list_id, cursor);
            ListSubscribersCollection.add(user);
            cursor = user.getNextCursor();
            remApiLimits = user.getRateLimitStatus().getRemaining();
        } while ((cursor != 0) && (remApiLimits != 0) && (--userLimit > 0));
        return ListSubscribersCollection;
    }

    public Object getUserListMembers(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpUserListMembers dumpUserListMembers = (CommandDumpUserListMembers) subCommand;
        outputFile = dumpUserListMembers.outputFile;
        int userLimit = dumpUserListMembers.limit;
        long cursor = -1;
        List<PagableResponseList<User>> ListMembersCollection = new ArrayList<PagableResponseList<User>>();
        do {
            PagableResponseList<User> user = twitter.getUserListMembers(
                    dumpUserListMembers.list_id, cursor);
            ListMembersCollection.add(user);
            cursor = user.getNextCursor();
            remApiLimits = user.getRateLimitStatus().getRemaining();
        } while ((cursor != 0) && (remApiLimits != 0) && (--userLimit > 0));
        return ListMembersCollection;
    }

    public Object getUserListSubscriptions(Twitter twitter, Object subCommand)
            throws TwitterException {
        CommandDumpUserListSubscriptions dumpUserListSubscriptions = (CommandDumpUserListSubscriptions) subCommand;
        outputFile = dumpUserListSubscriptions.outputFile;
        int userLimit = dumpUserListSubscriptions.limit;
        long cursor = -1;
        List<PagableResponseList<UserList>> ListSubscriptionsCollection = new ArrayList<PagableResponseList<UserList>>();
        do {
            PagableResponseList<UserList> userList = twitter
                    .getUserListSubscriptions(
                            dumpUserListSubscriptions.screenName, cursor);
            ListSubscriptionsCollection.add(userList);
            cursor = userList.getNextCursor();
            remApiLimits = userList.getRateLimitStatus().getRemaining();
        } while ((cursor != 0) && (remApiLimits != 0) && (--userLimit > 0));
        return ListSubscriptionsCollection;
    }

    private boolean isLong(String input) {
        try {
            Long.parseLong(input);
        } catch (ClassCastException | NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void showRateLimitStatus() {
        log.info("---------------------------------------------------");
        log.info("REMAINING TWITTER API CALLS: [" + remApiLimits + "]");
        log.info("---------------------------------------------------");
        remApiLimits = 0;
    }
}
