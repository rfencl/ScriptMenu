. snippets/color

while true
do
itemCnt=0
clear

echo   ${RED}${itemCnt}: Exit; ((itemCnt++))
echo  "${LTCYAN}${itemCnt}:" Edit File; ((itemCnt++))
Echo  "${LTCYAN}${itemCnt}:" View Logs; ((itemCnt++))
echo  "${LTCYAN}${itemCnt}:" Launch Midnight Commander; ((itemCnt++))
echo  "${LTCYAN}${itemCnt}:" Manage Tomcat; ((itemCnt++))
echo  "${LTCYAN}${itemCnt}:" System Setup; ((itemCnt++))
echo  "${LTCYAN}${itemCnt}:" Regression Tests; ((itemCnt++))
echo  "${LTCYAN}${itemCnt}:" Toggle Zero Config \(currently: $zeroConfigOn\); ((itemCnt++))
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
$((labelCnt++))) enableZeroConfig;curDwn=1;;
 *) curDwn=1 ;;
esac
moveCursorDown $curDwn ; cntr ; getKey $curDwn
done
