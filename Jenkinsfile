pipeline {
  agent any
    stages {
        stage('Dev: Checkout') {
           steps {
              git branch: '2.7.0.Final', url: 'https://github.com/mschreie/ticket-monster.git'
           }
        }
        stage('Dev: Build Tasks') {
           steps {
              openshiftBuild bldCfg: 'monster', env: [[name: 'MAVEN_MIRROR_URL', value: 'http://nexus3-nexus.apps.a447.oslab.opentlc.com/repository/maven-all-public']], namespace: 'ticket-monster-dev', showBuildLogs: 'true', verbose: 'false', waitTime: '', waitUnit: 'sec'
              openshiftVerifyBuild bldCfg: 'monster', checkForTriggeredDeployments: 'false', namespace: 'ticket-monster-dev', verbose: 'false', waitTime: ''
           }
        }

        stage('Dev: Deploy new image') {
           steps {
              openshiftDeploy depCfg: 'monster', namespace: 'ticket-monster-dev', verbose: 'false', waitTime: '', waitUnit: 'sec'
              openshiftVerifyDeployment depCfg: 'monster', namespace: 'ticket-monster-dev', replicaCount: '1', verbose: 'false', verifyReplicaCount: 'true', waitTime: '', waitUnit: 'sec'
              openshiftVerifyService namespace: 'ticket-monster-dev', svcName: 'monster', verbose: 'false', retryCount: '5'
           }
        }
    }
}



