TOOLS
  -Scripts-
  SubmodulePush.sh---This is a bash script that was made so that we wouldnt have to manually push our changes for the submodule then update the Parent. This now dose it for us so long as the paths mach up.
    
  copyAlias---This is a coppy of all of our Alias for git from our bashrc.
    
  deleteAutos.sh---This is to delete the autos & paths that are staying on the dashboard after they have been renamed or deleted. NOTE you must be connected by USB to the rio to run.

-ELASTIC LAYOUTS-
  elastic-layout.json---This is our standard layout at a compatition
    
  PIDtunner.json---This is a useful layout for when you are tuning pid it has a graph and prefrences set up.


NOTES
  1. We used something called swerve generator that created the swerve modules and base code for us to program. In doing so we had to create 2 folders for the 2 robots we had. Wich was origionaly the programing and main robots, we eventualy did away with that naming because those robots were being swiched so much and it was hard to foollow wich was wich. now we named them based on color and gave them identifiable tags.

  2. The 2 files were a pain in the neck to copy files back and forth when we were using the same files so we are thinking about moving to a Git Submodule.

  3. We have a submodule that is connected to another repository but we chose submodule because it was less tedious than doing something like cherrypicking between to fials or just copping and pasting them over we decided to have 2 branches in the submodule called orange and black.

4. we created aliases and scripts to run the git push because of how long it took to push both the Parent and
Submodule.


USEFULL VIDEOS FOR INCOMING PROGRAMERS
  General Git how dose rebase and HEAD work
  https://youtu.be/Ala6PHlYjmw?si=RcwfcByT-EZmqb-u
  
  Submodules
  https://youtu.be/JESI498HSMA?si=wghEE4ra_WXMRiNe

  Linux Basics    
  https://youtube.com/playlist?list=PLIhvC56v63IJIujb5cyE13oLuyORZpdkL&si=xQPCONwHfDNzGTMn NOTE it says hacking it's not

  Basics of Bash Scripting
  https://youtube.com/playlist?list=PLIhvC56v63IKioClkSNDjW7iz-6TFvLwS&si=_89nrmDYuRmZzRdY
