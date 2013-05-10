#!/bin/sh
# Source: http://developer.android.com/tools/testing/testing_otheride.html#RunTestsCommand
adb shell am instrument -w -r at.mfellner.android.cucumber.example.test/at.mfellner.cucumber.android.api.CucumberInstrumentation
