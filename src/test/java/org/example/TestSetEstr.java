package org.example;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestSetEstr {

    private JogoDaVelha jogo;

    @Before
    public void setUp() {
        jogo = new JogoDaVelha();
    }

    @Test
    public void CE01_GetCelula_LinhaMenorQue1() {
        try {
            jogo.getCelula(0, 1);
            fail("Deveria lançar IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("(0, 1)", e.getMessage());
        }
    }

    @Test
    public void CE02_GetCelula_LinhaMaiorQue3() {
        try {
            jogo.getCelula(4, 1);
            fail("Deveria lançar IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("(4, 1)", e.getMessage());
        }
    }

    @Test
    public void CE03_GetCelula_ColunaMenorQue1() {
        try {
            jogo.getCelula(1, 0);
            fail("Deveria lançar IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("(1, 0)", e.getMessage());
        }
    }

    @Test
    public void CE04_GetCelula_ColunaMaiorQue3() {
        try {
            jogo.getCelula(1, 4);
            fail("Deveria lançar IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("(1, 4)", e.getMessage());
        }
    }

    @Test
    public void CE05_IsEncerrada_FluxoFalsoEVerdadeiro() {
        // Cobre as duas saídas de isEncerrada()
        jogo.jogar('X', 1, 1);
        jogo.jogar('O', 2, 1);
        assertFalse("Deveria estar em andamento (falso)", jogo.isEncerrada());
        
        jogo.jogar('X', 1, 2);
        jogo.jogar('O', 2, 2);
        jogo.jogar('X', 1, 3); // X Vence
        
        assertTrue("Deveria estar encerrada (verdadeiro)", jogo.isEncerrada());
    }

    @Test
    public void CE06_GetTabuleiroComoTexto_Completo() {
        // Cobre todo o escopo de variáveis i e j na construção da string, além dos Ifs do formater
        assertEquals("---\n---\n---", jogo.getTabuleiroComoTexto());
        
        jogo.jogar('X', 1, 1);
        jogo.jogar('O', 1, 2);
        jogo.jogar('X', 2, 2);
        
        assertEquals('X', jogo.getCelula(1, 1));
        assertEquals("XO-\n-X-\n---", jogo.getTabuleiroComoTexto());
    }
}