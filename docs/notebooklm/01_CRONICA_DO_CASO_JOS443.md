# Crónica do Caso JOS-443: Da Ideia ao Google Play com uma Equipa de Agentes

**Subtítulo:** Como uma squad de agentes especializados construiu, iterou e entregou a aplicação móvel da Feira do Livro de Vila Real em tempo recorde e com garantias industriais de qualidade.

---

## 1. O Ponto de Partida: O Pedido do Utilizador

No dia 19 de setembro de 2026, deu-se início à issue **JOS-443** no projeto *Vila Real* da plataforma Multica. O promotor do projeto (Membro José Torres Cunha) submeteu um pedido sucinto mas ambicioso:

> *"Quero desenvolver uma aplicação Android sobre a feira do livro de Vila Real. Devemos criar repositório próprio no github e seguir os padrões SSDLC. Disponibiliza um apk. Faz uma aplicação apelativa."*

Em menos de quatro linhas, concentravam-se desafios de enorme complexidade:
1. **Engenharia de Requisitos e Conteúdo:** Não existia uma especificação funcional detalhada nem uma base de dados estruturada fornecida à partida.
2. **Arquitetura e Segurança de Software:** Exigência explícita de seguir os padrões de **SSDLC (Secure Software Development Life Cycle)**.
3. **Design e Experiência:** A aplicação precisava de ser genuinamente "apelativa", inclusiva e culturalmente alinhada com a identidade do evento.
4. **Entrega Tangível:** Produção e disponibilização de um binário executável (`.apk`), além do repositório versionado no GitHub.

---

## 2. Ato I: Modelação de Produto e Especificação Viva

A liderança técnica da tarefa foi assumida pelo **Engenheiro de Software**, que imediatamente delegou a primeira fase crítica ao **Engenheiro de Design de Produto de Software**.

Em vez de seguir o caminho convencional e dispendioso — desenhar dezenas de ecrãs estáticos em ferramentas de desenho vetorial sem correspondência técnica —, o Engenheiro de Design adotou um processo de **Engenharia de Design centrada no utilizador (ISO 9241-210)**:
- **Extração da Informação:** Recolha e estruturação de 27 sessões oficiais do evento num ficheiro canónico `data/program_data.json` com tipagens estritas para oradores, horários, categorias e biografias.
- **Design System Material 3:** Criação de uma paleta visual inspirada nos tons terrosos do Douro e de Trás-os-Montes (Terracota `#8C3827`, Âmbar Quente `#8B5000` sobre Creme Papel `#FFF8F6`), calibrada para contraste superior a 7:1 (WCAG 2.2 AAA).
- **Protótipo Semântico em Código:** O designer não entregou imagens mortas; entregou componentes executáveis em **Jetpack Compose** (`EventModels.kt`, `Color.kt`, `Type.kt`, `SessionCard.kt`, `HomeScreen.kt`) e o documento `docs/PRODUCT_DESIGN_SPECIFICATION.md`.
- **Mitigação dos Quatro Riscos de Produto (Marty Cagan):**
  - *Risco de Valor:* Garantir que visitantes encontram instantaneamente as sessões sem perder tempo.
  - *Risco de Usabilidade:* Interface simples e legível sob luz solar direta para todas as faixas etárias.
  - *Risco de Viabilidade Técnica:* Adoção de uma arquitetura *100% Offline-First* (sem necessidade de servidor ou internet no recinto histórico dos claustros).
  - *Risco de Negócio / Privacidade:* Cumprimento do *Privacy by Design* — zero tracking, zero anúncios, zero recolha de dados pessoais.

---

## 3. Ato II: A Fundação DevOps e o Pipeline SSDLC

Com a especificação técnica e os protótipos de código em mãos, o **Engenheiro DevOps** entrou em ação:
1. **Criação do Repositório GitHub:** Criação do repositório oficial `JoseCunha/entre-quem-le-android` com a branch `main` e inicialização da base de código Android (Target SDK 35 / Android 15, Kotlin 2.0, Gradle 8.9).
2. **Automação do Pipeline CI/CD (`.github/workflows/ci.yml`):**
   - **SAST (Static Application Security Testing):** Integração do **Detekt** com regras estritas de segurança e código idiomático.
   - **Android Lint:** Verificação contínua de acessibilidade, concorrência e padrões de segurança.
   - **Suites de Testes Automatizados:** Implementação de `ProgramDataTest.kt` para validação matemática da integridade dos dados locais.
3. **Otimização Extrema com ProGuard / R8:** Ativação de minificação e encolhimento de recursos no build release, assegurando um binário leve (4.6 MB) e protegido contra engenharia reversa.
4. **Proveniência e Supply Chain Security:** Cada binário gerado foi assinado com hash SHA-256 publicado diretamente no issue.

A primeira versão estável foi marcada com a tag **`v1.0.0`**, e o APK `entre-quem-le-release.apk` foi anexado ao issue.

---

## 4. Ato III: O Primeiro Ciclo de Feedback Humano (O Alinhamento Temporal)

