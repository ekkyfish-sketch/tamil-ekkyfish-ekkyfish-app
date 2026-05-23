# EkkyFish Android App - Quick Start Guide

## 📱 Installation & Setup

### 1. Clone Repository
```bash
git clone https://github.com/ekkyfish-sketch/tamil-ekkyfish-ekkyfish-app.git
cd tamil-ekkyfish-ekkyfish-app
```

### 2. Open in Android Studio
- Launch Android Studio
- File → Open → Select project directory
- Wait for Gradle sync

### 3. Configure Firebase

1. **Create Firebase Project**
   - Visit https://console.firebase.google.com
   - Click "Add project"
   - Name it "EkkyFish"
   - Enable Google Analytics
   - Create project

2. **Add Android App**
   - In Firebase console, add Android app
   - Package name: `com.ekkyfish`
   - Download `google-services.json`
   - Place in `app/` directory

3. **Enable Firebase Services**
   - Authentication → Email/Password
   - Realtime Database → Create database
   - Storage → Create bucket
   - Cloud Messaging → Enable

### 4. Configure Local Properties

Create `local.properties` in root:
```properties
sdkDir=/path/to/Android/sdk
stripe_publishable_key=YOUR_STRIPE_KEY
stripe_secret_key=YOUR_SECRET_KEY
```

### 5. Build & Run

```bash
# Build the project
./gradlew clean build

# Run on emulator or device
./gradlew installDebug
```

## 🔐 API Configuration

Update `app/src/main/java/com/ekkyfish/di/AppModule.kt`:

```kotlin
const val BASE_URL = "https://your-api.com/"
```

## 📦 Building Release APK/AAB

### Create Signing Key (First Time)
```
keytool -genkey -v -keystore ekkyfish.keystore -keyalg RSA -keysize 2048 -validity 10000 -alias ekkyfish_key
```

### Build Release Bundle
```bash
./gradlew bundleRelease
# Output: app/build/outputs/bundle/release/app-release.aab
```

### Build Release APK
```bash
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk
```

## 🚀 Deploy to Google Play Store

See `DEPLOYMENT.md` for detailed instructions

## 🧪 Testing

### Run Unit Tests
```bash
./gradlew test
```

### Run Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

### Static Analysis
```bash
./gradlew lint
```

## 🐛 Debugging

### Enable Logging
Logs are automatically enabled via Timber in debug builds

### View Logs
```bash
adb logcat
```

### Remote Debugging
- Connect device via USB
- Enable Developer Mode
- Enable USB Debugging
- Run: `adb devices`

## 📱 Device Requirements

- **Minimum SDK**: Android 5.0 (API 21)
- **Target SDK**: Android 14 (API 34)
- **RAM**: 2GB minimum
- **Storage**: 100MB minimum

## 🌐 Network Configuration

Add `res/xml/network_security_config.xml`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="false">
        <domain includeSubdomains="true">api.ekkyfish.com</domain>
    </domain-config>
</network-security-config>
```

## 💾 Database

### Room Database
- Auto-created on first run
- Located in app-specific directory
- No additional setup needed

### Firebase Realtime Database
- Rules configured in Firebase Console
- Sync enabled by default

## 🎨 Customization

### Change App Name
Edit `app/src/main/res/values/strings.xml`:
```xml
<string name="app_name">Your App Name</string>
```

### Change App Icon
Replace files in `app/src/main/res/mipmap-*/`

### Change Colors
Edit `app/src/main/java/com/ekkyfish/ui/theme/Color.kt`

## 📚 Project Structure

```
app/src/main/
├── java/com/ekkyfish/
│   ├── MainActivity.kt
│   ├── EkkyFishApp.kt
│   ├── data/
│   │   ├── local/      (Room Database)
│   │   ├── remote/     (API Services)
│   │   ├── model/      (Data Classes)
│   │   └── repository/ (Data Access)
│   ├── ui/
│   │   ├── screens/    (Composables)
│   │   ├── theme/      (Material Design)
│   │   └── navigation/ (Navigation Logic)
│   ├── viewmodel/      (ViewModels)
│   ├── di/             (Dependency Injection)
│   ├── services/       (Background Services)
│   ├── utils/          (Utilities)
│   └── workers/        (WorkManager)
└── res/
    ├── values/
    ├── drawable/
    ├── mipmap/
    └── xml/
```

## 🔧 Troubleshooting

### Gradle Sync Issues
```bash
./gradlew clean
./gradlew syncDebugSources
```

### Build Errors
```bash
./gradlew clean build --stacktrace
```

### Firebase Issues
- Ensure `google-services.json` is in `app/` directory
- Check Firebase console permissions
- Verify database rules

### Emulator Issues
- Use API 28+ for best compatibility
- Enable hardware acceleration
- Allocate 4GB+ RAM to emulator

## 📝 Code Style

- Language: Kotlin
- Architecture: MVVM
- Pattern: Repository
- Naming: camelCase for variables, PascalCase for classes

## 🤝 Contributing

See `CONTRIBUTING.md`

## 📄 License

MIT License - See `LICENSE` file

## 💬 Support

- Email: support@ekkyfish.com
- GitHub Issues: Report bugs here
- Documentation: Check README.md

---

**Happy Coding! 🐟**