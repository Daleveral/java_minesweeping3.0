## java 扫雷游戏注意事项 

参考 [尚学堂 B 站视频](https://www.bilibili.com/video/BV13M4y1V7WQ), 做了很大幅度的修改, 使源代码可读性更好一些, 并添加了数据库功能.

默认情况下数据库功能是关闭的, 若想连接数据库,请确保 lib 文件夹已被添加为库,我所使用的这个 jar 对应 MySQL 版本是 8.0.30 . 若版本有出入,请参考 [jdbc下载mysql的驱动 mysql5版本](https://blog.csdn.net/ifubing/article/details/115436430?fromshare=blogdetail&sharetype=blogdetail&sharerId=115436430&sharerefer=PC&sharesource=Dalereral&sharefrom=from_link) , 然后查看 DatabaseLink 类的注释,按要求进行操作即可,创建 minesweeping 表的 SQL 语句在 [createTable.txt](./createTable.txt) 中.

祝游戏愉快 !


