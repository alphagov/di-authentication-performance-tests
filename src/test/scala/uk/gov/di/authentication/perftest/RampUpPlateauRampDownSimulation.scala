package uk.gov.di.authentication.perftest

import io.gatling.core.Predef.{
  configuration,
  constantUsersPerSec,
  nothingFor,
  openInjectionProfileFactory,
  rampUsersPerSec
}
import io.gatling.http.Predef.http
import uk.gov.di.authentication.perftest.support.{Configuration, Parameters}

class RampUpPlateauRampDownSimulation extends AuthenticationSimulation {

  val rampStart: Double =
    Parameters.doubleParameter("rampStart", 1)

  setUp(
    signinScenario
      .inject(
        nothingFor(Configuration.warmUpDuration),
        rampUsersPerSec(
          rampStart
        ) to Configuration.peakUsers during (Configuration.testDuration / 4),
        constantUsersPerSec(
          Configuration.peakUsers
        ) during (Configuration.testDuration / 2),
        rampUsersPerSec(
          Configuration.peakUsers
        ) to rampStart during (Configuration.testDuration / 4)
      )
  ).protocols(http)
}
