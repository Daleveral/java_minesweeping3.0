package codes;

// 初始化地雷, 这个类为 MapBottom服务
// 这个类只有一个用途,就是生成一个对象后,使其调用 newMine() 方法,生成雷
// 从而使 Basis 中的 MapBottom 数组标记雷的坐标

public class BottomMine
{
    //存放坐标,对于第 n 个雷: x = mines[2n-2],  y = mines[2n-1]
    static int [] mines = new int [Basis.MINE_MAX *2];  //设置为静态是防止切换难度时造成数组越界

    int x,y;  //地雷坐标

    boolean isPlace =true; // 是否能放置,true 为可以放置, false 为不可

    void newMine() // 重置游戏时用于生成雷
    {
        for(int i=0;i<Basis.MINE_MAX*2;i+=2)
        {           // Math.random() 范围: [0,1)
            x=(int)(Math.random()*Basis.MAP_W +1); // [1, Basis.MAP_W ]区间的整数
            y=(int)(Math.random()*Basis.MAP_H +1); // [1, Basis.MAP_H ]区间的整数

            // 判断这个格子是否已经生成了雷,即对于新生成的随机坐标(x,y),要逐个检查是否与原来生成的重复了
            for(int j =0;j<i;j=j+2)
            {
                if(x==mines[j]&& y==mines[j+1])
                {
                    i=i-2;
                    isPlace =false; // 已经有雷了,禁止再放雷
                    break;
                }
            }


            if(isPlace)
            {   // 将坐标放入数组
                mines[i]=x;
                mines[i+1]=y;
            }
            isPlace =true; // 默认下一轮循环开始时是可以放雷的
        }

        for(int i=0;i<Basis.MINE_MAX * 2 ;i=i+2)
        {
            Basis.DATA_BOTTOM[mines[i]][mines[i+1]] =-1;
        }   // 生成的雷在 DATA_BOTTOM 类中进行标记,-1即代表雷

    }

}
