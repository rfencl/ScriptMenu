#!/bin/bash

echo "copy a file from turtle home to local home"
sshpass -p 'powin' scp  -r -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -r powin@10.0.0.3:$1 $2
