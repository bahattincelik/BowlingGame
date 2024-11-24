package com.bowlinggame;

import java.util.List;



public interface Frame {
    void addRoll(Roll roll);
    boolean isComplete();
    int getScore();
    int getFrameNumber();
    void setNextFrame(Frame frame);
    Frame getNextFrame();
    List<Roll> getAllRolls();
}
