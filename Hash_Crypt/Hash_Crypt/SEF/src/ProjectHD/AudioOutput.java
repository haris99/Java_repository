package ProjectHD;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class AudioOutput implements Runnable {
	private AudioFormat audioFormat;
	private TargetDataLine microphone;
	private DataLine.Info line;
	public static DataOutputStream outMic;
	public volatile boolean running = true;
	public volatile boolean stopped = false;
	private Socket connection;

	public AudioOutput(Socket socket) {
		connection = socket;
		audioFormat = new AudioFormat(8000.0f, 8, 1, true, false); // was 8 bits
		line = new DataLine.Info(TargetDataLine.class, audioFormat);

		try {
			microphone = (TargetDataLine) AudioSystem.getLine(line);
			openMicrophone();
			startMicrophone();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			System.out.println("Line is unavailable");
			e.printStackTrace();
		}
	}

	// PAREIZI IZVEIDOT THREADU

	public void start() {
		run();
	}

	public void pause() {
		running = false;
	}

	public void resume() {
		running = true;
	}

	public void stop() {
		stopped = true;
	}

	private void openMicrophone() {
		try {
			microphone.open(audioFormat);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void startMicrophone() {
		microphone.start();
	}

	public void run() {
		try {
			outMic = new DataOutputStream(connection.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("Cant get output stream");
			e1.printStackTrace();
		}
		int bytesRead = 0;
		byte[] soundBytes = new byte[1];
		int readBytes = 0;
		while (stopped == false) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while ((running == true)) { // bytesread=-1
				bytesRead = microphone.read(soundBytes, 0, soundBytes.length);
				//microphone.flush();
				// System.out.println(Arrays.toString(soundBytes));
				if (bytesRead >= 0) {
					try {
						outMic.write(soundBytes, 0, bytesRead);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// System.out.println(soundBytes.toString());

				// System.out.println(suspended);
			}
		}
	}

}
