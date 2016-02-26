RELEASE PROCESS

'Reference'
http://www.infoq.com/articles/agile-version-control
https://dzone.com/articles/why-i-never-use-maven-release

0 Four branches
      Spike | POC (per team or component), merges into Sprint (short-lived)
      Sprint | Work (per team or component)
      Trunk | Mainline (per platform for major/minor releases) (1.0.0, 1.1.0, 2.0.0)
      Patch | Release (for patch fixes 1.0.1) (optional, short-lived)
1 Coder works on stories in Sprint branch
2 Merges when story complete to Trunk branch.
      When team works concurrently on many stories, 1 story is king, rest are all servants.
3 Trunk branch is ready for a release anytime.
4 Reverse merge from trunk to your sprint branch every day to get latest of same team + other teams.
5 Resolve conflicts on the branch that is least stable (sprint)
6 Merge from your work branch to the trunk on a regular basis, for example whenever a story is done. Dont wait until the end of the sprint!
7 Release branches are created during critical Prod defects
      7.1 If there is a prod bug but Trunk is not in releasable state (no feature toggle)
      7.2 create a patch release branch 1.0.1 (do the fix there), reverse merge to trunk -> sprint

'github flow'
https://guides.github.com/introduction/flow/

1 Create a branch from 'master', start making changes in your branch say 'my-branch'
2 Create a Pull-request for a code-review/discussion of your changes before being merged to master
3 Deploy the code from 'my-branch' to production
        if good, merge to 'master',
        else revert deploy to 'master'
