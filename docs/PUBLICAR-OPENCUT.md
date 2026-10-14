# Publicar o OpenCut real na internet

Para o aplicativo Android abrir a interface completa do OpenCut, é preciso publicar `apps/web` online.

## Opção recomendada: Vercel

1. Crie uma conta gratuita em https://vercel.com.
2. Importe o repositório `Jorge-Elias-Jorge/Premier-pro`.
3. Em **Root Directory**, selecione `apps/web`.
4. Configure as variáveis de ambiente exigidas pelo projeto, se houver.
5. Faça o deploy.
6. Copie o endereço final, por exemplo:

```text
https://seu-opencut.vercel.app
```

## Configurar o APK

No GitHub, vá em:

**Settings → Secrets and variables → Actions → New repository secret**

Crie:

```text
Name: OPENCUT_WEB_URL
Value: https://seu-opencut.vercel.app
```

Depois, execute novamente o workflow **Build Android APK**.

O APK gerado abrirá automaticamente a interface real do OpenCut, com os mesmos painéis, timeline, preview e ferramentas da versão de computador, adaptados ao tamanho do celular.
