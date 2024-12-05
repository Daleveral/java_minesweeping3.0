package codes;

/*  顶层地图类 :
    包含: 鼠标点击的格子坐标 (temp_x,temp_y) ,重置方法 reGame(),
          topFunctions(),包括翻开和插旗操作与胜利失败判定
         控制格子递归打开的方法,以及绘制旗子,蓝色覆盖方块,插错旗的标记
 */

import java.awt.*;
import java.sql.SQLException;

public class MapTop
{
    MapTop()
    {
        reGame();
    }
    
    // 需要接收鼠标坐标值并且将其规范到雷区当中对应的位置
    int temp_x; // temp_x 与 temp_y 为宽高的整数坐标
    int temp_y;

    // 重置方法
    void reGame()
    {
        for(int i=1;i<=Basis.MAP_W;i++)
        {
            for(int j=1;j<=Basis.MAP_H;j++)
            {
                Basis.DATA_TOP[i][j]=0; // 重置为覆盖状态
            }
        }
    }

    //包括一系列的操作,在 paintTop() 方法中被调用
    // topFunctions()调用了这个类里的 numOpen()与 spaceOpen()方法
    void topFunctions()
    {   // 通过计算式规范坐标对应的格子位置
        temp_x =0;
        temp_y =0;
        if(Basis.MOUSE_X>Basis.MARGIN && Basis.MOUSE_Y> 3*Basis.MARGIN)
        {   // 这样做的目的是防止鼠标触碰左上角边缘也会翻开附近的格子 (与负数除法的取整有关)
            temp_x =(Basis.MOUSE_X-Basis.MARGIN)/Basis.SQUARE_LENGTH +1;
            temp_y =(Basis.MOUSE_Y-Basis.MARGIN * 3)/Basis.SQUARE_LENGTH +1;
        }


        if(temp_x>=1 && temp_x<=Basis.MAP_W && temp_y>=1 && temp_y<=Basis.MAP_H)
        {
            if(Basis.LEFT) // 鼠标左键事件,覆盖则翻开
            {
                if(Basis.DATA_TOP[temp_x][temp_y]==Basis.COVER )
                {
                    Basis.DATA_TOP[temp_x][temp_y]=Basis.DISCOVER;
                }

                spaceOpen(temp_x,temp_y); // 实现空格翻开 3x3
                Basis.LEFT = false;
            }

            if(Basis.RIGHT) // 鼠标右键事件,覆盖则插旗
            {
                // 覆盖状态则插旗
                if(Basis.DATA_TOP[temp_x][temp_y]== Basis.COVER)
                {
                    Basis.DATA_TOP[temp_x][temp_y]=Basis.FLAG;
                    Basis.FLAG_NUM ++;
                }

                // 插旗状态则取消,恢复到覆盖状态
                // 此处必须是有 else,否则刚插上的旗子就被取消了 !!!
                else if(Basis.DATA_TOP[temp_x][temp_y]==Basis.FLAG)
                {
                    Basis.DATA_TOP[temp_x][temp_y]=Basis.COVER;
                    Basis.FLAG_NUM --;
                }

                else if(Basis.DATA_TOP[temp_x][temp_y]==Basis.DISCOVER)
                {
                    numOpen(temp_x,temp_y);
                }
                Basis.RIGHT = false;
            }
        }

        // 下面是失败与成功判断,为规避最后一个点击的是雷但仍显示成功的 BUG ,
        // 要在失败后立即改写游戏状态, 所以 ifVictory() 必须在单独的 if 语句里

        if(Basis.STATE == Basis.GAMING)
        { ifLoss(); }

        if(Basis.STATE == Basis.GAMING)
        { ifVictory(); }

    } // topFunctions()末

    // 数字翻开
    void numOpen(int x,int y)
    {   int count =0; // 记录旗子数
        if(Basis.DATA_BOTTOM[x][y]>0) // 是数字
        { // 需要统计该格子周围旗子的数量
            for(int i=x-1;i<=x+1;i++)
            {
                for(int j=y-1;j<=y+1;j++)
                {
                    if(Basis.DATA_TOP[i][j]==Basis.FLAG)
                    {
                        count++; //记录的旗子数 +1
                    }
                }
            }
            //如果记录的旗子数量等于底层的数字
            //那么这个数字格子周围的雷已经全部判断出来了,就可翻开周围未插旗的格子
            if(count ==Basis.DATA_BOTTOM[x][y])
            {
                for(int i=x-1;i<=x+1;i++)
                {
                    for(int j=y-1;j<=y+1;j++)
                    {
                        if(Basis.DATA_TOP[i][j] !=Basis.FLAG) //没有插旗
                        {
                            Basis.DATA_TOP[i][j]=Basis.DISCOVER; //翻开
                        }

                        // 如果打开的位置是空格,那么还要递归开格子
                        if(i>=1 && j>=1 && i<=Basis.MAP_W && j<=Basis.MAP_H)
                        { // 进行这一步判断是为了避免最外层格子被重复翻导致出问题
                            spaceOpen(i,j);
                        }
                    }
                }
            }

        }
    }

