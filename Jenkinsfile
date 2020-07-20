pipeline {

    agent any
    stages {

        stage ('Build...') {
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }

        stage ('Unit Tests...') {
            steps {
                sh 'mvn test'
            }
        }

        stage ('Sonar Analysis...') {
            environment {
                scannerHome = tool 'SONAR_SCANNER'
            }

            steps {

                withSonarQubeEnv('SONAR') {
                    sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://host.docker.internal:9000 -Dsonar.login=e7349e2f515200bb471235e5700106e31fd7483e -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/src/test/**,**/model/**,**Application.java"
           
                }

            }
        }

        stage ('Quality Gate...') { 
            steps {
                sleep(30)
                timeout(time: 1, unit: 'MINUTES'){
                    waitForQualityGate abortPipeline: true
                } 
            }
        }



    }

}


