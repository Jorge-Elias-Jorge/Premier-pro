# Gerar o APK automaticamente

O workflow `.github/workflows/build-apk.yml` compila o aplicativo automaticamente em cada envio para a branch `main`.

## Como baixar o APK

1. Abra o repositório no GitHub.
2. Vá em **Actions**.
3. Abra o workflow **Build Android APK**.
4. Aguarde terminar com sucesso.
5. Em **Artifacts**, baixe **premier-pro-apk**.
6. Instale o arquivo `app-debug.apk` no celular.

## Funcionamento

- O aplicativo abre offline.
- Permite importar vídeo ou áudio.
- Possui preview, linha do tempo e exportação SRT.
- Gera legendas automáticas usando a API Groq/Whisper quando há internet e chave da API.
- A chave fica guardada apenas no armazenamento local do celular.
