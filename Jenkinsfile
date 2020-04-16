pipeline {
    agent any
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
    }
}