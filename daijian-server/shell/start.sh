#!/bin/bash
echo "daijian-gateway server starting..."

cd '/home/daijian-server/daijian-gateway/target'

#nohup java -jar daijian-gateway-1.0-SNAPSHOT.jar &>/dev/null &
nohup java -jar daijian-gateway-1.0-SNAPSHOT.jar > daijian.log 2>&1 &

# 判断8080的gateway服务启动与否
while [ -z "$(lsof -i:8080 -t -s TCP:LISTEN)" ]
do
  sleep 3
done
if [ -n "$(lsof -i:8080 -t -s TCP:LISTEN)" ]
then
  echo "daijian-gateway server start success!"
else
  echo "ERROR: daijian-gateway server failure !!!! "
  exit 5
fi

echo "daijian-user server starting..."
cd '/home/daijian-server/daijian-user/target'
nohup java -jar daijian-user-1.0-SNAPSHOT.jar > daijian.log 2>&1 &

# 判断8083的user服务启动与否
while [ -z "$(lsof -i:8083 -t -s TCP:LISTEN)" ] 
do
  sleep 3
done
if [ -n "$(lsof -i:8083 -t -s TCP:LISTEN)" ]
then
  echo "daijian-user server start success!"
else
  echo "ERROR: daijian-user server failure !!!! "
  exit 5
fi

echo "daijian-platform server starting..."
cd '/home/daijian-server/daijian-platform/target'
nohup java -jar daijian-platform-1.0-SNAPSHOT.jar > daijian.log 2>&1 &

# 判断8087的platform服务启动与否
while [ -z "$(lsof -i:8087 -t -s TCP:LISTEN)" ] 
do
  sleep 3
done
if [ -n "$(lsof -i:8087 -t -s TCP:LISTEN)" ]
then
  echo "daijian-platform server start success!"
else
  echo "ERROR: daijian-platform server failure !!!! "
  exit 5
fi

echo "daijian-support server starting..."
cd '/home/daijian-server/daijian-support/target'
nohup java -jar daijian-support-1.0-SNAPSHOT.jar > daijian.log 2>&1 &

# 判断8092的support服务启动与否
while [ -z "$(lsof -i:8092 -t -s TCP:LISTEN)" ] 
do
  sleep 3
done
if [ -n "$(lsof -i:8092 -t -s TCP:LISTEN)" ]
then
  echo "daijian-support server start success!"
else
  echo "ERROR: daijian-support server failure !!!! "
  exit 5
fi

echo "daijian-seller server starting..."
cd '/home/daijian-server/daijian-seller/target'
nohup java -jar daijian-seller-1.0-SNAPSHOT.jar > daijian.log 2>&1 &

# 判断8084的seller服务启动与否
while [ -z "$(lsof -i:8084 -t -s TCP:LISTEN)" ] 
do
  sleep 3
done
if [ -n "$(lsof -i:8084 -t -s TCP:LISTEN)" ]
then
  echo "daijian-seller server start success!"
else
  echo "ERROR: daijian-seller server failure !!!! "
  exit 5
fi

echo "daijian-trade server starting..."
cd '/home/daijian-server/daijian-trade/target'
nohup java -jar daijian-trade-1.0-SNAPSHOT.jar > daijian.log 2>&1 &

# 判断8086的trade服务启动与否
while [ -z "$(lsof -i:8086 -t -s TCP:LISTEN)" ] 
do
  sleep 3
done
if [ -n "$(lsof -i:8086 -t -s TCP:LISTEN)" ]
then
  echo "daijian-trade server start success!"
else
  echo "ERROR: daijian-trade server failure !!!! "
  exit 5
fi
