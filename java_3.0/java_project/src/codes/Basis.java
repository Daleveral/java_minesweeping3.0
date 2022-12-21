package codes;

/*
    工具类,存放静态参数,包括:
    画面参数: 游戏画面宽高,格子边长,边缘长度
    元素参数: 雷的初始个数,剩余旗子数量
    鼠标参数: 横纵坐标,点击状态
    游戏状态: (进行中,胜利,失败,难度选择)
    计时参数: (START_TIME,END_TIME)
    二维数组: (DATA_BOTTOM ,DATA_TOP)
    图片与与变量名的对应 .
    以及一个方法 printWords(),用于在窗口打印字符串
*/

import java.awt.*;

public class Basis
{
    static int MINE_MAX =50;     // 雷的个数

    static int MAP_W =28;       // 宽,单位为 SQUARE_LENGTH
    static int MAP_H =12;       // 高,单位为 SQUARE_LENGTH
    static int MARGIN =45;      // 边缘
    static int SQUARE_LENGTH =50; // 格子边长
    // 雷的个数,宽高均初始设置为最大值是为了防止数组越界

    static int FLAG_NUM =0; // 剩余旗子数量 (为雷的计数显示服务)

    // 鼠标相关的参数
    static int MOUSE_X;
    static int MOUSE_Y;
    // 状态
    static boolean LEFT = false;
    static boolean RIGHT = false;

    // 游戏状态 0:游戏中, 1:胜利, 2:失败, 3:难度选择
    static int STATE =3;
    static final int GAMING =0;
    static final int VICTORY =1;
    static final int LOSS = 2;
    static final int CHOOSING =3;

    static int level; // 只有记录到数据库一个用处

    // 倒计时
    static long START_TIME;
    static long END_TIME;



    // 底层元素  -1:雷 ,0:空 ,1~8:对应数字
    // 横纵坐标都加二是考虑到数字生成,避免数组越界
    static int [][] DATA_BOTTOM = new int[MAP_W+2][MAP_W+2];
    static final int BOMB = -1;

    // 顶部  -1无覆盖 ,0覆盖, 1插旗, 2插错旗
    static int [][] DATA_TOP = new int[MAP_W+2][MAP_W+2];
    static final int DISCOVER =-1;
    static final int COVER  =0;
    static final int FLAG =1;
    static final int WRONG_FLAG =2;


    // 载入图片
    static Image lei = Toolkit.getDefaultToolkit().getImage("imgs/lei.png");  // 地雷
    static Image top = Toolkit.getDefaultToolkit().getImage("imgs/top.png");  // 蓝方块
    static Image flag = Toolkit.getDefaultToolkit().getImage("imgs/flag.png"); // 旗帜
    static Image noflag = Toolkit.getDefaultToolkit().getImage("imgs/noflag.png"); // 旗帜错误标注

    static Image face = Toolkit.getDefaultToolkit().getImage("imgs/searching.png");  // 进行中
    static Image over = Toolkit.getDefaultToolkit().getImage("imgs/boom.png"); // 爆炸
    static Image win = Toolkit.getDefaultToolkit().getImage("imgs/victory.png"); // 胜利

    static Image[] images = new Image[9]; // 载入数字的图片
    static
    {
        for(int i=1;i<=8;i++)
        {
            images[i]=Toolkit.getDefaultToolkit().getImage("imgs/num/"+i+".png");
        }
    }

    static void printWords(Graphics g,String str,int x,int y,int size,Color color)
    {   // 打印字符串方法,用于打印 FirstWindow 的窗口和游戏窗口的剩余雷数,时间
        g.setColor(color);
        g.setFont(new Font("微软雅黑",Font.BOLD,size));
        g.drawString(str,x,y);
    }

}
