# Twitter Swiss Army Knife
twitter-swiss-army-knife <code>v1.0.0</code> releasing first stable version on <code>Mon Jan 11 2016</code>

<h2>How to build and Run</h2>
<h4>Build</h4>
<b>Build with maven</b>
<ul><li>run the following command in project's root directory:<br>
<code>mvn clean package</code></li></ul>

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

<h4>Help</h4>
use option <code>--help</code> with tsak command to display command options e.g.<br>
<code>tsak dumpFollowerIDs --help</code>
<h4>Note</h4> 
<ul>
<li>For detailed setup visit: http://openspinoza.org/tsak/setup</code></li>
<li>For detail description on available commands refer to: http://openspinoza.org/tsak/commands</li>
<li>You need to have twitter's access key, token key to run TSAK, for details refer to  <a href="https://github.com/project-spinoza/twitter-swiss-army-knife/wiki/Prerequisites">Setup</a> page</li>
</ul>


<h4>API Documentation</h4>
[JavaDoc](http://project-spinoza.github.io/twitter-swiss-army-knife/doc/)
<h3>Commands</h3>

<code> tsak dumpAccountSettings -o accountSettings.txt </code><br>
<code> tsak dumpFollowerIDs -uname ScreenName -limit 1 -o followerIDs.txt</code><br>
<code> tsak dumpFollowerIDs -uid 101010111 -o followerIDs.txt</code><br>
<code> tsak dumpFriendIDs -uname ScreenName -limit 1 -o friendIDs.txt</code><br>
<code> tsak dumpFriendIDs -uid 01010101 -limit 1 -o friendIDs.txt </code><br>
<code> tsak dumpUserTimeLine -uname ScreenName -limit 1 -o userTimeLine.txt</code><br>
<code> tsak dumpUserTimeLine -uid 101010111 -limit 1 -o userTimeLine.txt</code><br>
<code> tsak dumpHomeTimeLine -o homeTimeLine.txt</code><br>
<code> tsak dumpTweets -keywords "KEY words" -limit 1  -o tweets.txt</code><br>
<code> tsak dumpOwnRetweets -o ownRetweets.txt</code><br>
<code> tsak dumpStatus -sid 01010101 -o statuses.txt    </code><br>
<code> tsak dumpRetweeters -sid 01010101 -o retweeters.txt</code><br>
<code> tsak dumpMentionsTimeLine -o mentionTimeline.txt</code><br>
<code> tsak dumpUsersLookup -i ids.txt -o lookup.txt</code><br>
<code> tsak dumpBlockList -o blocklists.txt</code><br>
<code> tsak searchUsers -keywords "Key Word" -o users.txt</code><br>
<code> tsak showFriendShip -suser ScreenName -tuser ScreenName -o friendship.txt</code><br>
<code> tsak dumpFriendsList -uname screenname -limit 1 -o friendsList.txt</code><br>
<code> tsak dumpFriendsList -uid 01010101 -limit 1 -o friendsList.txt</code><br>
<code> tsak dumpFollowersList -uname screenname -limit 1 -o followersList.txt</code><br>
<code> tsak dumpFollowersList -uid 010101 -limit 1 -o followersList.txt</code><br>
<code> tsak dumpFavourites -o favourites.txt</code><br>
<code> tsak dumpSugeestedUserCats -o catagories.txt</code><br>
<code> tsak dumpUserSuggestions -slug family -o userSuggestions.txt</code><br>
<code> tsak dumpMemberSuggestions -slug family -o memberSuggestions.txt</code><br>
<code> tsak dumpUserLists -uname screenName -o userLists.txt</code><br>
<code> tsak dumpUserLists -uid 010101 -o userLists.txt</code><br>
<code> tsak dumpListStatuses -lid 010101 -o listStatuses.txt</code><br>
<code> tsak dumpSavedSearches -o searches.txt</code><br>
<code> tsak lookupFriendShip -i ids.txt -o frndships.txt</code><br>
<code> tsak dumpIncomingFriendships -limit 1 -o incomingFriendships.txt</code><br>
<code> tsak dumpOutgoingFriendships -limit 1 -o outgoingFriendships.txt</code><br>
<code> tsak dumpGeoDetails -pid 00685eca27fbd26b -o ginfo.txt</code><br>
<code> tsak dumpSimilarPlaces -lat 51.5072 -long 0.1275 -pname london -o similarPlaces.txt</code><br>
<code> tsak searchPlace -lat 51.5072 -long 0.1275 -o place.txt</code><br>
<code> tsak dumpAvailableTrends -o availableTrends.txt</code><br>
<code> tsak dumpPlaceTrends -woeid 010101 -o placeTrends.txt</code><br>
<code> tsak dumpClosestTrends -lat 0.0 -long 0.0 -o closestTrends.txt</code><br>
<code> tsak dumpMutesIDs -limit 1 -o mutes.txt</code><br>
<code> tsak dumpUserListMemberships -uname screenName -limit 1 -o listMemberships.txt</code><br>
<code> tsak dumpUserListMemberships -uid 010101 -limit 1 -o listMemberships.txt</code><br>
<code> tsak dumpUserListSubscribers -lid 02020202 -limit 1 -o listSubscribers.txt</code><br>
<code> tsak dumpUserListMembers -lid 02020202 -limit 1 -o listMembers.txt</code><br>
<code> tsak dumpUserListSubscriptions -uname screenName -limit 1 -o listSubscription.txt</code><br>
<code> tsak streamStatuses -keywords "iPhone" -show -o streamsOut.txt</code>


## License
The code is licensed under the [Apache License Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

