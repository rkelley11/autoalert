package general

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

import groovy.time.*

import org.openqa.selenium.*

import com.kms.katalon.core.util.KeywordUtil

public class Overlays extends WrappedSelenium{

	def waitForOverlayToClear(){

		KeywordUtil.logInfo("A By locator must be passed to general.Overlays.waitForOverlayToClear(By by)")

		false
	}

	@Keyword
	def waitForOverlayToClear(By by){

		def overlayClasses, overlay, pageLoaded, spinnerShowing, counter

		def stillChecking = true

		def timeExpired = false

		pageLoaded = spinnerShowing = false

		def timeStart = new Date()

		def timeoutPeriodToStart = new TimeDuration(0,0,3,0)

		def timeoutPeriodToClose = new TimeDuration(0,0,90,0)

		counter = 0

		// Wait for spinner/loading message to start.  Sometimes there's a slight delay - REFACTOR? See below
		while (!spinnerShowing && !timeExpired){

			counter += 1

			KeywordUtil.logInfo("Checking for overlay to SHOW. Attempt #" + counter as String)

			overlay = findElements(by, 2)

			if(overlay.size() > 0){

				KeywordUtil.logInfo("Checking overlay class to see if it DOES NOT contain 'ng-hide' = SHOWING")

				overlayClasses = getAttribute(overlay[0], "class")

				stillChecking = overlayClasses.contains("ng-hide")
			}

			if (stillChecking || !overlay){

				timeExpired = isTimeExpired(timeStart, timeoutPeriodToStart)

				KeywordUtil.logInfo("Overlay is NOT SHOWING.")

				if(timeExpired){

					KeywordUtil.logInfo("Time has expired (" + (timeoutPeriodToStart as String) +
							") waiting for overlay to SHOW. Exiting loop.")
				}
				else{

					KeywordUtil.logInfo("Waiting 1 second before checking again")

					sleep(1000) // Wait 1 second before polling again
				}
			}
			else{

				KeywordUtil.logInfo("Overlay is SHOWING. Exiting loop.")

				spinnerShowing = true
			}
		}

		timeExpired = false

		timeStart = new Date()

		counter = 0

		// Wait for spinner/loading message to stop - REFACTOR? See above
		while (!pageLoaded && !timeExpired){

			counter += 1

			KeywordUtil.logInfo("Checking for overlay to HIDE. Attempt #" + counter as String)

			overlay = findElements(by, 2)

			if(overlay){

				KeywordUtil.logInfo("Checking overlay class to see if it DOES contain 'ng-hide' = HIDDEN")

				overlayClasses = getAttribute(overlay[0], "class")

				stillChecking = !overlayClasses.contains("ng-hide")

				if (stillChecking){

					KeywordUtil.logInfo("Overlay is NOT HIDDEN.")

					timeExpired = isTimeExpired(timeStart, timeoutPeriodToClose)

					if(timeExpired){

						KeywordUtil.logInfo("Time has expired (" + (timeoutPeriodToClose as String) +
								") waiting for overlay to HIDE. Exiting loop.")
					}
					else{

						KeywordUtil.logInfo("\tWaiting 1 second before checking again")

						sleep(1000) // Wait 1 second before polling again
					}
				}
				else{

					KeywordUtil.logInfo("Overlay is HIDDEN. Exiting loop.")

					pageLoaded = true
				}
			}
		}

		pageLoaded
	}

	def waitForOverlayToClear(TestObject testObject){

		def overlayClasses, overlay, pageLoaded, spinnerShowing, counter

		def stillChecking = true

		def timeExpired = false

		pageLoaded = spinnerShowing = false

		def timeStart = new Date()

		def timeoutPeriodToStart = new TimeDuration(0,0,3,0)

		def timeoutPeriodToClose = new TimeDuration(0,0,90,0)

		counter = 0

		// Wait for spinner/loading message to start.  Sometimes there's a slight delay - REFACTOR? See below
		while (!spinnerShowing && !timeExpired){

			counter += 1

			KeywordUtil.logInfo("Checking for overlay to SHOW. Attempt #" + counter as String)

			KeywordUtil.logInfo("Checking overlay class to see if it DOES NOT contain 'ng-hide' = SHOWING")

			overlayClasses = WebUI.getAttribute(testObject, 'class')

			stillChecking = overlayClasses.contains("ng-hide")

			if (stillChecking || !overlay){

				timeExpired = isTimeExpired(timeStart, timeoutPeriodToStart)

				KeywordUtil.logInfo("Overlay is NOT SHOWING.")

				if(timeExpired){

					KeywordUtil.logInfo("Time has expired (" + (timeoutPeriodToStart as String) +
							") waiting for overlay to SHOW. Exiting loop.")
				}
				else{

					KeywordUtil.logInfo("Waiting 1 second before checking again")

					sleep(1000) // Wait 1 second before polling again
				}
			}
			else{

				KeywordUtil.logInfo("Overlay is SHOWING. Exiting loop.")

				spinnerShowing = true
			}
		}

		timeExpired = false

		timeStart = new Date()

		counter = 0

		// Wait for spinner/loading message to stop - REFACTOR? See above
		while (!pageLoaded && !timeExpired){

			counter += 1

			KeywordUtil.logInfo("Checking for overlay to HIDE. Attempt #" + counter as String)

			overlayClasses = WebUI.getAttribute(testObject, 'class')

			KeywordUtil.logInfo("Checking overlay class to see if it DOES contain 'ng-hide' = HIDDEN")

			stillChecking = !overlayClasses.contains("ng-hide")

			if (stillChecking){

				KeywordUtil.logInfo("Overlay is NOT HIDDEN.")

				timeExpired = isTimeExpired(timeStart, timeoutPeriodToClose)

				if(timeExpired){

					KeywordUtil.logInfo("Time has expired (" + (timeoutPeriodToClose as String) +
							") waiting for overlay to HIDE. Exiting loop.")
				}
				else{

					KeywordUtil.logInfo("\tWaiting 1 second before checking again")

					sleep(1000) // Wait 1 second before polling again
				}
			}
			else{

				KeywordUtil.logInfo("Overlay is HIDDEN. Exiting loop.")

				pageLoaded = true
			}
		}

		pageLoaded
	}
}
