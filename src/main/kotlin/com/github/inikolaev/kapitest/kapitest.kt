import com.github.inikolaev.kapitest.dsl.KApiTest

fun main(args: Array<String>) {
    KApiTest.scenario("Match integer status") {
        given {
            schema = "https"
            host = "httpbin.org"
            port = 443
            path = "/get"
        } then {
            status isEqual 200
        }
    }

    KApiTest.scenario("Do not match integer status") {
        given {
            schema = "https"
            host = "httpbin.org"
            port = 443
            path = "/get"
        } then {
            status isNotEqual 300
        }
    }

    KApiTest.scenario("Match string status") {
        given {
            schema = "https"
            host = "httpbin.org"
            port = 443
            path = "/get"
        } then {
            status isEqual "200"
        }
    }

    KApiTest.scenario("Do not match invalid string status") {
        given {
            schema = "https"
            host = "httpbin.org"
            port = 443
            path = "/get"
        } then {
            status isNotEqual "invalid number"
        }
    }

    KApiTest.scenario("Status is greater than 100") {
        given {
            schema = "https"
            host = "httpbin.org"
            port = 443
            path = "/get"
        } then {
            status isGreaterThan 100
        }
    }

    KApiTest.scenario("Status is greater or equals 200") {
        given {
            schema = "https"
            host = "httpbin.org"
            port = 443
            path = "/get"
        } then {
            status isGreaterOrEquals 200
        }
    }

    KApiTest.scenario("Status is lower than 300") {
        given {
            schema = "https"
            host = "httpbin.org"
            port = 443
            path = "/get"
        } then {
            status isLowerThan 300
        }
    }

    KApiTest.scenario("Status is lower or equals 200") {
        given {
            schema = "https"
            host = "httpbin.org"
            port = 443
            path = "/get"
        } then {
            status isGreaterOrEquals 200
        }
    }

    KApiTest.scenario("Status is 2xx") {
        given {
            schema = "https"
            host = "httpbin.org"
            port = 443
            path = "/get"
        } then {
            status isGreaterOrEquals 200
            status isLowerThan 300
        }
    }
}
