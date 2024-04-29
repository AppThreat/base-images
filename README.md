# Introduction

This repo contains the base images for AppThreat projects such as cdxgen. They were created based on specific issues and challenges observed in enterprise deployments so may or may not work for everyone.

## Unofficial cdxgen variants

### Legacy Java applications

The official cdxgen image bundles Java 20 with the latest maven and gradle. Legacy applications that rely on Java 11 can use the unofficial image `ghcr.io/appthreat/cdxgen-java:v10`.

Example invocation:

```shell
docker run --rm -v /tmp:/tmp -v $HOME/.m2:$HOME/.m2 -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-java:v10 -r /app -o /app/bom.json -t java
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
