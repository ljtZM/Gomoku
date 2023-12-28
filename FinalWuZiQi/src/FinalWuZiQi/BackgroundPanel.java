package FinalWuZiQi;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel{
	private Image backgroundImage;
	
	public BackgroundPanel(String imagePath) {
		backgroundImage=new ImageIcon(imagePath).getImage();
	}
	//覆盖了 JPanel 的 paintComponent 方法，用于在面板上绘制内容。
	//在 paintComponent 方法中，首先调用 super.paintComponent(g) 执行默认的绘制操作，然后在其上层绘制背景图片。
	//使用 g.drawImage() 方法将背景图片绘制到面板上，(0, 0) 是绘制的起始位置，getWidth() 和 getHeight() 分别是面板的宽度和高度，确保背景图片充满整个面板。
	//这样，BackgroundPanel 类就可以作为一个带有背景图片的面板使用，通过在窗口中添加这个面板，可以实现窗口的背景图设置。
	 @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	    }
}
