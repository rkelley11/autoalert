package opportunities.alertdesk

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
import opportunities.alertdesk.dashboard.OpportunitiesDashboard.Widgets as OpportunitiesDashboardWidgets
import opportunities.alertdesk.dashboard.ManagerDashboard.Widgets as ManagerDashboardWidgets
import opportunities.alertdesk.Opportunities

import java.util.concurrent.TimeUnit

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class Dashboard extends WrappedSelenium {

	def academyLinkObjName = TestObjectNames.Dashboard.QuickLinks.ACADEMY

	def activitiesLinkObjName = TestObjectNames.Dashboard.QuickLinks.ACTIVITIES

	def fiManagerLinkObjName = TestObjectNames.Dashboard.QuickLinks.FIMANAGER

	def opportunitiesLinkObjName = TestObjectNames.Dashboard.QuickLinks.OPPORTUNITIES

	def preOwnedFindABuyerLinkObjName = TestObjectNames.Dashboard.QuickLinks.PREOWNED_FINDABUYER

	def searchLinkObjName = TestObjectNames.Dashboard.QuickLinks.SEARCH

	def serviceConquestLinkObjName = TestObjectNames.Dashboard.QuickLinks.SERVICE_CONQUESTS

	def serviceDriveLinkObjName = TestObjectNames.Dashboard.QuickLinks.SERVICE_DRIVE

	def serviceDriveOffersLinkObjName = TestObjectNames.Dashboard.QuickLinks.SERVICE_DRIVE_OFFERS

	def opportunitiesContainerObjName = TestObjectNames.Dashboard.OpportunitiesByAlert.CONTAINER

	def opps, driver

	Dashboard(){

		this.opps = new Opportunities()

		this.driver = DriverFactory.getWebDriver()
	}

	@Keyword
	def getAllActiveOpportunities(){

		def alertCount, attribute

		def listOfOpps = new ArrayList<WebElement>()

		def oppsContainer = returnTestObject(opportunitiesContainerObjName)

		def byLocator = By.tagName('opportunity-block')

		def allOpps = findElements(byLocator, oppsContainer)

		allOpps.each{

			try{

				attribute = (it.getAttribute("alert-count")).trim()

				KeywordUtil.logInfo("Alert count attribute: " + attribute + "\tLength: " + attribute.size() as String)

				if(attribute != ""){

					alertCount = attribute as Integer

					if(alertCount > 0){

						listOfOpps.add(it)
					}
				}
			}
			catch(Exception e){

				KeywordUtil.logInfo("Exception: " + e as String)
			}
		}

		listOfOpps
	}

	@Keyword
	def verifyRandomOppCountMatchesSearchCount(ArrayList<WebElement> listOfOpps){

		def random, selectedIndex, selectedOpp, displayedOppCount, totalFromSearch

		random = new Random()

		selectedIndex = random.nextInt(listOfOpps.size())

		selectedOpp = listOfOpps.get(selectedIndex)

		displayedOppCount = selectedOpp.getAttribute("alert-count") as Integer

		click(selectedOpp)

		totalFromSearch = opps.returnTotalFoundFromSearch()

		displayedOppCount == totalFromSearch
	}

	@Keyword
	def verifySelectedOppCountMatchesSearchCount(WebElement selectedOpp){

		def displayedOppCount, totalFromSearch

		displayedOppCount = selectedOpp.getAttribute("alert-count") as Integer

		click(selectedOpp)

		totalFromSearch = opps.returnTotalFoundFromSearch()

		this.driver.navigate().back()

		displayedOppCount == totalFromSearch
	}

	@Keyword
	def getSelectedOppAlertCount(WebElement selectedOpp){

		selectedOpp.getAttribute("alert-count") as Integer
	}

	def getSelectedOppObjName(String alertName){

		def selectedOppObjName

		switch (alertName){

			case "alert":

				selectedOppObjName = "Opportunity - Alert"

				break

			case "flexAlert":

				selectedOppObjName = "Opportunity - Flex"

				break

			case "inMarket":

				selectedOppObjName = "Opportunity - In-Market"

				break

			case "inMarketEngaged":

				selectedOppObjName = "Opportunity - Engaged"

				break

			case "service":

				selectedOppObjName = "Opportunity - Service"

				break

			case "appointment":

				selectedOppObjName = "Opportunity - Pending Service"

				break

			case "mileage":

				selectedOppObjName = "Opportunity - Mileage"

				break

			case "warranty":

				selectedOppObjName = "Opportunity - Warranty"

				break

			case "contractEnd":

				selectedOppObjName = "Opportunity - Contract End"

				break

			default:

				throw new Exception("Opportunity type unknown: " + alertName)

				break
		}

		selectedOppObjName
	}

	def widgetDisplays(String noInfoAvailableObjName, String tableBodyObjName){

		def widgetDisplays = false

		def byLocatorNoInfoAvailable = getByLocator(returnTestObject(noInfoAvailableObjName))

		def noInfoAvail = this.driver.findElements(byLocatorNoInfoAvailable)

		if(noInfoAvail.size() == 1){

			widgetDisplays = true
		}
		else{

			widgetDisplays = doesTestObjectContainRows(returnTestObject(tableBodyObjName))
		}

		widgetDisplays
	}

	def widgetDisplays(OpportunitiesDashboardWidgets widget){

		widgetDisplays(findWidget(widget))
	}

	def widgetDisplays(ManagerDashboardWidgets widget){

		widgetDisplays(findWidget(widget))
	}

	def widgetDisplays(WebElement widgetElement){

		def itemFound, message

		def widgetDisplaysCorrectly = false

		def loading = true

		def byLoading = By.cssSelector('.cg-busy-animation')

		def byTable = By.cssSelector('table')

		def byOther = By.cssSelector('.cg-busy-widget-container')

		def byNoInfo = By.cssSelector('span:not(.ng-binding):not(.cursor)')

		def noInfoMessage = 'No Information Available'

		while(loading){

			def elements = findElements(byLoading, widgetElement, 2)

			if(elements){

				def classes = getAttribute(elements[elements.size() - 1], "class")

				KeywordUtil.logInfo("Loading classes: " + classes)

				loading = !classes.contains("ng-hide")
			}
			else{

				loading = false
			}

			message = loading ? "Widget still refreshing..." : "Widget done refreshing"

			KeywordUtil.logInfo(message)

			if(loading) sleep(1000)
		}

		if(findElements(byTable, widgetElement, 1).size() > 0){

			widgetDisplaysCorrectly = true

			itemFound = "Table"
		}
		else{

			if(findElements(byOther, widgetElement, 1).size() > 0){

				widgetDisplaysCorrectly = true

				itemFound = "Other items"
			}
			else{

				def spanElements = findElements(byNoInfo, widgetElement, 1)

				if(spanElements){

					def spanText = getText(spanElements[0])

					if(spanText == noInfoMessage){

						widgetDisplaysCorrectly = true

						itemFound = noInfoMessage
					}
				}
			}
		}

		message = widgetDisplaysCorrectly ? "Widget displays correctly: " + itemFound + " found" : "Widget does not display correctly"

		KeywordUtil.logInfo(message)

		widgetDisplaysCorrectly
	}

	@Keyword
	def refreshWidget(WebElement widgetElement){

		def refreshIcon, successful

		def by = By.cssSelector('.panel-heading icon[title=\"Refresh contents\"]')

		def refreshIcons = findElements(by, widgetElement)

		if(refreshIcons){

			refreshIcon = refreshIcons[0]

			click(refreshIcon)

			sleep(1000)

			successful = widgetDisplays(widgetElement)
		}
		else{

			successful = false
		}

		KeywordUtil.logInfo("Widget refreshed successfully: " + successful as String)

		successful
	}

	def refreshWidget(OpportunitiesDashboardWidgets widget){

		refreshWidget(findWidget(widget)) // findWidget returns a WebElement which the main refreshWidget uses
	}

	def refreshWidget(ManagerDashboardWidgets widget){

		refreshWidget(findWidget(widget)) // findWidget returns a WebElement which the main refreshWidget uses
	}

	@Keyword
	def findWidget(widget){

		def byAllWidgets = getByAllWidgets(widget)

		/*def byAllWidgets = widget instanceof OpportunitiesDashboardWidgets ? By.cssSelector("dashboard-opportunities .panel") : By.cssSelector("dashboard-widget .panel")
		 KeywordUtil.logInfo("By All Widgets used: " + byAllWidgets as String)*/

		def widgetName = widget.getValue()

		KeywordUtil.logInfo("Widget name found to search: " + widgetName as String)

		findWidget(widgetName, byAllWidgets)
	}

	def getByAllWidgets(widget){

		def byAllWidgets

		def logHeader

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			byAllWidgets = widget instanceof OpportunitiesDashboardWidgets ? By.cssSelector("dashboard-opportunities .panel") : By.cssSelector("dashboard-widget .panel")

			KeywordUtil.logInfo(logHeader + "By All Widgets used: " + byAllWidgets as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		byAllWidgets
	}

	@Keyword
	def widgetExists(widget){

		def widgetFound = false

		def logHeader

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def byAllWidgets = getByAllWidgets(widget)

			def widgetTitle = widget.getValue()

			def widgetTitles = getWidgetTitles(byAllWidgets)

			for(int i = 0; i < widgetTitles.size(); i++){

				KeywordUtil.logInfo(logHeader + "Comparing title \'" + widgetTitles[i] + "\' to desired title \'" + widgetTitle + "\'")

				if(widgetTitles[i] == widgetTitle){

					widgetFound = true

					break
				}
			}

			KeywordUtil.logInfo("Widget '" + widgetTitle + "' was" + (widgetFound ? " " : " NOT ") + "found")
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		widgetFound
	}

	def getWidgetTitles(By by){

		def widgetTitles = []

		def waitingForTitlesToAppear = true

		def attempts = 0

		def logHeader

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			while(waitingForTitlesToAppear && attempts < 5){

				attempts += 1

				KeywordUtil.logInfo(logHeader + 'Attempt #' + (attempts as String))

				def allWidgets = findElements(by, 5)

				if(allWidgets){

					for(int i = 0; i < allWidgets.size(); i++){

						def byHeaders = By.cssSelector('.panel-heading span:not(.cursor)')

						def possibleHeaderTitles = findElements(byHeaders, allWidgets[i])

						for (int ii = 0; ii < possibleHeaderTitles.size(); ii++){

							def elementText = getText(possibleHeaderTitles[ii])

							KeywordUtil.logInfo("Widget title found: " + elementText as String)

							if(ii == 0 && elementText == ''){

								break
							}
							else{

								waitingForTitlesToAppear = false

								widgetTitles.add(elementText)
							}
						}
					}
				}
			}

			KeywordUtil.logInfo(logHeader + 'Widget titles found: ' + (widgetTitles as String))
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		widgetTitles
	}

	def findWidget(String widgetTitle, By byAllWidgets){

		def foundWidget, logHeader

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def byHeaders = By.cssSelector('.panel-heading span:not(.cursor)')

			def allWidgets = findElements(byAllWidgets, 5)

			for(int i = 0; i < allWidgets.size(); i++){

				def possibleHeaderTitles = findElements(byHeaders, allWidgets[i])

				for (int ii = 0; ii < possibleHeaderTitles.size(); ii++){

					def elementText = getText(possibleHeaderTitles[ii])

					KeywordUtil.logInfo("Element text found: " + elementText as String)

					if(elementText == widgetTitle){

						foundWidget = allWidgets[i]

						break
					}
				}

				if (foundWidget) break
			}

			def message = foundWidget ? "Found" : "Not Found"

			KeywordUtil.logInfo("Widget '" + widgetTitle + "': " + message)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		foundWidget
	}

	@Keyword
	def clickWidgetRow(widget, column, desiredValue){

		def desiredRow, desiredCell, successful, message

		def messageColumn = column ? column as String : "any"

		def messageValue = desiredValue ? desiredValue as String : "any value"

		message = (widget as String) + " - Successfully clicked on a row with " + messageValue + " in " + messageColumn + " column: "

		try{

			def columnIndex, cells, cellText, cellTextLower

			def foundWidget = widget ? findWidget(widget) : null

			def table = foundWidget ? getWidgetTableBody(foundWidget) : null

			def rows = table ? getRowsFromTableBody(table) : null

			if(rows){

				columnIndex = column ? column.getValue() : 0

				for(int i = 0 ; i < rows.size() ; i++){

					cells = getCellsFromRow(rows[i])

					KeywordUtil.logInfo("Looking at row " + (i + 1) as String)

					if(desiredValue){

						KeywordUtil.logInfo("Column index: " + columnIndex as String)

						if(columnIndex instanceof Integer){

							KeywordUtil.logInfo("Column index is detected as an integer")

							cellText = getText(cells[columnIndex]).trim()

							cellTextLower = cellText ? cellText.toLowerCase() : null

							KeywordUtil.logInfo("Cell text in lower case: " + cellTextLower as String)

							if(cellTextLower == desiredValue.toLowerCase()){

								KeywordUtil.logInfo("Desired value, " + desiredValue + ", found!")

								desiredRow = rows[i]

								desiredCell = cells[columnIndex]

								break
							}
						}
						else{

							KeywordUtil.logInfo("Column index is NOT detected as an integer")

							for(int ii = 0 ; ii < cells.size() ; ii++){

								cellText = getText(cells[ii]).trim()

								cellTextLower = cellText ? cellText.toLowerCase() : null

								KeywordUtil.logInfo("Cell text in lower case: " + cellTextLower as String)

								if(cellTextLower == desiredValue.toLowerCase()){

									KeywordUtil.logInfo("Desired value found!")

									desiredRow = rows[i]

									desiredCell = cells[ii]

									break
								}
							}
						}
					}
					else{

						desiredRow = rows[i]

						desiredCell = cells[columnIndex]

						KeywordUtil.logInfo("Desired row: " + desiredRow as String)

						KeywordUtil.logInfo("Desired cell: " + desiredCell as String)
					}

					if(desiredRow || desiredCell) break
				}
			}

			if(desiredRow){

				def clickAttribute = getAttribute(desiredRow, "ng-click")

				KeywordUtil.logInfo("Choosing to click " + (clickAttribute ? "desiredRow" : "desiredCell"))

				def desiredElement = clickAttribute ? desiredRow : desiredCell

				successful = click(desiredElement)
			}
			else{

				successful = false
			}
		}
		catch(Exception e){

			KeywordUtil.markWarning(e.getMessage())
		}

		KeywordUtil.logInfo(message + successful as String)

		successful
	}

	def getWidgetTableBody(WebElement widget){

		def table

		try{

			def tables = findElements(By.cssSelector("tbody"), widget)

			table = tables[0]
		}
		catch (Exception e){
		}

		table
	}

	def getRowsFromTableBody(WebElement tableBody){

		def rows

		try{

			rows = findElements(By.cssSelector("tr"), tableBody)
		}
		catch (Exception e){
		}

		rows
	}

	def getCellsFromRow(WebElement row){

		def cells

		try{

			cells = findElements(By.cssSelector("td"), row)
		}
		catch (Exception e){
		}

		cells
	}
}
