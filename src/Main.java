
/*
 * Nikita Bizyuk
 * Individual Assignment (regular expressions)
 * Tom Capaul
 * TCSS 483
 * 02-01-2026
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Main {
    public static void main(String[] args) {

//        System.out.println(validateSocial("123-45-6789"));
//        System.out.println(validatePhoneNumber("901-558-9624"));
//        System.out.println(validateEmail("dweaed22134@yahoo.co"));
//        System.out.println(validateName("jimmy, butler, A"));
         //   System.out.println(validateDate("02-28-1995"));
//        System.out.println(validateAddress("789 north main street"));
//        System.out.println(cityStateZip("san diego, CA 98030"));
//        System.out.println(militaryTime("0109"));
//        System.out.println(UsCurrency("$57,555,000.36"));
//        System.out.println(url("https://regex101.com/"));
    }

    /**
     * Validate user input for social security number.
     * NO SOCIAL SECURITY ADMINISTRATION RULES
     * @param input
     * @return True if input is valid, false if input is not valid
     */
    public static boolean validateSocial(String input){
        Pattern pattern = Pattern.compile("^[0-9]{3} ?-? ?[0-9]{2} ?-? ?[0-9]{4}$");
        return pattern.matcher(input).matches();
    }

    /**
     *  US Phone number - parentheses are optional,
     *  as well as the dash between the last two sections
     * @param input
     * @return True if input is valid, false if input is not valid
     */
    public static boolean validatePhoneNumber(String input){
        Pattern pattern = Pattern.compile("^((\\([0-9]{3}\\))|([0-9]{3})) ?" +
                "-? ?[0-9]{3} ?-? ?[0-9]{4}$");
        return pattern.matcher(input).matches();
    }

    /**
     * E-mail address
     * @param input
     * @return True if input is valid, false if input is not valid
     */
    public static boolean validateEmail(String input){
        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[a-zA-Z]+\\.[a-zA-Z]{2,}$");
        return pattern.matcher(input).matches();
    }

    /**
     * Name on a class roster, assuming zero or more middle initials
     * - Last name, First name, MI (e.g. Smith, John, L
     * @param input
     * @return True if input is valid, false if input is not valid
     */
    public static boolean validateName(String input){
        Pattern pattern = Pattern.compile("^[A-Z'-]+[A-Za-z'-]+, ?[A-Z]['-]*[a-z'-]+" +
                "(, ?[A-Za-z'-])*$");
        return pattern.matcher(input).matches();
    }

    /**
     * Date in MM-DD-YYYY format - separators can be -'s, /'s -- you
     * must make sure months, days, year are valid (this includes leap years)
     * @param input
     * @return True if input is valid, false if input is not valid
     */
    public static boolean validateDate(String input){
        boolean validDate;
        Pattern pattern = Pattern.compile("^(0[1-9]|1[0-2])\\s*[-/]\\s*(0[1-9]|[12][0-9]|3[01])\\s*" +
                "[-/]\\s*\\d{4}$");
        boolean goodRegEx = pattern.matcher(input).matches();
        if(goodRegEx){
            validDate = isValidDate(input);
            if(!validDate){
                //System.out.println("This date is not valid");
                return false;
            }
        }
        return goodRegEx;
    }

    /**
     * Helper function for validateDate(). This function makes sure that outside of
     * regular expressions, that the date is actually a real calendar day.
     * @param input
     * @return True or false depending on whether date is real.
     */
    private static boolean isValidDate(String input){
        boolean valid = true;
        String[] parts = input.split("\\s*[-/]\\s*");
        int month = Integer.parseInt(parts[0]);
        int day = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        //System.out.println(month + " " + day + " " + year);
        int[] calendar = {31,28,31,30,31,30,31,31,30,31,30,31};
        boolean isLeap = ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0));
        if(isLeap){
            calendar[1] = 29;
        }
        if(month > 12 || month < 1 || (day < 1 || day > calendar[month - 1])){
            valid = false;
        }
        return valid;
    }

    /**
     * House address - Street number, street name, abbreviation for road, street,
     * boulevard or avenue.
     * @param input
     * @return True if input is valid, false if input is not valid
     */
    public static boolean validateAddress(String input){
        Pattern pattern = Pattern.compile("^\\d+\\s+[A-Za-z0-9'\\- ]+\\s+" +
                        "(St|Street|Rd|Road|Blvd|Boulevard|Ave|Avenue)$"
                , Pattern.CASE_INSENSITIVE);
        return pattern.matcher(input).matches();
    }

    /**
     * City followed by state followed by zip as
     * it should appear on a letter
     * @param input
     * @return True if input is valid, false if input is not valid
     */
    public static boolean cityStateZip(String input){
        Pattern pattern = Pattern.compile("^[A-Za-z ]{1,},? [A-Za-z]{2} \\d{5}(-\\d{4})?$");
        return pattern.matcher(input).matches();
    }

    /**
     * Military time (no colons used and
     * leading 0 is included for times under 10)
     * @param input
     * @return True if input is valid, false if input is not valid
     */
    public static boolean militaryTime(String input){
        Pattern pattern = Pattern.compile("^([0-1][0-9]|2[0-3])[0-5][0-9]$");
        return pattern.matcher(input).matches();
    }

    /**
     *US Currency down to the penny
     * (ex: $123,456,789.23)
     * @param input
     * @return True if input is valid, false if input is not valid
     */
    public static boolean UsCurrency(String input){
        Pattern pattern = Pattern.compile("^\\$\\d{1,3}(,?\\d{3})*\\.\\d{2}$");
        return pattern.matcher(input).matches();
    }

    /**
     *URL, optionally including http:// or
     *  https://, upper and lower case should be accepted
     * @param input
     * @return True if input is valid, false if input is not valid
     */
    public static boolean url(String input){
        Pattern pattern = Pattern.compile("^(http(s)?://)?([A-Za-z0-9]+[-]*\\.)+" +
                        "[A-Za-z]{2,63}(/[A-Za-z0-9/+=?%._&-]*)*$",
                Pattern.CASE_INSENSITIVE);
        return pattern.matcher(input).matches();
    }

    /**
     * A password that contains at least 10 characters and includes at
     * least one upper case character, one lower case character,
     * one digit, one punctuation mark, and does not have more than
     * 3 consecutive lower case characters
     * @param input
     * @return True if input is valid, false if input is not valid
     */
    public static boolean password(String input){
        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\p{Punct})" +
                "(?!.*[a-z]{4}).{10,}$");
        return pattern.matcher(input).matches();
    }

    /**
     *All words containing an odd number of alphabetic characters, ending in "ion"
     * @param input
     * @return True if input is valid, false if input is not valid
     */
    public static boolean oddIons(String input){
        Pattern pattern = Pattern.compile("^([A-Za-z]{2})*ion$");
        return pattern.matcher(input).matches();
    }
}