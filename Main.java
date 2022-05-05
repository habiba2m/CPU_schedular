package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

public class Main extends Application {

    String[] Array = new String[4];
    String type = new String();
    Button FCFS = new Button("First come first serve");
    Button SJF_Pre = new Button("Shortest job first preemptive");
    Button SJF_non = new Button("Shortest job first non preemptive");

    Button priority_pre = new Button("Priority preemptive");
    Button priority_non = new Button("Priority non preemptive");
    Button round_robin = new Button("Round Robin");

    public void start(Stage primaryStage) throws IOException {
        StackPane layout = new StackPane();
        VBox vBox = new VBox();
        vBox.setSpacing(6);
        vBox.getChildren().add(FCFS);
        vBox.getChildren().add(SJF_Pre);
        vBox.getChildren().add(SJF_non);
        vBox.getChildren().add(priority_pre);
        vBox.getChildren().add(priority_non);
        vBox.getChildren().add(round_robin);

        layout.getChildren().add(vBox);

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);


        FCFS.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    type = "FCFS";
                    display();
                } catch (Exception er1) {
                    er1.printStackTrace();
                }
            }
        }
        ));

        SJF_Pre.setOnMouseClicked(e -> {
            try {
                type = "SJF_pre";
                display();
            } catch (Exception er1) {
                er1.printStackTrace();

            }
        });
        SJF_non.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    type = "SJF_non";
                    display();
                } catch (Exception er1) {
                    er1.printStackTrace();

                }
            }
        }));
        priority_pre.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    type = "priority_pre";
                    display();
                } catch (Exception er1) {
                    er1.printStackTrace();

                }
            }
        }));
        priority_non.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                try {
                    type = "priority_non";
                    display();

                } catch (Exception er1) {
                    er1.printStackTrace();

                }
            }

        }
        ));
        round_robin.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    type = "RR";
                    display();
                } catch (Exception er1) {
                    er1.printStackTrace();
                }
            }

        }
        ));
        primaryStage.show();
    }


    public void display() {
        String[] arr = new String[4];
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        StackPane layout = new StackPane();
        VBox vBox = new VBox();
        vBox.setSpacing(6);
        TextField name = new TextField();
        name.setPromptText("Processes names");
        name.setFocusTraversable(false);
        TextField burstTimes = new TextField();
        burstTimes.setPromptText("Burst times");
        burstTimes.setFocusTraversable(false);
        TextField arrivalTimes = new TextField();
        arrivalTimes.setPromptText("Arrival times");
        arrivalTimes.setFocusTraversable(false);
        TextField temp = new TextField();
        if (type == "priority_pre" || type == "priority_non") {
            temp.setPromptText("Priority");
        } else if (type == "RR") {
            temp.setPromptText("Quantum");
        }

        temp.setFocusTraversable(false);
        Button gantt = new Button("Gantt chart");
        Button avg_wait = new Button("Average Waiting time");
        if (type == "priority_pre" || type == "priority_non" || type == "RR") {
            vBox.getChildren().addAll(name, burstTimes, arrivalTimes, temp, avg_wait, gantt);
        } else {
            vBox.getChildren().addAll(name, burstTimes, arrivalTimes, avg_wait, gantt);
        }
        layout.getChildren().add(vBox);
        Scene scene = new Scene(layout, 400, 400);
        window.setScene(scene);
        window.show();

        avg_wait.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    Array[0] = name.getText();
                    Array[1] = burstTimes.getText();
                    Array[2] = arrivalTimes.getText();

                    if (type == "FCFS") {
                        time_display(avg_waiting(Array,6));
                    } else if (type == "SJF_pre") {
                        time_display(avg_waiting(Array,2));
                    } else if (type == "SJF_non") {
                        time_display(avg_waiting(Array,3));
                    } else if (type == "priority_pre") {
                        Array[3] = temp.getText();
                        time_display(avg_waiting(Array,1));
                    } else if (type == "priority_non") {
                        Array[3] = temp.getText();
                        time_display(avg_waiting(Array,5));
                    } else if (type == "RR") {
                        Array[3] = temp.getText();
                        time_display(avg_waiting(Array,4));
                    }

                } catch (Exception er1) {
                    er1.printStackTrace();
                }
            }
        }
        ));
        gantt.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    Array[0] = name.getText();
                    Array[1] = burstTimes.getText();
                    Array[2] = arrivalTimes.getText();

                    if (type == "FCFS") {
                        String[] temp = (names(Array)).toArray(new String[0]);
                        gantt_display(

                                names(Array).toArray(new String[0]),
                                FCFS(Array)[0],
                                FCFS(Array)[1]

                        );
                    } else if (type == "SJF_pre") {
                        gantt_display(
                                names(Array).toArray(new String[0]),
                                preemtive_SJF(Array)[0],
                                preemtive_SJF(Array)[1]
                        );
                    } else if (type == "SJF_non") {
                        gantt_display(
                                names(Array).toArray(new String[0]),
                                nonpreemtive_SJF(Array)[0],
                                nonpreemtive_SJF(Array)[1]
                        );
                    } else if (type == "priority_pre") {
                        Array[3] = temp.getText();
                        gantt_display(
                                names(Array).toArray(new String[0]),
                                priority_preemptive(Array)[0],
                                priority_preemptive(Array)[1]
                        );
                    } else if (type == "priority_non") {
                        Array[3] = temp.getText();
                        gantt_display(
                                names(Array).toArray(new String[0]),
                                NP_Priority(Array)[0],
                                NP_Priority(Array)[1]

                        );
                    } else if (type == "RR") {
                        Array[3] = temp.getText();
                        gantt_display(
                                names(Array).toArray(new String[0]),
                                roundRobin(Array)[0],
                                roundRobin(Array)[1]
                        );
                    }

                } catch (Exception er1) {
                    er1.printStackTrace();
                }
            }
        }
        ));

