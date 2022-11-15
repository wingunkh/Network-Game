import javax.swing.ImageIcon;
import javax.swing.JLabel;

class Card {
	private String cardSrc;
	private ImageIcon cardIcon;
	private JLabel card;
	
	/*Setter 메서드*/
	public void setCardSrc(int srcNum) {
		this.cardSrc = "src/cards/" + srcNum + ".png";
	}
	public void setCardIcon(String src) {
		this.cardIcon = new ImageIcon(src);
	}
	public void setCard() {
		this.card.setIcon(this.cardIcon);
	}
	/*Setter 메서드*/
	
	/*Getter 메서드*/
	public String getCardSrc() {
		return this.cardSrc;
	}
	public JLabel getCard() {
		return this.card;
	}
	/*Getter 메서드*/
	
	public void backside() {
		this.cardSrc = "src/cards/Back.png";
		setCardIcon(cardSrc);
		setCard();
	}
	
	public Card() { //생성자
		this.card = new JLabel();
	}
}