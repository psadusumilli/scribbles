find . -name "*.scala" | xargs du -h 
find . -size +50M -size -100M
find . -type d -name Tecmint #dir only
find . -perm 777 
find . -type f -perm 0777 -print -exec chmod 644 {} \;
find . -type d -empty #empty files
find . -user root #root owned files
find . -group root #root owned files
find . -mtime +50 –mtime -100 #modified 50days ago but not older than 100 days
find . -cmin -60 #changed files in 1 hr
find . -mmin -60 #modified files in 1 hr
find . -amin -60 #accessed files in 1 hr