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

public class OpportunitySearches extends WrappedSelenium{

	@Keyword
	def clickAvailableColumnValue(OppSearchColumn column){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def rowsBy = By.cssSelector('search-preset-counts table tr.ng-star-inserted')

			def rows = findElements(rowsBy)

			def message = 'No clickable values found for ' + column.name() + ' column'

			if(rows){

				def cellsBy = By.cssSelector('td .count,.nocount')

				def columnIndex = column.getValue()['column']

				for(int i = 0 ; i < rows.size() ; i++){

					def cells = findElements(cellsBy, rows[i])

					if(cells){

						def uisref = getAttribute(cells[columnIndex], 'uisref')

						if(uisref){

							def cellValue = getText(cells[columnIndex])

							KeywordUtil.logInfo(logHeader + 'Attempting to click the value ' + (cellValue as String) + ' in cell ' + (columnIndex as String))

							click(cells[columnIndex])

							message = 'Clickable value for ' + column.name() + ' column found in row ' + (i as String)

							successful = true

							break
						}
					}
				}
			}
			else{

				throw new Exception('No rows found')
			}

			KeywordUtil.logInfo(logHeader + message)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	enum OppSearchColumn{
		TOTAL(['column':0]),
		WORKED(['column':1]),
		NOT_WORKED(['column':2])

		def getValue

		OppSearchColumn(LinkedHashMap getValue){

			this.getValue = getValue
		}

		def getValue(){

			return this.getValue
		}
	}
}
