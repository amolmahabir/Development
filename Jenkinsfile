pipeline {
    agent any
    tools {
        maven 'maven 3.6.3'
        jdk 'Java 11'
    }

    stages {
        stage('Checkout') {
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'Git-001', url: 'https://github.com/amolmahabir/Development']]])
            }
        }
        stage('Build') {
            steps {
                bat "mvn clean package -DskipTests"
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
            }
        }
    }
}