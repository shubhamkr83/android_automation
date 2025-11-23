
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


