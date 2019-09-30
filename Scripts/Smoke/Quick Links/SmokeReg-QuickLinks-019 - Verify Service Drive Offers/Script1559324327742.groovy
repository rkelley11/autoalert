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
import com.kms.katalon.core.util.KeywordUtil
import java.time.LocalDate as LocalDate
import begin.Initialize

// Login
new Initialize().readySetGo()

def dealerSelected = CustomKeywords.'opportunities.DealerSelect.selectDealer'('FMDEMO')

assert dealerSelected

// Verify Quick Link
def serviceDriveOffersQuickLink = findTestObject('Opportunities/Alert Desk/Dashboard/Quick Links/Quick Links - Service Drive Offers')

def serviceDriveOffersQuickLinkVisible = WebUI.waitForElementVisible(serviceDriveOffersQuickLink, 10)

assert serviceDriveOffersQuickLinkVisible

if(serviceDriveOffersQuickLinkVisible){
	def serviceDriveOffersQuickLinkText = WebUI.getText(serviceDriveOffersQuickLink)
	
	WebUI.verifyEqual(serviceDriveOffersQuickLinkText, 'Service Drive Offers', FailureHandling.CONTINUE_ON_FAILURE)
	
	def serviceDriveOffersQuickLinkClicked = CustomKeywords.'general.WrappedSelenium.click'(serviceDriveOffersQuickLink)
	
	assert serviceDriveOffersQuickLinkClicked
}

// Verify Heading
def serviceDriveOffersHeading = findTestObject('Opportunities/Alert Desk/Service Drive Offers/Service Drive Offers Header')

def serviceDriveOffersHeadingVisible = WebUI.waitForElementVisible(serviceDriveOffersHeading, 10)
	
WebUI.verifyEqual(serviceDriveOffersHeadingVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if(serviceDriveOffersHeadingVisible){
	def serviceDriveOffersHeadingText = WebUI.getText(serviceDriveOffersHeading)
	
	WebUI.verifyEqual(serviceDriveOffersHeadingText, 'Home: Service Drive Offers', FailureHandling.CONTINUE_ON_FAILURE)
}

// Verify and Open Change Filters
def changeFilters = findTestObject('Opportunities/Alert Desk/Service Drive Offers/Service Drive Offers - Button - Change Filters')

def changeFiltersVisible = WebUI.waitForElementVisible(changeFilters, 10)
	
WebUI.verifyEqual(changeFiltersVisible, true, FailureHandling.STOP_ON_FAILURE)

if(changeFiltersVisible){
	def changeFiltersClicked = CustomKeywords.'general.WrappedSelenium.click'(changeFilters)
	
	WebUI.verifyEqual(changeFiltersClicked, true, FailureHandling.STOP_ON_FAILURE)
}

// Verify and Open Calendar
def calendarButton = findTestObject('Opportunities/Alert Desk/Service Drive Offers/Filter/Service Drive Offers - Filter - Button - Calendar')

elementVisible = WebUI.waitForElementVisible(calendarButton, 10)
	
WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if(elementVisible){
	def clicked = CustomKeywords.'general.WrappedSelenium.click'(calendarButton)
	
	WebUI.verifyEqual(clicked, true, FailureHandling.STOP_ON_FAILURE)
}

// Select date 1 year in the past to ensure data is returned

def yearsBefore = LocalDate.now().minusYears(1)

def dateInfo = CustomKeywords.'general.Calendar.clickDate'(yearsBefore)

KeywordUtil.logInfo(dateInfo as String)

// Submit new date
def submitButton = findTestObject('Opportunities/Alert Desk/Service Drive Offers/Filter/Service Drive Offers - Filter - Button - Submit')

elementVisible = WebUI.waitForElementVisible(submitButton, 10)
	
WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if(elementVisible){
	def clicked = CustomKeywords.'general.WrappedSelenium.click'(submitButton)
	
	WebUI.verifyEqual(clicked, true, FailureHandling.STOP_ON_FAILURE)
	
	WebUI.delay(3) // Temporary to allow for new list to be generated in search results before next step.  Need to determine an overlay to watch for instead.
}

// Click First Available Print Offer
def printOfferClicked = CustomKeywords.'opportunities.alertdesk.ServiceDriveOffers.clickPrintOffer'(null)

assert printOfferClicked
	
def printOfferOpened = CustomKeywords.'opportunities.PrintOffers.didPrintOfferOpen'()

assert printOfferOpened
	
// Verify Print Offer Title
def printOfferTitle = findTestObject('Print Offer/Header/Print Offer - Header - Title')

def printOfferTitleVisible = WebUI.waitForElementVisible(printOfferTitle, 10)

assert printOfferTitleVisible
	
if(printOfferTitleVisible){
	def printOfferTitleText = WebUI.getText(printOfferTitle)
	
	def printOfferTitleTextCleaned = printOfferTitleText.replace('×', '').trim()
	
	assert printOfferTitleTextCleaned == 'Print Offer'
}