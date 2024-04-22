while true
do
let itemCnt=0
clear
cntrPrompt "$title"
cntr ; echo -e  "${RED}${indexes[itemCnt]}: Exit";   ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" menu2fn1; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" menu2fn2; ((itemCnt++))
read -sn1
let labelCnt=0
let curDwn=$((itemCnt+1))
case "$REPLY" in
${indexes[$((labelCnt++))]})  clear; exit 0;;
${indexes[$((labelCnt++))]})  (menu2fn1);curDwn=1;;
${indexes[$((labelCnt++))]})  (menu2fn2);curDwn=1;;
 *) curDwn=1 ;;
esac
moveCursorDown $curDwn ; cntr ; getKey $curDwn
done
