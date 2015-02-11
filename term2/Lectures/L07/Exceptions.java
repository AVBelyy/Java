public class Exceptions {
    final static double EPS = 1e-16;

     class Complex {
        Complex divide(Complex c) throws DBZComplexException {
            double denominator = /*c.re * c.re + c.im * c.im*/ 0.0;
            // if denominator == 0 => +- Inf or NaN => not cool
            if (Math.abs(denominator) < EPS) {
                //throw new DBZComplexException(denominator);
            }
            return null;
        }
    }

    class DBZComplexException extends Exception {
        private final Complex divident;
        public DBZComplexException(Complex d) {
            this.divident = d;
        }
    }
    
    public static void main(String[] args) {
        // lol
        int a[] = new int[10];
        int index = 0;
        try {
            while(true) {
                System.out.println(a[index++]);
            }
        } catch(IndexOutOfBoundsException e) {
        }
    }
}
