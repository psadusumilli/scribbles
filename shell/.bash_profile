### Piperita Theme ###
# Theme by Jacob Tomlinson
# https://github.com/killfall/terminal-piperita
# Source .bashrc if it exists
if [ -f ~/.bashrc ]; then
  source ~/.bashrc
fi

#-----------------------------------------------------------------------------------------------------------------------------
alias eclipse='$HOME/Tools/eclipse/eclipse'
alias mvn='$HOME/Tools/apache-maven-3.0.5/bin/mvn'
alias gst='git status'
alias g='git'
alias glog='git lg'
alias mci='mvn clean install'
alias mcc='mvn clean compile'
alias mjr='mvn clean jetty:run'
alias mjr='mvn clean jetty:run-war'
alias svnst='svn status'
alias svnlg='svn log'
alias svnup='svn update'
alias svnadd='svn add * --force'
alias vbash='vim ~/.bash_profile'
alias sbash='source ~/.bash_profile'

#-----------------------------------------------------------------------------------------------------------------------------
export ADMINTOOL_STAGE_DIR=$HOME/Code/c1/wlsdomain/user_projects/stage
export MW_HOME=$HOME/Tools/wls1036_dev
#export JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/1.6/Home/
#export JAVA_HOME=/System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home/
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_05.jdk/Contents/Home/
export USER_MEM_ARGS="-Xmx1024m -XX:PermSize=1024m"
export MAVEN_OPTS="-Xdebug -Xnoagent -Xms1024m -Xmx3000m -XX:PermSize=512M -XX:MaxPermSize=1024M -Djava.compiler=NONE"
#export MAVEN_OPTS="-Xdebug -Xnoagent -Xms1024m -Xmx3000m -XX:PermSize=512M -XX:MaxPermSize=1024M -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=n"

#-----------------------------------------------------------------------------------------------------------------------------
function wl-domain1(){
  cd $HOME/Code/c1/wlsdomain/domain1; 
  . $MW_HOME/wlserver/server/bin/setWLSEnv.sh ;
  java -Xmx1024m -XX:MaxPermSize=256m weblogic.Server;  
}
function wl-eapi(){
  cd $HOME/Installs/user_projects/domain/wls135_jaxrs;
  . $MW_HOME/wlserver/server/bin/setWLSEnv.sh ;
  java -Xmx1024m -XX:MaxPermSize=256m weblogic.Server;  
}
function wl-domains(){
  cd $HOME/Code/c1/wlsdomain;
}
function cd-c1(){
  cd $HOME/Code/c1;
  tab-name code-c1;
}
function cd-ads(){
  cd $HOME/Code/c1/cxc/ADSTokenIS;
  tab-name code-ads;
}
function cd-cxc(){
  cd $HOME/Code/c1/cxc/CXCPMTTokenIS;
  tab-name code-cxc;
}
function cd-rs(){
  cd $HOME/Code/c1/cxc/payments-token-registration;
  tab-name code-rs;
}
function cd-scribbles(){
  cd $HOME/Code/scribbles;
  tab-name code-scribbles;
}
function cd-vc(){
  cd $HOME/Code/vectorclocks;
  tab-name code-vectorclocks;
}
function tab-name(){
  echo -n -e "\033]0;$1\007";
}
function findbyname(){
  find . -name $1 | xargs ls -lt ;
}
function findcontains(){
  echo -e "\n|OCCURRENCES|-->>"; 
  grep --color=always -R -E $1 . | grep -v -E '.svn*|.git*';
  echo -e "\n|COUNTS|-->>";
  grep --color=always -R -E $1 -c . | grep -v -E ':0|.svn*';
}
function rst(){
  echo -e '\n|GIT|-->>';
  git status;
  echo -e '\n|SVN|-->>';
  svn status | grep ^M;  
}
function rlog(){
  echo -e '\n|GIT|-->>';
  git lg -n $1;
  echo -e '\n|SVN|-->>';
  svn log -l $1 | perl -l40pe 's/^-+/\n/';
}
function chr(){
  open -a Google\ Chrome http://$1;
}
function my-help(){
 echo "wl-domains => goto weblogic domains folder";
 echo "wl-domain1 => start default weblogic domain";
 echo "wl-eapi => start eapi-jaxrs weblogic domain";
 echo "cd-c1 => goto code/c1 folder";
 echo "cd-cxc => goto cxc IS folder";
 echo "cd-ads => goto ads IS folder";
 echo "cd-rs => goto rs api folder";
 echo "tab-name => set tab name";
}

### End Piperita Theme ###
