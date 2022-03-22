#!/bin/bash
PID1=$(ps -ef | grep daijian-trade-1.0-SNAPSHOT.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID1" ]
then
    echo daijian-trade is already stopped
else
    echo kill $PID1
    kill $PID1
fi

PID2=$(ps -ef | grep daijian-seller-1.0-SNAPSHOT.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID2" ]
then
    echo daijian-seller is already stopped
else
    echo kill $PID2
    kill $PID2
fi

PID3=$(ps -ef | grep daijian-support-1.0-SNAPSHOT.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID3" ]
then
    echo daijian-support is already stopped
else
    echo kill $PID3
    kill $PID3
fi

PID4=$(ps -ef | grep daijian-platform-1.0-SNAPSHOT.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID4" ]
then
    echo daijian-platform is already stopped
else
    echo kill $PID4
    kill $PID4
fi

PID5=$(ps -ef | grep daijian-user-1.0-SNAPSHOT.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID5" ]
then
    echo daijian-user is already stopped
else
    echo kill $PID5
    kill $PID5
fi

PID6=$(ps -ef | grep daijian-gateway-1.0-SNAPSHOT.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID6" ]
then
    echo daijian-gateway is already stopped
else
    echo kill $PID6
    kill $PID6
fi

