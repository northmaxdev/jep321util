# About
This project aims to provide a tiny set of modern, high-quality utility classes to complement the native HTTP client API
introduced in Java 11. The inspiration was initially drawn from a similar set of utilities provided in the
[Apache HTTP client](https://hc.apache.org/index.html) API, most notably — the `URIBuilder` class. This project's name
is a reference to the JEP that finalized the Java native HTTP client proposal.

The library's contents are based primarily on [RFC 3986](https://www.rfc-editor.org/rfc/rfc3986). It should be noted,
however, that the API does not follow the publication to the T. Instead, it:
1. Is designed specifically around HTTP (the RFC itself is a lot more universal)
2. Aims to simplify/streamline things when and where it is reasonable to do so

The library's API was designed to match Java 8 and Java 11 in terms of modernity. For instance, it makes heavy use of
immutability, static factory methods, and other related "best practices" and conventions. 

# Usage Examples
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

# Building & Installing
*Coming soon!*

# Licensing
See the [license file](LICENSE) for more information.
