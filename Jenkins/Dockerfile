FROM jenkinsci/jnlp-slave
MAINTAINER Paul Bruce <me@paulsbruce.io>
ARG BUILD_TOOLS_VERSION=26.0.0
ARG ANDROID_VERSION=26

##begin #android
USER root
RUN dpkg --add-architecture i386 && \
    apt-get update && \
    apt-get install -y --force-yes expect git wget libc6-i386 lib32stdc++6 lib32gcc1 lib32ncurses5 lib32z1 unzip && \
    apt-get clean && \
    apt-get autoclean && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

COPY ./scripts /opt/scripts
RUN chmod +x /opt/scripts/android-accept-licenses.sh

# Install Android tools
ENV SDK_VERSION "r25.2.5"
RUN mkdir /opt/android-sdk-linux && \
    cd /opt/android-sdk-linux
#RUN wget --output-document=tools-sdk.zip --quiet https://dl.google.com/android/repository/tools_${SDK_VERSION}-linux.zip
COPY cache/tools-sdk.zip tools-sdk.zip
RUN unzip tools-sdk.zip -d /opt/android-sdk-linux && \
    rm -f tools-sdk.zip && \
    chown -R jenkins.jenkins /opt

# Setup environment
ENV ANDROID_HOME /opt/android-sdk-linux
ENV PATH ${PATH}:${ANDROID_HOME}/tools/bin:${ANDROID_HOME}/tools:${ANDROID_HOME}/platform-tools

## Set up insecure default key
#RUN mkdir -m 0750 /home/jenkins/.android
#ADD files/adbkey /home/jenkins/.android/adbkey
#ADD files/adbkey.pub /home/jenkins/.android/adbkey.pub

## install Android SDK, auto-accepting terms of service
#RUN ( sleep 5 && while [ 1 ]; do sleep 1; echo y; done ) | \
    /opt/android-sdk-linux/tools/android update sdk --no-ui --all --filter \
    platform-tools,build-tools-${BUILD_TOOLS_VERSION},android-${ANDROID_VERSION}

#RUN which adb
#RUN which android

#RUN touch /home/jenkins/.android/repositories.cfg

#CMD ["start"]

##end #android


