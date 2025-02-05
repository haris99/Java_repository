package ProjectHD;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class AudioInput implements Runnable {
    private AudioFormat audioFormat;
    private DataLine.Info line;
    private Socket connection = null;
    private SourceDataLine inSpeaker = null;
    private DataInputStream soundIn = null;
	private volatile boolean running = true;
	private volatile boolean stopped = false;
    
    
    public AudioInput(Socket socket){
    	connection = socket;
    	audioFormat = new AudioFormat(8000.0f,8,1,true,false);
    	line = new DataLine.Info(SourceDataLine.class, audioFormat);
    	try {
			inSpeaker = (SourceDataLine)AudioSystem.getLine(line);
			//soundIn = new DataInputStream(connection.getInputStream());
			try {
				soundIn = new DataInputStream(connection.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			openSpeaker();
			startSpeaker();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			System.out.println("Line is unavailable");
			e.printStackTrace();
		}
    }
    
    public void start(){
    	run();
    }
    
    private void openSpeaker(){
    	try {
			inSpeaker.open();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			System.out.println("Line is unavailable, cant open");
			e.printStackTrace();
		}
    }
    
    private void startSpeaker(){
    	inSpeaker.start();
    }
    
	public void pause(){
		running = false;
	}
	
	public void resume(){
		running = true;
	}
	
	public void stop(){
		stopped = true;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int minVolume = 0;
		int bytesRead = 0;
		int volume;
	    byte[] inSound = new byte[1];
	    while (!stopped){
	    while(running == true)
	    {
	        try{bytesRead = soundIn.read(inSound, 0, inSound.length);} catch (Exception e){
	        	System.out.println("Cant read bytes!");
	        }
	        //System.out.println(Arrays.toString(inSound));
	        //System.out.println(Integer.parseInt(Arrays.toString(inSound).replaceAll("[\\D]", "")));
	        volume = Integer.parseInt(Arrays.toString(inSound).replaceAll("[\\D]", ""));
	         
	        if(bytesRead >= 0 && Math.abs(volume) >= minVolume )
	        {
	        	System.out.println(volume);
	            inSpeaker.write(inSound, 0, bytesRead);
	            //inSpeaker.flush();
	        }
	      }
	    }
	   }
		
	}

