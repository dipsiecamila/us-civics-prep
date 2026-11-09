#!/bin/bash
set -e

PROYECTO_DIR="$HOME/dsrrllo/us-civics-prep"
DESCARGAS_DIR="$HOME/dsrrllo"
ADB_BIN="$HOME/Android/Sdk/platform-tools/adb"

# 1. Buscar el archivo ZIP más reciente
ZIP_FILE=$(ls -t "$DESCARGAS_DIR"/*civics*.zip 2>/dev/null | head -n 1)

if [ -n "$ZIP_FILE" ]; then
    echo "📦 Procesando archivo ZIP: $ZIP_FILE..."
    mkdir -p "$PROYECTO_DIR"
    unzip -o "$ZIP_FILE" -d "$PROYECTO_DIR"
    rm "$ZIP_FILE"
else
    echo "ℹ️ No hay ZIP nuevo en $DESCARGAS_DIR, procesando estado actual en $PROYECTO_DIR..."
fi

cd "$PROYECTO_DIR"

# 2. Inyectar ruta del SDK de Android
echo "sdk.dir=$HOME/Android/Sdk" > local.properties

# 3. Generar debug.keystore si no existe
if [ ! -f "debug.keystore" ]; then
    echo "🔑 Generando debug.keystore de firma..."
    keytool -genkey -v -keystore debug.keystore -storepass android -alias androiddebugkey -keypass android -keyalg RSA -keysize 2048 -validity 10000 -dname "CN=Android Debug,O=Android,C=US" 2>/dev/null || true
fi

# 4. Generar gradlew si no existe
if [ ! -f "gradlew" ]; then
    echo "⚙️  Generando wrapper de Gradle..."
    gradle wrapper || true
fi

chmod +x gradlew 2>/dev/null || true

# 5. Sincronizar con Git / GitHub
if [ ! -d ".git" ]; then
    echo "🌱 Inicializando repositorio de Git..."
    git init
    git add .
    git commit -m "Initial commit desde AI Studio"
    gh repo create us-civics-prep --public --source=. --push || true
else
    echo "🚀 Guardando nueva versión en Git y subiendo a GitHub..."
    git add .
    git commit -m "Actualización automática desde AI Studio: $(date '+%Y-%m-%d %H:%M:%S')" || true
    git push origin master || git push origin main || true
fi

# 6. Desinstalar versión anterior con la ruta absoluta de ADB
echo "🧹 Limpiando versión anterior en el Pixel 7a..."
if [ -f "$ADB_BIN" ]; then
    "$ADB_BIN" uninstall com.aistudio.civicsprep.kqmzr 2>/dev/null || true
fi

# 7. Compilar e instalar en el Pixel 7a
echo "📱 Compilando e instalando en tu Pixel 7a..."
./gradlew installDebug

echo "✅ ¡PROCESO COMPLETADO EXITOSAMENTE! La app está actualizada en tu Pixel 7a y en GitHub."
