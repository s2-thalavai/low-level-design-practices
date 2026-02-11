#!/bin/bash

# ============================================================
# Full Java Developer Bootstrap Script (WSL / Ubuntu 24.04)
# Installs:
# - OpenJDK 21
# - SDKMAN
# - Maven
# - Gradle
# - Spring Boot CLI
# - Git
# - Node.js LTS
# - Docker (WSL compatible)
# ============================================================

set -e

echo "=================================================="
echo "Full Java Developer Environment Setup Started"
echo "=================================================="

# --------------------------------------------------
# 1. System Update
# --------------------------------------------------
echo "Updating system..."
sudo apt update -y
sudo apt upgrade -y

# --------------------------------------------------
# 2. Install base tools
# --------------------------------------------------
echo "Installing base tools..."
sudo apt install -y \
    curl wget unzip zip git build-essential ca-certificates gnupg lsb-release software-properties-common

# --------------------------------------------------
# 3. Install OpenJDK 21
# --------------------------------------------------
echo "Installing OpenJDK 21..."
sudo apt install -y openjdk-21-jdk

JAVA_HOME_PATH="/usr/lib/jvm/java-21-openjdk-amd64"

# Configure JAVA_HOME
if ! grep -q "JAVA_HOME" ~/.bashrc; then
    echo "" >> ~/.bashrc
    echo "# Java Configuration" >> ~/.bashrc
    echo "export JAVA_HOME=$JAVA_HOME_PATH" >> ~/.bashrc
    echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc
fi

export JAVA_HOME=$JAVA_HOME_PATH
export PATH=$JAVA_HOME/bin:$PATH

echo "JAVA_HOME set to $JAVA_HOME"

# --------------------------------------------------
# 4. Install SDKMAN
# --------------------------------------------------
if [ ! -d "$HOME/.sdkman" ]; then
    echo "Installing SDKMAN..."
    curl -s "https://get.sdkman.io" | bash
fi

source "$HOME/.sdkman/bin/sdkman-init.sh"

# Ensure SDKMAN loads automatically
if ! grep -q "sdkman-init.sh" ~/.bashrc; then
    echo '' >> ~/.bashrc
    echo '# SDKMAN Configuration' >> ~/.bashrc
    echo 'export SDKMAN_DIR="$HOME/.sdkman"' >> ~/.bashrc
    echo '[[ -s "$SDKMAN_DIR/bin/sdkman-init.sh" ]] && source "$SDKMAN_DIR/bin/sdkman-init.sh"' >> ~/.bashrc
fi

# --------------------------------------------------
# 5. Install Maven, Gradle, Spring Boot
# --------------------------------------------------
echo "Installing Maven..."
sdk install maven

echo "Installing Gradle..."
sdk install gradle

echo "Installing Spring Boot CLI..."
sdk install springboot

# --------------------------------------------------
# 6. Install Node.js (LTS)
# --------------------------------------------------
echo "Installing Node.js LTS..."
curl -fsSL https://deb.nodesource.com/setup_lts.x | sudo -E bash -
sudo apt install -y nodejs

# --------------------------------------------------
# 7. Install Docker (WSL friendly)
# --------------------------------------------------
echo "Installing Docker..."

sudo apt install -y docker.io

# Add user to docker group
sudo usermod -aG docker $USER

# Enable Docker
sudo systemctl enable docker || true
sudo systemctl start docker || true

# --------------------------------------------------
# 8. Final Verification
# --------------------------------------------------
echo ""
echo "=================================================="
echo "Installation Completed"
echo "=================================================="

echo ""
echo "Java:"
java -version

echo ""
echo "Maven:"
mvn -version || true

echo ""
echo "Gradle:"
gradle -v || true

echo ""
echo "Spring Boot:"
spring version || true

echo ""
echo "Node:"
node -v

echo ""
echo "NPM:"
npm -v

echo ""
echo "Git:"
git --version

echo ""
echo "Docker:"
docker --version || true

echo ""
echo "=================================================="
echo "IMPORTANT:"
echo "1. Restart WSL or run: source ~/.bashrc"
echo "2. For Docker in WSL, Docker Desktop (Windows) is recommended."
echo "=================================================="
