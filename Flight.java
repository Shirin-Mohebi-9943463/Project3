import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.Scanner;

public class Flight {

    private String path = "flight.dat";//آدرس پویا برای فایل فلایت
    private RandomAccessFile flight_file = new RandomAccessFile(path, "rw");
    private String flight_random[][] = new String[15][10];// یک ماتریس که پرواز های رندم را داخل اون میریزم
    private Random random;
    private String last_flight[] = new String[4];// ارایه ای که اخرین پرواز رو در خود ذخیره میکند
    private String City[] = new String[]{"Tehran", "Yazd", "Mashhad", "Shiraz", "Isfahan", "Kerman", "Kermanshah"};
    private String Day[] = new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private String Time[] = new String[]{"12", "13", "14", "15", "16", "17", "18"};
    private String Id[] = new String[]{"0305", "0315", "0205", "0215", "0405", "0415", "0517"};

    public Flight() throws IOException {// کانستراکتر فلایت که تابع رایت داخلش گذاشته شده
        this.random = new Random();
        writeflightfile();
    }
    //توابع ست و گت
    public RandomAccessFile getFlight_file() {
        return flight_file;
    }

    public void setFlight_file(RandomAccessFile flight_file) {
        this.flight_file = flight_file;
    }

    public String[][] getFlight_random() {
        return flight_random;
    }

    public void setFlight_random(String[][] flight_random) {
        this.flight_random = flight_random;
    }

    public String[] getLast_flight() {
        return last_flight;
    }

    public void setLast_flight(String[] last_flight) {
        this.last_flight = last_flight;
    }
    // کاربرد این تابع در کلاس یوزر گفته شد
    public String fixstringlength(String str, int len) {
        if (str.length() >= len) {
            return str.substring(0, len);
        } else {
            int spaceNo = len - str.length();
            for (int i = 0; i < spaceNo; i++) {
                str += " ";
            }
        }
        return str;
    }
    // دراینجا فایل فلایتمون رو رایت میکنیم به صورتی که هربار که رندم میگیریم رایت میکنیم
    public void writeflightfile() throws IOException {
        String from, to, day, time1,time2, id1, id2;
        flight_file.seek(0);
        for (int i = 0; i < 15; i++) {
            id1 = Id[random.nextInt(7)];
            id2 = Id[random.nextInt(7)];
            String final_id = isitsameid(id1, id2);
            flight_file.writeChars(fixstringlength(final_id, 20));
            from = City[random.nextInt(7)];
            flight_file.writeChars(fixstringlength(from, 20));
            to = City[random.nextInt(7)];
            String final_to = isitsamecity(from, to);
            flight_file.writeChars(fixstringlength(final_to, 20));
            day = Day[random.nextInt(7)];
            flight_file.writeChars(fixstringlength(day, 20));
            time1 = Time[random.nextInt(7)];
            time2=Time[random.nextInt(7)];
            String final_time=isitsametime(time1,time2);
            flight_file.writeChars(fixstringlength(final_time, 20));
        }
    }
    // این سه تابع برای چک کردن این هست که مبدا و مقصد یکسان نشود و ایدی هم یکسان نشود و ساعت هم یکسان نشود
    public String isitsamecity(String from, String to) {
        String final_to;
        while (from.equals(to)) {
            to = City[random.nextInt(7)];
        }
        final_to = to;
        return final_to;
    }

    public String isitsameid(String id1, String id2) {
        String final_id;
        while (id1.equals(id2)) {
            id2 = Id[random.nextInt(7)];
        }
        final_id = id2;
        return final_id;
    }

    public String isitsametime(String time1,String time2){
        String final_time;
        while (time1.equals(time2)){
            time2=Time[random.nextInt(7)];
        }
        final_time=time2;
        return final_time;
    }

