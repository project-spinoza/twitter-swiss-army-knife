package org.projectspinoza.twitterswissarmyknife;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.projectspinoza.twitterswissarmyknife.util.DataWriter;
import org.projectspinoza.twitterswissarmyknife.util.TsakResponseWriter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

/**
 * TwitterSwissArmyKnife
 * A simple command line utility that allows a user to interact with Twitter's public API.
 * it is very simple, straight forward, easy to use and flexible API. It
 * provides Method chaining, re-usability, flexibility and simplicity.
 * 
 * @author org.projectspinoza
 * @version v1.0
 *
 */
public class TwitterSwissArmyKnife {
    private static Logger log = LogManager.getRootLogger();
    private static TwitterSwissArmyKnife tsakInstance = null;

    private CLIDriver commandLineDriver;
    private TsakCommand tsakCommand;
    private JCommander subCommander;
    private JCommander rootCommander;
    private DataWriter dataWriter;
    private String parsedCommand;

    private boolean authorize;
    private Object data;

    private ConfigurationBuilder configurationBuilder;
    private Twitter twitter;
    
    /**
     * returns DataWriter instance.
     * 
     * @return dataWriter
     */
    public DataWriter getDataWriter() {
        return dataWriter;
    }
    /**
     * default Constructor for TwitterSwissArmyKnife, it prepares CommandlineDriver, tsakCommand and dataWriter, which can be override later if needed.
     * 
     */
    private TwitterSwissArmyKnife() {
        commandLineDriver = new CLIDriver();
        tsakCommand = new TsakCommand();
        dataWriter = new TsakResponseWriter();
    }
    /**
     * returns TwitterSwissArmyKnife's static instance
     * 
     * @return tsakInstance
     */
    public static TwitterSwissArmyKnife getInstance() {
        if (tsakInstance == null) {
            tsakInstance = new TwitterSwissArmyKnife();
        }
        return tsakInstance;
    }
    /**
     * returns True if user has authorization to access twitterAPI false other wise.
     * 
     * @return authorize
     */
    public boolean isAuthorized() {
        return authorize;
    }
    /**
     * sets OR overrides default commandlineDriver.
     * 
     * @param commandLineDriver
     * @return tsakInstance
     */
    public TwitterSwissArmyKnife setCommandLineDriver(
            CLIDriver commandLineDriver) {
        log.info("setting CommandLineDriver");
        tsakInstance.commandLineDriver = commandLineDriver;
        return tsakInstance;
    }
    /**
     * sets OR overrides default tsakCommand.
     * 
     * @param tsakCommands
     * @return tsakInstance
     */
    public TwitterSwissArmyKnife setTsakCommand(TsakCommand tsakCommands) {
        log.info("setting tsakCommand");
        tsakInstance.tsakCommand = tsakCommands;
        return tsakInstance;
    }
    /**
     * Sets OR Overrides default dataWriter.
     * 
     * @param dataWriter
     * @return tsakInstance
     */
    public TwitterSwissArmyKnife setWriter(DataWriter dataWriter) {
        tsakInstance.dataWriter = dataWriter;
        return tsakInstance;
    }
    /**
     * returns result e.g. the generated data of the executed command in Object format.
     * 
     * @return data
     */
    public Object getResult() {
        return tsakInstance.data;
    }
    /**
     * returns twitter instance
     * 
     * @return twitter
     */
    public Twitter getTwitterInstance() {
        return twitter;
    }
    /**
     * writes the result (generated data of the executed command) to the output file.
     *  
     * @return tsakInstance
     */
    public TwitterSwissArmyKnife write() {
        dataWriter.write(tsakInstance.data, subCommander.getParsedCommand(),
                commandLineDriver.getOutputFile());
        return tsakInstance;
    }
    /**
     * executes twitter streaming command.
     * 
     * @throws IOException
     */
    public void executeStreamingCommand() throws IOException {
        CommandStreamStatuses streamStatuses = (CommandStreamStatuses) getSubCommand(parsedCommand);
        (new TwitterStreamingExcecutor()).execute(configurationBuilder,
                streamStatuses);
    }
    /**
     * executes dump command.
     * 
     * @throws IOException
     * @throws TwitterException
     */
    public void executeDumpCommand() throws IOException, TwitterException {
        if (!isAuthorized()) {
            authorizeUser();
        }
        if (isAuthorized()) {
            Object subCommand = getSubCommand(parsedCommand);
            tsakInstance.data = commandLineDriver.executeCommand(twitter,
                    parsedCommand, subCommand);
        } else {
            log.error("User not authorized!");
        }
    }
    /**
     * executes (provided in the argument) command.
     * 
     * @param args
     * @return tsakInstance
     * @throws TwitterException
     * @throws ParameterException
     * @throws IOException
     */
    public TwitterSwissArmyKnife executeCommand(String[] args)
            throws TwitterException, ParameterException, IOException {
        if (args == null) {
            return tsakInstance;
        }
        rootCommander = null;
        rootCommander = new JCommander();
        rootCommander.addCommand("tsak", tsakCommand);
        subCommander = rootCommander.getCommands().get("tsak");
        activateSubCommands();
        rootCommander.parse(args);
        parsedCommand = subCommander.getParsedCommand();

        setConfigurationBuilder();
        if (parsedCommand.equals("streamStatuses")) {
            executeStreamingCommand();
        } else {
            executeDumpCommand();
        }
        return tsakInstance;
    }
    /**
     * authorizes user with the provided credentials.
     * 
     * @throws TwitterException
     */
    private void authorizeUser() throws TwitterException {
        twitter = new TwitterFactory(getConfigurationBuilder().build())
                .getInstance();
        twitter.verifyCredentials();
        authorize = true;
    }
    /**
     * sets twitter configuration builder.
     * 
     * @throws IOException
     */
    private void setConfigurationBuilder() throws IOException {
        if (isAuthorized()) {
            return;
        }
        if (!setCredentials()) {
            log.error("Credentials not provided!");
            authorize = false;
            return;
        }
        configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey(tsakCommand.getConsumerKey())
                .setOAuthConsumerSecret(tsakCommand.getConsumerSecret())
                .setOAuthAccessToken(tsakCommand.getAccessToken())
                .setOAuthAccessTokenSecret(tsakCommand.getAccessSecret());
    }
    /**
     * returns twitter configuration builder.
     * 
     * @return
     */
    private ConfigurationBuilder getConfigurationBuilder() {
        return configurationBuilder;
    }
    /**
     * sets/verifies provided twitter credentials and returns True on Success and False on Failure.
     * 
     * @return boolean
     * @throws IOException
     */
    private boolean setCredentials() throws IOException {
        if (!rootCommander.getParsedCommand().equals("tsak")) {
            log.info("Invalid Command: " + rootCommander.getParsedCommand());
            return false;
        }
        if (tsakCommand.getConsumerKey() == null
                || tsakCommand.getConsumerSecret() == null
                || tsakCommand.getAccessToken() == null
                || tsakCommand.getAccessSecret() == null) {
            String env_var = System.getenv("TSAK_CONF");
            if (env_var == null || env_var.isEmpty()) {
                log.error("Environment variable not set. TSAK_CONF {}");
                return false;
            }
            File propConfFile = new File(env_var + File.separator
                    + "tsak.properties");
            if (!propConfFile.exists()) {
                log.error("tsak.properties file does not exist in: " + env_var);
                return false;
            }
            Properties prop = new Properties();
            InputStream propInstream = new FileInputStream(propConfFile);
            prop.load(propInstream);
            propInstream.close();
            tsakCommand.setConsumerKey(prop.getProperty("consumerKey").trim());
            tsakCommand.setConsumerSecret(prop.getProperty("consumerSecret")
                    .trim());
            tsakCommand.setAccessToken(prop.getProperty("accessToken").trim());
            tsakCommand
                    .setAccessSecret(prop.getProperty("accessSecret").trim());
        }
        return true;
    }
    /**
     * activates/prepares all of the commands for execution.
     * 
     */
    public void activateSubCommands() {

        this.subCommander.addCommand(new CommandDumpFollowerIDs());
        this.subCommander.addCommand(new CommandDumpFriendIDs());
        this.subCommander.addCommand(new CommandDumpAccountSettings());
        this.subCommander.addCommand(new CommandDumpUserTimeLine());
        this.subCommander.addCommand(new CommandDumpHomeTimeLine());
        this.subCommander.addCommand(new CommandDumpTweets());
        this.subCommander.addCommand(new CommandDumpOwnRetweets());
        this.subCommander.addCommand(new CommandDumpStatus());
        this.subCommander.addCommand(new CommandDumpRetweeters());
        this.subCommander.addCommand(new CommandDumpMentionsTimeLine());
        this.subCommander.addCommand(new CommandDumpUsersLookup());
        this.subCommander.addCommand(new CommandDumpBlockList());
        this.subCommander.addCommand(new CommandSearchUsers());
        this.subCommander.addCommand(new CommandShowFriendShip());
        this.subCommander.addCommand(new CommandDumpFollowersList());
        this.subCommander.addCommand(new CommandDumpFriendsList());
        this.subCommander.addCommand(new CommandDumpFavourites());
        this.subCommander.addCommand(new CommandDumpSugeestedUserCats());
        this.subCommander.addCommand(new CommandDumpUserSuggestions());
        this.subCommander.addCommand(new CommandDumpMemberSuggestions());
        this.subCommander.addCommand(new CommandDumpUserLists());
        this.subCommander.addCommand(new CommandDumpListStatuses());
        this.subCommander.addCommand(new CommandDumpSavedSearches());
        this.subCommander.addCommand(new CommandLookupFriendShip());
        this.subCommander.addCommand(new CommandDumpIncomingFriendships());
        this.subCommander.addCommand(new CommandDumpOutgoingFriendships());
        this.subCommander.addCommand(new CommandDumpGeoDetails());
        this.subCommander.addCommand(new CommandDumpSimilarPlaces());
        this.subCommander.addCommand(new CommandSearchPlace());
        this.subCommander.addCommand(new CommandDumpAvailableTrends());
        this.subCommander.addCommand(new CommandDumpPlaceTrends());
        this.subCommander.addCommand(new CommandDumpClosestTrends());
        this.subCommander.addCommand(new CommandDumpMutesIDs());
        this.subCommander.addCommand(new CommandDumpMutesList());
        this.subCommander.addCommand(new CommandDumpUserListMemberships());
        this.subCommander.addCommand(new CommandDumpUserListSubscriptions());
        this.subCommander.addCommand(new CommandDumpUserListMembers());
        this.subCommander.addCommand(new CommandDumpUserListSubscribers());
        this.subCommander.addCommand(new CommandStreamStatuses());
    }
    /**
     * returns parsedCommand e.g. the provided command.
     * 
     * @param parsedCommand
     * @return subCommand
     */
    public Object getSubCommand(String parsedCommand) {
        return subCommander.getCommands().get(parsedCommand).getObjects()
                .get(0);
    }
}
