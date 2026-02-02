import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainRegexUnitTests {

    private static int passed = 0;
    private static int failed = 0;

    @BeforeAll
    static void printHeader() {
        System.out.println("================================================================================");
        System.out.println("REGEX UNIT TEST OUTPUT (Expected vs Actual)");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-12s | %-44s | %-32s | %-7s | %-7s | %-4s%n",
                "CATEGORY", "TEST NAME", "INPUT", "EXPECT", "ACTUAL", "RES");
        System.out.println("--------------------------------------------------------------------------------");
    }

    @AfterAll
    static void printSummary() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("============================== SUMMARY =========================================");
        int total = passed + failed;
        System.out.printf("Total: %d | Passed: %d | Failed: %d%n", total, passed, failed);
        System.out.println("================================================================================");
    }

    /**
     * Prints one formatted line for each test AND asserts so JUnit will still fail properly.
     */
    private static void assertAndPrint(String category, String input, boolean expected, boolean actual) {
        // Automatically captures the calling test method name
        String testName = Thread.currentThread().getStackTrace()[3].getMethodName();

        String shownInput = "\"" + input + "\"";
        if (shownInput.length() > 32) {
            shownInput = shownInput.substring(0, 29) + "...\"";
        }

        boolean ok = (expected == actual);
        String res = ok ? "PASS" : "FAIL";

        System.out.printf("%-12s | %-44s | %-32s | %-7s | %-7s | %-4s%n",
                category, testName, shownInput, expected, actual, res);

        if (ok) passed++; else failed++;

        // JUnit-grade assertion (keeps official pass/fail correct)
        assertEquals(expected, actual,
                () -> "FAILED: " + category + " :: " + testName
                        + " input=" + input + " expected=" + expected + " actual=" + actual);
    }

    // =========================================================
    // 1) SOCIAL SECURITY NUMBER
    // =========================================================

    @Test void testValidSSNAllDigits() {
        assertAndPrint("SSN", "123456789", true, Main.validateSocial("123456789"));
    }

    @Test void testValidSSNDashes() {
        assertAndPrint("SSN", "123-45-6789", true, Main.validateSocial("123-45-6789"));
    }

    @Test void testValidSSNSpaces() {
        assertAndPrint("SSN", "123 45 6789", true, Main.validateSocial("123 45 6789"));
    }

    @Test void testValidSSNSpacesAroundDashes() {
        assertAndPrint("SSN", "123 - 45 - 6789", true, Main.validateSocial("123 - 45 - 6789"));
    }

    @Test void testValidSSNMixedDashSpace() {
        assertAndPrint("SSN", "123-45 6789", true, Main.validateSocial("123-45 6789"));
    }

    @Test void testValidSSNMixedSpaceDash() {
        assertAndPrint("SSN", "123 45-6789", true, Main.validateSocial("123 45-6789"));
    }

    @Test void testValidSSNNoSpacesWithDashes() {
        assertAndPrint("SSN", "999-99-9999", true, Main.validateSocial("999-99-9999"));
    }

    @Test void testValidSSNZeroesAllowedByRegex() {
        assertAndPrint("SSN", "000-00-0000", true, Main.validateSocial("000-00-0000"));
    }

    @Test void testRejectSSNTooShort() {
        assertAndPrint("SSN", "123-45-678", false, Main.validateSocial("123-45-678"));
    }

    @Test void testRejectSSNTooLong() {
        assertAndPrint("SSN", "123-45-67890", false, Main.validateSocial("123-45-67890"));
    }

    @Test void testRejectSSNBadGroupingFirstPart() {
        assertAndPrint("SSN", "12-345-6789", false, Main.validateSocial("12-345-6789"));
    }

    @Test void testRejectSSNBadGroupingSecondPart() {
        assertAndPrint("SSN", "123-4-6789", false, Main.validateSocial("123-4-6789"));
    }

    @Test void testRejectSSNLetters() {
        assertAndPrint("SSN", "abc-de-ghij", false, Main.validateSocial("abc-de-ghij"));
    }

    @Test void testRejectSSNWrongSeparatorUnderscore() {
        assertAndPrint("SSN", "123_45_6789", false, Main.validateSocial("123_45_6789"));
    }

    @Test void testRejectSSNDoubleDashes() {
        assertAndPrint("SSN", "123--45--6789", false, Main.validateSocial("123--45--6789"));
    }

    @Test void testRejectSSNTrailingGarbage() {
        assertAndPrint("SSN", "123-45-6789x", false, Main.validateSocial("123-45-6789x"));
    }

    // =========================================================
    // 2) US PHONE NUMBER
    // =========================================================

    @Test void testValidPhoneAllDigits() {
        assertAndPrint("PHONE", "9015589624", true, Main.validatePhoneNumber("9015589624"));
    }

    @Test void testValidPhoneDashes() {
        assertAndPrint("PHONE", "901-558-9624", true, Main.validatePhoneNumber("901-558-9624"));
    }

    @Test void testValidPhoneSpaces() {
        assertAndPrint("PHONE", "901 558 9624", true, Main.validatePhoneNumber("901 558 9624"));
    }

    @Test void testValidPhoneParenthesesNoSpace() {
        assertAndPrint("PHONE", "(901)5589624", true, Main.validatePhoneNumber("(901)5589624"));
    }

    @Test void testValidPhoneParenthesesDash() {
        assertAndPrint("PHONE", "(901)558-9624", true, Main.validatePhoneNumber("(901)558-9624"));
    }

    @Test void testValidPhoneParenthesesSpaceThenDigits() {
        assertAndPrint("PHONE", "(901) 5589624", true, Main.validatePhoneNumber("(901) 5589624"));
    }

    @Test void testValidPhoneMixedDashNoDashLast() {
        assertAndPrint("PHONE", "901-5589624", true, Main.validatePhoneNumber("901-5589624"));
    }

    @Test void testValidPhoneMixedSpaceDash() {
        assertAndPrint("PHONE", "901 558-9624", true, Main.validatePhoneNumber("901 558-9624"));
    }

    @Test void testRejectPhoneTooShort() {
        assertAndPrint("PHONE", "901-558-962", false, Main.validatePhoneNumber("901-558-962"));
    }

    @Test void testRejectPhoneTooLong() {
        assertAndPrint("PHONE", "901-558-96244", false, Main.validatePhoneNumber("901-558-96244"));
    }

    @Test void testRejectPhoneBadAreaCodeLength() {
        assertAndPrint("PHONE", "90-1558-9624", false, Main.validatePhoneNumber("90-1558-9624"));
    }

    @Test void testRejectPhoneUnbalancedLeftParen() {
        assertAndPrint("PHONE", "(901-558-9624", false, Main.validatePhoneNumber("(901-558-9624"));
    }

    @Test void testRejectPhoneUnbalancedRightParen() {
        assertAndPrint("PHONE", "901) 558-9624", false, Main.validatePhoneNumber("901) 558-9624"));
    }

    @Test void testRejectPhoneLetters() {
        assertAndPrint("PHONE", "abc-def-ghij", false, Main.validatePhoneNumber("abc-def-ghij"));
    }

    @Test void testRejectPhoneDotsNotAllowed() {
        assertAndPrint("PHONE", "901.558.9624", false, Main.validatePhoneNumber("901.558.9624"));
    }

    @Test void testRejectPhoneExtraCharacters() {
        assertAndPrint("PHONE", "901-558-9624 ext 5", false, Main.validatePhoneNumber("901-558-9624 ext 5"));
    }

    // =========================================================
    // 3) EMAIL ADDRESS
    // =========================================================

    @Test void testValidEmailSimple() {
        assertAndPrint("EMAIL", "a@b.co", true, Main.validateEmail("a@b.co"));
    }

    @Test void testValidEmailCommon() {
        assertAndPrint("EMAIL", "john.doe@gmail.com", true, Main.validateEmail("john.doe@gmail.com"));
    }

    @Test void testValidEmailWithPlus() {
        assertAndPrint("EMAIL", "name+tag@domain.com", true, Main.validateEmail("name+tag@domain.com"));
    }

    @Test void testValidEmailWithUnderscore() {
        assertAndPrint("EMAIL", "user_name@school.edu", true, Main.validateEmail("user_name@school.edu"));
    }

    @Test void testValidEmailUppercaseAllowed() {
        assertAndPrint("EMAIL", "FIRST.LAST@EXAMPLE.COM", true, Main.validateEmail("FIRST.LAST@EXAMPLE.COM"));
    }

    @Test void testValidEmailDigitsInLocalPart() {
        assertAndPrint("EMAIL", "dweaed22134@yahoo.co", true, Main.validateEmail("dweaed22134@yahoo.co"));
    }

    @Test void testValidEmailHyphenInLocalPart() {
        assertAndPrint("EMAIL", "first-last@domain.org", true, Main.validateEmail("first-last@domain.org"));
    }

    @Test void testValidEmailPercentAllowed() {
        assertAndPrint("EMAIL", "a%test@domain.net", true, Main.validateEmail("a%test@domain.net"));
    }

    @Test void testRejectEmailMissingAt() {
        assertAndPrint("EMAIL", "plainaddress", false, Main.validateEmail("plainaddress"));
    }

    @Test void testRejectEmailMissingLocalPart() {
        assertAndPrint("EMAIL", "@domain.com", false, Main.validateEmail("@domain.com"));
    }

    @Test void testRejectEmailMissingDomain() {
        assertAndPrint("EMAIL", "name@", false, Main.validateEmail("name@"));
    }

    @Test void testRejectEmailMissingTLD() {
        assertAndPrint("EMAIL", "name@domain", false, Main.validateEmail("name@domain"));
    }

    @Test void testRejectEmailTLDTooShort() {
        assertAndPrint("EMAIL", "name@domain.c", false, Main.validateEmail("name@domain.c"));
    }

    @Test void testRejectEmailDoubleDotDomain() {
        assertAndPrint("EMAIL", "name@domain..com", false, Main.validateEmail("name@domain..com"));
    }

    @Test void testRejectEmailSpaceInLocalPart() {
        assertAndPrint("EMAIL", "na me@domain.com", false, Main.validateEmail("na me@domain.com"));
    }

    @Test void testRejectEmailSpaceInDomain() {
        assertAndPrint("EMAIL", "name@do main.com", false, Main.validateEmail("name@do main.com"));
    }

    // =========================================================
    // 4) ROSTER NAME: Last, First, MI(optional)
    // =========================================================

    @Test void testValidNameNoMiddleInitial() {
        assertAndPrint("NAME", "Smith, John", true, Main.validateName("Smith, John"));
    }

    @Test void testValidNameWithMiddleInitial() {
        assertAndPrint("NAME", "Smith, John, L", true, Main.validateName("Smith, John, L"));
    }

    @Test void testValidNameWithApostropheLastName() {
        assertAndPrint("NAME", "O'Neil, Sarah, A", true, Main.validateName("O'Neil, Sarah, A"));
    }

    @Test void testValidNameHyphenLastName() {
        assertAndPrint("NAME", "Garcia-Lopez, Maria, Q", true, Main.validateName("Garcia-Lopez, Maria, Q"));
    }

    @Test void testValidNameSingleLetterMiddleInitial() {
        assertAndPrint("NAME", "Brown, Lisa, Z", true, Main.validateName("Brown, Lisa, Z"));
    }

    @Test void testValidNameAnotherExampleNoMI() {
        assertAndPrint("NAME", "Johnson, Mark", true, Main.validateName("Johnson, Mark"));
    }

    @Test void testValidNameLastNameWithApostrophe() {
        assertAndPrint("NAME", "D'Angelo, Tony, R", true, Main.validateName("D'Angelo, Tony, R"));
    }

    @Test void testValidNameLastNameWithHyphen() {
        assertAndPrint("NAME", "Smith-Jones, Amy, B", true, Main.validateName("Smith-Jones, Amy, B"));
    }

    @Test void testRejectNameWrongOrder() {
        assertAndPrint("NAME", "John Smith", false, Main.validateName("John Smith"));
    }

    @Test void testRejectNameLowercaseStartLast() {
        assertAndPrint("NAME", "smith, John, L", false, Main.validateName("smith, John, L"));
    }

    @Test void testRejectNameLowercaseStartFirst() {
        assertAndPrint("NAME", "Smith, john, L", false, Main.validateName("Smith, john, L"));
    }

    @Test void testRejectNameMissingComma() {
        assertAndPrint("NAME", "Smith John L", false, Main.validateName("Smith John L"));
    }

    @Test void testRejectNameTwoLetterMI() {
        assertAndPrint("NAME", "Smith, John, LL", false, Main.validateName("Smith, John, LL"));
    }

    @Test void testRejectNameNumericMI() {
        assertAndPrint("NAME", "Smith, John, 7", false, Main.validateName("Smith, John, 7"));
    }

    @Test void testRejectNameExtraComma() {
        assertAndPrint("NAME", "Smith,, John, L", false, Main.validateName("Smith,, John, L"));
    }

    @Test void testRejectNameTrailingCommaOnly() {
        assertAndPrint("NAME", "Smith, John,", false, Main.validateName("Smith, John,"));
    }

    // =========================================================
    // 5) DATE MM-DD-YYYY or MM/DD/YYYY with real calendar validation
    // =========================================================

    @Test void testValidDateWithDashesTypical() {
        assertAndPrint("DATE", "02-28-1995", true, Main.validateDate("02-28-1995"));
    }

    @Test void testValidDateWithSlashesTypical() {
        assertAndPrint("DATE", "02/28/1995", true, Main.validateDate("02/28/1995"));
    }

    @Test void testValidDateLeapDayValidLeapYear() {
        assertAndPrint("DATE", "02-29-2024", true, Main.validateDate("02-29-2024"));
    }

    @Test void testValidDateEndOfYearSlashes() {
        assertAndPrint("DATE", "12/31/2024", true, Main.validateDate("12/31/2024"));
    }

    @Test void testValidDateStartOfYear() {
        assertAndPrint("DATE", "01-01-2000", true, Main.validateDate("01-01-2000"));
    }

    @Test void testValidDateApril30Valid() {
        assertAndPrint("DATE", "04/30/2010", true, Main.validateDate("04/30/2010"));
    }

    @Test void testValidDateNovember30Valid() {
        assertAndPrint("DATE", "11-30-1999", true, Main.validateDate("11-30-1999"));
    }

    @Test void testValidDateMiddleOfMonthValid() {
        assertAndPrint("DATE", "03/15/1987", true, Main.validateDate("03/15/1987"));
    }

    @Test void testRejectDateMonth13Invalid() {
        assertAndPrint("DATE", "13-01-2000", false, Main.validateDate("13-01-2000"));
    }

    @Test void testRejectDateMonth00Invalid() {
        assertAndPrint("DATE", "00/10/2000", false, Main.validateDate("00/10/2000"));
    }

    @Test void testRejectDateFeb30Invalid() {
        assertAndPrint("DATE", "02-30-2001", false, Main.validateDate("02-30-2001"));
    }

    @Test void testRejectDateFeb29NotLeapYear() {
        assertAndPrint("DATE", "02/29/2023", false, Main.validateDate("02/29/2023"));
    }

    @Test void testRejectDateApril31Invalid() {
        assertAndPrint("DATE", "04-31-2012", false, Main.validateDate("04-31-2012"));
    }

    @Test void testRejectDateWrongFormatSingleDigitMonth() {
        assertAndPrint("DATE", "1-01-2000", false, Main.validateDate("1-01-2000"));
    }

    @Test void testRejectDateWrongFormatSingleDigitDay() {
        assertAndPrint("DATE", "01/1/2000", false, Main.validateDate("01/1/2000"));
    }

    @Test void testRejectDateYearWrongLength() {
        assertAndPrint("DATE", "01-01-20", false, Main.validateDate("01-01-20"));
    }

    // =========================================================
    // 6) HOUSE ADDRESS: number + street name + road type
    // =========================================================

    @Test void testValidAddressStreetFullWord() {
        assertAndPrint("ADDRESS", "789 North Main Street", true, Main.validateAddress("789 North Main Street"));
    }

    @Test void testValidAddressStreetAbbrev() {
        assertAndPrint("ADDRESS", "9 Maple St", true, Main.validateAddress("9 Maple St"));
    }

    @Test void testValidAddressRoadFullWord() {
        assertAndPrint("ADDRESS", "42 Oak Road", true, Main.validateAddress("42 Oak Road"));
    }

    @Test void testValidAddressRoadAbbrev() {
        assertAndPrint("ADDRESS", "123 E Pine Rd", true, Main.validateAddress("123 E Pine Rd"));
    }

    @Test void testValidAddressBoulevardFullWord() {
        assertAndPrint("ADDRESS", "100 Broadway Boulevard", true, Main.validateAddress("100 Broadway Boulevard"));
    }

    @Test void testValidAddressBoulevardAbbrev() {
        assertAndPrint("ADDRESS", "321 King Blvd", true, Main.validateAddress("321 King Blvd"));
    }

    @Test void testValidAddressAvenueFullWord() {
        assertAndPrint("ADDRESS", "1600 Pennsylvania Avenue", true, Main.validateAddress("1600 Pennsylvania Avenue"));
    }

    @Test void testValidAddressAvenueAbbrev() {
        assertAndPrint("ADDRESS", "55 1st Ave", true, Main.validateAddress("55 1st Ave"));
    }

    @Test void testRejectAddressMissingNumber() {
        assertAndPrint("ADDRESS", "North Main Street", false, Main.validateAddress("North Main Street"));
    }

    @Test void testRejectAddressMissingRoadType() {
        assertAndPrint("ADDRESS", "789 North Main", false, Main.validateAddress("789 North Main"));
    }

    @Test void testRejectAddressOnlyNumber() {
        assertAndPrint("ADDRESS", "789", false, Main.validateAddress("789"));
    }

    @Test void testRejectAddressUnsupportedRoadType() {
        assertAndPrint("ADDRESS", "789 North Main Lane", false, Main.validateAddress("789 North Main Lane"));
    }

    @Test void testRejectAddressTypoRoadType() {
        assertAndPrint("ADDRESS", "789 North Main Strt", false, Main.validateAddress("789 North Main Strt"));
    }

    @Test void testRejectAddressIllegalCharHash() {
        assertAndPrint("ADDRESS", "789 # Main Street", false, Main.validateAddress("789 # Main Street"));
    }

    @Test void testRejectAddressEndsWithNumber() {
        assertAndPrint("ADDRESS", "789 North Main 123", false, Main.validateAddress("789 North Main 123"));
    }

    @Test void testRejectAddressRoadTypeWithDotNotAccepted() {
        assertAndPrint("ADDRESS", "789 North Main St.", false, Main.validateAddress("789 North Main St."));
    }

    // =========================================================
    // 7) CITY STATE ZIP
    // =========================================================

    @Test void testValidCityStateZipWithComma() {
        assertAndPrint("CITYSTATEZIP", "San Diego, CA 98030", true, Main.cityStateZip("San Diego, CA 98030"));
    }

    @Test void testValidCityStateZipNoComma() {
        assertAndPrint("CITYSTATEZIP", "Tacoma WA 98402", true, Main.cityStateZip("Tacoma WA 98402"));
    }

    @Test void testValidCityStateZipTwoWordCity() {
        assertAndPrint("CITYSTATEZIP", "New York, NY 10001", true, Main.cityStateZip("New York, NY 10001"));
    }

    @Test void testValidCityStateZipThreeWordCity() {
        assertAndPrint("CITYSTATEZIP", "Los Angeles CA 90001", true, Main.cityStateZip("Los Angeles CA 90001"));
    }

    @Test void testValidCityStateZipWithZipPlus4() {
        assertAndPrint("CITYSTATEZIP", "Seattle, WA 98101-1234", true, Main.cityStateZip("Seattle, WA 98101-1234"));
    }

    @Test void testValidCityStateZipAnother() {
        assertAndPrint("CITYSTATEZIP", "Austin, TX 73301", true, Main.cityStateZip("Austin, TX 73301"));
    }

    @Test void testValidCityStateZipNoCommaAnother() {
        assertAndPrint("CITYSTATEZIP", "Miami FL 33101", true, Main.cityStateZip("Miami FL 33101"));
    }

    @Test void testValidCityStateZipZipPlus4Another() {
        assertAndPrint("CITYSTATEZIP", "Portland OR 97201-0001", true, Main.cityStateZip("Portland OR 97201-0001"));
    }

    @Test void testRejectCityStateZipMissingZip() {
        assertAndPrint("CITYSTATEZIP", "San Diego, CA", false, Main.cityStateZip("San Diego, CA"));
    }

    @Test void testRejectCityStateZipStateTooLong() {
        assertAndPrint("CITYSTATEZIP", "San Diego, CAA 98030", false, Main.cityStateZip("San Diego, CAA 98030"));
    }

    @Test void testRejectCityStateZipZipTooShort() {
        assertAndPrint("CITYSTATEZIP", "San Diego, CA 9803", false, Main.cityStateZip("San Diego, CA 9803"));
    }

    @Test void testRejectCityStateZipZipTooLong() {
        assertAndPrint("CITYSTATEZIP", "San Diego, CA 980300", false, Main.cityStateZip("San Diego, CA 980300"));
    }

    @Test void testRejectCityStateZipWrongSeparator() {
        assertAndPrint("CITYSTATEZIP", "San Diego, CA-98030", false, Main.cityStateZip("San Diego, CA-98030"));
    }

    @Test void testRejectCityStateZipPlus4TooShort() {
        assertAndPrint("CITYSTATEZIP", "San Diego CA 98030-123", false, Main.cityStateZip("San Diego CA 98030-123"));
    }

    @Test void testRejectCityStateZipMissingCity() {
        assertAndPrint("CITYSTATEZIP", ", CA 98030", false, Main.cityStateZip(", CA 98030"));
    }

    @Test void testRejectCityStateZipNumericCity() {
        assertAndPrint("CITYSTATEZIP", "123, CA 98030", false, Main.cityStateZip("123, CA 98030"));
    }

    // =========================================================
    // 8) MILITARY TIME (HHMM)
    // =========================================================

    @Test void testValidMilitaryTimeMidnight() {
        assertAndPrint("MILTIME", "0000", true, Main.militaryTime("0000"));
    }

    @Test void testValidMilitaryTimeEarlyMorning() {
        assertAndPrint("MILTIME", "0109", true, Main.militaryTime("0109"));
    }

    @Test void testValidMilitaryTimeBeforeNoon() {
        assertAndPrint("MILTIME", "0959", true, Main.militaryTime("0959"));
    }

    @Test void testValidMilitaryTimeNoon() {
        assertAndPrint("MILTIME", "1200", true, Main.militaryTime("1200"));
    }

    @Test void testValidMilitaryTimeAfternoon() {
        assertAndPrint("MILTIME", "1357", true, Main.militaryTime("1357"));
    }

    @Test void testValidMilitaryTimeEvening() {
        assertAndPrint("MILTIME", "2305", true, Main.militaryTime("2305"));
    }

    @Test void testValidMilitaryTimeLastMinute() {
        assertAndPrint("MILTIME", "2359", true, Main.militaryTime("2359"));
    }

    @Test void testValidMilitaryTimeTenAM() {
        assertAndPrint("MILTIME", "1000", true, Main.militaryTime("1000"));
    }

    @Test void testRejectMilitaryTime24xxInvalid() {
        assertAndPrint("MILTIME", "2400", false, Main.militaryTime("2400"));
    }

    @Test void testRejectMilitaryTimeMinutes60Invalid() {
        assertAndPrint("MILTIME", "2360", false, Main.militaryTime("2360"));
    }

    @Test void testRejectMilitaryTimeMinute60Invalid() {
        assertAndPrint("MILTIME", "0060", false, Main.militaryTime("0060"));
    }

    @Test void testRejectMilitaryTimeTooShort() {
        assertAndPrint("MILTIME", "100", false, Main.militaryTime("100"));
    }

    @Test void testRejectMilitaryTimeHasColon() {
        assertAndPrint("MILTIME", "1:00", false, Main.militaryTime("1:00"));
    }

    @Test void testRejectMilitaryTimeLetters() {
        assertAndPrint("MILTIME", "ab12", false, Main.militaryTime("ab12"));
    }

    @Test void testRejectMilitaryTimeImpossible() {
        assertAndPrint("MILTIME", "9999", false, Main.militaryTime("9999"));
    }

    @Test void testRejectMilitaryTimeBadMinute61() {
        assertAndPrint("MILTIME", "1261", false, Main.militaryTime("1261"));
    }

    // =========================================================
    // 9) US CURRENCY
    // =========================================================

    @Test void testValidCurrencySmallAmount() {
        assertAndPrint("CURRENCY", "$0.01", true, Main.UsCurrency("$0.01"));
    }

    @Test void testValidCurrencyTwoDigits() {
        assertAndPrint("CURRENCY", "$12.34", true, Main.UsCurrency("$12.34"));
    }

    @Test void testValidCurrencyThreeDigits() {
        assertAndPrint("CURRENCY", "$999.99", true, Main.UsCurrency("$999.99"));
    }

    @Test void testValidCurrencyCommaThousands() {
        assertAndPrint("CURRENCY", "$1,234.56", true, Main.UsCurrency("$1,234.56"));
    }

    @Test void testValidCurrencyCommaTenThousands() {
        assertAndPrint("CURRENCY", "$12,345.67", true, Main.UsCurrency("$12,345.67"));
    }

    @Test void testValidCurrencyManyCommas() {
        assertAndPrint("CURRENCY", "$123,456,789.23", true, Main.UsCurrency("$123,456,789.23"));
    }

    @Test void testValidCurrencyNoCommasLargeIfAllowed() {
        assertAndPrint("CURRENCY", "$1234.00", true, Main.UsCurrency("$1234.00"));
    }

    @Test void testValidCurrencyGivenExample() {
        assertAndPrint("CURRENCY", "$57,555,000.36", true, Main.UsCurrency("$57,555,000.36"));
    }

    @Test void testRejectCurrencyMissingDollarSign() {
        assertAndPrint("CURRENCY", "57,555,000.36", false, Main.UsCurrency("57,555,000.36"));
    }

    @Test void testRejectCurrencyOneDecimalDigit() {
        assertAndPrint("CURRENCY", "$57,555,000.3", false, Main.UsCurrency("$57,555,000.3"));
    }

    @Test void testRejectCurrencyThreeDecimalDigits() {
        assertAndPrint("CURRENCY", "$57,555,000.366", false, Main.UsCurrency("$57,555,000.366"));
    }

    @Test void testRejectCurrencyBadCommaGrouping() {
        assertAndPrint("CURRENCY", "$57,55,000.36", false, Main.UsCurrency("$57,55,000.36"));
    }

    @Test void testRejectCurrencyCommaAfterDollar() {
        assertAndPrint("CURRENCY", "$,123.45", false, Main.UsCurrency("$,123.45"));
    }

    @Test void testRejectCurrencyMissingCents() {
        assertAndPrint("CURRENCY", "$123.", false, Main.UsCurrency("$123."));
    }

    @Test void testRejectCurrencyNegativeNotSupported() {
        assertAndPrint("CURRENCY", "$-12.34", false, Main.UsCurrency("$-12.34"));
    }

    @Test void testRejectCurrencyBadCommaPlacement() {
        assertAndPrint("CURRENCY", "$12,34.56", false, Main.UsCurrency("$12,34.56"));
    }

    // =========================================================
    // 10) URL (http/https optional; case-insensitive)
    // =========================================================

    @Test void testValidUrlHttpsFull() {
        assertAndPrint("URL", "https://regex101.com/", true, Main.url("https://regex101.com/"));
    }

    @Test void testValidUrlHttpFull() {
        assertAndPrint("URL", "http://example.com", true, Main.url("http://example.com"));
    }

    @Test void testValidUrlNoScheme() {
        assertAndPrint("URL", "example.com", true, Main.url("example.com"));
    }

    @Test void testValidUrlWithWwwAndPath() {
        assertAndPrint("URL", "www.example.com/path", true, Main.url("www.example.com/path"));
    }

    @Test void testValidUrlUppercaseSchemeAndDomain() {
        assertAndPrint("URL", "HTTPS://EXAMPLE.COM", true, Main.url("HTTPS://EXAMPLE.COM"));
    }

    @Test void testValidUrlSubdomainAndPath() {
        assertAndPrint("URL", "sub.domain.co.uk/path", true, Main.url("sub.domain.co.uk/path"));
    }

    @Test void testValidUrlTrailingSlash() {
        assertAndPrint("URL", "example.org/", true, Main.url("example.org/"));
    }

    @Test void testValidUrlMultiplePathSegments() {
        assertAndPrint("URL", "example.net/a/b/c", true, Main.url("example.net/a/b/c"));
    }

    @Test void testRejectUrlBadSchemeTypo() {
        assertAndPrint("URL", "htp://example.com", false, Main.url("htp://example.com"));
    }

    @Test void testRejectUrlIncompleteHttp() {
        assertAndPrint("URL", "http://", false, Main.url("http://"));
    }

    @Test void testRejectUrlNoTld() {
        assertAndPrint("URL", "example", false, Main.url("example"));
    }

    @Test void testRejectUrlDoubleDot() {
        assertAndPrint("URL", "example..com", false, Main.url("example..com"));
    }

    @Test void testRejectUrlSpaceInDomain() {
        assertAndPrint("URL", "http://exa mple.com", false, Main.url("http://exa mple.com"));
    }

    @Test void testRejectUrlMissingSchemeButHasColonSlashes() {
        assertAndPrint("URL", "://example.com", false, Main.url("://example.com"));
    }

    @Test void testRejectUrlLeadingHyphenLabel() {
        assertAndPrint("URL", "http://-example.com", false, Main.url("http://-example.com"));
    }

    @Test void testRejectUrlTldTooShort() {
        assertAndPrint("URL", "http://example.c", false, Main.url("http://example.c"));
    }

    // =========================================================
    // 11) PASSWORD
    // =========================================================

    @Test void testRejectPasswordFourConsecutiveLowercaseNotAllowed_Example() {
        assertAndPrint("PASSWORD", "Ab1!xxxxY2", false, Main.password("Ab1!xxxxY2"));
    }

    @Test void testValidPasswordMeetsAllRequirements2() {
        assertAndPrint("PASSWORD", "A1!b2@C3#d4", true, Main.password("A1!b2@C3#d4"));
    }

    @Test void testValidPasswordMeetsAllRequirements3() {
        assertAndPrint("PASSWORD", "Z9!a2Bc3dE", true, Main.password("Z9!a2Bc3dE"));
    }

    @Test void testValidPasswordMeetsAllRequirements4() {
        assertAndPrint("PASSWORD", "Qw1!Er2@Ty", true, Main.password("Qw1!Er2@Ty"));
    }

    @Test void testValidPasswordMeetsAllRequirements5() {
        assertAndPrint("PASSWORD", "M9$noP7&qr", true, Main.password("M9$noP7&qr"));
    }

    @Test void testValidPasswordMeetsAllRequirements6() {
        assertAndPrint("PASSWORD", "X1!yZ2@wV3#", true, Main.password("X1!yZ2@wV3#"));
    }

    @Test void testValidPasswordMeetsAllRequirements7() {
        assertAndPrint("PASSWORD", "P4!qR5@stU6", true, Main.password("P4!qR5@stU6"));
    }

    @Test void testValidPasswordMeetsAllRequirements8() {
        assertAndPrint("PASSWORD", "Aa1!Bb2@Cc", true, Main.password("Aa1!Bb2@Cc"));
    }

    @Test void testRejectPasswordTooShort() {
        assertAndPrint("PASSWORD", "Aa1!bbbb", false, Main.password("Aa1!bbbb"));
    }

    @Test void testRejectPasswordMissingUppercase() {
        assertAndPrint("PASSWORD", "alllowercase1!", false, Main.password("alllowercase1!"));
    }

    @Test void testRejectPasswordMissingLowercase() {
        assertAndPrint("PASSWORD", "ALLUPPERCASE1!", false, Main.password("ALLUPPERCASE1!"));
    }

    @Test void testRejectPasswordMissingDigit() {
        assertAndPrint("PASSWORD", "NoDigitsHere!!", false, Main.password("NoDigitsHere!!"));
    }

    @Test void testRejectPasswordMissingPunctuation() {
        assertAndPrint("PASSWORD", "NoPunctHere12", false, Main.password("NoPunctHere12"));
    }

    @Test void testRejectPasswordFourConsecutiveLowercaseNotAllowed() {
        assertAndPrint("PASSWORD", "A1!bcdeFgHi", false, Main.password("A1!bcdeFgHi"));
    }

    @Test void testRejectPasswordFiveConsecutiveLowercaseNotAllowed() {
        assertAndPrint("PASSWORD", "A1!abcdefGHI", false, Main.password("A1!abcdefGHI"));
    }

    @Test void testRejectPasswordNoPunctButHasOthers() {
        assertAndPrint("PASSWORD", "AbcdefG1234", false, Main.password("AbcdefG1234"));
    }

    // =========================================================
    // 12) ODD number of alphabetic characters ending in "ion"
    // =========================================================

    @Test void testValidOddIonIonOnly() {
        assertAndPrint("ODDION", "ion", true, Main.oddIons("ion"));
    }

    @Test void testValidOddIonCaution() {
        assertAndPrint("ODDION", "caution", true, Main.oddIons("caution"));
    }

    @Test void testValidOddIonEmotion() {
        assertAndPrint("ODDION", "emotion", true, Main.oddIons("emotion"));
    }

    @Test void testValidOddIonReunion() {
        assertAndPrint("ODDION", "reunion", true, Main.oddIons("reunion"));
    }

    @Test void testValidOddIonMillion() {
        assertAndPrint("ODDION", "million", true, Main.oddIons("million"));
    }

    @Test void testValidOddIonAbion() {
        assertAndPrint("ODDION", "abion", true, Main.oddIons("abion"));
    }

    @Test void testRejectOddIonEvenLengthXxion() {
        assertAndPrint("ODDION", "xxion", true, Main.oddIons("xxion"));
    }

    @Test void testRejectOddIonEvenLengthZzzion() {
        assertAndPrint("ODDION", "zzzion", false, Main.oddIons("zzzion"));
    }

    @Test void testRejectOddIonEvenLengthAction() {
        assertAndPrint("ODDION", "action", false, Main.oddIons("action"));
    }

    @Test void testRejectOddIonEvenLengthMotion() {
        assertAndPrint("ODDION", "motion", false, Main.oddIons("motion"));
    }

    @Test void testRejectOddIonEvenLengthCreation() {
        assertAndPrint("ODDION", "creation", false, Main.oddIons("creation"));
    }

    @Test void testRejectOddIonContainsDigit() {
        assertAndPrint("ODDION", "ion1", false, Main.oddIons("ion1"));
    }

    @Test void testRejectOddIonNotEndingIon() {
        assertAndPrint("ODDION", "ions", false, Main.oddIons("ions"));
    }

    @Test void testRejectOddIonNonAlpha() {
        assertAndPrint("ODDION", "i0n", false, Main.oddIons("i0n"));
    }

    @Test void testRejectOddIonWrongEnding() {
        assertAndPrint("ODDION", "nationwide", false, Main.oddIons("nationwide"));
    }

    @Test void testRejectOddIonUppercaseIfCaseSensitive() {
        assertAndPrint("ODDION", "ION", false, Main.oddIons("ION"));
    }
}
