package com.bowlinggame;


//Repräsentiert einen Standard-Frame im Bowling-Spiel

import java.util.ArrayList;
import java.util.List;

public class StandartFrame implements Frame{

    protected List<Roll> rolls=new ArrayList<>();
    protected int frameNumber;
    protected Frame nextFrame;

    public StandartFrame(int frameNumber) {
        this.frameNumber=frameNumber;
    }

    @Override
    public void addRoll(Roll roll){
        if(isComplete()) {
            throw new IllegalStateException("Frame ist bereits abgschlossen");
        }
        if(roll.getPinsKnockedDown()<0|| roll.getPinsKnockedDown()>10){
            throw new IllegalArgumentException("Ungültige Pinanzahl. Sie muss zwischen 0 und 10 liegen");
        }
        if(rolls.size()==1 && rolls.get(0).getPinsKnockedDown()+roll.getPinsKnockedDown()>10){
            throw new IllegalArgumentException("Die Gesamtpinanzahl in einem Frame darf 10 nich überschreiten");
        }

        rolls.add(roll);
    }

    @Override
    public boolean isComplete(){
        return isStrike() || rolls.size()==2;
    }

    protected boolean isStrike(){
        return rolls.size()>0 && rolls.get(0).getPinsKnockedDown()==10;
    }

    @Override
    public int getScore() {
        int score = getRollsScore();
        score += getBonus();
        return score;
    }

    protected int getRollsScore() {
        int score = 0;
        for (Roll roll : rolls) {
            score += roll.getPinsKnockedDown();
        }
        return score;
    }


    protected int getStrikeBonus() {
        if (nextFrame != null) {
            List<Roll> nextRolls = nextFrame.getAllRolls();
            if (nextRolls.size() >= 2) {
                return nextRolls.get(0).getPinsKnockedDown() + nextRolls.get(1).getPinsKnockedDown();
            } else if (nextRolls.size() == 1) {
                if (nextFrame.getNextFrame() != null) {
                    List<Roll> nextNextRolls = nextFrame.getNextFrame().getAllRolls();
                    if (!nextNextRolls.isEmpty()) {
                        return nextRolls.get(0).getPinsKnockedDown() + nextNextRolls.get(0).getPinsKnockedDown();
                    }
                } else {
                    return nextRolls.get(0).getPinsKnockedDown();
                }
            }
        }
        return 0;
    }


    protected boolean isSpare() {
        return rolls.size() == 2 && getRollsScore() == 10;
    }

    protected int getSpareBonus() {
        if (nextFrame != null && !nextFrame.getAllRolls().isEmpty()) {
            return nextFrame.getAllRolls().get(0).getPinsKnockedDown();
        }
        return 0;
    }


    protected int getBonus() {
        int bonus = 0;
        if (isStrike()) {
            bonus += getStrikeBonus();
        } else if (isSpare()) {
            bonus += getSpareBonus();
        }
        return bonus;
    }




    @Override
    public int getFrameNumber() {
        return frameNumber;
    }

    @Override
    public void setNextFrame(Frame nextFrame) {
        this.nextFrame = nextFrame;
    }

    @Override
    public Frame getNextFrame() {
        return nextFrame;
    }

    @Override
    public List<Roll> getAllRolls() {
        return rolls;
    }
}
