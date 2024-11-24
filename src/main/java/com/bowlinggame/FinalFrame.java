package com.bowlinggame;

import java.util.ArrayList;
import java.util.List;

public class FinalFrame implements Frame{

    private List<Roll> rolls = new ArrayList<>();
    private int frameNumber = 10;




    @Override
    public void addRoll(Roll roll) {

        if (isComplete()) {
            throw new IllegalStateException("Der finale Frame ist abgeschlossen.");
        }
        if (roll.getPinsKnockedDown() < 0 || roll.getPinsKnockedDown() > 10) {
            throw new IllegalArgumentException("Ungültige Pinanzahl. Sie muss zwischen 0 und 10 liegen.");
        }
        if (rolls.size() == 1 && !isStrikeOrSpare() && rolls.get(0).getPinsKnockedDown() + roll.getPinsKnockedDown() > 10) {
            throw new IllegalArgumentException("Die Gesamtpinanzahl in einem Frame darf 10 nicht überschreiten.");
        }
        rolls.add(roll);
    }

    @Override
    public boolean isComplete() {
        if (rolls.size() < 2) {
            return false;
        }
        if (rolls.size() == 2 && !isStrikeOrSpare()) {
            return true;
        }
        return rolls.size() == 3;
    }

    @Override
    public int getScore() {
        return getRollsScore();
    }

    private int getRollsScore() {
        int score = 0;
        for (Roll roll : rolls) {
            score += roll.getPinsKnockedDown();
        }
        return score;
    }

    private boolean isStrikeOrSpare() {
        return rolls.get(0).getPinsKnockedDown() == 10 || (rolls.size() >= 2 && rolls.get(0).getPinsKnockedDown() + rolls.get(1).getPinsKnockedDown() == 10);
    }


    @Override
    public int getFrameNumber() {
        return frameNumber;
    }

    @Override
    public void setNextFrame(Frame frame) {

    }

    @Override
    public Frame getNextFrame() {
        return null;
    }

    @Override
    public List<Roll> getAllRolls() {
        return rolls;
    }

    public int getSecondRollPins() {
        if (rolls.size() >= 2) {
            return rolls.get(1).getPinsKnockedDown();
        }
        return 0;
    }
}
