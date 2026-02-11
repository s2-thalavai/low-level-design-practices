#!/bin/bash

# ============================================================
# Data Infrastructure Setup (WSL / Ubuntu 24.04)
# Installs:
# - PostgreSQL
# - Redis
# - Apache Kafka (KRaft mode)
# ============================================================

set -e

echo "=================================================="
echo "Data Infrastructure Setup Started"
echo "=================================================="

# --------------------------------------------------
# 1. System Update
# --------------------------------------------------
sudo apt update -y

# --------------------------------------------------
# 2. PostgreSQL
# --------------------------------------------------
echo "Installing PostgreSQL..."
sudo apt install -y postgresql postgresql-contrib

sudo systemctl enable postgresql || true
sudo systemctl start postgresql || true

echo "Configuring PostgreSQL (dev/devdb)..."

sudo -u postgres psql <<EOF
DO \$\$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'dev') THEN
      CREATE ROLE dev LOGIN PASSWORD 'dev123';
   END IF;
END
\$\$;

DO \$\$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'devdb') THEN
      CREATE DATABASE devdb OWNER dev;
   END IF;
END
\$\$;
EOF

# --------------------------------------------------
# 3. Redis
# --------------------------------------------------
echo "Installing Redis..."
sudo apt install -y redis-server

sudo systemctl enable redis-server || true
sudo systemctl start redis-server || true

echo "Testing Redis..."
redis-cli ping

# --------------------------------------------------
# 4. Kafka Installation
# --------------------------------------------------
echo "Installing Kafka..."

KAFKA_VERSION="3.7.1"
SCALA_VERSION="2.13"
FILE="kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz"

cd /tmp

DOWNLOAD_URL="https://downloads.apache.org/kafka/${KAFKA_VERSION}/${FILE}"

if ! wget -q $DOWNLOAD_URL; then
    echo "Primary download failed. Using archive..."
    wget https://archive.apache.org/dist/kafka/${KAFKA_VERSION}/${FILE}
fi

# Extract to /opt
sudo tar -xzf $FILE -C /opt
sudo mv /opt/kafka_${SCALA_VERSION}-${KAFKA_VERSION} /opt/kafka
rm $FILE

sudo chown -R $USER:$USER /opt/kafka

# --------------------------------------------------
# 5. Kafka KRaft Configuration
# --------------------------------------------------
echo "Configuring Kafka (KRaft mode)..."

mkdir -p /opt/kafka/config/kraft

cat > /opt/kafka/config/kraft/server.properties <<EOF
process.roles=broker,controller
node.id=1
controller.quorum.voters=1@localhost:9093

listeners=PLAINTEXT://:9092,CONTROLLER://:9093
listener.security.protocol.map=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT
controller.listener.names=CONTROLLER

log.dirs=/tmp/kraft-combined-logs
num.partitions=3
offsets.topic.replication.factor=1
transaction.state.log.replication.factor=1
transaction.state.log.min.isr=1
EOF

# Format storage (only if not already formatted)
if [ ! -d "/tmp/kraft-combined-logs" ]; then
    echo "Formatting Kafka storage..."
    CLUSTER_ID=$(/opt/kafka/bin/kafka-storage.sh random-uuid)
    /opt/kafka/bin/kafka-storage.sh format \
        -t $CLUSTER_ID \
        -c /opt/kafka/config/kraft/server.properties
fi

# --------------------------------------------------
# 6. Start Scripts
# --------------------------------------------------
echo "Creating helper scripts..."

cat > ~/start-kafka.sh <<EOF
#!/bin/bash
/opt/kafka/bin/kafka-server-start.sh /opt/kafka/config/kraft/server.properties
EOF
chmod +x ~/start-kafka.sh

cat > ~/stop-kafka.sh <<EOF
#!/bin/bash
/opt/kafka/bin/kafka-server-stop.sh
EOF
chmod +x ~/stop-kafka.sh

# --------------------------------------------------
# 7. Verification
# --------------------------------------------------
echo ""
echo "=================================================="
echo "Installation Completed"
echo "=================================================="

echo ""
echo "PostgreSQL:"
psql --version

echo ""
echo "Redis:"
redis-cli ping

echo ""
echo "Kafka:"
/opt/kafka/bin/kafka-topics.sh --version

echo ""
echo "=================================================="
echo "Connection Details"
echo "--------------------------------------------------"
echo "PostgreSQL:"
echo " Host: localhost"
echo " Port: 5432"
echo " User: dev"
echo " Password: dev123"
echo " Database: devdb"
echo ""
echo "Redis:"
echo " Host: localhost"
echo " Port: 6379"
echo ""
echo "Kafka:"
echo " Bootstrap: localhost:9092"
echo ""
echo "Start Kafka:"
echo "  ~/start-kafka.sh"
echo ""
echo "Stop Kafka:"
echo "  ~/stop-kafka.sh"
echo "=================================================="
