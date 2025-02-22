package FinalWuZiQi;

import javax.swing.JOptionPane;

public class PainterControl4 {
	int chessColor = ShapeModel.BLACK;
	
	int Computer_row=0; 
	int Computer_col=0;
	
	int User_row=0;
	int User_col=0;
	
	AIPlayer ai=new AIPlayer();
	public void reportUserPressMouse(int row, int col) {
		// 用户是否成功放置棋子
		User_row=row;
		User_col=col;
		boolean success = Vars.model4.putChess(User_row,User_col, chessColor);
		if (success) {
			Vars.paintPanel4.repaint();
			if (Vars.model4.checkWin(User_row, User_col, chessColor)) {
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
			Vars.paintPanel4.repaint();
		}
	}

	private void startVictoryAnimation() {
		 // 播放胜利音效
		Vars.musicPlayer.play("D:/a-software/biancheng/eclipse/workspace/FinalWuZiQi/audio/win.wav");
	}
	
	public void reportComputerMove() {
		/*
		 * if (chessColor == ShapeModel4.BLACK) { // 人类玩家的轮次，不进行任何操作 } else {
		 */
			// 计算机玩家的轮次
			makeComputerMove();
		//}
	}

	public void undoMove1() {
		Vars.model4.undoMove();
		Vars.model4.RegretChess(User_col,User_row);
		
	}

	public void resetGame1() {
		Vars.model4.resetGame();
	}

	
	public void makeComputerMove() {
		ai.searchComputerMove(Vars.model4.chess);
		Computer_col=ai.getGoalY();
		Computer_row=ai.getGoalX();
		boolean success =Vars.model4.putChess(Computer_row, Computer_col, chessColor);		
		if(success) {
			Vars.paintPanel4.repaint();
	        if (Vars.model4.checkWin(Computer_row,Computer_col, chessColor)) {
	        	String winner = (chessColor == ShapeModel.BLACK) ? "Black" : "White";
				if (winner == "Black") {
					int option = JOptionPane.showConfirmDialog(null, " 玩家胜利!, 你想要重新开始游戏吗?",
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
		}
	        chessColor = -chessColor;
	        Vars.paintPanel4.repaint();
    }
}
