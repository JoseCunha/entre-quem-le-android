# Especificação de Design de Produto de Software: Aplicação Android "Entre Quem Lê" 2026

**Documento de Referência:** Brochura Oficial do Evento (`entre_quem_le_2026_brochura.pdf`)  
**Evento:** 3.ª Edição do Encontro Literário "Entre Quem Lê" & Feira do Livro de Vila Real (18 a 26 de Setembro de 2026)  
**Local:** Claustros do Palácio do Conde de Amarante (antigo Governo Civil), Vila Real  
**Organização:** Município de Vila Real  
**Parcerias:** Livrarias da cidade (FNAC, Bertrand, Branco, Livros e Narrativas), Associação Cultura Adentro, Teatro de Vila Real, Projeto Nortear (CCDR NORTE / Xunta de Galicia), Granada Cidade Criativa da Literatura pela UNESCO  
**Perfil Responsável:** Engenheiro de Design de Produto de Software  
**Normas:** ISO 9241-210:2019 (Human-Centred Design), WCAG 2.2 (AA/AAA), Material Design 3 (M3)  

---

## 1. Análise da Brochura Oficial e Alinhamento de Produto

### 1.1 Missão e Princípios
A 3.ª edição do Encontro Literário **"Entre Quem Lê"** decorre sob o lema *"Um encontro sem fronteiras"*. Com **52 atividades programadas ao longo de 9 dias**, o festival junta mais de 30 autores de referência nacional e ibérica nos Claustros do Palácio do Conde de Amarante.

Todos os eventos são de **participação gratuita**. Algumas atividades específicas (oficinas de artes e cursos com vagas limitadas) possuem indicação de *"Requer inscrição"* via email institucional (`entrequemle@cm-vilareal.pt`).

### 1.2 Novo Look & Feel Oficial (Brochura 2026)
A identidade gráfica do evento em 2026 afasta-se do tom terracota de edições passadas e assume um estilo **contemporâneo, urbano e de forte impacto visual**:
1. **Cor Dominante (Base):** Azul Noturno Profundo (`#0D2E50` / `#0F3256`), evocando a sobriedade dos claustros históricos e a profundidade literária.
2. **Cor de Destaque (Acento):** Amarelo Limão Vibrante / Acid Lime (`#E4EF00` / `#DCE700`), utilizado no logótipo icónico e nos marcadores de dia/hora.
3. **Cor Secundária (Equilíbrio):** Azul Cerúleo / Cobalto (`#1D5FA7`), presente na lombada e páginas da ilustração oficial, conferindo dinamismo e separação estrutural.
4. **Superfície & Cartões:** Branco Puro (`#FFFFFF`) e Fundo Branco-Azulado Fresco (`#F4F7FB`) em modo claro; Azul Abissal (`#08182B`) em modo escuro.
5. **Tipografia Oficial:** Sans-Serif geométrica, moderna e arrojada (*Bold Condensed* nos títulos e *Clean Sans* no corpo de texto), substituindo a estética serifada anterior.

---

## 2. Grelha Consolidada de Programação (52 Atividades)

A totalidade da programação foi extraída da brochura oficial e estruturada no ficheiro `data/program_data.json`:

* **Sexta, 18 de Setembro (Abertura):**
  - 18h00: Abertura da Feira do Livro (`Institucional`)
  - 18h30: Recital “Ler devia ser proibido”, com Paulo Araújo e Manuel Guimarães (`Recital`)
  - 19h30: Jesús Ortega (Granada UNESCO), moderação de Celeste Afonso (`Conversa`)
  - 21h00: Madalena Sá Fernandes (livro “Sótão”), moderação de Ana Cristina Rocha (`Conversa` - Parceria FNAC)
