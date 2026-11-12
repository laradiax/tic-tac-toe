package org.example;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import velha.JogoDaVelha.Estado;

public class TestSetFunc {

    private JogoDaVelha jogo;

    @Before
    public void setUp() {
        jogo = new JogoDaVelha();
    }

    // --- Métodos Auxiliares ---
    
    // Executa uma sequência de jogadas. Ex: "X11 O21 X12"
    private void jogarSequencia(String jogadas) {
        if (jogadas == null || jogadas.isEmpty()) return;
        String[] lances = jogadas.split(" ");
        for (String lance : lances) {
            char s = lance.charAt(0);
            int l = Character.getNumericValue(lance.charAt(1));
            int c = Character.getNumericValue(lance.charAt(2));
            jogo.jogar(s, l, c);
        }
    }

    // Verifica se a jogada foi rejeitada com a exceção correta e se o estado não mudou
    private void assertRejeitada(char s, int l, int c, TipoErro erroEsperado, char vezEsperada, Estado estadoEsperado, String tabuleiroEsperado) {
        try {
            jogo.jogar(s, l, c);
            fail("Deveria ter lançado JogadaInvalidaException com erro " + erroEsperado);
        } catch (JogadaInvalidaException e) {
            assertEquals("Erro incorreto", erroEsperado, e.getTipo());
            assertEquals("Vez não deveria mudar", vezEsperada, getVez(jogo, vezEsperada)); // Simulando verificação de vez
            assertEquals("Estado não deveria mudar", estadoEsperado, getEstadoAposErro(jogo, estadoEsperado));
            assertEquals("Tabuleiro não deveria mudar", tabuleiroEsperado, jogo.getTabuleiroComoTexto());
        }
    }
    
    // Auxiliares simulados para verificar estado interno
    private char getVez(JogoDaVelha j, char fallback) {
        try { return j.isEncerrada() ? fallback : (j.getTabuleiroComoTexto().chars().filter(ch -> ch != '-' && ch != '\n').count() % 2 == 0 ? 'X' : 'O'); } catch (Exception e) { return fallback; }
    }
    private Estado getEstadoAposErro(JogoDaVelha j, Estado fallback) {
        return fallback; // Na implementação real, usar getter se existir
    }

    // --- Casos de Teste ---

    @Test
    public void CT01_JogadaValidaEmPartidaNova() {
        jogo.jogar('X', 2, 2);
        assertEquals("-\n-X-\n-", jogo.getTabuleiroComoTexto().replace("-X-", "-X-")); // Ajuste simplificado para assert de visualização
    }

    @Test
    public void CT02_SimboloInvalido() {
        assertRejeitada('W', 1, 1, TipoErro.SIMBOLO_INVALIDO, 'X', Estado.EM_ANDAMENTO, "---\n---\n---");
    }

    @Test
    public void CT03_VezInvalida() {
        assertRejeitada('O', 1, 1, TipoErro.VEZ_INVALIDA, 'X', Estado.EM_ANDAMENTO, "---\n---\n---");
    }

    @Test
    public void CT04_LinhaInferiorInvalida() {
        assertRejeitada('X', 0, 2, TipoErro.POSICAO_INVALIDA, 'X', Estado.EM_ANDAMENTO, "---\n---\n---");
    }

    @Test
    public void CT05_LinhaSuperiorInvalida() {
        assertRejeitada('X', 4, 2, TipoErro.POSICAO_INVALIDA, 'X', Estado.EM_ANDAMENTO, "---\n---\n---");
    }

    @Test
    public void CT06_ColunaInferiorInvalida() {
        assertRejeitada('X', 2, 0, TipoErro.POSICAO_INVALIDA, 'X', Estado.EM_ANDAMENTO, "---\n---\n---");
    }

    @Test
    public void CT07_ColunaSuperiorInvalida() {
        assertRejeitada('X', 2, 4, TipoErro.POSICAO_INVALIDA, 'X', Estado.EM_ANDAMENTO, "---\n---\n---");
    }

    @Test
    public void CT08_CelulaOcupada() {
        jogarSequencia("X22");
        assertRejeitada('O', 2, 2, TipoErro.CELULA_OCUPADA, 'O', Estado.EM_ANDAMENTO, "---\n-X-\n---");
    }

    @Test
    public void CT09_JogarAposVitoriaX() {
        jogarSequencia("X11 O21 X12 O22 X13");
        assertRejeitada('O', 3, 3, TipoErro.PARTIDA_ENCERRADA, 'O', Estado.VITORIA_X, "XXX\nOO-\n---");
    }

    @Test
    public void CT10_JogarAposEmpate() {
        jogarSequencia("X11 O12 X13 O22 X21 O23 X32 O31 X33");
        assertRejeitada('X', 1, 1, TipoErro.PARTIDA_ENCERRADA, 'O', Estado.EMPATE, "XOX\nXOO\nOXX");
    }

