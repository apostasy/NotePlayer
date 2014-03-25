package notes;

/**
 * 
 * @author Rob Vogel
 *
 */
public class Note implements Comparable<Note>
{
	/**
	 *Note is an object containing the information needed for a musical note
	 */   
	
	/**
	 * Duration of the note. Can be .0625 (1/16), .125 (1/8), .25 (1/4), .5 (1/5) 1.
	 */
	private double length;
	/**
	 * Integral value of the note, distance from center A.
	 */
	private int value;
	/**
	 * Name of note, such as C# or G.
	 */
	public String name;	
	/**
	 * Frequency of the note in hertz
	 */
	private double frequency;
	/**
	 * Decides whether or not a note is sharp
	 */
	private boolean isSharp;

	/**
	 * Empty constructor creates a note object with a quarter note of center C
	 */
	public Note()
	{
		length = .25;
		value = 3;
		name = getName();
	}
	
	/**
	 * Constructor to set the value of the note but not the length
	 * @param value
	 * 	user input of value of note
	 */
	public Note(int value)
	{
		length = .25;
		this.value = value;
		name = getName();
	}
	
	/**
	 * Should only be used with the synth program
	 * 
	 * @param length
	 * Duration of the note
	 * @param name
	 * 	Letter name of the note (A, C#...)
	 */
	public Note(double length, String name)
	{
		this.length = length;
		value = setName(name);
		name = getName();
	}
	
	/**
	 * Parameterized constructor allows user to enter a specific note
	 * @param length
	 * 	user input for length of note.
	 * @param value
	 * 	user input of value of note.
	 */   
	public Note(double length, int value)
	{
		if(validLength(length))
		{
			this.length = length;
		} else
		{
			this.length = .25;
		}
		
		if(validValue(value))
		{
			this.value = value;
		}else
		{
			this.value = 3;
		}
		
		name = getName();
		
		isSharp();
	}
	
	/**
	 * Sets the specified note to the duration given (length)
	 * @param length
	 * 	Duration given to change note to. Can be .0625 (1/16), .125 (1/8), .25 (1/4), .5 (1/5) 1. 
	 */
	public void setLength(double length)
	{
		this.length = length;
	}
	
	/**
	 * Sets the specified note to the value given (value)
	 * @param value
	 * 	 The value the note will be changed to in relation to middle A.
	 */
	public void setValue(int value)
	{
		this.value = value;
	}
	
	/**
	 * Gets the duration of the note.
	 * @return
	 * 	Returns the duration of the note
	 */
	public double getLength()
	{
		return length;
	}
	
	/**
	 * Gets the distance of the note from center A.
	 * @return
	 * 	Returns the integral distance of the note to middle A
	 */
	public int getValue()
	{
		return value;
	}
	
	/**
	 * Displays information about the note
	 */
	public String toString()
	{
		//String name = "";
		String length = "";
		
		if(this.length == 1)
		{
			length = "whole";
		}else if(this.length == .5)
		{
			length = "half";
		}else if(this.length == .25)
		{
			length = "quarter";
		}else if(this.length == .125)
		{
			length = "eighth";
		}else if(this.length == .0625)
		{
			length = "sixteenth";
		}		
		
		return "\n" + length + " note of " + name + " with frequency of " + getFrequency();
	}
	
	/**
	 * Calculates the frequency of the note in hertz
	 * @return
	 * 	Returns the frequency in hertz
	 */
	public double getFrequency()
	{
		double value = this.value;
		double power = value / 12;
		
		frequency = 440 * Math.pow(2, power);
		
		return frequency;
	}
	
