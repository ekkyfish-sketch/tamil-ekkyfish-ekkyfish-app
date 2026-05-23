# Security Policy

## Reporting Security Issues

Please do NOT open public issues for security vulnerabilities.

Instead, email: security@ekkyfish.com

Include:
- Description of vulnerability
- Steps to reproduce
- Potential impact
- Suggested fix (if available)

## Security Best Practices

### API Security
- Use HTTPS only
- Validate all inputs
- Implement rate limiting
- Use API keys securely
- Rotate keys regularly

### Data Security
- Encrypt sensitive data
- Use ProGuard obfuscation
- Implement certificate pinning
- Secure token storage
- Clear sensitive data

### Authentication
- Use Firebase Auth
- Enforce strong passwords
- Implement 2FA ready
- Secure session management
- Logout on sensitive changes

### Database
- Implement proper rules
- Use parameterized queries
- Encrypt at rest
- Regular backups
- Access control

### Dependencies
- Keep updated
- Check vulnerabilities
- Use security scanner
- Pin versions
- Monitor advisories

## Vulnerability Management

1. **Report**: Email security concern
2. **Acknowledge**: Response within 48 hours
3. **Investigate**: Assess severity
4. **Fix**: Develop patch
5. **Release**: Issue security update
6. **Disclose**: Public notification

## Supported Versions

Security updates provided for:
- Current release
- Previous minor version
- Previous major version

## Security Updates

Critical security patches released:
- ASAP for critical issues
- Within 1 week for high severity
- Within 1 month for medium severity

## Third-party Dependencies

Regularly updated and scanned:
- Firebase
- Retrofit
- Room
- Hilt
- Stripe
- Google Play Services

## Compliance

- GDPR compliant
- CCPA ready
- PCI-DSS for payments
- OWASP guidelines
- Google Play policy

---

**Thank you for helping keep EkkyFish secure! 🔒**