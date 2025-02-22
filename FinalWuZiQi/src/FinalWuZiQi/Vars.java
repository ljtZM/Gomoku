package FinalWuZiQi;

public class Vars {
//包含了与五子棋游戏相关的多个静态变量,在别的类调用时直接使用Vars.xxx.xxx
	
	//自定义
	public static PainterControl control = new PainterControl();
	public static PaintPanel paintPanel = new PaintPanel();
	public static ShapeModel model = new ShapeModel();
	
	//低级人机
	public static PainterControl2 control2 = new PainterControl2();
	public static PaintPanel2 paintPanel2 = new PaintPanel2();
	public static ShapeModel2 model2 = new ShapeModel2();
	
	//好友交互
	public static PainterControl3 control3 = new PainterControl3();
	public static PaintPanel3 paintPanel3 = new PaintPanel3();
	public static ShapeModel3 model3 = new ShapeModel3();
	public static NetHelper net=new NetHelper();
	
	//高级人机
	public static PainterControl4 control4 = new PainterControl4();
	public static PaintPanel4 paintPanel4 = new PaintPanel4();
	public static ShapeModel4 model4 = new ShapeModel4();
	
	//用户
	public static UserInfo userInfo=new UserInfo();
	
	//音乐
	public static MusicPlayer musicPlayer=new MusicPlayer();
	
}
