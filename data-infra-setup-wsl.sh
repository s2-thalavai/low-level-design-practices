#!/bin/bash

# ============================================================
# Data Infrastructure Setup (WSL / Ubuntu)
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
# 1. Update system
# --------------------------------------------------
sudo apt update -y

# --------------------------------------------------
# 2. Install PostgreSQL
# --------------------------------------------------
echo "Installing PostgreSQL..."
sudo apt install -y postgresql postgresql-contrib

# Start PostgreSQL
sudo systemctl enable postgresql || true
sudo systemctl start postgresql || true

# Create default user and database
echo "Configuring PostgreSQL..."
sudo -u postgres psql <<EOF
CREATE USER dev WITH PASSWORD 'dev123';
ALTER USER dev CREATEDB;
CREATE DATABASE devdb OWNER dev;
EOF

echo "PostgreSQL user: dev / dev123"
echo "Database: devdb"

# --------------------------------------------------
# 3. Install Redis
# --------------------------------------------------
echo "Installing Redis..."
sudo apt install -y redis-server

# Enable Redis
sudo systemctl enable redis-server || true
sudo systemctl start redis-server || true

# Test Redis
redis-cli ping

# --------------------------------------------------
# 4. Install Kafka (KRaft mode)
# --------------------------------------------------
echo "Installing Kafka..."

KAFKA_VERSION="3.7.0"
SCALA_VERSION="2.13"

cd /opt
sudo wget https://downloads.apache.org/kafka/${KAFKA_VERSION}/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz
sudo tar -xzf kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz
sudo mv kafka_${SCALA_VERSION}-${KAFKA_VERSION} kafka
sudo rm kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz

sudo chown -R $USER:$USER /opt/kafka

# --------------------------------------------------
# 5. Configure Kafka KRaft
# --------------------------------------------------
echo "Configuring Kafka KRaft..."

cat > /opt/kafka/config/kraft/server.properties <<EOF
process.roles=broker,controller
node.id=1
controller.quorum.voters=1@localhost:9093
listeners=PLAINTEXT://:9092,CONTROLLER://:9093
listener.security.protocol.map=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT
controller.listener.names=CONTROLLER
log.dirs=/tmp/kraft-combined-logs
num.partitions=3
EOF

# Format storage
KAFKA_CLUSTER_ID=$(/opt/kafka/bin/kafka-storage.sh random-uuid)

echo "Formatting Kafka storage..."
/opt/kafka/bin/kafka-storage.sh format \
-t $KAFKA_CLUSTER_ID \
-c /opt/kafka/config/kraft/server.properties

# --------------------------------------------------
# 6. Create Start Scripts
# --------------------------------------------------

# Kafka start script
cat > ~/start-kafka.sh <<EOF
#!/bin/bash
/opt/kafka/bin/kafka-server-start.sh /opt/kafka/config/kraft/server.properties
EOF

chmod +x ~/start-kafka.sh

# PostgreSQL start
cat > ~/start-postgres.sh <<EOF
#!/bin/bash
sudo systemctl start postgresql
EOF

chmod +x ~/start-postgres.sh

# Redis start
cat > ~/start-redis.sh <<EOF
#!/bin/bash
sudo systemctl start redis-server
EOF

chmod +x ~/start-redis.sh

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
echo " ./start-kafka.sh"
echo "=================================================="
