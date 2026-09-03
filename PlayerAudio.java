import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class PlayerAudio {

    private Clip clip;

    private int volume = 100;

    public void tocar(String caminho) {

        try {

            File arquivo = new File(caminho);

            AudioInputStream audio =
                    AudioSystem.getAudioInputStream(arquivo);

            clip = AudioSystem.getClip();

            clip.open(audio);

            aplicarVolume();

            clip.loop(Clip.LOOP_CONTINUOUSLY);

            clip.start();

        } catch (Exception e) {

            System.out.println("Erro ao reproduzir o audio.");
            e.printStackTrace();
        }
    }

    public void pausar() {

        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void continuar() {

        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }

    public void parar() {

        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    public void definirVolume(int volume) {

        if (volume < 0) {
            volume = 0;
        }

        if (volume > 100) {
            volume = 100;
        }

        this.volume = volume;

        aplicarVolume();
    }

    public int getVolume() {

        return volume;
    }

    private void aplicarVolume() {

        if (clip == null) {
            return;
        }

        if (!clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            return;
        }

        FloatControl controle =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        if (volume == 0) {

            controle.setValue(controle.getMinimum());

            return;
        }

        float minimo = controle.getMinimum();
        float maximo = controle.getMaximum();

        float ganho = (float) (
                minimo +
                
                (maximo - minimo) * (volume / 100.0)
        );

        controle.setValue(ganho);
    }
}