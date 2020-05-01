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
                    bat 'mvn clean install -DskipTests'
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
                timeout(time: 20, unit: 'SECONDS') {
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
                    bat "curl -u admin:password --upload-file /target/development-1.0-SNAPSHOT.jar \"http://localhost:8046/artifactory/libs-release-local/com.amol.learn/development/1.0/development-1.0.jar\""
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