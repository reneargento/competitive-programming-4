package chapter1.section6.p.time.waster.problems.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 26/12/20.
 */
// Based on https://www.yuanmas.com/info/9war5xngzA.html
public class DoingWindows {

    private static class Window {
        long width;
        long height;

        public Window(long width, long height, boolean resizeToGcd) {
            this.width = width;
            this.height = height;

            if (resizeToGcd) {
                long gcd = gcd(width, height);
                this.width /= gcd;
                this.height /= gcd;
            }
        }

        void swapDimensions() {
            long aux = width;
            width = height;
            height = aux;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long screenWidth = scanner.nextLong();
        long screenHeight = scanner.nextLong();
        int setNumber = 1;

        while (screenWidth != 0 || screenHeight != 0) {
            Window screen = new Window(screenWidth, screenHeight, false);
            Window[] windows = new Window[4];
            for (int i = 0; i < windows.length; i++) {
                windows[i] = new Window(scanner.nextLong(), scanner.nextLong(), true);
            }

            boolean canCompletelyCover = canCompletelyCover(screen, windows);
            System.out.printf("Set %d: ", setNumber);
            System.out.println(canCompletelyCover ? "Yes" : "No");

            setNumber++;
            screenWidth = scanner.nextLong();
            screenHeight = scanner.nextLong();
        }
    }

    private static boolean canCompletelyCover(Window screen, Window[] windows) {
        if (tryToCover(screen, windows)) {
            return true;
        }
        screen.swapDimensions();
        for (int i = 0; i < 4; i++) {
            windows[i].swapDimensions();
        }
        return tryToCover(screen, windows);
    }

    private static boolean tryToCover(Window screen, Window[] windows) {
        if (check4WindowsStacked(screen, windows)) {
            return true;
        }

        if (check1WindowTopAnd3Below(screen, windows[0], windows[1], windows[2], windows[3])
                || check1WindowTopAnd3Below(screen, windows[0], windows[1], windows[3], windows[2])
                || check1WindowTopAnd3Below(screen, windows[0], windows[3], windows[2], windows[1])
                || check1WindowTopAnd3Below(screen, windows[3], windows[1], windows[2], windows[0])) {
            return true;
        }

        return check2WindowTop2Below(screen, windows[0], windows[1], windows[2], windows[3])
                || check2WindowTop2Below(screen, windows[0], windows[2], windows[1], windows[3])
                || check2WindowTop2Below(screen, windows[0], windows[3], windows[1], windows[2]);
    }

    private static boolean check4WindowsStacked(Window screen, Window[] windows) {
        int widthSum = 0;

        for (Window window : windows) {
            if (screen.height % window.height != 0) {
                return false;
            }
            widthSum += (screen.height / window.height) * window.width;
        }
        return widthSum == screen.width;
    }

    private static boolean check1WindowTopAnd3Below(Window screen, Window window1, Window window2, Window window3,
                                                    Window window4) {
        long widthSum = 0;

        if (screen.width % window4.width != 0) {
            return false;
        }
        long usedHeight = (screen.width / window4.width) * window4.height;
        if (usedHeight >= screen.height) {
            return false;
        }

        long remainingHeight = screen.height - usedHeight;
        if (checkIf3WindowsFitBothWays(screen.width, remainingHeight, window1, window2, window3)) {
            return true;
        }

        if (remainingHeight % window1.height != 0) {
            return false;
        }
        widthSum += (remainingHeight / window1.height) * window1.width;

        if (remainingHeight % window2.height != 0) {
            return false;
        }
        widthSum += (remainingHeight / window2.height) * window2.width;

        if (remainingHeight % window3.height != 0) {
            return false;
        }
        widthSum += (remainingHeight / window3.height) * window3.width;

        return widthSum == screen.width;
    }

    private static boolean checkIf3WindowsFitBothWays(long screenWidth, long screenHeight, Window window1,
                                                      Window window2, Window window3) {
        if (checkIf3WindowsFit(screenWidth, screenHeight, window1, window2, window3)) {
            return true;
        }
        Window window1WithSwappedDimensions = new Window(window1.height, window1.width, true);
        Window window2WithSwappedDimensions = new Window(window2.height, window2.width, true);
        Window window3WithSwappedDimensions = new Window(window3.height, window3.width, true);
        return checkIf3WindowsFit(screenHeight, screenWidth, window1WithSwappedDimensions, window2WithSwappedDimensions,
                window3WithSwappedDimensions);
    }

    private static boolean checkIf3WindowsFit(long screenWidth, long screenHeight, Window window1, Window window2,
                                              Window window3) {
        return check1WindowTopAnd2Below(screenWidth, screenHeight, window1, window2, window3)
                || check1WindowTopAnd2Below(screenWidth, screenHeight, window2, window1, window3)
                || check1WindowTopAnd2Below(screenWidth, screenHeight, window3, window1, window2);
    }

    private static boolean check1WindowTopAnd2Below(long screenWidth, long screenHeight, Window window1, Window window2,
                                                    Window window3) {
        if (screenHeight <= (screenWidth / window1.width) * window1.height) {
            return false;
        }
        long remainingHeight = screenHeight - (screenWidth / window1.width) * window1.height;

        long widthSum = 0;

        if (remainingHeight % window2.height != 0) {
            return false;
        }
        widthSum += (remainingHeight / window2.height) * window2.width;

        if (remainingHeight % window3.height != 0) {
            return false;
        }
        widthSum += (remainingHeight / window3.height) * window3.width;

        return widthSum == screenWidth;
    }

    private static boolean check2WindowTop2Below(Window screen, Window window1, Window window2, Window window3,
                                                 Window window4) {
        long widthSum = 0;

        long heightLCM = lcm(window1.height, window2.height);
        if (heightLCM >= screen.height) {
            return false;
        }

        widthSum += (heightLCM / window1.height) * window1.width;
        widthSum += (heightLCM / window2.height) * window2.width;

        if (screen.width % widthSum != 0) {
            return false;
        }
        if (screen.height <= (screen.width / widthSum) * heightLCM) {
            return false;
        }

        long remainingHeight = screen.height - (screen.width / widthSum) * heightLCM;
        widthSum = 0;

        if (remainingHeight % window3.height != 0) {
            return false;
        }
        widthSum += (remainingHeight / window3.height) * window3.width;

        if (remainingHeight % window4.height != 0) {
            return false;
        }
        widthSum += (remainingHeight / window4.height ) * window4.width;

        return screen.width == widthSum;
    }

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

    private static long lcm(long number1, long number2) {
        return number1 * (number2 / gcd(number1, number2));
    }
}
