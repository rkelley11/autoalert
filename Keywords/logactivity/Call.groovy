package logactivity

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
import test.Assistant.AfterFailure
import dealsheet.bottom.TestObjects as DealsheetTestObjects

import sql.*
import java.time.*
import java.time.format.*
import java.util.concurrent.TimeUnit
import java.time.temporal.ChronoUnit

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class Call extends WrappedSelenium{

	@Keyword
	def schedule(LocalDateTime scheduledDateTime=null){

		def testObjectUnderTest, testObjectVisible

		def testAssistant = new test.Assistant()
		def calendar = new general.Calendar()
		def dealsheetCommon = new dealsheet.Common()

		def formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

		def callDetails = [:]

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		// Opening Tab
		testObjectUnderTest = TestObjects.LogActivity.TAB

		testAssistant.waitForElementVisible(testObjectUnderTest, 10, AfterFailure.STOP)

		testAssistant.verifiedClick(testObjectUnderTest, AfterFailure.STOP)

		// Verify and click Schedule icon
		testObjectUnderTest = TestObjects.LogActivity.ActionMenu.SCHEDULE

		testAssistant.waitForElementVisible(testObjectUnderTest, 10, AfterFailure.STOP)

		testAssistant.verifiedClick(testObjectUnderTest, AfterFailure.STOP)

		// Verify and check Schedule Call radio button
		testObjectUnderTest = TestObjects.LogActivity.Schedule.Call.RADIO_BUTTON

		testAssistant.waitForElementVisible(testObjectUnderTest, 10, AfterFailure.STOP)

		testAssistant.verifiedClick(testObjectUnderTest, AfterFailure.STOP)

		// Click on Date Picker icon
		testObjectUnderTest = TestObjects.LogActivity.Schedule.DueDate.BUTTON

		testAssistant.waitForElementVisible(testObjectUnderTest, 10, AfterFailure.STOP)

		testAssistant.verifiedClick(testObjectUnderTest, AfterFailure.STOP)

		// Verify the day before is not selectable
		def dayBefore = LocalDate.now().minusDays(1)

		def dayBeforeSelectable = calendar.isDateAvailable(dayBefore)

		testAssistant.verifyFalse(dayBeforeSelectable, AfterFailure.STOP)

		// Pick scheduled day on calendar
		def scheduledDate = scheduledDateTime ? scheduledDateTime.toLocalDate() : LocalDate.now()

		def dateInfo = calendar.clickDate(scheduledDate)

		testAssistant.verifyTrue(dateInfo['available'], AfterFailure.STOP)

		// Verify date selected is displaying
		testObjectUnderTest = TestObjects.LogActivity.Schedule.DueDate.INPUT

		testAssistant.waitForElementVisible(testObjectUnderTest, 10, AfterFailure.STOP)

		callDetails['formattedDate'] = formatter.format(dateInfo['date'])

		testAssistant.verifyAttribute(testObjectUnderTest, 'value', callDetails['formattedDate'], AfterFailure.CONTINUE)

		// Set and get current hour
		testObjectUnderTest = TestObjects.LogActivity.Schedule.Time.Hour.INPUT

		testAssistant.waitForElementVisible(testObjectUnderTest, 10, AfterFailure.STOP)

		def desiredMeridiem

		if(scheduledDateTime.equals(null)){

			callDetails['originalHour'] = WebUI.getAttribute(testObjectUnderTest, 'value', FailureHandling.STOP_ON_FAILURE) as Integer
		}
		else{

			def time = scheduledDateTime.toLocalTime()

			def hour = time.getHour()

			callDetails['originalHour'] = (hour > 12 ? hour - 12 : hour) as String

			desiredMeridiem = hour > 11 ? 'PM' : 'AM'

			WebUI.clearText(testObjectUnderTest, FailureHandling.CONTINUE_ON_FAILURE)

			WebUI.sendKeys(testObjectUnderTest, callDetails['originalHour'], FailureHandling.STOP_ON_FAILURE)
		}

		// Set and get current minute selected
		testObjectUnderTest = TestObjects.LogActivity.Schedule.Time.Minute.INPUT

		testAssistant.waitForElementVisible(testObjectUnderTest, 10, AfterFailure.STOP)

		if(scheduledDateTime.equals(null)){

			callDetails['originalMinute'] = WebUI.getAttribute(testObjectUnderTest, 'value', FailureHandling.STOP_ON_FAILURE) as Integer
		}
		else{

			def time = scheduledDateTime.toLocalTime()

			callDetails['originalMinute'] = time.getMinute() as String

			WebUI.clearText(testObjectUnderTest, FailureHandling.CONTINUE_ON_FAILURE)

			WebUI.sendKeys(testObjectUnderTest, callDetails['originalMinute'], FailureHandling.STOP_ON_FAILURE)
		}

		// Gather whether AM or PM
		testObjectUnderTest = TestObjects.LogActivity.Schedule.Time.MERIDIEM

		testAssistant.waitForElementVisible(testObjectUnderTest, 10, AfterFailure.STOP)

		callDetails['originalMeridiem'] = WebUI.getText(testObjectUnderTest, FailureHandling.CONTINUE_ON_FAILURE)

		if(!scheduledDateTime.equals(null)){

			if(!callDetails['originalMeridiem'].equals(desiredMeridiem)){

				testAssistant.verifiedClick(testObjectUnderTest, AfterFailure.STOP)

				callDetails['originalMeridiem'] = desiredMeridiem
			}
		}

		// Click on the Other User link
		testObjectUnderTest = TestObjects.LogActivity.Schedule.AssignTo.OTHER_USER_LINK

		testAssistant.waitForElementVisible(testObjectUnderTest, 10, AfterFailure.STOP)

		testAssistant.verifiedClick(testObjectUnderTest, AfterFailure.STOP)

		// Select random user
		testObjectUnderTest = TestObjects.LogActivity.Schedule.AssignTo.Owner.DROPDOWN

		testObjectVisible = testAssistant.waitForElementVisible(testObjectUnderTest, 10, AfterFailure.STOP)

		if (testObjectVisible) {
			callDetails['owner'] = dealsheetCommon.selectRandomDropdown(testObjectUnderTest)

			testAssistant.verifyAttribute(testObjectUnderTest, 'value', callDetails['owner']['value'], AfterFailure.CONTINUE)
		}

		// Click Submit
		testObjectUnderTest = TestObjects.LogActivity.Button.SUBMIT

		testAssistant.waitForElementVisible(testObjectUnderTest, 10, AfterFailure.STOP)

		testAssistant.verifiedClick(testObjectUnderTest, AfterFailure.STOP)

		KeywordUtil.logInfo(logHeader + 'Closing details: ' + callDetails as String)

		// Record submitted time
		def now = LocalDateTime.now()

		def nowPlus1 = now.plusMinutes(1)

		callDetails['submittedDateTime'] = now.truncatedTo(ChronoUnit.MINUTES)

		callDetails['submittedDateTimePlus1'] = nowPlus1.truncatedTo(ChronoUnit.MINUTES)

		//Return call details
		callDetails
	}

	@Keyword
	def closing(ClosingCallResult callResult){

		def testObjectUnderTest, testObjectVisible

		def testAssistant = new test.Assistant()

		def closingDetails = [:]

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		// Verify & Click Complete Call link
		testObjectUnderTest = DealsheetTestObjects.ActivityAndNotes.UpcomingAppointments.CLOSING_CALL_LINK

		testAssistant.waitForElementVisible(testObjectUnderTest, 10, AfterFailure.STOP)

		testAssistant.verifyText(testObjectUnderTest, 'Complete Call', AfterFailure.CONTINUE)

		testAssistant.verifiedClick(testObjectUnderTest, AfterFailure.STOP)

		// Verify & Select Result of Call on Popup
		testObjectUnderTest = findTestObject('Object Repository/Dealsheet/Modals/Post Notes/Result/Dealsheet - Modals - Post Notes - Result - Dropdown')

		testAssistant.waitForElementVisible(testObjectUnderTest, 10, AfterFailure.STOP)

		def result = callResult.getValue()

		WebUI.selectOptionByValue(testObjectUnderTest, result['value'], false, FailureHandling.STOP_ON_FAILURE)

		closingDetails['result'] = 'Result: ' + result['text']

		// Verify Notes textbox
		testObjectUnderTest = findTestObject('Object Repository/Dealsheet/Modals/Post Notes/Note/Dealsheet - Modals - Post Notes - Note - Text Area')

		testObjectVisible = testAssistant.waitForElementVisible(testObjectUnderTest, 10, AfterFailure.STOP)

		closingDetails['notesMessage'] = 'This is a test ' + LocalDateTime.now() as String

		if (testObjectVisible) {
			WebUI.sendKeys(testObjectUnderTest, closingDetails['notesMessage'], FailureHandling.CONTINUE_ON_FAILURE)
		}

		// Verify & Click Submit button on Popup
		testObjectUnderTest = findTestObject('Object Repository/Dealsheet/Modals/Post Notes/Buttons/Dealsheet - Modals - Post Notes - Buttons - Submit')

		testAssistant.waitForElementVisible(testObjectUnderTest, 10, AfterFailure.STOP)

		testAssistant.verifyText(testObjectUnderTest, 'Submit', AfterFailure.CONTINUE)

		testAssistant.verifiedClick(testObjectUnderTest, AfterFailure.STOP)

		// Return closing details
		KeywordUtil.logInfo(logHeader + 'Closing details: ' + closingDetails as String)

		closingDetails
	}

	enum ClosingCallResult{
		INTERESTED(['value':'27', 'text':'Interested']),
		NOT_INTERESTED(['value':'6', 'text':'Not Interested']),
		NO_LONGER_OWNS(['value':'28', 'text':'No Longer Owns']),
		MARK_AS_SOLD(['value':'7', 'text':'Mark As Sold']),
		LEFT_MESSAGE(['value':'4', 'text':'Left Message']),
		CALL_BACK(['value':'2', 'text':'Call Back']),
		NO_ANSWER(['value':'5', 'text':'No Answer']),
		OTHER(['value':'32', 'text':'Other']),
		APPOINTMENT_SET(['value':'1', 'text':'Appointment Set']),
		VEHICLE_PAID_OFF(['value':'33', 'text':'Vehicle Paid Off'])

		def getValue

		ClosingCallResult(LinkedHashMap getValue){

			this.getValue = getValue
		}

		def getValue(){

			return this.getValue
		}
	}
}