	/**
	 * Gets the name of the note based on the value
	 * @return
	 * 	Returns the name of the note
	 */
	public String getName()
	{
		int noteValue = value;
		while(noteValue < 0)
		{
			noteValue += 12;
		}
		int noteName = noteValue % 12;
		
		if(noteName == 0)
		{
			name = "A";
		} else if(noteName == 1)
		{
			name = "A#";
		} else if(noteName == 2)
		{
			name = "B";
		} else if(noteName == 3)
		{
			name = "C";
		} else if(noteName == 4)
		{
			name = "C#";
		} else if(noteName == 5)
		{
			name = "D";
		} else if(noteName == 6)
		{
			name = "D#";
		} else if(noteName == 7)
		{
			name = "E";
		} else if(noteName == 8)
		{
			name = "F";
		} else if(noteName == 9)
		{
			name = "F#";
		} else if(noteName == 10)
		{
			name = "G";
		} else if(noteName == 11)
		{
			name = "G#";
		}
		
		return name;
	}
	
	/**
	 * Sets the value of the note based on A4 when given a letter value
	 * Should only be used with synth program
	 * @param name
	 * 	Name of note given
	 * @return
	 * 	Returns the number value of the note
	 */
	private int setName(String name)
	{
		int noteValue = 0;
		
		if(name.equalsIgnoreCase("a"))
		{
			noteValue = 0;
		}else if(name.equalsIgnoreCase("a#"))
		{
			noteValue = 1;
		}else if(name.equalsIgnoreCase("b"))
		{
			noteValue = 2;
		}else if(name.equalsIgnoreCase("c"))
		{
			noteValue = 3;
		}else if(name.equalsIgnoreCase("c#"))
		{
			noteValue = 4;
		}else if(name.equalsIgnoreCase("d"))
		{
			noteValue = 5;
		}else if(name.equalsIgnoreCase("d#"))
		{
			noteValue = 6;
		}else if(name.equalsIgnoreCase("e"))
		{
			noteValue = 7;
		}else if(name.equalsIgnoreCase("f"))
		{
			noteValue = -4;
		}else if(name.equalsIgnoreCase("f#"))
		{
			noteValue = -3;
		}else if(name.equalsIgnoreCase("g"))
		{
			noteValue = -2;
		}else if(name.equalsIgnoreCase("g#"))
		{
			noteValue = -1;
		}
		
		return noteValue;
	}
	
	/**
	 * Determines whether a note is sharp
	 * @return
	 * 	Returns a boolean value, true if sharp
	 */
	public boolean isSharp()
	{
		int noteValue = value;
		
		while(noteValue < 0)
		{
			noteValue += 12;
		}
		int noteName = value % 12;
		
		if(noteName == 1 || noteName == 4 || noteName == 6 || noteName == 9 || noteName == 11)
		{
			isSharp = true;
		} else 
		{
			isSharp = false;
		}
		
		return isSharp;
	}
	
	/**
	 * Determines if the duration of the note is valid
	 * @param length
	 * 	Duration of note given
	 * @return
	 * 	Returns true if valid, false if not
	 */
	public boolean validLength(double length)
	{
		boolean isValid = false;
		
		if(length == .0625)
		{
			isValid = true;
		}else if(length == .125)
		{
			isValid = true;
		}else if(length == .25)
		{
			isValid = true;
		}else if(length == .5)
		{
			isValid = true;
		}else if(length == 1)
		{
			isValid = true;
		}
		
		return isValid;
	}
	
	/**
	 * Pairs with java.sound.midi.*
	 * The Java synthesizer has a preset list of values it can take
	 * This ensures it will work in the synth
	 * @param value
	 * 	Value of the note from base A4
	 * @return
	 * 	Returns true if will work in synth, false if not
	 */
	public boolean validValue(int value)
	{
		boolean isValid = true;
		
		if(value < -57 || value > 70)
		{
			isValid = false;
		}
		
		return isValid;
	}

	@Override
	public int compareTo(Note note) 
	{
		if(this.getFrequency() > note.getFrequency())
		{
			return 1;
		} else if (this.getFrequency() < note.getFrequency())
		{
			return -1;
		}else
		{
	         if(this.getLength() > note.getLength())
	         {
	            return 1;
	         }else if(this.getLength() < note.getLength())
	         {
	            return -1;
	         }else
	         {
	            return 0;
	         }
		}
	}
}


















