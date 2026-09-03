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

            // Mantém o áudio tocando continuamente
            clip.loop(Clip.LOOP_CONTINUOUSLY);

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

    public void definirVolume(int novoVolume) {

        if (novoVolume < 0) {
            novoVolume = 0;
        }

        if (novoVolume > 100) {
            novoVolume = 100;
        }

        this.volume = novoVolume;

        aplicarVolume();
    }

    public int getVolume() {

        return volume;
    }

    private void aplicarVolume() {

        if (clip == null) {
            return;
        }

        if (!clip.isControlSupported(
                FloatControl.Type.MASTER_GAIN)) {
            return;
        }

        FloatControl controle =
                (FloatControl) clip.getControl(
                        FloatControl.Type.MASTER_GAIN
                );

        float ganho;

        if (volume == 0) {

            // Silêncio
            ganho = controle.getMinimum();

        } else if (volume == 50) {

            // volume intermediário
             ganho = -6.0f;  

        } else {

            // Volume 100%
            ganho = 0.0f;
        }

        // Proteção contra os limites do dispositivo
        if (ganho < controle.getMinimum()) {
            ganho = controle.getMinimum();
        }

        if (ganho > controle.getMaximum()) {
            ganho = controle.getMaximum();
        }

        controle.setValue(ganho);
    }
}