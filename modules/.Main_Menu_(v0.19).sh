while true
do
let itemCnt=0
clear
cntrPrompt "$title"
cntr ; echo -e  "${RED}${itemCnt}: Exit";   ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Edit File; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" View Logs; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Launch Midnight Commander; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Manage Tomcat; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" System Setup; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Regression Tests; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Toggle Stack Simulator \(currently: $stackSimON\); ((itemCnt++))
read -sn1
let labelCnt=0
let curDwn=$((itemCnt+1))
case "$REPLY" in
$((labelCnt++))) clear; exit 0;;
$((labelCnt++))) (doEditFile);;
$((labelCnt++))) (doViewLogs);;
$((labelCnt++))) (mc);;
$((labelCnt++))) (doManageTomcat);;
$((labelCnt++))) (doSystemSetup);;
$((labelCnt++))) (doRegressionTests);;
$((labelCnt++))) enableStackSim;curDwn=1;;
 *) curDwn=1 ;;
esac
moveCursorDown $curDwn ; cntr ; getKey $curDwn
done
