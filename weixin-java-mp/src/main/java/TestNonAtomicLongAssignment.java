/**
 * Created by qianjia on 15/1/25.
 */
public class TestNonAtomicLongAssignment {

  private static final long HI = 1l << 32;
  private static final long LO = 1l;

  private static final long TEST_NUMBER = HI | LO;

  private static long assignee = 0l;

  public static void main(String[] args) {

    Thread writer = new Thread(new Runnable() {
      @Override
      public void run() {
        while (true) {
          assignee = TEST_NUMBER;
        }
      }
    });
    writer.setDaemon(true);

    Thread reader = new Thread(new Runnable() {
      @Override
      public void run() {
        long i = 0;
        while (true) {
          i++;
          long test = assignee;
          if (test != TEST_NUMBER) {
            System.out.print(i + " times:" + toBin(test));
            break;
          }
        }
      }
    });

    //    Thread worker = new Thread(new Runnable() {
    //      @Override
    //      public void run() {
    //        double d = 89009808877238948224343435452333323113131313133434434341212323232424243434335354232390490189190420928348910913094983.323334401928d;
    //        while(true) {
    //          Math.cbrt(d);
    //          d = d - 1l;
    //        }
    //      }
    //    });
    //    worker.setDaemon(true);
    //    worker.start();

    writer.start();
    reader.start();

  }

  public static String toBin(long n) {
    StringBuilder sb = new StringBuilder(Long.toBinaryString(n));
    int padding = 64 - sb.length();
    while (padding > 0) {
      sb.insert(0, '0');
      padding--;
    }
    return sb.toString();
  }

}
