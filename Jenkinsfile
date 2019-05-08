/*
 * Copyright 2019 Red Hat, Inc. Red Hat licenses this file to you under
 * the Apache License, version 2.0 (the "License"); you may not use
 * this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

def MAVEN_PARAMS = '-B -e -fae -V -Dsurefire.rerunFailingTestsCount=2'

pipeline {

    agent {
        label 'checkin'
    }

    tools {
        jdk 'jdk8u112'
        maven 'maven-3.5.2'
    }

    options {
        buildDiscarder(
            logRotator(numToKeepStr: '50')
        )

        timeout(time: 2, unit: 'HOURS')
    }

    environment {
        MAVEN_OPTS = '-Xmx1G -XX:MaxPermSize=256m' 
    }

    stages {

        stage('Build') {

            steps {
                configFileProvider([configFile(fileId: 'm3-settings-new', variable: 'MAVEN_SETTINGS')]) {
                    sh "mvn $MAVEN_PARAMS -s $MAVEN_SETTINGS -f camel-sap/com.sap.conn.jco/pom.xml clean install" 
                    sh "mvn $MAVEN_PARAMS -s $MAVEN_SETTINGS -f camel-sap/com.sap.conn.idoc/pom.xml clean install" 
                    sh "mvn $MAVEN_PARAMS -s $MAVEN_SETTINGS -f camel-sap/pom.xml clean install" 
                }
            }

        }

        stage('Deploy') {

            when {
                branch '\\d.+redhat-.+'
                not { changeRequest() }
            }

            environment { 
                FUSE_QE_SSH_KEY = credentials('jenkins-fuse-qe') 
            }

            steps {
                configFileProvider([configFile(fileId: 'm3-settings-new', variable: 'MAVEN_SETTINGS')]) {
                    sh "mvn $MAVEN_PARAMS -s $MAVEN_SETTINGS -Prepository clean install"
                }

                sh "./publish.sh"
            }

        }

    }

    post {
        always {
            junit allowEmptyResults: true, testResults: '**/target/*-reports/*.xml'

            emailext(
                subject: '${DEFAULT_SUBJECT}',
                body: '${DEFAULT_CONTENT}',
                recipientProviders: [[$class: 'CulpritsRecipientProvider']]
            )
        }
    }
}
