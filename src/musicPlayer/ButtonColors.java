package musicPlayer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * sets the local for the images for the Gui to use for the button.
 * 
 * @author David Bowen
 *
 */
public enum ButtonColors {
	GREY(new ImageIcon(("/Volumes/CLASS/MyItems/src/musicPlayer/Images/greyPlayButton.png"))), GREEN(
			new ImageIcon(("/Volumes/CLASS//MyItems/src/musicPlayer/Images/greenStopButton.png"))), BLUE(
					new ImageIcon(("/Volumes/CLASS//MyItems/src/musicPlayer/Images/blueStopButton.png"))), YELLOW(
							new ImageIcon(("/Volumes/CLASS//MyItems/src/musicPlayer/Images/yellowStopButton.png"))), PURPLE(
									new ImageIcon(("/Volumes/CLASS//MyItems/src/musicPlayer/Images/purpleStopButton.png"))), PLAY(
											new ImageIcon(("/Volumes/CLASS//MyItems/src/musicPlayer/Images/greenPlay.png")));

	private Icon icon;

	private ButtonColors(Icon icon) {
		this.icon = icon;
	}

	public Icon getIcon() {
		return icon;
	}

}