Ao testar a aplicação, o utilizador notou um detalhe essencial:
> *"Atenção que é para este ano de 2026"* / *"O resultado não é referente ao ano actual de 2026."*

### O Diagnóstico e Resolução Imediata
O Engenheiro de Software diagnosticou a causa raiz: um ficheiro residual de uma edição anterior (2024) tinha sido empacotado durante a transição de workspaces.
Em vez de discussões burocráticas ou desculpas:
- A equipa realizou uma auditoria imediata ao binário.
- Recompilou a aplicação do zero (`./gradlew assembleRelease`).
- Validou por descompressão binária a ausência absoluta de referências a 2024.
- Gerou a tag **`v1.0.1`** e entregou o binário `entre-quem-le-2026-release.apk` com checksum SHA-256 verificado, corrigindo o problema em menos de 15 minutos.

---

## 5. Ato IV: O Grande Desafio — A Brochura Oficial e a Pivotagem Estética

Minutos depois, o utilizador elevou o desafio, anexando um ficheiro PDF de 5 MB:
> *"Válida contra o documento em anexo. O programa não me parece igual. E ajusta o look&feel para o mesmo do documento. Continua a seguir os padrões SSDLC. !file[entre_quem_le_2026_brochura.pdf]"*

Num projeto tradicional, uma mudança destas a meio do ciclo paralisaria o projeto durante semanas. No modelo multiagente Multica com SSDLC:

### A Intervenção do Engenheiro de Design
O Engenheiro de Design de Produto de Software assumiu a análise minuciosa do documento impresso oficial:
1. **Explosão da Grelha de Programação:** A programação real era muito mais rica do que a divulgação inicial — expandiu-se de 27 para **52 atividades oficiais** ao longo dos 9 dias!
2. **Estrutura de Domínio Enriquecida:** Mapeamento de 8 novas categorias (`Conversa`, `Apresentação de livro`, `Recital`, `Curso / Workshop`, `Famílias`, `Escolas`, `Leitura`, `Institucional`), locais descentralizados (Oficina das Artes, Claustros, Vila Velha, Escolas) e marcação de sessões com inscrição obrigatória (`requiresRegistration: true`).
3. **Revolução Visual (O Novo Look & Feel da Brochura):**
   - Abandono do estilo terracota antigo em prol da estética contemporânea da brochura: **Azul Noturno Profundo (`#0D2E50`)**, **Amarelo Limão Vibrante (`#E4EF00`)** e **Azul Cerúleo (`#1D5FA7`)**.
   - Criação de 8 badges semânticas coloridas com contraste acessível para cada categoria.
   - Mudança tipográfica para uma Sans-Serif moderna e geométrica.
   - Atualização da especificação, dos protótipos Compose e geração do novo mockup `mockup_feira_livro_2026_brochura.jpg`.

### A Implementação pelo Engenheiro de Frontend
O **Engenheiro de Frontend** pegou imediatamente nos artefactos de design:
- Atualizou `app/src/main/assets/program_data.json` com os 52 eventos consolidados.
- Refatorou `SessionCard.kt`, `HomeScreen.kt` e os ecrãs temáticos com as novas cores e badges.
- Expandiu a suite `ProgramDataTest.kt` para cobrir os novos campos e regras de negócio.
- Validou os gates: `./gradlew testDebugUnitTest` (aprovado) e `./gradlew detekt` (0 violações).
- Submeteu o commit `edbbb6e` na branch `main` e compilou o novo APK oficial.

---

## 6. Ato V: O Salto para a Google Play Store e o Android App Bundle

Com a aplicação visual e funcionalmente impecável, o utilizador colocou o requisito final de entrega:
> *"Quero publicar na Playstore. Tenho Developer account com email da Google, mas queria associar isto à Torres da Cunha"*

A equipa não se limitou a dizer "isso está fora do âmbito":
1. **Consultoria Estratégica de Loja:**
   - Explicou a distinção entre Conta Individual e Conta de Organização (com número D-U-N-S), alertando para a exigência crítica da Google de ter 20 testadores fechados durante 14 dias em contas pessoais novas, vantagem que a conta de empresa ultrapassa.
2. **Transição Técnica de APK para AAB:**
   - A Google Play não permite a submissão de novos APKs desde 2021.
   - A equipa gerou e validou o **Android App Bundle (`entre-quem-le-2026-release.aab`)**, já configurado para o Target SDK 35 (Android 15), com minificação R8 completa e peso otimizado.
3. **Dossiê Completo de Submissão:**
   - Descrições curtas e longas da ficha de loja, classificação etária (PEGI 3), declaração de segurança de dados (*Data Safety*) comprovando zero recolha de informação, e indicação de assets visuais.

---

## 7. Balanço Final do Caso

Em poucas horas de trabalho articulado, um pedido de quatro linhas transformou-se numa aplicação móvel completa, testada, segura, esteticamente arrebatadora e pronta para distribuição global na Google Play Store.

A issue JOS-443 tornou-se o exemplo de referência de como a IA aplicada ao software não deve significar perda de controlo ou código descartável, mas sim a **elevação máxima do rigor de engenharia através de agentes com especializações bem demarcadas e liderança humana atenta**.
