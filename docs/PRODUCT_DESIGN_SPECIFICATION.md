# Especificação de Design de Produto de Software: Aplicação Android "Entre Quem Lê"

**Evento:** Feira do Livro de Vila Real 2024 (20 a 28 de Setembro de 2024)  
**Organização:** Município de Vila Real (com parcerias de FNAC e Cultura a Dentro)  
**Perfil Responsável:** Engenheiro de Design de Produto de Software  
**Referenciais:** ISO 9241-210:2019 (Human-Centred Design), WCAG 2.2 (Níveis AA e AAA), Material Design 3 (M3)  
**Destinatário:** Engenheiro de Software e Equipa de Engenharia Android / SSDLC  

---

## 1. Enquadramento e Estratégia de Produto

### 1.1 Missão e Outcome
A aplicação móvel nativa Android **"Entre Quem Lê"** tem como missão guiar, informar e enriquecer a experiência dos visitantes da Feira do Livro de Vila Real. O produto foi desenhado para eliminar a fricção da consulta em suportes estáticos ou panfletos de papel, oferecendo uma navegação ágil, contextualizada ("o que está a decorrer agora"), inclusiva e disponível **100% offline** no recinto da feira (Jardim da Carreira / Centro de Vila Real).

### 1.2 Avaliação dos Quatro Riscos Fundamentais (Dual-Track / Marty Cagan)
1. **Risco de Valor (Value Risk):**
   - *Desafio:* O utilizador que visita uma feira ao ar livre abandona rapidamente aplicações lentas ou sobrecarregadas, preferindo perguntar no balcão ou consultar redes sociais.
   - *Mitigação:* Acesso em menos de 2 segundos à sessão em curso ou seguinte; favoritos de 1 toque; separação nítida por dia e categorias claras (Conversas, Apresentação de Livros, Infantil/Oficinas, Poesia, Música).
2. **Risco de Usabilidade (Usability Risk):**
   - *Desafio:* Público altamente heterogéneo (crianças em contexto escolar, famílias, estudantes universitários da UTAD e leitores seniores) em ambiente exterior com forte luminosidade natural.
   - *Mitigação:* Aderência rigorosa à WCAG 2.2 AA/AAA; contraste de cor mínimo superior a 4.5:1 (alcançando >7:1 no texto principal); alvos táteis (*touch targets*) de pelo menos 48x48dp; tipografia com suporte nativo a escalamento (*Dynamic Type* até 200%).
3. **Risco de Viabilidade Técnica (Feasibility Risk):**
   - *Desafio:* Em eventos municipais com aglomeração no recinto, a conectividade celular 4G/5G pode apresentar latência ou falhas temporárias.
   - *Mitigação:* Arquitetura *offline-first*. Todo o programa oficial dos 9 dias (27 sessões, biografias de autores e moderadores) é embutido na aplicação como asset JSON (`program_data.json`), carregando instantaneamente via Kotlin Serialization / Room. Notificações de lembretes funcionam localmente via `AlarmManager` / `WorkManager` sem necessidade de servidores externos.
4. **Risco de Viabilidade de Negócio e SSDLC (Business / Compliance Risk):**
   - *Desafio:* Garantir a conformidade com o RGPD, segurança do software e imagem institucional do Município de Vila Real.
   - *Mitigação:* Princípio de *Privacy by Design*. Zero rastreamento comportamental, zero anúncios publicitários, sem autenticação forçada e sem permissões intrusivas. Apenas a permissão de notificações locais (`POST_NOTIFICATIONS` no Android 13+) é solicitada, e estritamente no momento em que o utilizador guarda o primeiro evento como favorito.

---

## 2. Investigação e Síntese Centrada no Utilizador

### 2.1 Personas Arquetípicas
* **Sofia (34 anos) - Mãe e Educadora:**
  - *Objetivo:* Encontrar rapidamente atividades para os filhos (6 e 9 anos) durante a tarde de fim de semana ou pós-escola.
  - *Necessidade Crítica:* Filtro imediato de sessões infantis com indicação clara da faixa etária (ex: "Oficina Bárbara R. - 6 aos 12 anos", "Leitura de contos Anabela Nóbrega - 6 aos 10 anos").
* **Manuel (68 anos) - Leitor Sénior Transmontano:**
  - *Objetivo:* Assistir a conversas literárias com autores de renome (Dulce Maria Cardoso, Ana Margarida de Carvalho) e revisitar obras clássicas (Saramago por Pedro Lamares, escritores transmontanos).
  - *Necessidade Crítica:* Texto nítido de grande dimensão, botões confortáveis de tocar, facilidade em marcar os eventos na sua agenda pessoal e receber alerta 15 minutos antes.
* **Diana (21 anos) - Estudante da UTAD:**
  - *Objetivo:* Descobrir novas manifestações poéticas (Maria Caetano Vilalobos, Spoken Word) e o concerto de encerramento (Che Chabón).
  - *Necessidade Crítica:* Interface moderna, fluida e com visual atraente, navegação rápida por abas diárias.

