package uk.gov.di.authentication.perftest

import io.gatling.core.Predef.{
  configuration,
  openInjectionProfileFactory,
  rampUsersPerSec
}
import io.gatling.http.Predef.http
import uk.gov.di.authentication.perftest.support.{Configuration, Parameters}

class RampItSimulation extends AuthenticationSimulation {

  val rampStart: Double =
    Parameters.doubleParameter("rampStart", 1)

  setUp(
    signinScenario
      .inject(
        rampUsersPerSec(
          rampStart
        ) to Configuration.peakUsers during (Configuration.testDuration)
      )
  ).protocols(http)
}
