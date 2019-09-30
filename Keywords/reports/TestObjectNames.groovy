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

final class TestObjectNames {

	static class Menu{

		static String ICON = 'Reports - Common - Icon - Menu Toggle'

		static String WINDOW = 'Reports - Common - Menu'

		static String PERFORMANCE_REPORT = 'Reports - Common - Menu - Performance Report'

		static class Dashboard{

			static String HEADER = 'Reports Dashboard Section Header'
		}

		static class Opportunities{

			static String HEADER = 'Reports Opportunities Section Header'
		}

		static class AutoAssistant{

			static String HEADER = 'Reports - AutoAssistant - Menu Header'
		}

		static class GeoAlert{

			static String HEADER = 'Reports - GeoAlert - Menu Header'
		}

		static class Pando{

			static String HEADER = 'Reports Pando Section Header'
		}
	}

	static class Report{

		static String REFRESH_ICON = 'Reports - Common - Icon - Refresh'

		static class Dropdown{

			static String DEALERSHIP = 'Reports - Common - Dropdown - Dealerships'

			static String MAKE = 'Reports - Common - Dropdown - Make'
		}

		static class MonthlyPerformance{

			static String LINK = 'Reports - Monthly Performance - Link'

			static String TITLE1 = 'Reports - Monthly Performance - Title - 1'

			static String TITLE2 = 'Reports - Monthly Performance - Title - 2'

			static String DATE_DROPDOWN = 'Reports - Monthly Performance - Dropdown - Date'

			static class OneToOne{

				static String LEARN_MORE = 'Reports - MP - O2O - Link - Learn More'
			}

			static class ReputationPro{

				static String LEARN_MORE = 'Reports - MP - Rep Pro - Link - Learn More'
			}
		}
	}

	static class SettingsPanel{

		static String ICON = 'Reports - Common - Icon - Settings Panel'

		static String CONTAINER = 'Reports - Common - Menu - Settings Panel'
	}
}
