#!/bin/bash

# ============================================================
# Full Backend Developer Bootstrap (WSL / Ubuntu 24.04)
# Installs:
# Java 21 + Maven + Gradle + Spring Boot
# Python + Django + FastAPI + Celery
# Go + Gin + Fiber + Echo
# Rust (rustup)
# Node.js + Git + Docker
# ============================================================

set -e

echo "=================================================="
echo "Full Backend Environment Setup Started"
echo "=================================================="

# --------------------------------------------------
# 1. System Update
# --------------------------------------------------
sudo apt update -y
sudo apt upgrade -y

# --------------------------------------------------
# 2. Base Tools
# --------------------------------------------------
sudo apt install -y \
    curl wget unzip zip git build-essential \
    ca-certificates gnupg lsb-release software-properties-common \
    pkg-config libssl-dev

# --------------------------------------------------
# 3. Java 21
# --------------------------------------------------
sudo apt install -y openjdk-21-jdk

JAVA_HOME_PATH="/usr/lib/jvm/java-21-openjdk-amd64"

if ! grep -q "JAVA_HOME" ~/.bashrc; then
    echo "" >> ~/.bashrc
    echo "# Java Configuration" >> ~/.bashrc
    echo "export JAVA_HOME=$JAVA_HOME_PATH" >> ~/.bashrc
    echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc
fi

export JAVA_HOME=$JAVA_HOME_PATH
export PATH=$JAVA_HOME/bin:$PATH

# --------------------------------------------------
# 4. SDKMAN (Java ecosystem)
# --------------------------------------------------
if [ ! -d "$HOME/.sdkman" ]; then
    curl -s "https://get.sdkman.io" | bash
fi

source "$HOME/.sdkman/bin/sdkman-init.sh"

sdk install maven || true
sdk install gradle || true
sdk install springboot || true

# --------------------------------------------------
# 5. Node.js LTS
# --------------------------------------------------
curl -fsSL https://deb.nodesource.com/setup_lts.x | sudo -E bash -
sudo apt install -y nodejs

# --------------------------------------------------
# 6. Docker
# --------------------------------------------------
sudo apt install -y docker.io
sudo usermod -aG docker $USER
sudo systemctl enable docker || true
sudo systemctl start docker || true

# --------------------------------------------------
# 7. Python Environment
# --------------------------------------------------
echo "Installing Python..."
sudo apt install -y \
    python3 python3-pip python3-venv python3-dev

python3 -m pip install --upgrade pip

# Global Python tools
pip3 install --user \
    virtualenv \
    uvicorn \
    fastapi \
    django \
    celery \
    redis \
    requests \
    httpx \
    pydantic \
    pytest \
    black \
    isort

# Add local bin to PATH
if ! grep -q ".local/bin" ~/.bashrc; then
    echo '' >> ~/.bashrc
    echo '# Python local bin' >> ~/.bashrc
    echo 'export PATH=$HOME/.local/bin:$PATH' >> ~/.bashrc
fi

export PATH=$HOME/.local/bin:$PATH

# --------------------------------------------------
# 8. Go Installation
# --------------------------------------------------
echo "Installing Go..."
GO_VERSION="1.22.0"

wget https://go.dev/dl/go${GO_VERSION}.linux-amd64.tar.gz
sudo rm -rf /usr/local/go
sudo tar -C /usr/local -xzf go${GO_VERSION}.linux-amd64.tar.gz
rm go${GO_VERSION}.linux-amd64.tar.gz

# Configure Go env
if ! grep -q "/usr/local/go/bin" ~/.bashrc; then
    echo '' >> ~/.bashrc
    echo '# Go Configuration' >> ~/.bashrc
    echo 'export PATH=$PATH:/usr/local/go/bin' >> ~/.bashrc
    echo 'export GOPATH=$HOME/go' >> ~/.bashrc
    echo 'export PATH=$PATH:$GOPATH/bin' >> ~/.bashrc
fi

export PATH=$PATH:/usr/local/go/bin
export GOPATH=$HOME/go
export PATH=$PATH:$GOPATH/bin

# Install Go frameworks
echo "Installing Go frameworks..."
go install github.com/gin-gonic/gin@latest
go install github.com/gofiber/fiber/v2@latest
go install github.com/labstack/echo/v4@latest

# --------------------------------------------------
# 9. Rust Installation
# --------------------------------------------------
echo "Installing Rust..."
if [ ! -d "$HOME/.cargo" ]; then
    curl https://sh.rustup.rs -sSf | sh -s -- -y
fi

source "$HOME/.cargo/env"

# Add Rust to bashrc if not present
if ! grep -q ".cargo/env" ~/.bashrc; then
    echo '' >> ~/.bashrc
    echo '# Rust Configuration' >> ~/.bashrc
    echo 'source $HOME/.cargo/env' >> ~/.bashrc
fi

rustup update

# --------------------------------------------------
# 10. Final Verification
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
echo "Python:"
python3 --version

echo ""
echo "FastAPI:"
python3 -c "import fastapi; print(fastapi.__version__)" || true

echo ""
echo "Django:"
django-admin --version || true

echo ""
echo "Celery:"
celery --version || true

echo ""
echo "Go:"
go version

echo ""
echo "Rust:"
rustc --version

echo ""
echo "Node:"
node -v

echo ""
echo "Docker:"
docker --version || true

echo ""
echo "=================================================="
echo "IMPORTANT:"
echo "Run: source ~/.bashrc"
echo "Or restart WSL"
echo "=================================================="
