package test

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

import general.*
import general.Enums.Alerts

import sql.*
import java.time.*
import java.time.format.*
import java.util.concurrent.TimeUnit

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class Assistant extends WrappedSelenium{

	private defaultMessage = 'No message was given'

	//================================================================================================================
	//     WAIT FOR ELEMENT TO BE PRESENT
	//================================================================================================================

	@Keyword
	def waitForElementPresent(TestObject testObject, int timeOut, AfterFailure flowControl=AfterFailure.CONTINUE){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		timeOut = timeOut > 0 && timeOut < 200 ? timeOut : 30

		def testObjectPresent = false

		def testObjectName = new TestObjects().getTestObjectName(testObject)

		KeywordUtil.logInfo(logHeader + 'Verifying presence of test object: ' + (testObjectName as String))

		testObjectPresent = WebUI.waitForElementPresent(testObject, timeOut)

		def stepMessage = 'Verification that \'' + (testObjectName as String) + '\' IS present ' + (testObjectPresent ? 'PASSED' : 'FAILED')

		markTestStep(testObjectPresent, stepMessage, flowControl)

		testObjectPresent
	}

	//================================================================================================================
	//     WAIT FOR ELEMENT NOT PRESENT
	//================================================================================================================

	@Keyword
	def waitForElementNotPresent(TestObject testObject, int timeOut){

		waitForElementNotPresent(testObject, timeOut, AfterFailure.CONTINUE)
	}

	def waitForElementNotPresent(TestObject testObject, int timeOut, AfterFailure flowControl){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		timeOut = timeOut > 0 && timeOut < 200 ? timeOut : 30

		def testObjectNotPresent = false

		def testObjectName = new TestObjects().getTestObjectName(testObject)

		KeywordUtil.logInfo(logHeader + 'Verifying \''  + (testObjectName as String) + '\' test object is not present.')

		testObjectNotPresent = WebUI.waitForElementNotPresent(testObject, timeOut, FailureHandling.CONTINUE_ON_FAILURE)

		def stepMessage = 'Verification that \'' + (testObjectName as String) + '\' IS NOT present ' + (testObjectNotPresent ? 'PASSED' : 'FAILED')

		markTestStep(testObjectNotPresent, stepMessage, flowControl)

		testObjectNotPresent
	}

	//================================================================================================================
	//     WAIT FOR ELEMENT TO BE VISIBLE
	//================================================================================================================

	@Keyword
	def waitForElementVisible(TestObject testObject, int timeOut, AfterFailure flowControl=AfterFailure.CONTINUE){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		timeOut = timeOut > 0 && timeOut < 200 ? timeOut : 30

		def testObjectVisible = false

		def testObjectName = new TestObjects().getTestObjectName(testObject)

		KeywordUtil.logInfo(logHeader + 'Verifying visibility of test object: ' + (testObjectName as String))

		testObjectVisible = WebUI.waitForElementVisible(testObject, timeOut)

		def stepMessage = 'Waiting for \'' + (testObjectName as String) + '\' to be visible ' + (testObjectVisible ? 'PASSED' : 'FAILED')

		markTestStep(testObjectVisible, stepMessage, flowControl)

		testObjectVisible
	}

	//================================================================================================================
	//     IS ELEMENT VISIBLE
	//================================================================================================================

	@Keyword
	def isElementVisible(TestObject testObject, int timeOut){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		timeOut = timeOut > 0 && timeOut < 200 ? timeOut : 30

		def testObjectVisible = false

		def testObjectName = new TestObjects().getTestObjectName(testObject)

		KeywordUtil.logInfo(logHeader + 'Verifying if test object is visible: ' + (testObjectName as String))

		testObjectVisible = WebUI.waitForElementVisible(testObject, timeOut)

		def stepMessage = '\'' + (testObjectName as String) + '\' was' + (testObjectVisible ? ' ' : ' NOT ') + 'visible'

		KeywordUtil.logInfo(logHeader + stepMessage)

		testObjectVisible
	}

	//================================================================================================================
	//     VERIFIED TRUE
	//================================================================================================================

	@Keyword
	def verifyTrue(Boolean isTrue, String message, AfterFailure flowControl=AfterFailure.CONTINUE){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		KeywordUtil.logInfo(logHeader + 'Attempting to verify if submission is true')

		def submission = message.equals(this.defaultMessage) ? ' - Submission ' : ' '

		def stepMessage = message + submission + 'IS' + (isTrue ? ' ' : ' NOT ') + 'true'

		markTestStep(isTrue, stepMessage, flowControl)

		isTrue
	}

	//================================================================================================================
	//     VERIFIED FALSE
	//================================================================================================================

	@Keyword
	def verifyFalse(Boolean isTrue, String message, AfterFailure flowControl=AfterFailure.CONTINUE){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		KeywordUtil.logInfo(logHeader + 'Attempting to verify if submission is false')

		def submission = message.equals(this.defaultMessage) ? ' - Submission ' : ' '

		def stepMessage = message + submission + 'IS' + (isTrue ? ' NOT ' : ' ') + 'false'

		markTestStep(!isTrue, stepMessage, flowControl)

		isTrue
	}

	//================================================================================================================
	//     VERIFIED EQUAL
	//================================================================================================================

	@Keyword
	def verifyEqual(Object firstObject, Object secondObject, String message, AfterFailure flowControl=AfterFailure.CONTINUE){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		KeywordUtil.logInfo(logHeader + 'Attempting to verify if two objects are equal')

		def objectsEqual = firstObject.equals(secondObject)

		def stepMessage = message + ' - Objects' + (objectsEqual ? ' ' : ' DO NOT ') + 'match: \'' + (firstObject as String) +
				'\'' + (objectsEqual ? '' : ' / \'' + (secondObject as String) + '\'')

		markTestStep(objectsEqual, stepMessage, flowControl)

		objectsEqual
	}

	//================================================================================================================
	//     VERIFIED NOT EQUAL
	//================================================================================================================

	@Keyword
	def verifyNotEqual(Object firstObject, Object secondObject, String message, AfterFailure flowControl=AfterFailure.CONTINUE){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		KeywordUtil.logInfo(logHeader + 'Attempting to verify if two objects are equal')

		def objectsNotEqual = !firstObject.equals(secondObject)

		def stepMessage = 'Verifying objects are NOT equal: ' + message + '.' + (objectsNotEqual ? ' PASSED' : ' FAILED') +
				': \'' + (firstObject as String) + '\' / \'' + (secondObject as String) + '\''

		markTestStep(objectsNotEqual, stepMessage, flowControl)

		objectsNotEqual
	}

	//================================================================================================================
	//     VERIFIED CLICK
	//================================================================================================================

	@Keyword
	def verifiedClick(TestObject testObject, AfterFailure flowControl=AfterFailure.CONTINUE){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def testObjectName = new TestObjects().getTestObjectName(testObject)

		def testObjectClicked = click(testObject)

		def stepMessage = testObjectName + '\' was' + (testObjectClicked ? ' ' : ' NOT ') + 'clicked successfully'

		markTestStep(testObjectClicked, stepMessage, flowControl)

		testObjectClicked
	}

	//----------------------------------------------------------------------------------------------------------------

	def verifiedClick(WebElement webElement, AfterFailure flowControl=AfterFailure.CONTINUE){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def webElementClicked = click(webElement)

		def stepMessage = 'WebElement was' + (webElementClicked ? ' ' : ' NOT ') + 'clicked successful'

		markTestStep(webElementClicked, stepMessage, flowControl)

		webElementClicked
	}

	//================================================================================================================
	//     VERIFIED MOUSE OVER
	//================================================================================================================

	@Keyword
	def verifiedMouseOver(TestObject testObject, AfterFailure flowControl=AfterFailure.CONTINUE){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def testObjectName = new TestObjects().getTestObjectName(testObject)

		def testObjectMouseOver = mouseOver(testObject)

		def stepMessage = 'Mouse over \'' + testObjectName + '\' was ' + (testObjectMouseOver ? ' ' : ' NOT ') + 'successful'

		markTestStep(testObjectMouseOver, stepMessage, flowControl)

		testObjectMouseOver
	}

	//================================================================================================================
	//     VERIFIED TEXT (TestObject versus String)
	//================================================================================================================

	@Keyword
	def verifyText(TestObject testObject, String expectedText, String message, AfterFailure flowControl=AfterFailure.CONTINUE){

		def stepMessage, preMessage, messageBody, description

		def actualText = WebUI.getText(testObject)

		def textEqual = actualText.equals(expectedText)

		message = message == "" ? this.defaultMessage : message

		if(message == this.defaultMessage || message.toLowerCase().contains('defect')){

			preMessage = message + ' - '

			description = ' '
		}
		else{

			preMessage = ''

			description = ' ' + message + ' '
		}

		messageBody = textEqual ?
				'Actual and expected' + description + 'text match: \'' + (expectedText as String) + '\'' :
				'Actual' + description + 'text \'' + (actualText as String) + '\' does not match expected' + description + 'text \'' + expectedText + '\''

		stepMessage = preMessage + messageBody

		markTestStep(textEqual, stepMessage, flowControl)

		textEqual
	}

	//================================================================================================================
	//     VERIFIED TEXT (String versus String)
	//================================================================================================================

	@Keyword
	def verifyText(String firstText, String secondText, AfterFailure flowControl=AfterFailure.CONTINUE){

		def textEqual = firstText.equals(secondText)

		def stepMessage = textEqual ? 'Both strings match: ' + firstText :
				'\'' + firstText + '\' does not match \'' + secondText + '\''

		markTestStep(textEqual, stepMessage, flowControl)

		textEqual
	}

	//================================================================================================================
	//     VERIFIED TEXT CONTAINS (TestObject versus String)
	//================================================================================================================

	@Keyword
	def verifyTextContains(TestObject testObject, String expectedContainedText, AfterFailure flowControl=AfterFailure.CONTINUE){

		def actualText = WebUI.getText(testObject)

		def textContained = actualText.contains(expectedContainedText)

		def stepMessage = 'Actual text \'' + (actualValue as String) + '\' ' +
				(textContained ? ' CONTAINS ' : ' DOES NOT CONTAIN ') + 'the expected text \'' + expectedContainedText + '\''

		markTestStep(textContained, stepMessage, flowControl)

		textContained
	}

	//================================================================================================================
	//     VERIFIED TEXT CONTAINS (String vs. String)
	//================================================================================================================

	@Keyword
	def verifyTextContains(String firstText, String secondText, AfterFailure flowControl=AfterFailure.CONTINUE){

		def textContained = firstText.contains(secondText)

		def stepMessage = 'First string \'' + firstText + '\'' +
				(textContained ? ' CONTAINS ' : ' DOES NOT CONTAIN ') + 'the second string \'' + secondText + '\''

		markTestStep(textContained, stepMessage, flowControl)

		textContained
	}

	//================================================================================================================
	//     VERIFIED TEXT DOES NOT CONTAIN (String vs. String)
	//================================================================================================================

	@Keyword
	def verifyTextDoesNotContain(String firstText, String secondText, AfterFailure flowControl=AfterFailure.CONTINUE){

		def textDoesNotContain = !firstText.contains(secondText)

		def stepMessage = 'First string \'' + firstText + '\'' +
				(textDoesNotContain ? ' DOES NOT CONTAIN ' : ' CONTAINS ') + 'the second string \'' + secondText + '\''

		markTestStep(textDoesNotContain, stepMessage, flowControl)

		textDoesNotContain
	}

	//================================================================================================================
	//     VERIFY ATTRIBUTE
	//================================================================================================================

	@Keyword
	def verifyAttribute(TestObject testObject, String attribute, String expectedValue, String message, AfterFailure flowControl=AfterFailure.CONTINUE){

		def actualValue = WebUI.getAttribute(testObject, attribute)

		def valuesEqual = actualValue.equals(expectedValue)

		def stepMessage = valuesEqual ? message + ' - Actual and expected values match from \'' + attribute +
				'\' attribute: ' + (expectedValue as String) :
				message + ' - \'' + attribute + '\' attribute actual value \'' + (actualValue as String) +
				'\' does not match expected value \'' + expectedValue + '\''

		markTestStep(valuesEqual, stepMessage, flowControl)

		valuesEqual
	}

	//================================================================================================================
	//     VERIFY ATTRIBUTE CONTAINS
	//================================================================================================================

	@Keyword
	def verifyAttributeContains(TestObject testObject, String attribute, String expectedContainedValue, String message, AfterFailure flowControl=AfterFailure.CONTINUE){

		def actualValue = WebUI.getAttribute(testObject, attribute)

		def valueContained = actualValue.contains(expectedContainedValue)

		def testObjectName = new TestObjects().getTestObjectName(testObject)

		def stepMessage = message + (message ? ' - ' : '') + 'Verification that ' + testObjectName + ' \'' + attribute + '\' attribute (' + actualValue +
				') CONTAINS \'' + expectedContainedValue + '\' ' +	(valueContained ? 'PASSED' : 'FAILED')

		markTestStep(valueContained, stepMessage, flowControl)

		valueContained
	}

	//================================================================================================================
	//     VERIFY ATTRIBUTE DOES NOT CONTAIN
	//================================================================================================================

	@Keyword
	def verifyAttributeDoesNotContain(TestObject testObject, String attribute, String doesNotContainValue, String message, AfterFailure flowControl=AfterFailure.CONTINUE){

		def actualValue = WebUI.getAttribute(testObject, attribute)

		def valueDoesNotContain = !actualValue.contains(doesNotContainValue)

		def testObjectName = new TestObjects().getTestObjectName(testObject)

		def stepMessage = message + (message ? ' - ' : '') + 'Verification that ' + testObjectName + ' \'' + attribute + '\' attribute (' + actualValue +
				') DOES NOT CONTAIN \'' + doesNotContainValue + '\' ' +	(valueDoesNotContain ? 'PASSED' : 'FAILED')

		markTestStep(valueDoesNotContain, stepMessage, flowControl)

		valueDoesNotContain
	}

	//================================================================================================================
	//     VERIFIED URL
	//================================================================================================================

	@Keyword
	def verifyUrl(String expectedUrl, AfterFailure flowControl=AfterFailure.CONTINUE){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		KeywordUtil.logInfo(logHeader + 'Attempting to verify if the current url is correct')

		def currentUrl = WebUI.getUrl()

		def isCorrect = currentUrl == expectedUrl

		def stepMessage = isCorrect ?
				'Current and expected url (' + expectedUrl + ') match' :
				'Current url (' + currentUrl + ') does NOT match expected url (' + expectedUrl + ')'

		markTestStep(isCorrect, stepMessage, flowControl)

		isCorrect
	}

	//================================================================================================================
	//     MARK TEST STEP
	//================================================================================================================

	@Keyword
	def markTestStep(Boolean trueFalse, String stepMessage, AfterFailure flowControl){

		if(trueFalse){

			KeywordUtil.markPassed(stepMessage)
		}
		else{

			switch (flowControl){

				case AfterFailure.CONTINUE:

					KeywordUtil.markFailed(stepMessage)

					break

				case AfterFailure.STOP:

					KeywordUtil.markFailedAndStop(stepMessage)

					break

				default:

					KeywordUtil.markFailed(stepMessage)
			}
		}
	}

	enum AfterFailure{

		CONTINUE,
		STOP
	}
}