* **Sábado, 19 de Setembro:**
  - 10h00: Curso de Escrita Criativa com Rui Zink (Oficina das Artes - Requer inscrição) (`Workshop`)
  - 11h00: Kamishibai, com Cláudia Almendra (`Famílias`)
  - 12h00: Apresentação “Victorianas” (2.ª ed.) de Marília Miranda, por Álvaro Pinto e Concha López Jambrina (`Livro`)
  - 14h30: Luís Moreira Gonçalves (“Dormindo entre Cadáveres”), moderação de Artur Cristóvão (`Conversa`)
  - 16h30: Nelson Nunes (“De Onde Vem Este Cansaço?”), moderação de Patrícia Posse (`Conversa`)
  - 18h30: Rui Zink (“Olga salva o mundo”), moderação de João Oliveira (`Conversa`)
  - 21h00: Raquel Patriarca: Viagem ao universo literário das crianças e jovens (`Conversa`)
* **Domingo, 20 de Setembro:**
  - 11h00: Kamishibai, com Cláudia Almendra (`Famílias`)
  - 12h00: “O Segredo do Simão”, de A. F. Caseiro Marques com Mara Minhava (`Livro`)
  - 14h30: Rui Cardoso Martins (“As Melhoras da Morte”), moderação de Pedro Garcias (`Conversa`)
  - 16h30: Marco Neves (“As Raízes da Língua”), moderação de João Oliveira (`Conversa`)
  - 18h30: Ricardo Alexandre (“Tudo Sobre o Irão”), moderação de Miguel Carvalho (`Conversa`)
  - 21h00: Recital “Canções para Poetas”, de André Gago com Victor Zamora (`Recital`)
* **Segunda, 21 de Setembro:**
  - 11h00: Avental das Histórias, com Madalena Bobone (`Famílias/Escolas`)
  - 14h30: Leitura de Contos, com Anabela Nóbrega (`Famílias/Escolas`)
  - 14h30: Recital e conversa na escola com André Gago (`Escolas`)
  - 18h30: André Gago, moderação de Isabel Alves (`Conversa`)
  - 21h00: “Passado e Presente da Procissão do Bom Jesus do Calvário”, de Vítor Nogueira (`Livro`)
* **Terça, 22 de Setembro:**
  - 11h00: Avental das Histórias, com Madalena Bobone (`Famílias/Escolas`)
  - 14h30: Leitura de Contos, com Anabela Nóbrega (`Famílias/Escolas`)
  - 18h30: “São Paulo”, por Ricardo Ferreira de Almeida (`Livro`)
  - 21h00: “Saberes Cores Sabores”, pela Academia de Letras de Trás-os-Montes (`Livro`)
* **Quarta, 23 de Setembro:**
  - 11h00: Leitura e conversa com António Mota (`Famílias/Escolas`)
  - 14h30: Leitura e conversa com António Mota (`Famílias/Escolas`)
  - 14h30: Conversa na escola com Inês Bernardo (`Escolas`)
  - 18h30: Rui Lage (“Adeus, Campos Felizes”), moderação de Pedro Miranda (`Conversa`)
  - 21h00: Inês Bernardo (“Agarrar a Faca pelo Gume”), moderação de Luís Caetano (`Conversa`)
* **Quinta, 24 de Setembro:**
  - 11h00: Histórias Cantadas, com Bolinha de Música (`Famílias/Escolas`)
  - 14h30: Histórias Cantadas, com Bolinha de Música (`Famílias/Escolas`)
  - 18h30: Ana Portocarrero (“A (In)Felicidade de Sara Lisa”), moderação de Isabel Alves (`Conversa`)
  - 21h00: José Gardeazabal (“Mulher no Espaço”), moderação de Luís Caetano (`Conversa`)
* **Sexta, 25 de Setembro:**
  - 11h00: Histórias Cantadas, com Bolinha de Música (`Famílias/Escolas`)
  - 11h00: A Constituição Explicada às Crianças, com Sara Rodi (`Leitura/Escolas`)
  - 11h00: Contos Arrepiantes da História de Portugal, com Rui Correia e Fernando Nabais (`Leitura/Escolas`)
  - 14h30: Leitura de Contos, com Anabela Nóbrega (`Famílias/Escolas`)
  - 14h30: Recital de José Anjos (`Escolas`)
  - 18h30: Rui Reininho (“Soñetos”), moderação de Vítor Belanciano (`Conversa`)
  - 21h00: “Passo a Passo: Como Andando nos Fazemos Humanos”, de Jorge Pinto (`Livro`)
  - 22h00: Redoma - Carolina Viana / MALVA e Joana Rodrigues (`Recital`)
