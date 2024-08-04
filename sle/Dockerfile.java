FROM registry.suse.com/bci/openjdk-devel:11

ARG SBT_VERSION=1.8.3
ARG MAVEN_VERSION=3.6.3
ARG GRADLE_VERSION=7.6.4

ENV SBT_VERSION=$SBT_VERSION \
    MAVEN_VERSION=$MAVEN_VERSION \
    GRADLE_VERSION=$GRADLE_VERSION \
    GRADLE_OPTS="-Dorg.gradle.daemon=false" \
    MAVEN_HOME="/opt/maven/${MAVEN_VERSION}" \
    GRADLE_HOME="/opt/gradle/${GRADLE_VERSION}" \
    SBT_HOME="/opt/sbt/${SBT_VERSION}" \
    ANDROID_SDK_ROOT=/opt/android-sdk-linux \
    ANDROID_HOME=/opt/android-sdk-linux \
    LC_ALL=en_US.UTF-8 \
    LANG=en_US.UTF-8 \
    LANGUAGE=en_US.UTF-8 \
    JAVA_OPTIONS="-Dhttps.protocols=TLSv1.1,TLSv1.2" \
    PATH=${PATH}:${MAVEN_HOME}/bin:${GRADLE_HOME}/bin:${SBT_HOME}/bin:${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/tools:${ANDROID_HOME}/tools/bin:${ANDROID_HOME}/platform-tools:


RUN set -e; \
    ARCH_NAME="$(rpm --eval '%{_arch}')"; \
    url=; \
    case "${ARCH_NAME##*-}" in \
        'x86_64') \
            OS_ARCH_SUFFIX=''; \
            GOBIN_VERSION='amd64'; \
            ;; \
        'aarch64') \
            OS_ARCH_SUFFIX='-aarch64'; \
            GOBIN_VERSION='arm64'; \
            ;; \
        *) echo >&2 "error: unsupported architecture: '$ARCH_NAME'"; exit 1 ;; \
    esac \
    && zypper refresh && zypper --non-interactive update && zypper --non-interactive install -l --no-recommends git-core nodejs20 npm20 python311 python311-pip wget zip unzip make gawk \
    && curl -s "https://get.sdkman.io" | bash \
    && source "$HOME/.sdkman/bin/sdkman-init.sh" \
    && echo -e "sdkman_auto_answer=true\nsdkman_selfupdate_feature=false\nsdkman_auto_env=true\nsdkman_curl_connect_timeout=20\nsdkman_curl_max_time=0" >> $HOME/.sdkman/etc/config \
    && sdk install maven $MAVEN_VERSION \
    && sdk install gradle $GRADLE_VERSION \
    && sdk install sbt $SBT_VERSION \
    && sdk offline enable \
    && mv /root/.sdkman/candidates/* /opt/ \
    && rm -rf /root/.sdkman \
    && mkdir -p ${ANDROID_HOME}/cmdline-tools \
    && curl -L https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip -o ${ANDROID_HOME}/cmdline-tools/android_tools.zip \
    && unzip ${ANDROID_HOME}/cmdline-tools/android_tools.zip -d ${ANDROID_HOME}/cmdline-tools/ \
    && rm ${ANDROID_HOME}/cmdline-tools/android_tools.zip \
    && mv ${ANDROID_HOME}/cmdline-tools/cmdline-tools ${ANDROID_HOME}/cmdline-tools/latest \
    && yes | /opt/android-sdk-linux/cmdline-tools/latest/bin/sdkmanager --licenses --sdk_root=/opt/android-sdk-linux \
    && /opt/android-sdk-linux/cmdline-tools/latest/bin/sdkmanager 'platform-tools' --sdk_root=/opt/android-sdk-linux \
    && /opt/android-sdk-linux/cmdline-tools/latest/bin/sdkmanager 'platforms;android-33' --sdk_root=/opt/android-sdk-linux \
    && /opt/android-sdk-linux/cmdline-tools/latest/bin/sdkmanager 'build-tools;33.0.0' --sdk_root=/opt/android-sdk-linux \
    && curl -L --output /usr/bin/bazel https://github.com/bazelbuild/bazelisk/releases/latest/download/bazelisk-linux-${GOBIN_VERSION} \
    && chmod +x /usr/bin/bazel \
    && bazel --version \
    && zypper clean -a

CMD /bin/bash
