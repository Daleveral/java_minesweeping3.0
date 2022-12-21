package codes;

public class DatabaseLink
{

    /*
    
    访问你本地的数据库,请进行如下操作 :
      1,将 DB_FUNCTION 修改为 true
      2,在自己的电脑上建立一个表,存放游戏记录,建表的 SQL语句在文件 createTable.txt里
      3,修改下面四个静态字符串
        url 为 "jdbc:mysql//端口名/数据库名"
        user 用户名, 一般为 root
        pwd 为你设的密码
        dbname 为你给这个表起的名字,我的就叫做 minesweeping
    */


    static boolean DB_FUNCTION = false ; // 是否使用数据库功能,默认为关闭

    static boolean DATABASE = true; // 数据库的开关 (防止多次写入)

    static String url = "jdbc:mysql://localhost:3306/test_database";
    static String user = "root";
    static String pwd = "是你的生日还是银行卡密码呢(doge)";
    static String dbName = "minesweeping";

}
