@echo off
echo "Docs Push Bat"
echo;

echo "1.Move to working directory"
echo "execute cd MyCollections"
cd MyCollections
echo;

echo "2.start commit the changes to the local repository"
echo;

echo "execute git branch"
echo;
git branch
echo;

set now=%date% %time%
echo %now%
echo;

echo "Batch excution complete!"
pause