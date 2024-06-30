# Introduction

This repo contains the base images for AppThreat projects such as cdxgen. They were created based on specific issues and challenges observed in enterprise deployments. There will be no FREE community support offered for these images. Pull requests are NOT accepted.

## Unofficial cdxgen variants

### Legacy Java applications

The official cdxgen image bundles Java >= 21 with the latest maven and gradle. Legacy applications that rely on Java 11 can use the unofficial image `ghcr.io/appthreat/cdxgen-java:v10`. For Java 17, use `ghcr.io/appthreat/cdxgen-java17:v10`.

Example invocations:

Java 11 version

```shell
docker run --rm -v /tmp:/tmp -v $HOME/.m2:$HOME/.m2 -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-java11:v10 -r /app -o /app/bom.json -t java
```

Java 17 version

```shell
docker run --rm -v /tmp:/tmp -v $HOME/.m2:$HOME/.m2 -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-java17:v10 -r /app -o /app/bom.json -t java
```

### .Net Framework, .Net Core 3.1, and .Net 6.0 applications

Use the unofficial image `ghcr.io/appthreat/cdxgen-dotnet:v10`.

Example invocation:

.Net Framework 4.6 - 4.8

A bundled version of [nuget](./nuget/) and mono is used to support .Net framework apps.

```shell
docker run --rm -v /tmp:/tmp -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-dotnet6:v10 -r /app -o /app/bom.json -t dotnet-framework
```

Dotnet 3.1 or Dotnet 6.0

```shell
docker run --rm -v /tmp:/tmp -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-dotnet6:v10 -r /app -o /app/bom.json -t dotnet
```

Dotnet 7.0

```shell
docker run --rm -v /tmp:/tmp -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-dotnet7:v10 -r /app -o /app/bom.json -t dotnet
```

Dotnet 8.0

Dotnet 8 is also bundled with the official `ghcr.io/cyclonedx/cdxgen` image.

```shell
docker run --rm -v /tmp:/tmp -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-dotnet8:v10 -r /app -o /app/bom.json -t dotnet
```

Dotnet 9.0

Use the `cdxgen-rolling` image for testing dotnet 9 apps.

```shell
docker run --rm -v /tmp:/tmp -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-rolling:v10 -r /app -o /app/bom.json -t dotnet
```

### Python applications

Use the unofficial image `ghcr.io/appthreat/cdxgen-python312:v10` or `ghcr.io/appthreat/cdxgen-python311:v10`. This includes additional build tools and libraries to build a range of Python applications. Construction of the dependency tree is supported with Python >= 3.9.

Example invocation:

Python 3.6 (Direct dependencies only without dependency tree)

```shell
docker run --rm -v /tmp:/tmp -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-python36:v10 -r /app -o /app/bom.json -t python
```

NOTE: dependency tree is unavailable with Python 3.6

Python 3.9

```shell
docker run --rm -v /tmp:/tmp -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-python39:v10 -r /app -o /app/bom.json -t python
```

Python 3.10

```shell
docker run --rm -v /tmp:/tmp -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-python310:v10 -r /app -o /app/bom.json -t python
```

Python 3.11

```shell
docker run --rm -v /tmp:/tmp -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-python311:v10 -r /app -o /app/bom.json -t python
```

Python 3.12

```shell
docker run --rm -v /tmp:/tmp -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-python312:v10 -r /app -o /app/bom.json -t python
```

### Node.js applications

Use the unofficial image `ghcr.io/appthreat/cdxgen-node20:v10`.

Node.js 20

```shell
docker run --rm -v /tmp:/tmp -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-node20:v10 -r /app -o /app/bom.json -t js
```

## Troubleshooting

### Enable verbose output

Include the below argument to the docker run command (anywhere before the -t argument) to increase the verbosity of the output log.

```
-e CDXGEN_DEBUG_MODE=debug
```

Example:

```shell
docker run --rm -e CDXGEN_DEBUG_MODE=debug -v /tmp:/tmp -v $(pwd):/app:rw -t ghcr.io/appthreat/cdxgen-node20:v10 -r /app -o /app/bom.json -t js
```

## License

MIT

NOTE: No FREE support will be offered for users of these images. Pull Requests are NOT accepted.

## Useful links

- [Identifying .Net vs .Net Framework](https://learn.microsoft.com/en-us/dotnet/standard/frameworks)
