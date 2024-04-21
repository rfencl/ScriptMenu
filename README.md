# Shell menu system for bash and zsh.
1. Perform common commands via a menu to avoid typing. 
2. Easily add your own functions and menu items
3. Organize your menus into sub-menus
4. Create menus for different classes of commands
5. Useful for remote access via ssh.
# Basic Layout
The menu system has a set of functions organized in files. The menus can be organized in a hierarchical fashion with the file  "menu" defined as the main entry point.
Functions and sub menus can be ordered any way you like.

I put the menu definition in a file and the implementiong functions in another to help keep things organized.

Defining a menu is easy here is an example.

The menu items are listed in the menuItems array and the actions (functions to call) are entered in the functions array.
Here we are just launch other apps. external scripts can be launched or functions in files that are sourced.
```
main () {
  menuItems=('Launch Midnight Commander' 'Launch HTOP' )
  functions=('(mc)' '(htop)'  )
  genMenu "Main Menu ($scriptVersion)" "${menuItems[@]}" "${functions[@]}"
}
```
![image](https://github.com/rfencl/ScriptMenu/assets/2704939/e7af5b15-ecfc-4497-b36a-62147f92c8b7)

