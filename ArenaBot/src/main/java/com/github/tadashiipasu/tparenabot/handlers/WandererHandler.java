package com.github.tadashiipasu.tparenabot.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tadashiipasu.tparenabot.Bot;
import com.github.tadashiipasu.tparenabot.pojos.ModBlock;
import com.github.tadashiipasu.tparenabot.pojos.StatBlock;
import com.github.tadashiipasu.tparenabot.pojos.Viewer;
import com.github.tadashiipasu.tparenabot.pojos.Wanderer;
import com.github.tadashiipasu.tparenabot.pojos.builders.ModBlockBuilder;
import com.github.tadashiipasu.tparenabot.pojos.builders.StatBlockBuilder;
import com.github.tadashiipasu.tparenabot.pojos.builders.WandererBuilder;
import com.github.tadashiipasu.tparenabot.pojos.moves.Effect;
import com.github.tadashiipasu.tparenabot.pojos.moves.Move;
import com.github.tadashiipasu.tparenabot.pojos.moves.Status;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WandererHandler {

    private final String wandererFilePath = "./wanderers";
    private final ViewerHandler viewerHandler = new ViewerHandler();
    private final MoveHandler moveHandler = new MoveHandler();

    public String wandererRegistration(ChannelMessageEvent event, String userId) throws IOException {
        String viewerFilePath = "./viewers";
        File viewerFile = new File(String.format(viewerFilePath + "/%s.json", userId));
        if (viewerFile.createNewFile()) {
            viewerHandler.createViewer(event.getUser().getName(), userId);
            return createWanderer(userId);
        } else {
            if (wandererIsRegistered(userId)) {
                return "You have already been registered. Get to dueling!";
            } else {
                return createWanderer(userId);
            }
        }
    }

    public String createWanderer(String userId) throws IOException {
        Viewer viewer = viewerHandler.getViewer(userId);
        if (!viewer.isCheckedIn()) {
            viewer.setStreamPoints(viewer.getStreamPoints() + 1);
            viewer.setCheckedIn(true);
            viewerHandler.updateViewer(viewer);
        }
        StatBlock viewerStats = viewer.getStats();
        Integer maxHealth = statMod(viewerStats.getMaxHealth());
        StatBlock stats = new StatBlockBuilder()
                .setMaxHealth(maxHealth)
                .setCurrentHealth(maxHealth)
                .setStrength(statMod(viewerStats.getStrength()))
                .setMagic(statMod(viewerStats.getMagic()))
                .setDefense(statMod(viewerStats.getDefense()))
                .setMagicDefense(statMod(viewerStats.getMagicDefense()))
                .setAccuracy(statMod(viewerStats.getAccuracy()))
                .setEvasion(statMod(viewerStats.getEvasion()))
                .setSpeed(statMod(viewerStats.getSpeed()))
                .build();
        String modifier = "2/2";
        ModBlock modifiers = new ModBlockBuilder()
                .setStrength(modifier)
                .setMagic(modifier)
                .setDefense(modifier)
                .setMagicDefense(modifier)
                .setAccuracy(modifier)
                .setEvasion(modifier)
                .setSpeed(modifier)
                .build();
        List<Integer> moveList = moveHandler.selectMove(getWanderer(userId), 4);
        Wanderer wanderer = new WandererBuilder()
                .setUsername(viewer.getUsername())
                .setUserId(userId)
                .setLevel(1)
                .setExperience(0)
                .setOriginalStats(stats)
                .setAdjustedStats(stats)
                .setModifiers(modifiers)
                .setMoves(moveList)
                .setStatus(null)
                .setEffect(null)
                .setChallenger(false)
                .setChallenged(false)
                .setDueling(false)
                .setOpponentId("")
                .build();

        File wandererFile = new File(String.format(wandererFilePath + "/%s.json", userId));
        ObjectMapper mapper = new ObjectMapper();
        String message;
        if (wandererFile.createNewFile()) {
            mapper.writeValue(wandererFile, wanderer);
            message = String.format("%s has been successfully registered!",
                    wanderer.getUsername());
        } else {
            message = "An error has occurred during registration";
        }
        return message;
    }

    public String levelup(String userId, String stats) {
        Wanderer wanderer = getWanderer(userId);
        StatBlock wandererStats = wanderer.getOriginalStats();
        Random rand = new Random();
        int count = 0;
        List<String> statsList = Arrays.asList(stats.split(","));
        if (statsList.size() == 5 && wanderer.getExperience() >= (100 * wanderer.getLevel())) {
            for (String stat : statsList) {
                switch (stat.toUpperCase()) {
                    case "HP":
                        wandererStats.setMaxHealth(wandererStats.getMaxHealth() +
                                rand.nextInt(Math.round(wandererStats.getMaxHealth()/(float)2)) + 1);
                        count = count + 1;
                        break;
                    case "STR":
                        wandererStats.setStrength(wandererStats.getStrength() +
                                rand.nextInt(Math.round(wandererStats.getStrength()/(float)2)) + 1);
                        count = count + 1;
                        break;
                    case "MAG":
                        wandererStats.setMagic(wandererStats.getMagic() +
                                rand.nextInt(Math.round(wandererStats.getMagic()/(float)2)) + 1);
                        count = count + 1;
                        break;
                    case "DEF":
                        wandererStats.setDefense(wandererStats.getDefense() +
                                rand.nextInt(Math.round(wandererStats.getDefense()/(float)2)) + 1);
                        count = count + 1;
                        break;
                    case "MDF":
                        wandererStats.setMagicDefense(wandererStats.getMagicDefense() +
                                rand.nextInt(Math.round(wandererStats.getMagicDefense()/(float)2)) + 1);
                        count = count + 1;
                        break;
                    case "ACC":
                        wandererStats.setAccuracy(wandererStats.getAccuracy() +
                                rand.nextInt(Math.round(wandererStats.getAccuracy()/(float)2)) + 1);
                        count = count + 1;
                        break;
                    case "EVA":
                        wandererStats.setEvasion(wandererStats.getEvasion() +
                                rand.nextInt(Math.round(wandererStats.getEvasion()/(float)2)) + 1);
                        count = count + 1;
                        break;
                    case "SPD":
                        wandererStats.setSpeed(wandererStats.getSpeed() +
                                rand.nextInt(Math.round(wandererStats.getSpeed()/(float)2)) + 1);
                        count = count + 1;
                        break;
                }
            }
            if (count == 5) {
                wanderer.setOriginalStats(wandererStats);
                wanderer.setExperience(wanderer.getExperience() - (100 * wanderer.getLevel()));
                wanderer.setLevel(wanderer.getLevel() + 1);
                List<Integer> usableMoves = moveHandler.getUsableMoves(wanderer);
                wanderer.setLevelupMove((Integer) usableMoves.toArray()[rand.nextInt(usableMoves.size())]);
                Move move = moveHandler.getMove(wanderer.getLevelupMove());
                resetStats(wanderer);
                updateWanderer(wanderer);
                List<Integer> temp = wanderer.getMoves();
                temp.set(0, move.getId());
                wanderer.setMoves(temp);
                return String.format("%s has leveled up! You choose can to replace a move you know with (%s) " +
                                "by using \"~exchange <moveNumber>\" in a whisper!",
                        wanderer.getUsername(),
                        sendMoveInfo(wanderer.getUserId(), 1).substring(8));
            } else {
                return String.format("%s, one of the stats you supplied were misspelled. Please check your stat spelling " +
                                "and resubmit your levelup.",
                        wanderer.getUsername());
            }
        } else if (wanderer.getExperience() >= (100 * wanderer.getLevel())) {
            return String.format("Uhhh...%s, you don't have enough experience to level up. " +
                            "The fastest way to earn experience is to duel other wanderers!",
                    wanderer.getUsername());
        } else {
            return String.format("%s, you didn't properly input five stat values to level up. i.e.: ~levelup hp,mag,mag,acc,spd",
                    wanderer.getUsername());
        }
    }

    public boolean wandererIsRegistered(String userId) {
        return new File(String.format(wandererFilePath + "/%s.json", userId)).exists();
    }

    public Wanderer getWanderer(String userId) {
        File wandererFile = new File(String.format(wandererFilePath + "/%s.json", userId));
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(wandererFile, Wanderer.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void updateWanderer(Wanderer wanderer) {
        File wandererFile = new File(String.format(wandererFilePath + "/%s.json", wanderer.getUserId()));
        if (wanderer.getAdjustedStats().getCurrentHealth() < 1) {
            wandererFile.delete();
        } else {
            ObjectMapper mapper = new ObjectMapper();
            try {
                mapper.writeValue(wandererFile, wanderer);
            } catch (Exception ignored) {
            }
        }
    }

    public String exchangeMove(String userId, Integer moveIndex) {
        Wanderer wanderer = getWanderer(userId);
        if (wanderer.getLevelupMove() == 0) {
            return String.format("%s, you don't have a move to exchange NotLikeThis",
                    wanderer.getUsername());
        } else {
            List<Integer> moves = wanderer.getMoves();
            moves.set(moveIndex - 1, wanderer.getLevelupMove());
            wanderer.setMoves(moves);
            wanderer.setLevelupMove(0);
            updateWanderer(wanderer);
            return String.format("Congratulations on your new move %s!",
                    wanderer.getUsername());
        }
    }

    public String sendMoveList(String userId) {
        Wanderer wanderer = getWanderer(userId);
        StringBuilder message = new StringBuilder(String.format("%s knows: ", wanderer.getUsername()));
        for (int i = 0; i < wanderer.getMoves().size(); i++) {
            message.append(String.format("%s", moveHandler.getMove(wanderer.getMoves().get(i)).getMoveName()));
            if (i != wanderer.getMoves().size() - 1) {
                message.append(", ");
            }
        }
        return message.toString();
    }

    public String sendMoveInfo(String userId, Integer moveIndex) {
        Wanderer wanderer = getWanderer(userId);
        Move move = moveHandler.getMove(wanderer.getMoves().get(moveIndex - 1));
        StringBuilder moveInfo = new StringBuilder();
        moveInfo.append(String.format("Info on %s - Type: %s",
                move.getMoveName(),
                move.getType()));
        if (move.getDamage() != null) {
            moveInfo.append(String.format(", Power: %d",
                    move.getDamage()));
        } else {
            moveInfo.append(", Power: -");
        }
        if (move.getAccuracy() != null) {
            moveInfo.append(String.format(", Accuracy: %d",
                    move.getAccuracy()));
        } else {
            moveInfo.append(", Accuracy: -");
        }
        if (move.getDamage() != null) {
            moveInfo.append(String.format(", Crit Chance: %d",
                    move.getCritChance())).append("%");
        }
        if (move.isPriority()) {
            moveInfo.append(", Has Priority");
        }
        if (move.getStatus() != null) {
            for (Status status : Status.values()) {
                if (move.getStatus().get(status.label) != null) {
                    moveInfo.append(String.format(", Chance to %s",
                            status.label));
                }
            }
        }
        if (move.getEffect() != null) {
            for (Effect effect : move.getEffect()) {
                if (effect.getTarget().equals("Opponent")) {
                    if (effect.getAmount() > 0) {
                        moveInfo.append(String.format(", Raises %s's %s for %d Turns",
                                effect.getTarget(),
                                effect.getStat(),
                                effect.getDuration()));
                    } else {
                        moveInfo.append(String.format(", Lowers %s's %s for %d Turns",
                                effect.getTarget(),
                                effect.getStat(),
                                effect.getDuration()));
                    }
                }
                if (effect.getTarget().equals("Self")) {
                    if (effect.getAmount() > 0) {
                        moveInfo.append(String.format(", Raises own %s for %d Turns",
                                effect.getStat(),
                                effect.getDuration()));
                    } else {
                        moveInfo.append(String.format(", Lowers own %s for %d Turns",
                                effect.getStat(),
                                effect.getDuration()));
                    }
                }
            }
        }
        return moveInfo.toString();
    }

    public String sendStats(String userId) {
        Wanderer wanderer = getWanderer(userId);
        if (wanderer != null) {
            StatBlock stats = wanderer.getAdjustedStats();
            return String.format("%s's stats - Level:%d, XP:%d, HP:%d/%d, STR:%d, MAG:%d, DEF:%d, MDF:%d, EVA:%d, ACC:%d, SPD:%d",
                    wanderer.getUsername(),
                    wanderer.getLevel(),
                    wanderer.getExperience(),
                    stats.getCurrentHealth(),
                    stats.getMaxHealth(),
                    stats.getStrength(),
                    stats.getMagic(),
                    stats.getDefense(),
                    stats.getMagicDefense(),
                    stats.getAccuracy(),
                    stats.getEvasion(),
                    stats.getSpeed());
        } else {
            return "You have no stats to reference. Please use \"~register\" to create your Wanderer!";
        }
    }

    public void resetStats(Wanderer wanderer) {
        StatBlock originalStats = wanderer.getOriginalStats();
        StatBlock adjustedStats = wanderer.getAdjustedStats();
        adjustedStats.setMaxHealth(originalStats.getMaxHealth());
        adjustedStats.setStrength(originalStats.getStrength());
        adjustedStats.setMagic(originalStats.getMagic());
        adjustedStats.setDefense(originalStats.getDefense());
        adjustedStats.setMagicDefense(originalStats.getMagicDefense());
        adjustedStats.setAccuracy(originalStats.getAccuracy());
        adjustedStats.setEvasion(originalStats.getEvasion());
        adjustedStats.setSpeed(originalStats.getSpeed());
    }

    private Integer statMod(Integer stat) {
        Random rand = new Random();
        if (rand.nextInt()%2 == 0) {
            return stat + rand.nextInt(stat/2);
        } else {
            return stat - rand.nextInt(stat/2);
        }
    }

    public void calculateExperience(Wanderer winner, Wanderer loser) {
        Integer expGained = (loser.getLevel()*25) +
                loser.getOriginalStats().getMaxHealth() +
                loser.getOriginalStats().getStrength() +
                loser.getOriginalStats().getMagic() +
                loser.getOriginalStats().getDefense() +
                loser.getOriginalStats().getMagicDefense() +
                loser.getOriginalStats().getAccuracy() +
                loser.getOriginalStats().getEvasion() +
                loser.getOriginalStats().getSpeed();
        winner.setExperience(winner.getExperience() + expGained);
    }

    public String giveExperience(String admin, String wandererId, String amount) {
        Bot bot = new Bot();
        if (!bot.userIsPresent(admin)) {
            Wanderer wanderer = getWanderer(wandererId);
            if (wanderer != null) {
                wanderer.setExperience(wanderer.getExperience() + Integer.parseInt(amount));
                updateWanderer(wanderer);
                return String.format("%s has successfully been awarded %sXP!",
                        wanderer.getUsername(),
                        amount);
            } else {
                return "That user does not have a wanderer yet.";
            }
        } else {
            return "You do not have the necessary permission to use this command.";
        }
    }
}
