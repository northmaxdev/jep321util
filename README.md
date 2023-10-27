## About
This project aims to provide a tiny set of modern, high-quality utility classes to complement the native HTTP client API
introduced in Java 11. The inspiration was initially drawn from a similar set of utilities provided in the
[Apache HTTP client](https://hc.apache.org/index.html) API, most notably — the `URIBuilder` class. This project's name
is a reference to [the JEP that finalized the Java native HTTP client proposal](https://openjdk.org/jeps/321).

The library's contents are based primarily on [RFC 3986](https://www.rfc-editor.org/rfc/rfc3986). It should be noted,
however, that the API does not follow the publication to the T. Instead, it:
1. Is designed specifically around HTTP (the RFC itself is a lot more universal)
2. Aims to simplify/streamline things when and where it is reasonable to do so

## API Design

The `jep321util` library tries its best to stay in touch with the evolution of modern Java's conventions and best practices:

- **Immutability:** all classes in the library are deeply immutable by default *unless noted otherwise.*
- **Object creation:** all classes in the library are instantiated using primarily static factory methods.
  These methods use conventional names, such as: `of*`, `valueOf`, `create`, `from*`, `parse`, etc.
- **Nullability**: `null`s are actively avoided/discouraged. For parameters, if possible, classes attempt to handle `null`s gracefully,
  such as converting a `null` `Collection` to an empty one instead of throwing a `NullPointerException`. For return values, the library
  makes heavy use of `Optional` and empty collections. Either way, nullability is generally explicitly documented.
- **Standard textual representations:** a class that has a standardized "textual representation" (i.e. syntax) will have a `parse(String)`
  (or an equivalent) static factory method and a `toString` that is "symmetrical" to it (that is, `toString` will return values that are
  compatible with the `parse` method)

## Usage Examples
```java
/* http://localhost:8080/orders/12345 */
URI uri = URIBuilder.withLocalhost()
        .http()
        .port(8080)
        .pathSegment("orders")
        .pathSegment(12345)
        .build();
```

```java
/* https://example.com/convert?from=978&includeInactive=true */
URI uri = URIBuilder.withValidHost("example.com")
        .pathSegment("convert")
        .param("from", 978)
        .param("includeInactive", true)
        .build();
```

## Building & Installing
*Coming soon!*

## Licensing
See the [license file](LICENSE) for more information.
