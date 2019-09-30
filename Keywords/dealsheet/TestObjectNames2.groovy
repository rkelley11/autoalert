package dealsheet

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

final class TestObjectNames2 {

	static class UpperSection{

		static class PandoProfile{

			static String CONTAINER	= findTestObject('Object Repository/Dealsheet/Pando Profile/Pando Profile Container')

			static String CUSTOMER_NAME = 'Object Repository/Dealsheet/Pando Profile/Pando Profile Customer Name Header'

			static String TAB = 'Object Repository/Dealsheet/Pando Profile/Pando Tab'
		}

		static class LogActivity{

			static String TAB = 'Log Activity - Tab'

			static class RadioButtons{

				static String MARK_AS_SOLD = 'Log Activity - Radio Button - Mark As Sold'
			}

			static class Buttons{

				static String SUBMIT = 'Log Activity - Button - Submit'
			}
		}

		static class Icons{

			static String OPEN_DEALSHEET_IN_NEW_WINDOW 	= 'Open Dealsheet in New Window'

			static String OFFER_LINK 					= 'Dealsheet Offer - Link'

			static String CLOSE_DEALSHEET_ICON 			= 'Close Dealsheet'

			static String FLAG_DEALSHEET 				= 'Flag Dealsheet'

			static String MAP 							= 'Google Map Icon'
		}

		static class Customer{

			static String CUSTOMER_NAME 				= 'Customer Profile Name'

			static String CUSTOMER_PROFILE_EDIT_ICON 	= 'Dealsheet Customer Profile Edit Button'

			static class ClientEdit{

				static String COMPANY_NAME 	= 'Client Edit - Company Name'

				static String TITLE 		= 'Client Edit - Title'

				static class Address{

					static String FIRST 	= 'Client Edit - Address 1'

					static String SECOND 	= 'Client Edit - Address 2'

					static String CITY 		= 'Client Edit - City'

					static String ZIP_CODE 	= 'Client Edit - Zip Code'

					static class State{

						static String DROPDOWN = 'Client Edit - State'

						static String SELECTED = 'Client Edit - State - Selected'
					}
				}

				static class Buttons{

					static String CANCEL 		= 'Client Edit - Button - Cancel'

					static String CLOSE 		= 'Client Edit - Button - Close'

					static String SUBMIT 		= 'Client Edit - Button - Submit'

					static String CONFIRM_YES 	= 'Client Edit Confirmation Modal - Yes Button'
				}

				static class DoNot{

					static String CALL 	= 'Client Edit - Do Not Call'

					static String EMAIL = 'Client Edit - Do Not Email'

					static String MAIL 	= 'Client Edit - Do Not Mail'

					static String TEXT 	= 'Client Edit - Do Not Text'
				}

				static class Dropdown{

					static String LANGUAGE_PREFERENCE = 'Client Edit - Dropdown - Language Preference'
				}

				static class Email{

					static String PREFERRED 	= 'Client Edit - Email - Preferred'

					static String PREFERRED_BAD = 'Client Edit - Email - Preferred Bad'
				}

				static class Name{

					static String FIRST 	= 'Client Edit - Name - First'

					static String MIDDLE 	= 'Client Edit - Name - Middle'

					static String LAST 		= 'Client Edit - Name - Last'
				}

				static class Phone{

					static class Cell{

						static String PREFERRED 	= 'Client Edit - Phone - Cell - Preferred'

						static String PREFERRED_BAD = 'Client Edit - Phone - Cell - Preferred Bad'
					}

					static class Home{

						static String PREFERRED 	= 'Client Edit - Phone - Home - Preferred'

						static String PREFERRED_BAD = 'Client Edit - Phone - Home - Preferred Bad'
					}

					static class Work{

						static String PREFERRED 	= 'Client Edit - Phone - Work - Preferred'

						static String PREFERRED_BAD = 'Client Edit - Phone - Work - Preferred Bad'
					}
				}
			}

			static class GoogleMap{

				static String GOOGLE_LOGO = 'Google Logo'

				static class Buttons{

					static String CANCEL = 'Google Map Cancel Button'
				}
			}
		}

		static class Status{

			static String STATE = 'Dealsheet - Status'
		}
	}

	static class CarComparisonSection{

		static class ExistingVehicle{

			static class TrimInfo{

				static String NEW_USED 	= 'Car Comparison - Existing Vehicle - New Used'

				static String YEAR 		= 'Car Comparison - Existing Vehicle - Year'

				static String MAKE 		= 'Car Comparison - Existing Vehicle - Make'

				static String FULL_TRIM = 'Car Comparison - Existing Vehicle - Full Trim'
			}

