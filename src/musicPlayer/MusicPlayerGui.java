package musicPlayer;

/**
 * creates and presents the user with a interface for the user to play audio to mix and match.
 * The Gui uses the Music objects to open clips and play the audio in a continues loop. the 
 * use can press the button again and stop the music or click a button in the same family. when 
 * the a button of the same family is press the audio will switch audio and the buttons will 
 * change color to indicate the button that is being played. the user can press the record button
 * that will then write int's to a file for the user to later press the play button for the 
 * program to then change the buttons to the save composition. the user can also change the audio that 
 * each button will play with the "HipHop and Funk" buttons. this will stop all music and change to the 
 * new audio for the user to then press the buttons to start the new audio.    
 * @author Garret WAsden and David Bowmen.
 */
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.sound.sampled.Clip;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class MusicPlayerGui extends JFrame {

	private JPanel contentPane;
	public static String path;
	static Clip[] clip = new Clip[17];
	static Music[] music = new Music[17];
	static boolean[] clickColor = new boolean[17];
	private static JPanel mainPanel;
	public static JLabel[] buttonName = new JLabel[17];
	private static boolean Change = false;
	private static long songTime = 24000000;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		Arrays.fill(clickColor, false);
		SampleFile.readFile();
		musicArrayAssignment();
		MusicTools.ButtonAction(music[0]);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MusicPlayerGui frame = new MusicPlayerGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static boolean[] getClickColor() {
		return clickColor;
	}

	public static void setClickColor(int i) {
		MusicPlayerGui.clickColor[i] = true;
	}

	public static Music[] getMusic() {
		return music;
	}

	/**
	 * Create the frame.
	 */
	public MusicPlayerGui() {
		setFont(new Font("Al Tarikh", Font.PLAIN, 15));
		setTitle("Music Box");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 650);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel NorthPanel = createNorthPanel();
		contentPane.add(NorthPanel, BorderLayout.NORTH);

		JPanel MainPanel = createMainPanel();
		contentPane.add(MainPanel, BorderLayout.CENTER);

		JPanel WestPanel = createWestPanel();
		contentPane.add(WestPanel, BorderLayout.WEST);

		JPanel EastPanel = new JPanel();
		EastPanel.setBackground(Color.GRAY);
		contentPane.add(EastPanel, BorderLayout.EAST);

		JPanel SouthPanel = new JPanel();
		SouthPanel.setBackground(Color.GRAY);
		contentPane.add(SouthPanel, BorderLayout.SOUTH);
	}

	/**
	 * Creates the WestPanel with the Genre buttons and the play and save buttons.
	 * 
	 * @return
	 */
	private JPanel createWestPanel() {
		JPanel WestPanel = new JPanel();
		WestPanel.setBorder(new EmptyBorder(0, 20, 0, 0));
		WestPanel.setBackground(Color.GRAY);
		WestPanel.setLayout(new GridLayout(10, 1, 0, 25));
		JLabel label = new JLabel("");
		WestPanel.add(label);
		createlblGenre1(WestPanel);
		createlblGenre2(WestPanel);
		// createlblGenre3(WestPanel);
		// createlblGenre4(WestPanel);
		JLabel label_1 = new JLabel("");
		WestPanel.add(label_1);
		createlblExport(WestPanel);
		createlblPlay(WestPanel);
		return WestPanel;
	}

	/**
	 * Creates the North Panel with the labels for the buttons.
	 * 
	 * @return
	 */
	private JPanel createNorthPanel() {
		JPanel NorthPanel = new JPanel();
		NorthPanel.setBackground(Color.GRAY);
		NorthPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		NorthPanel.setLayout(new GridLayout(0, 5, 0, 0));
		createSpacer_3(NorthPanel);
		createlblDrums(NorthPanel);
		createlblBass(NorthPanel);
		createlblChords(NorthPanel);
		createlblMelodies(NorthPanel);

		return NorthPanel;
	}

	/**
	 * Creates the main panel to house the buttons that play the audio. there are 16
	 * buttons. There one Music object that use as a pacer. the other 16 music
	 * object will open and close bepending on what button is selected. the button
	 * are use arrays to assign it functions. each button is paired with the same
	 * elements of each array (ex: music[1],clickColor[1],button[1])
	 * 
	 * @return
	 */
	private JPanel createMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.GRAY);
		mainPanel.setBorder(new EmptyBorder(3, 20, 10, 10));
		mainPanel.setLayout(new GridLayout(4, 4, 0, 0));

		createlblGreyDrum_1(mainPanel);
		createlblGreyBass_1(mainPanel);
		createlblGreyChords_1(mainPanel);
		createlblGreyMelody_1(mainPanel);
		createlblGreyDrum_2(mainPanel);
		createlblGreyBass_2(mainPanel);
		createlblGreyChords_2(mainPanel);
		createlblGreyMelody_2(mainPanel);
		createlblGreyDrum_3(mainPanel);
		createlblGreyBass_3(mainPanel);
		createlblGreyChords_3(mainPanel);
		createlblGreyMelody_3(mainPanel);
		createlblGreyDrum_4(mainPanel);
		createlblGreyBass_4(mainPanel);
		createlblGreyChords_4(mainPanel);
		createlblGreyMelody_4(mainPanel);
		return mainPanel;
	}

	/**
	 * Sync the audio of a newly press button with the pacer object(music[0]) so
	 * that all four audio stay in time
	 * 
	 * @param baseLine
	 * @param music
	 */
	public static void buttonHandle(Music baseLine, Music music) {

		MusicTools.ButtonAction(music);
		long i = baseLine.getClip().getMicrosecondPosition() % songTime;
		System.out.println(i);
		music.getClip().setMicrosecondPosition(i);

	}

	/**
	 * changes the color of the button with the methods from the class MusicTools
	 * and sets the color of the buttons for the user to knows what button is
	 * activated
	 * 
	 * @param i
	 * @param start
	 * @param end
	 */
	public static void buttonPress(int i, int start, int end) {
		for (int j = start; j <= end; j++) {
			if (i == j) {
				clickColor[i] = true;
			} else {
				if (clickColor[j] == true) {
					MusicTools.ButtonStop(music[j]);
					clickColor[j] = false;
					buttonName[j].setIcon(ButtonColors.GREY.getIcon());
				}
			}
		}
		mainPanel.revalidate();
	}

	/**
	 * Sent the musics object in a array with the same element of other arrays.
	 */
	private static void musicArrayAssignment() {
		for (int i = 0; i < 17; i++) {
			music[i] = new Music(clip[i], SampleFile.getAudioFile(i));
		}
	}

	/**
	 * read the file made from the fileWriter in the sampleFile class that is made
	 * after the user presses the save button. changes the color and audio output of
	 * the gui for the user to make a previous set up that was saved.
	 */
	public static void fileReader() {
		for (int i = 1; i < 17; i++) {
			if (clickColor[i] == true) {
				MusicTools.ButtonStop(music[i]);
			}
			clickColor[i] = false;
			System.out.println(clickColor[i] + "=" + i);
			buttonName[i].setIcon(ButtonColors.GREY.getIcon());
		}
		try (Scanner scan = new Scanner(new File("MusicSaved.txt"))) {
			String[] ar = scan.nextLine().split(",");
			for (int i = 0; i < ar.length; i++) {
				int one = Integer.parseInt(ar[i]);
				if (clickColor[one] == false) {
					buttonName[one].setIcon(ButtonColors.PLAY.getIcon());
					buttonHandle(music[0], music[one]);
					clickColor[one] = true;
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * creates a button that will start audio when pressed and change color. the
	 * button if the pressed again will revert the color to the grey and stop the
	 * music. the button will also "turn off" the other button in it's family so
	 * that only one button from a family will play at a time.
	 * 
	 * @param MainPanel
	 */
	private void createlblGreyMelody_4(JPanel MainPanel) {
		buttonName[16] = new JLabel("");
		buttonName[16].setBorder(new LineBorder(Color.GRAY, 2));
		buttonName[16].setIcon(ButtonColors.GREY.getIcon());
		buttonName[16].setHorizontalAlignment(SwingConstants.CENTER);
		MainPanel.add(buttonName[16]);
		buttonName[16].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (clickColor[16] == false) {
					buttonName[16].setIcon(ButtonColors.PURPLE.getIcon());
					buttonHandle(music[0], music[16]);
					buttonPress(16, 13, 16);
				} else {
					buttonName[16].setIcon(ButtonColors.GREY.getIcon());
					MusicTools.ButtonStop(music[16]);
					clickColor[16] = false;
				}

			}

		});
	}

	/**
	 * creates a button that will start audio when pressed and change color. the
	 * button if the pressed again will revert the color to the grey and stop the
	 * music. the button will also "turn off" the other button in it's family so
	 * that only one button from a family will play at a time.
	 * 
	 * @param MainPanel
	 */
	private void createlblGreyChords_4(JPanel MainPanel) {
		buttonName[12] = new JLabel("");
		buttonName[12].setBorder(new LineBorder(Color.GRAY, 2));
		buttonName[12].setIcon(ButtonColors.GREY.getIcon());
		buttonName[12].setHorizontalAlignment(SwingConstants.CENTER);
		MainPanel.add(buttonName[12]);
		buttonName[12].addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (clickColor[12] == false) {
					buttonName[12].setIcon(ButtonColors.YELLOW.getIcon());
					buttonHandle(music[0], music[12]);
					clickColor[12] = true;
					buttonPress(12, 9, 12);
				} else {
					buttonName[12].setIcon(ButtonColors.GREY.getIcon());
					MusicTools.ButtonStop(music[12]);
					clickColor[12] = false;
				}

			}
		});
	}

	/**
	 * creates a button that will start audio when pressed and change color. the
	 * button if the pressed again will revert the color to the grey and stop the
	 * music. the button will also "turn off" the other button in it's family so
	 * that only one button from a family will play at a time.
	 * 
	 * @param MainPanel
	 */
	private void createlblGreyBass_4(JPanel MainPanel) {
		buttonName[8] = new JLabel("");
		buttonName[8].setBorder(new LineBorder(Color.GRAY, 2));
		buttonName[8].setHorizontalAlignment(SwingConstants.CENTER);
		buttonName[8].setIcon(ButtonColors.GREY.getIcon());
		MainPanel.add(buttonName[8]);
		buttonName[8].addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (clickColor[8] == false) {
					buttonName[8].setIcon(ButtonColors.BLUE.getIcon());
					buttonHandle(music[0], music[8]);
					clickColor[8] = true;
					buttonPress(8, 5, 8);
				} else {
					buttonName[8].setIcon(ButtonColors.GREY.getIcon());
					MusicTools.ButtonStop(music[8]);
					clickColor[8] = false;
				}

			}
		});
	}

	/**
	 * creates a button that will start audio when pressed and change color. the
	 * button if the pressed again will revert the color to the grey and stop the
	 * music. the button will also "turn off" the other button in it's family so
	 * that only one button from a family will play at a time.
	 * 
	 * @param MainPanel
	 */
	private void createlblGreyDrum_4(JPanel MainPanel) {
		buttonName[4] = new JLabel("");
		buttonName[4].setBorder(new LineBorder(Color.GRAY, 2));
		buttonName[4].setIcon(ButtonColors.GREY.getIcon());
		buttonName[4].setHorizontalAlignment(SwingConstants.CENTER);
		MainPanel.add(buttonName[4]);
		buttonName[4].addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (clickColor[4] == false) {
					buttonName[4].setIcon(ButtonColors.GREEN.getIcon());
					buttonHandle(music[0], music[4]);
					clickColor[4] = true;
					buttonPress(4, 1, 4);
				} else {
					buttonName[4].setIcon(ButtonColors.GREY.getIcon());
					MusicTools.ButtonStop(music[4]);
					clickColor[4] = false;
				}

			}
		});
	}

	/**
	 * creates a button that will start audio when pressed and change color. the
	 * button if the pressed again will revert the color to the grey and stop the
	 * music. the button will also "turn off" the other button in it's family so
	 * that only one button from a family will play at a time.
	 * 
	 * @param MainPanel
	 */
	private void createlblGreyMelody_3(JPanel MainPanel) {
		buttonName[15] = new JLabel("");
		buttonName[15].setBorder(new LineBorder(Color.GRAY, 2));
		buttonName[15].setIcon(ButtonColors.GREY.getIcon());
		buttonName[15].setHorizontalAlignment(SwingConstants.CENTER);
		MainPanel.add(buttonName[15]);
		buttonName[15].addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (clickColor[15] == false) {
					buttonName[15].setIcon(ButtonColors.PURPLE.getIcon());
					buttonHandle(music[0], music[15]);
					clickColor[15] = true;
					buttonPress(15, 13, 16);
				} else {
					buttonName[15].setIcon(ButtonColors.GREY.getIcon());
					MusicTools.ButtonStop(music[15]);
					clickColor[15] = false;
				}

			}
		});
	}

	/**
	 * creates a button that will start audio when pressed and change color. the
	 * button if the pressed again will revert the color to the grey and stop the
	 * music. the button will also "turn off" the other button in it's family so
	 * that only one button from a family will play at a time.
	 * 
	 * @param MainPanel
	 */
	private void createlblGreyChords_3(JPanel MainPanel) {
		buttonName[11] = new JLabel("");
		buttonName[11].setBorder(new LineBorder(Color.GRAY, 2));
		buttonName[11].setIcon(ButtonColors.GREY.getIcon());
		buttonName[11].setHorizontalAlignment(SwingConstants.CENTER);
		MainPanel.add(buttonName[11]);
		buttonName[11].addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (clickColor[11] == false) {
					buttonName[11].setIcon(ButtonColors.YELLOW.getIcon());
					buttonHandle(music[0], music[11]);
					clickColor[11] = true;
					buttonPress(11, 9, 12);
				} else {
					buttonName[11].setIcon(ButtonColors.GREY.getIcon());
					MusicTools.ButtonStop(music[11]);
					clickColor[11] = false;
				}

			}
		});
	}

	/**
	 * creates a button that will start audio when pressed and change color. the
	 * button if the pressed again will revert the color to the grey and stop the
	 * music. the button will also "turn off" the other button in it's family so
	 * that only one button from a family will play at a time.
	 * 
	 * @param MainPanel
	 */
	private void createlblGreyBass_3(JPanel MainPanel) {
		buttonName[7] = new JLabel("");
		buttonName[7].setBorder(new LineBorder(Color.GRAY, 2));
		buttonName[7].setIcon(ButtonColors.GREY.getIcon());
		buttonName[7].setHorizontalAlignment(SwingConstants.CENTER);
		MainPanel.add(buttonName[7]);
		buttonName[7].addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (clickColor[7] == false) {
					buttonName[7].setIcon(ButtonColors.BLUE.getIcon());
					buttonHandle(music[0], music[7]);
					clickColor[7] = true;
					buttonPress(7, 5, 8);
				} else {
					buttonName[7].setIcon(ButtonColors.GREY.getIcon());
					MusicTools.ButtonStop(music[7]);
					clickColor[7] = false;
				}

			}
		});
	}

	/**
	 * creates a button that will start audio when pressed and change color. the
	 * button if the pressed again will revert the color to the grey and stop the
	 * music. the button will also "turn off" the other button in it's family so
	 * that only one button from a family will play at a time.
	 * 
	 * @param MainPanel
	 */
	private void createlblGreyDrum_3(JPanel MainPanel) {
		buttonName[3] = new JLabel("");
		buttonName[3].setBorder(new LineBorder(Color.GRAY, 2));
		buttonName[3].setIcon(ButtonColors.GREY.getIcon());
		buttonName[3].setHorizontalAlignment(SwingConstants.CENTER);
		MainPanel.add(buttonName[3]);
		buttonName[3].addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (clickColor[3] == false) {
					buttonName[3].setIcon(ButtonColors.GREEN.getIcon());
					buttonHandle(music[0], music[3]);
					clickColor[3] = true;
					buttonPress(3, 1, 4);
				} else {
					buttonName[3].setIcon(ButtonColors.GREY.getIcon());
					MusicTools.ButtonStop(music[3]);
					clickColor[3] = false;
				}

			}
		});
	}

	/**
	 * creates a button that will start audio when pressed and change color. the
	 * button if the pressed again will revert the color to the grey and stop the
	 * music. the button will also "turn off" the other button in it's family so
	 * that only one button from a family will play at a time.
	 * 
	 * @param MainPanel
	 */
	private void createlblGreyMelody_2(JPanel MainPanel) {
		buttonName[14] = new JLabel("");
		buttonName[14].setBorder(new LineBorder(Color.GRAY, 2));
		buttonName[14].setIcon(ButtonColors.GREY.getIcon());
		buttonName[14].setHorizontalAlignment(SwingConstants.CENTER);
		MainPanel.add(buttonName[14]);
		buttonName[14].addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (clickColor[14] == false) {
					buttonName[14].setIcon(ButtonColors.PURPLE.getIcon());
					buttonHandle(music[0], music[14]);
					clickColor[14] = true;
					buttonPress(14, 13, 16);
				} else {
					buttonName[14].setIcon(ButtonColors.GREY.getIcon());
					MusicTools.ButtonStop(music[14]);
					clickColor[14] = false;
				}

			}
		});
	}

	/**
	 * creates a button that will start audio when pressed and change color. the
	 * button if the pressed again will revert the color to the grey and stop the
	 * music. the button will also "turn off" the other button in it's family so
	 * that only one button from a family will play at a time.
	 * 
	 * @param MainPanel
	 */
	private void createlblGreyChords_2(JPanel MainPanel) {
		buttonName[10] = new JLabel("");
		buttonName[10].setBorder(new LineBorder(Color.GRAY, 2));
		buttonName[10].setIcon(ButtonColors.GREY.getIcon());
		buttonName[10].setHorizontalAlignment(SwingConstants.CENTER);
		MainPanel.add(buttonName[10]);
		buttonName[10].addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (clickColor[10] == false) {
					buttonName[10].setIcon(ButtonColors.YELLOW.getIcon());
					buttonHandle(music[0], music[10]);
					clickColor[10] = true;
					buttonPress(10, 9, 12);
				} else {
					buttonName[10].setIcon(ButtonColors.GREY.getIcon());
					MusicTools.ButtonStop(music[10]);
					clickColor[10] = false;
				}

			}
		});
	}

	/**
	 * creates a button that will start audio when pressed and change color. the
	 * button if the pressed again will revert the color to the grey and stop the
	 * music. the button will also "turn off" the other button in it's family so
	 * that only one button from a family will play at a time.
	 * 
	 * @param MainPanel
	 */
	private void createlblGreyBass_2(JPanel MainPanel) {
		buttonName[6] = new JLabel("");
		buttonName[6].setBorder(new LineBorder(Color.GRAY, 2));
		buttonName[6].setHorizontalAlignment(SwingConstants.CENTER);
		buttonName[6].setIcon(ButtonColors.GREY.getIcon());
		MainPanel.add(buttonName[6]);
		buttonName[6].addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (clickColor[6] == false) {
					buttonName[6].setIcon(ButtonColors.BLUE.getIcon());
					buttonHandle(music[0], music[6]);
					clickColor[6] = true;
					buttonPress(6, 5, 8);
				} else {
					buttonName[6].setIcon(ButtonColors.GREY.getIcon());
					MusicTools.ButtonStop(music[6]);
					clickColor[6] = false;
				}

			}
		});
	}

	/**
	 * creates a button that will start audio when pressed and change color. the
	 * button if the pressed again will revert the color to the grey and stop the
	 * music. the button will also "turn off" the other button in it's family so
	 * that only one button from a family will play at a time.
	 * 
	 * @param MainPanel
	 */
	private void createlblGreyDrum_2(JPanel MainPanel) {
		buttonName[2] = new JLabel("");
		buttonName[2].setBorder(new LineBorder(new Color(128, 128, 128), 2));
		buttonName[2].setHorizontalAlignment(SwingConstants.CENTER);
		buttonName[2].setIcon(ButtonColors.GREY.getIcon());
		MainPanel.add(buttonName[2]);
		buttonName[2].addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (clickColor[2] == false) {
					buttonName[2].setIcon(ButtonColors.GREEN.getIcon());
					buttonHandle(music[0], music[2]);
					clickColor[2] = true;
					buttonPress(2, 1, 4);
				} else {
					buttonName[2].setIcon(ButtonColors.GREY.getIcon());
					MusicTools.ButtonStop(music[2]);
					clickColor[2] = false;
				}

			}
		});
	}

	/**
	 * creates a button that will start audio when pressed and change color. the
	 * button if the pressed again will revert the color to the grey and stop the
	 * music. the button will also "turn off" the other button in it's family so
	 * that only one button from a family will play at a time.
	 * 
	 * @param MainPanel
	 */
	private void createlblGreyMelody_1(JPanel MainPanel) {
		buttonName[13] = new JLabel("");
		buttonName[13].setBorder(new LineBorder(Color.GRAY, 2));
		buttonName[13].setIcon(ButtonColors.GREY.getIcon());
		buttonName[13].setHorizontalAlignment(SwingConstants.CENTER);
		MainPanel.add(buttonName[13]);
		buttonName[13].addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (clickColor[13] == false) {
					buttonName[13].setIcon(ButtonColors.PURPLE.getIcon());
					buttonHandle(music[0], music[13]);
					clickColor[13] = true;
					buttonPress(13, 13, 16);
				} else {
					buttonName[13].setIcon(ButtonColors.GREY.getIcon());
					MusicTools.ButtonStop(music[13]);
					clickColor[13] = false;
				}

			}
		});
	}

	/**
	 * creates a button that will start audio when pressed and change color. the
	 * button if the pressed again will revert the color to the grey and stop the
	 * music. the button will also "turn off" the other button in it's family so
	 * that only one button from a family will play at a time.
	 * 
	 * @param MainPanel
	 */
	private void createlblGreyChords_1(JPanel MainPanel) {
		buttonName[9] = new JLabel("");
		buttonName[9].setBorder(new LineBorder(Color.GRAY, 2));
		buttonName[9].setHorizontalAlignment(SwingConstants.CENTER);
		buttonName[9].setIcon(ButtonColors.GREY.getIcon());
		MainPanel.add(buttonName[9]);
		buttonName[9].addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (clickColor[9] == false) {
					buttonName[9].setIcon(ButtonColors.YELLOW.getIcon());
					buttonHandle(music[0], music[9]);
					clickColor[9] = true;
					buttonPress(9, 9, 12);
				} else {
					buttonName[9].setIcon(ButtonColors.GREY.getIcon());
					MusicTools.ButtonStop(music[9]);
					clickColor[9] = false;
				}

			}
		});
	}

	/**
	 * creates a button that will start audio when pressed and change color. the
	 * button if the pressed again will revert the color to the grey and stop the
	 * music. the button will also "turn off" the other button in it's family so
	 * that only one button from a family will play at a time.
	 * 
	 * @param MainPanel
	 */
	private void createlblGreyBass_1(JPanel MainPanel) {
		buttonName[5] = new JLabel("");
		buttonName[5].setBorder(new LineBorder(Color.GRAY, 2));
		buttonName[5].setHorizontalAlignment(SwingConstants.CENTER);
		buttonName[5].setIcon(ButtonColors.GREY.getIcon());
		MainPanel.add(buttonName[5]);
		buttonName[5].addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (clickColor[5] == false) {
					buttonName[5].setIcon(ButtonColors.BLUE.getIcon());
					buttonHandle(music[0], music[5]);
					clickColor[5] = true;
					buttonPress(5, 5, 8);
				} else {
					buttonName[5].setIcon(ButtonColors.GREY.getIcon());
					MusicTools.ButtonStop(music[5]);
					clickColor[5] = false;
				}

			}
		});
	}

	/**
	 * creates a button that will start audio when pressed and change color. the
	 * button if the pressed again will revert the color to the grey and stop the
	 * music. the button will also "turn off" the other button in it's family so
	 * that only one button from a family will play at a time.
	 * 
	 * @param MainPanel
	 */
	private void createlblGreyDrum_1(JPanel MainPanel) {
		buttonName[1] = new JLabel("");
		buttonName[1].setIcon(ButtonColors.GREY.getIcon());
		buttonName[1].setBorder(new LineBorder(new Color(128, 128, 128), 2));
		buttonName[1].setHorizontalAlignment(SwingConstants.CENTER);
		MainPanel.add(buttonName[1]);
		buttonName[1].addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (clickColor[1] == false) {
					buttonName[1].setIcon(ButtonColors.GREEN.getIcon());
					buttonHandle(music[0], music[1]);
					;
					clickColor[1] = true;
					buttonPress(1, 1, 4);
				} else {
					buttonName[1].setIcon(ButtonColors.GREY.getIcon());
					MusicTools.ButtonStop(music[1]);
					clickColor[1] = false;
				}

			}
		});
	}

	/**
	 * creates the north labels for the user to know what columns of buttons that
	 * the user to interact with
	 * 
	 * @param NorthPanel
	 */
	private void createlblMelodies(JPanel NorthPanel) {
		JLabel lblMelodies = new JLabel("      MELODIES");
		lblMelodies.setForeground(Color.WHITE);
		lblMelodies.setOpaque(true);
		lblMelodies.setBackground(Color.GRAY);
		NorthPanel.add(lblMelodies);
	}

	/**
	 * creates the north labels for the user to know what columns of buttons that
	 * the user to interact with
	 * 
	 * @param NorthPanel
	 */
	private void createlblChords(JPanel NorthPanel) {
		JLabel lblChords = new JLabel("       CHORDS");
		lblChords.setForeground(Color.WHITE);
		lblChords.setOpaque(true);
		lblChords.setBackground(Color.GRAY);
		NorthPanel.add(lblChords);
	}

	/**
	 * creates the north labels for the user to know what columns of buttons that
	 * the user to interact with
	 * 
	 * @param NorthPanel
	 */
	private void createlblBass(JPanel NorthPanel) {
		JLabel lblBass = new JLabel("         BASS");
		lblBass.setForeground(Color.WHITE);
		lblBass.setOpaque(true);
		lblBass.setBackground(Color.GRAY);
		NorthPanel.add(lblBass);
	}

	/**
	 * creates the north labels for the user to know what columns of buttons that
	 * the user to interact with
	 * 
	 * @param NorthPanel
	 */
	private void createlblDrums(JPanel NorthPanel) {
		JLabel lblDrums = new JLabel("        DRUMS");
		lblDrums.setForeground(Color.WHITE);
		lblDrums.setOpaque(true);
		lblDrums.setBackground(Color.GRAY);
		NorthPanel.add(lblDrums);
	}

	/**
	 * creates the north labels for the user to know what columns of buttons that
	 * the user to interact with
	 * 
	 * @param NorthPanel
	 */
	private void createSpacer_3(JPanel NorthPanel) {
		JLabel spacer_3 = new JLabel("");
		spacer_3.setBackground(Color.GRAY);
		spacer_3.setOpaque(true);
		NorthPanel.add(spacer_3);
	}

	/**
	 * create the save button for the user to user to save the mix that they have
	 * selected
	 * 
	 * @param WestPanel
	 */
	private void createlblExport(JPanel WestPanel) {
		JLabel lblExport = new JLabel("Save");
		lblExport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				SampleFile.fileWriter();
			}
		});

		lblExport.setBorder(new LineBorder(Color.DARK_GRAY));
		lblExport.setForeground(Color.DARK_GRAY);
		lblExport.setOpaque(true);
		lblExport.setBackground(new Color(255, 99, 71));
		lblExport.setHorizontalAlignment(SwingConstants.CENTER);
		WestPanel.add(lblExport);
	}

	/**
	 * creates a filler for the panel for user friendly layout.
	 * 
	 * @param WestPanel
	 */
	private void createlblPlay(JPanel WestPanel) {
		JLabel lblPlay = new JLabel("Play");
		lblPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				fileReader();
			}
		});
		lblPlay.setBorder(new LineBorder(Color.DARK_GRAY));
		lblPlay.setForeground(Color.DARK_GRAY);
		lblPlay.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlay.setOpaque(true);
		lblPlay.setBackground(new Color(60, 179, 113));
		WestPanel.add(lblPlay);
	}

	/**
	 * creates the HipHop button that will change the music of the button when
	 * pressed.
	 * 
	 * @param WestPanel
	 */
	private void createlblGenre1(JPanel WestPanel) {
		JLabel lblGenre1 = new JLabel("HipHop");
		lblGenre1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SampleFile.setFileNumber(1);
				for (int i = 0; i < 17; i++) {
					if (clickColor[i] == true) {
						MusicTools.ButtonStop(music[i]);
						clickColor[i] = false;
						buttonName[i].setIcon(ButtonColors.GREY.getIcon());
						SampleFile.readFile();
						music[i].setFile(SampleFile.getAudioFile(i));
					}
					music[i].setFile(SampleFile.getAudioFile(i));
					songTime = 24000000;
				}

			}
		});
		lblGenre1.setForeground(Color.DARK_GRAY);
		lblGenre1.setOpaque(true);
		lblGenre1.setHorizontalAlignment(SwingConstants.CENTER);
		lblGenre1.setBorder(new EmptyBorder(0, 22, 0, 22));
		lblGenre1.setBackground(Color.LIGHT_GRAY);
		WestPanel.add(lblGenre1);
	}

	/**
	 * creates the Funk button that will change the music of the button when
	 * pressed.
	 * 
	 * @param WestPanel
	 */
	private void createlblGenre2(JPanel WestPanel) {
		JLabel lblGenre2 = new JLabel("Funk");
		lblGenre2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SampleFile.setFileNumber(2);
				for (int i = 0; i < 17; i++) {
					if (clickColor[i] == true) {
						MusicTools.ButtonStop(music[i]);
						clickColor[i] = false;
						buttonName[i].setIcon(ButtonColors.GREY.getIcon());
						SampleFile.readFile();
					}
					music[i].setFile(SampleFile.getAudioFile(i));
					songTime = 32268890;
				}
			}
		});
		lblGenre2.setForeground(Color.DARK_GRAY);
		lblGenre2.setOpaque(true);
		lblGenre2.setHorizontalAlignment(SwingConstants.CENTER);
		lblGenre2.setBorder(new EmptyBorder(0, 22, 0, 22));
		lblGenre2.setBackground(Color.LIGHT_GRAY);
		WestPanel.add(lblGenre2);
	}

}
