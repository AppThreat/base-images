# Introduction

This repo contains the base images for AppThreat projects such as cdxgen. They were created based on specific issues and challenges observed in enterprise deployments. There will be no FREE community support offered for these images. Pull requests are NOT accepted.

## Unofficial cdxgen variants

### Legacy Java applications

The official cdxgen image bundles Java >= 21 with the latest maven and gradle. Legacy applications that rely on Java 11 can use the unofficial image `ghcr.io/appthreat/cdxgen-java:v10`. For Java 17, use `ghcr.io/appthreat/cdxgen-java17:v10`.

Example invocations:

Java 11 version

```shell
docker run --rm -v /tmp:/tmp -v $HOME/.m2:$HOME/.m2 -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-java:v10 -r /app -o /app/bom.json -t java
```

Java 17 version

```shell
docker run --rm -v /tmp:/tmp -v $HOME/.m2:$HOME/.m2 -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-java17:v10 -r /app -o /app/bom.json -t java
```

### Dotnet Core 3.1 and Dotnet 6.0 applications

Use the unofficial image `ghcr.io/appthreat/cdxgen-dotnet:v10`.

Example invocation:

```shell
docker run --rm -v /tmp:/tmp -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-dotnet:v10 -r /app -o /app/bom.json -t dotnet
```

### Python applications

Use the unofficial image `ghcr.io/appthreat/cdxgen-python:v10`. This includes additional build tools and libraries to build a range of Python applications.

Example invocation:

```shell
docker run --rm -v /tmp:/tmp -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-python:v10 -r /app -o /app/bom.json -t python
```

## License

MIT

NOTE: No FREE support will be offered for users of these images. Pull Requests are NOT accepted.
