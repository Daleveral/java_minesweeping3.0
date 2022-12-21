package codes;

import javax.swing.*;  // JFrame 对应的包
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

// GameWin,继承了 JFrame类,包括:
// 窗口宽高, 一个 MapBottom对象,一个 MapTop对象,一个 Database 对象
// launch() 方法的作用: 根据 Basis.STATE游戏状态,显示不同尺寸的窗口
// 代码块里设置鼠标行为
// 重写的 print() 方法特别重要,里面包含了绘制游戏窗口所有图像,
// 以及数据的更新,成功和失败的判定,对数据库的访问


public class Game extends JFrame
{
    int width =2 * Basis.MARGIN + Basis.MAP_W * Basis.SQUARE_LENGTH;
    int height = 4 *Basis.MARGIN +Basis.MAP_H *Basis.SQUARE_LENGTH;
    // 窗口的宽高参数,实际由 MAP_W 与 MAP_H 控制,设置不同的 MAP_W 与 MAP_H 数值即可控制大小

    Image offScreenImage = null;
    MapBottom mapBottom = new MapBottom(); // 这个对象一产生,雷和数字就已经安排好了
    MapTop mapTop = new MapTop();

    Database database = new Database();
    // 数据库类,这个类有没有意义要看用户选择的 DatabaseLink.DB_FUNCTION

    {
        // 鼠标逻辑
        // 功能:随时根据鼠标的点击,更新 Basis 类中记录的鼠标参数
        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                // super.mouseClicked(e);
                switch (Basis.STATE)
                {
                    case Basis.GAMING:
                        if(e.getButton()==1) //鼠标左键
                        {
                            Basis.MOUSE_X=e.getX(); // 此时鼠标左键的坐标
                            Basis.MOUSE_Y=e.getY(); // 此时鼠标右键的坐标
                            Basis.LEFT =true;
                        }
                        if(e.getButton()==3) //鼠标右键
                        {
                            Basis.MOUSE_X=e.getX(); // 此时鼠标左键的坐标
                            Basis.MOUSE_Y=e.getY(); // 此时鼠标右键的坐标
                            Basis.RIGHT=true;
                        }

                    case Basis.VICTORY:
                        // 前两个 case都不需要 break ,这样不管输赢或进行中,都能重置游戏

                    case Basis.LOSS:
                        if(e.getButton()==1)  // 点击正上方的游戏状态图标,重新开始游戏
                        {
                            int margin =Basis.MARGIN;
                            int w = Basis.MAP_W;
                            int sl =Basis.SQUARE_LENGTH;
                            // 静态变量名太长了,这里改短一点 ...
                            if( e.getX()>margin+sl*(w/2) && e.getX()<margin+sl*(1+w/2)
                                    && e.getY()>margin && e.getY()<margin+sl)
                            {     // 点击的就是正上方状态图标的位置
                                mapBottom.reGame();
                                mapTop.reGame();
                                Basis.FLAG_NUM =0 ; // 释放记录的旗子数
                                Basis.START_TIME = System.currentTimeMillis();
                                DatabaseLink.DATABASE = true;
                                Basis.STATE = Basis.GAMING;
                            }
                        }
                        break;
                } // switch 尾
            }
        }); // 设置鼠标结束

    }


    void launch()
    {
        Basis.START_TIME = System.currentTimeMillis(); // 记录时间
        this.setVisible(true); // 可见
        this.setSize(width,height); // 游戏窗口大小为 width x height,大小可根据难度而改变
        this.setLocationRelativeTo(null); //居中
        this.setTitle("扫雷游戏");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        while (true) // 死循环
        {
            repaint(); // 不停地调用绘制方法
            try { Thread.sleep(40); } //添加 40毫秒延时使其不会刷新太快
            catch (InterruptedException e)
            {e.printStackTrace();}
        }

    } // launch() 方法末尾



    @Override
    public void paint(Graphics g)
    {
       // 下面这段是打印游戏窗体,双缓冲绘制解决图片闪烁问题
        offScreenImage = this.createImage(width,height);
        Graphics gImage = offScreenImage.getGraphics();
        gImage.setColor(Color.orange);
        gImage.fillRect(0,0,width,height); // 填充整个画布
        mapBottom.paintBottom(gImage);
        mapTop.paintTop(gImage); // 不仅是打印,还监测着成功和失败

        if(DatabaseLink.DB_FUNCTION) // 如果开启了数据库功能,则使用数据库
        {
            try { database.useDatabase(); }
            catch (SQLException e)   // 检测到成功或者失败,就访问数据库
            { throw new RuntimeException(e); }
        }

        g.drawImage(offScreenImage,0,0,null);
    }

    public static void main(String[] args)
    {
        FirstWindow firstWindow = new FirstWindow();

        while (Basis.STATE ==Basis.CHOOSING)
        {
            try {  Thread.currentThread().sleep(100); }
            catch (InterruptedException e)
            {e.printStackTrace();}
        }
        // 阻止 main线程,直到鼠标响应,游戏状态改变

        firstWindow.dispose(); // 第一个窗口可以被释放了
//        firstWindow.setVisible(false); // 隐藏而不释放
        Game game = new Game();
        game.launch();

    }

}
