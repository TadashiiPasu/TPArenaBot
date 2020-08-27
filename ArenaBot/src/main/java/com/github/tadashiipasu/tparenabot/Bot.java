package com.github.tadashiipasu.tparenabot;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.tadashiipasu.tparenabot.features.ResponseOnWhisper;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.tadashiipasu.tparenabot.features.ResponseOnMessage;
import com.github.twitch4j.helix.domain.ChannelInformation;
import com.github.twitch4j.helix.domain.ExtensionActiveList;
import com.github.twitch4j.helix.domain.User;
import com.github.twitch4j.helix.domain.UserList;
import com.github.twitch4j.tmi.domain.Chatters;
import com.netflix.hystrix.HystrixCommand;

import java.util.Arrays;

public class Bot {

    //TODO Have these take from some config
    /**
     * Holds the Bot Configuration
     */
//    private Configuration configuration;

    /**
     * Twitch4J API
     */
    private final TwitchClient twitchClient;

    /**
     * Constructor
     */
    public Bot() {
        // Load Configuration
//        loadConfiguration();

        TwitchClientBuilder clientBuilder = TwitchClientBuilder.builder();

        //region Auth
        OAuth2Credential chatAccount = new OAuth2Credential("tadashiipasu_bot", "wjyj56uyqouiqlc88izr7lsxxda9y1");

        //endregion

        EventManager eventManager = new EventManager();
        //region TwitchClient
        twitchClient = clientBuilder
                .withClientId("r8rfhq9md627hkbv8jym6xkk1gdf5d")
                .withClientSecret("g77gjmq76imdg138raat3mmtrnufwj")
                .withEnableChat(true)
                .withChatAccount(chatAccount)
                .withEnableHelix(true)
                .withEnableTMI(true)
                /*
                 * Build the TwitchClient Instance
                 */
                .build();
        //endregion
    }

    /**
     * Method to register all com.github.tadashiipasu.tparenabot.features
     */
    public void registerFeatures() {
        SimpleEventHandler eventHandler = twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class);

        // Register Event-based com.github.tadashiipasu.tparenabot.features
        ResponseOnMessage responseOnMessage = new ResponseOnMessage(eventHandler);
        ResponseOnWhisper responseOnWhisper = new ResponseOnWhisper(eventHandler);
    }

    public boolean userIsPresent(String username) {
        Chatters chatters = twitchClient
                .getMessagingInterface()
                .getChatters("tadashiipasu")
                .execute();
        return chatters.getAllViewers().contains(username);
    }

    public String getUserId(String username) {
        String userId = "";
        UserList userList = twitchClient
                .getHelix()
                .getUsers(null, null, Arrays.asList(username))
                .execute();
        for (User user : userList.getUsers()) {
            userId = user.getId();
        }
        return userId;
    }

    public String getUsername(String userId) {
        String username = "";
        UserList userList = twitchClient
                .getHelix()
                .getUsers(null, Arrays.asList(userId), null)
                .execute();
        for (User user : userList.getUsers()) {
            username = user.getDisplayName();
        }
        return username;
    }

    public TwitchClient getTwitchClient() {
        return twitchClient;
    }

    /**
     * Load the Configuration
     */
//    private void loadConfiguration() {
//        try {
//            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//            InputStream is = classloader.getResourceAsStream("config.yaml");
//
//            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
//            configuration = mapper.readValue(is, Configuration.class);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            System.out.println("Unable to load Configuration ... Exiting.");
//            System.exit(1);
//        }
//    }

    public void start() {
        // Connect to all channels
        twitchClient.getChat().joinChannel("TadashiiPasu");

    }
}