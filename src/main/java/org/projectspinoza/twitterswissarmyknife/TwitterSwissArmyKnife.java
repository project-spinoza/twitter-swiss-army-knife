package org.projectspinoza.twitterswissarmyknife;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.projectspinoza.twitterswissarmyknife.command.BaseCommand;
import org.projectspinoza.twitterswissarmyknife.command.CommandStreamStatuses;
import org.projectspinoza.twitterswissarmyknife.command.TsakCommand;
import org.projectspinoza.twitterswissarmyknife.util.TsakResponse;
import org.reflections.Reflections;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * TwitterSwissArmyKnife A simple command line utility that allows a user to
 * interact with Twitter's public API. it is very simple, straight forward, easy
 * to use and flexible API.
 * 
 * @author org.projectspinoza
 * @version v1.0.0
 */
public class TwitterSwissArmyKnife{
    private static Logger log = LogManager.getRootLogger();
    private static final String TSAK_CORE_COMMANDS_PACKAGE = "org.projectspinoza.twitterswissarmyknife.command";
    
    private JCommander rootCommander;
    private TsakResponse tsakResponse;
    private Twitter twitter;
    
    private boolean authorized;
    private List<Class<?>> registeredCommands = new ArrayList<Class<?>>();
    
    /**
     * Instantiates TwitterSwissArmyKnife
     * 
     */
    public TwitterSwissArmyKnife(){
        this(null);
    }
    
    /**
     * Instantiates TwitterSwissArmyKnife
     * 
     * @param twitter
     */
    public TwitterSwissArmyKnife(Twitter twitter){
        this(null, twitter);
    }
  
    /**
     * Instantiates TwitterSwissArmyKnife
     * 
     * @param rootCommander
     * @param twitter
     */
    public TwitterSwissArmyKnife(JCommander rootCommander, Twitter twitter){
        try{
            init(rootCommander, twitter);
        }catch(TwitterException tex){
            log.error("Twitter authorization Exception: {}", tex.toString());
        }
    }
    
    /**
     * Initializes TwitterSwissArmyKnife
     * 
     * @param rootCommander
     * @param twitter
     * @throws TwitterException
     */
    private void init(JCommander rootCommander, Twitter twitter) throws TwitterException{
        if(rootCommander == null){
            rootCommander = new JCommander();
        }
        this.rootCommander = rootCommander;
        registerCommands(TSAK_CORE_COMMANDS_PACKAGE);
        if(twitter != null){
            this.twitter = twitter;
            verifyTwitterCredentials();
        }
    }
    
    /**
     * returns true if the user has been authorized false else where
     * 
     * @return boolean
     */
    public boolean isAuthorized() {
        return authorized;
    }
    
    private void verifyTwitterCredentials() throws TwitterException{
        twitter.verifyCredentials();
        authorized = true;
    }    
    
    /**
     * register all command classes in the given package, e.g. the classes which extends BaseCommand
     * 
     * @param packageName
     */
    public void registerCommands(String packageName){
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends BaseCommand>> tsakCommandSet = reflections.getSubTypesOf(BaseCommand.class);
        for (Class<?> clazz : tsakCommandSet) {
            registerCommand(clazz);
        }
    }
    
    /**
     * register the given class as a command class
     * 
     * @param clazz
     */
    public void registerCommand(Class<?> clazz){
        if(BaseCommand.class.isAssignableFrom(clazz) && !registeredCommands.contains(clazz)){
            registeredCommands.add(clazz);
        }else{
            log.error("Cannot register {}, either already registered or un-assignable to BaseCommand", clazz);
        }
    }
    
    /**
     * returns all registered commands
     * 
     * @return registeredCommands
     */
    public List<Class<?>> getRegisteredCommands(){
        return Collections.unmodifiableList(registeredCommands);
    }
    
    /**
     * returns Twitter instance
     * 
     * @return Twitter
     */
    public Twitter getTwitterInstance(){
        return twitter;
    }
    
    /**
     * returns result e.g. the generated response of the executed command.
     * 
     * @return TsakResponse
     */
    public TsakResponse getResult(){
        return tsakResponse;
    }
    
    /**
     * returns rootCommander.
     * 
     * @return JCommander
     */
    public JCommander getRootCommander(){
        return rootCommander;
    }
    
    /**
     * returns the instance of the provided command
     * 
     * @param String parsedCommand
     * @return BaseCommand
     */
    public BaseCommand getActiveCommand() {
        String parsedCommand = rootCommander.getCommands().get("tsak").getParsedCommand();
        return (BaseCommand) rootCommander.getCommands().get("tsak").getCommands().get(parsedCommand).getObjects().get(0);
    }
    
    /**
     * returns the instance of TsakCommand
     * 
     * @param parsedCommand
     * @return TsakCommand
     */
    public TsakCommand getTsakCommand() {
        return (TsakCommand) rootCommander.getCommands().get("tsak").getObjects().get(0);
    }
    