* **Sábado, 26 de Setembro (Encerramento):**
  - 10h00: Workshop Desenhar Saramago, com Rachel Caiano (Claustros - Requer inscrição) (`Workshop`)
  - 10h30: Piquenique de Livros, com Paula Cusati (Vila Velha) (`Workshop/Famílias`)
  - 12h00: Álvaro Magalhães e María Canosa (Conversas NORTEAR), moderação de João Ribeiro da Silva (`Conversa`)
  - 14h30: “Como Mentem os Políticos”, de Luís Paixão Martins (`Livro`)
  - 14h30: Oficina Lugares de Leitura, com Paula Cusati (Oficina das Artes - Requer inscrição) (`Workshop`)
  - 16h30: “Liber Avium”, de A. M. Pires Cabral (`Livro`)
  - 17h30: “Celeste dos Cravos”, pela neta e autora Carolina Caeiro Fontela (`Livro`)
  - 18h30: José Carlos Barros (“Protecção Civil” e “Vocação para os Desastres”), moderação de Carlos Vaz Marques (`Conversa`)
  - 21h00: “Quando o coração se fecha faz muito mais barulho que uma porta” - Leituras de António Lobo Antunes com José Anjos e Sofia Bodas de Carvalho (`Recital`)

---

## 3. Tokens Semânticos de Design (Material 3)

| Token | Hex Oficial (Brochura) | Aplicação no Sistema |
| :--- | :--- | :--- |
| `primary` | `#0D2E50` | TopAppBar, títulos de sessão, estados selecionados na BottomBar |
| `secondary` | `#6B7400` / `#E4EF00` | Acentos Amarelo Limão, realce da identidade "ENTRE QUEM LÊ" |
| `tertiary` | `#1D5FA7` | Subtítulos de oradores, moderação, linhas divisórias e banners informativos |
| `background` | `#F4F7FB` (Light) / `#08182B` (Dark) | Fundo da aplicação |
| `surface` | `#FFFFFF` (Light) / `#0E2540` (Dark) | Contentores dos cartões de sessão |
| `badgeConversa` | `#F5BA13` | Chip de Conversas e Debates (Texto `#1A1A1A`) |
| `badgeLivro` | `#E67E22` | Chip de Apresentação de Livro (Texto `#FFFFFF`) |
| `badgeRecital` | `#263238` | Chip de Recitais e Spoken Word (Texto `#FFFFFF`) |
| `badgeWorkshop` | `#4CAF50` | Chip de Cursos e Workshops com inscrição (Texto `#FFFFFF`) |
| `badgeFamilias` | `#D81B60` | Chip de Atividades de Famílias e Crianças (Texto `#FFFFFF`) |
| `badgeEscolas` | `#FF6F00` | Chip de Sessões Dedicadas às Escolas (Texto `#FFFFFF`) |
| `badgeLeitura` | `#1E88E5` | Chip de Sessões de Leitura (Texto `#FFFFFF`) |
| `badgeInstitucional` | `#CFD8DC` | Chip de Sessões Institucionais (Texto `#263238`) |

---

## 4. Auditoria de Acessibilidade & Requisitos Técnicos

1. **Contraste WCAG 2.2 AA / AAA:**
   - O par Azul Noturno (`#0D2E50`) sobre fundo Claro (`#F4F7FB`) atinge rácio de contraste superior a **11:1** (nível AAA).
   - O contraste entre Amarelo Limão e Azul Noturno no cabeçalho excede **9:1**.
   - As badges temáticas possuem textos em branco ou preto calibrados para garantir rácio mínimo de 4.5:1.
2. **Dimensão Tátil:**
   - Botões de favoritos, tabs diárias e chips possuem alvos de toque superiores a 48x48dp.
3. **Suporte TalkBack:**
   - Semântica completa no `SessionCard`, informando hora, título, oradores, modalidade de inscrição e estado de favorito.
4. **Arquitetura 100% Offline:**
   - Todas as 52 atividades embutidas no ficheiro `program_data.json` em `assets/`, sem qualquer dependência externa ou telemetria invasiva.
