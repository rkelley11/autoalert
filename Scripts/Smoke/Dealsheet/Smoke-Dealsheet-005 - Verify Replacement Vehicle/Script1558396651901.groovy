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
import org.openqa.selenium.Keys as Keys
import java.util.concurrent.ThreadLocalRandom as ThreadLocalRandom
import java.text.DecimalFormat as DecimalFormat
import java.time.LocalDate as LocalDate
import java.time.format.DateTimeFormatter as DateTimeFormatter
import dealsheet.Mileage as Mileage
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import begin.Initialize
import test.Assistant.AfterFailure

def testObjectUnderTest

// Login
new Initialize().readySetGo('automation1')

def mileage = new Mileage()

// Open Dealsheet
def dealsheetUrl = CustomKeywords.'dealsheet.Common.getRandomMVODealsheetURL'()

WebUI.navigateToUrl(dealsheetUrl, FailureHandling.STOP_ON_FAILURE)

def dealsheetLoaded = CustomKeywords.'dealsheet.Common.waitToLoad'()

CustomKeywords.'test.Assistant.verifyTrue'(dealsheetLoaded, 'Dealsheet successfully loaded', AfterFailure.STOP)

// Verify Replacement Vehicle Year Exists
testObjectUnderTest = findTestObject('Dealsheet/Car Comparison Section/Replacement/Trim Info/Car Comparison - Replacement - Year')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

def elementText = WebUI.getText(testObjectUnderTest)

def elementNotEmpty = (elementText != null) && !(elementText.isEmpty())

CustomKeywords.'test.Assistant.verifyTrue'(elementNotEmpty, 'Replacement car year is available', AfterFailure.STOP)

// Verify Replacement Vehicle Make Exists
testObjectUnderTest = findTestObject('Dealsheet/Car Comparison Section/Replacement/Trim Info/Car Comparison - Replacement - Make')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

elementText = WebUI.getText(testObjectUnderTest)

elementNotEmpty = (elementText != null) && !(elementText.isEmpty())

CustomKeywords.'test.Assistant.verifyTrue'(elementNotEmpty, 'Replacement car make is available', AfterFailure.STOP)

// Verify Replacement Vehicle Full Trim Exists
testObjectUnderTest = findTestObject('Dealsheet/Car Comparison Section/Replacement/Trim Info/Car Comparison - Replacement - Full Trim')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

elementText = WebUI.getText(testObjectUnderTest)

elementNotEmpty = (elementText != null) && !(elementText.isEmpty())

CustomKeywords.'test.Assistant.verifyTrue'(elementNotEmpty, 'Replacement car full trim is available', AfterFailure.STOP)

// Verify 'More Vehicles' dropdown exists with a list of vehicles
testObjectUnderTest = findTestObject('Dealsheet/Car Comparison Section/Replacement/More Vehicle Offers/Dealsheet - Car Comparison - Replacement - More Vehicle Offers - Link')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyAttributeDoesNotContain'(testObjectUnderTest, 'class', 'open', AfterFailure.STOP)

// Verify text
CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'More Vehicle Offers', AfterFailure.CONTINUE)

// Verify clicking & opening Dropdown
CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyAttributeContains'(testObjectUnderTest, 'class', 'open', AfterFailure.CONTINUE)

// Verify there are offers present
def offers = CustomKeywords.'dealsheet.middle.MoreVehicleOffers.getOffers'()

def offersPresent = offers.size() > 0

CustomKeywords.'test.Assistant.verifyTrue'(offersPresent, 'At least one offer is present', AfterFailure.STOP)

def goodOfferFound = false

// Click an offer that has terms and a payment
for(int i = 0 ; i < offers.size() ; i++){
	
	goodOfferFound = CustomKeywords.'dealsheet.middle.MoreVehicleOffers.isOfferGood'(offers[i])
	
	if(goodOfferFound){
		
		CustomKeywords.'test.Assistant.verifiedClick'(offers[i], AfterFailure.STOP)

		// Wait for dealsheet to refresh
		dealsheetLoaded = CustomKeywords.'dealsheet.Common.waitToLoad'()
		
		CustomKeywords.'test.Assistant.verifyTrue'(dealsheetLoaded, 'Dealsheet successfully refreshed', AfterFailure.STOP)
		
		// Verify dropdown closed after clicking offer
		CustomKeywords.'test.Assistant.verifyAttributeDoesNotContain'(testObjectUnderTest, 'class', 'open', AfterFailure.STOP)
				
		break
	}	
}

CustomKeywords.'test.Assistant.verifyTrue'(goodOfferFound, 'An offer with a term and payment was found', AfterFailure.STOP)

// Verify clicking Proposal opens upgrade proposal
testObjectUnderTest = findTestObject('Dealsheet/Car Comparison Section/Replacement/Proposal/Dealsheet - Car Comparison - Replacement - Proposal - Button')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)
	
def openedNewWindow = CustomKeywords.'dealsheet.middle.Proposal.verifyOpenInNewWindow'()

CustomKeywords.'test.Assistant.verifyTrue'(openedNewWindow, 'Clicking upgrade proposal opens new window', AfterFailure.STOP)