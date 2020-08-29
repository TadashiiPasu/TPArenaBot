package com.github.tadashiipasu.tparenabot.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tadashiipasu.tparenabot.Bot;
import com.github.tadashiipasu.tparenabot.pojos.*;
import com.github.tadashiipasu.tparenabot.pojos.builders.FightBuilder;
import com.github.tadashiipasu.tparenabot.pojos.moves.Effect;
import com.github.tadashiipasu.tparenabot.pojos.moves.Move;
import com.github.tadashiipasu.tparenabot.pojos.moves.Status;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FightHandler {
    private final String fightFilePath = "./fights";

    private final WandererHandler wandererHandler = new WandererHandler();
    private final MoveHandler moveHandler = new MoveHandler();

    public String fightInvite(String challengerId, String[] commandStrip) {
        Bot bot = new Bot();
        String target;
        if (commandStrip.length < 2) {
            return "To duel someone, you need to add their name! i.e: ~duel TadashiiPasu";
        }

        target = convertForTwitch(commandStrip[1]);
        if (bot.userIsPresent(target)) {
            String targetId = bot.getUserId(target);
            Wanderer challengerWanderer = wandererHandler.getWanderer(challengerId);
            Wanderer challengedWanderer = wandererHandler.getWanderer(targetId);
            if (challengedWanderer == null) {
                return String.format("%s has yet to register a Wanderer to duel with. Please use \"~register\" to do so!",
                        target);
            }
            if ((!challengedWanderer.getChallenged() && !challengedWanderer.getDueling()) &&
                    !challengerWanderer.getChallenged() && !challengerWanderer.getDueling()) {
                challengerWanderer.setChallenger(true);
                challengerWanderer.setChallenged(true);
                challengerWanderer.setOpponentId(targetId);
                wandererHandler.updateWanderer(challengerWanderer);
                challengedWanderer.setChallenged(true);
                challengedWanderer.setOpponentId(challengerId);
                wandererHandler.updateWanderer(challengedWanderer);
                try {
                    createFight(challengerId, challengedWanderer.getUserId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return String.format("%s, %s has challenged you to a duel! Do you accept? (~accept/~reject)", target,
                        challengerWanderer.getUsername());
            } else if (challengerWanderer.getChallenged()) {
                return String.format("Shame on you %s! You've already challenged %s, you know! DansGame",
                        challengerWanderer.getUsername(), bot.getUsername(challengerWanderer.getOpponentId()));
            } else if (challengerWanderer.getDueling()) {
                return String.format("Focus %s! You've already locked in mortal combat with %s, you know! DansGame",
                        challengerWanderer.getUsername(), bot.getUsername(challengerWanderer.getOpponentId()));
            } else {
                return String.format("Get in line %s! It looks like %s has already been challenged by %s!",
                        challengerWanderer.getUsername(), challengedWanderer.getUsername(),
                        bot.getUsername(challengedWanderer.getOpponentId()));
            }
        } else {
            return String.format("%s is not currently here BibleThump", target);
        }
    }

    public String fightStartup(String challengedId) {
        Wanderer challengedWanderer = wandererHandler.getWanderer(challengedId);
        Wanderer challengerWanderer = wandererHandler.getWanderer(challengedWanderer.getOpponentId());
        String fightId = challengedWanderer.getOpponentId() + challengedWanderer.getUserId();
        Fight fight = getFight(fightId);
        if (fight != null) {
            challengerWanderer.setDueling(true);
            challengerWanderer.setChallenged(false);
            challengedWanderer.setDueling(true);
            challengedWanderer.setChallenged(false);
            wandererHandler.updateWanderer(challengerWanderer);
            wandererHandler.updateWanderer(challengedWanderer);
            return "A match between two fearsome Wanderers is about to take place! PogChamp";
        }
        return "An error has occurred during confirmation";
    }

    public String fightCancel(String challengedId) {
        Wanderer challengedWanderer = wandererHandler.getWanderer(challengedId);
        Wanderer challengerWanderer = wandererHandler.getWanderer(challengedWanderer.getOpponentId());
        String fightId = challengedWanderer.getOpponentId() + challengedId;
        File fightFile = new File(String.format(fightFilePath + "/%s.json", fightId));
        if (fightFile.delete()) {
            challengerWanderer.setChallenger(false);
            challengerWanderer.setChallenged(false);
            challengerWanderer.setOpponentId("");
            challengedWanderer.setChallenged(false);
            challengedWanderer.setOpponentId("");
            wandererHandler.updateWanderer(challengerWanderer);
            wandererHandler.updateWanderer(challengedWanderer);
            return "The duel has been refused. Perhaps some other Viewer is up to the challenge? KappaHD";
        } else if (!fightFile.exists()) {
            return String.format("%s, you cannot reject a duel on someone else's behalf.", challengedWanderer.getUsername());
        } else {
            return "An error occurred during cancellation.";
        }
    }

    public String updateFight(String wandererId, Integer moveIndex) {
        String fightId;
        Wanderer wanderer = wandererHandler.getWanderer(wandererId);
        if (wanderer.isChallenger()) {
            fightId = wanderer.getUserId() + wanderer.getOpponentId();
        } else {
            fightId = wanderer.getOpponentId() + wanderer.getUserId();
        }
        Fight fight = getFight(fightId);
        if (fight != null) {
            File fightFile = new File(String.format(fightFilePath + "/%s.json", fightId));
            if (moveIndex != -1) {
                if (wanderer.isChallenger()) {
                    fight.setWandererOneMove(moveIndex);
                } else {
                    fight.setWandererTwoMove(moveIndex);
                }

                ObjectMapper mapper = new ObjectMapper();
                try {
                    mapper.writeValue(fightFile, fight);
                } catch (Exception ignored) {
                }

                if (fight.getWandererOneMove() != 0 && fight.getWandererTwoMove() != 0) {
                    return fightTurn(fight);
                }
            } else {
                fightFile.delete();
            }
        }
        return null;
    }

    public String fightTurn(Fight fight) {
        Wanderer wandererOne = wandererHandler.getWanderer(fight.getWandererOneId());
        Wanderer wandererTwo = wandererHandler.getWanderer(fight.getWandererTwoId());
        Move moveOne = moveHandler.getMove(wandererOne.getMoves().get(fight.wandererOneMove-1));
        Move moveTwo = moveHandler.getMove(wandererTwo.getMoves().get(fight.wandererTwoMove-1));
        StringBuilder combatLog = new StringBuilder();
        if (wandererOneMovesFirst(wandererOne, wandererTwo, moveOne, moveTwo)) {
            combatLog.append(determineHit(wandererOne, moveOne, wandererTwo));
            if (wandererTwo.getAdjustedStats().getCurrentHealth() > 0) {
                combatLog.append(determineHit(wandererTwo, moveTwo, wandererOne));
            }
        } else {
            combatLog.append(determineHit(wandererTwo, moveTwo, wandererOne));
            if (wandererOne.getAdjustedStats().getCurrentHealth() > 0) {
                combatLog.append(determineHit(wandererOne, moveOne, wandererTwo));
            }
        }

        // Effect/Status occurs here
        combatLog.append(postCombat(wandererOne));
        combatLog.append(postCombat(wandererTwo));

        combatLog.append(determineVictor(fight, wandererOne, wandererTwo));
        wandererHandler.updateWanderer(wandererOne);
        wandererHandler.updateWanderer(wandererTwo);

        return combatLog.toString();
    }

    private void createFight(String challengerId, String challengedId) throws IOException {
        String fightId = challengerId + challengedId;
        Fight fight = new FightBuilder()
                .setFightId(fightId)
                .setWandererOneId(challengerId)
                .setWandererTwoId(challengedId)
                .setFightStart(System.currentTimeMillis())
                .setWandererOneMove(0)
                .setWandererTwoMove(0)
                .build();
        File fightFile = new File(String.format(fightFilePath + "/%s.json",
                fightId));
        ObjectMapper mapper = new ObjectMapper();
        if (fightFile.createNewFile()) {
            mapper.writeValue(fightFile, fight);
        }
    }

    private Fight getFight(String fightId) {
        File fightFile = new File(String.format(fightFilePath + "/%s.json", fightId));
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(fightFile, Fight.class);
        } catch (Exception e) {
            return null;
        }
    }

    private boolean wandererOneMovesFirst(Wanderer wandererOne, Wanderer wandererTwo, Move moveOne, Move moveTwo) {
        Random rand = new Random();
        if (moveOne.isPriority() && moveTwo.isPriority()) {
            if (wandererOne.getAdjustedStats().getSpeed() > wandererTwo.getAdjustedStats().getSpeed()) {
                return true;
            } else if (wandererTwo.getAdjustedStats().getSpeed() > wandererOne.getAdjustedStats().getSpeed()) {
                return false;
            } else {
                return rand.nextInt() % 2 == 0;
            }
        } else if (moveOne.isPriority()) {
            return true;
        } else if (moveTwo.isPriority()) {
            return false;
        } else if (wandererOne.getAdjustedStats().getSpeed() > wandererTwo.getAdjustedStats().getSpeed()) {
            return true;
        } else if (wandererTwo.getAdjustedStats().getSpeed() > wandererOne.getAdjustedStats().getSpeed()) {
            return false;
        } else {
            return rand.nextInt() % 2 == 0;
        }
    }

    @SuppressWarnings("ConstantConditions")
    private String determineHit(Wanderer attacker, Move move, Wanderer defender) {
        StringBuilder combatLog = new StringBuilder();
        if (!checkParalysisFrozenSleep(attacker)) {
            float atkDefMod;
            Integer damage = null;
            int crit;
            Random rand = new Random();
            int hit = rand.nextInt(100) + 1;
            if (move.getCritChance() != null && rand.nextInt(100) + 1 < move.getCritChance()) {
                crit = 2;
            } else {
                crit = 1;
            }
            float mod = crit * ((float) (rand.nextInt(16) + 85) / 100);
            if (move.getAccuracy() == null ||
                    hit / ((float) attacker.getAdjustedStats().getAccuracy() / (float) defender.getAdjustedStats().getEvasion()) < move.getAccuracy()) {
                if (move.getDamage() != null) {
                    if (move.getType().equals("Physical")) {
                        atkDefMod = (float) attacker.getAdjustedStats().getStrength() / (float) defender.getAdjustedStats().getDefense();
                        //noinspection IntegerDivisionInFloatingPointContext
                        damage = Math.round((((((((2 * attacker.getLevel()) / 5) + 2) * move.getDamage()) * (atkDefMod)) / 50) + 2) * mod);
                        defender.getAdjustedStats().setCurrentHealth(defender.getAdjustedStats().getCurrentHealth() - damage);
                    } else if (move.getType().equals("Magical")) {
                        atkDefMod = (float) attacker.getAdjustedStats().getMagic() / (float) defender.getAdjustedStats().getMagicDefense();
                        //noinspection IntegerDivisionInFloatingPointContext
                        damage = Math.round((((((((2 * attacker.getLevel()) / 5) + 2) * move.getDamage()) * (atkDefMod)) / 50) + 2) * mod);
                        defender.getAdjustedStats().setCurrentHealth(defender.getAdjustedStats().getCurrentHealth() - damage);
                    }
                    if (crit == 2 && damage != null) {
                        combatLog.append(String.format("CRITICAL HIT! %s's %s dealt %d damage, reducing %s's HP to %d. ",
                                attacker.getUsername(),
                                move.getMoveName(),
                                damage,
                                defender.getUsername(),
                                defender.getAdjustedStats().getCurrentHealth()));
                    } else {
                        combatLog.append(String.format("%s's %s dealt %d damage, reducing %s's HP to %d. ",
                                attacker.getUsername(),
                                move.getMoveName(),
                                damage,
                                defender.getUsername(),
                                defender.getAdjustedStats().getCurrentHealth()));
                    }
                }
                if (move.getStatus() != null) {
                    if (defender.getStatus() == null && statusAfflicted(move.getStatus(), defender)) {
                        defender.setStatus(move.getStatus());
                        combatLog.append(String.format("D: %s has been afflicted with %s by %s's %s! ",
                                defender.getUsername(),
                                getStatusLabel(move.getStatus()),
                                attacker.getUsername(),
                                move.getMoveName()));
                    } else if (move.getDamage() == null){
                        combatLog.append(String.format("D: Oh no! %s's attack missed! ",
                                attacker.getUsername()));
                    }
                }
                if (move.getEffect() != null) {
                    for (Effect effect : move.getEffect()) {
                        List<Effect> currentEffects;
                        if (effect.getTarget().equals("Self")) {
                            if (attacker.getEffect() == null) {
                                currentEffects = new ArrayList<>();
                            } else {
                                currentEffects = attacker.getEffect();
                            }
                            combatLog.append(statAdjust(attacker, effect, move.getMoveName()));
                            currentEffects.add(effect);
                            attacker.setEffect(currentEffects);
                        } else {
                            if (defender.getEffect() == null) {
                                currentEffects = new ArrayList<>();
                            } else {
                                currentEffects = defender.getEffect();
                            }
                            combatLog.append(statAdjust(defender, effect, move.getMoveName()));
                            currentEffects.add(effect);
                            defender.setEffect(currentEffects);
                        }
                    }
                }
            } else {
                combatLog.append(String.format("D: Oh no! %s's attack missed! ",
                        attacker.getUsername()));
            }
        } else {
            String statusMessage = "";

            if (attacker.getStatus().containsKey("Paralysis")) {
                statusMessage = String.format("%s is paralyzed and can't move a muscle! ",
                        attacker.getUsername());
            } else if (attacker.getStatus().containsKey("Freeze")) {
                statusMessage = String.format("%s is frozen to the core and cannot move! ",
                        attacker.getUsername());
            } else if (attacker.getStatus().containsKey("Sleep")) {
                statusMessage = String.format("%s is fast asleep! ",
                        attacker.getUsername());
            }

            combatLog.append(statusMessage);
        }
        return combatLog.toString();
    }

    private String postCombat(Wanderer wanderer) {
        StringBuilder combatLog = new StringBuilder();
        if (wanderer.getEffect() != null) {
            List<Effect> currentEffects = new ArrayList<>(wanderer.getEffect());
            for (Effect effect : currentEffects) {
                wanderer.getEffect().remove(effect);
                if(!effect.getEffectName().equals("Buff")) {
                    if (effect.getAmount() != null) {
                        StatBlock damageApplied = wanderer.getAdjustedStats();
                        damageApplied.setCurrentHealth(damageApplied.getCurrentHealth() - effect.getAmount());
                        wanderer.setAdjustedStats(damageApplied);
                        combatLog.append(String.format("%s has been hurt by the %s for %d damage. ",
                                wanderer.getUsername(),
                                effect.getEffectName(),
                                effect.getAmount()));
                    }
                }
                effect.setDuration(effect.getDuration() - 1);
                if (effect.getDuration() == 0) {
                    if (effect.getEffectName().equals("Buff")) {
                        Effect endBuff = new Effect(effect.getEffectName(), effect.getStat(), effect.getAmount() * -1, effect.getDuration(), effect.getTarget());
                        statAdjust(wanderer, endBuff, null);
                    } else {
                        wanderer.setStatus(null);
                        combatLog.append(String.format("%s has recovered from their %s! ",
                                wanderer.getUsername(),
                                effect.getEffectName()));
                    }
                } else {
                    List<Effect> updatedEffects = wanderer.getEffect();
                    updatedEffects.add(effect);
                    wanderer.setEffect(updatedEffects);
                }
            }
        }
        return combatLog.toString();
    }

    private boolean checkParalysisFrozenSleep(Wanderer wandererOne) {
        Random rand = new Random();
        Map<String, Integer> status = wandererOne.getStatus();
        if (status != null) {
            if (status.containsKey("Paralysis")) {
                return rand.nextInt(100) + 1 < 25;
            } else if (status.containsKey("Freeze")) {
                return true;
            } else return status.containsKey("Sleep");
        }
        return false;
    }

    private boolean statusAfflicted(Map<String, Integer> status, Wanderer defender) {
        Random rand = new Random();
        int hit = rand.nextInt(100);
        for (Status affliction : Status.values()) {
            if (status.containsKey(affliction.label)) {
                if (hit < status.get(affliction.label)) {
                    Effect effect = new Effect(affliction.label, null, null, rand.nextInt(5)+1, "Opponent");
                    Effect bonusEffect = new Effect("Buff", null, null, effect.getDuration(), "Opponent");
                    switch (affliction.label) {
                        case "Burn":
                            effect.setAmount(Math.round((float) defender.getOriginalStats().getMaxHealth() / 8));
                            bonusEffect.setStat("Strength");
                            bonusEffect.setAmount(-2);
                            statAdjust(defender, bonusEffect, null);
                            break;
                        case "Poison":
                            effect.setAmount(Math.round((float) defender.getOriginalStats().getMaxHealth() / 8));
                            break;
                        case "Paralysis":
                            bonusEffect.setStat("Speed");
                            bonusEffect.setAmount(-2);
                            statAdjust(defender, bonusEffect, null);
                            break;
                    }
                    List<Effect> effects = defender.getEffect();
                    if (effects == null) {
                        effects = new ArrayList<>();
                    }
                    effects.add(effect);
                    if (bonusEffect.getStat() != null) {
                        effects.add(bonusEffect);
                    }
                    defender.setEffect(effects);
                    return true;
                }
            }
        }
        return false;
    }

    private String getStatusLabel(Map<String, Integer> status) {
        for (Status affliction : Status.values()) {
            if (status.get(affliction.label) != null && status.get(affliction.label) != 0) {
                return affliction.label;
            }
        }
        return "";
    }

    private String statAdjust(Wanderer afflicted, Effect effect, String moveName) {
        StatBlock originalStats = afflicted.getOriginalStats();
        StatBlock adjustedStats = afflicted.getAdjustedStats();
        ModBlock modifiers = afflicted.getModifiers();
        switch (effect.getStat()) {
            case "Strength":
                adjustedStats.setStrength(getModifiedStat(originalStats.getStrength(), modifiers.getStrength(), effect.getAmount()));
                modifiers.setStrength(updateModifier(modifiers.getStrength(), effect.getAmount()));
                afflicted.setAdjustedStats(adjustedStats);
                break;
            case "Magic":
                adjustedStats.setMagic(getModifiedStat(originalStats.getMagic(), modifiers.getMagic(), effect.getAmount()));
                modifiers.setMagic(updateModifier(modifiers.getMagic(), effect.getAmount()));
                afflicted.setAdjustedStats(adjustedStats);
                break;
            case "Defense":
                adjustedStats.setDefense(getModifiedStat(originalStats.getDefense(), modifiers.getDefense(), effect.getAmount()));
                modifiers.setDefense(updateModifier(modifiers.getDefense(), effect.getAmount()));
                afflicted.setAdjustedStats(adjustedStats);
                break;
            case "Magic Defense":
                adjustedStats.setMagicDefense(getModifiedStat(originalStats.getMagicDefense(), modifiers.getMagicDefense(), effect.getAmount()));
                modifiers.setMagicDefense(updateModifier(modifiers.getMagicDefense(), effect.getAmount()));
                afflicted.setAdjustedStats(adjustedStats);
                break;
            case "Evasion":
                adjustedStats.setEvasion(getModifiedStat(originalStats.getAccuracy(), modifiers.getAccuracy(), effect.getAmount()));
                modifiers.setEvasion(updateModifier(modifiers.getEvasion(), effect.getAmount()));
                afflicted.setAdjustedStats(adjustedStats);
                break;
            case "Accuracy":
                adjustedStats.setAccuracy(getModifiedStat(originalStats.getEvasion(), modifiers.getEvasion(), effect.getAmount()));
                modifiers.setAccuracy(updateModifier(modifiers.getAccuracy(), effect.getAmount()));
                afflicted.setAdjustedStats(adjustedStats);
                break;
            case "Speed":
                adjustedStats.setSpeed(getModifiedStat(originalStats.getSpeed(), modifiers.getSpeed(), effect.getAmount()));
                modifiers.setSpeed(updateModifier(modifiers.getSpeed(), effect.getAmount()));
                afflicted.setAdjustedStats(adjustedStats);
                break;
        }
        if (moveName != null) {
            if (effect.getAmount() < 0) {
                return String.format("%s's %s was reduced by %s! ",
                        afflicted.getUsername(),
                        effect.getStat(),
                        moveName);
            } else {
                return String.format("%s's %s was increased %s! ",
                        afflicted.getUsername(),
                        effect.getStat(),
                        moveName);
            }
        } else {
            return "";
        }
    }

    private int getModifiedStat(Integer originalStat, String modifiers, Integer effect) {
        int nom = Integer.parseInt(modifiers.split("/")[0]);
        int denom = Integer.parseInt(modifiers.split("/")[1]);
        float ratio;
        if (nom > 2) {
            nom = nom + effect;
            ratio = nom / (float) 2;
        } else if (denom > 2) {
            denom = (denom + (effect * -1));
            ratio = 2 / (float)denom;
        } else {
            if (effect > 0) {
                nom = nom + effect;
                ratio = nom / (float) 2;
            } else {
                denom = (denom + (effect * -1));
                ratio = 2 / (float)denom;
            }
        }
        return Math.round(originalStat * ratio);
    }

    private String updateModifier(String modifiers, Integer effect) {
        int nom = Integer.parseInt(modifiers.split("/")[0]);
        int denom = Integer.parseInt(modifiers.split("/")[1]);
        if (nom > 2) {
            nom = nom + effect;
            return String.format("%d/2", nom);
        } else if (denom > 2) {
            denom = (denom + (effect * -1));
            return String.format("2/%d", denom);
        } else {
            if (effect > 0) {
                nom = nom + effect;
                return String.format("%d/2", nom);
            } else {
                denom = (denom + (effect * -1));
                return String.format("2/%d", denom);
            }
        }
    }

    private String determineVictor(Fight fight, Wanderer wandererOne, Wanderer wandererTwo) {
        StringBuilder combatLog = new StringBuilder();
        if (wandererOne.getAdjustedStats().getCurrentHealth() < 1 && wandererTwo.getAdjustedStats().getCurrentHealth() < 1) {
            updateFight(fight.wandererOneId, -1);
            updateFight(fight.wandererTwoId, -1);
            combatLog.append(String.format("Through a cruel twist of fate, " +
                            "unyielding in their fervor, both %s and %s have perished BibleThump " +
                            "May they walk the Righteous Path once more",
                    wandererOne.getUsername(),
                    wandererTwo.getUsername()));
        } else if (wandererOne.getAdjustedStats().getCurrentHealth() < 1) {
            updateFight(fight.wandererOneId, -1);
            wandererHandler.calculateExperience(wandererTwo, wandererOne);
            wandererTwo.setDueling(false);
            wandererTwo.setOpponentId("");
            wandererTwo.setStatus(null);
            wandererTwo.setEffect(null);
            wandererHandler.resetStats(wandererTwo);
            combatLog.append(String.format("The victor is %s as they slay %s! " +
                            "They have triumphed over their foe with their sheer skill and cunning! " +
                            "May you continue along the Righteous Path",
                    wandererTwo.getUsername(),
                    wandererOne.getUsername()));
        } else if (wandererTwo.getAdjustedStats().getCurrentHealth() < 1) {
            updateFight(fight.wandererTwoId, -1);
            wandererHandler.calculateExperience(wandererOne, wandererTwo);
            wandererOne.setChallenger(false);
            wandererOne.setDueling(false);
            wandererOne.setOpponentId("");
            wandererOne.setStatus(null);
            wandererOne.setEffect(null);
            wandererHandler.resetStats(wandererOne);
            combatLog.append(String.format("The victor is %s as they slay %s! " +
                            "They have triumphed over their foe with their sheer skill and cunning! " +
                            "May you continue along the Righteous Path",
                    wandererOne.getUsername(),
                    wandererTwo.getUsername()));
        }
        updateFight(fight.wandererOneId, 0);
        updateFight(fight.wandererTwoId, 0);
        return combatLog.toString();
    }

    @NotNull
    private String convertForTwitch(String rawUser) {
        String target;
        if (rawUser.startsWith("@")) {
            target = rawUser.substring(1).toLowerCase();
        } else {
            target = rawUser.toLowerCase();
        }
        return target;
    }
}