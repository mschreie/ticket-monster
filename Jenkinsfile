node {
   stage 'Checkout'
   // git branch: 'master', url: 'https://github.com/mschreie/ticket-monster.git'
   git branch: '2.7.0.Final', url: 'https://github.com/mschreie/ticket-monster.git'

   
   // ** NOTE: This 'M3' maven tool must be configured in the global configuration.
   // def mvnHome = tool 'maven'
   
   stage 'Build'
   // sh "${mvnHome}/bin/mvn -f demo/pom.xml clean install"
   openshiftBuild bldCfg: 'ticket-monster', env: [[name: 'MAVEN_MIRROR_URL', value: 'http://nexus3-nexus.apps.1db2.oslab.opentlc.com/repository/maven-all-public']], namespace: 'ticket-monster-dev', showBuildLogs: 'true', verbose: 'false', waitTime: '', waitUnit: 'sec'
    openshiftVerifyBuild bldCfg: 'ticket-monster', checkForTriggeredDeployments: 'false', namespace: 'ticket-monster-dev', verbose: 'false', waitTime: ''
   
   stage 'Deploy to dev'
   def builder = new com.openshift.jenkins.plugins.pipeline.OpenShiftBuilder("", "ticket-monster", "ticket-monster-dev", "", "", "", "", "true", "", "")
   step builder
}

