import com.github.inikolaev.kapitest.dsl.KApiTest

fun main(args: Array<String>) {
    KApiTest.scenario("Test scenario 1") {
        given {
            schema = "https"
            host = "localhost"
            port = 443
        } then {
            status isEqual 200
            headers["content-type"] isEqual "application/json"
        }
    }

    KApiTest.scenario("Test scenario 2") {
        given {
            schema = "https"
            host = "localhost"
            port = 443
        } then {
            status isEqual 300
            headers["content-type"] isEqual "application/json"
        }
    }
}
