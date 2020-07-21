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
                sleep(25)
                timeout(time: 1, unit: 'MINUTES'){
                    waitForQualityGate abortPipeline: true
                } 
            }
        }


        stage ('Deploy Backend') {
            steps {
                deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://host.docker.internal:8001')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }

        
        stage ('Api Test') {
            steps {
                dir ('api-test') {
                    git credentialsId: 'LoginGit', url: 'https://github.com/marcelofvieira/tasks-api-test.git'
                    sh 'mvn test'            
                }
            }
        }


        stage ('Deploy Frontend') {
            steps {

                dir ('task-frontend') {
                    git credentialsId: 'LoginGit', url: 'https://github.com/marcelofvieira/tasks-frontend.git'

                    sh 'mvn clean package'

                    deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://host.docker.internal:8001')], contextPath: 'tasks', war: 'target/tasks.war'              
                }

            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml, api-test/target/surefire-reports/*.xml'
        }
    }
}


