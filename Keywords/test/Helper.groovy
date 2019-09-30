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

public class Helper extends WrappedSelenium{

	@Keyword
	def verifyTestObjectAndClick(TestObject testObject){

		def logHeader

		def results = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + 'Attempting to verify the test object is visible and then click')

			results['passed'] = false

			def testObjectVisible = WebUI.waitForElementVisible(testObject, 10)

			if (testObjectVisible) {

				def successfulClick = click(testObject)

				if(successfulClick){

					results['passed'] = true

					results['message'] = 'Test object is visible and successfully clicked'

					KeywordUtil.logInfo(logHeader + results['message'])
				}
				else{

					results['message'] = 'Test object is visible, but was not clicked successfully'

					throw new Exception(results['message'])
				}
			}
			else{

				results['message'] = 'Test object not visible'

				throw new Exception(results['message'])
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		KeywordUtil.logInfo(logHeader + 'Results: ' + results as String)

		results
	}

	@Keyword
	def verifyTestObjectAndClick(TestObject testObject, String text){

		def logHeader

		def results = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + 'Attempting to verify the test object is visible, the text is equal to \'' + text + '\' and then click')

			results['passed'] = false

			def testObjectVisible = WebUI.waitForElementVisible(testObject, 10)

			if (testObjectVisible) {
				def testObjectText = WebUI.getText(testObject, FailureHandling.CONTINUE_ON_FAILURE)

				results['text'] = testObjectText as String

				if(results['text'] == text){

					def successfulClick = click(testObject)

					if(successfulClick){

						results['passed'] = true

						results['message'] = 'Test object is visible, text is as expected and successfully clicked'

						KeywordUtil.logInfo(logHeader + results['message'])
					}
					else{

						results['message'] = 'Test object is visible, text is as expected, but was not clicked successfully'

						throw new Exception(results['message'])
					}
				}
				else{

					results['message'] = 'Test object is visible and text is not as expected. \'' + (testObjectText as String) + '\' IS NOT \'' + text +'\''

					throw new Exception(results['message'])
				}
			}
			else{

				results['message'] = 'Test object not visible'

				throw new Exception(results['message'])
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		KeywordUtil.logInfo(logHeader + 'Results: ' + results as String)

		results
	}

	@Keyword
	def verifyTestObjectAndText(TestObject testObject, String text){

		def logHeader

		def results = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + 'Attempting to verify the test object is visible and that the text is equal to \'' + text + '\'')

			results['passed'] = false

			def testObjectVisible = WebUI.waitForElementVisible(testObject, 10)

			if (testObjectVisible) {
				def testObjectText = WebUI.getText(testObject, FailureHandling.CONTINUE_ON_FAILURE)

				results['text'] = testObjectText as String

				if(results['text'] == text){

					results['passed'] = true

					results['message'] = 'Test object is visible and text is as expected'

					KeywordUtil.logInfo(logHeader + results['message'])
				}
				else{

					results['message'] = 'Test object is visible and text IS NOT as expected. \'' + (testObjectText as String) + '\' IS NOT \'' + text +'\''

					throw new Exception(results['message'])
				}
			}
			else{

				results['message'] = 'Test object not visible'

				throw new Exception(results['message'])
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		KeywordUtil.logInfo(logHeader + 'Results: ' + results as String)

		results
	}

	@Keyword
	def verifyTestObjectAndReturnText(TestObject testObject){

		def logHeader

		def results = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + 'Attempting to verify the test object is visible and returning the text')

			results['passed'] = false

			def testObjectVisible = WebUI.waitForElementVisible(testObject, 10)

			if (testObjectVisible) {
				def testObjectText = WebUI.getText(testObject, FailureHandling.CONTINUE_ON_FAILURE)

				results['text'] = testObjectText as String

				results['passed'] = true

				results['message'] = 'Test object is visible and text is \'' + results['text']

				KeywordUtil.logInfo(logHeader + results['message'])
			}
			else{

				results['message'] = 'Test object not visible'

				throw new Exception(results['message'])
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		KeywordUtil.logInfo(logHeader + 'Results: ' + results as String)

		results
	}

	@Keyword
	def verifyTestObjectAndInputText(TestObject testObject, String text){

		def logHeader

		def results = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + 'Attempting to verify the test object is visible and input \'' + text + '\'')

			results['passed'] = false

			def testObjectVisible = WebUI.waitForElementVisible(testObject, 10)

			if (testObjectVisible) {

				WebUI.sendKeys(testObject, text, FailureHandling.CONTINUE_ON_FAILURE)

				results['passed'] = true

				results['message'] = 'Test object is visible and text has been input'

				KeywordUtil.logInfo(logHeader + results['message'])
			}
			else{

				results['message'] = 'Test object not visible'

				throw new Exception(results['message'])
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		KeywordUtil.logInfo(logHeader + 'Results: ' + results as String)

		results
	}

	@Keyword
	def verifyTestObjectAndReturnAttribute(TestObject testObject, String attribute){

		def logHeader

		def results = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + 'Attempting to verify the test object is visible and returning the text')

			results['passed'] = false

			def testObjectVisible = WebUI.waitForElementVisible(testObject, 10)

			if (testObjectVisible) {
				def testObjectAttributeValue = WebUI.getAttribute(testObject, attribute, FailureHandling.CONTINUE_ON_FAILURE)

				results['value'] = testObjectAttributeValue as String

				results['passed'] = true

				results['message'] = 'Test object is visible and attribute (' + attribute + ') value is \'' + results['value'] + '\''

				KeywordUtil.logInfo(logHeader + results['message'])
			}
			else{

				results['message'] = 'Test object not visible'

				throw new Exception(results['message'])
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		KeywordUtil.logInfo(logHeader + 'Results: ' + results as String)

		results
	}

	@Keyword
	def verifyTestObjectAndAttribute(TestObject testObject, String attribute, String expectedValue){

		def logHeader

		def results = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + 'Attempting to verify the test object is visible and returning the text')

			results['passed'] = false

			def testObjectVisible = WebUI.waitForElementVisible(testObject, 10)

			if (testObjectVisible) {
				def testObjectAttributeValue = WebUI.getAttribute(testObject, attribute, FailureHandling.CONTINUE_ON_FAILURE)

				results['value'] = testObjectAttributeValue as String

				if(results['value'] == expectedValue){

					results['passed'] = true

					results['message'] = 'Test object is visible and attribute (' + attribute + ') value is \'' + results['value'] + '\' and matches expected value'

					KeywordUtil.logInfo(logHeader + results['message'])
				}
				else{

					results['message'] = 'Test object is visible, but attribute (' + attribute +
							') value is \'' + results['value'] + '\' and does not match expected value \'' + expectedValue + '\''

					throw new Exception(results['message'])
				}
			}
			else{

				results['message'] = 'Test object not visible'

				throw new Exception(results['message'])
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		KeywordUtil.logInfo(logHeader + 'Results: ' + results as String)

		results
	}
}