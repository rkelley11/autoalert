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
import test.Assistant.AfterFailure
import general.Urls
import begin.Initialize
import search.Page.SearchPageSections

// Login
new Initialize().readySetGo('automation10')

def section = SearchPageSections.SEARCH
def testObjectUnderTest

// Go to page
searchUrl = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.OPPORTUNITIES_DASHBOARD

WebUI.navigateToUrl(searchUrl)

CustomKeywords.'test.Assistant.verifyEqual'(WebUI.getUrl(), searchUrl, 'Navigated successfully to the Opportunities Dashboard', AfterFailure.STOP)

// Refresh Tagged Opportunities
testObjectUnderTest = findTestObject('Object Repository/Opportunities/Alert Desk/Dashboard/Opportunities Dashboard/Tagged Opportunities/Tagged Opportunities Refresh Button')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Get list of Tagged Opportunities
def taggedOpportunitiesList = CustomKeywords.'opportunities.alertdesk.dashboard.opportunities.TaggedOpportunities.returnList'()

Collections.sort(taggedOpportunitiesList)

// Go to the Search page
def searchUrl = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.SEARCH

WebUI.navigateToUrl(searchUrl)

CustomKeywords.'test.Assistant.verifyEqual'(WebUI.getUrl(), searchUrl, 'Navigated successfully to the search page url', AfterFailure.STOP)

// Expand section
def sectionToggled = CustomKeywords.'search.Page.expandSection'(section)

CustomKeywords.'test.Assistant.verifyTrue'(sectionToggled, section.getValue()['header'] + ' section successfully expanded', AfterFailure.STOP)

// Tags
// Verify label
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Tags/Search Section - Tags - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Tags', 'Tags label', AfterFailure.CONTINUE)

// Open dropdown
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Tags/Search Section - Tags - Input')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Get dropdown options and confirm matches Tagged Opportunities list
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Tags/Search Section - Tags - Dropdown')

def options = CustomKeywords.'search.section.Search.getDropdownOptions'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyNotEqual'(options, null, AfterFailure.STOP)

def optionsList = []

for(int i = 0 ; i < options.size() ; i++){
	
	optionsList.add(options[i][0])
}

CustomKeywords.'test.Assistant.verifyEqual'(optionsList, taggedOpportunitiesList, 'Tags dropdown (Search Page vs. Tagged Opportunities)', AfterFailure.STOP)