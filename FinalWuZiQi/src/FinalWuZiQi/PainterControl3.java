package FinalWuZiQi;

import javax.swing.JOptionPane;

public class PainterControl3 {
	int chessColor = ShapeModel3.BLACK;
	private boolean openDoor=true;
	
	public void reportUserPressMouse(int row, int col) {
		
		//用户是否成功放置棋子
		if(!openDoor) {//门不开，不能下棋
			return;
		}
		boolean success = Vars.model3.putChess(row,col,chessColor);
		if(success){
			Vars.paintPanel3.repaint();
			if(Vars.model3.checkWin(row, col, chessColor)) {
				String winner = (chessColor == ShapeModel.BLACK) ? "Black" : "White";
				if (chessColor==1&&winner == "Black") {
					 startVictoryAnimation();
					int option = JOptionPane.showConfirmDialog(null,winner+ " player wins!",
							"Game Over", JOptionPane.CLOSED_OPTION);
					Vars.userInfo.setRank(Vars.userInfo.getRank()+10);
				} else if(winner=="White"){
					 startVictoryAnimation();
					int option = JOptionPane.showConfirmDialog(null,winner+ " player wins!",
							"Game Over", JOptionPane.CLOSED_OPTION);
				}
				
			}
			openDoor=false;//这边下完棋后就把门关上，再点击棋盘也无法正确下棋
			Vars.paintPanel3.repaint();
			Vars.net.sendChess(row, col);//告诉对方下棋成功
		}
	}
	
	private void startVictoryAnimation() {
		 // 播放胜利音效
		Vars.musicPlayer.play("D:/a-software/biancheng/eclipse/workspace/FinalWuZiQi/audio/win.wav");
	}

	public void otherChess(int row, int col) {
		chessColor=-chessColor;
		boolean success = Vars.model3.putChess(row,col,chessColor);
		if(success){
			Vars.paintPanel3.repaint();
			openDoor=true;//另一方下完棋，把openDoor置为真，才能继续正确下棋
			chessColor=-chessColor;
			Vars.paintPanel3.repaint();
		}
	}

	public void otherChat(String line) {
	}
	
	public void setChessColor(int color) {
		this.chessColor=color;
	}
	
	public void setOpenDoor(boolean openDoor) {
		this.openDoor = openDoor;
	}

	
}
