## java 扫雷游戏 3.0 注意事项 

默认情况下数据库功能是关闭的 

若想连接数据库,请确保 lib 文件夹已被添加为库,我所使用的这个 jar 对应 MySQL版本是 8.0.30,

若版本有出入,请浏览  : https://blog.csdn.net/ifubing/article/details/115436430?ops_request_misc=%7B%22request%5Fid%22%3A%22162402086016780261976676%22%2C%22scm%22%3A%2220140713.130102334.pc%5Fblog.%22%7D&request_id=162402086016780261976676&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~blog~first_rank_v2~rank_v29-1-115436430.nonecase&utm_term=jdbc&spm=1018.2226.3001.4450

然后查看 DatabaseLink 类的注释,按要求进行操作即可,创建 minesweeping 表的 SQL 语句在 createTable.txt 中

祝游戏愉快 !

作者主页 : https://github.com/Daleveral , 有 ( a little little ) 可能会接着更新 ...