    // تابع read کردن برای کلاس فلایت
    public String[][] readflightfile() throws IOException {
        // دراینجا یک ماتریس جدید تعریف میکنیم
        String[][] strings = new String[15][10];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 10; j++) {
                strings[i][j] = "";
            }
        }
        flight_file.seek(0);// از اول شروع میکنیم
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 10; j++) {
                for (int size = 0; size < 10; size++) {
                    strings[i][j] += flight_file.readChar();// و به ترتیب read میکنیم
                }
                strings[i][j] = strings[i][j].trim();// فواصل ایجاد شده را میگیره
            }
        }
        flight_random = strings;//سپس ماترس نهایی رو در ماتریس فلایت رندم میریزیم
        return flight_random;
    }
    // برای نشون دادن پرواز های رندم هست
    public void showlistofflights() throws IOException {
        System.out.println("LIST OF FLIGHTS");
        System.out.printf("%10s %16s %18s %20s %22s", "Id", "From", "To", "Day", "Time");
        System.out.println();
        flight_random = readflightfile();// اول فایل رو میخونیم
        for (int i = 0; i < 15; i++) {// بعد به ترتیب چاپ میکنیم
            for (int j = 0; j < 10; j++) {
                System.out.printf("%10s", flight_random[i][j]);
            }
            System.out.println();
        }
    }
    // تابع سرچ
    //دراینجا به ترتیب کاربر موارد خواسته شده رو وارد میکنه بعد از همون پرواز های رندم سرچ میشه که همچین مواردی رو داریم یا نه اگر نبود دوباره به اول سرچ برمیگردیم
    public void search() throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Search");
        System.out.println("Enter your starting city:");// کاربر مبدارو وارد میکنه
        String user_from = input.nextLine();
        flight_random = readflightfile();// فایل رندم خوانده میشه
        boolean isFound = false;// در اینجا یک متغیر بولین میگیریم که اول false هست
        for (int i = 0; i < 15; i++) {
            if (flight_random[i][2].equals(user_from)) {// در اینجا چک میشه که ایا شهری که کاربر وارد کرده در رندم ما وجود دارد یا خیر
                isFound = true;// اگر بود دراینجا این متغیر true میشه
                System.out.printf("%10s %16s %18s %20s %22s", flight_random[i][0], flight_random[i][2], flight_random[i][4], flight_random[i][6], flight_random[i][8]);
                System.out.println();
            }
        }
        if (!isFound) {// اگه همون فالس موند میگیم پرواز نیست
            System.out.println("This flight is not exist");
            search();
        }
        // در ادامه مراحل قبل تکرار میشه فقط در ایف ها هربار موارد جدید اضافه میشن
        System.out.println("Enter your destination:");
        String user_to = input.nextLine();
        isFound = false;
        for (int i = 0; i < 15; i++) {
            if (flight_random[i][2].equals(user_from) && flight_random[i][4].equals(user_to)) {
                isFound = true;
                System.out.printf("%10s %16s %18s %20s %22s", flight_random[i][0], flight_random[i][2], flight_random[i][4], flight_random[i][6], flight_random[i][8]);
                System.out.println();
            }
        }

        if (!isFound) {
            System.out.println("This flight is not exist");
            search();
        }

        System.out.println("Enter your day:");
        String user_day = input.nextLine();

        isFound = false;
        for (int i = 0; i < 15; i++) {
            if (flight_random[i][2].equals(user_from) && flight_random[i][4].equals(user_to) && flight_random[i][6].equals(user_day)) {
                isFound = true;
                System.out.printf("%10s %16s %18s %20s %22s", flight_random[i][0], flight_random[i][2], flight_random[i][4], flight_random[i][6], flight_random[i][8]);
                System.out.println();
            }
        }
        if (!isFound) {
            System.out.println("This flight is not exist");
            search();
        }

        System.out.println("Enter your time:");
        String user_time = input.nextLine();
        isFound = false;
        for (int i = 0; i < 15; i++) {
            if (flight_random[i][2].equals(user_from) && flight_random[i][4].equals(user_to) && flight_random[i][6].equals(user_day) && flight_random[i][8].equals(user_time)) {
                isFound = true;
                //در اینجا پرواز نهایی رو وارد ارایه ی رشته ای last_flight میریزیم
                last_flight[0]=flight_random[i][2];
                last_flight[1]=flight_random[i][4];
                last_flight[2]=flight_random[i][6];
                last_flight[3]=flight_random[i][8];
                System.out.printf("%10s %16s %18s %20s %22s", flight_random[i][0], flight_random[i][2], flight_random[i][4], flight_random[i][6], flight_random[i][8]);
                System.out.println();
            }
        }
        if (!isFound) {
            System.out.println("This flight is not exist");
            search();
        }
        System.out.println("This is your final flight:");
        for (int i=0;i<1;i++){
            System.out.printf("%10s %16s %18s %20s",last_flight[i],last_flight[i+1],last_flight[i+2],last_flight[i+3]);
            System.out.println();
        }
    }
}
