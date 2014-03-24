#pattern match is simple shell expression and not regex
echo -n "enter your char: "
read x
case $x in
    [a-zA-Z] )  echo "entered only alphabets" 
        ;;
    ([0-9]) )  echo "entered only digits" 
        ;;
    * )  echo "entered unknown" 
esac    