//        return arr;
    }

    public void time_display(float x) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        BorderPane layout = new BorderPane();
        window.setTitle("Average waiting time");
        Text t;
        t = new Text(String.valueOf(x));
        layout.setCenter(t);
        Scene scene = new Scene(layout, 400, 400);
        window.setScene(scene);
        window.showAndWait();

    }

    public void gantt_display(String[] arr, String[] processes, String[] duration) throws FileNotFoundException {
        System.out.println("before");

        JFreeChart chart = ChartFactory.createGanttChart("Scheduler", "Processes", "Time", createDataset(arr, processes, duration),
                true, true, false);
        System.out.println("after");

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        String userDirectory = System.getProperty("user.dir");
        try {
            ChartUtilities.saveChartAsJPEG(new File(userDirectory + "/chart.jpeg"), chart, 500, 300);

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Image image = new Image(new FileInputStream(userDirectory + "/chart.jpeg"));

        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(50);
        imageView.setY(25);

        //setting the fit height and width of the image view
        imageView.setFitHeight(300);
        imageView.setFitWidth(500);

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);
        String [][] arr2d = new String[2][processes.length];
        for(int i = 0; i <processes.length; i++){
            arr2d[0][i] = processes[i];
            arr2d[1][i] = duration[i];
        }
        ScrollPane scrollPane = new ScrollPane();
        VBox root = new VBox();
//        root.setSpacing(10);
        Text text0 = new Text();
        String text = clean_text(arr2d);
        text0.setText(text);
        text0.setX(250);
        text0.setY(350);
        root.getChildren().add(imageView);
        root.getChildren().add(text0);
        scrollPane.setContent(root);

        Scene scene = new Scene(scrollPane, 600, 400);

        window.setScene(scene);
        window.show();

    }

    public int arrIndex(String[] arr, String s) {
        int res = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(s)) {
                res = i;
                break;
            }
        }
        return res;
    }

    private IntervalCategoryDataset createDataset(String[] arr, String[] processes, String[] duration) {

        TaskSeriesCollection dataset = new TaskSeriesCollection();
        TaskSeries[] series = new TaskSeries[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            series[i] = new TaskSeries(arr[i]);
        }
        series[arr.length] = new TaskSeries("idle");
        for (int i = 0; i < processes.length; i++) {
            if (arrIndex(arr, processes[i]) == -1) {
                series[arr.length].add(new Task(Integer.toString(i),
                        new SimpleTimePeriod((long)Float.parseFloat(duration[i].substring(0, duration[i].indexOf(":"))),
                                (long) Float.parseFloat(duration[i].substring(duration[i].indexOf(":") + 1)))));
            } else {
                series[arrIndex(arr, processes[i])].add(new Task(Integer.toString(i),
                        new SimpleTimePeriod((long)Float.parseFloat(duration[i].substring(0, duration[i].indexOf(":"))),
                                (long) Float.parseFloat(duration[i].substring(duration[i].indexOf(":") + 1)))));

            }
        }
        for (int i = 0; i < arr.length + 1; i++) {
            dataset.add(series[i]);
        }

        return dataset;

    }

    public static void main(String[] args) {
        launch();
    }
    ////////////////////////////////////////////////////////////
    public static ArrayList <String> names(String[]s){
        ArrayList <String> d=new ArrayList <String>();
        ArrayList <process> c= toProcess("sjf",s);
        for(int i=0;i<c.size();i++){
            d.add(c.get(i).name);
        }
        return d;
    }

    public static ArrayList <process> toProcess(String type,String [] s){
        ArrayList <process> processes= new ArrayList <process>();
        String n="";
        for(int j=0;j<s[0].length();j++){
            if(s[0].charAt(j)!=',') n=n+s[0].charAt(j);
            else if(s[0].charAt(j)==','){
                process d= new process();
                d.name=n;
                processes.add(d);
                n="";
            }
            if(j==s[0].length()-1){
                process d= new process();
                d.name=n;
                processes.add(d);
                n="";
            }
        }
        double time;
        int k=0,p=0;
        for(int j=0;j<s[1].length();j++){
            if(j==s[1].length()-1){
                time=Double.parseDouble(s[1].substring(k));
                processes.get(p).burst_time=time;
            }
            if(s[1].charAt(j)==',') {
                time=Double.parseDouble(s[1].substring(k, j));
                processes.get(p).burst_time=time;
                k=j+1;
                p++;
            }
        }
        time=0;k=0;p=0;
        for(int j=0;j<s[2].length();j++){
            if(j==s[2].length()-1){
                time=Double.parseDouble(s[2].substring(k));
                processes.get(p).arrival_time=time;
            }
            if(s[2].charAt(j)==',') {
                time=Double.parseDouble(s[2].substring(k, j));
                processes.get(p).arrival_time=time;
                k=j+1;
                p++;
            }
        }
        if(type=="priority"){
            int pr=0;k=0;p=0;
            for(int j=0;j<s[3].length();j++){
                if(j==s[3].length()-1){
                    pr=Integer.parseInt(s[3].substring(k));
                    processes.get(p).priority=pr;
                }
                if(s[3].charAt(j)==',') {
                    pr=Integer.parseInt(s[3].substring(k, j));
                    processes.get(p).priority=pr;
                    k=j+1;
                    p++;
                }
            }
        }
        else if(type=="RR"){
            double q=Double.parseDouble(s[3]);
            for(int i=0;i<processes.size();i++){
                processes.get(i).quantum=q;
            }
        }

        return processes;
    }

    static class compare_arr implements Comparator<process>
    {
        public int compare(process p1, process p2)
        {
            if (p1.arrival_time < p2.arrival_time) return -1;
            if (p1.arrival_time > p2.arrival_time) return 1;
            else return 0;
        }
    }

    static class compare_burst implements Comparator<process>
    {
        public int compare(process p1, process p2)
        {
            if (p1.burst_time < p2.burst_time) return -1;
            if (p1.burst_time > p2.burst_time) return 1;
            else return 0;
        }
    }

    //Round robin
    public static String[][] roundRobin(String [] in )
    {


        ArrayList<process> proc = toProcess("RR",in);
        Collections.sort(proc, new psort_arrival());
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> times = new ArrayList<String>();

        String p [] = new String [proc.size()];
        double a []  = new double [proc.size()];
        double b []  = new double [proc.size()];
        for (int i = 0; i < proc.size(); i++){
            p[i] = proc.get(i).name;
            a[i] = proc.get(i).arrival_time;
            b[i] = proc.get(i).burst_time;
        }
        double n = proc.get(0).quantum;

        //String p[], double a[],
        //                      double b[], double n
        // result of average times
        double res = 0;
        double resc = 0;

        // for sequence storage
        String seq = new String();
        String timeline = new String();

        // copy the burst array and arrival array
        // for not effecting the actual array
        double res_b[] = new double[b.length];
        double res_a[] = new double[a.length];

        for (int i = 0; i < res_b.length; i++) {
            res_b[i] = b[i];
            res_a[i] = a[i];
        }

        // critical time of system
        double t = 0;
        int counter = 0;
        // for store the waiting time
        double w[] = new double[p.length];

        // for store the Completion time
        double comp[] = new double[p.length];

        while (true) {
            boolean flag = true;
            for (int i = 0; i < p.length; i++) {
                boolean trial = true;
                // these condition for if
                // arrival is not on zero

                // check that if there come before qtime
                if (res_a[i] <= t) {
                    if (res_a[i] <= 100) {
                        if (res_b[i] > 0) {
                            flag = false;
                            if (res_b[i] > n) {

                                // make decrease the b time
                                t = t + n;
                                res_b[i] = res_b[i] - n;

                                timeline += t-n +":" + t +" ";

                                res_a[i] = res_a[i] + n;
                                seq += " " + p[i];
                                counter++;
                                names.add(p[i]);
                                times.add(t-n +":" + t +" ");

                            }
                            else {

                                // for last time
                                t = t + res_b[i];
                                double u = res_b[i];

                                // store comp time
                                comp[i] = t - a[i];

                                // store wait time
                                w[i] = t - b[i] - a[i];
                                res_b[i] = 0;

                                // add sequence
                                seq += " " + p[i];
                                timeline += t-u +":" + t +" ";
                                counter++;
                                names.add(p[i]);
                                times.add(t-u +":" + t +" ");
                            }
                        }
                    }
                }

                // if no process is come on thse critical
                else if (res_a[i] > t) {
                    for (int j = 0; j < i ;j++){

                        if ( res_b[j] !=0 && j<i){
                            flag = false;
                            if (res_b[j] > n) {

                                // make decrease the b time
                                t = t + n;
                                res_b[j] = res_b[j] - n;

                                timeline += t-n +":" + t +" ";

                                res_a[j] = res_a[j] + n;
                                seq += " " + p[j];
                                counter++;
                                names.add(p[j]);
                                times.add(t-n +":" + t +" ");
                                trial = false;


                            }
                            else {
                                trial = false;
                                // for last time
                                t = t + res_b[j];
                                double u = res_b[j];

                                // store comp time
                                comp[j] = t - a[j];

                                // store wait time
                                w[j] = t - b[j] - a[j];
                                res_b[j] = 0;

                                // add sequence
                                seq += " " + p[j];
                                timeline += t-u +":" + t +" ";
                                counter++;
                                names.add(p[j]);
                                times.add(t-u +":" + t +" ");

                            }
                        }
                    }

                    if (!trial){
                        i--;
                    }




                    if (trial){
                        double pop =t;
                        t++;
                        i--;
                        seq += " idle";
                        timeline += pop +":" + t +" ";
                        counter++;
                        names.add("idle");
                        times.add(pop +":" + t +" ");}
                }
            }
            // for exit the while loop
            if (flag) {
                break;
            }
        }

        System.out.println("name  ctime  wtime");
        for (int i = 0; i < p.length; i++) {
            System.out.println(" " + p[i] + "    " + comp[i]
                    + "    " + w[i]);

            res = res + w[i];
            resc = resc + comp[i];
        }

        System.out.println("Average waiting time is "
                + (float)res / p.length);
        System.out.println("Average compilation  time is "
                + (float)resc / p.length);
        System.out.println("Sequence is like that " + seq);
        System.out.println("Timeline is like that " + timeline);
        String[][] twoD_arr = new String[2][names.size()];
        for(int i=0;i<names.size();i++){
            twoD_arr[0][i] = names.get(i);
        }
        for(int i=0;i<times.size();i++){
            twoD_arr[1][i] = times.get(i);
        }
        return twoD_arr;
    }


    public  static String[][] NP_Priority(String[]s)
    {

        ArrayList <process> p = toProcess("priority", s);
        process[] process = new process[p.size()];
        for(int k = 0 ; k < p.size() ; k++) {
            process[k] = p.get(k);
        }


        double []waitingTime=new double[process.length] ;
        double []turnaroundTime=new double[process.length];

        int CPU=0;
        String[][]g=new String[2][process.length];
        double[]finishtime=new double[process.length];
        int NOP=process.length;
        double LAT = 0; //LastArrivalTime
        for (int i = 0; i < process.length; i++)
            if (process.length > LAT)
                LAT = process[i].arrival_time;

        int MAX_P = 0; //Max Priority
        for (int i = 0; i < process.length; i++)
            if (process[i].priority > MAX_P)
                MAX_P = process[i].priority;

        int ATi = 0;     //Pointing to Arrival Time indix
        int P1 = process[0].priority; //Pointing to 1st priority Value
        int P2 ; //Pointing to 2nd priority Value

        //finding the First Arrival Time and Highest priority Process
        int j = -1;
        while (NOP> 0 && CPU <= 1000)
        {
            for (int i = 0; i < process.length; i++)
            {
                if ((process[i].arrival_time <= CPU) && (process[i].arrival_time != (LAT + 10)))
                {
                    if (process[i].priority != (MAX_P + 1))
                    {
                        P2 = process[i].priority;
                        j = 1;

                        if (P2 < P1)
                        {
                            j = 1;
                            ATi = i;
                            P1 = process[i].priority;
                            // P2 = PPt[i];
                        }

                    }
                }
            }

            if (j == -1)
            {
                CPU = CPU + 1;
                //continue;
            }
            else
            {

                waitingTime[ATi] =  CPU - process[ATi].arrival_time;
                CPU = CPU +(int)(process[ATi].burst_time);
                finishtime[ATi]=CPU;
                turnaroundTime[ATi] = CPU - process[ATi].arrival_time;
                process[ATi].arrival_time = LAT + 10;
                j = -1;
                process[ATi].priority = MAX_P + 1;
                ATi = 0;        //Pointing to Arrival Time index
                P1 = MAX_P + 1; //Pointing to 1st priority Value
                // P2 = MAX_P + 1; //Pointing to 2nd priority Value
                NOP = NOP - 1;
            }




        }

        String[][]x=new String[2][100];

        //creating 2d array
        for (int i = 0; i < process.length; i++)
        {
            g[0][i]="p"+(i+1);
            g[1][i]=(float)((finishtime[i]-process[i].burst_time))+":"+(float)(finishtime[i])+" ";

        }

        //sorting
        int u=0;
        int c;
        while(u<process.length-1){
            c=0;
            while(c<process.length-u-1){

                if(finishtime[c+1]<finishtime[c]){
                    double r=process[c].burst_time;
                    process[c].burst_time=process[c+1].burst_time;
                    process[c+1].burst_time=r;
                    double t=finishtime[c];
                    finishtime[c]=finishtime[c+1];
                    finishtime[c+1]=t;
                    String temp=g[0][c];
                    g[0][c]=g[0][c+1];
                    g[0][c+1]=temp;
                    String temp1=g[1][c];
                    g[1][c]=g[1][c+1];
                    g[1][c+1]=temp1;
                }

                c++;

            }
            u++;

        }
        //check on ideal period
        int y=0;
        if((finishtime[0]-process[0].burst_time)!=0){
            x[0][0]="ideal";
            x[1][0]=""+(float)0+":"+(float)((finishtime[0]-process[0].burst_time));
            y++;
        }
        for(int i=0;i<process.length;i++){
            x[0][y]=g[0][i];
            x[1][y]=g[1][i];
            if(i<process.length-1){
                if((finishtime[i+1]-process[i+1].burst_time)==finishtime[i]){
                    y++;
                    continue;
                }
                else{
                    y++;
                    x[0][y]="ideal";
                    x[1][y]=""+(float)(finishtime[i])+":"+(float)((finishtime[i+1]-process[i+1].burst_time));

                }
            }
            y++;
        }



        double AvgWT = 0;  //Average waiting time
        double AVGTaT = 0; // Average Turn around time
        for (int i = 0; i < process.length; i++)
        {
            AvgWT = waitingTime[i] + AvgWT;
            AVGTaT = turnaroundTime[i] + AVGTaT;
        }

        //  System.out.println( "Average waiting time = " + Math.round((AvgWT/ process.length)*100.0)/100.0);
        // System.out.println("Average turnaround time = " + Math.round((AVGTaT/ process.length)*100.0)/100.0 + "\n");
        String [][]t=new String[2][y];
        for(int i=0;i<y;i++){
            t[0][i]=x[0][i];
            t[1][i]=x[1][i];

        }
        return t;
    }


    //nonpreemtive SJF scheduler
    public static String[][] nonpreemtive_SJF(String[] s) {

        ArrayList<process> p = toProcess("SJF", s);
        process[] processes = new process[p.size()];
        for (int k = 0; k < p.size(); k++) {
            processes[k] = p.get(k);
        }
        compare_arr sarr = new compare_arr();
        Arrays.sort(processes, sarr);
//        System.out.println("processes:");
//        for (int i = 0; i < processes.length; i++) {
//            System.out.println(processes[i].name + " " + processes[i].arrival_time + " " + processes[i].burst_time);
//        }
        ArrayList<process> timeline = new ArrayList<process>();
        ArrayList<process> remain = new ArrayList<process>();
        double time = 0;
        int i = 0;
        remain.add(processes[i]);
        if (time < processes[i].arrival_time) {
            timeline.add(new process("idle", 0, processes[i].arrival_time - time));
            time = time + (processes[i].arrival_time - time);
        }
        while (i < processes.length-1) {

            for (int j = i + 1; j < processes.length; j++) {
                if (processes[j].arrival_time <= time) {
                    remain.add(processes[j]);
                    i++;
                } else {
                    break;
                }
            }
            if (remain.isEmpty()) {
                if (time < processes[i + 1].arrival_time) {
                    timeline.add(new process("idle", 0, processes[i + 1].arrival_time - time));
                    time = time + (processes[i + 1].arrival_time - time);
                }
            } else {
                compare_burst soarr = new compare_burst();
                Collections.sort(remain, soarr);
                timeline.add(remain.get(0));
                time = time + remain.get(0).burst_time;
                remain.remove(0);
            }
        }
        if(!remain.isEmpty()) {
            compare_burst soarr = new compare_burst();
            Collections.sort(remain, soarr);
            for(int k = 0 ; k < remain.size() ; k++) {
                timeline.add(remain.get(k));
                time = time + remain.get(k).burst_time;
            }

        }

        return conv_to_2d(timeline);
    }


    //preemtive SJF scheduler
    public static String[][] preemtive_SJF(String[] s) {
        ArrayList<process> p = toProcess("SJF", s);
        process[] processes = new process[p.size()];
        for (int k = 0; k < p.size(); k++) {
            processes[k] = p.get(k);
        }
        compare_arr sarr = new compare_arr();
        Arrays.sort(processes, sarr);
        System.out.println("processes:");
        for (int i = 0; i < processes.length; i++) {
            System.out.println(processes[i].name + " " + processes[i].arrival_time + " " + processes[i].burst_time);
        }
        ArrayList<gantt> timeline = new ArrayList<gantt>();
        List<pro_order> remain = new ArrayList<pro_order>();
        int i = 0;
        double time = 0;
        if (time < processes[i].arrival_time) {
            timeline.add(new gantt("idle", processes[i].arrival_time - time));
            time = time + (processes[i].arrival_time - time);
        }
        int j = 1;
        for (j = 1; j < processes.length; j++) {
            if ((time + processes[i].burst_time > processes[j].arrival_time) && (processes[i].burst_time != 0)) {
                processes[i].burst_time = processes[i].burst_time - (processes[j].arrival_time - time);
                if (processes[i].burst_time <= processes[j].burst_time) {
                    remain.add(new pro_order(processes[j], j));
                    if ((processes[j].arrival_time - time) != 0) {
                        timeline.add(new gantt(processes[i].name, processes[j].arrival_time - time));
                    }
                    time = time + (processes[j].arrival_time - time);
                } else {
                    if ((processes[j].arrival_time - time) != 0) {
                        timeline.add(new gantt(processes[i].name, processes[j].arrival_time - time));
                    }
                    if (processes[i].burst_time != 0) {
                        remain.add(new pro_order(processes[i], i));
                    }
                    i = j;
                    time = time + (processes[j].arrival_time - time);

                }
            } else {
                double t = time + processes[i].burst_time;
                if (processes[i].burst_time != 0) {
                    timeline.add(new gantt(processes[i].name, processes[i].burst_time));
                }
                if (time + processes[i].burst_time == processes[j].arrival_time) {
                    remain.add(new pro_order(processes[j], j));
                }
                time = time + processes[i].burst_time;
                if (remain.isEmpty()) {
                    if (time < processes[j].arrival_time) {
                        timeline.add(new gantt("idle", processes[j].arrival_time - time));
                        time = time + (processes[j].arrival_time - time);
                        i = j;
                    }
                } else {
                    Collections.sort(remain);
                    //i = first value in remain;
                    i = remain.get(0).o;
                    remain.remove(0);
                    if (t < processes[j].arrival_time) {
                        j--;
                    }

                }
            }
        }
        if(processes[i].burst_time !=0) {
            timeline.add(new gantt(processes[i].name, processes[i].burst_time));
        }
        Collections.sort(remain);
        for (int k = 0; k < remain.size(); k++) {
            String name = remain.get(k).p.name;
            Double t = remain.get(k).p.burst_time;
            System.out.println(name + " " + t);
            timeline.add(new gantt(name, t));

        }
        System.out.println("timeline");
        //loop.
        for (int k = 0; k < timeline.size(); k++) {
            System.out.println(timeline.get(k).name + " " + timeline.get(k).time);

        }
        return conv_to_2dgantt(timeline);
    }




    private static String[][] conv_to_2d(ArrayList<process> timeline) {
        String[][] two_D = new String[2][timeline.size()];
        double time = 0;
        for (int i = 0; i < timeline.size(); i++) {
            two_D[0][i] = timeline.get(i).name;
            two_D[1][i] = time + ":" + (time + timeline.get(i).burst_time); //String.valueOf(timeline.get(i).time);
            time = time + timeline.get(i).burst_time;
        }
        return two_D;

    }

    private static String[][] conv_to_2dgantt(ArrayList<gantt> timeline) {
        String[][] two_D = new String[2][timeline.size()];
        double time = 0;
        for (int i = 0; i < timeline.size(); i++) {
            two_D[0][i] = timeline.get(i).name;
            two_D[1][i] = time + ":" + (time + timeline.get(i).time); //String.valueOf(timeline.get(i).time);
            time = time + timeline.get(i).time;
        }
        return two_D;
    }
    // preemptive priority
    public  static String[][] priority_preemptive(String []in) {
        ArrayList<process> p=toProcess("priority",in);
        ArrayList<gantt> g = new ArrayList<gantt>();
        ArrayList<remain> v = new ArrayList<remain>();
//        ArrayList<process> pr = new ArrayList<process>();
//        ArrayList<Integer> no = new ArrayList<Integer>();
        double ti=p.get(0).arrival_time;
        boolean eq=true;
        for(int i=1;i<p.size();i++){
            if(ti!= p.get(i).arrival_time){
                eq=false;
                break;
            }
        }

        if(eq){
            Collections.sort(p, new psort_priority());
            double time=0;
            if(p.get(0).arrival_time!=0) {
                gantt f = new gantt();
                f.name = "idle";
                f.time = p.get(0).arrival_time;
                g.add(f);
                time += p.get(0).arrival_time;
            }

            for (int i = 0; i < p.size(); i++) {
                gantt f = new gantt();
                f.name = p.get(i).name;
                f.time = p.get(i).burst_time + time;
                g.add(f);
                time += p.get(i).burst_time;
            }
        }
        else{
            Collections.sort(p, new psort_arrival());
            double sum = 0;
            for (int i = 0; i < p.size(); i++) {
                sum += p.get(i).burst_time;
            }
            double total_time = 0;
            double first;
            double max = p.get(p.size() - 1).arrival_time;
            int c = 0;
            while (total_time < sum) {
                if( p.get(0).arrival_time==0){
                    gantt a = new gantt();
                    a.name = p.get(0).name;
                    g.add(a);

                    first=0;}
                else{
                    gantt l = new gantt();
                    l.name = "idle";
                    l.time=p.get(0).arrival_time;
                    total_time=p.get(0).arrival_time;
                    g.add(l);
                    gantt a = new gantt();
                    a.name = p.get(0).name;
                    g.add(a);

                    first=total_time;
                }
                for (int i = 1; i < p.size(); i++) {
                    if(p.get(i).arrival_time==p.get(c).arrival_time){
                        if(p.get(c).priority<p.get(i).priority){
                            remain j = new remain();
                            process f=new process();
                            f.name = p.get(i).name;
                            f.arrival_time = p.get(i).arrival_time;
                            f.burst_time = p.get(i).burst_time;
                            f.priority = p.get(i).priority;
                            j.p=f;
                            j.index=i;
                            v.add(j);
                            continue;
                        }

                    }
                    total_time = p.get(i).arrival_time;
                    if (total_time == max) {
                        break;
                    }
                    if (p.get(c).burst_time == p.get(i).arrival_time - first) {
                        g.get(g.size() - 1).time = p.get(i).arrival_time;
//                 total_time=p.get(i).arrival_time;
                        Collections.sort(v, new sort_arrival());
                        Collections.sort(v, new sort_priority());
                        if (!v.isEmpty() && v.get(0).p.priority < p.get(i).priority) {
                            c = v.get(0).index;
                            first= total_time;
                            gantt w = new gantt();
                            w.name = v.get(0).p.name;
                            g.add(w);
                            //1111
                            v.remove(0);

                            remain j = new remain();
                            process f=new process();
                            f.name = p.get(i).name;
                            f.arrival_time = p.get(i).arrival_time;
                            f.burst_time = p.get(i).burst_time;
                            f.priority = p.get(i).priority;
                            j.p=f;
                            j.index=i;
                            v.add(j);

                            continue;

                        } else {
                            c = i;
                            first= total_time;
                            first=p.get(i).arrival_time;
                            gantt w = new gantt();
                            w.name = p.get(i).name;
                            g.add(w);
                            continue;
                        }
                    } else if (p.get(c).burst_time < p.get(i).arrival_time - first) {
                        double y = (p.get(i).arrival_time - first) - p.get(c).burst_time;
                        g.get(g.size() - 1).time = total_time - (p.get(i).arrival_time - p.get(c).burst_time);
                        Collections.sort(v, new sort_arrival());
                        Collections.sort(v, new sort_priority());

                        if (v.isEmpty()) {
                            gantt u = new gantt();
                            u.name = "idle";
                            u.time=total_time;
                            g.add(u);
                            gantt q = new gantt();
                            q.name = p.get(i).name;
                            g.add(q);
                            c = i;
                            first= total_time;

                        } else {
                            Collections.sort(v, new sort_arrival());
                            Collections.sort(v, new sort_priority());
                            gantt u = new gantt();
                            u.name = v.get(0).p.name;
                            c=v.get(0).index;
                            first= total_time-y;
                            if(v.get(0).p.burst_time==y){
                                u.time = total_time;
                                g.add(u);
                                v.remove(0);

                                if(!v.isEmpty() && v.get(0).p.priority<p.get(i).priority){
                                    gantt o= new gantt();
                                    o.name=v.get(0).p.name;
                                    c=v.get(0).index;
                                    first= total_time-y;
                                    v.get(0).p.burst_time-=y;
                                    g.add(o);
                                    remain d=new remain();
                                    process f=new process();
                                    f.name=p.get(i).name;
                                    f.arrival_time=p.get(i).arrival_time;
                                    f.burst_time=p.get(i).burst_time;
                                    f.priority=p.get(i).priority;
                                    d.p=f;
                                    d.index=i;
                                    v.add(d);

                                }
                                else{
                                    gantt o= new gantt();
                                    o.name=p.get(i).name;
                                    c=i;
                                    first= total_time;
                                    g.add(o);
                                }
                            }
                            else{
                                if(v.get(0).p.priority>p.get(i).priority){
                                    u.time = total_time;
                                    g.add(u);
                                    v.get(0).p.burst_time-=y;
                                    gantt o= new gantt();
                                    o.name=p.get(i).name;
                                    g.add(o);
                                    c=i;
                                    first= total_time;
                                }
                                else if(v.size()==1 || v.get(0).p.priority<v.get(1).p.priority ){
                                    g.add(u);
                                    v.remove(0);
                                    remain d=new remain();
                                    process f=new process();
                                    f.name=p.get(i).name;
                                    f.arrival_time=p.get(i).arrival_time;
                                    f.burst_time=p.get(i).burst_time;
                                    f.priority=p.get(i).priority;
                                    d.p=f;
                                    d.index=i;
                                    v.add(d);

                                }
                                else{
                                    u.time = total_time;
                                    g.add(u);
                                    v.get(0).p.burst_time-=y;
                                    gantt o= new gantt();
                                    o.name=v.get(1).p.name;
                                    g.add(o);
                                    c=v.get(1).index;
                                    v.remove(1);
                                    first= total_time;
                                    remain d=new remain();
                                    process f=new process();
                                    f.name=p.get(i).name;
                                    f.arrival_time=p.get(i).arrival_time;
                                    f.burst_time=p.get(i).burst_time;
                                    f.priority=p.get(i).priority;
                                    d.p=f;
                                    d.index=i;
                                    v.add(d);
                                }
                            }
                        }
                    }
                    else {
                        if((!v.isEmpty() && p.get(c).priority<v.get(0).p.priority && p.get(c).priority<p.get(i).priority)||(v.isEmpty() &&p.get(c).priority<p.get(i).priority)){
                            remain n=new remain ();
                            process f=new process();
                            f.name=p.get(i).name;
                            f.arrival_time=p.get(i).arrival_time;
                            f.burst_time=p.get(i).burst_time;
                            f.priority=p.get(i).priority;
                            n.p=f;
                            n.index=i;
                            v.add(n);

                            continue;
                        }

                        else {
                            g.get(g.size()-1).time=total_time;
                            remain b=new remain ();
                            process f=new process();
                            f.name=p.get(c).name;
                            f.arrival_time=p.get(c).arrival_time;
                            if(g.size()>=2)
                                f.burst_time=p.get(c).burst_time-(total_time-g.get(g.size()-2).time);
                            else f.burst_time=p.get(c).burst_time-total_time;
                            f.priority=p.get(c).priority;
                            b.p=f;
                            b.index=c;
                            v.add(b);

                            Collections.sort(v, new sort_arrival());
                            Collections.sort(v, new sort_priority());
                            if(!v.isEmpty() && v.get(0).p.priority<p.get(i).priority){
                                remain n=new remain ();
                                process k=new process();
                                k.name=p.get(i).name;
                                k.arrival_time=p.get(i).arrival_time;
                                k.burst_time=p.get(i).burst_time;
                                k.priority=p.get(i).priority;
                                n.p=k;
                                n.index=i;
                                v.add(n);

                                gantt w= new gantt ();
                                w.name=v.get(0).p.name;
                                g.add(w);
                                c=v.get(0).index;
                                first= total_time;
                            }
                            else{
                                gantt w= new gantt ();
                                w.name=p.get(i).name;
                                g.add(w);
                                c=i;
                                first= total_time;

                            }

                        }


                    }

                }
                if (g.get(g.size() - 1).time == 0) {
                    double t = g.get(g.size() - 2).time;
                    if (p.get(c).burst_time != total_time - t) {
                        g.get(g.size() - 1).time = t + p.get(c).burst_time;
                        total_time += p.get(c).burst_time - (total_time - t);
                    } else {
                        g.get(g.size() - 1).time = total_time;
                    }
                }
                c++;
//            for (int i = 0; i < pr.size(); i++) {
//                process b = new process();
//                b.name = pr.get(i).name;
//                b.burst_time = pr.get(i).burst_time;
//                b.priority = pr.get(i).priority;
//                b.arrival_time = pr.get(i).arrival_time;
//                v.add(b);
//            }
                while (c < p.size()) {
                    remain b = new remain();
                    boolean h = false;
                    for (int i = 0; i < v.size(); i++) {
                        if (v.get(i).p.name == p.get(c).name) {
                            h = true;
                            break;
                        }

                    }
                    if (h) {
                        c++;
                        continue;
                    }
                    process f=new process();
                    f.name = p.get(c).name;
                    f.burst_time = p.get(c).burst_time;
                    f.priority = p.get(c).priority;
                    f.arrival_time = p.get(c).arrival_time;
                    b.p=f;
                    v.add(b);
                    c++;
                }
                Collections.sort(v, new sort_arrival());
                Collections.sort(v, new sort_priority());
                for (int i = 0; i < v.size(); i++) {
                    gantt f = new gantt();
                    f.name = v.get(i).p.name;
                    f.time = v.get(i).p.burst_time + total_time;
                    g.add(f);
                    total_time += v.get(i).p.burst_time;
                }

            }
        }
        return convert_to_2dgantt(g);
    }
    private static String[][] convert_to_2dgantt(ArrayList<gantt> timeline) {
        String[][] two_D = new String[2][timeline.size()];
        double time = 0;
        for(int i = 0 ; i < timeline.size(); i++ ) {
            two_D[0][i] = timeline.get(i).name;
            two_D[1][i] = time+ ":" + ( timeline.get(i).time) ;  //String.valueOf(timeline.get(i).time);
            time =  timeline.get(i).time;
        }
        return two_D;
    }
    public static String[][] FCFS(String [] s)
    {
        ArrayList <process> p = toProcess("FCFS", s);
        process[] processes = new process[p.size()];
        for(int k = 0 ; k < p.size() ; k++)
        {
            processes[k] = p.get(k);
        }


        String[][]x=new String[2][100];
        double ct[] = new double[processes.length];     // completion times
        double ta[] = new double[processes.length];     // turn around times
        double wt[] = new double[processes.length];     // waiting times
        double temp;
        String tempp;
        double avgwt=0,avgta=0;

        //sorting according to arrival times
        for(int i = 0 ; i <processes.length; i++)
        {
            for(int  j=0;  j < (processes.length-(i+1)) ; j++)
            {
                if( processes[j].arrival_time > processes[j+1].arrival_time )
                {
                    temp = processes[j].arrival_time;
                    processes[j].arrival_time = processes[j+1].arrival_time;
                    processes[j+1].arrival_time = temp;
                    temp = processes[j].burst_time;
                    processes[j].burst_time = processes[j+1].burst_time;
                    processes[j+1].burst_time = temp;
                    tempp = processes[j].name;
                    processes[j].name = processes[j+1].name;
                    processes[j+1].name = tempp;
                }
            }
        }

        for(int  i = 0 ; i < processes.length; i++)
        {
            if( i == 0)
            {
                ct[i] = (processes[i].arrival_time) + (processes[i].burst_time);
            }
            else
            {
                if( processes[i].arrival_time > ct[i-1])
                {
                    ct[i] = (processes[i].arrival_time + processes[i].burst_time);
                }
                else
                    ct[i] = ct[i-1] + processes[i].burst_time;
            }
            ta[i] = ct[i] - processes[i].arrival_time ;          // turnaround time= completion time- arrival time
            wt[i] = ta[i] - processes[i].burst_time ;          // waiting time= turnaround time- burst time
            avgwt += wt[i] ;               // total waiting time
            avgta += ta[i] ;               // total turnaround time
        }

        String[] ana=new String [processes.length];

        for(int i=0; i<ana.length; i++)
        {ana[i]= ((ct[i]-processes[i].burst_time) +":" + ct[i] );}

        String[] anaa=new String [processes.length];

        for(int i=0; i<ana.length; i++)
        {  anaa[i]= (processes[i].name ); }
        String result[][] = {anaa,ana};
        int y=0;
        if((ct[0]-processes[0].burst_time)!=0){
            x[0][0]="ideal";
            x[1][0]=""+0+":"+((int)(ct[0]-processes[0].burst_time));
            y++;
        }
        for(int i=0;i<processes.length;i++){
            x[0][y]=result[0][i];
            x[1][y]=result[1][i];
            if(i<processes.length-1){
                if((ct[i+1]-processes[i+1].burst_time)==ct[i]){
                    y++;
                    continue;
                }
                else{
                    y++;
                    x[0][y]="ideal";
                    x[1][y]=""+ct[i]+":"+((int)(ct[i+1]-processes[i+1].burst_time));

                }
            }
            y++;
        }
        String [][]t=new String[2][y];
        for(int i=0;i<y;i++){
            t[0][i]=x[0][i];
            t[1][i]=x[1][i];

        }
        System.out.println("\nAverage waiting time= "+ (avgwt/processes.length));     // printing average waiting time.
        System.out.println("Average turnaround time= "+(avgta/processes.length));
        return t;

    }


    public static float avg_waiting (String[]in,int type){
        float avg=0;
        String [][]s= {};
        ArrayList <process> pr= new ArrayList <process>();
        if(type==1){
            s=priority_preemptive(in);
            pr= toProcess("priority",in);
            System.out.println("fffffff");
        }
        else if(type==2){
            s=preemtive_SJF(in);
            pr= toProcess("SJF",in);
        }
        else if(type==3){
            s=nonpreemtive_SJF(in);
            pr= toProcess("SJF",in);
        }
        else if(type==4){
            s=roundRobin(in);
            pr= toProcess("RR",in);
        }
        else if(type==5){
            s=NP_Priority(in);
            pr= toProcess("priority",in);
        }
        else if(type==6){
            s=FCFS(in);
            pr=toProcess("FCFS",in);
        }
        ArrayList <gantt> g = new ArrayList <gantt>();
        for(int i=0;i<s[0].length;i++){
            gantt f= new gantt();
            f.name=s[0][i];
            double t=0;
            for(int j=0;j<s[1][i].length();j++){
                if(s[1][i].charAt(j)!=':') continue;
                else{
                    t=Double.parseDouble(s[1][i].substring(j+1));
                    break;
                }
            }
            f.time=t;
            g.add(f);
        }

        ArrayList <depature> de = new ArrayList <depature>();
        for(int i=0;i<pr.size();i++){
            String n=pr.get(i).name;
            depature d= new depature();
            d.name =n;
            d.a_time=pr.get(i).arrival_time;
            d.b_time=pr.get(i).burst_time;
            int last=0;
            for(int j=0; j<g.size();j++){
                if(g.get(j).name.equals(n))
                    last =j;
            }
            d.d_time=g.get(last).time;
            de.add(d);

        }
        for(int i=0;i<de.size();i++){
            avg+=(de.get(i).d_time-de.get(i).a_time-de.get(i).b_time);
        }


        return avg/pr.size();
    }
    public static String clean_text(String[][]s){
        String r="";
        for(int i=0;i<s[0].length;i++){
            r+=s[0][i]+" -> "+s[1][i]+"\n";

        }

        return r;
    }


}



