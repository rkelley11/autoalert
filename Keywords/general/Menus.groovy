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

import java.time.*
import java.util.LinkedHashMap

import com.kms.katalon.core.util.KeywordUtil

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import general.Enums.*

public class Menus extends WrappedSelenium{

	/**
	 * 
	 * @return returns true if item's menu is open and visible, false is collapsed
	 */
	@Keyword
	def isOpen(menu){

		def menuIsOpen

		try{

			KeywordUtil.logInfo("Menu received: " + menu as String)

			menuIsOpen = isOpen(menu.getValue())
		}
		catch(Exception e){

			KeywordUtil.markWarning(e.getMessage())

			menuIsOpen = false
		}

		menuIsOpen
	}

	/**
	 * 
	 * @return returns true if item's menu is open and visible, false is collapsed
	 */
	def isOpen(LinkedHashMap menuObject){

		def menuIsOpen

		try{

			KeywordUtil.logInfo("Checking to see if the " + menuObject['title'].toUpperCase() +
					" menu is currently " + Enums.Switch.OPEN.getValue()['description'].toUpperCase())

			def menuWindow = returnTestObject(menuObject['menu'])

			def classes = getAttribute(menuWindow, 'class')

			KeywordUtil.logInfo(menuObject['title'].toUpperCase() + " menu classes: " + classes)

			menuIsOpen = !classes.contains('ng-hide')
		}
		catch(Exception e){

			KeywordUtil.markWarning(e.getMessage())

			menuIsOpen = false
		}

		KeywordUtil.logInfo(menuObject['title'].toUpperCase() + " menu is " +
				(menuIsOpen ? Enums.Switch.OPEN.getValue()['description'].toUpperCase() : Enums.Switch.CLOSE.getValue()['description'].toUpperCase()))

		menuIsOpen
	}

	/**
	 * 
	 * @param item - Use ToggleItems enum to choose the item to be toggled
	 * @param toggle - Use Toggle enum to choose whether to expand or collapse the menu
	 * @return true if successful, false if not
	 */
	@Keyword
	def toggle(menu, toggle){

		def successful, menuObject, toggleObject

		try{

			menuObject = menu.getValue()

			toggleObject = toggle.getValue()

			KeywordUtil.logInfo("Menu received : " + (menu as String) + ", Value: " + menuObject as String)

			KeywordUtil.logInfo("Toggle desired: " + (toggle as String) + ", Value: " + toggleObject as String)

			successful = asdasd(menuObject, toggleObject)
		}
		catch(Exception e){

			KeywordUtil.markWarning(e.getMessage())

			successful = false
		}

		successful
	}

	/**
	 * 
	 * @param item - Use ToggleItems enum to choose the item to be toggled
	 * @param toggle - Use Toggle enum to choose whether to expand or collapse the menu
	 * @return true if successful, false if not
	 */
	def asdasd(LinkedHashMap menuObject, LinkedHashMap toggleObject){

		def successful, currentlyOpen

		try{

			KeywordUtil.logInfo("Attempting to " + toggleObject['name'].toUpperCase() + " the " + menuObject['title'].toUpperCase() + " menu")

			currentlyOpen = isOpen(menuObject)

			if((toggleObject['name'] == "Open" && !currentlyOpen) || (toggleObject['name'] == "Close" && currentlyOpen)){

				def toggleIcon = returnTestObject(menuObject['toggle'])

				click(toggleIcon)

				currentlyOpen = isOpen(menuObject)

				successful = ((toggleObject['name'] == "Open" && currentlyOpen) || (toggleObject['name'] == "Close" && !currentlyOpen))

				KeywordUtil.logInfo(menuObject['title'].toUpperCase() + " menu was" +
						(successful ? " " : " NOT ") + "successfully " + toggleObject['description'].toUpperCase())
			}
			else{

				successful = true

				KeywordUtil.logInfo(menuObject['title'].toUpperCase() + " menu was already " + toggleObject['description'].toUpperCase())
			}
		}
		catch(Exception e){

			KeywordUtil.markWarning(e.getMessage())

			successful = false
		}

		successful
	}
}
