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

ghPagesPath="/Users/chandu0101/Desktop/kode/programming/scalaprojects/chandu0101.github.io"

projectPath=${ghPagesPath}/${projectName}

cp -r build/ ${projectPath}/

cd ${ghPagesPath}

git add ${projectName}

git commit -m "$comment"

git push