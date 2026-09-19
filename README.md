# Entre Quem Lê — Feira do Livro de Vila Real 2026

[![Android SSDLC CI/CD](https://github.com/JoseCunha/entre-quem-le-android/actions/workflows/ci.yml/badge.svg)](https://github.com/JoseCunha/entre-quem-le-android/actions/workflows/ci.yml)
[![Target SDK](https://img.shields.io/badge/Target%20SDK-35%20(Android%2015)-brightgreen.svg)](https://developer.android.com)
[![Min SDK](https://img.shields.io/badge/Min%20SDK-26%20(Android%208.0)-blue.svg)](https://developer.android.com)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose%20%2F%20Material%203-blueviolet.svg)](https://developer.android.com/jetpack/compose)
[![WCAG 2.2](https://img.shields.io/badge/Accessibility-WCAG%202.2%20AAA-success.svg)](https://www.w3.org/WAI/standards-guidelines/wcag/)
[![Privacy by Design](https://img.shields.io/badge/Privacy-100%25%20Offline%20%2F%20Zero%20Tracking-orange.svg)](#segurança-e-privacidade-ssdlc)

Aplicação móvel Android oficial da Feira do Livro de Vila Real e encontro literário **"Entre Quem Lê"** (3.ª Edição), que decorre de **18 a 26 de Setembro de 2026** nos **Claustros do Palácio do Conde de Amarante (antigo Governo Civil)**, promovida pelo **Município de Vila Real** com parcerias do **Teatro de Vila Real**, **Projeto Nortear** e **Granada UNESCO**.

---

## 📖 Visão Geral e Experiência do Utilizador

A aplicação foi desenhada para guiar visitantes locais, leitores seniores, crianças em idade escolar e a comunidade académica da UTAD, eliminando a dependência de cartazes físicos ou de ligações móveis instáveis no recinto:

- **100% Offline-First:** Toda a programação oficial (30 sessões, biografias completas de autores e promotores) está embutida nos assets locais (`assets/program_data.json`), garantindo arranque instantâneo e resposta em menos de 2 segundos.
- **Navegação Intuitiva por 9 Dias:** Acesso imediato a qualquer dia da feira com seletor horizontal de abas (`Sex 18` a `Sáb 26`).
- **Filtros Temáticos Rápidos:** Chips de filtragem para *Conversas & Debates*, *Apresentação de Livros*, *Infantil & Oficinas*, *Poesia & Recitais*, *Música* e *Formação*.
- **Agenda Pessoal de Favoritos:** Marcador de 1 toque para guardar sessões preferidas e consultar a agenda personalizada.
- **Catálogo de Autores & Convidados:** Lista detalhada de intervenientes com biografias completas (ex: Rui Reininho, Rui Zink, David Uclés, José Gardeazabal, Rui Cardoso Martins, Rui Lage, Marco Neves, Inês Bernardo, André Gago, entre outros).

---

## 🎨 Design System e Acessibilidade (Material 3 & WCAG 2.2)

- **Identidade Visual Curada:** Paleta inspirada nos tons quentes de Trás-os-Montes e do Douro — Terracota (`#8C3827`), Âmbar Quente (`#8B5000`) e Fundo Creme Papel (`#FFF8F6`) com suporte integral a Dark Mode.
- **Tipografia Editorial:** Títulos em família serifada clássica (`FontFamily.Serif`) e corpo de leitura em sem-serifa nítida de alta legibilidade sob forte luz solar.
- **Alvos Táteis Conformes:** Botões e chips com dimensões mínimas de 48x48dp (WCAG 2.5.5 / 2.5.8).
- **Semântica TalkBack:** Rótulos semânticos completos em todos os elementos táteis e estados de favoritos.
- **Rácio de Contraste AAA:** Contraste > 5.5:1 nas cores base e > 7:1 no texto principal.

---

## 🛡️ Segurança e SSDLC (Secure Software Development Life Cycle)

O projeto segue padrões de excelência de engenharia e ciclo de vida de desenvolvimento seguro:

1. **Privacy by Design:**
   - Zero dependências de servidores ou analítica externa.
   - Nenhuma permissão perigosa solicitada (sem permissão de rede `INTERNET`, sem geolocalização, sem acesso a ficheiros).
   - Apenas `POST_NOTIFICATIONS` declarada para lembretes locais opcionais no Android 13+ (API 33+).
   - `android:allowBackup="false"` no `AndroidManifest.xml` para proteger dados locais.
2. **SAST (Static Application Security Testing):**
   - Análise estática com **Detekt** (`config/detekt/detekt.yml`) para garantia de boas práticas e segurança de código Kotlin.
   - **Android Lint** com verificação rigorosa de segurança, performance e acessibilidade.
3. **Ofuscação e Minificação:**
   - ProGuard/R8 ativado no build `release` com encolhimento de código e de recursos (`isMinifyEnabled = true`, `isShrinkResources = true`).
4. **Supply Chain Security & Provenance:**
   - Verificação de checksums SHA-256 de todos os artefactos APK gerados.
   - Pipeline automatizado em GitHub Actions para build reproduzível e publicação de releases.

---

## 🛠️ Estrutura do Projeto

```
.
├── .github/workflows/
│   └── ci.yml               # Pipeline CI/CD SSDLC (SAST, Lint, Testes, Build APK)
├── app/
│   ├── src/main/
│   │   ├── assets/
│   │   │   └── program_data.json   # Base de dados oficial completa dos 9 dias
│   │   ├── java/pt/cmvilareal/entrequemle/
│   │   │   ├── data/               # Repositório e DTOs KotlinX Serialization
│   │   │   ├── model/              # Modelos de domínio (Session, Speaker, Category)
│   │   │   ├── ui/
│   │   │   │   ├── components/     # Componentes Compose acessíveis (SessionCard)
│   │   │   │   ├── screens/        # Ecrãs (Home, Schedule, Authors, Bookmarks, Info)
│   │   │   │   └── theme/          # Tokens Material 3 (Color, Type, Theme)
│   │   │   └── MainActivity.kt     # Ponto de entrada da aplicação Compose
│   │   └── res/                    # Vetores, ícones adaptativos e strings
│   ├── build.gradle.kts     # Configuração do módulo Android
│   └── proguard-rules.pro   # Regras R8/ProGuard
├── config/detekt/
│   └── detekt.yml           # Regras de SAST e qualidade de código
├── data/
│   └── program_data.json    # Dados de produto e especificação
├── docs/
│   └── PRODUCT_DESIGN_SPECIFICATION.md  # Especificação técnica e funcional
├── gradle/
│   ├── libs.versions.toml   # Catálogo de versões e dependências
│   └── wrapper/             # Gradle Wrapper 8.9
├── build.gradle.kts         # Configuração raiz
└── settings.gradle.kts      # Gestão de plugins e repositórios
```

---

## 🚀 Comandos de Build e Verificação

### Executar Testes Unitários
```bash
./gradlew testDebugUnitTest
```

### Executar Análise Estática SAST (Detekt)
```bash
./gradlew detekt
```

### Executar Android Lint
```bash
./gradlew lintDebug
```

### Compilar APK (Debug e Release)
```bash
./gradlew assembleDebug assembleRelease
```
Os ficheiros gerados situam-se em `app/build/outputs/apk/debug/app-debug.apk` e `app/build/outputs/apk/release/app-release-unsigned.apk`.

---

## 🏛️ Créditos e Organização

- **Promotor Principal:** Câmara Municipal de Vila Real
- **Parceiros Oficiais:** FNAC, Cultura a Dentro
- **Design e Engenharia:** Equipa Multica (Design de Produto, Engenharia de Software e Engenharia DevOps)
