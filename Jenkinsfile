pipeline {
  
  agent any
  stages {
    stage('Build') {
        steps{
      withMaven(
        // Maven installation declared in the Jenkins "Global Tool Configuration"
        maven: 'M3') {
 
      // Run the maven build
      bat "mvn test"
 
    }
        }
    }
    stage ('Unit Test') {
      steps {
        withMaven(
        // Maven installation declared in the Jenkins "Global Tool Configuration"
        maven: 'M3') {
 
      // Run the maven build
      bat "mvn clean install -DskipTests"
      }      
    }
  }
  }
}
