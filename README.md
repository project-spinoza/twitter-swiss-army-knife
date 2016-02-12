# Twitter Swiss Army Knife
twitter-swiss-army-knife `v1.0.0` is ready for release!!!
####TSAK GUI
![GUI](https://github.com/project-spinoza/twitter-swiss-army-knife/blob/gh-pages/images/gui.png)

####TSAK CMD
![CMD](https://github.com/project-spinoza/twitter-swiss-army-knife/blob/gh-pages/images/tsak_crop.jpg)

##How to build and Run
#### Download
`git clone https://github.com/project-spinoza/twitter-swiss-army-knife.git`
<br>OR<br>
Download zip file - [download](https://github.com/project-spinoza/twitter-swiss-army-knife/archive/master.zip)
<br>
####Build
**Build with maven**<br>
* run the following command from project's root directory:<br>
`mvn clean package`

####Setup

* create twitter app [prerequisites](https://github.com/project-spinoza/twitter-swiss-army-knife/wiki/Prerequisites)<br>
* edit `tsak.properties` file and enter your keys e.g. <br><br>
`accessToken=[enter your access token here]`<br>
`consumerSecret=[enter your consumer secret key here]`<br>
`consumerKey=[enter your consumer key here]`<br>
`accessSecret=[enter your accessSecret key here]`<br><br>

####Run in GUI mode
* Inside project root directory, run `bash startup_gui.sh`<br>
* **Note**: GUI mode may not support full options

####Run in command line mode
* create <code>TSAK_CONF</code> environment variable and point it to the directory containing tsak.properties file<br>
* Inside project root directory, run one of the following startup file depending on the type of platform:<br> 
`bash startup.sh` **Linux**<br>
`startup.bat` **Windows**
<br>
Run either of the available TSAK commands</b>

####Help
use option `--help` with tsak command to display command options e.g.<br>
`tsak dumpFollowerIDs --help`

####Note
* For detailed setup visit [setup](https://github.com/project-spinoza/twitter-swiss-army-knife/wiki/Prerequisites)<br>
* For detail description on available commands refer to [command line usage](https://github.com/project-spinoza/twitter-swiss-army-knife/wiki/Command-Line-Usage)<br>
* You need to have twitter's access key, token key to run TSAK, for details refer to [tsak setup](https://github.com/project-spinoza/twitter-swiss-army-knife/wiki/Prerequisites) page<br>
* For API Documentation refer to [doc](http://project-spinoza.github.io/twitter-swiss-army-knife/doc/)

####Available Tsak Commands [detail usage](https://github.com/project-spinoza/twitter-swiss-army-knife/wiki/Command-Line-Usage)

`tsak dumpAccountSettings -o accountSettings.txt`<br>
`tsak dumpFollowerIDs -uname ScreenName -limit 1 -o followerIDs.txt`<br>
`tsak dumpFollowerIDs -uid 101010111 -o followerIDs.txt`<br>
`tsak dumpFriendIDs -uname ScreenName -limit 1 -o friendIDs.txt`<br>
`tsak dumpFriendIDs -uid 01010101 -limit 1 -o friendIDs.txt`<br>
`tsak dumpUserTimeLine -uname ScreenName -limit 1 -o userTimeLine.txt`<br>
`tsak dumpUserTimeLine -uid 101010111 -limit 1 -o userTimeLine.txt`<br>
`tsak dumpHomeTimeLine -o homeTimeLine.txt`<br>
`tsak dumpTweets -keywords "KEY words" -limit 1  -o tweets.txt`<br>
`tsak dumpOwnRetweets -o ownRetweets.txt`<br>
`tsak dumpStatus -sid 01010101 -o statuses.txt`<br>
`tsak dumpRetweeters -sid 01010101 -o retweeters.txt`<br>
`tsak dumpMentionsTimeLine -o mentionTimeline.txt`<br>
`tsak dumpUsersLookup -i ids.txt -o lookup.txt`<br>
`tsak dumpBlockList -o blocklists.txt`<br>
`tsak searchUsers -keywords "Key Word" -o users.txt`<br>
`tsak showFriendShip -suser ScreenName -tuser ScreenName -o friendship.txt`<br>
`tsak dumpFriendsList -uname screenname -limit 1 -o friendsList.txt`<br>
`tsak dumpFriendsList -uid 01010101 -limit 1 -o friendsList.txt`<br>
`tsak dumpFollowersList -uname screenname -limit 1 -o followersList.txt`<br>
`tsak dumpFollowersList -uid 010101 -limit 1 -o followersList.txt`<br>
`tsak dumpFavourites -o favourites.txt`<br>
`tsak dumpSugeestedUserCats -o catagories.txt`<br>
`tsak dumpUserSuggestions -slug family -o userSuggestions.txt`<br>
`tsak dumpMemberSuggestions -slug family -o memberSuggestions.txt`<br>
`tsak dumpUserLists -uname screenName -o userLists.txt`<br>
`tsak dumpUserLists -uid 010101 -o userLists.txt`<br>
`tsak dumpListStatuses -lid 010101 -o listStatuses.txt`<br>
`tsak dumpSavedSearches -o searches.txt`<br>
`tsak lookupFriendShip -i ids.txt -o frndships.txt`<br>
`tsak dumpIncomingFriendships -limit 1 -o incomingFriendships.txt`<br>
`tsak dumpOutgoingFriendships -limit 1 -o outgoingFriendships.txt`<br>
`tsak dumpGeoDetails -pid 00685eca27fbd26b -o ginfo.txt`<br>
`tsak dumpSimilarPlaces -lat 51.5072 -long 0.1275 -pname london -o similarPlaces.txt`<br>
`tsak searchPlace -lat 51.5072 -long 0.1275 -o place.txt`<br>
`tsak dumpAvailableTrends -o availableTrends.txt`<br>
`tsak dumpPlaceTrends -woeid 010101 -o placeTrends.txt`<br>
`tsak dumpClosestTrends -lat 0.0 -long 0.0 -o closestTrends.txt`<br>
`tsak dumpMutesIDs -limit 1 -o mutes.txt`<br>
`tsak dumpUserListMemberships -uname screenName -limit 1 -o listMemberships.txt`<br>
`tsak dumpUserListMemberships -uid 010101 -limit 1 -o listMemberships.txt`<br>
`tsak dumpUserListSubscribers -lid 02020202 -limit 1 -o listSubscribers.txt`<br>
`tsak dumpUserListMembers -lid 02020202 -limit 1 -o listMembers.txt`<br>
`tsak dumpUserListSubscriptions -uname screenName -limit 1 -o listSubscription.txt`<br>
`tsak streamStatuses -keywords "iPhone" -show -o streamsOut.txt`


## License
The code is licensed under the [Apache License Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
<br>
## Questions or Suggestions
**Email** `project.spinoza@gmail.com`<br>
**Issues** https://github.com/project-spinoza/twitter-swiss-army-knife/issues

