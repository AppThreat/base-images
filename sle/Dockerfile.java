FROM registry.suse.com/bci/openjdk-devel:11 as builder

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
    esac; \
    zypper refresh && zypper --non-interactive update && zypper --non-interactive install -l --no-recommends git-core nodejs20 npm20 python311 python311-pip wget zip unzip make awk \
    && zypper clean -a

CMD /bin/bash
