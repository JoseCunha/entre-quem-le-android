# Orquestração Multiagente e Papéis Especializados

**Subtítulo:** A dinâmica da squad de agentes autónomos, os limites de especialização e o papel insubstituível da liderança humana (*Human-in-the-Loop*).

---

## 1. O Falso Mito do "Agente Faz-Tudo"

Muitas tentativas de utilizar agentes de inteligência artificial em desenvolvimento de software falham porque atribuem a um único modelo a responsabilidade integral por todo o ciclo de vida: do levantamento de requisitos ao design visual, da escrita de código à infraestrutura de cloud e aos testes.

Este modelo monolítico gera três problemas graves:
1. **Perda de Contexto e Alucinação:** À medida que a conversa se alonga, o agente esquece diretrizes iniciais ou confunde versões.
2. **Superficialidade de Conhecimento:** Um agente generalista tende a inventar soluções visuais banais e a descurar boas práticas avançadas de engenharia (como regras do compilador R8 ou rácios WCAG AAA).
3. **Falta de Verificação Cruzada:** O mesmo agente que escreve o código valida o seu próprio trabalho, gerando um enviesamento de auto-aprovação.

No caso **JOS-443**, a plataforma Multica empregou uma **Squad de Especialistas**, onde cada agente possui um mandato formal, regras operacionais e fronteiras bem definidas.

---

## 2. A Squad de Agentes e as suas Responsabilidades

```
                 ┌──────────────────────────────┐
                 │        MEMBRO HUMANO         │
                 │   (Visão, Contexto e Decisão)│
                 └──────────────┬───────────────┘
                                │
                                ▼
                 ┌──────────────────────────────┐
                 │    ENGENHEIRO DE SOFTWARE    │
                 │(Coordenação, Arquitetura & QA)│
                 └──────┬───────────────┬───────┘
                        │               │
        ┌───────────────┴────┐     ┌────┴───────────────┐
        ▼                    ▼     ▼                    ▼
┌──────────────┐  ┌──────────────┐ ┌──────────────┐  ┌──────────────┐
│ ENGENHEIRO DE│  │  ENGENHEIRO  │ │  ENGENHEIRO  │  │  SERVIÇOS DE  │
│DESIGN PRODUTO│  │    DEVOPS    │ │   FRONTEND   │  │ DISTRIBUIÇÃO │
│(HCD, Dados e │  │(CI/CD, SAST &│ │(Jetpack Comp,│  │(Play Store & │
│  Protótipo)  │  │ Repositório) │ │Testes & Build│  │  Bundle AAB) │
└──────────────┘  └──────────────┘ └──────────────┘  └──────────────┘
```

---

### 1. Engenheiro de Software (Tech Lead & Coordenador)
- **Papel:** Líder de equipa, arquiteto de sistemas e garante da coesão do projeto.
- **Ações Chave no Caso:**
  - Decomposição do pedido do utilizador em entregáveis técnicos claros.
  - Orquestração dos despachos para os especialistas de Design, DevOps e Frontend.
  - Auditoria dos binários e verificação do cumprimento de todos os critérios de aceitação.
  - Gestão de incidentes e resiliência (gestão da pausa temporária de quota da API sem perda de dados ou pânico).
  - Consultoria estratégica sobre a publicação na Google Play Console (marca Torres da Cunha, D-U-N-S, regras de 20 testadores).

### 2. Engenheiro de Design de Produto de Software
- **Papel:** Especialista em Human-Centred Design (ISO 9241-210), modelação de informação, usabilidade, acessibilidade e protótipos de alta fidelidade técnica.
- **Ações Chave no Caso:**
  - Extração minuciosa dos dados do evento a partir de cartazes e da brochura oficial de 5 MB, gerando o schema JSON unificado (`data/program_data.json`).
  - Redefinição dos tokens de design (paletas cromáticas, escalas tipográficas) com contraste rigoroso WCAG 2.2 AAA.
  - Criação de protótipos executáveis em código nativo (**Jetpack Compose**), eliminando o fosso entre o design e a programação.
  - Redação da especificação viva em `docs/PRODUCT_DESIGN_SPECIFICATION.md`.

