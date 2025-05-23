package com.pvz.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Mysql {

    // --- 数据库连接参数 ---
    // 建议将这些参数配置化，而不是硬编码
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PORT = "3306";
    private static final String DEFAULT_DATABASE = "plantsVSzombie"; 
    private static final String DEFAULT_USER = "root";     
    private static final String DEFAULT_PASSWORD = "root"; 

    private String jdbcUrl;
    private String username;
    private String password;
    private Connection connection;

    /**
     * 内部类，用于封装从数据库读取的成绩数据
     */
    public static class ScoreData {
        public String nickname;
        public String difficulty;
        public int durationSeconds;
        public Timestamp scoreTime;

        public ScoreData(String nickname, String difficulty, int durationSeconds, Timestamp scoreTime) {
            this.nickname = nickname;
            this.difficulty = difficulty;
            this.durationSeconds = durationSeconds;
            this.scoreTime = scoreTime;
        }

        @Override
        public String toString() {
            return "ScoreData{" +
                   "nickname='" + nickname + '\'' +
                   ", difficulty='" + difficulty + '\'' +
                   ", durationSeconds=" + durationSeconds +
                   ", scoreTime=" + scoreTime +
                   '}';
        }
    }


    /**
     * 读取所有难度的游戏成绩排行榜 (表名: game_scores)
     * @param limit 返回记录的最大数量
     * @return 包含ScoreData对象的列表，按坚持时间降序排列
     */
    public List<ScoreData> getAllScores(int limit) {
        List<ScoreData> scores = new ArrayList<>();
        if (this.connection == null) {
            System.err.println("错误: 数据库未连接，无法读取成绩。请先调用 connect() 方法。");
            return scores; // 返回空列表
        }

        // SQL语句，按坚持时间降序排列，不筛选难度
        String sql = "SELECT nickname, difficulty, duration_seconds, score_time " +
                     "FROM game_scores " +
                     "ORDER BY duration_seconds DESC " +
                     "LIMIT ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, limit);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String nickname = rs.getString("nickname");
                    String diff = rs.getString("difficulty");
                    int duration = rs.getInt("duration_seconds");
                    Timestamp scoreTime = rs.getTimestamp("score_time");
                    scores.add(new ScoreData(nickname, diff, duration, scoreTime));
                }
                System.out.println("成功读取 " + scores.size() + " 条所有难度的成绩。");
            }
        } catch (SQLException e) {
            System.err.println("错误: 读取所有成绩时发生SQL错误。");
            e.printStackTrace();
        }
        return scores;
    }

    /**
     * 检查数据库连接是否仍然有效
     * @return true 如果连接有效, false 如果无效或未连接
     */
// ... (已有代码) ...

    /**
     * 构造函数，使用默认连接参数
     */
    public Mysql() {
        this(DEFAULT_HOST, DEFAULT_PORT, DEFAULT_DATABASE, DEFAULT_USER, DEFAULT_PASSWORD);
    }

    /**
     * 构造函数，允许自定义连接参数
     * @param host 数据库主机名或IP
     * @param port 数据库端口
     * @param databaseName 数据库名称
     * @param username 用户名
     * @param password 密码
     */
    public Mysql(String host, String port, String databaseName, String username, String password) {
        // 构建JDBC URL，包含推荐的参数以避免常见问题
        this.jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + databaseName +
                       "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
        this.username = username;
        this.password = password;
    }

    /**
     * 建立数据库连接
     * @return true 如果连接成功, false 如果失败
     */
    public boolean connect() {
        try {
            // 1. 加载JDBC驱动 (对于JDBC 4.0+ 通常是自动的，但显式加载更保险)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. 获取数据库连接
            this.connection = DriverManager.getConnection(this.jdbcUrl, this.username, this.password);
            if (this.connection != null && !this.connection.isClosed()) {
                System.out.println("成功连接到MySQL数据库: " + this.jdbcUrl.substring(0, this.jdbcUrl.indexOf("?")));
                return true;
            }
        } catch (ClassNotFoundException e) {
            System.err.println("错误: MySQL JDBC驱动未找到。请确保驱动JAR包已添加到类路径。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("错误: 连接MySQL数据库失败。");
            System.err.println("JDBC URL: " + this.jdbcUrl);
            System.err.println("用户名: " + this.username);
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("ErrorCode: " + e.getErrorCode());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 断开数据库连接
     */
    public void disconnect() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
                System.out.println("MySQL数据库连接已关闭。");
            }
        } catch (SQLException e) {
            System.err.println("错误: 关闭MySQL连接时发生错误。");
            e.printStackTrace();
        }
    }

    /**
     * 将游戏成绩存入数据库 (表名: game_scores)
     * @param nickname 玩家昵称
     * @param difficulty 游戏难度
     * @param durationSeconds 坚持时间（秒）
     * @return true 如果保存成功, false 如果失败
     */
    public boolean saveScore(String nickname, String difficulty, int durationSeconds) {
        if (this.connection == null) {
            System.err.println("错误: 数据库未连接，无法保存成绩。请先调用 connect() 方法。");
            return false;
        }

        // SQL语句使用PreparedStatement防止SQL注入
        String sql = "INSERT INTO game_scores (nickname, difficulty, duration_seconds) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // 设置参数
            pstmt.setString(1, nickname);
            pstmt.setString(2, difficulty);
            pstmt.setInt(3, durationSeconds);

            // 执行插入操作
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // 获取自动生成的主键 (如果需要)
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long id = generatedKeys.getLong(1);
                        System.out.println("成绩已成功保存到 'game_scores' 表！记录ID: " + id);
                        return true;
                    }
                }
            } else {
                System.err.println("警告: 保存成绩失败，没有行受到影响。");
            }
        } catch (SQLException e) {
            System.err.println("错误: 保存成绩时发生SQL错误。");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据难度读取游戏成绩排行榜 (表名: game_scores)
     * @param difficulty 要查询的难度
     * @param limit 返回记录的最大数量
     * @return 包含ScoreData对象的列表，按坚持时间降序排列
     */
    public List<ScoreData> getScoresByDifficulty(String difficulty, int limit) {
        List<ScoreData> scores = new ArrayList<>();
        if (this.connection == null) {
            System.err.println("错误: 数据库未连接，无法读取成绩。请先调用 connect() 方法。");
            return scores; // 返回空列表
        }

        // SQL语句，按坚持时间降序排列
        String sql = "SELECT nickname, difficulty, duration_seconds, score_time " +
                     "FROM game_scores " +
                     "WHERE difficulty = ? " +
                     "ORDER BY duration_seconds DESC " +
                     "LIMIT ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, difficulty);
            pstmt.setInt(2, limit);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String nickname = rs.getString("nickname");
                    String diff = rs.getString("difficulty"); // 可以用参数difficulty，但从DB读更准确
                    int duration = rs.getInt("duration_seconds");
                    Timestamp scoreTime = rs.getTimestamp("score_time");
                    scores.add(new ScoreData(nickname, diff, duration, scoreTime));
                }
                System.out.println("成功读取 " + scores.size() + " 条 '" + difficulty + "' 难度的成绩。");
            }
        } catch (SQLException e) {
            System.err.println("错误: 读取成绩时发生SQL错误。");
            e.printStackTrace();
        }
        return scores;
    }

    /**
     * 检查数据库连接是否仍然有效
     * @return true 如果连接有效, false 如果无效或未连接
     */
    public boolean isConnected() {
        try {
            return this.connection != null && !this.connection.isClosed() && this.connection.isValid(1); // 1秒超时
        } catch (SQLException e) {
            return false;
        }
    }

   
}
