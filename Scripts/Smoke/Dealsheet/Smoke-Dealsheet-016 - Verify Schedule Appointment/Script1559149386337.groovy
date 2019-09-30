import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import dealsheet.bottom.Common.BottomSectionTabs2 as BottomSectionTabs2
import java.time.LocalDate as LocalDate
import general.Calendar as Calendar
import opportunities.alertdesk.dashboard.OpportunitiesDashboard.Widgets as OpportunitiesDashboardWidgets
import begin.Initialize

// Login
new Initialize().readySetGo('automation5')

def elementVisible

def elementText

def alert = findTestObject('Opportunities/Alert Desk/Dashboard/Opportunities Dashboard/Opportunities by Alert/Opportunity - Alert')

elementVisible = WebUI.waitForElementVisible(alert, 10)

CustomKeywords.'general.WrappedSelenium.click'(alert)

CustomKeywords.'opportunities.alertdesk.Opportunities.clickFirstRow'()

def dealsheetLoaded = CustomKeywords.'dealsheet.Common.waitToLoad'()

assert dealsheetLoaded

// Opening Tab
def tabButton = findTestObject('Dealsheet/Log Activity/Log Activity/Common/Dealsheet - Log Activity - Tab - Button')

elementVisible = WebUI.waitForElementVisible(tabButton, 10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.STOP_ON_FAILURE)

if (elementVisible) {
	def tabOpened = CustomKeywords.'general.WrappedSelenium.click'(tabButton)

	WebUI.verifyEqual(tabOpened, true, FailureHandling.STOP_ON_FAILURE)
}

// Verify and clicking Schedule icon
def scheduleIcon = findTestObject('Dealsheet/Log Activity/Schedule/Action Menu/Dealsheet - Log Activity - Schedule - Action Menu - Button')

elementVisible = WebUI.waitForElementVisible(scheduleIcon, 10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.STOP_ON_FAILURE)

if (elementVisible) {
	def clicked = CustomKeywords.'general.WrappedSelenium.click'(scheduleIcon)

	WebUI.verifyEqual(clicked, true, FailureHandling.STOP_ON_FAILURE)
}

// Click on Date Picker icon
def datePickerIcon = findTestObject('Dealsheet/Log Activity/Schedule/Due Date/Calendar/Dealsheet - Log Activity - Due Date - Calendar - Button')

