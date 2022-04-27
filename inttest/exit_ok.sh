#!/bin/bash

rm $(pwd)/.exit_ok.xml

java -Dorg.slf4j.simpleLogger.defaultLogLevel=warn -jar ../build/mex.jar \
  -c jdbc:postgresql:postgres \
  -u postgres \
  -p postgres \
  -x $(pwd)/.exit_ok.xml

jarexit=$?

test -f $(pwd)/.exit_ok.xml

filewritten=$?

if [ $jarexit -ne 0 -o  $filewritten -ne 0 ]; then
  echo exit_ok test failed
  exit 1
else
  echo exit_ok test passed
  exit 0
fi


