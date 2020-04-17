// Get Artifactory server instance, defined in the Artifactory Plugin administration page.
def server = Artifactory.server "artifactory"
// Create an Artifactory Maven instance.
def rtMaven = Artifactory.newMavenBuild()
rtMaven.tool = "maven 3.6.3"
def buildInfo

pipeline {
    agent any
    tools {
        jdk 'Java 11'
        maven 'maven 3.6.3'
    }

    stages {
        stage('Checkout') {
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'Git-001', url: 'https://github.com/amolmahabir/Development']]])
            }
        }

        stage('Build') {
            steps {
                script {
                    buildInfo = rtMaven.run pom: 'pom.xml', goals: 'clean install -DskipTests'
                }
            }
        }

        stage('Unit test') {
            steps {
                bat "mvn test"
            }
        }

        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv(credentialsId: 'sonar-secret', installationName: 'Sonar 8.2') {
                    bat "mvn sonar:sonar"
                }
                timeout(time: 1, unit: 'MINUTES') {
                    script {
                        def qg = waitForQualityGate()
                        if (qg.status != 'OK') {
                            error "Pipeline aborted due to a quality gate failure: ${qg.status}"
                        }
                    }
                }
            }
        }

        stage('Integration testing') {
            steps {
                bat "ECHO Integration testing"
            }
        }

        stage('Packaging') {
            steps {
                script {
                    // Set Artifactory repositories for dependencies resolution and artifacts deployment.
                    rtMaven.deployer releaseRepo:'libs-release-local', snapshotRepo:'libs-snapshot-local', server: server
                    rtMaven.resolver releaseRepo:'libs-release', snapshotRepo:'libs-snapshot', server: server
                    server.publishBuildInfo buildInfo
                }
            }
        }

        stage('Deploy to Sogeti') {
            steps {
                bat "ECHO Deploy to Sogeti"
            }
        }

        stage('Transfer to KvK') {
            steps {
                bat "ECHO Transfer to KvK"
            }
        }
    }
}