#!/bin/bash

/app/pivotal-tc-server-developer-3.1.3.SR1/tcruntime-ctl.sh my_server start
sleep 1s

tail -f /app/pivotal-tc-server-developer-3.1.3.SR1/my_server/logs/catalina.out
