package edu.trincoll.patterns.structural.facade;

/**
 * Subsystem class - Complex audio functionality.
 */
public class AudioPlayer {

    public void loadAudio(String filename) {
        System.out.println("Loading audio: " + filename);
    }

    public void play() {
        System.out.println("Playing audio");
    }

    public void stop() {
        System.out.println("Stopping audio");
    }
}
