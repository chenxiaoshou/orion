package commontest;

public class TestCase2 {

	public static void main(String[] args) {
		/*int a = -115;
		System.out.println(Integer.toBinaryString((byte)a));
		int b = 256 - (-(-115));
		System.out.println(b);
		System.out.println(Integer.toBinaryString((byte)b));*/
		
		double c = 0.33333333333333333333333333;
		double d = 0.33333333333333333333333334;
		double e = 0.3333333333333333;
		double f = 0.00000000000000000000000000000000000000333333333333333333333333333333333;
		double g = 0.00000000000000000000000000000000000000333333333333333333333333333333334;
		System.out.println(f > g);
		System.out.println(f < g);
		System.out.println(f == g);
	}

}