---

## 3. Exploração de Alternativas e Justificação de Decisão

Como exigido pelo processo de engenharia de design (ISO 9241-210), foram exploradas e contrastadas três abordagens estruturais:

| Abordagem | Descrição | Avaliação Técnica & Usabilidade | Veredito |
| :--- | :--- | :--- | :--- |
| **Alternativa A: Visualizador Interativo de Cartaz / PDF** | Apresentação do poster oficial em ecrã inteiro com pan-and-zoom e zonas clicáveis. | **Falha Crítica de Acessibilidade:** Impossibilita leitura por leitores de ecrã (TalkBack); péssima ergonomia em ecrãs móveis verticais; sem busca, filtros ou lembretes. | **REJEITADA** |
| **Alternativa B: Feed Cronológico Linear Contínuo** | Lista vertical única contendo todas as 27 sessões dos 9 dias consecutivamente. | **Sobrecarga Cognitiva:** Obriga o utilizador no terreno a um scroll exaustivo para encontrar o dia corrente; desorientação temporal em eventos com múltiplos dias. | **REJEITADA** |
| **Alternativa C: Hub Modular com Abas Diárias + Filtros Rápidos (M3)** | Barra de abas superior com os 9 dias (Sex 20 a Sáb 28), chips de categorias temáticas, cartões semânticos e Bottom Navigation. | **Acessível, Eficaz e Intuitiva:** 1 toque para mudar de dia; 1 toque para filtrar categorias; alvos táteis >48dp; semântica TalkBack completa e offline instantâneo. | **ADOTADA** |

---

## 4. Arquitetura de Informação e Fluxos de Navegação

### 4.1 Estrutura de Menus (Bottom Navigation Bar)
1. **Hoje (`HomeScreen`):**
   - Destaque do dia selecionado (ou dia corrente do evento).
   - Abas dos 9 dias da Feira (`Sex 20`, `Sáb 21`, `Dom 22`, `Seg 23`, `Ter 24`, `Qua 25`, `Qui 26`, `Sex 27`, `Sáb 28`).
   - Barra de Chips de filtro: `Todos`, `Conversas & Debates`, `Apresentação de Livros`, `Infantil & Oficinas`, `Poesia & Recitais`, `Música`.
   - Lista vertical de cartões de sessão (`SessionCard`) com horário destacado, promotor (FNAC, Feira do Livro, Cultura a Dentro), oradores e botão de favorito.
2. **Programa (`ScheduleScreen`):**
   - Visão panorâmica de todo o festival com agrupamento cronológico e barra de pesquisa textual (pesquisa por nome de livro, autor ou moderador).
3. **Autores & Convidados (`SpeakersScreen`):**
   - Lista alfabética de todos os intervenientes (ex: Dulce Maria Cardoso, André Gago, Ana Zanatti, Pedro Lamares, Bárbara R., Alexandre Parafita, etc.), com minibiografia e ligação direta às sessões em que participam.
4. **Minha Agenda (`BookmarksScreen`):**
   - As sessões selecionadas pelo utilizador com a indicação temporal de contagem decrescente e ativação de lembrete de notificação local (15 minutos antes da sessão).
5. **Sobre a Feira (`InfoScreen`):**
   - Localização no Jardim da Carreira (Vila Real), horários de funcionamento, promotores institucionais (Câmara Municipal de Vila Real, FNAC, Cultura a Dentro), notas de acessibilidade física e créditos.

### 4.2 Estados da Interface (State Handling)
* **Loading State:** Shimmer semântico elegante respeitando a altura dos cartões.
* **Content State:** Apresentação da grelha de cartões com transições fluidas de elevação Material 3.
* **Empty State:** Ilustração literária acolhedora e texto explicativo quando não existem sessões para a combinação de filtros selecionada ou quando a lista de favoritos ainda não contém itens guardados.
* **Accessibility Fallback:** Rótulos semânticos explícitos em todos os elementos táteis para utilizadores invisuais ou de baixa visão.

---

## 5. Design System e Identidade Visual (Material Design 3)

### 5.1 Conceito Visual: *"Cultura, Território & Modernidade"*
A identidade visual foi buscar inspiração aos tons quentes da identidade do evento *"Entre Quem Lê"*, ao granito e ao xisto da região de Trás-os-Montes e Douro, e ao calor do papel literário de livro aberto.

