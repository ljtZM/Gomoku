package FinalWuZiQi;

public class AIPlayer{
	private int[][] score = new int[Vars.model4.WIDTH][Vars.model4.WIDTH];//每个位置得分
	private int goalX;
	private int goalY;
	
	public int getGoalX() {
		return goalX;
	}

	public void setGoalX(int goalX) {
		this.goalX = goalX;
	}

	public int getGoalY() {
		return goalY;
	}

	public void setGoalY(int goalY) {
		this.goalY = goalY;
	}

	public AIPlayer(){}	

	//______确定机器落子位置
	public void searchComputerMove(int [][]chess){
		//每次调用函数都初始化score评分数组，每次都要遍历整个chess数组来评判每一个位置的分数
		for(int i = 0; i  < Vars.model4.WIDTH; i++){
			for(int j = 0; j < Vars.model4.WIDTH; j++){
				score[i][j] = 0;
			}
		}
		//每次调用函数都初始化五元组内的黑棋白棋数量为0，临时得分为0
		int numOfPlayer = 0;//五元组中的黑棋数量
		int numOfAI = 0;//五元组中的白棋数量
		int tempScore = 0;//五元组得分临时变量
		//每次调用函数都初始目标坐标以及最大分数（最大分数勿忘！）
		int goalX = -1;//目标x坐标
		int goalY = -1;//目标y坐标
		int maxScore = -1;//最大分数

		//1.横向的19个行
		for(int i = 0; i < 19; i++){
			for(int j = 0; j < 15; j++){
				int k = j;
				while(k < j + 5){
					
					if(chess[i][k] == -1) numOfAI++;
					else if(chess[i][k] == 1)numOfPlayer++;
				
					k++;
				}
				tempScore = evaluateScore(numOfPlayer, numOfAI);
				//为该五元组的每个位置添加分数
				for(k = j; k < j + 5; k++){
					score[i][k] += tempScore;
				}
				//置零
				numOfPlayer = 0;//五元组中的黑棋数量
				numOfAI = 0;//五元组中的白棋数量
				tempScore = 0;//五元组得分临时变量
			}
		}
		
		//2.纵向19行
		for(int i = 0; i < 19; i++){
			for(int j = 0; j < 15; j++){
				int k = j;
				while(k < j + 5){
					if(chess[k][i] == -1) numOfAI++;
					else if(chess[k][i] == 1)numOfPlayer++;
				
					k++;
				}
				tempScore = evaluateScore(numOfPlayer, numOfAI);
				//为 
				for(k = j; k < j + 5; k++){
					score[k][i] += tempScore;
				}
				//置零
				numOfPlayer = 0;//五元组中的黑棋数量
				numOfAI = 0;//五元组中的白棋数量
				tempScore = 0;//五元组得分临时变量
			}
		}

		//3.扫描右上角到左下角上侧部分
		for(int i = 18; i >= 4; i--){
			for(int k = i, j = 0; j < 19 && k >= 0; j++, k--){
				int m = k;
				int n = j;
				while(m > k - 5 && k - 5 >= -1){
					if(chess[m][n] == -1) numOfAI++;
					else if(chess[m][n] == 1)numOfPlayer++;
					m--;
					n++;
				}
				//注意斜向判断的时候，可能构不成五元组（靠近四个角落），遇到这种情况要忽略掉
				if(m == k-5){
					tempScore = evaluateScore(numOfPlayer, numOfAI);
					//为该五元组的每个位置添加分数
					for(m = k, n = j; m > k - 5 ; m--, n++){
						score[m][n] += tempScore;
					}
				}

				//置零
				numOfPlayer = 0;//五元组中的黑棋数量
				numOfAI = 0;//五元组中的白棋数量
				tempScore = 0;//五元组得分临时变量

			}
		}
		
		//4.扫描右上角到左下角下侧部分
		for(int i = 1; i < 19; i++){
			for(int k = i, j = 18; j >= 0 && k < 19; j--, k++){
				int m = k;
				int n = j;
				while(m < k + 5 && k + 5 <= 19){
					if(chess[n][m] == -1) numOfAI++;
					else if(chess[n][m] == 1)numOfPlayer++;
					m++;
					n--;
				}
				//注意斜向判断的时候，可能构不成五元组（靠近四个角落），遇到这种情况要忽略掉
				if(m == k+5){
					tempScore = evaluateScore(numOfPlayer, numOfAI);
					//为该五元组的每个位置添加分数
					for(m = k, n = j; m < k + 5; m++, n--){
						score[n][m] += tempScore;
					}
				}
				//置零
				numOfPlayer = 0;//五元组中的黑棋数量
				numOfAI = 0;//五元组中的白棋数量
				tempScore = 0;//五元组得分临时变量

			}
		}

		//5.扫描左上角到右下角上侧部分
		for(int i = 0; i < 19; i++){
			for(int k = i, j = 0; j < 19 && k < 19; j++, k++){
				int m = k;
				int n = j;
				while(m < k + 5 && k + 5 <= 19){
					if(chess[m][n] == -1) numOfAI++;
					else if(chess[m][n] == 1)numOfPlayer++;
					
					m++;
					n++;
				}
				//注意斜向判断的时候，可能构不成五元组（靠近四个角落），遇到这种情况要忽略掉
				if(m == k + 5){
					tempScore = evaluateScore(numOfPlayer, numOfAI);
					//为该五元组的每个位置添加分数
					for(m = k, n = j; m < k + 5; m++, n++){
						score[m][n] += tempScore;
					}
				}

				//置零
				numOfPlayer = 0;//五元组中的黑棋数量
				numOfAI = 0;//五元组中的白棋数量
				tempScore = 0;//五元组得分临时变量

			}
		}	
	
		//6.扫描左上角到右下角下侧部分
		for(int i = 1; i < 19; i++){
			for(int k = i, j = 0; j < 19 && k < 19; j++, k++){
				int m = k;
				int n = j;
				while(m < k + 5 && k + 5 <= 15){
					if(chess[n][m] == -1) numOfAI++;
					else if(chess[n][m] == 1)numOfPlayer++;
					
					m++;
					n++;
				}
				//注意斜向判断的时候，可能构不成五元组（靠近四个角落），遇到这种情况要忽略掉
				if(m == k + 5){
					tempScore = evaluateScore(numOfPlayer, numOfAI);
					//为该五元组的每个位置添加分数
					for(m = k, n = j; m < k + 5; m++, n++){
						score[n][m] += tempScore;
					}
				}

				//置零
				numOfPlayer = 0;//五元组中的黑棋数量
				numOfAI = 0;//五元组中的白棋数量
				tempScore = 0;//五元组得分临时变量

			}
		}	
	
		//从空位置中找到得分最大的位置
		for(int i = 0; i < 19; i++){
			for(int j = 0; j < 19; j++){
				if(chess[i][j] == 0 && score[i][j] > maxScore){
					goalX = i;
					goalY = j;
					maxScore = score[i][j];
				}
			}
		}		

		if(goalX != -1 && goalY != -1) {
			setGoalX(goalX);
			setGoalY(goalY);
		}
	}
	
