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

import reports.TestObjectNames as ReportsTOs

public class Enums {

	enum Switch{
		OPEN(['name':'Open', 'description': 'Open']),
		CLOSE(['name':'Close', 'description': 'Closed']),

		def getValue

		Switch(LinkedHashMap getValue){

			this.getValue = getValue
		}

		def getValue(){

			return this.getValue
		}
	}

	enum Sort{
		ASCENDING,
		DESCENDING
	}

	enum Alerts{
		ALERT(['letter':'A', 'description':'Alert']),
		FLEX(['letter':'F', 'description':'Flex']),
		IN_MARKET(['letter':'I', 'description':'In-Market']),
		ENGAGED(['letter':'E', 'description':'Engaged']),
		SERVICE(['letter':'S', 'description':'Service']),
		PENDING_SERVICE(['letter':'P', 'description':'Pending Service']),
		MILEAGE(['letter':'M', 'description':'Mileage']),
		WARRANTY(['letter':'W', 'description':'Warranty']),
		CONTRACT_END(['letter':'C', 'description':'Contract End'])

		def getValue

		Alerts(LinkedHashMap getValue){

			this.getValue = getValue
		}

		def getValue(){

			return this.getValue
		}
	}

	static class Reports{

		enum Toggleable{
			MENU(['menu':ReportsTOs.Menu.WINDOW, 'toggle':ReportsTOs.Menu.ICON, 'title':'Reports']),
			SETTINGS_PANEL(['menu':ReportsTOs.SettingsPanel.CONTAINER, 'toggle':ReportsTOs.SettingsPanel.ICON, 'title':'Settings Panel'])

			def getValue

			Toggleable(LinkedHashMap getValue){

				this.getValue = getValue
			}

			def getValue(){

				return this.getValue
			}
		}

		enum Dropdown{
			DEALERSHIP(['testObject':ReportsTOs.Report.Dropdown.DEALERSHIP]),
			MAKE(['testObject':ReportsTOs.Report.Dropdown.MAKE])

			def getValue

			Dropdown(LinkedHashMap getValue){

				this.getValue = getValue
			}

			def getValue(){

				return this.getValue
			}
		}
	}
}
