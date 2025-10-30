package edu.trincoll.patterns.structural.facade;

/**
 * Subsystem class - Complex subtitle functionality.
 */
public class SubtitleRenderer {

    public void loadSubtitles(String filename) {
        System.out.println("Loading subtitles: " + filename);
    }

    public void show() {
        System.out.println("Showing subtitles");
    }

    public void hide() {
        System.out.println("Hiding subtitles");
    }
}
