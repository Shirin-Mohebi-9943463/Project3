import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Others {
    // در اینجا دو کلاس یوزر و فلایت نیو شدن چون در ادامه وقتی بخواهیم توابع این کلاس هارو صدا کنیم به این دو کلاس نیاز داریم
    private User user=new User();
    private Flight flight=new Flight();
    private String username;
    private String password;

    public Others() throws IOException {
    }
    // منوی اصلی
    public void mainmenue() throws IOException {
        Scanner in=new Scanner(System.in);
        System.out.println("Welcome to Iran Air site");
        System.out.println("Enter 1 to Register");
        System.out.println("Enter 2 to Login");
        switch (in.nextInt()){
            case 1:
                Register();// اگه 1 رو وارد کنه وارد منوی ثبت نام میشه
                break;
            case 2:
                login();// اگر 2 رو وارد کنه وارد منوی ورود میشه
                break;
            default:// واگر هرچیز دیگه ای وارد کرد به همین منو برگرده
                mainmenue();
        }
    }
    // منوی ثبت نام
    public void Register() throws IOException {
        System.out.println("Register");
        System.out.println("Enter your username:");// کاربر یوزر نیم رو وارد میکنه
        Scanner input=new Scanner(System.in);
        username=input.nextLine();
        System.out.println("Enter your password:");// پسورد هم وارد میکنه
        password=input.nextLine();
        for (int i=0;i<user.getUser_file().length()/6080;i++){// در اینجا چک میشود که ایا همچین یوزری در فایل یوزر ما وجود دارد یا نه
            user.getUser_file().seek(i*6080);
            user.readuserfile();// فایل یوزر خونده بشه
            if (user.getUsername().equals(username)){// واگر برابر بود پیام مناسب چاپ بشه
                System.out.println("your username was taken by another user");
                mainmenue();
                break;
            }
        }
        // اگر برابر نبود این اتفاقات میفته
        User user1=new User(username,password);// در اینجا یک یوزر نیو میشه
        user=user1;// که در اینجا در فایل قرار بگیره
        user.writeuserfile();// و بعد رایت میشه
        System.out.println("register successfully");
        login();
    }

    public void login() throws IOException {
        System.out.println("Login");
        System.out.println("Enter your username:");
        Scanner in=new Scanner(System.in);
        username=in.nextLine();
        System.out.println("Enter your password:");
        password=in.nextLine();
        for (int i=0;i<user.getUser_file().length()/6080;i++){// در اینجا باید چک بشه که ایا ما در فایل یوزرمون یوززنیم و پسورد وارد شده توسط کاربر را داریم یا خیر
            user.getUser_file().seek(i*6080);
            user.readuserfile();
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){// اگه بود که وارد guidance panel میشه
                System.out.println("login Successfully");
                guidancepanel();
                break;
            }
        }
        // اگرم نبود که پیام مناسب چاپ میشه
        System.out.println("Please enter one more time(maybe you have not registered yet or maybe what you entered is wrong)");
        mainmenue();
    }

    public void guidancepanel() throws IOException {
        System.out.println("guidance panel");
        System.out.println("enter 1 to show list of flights");
        System.out.println("enter 2 to search flights");
        System.out.println("enter 3 to show your flights");
        System.out.println("enter 4 logout");
        Scanner in=new Scanner(System.in);
        switch (in.nextInt()){
            case 1:// اگه کاربر 1 رو وارد کنه کل پرواز ها نمایش داده میشن
                //پرواز های رندم نمایش داده میشوند
                flight.showlistofflights();
                System.out.println("enter 0 to back to guidance panel");
                switch (in.nextInt()){
                    case 0:// اگه 0 وارد کنه به همین منو برمیگرده
                        guidancepanel();
                        break;
                    default:// در غیر این صورت باز به همین منو بر میگرده
                        guidancepanel();
                }
                break;
            case 2:// اگه 2 وارد شه وارد سرچ میشه
                //و تابع سرچ اجرا میشه
                flight.search();
                System.out.println("if you want to buy it enter 9 unless enter 0 to back to guidance panel");
                switch (in.nextInt()){
                    case 9:// اگه 9 وارد کنه پرواز میخره و به فایل یوزر باید اضافه بشه
                        for (int k=0;k<user.getUser_file().length()/6080;k++){
                            k=k+1;
                            user.getUser_file().seek(k*80);
                            for (int i=0;i<flight.getLast_flight().length;i++){
                                user.getUser_file().writeChars(user.fixstringlength(flight.getLast_flight()[i],20));
                            }
                        }
                        System.out.println("This flight added");
                        guidancepanel();
                        break;
                    case 0:// اگه 0 وارد کنه باید بر گرده به همین منو
                        guidancepanel();
                        break;
                    default:// در غیر این صورت باز برگرده به همین منو
                        guidancepanel();
                }
                break;
            case 3:// اگه 3 رو وارد کرد لیست پرواز های خریده شده نمایش داده بشن
                //list
                for (int k=0;k<user.getUser_file().length()/6080;k++){
                    k=k+1;
                    user.getUser_file().seek(k*80);
                    for (int i=0;i<flight.getLast_flight().length;i++){
                        System.out.printf("%10s",user.readString(user.getUser_file(),20));
                    }
                    System.out.println();
                }
                guidancepanel();
                break;
            case 4:// اگر 4 وارد شد خروج بشه از کاربر و وارد منوی اولی بشه
                //logout
                mainmenue();
            default:
                break;
        }
    }
}
