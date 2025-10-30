package edu.trincoll.patterns.structural.facade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Facade Pattern Tests")
class FacadeTest {

    @Test
    @DisplayName("Facade simplifies playing a movie")
    void facadeSimpliesPlayingMovie() {
        MediaPlayerFacade player = new MediaPlayerFacade();

        // One simple call instead of many
        player.playMovie("inception");

        // Behind the scenes: loads audio, video, subtitles and plays all
    }

    @Test
    @DisplayName("Facade simplifies stopping a movie")
    void facadeSimpliesStoppingMovie() {
        MediaPlayerFacade player = new MediaPlayerFacade();

        player.playMovie("inception");
        player.stopMovie();

        // Behind the scenes: stops audio, video, and hides subtitles
    }

    @Test
    @DisplayName("Facade coordinates all subsystems")
    void facadeCoordinatesAllSubsystems() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            MediaPlayerFacade player = new MediaPlayerFacade();
            player.playMovie("test");

            String output = outputStream.toString();

            // Verify all subsystems were invoked
            assertThat(output).contains("Loading audio");
            assertThat(output).contains("Loading video");
            assertThat(output).contains("Loading subtitles");
            assertThat(output).contains("Playing audio");
            assertThat(output).contains("Playing video");
            assertThat(output).contains("Showing subtitles");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("Individual subsystems can still be used directly")
    void subsystemsCanBeUsedDirectly() {
        AudioPlayer audio = new AudioPlayer();
        VideoPlayer video = new VideoPlayer();

        // Can use subsystems directly if needed
        audio.loadAudio("song.mp3");
        audio.play();

        video.loadVideo("movie.mp4");
        video.play();
    }
}
