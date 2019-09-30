package logactivity

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

final class TestObjects {

	static class LogActivity{

		static TestObject TAB = findTestObject('Dealsheet/Log Activity/Log Activity/Common/Dealsheet - Log Activity - Tab - Button')

		static class ActionMenu{

			static TestObject SCHEDULE = findTestObject('Dealsheet/Log Activity/Schedule/Action Menu/Dealsheet - Log Activity - Schedule - Action Menu - Button')
		}
		
		static class Button{
			
			static TestObject SUBMIT = findTestObject('Dealsheet/Log Activity/Schedule/Buttons/Dealsheet - Log Activity - Schedule - Buttons - Submit')
			
			static TestObject CANCEL = findTestObject('Dealsheet/Log Activity/Schedule/Buttons/Dealsheet - Log Activity - Schedule - Buttons - Cancel')
		}

		static class Schedule{

			static class Call{
	
				static TestObject RADIO_BUTTON = findTestObject('Object Repository/Dealsheet/Log Activity/Schedule/Type/Call/Dealsheet - Log Activity - Schedule - Type - Call - Radio Button')
			}

			static class DueDate{

				static TestObject BUTTON = findTestObject('Dealsheet/Log Activity/Schedule/Due Date/Calendar/Dealsheet - Log Activity - Due Date - Calendar - Button')
				
				static TestObject INPUT = findTestObject('Dealsheet/Log Activity/Schedule/Due Date/Dealsheet - Log Activity - Due Date - Input')
			}

			static class Time{
				
					static TestObject MERIDIEM = findTestObject('Object Repository/Dealsheet/Log Activity/Schedule/Time/Meridiem/Dealsheet - Log Activity - Time - Meridiem')
				
				static class Hour{
					
					static TestObject INPUT = findTestObject('Dealsheet/Log Activity/Schedule/Time/Hour/Dealsheet - Log Activity - Time - Hour - Input')
				}
				
				static class Minute{
					
					static TestObject INPUT = findTestObject('Dealsheet/Log Activity/Schedule/Time/Minute/Dealsheet - Log Activity - Time - Minute - Input')					
				}			
			}

			static class AssignTo{
				
				static TestObject OTHER_USER_LINK = findTestObject('Dealsheet/Log Activity/Schedule/Assign To/Dealsheet - Log Activity - Assign To - Other User - Link')
				
				static class Owner{
					
					static TestObject DROPDOWN = findTestObject('Dealsheet/Log Activity/Schedule/Assign To/Dealsheet - Log Activity - Assign To - Other User - Dropdown')
				}				
			}	
		}
	}
}