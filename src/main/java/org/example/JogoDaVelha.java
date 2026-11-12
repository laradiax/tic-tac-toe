package org.example;

public class JogoDaVelha {

    public enum Estado {
        EM_ANDAMENTO, VITORIA_X, VITORIA_O, EMPATE
    }

    private char[][] tabuleiro;
    private char vez;
    private int numJogadas;
    private Estado estado;

    public JogoDaVelha() {
        reiniciar();
    }

    public void reiniciar() {
        tabuleiro = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = '-';
            }
        }
        vez = 'X';
        numJogadas = 0;
        estado = Estado.EM_ANDAMENTO;
    }

    public void jogar(char simbolo, int linha, int coluna) {
        // Validação estrita na ordem da Premissa P5
        if (estado != Estado.EM_ANDAMENTO) {
            throw new JogadaInvalidaException(TipoErro.PARTIDA_ENCERRADA);
        }
        if (simbolo != 'X' && simbolo != 'O') {
            throw new JogadaInvalidaException(TipoErro.SIMBOLO_INVALIDO);
        }
        if (simbolo != vez) {
            throw new JogadaInvalidaException(TipoErro.VEZ_INVALIDA);
        }
        if (linha < 1 || linha > 3 || coluna < 1 || coluna > 3) {
            throw new JogadaInvalidaException(TipoErro.POSICAO_INVALIDA);
        }
        if (tabuleiro[linha - 1][coluna - 1] != '-') {
            throw new JogadaInvalidaException(TipoErro.CELULA_OCUPADA);
        }

        // Executa a jogada
        tabuleiro[linha - 1][coluna - 1] = simbolo;
        numJogadas++;

        // Verifica vitória antes de empate (Premissa P6)
        if (verificarLinhas(simbolo)) {
            estado = (simbolo == 'X') ? Estado.VITORIA_X : Estado.VITORIA_O;
        } else if (numJogadas == 9) {
            estado = Estado.EMPATE;
        } else {
            vez = (vez == 'X') ? 'O' : 'X';
        }
    }

    private boolean verificarLinhas(char s) {
        // Horizontais e Verticais
        for (int i = 0; i < 3; i++) {
            if ((tabuleiro[i][0] == s && tabuleiro[i][1] == s && tabuleiro[i][2] == s) ||
                (tabuleiro[0][i] == s && tabuleiro[1][i] == s && tabuleiro[2][i] == s)) {
                return true;
            }
        }
        // Diagonais
        if (tabuleiro[0][0] == s && tabuleiro[1][1] == s && tabuleiro[2][2] == s) return true;
        if (tabuleiro[0][2] == s && tabuleiro[1][1] == s && tabuleiro[2][0] == s) return true;
        
        return false;
    }

    public char getCelula(int linha, int coluna) {
        if (linha < 1 || linha > 3 || coluna < 1 || coluna > 3) {
            throw new IllegalArgumentException("(" + linha + ", " + coluna + ")");
        }
        return tabuleiro[linha - 1][coluna - 1];
    }

    public boolean isEncerrada() {
        return estado != Estado.EM_ANDAMENTO;
    }

    public String getTabuleiroComoTexto() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(tabuleiro[i][j]);
            }
            if (i < 2) sb.append("\n");
        }
        return sb.toString();
    }
}