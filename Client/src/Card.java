import javax.swing.ImageIcon;
import javax.swing.JLabel;

class Card {
	private int cardNum;
	private String cardSrc;
	private String backSrc = "src/cards/Back.png";
	private JLabel card;
	private ImageIcon cardIcon;
	
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
	public int getCardNum() {
		return this.cardNum;
	}
	
	public String getCardSrc() {
		return this.cardSrc;
	}
	public String getBackSrc() {
		return this.backSrc;
	}
	public JLabel getCard() {
		return this.card;
	}
	
	public Card(int num) {
		this.card = new JLabel(Integer.toString(cardNum));
		setCardNum(num);
		setCardIcon(backSrc);
		setCard();
	}
	
	/* JavaObjectClientView ¿¹½Ã
	 * Card[] myCards = new Card[3];
	 * for (int i = 0; i < 3; i++) {
	 * myCards[i] = new Card(i);
	 * }
	 * =>
	 * myCards[0] => cardNum = 0, cardSrc = "";
	 */
}
