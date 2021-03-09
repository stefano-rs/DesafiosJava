import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Main {
    public static void main(String[] args) {
        Jogo jogo = new Jogo("Marcia", "Fernanda");
        Jogador vencedor = jogo.executar("Tesoura", "Papel");

        if (vencedor == null) {
            System.out.println("Empate");
        } else {
            System.out.println("O vencedor foi: " + vencedor.nome);
        }    
    }
}

class Jogador {
    public final String nome;

    public Jogador(String nome) {
        this.nome = nome;
    }

    public Jogada criarJogada(String simbolo) {
       return new Jogada(this, Simbolos.valueOf(simbolo));
    }
}

class Jogada {
    public Jogador jogador;
    public Simbolos simbolo;

    public Jogada(Jogador jogador, Simbolos simbolo) {
        this.jogador = jogador;
        this.simbolo = simbolo;
    }
}

enum Simbolos {
        Tesoura, Papel, Pedra, Lagarto, Spock;
}

class Jogo {
    public static final Map<Simbolos, Set<Simbolos>> REGRAS;

    private Jogador jogadorA;
    private Jogador jogadorB;

    static {
        Map<Simbolos, Set<Simbolos>> regrasMap = new HashMap<>();
        regrasMap.put(Simbolos.Tesoura, Collections.unmodifiableSet(Set.of(Simbolos.Papel, Simbolos.Lagarto)));
        regrasMap.put(Simbolos.Papel, Collections.unmodifiableSet(Set.of(Simbolos.Pedra, Simbolos.Spock)));
        regrasMap.put(Simbolos.Pedra, Collections.unmodifiableSet(Set.of(Simbolos.Lagarto, Simbolos.Tesoura)));
        regrasMap.put(Simbolos.Lagarto, Collections.unmodifiableSet(Set.of(Simbolos.Spock, Simbolos.Papel)));
        regrasMap.put(Simbolos.Spock, Collections.unmodifiableSet(Set.of(Simbolos.Tesoura, Simbolos.Pedra)));
        REGRAS = Collections.unmodifiableMap(regrasMap);
    }

    public Jogo(String nomeJogadorA, String nomeJogadorB) {
        this.jogadorA = new Jogador(nomeJogadorA);
        this.jogadorB = new Jogador(nomeJogadorB);
    }

    public Jogador executar(String simboloA, String simboloB) {
        Jogada a = this.jogadorA.criarJogada(simboloA);
        Jogada b = this.jogadorB.criarJogada(simboloB);

        if (REGRAS.get(a.simbolo).contains(b.simbolo)) {
            return a.jogador;
        }

        if (REGRAS.get(b.simbolo).contains(a.simbolo)) {
            return b.jogador;
        }

        return null;
    }
}
