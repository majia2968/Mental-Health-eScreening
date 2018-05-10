#!/bin/bash
# java-build.sh

# Author: Brian Taylor <btaylor@longviewinc.com>

printf "\n\n\n\n**** STARTING build.sh ********************\n\n"

/usr/bin/perl -i -pe "s|%%DTR_PREFIX%%|$DTR_PREFIX|" ./nextgen/Dockerfile
/usr/bin/perl -i -pe "s|%%DTR_ORG%%|$DTR_ORG|" ./nextgen/Dockerfile

#export WAR_FILE="./escreening.war"

printf "\n\n**** Optional: Producing Build Artifacts ********************\n"

# Package war file into .tar.gz file as required by Jenkins templates.
#tar -czvf $JOB_NAME.BUILD-$BUILD_NUMBER.tar.gz $WAR_FILE nextgen/* docker-compose.yml

printf "\n\n**** COMPLETED build.sh ********************\n\n\n\n"
