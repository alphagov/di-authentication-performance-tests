package uk.gov.di.authentication.perftest

import io.gatling.core.Predef.{exec, scenario}
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.scenario.Simulation

class HelloWorldAuthSimulation extends Simulation {

  val scnHelloWorldAuth: ScenarioBuilder =
    scenario("HelloWorldAuth")
      .exec(buildGetRequest("hello", "http://bbc.co.uk"))
      .exec { session =>
        println("HelloWorldAuth: \n" + session)
        session
      }

  private def buildGetRequest(name: String, path: String): ChainBuilder = {
    exec(
      http(f"GET - $name%s")
        .get(path)
        .check(status.is(200))
    )
  }

  setUp(
    scnHelloWorldAuth.inject(atOnceUsers(1))
  )
}
