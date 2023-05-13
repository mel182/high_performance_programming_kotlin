package wz_retrofit3_concept;

public class charTest {

    public static void main(String[] args) {

        String input = "This is the test value";
        int codePoint = 0;
        for (int i = 0, limit = input.length(); i < limit; i += Character.charCount(codePoint)) {

            codePoint = input.codePointAt(i);
            System.out.println("index: "+i);
            System.out.println("code point: "+codePoint);
            System.out.println("character char count: "+Character.charCount(codePoint));

        }



    }





}
