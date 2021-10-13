package uk.gov.di.authentication.perftest.support

import scala.concurrent.duration.{DurationLong, FiniteDuration}
import scala.language.postfixOps

object Configuration {
  val OP_API_BASE_URL =
    System
      .getenv()
      .getOrDefault(
        "OP_API_BASE_URL",
        "https://api.build.auth.ida.digital.cabinet-office.gov.uk"
      )

  val FRONTEND_URL =
    System
      .getenv()
      .getOrDefault(
        "FRONTEND_URL",
        "https://front.build.auth.ida.digital.cabinet-office.gov.uk"
      )

  val TEST_CLIENT_ID =
    System
      .getenv()
      .getOrDefault(
        "TEST_CLIENT_ID",
        "perf-test-client"
      )

  val TEST_USER_EMAIL = System
    .getenv()
    .getOrDefault(
      "TEST_USER_EMAIL",
      "test.user@digital.cabinet-office.gov.uk"
    )

  val TEST_USER_PASSWORD = System
    .getenv()
    .getOrDefault(
      "TEST_USER_PASSWORD",
      "passw0rd"
    )

  val TEST_USER_PHONE_CODE = System
    .getenv()
    .getOrDefault(
      "TEST_USER_PHONE_CODE",
      "123456"
    )

  val REDIRECT_URI = System
    .getenv()
    .getOrDefault(
      "REDIRECT_URI",
      "http://localhost:8080"
    )

  val AM_URL = System
    .getenv()
    .getOrDefault(
      "AM_URL",
      "http://localhost:8085"
    )

  val testDuration: FiniteDuration =
    Integer.getInteger("duration", 1).toLong seconds

  val warmUpRate: Double = Parameters.doubleParameter("warmUpRate", 1)

  val warmUpDuration: FiniteDuration =
    Integer.getInteger("warmUpDuration", 1).toLong seconds

  val peakUsers: Double = Parameters.doubleParameter("peakUsers", 1)

  val expectedMinFailurePercentage: Double =
    Parameters.doubleParameter("expectedMinFailurePercentage", 32)
  val expectedMaxFailurePercentage: Double =
    Parameters.doubleParameter("expectedMaxFailurePercentage", 35)

  val pauseMin: FiniteDuration = Parameters
    .doubleParameter("pauseBetweenRequestsInSecondsMin", 3)
    .toLong seconds
  val pauseMax: FiniteDuration = Parameters
    .doubleParameter("pauseBetweenRequestsInSecondsMax", 6)
    .toLong seconds
}
