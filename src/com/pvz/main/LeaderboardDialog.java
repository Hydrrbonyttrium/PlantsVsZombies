package com.pvz.main;

import com.pvz.db.Mysql;
import com.pvz.db.Mysql.ScoreData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class LeaderboardDialog extends JDialog {
    private Mysql mysql;
    private JTable leaderboardTable;
    private JComboBox<String> difficultyComboBox;
    private DefaultTableModel tableModel;

    // 确保这些难度字符串与 GamePanel 中保存到数据库时使用的字符串一致
    private final String[] DIFFICULTIES = { "ALL","EASY", "NORMAL", "HARD", "TEST" };

    public LeaderboardDialog(JFrame parent, Mysql mysql) {
        super(parent, "排行榜", true); // true 表示模态对话框
        this.mysql = mysql;

        setSize(600, 400);
        setLocationRelativeTo(parent); // 相对于父窗口居中
        setLayout(new BorderLayout());

        // 顶部面板：用于选择难度
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("选择难度:"));
        difficultyComboBox = new JComboBox<>(DIFFICULTIES);
        difficultyComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadScores();
            }
        });
        topPanel.add(difficultyComboBox);
        add(topPanel, BorderLayout.NORTH);

        // 中部面板：表格显示排行榜数据
        String[] columnNames = { "排名", "昵称", "难度", "坚持时间 (秒)", "得分时间" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 设置表格单元格不可编辑
            }
        };
        leaderboardTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        add(scrollPane, BorderLayout.CENTER);

        // 底部面板：关闭按钮
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("关闭");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 关闭对话框
            }
        });
        bottomPanel.add(closeButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // 初始化时加载数据
        // 确保mysql对象不为null并且已连接
        if (this.mysql != null) {
            if (!this.mysql.isConnected()) {
                this.mysql.connect();
            }
            if (this.mysql.isConnected()) {
                loadScores(); // 确保在构造函数末尾调用 loadScores
            } else {
                JOptionPane.showMessageDialog(this, "数据库连接失败，无法加载排行榜。", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } else {
             JOptionPane.showMessageDialog(this, "数据库服务未初始化，无法加载排行榜。", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadScores() {
        if (mysql == null || !mysql.isConnected()) {
            tableModel.setRowCount(0); // 清空表格
            // 如果mysql对象存在但未连接，则提示用户
            if (mysql != null && !mysql.isConnected()) {
                JOptionPane.showMessageDialog(this, "数据库未连接，无法加载排行榜数据。", "提示", JOptionPane.WARNING_MESSAGE);
            } else if (mysql == null) {
                JOptionPane.showMessageDialog(this, "数据库服务异常，无法加载排行榜数据。", "错误", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        String selectedDifficulty = (String) difficultyComboBox.getSelectedItem();
        if (selectedDifficulty == null) {
            return; // 如果没有选中的难度，则不执行任何操作
        }

        List<ScoreData> scores;
        // 定义获取排行榜记录的数量上限
        int limit = 10; // 您可以根据需要调整这个值，或者使其可配置

        if ("ALL".equals(selectedDifficulty)) {
            // 如果选择的是 "ALL"，则调用新的 getAllScores 方法
            scores = mysql.getAllScores(limit);
        } else {
            // 否则，按原方式获取特定难度的成绩
            scores = mysql.getScoresByDifficulty(selectedDifficulty, limit);
        }
        // 清空表格的旧数据
        tableModel.setRowCount(0);

        if (scores != null) {
            int rank = 1;
            for (ScoreData score : scores) {
                Vector<Object> row = new Vector<>();
                row.add(rank++);
                row.add(score.nickname);
                row.add(score.difficulty);
                row.add(score.durationSeconds);
                row.add(score.scoreTime); // Timestamp对象会被JTable默认渲染器格式化
                tableModel.addRow(row);
            }
        } else {
            // 如果获取数据失败，可以给出提示
            System.err.println("未能加载难度为: " + selectedDifficulty + " 的排行榜数据。");
        }
    }
}
