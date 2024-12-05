package codes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FirstWindow extends JFrame
{
    FirstWindow()
    {
        this.setVisible(true);
        this.setSize(500,500);
        this.setLocationRelativeTo(null); //居中
        this.setTitle("选择难度");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    @Override
    public void paint(Graphics g)
    {
        if(Basis.STATE ==Basis.CHOOSING)
        {
            g.setColor(Color.CYAN);
            g.fillRect(0,0,500,500);

            g.setColor(Color.red);
            g.drawRoundRect(100,50,300,100,20,20);
            Basis.printWords(g,"简单~",220,100,30,Color.pink);

            g.drawRoundRect(100,200,300,100,20,20);
            Basis.printWords(g,"普通",220,250,30,Color.yellow);

            g.drawRoundRect(100,350,300,100,20,20);
            Basis.printWords(g,"爆炸 !",220,400,30,Color.red);
        }
    }


    { // 代码块里面写鼠标逻辑
        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
                if (e.getButton() == 1 && Basis.STATE == Basis.CHOOSING) //鼠标左键
                {
                    Basis.MOUSE_X = e.getX();
                    Basis.MOUSE_Y = e.getY();
                    if (Basis.MOUSE_X > 100 && Basis.MOUSE_X < 400)
                    {
                        if (Basis.MOUSE_Y > 50 && Basis.MOUSE_Y < 150)
                        {
                            Basis.MINE_MAX = 6;
                            Basis.MAP_W = 9;
                            Basis.MAP_H = 9;
                            Basis.level =1;
                            System.out.println("玩家选择简单模式");
                        }
                        if (Basis.MOUSE_Y > 200 && Basis.MOUSE_Y < 300)
                        {
                            Basis.MINE_MAX = 20;
                            Basis.MAP_W = 16;
                            Basis.MAP_H = 12;
                            Basis.level =2;
                            System.out.println("玩家选择普通模式");
                        }
                        if (Basis.MOUSE_Y > 350 && Basis.MOUSE_Y < 450)
                        {
                            Basis.MINE_MAX = 50;
                            Basis.MAP_W = 28;
                            Basis.MAP_H = 12;
                            Basis.level =3;
                            System.out.println("玩家选择困难模式");
                        }

                    }
                    Basis.STATE = Basis.GAMING; // 切换状态,然后游戏窗口就能显示了
                }
                     /* 程序刚进行时,就会进行这个 if 的判定,鼠标点击一下选择了难度之后,
                     马上更新 Basis 类中的坐标记录, 根据难度修改地图大小和雷数量
                     并且将 Basis.STATE赋值为 Basis.GAMING,即进入了游戏中状态 */
            }
        });// 鼠标设置结束
    }

}
