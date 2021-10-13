# di-authentication-performance-tests

Performance tests for the GOV.UK account authentication component. 

The project uses the [Gatling Stress Tool](https://gatling.io).
 
## How to run the tests

1. Clone this repo.
2. Take a copy of the `.env.sample` file and rename it to `.env`.
3. Fill in the all the environment variable values according to the environment under test.
4. Choose a scenario to run in `run-perf-tests.sh` and comment out the rest.
5. Choose the desired paramaters such as test duration and number of concurrent users.
6. Before running any tests it's best to inform the Cyber team so they don't think anything untoward is happening.  It's better to run the tests when not connected to the VPN.
7. Make sure there is a test client configured in the environment being used for testing.
8. Make a note of the client id.
9. Decide on a test email then sign up this email manually in the test environment.
10. Add the email to the test client allowlist in the database.  This will suppress notifications when running the tests.
11. Run `run-perf-tests.sh` to start the tests.
12. When the tests have run a zip containing the reports is generated.

**Only run the tests using a test client and test user so that notifications are suppressed.**

### Environment Variables

| Name | Description |
|---|---|
|OP_API_BASE_URL|Url to the authorization server|
|FRONTEND_URL|Base url of the frontend application|
|AM_URL|Base url of the account management application|
|REDIRECT_URI|Valid redirect uri for the client|
|TEST_CLIENT_ID|Client id of the test client|
|TEST_USER_EMAIL|Email being used for testing|
|TEST_USER_PASSWORD|Password for the account|
|TEST_USER_PHONE_CODE|Test one time password|
