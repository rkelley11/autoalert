package dealsheet

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
import dealsheet.TestObjectNames

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class CustomerProfile extends WrappedSelenium{
	
	/*
	 * Many areas on this page refer to the TestObjectNames class which has been in the process of being re-factored out.
	 * Instead of using the TestObjectNames class to store the names and then using a custom returnTestObject() method, it is
	 * being replaced with the findTestObject() method from Katalon because Katalon will automatically update the objects path if changes
	 * in structure are made, but only if used with this method.  Is the path is stored in a string and used with findTestObject then
	 * it does not get updated which was the main reason why the original returnTestObject() method was created.
	 */

	def utils, randomGenerator, housekeeper, driver

	def objNames, originalFieldValues, randomFieldValues, attributeValue, elementsList

	def homePhoneDropdownListByLocator = By.cssSelector("[phones=\"\$ctrl.homePhones\"] [class*=\"dropdown-menu\"] [ng-bind=\"\$ctrl.phone.value | localePhone\"]")
	def workPhoneDropdownListByLocator = By.cssSelector("[phones=\"\$ctrl.workPhones\"] [class*=\"dropdown-menu\"] [ng-bind=\"\$ctrl.phone.value | localePhone\"]")
	def cellPhoneDropdownListByLocator = By.cssSelector("[phones=\"\$ctrl.cellPhones\"] [class*=\"dropdown-menu\"] [ng-bind=\"\$ctrl.phone.value | localePhone\"]")
	def emailDropdownListByLocator = By.cssSelector("customer-email-list [class*=\"dropdown-menu\"] customer-email")

	def homePhoneClientEditListByLocator = By.cssSelector("[on-change=\"\$ctrl.onAlternateContactChanged('alternateHomePhones', change)\"] [name=\"phoneNumber\"]")
	def workPhoneClientEditListByLocator = By.cssSelector("[on-change=\"\$ctrl.onAlternateContactChanged('alternateWorkPhones', change)\"] [name=\"phoneNumber\"]")
	def cellPhoneClientEditListByLocator = By.cssSelector("[on-change=\"\$ctrl.onAlternateContactChanged('alternateCellPhones', change)\"] [name=\"phoneNumber\"]")
	def emailClientEditListByLocator = By.cssSelector("[on-change=\"\$ctrl.onAlternateContactChanged('alternateEmailAddresses', change)\"] [name=\"emailAddress\"]")

	def flagDealsheetObjName = TestObjectNames.UpperSection.Icons.FLAG_DEALSHEET
	def selectedStateObjName = TestObjectNames.UpperSection.Customer.ClientEdit.Address.State.SELECTED
	def clientEditSubmitButtonObjName = TestObjectNames.UpperSection.Customer.ClientEdit.Buttons.SUBMIT
	def customerProfileEditIconObjName = TestObjectNames.UpperSection.Customer.CUSTOMER_PROFILE_EDIT_ICON
	def clientEditConfirmYesButtonObjName = TestObjectNames.UpperSection.Customer.ClientEdit.Buttons.CONFIRM_YES

	CustomerProfile(){

		this.utils = new Utilities()

		this.objNames = loadObjNames()

		this.randomFieldValues = this.originalFieldValues = [:]

		this.housekeeper = new Housekeeping()

		this.driver = DriverFactory.getWebDriver()

		this.randomGenerator = new RandomGenerator()
	}

	def loadObjNames(){

		def objNames = [:]

		objNames[FieldNames.FIRSTNAME] = TestObjectNames.UpperSection.Customer.ClientEdit.Name.FIRST
		objNames[FieldNames.MIDDLENAME] = TestObjectNames.UpperSection.Customer.ClientEdit.Name.MIDDLE
		objNames[FieldNames.LASTNAME] = TestObjectNames.UpperSection.Customer.ClientEdit.Name.LAST

		objNames[FieldNames.COMPANYNAME] = TestObjectNames.UpperSection.Customer.ClientEdit.COMPANY_NAME

		objNames[FieldNames.ADDRESS1] = TestObjectNames.UpperSection.Customer.ClientEdit.Address.FIRST
		objNames[FieldNames.ADDRESS2] = TestObjectNames.UpperSection.Customer.ClientEdit.Address.SECOND
		objNames[FieldNames.CITY] = TestObjectNames.UpperSection.Customer.ClientEdit.Address.CITY

		objNames[FieldNames.STATE] = TestObjectNames.UpperSection.Customer.ClientEdit.Address.State.DROPDOWN

		objNames[FieldNames.ZIPCODE] = TestObjectNames.UpperSection.Customer.ClientEdit.Address.ZIP_CODE

		objNames[FieldNames.HOMEPHONE1] = TestObjectNames.UpperSection.Customer.ClientEdit.Phone.Home.PREFERRED
		objNames[FieldNames.HOMEPHONE1BAD] = TestObjectNames.UpperSection.Customer.ClientEdit.Phone.Home.PREFERRED_BAD

		objNames[FieldNames.WORKPHONE1] = TestObjectNames.UpperSection.Customer.ClientEdit.Phone.Work.PREFERRED
		objNames[FieldNames.WORKPHONE1BAD] = TestObjectNames.UpperSection.Customer.ClientEdit.Phone.Work.PREFERRED_BAD

		objNames[FieldNames.CELLPHONE1] = TestObjectNames.UpperSection.Customer.ClientEdit.Phone.Cell.PREFERRED
		objNames[FieldNames.CELLPHONE1BAD] = TestObjectNames.UpperSection.Customer.ClientEdit.Phone.Cell.PREFERRED_BAD

		objNames[FieldNames.EMAIL1] = TestObjectNames.UpperSection.Customer.ClientEdit.Email.PREFERRED
		objNames[FieldNames.EMAIL1BAD] = TestObjectNames.UpperSection.Customer.ClientEdit.Email.PREFERRED_BAD

		objNames[FieldNames.DONOTCALL] = TestObjectNames.UpperSection.Customer.ClientEdit.DoNot.CALL
		objNames[FieldNames.DONOTEMAIL] = TestObjectNames.UpperSection.Customer.ClientEdit.DoNot.EMAIL
		objNames[FieldNames.DONOTMAIL] = TestObjectNames.UpperSection.Customer.ClientEdit.DoNot.MAIL

		objNames[FieldNames.LANGUAGE_PREFERENCE] = TestObjectNames.UpperSection.Customer.ClientEdit.Dropdown.LANGUAGE_PREFERENCE

		objNames
	}

	@Keyword
	def getAlternatePhoneNumbersFromDealsheet(PhoneTypes phoneType){

		def by, attribute

		def itemsList = []

		switch (phoneType){

			case (PhoneTypes.HOMEMAINPAGE):

				by = homePhoneDropdownListByLocator

				attribute = "text"

				break

			case (PhoneTypes.WORKMAINPAGE):

				by = workPhoneDropdownListByLocator

				attribute = "text"

				break

			case (PhoneTypes.CELLMAINPAGE):

				by = cellPhoneDropdownListByLocator

				attribute = "text"

				break

			case (PhoneTypes.HOMECLIENTEDIT):

				by = homePhoneClientEditListByLocator

				attribute = "value"

				break

			case (PhoneTypes.WORKCLIENTEDIT):

				by = workPhoneClientEditListByLocator

				attribute = "value"

				break

			case (PhoneTypes.CELLCLIENTEDIT):

				by = cellPhoneClientEditListByLocator

				attribute = "value"

				break

			case (PhoneTypes.EMAILMAINPAGE):

				by = emailDropdownListByLocator

				attribute = "text"

				break

			case (PhoneTypes.EMAILCLIENTEDIT):

				by = emailClientEditListByLocator

				attribute = "value"

				break

			default:

				break
		}

		elementsList = findElements(by)

		(elementsList).each{

			attributeValue = it.getAttribute(attribute)

			if(!attributeValue){

				attributeValue = it.getText()
			}

			itemsList.add(attributeValue)
		}

		itemsList
	}

	@Keyword
	def getExpectedDisplayedCustomerProfileName(){

		def firstMiddleLast = ""

		def expectedDisplayedName, spacing

		this.originalFieldValues = getAllClientFieldValues()

		[
			FieldNames.FIRSTNAME,
			FieldNames.MIDDLENAME,
			FieldNames.LASTNAME
		].each{

			spacing = (firstMiddleLast) ? " " : ""

			if(this.originalFieldValues[it]){

				firstMiddleLast += spacing + this.originalFieldValues[it]
			}
		}

		if(this.originalFieldValues[FieldNames.COMPANYNAME]){

			expectedDisplayedName = this.originalFieldValues[FieldNames.COMPANYNAME] + " (" + firstMiddleLast + ")"
		}
		else{

			expectedDisplayedName = firstMiddleLast
		}

		expectedDisplayedName
	}

	@Keyword
	def getExpectedDisplayedStreet(){

		def expectedStreetDisplayed = ""

		def spacing

		this.originalFieldValues = getAllClientFieldValues()

		[
			FieldNames.ADDRESS1,
			FieldNames.ADDRESS2
		].each{

			spacing = (expectedStreetDisplayed) ? " " : ""

			if(this.originalFieldValues[it]){

				expectedStreetDisplayed += spacing + this.originalFieldValues[it]
			}
		}

		expectedStreetDisplayed
	}

	@Keyword
	def getExpectedDisplayedCompleteAddress(){

		def expectedAddressDisplayed = getExpectedDisplayedStreet()

		def states = getStateName()

		def spacing, notStateSpacing, buildingAddress

		def addresses = []

		this.originalFieldValues = getAllClientFieldValues()

		for(int i = 0; i < states.size(); i++){

			buildingAddress = expectedAddressDisplayed

			[
				FieldNames.CITY,
				FieldNames.STATE,
				FieldNames.ZIPCODE
			].each{

				notStateSpacing = (it == FieldNames.STATE && this.originalFieldValues[FieldNames.CITY]) ? "" : " "

				spacing = (expectedAddressDisplayed) ? notStateSpacing + ", " : ""

				if(this.originalFieldValues[it]){

					buildingAddress += spacing + ((it == FieldNames.STATE) ? " " + states[i] : this.originalFieldValues[it])
				}
			}

			addresses.add(buildingAddress)
		}

		addresses
	}

	@Keyword
	def getFlaggedStatus(){

		def flagTitle = getAttribute(returnTestObject(flagDealsheetObjName), "title")

		def flagged = (flagTitle == "Flag Deal Sheet") ? false : true

		flagged
	}

	def getStateName(){

		def states = []

		def uneditedState = WebUI.getText(returnTestObject(selectedStateObjName))

		def dashIndex = uneditedState.indexOf("-") + 1

		states.add(uneditedState.substring(0, dashIndex - 1))

		states.add(uneditedState.substring(dashIndex, uneditedState.size()))

		states
	}

	@Keyword
	def verifyAllClientEditFields(){

		def key, afterFieldValues, isItCorrect

		this.originalFieldValues = getAllClientFieldValues()

		for (Map.Entry<FieldNames, String> entry : this.objNames.entrySet()) {

			key = entry.getKey()

			this.randomFieldValues[key] = generateRandomFieldValue(key)

			inputData(key, this.randomFieldValues[key])
		}

		clickAndAcceptConfirmation(returnTestObject(clientEditSubmitButtonObjName))

		click(returnTestObject(customerProfileEditIconObjName))

		afterFieldValues = getAllClientFieldValues()

		KeywordUtil.logInfo("Random Fields: " + this.randomFieldValues as String)

		KeywordUtil.logInfo("After Fields: " + afterFieldValues as String)

		isItCorrect = (this.randomFieldValues as String).equals(afterFieldValues as String)

		KeywordUtil.logInfo("The random fields equal to the fields after input: " + isItCorrect as String)

		for (Map.Entry<FieldNames, String> entry : this.objNames.entrySet()) {

			key = entry.getKey()

			inputData(key, this.originalFieldValues[key])
		}

		clickAndAcceptConfirmation(returnTestObject(clientEditSubmitButtonObjName))

		isItCorrect
	}

	def clickAndAcceptConfirmation(testObject){

		click(testObject)

		this.housekeeper.clearModal(returnTestObject(clientEditConfirmYesButtonObjName))
	}

	def inputData(fieldName, fieldValue){

		def testObject, currentCheckedStatus

		testObject = returnTestObject(this.objNames[fieldName])

		switch (fieldName){

			case FieldNames.HOMEPHONE1:
			case FieldNames.WORKPHONE1:
			case FieldNames.CELLPHONE1:

				KeywordUtil.logInfo("Switch - Case: "  + fieldName as String)

				WebUI.clearText(testObject)

				WebUI.sendKeys(testObject, fieldValue)

				break

			case FieldNames.HOMEPHONE1BAD:
			case FieldNames.WORKPHONE1BAD:
			case FieldNames.CELLPHONE1BAD:
			case FieldNames.EMAIL1BAD:
			case FieldNames.DONOTCALL:
			case FieldNames.DONOTEMAIL:
			case FieldNames.DONOTMAIL:
			case FieldNames.DONOTTEXT:

				KeywordUtil.logInfo("RANDOM Switch - Case: " + fieldName as String)

				currentCheckedStatus = (WebUI.getAttribute(testObject, "class")).contains("ng-not-empty")

				if(currentCheckedStatus != fieldValue){

					click(testObject)
				}

				break

			case FieldNames.STATE:
			case FieldNames.LANGUAGE_PREFERENCE:

				WebUI.selectOptionByValue(testObject, fieldValue as String, false)

				break

			case FieldNames.CANCEL:
			case FieldNames.SUBMIT:

				break

			default:

				KeywordUtil.logInfo("Switch - Case: DEFAULT")

				WebUI.clearText(testObject)

				WebUI.sendKeys(testObject, fieldValue)

				break
		}
	}

	@Keyword
	def clearBadCheckboxes(){

		def by = By.cssSelector("form [type=\"checkbox\"]")

		click(returnTestObject(customerProfileEditIconObjName))

		//WebUI.click(returnTestObject(customerProfileEditIconObjName))

		def checkboxes = findElements(by)

		checkboxes.each{

			if(it.isSelected()){

				click(it)
			}
		}

		clickAndAcceptConfirmation(returnTestObject(clientEditSubmitButtonObjName))
	}

	def generateRandomFieldValue(FieldNames fieldName){

		def testObject, randomFieldValue

		switch (fieldName){

			case FieldNames.HOMEPHONE1BAD:
			case FieldNames.WORKPHONE1BAD:
			case FieldNames.CELLPHONE1BAD:
			case FieldNames.EMAIL1BAD:
			case FieldNames.DONOTCALL:
			case FieldNames.DONOTEMAIL:
			case FieldNames.DONOTMAIL:
			case FieldNames.DONOTTEXT:

				KeywordUtil.logInfo("RANDOM Switch - Case: " + fieldName as String)

				randomFieldValue = !this.originalFieldValues[fieldName]

				break

			case FieldNames.HOMEPHONE1:
			case FieldNames.WORKPHONE1:
			case FieldNames.CELLPHONE1:

				KeywordUtil.logInfo("RANDOM Switch - Case: " + fieldName as String)

				randomFieldValue = this.randomGenerator.generateRandomPhoneNumber()

				break

			case FieldNames.EMAIL1:

				randomFieldValue = this.randomGenerator.generateRandomEmailAddress()

				break

			case FieldNames.ADDRESS1:
			case FieldNames.ADDRESS2:

				randomFieldValue = this.randomGenerator.generateRandomStreetAddress()

				break

			case FieldNames.ZIPCODE:

				randomFieldValue = this.randomGenerator.generateRandomString(5)

				break

			case FieldNames.STATE:

				randomFieldValue = new Random().nextInt(59) + 2

				break

			case FieldNames.LANGUAGE_PREFERENCE:

				randomFieldValue = 'en-US' //Need to change to make random choice

				break

			case FieldNames.CANCEL:
			case FieldNames.SUBMIT:

				randomFieldValue = null

				break

			default:

				KeywordUtil.logInfo("Switch - Case: DEFAULT")

				randomFieldValue = this.randomGenerator.generateRandomString(new Random().nextInt(11) + 5)

				break
		}

		randomFieldValue
	}

	def getAllClientFieldValues(){

		def key, value, testObject, fieldValue

		def clientFields = [:]

		for (Map.Entry<FieldNames, String> entry : this.objNames.entrySet()) {

			key = entry.getKey()

			value = entry.getValue()

			testObject = returnTestObject(value)

			switch (key){

				case FieldNames.HOMEPHONE1BAD:
				case FieldNames.HOMEPHONE2BAD:
				case FieldNames.WORKPHONE1BAD:
				case FieldNames.CELLPHONE1BAD:
				case FieldNames.EMAIL1BAD:
				case FieldNames.EMAIL2BAD:
				case FieldNames.DONOTCALL:
				case FieldNames.DONOTEMAIL:
				case FieldNames.DONOTMAIL:
				case FieldNames.DONOTTEXT:

					KeywordUtil.logInfo("Switch - Case: " + key as String)

					fieldValue = (WebUI.getAttribute(testObject, "class")).contains("ng-not-empty")

					break

				case FieldNames.HOMEPHONE1:
				case FieldNames.WORKPHONE1:
				case FieldNames.CELLPHONE1:

					KeywordUtil.logInfo("Switch - Case: DEFAULT")

					def preFieldValue = WebUI.getAttribute(testObject, "value")

					fieldValue = preFieldValue.replaceAll("[() -]", "")

					break

				/*case FieldNames.LANGUAGE_PREFERENCE:
				 fieldValue = WebUI.getAttribute(testObject, "value")
				 KeywordUtil.logInfo("Switch - Case: Need to figure this out")
				 break*/

				case FieldNames.STATE:
				case FieldNames.LANGUAGE_PREFERENCE:
				default:

					fieldValue = WebUI.getAttribute(testObject, "value")

					KeywordUtil.logInfo("Switch - Case: DEFAULT")

					break
			}

			clientFields[key] = fieldValue

			KeywordUtil.logInfo("Key: " + (key as String) + ", Value: " + clientFields[key] as String)
		}

		clientFields
	}

	// This method probably should be combined with others to form one method to interact with fields
	@Keyword
	def setCheckbox(FieldNames fieldName, Boolean state){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def passed = false

		try{

			click(returnTestObject(customerProfileEditIconObjName))

			inputData(fieldName, state)

			clickAndAcceptConfirmation(returnTestObject(TestObjectNames.UpperSection.Customer.ClientEdit.Buttons.SUBMIT))

			passed = true

		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		passed
	}

	@Keyword
	def areOpportunitiesDisplayed(){

		def oppsBy = By.cssSelector(".qa-ds-alerts .qa-alerts-opportunities .opportunity")

		def oppsDisplayed = findElements(oppsBy, 1)

		oppsDisplayed.size() > 0
	}

	@Keyword
	def getAllContactInfo(DealsheetContactFields contactField){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def allContactInfo = []

		try{

			def allRows = getAllRows(contactField)

			for (int i = 0 ; i < allRows.size() ; i++){

				def contactInfo = []

				def classes = getAttribute(allRows[i], 'class')

				def strikeThrough = classes.contains('strike-out')

				def text

				if(strikeThrough){

					def js = (JavascriptExecutor)this.driver

					text = js.executeScript("return arguments[0].innerHTML;",allRows[i])

					KeywordUtil.logInfo(logHeader + "Text found with Javascript: " + text as String)
				}
				else{

					text = getAttribute(allRows[i], 'text')
				}

				contactInfo.add(text)

				contactInfo.add(strikeThrough)

				allContactInfo.add(contactInfo)
			}

			KeywordUtil.logInfo(logHeader + "All contact info for " + (contactField.name()) + ": " + allContactInfo as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		allContactInfo
	}

	// EXACTLY THE SAME AS METHOD IN ClientEdit class
	def getAllRows(DealsheetContactFields contactField){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def allRows

		try{

			def fieldInfo = contactField.getValue()

			allRows = findElements(By.cssSelector(fieldInfo['allRows']))

			def allRowsSize = allRows.size()

			KeywordUtil.logInfo("Found " + (allRowsSize as String) + " row" + (allRowsSize == 1 ? "" : "s") + " for the " + contactField.name() + " section")
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		allRows
	}

	// SHOULD ONLY USE THIS OR THE FIELDNAMES ENUM BELOW.  NEED TO COMBINE
	enum DealsheetContactFields{

		HOME_PHONE(['allRows':'.qa-customer-phone-list-home [ng-if*="phone.value"]']),
		WORK_PHONE(['allRows':'.qa-customer-phone-list-work [ng-if*="phone.value"]']),
		CELL_PHONE(['allRows':'.qa-customer-phone-list-cell [ng-if*="phone.value"]']),
		EMAIL(['allRows':'customer-email'])

		def getValue

		DealsheetContactFields(LinkedHashMap getValue){

			this.getValue = getValue
		}

		def getValue(){

			return this.getValue
		}
	}

	enum FieldNames{
		FIRSTNAME,
		MIDDLENAME,
		LASTNAME,
		COMPANYNAME,
		ADDRESS1,
		ADDRESS2,
		CITY,
		STATE,
		ZIPCODE,
		HOMEPHONE1,
		HOMEPHONE1BAD,
		HOMEPHONE2,
		HOMEPHONE2BAD,
		WORKPHONE1,
		WORKPHONE1BAD,
		CELLPHONE1,
		CELLPHONE1BAD,
		EMAIL1,
		EMAIL1BAD,
		EMAIL2,
		EMAIL2BAD,
		DONOTCALL,
		DONOTEMAIL,
		DONOTMAIL,
		DONOTTEXT,
		LANGUAGE_PREFERENCE,
		CANCEL,
		SUBMIT
	}

	enum PhoneTypes{
		HOMEMAINPAGE,
		WORKMAINPAGE,
		CELLMAINPAGE,
		HOMECLIENTEDIT,
		WORKCLIENTEDIT,
		CELLCLIENTEDIT,
		EMAILMAINPAGE,
		EMAILCLIENTEDIT
	}
}