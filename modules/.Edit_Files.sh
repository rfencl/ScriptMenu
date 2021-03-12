while true
do
let itemCnt=0
clear
cntrPrompt "$title"
cntr ; echo -e  "${RED}${itemCnt}: Exit";   ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" /opt/tomcat/conf/Catalina/localhost/turtle.xml; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" /etc/powin/configuration.json; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" /etc/powin/stacksimulator.json; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" /etc/powin/device/device-20-PcsSimulator.json; ((itemCnt++))
read -sn1
let labelCnt=0
let curDwn=$((itemCnt+1))
case "$REPLY" in
$((labelCnt++))) clear; exit 0;;
$((labelCnt++))) (editFile /opt/tomcat/conf/Catalina/localhost/turtle.xml);;
$((labelCnt++))) (editFile /etc/powin/configuration.json);;
$((labelCnt++))) (editFile /etc/powin/stacksimulator.json);;
$((labelCnt++))) (editFile /etc/powin/device/device-20-PcsSimulator.json);;
 *) curDwn=1 ;;
esac
moveCursorDown $curDwn ; cntr ; getKey $curDwn
done
