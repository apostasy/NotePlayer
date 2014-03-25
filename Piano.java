package notes;

import java.awt.*;
import java.awt.event.*;
//import java.lang.reflect.InvocationTargetException;

//import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
@SuppressWarnings({"serial", "rawtypes"})

/**
 * 
 * @author Rob Vogel
 *
 */
public class Piano extends JFrame implements ActionListener
{
	private JTextPane scoreWindow;
	private JComboBox noteBox;
	private JComboBox lengthBox;
	private String[] notesList;
	private String[] lengthList = {"1", "1/2", "1/4", "1/8", "1/16"};
	private String[] loopList = {"0", "1", "2", "3", "4"};
	private String line1 = "";
	private String line2 = "\n";
	private String line3 = "\n";
	private String line4 = "\n";
	private String line5 = "\n";
	private String line6 = "\n";
	private String line7 = "\n";
	private JLabel lblLength;
	private JButton btnAddNote;
	private JButton btnRemoveNote;
	private Note[] userNotes;
	private int noteCounter = 0;
	private JComboBox loopBox;
	private JLabel lblLoops;
	private JButton btnPlaySong;
	private JTextField txtBPM;
	private JButton btnStopSong;
	private Thread piano;
	//private boolean startSong;

	
	@SuppressWarnings({ "unchecked" })
	/**
	 * Piano writes the JFrame to the screen
	 */
	public Piano()
	{
		super("Piano Player");
		
		userNotes = new Note[28];
		
		notesList = new String[12];
		
		for(int i = 0; i < notesList.length; i++)
		{
			Note[] note = new Note[12];
			
			note[i] = new Note(i);
			
			notesList[i] = note[i].name;
		}
		Font font = new Font("Courier New", Font.PLAIN, 12);
		
		scoreWindow = new JTextPane();
		scoreWindow.setEditable(false);
		
		scoreWindow.setFont(font);
		
		noteBox = new JComboBox(notesList);
		
		JLabel lblNote = new JLabel("Pick a note");
		
		lengthBox = new JComboBox(lengthList);
		
		lblLength = new JLabel("Pick a length");
		
		btnAddNote = new JButton("Add Note");
		
		btnRemoveNote = new JButton("Remove Note");
		
		loopBox = new JComboBox(loopList);
		
		lblLoops = new JLabel("Loops");
		
		btnPlaySong = new JButton("Play Song");
		
		txtBPM = new JTextField();
		txtBPM.setText("120");
		txtBPM.setColumns(10);
		
		JLabel lblBpm = new JLabel("bpm");
		
		btnStopSong = new JButton("Stop Song");
	      
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scoreWindow, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblLoops))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(txtBPM, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(btnPlaySong, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(loopBox, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnStopSong)
							.addGap(124)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(noteBox, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNote))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lengthBox, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblLength))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnAddNote, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
								.addComponent(btnRemoveNote, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblBpm)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scoreWindow, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
					.addGap(66)
					.addComponent(lblBpm)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtBPM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLoops)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblLength)
							.addComponent(lblNote)
							.addComponent(loopBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnAddNote))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnPlaySong)
						.addComponent(noteBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lengthBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRemoveNote)
						.addComponent(btnStopSong))
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
		
		this.btnAddNote.addActionListener(this);
		this.btnRemoveNote.addActionListener(this);
		this.btnPlaySong.addActionListener(this);
		this.btnStopSong.addActionListener(this);
		
		setSize(650, 400);
		setVisible(true);
	}
	
	/**
	 * Updates score when notes are added
	 * @param text
	 * The String that will be written to the scoreWindow
	 */
	private void showNotes(final String text)
	{
		SwingUtilities.invokeLater(
			new Runnable()
			{
				public void run()
				{
					scoreWindow.setText(drawScore(text));
				}
			}
		);
	}
	
	/**
	 * Creates the string that will be written to the screen
	 * @param text
	 * 	Which note should be added to the screen
	 * @return
	 * 	Returns the string to be written to the screen
	 */
	private String drawScore(String text)
	{
		String score = "";
		
		if(text.equalsIgnoreCase("c"))
		{
			line1 += "---";
			line2 += "   ";
			line3 += "-O-";
			line4 += "   ";
			line5 += "---";
			line6 += "   ";
			line7 += "---";					
		}else if(text.equalsIgnoreCase("c#"))
		{
			line1 += "---";
			line2 += "   ";
			line3 += "-O#";
			line4 += "   ";
			line5 += "---";
			line6 += "   ";
			line7 += "---";					
		}else if(text.equalsIgnoreCase("d"))
		{
			line1 += "---";
			line2 += " O ";
			line3 += "---";
			line4 += "   ";
			line5 += "---";
			line6 += "   ";
			line7 += "---";					
		}else if(text.equalsIgnoreCase("d#"))
		{
			line1 += "---";
			line2 += " O#";
			line3 += "---";
			line4 += "   ";
			line5 += "---";
			line6 += "   ";
			line7 += "---";					
		}else if(text.equalsIgnoreCase("e"))
		{
			line1 += "-O-";
			line2 += "   ";
			line3 += "---";
			line4 += "   ";
			line5 += "---";
			line6 += "   ";
			line7 += "---";					
		}else if(text.equalsIgnoreCase("f"))
		{
			line1 += "---";
			line2 += "   ";
			line3 += "---";
			line4 += "   ";
			line5 += "---";
			line6 += "   ";
			line7 += "-O-";					
		}else if(text.equalsIgnoreCase("f#"))
		{
			line1 += "---";
			line2 += "   ";
			line3 += "---";
			line4 += "   ";
			line5 += "---";
			line6 += "   ";
			line7 += "-O#";					
		}else if(text.equalsIgnoreCase("g"))
		{
			line1 += "---";
			line2 += "   ";
			line3 += "---";
			line4 += "   ";
			line5 += "---";
			line6 += " O ";
			line7 += "---";					
		}else if(text.equalsIgnoreCase("g#"))
		{
			line1 += "---";
			line2 += "   ";
			line3 += "---";
			line4 += "   ";
			line5 += "---";
			line6 += " O#";
			line7 += "---";					
		}else if(text.equalsIgnoreCase("a"))
		{
			line1 += "---";
			line2 += "   ";
			line3 += "---";
			line4 += "   ";
			line5 += "-O-";
			line6 += "   ";
			line7 += "---";					
		}else if(text.equalsIgnoreCase("a#"))
		{
			line1 += "---";
			line2 += "   ";
			line3 += "---";
			line4 += "   ";
			line5 += "-O#";
			line6 += "   ";
			line7 += "---";					
		}else if(text.equalsIgnoreCase("b"))
		{
			line1 += "---";
			line2 += "   ";
			line3 += "---";
			line4 += " O ";
			line5 += "---";
			line6 += "   ";
			line7 += "---";					
		}
		
		score = line1 + line2 + line3 + line4 + line5 + line6 + line7;
		
		return score;
	}
	
	/**
	 * Converts the notes duration from a string to a double
	 * @param noteLength
	 * 	String of duration
	 * @return
	 * 	Returns the double value of the note duration
	 */
	public double noteLength(String noteLength)
	{
		double dubLength = .25;
		
		if(noteLength.equals("1"))
		{
			dubLength = 1;
		}else if(noteLength.equals("1/2"))
		{
			dubLength = .5;
		}else if(noteLength.equals("1/4"))
		{
			dubLength = .25;
		}else if(noteLength.equals("1/8"))
		{
			dubLength = .125;
		}else if(noteLength.equals("1/16"))
		{
			dubLength = .0625;
		}
		
		return dubLength;
	}
	
	/**
	 * Removes the most recent note from the String
	 * @return
	 * 	Returns new String without most recent note
	 */
	public String removeNote()
	{
		String score;
		line1 = line1.substring(0, line1.length() - 3);
		line2 = line2.substring(0, line2.length() - 3);
		line3 = line3.substring(0, line3.length() - 3);
		line4 = line4.substring(0, line4.length() - 3);
		line5 = line5.substring(0, line5.length() - 3);
		line6 = line6.substring(0, line6.length() - 3);
		line7 = line7.substring(0, line7.length() - 3);
		
		score = line1 + line2 + line3 + line4 + line5 + line6 + line7;
		
		return score;		
	}
	
	/**
	 * Redraws the scoreWindow after previous note has been removed,
	 * also removes the note from the note list and sets the counter accordingly
	 * @param score
	 * 	The new score after note is removed
	 */
	public void redrawRemove(final String score)
	{		
		SwingUtilities.invokeLater(
				new Runnable()
				{
					public void run()
					{
						scoreWindow.setText(drawScore(score));
					}
				}
			);
		
		noteCounter--;
		userNotes[noteCounter] = null;
	}
	
	/**
	 * Plays the song based on the notes in the note list called userNotes
	 */
	
	
	/**
	 * Gives a string value of the object
	 * @return
	 * 	Returns a list of each note in the userNotes list
	 */
	public String toString()
	{
		String toString = "";
		
		for(int i = 0; i < userNotes.length; i++)
		{
			if(userNotes[i] == null)
			{
				break;
			}else
			{
				if(i > 0)
				{
					toString += ", ";
				}
				
				toString += userNotes[i].toString();
			}			
		}
		
		return toString;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == btnAddNote)
		{
			String noteName = notesList[noteBox.getSelectedIndex()];
			double noteLength = noteLength(lengthList[lengthBox.getSelectedIndex()]);
				   
			try
			{
				userNotes[noteCounter] = new Note(noteLength, noteName);
				noteCounter++;
				showNotes(noteName);
			}catch(ArrayIndexOutOfBoundsException oob)
			{
				JOptionPane.showMessageDialog(null, "You can only have 28 notes.", "Error", JOptionPane.ERROR_MESSAGE);
			}		  
		}
		if(e.getSource() == btnRemoveNote)
		{
			try
			   {
				   redrawRemove(removeNote());
			   }catch(StringIndexOutOfBoundsException oob)
			   {
				   JOptionPane.showMessageDialog(null, "There aren't any notes to remove.", "Error", JOptionPane.ERROR_MESSAGE);
			   } catch(ArrayIndexOutOfBoundsException oob)
			   {
				   JOptionPane.showMessageDialog(null, "There aren't any notes to remove.", "Error", JOptionPane.ERROR_MESSAGE);
			   }
		}
		if(e.getSource() == btnPlaySong)
		{
			float bpm = 120;
			
			try
			{
				bpm = Float.parseFloat(txtBPM.getText());
				piano = new Thread(new PianoPlayer(bpm, userNotes));
			}catch (NumberFormatException nfe)
			{
				JOptionPane.showMessageDialog(null, "Please enter a proper BPM", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			
			//for(int i = 0; i < loopBox.getSelectedIndex() + 1; i++)
			{
				piano.start();
			}
		}
		if(e.getSource() == btnStopSong)
		{
			piano.interrupt();
		}
	}
}
