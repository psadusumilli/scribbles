#$? will contain the exit status of the last command executed
cd ~/non-existent dir

if [ "$?" = "0" ]; then
	rm -r *
else
	echo "Cannot change directory!" 1>&2 #error message on standard error
	exit 1
fi