    // TODO 空格的递归
    // 打开的是空格,那么就翻出周围 3x3的格子
    // 而如果间接翻出的空格周围 3x3 还是空格,则还会触发翻开,这里就要使用递归算法 !
    void spaceOpen(int x,int y)
    {
        if(Basis.DATA_BOTTOM[x][y]==0)
        {
            for(int i=x-1;i<=x+1;i++)
            {
                for(int j=y-1;j<=y+1;j++)
                {
                    // 这里进行一个判断,被打开的空格就不会被重复打开
                    if(Basis.DATA_TOP[i][j]!=-1)
                    {
                        Basis.DATA_TOP[i][j]=-1; // 改为无覆盖状态
                        if(i>=1 && j>=1 && i<=Basis.MAP_W && j<=Basis.MAP_H)
                        { // 进行这一步判断是为了避免最外层格子被重复翻导致出问题
                            spaceOpen(i,j);
                        }
                    }
                }
            }
        }
    }


    // 失败判定 ,true表示失败
    boolean ifLoss()
    {
        for (int i=1;i<=Basis.MAP_W;i++)
        {
            for (int j=1;j<=Basis.MAP_H;j++)
            {
                if(Basis.DATA_BOTTOM[i][j] == Basis.BOMB &&
                   Basis.DATA_TOP[i][j] == Basis.DISCOVER)
                {
                    Basis.STATE =Basis.LOSS; // 失败了
                    showBombs();
                    return true;
                }
            }
        }
        return false;
    }

    // 失败后则打印所有的雷
    void showBombs()
    {
        for (int i=1;i<=Basis.MAP_W;i++)
        {
            for (int j=1;j<=Basis.MAP_H;j++)
            {
                // 底层是雷,顶层未插旗,显示雷
                if(Basis.DATA_BOTTOM[i][j]==Basis.BOMB && Basis.DATA_TOP[i][j]!=Basis.FLAG)
                {
                    Basis.DATA_TOP[i][j]=Basis.BOMB;
                }
                // 底层不是雷,顶层插旗,显示插错旗
                if(Basis.DATA_BOTTOM[i][j]!=Basis.BOMB && Basis.DATA_TOP[i][j]==Basis.FLAG)
                {
                    Basis.DATA_TOP[i][j]=Basis.WRONG_FLAG;
                }

            }
        }
    }

    // 胜利判定 true 则胜利
    boolean ifVictory()
    {
        int count =0; // 统计插旗和覆盖的格子数量
        for (int i=1;i<=Basis.MAP_W;i++)
        {
            for (int j=1;j<=Basis.MAP_H;j++)
            {
                if(Basis.DATA_TOP[i][j] != Basis.DISCOVER )
                {
                    count++;
                }
            }
        }
        if(count == Basis.MINE_MAX)
        {
            Basis.STATE =Basis.VICTORY;  // 胜利 !

            for (int i=1;i<=Basis.MAP_W;i++)
            {
                for (int j=1;j<=Basis.MAP_H;j++)
                {   // 未插旗仍保持覆盖的,变成旗
                    if(Basis.DATA_TOP[i][j]==Basis.COVER)
                    {
                        Basis.DATA_TOP[i][j]=Basis.FLAG;
                    }
                }
            }
            Basis.FLAG_NUM =Basis.MINE_MAX;
            return true;
        }
        return false;
    }


    // 绘制方法
    void paintTop(Graphics g)
    {
        topFunctions(); // todo 一直进行的 topFunctions()
        // topFunctions()方法包括了很多功能,它在 Game 类的重写的 paint()方法中被调用,
        // 而 paint() 是持续运转的,topFunctions()也在不断判断和更新游戏局势
        for (int i =1;i<=Basis.MAP_W;i++)
        {
            for (int j=1;j<=Basis.MAP_H;j++)
            {
                // 覆盖
                if(Basis.DATA_TOP[i][j]==Basis.COVER)
                {
                    g.drawImage(Basis.top,
                            Basis.MARGIN+(i-1)*Basis.SQUARE_LENGTH +1,
                            Basis.MARGIN*3+(j-1)*Basis.SQUARE_LENGTH +1,
                            Basis.SQUARE_LENGTH -2,
                            Basis.SQUARE_LENGTH -2,
                            null);
                }
                // 插旗
                if(Basis.DATA_TOP[i][j]==Basis.FLAG)
                {
                    g.drawImage(Basis.flag,
                            Basis.MARGIN+(i-1)*Basis.SQUARE_LENGTH +1,
                            Basis.MARGIN*3+(j-1)*Basis.SQUARE_LENGTH +1,
                            Basis.SQUARE_LENGTH -2,
                            Basis.SQUARE_LENGTH -2,
                            null);
                }
                // 插错旗
                if(Basis.DATA_TOP[i][j]==Basis.WRONG_FLAG)
                {
                    g.drawImage(Basis.noflag,
                            Basis.MARGIN+(i-1)*Basis.SQUARE_LENGTH +1,
                            Basis.MARGIN*3+(j-1)*Basis.SQUARE_LENGTH +1,
                            Basis.SQUARE_LENGTH -2,
                            Basis.SQUARE_LENGTH -2,
                            null);
                }

            }
        }

    }
}
