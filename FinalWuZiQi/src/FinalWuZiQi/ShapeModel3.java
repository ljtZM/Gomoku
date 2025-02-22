package FinalWuZiQi;

public class ShapeModel3 {
	//表示五子棋棋盘的宽度和高度
	public static final int WIDTH = 20;
	public static final int BLACK = 1;
	public static final int WHITE = -1;
	public static final int SPACE = 0;
	private int lastMoveRow=-1;
	private int lastMoveCol=-1;
	
	//创建一个二维数组 chess，用于表示棋盘上每个位置的状态。初始状态为全部为0（空格）
	private int[][] chess = new int[WIDTH][WIDTH];

	 // 悔棋方法
    public void undoMove() {
        if (lastMoveRow != -1 && lastMoveCol != -1) {
            chess[lastMoveRow][lastMoveCol] = SPACE;
            lastMoveRow = -1;
            lastMoveCol = -1;
        }
    }
    
	public boolean putChess(int row, int col, int chessColor) {
		//检查传入的行列坐标是否在有效范围内，且指定位置为空格
		if(row>=0&&row<WIDTH&&col>=0&&col<WIDTH && chess[row][col]==SPACE){
			chess[row][col] = chessColor;//将指定位置的棋盘状态设置为传入的棋子颜色
			lastMoveCol=col;//记录最后一步的行和列
			lastMoveRow=row;
			return true;
		}
		return false;
	}
	
	public int getChess(int row, int col) {
		return chess[row][col];
		//返回指定位置的棋子颜色
	}
	
	public void resetGame() {
		//重置棋盘状态为全部空格
		for(int i=0;i<WIDTH;i++) {
			for(int j=0;j<WIDTH;j++) {
				chess[i][j]=SPACE;
			}
		}
	}
	 public boolean checkWin(int row, int col, int chessColor) {
	        // 检查横向是否有五子相连
	        if (checkConsecutive(row, col, 1, 0, chessColor)) return true;
	        // 检查纵向是否有五子相连
	        if (checkConsecutive(row, col, 0, 1, chessColor)) return true;
	        // 检查左上到右下斜线是否有五子相连
	        if (checkConsecutive(row, col, 1, 1, chessColor)) return true;
	        // 检查左下到右上斜线是否有五子相连
	        if (checkConsecutive(row, col, 1, -1, chessColor)) return true;
	        
	        return false;
	    }

	    private boolean checkConsecutive(int row, int col, int rowDelta, int colDelta, int chessColor) {
	        int count = 1;
	        // 向前检查
	        for (int i = 1; i < 5; i++) {
	            int newRow = row + i * rowDelta;
	            int newCol = col + i * colDelta;

	            if (newRow >= 0 && newRow < WIDTH && newCol >= 0 && newCol < WIDTH &&
	                chess[newRow][newCol] == chessColor) {
	                count++;
	            } else {
	                break;
	            }
	        }

	        // 向后检查
	        for (int i = 1; i < 5; i++) {
	            int newRow = row - i * rowDelta;
	            int newCol = col - i * colDelta;

	            if (newRow >= 0 && newRow < WIDTH && newCol >= 0 && newCol < WIDTH &&
	                chess[newRow][newCol] == chessColor) {
	                count++;
	            } else {
	                break;
	            }
	        }

	        return count == 5; // 如果有五子相连，则返回true
	    }
}
