import java.text.DecimalFormat;

public class Main
{
    
    private static final String[] tensName = {"", " Ten", " Twenty", " Thirty", " Forty", " Fifty", " Sixty",
            " Seventy", " Eighty", " Ninety"};

    private static final String[] numName = {"", " One", " Two", " Three", " Four", " Five", " Six", " Seven",
            " Eight", " Nine", " Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen", " Sixteen",
            " Seventeen", " Eighteen", " Nineteen"};
    
	public static void main(String[] args) {
		System.out.println(convert(133456789.159));
	}
	
	public static String convert(double rupees){
        long rupee = (long) Math.floor(rupees);
        int paisa = (int) Math.floor((rupees - rupee) * 100);

        String s = rupeeToWord(rupee, true); 
        if(paisa != 0)
            s = s + "And " + rupeeToWord(paisa, false);
            
        s += "Only";

        return s.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }
    
    private static String rupeeToWord(long rupee, boolean isRupee) {
        if (rupee == 0){
            return "";
        }

        String snumber = Long.toString(rupee);
    

        // pad with "0"
        String mask = "000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(rupee);

        // nnnXXnnnnnnn
        int crores = Integer.parseInt(snumber.substring(0,2));
        // nnnnnXXnnnnn
        int lakhs  = Integer.parseInt(snumber.substring(2,4));
        // nnnnnnnXXnnn
        int thousands = Integer.parseInt(snumber.substring(4,6));
        // nnnnnnnnnXXX
        int hundreds = Integer.parseInt(snumber.substring(6,9));
        

        //Calculating for nnnXXnnnnnnn
        String tradCrores;
        switch (crores) {
            case 0:
                tradCrores = "";
                break;
            case 1 :
                tradCrores = "One Crore ";
                break;
            default :
                tradCrores = convertLessThanOneThousand(crores)
                        + " Crores ";
        }
        String result = tradCrores;
        
        //Calculating for nnnnnXXnnnnn
        String tradLakhs;
        switch (lakhs) {
            case 0:
                tradLakhs = "";
                break;
            case 1:
                tradLakhs = "One Lakh ";
                break;
            default:
                tradLakhs = convertLessThanOneThousand(lakhs) + " Lakhs ";
        }
        result = result + tradLakhs;
        
        //Calculating for nnnnnnnXXnnn
        String tradThousands;
        switch (thousands) {
            case 0:
                tradThousands = "";
                break;
            case 1:
                tradThousands = "One Thousand ";
                break;
            default:
                tradThousands = convertLessThanOneThousand(thousands) + " Thousands ";
        }
        result = result + tradThousands;
                
        //Calculating for nnnnnnnnnXXX
        String tradHundreds;
        switch (hundreds) {
            case 0:
                tradHundreds = "";
                break;
            case 1 :
                tradHundreds = "One Rupee ";
                break;
            default :
                if(isRupee){
                    tradHundreds = convertLessThanOneThousand(hundreds) + " Rupees ";
                }else{
                    tradHundreds = convertLessThanOneThousand(hundreds) + " Paisa ";
                }
        }
        result =  result + tradHundreds;

        
        // remove extra spaces!
        return result;
    }

    private static String convertLessThanOneThousand(int number) {
        String soFar;

        if (number % 100 < 20){
            soFar = numName[number % 100];
            number /= 100;
        }
        else {
            soFar = numName[number % 10];
            number /= 10;

            soFar = tensName[number % 10] + soFar;
            number /= 10;
        }
        if (number == 0) return soFar;
        return numName[number] + " Hundred" + soFar;
    }

}