### 5.2 Tokens de Cores Semânticas
| Token | Modo Claro (Light) | Modo Escuro (Dark) | Rácio de Contraste WCAG | Função Semântica |
| :--- | :--- | :--- | :--- | :--- |
| `primary` | `#8C3827` (Terracota) | `#FFB4A5` (Salmão Suave) | > 5.5:1 (AA/AAA) | Ações principais, destaques literários |
| `onPrimary` | `#FFFFFF` | `#561E14` | > 7.0:1 (AAA) | Texto sobre cor primária |
| `secondary` | `#8B5000` (Âmbar Quente) | `#FFB77B` (Âmbar Claro) | > 5.0:1 (AA) | Badges horárias e categorias complementares |
| `tertiary` | `#3E6837` (Verde Parque) | `#A4D39B` (Verde Pálido) | > 4.8:1 (AA) | Atividades infantis, ao ar livre e parceiros |
| `background` | `#FFF8F6` (Creme Papel) | `#1A1110` (Café Profundo) | Base | Fundo da aplicação suave para leitura |
| `onBackground`| `#231A18` (Carvão Leitura)| `#F1DFDC` (Creme Nítido) | > 12.0:1 (AAA) | Tipografia de leitura e títulos principais |
| `surfaceVariant`| `#F5DDD8` | `#534340` | Base de cartões | Contentor de cartões com relevo suave |

### 5.3 Escala Tipográfica (Jetpack Compose Typography)
* **Display / Headline:** Família Serifada clássica (`FontFamily.Serif`), conferindo o peso e a nobreza editorial de uma feira literária.
* **Body / Labels:** Sans-serif neutra com kerning calibrado (`FontFamily.SansSerif`), garantindo legibilidade perfeita a distâncias de braço e sob luz solar.

---

## 6. Auditoria de Acessibilidade e Inclusão (WCAG 2.2)

1. **Critério 1.4.3 / 1.4.6 (Contraste Mínimo e Aumentado):**
   - Todos os textos informativos cumprem um rácio de pelo menos 5.5:1 em relação ao fundo dos cartões, ultrapassando o requisito de 4.5:1 da norma AA.
2. **Critério 2.5.5 / 2.5.8 (Dimensão do Alvo de Toque):**
   - O botão de favoritos e os chips de navegação têm alvos mínimos de **48x48dp**, garantindo conforto tátil inclusive para pessoas com destreza motora reduzida ou uso em movimento.
3. **Critério 1.1.1 e 4.1.2 (Compatibilidade TalkBack):**
   - O botão de alternar favorito possui `contentDescription` dinâmico e contextual: *"Guardar [Título] na minha agenda de favoritos"* ou *"Remover [Título] da minha agenda de favoritos"*.
4. **Critério 1.4.4 e 1.4.12 (Redimensionamento de Texto):**
   - Layouts em `LazyColumn` e `Row` com quebra flexível e sem truncatura prematura, suportando ampliações de fonte até 200%.

---

## 7. Modelo de Dados e Handoff para Engenharia de Software

### 7.1 Ficheiro de Dados Estruturado
O ficheiro `data/program_data.json` já se encontra gerado no projeto com **100% da programação extraída do cartaz oficial**:
* **27 Sessões** distribuídas pelos 9 dias.
* **Biografias completas** de autores convidados (ex: Ana Margarida de Carvalho, Dulce Maria Cardoso, André Gago, Ana Zanatti, Pedro Lamares, José Anjos, Maria Caetano Vilalobos, etc.).
* **Promotores mapeados:** Câmara Municipal de Vila Real, FNAC, Cultura a Dentro.
* **Metadados de recinto e coordenadas geográficas.**

### 7.2 Entregáveis Técnicos na Workspace
* `docs/compose_prototype/EventModels.kt`: Classes de domínio Kotlin (`Session`, `EventCategory`, `Speaker`, `FestivalInfo`).
* `docs/compose_prototype/Color.kt`: Tokens semânticos Material 3 para temas Claro e Escuro.
* `docs/compose_prototype/Type.kt`: Configuração tipográfica editorial.
* `docs/compose_prototype/Theme.kt`: Wrapper de tema `EntreQuemLeTheme`.
* `docs/compose_prototype/SessionCard.kt`: Componente acessível com TalkBack e touch targets de 48dp.
* `docs/compose_prototype/HomeScreen.kt`: Ecrã completo com abas de dias, filtros e lista de sessões.
* `mockup_feira_livro_home.jpg`: Mockup visual de alta fidelidade da interface.

### 7.3 Recomendações SSDLC (Secure Software Development Life Cycle)
1. **Permissões Mínimas:** Apenas declarar `<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />` no `AndroidManifest.xml` se o utilizador ativar alarmes no Android 13+ (API 33+). Nenhuma permissão de localização, câmara ou leitura de ficheiros é necessária.
2. **Dependências Seguras:** Utilizar exclusivamente bibliotecas oficiais AndroidX / Jetpack Compose e KotlinX Serialization, sem SDKs de publicidade ou tracking de terceiros.
3. **Pipeline CI/CD GitHub Actions:**
   - Job de SAST: Executar `detekt` e `spotless` para análise estática de código e conformidade de estilo.
   - Job de Testes Unitários: Validar a deserialização correta de `program_data.json` e a lógica do repositório de favoritos.
   - Job de Build e Assinatura: Gerar o APK release compilado com ProGuard/R8 habilitado.
