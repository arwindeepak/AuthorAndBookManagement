pipeline {
    agent any

    tools {
        maven 'Maven-3'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t author-book-management .'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                docker stop author-book-app || true
                docker rm author-book-app || true

                docker run -d \
                  -p 8081:8081 \
                  --name author-book-app \
                  author-book-management
                '''
            }
        }
    }
}