package kaustubhdandekar.lib.javaio.benchmark;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kaustubhdandekar.lib.javaio.FastScanner;

import java.io.*;
import java.util.Scanner;

public class Test extends Application {

    private static final String INPUTPATH = "src\\kaustubhdandekar\\lib\\javaio\\benchmark\\input.in";
    private static AreaChart<String, Number> areachart;
    private static long[] finalS, finalB, finalF;

    public static void main(String[] args) {
        try {

            final int maxInputSize = 50;
//            final String data = "ABCDEFGHIJKLM123456789 ";    // For Read Test
//            final String data = "7894561237894561237 ";    // For Long Test
            final String data = "78945612378.94561237 ";    // For Double Test

            finalS = new long[maxInputSize];
            finalB = new long[maxInputSize];
            finalF = new long[maxInputSize];

            PrintWriter writer;
            StringBuilder stringBuilder = new StringBuilder();

            int i = 0;
            while (i < maxInputSize) {
                writer = new PrintWriter(new File(INPUTPATH));
                stringBuilder.append(data);
                writer.write(stringBuilder.toString());
                writer.close();

                /*
//                finalS[i] = scanner_readTest(i);
                finalB[i] = bufferedreader_readTest(i);
                finalF[i] = fastscanner_readTest(i);
                */

                /*
//                finalS[i] = scanner_longTest(i);
                finalB[i] = bufferedreader_longTest(i);
                finalF[i] = fastscanner_longTest(i);
                */

//                finalS[i] = scanner_doubleTest(i);
                finalB[i] = bufferedreader_doubleTest(i);
                finalF[i] = fastscanner_doubleTest(i);

                i++;
            }

            launch(args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


//    READ
    private static long scanner_readTest(int I) throws IOException{
        Scanner scanner = new Scanner(new FileInputStream(INPUTPATH));

        long x;
        int i = I;
        x = System.nanoTime();
        while(i-->0){
            String tmp = scanner.next();
        }
        x = System.nanoTime() - x;

        scanner.close();
        return x;
    }

    private static long bufferedreader_readTest(int I) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(INPUTPATH)));

        long x;
        int i = I;

        x = System.nanoTime();
        String[] buf = bufferedReader.readLine().split(" ");
        while (i-->0){
            String tmp = buf[i];
        }
        x = System.nanoTime() - x;

        bufferedReader.close();
        return x;
    }

    private static long fastscanner_readTest(int I) throws IOException{
        FastScanner fastScanner = new FastScanner(new FileInputStream(INPUTPATH));

        long x;
        int i = I;
        x = System.nanoTime();
        while(i-->0){
            String tmp = fastScanner.next();
        }
        x = System.nanoTime() - x;

        fastScanner.close();
        return x;
    }

//    DOUBLE
    private static long scanner_doubleTest(int I) throws IOException{
        Scanner scanner = new Scanner(new FileInputStream(INPUTPATH));

        long x;
        int i = I;

        x = System.nanoTime();
        while(i-->0){
            double tmp = scanner.nextDouble();
        }
        x = System.nanoTime() - x;

        scanner.close();
        return x;
    }

    private static long bufferedreader_doubleTest(int I) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(INPUTPATH)));
        long x;
        int i = I;

        x = System.nanoTime();
        String[] buf = bufferedReader.readLine().split(" ");
        while(i-->0) {
            double tmp = Double.parseDouble(buf[i]);
        }
        x = System.nanoTime() - x;

        bufferedReader.close();
        return x;
    }

    private static long fastscanner_doubleTest(int I) throws IOException{
        FastScanner fastScanner = new FastScanner(new FileInputStream(INPUTPATH));

        long x;
        int i = I;

        x = System.nanoTime();
        while(i-->0){
            double tmp = fastScanner.nextDoubleFast();
        }
        x = System.nanoTime() - x;

        fastScanner.close();
        return x;
    }

//    LONG
    private static long scanner_longTest(int I) throws IOException{
        Scanner scanner = new Scanner(new FileInputStream(INPUTPATH));

        long x;
        int i = I;

        x = System.nanoTime();

        while(i-->0){
            long tmp = scanner.nextLong();
        }

        x = System.nanoTime() - x;
        scanner.close();
        return x;
    }

    private static long bufferedreader_longTest(int I) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(INPUTPATH)));
        long x;
        int i = I;

        x = System.nanoTime();

        String[] buf = bufferedReader.readLine().split(" ");
        long[] tmp = new long[buf.length];
        while(i-->0) {
            tmp[i] = Long.parseLong(buf[i]);
        }
        x = System.nanoTime() - x;

        bufferedReader.close();
        return x;
    }

    private static long fastscanner_longTest(int I) throws IOException{
        FastScanner fastScanner = new FastScanner(new FileInputStream(INPUTPATH));

        long x;
        int i = I;

        x = System.nanoTime();

        while(i-->0){
            long tmp = fastScanner.nextLongFast();
        }

        x = System.nanoTime() - x;
        fastScanner.close();
        return x;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane view = new Pane();
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Input Size");
        xAxis.setAnimated(false);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Nano Seconds");

        areachart = new AreaChart<>(xAxis, yAxis);
        view.getChildren().add(areachart);

        primaryStage.setTitle("Performance Graph");
        primaryStage.setScene(new Scene(view));
        primaryStage.show();

        Platform.runLater(()->{

            XYChart.Series<String, Number> series;

            series = new XYChart.Series<>();
            series.setName("Scanner");
            for (int i = 0; i < finalS.length; i++)
                series.getData().add(new XYChart.Data<>(String.valueOf(i+1), finalB[i]));
            areachart.getData().add(series);
            series.getNode().setStyle("255, 0, 0");

            series = new XYChart.Series<>();
            series.setName("BufferedReader");
            for (int i = 0; i < finalB.length; i++)
                series.getData().add(new XYChart.Data<>(String.valueOf(i+1), finalB[i]));
            areachart.getData().add(series);
            series.getNode().setStyle("255, 0, 0");

            series = new XYChart.Series<>();
            series.setName("FastScanner");
            for (int i = 0; i < finalF.length; i++)
                series.getData().add(new XYChart.Data<>(String.valueOf(i+1), finalF[i]));
            areachart.getData().add(series);
            series.getNode().lookup(".chart-series-area-fill").setStyle("0, 0, 255");
        });
    }
}
