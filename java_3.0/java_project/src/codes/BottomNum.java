package codes;

// 底层数字类,为 MapBottom服务
// 这个类只有一个作用: 创建一个对象,调用 newNum()方法,根据雷的坐标生成 3x3 范围内的数字

public class BottomNum
{
    void newNum()
    {
        for(int i=1;i<=Basis.MAP_W;i++)
        {
            for (int j=1;j<=Basis.MAP_H;j++)
            {
                if(Basis.DATA_BOTTOM[i][j]==-1) //判断这个位置是否为雷
                {
                    for (int k=i-1;k<=i+1;k++) // 遍历雷附近 3x3 的区域
                    {
                        for(int l=j-1;l<=j+1;l++)
                        {
                            if(Basis.DATA_BOTTOM[k][l]>=0) //不是雷
                                Basis.DATA_BOTTOM[k][l]++; //数值加一
                        }
                    }
                }
            }
        }
    }
}
