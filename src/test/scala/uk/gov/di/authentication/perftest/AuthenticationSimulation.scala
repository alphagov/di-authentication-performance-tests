package uk.gov.di.authentication.perftest

import io.gatling.core.Predef.{
  exec,
  findCheckBuilder2ValidatorCheckBuilder,
  scenario
}
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef.{http, status}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.di.authentication.perftest.support.{Configuration, Parameters}

import scala.language.postfixOps

class AuthenticationSimulation extends Simulation {

  val authorizeRequestString: String =
    "/authorize?" +
      "scope=openid+phone+email&" +
      "response_type=code&" +
      "redirect_uri=" + Configuration.REDIRECT_URI + "&" +
      "state=sEazICy8jKFFlt-NLSw5yqYRA2r4q5BZGcAf9sYeWRg&" +
      "nonce=gyRdMfQGsQS9BvhU-lBwENOZ0UU&" +
      "client_id=" +
      Configuration.TEST_CLIENT_ID +
      "&cookie_consent=accept";

  val warmUpScenario: ScenarioBuilder =
    scenario("Warm up stack by making a status request or two")
      .exec(
        buildGetRequest(
          "hello",
          Configuration.FRONTEND_URL + "/sign-in-or-create",
          401
        )
      )

  val signinScenario: ScenarioBuilder =
    scenario("Existing user sign in")
      .exec(
        buildGetRequest(
          "authorize",
          Configuration.OP_API_BASE_URL + authorizeRequestString,
          200
        ),
        reqCreateOrSignInPost(),
        reqEnterEmailPost(),
        reqEnterPasswordPost(),
        reqEnterCodePost(),
        reqAccountManagementLaunchGetRequest()
      )

  private def buildGetRequest(
      name: String,
      path: String,
      expectedStatus: Int
  ): ChainBuilder = {
    exec(
      http(f"GET - $name%s")
        .get(path)
        .check(
          status.is(expectedStatus),
          regex("""<input type="hidden" name="_csrf" value="(.*)"/>""")
            .saveAs("csrfToken")
        )
        .header("User-Agent", "Chrome")
    ).pause(Configuration.pauseMin, Configuration.pauseMax)
  }

  private def reqCreateOrSignInPost() = exec(
    http("POST - CreateOrSignIn")
      .post(Configuration.FRONTEND_URL + "/sign-in-or-create")
      .formParam("_csrf", "${csrfToken}")
      .formParam("createAccount", "false")
      .check(
        status.is(200)
      )
  ).pause(Configuration.pauseMin, Configuration.pauseMax)

  private def reqEnterEmailPost() = exec(
    http("POST - Enter Email")
      .post(Configuration.FRONTEND_URL + "/enter-email")
      .formParam("_csrf", "${csrfToken}")
      .formParam("email", Configuration.TEST_USER_EMAIL)
      .check(status.is(200))
  )
    .pause(Configuration.pauseMin, Configuration.pauseMax)

  private def reqEnterPasswordPost() = exec(
    http("POST - Enter Password")
      .post(Configuration.FRONTEND_URL + "/enter-password")
      .formParam("_csrf", "${csrfToken}")
      .formParam("password", Configuration.TEST_USER_PASSWORD)
      .check(status.is(200))
  ).pause(Configuration.pauseMin, Configuration.pauseMax)

  private def reqEnterCodePost() = exec(
    http("POST - Enter Code")
      .post(Configuration.FRONTEND_URL + "/enter-code")
      .formParam("_csrf", "${csrfToken}")
      .formParam("code", Configuration.TEST_USER_PHONE_CODE)
      .check(status.is(200))
      .header("User-Agent", "Chrome")
  ).pause(Configuration.pauseMin, Configuration.pauseMax)

  private def reqAccountManagementLaunchGetRequest(): ChainBuilder = {
    exec(
      http("GET - Account Management")
        .get(Configuration.AM_URL)
        .check(
          status.is(200)
        )
        .header("User-Agent", "Chrome")
    ).pause(Configuration.pauseMin, Configuration.pauseMax)
  }

}
