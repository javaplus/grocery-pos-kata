pipeline {
  
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean package -Dmaven.test.skip=true'
      }
    }
    stage ('Unit Test') {
      steps {
        sh 'mvn test'   
        junit 'target/surefire-reports/*.xml'
      }      
    }
  }
}
