# Twitter Swiss Army Knife
  twitter-swiss-army-knife

<h3>Setup:</h3>
<ul>
<li>Build with maven:  <code>mvn clean package</code></li>
<li>Create <code>TSAK_CONF</code> env variable pointing to a readable directory.</li>
<li>Copy <code>tsak.properties</code> file to the directory pointed by <code>TSAK_CONF</code>.</li>
<li>Edit <code>tsak.properties</code> file and fill up with required Twitter keys.</li>
<li>Inside project root directory, run one of the following startup file depending on the type of plateform:<br> 
<code>startup.sh</code> <b>Linux</b><br>
<code>startup.bat</code> <b>Windows</b>
</li>
<li>For detailed setup visit: http://openspinoza.org/tsak/setup</li>
</ul>

Run either of the available TSAK commands.

<h5>Note:</h5> 
<ul>
<li>New option <code>-limit</code> added which specifies the number of api calls to be consumed for the commands which can consume  more than one api call, default <code>-limit 1</code></li>
<li>For detail description on available commands refer to: http://openspinoza.org/tsak/commands</li>
</ul>
<h3>Commands</h3>

<code> tsak dumpAccountSettings -limit 1 -o accountSettings.txt </code><br>
<code> tsak dumpFollowerIDs -uname ScreenName -limit 1 -o followers.txt      </code><br>
<code> tsak dumpFollowerIDs -uid 101010111 -o followers.txt   </code><br>
<code> tsak dumpFriendIDs -uname ScreenName -limit 1 -o friends.txt    </dumpAccountSettingscode><br>
<code> tsak dumpFriendIDs -uid 01010101 -limit 1 -o friends.txt    </code><br>
<code> tsak dumpUserTimeLine -uname ScreenName -limit 1 -o timeline.txt    </code><br>
<code> tsak dumpUserTimeLine -uid 101010111 -limit 1 -o timeline.txt   </code><br>
<code> tsak dumpHomeTimeLine -limit 1 -o timeline.txt    </code><br>
<code> tsak dumpTweets -keywords "KEY words" -limit 1  -o tweets.txt    </code><br>
<code> tsak dumpOwnRetweets -o OwnRetweets.txt    </code><br>
<code> tsak dumpStatus -sid 01010101 -o Statuses.txt    </code><br>
<code> tsak dumpRetweeters -sid 01010101 -o Retweeters.txt    </code><br>
<code> tsak dumpMentionsTimeLine -o MentionTimeline.txt   </code><br>
<code> tsak dumpUsersLookup -i ids.txt -o lookup.txt    </code><br>
<code> tsak dumpBlockList -o blocklists.txt   </code><br>
<code> tsak searchUsers -keywords "Key Word" -o users.txt   </code><br>
<code> tsak showFriendShip -suser ScreenName -tuser ScreenName -o friendship.txt    </code><br>
<code> tsak dumpFriendsList -uname screenname -limit 1 -o friendlist.txt   </code><br>
<code> tsak dumpFriendsList -uid 01010101 -limit 1 -o friendlist.txt   </code><br>
<code> tsak dumpFollowersList -uname screenname -limit 1 -o followers.txt    </code><br>
<code> tsak dumpFollowersList -uid 010101 -limit 1 -o followers.txt    </code><br>
<code> tsak dumpFavourites -o favourites.txt    </code><br>
<code> tsak dumpSugeestedUserCats -o catagories.txt   </code><br>
<code> tsak dumpUserSuggestions -slug family -o suggestions.txt   </code><br>
<code> tsak dumpMemberSuggestions -slug family -o members.txt   </code><br>
<code> tsak dumpUserLists -uname screenName -o lists.txt    </code><br>
<code> tsak dumpUserLists -uid 010101 -o lists.txt    </code><br>
<code> tsak dumpListStatuses -lid 010101 -o liststatuses.txt    </code><br>
<code> tsak dumpSavedSearches -o searches.txt   </code><br>
<code> tsak lookupFriendShip -i ids.txt -o frndships.txt    </code><br>
<code> tsak dumpIncomingFriendships -limit 1 -o frndships.txt    </code><br>
<code> tsak dumpOutgoingFriendships -limit 1 -o frndships.txt    </code><br>
<code> tsak dumpGeoDetails -pid 00685eca27fbd26b -o ginfo.txt   </code><br>
<code> tsak dumpSimilarPlaces -lat 51.5072 -long 0.1275 -pname london -o places.txt   </code><br>
<code> tsak searchPlace -lat 51.5072 -long 0.1275 -o places.txt   </code><br>
<code> tsak dumpAvailableTrends -o output.txt   </code><br>
<code> tsak dumpPlaceTrends -woeid 010101 -o trends.txt   </code><br>
<code> tsak dumpClosestTrends -lat 0.0 -long 0.0 -o ClosestTrends.txt   </code><br>
<code> tsak dumpMutesIDs -limit 1 -o mutes.txt   </code><br>
<code> tsak dumpUserListMemberships -uname screenName -limit 1 -o memberships.txt    </code><br>
<code> tsak dumpUserListMemberships -uid 010101 -limit 1 -o memberships.txt    </code><br>
<code> tsak dumpUserListSubscribers -lid 02020202 -limit 1 -o subscribers.txt    </code><br>
<code> tsak dumpUserListMembers -lid 02020202 -limit 1 -o output.txt   </code><br>
<code> tsak dumpUserListSubscriptions -uname screenName -limit 1 -o subscription.txt   </code><br>
