package com.bowlinggame;

import com.sun.jdi.connect.Connector;

import java.sql.SQLOutput;
import java.util.Scanner;

public class BowlingApp {

    public static void main(String[] args) {

         Scanner scanner = new Scanner(System.in);
         BowlingGame game = new BowlingGame();

         displayWelcomeMessage();

        while(!game.isGameComplete())

        {
        Frame currentFrame = game.getCurrentFrame();
        int frameNumber = currentFrame.getFrameNumber();
        int rollNumber = currentFrame.getAllRolls().size() + 1;
        int maxPins = getMaxPins(currentFrame);

        System.out.print("Frame " + frameNumber + ", Wurf " + rollNumber + " - "
                + "Anzahl der umgeworfenen Pins (0 bis " + maxPins + "): ");
        int pins;
        try {
            pins = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Bitte geben Sie eine gültige Zahl ein.");
            scanner.next();
            continue;
        }

        if (pins < 0 || pins > maxPins) {
            System.out.println("Ungültige Pinanzahl. Bitte geben Sie eine Zahl zwischen 0 und " + maxPins + " ein.");
            continue;
        }

        try {
            game.roll(pins);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            break;
        }
     }
    scanner.close();

    displayScores(game);
}

    private static int getMaxPins(Frame frame) {
        if (frame instanceof FinalFrame) {
            return 10;
        } else {
            int rollsSize = frame.getAllRolls().size();
            if (rollsSize == 0) {
                return 10;
            } else if (rollsSize == 1) {
                int firstRollPins = frame.getAllRolls().get(0).getPinsKnockedDown();
                return 10 - firstRollPins;
            }
        }
        return 10;
    }

    private static void displayWelcomeMessage(){
        System.out.println("Willkommen zu unserem Bowling-Spiel!");
        System.out.println("Spielregeln:");
        System.out.println("- Das Spiel besteht aus 10 Frames.");
        System.out.println("- In jedem Frame haben Sie zwei Würfe, um alle 10 Pins umzuwerfen.");
        System.out.println("- Wenn Sie im ersten Wurf alle 10 Pins umwerfen, ist das ein Strike.");
        System.out.println("- Wenn Sie im zweiten Wurf die restlichen Pins umwerfen, ist das ein Spare.");
        System.out.println("- Im 10. Frame erhalten Sie bei einem Strike oder Spare einen zusätzlichen Wurf.");
        System.out.println("- Viel Spaß!\n");
    }


    private static void displayScores(BowlingGame game){
        System.out.println("\nPunktzahlen pro Frame:");
        int cumulativeScore=0;
        for (Frame frame : game.getFrames()){
            int frameScore=frame.getScore();
            cumulativeScore+=frameScore;
            System.out.println("Frame "+ frame.getFrameNumber() + ": "+frameScore + " (Gesamt: "+ cumulativeScore + ")");

    }
            System.out.println("Ihre  Gesamtpunktzahl: " + cumulativeScore);
}}










