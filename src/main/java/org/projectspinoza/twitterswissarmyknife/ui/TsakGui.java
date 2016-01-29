package org.projectspinoza.twitterswissarmyknife.ui;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.projectspinoza.twitterswissarmyknife.TwitterSwissArmyKnife;

public class TsakGui extends Application {
	private static String commandOptions = "";
	private static String commandName = "";
	private static final double PREFERED_INPUT_WIDTH = 300;
	private static final double PREFERED_WINDOW_WIDTH = 500;
	private static final double PREFERED_WINDOW_HEIGHT = 400;
	private static Map<String, List<String>> commandsMap = new HashMap<String, List<String>>();
	private static String commandPrefix = null;

	private static TwitterSwissArmyKnife tsakInstance = new TwitterSwissArmyKnife();

	public static void launchGui(String[] args) {
		registerCommands();
		launch(args);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void start(Stage primaryStage) {

		final GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Scene scene = new Scene(grid, PREFERED_WINDOW_WIDTH,
				PREFERED_WINDOW_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.setTitle("TwitterSwissArmyKnife -v1.0");

		Text tsakTitle = new Text("TSAK");
		tsakTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(tsakTitle, 0, 0, 2, 1);

		// . grid.add(item, columnIndex, rowIndex)

		Label commandsLabel = new Label("Commands");
		grid.add(commandsLabel, 0, 1);

		ObservableList<String> options = FXCollections.observableArrayList(
			"dumpAccountSettings", "dumpAvailableTrends", "dumpBlockList",
			"dumpClosestTrends", "dumpFavourites", "dumpFollowerIDs",
			"dumpFollowersList", "dumpFriendIDs", "dumpFriendsList",
			"dumpGeoDetails", "dumpHomeTimeLine",
			"dumpIncomingFriendships", "dumpListStatuses",
			"dumpMemberSuggestions", "dumpMentionsTimeLine",
			"dumpMutesIDs", "dumpMutesList", "dumpOutgoingFriendships",
			"dumpOwnRetweets", "dumpPlaceTrends", "dumpRetweeters",
			"dumpSavedSearches", "dumpSimilarPlaces", "dumpStatus",
			"dumpSugeestedUserCats", "dumpTweets", "dumpUserListMembers",
			"dumpUserListMemberships", "dumpUserLists",
			"dumpUserListSubscribers", "dumpUserListSubscriptions",
			"dumpUserSuggestions", "dumpUserTimeLine", "searchPlace",
			"searchUsers", "showFriendShip");
		final ComboBox commandBox = new ComboBox(options);
		commandBox.setPrefWidth(PREFERED_INPUT_WIDTH);
		commandBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String previousV, String currentV) {
				try {
					for (int j = 0; j < 8; j++) {
						grid.getChildren().remove(5);
					}
				} catch (Exception ex) {
					// . TODO
				}
				List<String> options = commandsMap.get(currentV);
				int r = 3;
				commandName = currentV;
				for (String key : options) {
					Label optionLabel = new Label(key);
					TextField optionField = new TextField();
					optionField.setPrefWidth(PREFERED_INPUT_WIDTH);
					grid.add(optionLabel, 0, r);
					grid.add(optionField, 1, r);
					r++;
				}
			}
		});

		grid.add(commandBox, 1, 1);

		final Text actiontarget = new Text();
		grid.add(actiontarget, 1, 2);

