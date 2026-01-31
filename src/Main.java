import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

//        System.out.println(validateSocial("123-45-6789"));
//        System.out.println(validatePhoneNumber("901-558-9624"));
//        System.out.println(validateEmail("dweaed22134@yahoo.co"));
//        System.out.println(validateName("jimmy, butler, A"));
         //   System.out.println(validateDate("02-28-1995"));
        System.out.println(validateAddress("789 north main street"));
        System.out.println(cityStateZip("san diego, CA 98030"));
        System.out.println(militaryTime("0109"));
        System.out.println(UsCurrency("$57,555,000.36"));
        System.out.println(url("https://regex101.com/"));
    }

    /**
     * Validate user input for social security number.
     * NO SOCIAL SECURITY ADMINISTRATION RULES
     * @param input
     * @return
     */
    public static boolean validateSocial(String input){
        Pattern pattern = Pattern.compile("[0-9]{3} ?- ?[0-9]{2} ?- ?[0-9]{4}");
        return pattern.matcher(input).matches();
    }

    public static boolean validatePhoneNumber(String input){
        Pattern pattern = Pattern.compile("[0-9]{3} ?- ?[0-9]{3} ?- ?[0-9]{4}");
        return pattern.matcher(input).matches();
    }
    public static boolean validateEmail(String input){
        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[a-zA-Z]+\\.[a-zA-Z]{2,}$");
        return pattern.matcher(input).matches();
    }
    public static boolean validateName(String input){
        Pattern pattern = Pattern.compile("^[A-Z][a-z'-]+, ?[A-Z][a-z]+, ?[A-Za-z'-]*$");
        return pattern.matcher(input).matches();
    }

    public static boolean validateDate(String input){
        boolean validDate;
        Pattern pattern = Pattern.compile("^[0-9]{2} ?- ?[0-9]{2} ?- ?[0-9]{4}$");
        boolean goodRegEx = pattern.matcher(input).matches();
        if(goodRegEx){
            validDate = isValidDate(input);
            if(!validDate){
                System.out.println("This date is not valid");
                return false;
            }
        }
        return goodRegEx;
    }

    /**
     * Helper function for validateDate(). This function makes sure that outside of
     * regular expressions, that the date is actually a real calendar day.
     * @param input
     * @return
     */
    private static boolean isValidDate(String input){
        boolean valid = true;
        String[] parts = input.split("\\s*-\\s*");
        int month = Integer.parseInt(parts[0]);
        int day = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        System.out.println(month + " " + day + " " + year);
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

    public static boolean validateAddress(String input){
        Pattern pattern = Pattern.compile("^\\d+ [A-Za-z0-9'\\- ]+ [A-Za-z]+$");
        return pattern.matcher(input).matches();
    }

    public static boolean cityStateZip(String input){
        Pattern pattern = Pattern.compile("^[A-Za-z ]{1,},? [A-Za-z]{2} \\d{5}(-\\d{4})?$");
        return pattern.matcher(input).matches();
    }

    public static boolean militaryTime(String input){
        Pattern pattern = Pattern.compile("^([0-1][0-9]|2[0-3])[0-5][0-9]$");
        return pattern.matcher(input).matches();
    }

    public static boolean UsCurrency(String input){
        Pattern pattern = Pattern.compile("^\\$\\d{1,3}(,\\d{3})*\\.\\d{2}$");
        return pattern.matcher(input).matches();
    }

    public static boolean url(String input){
        Pattern pattern = Pattern.compile("^https?://([A-Za-z0-9-]+\\.)+[a-z]{2,63}(/[A-Za-z0-9/+=?%._&-]*)*$");
        return pattern.matcher(input).matches();
    }

    public static boolean password(String input){
        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\p{Punct})" +
                "(?!.*[a-z]{3}).{10,}$");
        return pattern.matcher(input).matches();
    }

    public static boolean oddIons(String input){
        return false;
    }
}