package com.github.tadashiipasu.tparenabot.features;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tadashiipasu.tparenabot.pojos.Wanderer;
import com.github.tadashiipasu.tparenabot.pojos.moves.Move;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoveHandler {

    private final String movesFilePath = "./ArenaBot/src/main/java/com/github/tadashiipasu/tparenabot/pojos/moves/Moves";

    public List<Integer> selectMove(@Nullable Wanderer wanderer, Integer amount) {
        Random rand = new Random();
        List<Integer> selectedMoves = new ArrayList<>();
        List<Integer> usableMoves = getUsableMoves(wanderer);
        for (int i = 0; i < amount; i++) {
            int randomIndex = rand.nextInt(usableMoves.size());
            if (!selectedMoves.contains(usableMoves.get(randomIndex))) {
                selectedMoves.add(usableMoves.get(randomIndex));
            }
        }
        return selectedMoves;
    }

    public List<Integer> getUsableMoves(@Nullable Wanderer wanderer) {
        List<Integer> usableMoves = new ArrayList<>();
        File movesFile = new File(movesFilePath);
        BufferedReader reader;
        try {
            ObjectMapper mapper = new ObjectMapper();
            reader = new BufferedReader(new FileReader(movesFile));
            String line = reader.readLine();
            while (line != null) {
                Move move = mapper.readValue(line, Move.class);
                if(wanderer != null) {
                    if (checkRequirements(wanderer, move)) {
                        usableMoves.add(move.getId());
                    }
                } else {
                    if (move.getLevel() == 1) {
                        usableMoves.add(move.getId());
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usableMoves;
    }

    public Move getMove(Integer moveId) {
        File movesFile = new File(movesFilePath);
        BufferedReader reader;
        try {
            ObjectMapper mapper = new ObjectMapper();
            reader = new BufferedReader(new FileReader(movesFile));
            String line = reader.readLine();
            while (line != null) {
                Move move = mapper.readValue(line, Move.class);
                if (move.getId().equals(moveId)) {
                    return move;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean checkRequirements(Wanderer wanderer, Move move) {
        boolean canLearn = false;
        if (wanderer.getLevel() >= move.getLevel() &&
                wanderer.getStats().getMaxHealth() >= move.getRequiredStats().getMaxHealth() &&
                wanderer.getStats().getStrength() >= move.getRequiredStats().getStrength() &&
                wanderer.getStats().getMagic() >= move.getRequiredStats().getMagic() &&
                wanderer.getStats().getDefense() >= move.getRequiredStats().getDefense() &&
                wanderer.getStats().getMagicDefense() >= move.getRequiredStats().getMagicDefense() &&
                wanderer.getStats().getAccuracy() >= move.getRequiredStats().getAccuracy() &&
                wanderer.getStats().getEvasion() >= move.getRequiredStats().getEvasion() &&
                wanderer.getStats().getSpeed() >= move.getRequiredStats().getSpeed()
        ) {
            canLearn = true;
        }
        return canLearn;
    }
}
