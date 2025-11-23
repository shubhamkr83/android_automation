
# Bizup App

This is Zero commission video shopping B2B wholesale app, specially made for B2B buyers and sellers in the fashion industry. 100% genuine sellers, cash on delivery (COD) available, cheapest online wholesale rates. 

## Tech Used:- 
- Java
- Appium 
- TestNG 
- Maven
- Jenkins 
- Extend Report

## Installation Process of an Appium Java Project from Git to Local System (Windows)

This README provides a concise guide on installing an Appium Java project from a Git repository onto a Windows system. Follow these steps to get started:

### Prerequisites

- Ensure you have Java Development Kit (JDK) installed on your system. You can verify this by running `java -version` in your command prompt. If JDK is not installed, download and install it from the [Oracle website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

- Install Node.js and npm (Node Package Manager), which are required for managing Appium and its dependencies. Download Node.js from [Node.js official website](https://nodejs.org/en/download/) and install it. npm comes bundled with Node.js.

### Step-by-Step Installation

1. **Clone the Repository**
   - Open Command Prompt.
   - Navigate to the directory where you want to clone the project.
   - Run `git clone <repository-url>` to clone the project.

2. **Install Java Project Dependencies**
   - Navigate to the project directory.
   - Run `mvn clean install` to compile and install the project dependencies. Maven is assumed to be installed and configured correctly on your system.

3. **Install Appium**
   - Still in the project directory, run `npm install -g appium` to install Appium globally on your system.

4. **Install Appium Java Client**
   - Within the project directory, run `npm install appium-java-client` to install the Appium Java client library.


7. **Running the Project**
   - With the project dependencies installed and Appium set up, you can now run your Appium Java tests. Use Maven to run your tests by executing `mvn test` within the project directory.



---

## Overview

This repository contains the **Android UI automation framework** for the **Bizup Buyer App** – a zero-commission B2B video shopping application for fashion buyers and sellers.

The automation covers major buyer journeys like **login**, **search & seller chat**, and **cart/order flows**, and supports multiple **app versions** (143, 148, 151) and **test types** (Smoke / Regression).

## Features

- **Page Object Model (POM)** for Bizup screens (`buyer.pageObjects`).
- **Versioned test suites** under `Bizupautomation.testCases.version_143/148/151`.
- Central **driver & Appium lifecycle** in `Bizupautomation.testUtils.Base`.
- Reusable **Android actions & utilities** in `AndroidActions`.
- **TestNG + ExtentReports** integration via custom `Listeners`.
- **Log4j2 logging** configured via `src/main/resources/log4j2.xml`.
- Optional **BrowserStack** integration using YAML configs per app version.
- Optional **email summary** with HTML report and test statistics after suite execution.

## Tech Stack

- Java 8+
- Appium Java Client `10.0.0`
- TestNG `7.11.0`
- Maven
- ExtentReports `5.1.2`
- Log4j2 (`log4j-core` / `log4j-api` `2.24.3`)
- BrowserStack Java SDK `1.39.1`
- Jackson (JSON/YAML), Apache Commons IO, org.json
- JavaMail (`javax.mail` `1.6.2`)

## High-Level Project Structure

```text
android-automation/
  pom.xml                 # Dependencies and Maven profiles
  README.md
  testNGSuites/           # TestNG XML suites (Smoke & Regression per app version)
  browserstack_app*.yml   # BrowserStack configs per app version

  src/
    main/
      java/
        buyer/
          pageObjects/    # Page Objects for Bizup app screens
          resources/      # data.properties (Appium, email, BrowserStack IDs)
          utils/          # Additional utilities (if any)
      resources/
        log4j2.xml        # Log4j2 configuration

    test/
      java/
        Bizupautomation/
          testCases/
            version_143/
            version_148/
            version_151/  # Tests grouped by app version
          testData/       # Test data (JSON etc.)
          testUtils/      # Base, AndroidActions, Listeners, RetryAnalyzer, etc.

  reports/                # Extent HTML reports & screenshots
  logs/                   # Log files (per log4j2.xml)
```

## Configuration

### 1. App & framework settings (`data.properties`)

Location: `src/main/java/buyer/resources/data.properties`

Important keys:

- `ipAddress` / `port` – Appium server host and port (e.g. `127.0.0.1:4723`).
- `Device1`, `Device2`, `Devise3` – Device names/UDIDs for local execution.
- `EMAIL_USERNAME`, `EMAIL_PASSWORD` – SMTP credentials for report emails.
- `BROWSERSTACK_EMAIL`, `BROWSERSTACK_BUILD_ID` – Used for BrowserStack dashboard link in emails.

> Replace real secrets with your own values and avoid committing them in public repos.

### 2. BrowserStack configs

Files:

- `browserstack_app151.yml`
- `browserstack_app148.yml`
- `browserstack_app143.yml`

Each file defines:

- `userName`, `accessKey` – BrowserStack credentials.
- `app` – Bizup app under test (`bs://<app-id>` or local path).
- `projectName`, `buildName`, `buildIdentifier` – Reporting metadata.
- `platforms` – Device & OS matrix.

These are wired to Maven profiles via the `browserstack.config` system property in `pom.xml`.

### 3. Appium main.js path

In `Bizupautomation.testUtils.Base`, update `NODE_JS_MAIN_PATH` to point to your global Appium installation, for example:

```java
public static String NODE_JS_MAIN_PATH =
    "C:\\Users\\<user>\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
```

## Setup & Prerequisites

1. Install:
   - JDK 8+
   - Maven
   - Node.js & npm
   - Appium (`npm install -g appium`)
   - Android SDK & platform-tools (`adb`)

2. Clone and build:

```bash
git clone <repo-url>
cd android-automation
mvn clean install
```

3. Configure:
   - Update `data.properties` with your device, email, and BrowserStack details.
   - Adjust `NODE_JS_MAIN_PATH` in `Base` if required.
   - Ensure Bizup app is installed on the target device/emulator or uploaded to BrowserStack.

## How to Run Tests

Tests are executed via **Maven profiles** that map to TestNG suites in `testNGSuites/`.

### Smoke suites

```bash
# Smoke tests for version 3.0.4 (151)
mvn clean test -P Smoke151

# Smoke tests for version 3.0.1 (148)
mvn clean test -P Smoke148

# Smoke tests for version 2.16.4 (143)
mvn clean test -P Smoke143
```

### Regression suites

```bash
# Regression tests for version 3.0.4 (151)
mvn clean test -P Regression151

# Regression tests for version 3.0.1 (148)
mvn clean test -P Regression148

# Regression tests for version 2.16.4 (143)
mvn clean test -P Regression143
```

> You can also run individual TestNG classes from your IDE, as long as they extend `Base`.

## Reports & Logs

- **ExtentReports HTML** generated under `reports/`.
- **Screenshots on failure** saved in `reports/` via `AndroidActions.getScreenshotPath`.
- Standard **TestNG output** under `test-output/`.
- **Log4j2 logs** written as configured in `log4j2.xml` (commonly under `logs/`).

## BrowserStack & Email Summary

- When enabled, `Listeners` + `AndroidActions` build an **HTML email summary** after suite completion:
  - Total, passed, failed, skipped tests.
  - Per-test status and description.
  - BrowserStack dashboard link based on `BROWSERSTACK_BUILD_ID`.
- Attachments such as the Extent HTML report (and optionally logs) can be sent using JavaMail.

To enable emails, ensure SMTP credentials in `data.properties` are valid and permitted to send programmatically.
