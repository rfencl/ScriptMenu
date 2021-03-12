while true
do
let itemCnt=0
clear
cntrPrompt "$title"
cntr ; echo -e  "${RED}${itemCnt}: Exit";   ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Stop Tomcat; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Start Tomcat; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Start Tomcat Debug; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Restart Tomcat; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:"  Tail Catalina.out; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Tail Turtle.log; ((itemCnt++))
read -sn1
let labelCnt=0
let curDwn=$((itemCnt+1))
case "$REPLY" in
$((labelCnt++))) clear; exit 0;;
$((labelCnt++))) (stopTomcat);curDwn=1;;
$((labelCnt++))) (startTomcat);curDwn=1;;
$((labelCnt++))) (startTomcat -d);curDwn=1;;
$((labelCnt++))) (restartTomcat);curDwn=1;;
$((labelCnt++))) (tailCatalina);curDwn=1,;;
$((labelCnt++))) (tailTurtle);curDwn=1;;
 *) curDwn=1 ;;
esac
moveCursorDown $curDwn ; cntr ; getKey $curDwn
done
