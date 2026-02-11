# Java Setup

| Category   | Tools                 |
| ---------- | --------------------- |
| Java       | JDK 21, Maven, Gradle |
| Python     | pyenv, Poetry         |
| Frameworks | Django, FastAPI       |
| Workers    | Celery                |
| Go         | Gin, Fiber, Echo      |
| Rust       | rustup                |
| Infra      | Docker                |
| JS         | Node LTS              |


# Java 21 + Maven Setup on Ubuntu (WSL2)

**Environment**

-   Host: Windows 10 (WSL2)
    
-   OS: Ubuntu 24.04.3 LTS
    
-   Kernel: 6.6.87.2-microsoft-standard-WSL2
    
-   Date: Feb 11, 2026
    

----------

## 1. Open WSL Ubuntu

` ``bash
wsl ~
` 

Verify system:

```bash
uname -a
``` 

----------

## 2. Check Existing Java

```bash
java --version
``` 

Output (before upgrade):

```
openjdk  17.0.16
``` 

----------

## 3. Update Packages

```bash
sudo apt update
``` 

----------

## 4. Install OpenJDK 21

```bash
sudo apt install openjdk-21-jdk -y
``` 

Verify installation:

```bash
java -version
``` 

Output:

`openjdk version "21.0.10"` 

----------

## 5. Manage Multiple Java Versions

List installed versions:

```bash
update-alternatives --list java
``` 

Example:

```
/usr/lib/jvm/java-17-openjdk-amd64/bin/java /usr/lib/jvm/java-21-openjdk-amd64/bin/java
``` 

Switch version (optional):

```
sudo update-alternatives --config java
``` 

Verify active Java path:

```bash
readlink -f $(which java)
``` 

----------

## 6. Set JAVA_HOME

Edit `.bashrc`:

```bash
nano ~/.bashrc
``` 

Add at the end:

```bash
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64 export PATH=$JAVA_HOME/bin:$PATH
``` 

Reload configuration:

```bash
source ~/.bashrc
``` 

Verify:

```bash
echo  $JAVA_HOME
```

Output:

```
/usr/lib/jvm/java-21-openjdk-amd64
``` 

----------

## 7. Install Maven

```
sudo apt install maven -y
``` 

Verify Maven:

```
mvn -version
``` 

Output:

`Apache  Maven  3.8.7  Java version:  21.0.10` 

----------

## 8. Final Environment Check

```
java -version 
echo  $JAVA_HOME 
mvn -version
``` 

Expected:

-   Java 21 active
    
-   JAVA_HOME set correctly
    
-   Maven using Java 21


---------------------

## Output

<img width="1145" height="452" alt="image" src="https://github.com/user-attachments/assets/9b0c3759-775b-4e4a-a73a-55fd5ca65612" />

---------------------

## production-ready setup script


It will:

-   Update Ubuntu
    
-   Install OpenJDK 21
    
-   Set `JAVA_HOME`
    
-   Configure alternatives
    
-   Install Maven
    
-   Verify everything

## java-maven-setup-wsl.sh

```bash
#!/bin/bash

# ============================================
# Java 21 + Maven Setup Script for Ubuntu (WSL2)
# Author: Setup Automation
# Date: Feb 2026
# ============================================

set -e

echo "======================================"
echo "Java + Maven Setup Started"
echo "======================================"

# 1. Update system
echo "Updating package list..."
sudo apt update -y

# Optional upgrade
echo "Upgrading existing packages..."
sudo apt upgrade -y

# 2. Install OpenJDK 21
echo "Installing OpenJDK 21..."
sudo apt install -y openjdk-21-jdk

# 3. Verify Java installation
echo "Verifying Java installation..."
java -version

# 4. Set JAVA_HOME automatically
JAVA_HOME_PATH="/usr/lib/jvm/java-21-openjdk-amd64"

echo "Configuring JAVA_HOME..."

# Check if already present
if grep -q "JAVA_HOME" ~/.bashrc; then
    echo "JAVA_HOME already configured in .bashrc"
else
    echo "" >> ~/.bashrc
    echo "# Java Environment" >> ~/.bashrc
    echo "export JAVA_HOME=$JAVA_HOME_PATH" >> ~/.bashrc
    echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc
    echo "JAVA_HOME added to .bashrc"
fi

# Load environment
export JAVA_HOME=$JAVA_HOME_PATH
export PATH=$JAVA_HOME/bin:$PATH

echo "JAVA_HOME set to: $JAVA_HOME"

# 5. Configure alternatives (if multiple versions exist)
echo "Configuring Java alternatives..."
sudo update-alternatives --set java $JAVA_HOME_PATH/bin/java || true
sudo update-alternatives --set javac $JAVA_HOME_PATH/bin/javac || true

# 6. Install Maven
echo "Installing Maven..."
sudo apt install -y maven

# 7. Verify Maven
echo "Verifying Maven installation..."
mvn -version

# 8. Final Summary
echo ""
echo "======================================"
echo "Setup Completed Successfully"
echo "======================================"
echo "Java Version:"
java -version
echo ""
echo "JAVA_HOME:"
echo $JAVA_HOME
echo ""
echo "Maven Version:"
mvn -version
echo ""
echo "Note: Run 'source ~/.bashrc' or restart terminal if needed."
echo "======================================"

```


## How to Use

### 1. Create the file

```bash
nano java-maven-setup-wsl.sh
``` 

Paste the script and save.

----------

### 2. Make it executable

```bash
chmod +x java-maven-setup-wsl.sh
``` 

----------

### 3. Run it

```bash
./java-maven-setup-wsl.sh
``` 

----------

## Optional (Recommended for Dev Machines)

Reload environment after script:

```bash
source ~/.bashrc
```

<img width="1918" height="1020" alt="image" src="https://github.com/user-attachments/assets/aca66673-4110-442e-91ce-5d5e53afcff8" />

<img width="1918" height="1018" alt="image" src="https://github.com/user-attachments/assets/a2ed6c56-d32f-4060-8773-9b7c42d8bde8" />

<img width="1312" height="1017" alt="image" src="https://github.com/user-attachments/assets/9105d664-83b8-4281-9385-e1274090dff7" />

------------------------------------

