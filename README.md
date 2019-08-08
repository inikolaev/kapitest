# Kung Fu

Library for API test automation which provides a convenient and easy to use Kotlin DSL.

## Example

Here's a simple example which makes request to `https://httpbin.org/get` and verifies
that response status is equal to `200`:

```kotlin
scenario("Match integer status") {
    given {
        schema = "https"
        host = "httpbin.org"
        port = 443
        path = "/get"
    } then {
        status isEqual 200
    }
}
```

The library produces the following output:

```
✔ Match integer status
	✔ status is equal to 200
```