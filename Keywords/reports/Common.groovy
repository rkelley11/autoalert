package reports

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

import com.kms.katalon.core.util.KeywordUtil

import general.*
import general.Enums.*
import opportunities.DealerSelect

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

public class Common extends WrappedSelenium{

	//def overlays

	def byLoading = By.cssSelector("reports .reports-container .cg-busy-backdrop")

	def dashboardSectionHeaderObjName = TestObjectNames.Menu.Dashboard.HEADER

	def opportunitiesSectionHeaderObjName = TestObjectNames.Menu.Opportunities.HEADER

	def pandoSectionHeaderObjName = TestObjectNames.Menu.Pando.HEADER

	def autoAssistantSectionHeaderObjName = TestObjectNames.Menu.AutoAssistant.HEADER

	def geoAlertSectionHeaderObjName = TestObjectNames.Menu.GeoAlert.HEADER

	/**
	 * 
	 * @return returns true if item's menu is open and visible, false is collapsed
	 */
	@Keyword
	def refresh(){

		def logHeader

		def refreshSuccessful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + "Report refresh starting.....")

			def refreshIcon = returnTestObject(TestObjectNames.Report.REFRESH_ICON)

			refreshSuccessful = click(refreshIcon)

			KeywordUtil.logInfo(logHeader + "Clicking refresh button was" + (refreshSuccessful ? " " : " NOT ") + "successful")

