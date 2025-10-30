package edu.trincoll.patterns.structural.facade;

/**
 * Facade - Provides simple interface to complex subsystem.
 *
 * Facade Pattern Benefits:
 * - Simplifies complex subsystems
 * - Reduces dependencies on subsystem classes
 * - Good for organizing subsystems into layers
 * - Clients can still access subsystem directly if needed
 */
public class MediaPlayerFacade {
    private final AudioPlayer audio;
    private final VideoPlayer video;
    private final SubtitleRenderer subtitles;

    public MediaPlayerFacade() {
        this.audio = new AudioPlayer();
        this.video = new VideoPlayer();
        this.subtitles = new SubtitleRenderer();
    }

    /**
     * Simple interface - one method instead of many complex calls.
     */
    public void playMovie(String filename) {
        System.out.println("Starting movie: " + filename);
        audio.loadAudio(filename + ".mp3");
        video.loadVideo(filename + ".mp4");
        subtitles.loadSubtitles(filename + ".srt");
        audio.play();
        video.play();
        subtitles.show();
    }

    /**
     * Another simple interface for stopping.
     */
    public void stopMovie() {
        System.out.println("Stopping movie");
        audio.stop();
        video.stop();
        subtitles.hide();
    }
}
