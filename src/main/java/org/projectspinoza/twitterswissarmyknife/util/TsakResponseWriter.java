package org.projectspinoza.twitterswissarmyknife.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import twitter4j.AccountSettings;
import twitter4j.Category;
import twitter4j.Friendship;
import twitter4j.IDs;
import twitter4j.Location;
import twitter4j.PagableResponseList;
import twitter4j.Place;
import twitter4j.Relationship;
import twitter4j.ResponseList;
import twitter4j.SavedSearch;
import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.User;
import twitter4j.UserList;

import com.google.gson.Gson;

public class TsakResponseWriter implements DataWriter {
    private static Logger log = LogManager.getRootLogger();
    private String output_file_name;
    private PrintStream writer;

    public TsakResponseWriter() {
    }

    public TsakResponseWriter(String outputfile) {
        this.output_file_name = outputfile;
    }

    @Override
    public void write(Object twitterResponseData, String parsedCommand,
            String outputFile) {
        if (outputFile == null || outputFile.trim().isEmpty()) {
            this.output_file_name = parsedCommand + "_out.txt";
        } else {
            this.output_file_name = outputFile;
        }
        write(twitterResponseData, parsedCommand);
    }

    @SuppressWarnings("unchecked")
    public void write(Object twitterResponseData, String parsedCommand) {
        getWriterHandle();
        if (parsedCommand.equals("dumpFollowerIDs")
                || parsedCommand.equals("dumpFriendIDs")
                || parsedCommand.equals("dumpIncomingFriendships")
                || parsedCommand.equals("dumpOutgoingFriendships")
                || parsedCommand.equals("dumpMutesIDs")) {
            IDsListJsonWriter((List<IDs>) twitterResponseData);
        } else if (parsedCommand.equals("dumpRetweeters")) {
            IDsOnlyJsonWriter((IDs) twitterResponseData);
        } else if (parsedCommand.equals("dumpUserListMemberships")
                || parsedCommand.equals("dumpUserListSubscriptions")) {
            pagableResponseListJsonWriter((List<PagableResponseList<UserList>>) twitterResponseData);
        } else if (parsedCommand.equals("dumpHomeTimeLine")
                || parsedCommand.equals("dumpOwnRetweets")
                || parsedCommand.equals("dumpMentionsTimeLine")) {
            statusListJsonWriter((List<Status>) twitterResponseData);
        } else if (parsedCommand.equals("dumpUserTimeLine")
                || parsedCommand.equals("dumpListStatuses")) {
            responseListStatusJsonWriter((List<ResponseList<Status>>) twitterResponseData);
        } else if (parsedCommand.equals("dumpAccountSettings")) {
            accountSettingsJsonWriter((AccountSettings) twitterResponseData);
        } else if (parsedCommand.equals("dumpTweets")) {
            statusListsJsonWriter(((List<List<Status>>) twitterResponseData));
        } else if (parsedCommand.equals("dumpFavourites")) {
            statusListJsonWriter((List<Status>) twitterResponseData);
        } else if (parsedCommand.equals("dumpStatus")) {
            statusJsonWriter((Status) twitterResponseData);
        } else if (parsedCommand.equals("dumpBlockList")) {
            userListJsonWriter((List<User>) twitterResponseData);
        } else if (parsedCommand.equals("showFriendShip")) {
            relationshipJsonWriter((Relationship) twitterResponseData);
        } else if (parsedCommand.equals("dumpSugeestedUserCats")) {
            catListJsonWriter((ResponseList<Category>) twitterResponseData);
        } else if (parsedCommand.equals("dumpUserSuggestions")
                || parsedCommand.equals("dumpMemberSuggestions")) {
            usersResponseListJsonWriter((ResponseList<User>) twitterResponseData);
        } else if (parsedCommand.equals("dumpUserLists")) {
            responseUserListsJsonWriter((ResponseList<UserList>) twitterResponseData);
        } else if (parsedCommand.equals("dumpSavedSearches")) {
            responseSavedSearchesJsonWriter((ResponseList<SavedSearch>) twitterResponseData);
        } else if (parsedCommand.equals("dumpGeoDetails")) {
            geoPlaceJsonWriter((Place) twitterResponseData);
        } else if (parsedCommand.equals("dumpSimilarPlaces")
                || parsedCommand.equals("searchPlace")) {
            geoResponsePlacesListJsonWriter((ResponseList<Place>) twitterResponseData);
        } else if (parsedCommand.equals("dumpAvailableTrends")
                || parsedCommand.equals("dumpClosestTrends")) {
            responseLocationsListJsonWriter((ResponseList<Location>) twitterResponseData);
        } else if (parsedCommand.equals("dumpPlaceTrends")) {
            trendsJsonWriter((Trends) twitterResponseData);
        } else if (parsedCommand.equals("dumpMutesList")
                || parsedCommand.equals("dumpUserListSubscribers")
                || parsedCommand.equals("dumpUserListMembers")
                || parsedCommand.equals("dumpFriendsList")
                || parsedCommand.equals("dumpFollowersList")) {
            pagableResponseUsersJsonWriter((List<PagableResponseList<User>>) twitterResponseData);
        } else if (parsedCommand.equals("dumpUsersLookup")
                || (parsedCommand.equals("searchUsers"))) {
            responselistUserJsonWriter((List<ResponseList<User>>) twitterResponseData);
        } else if (parsedCommand.equals("lookupFriendShip")) {
            responselistFriendshipJsonWriter((List<ResponseList<Friendship>>) twitterResponseData);
        } else if (parsedCommand.equals("streamStatuses")) {

            String trend_json = new Gson().toJson((Status) twitterResponseData);
            writeLine(trend_json, true);

        } else {
            log.info("No Parser found for -> " + parsedCommand);
        }
        closeWriterHandler();
    }

