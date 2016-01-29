package org.projectspinoza.twitterswissarmyknife;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.projectspinoza.twitterswissarmyknife.ui.TsakGui;

import com.beust.jcommander.ParameterException;

import jline.console.ConsoleReader;
import twitter4j.TwitterException;

/**
 * TwitterSwissArmyKnife Driver class
 * 
 * @author org.projectspinoza
 * @version v1.0.0
 * 
 */
public class Main {
    private static Logger log = LogManager.getRootLogger();
    private static ConsoleReader consoleReader;

    /**
     * TwitterSwissArmyKnife launcher
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        log.info("Initializing TSAK");
        
        if(args.length == 1 && args[0].equals("gui")){
            launchGui(args);
        }else{
            launchCmd(args);
        }
        
    }
    /**
     * launch tsak in GUI mode
     * 
     * @param args
     */
    public static void launchGui(String[] args){
        TsakGui.launchGui(args);
    }
    /**
     * launch tsak in commandline mode
     * 
     * @param args
     * @throws IOException
     */
    public static void launchCmd(String[] args) throws IOException{
        consoleReader = new ConsoleReader();
        TwitterSwissArmyKnife tsak = new TwitterSwissArmyKnife();
        while (true) {
            try {
                String[] command = getCommandLineArguments();
                tsak.executeCommand(command).write();
            } catch (ParameterException ex) {
                log.error(ex.getMessage());
            } catch (TwitterException ex) {
                log.error(ex.getMessage());
            } catch (Exception ex) {
                log.error(ex.getMessage());
            } finally {
                consoleReader.flush();
            }
        }
    }
    /**
     * parse command.
     * 
     * @return String[] parsed command
     * @throws IOException
     */
    public static String[] getCommandLineArguments() throws IOException {
        String commandLine = consoleReader.readLine("tsak> ");
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