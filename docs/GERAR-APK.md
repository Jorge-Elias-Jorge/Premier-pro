# Gerar o APK do Premier Pro

1. Instale Node.js 20+, Bun, JDK 17 e Android Studio com SDK 35.
2. Crie `apps/web/.env.local` com `VITE_GROQ_API_KEY=sua_chave`.
3. Na raiz: `bun install`.
4. Em `android/`: `npm install`.
5. Gere a interface: `bun run build:web`.
6. Se houver exportação estática: `mkdir -p android/www && cp -r apps/web/out/* android/www/`.
7. Sincronize: `cd android && npm run cap:sync`.
8. Gere o APK: `npm run apk:debug`.
9. Instale: `adb install android/app/build/outputs/apk/debug/app-debug.apk`.

O APK fica em `android/app/build/outputs/apk/debug/app-debug.apk`.
