public class Instrumento implements Runnable {

    private String nome;
    private String caminhoAudio;

    private boolean tocando;
    private boolean executando;

    private PlayerAudio player;

    public Instrumento(String nome, String caminhoAudio) {

        this.nome = nome;
        this.caminhoAudio = caminhoAudio;

        this.tocando = true;
        this.executando = true;

        this.player = new PlayerAudio();
    }

    @Override
    public void run() {

        player.tocar(caminhoAudio);

        synchronized (this) {

            while (executando) {

                try {

                    wait();

                } catch (InterruptedException e) {

                    executando = false;
                    Thread.currentThread().interrupt();
                }
            }
        }

        player.parar();

    }

    public synchronized void pausar() {

        if (!tocando || !executando) {
            return;
        }

        tocando = false;

        player.pausar();
        
    }

    public synchronized void continuar() {

        if (tocando || !executando) {
            return;
        }

        tocando = true;

        player.continuar();

        notifyAll();

    }

    public synchronized void parar() {

        if (!executando) {
            return;
        }

        executando = false;
        tocando = false;

        player.parar();

        notifyAll();
    }

    public synchronized void definirVolume(int volume) {

        if (volume < 0) {
            volume = 0;
        }

        if (volume > 100) {
            volume = 100;
        }

        player.definirVolume(volume);

    }

    public synchronized String getStatus() {

        if (!executando) {
            return "ENCERRADO";
        }

        if (tocando) {
            return "TOCANDO";
        }

        return "PAUSADO";
    }

    public synchronized int getVolume() {

        return player.getVolume();
        
    }
    public String getNome() {
    return nome;
}
}