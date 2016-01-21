package org.projectspinoza.twitterswissarmyknife;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.beust.jcommander.ParameterException;

import jline.console.ConsoleReader;
import twitter4j.TwitterException;

/**
 * Main is the Driver class for the TwitterSwissArmyKnife utility, and contains
 * various usage examples for this utility.
 * 
 * @author org.projectspinoza
 * @version v1.0.0
 * 
 */
public class Main {
    private static Logger log = LogManager.getRootLogger();
    private static ConsoleReader consoleReader;

    /**
     * provides TSAK usage
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {
        log.info("Initializing TSAK");

        consoleReader = new ConsoleReader();
        TwitterSwissArmyKnife tsak = new TwitterSwissArmyKnife();
        
        while (true) {
            try {
                tsak.executeCommand(getCommandLineArguments()).write();
            } catch (ParameterException ex) {
                consoleReader.println(ex.getMessage());
            } catch (TwitterException ex) {
                consoleReader.println(ex.getMessage());
            } catch (IllegalStateException ex) {
                consoleReader.println(ex.getMessage());
            } catch (IOException ex) {
                consoleReader.println(ex.getMessage());
            } finally {
                consoleReader.flush();
            }
        }
    }

    /**
     * reads command from the console, and returns it after parsing.
     * 
     * @return String[] parsed command
     * @throws IOException
     */
    public static String[] getCommandLineArguments() throws IOException {
        String commandLine = consoleReader.readLine("tsak>");
        if (commandLine.trim().equals("")) {
            return null;
        }
        if (commandLine.trim().equals("exit")) {
            consoleReader.println("Good Bye...");
            consoleReader.flush();
            System.exit(0);
        }
        List<String> cmdArgs = new ArrayList<String>();
        Pattern regex = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
        Matcher regexMatcher = regex.matcher(commandLine);
        while (regexMatcher.find()) {
            cmdArgs.add(regexMatcher.group());
        }
        return cmdArgs.toArray(new String[cmdArgs.size()]);
    }
}