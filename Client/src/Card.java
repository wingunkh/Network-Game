import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class Card {
	private String cardSrc;
	private ImageIcon cardIcon;
	private JLabel card;

	private int x;
	private int y;
	private int width=75;
	private int height=125;
	
	public void setCardSrc(int srcNum) {
		this.cardSrc = "src/cards/" + srcNum + ".png";
	}
	public void setCardIcon(String src) {
		Image img = new ImageIcon(src).getImage();
		Image updateImage = img.getScaledInstance(75, 125, java.awt.Image.SCALE_SMOOTH);
		this.cardIcon = new ImageIcon(updateImage);
	}
	
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public String getCardSrc() {
		return this.cardSrc;
	}
	public ImageIcon getCardIcon() {
		return this.cardIcon;
	}
	public JLabel getCard() {
		return this.card;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	
	public void backside() { //카드의 뒷면이 보이게 화면에 보여주는 메서드
		this.cardSrc = "src/cards/Back.png";
		setCardIcon(cardSrc);
		this.card.setIcon(getCardIcon());
	}
	
	public void flip() { //카드의 앞면이 보이게 화면에 보여주는 메서드
		this.card.setIcon(getCardIcon());
	}
	
	public void setCardBounds() {
		this.card.setBounds(x, y, width, height);
	}
	
	public Card(int x, int y) { //생성자
		this.x = x;
		this.y = y;
		this.card = new JLabel();
	}
}