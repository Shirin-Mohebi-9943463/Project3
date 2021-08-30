import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class  User{

    private String username;//20 کاراکتر که میشه 40 بایت
    private String password;//20 کاراکتر که میشه 40 بایت
    private String flights[][]=new String[15][10];//15*10=150 در کل 150 تا خونه داریم که       150*40=6000 هرکدوم 40 بایت فضا دارند که میشه کلا 6000 تا بایت برای ماتریس فقط     40+40+6000=6080 که این عدد کل بایت هست
    private String path="user.dat";//آدرس پویا برای فایل یوزر
    private RandomAccessFile user_file=new RandomAccessFile(path,"rw");

    public User() throws FileNotFoundException {//کانستراکتور خالی ایجاد شده چون در کلاس others از این کلاس استفاده خواهد شد
    }

    public User(String username, String password) throws FileNotFoundException {//کانستراکتور یوزر که یوزنیم و پسورد به آن پاس داده شده
        this.username = username;
        this.password = password;
        // دراینجا ماتریس فلایت های کاربر را با یک مقدار پیش فرضی پر کردم
        for (int i=0;i<15;i++){
            for (int j=0;j<10;j++){
                flights[i][j]="*";
            }
        }
    }
//توابع ست و گت
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[][] getFlights() {
        return flights;
    }

    public void setFlights(String[][] flights) {
        this.flights = flights;
    }

    public RandomAccessFile getUser_file() {
        return user_file;
    }

    public void setUser_file(RandomAccessFile user_file) {
        this.user_file = user_file;
    }

    public String fixstringlength(String str, int len){//تابع فیکس رشته و حداکثر طول را گرفته و برای آن رشته به اندازه len جا درست میکنه
        //مثلا اگه shirin رو واردش کنم مثلا به اندازه 20 کاراکتر جا برای shirin میذاره و بقیه رو فاصله پر میکنه
        if(str.length()>=len){
            return str.substring(0,len);
        }
        else {
            int spaceNo=len-str.length();
            for (int i=0;i<spaceNo;i++){
                str+=" ";
            }
        }
        return str;
    }
    // تابع رایت برای فایل یوزر
    public void writeuserfile() throws IOException {
        user_file.seek(user_file.length());
        user_file.writeChars(fixstringlength(username,20));
        user_file.writeChars(fixstringlength(password,20));
        // دراینجا همون ماتریس فلایتمو رایت کردم
        for (int i=0;i<15;i++){
            for (int j=0;j<10;j++){
                user_file.writeChars(fixstringlength("*",20));
            }
        }
    }
    //این تابع فاصله های ایجاد شده رو با استفاده از متد trim پاک میکنم
    public String readString(RandomAccessFile file,int len) throws IOException {
        String str="";
        for (int i=0;i<len;i++){
            str+=file.readChar();
        }
        return str.trim();
    }
    // متد read برای فایل یوزر
    public void readuserfile() throws IOException {
        username=readString(user_file,20);
        password=readString(user_file,20);
        //دراینجا ماتریس فلایتمو read کردم
        for (int i=0;i<15;i++){
            for (int j=0;j<10;j++){
                flights[i][j]=readString(user_file,20);
            }
        }
    }
}