import javax.swing.ImageIcon;
import javax.swing.JLabel;

class Card {
	private int cardNum;
	private String cardSrc;
	private String backSrc = "src/cards/Back.png";
	private ImageIcon cardIcon;
	private JLabel card;
	
	/*Setter 메서드*/
	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}
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
	public int getCardNum() {
		return this.cardNum;
	}
	public String getCardSrc() {
		return this.cardSrc;
	}
	public String getBackSrc() {
		return this.backSrc;
	}
	public JLabel getCard() { //
		return this.card;
	}
	/*Getter 메서드*/
	
	public Card(int num) { //생성자
		this.card = new JLabel(Integer.toString(cardNum));
		setCardNum(num);
		setCardIcon(backSrc);
		setCard();
	}
}