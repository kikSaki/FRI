import java.awt.Color;

class Sonce {

  private int x;
  private int y;
  private int r;
  private int n;

  Sonce(int x, int y, int r, int n) {
    this.x = x;
    this.y = y;
    this.r = r;
    this.n = n;
  }

  public void narisi() {
    StdDraw.setPenColor(Color.yellow);
    StdDraw.circle(this.x, this.y, this.r);

    int stKrakov = 2 * this.n;

    double kot = 0;

    double pX = 0;
    double pY = r;

    double tY = r * 2;
    double tX = 0;

    int kolko = 360 / stKrakov;

    for (int i = 0; i < stKrakov; i++) {

      double kotR = 2 * Math.PI * kot / 360;
      pX = r * Math.cos(kotR);
      pY = r * Math.sin(kotR);

      tX = 2 * r * Math.cos(kotR);
      tY = 2 * r * Math.sin(kotR);

      if (i % 2 == 0) {

        pX = (r + r / 3) * Math.cos(kotR);
        pY = (r + r / 3) * Math.sin(kotR);

        tX = 2 * (r + r / 3) * Math.cos(kotR);
        tY = 2 * (r + r / 3) * Math.sin(kotR);

        StdDraw.line(pX, pY, tX, tY);
      } else {
        StdDraw.line(pX, pY, tX, tY);
      }

      kot += kolko;

    }

  }

}

public class Risanje {

  static void kvadratki() {
    int n = 25; // stevilo kvadratkov v vsaki vrsti in stolpcu
    int s = 10; // velikost posameznega kvadratka

    int logo = 32837969;

    String k = Integer.toBinaryString(logo);

    String[] l = new String[5];

    for (int i = 0; i < 5; i++) {
      l[i] = k.substring(i * 5, i * 5 + 5);
    }

    StdDraw.setScale(0, 5);
    for (int i = 4; i >= 0; i--) {
      for (int j = 4; j >= 0; j--) {

        if (l[4 - i].charAt(4 - j) == '1') {
          StdDraw.setPenColor(Color.black);
        }
        // System.out.println(j + " " + i);
        StdDraw.filledRectangle(j + 0.5, i + 0.5, 0.5, 0.5);
        StdDraw.setPenColor(Color.white);
      }
    }

  }

  public static void main(String[] args) {

    // kvadratki();

    StdDraw.setScale(-100, 100);
    StdDraw.setPenRadius(0.01);
    Sonce s = new Sonce(0, 0, 30, 10);
    s.narisi();

  }
}