elementVisible = WebUI.waitForElementVisible(datePickerIcon, 10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if (elementVisible) {
	def clicked = CustomKeywords.'general.WrappedSelenium.click'(datePickerIcon)

	WebUI.verifyEqual(clicked, true, FailureHandling.CONTINUE_ON_FAILURE)
}

// Pick a 1 day in the future on calendar
def futureDate = LocalDate.now().plusDays(1)

def dateInfo = CustomKeywords.'general.Calendar.clickDate'(futureDate)

WebUI.verifyEqual(dateInfo['available'], true, FailureHandling.CONTINUE_ON_FAILURE)

// Get current hour selected
def hourField = findTestObject('Dealsheet/Log Activity/Schedule/Time/Hour/Dealsheet - Log Activity - Time - Hour - Input')

elementVisible = WebUI.waitForElementVisible(hourField, 10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

def originalHour

def expectedHour

if (elementVisible) {
	originalHour = WebUI.getAttribute(hourField, 'value') as Integer
	
	println(originalHour as String)
	
	expectedHour = originalHour == 12 ? 1 : originalHour + 1
}

// Click on the Up arrow for Hour and verify
def hourArrowUp = findTestObject('Dealsheet/Log Activity/Schedule/Time/Hour/Dealsheet - Log Activity - Time - Hour - Arrow - Up')

elementVisible = WebUI.waitForElementVisible(hourArrowUp, 10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if (elementVisible) {
	def clicked = CustomKeywords.'general.WrappedSelenium.click'(hourArrowUp)

	WebUI.verifyEqual(clicked, true, FailureHandling.CONTINUE_ON_FAILURE)
	
	def newHour = WebUI.getAttribute(hourField, 'value') as Integer

	WebUI.verifyEqual(newHour, expectedHour, FailureHandling.CONTINUE_ON_FAILURE)
}

// Get current minute selected
def minuteField = findTestObject('Dealsheet/Log Activity/Schedule/Time/Minute/Dealsheet - Log Activity - Time - Minute - Input')

elementVisible = WebUI.waitForElementVisible(minuteField, 10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

def originalMinute

def expectedMinute

if (elementVisible) {
	originalMinute = WebUI.getAttribute(minuteField, 'value') as Integer
	
	expectedMinute = originalMinute < 5 ? 60 - (5 - originalMinute) : originalMinute - 5
}

// Click on the Down arrow for Minute and verify
def minuteArrowDown = findTestObject('Dealsheet/Log Activity/Schedule/Time/Minute/Dealsheet - Log Activity - Time - Minute - Arrow - Down')

elementVisible = WebUI.waitForElementVisible(minuteArrowDown, 10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if (elementVisible) {
	def clicked = CustomKeywords.'general.WrappedSelenium.click'(minuteArrowDown)

	WebUI.verifyEqual(clicked, true, FailureHandling.CONTINUE_ON_FAILURE)
	
	def newMinute = WebUI.getAttribute(minuteField, 'value') as Integer

	WebUI.verifyEqual(newMinute, expectedMinute, FailureHandling.CONTINUE_ON_FAILURE)
}

// Click on the Other User link
def otherUserLink = findTestObject('Dealsheet/Log Activity/Schedule/Assign To/Dealsheet - Log Activity - Assign To - Other User - Link')

elementVisible = WebUI.waitForElementVisible(otherUserLink, 10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if (elementVisible) {
	def clicked = CustomKeywords.'general.WrappedSelenium.click'(otherUserLink)

	WebUI.verifyEqual(clicked, true, FailureHandling.CONTINUE_ON_FAILURE)
}

// Select random user
def otherUserDropdown = findTestObject('Dealsheet/Log Activity/Schedule/Assign To/Dealsheet - Log Activity - Assign To - Other User - Dropdown')

elementVisible = WebUI.waitForElementVisible(otherUserDropdown, 10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if (elementVisible) {
	def randomUser = CustomKeywords.'dealsheet.Common.selectRandomDropdown'(otherUserDropdown)
	
	def selectedValue = WebUI.getAttribute(otherUserDropdown, 'value')

	WebUI.verifyEqual(randomUser['value'], selectedValue, FailureHandling.CONTINUE_ON_FAILURE)
}

// Click Submit
def submitButton = findTestObject('Dealsheet/Log Activity/Schedule/Buttons/Dealsheet - Log Activity - Schedule - Buttons - Submit')

elementVisible = WebUI.waitForElementVisible(submitButton, 10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.STOP_ON_FAILURE)

if (elementVisible) {
	def clicked = CustomKeywords.'general.WrappedSelenium.click'(submitButton)

	WebUI.verifyEqual(clicked, true, FailureHandling.STOP_ON_FAILURE)
}

// Get dealsheet customer name
def customerNameTO = findTestObject('Dealsheet/Top Section/Customer Profile/Customer Profile Name')

elementVisible = WebUI.waitForElementVisible(customerNameTO, 10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.STOP_ON_FAILURE)

def customerName

if (elementVisible) {
	customerName = WebUI.getText(customerNameTO, FailureHandling.STOP_ON_FAILURE)

	// Close dealsheet
	def closeIcon = findTestObject('Dealsheet/Top Section/Icons/Upper/Close Dealsheet')
	
	elementVisible = WebUI.waitForElementVisible(closeIcon, 10)
	
	WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

	if (elementVisible) {
		def clicked = CustomKeywords.'general.WrappedSelenium.click'(closeIcon)
	
		WebUI.verifyEqual(clicked, true, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	// Go to home page
	WebUI.navigateToUrl(GlobalVariable.Url_Login)

	// Click on the refresh button for My Agenda
	def refreshed = CustomKeywords.'opportunities.alertdesk.Dashboard.refreshWidget'(OpportunitiesDashboardWidgets.MY_AGENDA)
	refreshed = CustomKeywords.'opportunities.alertdesk.Dashboard.refreshWidget'(OpportunitiesDashboardWidgets.MY_AGENDA) // Defect requires clicking refresh 2x
	
	WebUI.verifyEqual(refreshed, true, FailureHandling.CONTINUE_ON_FAILURE)

	// Check My Agenda for newly scheduled dealsheet
	def myAgendaTableBody = findTestObject('Opportunities/Alert Desk/Dashboard/Opportunities Dashboard/My Agenda/Opps Dash - My Agenda - Table Body')
	
	elementVisible = WebUI.waitForElementVisible(myAgendaTableBody, 10)
	
	WebUI.verifyEqual(elementVisible, true, FailureHandling.STOP_ON_FAILURE)
	
	if (elementVisible) {
		def scheduled = CustomKeywords.'opportunities.alertdesk.dashboard.opportunities.MyAgenda.returnRow'(customerName)
	
		WebUI.verifyEqual(scheduled != null, true, FailureHandling.STOP_ON_FAILURE)
		
		CustomKeywords.'opportunities.alertdesk.dashboard.opportunities.MyAgenda.clearMyAgenda'()
	}
}