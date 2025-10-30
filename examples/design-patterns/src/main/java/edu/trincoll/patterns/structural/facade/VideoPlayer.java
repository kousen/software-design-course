package edu.trincoll.patterns.structural.facade;

/**
 * Subsystem class - Complex video functionality.
 */
public class VideoPlayer {

    public void loadVideo(String filename) {
        System.out.println("Loading video: " + filename);
    }

    public void play() {
        System.out.println("Playing video");
    }

    public void stop() {
        System.out.println("Stopping video");
    }
}
