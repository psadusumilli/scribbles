echo -n "enter your string: "
read x
case $x in
    [a-zA-Z]+ )  echo "entered only alphabets" 
        ;;
    ([0-9])+ )  echo "entered only digits" 
        ;;
    * )  echo "entered unknown" 
esac    
