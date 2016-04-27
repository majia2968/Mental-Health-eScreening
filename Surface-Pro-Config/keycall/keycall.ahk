#NoEnv  ; Recommended for performance and compatibility with future AutoHotkey releases.

SendMode Input  ; Recommended for new scripts due to its superior speed and reliability.
SetWorkingDir %A_ScriptDir%  ; Ensures a consistent starting directory.
#NoEnv
#SingleInstance
#InstallMouseHook

Menu, Tray, Icon , img/tray.ico,,1
Menu, Tray, Click, 1 
Menu, Tray, NoStandard ; remove standard Menu items
Menu, Tray, Add , &off, off ;add a item named Change that goes to the Change label
Menu, Tray, Add , E&xit, ButtonExit ;add a item named Exit that goes to the ButtonExit label
Menu, Tray, Default, &off


tipcall=0
tipcount:=0
oldmx:=0
tipid=0
Gui, +AlwaysOnTop +Owner -Resize +ToolWindow -E0x00000020 +E0x08000000 
;Gui,Add,button,x0 y0 w60 h60 gtabtip,
Gui,Add,picture,x0 y0 w60 h60 gtabtip, img/buttons-01.png ;0x4000000
Gui, -Caption
;Gui, Color, EEAA99
;Gui +LastFound  ; Make the GUI window the last found window for use by the line below.
;WinSet, TransColor, EEAA99 200

repaint:
;MsgBox hola
   CoordMode,  Caret, Screen
    zoomx:=a_Caret("X","screen") +20
    zoomy:=a_Caret("y","screen") +40
    zoomh:=a_Caret("h")
    zoomw:=a_Caret("w")
	;tooltip, %zoomw%
;tooltip %tipcount%
    if (zoomx!="") and (zoomy!="") and (zoomh>2){
        ;MouseGetPos, mx, my, id, control
	;if (mx!=oldmx)
        ;tipcount=0
        ;oldmx:=mx
	if (tipcall=0)  {
	    Gui +LastFound  
	    WinSet, TransColor, off
	    Gui,Show,x%zoomx% y%zoomy% w60 h60 NA
	    ;tipcount:=tipcount+1
        }else{
	    Gui +LastFound  
	    gui, hide
	    ;WinSet, TransColor, off
    	    ;WinGetClass, class, ahk_id %id%
	    ;if (class="IPTip_Main_Window"){
	    ;	tipid:=id
	    ;msgbox %control%
	    ;}
	    ;if (tipid!=0) 		
	}
    } else {
	gui, hide	
	;tipcount:=0
	if (tipcall=1) {
	send {lwin down}cc{lwin up}
	;PostM;essage,0x112,0xF060,,,ahk_class IPTip_Main_Window
	tipcall=0
	}
    }
settimer, repaint, 200

;tooltip %tipcount%
return

off:
if (A_IsPaused){
Menu, Tray, Icon , img/tray.ico,,1
Menu, Tray, Rename, &on , &off
}else{
Menu, Tray, Icon , img/trayp.ico,,1
Menu, Tray, Rename, &off , &on
}
gui, hide
pause	
Return

ButtonExit:
ExitApp

tabtip:
 Run, C:\Program Files\Common Files\microsoft shared\ink\TabTip.exe
 tipcall=1
return

;http://www.autohotkey.com/community/viewtopic.php?t=51635
A_Caret(param, coordMode = "Relative")
{
   target_window := DllCall("GetForegroundWindow")
   If !target_window
      Return ""
   VarSetCapacity(guiThreadInfo, 48)
   NumPut(48, guiThreadInfo, 0)
   DllCall("GetGUIThreadInfo", uint, 0, str, guiThreadInfo)
   hwndCaret := NumGet(GuiThreadInfo, 28)
   If !hwndCaret
      Return ""
   top := NumGet(guiThreadInfo, 36)
   bottom := NumGet(guiThreadInfo, 44)
   If param = h
      Return bottom - top
   left := NumGet(guiThreadInfo, 32)
   right := NumGet(guiThreadInfo, 40)
   If param = w
      Return right - left

   VarSetCapacity(sPoint, 8, 0)
   NumPut(left, sPoint, 0, "Int")
   NumPut(top, sPoint, 4, "Int")

   DllCall("ClientToScreen", "UInt", hwndCaret, "UInt", &sPoint)
   
   left := NumGet(sPoint, 0, "Int")
   top := NumGet(sPoint, 4, "Int")
   
   If coordMode = Relative
   {
      VarSetCapacity(rect, 16)
      DllCall("GetWindowRect", UInt, target_window, UInt, &rect)
      left -= NumGet(Rect, 0, True)
      top  -= NumGet(Rect, 4, True)
   }
   If param = x
      Return left
   Else
      Return top
}