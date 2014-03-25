package notes;

import javax.sound.midi.*;

public class PianoPlayer implements Runnable
{
	private float bpm;
	private Note[] userNotes;
	
	
	public PianoPlayer(float bpm, Note[] userNotes)
	{
		this.bpm = bpm;
		this.userNotes = userNotes;
	}
	
	public void run()
	{
		this.playSong();
	}
	
	public void playSong()
	{
		//beatsPerMin can be set to whatever the programmer wants
		float beatsPerMin = bpm;
		//beatsPerMilli will be how long each thread should wait to get desired bpm
		float beatsPerMilli = 400 / (beatsPerMin / 60);
		
		int noteCounter = 0;
		
		for(int i = 0; i < userNotes.length; i++)
		{
			if(userNotes[i] == null)
			{
				break;
			}
			
			noteCounter++;
		}
		
		Note[] notes = new Note[noteCounter];
		
		for(int i = 0; i < notes.length; i++)
		{
			notes[i] = userNotes[i];
		}
			
		try
		{
			//creates a synthesizer object and initializes it to the default synthesizer
			Synthesizer synthesizer = MidiSystem.getSynthesizer();
			//opens the synthesizer so that it can use system resources and become operational
			synthesizer.open();
			//creates a midichannel object and adds the synthesizer channel to it
			MidiChannel channel0 = synthesizer.getChannels()[0];
			
			//goes through each note in the list
			for(int i = 0; i < notes.length; i++)
			{
				//turns on the note, adding 57 because the note object is base A4 and
				//the Midi note is from 0-127 where 60 is C5
				channel0.noteOn(notes[i].getValue() + 57, 50);
				
				try
				{
					//Thread will sleep for as long as the note should last
					Thread.sleep((int)(notes[i].getLength() * beatsPerMilli));
				}catch(InterruptedException ie)
				{
					channel0.allNotesOff();
					break;
				} finally
				{
					//turns off the note so it stops playing
					channel0.noteOff(notes[i].getValue() + 57);
				}
			}
			
			synthesizer.close();
		}catch (MidiUnavailableException mue)
		{
			//getSynthesizer may throw an mue if it cannot load for any reason
			mue.printStackTrace();
		}
	}
}
