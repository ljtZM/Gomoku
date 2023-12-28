package FinalWuZiQi;

import java.util.Random;

import javax.swing.JOptionPane;

public class PainterControl2 {
	int chessColor = ShapeModel.BLACK;
	
	//AI下棋子的横纵坐标
	int Computer_row=0;
	int Computer_col=0;

	//用户下棋子 的横纵坐标
	int User_row=0;
	int User_col=0;
	public void reportUserPressMouse(int row, int col) {
		// 用户是否成功放置棋子
		User_row=row;
		User_col=col;
		boolean success = Vars.model2.putChess(User_row,User_col, chessColor);
		if (success) {
			Vars.paintPanel2.repaint();
			if (Vars.model2.checkWin(User_row, User_col, chessColor)) {
				String winner = (chessColor == ShapeModel.BLACK) ? "Black" : "White";
				if (winner == "Black") {
					startVictoryAnimation();
					int option = JOptionPane.showConfirmDialog(null, " 玩家胜利!, 你想要重新开始游戏吗?",
							"Game Over", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION) {
						// 用户点击确定，清空棋盘并重置游戏状态
						resetGame1();
					} else {
						// 用户点击取消，可以在这里处理其他逻辑
					}
				} else {
					startVictoryAnimation();
					int option = JOptionPane.showConfirmDialog(null, " AI胜利!,你想要重新开始游戏吗?",
							"Game Over", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION) {
						// 用户点击确定，清空棋盘并重置游戏状态
						resetGame1();
					} else {
						// 用户点击取消，可以在这里处理其他逻辑
					}
				}
			}
			chessColor = -chessColor;
			//Vars.paintPanel2.repaint();
		}
	}

	private void startVictoryAnimation() {
		 // 播放胜利音效
		Vars.musicPlayer.play("D:/a-software/biancheng/eclipse/workspace/FinalWuZiQi/audio/win.wav");
	}
	public void reportComputerMove() {
		if (chessColor == ShapeModel2.BLACK) {
			// 人类玩家的轮次，不进行任何操作
		} else {
			// 计算机玩家的轮次
			makeComputerMove();
		}
	}

	public void undoMove1() {
		//悔棋注意悔两个棋子（用户和AI）
		Vars.model2.undoMove();
		Vars.model2.RegretChess(User_col,User_row);
		
	}

	public void resetGame1() {
		Vars.model2.resetGame();
	}

	
    private void makeComputerMove() { // 基本AI：进行随机移动 
    	Random random = new Random(); 
	    do { 
	    	Computer_row = random.nextInt(ShapeModel2.WIDTH); 
	        Computer_col = random.nextInt(ShapeModel2.WIDTH); 
	    } while (!Vars.model2.putChess(Computer_row, Computer_col, chessColor));
	 
	    Vars.paintPanel2.repaint();
		if (Vars.model2.checkWin(Computer_row, Computer_col, chessColor)) {
			String winner = (chessColor == ShapeModel.BLACK) ? "Black" : "White";
			if (winner == "Black") {
				int option = JOptionPane.showConfirmDialog(null, "玩家胜利!, 你想要重新开始游戏吗?",
						"Game Over", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					// 用户点击确定，清空棋盘并重置游戏状态
					resetGame1();
				} else {
					// 用户点击取消，可以在这里处理其他逻辑
				}
			} else {
				int option = JOptionPane.showConfirmDialog(null, " AI胜利!, 你想要重新开始游戏吗?",
						"Game Over", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					// 用户点击确定，清空棋盘并重置游戏状态
					resetGame1();
				} else {
					// 用户点击取消，可以在这里处理其他逻辑
				}
			}
		}
		chessColor = -chessColor;
		Vars.paintPanel2.repaint();
    }
	
}
