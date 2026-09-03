public class Monitor implements Runnable { 
 
    private Instrumento bateria; 
    private Instrumento baixo; 
    private Instrumento guitarra; 
    private Instrumento ostinato; 
    private Instrumento vocal; 
 
    private volatile boolean executando; 
 
    public Monitor( 
            Instrumento bateria, 
            Instrumento baixo, 
            Instrumento guitarra, 
            Instrumento ostinato, 
            Instrumento vocal) { 
 
        this.bateria = bateria; 
        this.baixo = baixo; 
        this.guitarra = guitarra; 
        this.ostinato = ostinato; 
        this.vocal = vocal; 
 
        this.executando = true; 
    } 
 
    @Override 
    public void run() { 
 
        mostrarPainelInicial(); 
 
        while (executando) { 
 
            try { 
 
                Thread.sleep(20000); 
 
            } catch (InterruptedException e) { 
 
                if (!executando) { 
                    break; 
                } 
 
                Thread.currentThread().interrupt(); 
                break; 
            } 
 
            if (executando) { 
                mostrarStatus(); 
            } 
        } 
    } 
 
    private void mostrarPainelInicial() { 
 
        System.out.println(); 
        System.out.println("============================================================"); 
        System.out.println("                    MESA DJ - JAVA"); 
        System.out.println("                     PAINEL AO VIVO"); 
        System.out.println("============================================================"); 
 
        System.out.println(); 
        System.out.println("FAIXAS"); 
        System.out.println("------------------------------------------------------------"); 
        System.out.println(" [1] BATERIA       [2] BAIXO       [3] GUITARRA"); 
        System.out.println(" [4] OSTINATO      [5] VOCAL"); 
        System.out.println("------------------------------------------------------------"); 
 
        System.out.println(); 
        System.out.println("VOLUME"); 
        System.out.println("----------------------------------------------------------------"); 
        System.out.println("                 VOLUME          VOLUME          VOLUME"); 
        System.out.println("                   0%              50%             100%"); 
        System.out.println("----------------------------------------------------------------"); 
        System.out.println(" BATERIA       |    0           |    50          |    100"); 
        System.out.println(" BAIXO         |    0           |    50          |    100"); 
        System.out.println(" GUITARRA      |    0           |    50          |    100"); 
        System.out.println(" OSTINATO      |    0           |    50          |    100"); 
        System.out.println(" VOCAL         |    0           |    50          |    100"); 
        System.out.println("----------------------------------------------------------------"); 
 
        System.out.println(); 
        System.out.println("CONTROLES"); 
        System.out.println("------------------------------------------------------------"); 
        System.out.println(" pause <instrumento>       play <instrumento>"); 
        System.out.println(" volume <instrumento> <valor>"); 
        System.out.println(" status                    stop"); 
        System.out.println("------------------------------------------------------------"); 
 
        System.out.println(); 
        System.out.println("Atualizacao automatica do status: 2 segundos"); 
        System.out.println("============================================================"); 
        System.out.println(); 
 
        System.out.print("Comando > "); 
    } 
 
    private void mostrarStatus() { 
 
        System.out.println(); 
        System.out.println("-------------------- STATUS AO VIVO --------------------"); 
        System.out.println(); 
 
        mostrarFaixa("BATERIA", bateria); 
        mostrarFaixa("BAIXO", baixo); 
        mostrarFaixa("GUITARRA", guitarra); 
        mostrarFaixa("OSTINATO", ostinato); 
        mostrarFaixa("VOCAL", vocal); 
 
        System.out.println(); 
        System.out.println("---------------------------------------------------------"); 
        System.out.println(); 
 
        System.out.print("Comando > "); 
    } 
 
    private void mostrarFaixa( 
            String nome, 
            Instrumento instrumento) { 
 
        System.out.printf( 
                " %-12s %-10s   Volume: %3d%%%n", 
                nome, 
                instrumento.getStatus(), 
                instrumento.getVolume() 
        ); 
    } 
 
    public void parar() { 
 
        executando = false; 
    } 
}