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

final class TestObjectNames{

	static class General{

		static String BUSY_BACKGROUND = 'Busy Backdrop iFrame'
	}

	static class Housekeeping{

		static class Dialog{

			static String OK = 'Announcement Dialog Ok Button'
		}
		
		static class ChangeMgmtLiveWebinar{
		
			static class Buttons{
	
				static String CANCEL = 'Dialog - Change Mgmt Live Webinar - Buttons - Cancel'
			}
		}
	}

	static class Katalon{

		static String OBJECT_REPO_DIRECTORY_NAME = 'Object Repository'
	}

	static class Navigation{

		static String AUTOALERT_LOGO = 'AutoAlert Logo Image'
	}

	static class Opportunities{

		static String BODY_AA_LEGACY = 'Opportunities - Body - aa-legacy'
	}
}
