package opportunities.alertdesk.dashboard.opportunities

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
import general.Enums.Alerts
import dealsheet.Common
import dealsheet.bottom.*
import dealsheet.bottom.Common as BottomCommon
import dealsheet.bottom.Common.BottomSectionTabs2 as BottomSectionTabs2
import opportunities.alertdesk.Dashboard
import opportunities.alertdesk.dashboard.OpportunitiesDashboard.Widgets as OpportunitiesDashboardWidgets

import sql.*
import groovy.time.*
import java.util.concurrent.TimeUnit

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class MyAgenda extends WrappedSelenium{

	def tableBodyObjName = TestObjectNames.MyAgenda.TABLE_BODY

	@Keyword
	def returnRowDetails(String clientName){

		def logHeader

		def rowDetails

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def desiredRow = returnRow(clientName)

			if(desiredRow){

				rowDetails = [:]

				rowDetails['row'] = desiredRow

				def by = By.cssSelector('td')

				def cells = findElements(by, desiredRow)

				if(cells){

					def numberOfCells = cells.size()

					if(numberOfCells == 5){

						for(int i = 0 ; i < numberOfCells ; i++){

							def cellText = getText(cells[i])

							if(cellText){

								rowDetails[i as String] = cellText
							}
							else{

								def iconType = new Icons().getIconType(cells[i])

								if(iconType){

									rowDetails['icon'] = iconType
								}
								else{

									rowDetails['icon'] = 'Not found'

									KeywordUtil.markWarning(logHeader + 'No text or icon information found for cell ' + i as String)
								}
							}
						}
					}
					else{

						throw new Exception(logHeader + 'Expecting 5 cells and found ' + (numberOfCells as String))
					}
				}
			}

			KeywordUtil.logInfo(logHeader + 'Row cell details: ' + (rowDetails as String))
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		rowDetails
	}

	def returnRow(String clientName){

		def logHeader

		def desiredRow

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def rows = returnAllMyAgendaRows()

			for(int i = 0 ; i < rows.size() ; i++){

				def foundRow = isDesiredRow(rows[i], clientName)

				if(foundRow){

					desiredRow = rows[i]

					break
				}
			}

			KeywordUtil.logInfo(logHeader + "Search WAS" + (desiredRow != null ? " " : " NOT ") + "successful for " + clientName)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		desiredRow
	}

	def returnAllMyAgendaRows(){

		def logHeader

		def rows

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def testObject = returnTestObject(tableBodyObjName)

			def tables = new Tables()

			rows = tables.returnRows(testObject)

			def message

			if(rows){

				message = rows.size() as String
			}
			else{

				message = 'None'
			}

			KeywordUtil.logInfo(logHeader + "My Agenda rows found: " + message)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		rows
	}

	def isDesiredRow(WebElement element, String clientName){

		def logHeader

		def desiredRow = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def by = By.cssSelector('td')

			def cells = findElements(by, element)

			if(cells){

				def foundClientName = getText(cells[3])

				desiredRow = clientName.toLowerCase() == foundClientName.toLowerCase()
			}

			KeywordUtil.logInfo(logHeader + clientName + " WAS" + (desiredRow ? " " : " NOT ") + "found")
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		desiredRow
	}

	@Keyword
	def clearMyAgenda(){

		def logHeader, driver

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def TestObjectUnderTest = findTestObject('Object Repository/Opportunities/Alert Desk/Dashboard/Opportunities Dashboard/My Agenda/Opps Dash - My Agenda - View All')

			click(TestObjectUnderTest)

			driver = DriverFactory.getWebDriver()

			driver.switchTo().frame('autoalertiframe')

			def by = By.cssSelector('a[title="Cancel"]')

			def appts2Cancel = findElements(by, 10)

			if(appts2Cancel){

				for(int i = 0 ; i < appts2Cancel.size() ; i++){

					click(appts2Cancel[i])

					def by2 = By.cssSelector(".authenticated > [tabindex='-1']:nth-child(14) > div:nth-child(11) .ui-dialog-buttonset > [role='button']:nth-child(1)")

					click(findElement(by2))

					//WebUI.click(findTestObject('Object Repository/Opportunities/Alert Desk/Activities/Agenda/Modal/Opportunities - Alert Desk - Activities - Agenda - Appts - Modal - Buttons - Ok'))
				}
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		driver.switchTo().defaultContent()

		successful
	}

	@Keyword
	def clearMyAgendaOLD(){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def rows = returnAllMyAgendaRows()

			if(rows){

				cancelScheduledItem(rows[0])

				// Go to home page
				WebUI.navigateToUrl(GlobalVariable.Url_Login)

				// Click on the refresh button 2x (possible issue) for My Agenda
				def dash = new Dashboard()

				dash.refreshWidget(OpportunitiesDashboardWidgets.MY_AGENDA)

				dash.refreshWidget(OpportunitiesDashboardWidgets.MY_AGENDA)

				// See if there are more rows
				rows = returnAllMyAgendaRows()

				if(rows){

					KeywordUtil.logInfo(logHeader + 'Continuing to clear rows in My Agenda')

					clearMyAgenda()
				}
				else{

					successful = true

					KeywordUtil.logInfo(logHeader + 'Clearing My Agenda WAS successful')
				}
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	@Keyword
	def cancelScheduledItem(WebElement row){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			// Re-open dealsheet from My Agenda
			def reopeningDealsheetClicked = click(row)

			if(!reopeningDealsheetClicked) throw new Exception('Unable to click on dealsheet')

			// Wait for dealsheet to update
			def dealsheetLoaded = new Common().waitToLoad()

			if(!dealsheetLoaded) throw new Exception('Unable to load dealsheet')

			// Make Activity & Notes section active & prepare expected results
			def openTab = new BottomCommon().openTab(BottomSectionTabs2.ACTIVITY_AND_NOTES)

			if(!openTab) throw new Exception('Unable to open Activity & Notes section')

			def activityAndNotes = new ActivityAndNotes()

			successful = activityAndNotes.cancelAllUpcomingAppointments()

			/*// Click Cancel Appointment link
			 def cancelLink = findTestObject('Dealsheet/Tabs/Activity and Notes/Upcoming Appointments/Links/Dealsheet - Tabs - Activity and Notes - Upcoming Appts - Links - Cancel')
			 def cancelLinkVisible = WebUI.waitForElementVisible(cancelLink, 10)
			 if(!cancelLinkVisible) throw new Exception('Unable to find Cancel link')
			 def cancelLinkClicked = click(cancelLink)
			 if(!cancelLinkClicked) throw new Exception('Unable to click Cancel link')
			 // Click Submit button on Popup
			 def popupSubmitButton = findTestObject('Object Repository/Dealsheet/Modals/Post Notes/Buttons/Dealsheet - Modals - Post Notes - Buttons - Submit')
			 def popupSubmitButtonVisible = WebUI.waitForElementVisible(popupSubmitButton, 10)
			 if(!popupSubmitButtonVisible) throw new Exception('Unable to find Submit button')
			 def popupSubmitButtonClicked = click(popupSubmitButton)
			 if(!popupSubmitButtonClicked) throw new Exception('Unable to click Submit button')*/

			KeywordUtil.logInfo(logHeader + 'Clearing My Agenda WAS' + (successful ? ' ' : ' NOT ') + 'successful')
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}
}
