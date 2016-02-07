
//create branch local and remote

git branch backup
git checkout branch
git push origin backup
git pull origin backup

//git revert to a particular commit
# Reset the index to the desired tree
git reset 56e05fced
git reset --soft HEAD@{1}
git commit -m "Revert to 56e05fced"
git reset --hard
