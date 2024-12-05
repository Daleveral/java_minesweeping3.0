package codes;

import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Database
{
    void useDatabase() throws SQLException
    {
        if(DatabaseLink.DATABASE == true &&
          (Basis.STATE == Basis.VICTORY || Basis.STATE == Basis.LOSS) )
        {
            Connection connection = DriverManager.getConnection(
                DatabaseLink.url, DatabaseLink.user, DatabaseLink.pwd);

            if (connection == null)
            { System.out.println("连接数据库失败"); }
            else
            { System.out.println("连接数据库成功"); }

            Statement statement = connection.createStatement();  // 执行者

            // 下面创建字符串,date_str,level_str,state_str,seconds_str 均为要导入数据库的字段

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String date_str = formatter.format(date); // 日期字段

            String level_str = " ";
            switch (Basis.level)
            {
                case 1: level_str = "简单"; break;
                case 2: level_str = "普通"; break;
                case 3: level_str = "困难"; break;
            } // 难度字段

            String state_str = " ";
            if (Basis.STATE == Basis.VICTORY)
            { state_str = "胜利"; }
            if (Basis.STATE == Basis.LOSS)
            { state_str = "失败"; } // 结果字段


            String seconds_str; // 历时字段
            int seconds = (int) ((Basis.END_TIME - Basis.START_TIME) / 1000);
            int min = seconds / 60;
            int remainder = seconds % 60;
            seconds_str = "00:" + min + ":" + remainder; // 不会真有人玩我这一局扫雷超过一个小时吧 ...


            String sql = "INSERT INTO "+ DatabaseLink.dbName + " VALUES" +  "( '" + date_str
              + "', 9999,'" + level_str + "', '" + state_str + "', '" + seconds_str + "' )";

            statement.executeUpdate(sql); // 执行插入语句,9999是不正确的数值,下面会修改


            // 以下的操作是对 "此日第几次" 字段进行修改,需要访问数据库,对照日期才能准确修改

            int this_times = 0; // 等一下被赋值为新插入的 9999 而已
            int last_times =0; // 马上会被赋值为 9999 的前一条数据(是准确的)

            String Qsql = "select * from "+ DatabaseLink.dbName;
            ResultSet resultSet = statement.executeQuery(Qsql);
            java.sql.Date thisDate = null;
            java.sql.Date lastDate = null;
            while (resultSet.next())
            {
                lastDate = thisDate;
                thisDate = resultSet.getDate("日期");
                last_times =this_times;
                this_times = resultSet.getInt("此日第几次");
            }
            int times_add1= last_times +1;

//            System.out.println("执行前");

            if (thisDate.equals(lastDate)) // 用 equals() 方法比较,不能使用 ==
            {
                String addOne = "UPDATE "+ DatabaseLink.dbName +" SET 此日第几次 = " + times_add1 +
                        " where 日期 =" + "'" + date_str + "'" +" and 此日第几次 = 9999";
                statement.executeUpdate(addOne);
                System.out.println("数据库此日数据加一");
            }
            else
            {
                String newDay = "UPDATE "+DatabaseLink.dbName+" SET 此日第几次 = 1"  +
                        " where 日期 =" + "'" + date_str + "'" +" and 此日第几次 = 9999";
                statement.executeUpdate(newDay);
                System.out.println("数据库择日数据加一");
            }

            DatabaseLink.DATABASE = false;
            connection.close();
        }
    }

}
