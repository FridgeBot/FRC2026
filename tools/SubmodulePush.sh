#!/bin/bash

read -p "Pick one SubModule by the first letter lowercase: " Flags

case $Flags in
    o)
    color="Orange"
    ;;
    b)

    color="Black"
    ;;
    *)
    exit
    ;;
esac


read -p "commit Message: " Message

cd ~/Desktop/FRC2026/MainRobot/src/main/java/frc/robot/submodule/

git add --all

    git commit -m "$Message"

    git push -u origin $color

    cd ~/Desktop/FRC2026/MainRobot/src

    git add main/java/frc/robot/submodule

    git commit -m "Submodule update after commit: $Message"
    

    git push