### 3. Engenheiro DevOps
- **Papel:** Infraestrutura como código, automação de pipelines de entrega contínua (CI/CD) e segurança de supply chain.
- **Ações Chave no Caso:**
  - Inicialização do repositório no GitHub (`JoseCunha/entre-quem-le-android`).
  - Configuração do pipeline em GitHub Actions (`.github/workflows/ci.yml`) integrando SAST (Detekt), Lint de acessibilidade e testes unitários.
  - Otimização do processo de compilação release com ProGuard/R8.
  - Automação do cálculo de proveniência criptográfica (SHA-256) e publicação de releases.

### 4. Engenheiro de Frontend
- **Papel:** Especialista em desenvolvimento Android moderno com Kotlin e Jetpack Compose Material 3.
- **Ações Chave no Caso:**
  - Implementação dos ecrãs de produção a partir dos protótipos fornecidos pelo Design.
  - Integração da base de dados oficial (52 sessões) em `app/src/main/assets/`.
  - Desenvolvimento de testes unitários automatizados para assegurar a consistência da navegação e das regras de negócio.
  - Geração e verificação dos binários finais (`.apk` e `.aab`).

---

## 3. Mecanismos de Colaboração e Tolerância a Falhas

A colaboração entre agentes na plataforma Multica assenta em princípios rigorosos que garantem previsibilidade:

### A. Comunicação Assíncrona Baseada em Artefactos Concretos
Nenhum agente diz apenas *"já terminei a minha parte"*. Cada transição de tarefa foi acompanhada por:
- O caminho exato dos ficheiros criados/alterados (ex: `data/program_data.json`, `docs/compose_prototype/`);
- O resultado numérico dos comandos de validação (ex: 0 violações no Detekt, 4/4 suites de testes aprovadas);
- O estado dos branches e tags no Git (`v1.0.0`, `v1.0.1`, commit `edbbb6e`);
- Os hashes criptográficos de proveniência dos binários.

### B. Resiliência Perante Limitações Externas (O Caso da Quota de API)
Durante a implementação da versão com a brochura, um dos agentes atingiu o limite de taxa do fornecedor do modelo de IA (`RESOURCE_EXHAUSTED 429`).
Em sistemas mal orquestrados, isto teria resultado em aborto do processo ou corrupção do workspace.
Na squad Multica:
1. O sistema capturou o erro de forma segura e notificou a liderança da equipa.
2. O Engenheiro de Software manteve o estado intacto e aguardou o término da janela de arrefecimento.
3. Assim que o acesso foi restabelecido, reatribuiu a tarefa com o contexto preservado, permitindo a conclusão bem-sucedida do trabalho sem qualquer intervenção corretiva humana.

---

## 4. O Papel Estratégico do Humano (*Human-in-the-Loop*)

O caso JOS-443 demonstra com perfeição onde a liderança humana cria o maior valor:
- **Não na microgestão de código:** O utilizador não precisou de escrever uma única linha de Kotlin, configurar o Gradle ou afinar regras do ProGuard.
- **Mas sim na direção estratégica e validação de contexto:**
  1. *O alinhamento temporal:* Apontar que o ano pretendido era 2026 e não a edição antiga.
  2. *O fornecimento de artefactos reais:* Disponibilizar o PDF da brochura oficial com o programa fechado de 52 sessões.
  3. *A ambição comercial de negócio:* Definir que a app deveria ser publicada na Google Play Store sob a chancela da marca *"Torres da Cunha"*.

A squad de agentes funcionou como uma equipa de engenharia de elite às ordens de um Diretor de Produto ou Empreendedor: o humano define o *quê* e o *porquê*; os agentes garantem o *como* com rigor absoluto.
