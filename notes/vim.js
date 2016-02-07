vimtutor -> for starting a interactive tutorial

#------------Navigation---------------#
h, left arrow 	-> to move left
l, right arrow 	-> to move right
k, up arrow     -> to move up
j, down arrow   -> to move down
<n>w		-> to move words forward, foreg 2w is 2 words forward, cursor at beginning of word
<n>e		-> to move words forward, foreg 3e is 3 words forward, cursor at end of word	

0		-> to move to start of line
gg		-> to move to top of file
Shift+g         -> to move to last line
o		-> to insert new line after cursor line and enter insert mode.
O		-> to insert new line before cursor line and enter insert mode.
v		-> to enter visual/hightlight mode, good for multi-selecting multiple lines for yanking/copying
5G		-> to move to line 5

#----------save and exit-------------#
:q! 		-> to exit w/o making changes
:x or :wq	-> to exit with changes
:w		-> to save w/o exiting
u 		-> to undo the last command, maintains a history of commands
U		-> to undo the all commands in a line	

#------------text edit---------------#
x 		-> to delete character in non-insert mode
i or a		-> to enter insert-mode
esc		-> to escape from insert-mode
dw		-> to delete word, keep cursor at beginning of word to delete in non-insert mode
d2w 		-> to delete 2 words
d$		-> to delete part of line, deletes line content from cursor to end of line in non-insert mode.
dd		-> to delete line
3dd		-> to delete 3 lines
p 		-> to paste
y		-> to copy/yank
5yy		-> to copy 5 lines
r		-> to replace character in cursor
cw		-> to change word
c3w		-> to change 3 words
c$ 		-> to change line content
#--------------search----------------#
/<phrase> 	-> to search for the 'phrase'
n		-> to find the next item in the forward direction
N		-> to find the next item in the backward direction
?<phrase>	-> to search for 'phrase' in backward direction	
%		-> to find matching parentheses, place cursor on opening bracket, it will find the matching closing bracket.

:set ic		-> to set searching in ignore-case mode. 
:set noic	-> to unset searching in ignore-case mode. 
:set is		-> to set searching in partial mode. 
:set nois	-> to unset searching in partial mode. 
:set hls	-> to set highlighting of all matches 
:set noic	-> to unset highlighting


#--------------substitute-------------#
:s/<old>/<new>		-> to change first occurrence of old with new
:s/<old>/<new>/g	-> to change all occurrences of old with new in line selected. g is global
:%s/<old>/<new>/g 	-> to change all occurrences of old with new in entire file
:%s/<old>/<new>/gc 	-> to change all occurrences of old with new in entire file with interactive prompts
:2,6s/<old>/<new>/g	-> to change all occurrences of old with new between lines 2 and 6.
#-------------------misc--------------#
ctrl+g		-> to display meta-data about file edited.
:!		-> to execute external commands, foreg: :!ls will list files, then esc will take you back to edit mode
:r <filepath>	-> to insert file content here



