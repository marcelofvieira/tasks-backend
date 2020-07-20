pipeline {

    agent any
    stages {

        stage ('Build backend...') {
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }

        stage ('Unit Tests backend...') {
            steps {
                sh 'mvn test'
            }
        }


    }

}