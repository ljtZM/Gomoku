package FinalWuZiQi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PaintPanel extends BackgroundPanel {
    private int gap = 15;  //棋盘与窗口边界的距离
    private int unit = 10; //每一个小棋格的宽度
    private int screenWidth;//窗口宽度
    private int screenHeight;//窗口高度
    private int x1, y1;//x1,y1分别是棋盘左上角横纵坐标

    public JButton resetButton;//悔棋按钮
    private Timer timer;//计时器
    private int countdown = 10;//倒计时
    
    public JButton GiveinButton; //认输按钮
    
    public JButton RetryMovebtn;//复盘按钮
    public JButton preMovebtn;//上一步
    public JButton nextMovebtn;//下一步
    
    public JButton ReBeginBtn;//重新开始游戏按钮
    private int[][][]chessBoard= {};//初始化一个空棋盘，为复盘做准备(按顺序存储之前下的棋)
    int moveIndex=0;  //记录第几步
    int index=0;

    private boolean resetButtonClicked = false; // 新增标志位
	protected int row;
	protected int col;

    public PaintPanel() {
    	super("/D:/a-software/biancheng/eclipse/workspace/FinalWuZiQi/img/PK.png");
    	 chessBoard=new int[100][ShapeModel.WIDTH][ShapeModel.WIDTH];
         addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
            	//x和y分别是鼠标点击的横纵坐标
                int x = e.getX();
                int y = e.getY();

                //col和row分别是鼠标点击在棋盘上的横纵坐标 
                int row = (y - y1) / unit;
                int col = (x - x1) / unit;

                //用户不可能精准点击在交叉处，精准修正
                if ((y - y1) % unit > unit / 2) {
                    row++;
                }
                if ((x - x1) % unit > unit / 2) {
                    col++;
                }

                Vars.control.reportUserPressMouse(row, col);
                //在ChessBoard数组内记录，为复盘做准备
                chessBoard[moveIndex++][row][col]=-Vars.control.chessColor;
                // 用户下了一个棋子后开始悔棋计时
                startCountdown();
            }
        });

         //addComponentListener 方法用于向组件添加监听器，这里使用了匿名内部类 ComponentAdapter。
         //componentResized 方法是 ComponentAdapter 提供的方法，当组件大小发生变化时会触发该方法
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
            	//用户可以拖动窗口的大小，实时获得窗口的宽和高
                screenWidth = getWidth();
                screenHeight = getHeight();
                
                //为了使棋盘居中操作
                int min = Math.min(screenHeight, screenWidth);
                unit = (min - gap * 2) / (ShapeModel.WIDTH - 1);

                x1 = (screenWidth - unit * (ShapeModel.WIDTH - 1)) / 2;
                y1 = (screenHeight - unit * (ShapeModel.WIDTH - 1)) / 2;
                repaint();
            }
        });

        resetButton = new JButton("悔棋（10s）");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!resetButtonClicked && countdown > 0) {
                	//如果悔棋按钮没被点并且倒计时没结束，此时点击生效
                    resetButtonClicked = true;
                    Vars.control.undoMove1();
                    //悔棋后别忘了repaint
                    repaint();
                    //因为在别的操作处执行了棋子颜色交换操作（reportUserPressMouse，自动改变棋子颜色），但悔棋后颜色不应该做出改变，所以再来一个棋子颜色改变操作
                    Vars.control.chessColor=-Vars.control.chessColor;
                    stopCountdown();  // 在用户点击悔棋按钮后停止倒计时
                }
            }
        });

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (countdown > 0) {
                    countdown--;
                    //使悔棋按钮上的倒计时实时更改
                    updateButtonLabel();
                } else {
                    stopCountdown();  // 在计时结束后停止倒计时
                }
            }
        });

       // 添加按钮到面板
        add(resetButton);
        
        GiveinButton=new JButton("认输");
        GiveinButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Vars.control.chessColor == 1) {//1表示的是黑色棋子
                    JOptionPane.showMessageDialog(PaintPanel.this, "白棋玩家胜利！");
                } else {
                    JOptionPane.showMessageDialog(PaintPanel.this, "黑棋玩家胜利！");
                }
			}
		});
        
        add(GiveinButton);
        
        RetryMovebtn=new JButton("复盘");
        preMovebtn=new JButton("上一步");
        nextMovebtn=new JButton("下一步");
        add(RetryMovebtn);
        add(preMovebtn);
        add(nextMovebtn);
        //在未点击复盘按钮时，上一步，下一步按钮为不可点击状态
        preMovebtn.setEnabled(false);
        nextMovebtn.setEnabled(false);
        
        RetryMovebtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int option=JOptionPane.showConfirmDialog(null, " 你想要清空棋盘吗?", "复盘", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    // 用户点击确定，清空棋盘并重置游戏状态
                	Vars.control.resetGame1();
                	index=0;//index为复盘时的用户下棋顺序跟踪
                	repaint();
                	
                }else {
                	index=moveIndex;//index为复盘时的用户下棋顺序跟踪
                }
                
				nextMovebtn.setEnabled(true);
                preMovebtn.setEnabled(true);
				resetButton.setEnabled(false);
				GiveinButton.setEnabled(false);
			}
		});
        
        preMovebtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				preMove();
			}
		});
        
        nextMovebtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				nextMove();
			}
		});
       
        ReBeginBtn=new JButton("重新开始游戏");
        ReBeginBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ReBegin();
			}
		});
        
        add(ReBeginBtn);
    }

    protected void ReBegin() {
    	//所有东西都清空之后repaint
        Vars.control.resetGame1();  
        Vars.control.chessColor=ShapeModel.BLACK;
        index = 0;
        moveIndex = 0;
        resetButton.setEnabled(true);
        GiveinButton.setEnabled(true);
        preMovebtn.setEnabled(false);
        nextMovebtn.setEnabled(false);
        repaint();
    }

	protected void nextMove() {
//之前一直不对，因为之前只保存了最后一次移动的棋子状态，之前的全被覆盖掉，所以呈现的效果是一次棋盘只出现一个棋子
        if (index < chessBoard.length - 1) {
            index++;
            for (int i = 0; i < index; i++) {
                for (int row = 0; row < ShapeModel.WIDTH; row++) {
                    for (int col = 0; col < ShapeModel.WIDTH; col++) {
//在这个做出更改，当i==0时，才实现完全覆盖，不为0时，只实现有棋子的地方进行覆盖
                        if (i == 0) {
                            Vars.model.chess[row][col] = chessBoard[i][row][col];
                        } else {
                            if (chessBoard[i][row][col] != 0) {
                                Vars.model.chess[row][col] = chessBoard[i][row][col];
                            }
                        }
                    }
                }
            }
            repaint();
        }
    }

    protected void preMove() {
//之前的错误同理，已改正
        if (index > 0) {
            index--;
            for (int i = index - 1; i >= 0; i--) {
                for (int row = 0; row < ShapeModel.WIDTH; row++) {
                    for (int col = 0; col < ShapeModel.WIDTH; col++) {
                        if (i == index - 1) {
                            Vars.model.chess[row][col] = chessBoard[i][row][col];
                        } else {
                            if (chessBoard[i][row][col] != 0) {
                                Vars.model.chess[row][col] = chessBoard[i][row][col];
                            }
                        }
                    }
                }
            }
            repaint();
        }
    }



	protected void startCountdown() {
        timer.restart();
        countdown = 10;
        resetButtonClicked = false;
        resetButton.setEnabled(true);
        updateButtonLabel();
    }

    private void stopCountdown() {
        timer.stop();
        countdown = 0;
        resetButton.setEnabled(false);
        updateButtonLabel();
    }

    protected void updateButtonLabel() {
        resetButton.setText("悔棋（" + countdown + "s）");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawChessPanel(g);
        drawChess(g);
    }
