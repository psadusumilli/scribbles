#!/bin/sh

file=/Users/xwg532/Code/vrc/scribbles/shell/csv_input.txt
i=1
newline=""
for line in $(cat ${file}); do
    if [ ${i} -le 16 ]; then
        newline=${newline}${line},
        i=$[i+1]
    else
        echo ${newline}"|"
        newline=""
        i=1
    fi
done
