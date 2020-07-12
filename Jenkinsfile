// Scripted Pipeline
// Requires libraries from https://github.com/Prouser123/jenkins-tools
// Made by @Prouser123 for https://ci.jcx.ovh.

node('docker-cli') {
  postJobGhStatus() {
    cleanWs()

    docker.image('jcxldn/openjdk-alpine:14-jdk-slim').inside {

      scmCloneStage()

      stage('Build') {
        // 'gradle wrapper' is not required here - it is only needed to update / generate a NEW wrapper, not use an existing one.
        sh 'chmod +x ./gradlew && ./gradlew build -s'

        archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
        ghSetStatus("The build passed.", "success", "ci")
      }
    }
  }
}
