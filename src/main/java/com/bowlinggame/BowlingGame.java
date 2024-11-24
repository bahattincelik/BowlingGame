package com.bowlinggame;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {


    private List<Frame> frames = new ArrayList<>();
    private int currentFrameIndex=0;

    public BowlingGame(){
        for (int i = 1; i <=9; i++) {
            frames.add(new StandartFrame(i));
        }
        frames.add(new FinalFrame());

        for (int i = 0; i < frames.size()-1; i++) {
            frames.get(i).setNextFrame(frames.get(i+1));
        }
    }
    public void roll(int pins){
        if(isGameComplete()) {
        throw new IllegalStateException("Das Spiel ist abgeschlossen. Keine weiteren Würfe möglich ");
        }
        Frame currentFrame= frames.get(currentFrameIndex);

        try{
            currentFrame.addRoll(new Roll(pins));
        }catch (IllegalArgumentException e){
            throw e;
        }catch (IllegalStateException e){
            currentFrameIndex++;
            roll(pins);
        }
        if(currentFrame.isComplete()&& currentFrameIndex<9){
            currentFrameIndex++;
        }
    }

    public int getTotalScore() {
        int totalScore = 0;
        for (Frame frame : frames) {
            totalScore += frame.getScore();
        }
        return totalScore;
    }
    public List<Frame> getFrames() {
        return frames;
    }
    public boolean isGameComplete() {
        return frames.get(9).isComplete();
    }
    public Frame getCurrentFrame() {
        return frames.get(currentFrameIndex);
    }
}
