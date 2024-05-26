while true
do
let itemCnt=1
clear
cntrPrompt 'Menu2 (v0.01)'
cntr ; echo -e  "${RED}${indexes[itemCnt]}: Exit";   ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" menu2fn1; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" menu2fn2; ((itemCnt++))
read -sk1
let labelCnt=1
let curDwn=$((itemCnt+1))
case "$REPLY" in
${indexes[$((labelCnt++))]}) clear; return 0;;
${indexes[$((labelCnt++))]}) (menu2fn1);curDwn=1;;
${indexes[$((labelCnt++))]}) (menu2fn2);curDwn=1;;
 *) curDwn=1 ;;
esac
moveCursorDown $curDwn ; cntr ; getKey $curDwn
done
