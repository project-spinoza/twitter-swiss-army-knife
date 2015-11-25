package org.projectspinoza.twitterswissarmyknife;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jline.console.ConsoleReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import twitter4j.TwitterException;

import com.beust.jcommander.ParameterException;

/**
 * @author org.projectspinoza
 * 
 * TwitterSwissArmyKnife
 * 
 * Is a very simple, straightforward, easy to use and flexible API. It
 * provides Method chaining, re-usability, flexibility and simplicity.
 *
 */
public class Main {
	private static Logger log = LogManager.getRootLogger();

	public static void main(String[] args) throws TwitterException, IOException {
		log.info("Initializing TSAK");

		final ConsoleReader reader = new ConsoleReader();
		String commandLine;
		/**
		 * TSAK USAGE
		 * 
		 * 1. USAGE (NORMAL|SIMPLE)
		 * =======================================
		 * 	TwitterSwissArmyKnife tsak = TwitterSwissArmyKnife.getInstance();
		 * 	tsak.executeCommand(args).write();
		 */
		
		/**
		 * TSAK USAGE
		 * 
		 * 2. Using TSAK as a shell
		 * ===================================
		 * 
		 */
		while (true) {
			try {
				commandLine = reader.readLine("tsak>");
				if (commandLine.trim().equals("")) {
					continue;
				} else if (commandLine.trim().equals("exit")) {
					reader.println("Good Bye....");
					reader.flush();
					break;
				} else {
					List<String> mList = new ArrayList<String>();
					Pattern regex = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
					Matcher regexMatcher = regex.matcher(commandLine);
					TwitterSwissArmyKnife tsak = TwitterSwissArmyKnife.getInstance();
					while (regexMatcher.find()) {
						mList.add(regexMatcher.group());
					}
					String[] cmdArgs = mList.toArray(new String[mList.size()]);
					tsak.executeCommand(cmdArgs).write();
				}
			} catch(ParameterException ex){
				log.error(ex.getMessage());
			}catch(TwitterException ex){
				log.error(ex.getMessage());
			} catch (IllegalStateException ex) {
				reader.println(ex.getMessage());
				reader.flush();
			} catch (IOException ex) {
				reader.println(ex.getMessage());
				reader.flush();
			}
		}

		/**
		 * 3: USAGE 
		 * 
		 * (Don't want to provide credentials for each command OR want to run multiple commands in a loop?)
		 * =================================================================================================
		 * 
		 * String[] followersCommand = {"tsak","dumpFollowerIDs","-uname ","dmkavanagh", "-o", "followersIds.txt"};
		 * String[] userListsCommand = {"tsak", "dumpUserLists", "-uname","dmkavanagh", "-o", "userLists.txt"}; 
		 * String[] userSuggestionsCommand = {"tsak", "dumpUserSuggestions", "-slug", "Sports", "-o", "userSuggestions.txt"};
		 *
		 * List<String[]> commands = new ArrayList<String[]>();
		 * commands.add(followersCommand); 
		 * commands.add(userListsCommand);
		 * commands.add(userSuggestionsCommand);
		 *
		 * TwitterSwissArmyKnife tsak = TwitterSwissArmyKnife.getInstance();
		 * for(String[] command : commands){ 
		 * 		tsak.executeCommand(command).write();
		 *  }
		 *
		 */

		/**
		 * 4: USAGE (Do you want to provide your own CommandLineDriver, DataWriter etc.? )
		 * ================================================================================
		 * 
		 * TwitterSwissArmyKnife tsak = TwitterSwissArmyKnife.getInstance();
		 * 
		 * tsak.setCommandLineDriver(yourCommandLineDriver)
		 * 	   .setTsakCommand(yourTsakCommands) 
		 * 	   .setWriter(yourWriter)
		 * 	   .set...()
		 * 	   .executeCommand(args).write();
		 */

		/**
		 * 5: USAGE (Do you want to get/analyze the original response from twitter API ?)
		 * ===============================================================================
		 * 
		 * TwitterSwissArmyKnife tsak = TwitterSwissArmyKnife.getInstance();
		 *
		 * i. get the result only 
		 * ------------------- 
		 * Object result = tsak.executeCommand(args).getResult();
		 *
		 * ii. write and then get the result 
		 * ---------------------------------
		 *  Object result = tsak.executeCommand(args).write().getResult();
		 *
		 */

		/**
		 * 5: USAGE (much more) 
		 * ============================
		 * 
		 * for more detailed usage study the API. (TwitterSwissArmyKnife)
		 * 
		 */
		log.info("Done!");
	}
}