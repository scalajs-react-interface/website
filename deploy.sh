#!/bin/sh


#Handy script to deploy app to github pages(gh-pages)

# get comment
comment="$1"

rm -rf build

sbt clean

sbt fullOptJS

yarn install

npm run build


if [ "$comment" == "" ]; then
comment="push from CI"
echo "no comment specified to deploy, using default : $comment"
fi

projectName="sri"

ghPagesPath="/Users/chandu0101/Desktop/kode/programming/scalaprojects/sri/scalajs-react-interface.github.io"

projectPath=${ghPagesPath}/

cp -r build/ ${projectPath}/

cd ${ghPagesPath}

git add .

git commit -m "$comment"

git push