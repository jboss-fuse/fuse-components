#!/bin/env bash

set -euo pipefail

BUILD_TIMESTAMP=`date -u +%Y-%m-%d_%H-%M-%S`

# work around invalid chars in a matrix job's workspace path using symlink magic
tmpdir=$(mktemp -d); pushd $tmpdir >/dev/null; ln -s ${WORKSPACE} ws; popd >/dev/null

if [[ -f ${WORKSPACE}/rsync.sh ]]; then rm -f ${WORKSPACE}/rsync.sh; fi
cd ${WORKSPACE}
wget https://raw.githubusercontent.com/jbosstools/jbosstools-build-ci/master/publish/rsync.sh --no-check-certificate -O ${WORKSPACE}/rsync.sh
chmod +x ${WORKSPACE}/rsync.sh
RSYNC=${WORKSPACE}/rsync.sh

wget https://raw.githubusercontent.com/jbosstools/jbosstools-build-ci/master/util/cleanup/jbosstools-cleanup.sh --no-check-certificate -O ${WORKSPACE}/cleanup.sh
chmod +x ${WORKSPACE}/cleanup.sh
CLEANUP=${WORKSPACE}/cleanup.sh

FUSE_VERSION=`grep -m1 "<version>" camel-sap/camel-sap-repository/org.fusesource.camel.component.sap.updatesite/pom.xml | cut -d"-" -f 1 | cut -d">" -f 2`

publishingPath=fuse.sap.updatesite/${FUSE_VERSION}/${BUILD_TIMESTAMP}-B${BUILD_NUMBER}/all/repo

mv ${WORKSPACE}/camel-sap/camel-sap-repository/org.fusesource.camel.component.sap.updatesite/target/org.fusesource.camel.component.sap.updatesite-*.zip ${WORKSPACE}/camel-sap/camel-sap-repository/org.fusesource.camel.component.sap.updatesite/target/sap-updatesite.zip

sha256sum ${WORKSPACE}/camel-sap/camel-sap-repository/org.fusesource.camel.component.sap.updatesite/target/sap-updatesite.zip | awk 'BEGIN {FS=" "}{print $1}' > \
    ${WORKSPACE}/camel-sap/camel-sap-repository/org.fusesource.camel.component.sap.updatesite/target/sap-updatesite.zip.sha256

# create the folder (temp workaround)
ssh -i $FUSE_QE_SSH_KEY jenkins@fuse-qe.rhev-ci-vms.eng.rdu2.redhat.com mkdir -pv /mnt/data/jenkins/p2/${publishingPath}

export RSYNC_RSH="ssh -i $FUSE_QE_SSH_KEY"

# Publish the SAP p2 update site.
${RSYNC} -DESTINATION jenkins@fuse-qe.rhev-ci-vms.eng.rdu2.redhat.com:/mnt/data/jenkins/p2 \
  -s ${WORKSPACE}/camel-sap/camel-sap-repository/org.fusesource.camel.component.sap.updatesite/target/repository -t ${publishingPath}
${CLEANUP} -DESTINATION jenkins@fuse-qe.rhev-ci-vms.eng.rdu2.redhat.com:/mnt/data/jenkins/p2 \
  --regen-metadata-only --no-subdirs --link 1 -d fuse.sap.updatesite/${FUSE_VERSION}

# Publish the SAP p2 site files
rsync -v -e "ssh -i $FUSE_QE_SSH_KEY" -aPrzq --protocol=28 ${WORKSPACE}/camel-sap/camel-sap-repository/org.fusesource.camel.component.sap.updatesite/target/sap-updatesite.zip* \
    jenkins@fuse-qe.rhev-ci-vms.eng.rdu2.redhat.com:/mnt/data/jenkins/p2/${publishingPath}

