package FinalWuZiQi;

import javax.swing.JOptionPane;

public class PainterControl {
	//初始化chessColor
	int chessColor = ShapeModel.BLACK;

	public void reportUserPressMouse(int row, int col) {
		//用户是否成功放置棋子
		boolean success = Vars.model.putChess(row,col,chessColor);
		if(success){
			//若用户成功下棋，重绘棋盘
			Vars.paintPanel.repaint();
			if(Vars.model.checkWin(row, col, chessColor)) {//判断有没有人胜利
				String winner = (chessColor == ShapeModel.BLACK) ? "Black" : "White";
				startVictoryAnimation();
                int option=JOptionPane.showConfirmDialog(null, winner + " 玩家获胜！, 你想要清空棋盘吗?", "Game Over", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    // 用户点击确定，清空棋盘并重置游戏状态
                	resetGame1();
                } else {
                }
			}
			chessColor = -chessColor;
			//Vars.paintPanel.repaint();
		}
	}
	
	private void startVictoryAnimation() {
		 // 播放胜利音效
		Vars.musicPlayer.play("D:/a-software/biancheng/eclipse/workspace/FinalWuZiQi/audio/win.wav");
	}
	//悔棋函数
	public void undoMove1() {
		Vars.model.undoMove();
	}
	
	//清空棋盘函数
	public void resetGame1() {
		Vars.model.resetGame();
	}
	
}
