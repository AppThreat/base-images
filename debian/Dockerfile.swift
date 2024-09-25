FROM debian:12

# pub   rsa4096 2024-09-16 [SC] [expires: 2026-09-16]
#      52BB7E3DE28A71BE22EC05FFEF80A866B47A981F
# uid           [ unknown] Swift 6.x Release Signing Key <swift-infrastructure@forums.swift.org>
ARG SWIFT_SIGNING_KEY=52BB7E3DE28A71BE22EC05FFEF80A866B47A981F
ARG SWIFT_PLATFORM=debian12
ARG SWIFT_BRANCH=swift-6.0-release
ARG SWIFT_VERSION=swift-6.0-RELEASE
ARG SWIFT_WEBROOT=https://download.swift.org
ARG SWIFT_STATIC_SDK_CHECKSUM=7984c2cf175bde52ba6ea1fcbe27fc4a148a6237c41c719209c9288ed3ceb652
ARG SOURCEKITTEN_VERSION=0.36.0

ENV SWIFT_SIGNING_KEY=$SWIFT_SIGNING_KEY \
    SWIFT_PLATFORM=$SWIFT_PLATFORM \
    SWIFT_BRANCH=$SWIFT_BRANCH \
    SWIFT_VERSION=$SWIFT_VERSION \
    SWIFT_WEBROOT=$SWIFT_WEBROOT \
    PATH=${PATH}:/usr/local/bin:
    
RUN set -e; \
    ARCH_NAME="$(dpkg --print-architecture)"; \
    url=; \
    case "${ARCH_NAME##*-}" in \
        'amd64') \
            OS_ARCH_SUFFIX=''; \
            ;; \
        'arm64') \
            OS_ARCH_SUFFIX='-aarch64'; \
            ;; \
        *) echo >&2 "error: unsupported architecture: '$ARCH_NAME'"; exit 1 ;; \
    esac; \
    export DEBIAN_FRONTEND=noninteractive DEBCONF_NONINTERACTIVE_SEEN=true && apt-get -q update && \
    apt-get -q install -y \
        curl gpg \
        binutils-gold \
        libicu-dev \
        libcurl4-openssl-dev \
        libedit-dev \
        libsqlite3-dev \
        libncurses-dev \
        libpython3-dev \
        libxml2-dev \
        pkg-config \
        uuid-dev \
        tzdata \
        git \
        gcc \
    && SWIFT_WEBDIR="$SWIFT_WEBROOT/$SWIFT_BRANCH/$(echo $SWIFT_PLATFORM | tr -d .)$OS_ARCH_SUFFIX" \
    && SWIFT_BIN_URL="$SWIFT_WEBDIR/$SWIFT_VERSION/$SWIFT_VERSION-$SWIFT_PLATFORM$OS_ARCH_SUFFIX.tar.gz" \
    && SWIFT_SIG_URL="$SWIFT_BIN_URL.sig" \
    && export DEBIAN_FRONTEND=noninteractive \
    && export GNUPGHOME="$(mktemp -d)" \
    && curl -fsSL "$SWIFT_BIN_URL" -o swift.tar.gz "$SWIFT_SIG_URL" -o swift.tar.gz.sig \
    && gpg --batch --quiet --keyserver keyserver.ubuntu.com --recv-keys "$SWIFT_SIGNING_KEY" \
    && gpg --batch --verify swift.tar.gz.sig swift.tar.gz \
    && tar -xzf swift.tar.gz --directory / --strip-components=1 \
    && chmod -R o+r /usr/lib/swift \
    && rm -rf "$GNUPGHOME" swift.tar.gz.sig swift.tar.gz \
    && rm -rf /var/lib/apt/lists/* \
    && swift --version \
    && swift sdk install ${SWIFT_WEBROOT}/${SWIFT_BRANCH}/static-sdk/${SWIFT_VERSION}/${SWIFT_VERSION}_static-linux-0.0.1.artifactbundle.tar.gz --checksum ${SWIFT_STATIC_SDK_CHECKSUM} \
    && mkdir -p /opt/kitten /usr/local/bin \
    && curl -L https://github.com/jpsim/SourceKitten/releases/download/${SOURCEKITTEN_VERSION}/SourceKitten-${SOURCEKITTEN_VERSION}.tar.gz -o /opt/kitten/SourceKitten.tar.gz \
    && cd /opt/kitten/ && tar -xvf SourceKitten.tar.gz \
    && cd /opt/kitten/SourceKitten-${SOURCEKITTEN_VERSION} \
    && swift build -c release \
    && cp /opt/kitten/SourceKitten-${SOURCEKITTEN_VERSION}/.build/release/sourcekitten /usr/local/bin/sourcekitten \
    && cd /root && rm -rf /opt/kitten \
    && sourcekitten --help
