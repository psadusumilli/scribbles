sed - stream editor

#1: change data to DATA in analysis.log and make new file analysis1.log
sed s/data/DATA/ analysis.log > analysis1.log

#2: \ is the escape char but delimiter / can be changed to |,:
sed 's|/usr/local/bin|/common/bin|' <old.txt >new.txt

#3: using & as the matched string, -r flag is the extended regex
echo "123 abc" | sed 's/[0-9]*/& &/'
123 123 abc
echo "abc 123" | sed -r 's/[0-9]+/& &/' 
abc 123 123

#4: back references
echo abcd123 | sed 's/\([a-z]*\).*/\1/'
abcd
\([a-z]*\) captures abcd, .* captures rest 123

#5: switch words
$ echo 'dude gal' | sed 's/\([a-z]*\) \([a-z]*\)/\2 \1/'
gal dude

#6: global flag g and case flag I
echo '<p>man</p>' | sed 's/<[\/]*p>/X/g'
XmanX
echo '<p>man</p>' | sed 's/<[\/]*P>/X/gI'
XmanX

#7: multiple expressions
echo 'babe' | sed -e 's/a/A/g' -e 's/b/B/g'
BABe

#8 grep - finds lines with pattern 'Wellmark'. p is the print flag, -n is the quiet flag to prevent printing everything when no match is found
sed -n '/Wellmark/p' SystemOut.log
#9 - shell script
sed -n 's/'"$1"'/&/p'
#10 - LINE NO: delete first number occurence in 3rd line of file.txt
sed '3 s/[0-9][0-9]*//' <file.txt >new.txt
#11 - line range 101-532
sed '101,532 s/A/a/' <file.txt >new.txt 

#12 -betweemn pattern ranges
$ echo 'begin I am bad end'  | sed '/begin/,/end/ s/bad/good/'
begin I am good end
$ echo 'begin I am bad end'  | sed '/b[a-z]*/,/e[a-z]*/ s/bad/good/'
begin I am good end


