# DCC168 - Teste de Software (2026-3)
## Trabalho Final: Jogo da Velha - Partes I e II

Este repositório contém a implementação em Java do Jogo da Velha e a automação dos conjuntos de testes funcionais e estruturais correspondentes à **Parte II** do trabalho prático da disciplina DCC168, ministrada pelo Prof. André Luiz de Oliveira na Universidade Federal de Juiz de Fora (UFJF).

### 👥 Equipe
- Lara da Silva Dias
- Luiza Caldeira Daniel
- Vitor de Souza Reis

---

### 📂 Estrutura do Projeto

O projeto está estruturado no padrão Maven/Eclipse e organizado da seguinte forma:

```text
/
├── src/
│   ├── main/java/org/example/      # Classes de domínio (JogoDaVelha, Estado, TipoErro, JogadaInvalidaException)
│   └── test/java/org/example/      # Suítes e casos de teste automatizados
│       ├── TestSetFunc.java        # 51 casos de teste funcionais (Parte I)
│       ├── TestSetEstr.java        # 6 casos de teste estruturais complementares (Parte II)
│       ├── AllTests.java           # Suíte unificada para execução no JUnit e EclEmma (57 testes)
│       └── JogoDaVelhaAllTest.java # Suíte compatível com o executor do plugin Baduíno
├── .baduino/                       # Relatórios brutos e logs gerados pelo Baduíno
├── pom.xml                         # Configuração de dependências do projeto
└── README.md                       # Este arquivo de instruções

```

---

### ⚙️ Pré-requisitos e Ferramentas

Para executar o projeto e reproduzir as medições de cobertura estrutural, recomenda-se:

* **Java Development Kit (JDK):** Versão 8 (testado no JDK 1.8.0_502, exigido pelo plugin legado do Baduíno).
* **IDE:** Eclipse Neon.3 (4.6.3) configurado com JDK 1.8.
* **Framework de Testes:** JUnit 4.13.2.
* **Plugins de Cobertura (Eclipse):**
* [EclEmma](https://www.eclemma.org/) (Análise de Fluxo de Controle).
* [Baduíno](https://github.com/saeg/baduino) (Análise de Fluxo de Dados).



---

### 🚀 Como Importar e Executar

#### 1. Importar o Projeto no Eclipse

1. Abra o Eclipse IDE.
2. Acesse `File > Import...`.
3. Selecione `General > Existing Projects into Workspace` e clique em `Next`.
4. Em `Select root directory`, aponte para a pasta local deste repositório e clique em `Finish`.

#### 2. Executar os Testes no JUnit

1. No *Package Explorer*, navegue até `src/test/java/org/example`.
2. Clique com o botão direito sobre `AllTests.java` (ou individualmente em `TestSetFunc.java` / `TestSetEstr.java`).
3. Selecione `Run As > JUnit Test`.
4. Confirme que todos os **57 testes** (51 funcionais + 6 estruturais) passam com a barra verde.

#### 3. Verificar o Fluxo de Controle (EclEmma)

1. Clique com o botão direito sobre `AllTests.java` e selecione `Coverage As > JUnit Test`.
2. Na aba *Coverage*, expanda `src/main/java` para visualizar apenas as classes de domínio do jogo.
3. O resultado esperado é **100% de cobertura de métodos, linhas e desvios** em `src/main/java`.

#### 4. Verificar o Fluxo de Dados (Baduíno)

1. Certifique-se de que a saída de compilação de `src/test/java` aponta para a pasta padrão do projeto (`Default output folder`) e que a suíte `JogoDaVelhaAllTest.java` está presente.
2. Clique com o botão direito sobre a pasta raiz do projeto (`tic-tac-toe`) e selecione `Baduino > Run Tests`.
3. Na aba *Baduino*, a classe `org.example.JogoDaVelha` exibirá **227/233 DUAs cobertos**, atingindo **100% dos requisitos de uso executáveis**.

---

### 📊 Resumo dos Resultados de Cobertura

Ao executar o conjunto completo (`TestSet-Func` + `TestSet-Estr` — 57 testes), as classes de domínio (`src/main/java`) atingem:

* **Métodos:** 100% (11/11)
* **All-nodes (Linhas):** 100% (59/59)
* **All-edges (Desvios):** 100% (72/72)
* **All-uses / DUAs (Usos executáveis):** 100% (227/227)

*Nota: Dos 233 requisitos de uso mapeados pelo Baduíno, 6 requisitos de p-uso (2 em `reiniciar`, 1 em `verificarLinhas` e 3 em `getTabuleiroComoTexto`) correspondem às saídas falsas imediatas de laços `for` de limite fixo (`i < 3`), sendo matematicamente inexecutáveis conforme detalhado na Seção 4.3 do relatório.*

```