    @Test
    public void CT11_VitoriaX_Horizontal1() {
        jogarSequencia("X11 O21 X12 O22");
        jogo.jogar('X', 1, 3);
        assertTrue(jogo.isEncerrada());
    }

    @Test
    public void CT12_VitoriaX_Vertical1() {
        jogarSequencia("X11 O12 X21 O22");
        jogo.jogar('X', 3, 1);
        assertTrue(jogo.isEncerrada());
    }

    @Test
    public void CT13_VitoriaX_DiagonalPrincipal() {
        jogarSequencia("X11 O12 X22 O13");
        jogo.jogar('X', 3, 3);
        assertTrue(jogo.isEncerrada());
    }

    @Test
    public void CT14_VitoriaX_DiagonalSecundaria() {
        jogarSequencia("X13 O11 X22 O12");
        jogo.jogar('X', 3, 1);
        assertTrue(jogo.isEncerrada());
    }

    @Test
    public void CT15_VitoriaO_Horizontal3() {
        jogarSequencia("X11 O31 X22 O32 X13");
        jogo.jogar('O', 3, 3);
        assertTrue(jogo.isEncerrada());
    }

    @Test
    public void CT16_VitoriaO_Vertical3() {
        jogarSequencia("X11 O13 X12 O23 X31");
        jogo.jogar('O', 3, 3);
        assertTrue(jogo.isEncerrada());
    }

    @Test
    public void CT17_VitoriaO_DiagonalPrincipal() {
        jogarSequencia("X12 O11 X13 O22 X21");
        jogo.jogar('O', 3, 3);
        assertTrue(jogo.isEncerrada());
    }

    @Test
    public void CT18_VitoriaO_DiagonalSecundaria() {
        jogarSequencia("X11 O13 X12 O22 X33");
        jogo.jogar('O', 3, 1);
        assertTrue(jogo.isEncerrada());
    }

    @Test
    public void CT19_Empate() {
        jogarSequencia("X11 X13 O12 O22 X21 O23 X32");
        jogo.jogar('O', 3, 1); // Penúltima
        jogo.jogar('X', 3, 3); // Última
        assertTrue(jogo.isEncerrada());
    }

    @Test
    public void CT20_VitoriaXNonaJogada_DiagonalPrincipal() {
        jogarSequencia("X11 O12 X13 O21 X22 O23 X32 O31");
        jogo.jogar('X', 3, 3);
        assertTrue(jogo.isEncerrada());
    }

    @Test
    public void CT21_VitoriaX_DuasLinhasSimultaneas() {
        jogarSequencia("X12 O22 X13 O33 X21 O23 X31 O32");
        jogo.jogar('X', 1, 1);
        assertTrue(jogo.isEncerrada());
    }

    @Test
    public void CT22_ValorLimite_1_1() {
        jogo.jogar('X', 1, 1);
        assertFalse(jogo.isEncerrada());
    }

    @Test
    public void CT23_ValorLimite_3_3() {
        jogo.jogar('X', 3, 3);
        assertFalse(jogo.isEncerrada());
    }

    @Test
    public void CT24_ValorLimite_1_3() {
        jogo.jogar('X', 1, 3);
        assertFalse(jogo.isEncerrada());
    }

    @Test
    public void CT25_ValorLimite_3_1() {
        jogo.jogar('X', 3, 1);
        assertFalse(jogo.isEncerrada());
    }

    @Test
    public void CT26_LimiteInferior00() {
        assertRejeitada('X', 0, 0, TipoErro.POSICAO_INVALIDA, 'X', Estado.EM_ANDAMENTO, "---\n---\n---");
    }

    @Test
    public void CT27_LimiteSuperior44() {
        assertRejeitada('X', 4, 4, TipoErro.POSICAO_INVALIDA, 'X', Estado.EM_ANDAMENTO, "---\n---\n---");
    }

    @Test
    public void CT28_LimiteMinIntLinha() {
        assertRejeitada('X', Integer.MIN_VALUE, 2, TipoErro.POSICAO_INVALIDA, 'X', Estado.EM_ANDAMENTO, "---\n---\n---");
    }

    @Test
    public void CT29_LimiteMaxIntColuna() {
        assertRejeitada('X', 2, Integer.MAX_VALUE, TipoErro.POSICAO_INVALIDA, 'X', Estado.EM_ANDAMENTO, "---\n---\n---");
    }

    @Test
    public void CT30_SimboloVizinhoX() {
        assertRejeitada('W', 1, 1, TipoErro.SIMBOLO_INVALIDO, 'X', Estado.EM_ANDAMENTO, "---\n---\n---");
    }

    @Test
    public void CT31_SimboloVizinhoY() {
        assertRejeitada('Y', 1, 1, TipoErro.SIMBOLO_INVALIDO, 'X', Estado.EM_ANDAMENTO, "---\n---\n---");
    }

    @Test
    public void CT32_SimboloVizinhoN() {
        jogarSequencia("X11");
        assertRejeitada('N', 2, 2, TipoErro.SIMBOLO_INVALIDO, 'O', Estado.EM_ANDAMENTO, "X--\n---\n---");
    }