			if (refreshSuccessful){

				//refreshSuccessful = this.overlays.waitForOverlayToClear(this.byLoading)

				refreshSuccessful = new Overlays().waitForOverlayToClear(this.byLoading)
			}
		}
		catch(Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		KeywordUtil.logInfo(logHeader + "Report refresh was" + (refreshSuccessful ? " " : " NOT ") + "successful")

		refreshSuccessful
	}

	/**
	 * 
	 * @return returns true if item's menu is open and visible, false is collapsed
	 */
	@Keyword
	def shouldPerformanceReportBeAvailable(){

		def logHeader

		def shouldBeAvailable = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def firstDayOfReportAvailability = 10

			def currentDayOfTheMonth = LocalDateTime.now().getDayOfMonth()

			shouldBeAvailable = currentDayOfTheMonth >= firstDayOfReportAvailability

			KeywordUtil.logInfo("Current day of the month: " + currentDayOfTheMonth as String)

			KeywordUtil.logInfo("Performance Report SHOULD" + (shouldBeAvailable ? " " : " NOT ") + "be available")
		}
		catch(Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		shouldBeAvailable
	}

	/**
	 * 
	 * @return returns true if dealership dropdown is correct, false if not
	 */
	@Keyword
	def isDealershipDropdownCorrect(){

		def logHeader

		def isCorrect = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def actualOptions = (getAllOptions(returnTestObject(TestObjectNames.Report.Dropdown.DEALERSHIP))).sort()

			def expectedOptions = determineExpectedDealerOptions()

			KeywordUtil.logInfo(logHeader + "Actual options:\t" + actualOptions as String)

			KeywordUtil.logInfo(logHeader + "Expected options:\t" + expectedOptions as String)

			isCorrect = actualOptions == expectedOptions

			KeywordUtil.logInfo(logHeader + "Actual & Expected options ARE" + (isCorrect ? " " : " NOT ") + "the same")
		}
		catch(Exception e){

			KeywordUtil.markWarning(e.getMessage())
		}

		isCorrect
	}

	/**
	 *
	 * @return returns a sorted list of the dealers currently selected from the home page
	 */
	def determineExpectedDealerOptions(){

		def logHeader

		def expectedOptions = []

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def currentlySelectedDealers = new DealerSelect().getDealerList().sort()

			if(currentlySelectedDealers.size() > 1){

				expectedOptions.add('All Dealerships')

				for (int i = 0 ; i < currentlySelectedDealers.size() ; i++){

					expectedOptions.add(currentlySelectedDealers[i])
				}
			}
			else{

				expectedOptions = currentlySelectedDealers
			}
		}
		catch(Exception e){

			KeywordUtil.markWarning(e.getMessage())
		}

		expectedOptions
	}

	/**
	 * 
	 * @return returns true if report section is open, false if not
	 */
	@Keyword
	def isSectionOpen(TestObject testObject){

		def logHeader

		def isOpen = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + "Attempting to see if report section is open")

			def classes = getAttribute(testObject, 'class')

			isOpen = !classes.contains('ng-hide')

			KeywordUtil.logInfo(logHeader + "Section IS" + (isOpen ? " " : " NOT ") + "open")
		}
		catch(Exception e){

			KeywordUtil.markWarning(e.getMessage())
		}

		isOpen
	}

	/**
	 * 
	 * @param section - Use ReportSections enum to select which report section to toggle
	 * @param toggle - Use Toggle enum to choose whether to expand or collapse the selected section
	 * @return returns true if toggle is successful
	 */
	def toggleSection(ReportSections section, Enums.Switch toggle){

		def sectionObjName, sectionBy, currentToggle, foundItems

		def chevron, elementIsVisible

		def successfulToggle = false

		def logHeader

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def bySectionOpportunities = By.cssSelector("[ng-if*=\"\$ctrl.opportunitiesMenuVisible\"]")

			def bySectionPando = By.cssSelector("[ng-if*=\"\$ctrl.communicationMenuVisible\"]")

			def bySectionAutoAssistant = By.cssSelector("[ng-if*=\"\$ctrl.autoAssistantMenuVisible\"]")

			def bySectionGeoAlert = By.cssSelector("[ng-if*=\"\$ctrl.geoAlertMenuVisible\"]")

			KeywordUtil.logInfo(logHeader + "Attempting to set Section: " + (section as String) + ", Toggle: " + toggle as String)

			switch (section){

				case ReportSections.DASHBOARD:
				
					sectionObjName = dashboardSectionHeaderObjName

					sectionBy = null

					break

				case ReportSections.OPPORTUNITIES:

					sectionObjName = opportunitiesSectionHeaderObjName

					sectionBy = bySectionOpportunities

					break

				/*case ReportSections.ONE2ONE:
				 sectionObjName = one2oneSectionHeaderObjName
				 sectionBy = bySectionOne2One
				 break*/

				case ReportSections.PANDO:

					sectionObjName = pandoSectionHeaderObjName

					sectionBy = bySectionPando

					break

				case ReportSections.AUTOASSISTANT:

					sectionObjName = autoAssistantSectionHeaderObjName

					sectionBy = bySectionAutoAssistant

					break

				case ReportSections.GEOALERT:

					sectionObjName = geoAlertSectionHeaderObjName

					sectionBy = bySectionGeoAlert

					break

				default:

					throw new Exception("Report section not found: " + section as String)

					break
			}

			successfulToggle = toggleChevron(sectionBy, toggle)
		}
		catch(Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successfulToggle
	}

	/**
	 * 
	 * @param testObject - Use the desired TestObject
	 * @param toggle - Use Switch enum to choose whether to expand or collapse the selected section
	 * @return returns true if toggle was successful
	 */
	@Keyword
	def toggleChevron(TestObject testObject, Enums.Switch toggle){

		def logHeader

		def successfulToggle = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def by = getByLocator(testObject)

			successfulToggle = toggleChevron(by, toggle)
		}
		catch (Exception e) {

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successfulToggle
	}

	/**
	 *
	 * @param by - the selenium selector By used to find elements(s)
	 * @param toggle - Use Switch enum to choose whether to expand or collapse the selected section
	 * @return returns true if toggle was successful
	 */
	def toggleChevron(By chevronBy, Enums.Switch toggle){

		def logHeader

		def successfulToggle = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def currentToggle

			KeywordUtil.logInfo(logHeader + "Attempting to locate the sections chevron")

			def chevron = findElements(chevronBy)

			if(chevron){

				currentToggle = currentChevron(chevron[0])

				/*currentToggle = getAttribute(chevron[0], 'class').contains('aa-icon-chevron-down') ? Switch.OPEN : Switch.CLOSE
				 KeywordUtil.logInfo(logHeader + "Section currently: " + currentToggle.getValue()['description'].toUpperCase() as String)*/

				if (!toggle.equals(currentToggle)){

					successfulToggle = click(chevron[0])
				}
				else{

					successfulToggle = true
				}
			}
			else{

				KeywordUtil.logInfo(logHeader + "Chevron to toggle section was not found")
			}
		}
		catch (Exception e) {

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successfulToggle
	}

	/**
	 * 
	 * @param element - Pass the web element to be checked
	 * @return the Switch enum representing the current status
	 */
	@Keyword
	def getCurrentChevronStatus(WebElement element){

		currentChevron(element)
	}

	/**
	 * 
	 * @param testObject - Pass the TestObject to be checked
	 * @return the Switch enum representing the current status
	 */
	def getCurrentChevronStatus(TestObject testObject){

		currentChevron(testObject)
	}

	/**
	 * 
	 * @param thing1 - a catchall to pass either the web element or TestObject to be checked
	 * @return the Switch enum representing the current status
	 */
	def currentChevron(thing1){

		def logHeader, chevronSwitch

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def classes = getAttribute(thing1, 'class')

			KeywordUtil.logInfo(logHeader + "Chevron classes: " + classes as String)

			chevronSwitch = classes.contains('aa-icon-chevron-down') ? Switch.OPEN : Switch.CLOSE

			KeywordUtil.logInfo(logHeader + "Section currently: " + chevronSwitch.getValue()['description'].toUpperCase() as String)
		}
		catch (Exception e) {

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		chevronSwitch
	}

	enum ReportSections{
		DASHBOARD,
		OPPORTUNITIES,
		ONE2ONE,
		PANDO,
		AUTOASSISTANT,
		GEOALERT
	}
}
