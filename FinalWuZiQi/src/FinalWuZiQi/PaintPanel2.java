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

public class PaintPanel2 extends BackgroundPanel{
	 private int gap = 15;
	    private int unit = 10;
	    private int screenWidth;
	    private int screenHeight;
	    //棋盘左上角的横纵坐标
	    private int x1, y1;

	    public JButton resetButton;
	    private Timer timer;
	    private int countdown = 10;
	    public JButton ReBeginBtn;
	    public JButton GiveinButton; 

	    private boolean resetButtonClicked = false; // 新增标志位

	    public PaintPanel2() {
	    	super("/D:/a-software/biancheng/eclipse/workspace/FinalWuZiQi/img/PK.png");
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

	                Vars.control2.reportUserPressMouse(row, col);
	                
	                startComputerMove();

	                // 用户下了一个棋子后开始悔棋计时
	                startCountdown();
	            }
	        });

	        addComponentListener(new ComponentAdapter() {
	            @Override
	            public void componentResized(ComponentEvent e) {
	                screenWidth = getWidth();
	                screenHeight = getHeight();
	                int min = Math.min(screenHeight, screenWidth);
	                unit = (min - gap * 2) / (ShapeModel2.WIDTH - 1);

	                x1 = (screenWidth - unit * (ShapeModel2.WIDTH - 1)) / 2;
	                y1 = (screenHeight - unit * (ShapeModel2.WIDTH - 1)) / 2;

	                repaint();
	            }
	        });

	        resetButton = new JButton("悔棋（10s）");
	        resetButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (!resetButtonClicked && countdown > 0) {
	                    resetButtonClicked = true;
	                    Vars.control2.undoMove1();
	                    repaint();
	                    //Vars.control2.chessColor=-Vars.control2.chessColor;
	                    stopCountdown();  // 在用户点击悔棋按钮后停止倒计时
	                }
	            }
	        });

	        timer = new Timer(1000, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (countdown > 0) {
	                    countdown--;
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
	                JOptionPane.showMessageDialog(PaintPanel2.this, "AI胜利！");
				}
			});
	        
	        add(GiveinButton);
	        
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
	        Vars.control2.resetGame1();  
	        Vars.control2.chessColor=ShapeModel.BLACK;
	        resetButton.setEnabled(true);
	        GiveinButton.setEnabled(true);
	        repaint();
	    }
	    
	    public void startComputerMove() {
	        Vars.control2.reportComputerMove();
	        startCountdown(); // 启动倒计时以等待下一次移动
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

	    private void drawChess(Graphics g) {
	        for (int row = 0; row < ShapeModel.WIDTH; row++) {
	            for (int col = 0; col < ShapeModel.WIDTH; col++) {
	                int c = Vars.model2.getChess(row, col);

	                if (c == ShapeModel.BLACK) {
	                    g.setColor(Color.black);
	                    g.fillOval(x1 + unit * col - unit / 2, y1 + unit * row - unit / 2, unit, unit);
	                } else if (c == ShapeModel.WHITE) {
	                    g.setColor(Color.white);
	                    g.fillOval(x1 + unit * col - unit / 2, y1 + unit * row - unit / 2, unit, unit);
	                }
	            }
	        }
	    }

	    private void drawChessPanel(Graphics g) {
	        for (int i = 0; i < ShapeModel.WIDTH; i++) {
	            g.drawLine(x1, y1 + unit * i, x1 + unit * (ShapeModel.WIDTH - 1), y1 + unit * i);
	            g.drawLine(x1 + unit * i, y1, x1 + unit * i, y1 + unit * (ShapeModel.WIDTH - 1));
	        }
	    }
}