		Button btn = new Button("Run Command");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 7);
		// . row 3, 4, 5, 6 are reserved for dynamic fields, from position 5;
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				int ic = 6;
				commandOptions = "";
				boolean fieldMissing = false;
				try {
					for (int j = 1; j < 4; j++) {
						TextField f = (TextField) grid.getChildren().get(ic);
						Label l = (Label) grid.getChildren().get(ic - 1);
						if (l.getText().isEmpty() || f.getText().isEmpty()) {
							actiontarget.setFill(Color.FIREBRICK);
							actiontarget
									.setText("Some required fields are missing!");
							fieldMissing = true;
						}
						commandOptions += l.getText() + " " + f.getText() + " ";
						ic += 2;
					}
				} catch (Exception ex) {
					// . TODO
				} finally {
					if (!fieldMissing && !commandOptions.isEmpty()) {
						actiontarget.setText("");
						if (commandPrefix == null || commandPrefix.isEmpty()) {
							actiontarget.setFill(Color.FIREBRICK);
							actiontarget.setText("tsak.properties file not found!");
						} else {
							String tsakCommand = commandPrefix + commandName + " " + commandOptions;
							actiontarget.setFill(Color.DARKGREY);
							actiontarget.setText("Running command...");
							try {
								tsakInstance.executeCommand(tsakCommand.split(" ")).write();
								actiontarget.setText("Done!");
							} catch (Exception ex) {
								actiontarget.setText("Error...");
							}
						}
					} else {
						actiontarget.setFill(Color.FIREBRICK);
						actiontarget.setText("Some required fields are missing!");
					}
				}
			}
		});
		primaryStage.show();
		commandBox.getSelectionModel().select(5);
	}

	public static void registerCommands() {

		List<String> dumpAccountSettings = new ArrayList<String>();
		dumpAccountSettings.addAll(Arrays.asList("-o".split(" ")));
		commandsMap.put("dumpAccountSettings", dumpAccountSettings);

		List<String> dumpAvailableTrends = new ArrayList<String>();
		dumpAvailableTrends.addAll(Arrays.asList("-o".split(" ")));
		commandsMap.put("dumpAvailableTrends", dumpAvailableTrends);

		List<String> dumpBlockList = new ArrayList<String>();
		dumpBlockList.addAll(Arrays.asList("-o".split(" ")));
		commandsMap.put("dumpBlockList", dumpBlockList);

		List<String> dumpClosestTrends = new ArrayList<String>();
		dumpClosestTrends.addAll(Arrays.asList("-lat -long -o".split(" ")));
		commandsMap.put("dumpClosestTrends", dumpClosestTrends);

		List<String> dumpFavourites = new ArrayList<String>();
		dumpFavourites.addAll(Arrays.asList("-o".split(" ")));
		commandsMap.put("dumpFavourites", dumpFavourites);

		List<String> dumpFollowerIDs = new ArrayList<String>();
		dumpFollowerIDs.addAll(Arrays.asList("-uname -limit -o".split(" ")));
		commandsMap.put("dumpFollowerIDs", dumpFollowerIDs);

		List<String> dumpFollowersList = new ArrayList<String>();
		dumpFollowersList.addAll(Arrays.asList("-uname -limit -o".split(" ")));
		commandsMap.put("dumpFollowersList", dumpFollowersList);

		List<String> dumpFriendIDs = new ArrayList<String>();
		dumpFriendIDs.addAll(Arrays.asList("-uname -limit -o".split(" ")));
		commandsMap.put("dumpFriendIDs", dumpFriendIDs);

		List<String> dumpFriendsList = new ArrayList<String>();
		dumpFriendsList.addAll(Arrays.asList("-uname -limit -o".split(" ")));
		commandsMap.put("dumpFriendsList", dumpFriendsList);

		List<String> dumpGeoDetails = new ArrayList<String>();
		dumpGeoDetails.addAll(Arrays.asList("-pid -o".split(" ")));
		commandsMap.put("dumpGeoDetails", dumpGeoDetails);

		List<String> dumpHomeTimeLine = new ArrayList<String>();
		dumpHomeTimeLine.addAll(Arrays.asList("-o".split(" ")));
		commandsMap.put("dumpHomeTimeLine", dumpHomeTimeLine);

		List<String> dumpIncomingFriendships = new ArrayList<String>();
		dumpIncomingFriendships.addAll(Arrays.asList("-limit -o".split(" ")));
		commandsMap.put("dumpIncomingFriendships", dumpIncomingFriendships);

		List<String> dumpListStatuses = new ArrayList<String>();
		dumpListStatuses.addAll(Arrays.asList("-lid -o".split(" ")));
		commandsMap.put("dumpListStatuses", dumpListStatuses);

		List<String> dumpMemberSuggestions = new ArrayList<String>();
		dumpMemberSuggestions.addAll(Arrays.asList("-slug -o".split(" ")));
		commandsMap.put("dumpMemberSuggestions", dumpMemberSuggestions);

		List<String> dumpMentionsTimeLine = new ArrayList<String>();
		dumpMentionsTimeLine.addAll(Arrays.asList("-o".split(" ")));
		commandsMap.put("dumpMentionsTimeLine", dumpMentionsTimeLine);

		List<String> dumpMutesIDs = new ArrayList<String>();
		dumpMutesIDs.addAll(Arrays.asList("-limit -o".split(" ")));
		commandsMap.put("dumpMutesIDs", dumpMutesIDs);

		List<String> dumpMutesList = new ArrayList<String>();
		dumpMutesList.addAll(Arrays.asList("-limit -o".split(" ")));
		commandsMap.put("dumpMutesList", dumpMutesList);

		List<String> dumpOutgoingFriendships = new ArrayList<String>();
		dumpOutgoingFriendships.addAll(Arrays.asList("-limit -o".split(" ")));
		commandsMap.put("dumpOutgoingFriendships", dumpOutgoingFriendships);

		List<String> dumpOwnRetweets = new ArrayList<String>();
		dumpOwnRetweets.addAll(Arrays.asList("-o".split(" ")));
		commandsMap.put("dumpOwnRetweets", dumpOwnRetweets);

		List<String> dumpPlaceTrends = new ArrayList<String>();
		dumpPlaceTrends.addAll(Arrays.asList("-woeid -o".split(" ")));
		commandsMap.put("dumpPlaceTrends", dumpPlaceTrends);

		List<String> dumpRetweeters = new ArrayList<String>();
		dumpRetweeters.addAll(Arrays.asList("-sid -o".split(" ")));
		commandsMap.put("dumpRetweeters", dumpRetweeters);

		List<String> dumpSavedSearches = new ArrayList<String>();
		dumpSavedSearches.addAll(Arrays.asList("-o".split(" ")));
		commandsMap.put("dumpSavedSearches", dumpSavedSearches);

		List<String> dumpSimilarPlaces = new ArrayList<String>();
		dumpSimilarPlaces.addAll(Arrays.asList("-lat -long -pname -o".split(" ")));
		commandsMap.put("dumpSimilarPlaces", dumpSimilarPlaces);

		List<String> dumpStatus = new ArrayList<String>();
		dumpStatus.addAll(Arrays.asList("-sid -o".split(" ")));
		commandsMap.put("dumpStatus", dumpStatus);

		List<String> dumpSugeestedUserCats = new ArrayList<String>();
		dumpSugeestedUserCats.addAll(Arrays.asList("-o".split(" ")));
		commandsMap.put("dumpSugeestedUserCats", dumpSugeestedUserCats);

		List<String> dumpTweets = new ArrayList<String>();
		dumpTweets.addAll(Arrays.asList("-keywords -limit -o".split(" ")));
		commandsMap.put("dumpTweets", dumpTweets);

		List<String> dumpUserListMembers = new ArrayList<String>();
		dumpUserListMembers.addAll(Arrays.asList("-lid -limit -o".split(" ")));
		commandsMap.put("dumpUserListMembers", dumpUserListMembers);

		List<String> dumpUserListMemberships = new ArrayList<String>();
		dumpUserListMemberships.addAll(Arrays.asList("-uname -limit -o".split(" ")));
		commandsMap.put("dumpUserListMemberships", dumpUserListMemberships);

		List<String> dumpUserLists = new ArrayList<String>();
		dumpUserLists.addAll(Arrays.asList("-uname -limit -o".split(" ")));
		commandsMap.put("dumpUserLists", dumpUserLists);

		List<String> dumpUserListSubscribers = new ArrayList<String>();
		dumpUserListSubscribers.addAll(Arrays.asList("-lid -limit -o".split(" ")));
		commandsMap.put("dumpUserListSubscribers", dumpUserListSubscribers);

		List<String> dumpUserListSubscriptions = new ArrayList<String>();
		dumpUserListSubscriptions.addAll(Arrays.asList("-uname -limit -o".split(" ")));
		commandsMap.put("dumpUserListSubscriptions", dumpUserListSubscriptions);

		List<String> dumpUserSuggestions = new ArrayList<String>();
		dumpUserSuggestions.addAll(Arrays.asList("-slug -o".split(" ")));
		commandsMap.put("dumpUserSuggestions", dumpUserSuggestions);

		List<String> dumpUserTimeLine = new ArrayList<String>();
		dumpUserTimeLine.addAll(Arrays.asList("-uname -limit -o".split(" ")));
		commandsMap.put("dumpUserTimeLine", dumpUserTimeLine);

		List<String> searchPlace = new ArrayList<String>();
		searchPlace.addAll(Arrays.asList("-lat -long -o".split(" ")));
		commandsMap.put("searchPlace", searchPlace);

		List<String> searchUsers = new ArrayList<String>();
		searchUsers.addAll(Arrays.asList("-keyword -o".split(" ")));
		commandsMap.put("searchUsers", searchUsers);

		List<String> showFriendShip = new ArrayList<String>();
		showFriendShip.addAll(Arrays.asList("-suser -tuser -o".split(" ")));
		commandsMap.put("showFriendShip", showFriendShip);

		setCommandPrefix();
	}

	private static void setCommandPrefix() {
		try {
			Properties prop = new Properties();
			InputStream propInstream = new FileInputStream("tsak.properties");
			prop.load(propInstream);
			propInstream.close();
			String consumerKey = prop.getProperty("consumerKey");
			String consumerSecret = prop.getProperty("consumerSecret");
			String accessToken = prop.getProperty("accessToken");
			String accessSecret = prop.getProperty("accessSecret");

			commandPrefix = "tsak -consumerKey=" + consumerKey + " "
					+ "-consumerSecret=" + consumerSecret + " "
					+ "-accessToken=" + accessToken + " " 
					+ "-accessSecret=" + accessSecret + " ";

		} catch (Exception ex) {
			// . TODO
		}
	}

}