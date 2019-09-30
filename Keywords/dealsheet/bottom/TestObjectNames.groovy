package dealsheet.bottom

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

final class TestObjectNames {

	static class Tabs{

		static String ALERTS_AND_SCRIPTS = 'Dealsheet - Tabs - Alerts and Scripts - Tab'

		static String EXISTING_VEHICLE_DETAILS = 'Dealsheet - Tabs - Existing Vehicle Details - Tab'

		static String REPLACEMENT_DETAILS = 'Dealsheet - Tabs - Replacement Details - Tab'

		static String FEATURE_COMPARISON = 'Dealsheet - Tabs - Feature Comparison - Tab'

		static String ACTIVITY_AND_NOTES = 'Dealsheet - Tabs - Activity and Notes - Tab'

		static String SERVICE_HISTORY = 'Dealsheet - Tabs - Service History - Tab'

		static String OTHER_VEHICLES_OWNED = 'Dealsheet - Tabs - Other Vehicles Owned - Tab'

		static String DEAL_HISTORY = 'Dealsheet - Tabs - Deal History - Tab'

		static String CHANGE_LOG = 'Dealsheet - Tabs - Change Log - Tab'
	}

	static class ActivityAndNotes{

		static class Filters{

			static String CONTAINER = 'Dealsheet - Tabs - Activity and Notes - Activity Filters - Container'
		}
	}

	static class AlertsAndScripts{

		static class Opportunities{

			static String HEADING = 'Dealsheet - Tabs - Alerts and Scripts - Opportunities - Heading'
		}

		static class Scripts{

			static String HEADING = 'Dealsheet - Tabs - Alerts and Scripts - Scripts - Heading'
		}
	}

	static class ReplacementDetails{

		static String HEADING = 'Dealsheet - Tabs - Replacement Details - Heading'

		static class Buttons{

			static String IN_STOCK_MATCH = 'Dealsheet - Tabs - Replacement Details - Buttons - In-Stock Match'
		}

		static class InStockMatch{

			static String HEADING = 'Dealsheet - Tabs - Replacement Details - In-Stock Match - Heading'
		}

		static class PaymentGrid{

			static String TABLE_BODY = 'Dealsheet - Tabs - Replacement Details - Table Body - Payment Grid'
		}
	}
}