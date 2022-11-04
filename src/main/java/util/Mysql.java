package util;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用该类创建一个mysql数据库管理系统的对象
 * @author 张攀，罗义锋
 * @version 1.0
 */
public class Mysql {
    private final String mysqlIP;
    private final String username;
    private final String password;

    /**
     * 提供mysql的ip，登陆所用的用户名和密码，登陆对应的mysql。
     * @param mysqlIP mysql对应的主机ip
     * @param username 登陆所使用的用户名
     * @param password 用户名所对应的密码
     */
    public Mysql(String mysqlIP, String username, String password) {
        this.mysqlIP = mysqlIP;
        this.username = username;
        this.password = password;
    }

    /**
     * 只提供密码的情况下，默认以管理员方式登录本地mysql。
     * @param password 管理员密码
     */
    public Mysql(String password) {
        this("127.0.0.1", "root", password);
    }

    /**
     * 使用数据库管理系统中的一个数据库
     * @param databaseName 数据库名
     * @return 一个Database类型的对象
     */
    public Database useDatabase(String databaseName) {
        return new Database() {
            @Override
            public List<String> executeDQL(String sql) {
                Connection conn = null;
                Statement stat = null;
                ResultSet res = null;
                List<String> result = null;

                try {
                    // 1. 注册驱动
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    // 2. 获取连接
                    conn = DriverManager.getConnection("jdbc:mysql://" + mysqlIP + ":3306/" + databaseName + "?serverTimeZone=UTC", username, password);
                    // 3. 获取操作数据库的对象
                    stat = conn.createStatement();
                    // 4. 执行sql语句
                    res = stat.executeQuery(sql);
                    // 5. 处理查询结果集
                    // 创建一个用来存放结果的List对象
                    result = new ArrayList<>();

                    // 拿到结果集的元数据信息，并将列名添加到result中
                    ResultSetMetaData metaData = res.getMetaData();
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        stringBuilder.append(metaData.getColumnName(i)).append(" ");
                    }
                    result.add(stringBuilder.toString());

                    // 将结果集中的值存放到result中
                    while (res.next()) {
                        stringBuilder = new StringBuilder();
                        for (int i = 1; i <= metaData.getColumnCount(); i++) {
                            stringBuilder.append(res.getString(i)).append(" ");
                        }
                        result.add(stringBuilder.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 6. 释放资源
                    if (res != null) {
                        try {
                            res.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    if (stat != null) {
                        try {
                            stat.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                return result;
            }

            @Override
            public int executeDML(String sql) {
                Connection conn = null;
                Statement stat = null;
                int count = 0;

                try {
                    // 1. 注册驱动
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    // 2. 获取连接
                    conn = DriverManager.getConnection("jdbc:mysql://" + mysqlIP + ":3306/" + databaseName + "?serverTimeZone=UTC", username, password);
                    // 3. 获取操作数据库的对象
                    stat = conn.createStatement();
                    // 4. 执行sql语句
                    count = stat.executeUpdate(sql);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 6. 释放资源
                    if (stat != null) {
                        try {
                            stat.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return count;
            }

            @Override
            public List<String> showTables() {
                Connection conn = null;
                Statement stat = null;
                ResultSet res = null;
                List<String> result = null;

                try {
                    // 1. 注册驱动
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    // 2. 获取连接
                    conn = DriverManager.getConnection("jdbc:mysql://" + mysqlIP + ":3306/" + databaseName + "?serverTimeZone=UTC", username, password);
                    // 3. 获取操作数据库的对象
                    stat = conn.createStatement();
                    // 4. 执行sql语句
                    res = stat.executeQuery("show tables;");
                    // 5. 处理查询结果集
                    // 创建一个用来存放结果的List对象
                    result = new ArrayList<>();
                    while (res.next()) {
                        result.add(res.getString(1));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 6. 释放资源
                    if (res != null) {
                        try {
                            res.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    if (stat != null) {
                        try {
                            stat.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                return result;
            }
        };
    }
}
