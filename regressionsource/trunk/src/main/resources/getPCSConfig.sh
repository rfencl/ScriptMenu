#################################################
# If the file exists on this machine then use it
#  else it is on a remote turtle
################################################
files=( "/etc/powin/device/device-20*json" )
dest=/home/powin/device-20
if (( ${#files[@]} )); then
 cp $files $dest
else 
  sshpass -p 'powin' scp  -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -r powin@10.0.0.3:/etc/powin/device/device-20*.json $dest
fi