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

import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class WrappedSelenium extends Utilities{

	def driver, wait

	WrappedSelenium(){

		this.driver = DriverFactory.getWebDriver()

		this.wait = new WebDriverWait(this.driver, 10)
	}

	//================================================================================================================
	//     NAVIGATE
	//================================================================================================================

	def navigateToUrl(String url){

		WebUI.navigateToUrl(url)
	}

	//================================================================================================================
	//     MOUSE OVER
	//================================================================================================================

	@Keyword
	def mouseOver(TestObject testObject){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			if(GlobalVariable.Browser == "firefox"){

				def act = new Actions(this.driver)

				def element = findElement(testObject)

				act.moveToElement(element).perform()
			}
			else{

				WebUI.mouseOver(testObject)
			}

			successful = true
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	@Keyword
	def mouseOver1AndClick2(TestObject testObject1, TestObject testObject2){

		def act = new Actions(this.driver)

		def element1 = findElement(testObject1)

		def element2 = findElement(testObject2)

		act.moveToElement(element1).moveToElement(element2).click().build().perform()
	}

	//================================================================================================================
	//     GET ATTRIBUTE
	//================================================================================================================

	def getAttribute(WebElement element, String attributeName){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def attributeValue

		try{

			attributeValue = element.getAttribute(attributeName)

			KeywordUtil.logInfo(logHeader + "'" + (attributeName as String) + "' attribute value: " + attributeValue as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		attributeValue
	}

	def getAttribute(TestObject testObject, String attributeName){

		WebUI.getAttribute(testObject, attributeName)
	}

	def getAttribute(By by, String attributeName){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def attributeValue

		try{

			attributeValue = findElement(by).getAttribute(attributeName)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		attributeValue
	}

	//================================================================================================================
	//     CLICK
	//================================================================================================================

	@Keyword
	def click(WebElement element){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def elementHasBeenClicked = false

		if(element){

			KeywordUtil.logInfo(logHeader + "Attempting to pass along a WEB ELEMENT to click")

			elementHasBeenClicked = click(element, 20)
		}
		else{

			KeywordUtil.markWarning(logHeader + "Element passed to click is null")
		}

		elementHasBeenClicked
	}

	def click(WebElement element, int timeout){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		if(!timeout) timeout = 20

		def timeExpired = false

		def elementHasBeenClicked = false

		def timeoutPeriod = new TimeDuration(0, 0, timeout, 0)

		def timeStart = new Date()

		sleep(500)

		while(!elementHasBeenClicked && !timeExpired){

			try{

				scrollToElement(element)

				KeywordUtil.logInfo(logHeader + "Attempting to click on element")

				element.click()

				elementHasBeenClicked = true
			}
			catch(Exception e){

				def message = e.getMessage()

				KeywordUtil.markWarning(message)

				// If exception due to element being stale then attempting stop trying
				if(message.contains('stale element reference')){

					elementHasBeenClicked = null

					timeoutPeriod = new TimeDuration(0, 0, 0, 0)
				}
			}

			if(!elementHasBeenClicked){

				sleep(1000)
			}

			timeExpired = isTimeExpired(timeStart, timeoutPeriod)
		}

		if(elementHasBeenClicked){

			KeywordUtil.logInfo(logHeader + "Element was successfully clicked")
		}
		else{

			KeywordUtil.logInfo(logHeader + "Element was NOT successfully clicked before timeout of " + timeoutPeriod as String)
		}

		elementHasBeenClicked
	}

	def click(TestObject testObject, int timeout){

		def logHeader

		def elementHasBeenClicked = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + "Attempting to click on a TEST OBJECT")

			def parentObject = testObject.getParentObject()

			if (parentObject) this.driver.switchTo().frame(findElement(parentObject))

			def element = findElement(testObject)

			elementHasBeenClicked = click(element, timeout)

			//if(elementHasBeenClicked == null) click(testObject, timeout) // Attempt to retry stale element
		}
		catch(Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}
		finally{

			this.driver.switchTo().defaultContent()
		}

		elementHasBeenClicked
	}

	def click(TestObject testObject){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def elementHasBeenClicked = false

		if(testObject){

			KeywordUtil.logInfo(logHeader + "Attempting to pass along a TEST OBJECT to click")

			elementHasBeenClicked = click(testObject, 20)
		}
		else{

			KeywordUtil.markWarning(logHeader + "Test Object passed to click is null")
		}

		elementHasBeenClicked
	}

	def click(By by){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def timeExpired = false

		def elementHasBeenClicked = false

		def timeoutPeriod = new TimeDuration(0,0,20,0)

		def timeStart = new Date()

		while(!elementHasBeenClicked && !timeExpired){

			try{

				KeywordUtil.logInfo(logHeader + "Attempting to click on element using " + by as String)

				def element = findElement(by)

				scrollToElement(element)

				element.click()

				elementHasBeenClicked = true
			}
			catch(Exception e){

				KeywordUtil.markWarning(e.getMessage())
			}

			if(!elementHasBeenClicked){

				KeywordUtil.logInfo(logHeader + "Sleep for 1 second")

				sleep(1000)
			}

			timeExpired = isTimeExpired(timeStart, timeoutPeriod)
		}

		if(elementHasBeenClicked){

			KeywordUtil.logInfo(logHeader + "Element was clicked successfully")
		}
		else{

			KeywordUtil.logInfo(logHeader + "Element was unable to be clicked before timing out")
		}

		elementHasBeenClicked
	}

	//================================================================================================================
	//     FIND ELEMENTS
	//================================================================================================================

	@Keyword
	def findElement(By by){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		KeywordUtil.logInfo(logHeader + "Attempting to find WEB ELEMENT using " + by as String)

		def elements = findElements(by)

		def element

		if(elements){

			element = elements[0]
		}
	}

	def findElement(TestObject testObject){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		KeywordUtil.logInfo(logHeader + "Attempting to find WEB ELEMENT from TEST OBJECT")

		def by = getByLocator(testObject)

		findElement(by)
	}

	def findElement(By by, Integer timeout){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		KeywordUtil.logInfo(logHeader + "Attempting to find WEB ELEMENT using " + by as String)

		def elements = findElements(by, timeout)

		def element

		if(elements){

			element = elements[0]
		}
	}

	def findElement(TestObject testObject, Integer timeout){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		KeywordUtil.logInfo(logHeader + "Attempting to find WEB ELEMENT from TEST OBJECT")

		def by = getByLocator(testObject)

		findElement(by, timeout)
	}

	@Keyword
	def findElements(By by){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		KeywordUtil.logInfo(logHeader + "Find elements received: By")

		findElements(by, this.driver, 10)
	}

	def findElements(By by, Integer timeout){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		KeywordUtil.logInfo(logHeader + "Find elements received: By and Timeout")

		findElements(by, this.driver, timeout)
	}

	def findElements(By by, WebElement element){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		KeywordUtil.logInfo(logHeader + "Find elements received: By and Element")

		findElements(by, element, 10)
	}

	def findElements(By by, element, int timeout){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		KeywordUtil.logInfo(logHeader + "Attempting to find WEB ELEMENTS using " + (by as String) + " with a timeout of " + (timeout as String) + " seconds.")

		def elements, duration

		def timeExpired = false

		def elementsHaveBeenFound = false

		def timeoutPeriod = new TimeDuration(0,0,timeout,0)

		def timeStart = new Date()

		while(!elementsHaveBeenFound && !timeExpired){

			try{

				elements = element.findElements(by)

				KeywordUtil.logInfo(logHeader + "Find Elements found: " + elements.size() as String)

				elementsHaveBeenFound = elements.size() > 0

				if(!elementsHaveBeenFound){

					sleep(1000) //Wait 1 second before polling again
				}
			}
			catch (Exception e){

				KeywordUtil.markWarning(logHeader + e.getMessage())
			}

			if(!elementsHaveBeenFound) timeExpired = isTimeExpired(timeStart, timeoutPeriod)
		}

		elements
	}

	def findElements(By by, TestObject testObject){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def elements

		KeywordUtil.logInfo(logHeader + "Attempting to find WEB ELEMENTS from TEST OBJECT using " + by as String)

		def testObjectElement = findElement(testObject)

		if(testObjectElement){

			elements = findElements(by, testObjectElement, 10)
		}

		KeywordUtil.logInfo(logHeader + "Elements found using (" + (by as String) + "): " + elements.size() as String)

		elements
	}

	def findElements(By by, TestObject testObject, Integer timeout){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def elements

		KeywordUtil.logInfo(logHeader + "Attempting to find WEB ELEMENTS from TEST OBJECT using " + by as String)

		def testObjectElement = findElement(testObject)

		if(testObjectElement){

			if(timeout < 1) timeout = 1

			if(timeout > 30) timeout = 30

			elements = findElements(by, testObjectElement, timeout)
		}

		KeywordUtil.logInfo(logHeader + "Elements found using (" + (by as String) + "): " + elements.size() as String)

		elements
	}

	def findElementsOLD(By by, TestObject testObject){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		KeywordUtil.logInfo(logHeader + "Attempting to find WEB ELEMENTS from TEST OBJECT using " + by as String)

		def elements = findElement(testObject).findElements(by)

		KeywordUtil.logInfo(logHeader + "Elements found using (" + (by as String) + "): " + elements.size() as String)

		elements
	}

	//================================================================================================================
	//     GET TEXT
	//================================================================================================================

	@Keyword
	def getText(WebElement webElement){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		//scrollToElement(webElement)

		def textFound = webElement.getText()

		KeywordUtil.logInfo(logHeader + "Text found from WEB ELEMENT: " + textFound)

		textFound
	}

	def getText(TestObject testObject){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def textFound = WebUI.getText(testObject)

		KeywordUtil.logInfo(logHeader + "Text found from TEST OBJECT: " + textFound)

		textFound
	}

	//================================================================================================================
	//     CLEAR TEXT
	//================================================================================================================

	def clearText(TestObject testObject){

		WebUI.clearText(testObject)
	}

	//================================================================================================================
	//     SEND KEYS
	//================================================================================================================

	def sendKeys(TestObject testObject, String keysToSend){

		def element = findElement(testObject)

		scrollToElement(element)

		element.sendKeys(keysToSend)
	}

	//================================================================================================================
	//     CHECKBOXES
	//================================================================================================================

	def check(WebElement checkbox){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			if(checkbox.isSelected()){

				KeywordUtil.logInfo(logHeader + "Checkbox already checked.  Doing nothing.")

				successful = true
			}
			else{

				successful = click(checkbox)

				KeywordUtil.logInfo(logHeader + "Checkbox WAS" + (successful ? " " : " NOT ") + "successfully checked")
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	def check(TestObject testObject){

		check(findElement(testObject))
	}

	def uncheck(WebElement checkbox){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			if(checkbox.isSelected()){

				successful = click(checkbox)

				KeywordUtil.logInfo(logHeader + "Checkbox WAS" + (successful ? " " : " NOT ") + "successfully unchecked")
			}
			else{

				KeywordUtil.logInfo(logHeader + "Checkbox already unchecked.  Doing nothing.")

				successful = true
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	def uncheck(TestObject testObject){

		uncheck(findElement(testObject))
	}


	//================================================================================================================
	//     DROPDOWNS
	//================================================================================================================

	def selectOptionByLabel(TestObject testObject, String label, Boolean isRegex){

		WebUI.selectOptionByLabel(testObject, label, isRegex)
	}

	def getAllOptions(TestObject testObject){

		def optionsList = []

		def by = By.cssSelector('option')

		def element = findElement(testObject)

		def options = findElements(by, element)

		for(WebElement option : options){

			optionsList.add(getText(option))
		}

		optionsList
	}

	// Refactor the above method to only use this one
	@Keyword
	def getAllOptions2(TestObject testObject){

		def logHeader

		def optionsList = []

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def by = By.cssSelector('option')

			def element = findElement(testObject)

			def options = findElements(by, element)

			for(WebElement option : options){

				def optionInfo = [:]

				optionInfo['text'] = getText(option)

				optionInfo['value'] = getAttribute(option, 'value')

				optionsList.add(optionInfo)
			}

			KeywordUtil.logInfo(logHeader + "Options found: " + optionsList as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		optionsList
	}

	@Keyword
	def optionExists(TestObject dropdown, String option){

		def logHeader

		def optionDoesExist = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def allOptions = getAllOptions2(dropdown)

			if(allOptions){

				def numberOfOptions = allOptions.size()

				for(int i = 0 ; i < numberOfOptions ; i++){

					def optionText = allOptions[i]['text']

					if(optionText){

						optionDoesExist = optionText.toLowerCase() == option.toLowerCase()

						if(optionDoesExist){

							KeywordUtil.logInfo(logHeader + "Option found '" + optionText + "' matching desired option '" + option + "'")

							break
						}
					}
				}
			}
			else{

				throw new Exception("No options found")
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		optionDoesExist
	}

	//================================================================================================================
	//     BY LOCATORS
	//================================================================================================================

	def getByLocator(TestObject testObject){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def activeProperty = getActiveProperty(testObject)

		KeywordUtil.logInfo(logHeader + "Active Property - Name: " + activeProperty.name + ", Value: " + activeProperty.value)

		getByLocator(activeProperty.name, activeProperty.value)
	}

	def getByLocator(String name, String value){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def byLocator

		switch (name){

			case "xpath":

				byLocator = By.xpath(value)

				break

			case "id":

				byLocator = By.id(value)

				break

			case "css":

				byLocator = By.cssSelector(value)

				break

			default:

				byLocator = By.xpath(value)

				break
		}

		KeywordUtil.logInfo(logHeader + "By Locator: " + byLocator as String)

		byLocator
	}

	//================================================================================================================
	//     PAGE SOURCE
	//================================================================================================================

	def getPageSource(){

		this.driver.getPageSource()
	}

	//================================================================================================================
	//     WAITING - Temporary place holders using WebUI framework
	//================================================================================================================

	@Keyword
	def waitForElementVisible(testObject, timeout){

		waitForElementVisible(testObject, timeout)
	}

	def waitForElementVisible(TestObject testObject){

		WebUI.waitForElementVisible(testObject, 10)
	}

	def waitForElementVisible(TestObject testObject, int timeout){

		WebUI.waitForElementVisible(testObject, timeout)
	}

	@Keyword
	def waitForElementToNotBeVisible(TestObject testObject){

		WebUI.waitForElementNotVisible(testObject, 10)
	}

	//================================================================================================================
	//     VERIFY - Temporary place holders using WebUI framework
	//================================================================================================================

	def verifyElementClickable(testObject){

		WebUI.verifyElementClickable(testObject)
	}

	//================================================================================================================
	//     GET CSS VALUE
	//================================================================================================================

	@Keyword
	def getCssValue(TestObject testObject, String attribute){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def cssValue

		try{

			def element = findElement(testObject)

			cssValue = element.getCssValue(attribute)

			KeywordUtil.logInfo(logHeader + "CSS Value: " + attribute as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		cssValue
	}

	//================================================================================================================
	//     MISCELLANEOUS - NOT STRICTLY SELENIUM, BUT SELENIUM RELATED
	//================================================================================================================

	def getActiveProperty(TestObject testObject){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def propertiesList = testObject.getProperties()

		def isAnActiveProperty = false

		def numberOfProperties = propertiesList.size()

		for(int i = 0; i < numberOfProperties; i++){

			isAnActiveProperty = propertiesList[i].isActive()

			if (isAnActiveProperty){

				KeywordUtil.logInfo(logHeader + "Active property found")

				return propertiesList[i]

				break
			}
		}

		throw new Exception(logHeader + "Active property NOT found")
	}

	def scrollToElement(WebElement element){

		//KeywordUtil.logInfo("Attempting to scroll to element")

		//this.driver.executeScript("arguments[0].scrollIntoView();", element)

		// Need to look into making this better.  Scrolling all the time doesn't appear to work.  Sometimes, it makes popup menus disappear.
	}

	def isElementDisabled(By by){

		isElementDisabled(findElement(by))
	}

	def isElementDisabled(WebElement element){

		def isDisabled, disabledAttribute, logHeader

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			disabledAttribute = getAttribute(element, "disabled")

			isDisabled = disabledAttribute == 'disabled'
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())

			isDisabled = true // assuming exception when checking attribute means attribute doesn't exist
		}

		isDisabled
	}

	def isElementActive(WebElement element){

		def logHeader

		def isActive = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			isActive = element.isEnabled()
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		isActive
	}
}