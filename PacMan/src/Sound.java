import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.ArrayList;

/**
 * Singleton class for playing sound effects.
 * Original sounds are taken from https://freesound.org
 */

public class Sound {
    protected static final Sound INSTANCE = new Sound();
    private String[] sounds = {"eat", "die", "kill", "gameover", "mazefinished", "candy"};
    private final String path = "PacMan/src/resources/sounds/";
    private ArrayList<File> soundFiles = new ArrayList<>();
    private ArrayList<Clip> soundClips = new ArrayList<>();

    /**
     * Singleton, private constructor.
     * Reads the sound files and stores the audio clips in an array
     */
    private Sound() {
        try {
            int i = 0;
            for (String sound : sounds) {
                soundFiles.add(new File(path + sound + ".wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(soundFiles.get(i)));
                soundClips.add(clip);
                i++;
            }
        } catch (Exception e) { System.out.println("Error reading sound files.."); }

    }

    /**
     * Plays a sound
     * @param s name of soundeffect
     */
    protected void play(String s) {
        int soundIndex = 0;

        switch (s) {
            case "eat":
                soundIndex = 0;
                break;

            case "die":
                soundIndex = 1;
                break;

            case "kill":
                soundIndex = 2;
                break;

            case "gameover":
                soundIndex = 3;
                break;

            case "mazefinished":
                soundIndex = 4;
                break;

            case "candy":
                soundIndex = 5;
                break;

        }

        if (soundIndex >= 0 && soundIndex <= soundClips.size()) {
            soundClips.get(soundIndex).setFramePosition(soundIndex);
            soundClips.get(soundIndex).start();
        }
    }
}
