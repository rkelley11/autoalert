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

import groovy.time.*

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class GoogleMap extends WrappedSelenium{
	
	/*
	 * Many areas on this page refer to the TestObjectNames class which has been in the process of being re-factored out.
	 * Instead of using the TestObjectNames class to store the names and then using a custom returnTestObject() method, it is
	 * being replaced with the findTestObject() method from Katalon because Katalon will automatically update the objects path if changes
	 * in structure are made, but only if used with this method.  Is the path is stored in a string and used with findTestObject then
	 * it does not get updated which was the main reason why the original returnTestObject() method was created.
	 */

	def utils, driver, timeoutPeriod

	def googleLogoObjName = TestObjectNames.UpperSection.Customer.GoogleMap.GOOGLE_LOGO

	def cancelButtonObjName = TestObjectNames.UpperSection.Customer.GoogleMap.Buttons.CANCEL

	def googleMapIconObjName = TestObjectNames.UpperSection.Icons.MAP

	GoogleMap(){

		this.utils = new Utilities()

		this.driver = DriverFactory.getWebDriver()

		this.timeoutPeriod = new TimeDuration(0,0,10,0)
	}

	@Keyword
	def mapOpensAndClosesProperly(){

		def mapDisplays, mapCloses = false

		click(returnTestObject(googleMapIconObjName))

		mapDisplays = waitForGoogleMapToDisplay()

		if(mapDisplays){

			click(returnTestObject(cancelButtonObjName))

			mapCloses = waitForGoogleMapToClose()
		}

		mapDisplays && mapCloses
	}

	def waitForGoogleMapToClose(){

		def foundMaps

		def mapCloses = false

		def timeStart = new Date()

		def byLocator = By.cssSelector("#map")

		while (!mapCloses && (TimeCategory.minus(new Date(), timeStart) < this.timeoutPeriod)){

			foundMaps = findElements(byLocator)

			if(foundMaps.size() == 0){

				mapCloses = true
			}
		}

		KeywordUtil.logInfo("Map closed properly: " + mapCloses as String)

		mapCloses
	}

	def waitForGoogleMapToDisplay(){

		def mapCanvasIsShowing, addressNotFound = false

		def timeStart = new Date()

		while (!(mapCanvasIsShowing || addressNotFound) && (TimeCategory.minus(new Date(), timeStart) < this.timeoutPeriod)){

			mapCanvasIsShowing = doMapGridsDisplay()

			addressNotFound = doesAddressNotFoundDisplay()
		}

		KeywordUtil.logInfo("Map Canvas is Showing: " + mapCanvasIsShowing as String)

		KeywordUtil.logInfo("Address not found: " + addressNotFound as String)

		mapCanvasIsShowing || addressNotFound
	}

	def doMapGridsDisplay(){

		def byLocator = By.cssSelector(".gm-style > [tabindex] img")

		def minimumNumberOfExpectedImagesInCanvas = 4

		def mapCanvas = findElements(byLocator)

		def numberOfCanvasImagesFound = mapCanvas.size()

		KeywordUtil.logInfo("Map canvas images found: " + numberOfCanvasImagesFound as String)

		numberOfCanvasImagesFound >= minimumNumberOfExpectedImagesInCanvas
	}

	def doesAddressNotFoundDisplay(){

		getPageSource().contains("The address was not found by Google Maps.  Please correct the address and try again.")
	}
}
