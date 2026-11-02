pipeline {
    agent any

    tools { jdk 'JDK17' }

    parameters {
        choice(name: 'EXECUTION_MODE', choices: ['browserstack', 'local'], description: 'Where to run Android tests')
    }

    stages {
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Run tests') {
            steps {
                script {
                    if (params.EXECUTION_MODE == 'browserstack') {
                        withCredentials([
                            usernamePassword(credentialsId: 'browserstack-credentials',
                                usernameVariable: 'BROWSERSTACK_USERNAME',
                                passwordVariable: 'BROWSERSTACK_ACCESS_KEY'),
                            string(credentialsId: 'browserstack-app-id',
                                variable: 'BROWSERSTACK_APP_ID')
                        ]) {
                            sh '''
                              chmod +x gradlew
                              ./gradlew clean test \\
                                -DexecutionMode=browserstack \\
                                -DuserName="$BROWSERSTACK_USERNAME" \\
                                -DaccessKey="$BROWSERSTACK_ACCESS_KEY" \\
                                -Dapp="$BROWSERSTACK_APP_ID"
                            '''
                        }
                    } else {
                        sh './gradlew clean test -DexecutionMode=local'
                    }
                }
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'
            archiveArtifacts allowEmptyArchive: true, artifacts: 'build/allure-results/**'
        }
    }
}
