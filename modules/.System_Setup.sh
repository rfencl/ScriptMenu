while true
do
let itemCnt=0
clear
cntrPrompt "$title"
cntr ; echo -e  "${RED}${itemCnt}: Exit";   ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Flush redis; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Troubleshoot; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" List Dirs; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Manage App Versions; ((itemCnt++))
read -sn1
let labelCnt=0
let curDwn=$((itemCnt+1))
case "$REPLY" in
$((labelCnt++))) clear; exit 0;;
$((labelCnt++))) (flushRedis);curDwn=1;;
$((labelCnt++))) (troubleshoot);curDwn=1;;
$((labelCnt++))) (doListDirs);curDwn=1;;
$((labelCnt++))) (doManageAppVersions);;
 *) curDwn=1 ;;
esac
moveCursorDown $curDwn ; cntr ; getKey $curDwn
done
