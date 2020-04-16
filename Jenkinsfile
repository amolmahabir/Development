// Get Artifactory server instance, defined in the Artifactory Plugin administration page.
def server = Artifactory.server "artifactory"
// Create an Artifactory Maven instance.
def rtMaven = Artifactory.newMavenBuild()
def buildInfo

pipeline {
    agent any
    tools {
        jdk 'Java 11'
    }

    stages {
        rtMaven.tool = "Maven 3.6.3"
        stage('Checkout') {
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'Git-001', url: 'https://github.com/amolmahabir/Development']]])
            }
        }
        stage('Build') {
            steps {
                buildInfo = rtMaven.run pom: 'pom.xml', goals: 'clean install -DskipTests'
            }
        }
        stage('Unit test') {
            steps {
                rtMaven.run pom: 'pom.xml', goals: 'test'
            }
        }

        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv(credentialsId: 'sonar-secret', installationName: 'Sonar 8.2') {
                    rtMaven.run pom: 'pom.xml', goals: 'sonar:sonar'
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
                // Set Artifactory repositories for dependencies resolution and artifacts deployment.
                rtMaven.deployer releaseRepo:'libs-release-local', snapshotRepo:'libs-snapshot-local', server: server
                rtMaven.resolver releaseRepo:'libs-release', snapshotRepo:'libs-snapshot', server: server
                server.publishBuildInfo buildInfo
            }
        }

        stage('Deploy to Sogeti') {
            steps {
                bat "Deploy to Sogeti"
            }
        }

        stage('Transfer to KvK') {
            steps {
                bat "Transfer to KvK"
            }
        }
    }
}