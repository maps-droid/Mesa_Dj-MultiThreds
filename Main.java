import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

    Scanner entrada = new Scanner(System.in);

    // =====================================================
    // CRIA AS CINCO FAIXAS
    // =====================================================

    Instrumento bateria = new Instrumento(
            "Bateria",
            "musicas/bateria.wav"
    );

    Instrumento baixo = new Instrumento(
            "Baixo",
            "musicas/baixo.wav"
    );

    Instrumento guitarra = new Instrumento(
            "Guitarra",
            "musicas/guitarra.wav"
    );

    Instrumento ostinato = new Instrumento(
            "Ostinato",
            "musicas/ostinato.wav"
    );

    Instrumento vocal = new Instrumento(
            "Vocal",
            "musicas/vocal.wav"
    );

    // =====================================================
    // CRIA AS THREADS DOS INSTRUMENTOS
    // =====================================================

    Thread threadBateria = new Thread(bateria, "Thread-Bateria");
    Thread threadBaixo = new Thread(baixo, "Thread-Baixo");
    Thread threadGuitarra = new Thread(guitarra, "Thread-Guitarra");
    Thread threadOstinato = new Thread(ostinato, "Thread-Ostinato");
    Thread threadVocal = new Thread(vocal, "Thread-Vocal");

    // =====================================================
    // INICIA AS THREADS
    // =====================================================

    threadBateria.start();
    threadBaixo.start();
    threadGuitarra.start();
    threadOstinato.start();
    threadVocal.start();

    // =====================================================
    // CRIA A THREAD DO MONITOR
    // =====================================================

    Monitor monitor = new Monitor(
            bateria,
            baixo,
            guitarra,
            ostinato,
            vocal
    );

    Thread threadMonitor = new Thread(monitor, "Thread-Monitor");

    threadMonitor.start();

    // =====================================================
    // RECEBE OS COMANDOS
    // =====================================================

    while (true) {

        System.out.print("> ");

        String comando = entrada.nextLine()
                .toLowerCase()
                .trim();

        switch (comando) {

            // =================================================
            // BATERIA
            // =================================================

            case "pause bateria":
                bateria.pausar();
                break;

            case "play bateria":
                bateria.continuar();
                break;

            // =================================================
            // BAIXO
            // =================================================

            case "pause baixo":
                baixo.pausar();
                break;

            case "play baixo":
                baixo.continuar();
                break;

            // =================================================
            // GUITARRA
            // =================================================

            case "pause guitarra":
                guitarra.pausar();
                break;

            case "play guitarra":
                guitarra.continuar();
                break;

            // =================================================
            // OSTINATO
            // =================================================

            case "pause ostinato":
                ostinato.pausar();
                break;

            case "play ostinato":
                ostinato.continuar();
                break;

            // =================================================
            // VOCAL
            // =================================================

            case "pause vocal":
                vocal.pausar();
                break;

            case "play vocal":
                vocal.continuar();
                break;

            // =================================================
            // VOLUME DA BATERIA
            // =================================================

            case "volume bateria 0":
                bateria.definirVolume(0);
                break;

            case "volume bateria 50":
                bateria.definirVolume(50);
                break;

            case "volume bateria 100":
                bateria.definirVolume(100);
                break;

            // =================================================
            // VOLUME DO BAIXO
            // =================================================

            case "volume baixo 0":
                baixo.definirVolume(0);
                break;

            case "volume baixo 50":
                baixo.definirVolume(50);
                break;

            case "volume baixo 100":
                baixo.definirVolume(100);
                break;

            // =================================================
            // VOLUME DA GUITARRA
            // =================================================

            case "volume guitarra 0":
                guitarra.definirVolume(0);
                break;

            case "volume guitarra 50":
                guitarra.definirVolume(50);
                break;

            case "volume guitarra 100":
                guitarra.definirVolume(100);
                break;

            // =================================================
            // VOLUME DO OSTINATO
            // =================================================

            case "volume ostinato 0":
                ostinato.definirVolume(0);
                break;

            case "volume ostinato 50":
                ostinato.definirVolume(50);
                break;

            case "volume ostinato 100":
                ostinato.definirVolume(100);
                break;

            // =================================================
            // VOLUME DO VOCAL
            // =================================================

            case "volume vocal 0":
                vocal.definirVolume(0);
                break;

            case "volume vocal 50":
                vocal.definirVolume(50);
                break;

            case "volume vocal 100":
                vocal.definirVolume(100);
                break;

            // =================================================
            // STATUS
            // =================================================

            case "status":

                System.out.println();
                System.out.println("============================================================");
                System.out.println("                         STATUS");
                System.out.println("============================================================");

                System.out.println(
                        "Bateria : " + bateria.getStatus()
                                + "   Volume: " + bateria.getVolume() + "%"
                );

                System.out.println(
                        "Baixo   : " + baixo.getStatus()
                                + "   Volume: " + baixo.getVolume() + "%"
                );

                System.out.println(
                        "Guitarra: " + guitarra.getStatus()
                                + "   Volume: " + guitarra.getVolume() + "%"
                );

                System.out.println(
                        "Ostinato: " + ostinato.getStatus()
                                + "   Volume: " + ostinato.getVolume() + "%"
                );

                System.out.println(
                        "Vocal   : " + vocal.getStatus()
                                + "   Volume: " + vocal.getVolume() + "%"
                );

                System.out.println("============================================================");
                System.out.println();

                break;

            // =================================================
            // STOP
            // =================================================

            case "stop":

                System.out.println();
                System.out.println("Encerrando a Mesa DJ...");
                System.out.println();

                bateria.parar();
                baixo.parar();
                guitarra.parar();
                ostinato.parar();
                vocal.parar();

                monitor.parar();

                try {

                    threadBateria.join();
                    threadBaixo.join();
                    threadGuitarra.join();
                    threadOstinato.join();
                    threadVocal.join();
                    threadMonitor.join();

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();
                }

                entrada.close();

                System.out.println();
                System.out.println("Mesa DJ encerrada.");

                return;

            // =================================================
            // COMANDO INVÁLIDO
            // =================================================

            default:

                System.out.println(
                        "Comando invalido."
                );

                System.out.println(
                        "Digite 'status' para consultar as faixas."
                );
        }
    }
}

}
