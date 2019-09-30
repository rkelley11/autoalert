package opportunities

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

import org.openqa.selenium.*

final class TestObjectNames{

	static class DealerSelect{

		static class Button{

			static String SUBMIT = 'Dealer Select - Button - Submit'

			static String CANCEL = 'Dealer Select - Button - Cancel'
		}

		static String DEALER_SELECT_LINK = 'Dealer Select - Link'

		static String REMOVE_ALL_LINK = 'Dealer Select - Remove All Link'

		static String TEXTBOX = 'Dealer Select - Input Textbox'
	}

	static class LiveSupport{

		static String LINK = 'Live Support Link'

		static String START_CHAT_BUTTON = 'Start Chat Button'
	}

	static class PandoAvailability{

		static String DROPDOWN = 'Pando Availability Dropdown'

		static String ICON = 'Pando Availability Icon'

		static String CANCEL_BUTTON = 'Pando Availability Button - Cancel'

		static String UPDATE_BUTTON = 'Pando Availability Button - Update'

		static String NOT_AVAILABLE_OPTION = 'Pando Availability Dropdown - Not Available Option'

		static String BUSY_OPTION = 'Pando Availability Dropdown - Busy Option'

		static String BUSY_FOR = 'Pando Availability Textbox - Busy For'

		static String POPUP = 'Pando Availability Popup'
	}

	static class PandoChat{

		static String ICON = "Pando Chat Icon"

		static String LOGO = "Pando Inbox Logo"

		static String TITLE = "Pando"
	}

	static class RecentlyViewedDealSheets{

		static String ICON = "Recently Viewed Deal Sheets Icon"
	}

	static class StockSearch{

		static String PANDO_PROFILE_CONTAINER = 'Pando Profile Container'

		static String PANDO_PROFILE_CUSTOMER_NAME = 'Pando Profile Customer Name Header'
	}
}
