package com.rjc.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    int length; //蛇身长度
    int[] snakeX = new int[600]; //蛇身坐标X
    int[] snakeY = new int[500]; //蛇身坐标Y
    String direct; //方向
    boolean isStart; //游戏是否开始
    Timer timer = new Timer(100,this); //定时器
    int foodX;
    int foodY;
    Random random;
    boolean fail; //失败
    int score;

    public GamePanel(){
        init();
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start(); //让时间动起来
    }

    //初始化
    public void init(){
        length = 3;
        snakeX[0] = 100;snakeY[0] = 100; //头部坐标
        snakeX[1] = 75;snakeY[1] = 100; //第一个身体坐标
        snakeX[2] = 50;snakeY[2] = 100; //第二个身体坐标
        direct = "R";
        isStart = false;
        random = new Random();
        foodX = 25+25*random.nextInt(34);
        foodY = 75+75*random.nextInt(24);
        score = 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //清屏
        this.setBackground(Color.BLACK);

        //静态界面绘制填充背景
        g.fillRect(0,0,1000,820);
        //画一条静态的小蛇
        switch (direct){
            case "U":Data.up.paintIcon(this,g,snakeX[0],snakeY[0]);break;
            case "D":Data.down.paintIcon(this,g,snakeX[0],snakeY[0]);break;
            case "L":Data.left.paintIcon(this,g,snakeX[0],snakeY[0]);break;
            case "R": Data.right.paintIcon(this,g,snakeX[0],snakeY[0]);break;
        }
        for(int i = 1;i<length;i++){
            Data.body.paintIcon(this,g,snakeX[i],snakeY[i]);
        }
        //画积分
        g.setColor(Color.white);
        g.setFont(new Font("微软雅黑",Font.BOLD,20));
        g.drawString("长度: "+length,750,35);
        g.drawString("积分: "+score,750,75);
        //画食物
        Data.food.paintIcon(this,g,foodX,foodY);
        if(foodX == snakeX[0] && foodY == snakeY[0]){
            length++;
            score+=10;
            //重新生成食物
            foodX = 25+25*random.nextInt(30);
            foodY = 75+75*random.nextInt(8);
        }
        //监听游戏的暂停和启动
        if(!isStart){
            g.setColor(Color.white);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("按下空格开始游戏",300,350);
        }
        for(int i = 1;i<length;i++){
            if(snakeX[i] == snakeX[0] && snakeY[i] == snakeY[0]){
                fail = true;
                break;
            }
        }
        if(fail){
            g.setColor(Color.red);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("您已经失败,按下空格重新开始!",200,300);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //获取按下的键盘是哪个键
        int keyCode = e.getKeyCode();
        switch (keyCode){
            case KeyEvent.VK_SPACE:
                if(fail){
                    fail = false;
                    init();
                }else{
                    isStart = !isStart;
                }
                repaint();
                break;
            case KeyEvent.VK_UP:
                direct = "U";
                break;
            case KeyEvent.VK_DOWN:
                direct = "D";
                break;
            case KeyEvent.VK_LEFT:
                direct = "L";
                break;
            case KeyEvent.VK_RIGHT:
                direct = "R";
                break;
        }
    }

    public void judgeRightBorderAndMoveSnakeHead(){
        snakeX[0]+=25;
        if(snakeX[0]>900){
            snakeX[0] = 25;
        }
    }

    public void judgeLeftBorderAndMoveSnakeHead(){
        snakeX[0]-=25;
        if(snakeX[0]<25){
            snakeX[0] = 900;
        }
    }

    public void judgeUpBorderAndMoveSnakeHead(){
        snakeY[0]-=25;
        if(snakeY[0]<0){
            snakeY[0] = 720;
        }
    }

    public void judgeDownBorderAndMoveSnakeHead(){
        snakeY[0]+=25;
        if(snakeY[0]>720){
            snakeY[0] = 75;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //如果游戏处于开始状态
        if(isStart && fail == false){
            //右移
            for(int i = length-1; i>0;i--){
                snakeX[i] = snakeX[i-1];
                snakeY[i] = snakeY[i-1];
            }
            //通过控制方向让头部运动
            switch(direct){
                case "U": judgeUpBorderAndMoveSnakeHead();break;
                case "D": judgeDownBorderAndMoveSnakeHead();break;
                case "L": judgeLeftBorderAndMoveSnakeHead();break;
                case "R": judgeRightBorderAndMoveSnakeHead();break;
            }

        }
        repaint();
    }
}
