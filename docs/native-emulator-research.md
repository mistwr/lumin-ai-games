# LUMIN AI GAMES — auditoria de bases open source (2026-10-28)

## Objetivo
Substituir a experiência HTML experimental por frontend Android nativo com Biblioteca, Descobrir, Importar e Jogar, capas, saves e controlos.

## Projetos identificados
- Omnidroid: https://github.com/SykikXO/Omnidroid — Kotlin/Jetpack Compose/Libretro; GPL-3.0. Investigar viabilidade de fork, submodules, compilação e distribuição de atribuições/código-fonte.
- Lemuroid: https://github.com/swordfish90/lemuroid — GPL-3.0; referência para emulação Android.
- eOr: https://github.com/keweis2/eOr — biblioteca, metadados e arte. Confirmar licença antes de copiar código.
- Titanius Launcher: https://github.com/dsolonenko/titanius-launcher — MIT; referência para launcher.
- Homebrew Hub API: https://github.com/gbdev/homebrewhub/blob/main/API.md — /api/all, /api/search, /api/entry/<slug>.json; até 10 entradas/página.

## Defeitos confirmados no protótipo atual
- Catálogo remoto apresenta "Failed to fetch" no Android. Possíveis causas: CORS, rede, URL e comportamento WebView; não afirmar causa sem logs.
- Consolas são cartões estáticos.
- Controlos e save states dependem da UI padrão do EmulatorJS.
- Falta ícone e branding Android.
- Não existe correspondência com o nível de interface apresentado nas referências.

## Plano de engenharia reversa permitida
1. Verificar código, licenças, dependências e build reproduzível do Omnidroid. Não misturar código GPL com distribuição proprietária incompatível.
2. Criar protótipo Android nativo num ramo separado, sem alterar a produção existente.
3. Criar biblioteca local com capas, favoritos e continuar a jogar.
4. Integrar fontes homebrew com licença verificada e cache local; não agregar ROMs comerciais não autorizadas.
5. Integrar saves nativos e testar persistência entre sessões.
6. Validar APK instalada em Android antes de declarar concluído.

## Estado
Documento de pesquisa; nenhuma migração nativa nem APK final foi implementada por este documento.
