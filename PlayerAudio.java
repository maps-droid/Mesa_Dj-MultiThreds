import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class PlayerAudio {

    private static final long INICIO_SINCRONIA = System.nanoTime();

    private Clip clip;
    private int volume = 100;

    public synchronized void tocar(String caminho) {

        try {

            File arquivo = new File(caminho);

            AudioInputStream audio =
                    AudioSystem.getAudioInputStream(arquivo);

            clip = AudioSystem.getClip();

            clip.open(audio);

            aplicarVolume();

            sincronizarPosicao();
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {

            System.out.println("Erro ao reproduzir o audio.");
            e.printStackTrace();
        }
    }

    public synchronized void pausar() {

        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public synchronized void continuar() {

        if (clip != null && !clip.isRunning()) {
            sincronizarPosicao();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public synchronized void parar() {

        if (clip != null) {

            clip.stop();
            clip.close();
        }
    }

    private void sincronizarPosicao() {

        if (clip == null || clip.getFrameLength() <= 0) {
            return;
        }

        long tempoDecorrido = System.nanoTime() - INICIO_SINCRONIA;
        double framesDecorridos = tempoDecorrido
                / 1_000_000_000.0
                * clip.getFormat().getFrameRate();

        long frame = (long) framesDecorridos % clip.getFrameLength();
        clip.setFramePosition((int) frame);
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