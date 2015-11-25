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
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class TwitterSwissArmyKnife {
	private static Logger log = LogManager.getRootLogger();
	private static TwitterSwissArmyKnife tsakInstance = null;

	private CLIDriver commandLineDriver;
	private TsakCommand tsakCommand;
	private JCommander subCommander;
	private JCommander rootCommander;
	private DataWriter dataWriter;
	
	public DataWriter getDataWriter() {
		return dataWriter;
	}
	private boolean authorize;
	private Object data;
	
	private Twitter twitter;
	private User user;
	
	private TwitterSwissArmyKnife() {
		commandLineDriver = new CLIDriver();
		tsakCommand = new TsakCommand();
		dataWriter = new TsakResponseWriter();
	}
	public static TwitterSwissArmyKnife getInstance() {	
		if (tsakInstance == null) {
			tsakInstance = new TwitterSwissArmyKnife();
		}
		return tsakInstance;
	}
	public boolean isAuthorized() {
		return authorize;
	}
	public TwitterSwissArmyKnife setCommandLineDriver(CLIDriver commandLineDriver) {
		log.info("setting CommandLineDriver");
		tsakInstance.commandLineDriver = commandLineDriver;
		return tsakInstance;
	}
	public TwitterSwissArmyKnife setTsakCommand(TsakCommand tsakCommands) {
		log.info("setting tsakCommand");
		tsakInstance.tsakCommand = tsakCommands;
		return tsakInstance;
	}
	public TwitterSwissArmyKnife setWriter(DataWriter dataWriter){
		tsakInstance.dataWriter = dataWriter;
		return tsakInstance;
	}
	public Object getResult(){
		return tsakInstance.data;
	}
	public Twitter getTwitterInstance() {
		return twitter;
	}
	public User getUser() {
		return user;
	}
	public TwitterSwissArmyKnife write(){
		dataWriter.write(tsakInstance.data, subCommander.getParsedCommand(), commandLineDriver.getOutputFile());
		return tsakInstance;	
	}
	private void executeCommand() throws TwitterException, IOException{
		tsakInstance.data =  commandLineDriver.executeCommand(twitter);
	}
	public TwitterSwissArmyKnife executeCommand(String[] args) throws TwitterException, ParameterException, IOException{
		rootCommander = null;
		rootCommander = new JCommander();
		rootCommander.addCommand("tsak", tsakCommand);
		subCommander = rootCommander.getCommands().get("tsak");
		commandLineDriver.activateSubCommands(subCommander);
		rootCommander.parse(args);
		if(!isAuthorized()){
			authorizeUser();
		}
		if (isAuthorized()) {
			executeCommand();
		}else{log.error("User not authorized!");}
		return tsakInstance;
	}
	private TwitterSwissArmyKnife authorizeUser() {
		if (isAuthorized()){
			return tsakInstance;
		}
		try {
			if (!setCredentials()) {
				log.error("Credentials not provided!");
				authorize = false;
				return tsakInstance;
			}
			ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
			configurationBuilder.setDebugEnabled(true)
					.setOAuthConsumerKey(tsakCommand.getConsumerKey())
					.setOAuthConsumerSecret(tsakCommand.getConsumerSecret())
					.setOAuthAccessToken(tsakCommand.getAccessToken())
					.setOAuthAccessTokenSecret(tsakCommand.getAccessSecret());

			twitter = new TwitterFactory(configurationBuilder.build())
					.getInstance();
			user = twitter.verifyCredentials();
			authorize = true;

		} catch (IOException ioex) {
			log.error("Cannot read tsak.properties file!");
			authorize = false;
		} catch (TwitterException tex) {
			log.error("Authorization failed!");
			log.error(tex.getErrorMessage());
			authorize = false;
		}
		return tsakInstance;
	}
	private boolean setCredentials() throws IOException {
		if (!rootCommander.getParsedCommand().equals("tsak")) {
			log.info("Invalid Command: " + rootCommander.getParsedCommand());
			return false;
		}
		if (tsakCommand.getConsumerKey() == null || tsakCommand.getConsumerSecret() == null
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
			tsakCommand.setConsumerSecret(prop.getProperty("consumerSecret").trim());
			tsakCommand.setAccessToken(prop.getProperty("accessToken").trim());
			tsakCommand.setAccessSecret(prop.getProperty("accessSecret").trim());
		}
		return true;
	}
}
