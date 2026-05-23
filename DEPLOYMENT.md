# Google Play Store Deployment Guide

## Prerequisites
- Android Studio installed
- Google Play Developer Account ($25 one-time fee)
- Your app APK/AAB built

## Step-by-Step Deployment

### 1. Create Google Play Developer Account
- Visit https://play.google.com/apps/publish/
- Sign in with your Google account
- Pay $25 registration fee
- Complete account setup

### 2. Configure Your App

#### Update app/build.gradle.kts:
```gradle
android {
    compileSdk = 34
    
    defaultConfig {
        applicationId = "com.ekkyfish"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
    }
}
```

### 3. Create Signing Key

**In Android Studio:**
1. Build → Generate Signed Bundle/APK
2. Select "Android App Bundle (AAB)"
3. Click "Create new..." under Key store path
4. Fill in the details:
   - Key store path: Choose location
   - Password: Create strong password (save it!)
   - Key alias: "ekkyfish_key"
   - Validity: 10000 days
   - CN: Your name
5. Click "Create"
6. Select newly created keystore
7. Click "Next" and "Finish"

### 4. Build Release AAB

```bash
# Using Gradle
./gradlew bundleRelease

# APK will be at: app/build/outputs/bundle/release/app-release.aab
```

### 5. Upload to Google Play Console

1. Open https://play.google.com/console/
2. Click "Create app"
3. Fill in app details:
   - App name: "EkkyFish"
   - Default language: English
   - App or game: "App"
   - Free or paid: "Free"
4. Accept declaration and click "Create app"

### 6. Fill in App Information

#### App Access
- Click "App access" → "Declare app access"
- Select access types (if any)

#### Ads
- Click "Ads" → Declare if your app contains ads

#### Content Rating
1. Go to "Content rating"
2. Complete the questionnaire
3. Get your content rating certificate

#### Target Audience
- Go to "Target audience"
- Select appropriate categories

#### Content
- Add app icon (512x512 PNG)
- Add screenshots (up to 8)
- Add feature graphic (1024x500 PNG)
- Write short description (80 chars max)
- Write full description (4000 chars max)
- Add privacy policy URL

#### Pricing & Distribution
- Select "Free"
- Choose countries where available

### 7. Upload Release

1. Go to "Release" → "Production"
2. Click "Create new release"
3. Upload AAB file (app-release.aab)
4. Add release notes
5. Review and confirm
6. Click "Submit release"

### 8. Review & Publishing

- Google will review your app (2-4 hours typically)
- You'll receive notification when approved
- Your app goes live on Google Play Store!

## Free App Checklist

- ✅ No payment required
- ✅ Free app category selected
- ✅ Privacy policy provided
- ✅ Content rating completed
- ✅ Screenshot and graphics prepared
- ✅ Proper version code (starts at 1)
- ✅ Signed APK/AAB

## Important URLs for Google Play

- **Console**: https://play.google.com/console/
- **Play Store**: https://play.google.com/store/
- **Policy Center**: https://support.google.com/googleplay/android-developer/

## Post-Launch Updates

### To release updates:
1. Increment versionCode in build.gradle.kts
2. Update versionName (e.g., 1.0.1)
3. Build new release AAB
4. Go to Production release
5. Click "Create new release"
6. Upload new AAB
7. Add changelog
8. Submit for review

## Marketing Your App

After launch:
- Share link: `https://play.google.com/store/apps/details?id=com.ekkyfish`
- Add to social media
- Encourage user reviews
- Monitor ratings and feedback

## Support

For issues:
- Google Play Help: https://support.google.com/googleplay/
- Android Developer Support: https://developer.android.com/support

---

**Total Cost: $25 (one-time) for Google Play Developer Account**
**App Distribution: FREE!** 🎉