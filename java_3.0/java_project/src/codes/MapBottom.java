package codes;

// 底层地图,绘制游戏相关组件
// 简单的说,MapBottom类的功能就是:创建对象时自动生成雷与数字,
// reGame()调用可以重置底层信息
// 绘制: 状态图标,横竖线,背景,雷的图片,数字的图片

import java.awt.*;

public class MapBottom
{
    BottomMine bottomMine = new BottomMine();
    BottomNum bottomNum = new BottomNum();
    {
        bottomMine.newMine();     // 生成雷,在 Basis 中标记雷坐标
        bottomNum.newNum();     // 根据雷生成数字,并在 Basis 中标记坐标
    }   // MapBottom对象一出现,雷和数字就生成了

    // 重置方法
    void reGame()
    {
        for(int i=1;i<=Basis.MAP_W;i++)
        {
            for(int j=1;j<=Basis.MAP_H;j++)
            {
                Basis.DATA_BOTTOM[i][j]=0; // 将雷和数字全部擦除,底层全设为空 .
            }
        }
        bottomMine.newMine();
        bottomNum.newNum();   // 重新生成雷和对应数字
    }


    //绘制方法
    //包括:横竖线,雷和数字图片,雷的剩余数目,倒计时,状态图标
    void paintBottom(Graphics g)
    {
        g.setColor(Color.red);

        for(int i=0;i<=Basis.MAP_W;i++) // 画竖线
        {
            g.drawLine( Basis.MARGIN+ i*Basis.SQUARE_LENGTH,
                    3*Basis.MARGIN,
                    Basis.MARGIN + i*Basis.SQUARE_LENGTH,
                    3*Basis.MARGIN+Basis.MAP_H * Basis.SQUARE_LENGTH );
        }

        for(int i=0;i<=Basis.MAP_H;i++)  // 画横线
        {
            g.drawLine(    Basis.MARGIN,
                    3*Basis.MARGIN + i*Basis.SQUARE_LENGTH,
                    Basis.MARGIN + Basis.MAP_W * Basis.SQUARE_LENGTH,
                    3*Basis.MARGIN + i*Basis.SQUARE_LENGTH );
        }


        for (int i =1;i<=Basis.MAP_W;i++)
        {
            for (int j=1;j<=Basis.MAP_H;j++)
            {
                // 加载雷的 png图片
                if(Basis.DATA_BOTTOM[i][j]==-1)
                {
                    g.drawImage(Basis.lei,
                            Basis.MARGIN+(i-1)*Basis.SQUARE_LENGTH +6,
                            Basis.MARGIN*3+(j-1)*Basis.SQUARE_LENGTH +6,
                            Basis.SQUARE_LENGTH -10,
                            Basis.SQUARE_LENGTH -10,
                            null);
                }
                // 加载数字的 png图片
                if(Basis.DATA_BOTTOM[i][j]>=0)
                {
                    g.drawImage(Basis.images[Basis.DATA_BOTTOM[i][j]],
                            Basis.MARGIN+(i-1)*Basis.SQUARE_LENGTH +1,
                            Basis.MARGIN*3+(j-1)*Basis.SQUARE_LENGTH +1,
                            Basis.SQUARE_LENGTH -2,
                            Basis.SQUARE_LENGTH -2,
                            null);
                }
            }
        }

        // 绘制剩余雷数
        Basis.printWords(g,""+(Basis.MINE_MAX-Basis.FLAG_NUM),
                Basis.MARGIN,2 * Basis.MARGIN,30,Color.blue);

        // 绘制倒计时,时间为秒,为符合习惯,超过一分钟的给予 60进制分钟计时
        int seconds =(int)((Basis.END_TIME-Basis.START_TIME)/1000);
        int min = seconds/60;
        int remainder = seconds % 60;
        if(seconds<60)
        {
            Basis.printWords(g,""+(Basis.END_TIME-Basis.START_TIME)/1000+"s",
                    Basis.MARGIN + Basis.SQUARE_LENGTH *(Basis.MAP_W-1),
                    2 * Basis.MARGIN,30,Color.magenta);
        }

        if(seconds >=60)
        {
            if(remainder<10)
            {
                Basis.printWords(g,""+min +"min0"+remainder+"s",
                        Basis.MARGIN + Basis.SQUARE_LENGTH *(Basis.MAP_W-3),
                        2 * Basis.MARGIN,30,Color.red);
            }
            if (remainder>=10)
            {
                Basis.printWords(g,""+min +"min"+remainder+"s",
                        Basis.MARGIN + Basis.SQUARE_LENGTH *(Basis.MAP_W-3),
                        2 * Basis.MARGIN,30,Color.red);
            }
        }


        switch (Basis.STATE)
        {
            case Basis.GAMING:
                    Basis.END_TIME = System.currentTimeMillis();
                    g.drawImage(Basis.face,
                    Basis.MARGIN + Basis.SQUARE_LENGTH * (Basis.MAP_W/2),
                    Basis.MARGIN,null);
            break;

            case Basis.VICTORY:
                    g.drawImage(Basis.win,
                    Basis.MARGIN + Basis.SQUARE_LENGTH * (Basis.MAP_W/2),
                    Basis.MARGIN,null);
                break;

            case Basis.LOSS:
                    g.drawImage(Basis.over,
                    Basis.MARGIN + Basis.SQUARE_LENGTH * (Basis.MAP_W/2),
                    Basis.MARGIN,null);
                break;
        }
    }

}