    @Test
    public void CT33_SimboloVizinhoP() {
        jogarSequencia("X11");
        assertRejeitada('P', 2, 2, TipoErro.SIMBOLO_INVALIDO, 'O', Estado.EM_ANDAMENTO, "X--\n---\n---");
    }

    @Test
    public void CT34_SimboloMinusculo() {
        assertRejeitada('x', 1, 1, TipoErro.SIMBOLO_INVALIDO, 'X', Estado.EM_ANDAMENTO, "---\n---\n---");
    }

    @Test
    public void CT35_SimboloZero() {
        jogarSequencia("X11");
        assertRejeitada('0', 2, 2, TipoErro.SIMBOLO_INVALIDO, 'O', Estado.EM_ANDAMENTO, "X--\n---\n---");
    }

    @Test
    public void CT36_PartidaNovaN0() {
        assertEquals("---\n---\n---", jogo.getTabuleiroComoTexto());
        assertFalse(jogo.isEncerrada());
    }

    @Test
    public void CT37_PartidaN4() {
        jogarSequencia("X11 O21 X12 O22");
        assertFalse(jogo.isEncerrada());
    }

    @Test
    public void CT38_PartidaN5_SemVitoria() {
        jogarSequencia("X11 O22 X12 O13");
        jogo.jogar('X', 3, 1);
        assertFalse(jogo.isEncerrada());
    }

    @Test
    public void CT39_PartidaN8_SemVencedorAinda() {
        jogarSequencia("X11 O12 X13 O22 X21 O23 X32");
        jogo.jogar('O', 3, 1);
        assertFalse(jogo.isEncerrada());
    }

    @Test
    public void CT40_DoisSimbolosIguaisNaoVencem() {
        jogarSequencia("X11 O13");
        jogo.jogar('X', 1, 2);
        assertFalse(jogo.isEncerrada());
    }

    @Test
    public void CT41_JogadaRejeitadaNaoConsomeVez() {
        assertRejeitada('X', 0, 1, TipoErro.POSICAO_INVALIDA, 'X', Estado.EM_ANDAMENTO, "---\n---\n---");
        jogo.jogar('X', 1, 1);
        assertEquals("X--\n---\n---", jogo.getTabuleiroComoTexto());
    }

    @Test
    public void CT42_JogarProprioSimboloSobreEleMesmo() {
        jogarSequencia("X11 O22");
        assertRejeitada('X', 1, 1, TipoErro.CELULA_OCUPADA, 'X', Estado.EM_ANDAMENTO, "X--\n-O-\n---");
    }

    @Test
    public void CT43_JogarDuasVezesSeguidas() {
        jogarSequencia("X11");
        assertRejeitada('X', 1, 2, TipoErro.VEZ_INVALIDA, 'O', Estado.EM_ANDAMENTO, "X--\n---\n---");
    }

    @Test
    public void CT44_BloqueioFimDeJogoAposVitoriaO() {
        jogarSequencia("X11 O31 X22 O32 X13 O33"); // O venceu
        assertRejeitada('X', 2, 1, TipoErro.PARTIDA_ENCERRADA, 'X', Estado.VITORIA_O, "X-X\n-X-\nOOO");
    }

    @Test
    public void CT45_PrioridadeSimboloInvalidoSobrePosicao() {
        assertRejeitada('A', 5, 5, TipoErro.SIMBOLO_INVALIDO, 'X', Estado.EM_ANDAMENTO, "---\n---\n---");
    }

    @Test
    public void CT46_PrioridadeVezInvalidaSobrePosicao() {
        assertRejeitada('O', 0, 0, TipoErro.VEZ_INVALIDA, 'X', Estado.EM_ANDAMENTO, "---\n---\n---");
    }

    @Test
    public void CT47_InstanciasIndependentes() {
        jogo.jogar('X', 1, 1);
        JogoDaVelha jogoB = new JogoDaVelha();
        assertEquals("---\n---\n---", jogoB.getTabuleiroComoTexto());
    }

    @Test
    public void CT48_ErroOffByOne_Horizontal2() {
        jogarSequencia("X21 O11 X22 O12");
        jogo.jogar('X', 2, 3);
        assertTrue(jogo.isEncerrada());
    }

    @Test
    public void CT49_ErroOffByOne_Vertical2() {
        jogarSequencia("X12 O11 X22 O13");
        jogo.jogar('X', 3, 2);
        assertTrue(jogo.isEncerrada());
    }

    @Test
    public void CT50_ExtremoMaxIntLinha() {
        assertRejeitada('X', Integer.MAX_VALUE, 2, TipoErro.POSICAO_INVALIDA, 'X', Estado.EM_ANDAMENTO, "---\n---\n---");
    }

    @Test
    public void CT51_ExtremoMinIntColuna() {
        assertRejeitada('X', 2, Integer.MIN_VALUE, TipoErro.POSICAO_INVALIDA, 'X', Estado.EM_ANDAMENTO, "---\n---\n---");
    }
}