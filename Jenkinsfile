node {

    try {

        def ANDROID_HOME='/opt/android-sdk-linux'
        def ADB="$ANDROID_HOME/platform-tools/adb"

        stage('Checkout') {
            checkout scm
            sh 'git submodule update --init'
        }

        stage('Clean Project') {
            sh "./gradlew clean -PBUILD_NUMBER=${env.BUILD_NUMBER}"
        }

        stage('Build Debug APKs') {
            sh "./gradlew assembleDebug assembleAndroidTest -PBUILD_NUMBER=${env.BUILD_NUMBER}"
        }

        stage('Run unit tests') {
            sh "./gradlew testDebugUnitTest -PBUILD_NUMBER=${env.BUILD_NUMBER}"
        }

/*
        stage('Stage Instrumented Tests') {
            sh "$ADB start-server"

            def error
            parallel (
              launchEmulator: {
                  sh "$ANDROID_HOME/tools/qemu/linux-x86_64/qemu-system-x86_64 -engine classic -prop persist.sys.language=en -prop persist.sys.country=US -avd test -no-snapshot-load -no-snapshot-save -no-window"
              },
              runAndroidTests: {
                  timeout(time: 20, unit: 'SECONDS') {
                    sh "$ADB wait-for-device"
                  }
                  try {
                      sh "./gradlew :app:connectedAndroidTest"
                  } catch(e) {
                      error = e
                  }
                  sh script: '/var/lib/jenkins/kill-emu.sh'
              }
            )
            if (error != null) {
                throw error
            }
          }
*/
        currentBuild.result = "SUCCESS"

    } catch (e) {
        // If there was an exception thrown, the build failed
        currentBuild.result = "FAILED"
        throw e
    } finally {
        // Success or failure, always send notifications
        notifyBuild(currentBuild.result)
    }
}


// paste from https://jenkins.io/blog/2016/07/18/pipline-notifications/
def notifyBuild(String buildStatus = 'STARTED') {
  // build status of null means successful
  buildStatus = buildStatus ?: 'SUCCESS'

  // Default values
  def colorName = 'RED'
  def colorCode = '#FF0000'
  def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
  def summary = "${subject} (${env.BUILD_URL})"
  def details = """<p>STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
    <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>"""

  // Override default values based on build status
  if (buildStatus == 'STARTED') {
    color = 'YELLOW'
    colorCode = '#FFFF00'
  } else if (buildStatus == 'SUCCESS') {
    color = 'GREEN'
    colorCode = '#00FF00'
  } else {
    color = 'RED'
    colorCode = '#FF0000'
  }

  // Send notifications
  slackSend (color: colorCode, message: summary)
}











/*
        def ANDROID_HOME='/opt/android-sdk-linux'
        def ADB="$ANDROID_HOME/platform-tools/adb"

        stage('Run Espresso tests on emulator') {
          sh "$ADB start-server"

          def error
          parallel (
            launchEmulator: {
                sh "$ANDROID_HOME/tools/qemu/linux-x86_64/qemu-system-x86_64 -engine classic -prop persist.sys.language=en -prop persist.sys.country=US -avd test -no-snapshot-load -no-snapshot-save -no-window"
            },
            runAndroidTests: {
                timeout(time: 20, unit: 'SECONDS') {
                  sh "$ADB wait-for-device"
                }
                try {
                    sh "./gradlew connectedAndroidTest"
                } catch(e) {
                    error = e
                }
                sh "$ADB devices | grep emulator | cut -f1 | while read line; do $ADB -s $line emu kill; done"
                sh "$ADB kill-server"
            }
          )
          if (error != null) {
              throw error
          }
        }
*/
