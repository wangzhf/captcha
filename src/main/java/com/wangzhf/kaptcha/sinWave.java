package com.wangzhf.kaptcha;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


import javax.swing.JFrame;
import javax.swing.Timer;

public class sinWave extends JFrame {

    private static final int NUM = 1500;
    private static final double TWOPI = 2 * 3.1415926;
    private static final int STEP = 10;
    private int width;
    private int height;
    private int i;
    private int[] xPoint = new int[NUM];
    private int[] yPoint = new int[NUM];
    private int[] yCopyPoints = new int[STEP];
    private Timer time;

    public sinWave() {     //设置面板 出现的尺寸和位置
        setSize(600, 500);
        setBackground(Color.WHITE);
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = getWidth();
        height = getHeight();
        this.setLocation((scrSize.width - width) / 2, (scrSize.height - height) / 2);
        setVisible(true);
        //下面开始对xy 轴数据进行初始化
        for (i = 0; i < NUM; i++) {
            xPoint[i] = width * i / NUM;
            yPoint[i] = (int) (height / 2 * (1 - Math.sin(TWOPI * i / NUM)));
        }
        //下面添加一个时间监听器
        time = new Timer(200, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                //在这个时间里面主要做两件事情 先截取一段然后黏在后面
                //而且做的事情越x 无关
                for (i = 0; i < STEP; i++) {
                    yCopyPoints[i] = yPoint[i];
                }
                for (i = 0; i < NUM - STEP; i++) {
                    yPoint[i] = yPoint[i + STEP];
                }
                for (i = 0; i < STEP; i++) {
                    yPoint[i + NUM - STEP] = yCopyPoints[i];
                }
                repaint();
            }
        });
        //时间监听事件完成之后现在要做的是加一个组件适配器
        addComponentListener(new ComponentAdapter() {
            //适配器中新建一个函数 给这个函数提那家一个事件
            public void componentResized(ComponentEvent ce) {
                width = getWidth();
                height = getHeight();
                for (i = 0; i < NUM; i++) {
                    xPoint[i] = width * i / NUM;
                    yPoint[i] = (int) (height / 2 * (1 - Math.sin(TWOPI * i / NUM)));
                }
            }
        });

        time.start();
    }

    //上面是主方法里面的实现下面是画图函数
    public void paint(Graphics g) {
        super.paint(g); //继承
        g.setColor(Color.RED);
        g.drawLine(0, height / 2, width, height / 2);
        g.setColor(Color.GREEN);
        g.drawPolyline(xPoint, yPoint, NUM);

    }

    public static void main(String[] args) {
        sinWave sinwave = new sinWave();
        sinwave.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