//画棋子（实心椭圆）
    private void drawChess(Graphics g) {
    	for (int row = 0; row < ShapeModel.WIDTH; row++) {
            for (int col = 0; col < ShapeModel.WIDTH; col++) {
                int c = Vars.model.getChess(row, col);

                if (c == ShapeModel.BLACK) {
                    g.setColor(Color.black);
                    //(x1 + unit * col - unit / 2, y1 + unit * row - unit / 2) 是椭圆椭圆外接矩形左上角的坐标。
                    //(unit, unit) 是椭圆的宽度和高度。
                    g.fillOval(x1 + unit * col - unit / 2, y1 + unit * row - unit / 2, unit, unit);
                } else if (c == ShapeModel.WHITE) {
                    g.setColor(Color.white);
                    g.fillOval(x1 + unit * col - unit / 2, y1 + unit * row - unit / 2, unit, unit);
                }
            }
        }
    }
//画棋盘，画横线，画竖线
    private void drawChessPanel(Graphics g) {
        for (int i = 0; i < ShapeModel.WIDTH; i++) {
        	//绘制水平线
        	//x1 是起始点的 x 坐标，y1 + unit * i 是起始点的 y 坐标，
        	//x1 + unit * (ShapeModel.WIDTH - 1) 是结束点的 x 坐标，y1 + unit * i 是结束点的 y 坐标。这样就绘制了一条水平线。
            g.drawLine(x1, y1 + unit * i, x1 + unit * (ShapeModel.WIDTH - 1), y1 + unit * i);
            //绘制垂直线
            //x1 + unit * i 是起始点的 x 坐标，y1 是起始点的 y 坐标，
            //x1 + unit * i 是结束点的 x 坐标，y1 + unit * (ShapeModel.WIDTH - 1) 是结束点的 y 坐标。这样就绘制了一条垂直线。
            g.drawLine(x1 + unit * i, y1, x1 + unit * i, y1 + unit * (ShapeModel.WIDTH - 1));
        }
    }
}
