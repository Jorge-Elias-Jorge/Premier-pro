# Gerar o APK do Premier Pro

O aplicativo Android abre a interface real do OpenCut hospedada online.

## 1. Publicar o OpenCut

Siga o guia `docs/PUBLICAR-OPENCUT.md` e publique `apps/web` no Vercel.

## 2. Configurar a URL

Crie o secret no GitHub:

```text
Name: OPENCUT_WEB_URL
Value: https://seu-opencut.vercel.app
```

## 3. Gerar o APK

O workflow `.github/workflows/build-apk.yml` gera o APK automaticamente em cada push para `main`.

Você também pode executá-lo manualmente em **Actions → Build Android APK → Run workflow**.

## 4. Baixar e instalar

1. Abra **Actions**.
2. Abra a execução concluída.
3. Baixe o artefato **premier-pro-apk**.
4. Extraia o ZIP.
5. Instale `app-debug.apk` no celular.

O aplicativo abrirá a interface OpenCut real, adaptada ao tamanho da tela do celular.
