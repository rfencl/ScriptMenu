while true
do
let itemCnt=0
clear
cntrPrompt "$title"
cntr ; echo -e  "${RED}${itemCnt}: Exit";   ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Checkout Source; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" ModBus Endpoint Test; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Sunspec Power App Test; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Power Command App Test; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${itemCnt}:" Move Power Test; ((itemCnt++))
read -sn1
let labelCnt=0
let curDwn=$((itemCnt+1))
case "$REPLY" in
$((labelCnt++))) clear; exit 0;;
$((labelCnt++))) (doCheckoutSource);;
$((labelCnt++))) (doModBusEndPoints);curDwn=1;;
$((labelCnt++))) (doSunspecPowerAppTest);curDwn=1;;
$((labelCnt++))) (doPowerCommandAppTest);curDwn=1;;
$((labelCnt++))) (doMovePower);curDwn=1;;
 *) curDwn=1 ;;
esac
moveCursorDown $curDwn ; cntr ; getKey $curDwn
done
