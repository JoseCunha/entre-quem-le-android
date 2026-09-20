# Caderno de Fontes para NotebookLM: O Caso de Sucesso da Issue JOS-443

**Aplicação Android "Entre Quem Lê" — Feira do Livro de Vila Real 2026**  
*Desenvolvimento de Software Seguro (SSDLC), Orquestração Multiagente e Redução Radical de Desperdício (Lean)*

---

## 1. Visão Geral deste Caderno

Este conjunto modular de documentos foi concebido para alimentar o **Google NotebookLM** (ou outros modelos de raciocínio documental) com toda a evidência factual, arquitetural e metodológica do trabalho realizado na issue **JOS-443**.

O objetivo primordial é permitir a geração de:
1. **Vídeo Promocional / Reel / Pitch Executivo:** Narrativa de alto impacto visual e argumentativo sobre como uma equipa de agentes autónomos, governada por princípios rigorosos de engenharia de software (SSDLC e Lean), concebeu, refinou e entregou uma aplicação móvel Android nativa com garantias de qualidade de topo.
2. **Audio Overview (Podcast / Deep Dive):** Conversa estimulante e aprofundada entre anfitriões de IA do NotebookLM sobre a transição de um paradigma tradicional lento e consumidor de recursos para um ecossistema ágil de agentes especializados.
3. **Estudo de Caso Académico e Industrial:** Demonstração prática de eliminação de desperdício em artefactos de software, conformidade de acessibilidade (WCAG 2.2 AAA), segurança *Privacy by Design* e prontidão para a Google Play Store.

---

## 2. Estrutura Modular dos Documentos

Para facilitar a consulta temática e permitir ao NotebookLM cruzar referências com precisão, a informação foi decomposta em 5 módulos complementares:

| Módulo | Ficheiro | Foco Principal |
| :--- | :--- | :--- |
| **01** | `01_CRONICA_DO_CASO_JOS443.md` | **A Narrativa Cronológica:** O relato detalhado dos acontecimentos, desde o pedido inicial até ao bundle para a Play Store, passando pelos ciclos de feedback do utilizador e resolução de desafios em tempo real. |
| **02** | `02_METODOLOGIA_SSDLC_E_QUALITY_GATES.md` | **Rigor de Engenharia e Segurança:** Os gates de qualidade implementados (SAST com Detekt, Android Lint, testes unitários automatizados, minificação R8/ProGuard, acessibilidade WCAG 2.2 AAA e proveniência criptográfica). |
| **03** | `03_ORQUESTRACAO_MULTIAGENTE_E_PAPEIS.md` | **Dinâmica de Equipa e Especialização:** Como os agentes (Software Engineer, Product Design Engineer, DevOps Engineer, Frontend Engineer) colaboraram com o humano no circuito (*Human-in-the-Loop*), evitando alucinações e retrabalho. |
| **04** | `04_LEAN_SOFTWARE_E_REDUCAO_DE_DESPERDICIO.md` | **O Valor Lean e Eficiência:** Como a arquitetura *offline-first*, a modelação única de dados em JSON e o protótipo semântico em Jetpack Compose erradicaram os 7 desperdícios clássicos do desenvolvimento de software. |
| **05** | `05_ROTEIRO_VIDEO_PROMOCIONAL_NOTEBOOKLM.md` | **Roteiro e Guião de Produção:** Estrutura cena a cena para vídeo/reel promocional (60s a 120s), sugestões visuais, tom de locução, mensagens-chave e prompts de ativação imediata. |

---

## 3. Metadados do Projeto e Rastreabilidade

- **Projeto Multica:** Vila Real (*Projectos para a cidade de Vila Real*)
- **Issue de Origem:** `JOS-443` (`01a0bafd-214b-7ad1-86de-ac42265d0060`)
- **Issue de Síntese:** `JOS-444` (`01a0bdaa-d0a0-7dfd-9200-0865825ae7e8`)
- **Produto:** Aplicação Móvel Android nativa *"Entre Quem Lê"*
- **Evento:** 3.ª Edição do Encontro Literário e Feira do Livro de Vila Real (18 a 26 de Setembro de 2026)
- **Local:** Claustros do Palácio do Conde de Amarante (antigo Governo Civil), Vila Real
- **Repositório Público GitHub:** `https://github.com/JoseCunha/entre-quem-le-android`
- **Releases e Binários Produzidos:**
  - `v1.0.0` (d6f49a0): Primeira baseline com pipeline SSDLC e 27 sessões.
  - `v1.0.1` (ac50920): Alinhamento temporal integral com a edição de 2026.
  - Commit `edbbb6e`: Integração completa da brochura oficial de 2026 (52 sessões, 8 categorias, novo design system).
  - `entre-quem-le-2026-release.apk` (4.6 MB): Binário minificado com R8 pronto a instalar.
  - `entre-quem-le-2026-release.aab` (17.8 MB): Android App Bundle assinado e configurado para a Google Play Console.

---

## 4. Prompts Recomendados para Executar no NotebookLM

Depois de carregar estes ficheiros no NotebookLM, sugerem-se os seguintes prompts para gerar conteúdos promocionais e educativos:

### Prompt 1: Gerar Podcast / Audio Overview (Deep Dive)
> *"Gera uma conversa aprofundada e estimulante entre dois especialistas em tecnologia sobre o caso de estudo da issue JOS-443. Destaca o contraste entre o desenvolvimento tradicional e o desenvolvimento orquestrado por agentes com SSDLC, focando em como a equipa eliminou desperdícios de software e respondeu de forma instantânea à mudança de requisitos quando o utilizador entregou a brochura oficial em PDF."*

### Prompt 2: Gerar Roteiro para Vídeo Promocional de 90 Segundos
> *"Com base no documento `05_ROTEIRO_VIDEO_PROMOCIONAL_NOTEBOOKLM.md` e na cronologia de `01_CRONICA_DO_CASO_JOS443.md`, gera um guião de vídeo promocional dinâmico e visual para o LinkedIn e YouTube Shorts. O vídeo deve enfatizar três pilares: 1) Redução de desperdício em artefactos de software; 2) Qualidade industrial comprovada por gates SSDLC; 3) O poder da colaboração entre agentes autónomos e liderança humana."*

### Prompt 3: FAQ Executiva sobre Governação de Agentes de IA
> *"Cria um documento de perguntas e respostas para Diretores de Tecnologia (CTOs) e Líderes de Produto que demonstre, com dados do caso JOS-443, como garantir que agentes de codificação não produzem código defeituoso ou 'alucinações', evidenciando o papel dos testes automatizados, SAST e da especificação semântica em Jetpack Compose."*
