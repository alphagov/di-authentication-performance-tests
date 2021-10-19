#!/usr/bin/env bash

# shellcheck disable=SC2046
export $(grep -v '^#' .env | xargs)

./gradlew spotlessApply

#
# Choose from the following scenarios
#

./gradlew clean gatlingTest zipTestResults \
    -Pscenario=ConstantUsersSimulation \
    -Dduration=600 \
    -DpeakUsers=2

#./gradlew clean gatlingTest zipTestResults \
#    -Pscenario=RampItSimulation \
#    -Dduration=120 \
#    -DpeakUsers=8

#./gradlew clean gatlingTest zipTestResults \
#    -Pscenario=RampUpPlateauRampDownSimulation \
#    -Dduration=15 \
#    -DpeakUsers=0.5 \
#    -DrampStart=0.1 \
