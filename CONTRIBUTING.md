# Contributing to EkkyFish

Thank you for your interest in contributing! Here's how you can help.

## Code of Conduct

Be respectful, inclusive, and constructive in all interactions.

## How to Contribute

### 1. Fork the Repository
```bash
git clone https://github.com/ekkyfish-sketch/tamil-ekkyfish-ekkyfish-app.git
cd tamil-ekkyfish-ekkyfish-app
git remote add upstream https://github.com/ekkyfish-sketch/tamil-ekkyfish-ekkyfish-app.git
```

### 2. Create a Feature Branch
```bash
git checkout -b feature/your-feature-name
```

### 3. Make Your Changes

- Follow Kotlin style guide
- Write meaningful commit messages
- Add tests for new features
- Update documentation

### 4. Commit Your Changes
```bash
git add .
git commit -m "Add: describe your changes"
```

### 5. Push to Your Fork
```bash
git push origin feature/your-feature-name
```

### 6. Create a Pull Request

- Go to GitHub repository
- Click "New Pull Request"
- Select your branch
- Fill in PR description
- Reference related issues
- Submit for review

## Code Style Guidelines

### Kotlin
- Use 4-space indentation
- Follow Google Kotlin style guide
- Use meaningful variable names
- Add KDoc comments for public functions

### Android
- Use MVVM architecture
- Follow Material Design 3
- Use Compose for UI when possible
- Keep Activities/Fragments minimal

### Naming Conventions

```kotlin
// Classes and Objects
class UserViewModel : ViewModel()
data class User(val id: String, val name: String)

// Variables and Functions
val userName: String = "John"
fun getUserById(id: String): User?

// Constants
companion object {
    const val MAX_ITEMS = 100
}

// Private members
private val _isLoading = MutableStateFlow(false)
private fun loadData() { }
```

## Commit Message Format

```
<type>: <subject>

<body>

<footer>
```

### Types
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation
- `style`: Code style
- `refactor`: Code refactoring
- `test`: Tests
- `chore`: Build, dependencies

### Examples
```
feat: Add wishlist functionality
fix: Resolve cart calculation error
docs: Update API documentation
```

## Testing

### Write Tests
```kotlin
class UserViewModelTest {
    @Test
    fun testLogin() {
        // Test implementation
    }
}
```

### Run Tests
```bash
./gradlew test
./gradlew connectedAndroidTest
```

## Pull Request Checklist

- [ ] Code follows style guidelines
- [ ] Tests written and passing
- [ ] Documentation updated
- [ ] No breaking changes
- [ ] PR description is clear
- [ ] Commits are meaningful

## Reporting Issues

### Bug Report Template
```
**Description**
Clear description of the issue

**Steps to Reproduce**
1. Step 1
2. Step 2
3. Step 3

**Expected Behavior**
What should happen

**Actual Behavior**
What actually happens

**Screenshots**
If applicable

**Device Info**
- Device: iPhone 12
- Android Version: 12
- App Version: 1.0.0
```

## Feature Request

### Feature Template
```
**Description**
What feature would you like?

**Use Case**
Why is this feature needed?

**Proposed Solution**
How should it work?

**Alternatives**
Other solutions considered
```

## Review Process

1. **Submitted**: PR is created
2. **In Review**: Maintainers review code
3. **Changes Requested**: Make updates if needed
4. **Approved**: PR is approved
5. **Merged**: PR is merged to main

## Development Setup

See `QUICKSTART.md` for detailed setup instructions.

## Build Commands

```bash
# Build debug
./gradlew assembleDebug

# Build release
./gradlew bundleRelease

# Run tests
./gradlew test

# Check lint
./gradlew lint

# Generate documentation
./gradlew dokka
```

## Performance Guidelines

- Avoid blocking main thread
- Use coroutines for async operations
- Optimize database queries
- Cache API responses
- Compress images
- Profile with Android Profiler

## Documentation

Update documentation when:
- Adding new features
- Changing API endpoints
- Modifying architecture
- Adding dependencies

## Community

- Discussions: GitHub Discussions
- Issues: GitHub Issues
- Email: support@ekkyfish.com

## Recognition

Contributors will be:
- Added to CONTRIBUTORS.md
- Mentioned in releases
- Recognized in documentation

## Questions?

Feel free to ask in:
- GitHub Discussions
- GitHub Issues
- Email

---

**Thank you for contributing! 🐟**