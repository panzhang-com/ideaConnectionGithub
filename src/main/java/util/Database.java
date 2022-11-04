package util;

import java.util.List;

/**
 * 数据库类型的接口
 * @author 张攀，罗义锋
 * @version 1.0
 */
public interface Database {
    /**
     * 执行DQL语句，将查询结果集包装成List
     * @param sql DQL语句的字符串
     * @return 查询结果
     */
    List<String>
    executeDQL(String sql);

    /**
     * 执行DML语句
     * @param sql DML语句的字符串
     * @return 数据库中被影响到的记录条数
     */
    int executeDML(String sql);

    /**
     * 展示数据库中所有表名和视图名
     * @return 所有表名和视图名的List集合
     */
    List<String> showTables();
}
