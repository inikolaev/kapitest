import com.github.inikolaev.kapitest.dsl.KApiTest

fun main(args: Array<String>) {
    KApiTest.scenario("Test scenario 1") {
        given {
            schema = "https"
            host = "httpbin.org"
            port = 443
            path = "/get"
        } then {
            status isEqual 200
            headers["content-type"] isEqual "application/json"
        }
    }

    KApiTest.scenario("Test scenario 2") {
        given {
            schema = "https"
            host = "httpbin.org"
            port = 443
            path = "/get"
        } then {
            status isEqual 300
            headers["content-type"] isEqual "application/json"
            body isEqual "some response body"
        }
    }
}
