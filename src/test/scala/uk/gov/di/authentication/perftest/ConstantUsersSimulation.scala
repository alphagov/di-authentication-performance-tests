package uk.gov.di.authentication.perftest

import io.gatling.core.Predef.{closedInjectionProfileFactory, constantConcurrentUsers, rampConcurrentUsers}
import uk.gov.di.authentication.perftest.support.{Configuration, Parameters}

class ConstantUsersSimulation extends AuthenticationSimulation {
  setUp(
    signinScenario
      .inject(
        constantConcurrentUsers(
          Configuration.peakUsers.toInt
        ) during (Configuration.testDuration)
      )
  )
}