			static class ContractInfo{

				static String SALE_TYPE 			= 'Car Comparison - Existing Vehicle - Sale Type'

				static String TERM_AND_PAYMENT 		= 'Car Comparison - Existing Vehicle - Term and Payment'

				static String PAYMENTS_REMAINING 	= 'Car Comparison - Existing Vehicle - Payments Remaining'

				static String APR 					= 'Car Comparison - Existing Vehicle - APR'
			}

			static class MarkAsSold{

				static String CHECKBOX = 'Car Comparison - Existing Vehicle - Mark as Sold - Checkbox'
			}

			static class RecentRO{

				static String DATE = 'Car Comparison - Existing Vehicle - Recent RO'
			}

			static class ServiceAppt{

				static String DATE_TIME = 'Car Comparison - Existing Vehicle - Service Appt'
			}

			static class Mileage{

				/*static String UPDATE_MILEAGE_INPUT 			= "Update Mileage Modal - Input - Mileage"
				 static String UPDATE_MILEAGE_READ_ON 		= 'Update Mileage Modal - Input - Read On'
				 static String UPDATE_MILEAGE_READON_BUTTON 	= "Update Mileage Modal - Button - Read On"
				 static String UPDATE_MILEAGE_CANCEL_BUTTON 	= "Update Mileage Modal - Button - Cancel"
				 static String UPDATE_MILEAGE_SUBMIT_BUTTON 	= "Update Mileage Modal - Button - Submit"*/
			}

			static class Trade{

				static String VALUE = 'Car Comparison - Existing Vehicle - Trade - Input'
			}

			static class CashDown{

				static String AMOUNT = 'Car Comparison - Existing Vehicle - Cash Down'
			}

			static class Payoff{

				static String LABEL 	= 'Car Comparison - Existing Vehicle - Payoff - Label'

				static String AMOUNT 	= 'Car Comparison - Existing Vehicle - Payoff - Amount'

				static String EDIT_ICON = 'Car Comparison - Existing Vehicle - Payoff - Edit Icon'
			}

			static class MaturityDate{

				static String MAKE = 'Car Comparison - Existing Vehicle - Maturity Date'
			}

			static class Warranty{

				static String MAKE = 'Car Comparison - Existing Vehicle - Warranty'
			}
		}
	}

	static class BottomSection{

		static class ExistingVehicleDetails{

			static String VIN = 'Bottom Section - VIN'

			static class Residual{

				static String AMOUNT = 'Dealsheet - Tabs - Existing Vehicle Details - Equity Available - Payoff - Amount'
			}
		}
	}

	static class Modals{

		static class General{

			static String YES 		= 'Dealsheet Modal - Button - Yes'

			static String CANCEL 	= 'Dealsheet Modal - Button - Cancel'
		}

		static class CRM{

			static String SUBMIT 	= 'CRM Modal - Button - Submit'

			static String CANCEL 	= 'CRM Modal - Button - Cancel'

			static String CONTAINER = 'CRM Modal - Container'

			static String HEADER 	= 'CRM Modal - Header'

			static String TEXT_AREA = 'CRM Modal - Text Area'
		}

		static class ReassignClient{

			static String CANCEL 		= 'Reassign Client Modal - Button - Cancel'

			static String SUBMIT 		= 'Reassign Client Modal - Button - Submit'

			static String TITLE 		= 'Reassign Client Modal - Title'

			static String DEALER_LIST 	= 'Reassign Client Modal - Unordered List of Dealers'
		}

		static class SaveAdjustPayoff{

			static String AMOUNT 			= 'Save Adjust Payoff Modal - Input - Amount'

			static String GOOD_THROUGH 		= 'Save Adjust Payoff Modal - Input - Good Through'

			static String CALENDAR_BUTTON 	= 'Save Adjust Payoff Modal - Button - Calendar'

			static String CANCEL_BUTTON 	= 'Save Adjust Payoff Modal - Button - Cancel'

			static String SUBMIT_BUTTON 	= 'Save Adjust Payoff Modal - Button - Submit'
		}

		static class UpdateMileage{

			static String MILEAGE_INPUT 	= 'Update Mileage Modal - Input - Mileage'

			static String READ_ON 			= 'Update Mileage Modal - Input - Read On'

			static String CALENDAR_BUTTON 	= 'Update Mileage Modal - Button - Read On'

			static String CANCEL_BUTTON 	= 'Update Mileage Modal - Button - Cancel'

			static String SUBMIT_BUTTON 	= 'Update Mileage Modal - Button - Submit'
		}
	}

	static class Other{

		static String SPINNER = 'Dealsheet Spinner'
	}
}