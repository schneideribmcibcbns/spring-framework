DIRS=`ls`
for DIR in $DIRS
do
	if [[ -d $DIR ]];then
		cd $DIR
		cp ../build.gradle .
		touch README.md
		cd ..
	fi
done
