@echo off
echo "commit bat"
echo;

echo "1.enter code repository"
cd MyCollections 
echo "The current directory is : "  %cd%
 
echo;

echo "2.Start submitting code to the local repository"

git add *
echo;
 
echo "3.Commit the changes to the local repository"
set now=%date% %time%
echo current time : %now%
echo;
git commit -m "%now%"
echo;
 
echo "4.Commit the changes to the remote git server"
git push
echo;
 
echo "Batch execution complete!"
pause