	//不同类型的五元组情况评分
	public int evaluateScore(int humanChessmanNum, int machineChessmanNum){
		//1.既有人类落子，又有机器落子，判分为0
		if(humanChessmanNum > 0 && machineChessmanNum > 0){
			return 0;
		}
		//2.全部为空，没有落子，判分为1
		if(humanChessmanNum == 0 && machineChessmanNum == 0){
			return 1;
		}
		//3.机器落1子，判分为35
		if(machineChessmanNum == 1){
			return 30;
		}
		//4.机器落2子，判分为800
		if(machineChessmanNum == 2){
			return 800;
		}
		//5.机器落3子，判分为15000
		if(machineChessmanNum == 3){
			return 15000;
		}
		//6.机器落4子，判分为800000
		if(machineChessmanNum == 4){
			return 800000;
		}
		//7.人类落1子，判分为15
		if(humanChessmanNum == 1){
			return 15;
		}
		//8.人类落2子，判分为400
		if(humanChessmanNum == 2){
			return 400;
		}
		//9.人类落3子，判分为1800
		if(humanChessmanNum == 3){
			return 1800;
		}
		//10.人类落4子，判分为100000
		if(humanChessmanNum == 4){
			return 100000;
		}
		return -1;//若是其他结果肯定出错了。这行代码根本不可能执行
	}
	
}