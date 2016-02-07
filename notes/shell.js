
#-----------------------------------------------------------------------------------------------------------------------------------
#operators
-n string1 - string1 is not null and does exist
-z string1 - string1 is null and does exist
-s file1 - file1 is non empty file
-f file1 - file1 is a file not a dir
-d file1 - file1 is a dir and exists
-w file1 - file1 is writable
-r file1 - file1 is read-only
-x file1 - file1 is executable

#-----------------------------------------------------------------------------------------------------------------------------------
pstree => to print process tree

ls > file => command output to file, will overwrite file if exists.
ls >> file => will append to end of file if already exists.
ls -lR => recursive listing of files`

#-----------------------------------------------------------------------------------------------------------------------------------
cat < file => file contents will be input for command

vim names => vijay, rekha, shravan.
$ tr "[a-z]" "[A-Z]" < names > capital_names => VIJAY, REKHA, SHRAVAN

tr is translate command, tr [set1] [set2]
#-----------------------------------------------------------------------------------------------------------------------------------
head -4 < names => prints first 3 lines of names
tail -4 < names => prints last 3 lines of names
sort names > uniq => removes duplicates but works only with sorted input.
cat 123.log  | sed -e  's/SystemOut/""/' > 345.log
