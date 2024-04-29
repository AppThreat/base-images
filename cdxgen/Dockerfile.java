FROM ghcr.io/appthreat/bci-java:main

LABEL maintainer="AppThreat" \
      org.opencontainers.image.authors="Team AppThreat <cloud@appthreat.com>" \
      org.opencontainers.image.source="https://github.com/AppThreat/base-images" \
      org.opencontainers.image.url="https://github.com/AppThreat/base-images" \
      org.opencontainers.image.version="rolling" \
      org.opencontainers.image.vendor="AppThreat" \
      org.opencontainers.image.licenses="Apache-2.0" \
      org.opencontainers.image.title="cdxgen" \
      org.opencontainers.image.description="Rolling image with cdxgen SBOM generator for legacy java apps" \
      org.opencontainers.docker.cmd="docker run --rm -v /tmp:/tmp -p 9090:9090 -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-java:v10 -r /app --server"

RUN npm install -g @cyclonedx/cdxgen --omit=dev

ENTRYPOINT ["cdxgen"]
