while true
do
let itemCnt=0
clear
cntrPrompt "$title"
cntr ; echo -e  "${RED}${itemCnt}: Exit";   ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" turtle.log; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" coblynau.log; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" knocker.log; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" primrose.log; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" kobold.log; ((itemCnt++))
read -sn1
let labelCnt=0
let curDwn=$((itemCnt+1))
case "$REPLY" in
$((labelCnt++))) clear; exit 0;;
$((labelCnt++))) (viewLog /var/log/tomcat8/turtle.log);;
$((labelCnt++))) (viewLog /var/log/tomcat8/coblynau.log);;
$((labelCnt++))) (viewLog /var/log/tomcat8/knocker.log);;
$((labelCnt++))) (viewLog /var/log/tomcat8/primrose.log);;
$((labelCnt++))) (viewLog /var/log/tomcat8/kobold.log);;
 *) curDwn=1 ;;
esac
moveCursorDown $curDwn ; cntr ; getKey $curDwn
done
