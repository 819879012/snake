package com.rjc.snake;


import javax.swing.*;

public class StartGames {
    public static void main(String[] args) {
        //1.创建框架
        JFrame frame = new JFrame("贪吃蛇小游戏");
        frame.setBounds(500,200,900,720);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GamePanel());
        frame.setVisible(true);
    }
}