# EkkyFish - Android Fish Sales App

A complete Android application for fish sales with product catalog, user authentication, shopping cart, and payment integration.

## Features

✅ **Product Catalog** - Browse and view fish products
✅ **User Authentication** - Sign up and login functionality
✅ **Shopping Cart** - Add/remove items and manage quantities
✅ **Payment Integration** - Secure payment processing
✅ **User Profiles** - Manage user account and orders
✅ **Search & Filter** - Find products easily
✅ **Order History** - Track past purchases
✅ **Real-time Updates** - Live inventory updates

## Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM + Repository Pattern
- **UI Framework**: Jetpack Compose & Traditional XML layouts
- **Database**: Room Database
- **Networking**: Retrofit + OkHttp
- **Dependency Injection**: Hilt
- **Authentication**: Firebase Auth
- **Payment**: Stripe Integration

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

## Contributing

1. Create a new branch for your feature
2. Commit your changes
3. Push to the branch
4. Create a Pull Request

## License

MIT License - See LICENSE file for details

## Support

For support, email: support@ekkyfish.com

## Authors

- **EkkyFish Team** - Initial work

---

**Happy Coding! 🐟**