    /**
     * activates all registered commands
     * 
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private void activateCommands() throws InstantiationException, IllegalAccessException{
        for(Class<?> clazz : registeredCommands){
            rootCommander.getCommands().get("tsak").addCommand(clazz.newInstance());
        }
    }
    
    /**
     * authorizes user with the provided credentials.
     * 
     * @throws TwitterException
     */
    public void authorizeUser() throws TwitterException, IOException {
        if (!setCredentials()) {
            log.error("Credentials not provided!");
            authorized = false;
            return;
        }
        if(twitter == null){
            TsakCommand commandTsak = getTsakCommand();
            twitter = new TwitterFactory(
                (new ConfigurationBuilder()
                    .setDebugEnabled(true)
                    .setOAuthConsumerKey(commandTsak.getConsumerKey())
                    .setOAuthConsumerSecret(commandTsak.getConsumerSecret())
                    .setOAuthAccessToken(commandTsak.getAccessToken())
                    .setOAuthAccessTokenSecret(commandTsak.getAccessSecret())).build()).getInstance();
        }
        verifyTwitterCredentials();
    }
    
    /**
     * executes the provided command
     * 
     * @param args
     * @return TwitterSwissArmyKnife
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws TwitterException
     * @throws ParameterException
     * @throws IOException
     */
    public TwitterSwissArmyKnife executeCommand(String[] args) throws InstantiationException, IllegalAccessException, TwitterException, ParameterException, IOException {
        tsakResponse = null;
        if (args == null) {
            log.info("Need help?? run > tsak <commandName> --help");
            return this;
        }
        rootCommander.addCommand("tsak", new TsakCommand());
        activateCommands();
        rootCommander.parse(args);
        BaseCommand baseCommand = getActiveCommand();    
        if (baseCommand.needHelp()) {
            JCommander tsakCommander = rootCommander.getCommands().get("tsak");  
            tsakCommander.usage(tsakCommander.getParsedCommand());
            return this;
        }
        if (!isAuthorized()) {
            authorizeUser();
        }
        log.info("tsak:{}", rootCommander.getCommands().get("tsak").getObjects().get(0));
        tsakResponse = baseCommand.execute(getTwitterInstance());
        
        if(!(tsakResponse == null || baseCommand instanceof CommandStreamStatuses)){
            showRateLimitStatus(tsakResponse);
        }
        
        return this;
    }
    
    /**
     * writes the result (generated data of the executed command) to the output
     * file.
     * 
     * @return TwitterSwissArmyKnife
     */
    public TwitterSwissArmyKnife write() {
        if (tsakResponse == null) {
            return this;
        }
        BufferedWriter bufferedWriter = null;
        try {
            BaseCommand baseCommand = getActiveCommand();
            bufferedWriter = new BufferedWriter(new FileWriter(new File(baseCommand.getOutputFile())));
            baseCommand.write(tsakResponse, bufferedWriter);
            bufferedWriter.close();
        } catch (Exception ex) {
            log.debug(ex.getMessage());
        } finally{
            if(bufferedWriter != null){
                try {
                    bufferedWriter.close();
                } catch (IOException ioex){
                   //. TODO 
                }
            }
        }
        return this;
    }
    
    /**
     * sets/verifies provided twitter credentials and returns True on Success
     * and False on Failure.
     * 
     * @return boolean
     * @throws IOException
     * @throws NullPointerException
     */
    private boolean setCredentials() throws IOException, NullPointerException{     
        if (!rootCommander.getParsedCommand().equals("tsak")) {
            log.info("Invalid Command: " + rootCommander.getParsedCommand());
            return false;
        }
        TsakCommand commandTsak = getTsakCommand();
        if (getTsakCommand().getConsumerKey() == null || commandTsak.getConsumerSecret() == null || commandTsak.getAccessToken() == null || commandTsak.getAccessSecret() == null) {
            String env = System.getenv("TSAK_CONF");
            if (env == null || env.isEmpty()) {
                log.error("Environment variable not set. TSAK_CONF {}", env);
                return false;
            }
            File propConfFile = new File(env + File.separator + "tsak.properties");
            if (!propConfFile.exists()) {
                log.error("tsak.properties file does not exist in: " + env);
                return false;
            }
            Properties prop = new Properties();
            InputStream propInstream = new FileInputStream(propConfFile);
            prop.load(propInstream);
            propInstream.close();
            String consumerKey = prop.getProperty("consumerKey");
            String consumerSecret = prop.getProperty("consumerSecret");
            String accessToken = prop.getProperty("accessToken");
            String accessSecret = prop.getProperty("accessSecret");
            if(consumerKey == null || consumerSecret == null || accessToken == null || accessSecret == null){
                log.error("some or all keys are missing!");
                return false;
            } 
            commandTsak.setConsumerKey(consumerKey.trim());
            commandTsak.setConsumerSecret(consumerSecret.trim());
            commandTsak.setAccessToken(accessToken.trim());
            commandTsak.setAccessSecret(accessSecret.trim());
        }
        return true;
    }
    
    /**
     * prints RateLimitStatus for specific command.
     * 
     * @param remApiLimits
     */
    public void showRateLimitStatus(TsakResponse tsakResponse) {
        log.info("---------------------------------------------------");
        log.info("DONE!!! REMAINING TWITTER API CALLS: [" + tsakResponse.getRemApiLimits() + "]");
        log.info("---------------------------------------------------");
    }
}
