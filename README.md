<div align="center">

# 🛍️ Bizup Buyer App - Android Automation Framework

[![Java](https://img.shields.io/badge/Java-8+-orange.svg)](https://www.oracle.com/java/)
[![Appium](https://img.shields.io/badge/Appium-10.0.0-blue.svg)](http://appium.io/)
[![TestNG](https://img.shields.io/badge/TestNG-7.11.0-red.svg)](https://testng.org/)
[![Maven](https://img.shields.io/badge/Maven-Build-green.svg)](https://maven.apache.org/)

**Zero-commission B2B video shopping platform for the fashion industry**

*100% genuine sellers • Cash on Delivery • Wholesale rates*

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Key Features](#-key-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Installation & Setup](#-installation--setup)
- [Configuration](#-configuration)
- [Running Tests](#-running-tests)
- [Reports & Logs](#-reports--logs)
- [BrowserStack Integration](#-browserstack-integration)
- [Email Notifications](#-email-notifications)

---

## 🎯 Overview

This repository contains the **Android UI automation framework** for the **Bizup Buyer App** – a zero-commission B2B video shopping application connecting fashion buyers and sellers.

### What We Test

✅ **Login flows** – Language selection, authentication, permissions  
✅ **Search & Discovery** – Product search, seller profiles, video feeds  
✅ **Cart & Orders** – Add to cart, checkout flows, order management  
✅ **Multi-version support** – App versions `2.16.4 (143)`, `3.0.1 (148)`, `3.0.4 (151)`  
✅ **Test types** – Smoke & Regression suites per version

## ✨ Key Features

| Feature | Description |
|---------|-------------|
| 🎨 **Page Object Model** | Clean separation of test logic and UI elements (`buyer.pageObjects`) |
| 📦 **Versioned Test Suites** | Organized by app version: `143`, `148`, `151` |
| 🔧 **Centralized Driver Management** | Single `AndroidDriver` instance via `Base` class |
| 🛠️ **Reusable Utilities** | Common actions in `AndroidActions` (screenshots, JSON parsing, etc.) |
| 📊 **Rich Reporting** | ExtentReports with screenshots on failure |
| 📝 **Structured Logging** | Log4j2 configuration for debugging |
| ☁️ **BrowserStack Ready** | YAML configs for cloud device testing |
| 📧 **Email Notifications** | Automated HTML summary with test statistics |
| 🔄 **Retry Mechanism** | Auto-retry failed tests via `RetryAnalyzer` |

## 🔧 Tech Stack

<table>
<tr>
<td width="50%">

**Core Technologies**
- ☕ Java 8+
- 📱 Appium Java Client `10.0.0`
- 🧪 TestNG `7.11.0`
- 📦 Maven (Build & Dependency Management)

</td>
<td width="50%">

**Libraries & Tools**
- 📊 ExtentReports `5.1.2`
- 📝 Log4j2 `2.24.3`
- ☁️ BrowserStack Java SDK `1.39.1`
- 📧 JavaMail `1.6.2`
- 🔄 Jackson (JSON/YAML), Commons IO

</td>
</tr>
</table>

## 📁 Project Structure

```text
android-automation/
├── 📄 pom.xml                          # Maven dependencies & test profiles
├── 📄 README.md
├── 📂 testNGSuites/                    # TestNG XML suites
│   ├── testng_Smoke143.xml
│   ├── testng_Smoke148.xml
│   ├── testng_Smoke151.xml
│   ├── testng_Regression143.xml
│   ├── testng_Regression148.xml
│   └── testng_Regression151.xml
├── 📄 browserstack_app*.yml            # BrowserStack configs per version
│
├── 📂 src/
│   ├── 📂 main/java/buyer/
│   │   ├── 📂 pageObjects/             # Page Object Model classes
│   │   ├── 📂 resources/               # data.properties (config)
│   │   └── 📂 utils/                   # Shared utilities
│   ├── 📂 main/resources/
│   │   └── 📄 log4j2.xml               # Logging configuration
│   │
│   └── 📂 test/java/Bizupautomation/
│       ├── 📂 testCases/
│       │   ├── 📂 version_143/         # Tests for app v2.16.4
│       │   ├── 📂 version_148/         # Tests for app v3.0.1
│       │   └── 📂 version_151/         # Tests for app v3.0.4
│       ├── 📂 testData/                # Test data (JSON files)
│       └── 📂 testUtils/               # Base, AndroidActions, Listeners, etc.
│
├── 📂 reports/                         # ExtentReports HTML & screenshots
└── 📂 logs/                            # Log4j2 log files
```

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

| Tool | Version | Verification Command | Download Link |
|------|---------|---------------------|---------------|
| ☕ **Java JDK** | 8+ | `java -version` | [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html) |
| 📦 **Maven** | 3.8+ | `mvn -version` | [Maven](https://maven.apache.org/download.cgi) |
| 🟢 **Node.js & npm** | Latest LTS | `node -v` / `npm -v` | [Node.js](https://nodejs.org/) |
| 📱 **Appium** | Latest | `appium -v` | `npm install -g appium` |
| 🤖 **Android SDK** | Latest | `adb version` | [Android Studio](https://developer.android.com/studio) |

---

## 🚀 Installation & Setup

### Step 1: Clone the Repository

```bash
git clone <repository-url>
cd android-automation
```

### Step 2: Install Dependencies

```bash
# Install all Maven dependencies (includes Appium Java Client)
mvn clean install
```

> **Note:** The Appium Java Client is automatically downloaded as a Maven dependency from `pom.xml`. No need to install it separately via npm.

### Step 3: Install Appium Server

```bash
# Install Appium globally
npm install -g appium
```

### Step 4: Verify Installation

```bash
# Check all tools are properly installed
java -version
mvn -version
appium -v
adb version
```

---

## ⚙️ Configuration

### 1️⃣ Framework Settings (`data.properties`)

**Location:** `src/main/java/buyer/resources/data.properties`

```properties
# Appium Server Configuration
ipAddress=127.0.0.1
port=4723

# Device Configuration (for local execution)
Device1="Samsung SM-E146B"
Device2="Xiaomi Redmi Y1"
Devise3="Samsung SM-S711B"

# Email Configuration (for test reports)
EMAIL_USERNAME=your-email@example.com
EMAIL_PASSWORD=your-app-password

# BrowserStack Configuration
BROWSERSTACK_EMAIL=your-browserstack-email@example.com
BROWSERSTACK_BUILD_ID=your-build-id
```

> ⚠️ **Security:** Never commit real credentials to version control. Use environment variables or secret management tools.

### 2️⃣ BrowserStack Configs

Each app version has its own BrowserStack configuration:

| File | App Version | Purpose |
|------|-------------|---------|
| `browserstack_app151.yml` | 3.0.4 (151) | Latest version tests |
| `browserstack_app148.yml` | 3.0.1 (148) | Previous version tests |
| `browserstack_app143.yml` | 2.16.4 (143) | Legacy version tests |

**Key Configuration Fields:**
- `userName`, `accessKey` – BrowserStack credentials
- `app` – App under test (`bs://<app-id>`)
- `platforms` – Device & OS combinations
- `projectName`, `buildName` – Reporting metadata

### 3️⃣ Appium Server Path

Update `NODE_JS_MAIN_PATH` in `Bizupautomation.testUtils.Base`:

```java
// Windows Example
public static String NODE_JS_MAIN_PATH = 
    "C:\\Users\\<YourUser>\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";

// Linux/Mac Example
// public static String NODE_JS_MAIN_PATH = 
//     "/usr/local/lib/node_modules/appium/build/lib/main.js";
```

### 4️⃣ Final Setup Checklist

- [ ] Update `data.properties` with your device/email/BrowserStack details
- [ ] Adjust `NODE_JS_MAIN_PATH` in `Base.java`
- [ ] Ensure Bizup app is installed on device/emulator OR uploaded to BrowserStack
- [ ] Verify device is connected: `adb devices`

## 🧪 Running Tests

Tests are executed via **Maven profiles** that map to TestNG XML suites in `testNGSuites/`.

### 🔥 Smoke Test Suites

Quick sanity checks covering critical flows:

```bash
# App Version 3.0.4 (151) - Latest
mvn clean test -P Smoke151

# App Version 3.0.1 (148)
mvn clean test -P Smoke148

# App Version 2.16.4 (143) - Legacy
mvn clean test -P Smoke143
```

### 🔄 Regression Test Suites

Comprehensive test coverage:

```bash
# App Version 3.0.4 (151) - Latest
mvn clean test -P Regression151

# App Version 3.0.1 (148)
mvn clean test -P Regression148

# App Version 2.16.4 (143) - Legacy
mvn clean test -P Regression143
```

### 💻 Running from IDE

1. Open any test class under `Bizupautomation.testCases.version_xxx`
2. Ensure the class extends `Base`
3. Right-click → **Run as TestNG Test**

### 📊 Available Test Profiles

| Profile | Suite File | App Version | Test Type |
|---------|-----------|-------------|-----------|
| `Smoke151` | `testng_Smoke151.xml` | 3.0.4 (151) | Smoke |
| `Smoke148` | `testng_Smoke148.xml` | 3.0.1 (148) | Smoke |
| `Smoke143` | `testng_Smoke143.xml` | 2.16.4 (143) | Smoke |
| `Regression151` | `testng_Regression151.xml` | 3.0.4 (151) | Regression |
| `Regression148` | `testng_Regression148.xml` | 3.0.1 (148) | Regression |
| `Regression143` | `testng_Regression143.xml` | 2.16.4 (143) | Regression |

## 📊 Reports & Logs

### Test Reports

| Report Type | Location | Description |
|-------------|----------|-------------|
| 📈 **ExtentReports** | `reports/` | Rich HTML reports with test details |
| 📸 **Screenshots** | `reports/` | Auto-captured on test failures |
| 📋 **TestNG Reports** | `test-output/` | Standard TestNG HTML/XML reports |
| 📝 **Log Files** | `logs/` | Detailed execution logs (Log4j2) |

### Sample Report Structure

```text
reports/
├── TestReport_2024-11-23_14.html    # Timestamped HTML report
├── TestLogin.png                     # Screenshot on failure
├── TestCartFlow.png
└── ...

logs/
├── application.log                   # Main application log
└── ...
```

---

## ☁️ BrowserStack Integration

### Features

- ✅ Run tests on **real devices** in the cloud
- ✅ Parallel execution across multiple devices
- ✅ Automatic video recording of test sessions
- ✅ Network logs and device logs capture
- ✅ Dashboard with detailed test analytics

### Configuration

Each Maven profile automatically loads the corresponding BrowserStack YAML:

```bash
mvn clean test -P Smoke151
# Loads: browserstack_app151.yml
# Runs on: Samsung Galaxy S22 Ultra (Android 12)
```

### View Results

After execution, check your BrowserStack dashboard:
```
https://app-automate.browserstack.com/dashboard/v2/builds/<BUILD_ID>
```

---

## 📧 Email Notifications

### Automated Email Reports

After each test suite execution, an **HTML email summary** is automatically sent with:

✅ **Test Statistics** – Total, Passed, Failed, Skipped counts  
✅ **Per-Test Details** – Test name, description, and status  
✅ **BrowserStack Link** – Direct link to dashboard  
✅ **Attachments** – ExtentReports HTML and log files

### Email Configuration

1. Update `data.properties`:
   ```properties
   EMAIL_USERNAME=your-email@example.com
   EMAIL_PASSWORD=your-app-password
   ```

2. For Gmail, enable **App Passwords**:
   - Go to Google Account → Security → 2-Step Verification → App Passwords
   - Generate a new app password for "Mail"

3. Update recipient list in `Listeners.java`:
   ```java
   AndroidActions.sendReportEmailWithLogs(
       "qa-team@example.com, manager@example.com",
       "🚀 Bizup Buyer App Test Execution Report",
       emailBody,
       reportFilePath
   );
   ```

### Sample Email

![BizUp Mail - 🚀 Bizup Buyer App Test Execution Report_page-0001](https://github.com/user-attachments/assets/b8990746-90a3-4413-8954-63cb606d498b)

![BizUp Mail - 🚀 Bizup Buyer App Test Execution Report_page-0002](https://github.com/user-attachments/assets/29e984e8-e15a-4d0e-aeb7-bb159c2c3901)

---