    public void closeWriterHandler() {
        if (this.writer != null) {
            this.writer.close();
        }
    }

    public void writeLine(String line, boolean newline) {
        if (newline) {
            this.writer.println(line);
        } else {
            this.writer.print(line);
        }
        this.writer.flush();
    }

    public void trendsJsonWriter(Trends trends) {
        for (Trend trend : trends.getTrends()) {
            String trend_json = new Gson().toJson(trend);
            writeLine(trend_json, true);
        }
    }

    public void responseLocationsListJsonWriter(ResponseList<Location> locations) {
        for (Location location : locations) {
            String location_json = new Gson().toJson(location);
            writeLine(location_json, true);
        }
    }

    public void geoResponsePlacesListJsonWriter(ResponseList<Place> places) {
        for (Place place : places) {
            geoPlaceJsonWriter(place);
        }
    }

    public void geoPlaceJsonWriter(Place place) {
        String placeInfo_json = new Gson().toJson(place);
        writeLine(placeInfo_json, true);
    }

    public void responseSavedSearchesJsonWriter(
            ResponseList<SavedSearch> savedsearches) {
        for (SavedSearch savedSearch : savedsearches) {
            String savedsearches_json = new Gson().toJson(savedSearch);
            writeLine(savedsearches_json, true);
        }
    }

    public void responseUserListsJsonWriter(ResponseList<UserList> userlists) {
        for (UserList list : userlists) {
            String ulist_json = new Gson().toJson(list);
            writeLine(ulist_json, true);
        }
    }

    public void relationshipJsonWriter(Relationship relation) {
        String rel_json = new Gson().toJson(relation);
        writeLine(rel_json, true);
    }

    public void userListJsonWriter(List<User> uList) {
        for (User user : uList) {
            String user_json = new Gson().toJson(user);
            writeLine(user_json, true);
        }
    }

    public void statusListJsonWriter(List<Status> sList) {
        for (Status status : sList) {
            String status_json = new Gson().toJson(status);
            writeLine(status_json, true);
        }
    }

    public void statusListsJsonWriter(List<List<Status>> sLists) {
        for (List<Status> sList : sLists) {
            for (Status status : sList) {
                String status_json = new Gson().toJson(status);
                writeLine(status_json, true);
            }
        }
    }

    public void responseListStatusJsonWriter(List<ResponseList<Status>> sLists) {
        for (ResponseList<Status> sList : sLists) {
            for (Status status : sList) {
                String status_json = new Gson().toJson(status);
                writeLine(status_json, true);
            }
        }
    }

    public void responselistUserJsonWriter(List<ResponseList<User>> sLists) {
        for (ResponseList<User> users : sLists) {
            for (User user : users) {
                String status_json = new Gson().toJson(user);
                writeLine(status_json, true);
            }
        }
    }

    public void responselistFriendshipJsonWriter(
            List<ResponseList<Friendship>> Friendships) {
        for (ResponseList<Friendship> Friendship : Friendships) {
            for (Friendship friendship : Friendship) {
                String status_json = new Gson().toJson(friendship);
                writeLine(status_json, true);
            }
        }
    }

    public void statusJsonWriter(Status status) {
        String status_json = new Gson().toJson(status);
        writeLine(status_json, true);
    }

    public void accountSettingsJsonWriter(AccountSettings account_settings) {
        String settings_json = new Gson().toJson(account_settings);
        writeLine(settings_json, true);
    }

    public void catListJsonWriter(ResponseList<Category> categories) {
        for (Category category : categories) {
            String cat_json = new Gson().toJson(category);
            writeLine(cat_json, true);
        }
    }

    public void usersResponseListJsonWriter(ResponseList<User> users) {
        for (User user : users) {
            String user_json = new Gson().toJson(user);
            writeLine(user_json, true);
        }
    }

    public void pagableResponseListJsonWriter(
            List<PagableResponseList<UserList>> mResponse) {
        for (PagableResponseList<UserList> pResponse : mResponse) {
            for (UserList ulist : pResponse) {
                String ulist_json = new Gson().toJson(ulist);
                writeLine(ulist_json, true);
            }
        }
    }

    public void pagableResponseUsersJsonWriter(
            List<PagableResponseList<User>> usersList) {
        for (PagableResponseList<User> users : usersList) {
            for (User user : users) {
                String user_json = new Gson().toJson(user);
                writeLine(user_json, true);
            }
        }
    }

    public void IDsListJsonWriter(List<IDs> Ids_list) {
        for (IDs ids : Ids_list) {
            IDsOnlyJsonWriter(ids);
        }
    }

    public void IDsOnlyJsonWriter(IDs ids) {
        JSONObject ids_json = new JSONObject();
        ids_json.accumulate("ids", ids.getIDs());
        writeLine(ids_json.toString(), true);
    }

    public void getWriterHandle() {
        try {
            this.writer = new PrintStream(new FileOutputStream(new File(
                    this.output_file_name), true));
        } catch (FileNotFoundException e) {
            log.error("Error writing to file.. ->" + e.getMessage());
        }
    }
}