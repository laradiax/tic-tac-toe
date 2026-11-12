# DCC168 - Teste de Software (2026-3)
## Trabalho Final: Jogo da Velha - Parte I e II

Este repositório contém a implementação em Java do Jogo da Velha e a automação dos conjuntos de testes funcionais e estruturais correspondentes à **Parte II** do trabalho prático da disciplina DCC168, ministrada pelo Prof. André Luiz de Oliveira na Universidade Federal de Juiz de Fora (UFJF).

### 👥 Equipe
* Lara da Silva Dias
* Luiza Caldeira Daniel
* Vitor de Souza Reis

---

### 📂 Estrutura do Projeto

O projeto foi desenvolvido no **Eclipse IDE** e está organizado da seguinte forma:

```text
/
├── src/                # Código-fonte principal da aplicação (regras de negócio)
│   └── velha/          # Pacote principal (JogoDaVelha, Estado, TipoErro, etc.)
├── test/               # Código-fonte dos casos de teste automatizados
│   └── velha/          # Classes TestSetFunc.java (51 testes) e TestSetEstr.java (6 testes)
├── relatorios/         # Relatórios de cobertura gerados pelas ferramentas
│   ├── func/           # Relatórios EclEmma e Baduíno (apenas TestSet-Func)
│   └── func+estr/      # Relatórios EclEmma e Baduíno (TestSet-Func + TestSet-Estr)
├── tools/              # Ferramenta de medição própria (conforme secção 6 do relatório)
├── README.md           # Este ficheiro de instruções
└── Relatorio_Parte_II.pdf # Relatório final detalhando as métricas e análises

```

---

### ⚙️ Pré-requisitos e Ferramentas

Para executar o projeto e avaliar a cobertura de código, é necessário:

* **Java Development Kit (JDK):** Versão 8 ou superior (testado no JDK 21).
* **IDE:** Eclipse IDE.
* **Framework de Testes:** JUnit 4.13.2.
* **Plugins de Cobertura (Eclipse):**
* [EclEmma](https://www.eclemma.org/) (Fluxo de Controle).
* [Baduíno](https://github.com/saeg/baduino) (Fluxo de Dados).

---

### 🚀 Como Importar e Executar

#### 1. Importar o Projeto no Eclipse

1. Abra o Eclipse IDE.
2. Vá a `File > Import...`
3. Escolha `General > Existing Projects into Workspace` e clique em `Next`.
4. Em `Select root directory`, procure a pasta extraída deste `.zip`.
5. Certifique-se de que o projeto está selecionado na lista e clique em `Finish`.

#### 2. Executar os Testes no JUnit

1. No *Project Explorer*, navegue até à pasta `test/velha`.
2. Clique com o botão direito sobre `TestSetFunc.java` ou `TestSetEstr.java`.
3. Selecione `Run As > JUnit Test`.
4. Confirme que todos os 57 testes (51 funcionais + 6 estruturais) passam com a barra verde.

#### 3. Verificar a Cobertura Estrutural (EclEmma)

1. No *Project Explorer*, selecione as duas classes de teste (`TestSetFunc.java` e `TestSetEstr.java`).
2. Clique com o botão direito e selecione `Coverage As > JUnit Test`.
3. A janela *Coverage* exibirá as métricas. O resultado esperado é **100% de cobertura de linhas e desvios** na classe `JogoDaVelha.java`.

#### 4. Verificar o Fluxo de Dados (Baduíno)

1. Certifique-se de que o plugin Baduíno está instalado no Eclipse.
2. Selecione as classes de teste, clique com o botão direito e escolha a opção de execução do Baduíno (conforme as instruções da ferramenta).
3. A view do Baduíno deverá atestar a cobertura total (100%) dos usos executáveis, cobrindo todos os pares definição-uso (all-uses).

---

### 📊 Resumo dos Resultados de Cobertura

Ao executar o conjunto completo (`TestSet-Func` + `TestSet-Estr`), o projeto atinge:

* **All-nodes (Linhas):** 100% (78/78)
* **All-edges (Desvios):** 100% (74/74)
* **All-defs (Definições):** 100% (18/18)
* **All-uses (Usos executáveis):** 100% (134/134)

*Nota: 9 requisitos de uso foram identificados como caminhos matematicamente impossíveis de executar, conforme detalhado na secção 4.3 do relatório em PDF.*
