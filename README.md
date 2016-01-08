# Twitter Swiss Army Knife
twitter-swiss-army-knife <code>v1.0.0</code> releasing first stable version on <code>Mon Jan 11 2016</code>

<h2>How to build and Run</h2>
<h4>Build</h4>
<ul><li>Build with maven:  <code>mvn clean package</code></li></ul>

<h4>Setup</h4>
<ul>
<li>create twitter App <a href="http://openspinoza.org/tsak/setup">setup</a></li>
<li>
edit tsak.properties file and enter your keys <br>
<code>accessToken=[enter your access token here]</code><br>
<code>consumerSecret=[enter your consumer secret key here]</code><br>
<code>consumerKey=[enter your consumer key here]</code><br>
<code>accessSecret=[enter your accessSecret key here]</code><br>
</li>
<li>create <code>TSAK_CONF</code> environment variable and point it to the directory containing tsak.properties file</li>
</ul>
<h4>Run</h4>
<ul>
<li>Inside project root directory, run one of the following startup file depending on the type of plateform:<br> 
<code>bash startup.sh</code> <b>Linux</b><br>
<code>startup.bat</code> <b>Windows</b>
</li>
</ul>

Run either of the available TSAK commands.</b>

<h4>Note:</h4> 
<ul>
<li>For detailed setup visit: http://openspinoza.org/tsak/setup</code></li>
<li>For detail description on available commands refer to: http://openspinoza.org/tsak/commands</li>
<li>You need to have twitter's access key, token key to run TSAK, for details refer to  <a href=" https://github.com/project-spinoza/twitter-swiss-army-knife/wiki/Prerequisites">Setup</a> page</li>
</ul>


<h4>API Documentation</h4>
[JavaDoc](http://project-spinoza.github.io/twitter-swiss-army-knife/doc/)
<h3>Commands</h3>

<code> tsak dumpAccountSettings -o accountSettings.txt </code><br>
<code> tsak dumpFollowerIDs -uname ScreenName -limit 1 -o followers.txt</code><br>
<code> tsak dumpFollowerIDs -uid 101010111 -o followers.txt</code><br>
<code> tsak dumpFriendIDs -uname ScreenName -limit 1 -o friends.txt</code><br>
<code> tsak dumpFriendIDs -uid 01010101 -limit 1 -o friends.txt </code><br>
<code> tsak dumpUserTimeLine -uname ScreenName -limit 1 -o timeline.txt</code><br>
<code> tsak dumpUserTimeLine -uid 101010111 -limit 1 -o timeline.txt</code><br>
<code> tsak dumpHomeTimeLine -o timeline.txt</code><br>
<code> tsak dumpTweets -keywords "KEY words" -limit 1  -o tweets.txt</code><br>
<code> tsak dumpOwnRetweets -o OwnRetweets.txt</code><br>
<code> tsak dumpStatus -sid 01010101 -o Statuses.txt    </code><br>
<code> tsak dumpRetweeters -sid 01010101 -o Retweeters.txt</code><br>
<code> tsak dumpMentionsTimeLine -o MentionTimeline.txt</code><br>
<code> tsak dumpUsersLookup -i ids.txt -o lookup.txt</code><br>
<code> tsak dumpBlockList -o blocklists.txt</code><br>
<code> tsak searchUsers -keywords "Key Word" -o users.txt</code><br>
<code> tsak showFriendShip -suser ScreenName -tuser ScreenName -o friendship.txt</code><br>
<code> tsak dumpFriendsList -uname screenname -limit 1 -o friendlist.txt</code><br>
<code> tsak dumpFriendsList -uid 01010101 -limit 1 -o friendlist.txt</code><br>
<code> tsak dumpFollowersList -uname screenname -limit 1 -o followers.txt</code><br>
<code> tsak dumpFollowersList -uid 010101 -limit 1 -o followers.txt</code><br>
<code> tsak dumpFavourites -o favourites.txt</code><br>
<code> tsak dumpSugeestedUserCats -o catagories.txt</code><br>
<code> tsak dumpUserSuggestions -slug family -o suggestions.txt</code><br>
<code> tsak dumpMemberSuggestions -slug family -o members.txt</code><br>
<code> tsak dumpUserLists -uname screenName -o lists.txt</code><br>
<code> tsak dumpUserLists -uid 010101 -o lists.txt</code><br>
<code> tsak dumpListStatuses -lid 010101 -o liststatuses.txt</code><br>
<code> tsak dumpSavedSearches -o searches.txt</code><br>
<code> tsak lookupFriendShip -i ids.txt -o frndships.txt</code><br>
<code> tsak dumpIncomingFriendships -limit 1 -o frndships.txt</code><br>
<code> tsak dumpOutgoingFriendships -limit 1 -o frndships.txt</code><br>
<code> tsak dumpGeoDetails -pid 00685eca27fbd26b -o ginfo.txt</code><br>
<code> tsak dumpSimilarPlaces -lat 51.5072 -long 0.1275 -pname london -o places.txt</code><br>
<code> tsak searchPlace -lat 51.5072 -long 0.1275 -o places.txt</code><br>
<code> tsak dumpAvailableTrends -o output.txt</code><br>
<code> tsak dumpPlaceTrends -woeid 010101 -o trends.txt</code><br>
<code> tsak dumpClosestTrends -lat 0.0 -long 0.0 -o ClosestTrends.txt</code><br>
<code> tsak dumpMutesIDs -limit 1 -o mutes.txt</code><br>
<code> tsak dumpUserListMemberships -uname screenName -limit 1 -o memberships.txt</code><br>
<code> tsak dumpUserListMemberships -uid 010101 -limit 1 -o memberships.txt</code><br>
<code> tsak dumpUserListSubscribers -lid 02020202 -limit 1 -o subscribers.txt</code><br>
<code> tsak dumpUserListMembers -lid 02020202 -limit 1 -o output.txt</code><br>
<code> tsak dumpUserListSubscriptions -uname screenName -limit 1 -o subscription.txt</code><br>
<code> tsak streamStatuses -keywords "iPhone" -store true -o streamsOut.txt</code>
