package FinalWuZiQi;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicPlayer {
	private Clip clip;
	public void play(String filePath) {
		try {
			// 创建音频文件对象
			File audioFile=new File(filePath);
			AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(audioFile);
			AudioFormat format = audioInputStream.getFormat();
			//AudioInputStream 是 Java 音频输入流的类，用于读取音频数据。
			//getFormat() 方法用于获取音频输入流的格式，包括采样率、每个样本的位数、通道数等信息。
            DataLine.Info info = new DataLine.Info(Clip.class, format);
   
            
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioInputStream);
            clip.start();
		}catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
	}
	
	// 停止播放音乐
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close(); // 关闭Clip以释放资源
        }
    }


}
