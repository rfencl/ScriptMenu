while true
do
let itemCnt=0
clear
cntrPrompt "$title"
cntr ; echo -e  "${RED}${itemCnt}: Exit";   ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" List installed App Versions; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Refresh App Versions; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" List\\Set Latest App Versions; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Select Specific App versions; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Download App War Files; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Deploy War Files; ((itemCnt++))
read -sn1
let labelCnt=0
let curDwn=$((itemCnt+1))
case "$REPLY" in
$((labelCnt++))) clear; exit 0;;
$((labelCnt++))) (doListInstalled);curDwn=1;;
$((labelCnt++))) (updateAppVersions);curDwn=1;;
$((labelCnt++))) (readAppVersionsFromArchiva);curDwn=1;;
$((labelCnt++))) (gedit tools/appversions.properties tools/*versions.xml);curDwn=1;;
$((labelCnt++))) (loadWar);curDwn=1;;
$((labelCnt++))) (deployWar);curDwn=1;;
 *) curDwn=1 ;;
esac
moveCursorDown $curDwn ; cntr ; getKey $curDwn
done
