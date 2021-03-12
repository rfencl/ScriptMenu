#################################################
# If the file exists on this machine then use it
#  else it is on a remote turtle
################################################
files="/etc/powin/configuration.json"
if [ -e  $files ]; then
 cp $files /home/powin
else 
  sshpass -p 'powin' scp  -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -r powin@10.0.0.3:${files} /home/powin/configuration.json
fi