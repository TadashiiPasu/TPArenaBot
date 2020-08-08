package com.github.tadashiipasu.tparenabot;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.tadashiipasu.tparenabot.features.ResponseOnMessage;

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
        OAuth2Credential chatAccount = new OAuth2Credential("tadashiipasu_bot", "dx2df2pwc3pwbyx5vma9zxlhn4h263");

        //endregion

        EventManager eventManager = new EventManager();
        //region TwitchClient
        twitchClient = clientBuilder
                .withClientId("r8rfhq9md627hkbv8jym6xkk1gdf5d")
                .withClientSecret("g77gjmq76imdg138raat3mmtrnufwj")
                .withEnableChat(true)
                .withChatAccount(chatAccount)
                .withEnableHelix(true)
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