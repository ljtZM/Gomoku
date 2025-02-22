package FinalWuZiQi;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class PaintPanel3 extends JPanel {
    private int gap = 15;
    private int unit = 10;
    private int screenWidth;
    private int screenHeight;
    private int x1, y1;

    private JPanel chatPanel;  // 新添加的聊天区域面板
    private JPanel chessPanel;

    public JTextArea chatArea;
    private JTextField messageInput;
    
    public JButton GiveinButton; 
    
    //新东西
    private JTextField ipTF=new JTextField(20);
    private JTextField portTF=new JTextField(10);
    private JButton listenBtn=new JButton("listen");
    private JButton connBtn=new JButton("connect");
	
    public PaintPanel3() {

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                int row = (y - y1) / unit;
                int col = (x - x1) / unit;

                if ((y - y1) % unit > unit / 2) {
                    row++;
                }
                if ((x - x1) % unit > unit / 2) {
                    col++;
                }

                Vars.control3.reportUserPressMouse(row, col);
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                screenWidth = getWidth()-300;
                screenHeight = getHeight();
                int min = Math.min(screenHeight, screenWidth);
                unit = (min - gap * 2) / (ShapeModel3.WIDTH - 1);

                x1 = (screenWidth - unit * (ShapeModel3.WIDTH - 1)) / 2;
                y1 = (screenHeight - unit * (ShapeModel3.WIDTH - 1)) / 2;

                repaint();
            }
        });

        chessPanel = new BackgroundPanel("/D:/a-software/biancheng/eclipse/workspace/FinalWuZiQi/img/PK.png") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawChessPanel(g); // 在chessPanel上绘制棋盘
                drawChess(g);      // 在chessPanel上绘制棋子
            }
        };
        
        GiveinButton=new JButton("认输");
        GiveinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Vars.control3.chessColor == 1) {
                    JOptionPane.showMessageDialog(PaintPanel3.this, "白棋玩家胜利！");
                    Vars.userInfo.setRank(Vars.userInfo.getRank()-10);
                } else {
                    JOptionPane.showMessageDialog(PaintPanel3.this, "黑棋玩家胜利！");
                }
            }
        });

        ipTF.setText("localhost");
        portTF.setText("8000");

        listenBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vars.net.startListen();
                Vars.control3.setChessColor(ShapeModel2.BLACK);
                Vars.control3.setOpenDoor(true);
            }
        });

        connBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip=ipTF.getText();
                String port=portTF.getText();
                Vars.control3.setChessColor(ShapeModel2.WHITE);
                Vars.control3.setOpenDoor(false);//门先关上，对面下下棋
                Vars.net.connect(ip, Integer.parseInt(port));
            }
        });

        JPanel ButtonPanel=new JPanel();
        
        ButtonPanel.add(ipTF);
        ButtonPanel.add(portTF);
        ButtonPanel.add(listenBtn);
        ButtonPanel.add(connBtn);
        ButtonPanel.add(GiveinButton);
        
        chessPanel.add(ButtonPanel,BorderLayout.NORTH);
        // 创建聊天区域
        chatPanel = new JPanel(new BorderLayout());
      
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);

        messageInput = new JTextField();
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(messageInput, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        chatPanel.add(chatScrollPane, BorderLayout.CENTER);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        
     // 设置chessPanel的宽度
        chessPanel.setPreferredSize(new Dimension(600, 800));
        add(chessPanel, BorderLayout.WEST);

        // 设置chatPanel的宽度
        chatPanel.setPreferredSize(new Dimension(200, 800));
        add(chatPanel, BorderLayout.EAST);

    }

    private void sendMessage() {
        String message = messageInput.getText().trim();
        if (!message.isEmpty()) {
            chatArea.append("You: " + message + "\n");
            messageInput.setText("");
            Vars.net.sendChat(message);
        }
    }

    private void drawChess(Graphics g) {
        for (int row = 0; row < ShapeModel3.WIDTH; row++) {
            for (int col = 0; col < ShapeModel3.WIDTH; col++) {
                int c = Vars.model3.getChess(row, col);

                if (c == ShapeModel3.BLACK) {
                    g.setColor(Color.black);
                    g.fillOval(x1 + unit * col - unit / 2, y1 + unit * row - unit / 2, unit, unit);
                } else if (c == ShapeModel3.WHITE) {
                    g.setColor(Color.white);
                    g.fillOval(x1 + unit * col - unit / 2, y1 + unit * row - unit / 2, unit, unit);
                }
            }
        }
    }

    private void drawChessPanel(Graphics g) {
        for (int i = 0; i < ShapeModel3.WIDTH; i++) {
            g.drawLine(x1, y1 + unit * i, x1 + unit * (ShapeModel3.WIDTH - 1), y1 + unit * i);
            g.drawLine(x1 + unit * i, y1, x1 + unit * i, y1 + unit * (ShapeModel3.WIDTH - 1));
        }
    }
}
