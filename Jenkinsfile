pipeline {
    agent any
    
    environment {
        GITHUB_REPO = 'https://github.com/ZeroSTF/Devops-Exam.git'
        BRANCH = 'riadh'
        DOCKER_IMAGE = 'riadhchnitir0506/tp-foyer'
        VERSION = '5.0.0'
        NEXUS_CREDENTIALS = credentials('nexus-credentials')
        DOCKER_CREDENTIALS = credentials('docker-hub-credentials')
    }
    
    triggers {
	//testing it now
        githubPush()
    }
    
    stages {
        stage('Git') {
            steps {
                cleanWs()
                checkout([$class: 'GitSCM',
                    branches: [[name: "${BRANCH}"]],
                    userRemoteConfigs: [[url: "${GITHUB_REPO}"]],
                    extensions: [
                        [$class: 'CloneOption',
                            depth: 1,
                            noTags: true,
                            shallow: true,
                            timeout: 30
                        ]
                    ]
                ])
            }
        }
        
        stage('Maven Build') {
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }
        
        stage('JUnit/Mockito') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    jacoco execPattern: 'target/jacoco.exec'
                }
            }
        }
        
        stage('SonarQube') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh '''
                        mvn sonar:sonar \
                        -Dsonar.projectKey=devops-exam-riadh \
                        -Dsonar.projectName='DEVOPS Exam' \
                        -Dsonar.host.url=http://localhost:9000 \
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                    '''
                }
            }
        }
        
        stage('Nexus') {
            steps {
                nexusArtifactUploader(
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    nexusUrl: 'localhost:8081',
                    groupId: 'tn.esprit',
                    version: "${VERSION}",
                    repository: 'maven-releases',
                    credentialsId: 'nexus-credentials',
                    artifacts: [
                        [artifactId: 'tp-foyer',
                         type: 'jar',
                         classifier: '',
                         file: 'target/tp-foyer-5.0.0.jar']
                    ]
                )
            }
        }
        
        stage('Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE}:${VERSION}")
                }
            }
        }
        
        stage('Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', 
                                                  usernameVariable: 'DOCKER_USER', 
                                                  passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        docker push ${DOCKER_IMAGE}:${VERSION}
                    '''
                }
            }
        }
        
        stage('Docker Compose') {
            steps {
                script {
                    sh '''
                        docker compose down
                        docker compose up -d
                        sleep 30
                    '''
                }
            }
        }
        
        stage('Selenium') {
            steps {
                dir('frontend') {
                    script {
                        sh 'sleep 30'
                        sh 'npm install'
                        sh 'npm run e2e'
                    }
                }
            }
            post {
                always {
                    archiveArtifacts artifacts: 'frontend/e2e-test-results/**/*', allowEmptyArchive: true
                }
            }
        }
        
        stage('Grafana') {
            steps {
                echo "Backend application metrics are already being tracked and visualized."
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline completed successfully!'
            mail to: 'riadh.chnitir@esprit.tn',
                 subject: 'Successful Pipeline Run',
                 body: 'The pipeline has completed successfully.'
        }
        failure {
            echo 'Pipeline failed!'
            mail to: 'riadh.chnitir@esprit.tn',
                 subject: 'Pipeline Failure',
                 body: 'The pipeline has failed. Please investigate.'
        }
        always {
            cleanWs()
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
    }
}