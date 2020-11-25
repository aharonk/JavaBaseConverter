package archHW;

import java.util.*;

public class Main {
    //converts a single hex symbol into an int
    private static Integer hexIntToDec(Character entry) {
        Map<String, Integer> hex_list = Map.of(
                "A", 10,
                "B", 11,
                "C", 12,
                "D", 13,
                "E", 14,
                "F", 15);
        int cur_digit = 0;
        if (Character.isDigit(entry)) {
            cur_digit = Character.getNumericValue(entry);
        } else if (Character.isLetter(entry)) {
            String entry_str = Character.toString(entry);
            entry_str = entry_str.toUpperCase();
            cur_digit = hex_list.get(entry_str);
        }
        return  cur_digit;
    }

    //Converts dec number into hex symbol
    private static String decIntToHex(int entry) {
        Map<Integer, String> hex_list = Map.of(
                10, "A",
                11, "B",
                12, "C",
                13, "D",
                14, "E",
                15, "F");
        String cur_digit = "";
        if (entry < 10) {
            cur_digit = Integer.toString(entry);
        } else if (entry <= 16) {
            cur_digit = hex_list.get(entry);
        }
        return  cur_digit;
    }

    //converts decimal to any other base (<=16)
    private static String baseTenToX(int num, int new_base) {
        //Initialize variables
        int new_base_num, exponent = 0;
        StringBuilder final_number = new StringBuilder();

        //find largest power of new_base that fits in num
        for (int new_num = 0; new_num <= num; exponent++) {
            new_num = (int) Math.pow(new_base, exponent);
        }

        //going down from largest power, add num to string
        while (num > 0) {
            new_base_num = num / (int) Math.pow(new_base, exponent);
            num %= (int) Math.pow(new_base, exponent);
            exponent--;

            //convert num to hex if needed, add to string
            String new_base_num_str = decIntToHex(new_base_num);
            final_number.append(new_base_num_str);
        }
        return "0" + final_number;
    }

    //Converts any base (<=16) to decimal
    private static Double baseXToTen(String orig, int old_base) {
        int cur_digit, exponent = 0;
        double final_num = 0;

        // create REVERSED list of numbers
        List<Character> orig_list = new ArrayList<>();
        for (char ch : orig.toCharArray()) {
            orig_list.add(ch);
        }
        Collections.reverse(orig_list);
        for (Character entry : orig_list) {
            cur_digit = hexIntToDec(entry);
            final_num += (cur_digit * Math.pow(old_base, exponent));
            exponent++;
            orig_list.set(0, 'G');
        }
        return final_num;
    }

    //specifically converts decimal to binary
    private static String BaseTenToTwoBitwise(double number) {
        int large_num, cur_exp = 0;
        int num = (int) number;
        StringBuilder final_num = new StringBuilder();

        //Find largest power of 2 that fits
        for(int i = 0; Math.pow(2, i) <= num; i++) {
            cur_exp = i;
        }

        for (; cur_exp >= 0; cur_exp--) {
            int val = (int) Math.pow(2, cur_exp);

            //divide and mod by 2^power
            large_num = num >> cur_exp;
            num %= val;

            final_num.append(large_num);
        }
        return "0" + final_num;
    }

    //Driver code
    public static String baseConvert(String num, int old_base, int new_base) {
        //Initialization
        double num_base_ten;

        //Math
        if (old_base != 10) {
            num_base_ten = baseXToTen(num, old_base);
        } else {
            num_base_ten = Double.parseDouble(num);
        }

        //Outputs
        if (new_base == 10) {
            return (Double.toString(num_base_ten));
        } else if (new_base == 2) {
            return (BaseTenToTwoBitwise(num_base_ten));
        } else {
            return (baseTenToX((int) num_base_ten, new_base));
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int old_base, new_base;

        System.out.print("Number to convert? ");
        String num = input.next();
        System.out.print("Old Base? ");
        old_base = input.nextInt();
        System.out.print("New Base? ");
        new_base = input.nextInt();

        System.out.println(baseConvert(num, old_base, new_base));

        input.close();
    }
}