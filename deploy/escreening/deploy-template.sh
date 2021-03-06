#!/bin/bash

# git branch to use
branch="master"

# name of tomcat instance
tomcatInstance=""
# maven profile
profile=""

# database application logon
jdbcUsername=""
jdbcPassword=""

# vista specific 
vistaIp=""
vistaPort="" 
vistaPrimaryStation="" 
quickOrderIen=""
refTbiServiceName="" #for local SANDBOX its value is TBI/POLYTRAUMA SUPPORT CLINIC TEAM
samplePatientIen=""  #any veteran IEN number that is valid in the VistA instance

# Proxy account credentials
# NOTE: When entering access and verify codes directly in the config file (not using the configuration editor), if the codes contain the following special characters, they need to be entered as follows:
#
#		special char    enter as
#		    <           &lt;
#		    &           &amp;
#		    "           &quot;
#		    '           &apos;
vistaAccessCode=""
vistaVerifyCode="" 
vistaDuz=""
vistaEncrypted=""  # true or false


deploy.sh $tomcatInstance $profile $branch $jdbcUsername $jdbcPassword $vistaIp $vistaPort $vistaPrimaryStation $vistaAccessCode $vistaVerifyCode $vistaDuz $vistaEncrypted $quickOrderIen $samplePatientIen "$refTbiServiceName"


