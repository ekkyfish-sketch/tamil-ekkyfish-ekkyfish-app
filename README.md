# 🐟 EkkyFish - Android Fish Sales & Delivery App

Your ultimate fish ordering and delivery application. EkkyFish brings fresh, quality fish directly to your doorstep with real-time tracking and best prices guaranteed.

## About EkkyFish

**Version**: 1.0.0

EkkyFish is a modern Android application designed to revolutionize the way you order fresh fish online. With our user-friendly interface and robust features, we provide a seamless shopping experience for fish enthusiasts.

## Core Features

✅ **Fresh Fish Delivery** - High-quality fish delivered to your doorstep
✅ **Real-time Order Tracking** - Track your orders from preparation to delivery
✅ **Product Catalog** - Browse and view diverse fish products with detailed information
✅ **User Authentication** - Secure sign up and login functionality
✅ **Shopping Cart** - Add/remove items and manage quantities easily
✅ **Multiple Payment Options** - Various payment methods for your convenience
✅ **User Profiles** - Manage your account, addresses, and preferences
✅ **Order History** - Access and reorder from past purchases
✅ **Search & Filter** - Find products by type, price, and availability
✅ **Best Prices Guaranteed** - Competitive pricing with regular discounts
✅ **24/7 Customer Support** - Round-the-clock support for your queries
✅ **Real-time Updates** - Live inventory and delivery updates

## Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM + Repository Pattern
- **UI Framework**: Jetpack Compose & Material Design 3
- **Database**: Room Database (Local Storage)
- **Networking**: Retrofit + OkHttp
- **Dependency Injection**: Hilt DI
- **Authentication**: Firebase Authentication
- **Payment Gateway**: Stripe Integration
- **Logging**: Timber Logging
- **Minimum SDK**: Android 21 (API Level 21)
- **Target SDK**: Android 34 (Latest)

## Project Structure

```
ekkyfish-app/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/ekkyfish/
│   │   │   │   ├── ui/
│   │   │   │   ├── viewmodel/
│   │   │   │   ├── repository/
│   │   │   │   ├── database/
│   │   │   │   ├── network/
│   │   │   │   └── utils/
│   │   │   └── res/
│   │   └── test/
│   └── build.gradle.kts
└── gradle/
```

## Getting Started

### Prerequisites
- Android Studio (latest version)
- Java 11 or higher
- Android SDK 21+

### Installation

1. Clone the repository
```bash
git clone https://github.com/ekkyfish-sketch/tamil-ekkyfish-ekkyfish-app.git
cd tamil-ekkyfish-ekkyfish-app
```

2. Open in Android Studio
- File → Open → Select the project directory

3. Build the project
- Build → Make Project

4. Run on emulator or device
- Run → Run 'app'

## Configuration

### Firebase Setup
1. Create Firebase project at https://console.firebase.google.com
2. Download google-services.json
3. Place in `app/` directory

### Stripe Setup
1. Get API keys from https://stripe.com
2. Add to `local.properties`:
```properties
stripe_publishable_key=YOUR_KEY
stripe_secret_key=YOUR_KEY
```

## API Endpoints

- `GET /api/products` - Get all products
- `POST /api/orders` - Create order
- `GET /api/orders/{id}` - Get order details
- `POST /api/payments` - Process payment

## Architecture

The app follows MVVM architecture with Repository pattern:

```
UI Layer (Activities/Fragments)
    ↓
ViewModel Layer
    ↓
Repository Layer
    ↓
Data Layer (Database, API, SharedPreferences)
```

## Welcome Screen & App Information

The app features a beautiful welcome screen with:
- 🐟 **EkkyFish Branding** - Eye-catching app title and branding
- **App Description** - Clear value proposition
- **Key Features Showcase** - Highlights of 5 main features
- **Version Information** - Current app version (1.0.0)
- **Copyright Notice** - © 2024 EkkyFish. All rights reserved.

### Welcome Screen Features Highlighted:
1. Fresh fish delivery to your doorstep
2. Real-time order tracking
3. Multiple payment options
4. Best prices guaranteed
5. Customer support 24/7

## Contributing

1. Create a new branch for your feature
2. Commit your changes
3. Push to the branch
4. Create a Pull Request

## Development Status

**Current Version**: 1.0.0 (Initial Release)

### Upcoming Features:
- 🔄 Advanced search filters
- 📱 Mobile app optimization
- 💬 In-app messaging
- 🎁 Loyalty rewards program
- 📸 Product image gallery
- ⭐ Customer reviews and ratings

## Contact & Support

📧 **Email**: support@ekkyfish.com  
🌐 **Website**: www.ekkyfish.com  
📱 **Customer Support**: Available 24/7

## License

MIT License - See LICENSE file for details

## Company Information

**EkkyFish**
- Fresh Fish Delivery Service
- Founded: 2024
- Headquarters: India
- Mission: Bring fresh, quality fish to every doorstep

## Authors & Contributors

- **EkkyFish Team** - Core development
- **Contributors** - Community support

---

**Happy Ordering! 🐟**  
**Fresh Fish, Fast Delivery, Best Prices!**
