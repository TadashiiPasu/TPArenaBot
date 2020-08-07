import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.TwitchChat;

public class Main {

    public static void main(String[] args) {
        //Doesn't compile for the time being
        //TPArenaBot bot = new TPArenaBot("tadashiipasu_bot", "dx2df2pwc3pwbyx5vma9zxlhn4h263", "TadashiiPasu");

        OAuth2Credential chatAccount = new OAuth2Credential("tadashiipasu_bot", "dx2df2pwc3pwbyx5vma9zxlhn4h263");
        TwitchClient bot = TwitchClientBuilder.builder()
                .withEnableChat(true)
                .withChatAccount(chatAccount)
                .withDefaultAuthToken(chatAccount)
                .build();

        TwitchChat chat = bot.getChat();
        chat.joinChannel("TadashiiPasu");
        chat.sendMessage("TadashiiPasu", "Hey!");
    }
}