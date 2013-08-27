#!/bin/sh
# Source: http://developer.android.com/tools/testing/testing_otheride.html#RunTestsCommand
adb shell am instrument -w -r cucumber.example.android.cukeulator.test/cucumber.android.api.CucumberInstrumentation