class process {
    String name;
    double burst_time;
    double arrival_time;
    int priority;
    double quantum;
    public process(){
    }

    public process(String n, double at, double bt) {
        name = n;
        arrival_time = at;
        burst_time = bt;
    }

}
class depature{
    String name;
    double d_time;
    double a_time;
    double b_time;
}
class gantt{
    String name;
    double time;
    public gantt(){};
    public gantt(String n, double t) {
        name = n;
        time = t;
    }
}

class remain {
    process p;
    int index;
}
class sort_arrival implements Comparator<remain> {

    public int compare(remain a, remain b) {
        return (int) (a.p.arrival_time - b.p.arrival_time);
    }

}
class  psort_arrival implements Comparator<process> {

    public int compare(process a, process b) {
        return (int) (a.arrival_time - b.arrival_time);
    }

}

class psort_priority implements Comparator<process> {

    public int compare(process a, process b) {
        return (int) (a.priority - b.priority);
    }

}

class sort_priority implements Comparator<remain> {

    public int compare(remain a, remain b) {
        return (int) (a.p.priority - b.p.priority);
    }

}



class compare_arr implements Comparator<process>
{
    public int compare(process p1, process p2)
    {
        if (p1.arrival_time < p2.arrival_time) return -1;
        if (p1.arrival_time > p2.arrival_time) return 1;
        else return 0;
    }
}

class compare_burst implements Comparator<process>
{
    public int compare(process p1, process p2)
    {
        if (p1.burst_time < p2.burst_time) return -1;
        if (p1.burst_time > p2.burst_time) return 1;
        else return 0;
    }
}
class pro_order implements Comparable<pro_order> {

    process p;
    int o;

    public pro_order(process p, int o) {
        this.p = p;
        this.o = o;
    }

    @Override
    public int compareTo(pro_order o) {
        if ((this.p).burst_time > (o.p).burst_time) {
            return 1;
        }
        if ((this.p).burst_time < (o.p).burst_time) {
            return -1;
        }
        return 0;
    }
}






