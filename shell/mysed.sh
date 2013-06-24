find $1 -type f | while read file
do
    sed "s/$2/$3/g" "$file" > tmp
    mv tmp "$file"
    echo "$file changed"
done