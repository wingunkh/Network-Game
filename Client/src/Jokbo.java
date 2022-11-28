public class Jokbo {
	private String card1;
	private String card2;
	private int level;
	private int sum;
	
	public Jokbo(Card card1, Card card2) {
		this.card1 = card1.getCardSrc().substring(10,12);
		this.card2 = card2.getCardSrc().substring(10,12);
	}
	
	//00 : 1 02 : 2 04 : 3 06 : 4 08 : 5 10 : 6 12 : 7 14 : 8 16 : 9 18 : 10
	//00 : 1±¤ 04 : 3±¤ 14 : 8±¤
	//00(1), 02(2), 06(4), 12(7), 15(8), 16(9), 18(10) -> ¿­²ý ÆÐ
	public String calculateJokbo() {
		String x = "";
		switch(card1) {
		case "00" : // 1±¤
			switch(card2) {
			case "01": x = "»æ¶¯"; // 1¿ù
			break;
			case "02": x = "¾Ë¸®"; // 2¿ù
			break;
			case "03": x = "¾Ë¸®"; // 2¿ù
			break;
			case "04": x = "ÀÏ»ï±¤¶¯"; // 3±¤
			break;
			case "05": x = "³×²ý"; // 3¿ù
			break;
			case "06": x = "µ¶»ç"; // 4¿ù
			break;
			case "07": x = "µ¶»ç"; // 4¿ù
			break;
			case "08": x = "¿©¼¸²ý"; // 5¿ù
			break;
			case "09": x = "¿©¼¸²ý"; // 5¿ù
			break;
			case "10": x = "ÀÏ°ö²ý"; // 6¿ù
			break;
			case "11": x = "ÀÏ°ö²ý"; // 6¿ù
			break;
			case "12": x = "¿©´ü²ý"; // 7¿ù
			break;
			case "13": x = "¿©´ü²ý"; // 7¿ù
			break;
			case "14": x = "ÀÏÆÈ±¤¶¯"; // 8±¤
			break;
			case "15": x = "°©¿À"; // 8¿ù
			break;
			case "16": x = "±¸»æ"; // 9¿ù
			break;
			case "17": x = "±¸»æ"; // 9¿ù
			break;
			case "18": x = "Àå»æ"; // 10¿ù
			break;
			case "19": x = "Àå»æ"; // 10¿ù
			break;
			}
		break;
		case "01" : // 1¿ù
			switch(card2) {
			case "00": x = "»æ¶¯"; // 1±¤
			break;
			case "02": x = "¾Ë¸®"; // 2¿ù
			break;
			case "03": x = "¾Ë¸®"; // 2¿ù
			break;
			case "04": x = "³×²ý"; // 3±¤
			break;
			case "05": x = "³×²ý"; // 3¿ù
			break;
			case "06": x = "µ¶»ç"; // 4¿ù
			break;
			case "07": x = "µ¶»ç"; // 4¿ù
			break;
			case "08": x = "¿©¼¸²ý"; // 5¿ù
			break;
			case "09": x = "¿©¼¸²ý"; // 5¿ù
			break;
			case "10": x = "ÀÏ°ö²ý"; // 6¿ù
			break;
			case "11": x = "ÀÏ°ö²ý"; // 6¿ù
			break;
			case "12": x = "¿©´ü²ý"; // 7¿ù
			break;
			case "13": x = "¿©´ü²ý"; // 7¿ù
			break;
			case "14": x = "°©¿À"; // 8±¤
			break;
			case "15": x = "°©¿À"; // 8¿ù
			break;
			case "16": x = "±¸»æ"; // 9¿ù
			break;
			case "17": x = "±¸»æ"; // 9¿ù
			break;
			case "18": x = "Àå»æ"; // 10¿ù
			break;
			case "19": x = "Àå»æ"; // 10¿ù
			break;
			}
		break;
		case "02" : // 2¿ù
			switch(card2) {
			case "00": x = "¾Ë¸®"; // 1±¤
			break;
			case "01": x = "¾Ë¸®"; // 1¿ù
			break;
			case "03": x = "ÀÌ¶¯"; // 2¿ù
			break;
			case "04": x = "´Ù¼¸²ý"; // 3±¤
			break;
			case "05": x = "´Ù¼¸²ý"; // 3¿ù
			break;
			case "06": x = "¿©¼¸²ý"; // 4¿ù
			break;
			case "07": x = "¿©¼¸²ý"; // 4¿ù
			break;
			case "08": x = "ÀÏ°ö²ý"; // 5¿ù
			break;
			case "09": x = "ÀÏ°ö²ý"; // 5¿ù
			break;
			case "10": x = "¿©´ü²ý"; // 6¿ù
			break;
			case "11": x = "¿©´ü²ý"; // 6¿ù
			break;
			case "12": x = "°©¿À"; // 7¿ù
			break;
			case "13": x = "°©¿À"; // 7¿ù
			break;
			case "14": x = "¸ÁÅë"; // 8±¤
			break;
			case "15": x = "¸ÁÅë"; // 8¿ù
			break;
			case "16": x = "ÇÑ²ý"; // 9¿ù
			break;
			case "17": x = "ÇÑ²ý"; // 9¿ù
			break;
			case "18": x = "µÎ²ý"; // 10¿ù
			break;
			case "19": x = "µÎ²ý"; // 10¿ù
			break;
			}
		break;
		case "03" : // 2¿ù
			switch(card2) {
			case "00": x = "¾Ë¸®"; // 1±¤
			break;
			case "01": x = "¾Ë¸®"; // 1¿ù
			break;
			case "02": x = "ÀÌ¶¯"; // 2¿ù
			break;
			case "04": x = "´Ù¼¸²ý"; // 3±¤
			break;
			case "05": x = "´Ù¼¸²ý"; // 3¿ù
			break;
			case "06": x = "¿©¼¸²ý"; // 4¿ù
			break;
			case "07": x = "¿©¼¸²ý"; // 4¿ù
			break;
			case "08": x = "ÀÏ°ö²ý"; // 5¿ù
			break;
			case "09": x = "ÀÏ°ö²ý"; // 5¿ù
			break;
			case "10": x = "¿©´ü²ý"; // 6¿ù
			break;
			case "11": x = "¿©´ü²ý"; // 6¿ù
			break;
			case "12": x = "°©¿À"; // 7¿ù
			break;
			case "13": x = "°©¿À"; // 7¿ù
			break;
			case "14": x = "¸ÁÅë"; // 8±¤
			break;
			case "15": x = "¸ÁÅë"; // 8¿ù
			break;
			case "16": x = "ÇÑ²ý"; // 9¿ù
			break;
			case "17": x = "ÇÑ²ý"; // 9¿ù
			break;
			case "18": x = "µÎ²ý"; // 10¿ù
			break;
			case "19": x = "µÎ²ý"; // 10¿ù
			break;
			}
		break;
		case "04" : // 3±¤
			switch(card2) {
			case "00": x = "ÀÏ»ï±¤¶¯"; // 1±¤
			break;
			case "01": x = "³×²ý"; // 1¿ù
			break;
			case "02": x = "´Ù¼¸²ý"; // 2¿ù
			break;
			case "03": x = "´Ù¼¸²ý"; // 2¿ù
			break;
			case "05": x = "»ï¶¯"; // 3¿ù
			break;
			case "06": x = "ÀÏ°ö²ý"; // 4¿ù
			break;
			case "07": x = "ÀÏ°ö²ý"; // 4¿ù
			break;
			case "08": x = "¿©´ü²ý"; // 5¿ù
			break;
			case "09": x = "¿©´ü²ý"; // 5¿ù
			break;
			case "10": x = "°©¿À"; // 6¿ù
			break;
			case "11": x = "°©¿À"; // 6¿ù
			break;
			case "12": x = "¶¯ÀâÀÌ"; // 7¿ù
			break;
			case "13": x = "¸ÁÅë"; // 7¿ù
			break;
			case "14": x = "ÇÑ²ý"; // 8±¤
			break;
			case "15": x = "ÇÑ²ý"; // 8¿ù
			break;
			case "16": x = "µÎ²ý"; // 9¿ù
			break;
			case "17": x = "µÎ²ý"; // 9¿ù
			break;
			case "18": x = "¼¼²ý"; // 10¿ù
			break;
			case "19": x = "¼¼²ý"; // 10¿ù
			break;
			}
		break;
		case "05" : // 3¿ù
			switch(card2) {
			case "00": x = "³×²ý"; // 1±¤
			break;
			case "01": x = "³×²ý"; // 1¿ù
			break;
			case "02": x = "´Ù¼¸²ý"; // 2¿ù
			break;
			case "03": x = "´Ù¼¸²ý"; // 2¿ù
			break;
			case "04": x = "»ï¶¯"; // 3±¤
			break;
			case "06": x = "ÀÏ°ö²ý"; // 4¿ù
			break;
			case "07": x = "ÀÏ°ö²ý"; // 4¿ù
			break;
			case "08": x = "¿©´ü²ý"; // 5¿ù
			break;
			case "09": x = "¿©´ü²ý"; // 5¿ù
			break;
			case "10": x = "°©¿À"; // 6¿ù
			break;
			case "11": x = "°©¿À"; // 6¿ù
			break;
			case "12": x = "¸ÁÅë"; // 7¿ù
			break;
			case "13": x = "¸ÁÅë"; // 7¿ù
			break;
			case "14": x = "ÇÑ²ý"; // 8±¤
			break;
			case "15": x = "ÇÑ²ý"; // 8¿ù
			break;
			case "16": x = "µÎ²ý"; // 9¿ù
			break;
			case "17": x = "µÎ²ý"; // 9¿ù
			break;
			case "18": x = "¼¼²ý"; // 10¿ù
			break;
			case "19": x = "¼¼²ý"; // 10¿ù
			break;
			}
		break;
		case "06" : // 4¿ù
			switch(card2) {
			case "00": x = "µ¶»ç"; // 1±¤
			break;
			case "01": x = "µ¶»ç"; // 1¿ù
			break;
			case "02": x = "¿©¼¸²ý"; // 2¿ù
			break;
			case "03": x = "¿©¼¸²ý"; // 2¿ù
			break;
			case "04": x = "ÀÏ°ö²ý"; // 3±¤
			break;
			case "05": x = "ÀÏ°ö²ý"; // 3¿ù
			break;
			case "07": x = "»ç¶¯"; // 4¿ù
			break;
			case "08": x = "°©¿À"; // 5¿ù
			break;
			case "09": x = "°©¿À"; // 5¿ù
			break;
			case "10": x = "¼¼·ú"; // 6¿ù
			break;
			case "11": x = "¼¼·ú"; // 6¿ù
			break;
			case "12": x = "¾ÏÇà¾î»ç"; // 7¿ù
			break;
			case "13": x = "ÇÑ²ý"; // 7¿ù
			break;
			case "14": x = "µÎ²ý"; // 8±¤
			break;
			case "15": x = "µÎ²ý"; // 8¿ù
			break;
			case "16": x = "¸ÛÅÖ±¸¸®±¸»ç"; // 9¿ù
			break;
			case "17": x = "±¸»ç"; // 9¿ù
			break;
			case "18": x = "Àå»ç"; // 10¿ù
			break;
			case "19": x = "Àå»ç"; // 10¿ù
			break;
			}
		break;
		case "07" : // 4¿ù
			switch(card2) {
			case "00": x = "µ¶»ç"; // 1±¤
			break;
			case "01": x = "µ¶»ç"; // 1¿ù
			break;
			case "02": x = "¿©¼¸²ý"; // 2¿ù
			break;
			case "03": x = "¿©¼¸²ý"; // 2¿ù
			break;
			case "04": x = "ÀÏ°ö²ý"; // 3±¤
			break;
			case "05": x = "ÀÏ°ö²ý"; // 3¿ù
			break;
			case "06": x = "»ç¶¯"; // 4¿ù
			break;
			case "08": x = "°©¿À"; // 5¿ù
			break;
			case "09": x = "°©¿À"; // 5¿ù
			break;
			case "10": x = "¼¼·ú"; // 6¿ù
			break;
			case "11": x = "¼¼·ú"; // 6¿ù
			break;
			case "12": x = "ÇÑ²ý"; // 7¿ù
			break;
			case "13": x = "ÇÑ²ý"; // 7¿ù
			break;
			case "14": x = "µÎ²ý"; // 8±¤
			break;
			case "15": x = "µÎ²ý"; // 8¿ù
			break;
			case "16": x = "±¸»ç"; // 9¿ù
			break;
			case "17": x = "±¸»ç"; // 9¿ù
			break;
			case "18": x = "Àå»ç"; // 10¿ù
			break;
			case "19": x = "Àå»ç"; // 10¿ù
			break;
			}
		break;
		case "08" : // 5¿ù
			switch(card2) {
			case "00": x = "¿©¼¸²ý"; // 1±¤
			break;
			case "01": x = "¿©¼¸²ý"; // 1¿ù
			break;
			case "02": x = "ÀÏ°ö²ý"; // 2¿ù
			break;
			case "03": x = "ÀÏ°ö²ý"; // 2¿ù
			break;
			case "04": x = "¿©´ü²ý"; // 3±¤
			break;
			case "05": x = "¿©´ü²ý"; // 3¿ù
			break;
			case "06": x = "°©¿À"; // 4¿ù
			break;
			case "07": x = "°©¿À"; // 4¿ù
			break;
			case "09": x = "¿À¶¯"; // 5¿ù
			break;
			case "10": x = "ÇÑ²à"; // 6¿ù
			break;
			case "11": x = "ÇÑ²ý"; // 6¿ù
			break;
			case "12": x = "µÎ²ý"; // 7¿ù
			break;
			case "13": x = "µÎ²ý"; // 7¿ù
			break;
			case "14": x = "¼¼²ý"; // 8±¤
			break;
			case "15": x = "¼¼²ý"; // 8¿ù
			break;
			case "16": x = "³×²ý"; // 9¿ù
			break;
			case "17": x = "³×²ý"; // 9¿ù
			break;
			case "18": x = "´Ù¼¸²ý"; // 10¿ù
			break;
			case "19": x = "´Ù¼¸²ý"; // 10¿ù
			break;
			}
		break;
		case "09" : // 5¿ù
			switch(card2) {
			case "00": x = "¿©¼¸²ý"; // 1±¤
			break;
			case "01": x = "¿©¼¸²ý"; // 1¿ù
			break;
			case "02": x = "ÀÏ°ö²ý"; // 2¿ù
			break;
			case "03": x = "ÀÏ°ö²ý"; // 2¿ù
			break;
			case "04": x = "¿©´ü²ý"; // 3±¤
			break;
			case "05": x = "¿©´ü²ý"; // 3¿ù
			break;
			case "06": x = "°©¿À"; // 4¿ù
			break;
			case "07": x = "°©¿À"; // 4¿ù
			break;
			case "08": x = "¿À¶¯"; // 5¿ù
			break;
			case "10": x = "ÇÑ²à"; // 6¿ù
			break;
			case "11": x = "ÇÑ²ý"; // 6¿ù
			break;
			case "12": x = "µÎ²ý"; // 7¿ù
			break;
			case "13": x = "µÎ²ý"; // 7¿ù
			break;
			case "14": x = "¼¼²ý"; // 8±¤
			break;
			case "15": x = "¼¼²ý"; // 8¿ù
			break;
			case "16": x = "³×²ý"; // 9¿ù
			break;
			case "17": x = "³×²ý"; // 9¿ù
			break;
			case "18": x = "´Ù¼¸²ý"; // 10¿ù
			break;
			case "19": x = "´Ù¼¸²ý"; // 10¿ù
			break;
			}
		break;
		case "10" : // 6¿ù
			switch(card2) {
			case "00": x = "ÀÏ°ö²ý"; // 1±¤
			break;
			case "01": x = "ÀÏ°ö²ý"; // 1¿ù
			break;
			case "02": x = "¿©´ü²ý"; // 2¿ù
			break;
			case "03": x = "¿©´ü²ý"; // 2¿ù
			break;
			case "04": x = "°©¿À"; // 3±¤
			break;
			case "05": x = "°©¿À"; // 3¿ù
			break;
			case "06": x = "¼¼·ú"; // 4¿ù
			break;
			case "07": x = "¼¼·ú"; // 4¿ù
			break;
			case "08": x = "ÇÑ²ý"; // 5¿ù	
			break;
			case "09": x = "ÇÑ²ý"; // 5¿ù
			break;
			case "11": x = "À°¶¯"; // 6¿ù
			break;
			case "12": x = "¼¼²ý"; // 7¿ù
			break;
			case "13": x = "¼¼²ý"; // 7¿ù
			break;
			case "14": x = "³×²ý"; // 8±¤
			break;
			case "15": x = "³×²ý"; // 8¿ù
			break;
			case "16": x = "´Ù¼¸²ý"; // 9¿ù
			break;
			case "17": x = "´Ù¼¸²ý"; // 9¿ù
			break;
			case "18": x = "¿©¼¸²ý"; // 10¿ù
			break;
			case "19": x = "¿©¼¸²ý"; // 10¿ù
			break;
			}
		break;
		case "11" : // 6¿ù
			switch(card2) {
			case "00": x = "ÀÏ°ö²ý"; // 1±¤
			break;
			case "01": x = "ÀÏ°ö²ý"; // 1¿ù
			break;
			case "02": x = "¿©´ü²ý"; // 2¿ù
			break;
			case "03": x = "¿©´ü²ý"; // 2¿ù
			break;
			case "04": x = "°©¿À"; // 3±¤
			break;
			case "05": x = "°©¿À"; // 3¿ù
			break;
			case "06": x = "¼¼·ú"; // 4¿ù
			break;
			case "07": x = "¼¼·ú"; // 4¿ù
			break;
			case "08": x = "ÇÑ²ý"; // 5¿ù	
			break;
			case "09": x = "ÇÑ²ý"; // 5¿ù
			break;
			case "10": x = "À°¶¯"; // 6¿ù
			break;
			case "12": x = "¼¼²ý"; // 7¿ù
			break;
			case "13": x = "¼¼²ý"; // 7¿ù
			break;
			case "14": x = "³×²ý"; // 8±¤
			break;
			case "15": x = "³×²ý"; // 8¿ù
			break;
			case "16": x = "´Ù¼¸²ý"; // 9¿ù
			break;
			case "17": x = "´Ù¼¸²ý"; // 9¿ù
			break;
			case "18": x = "¿©¼¸²ý"; // 10¿ù
			break;
			case "19": x = "¿©¼¸²ý"; // 10¿ù
			break;
			}
		break;
		case "12" : // 7¿ù
			switch(card2) {
			case "00": x = "¿©´ü²ý"; // 1±¤
			break;
			case "01": x = "¿©´ü²ý"; // 1¿ù
			break;
			case "02": x = "°©¿À"; // 2¿ù
			break;
			case "03": x = "°©¿À"; // 2¿ù
			break;
			case "04": x = "¶¯ÀâÀÌ"; // 3±¤
			break;
			case "05": x = "¸ÁÅë"; // 3¿ù
			break;
			case "06": x = "¾ÏÇà¾î»ç"; // 4¿ù
			break;
			case "07": x = "ÇÑ²ý"; // 4¿ù
			break;
			case "08": x = "µÎ²ý"; // 5¿ù	
			break;
			case "09": x = "µÎ²ý"; // 5¿ù
			break;
			case "10": x = "¼¼²ý"; // 6¿ù
			break;
			case "11": x = "¼¼²ý"; // 6¿ù
			break;
			case "13": x = "Ä¥¶¯"; // 7¿ù
			break;
			case "14": x = "´Ù¼¸²ý"; // 8±¤
			break;
			case "15": x = "´Ù¼¸²ý"; // 8¿ù
			break;
			case "16": x = "¿©¼¸²ý"; // 9¿ù
			break;
			case "17": x = "¿©¼¸²ý"; // 9¿ù
			break;
			case "18": x = "ÀÏ°ö²ý"; // 10¿ù
			break;
			case "19": x = "ÀÏ°ö²ý"; // 10¿ù
			break;
			}
		break;
		case "13" : // 7¿ù
			switch(card2) {
			case "00": x = "¿©´ü²ý"; // 1±¤
			break;
			case "01": x = "¿©´ü²ý"; // 1¿ù
			break;
			case "02": x = "°©¿À"; // 2¿ù
			break;
			case "03": x = "°©¿À"; // 2¿ù
			break;
			case "04": x = "¸ÁÅë"; // 3±¤
			break;
			case "05": x = "¸ÁÅë"; // 3¿ù
			break;
			case "06": x = "ÇÑ²ý"; // 4¿ù
			break;
			case "07": x = "ÇÑ²ý"; // 4¿ù
			break;
			case "08": x = "µÎ²ý"; // 5¿ù	
			break;
			case "09": x = "µÎ²ý"; // 5¿ù
			break;
			case "10": x = "¼¼²ý"; // 6¿ù
			break;
			case "11": x = "¼¼²ý"; // 6¿ù
			break;
			case "12": x = "Ä¥¶¯"; // 7¿ù
			break;
			case "14": x = "´Ù¼¸²ý"; // 8±¤
			break;
			case "15": x = "´Ù¼¸²ý"; // 8¿ù
			break;
			case "16": x = "¿©¼¸²ý"; // 9¿ù
			break;
			case "17": x = "¿©¼¸²ý"; // 9¿ù
			break;
			case "18": x = "ÀÏ°ö²ý"; // 10¿ù
			break;
			case "19": x = "ÀÏ°ö²ý"; // 10¿ù
			break;
			}
		break;
		case "14" : // 8±¤
			switch(card2) {
			case "00": x = "ÀÏÆÈ±¤¶¯"; // 1±¤
			break;
			case "01": x = "°©¿À"; // 1¿ù
			break;
			case "02": x = "¸ÁÅë"; // 2¿ù
			break;
			case "03": x = "¸ÁÅë"; // 2¿ù
			break;
			case "04": x = "»ïÆÈ±¤¶¯"; // 3±¤
			break;
			case "05": x = "ÇÑ²à"; // 3¿ù
			break;
			case "06": x = "µÎ²ý"; // 4¿ù
			break;
			case "07": x = "µÎ²ý"; // 4¿ù
			break;
			case "08": x = "¼¼²ý"; // 5¿ù	
			break;
			case "09": x = "¼¼²ý"; // 5¿ù
			break;
			case "10": x = "³×²ý"; // 6¿ù
			break;
			case "11": x = "³×²ý"; // 6¿ù
			break;
			case "12": x = "´Ù¼¸²ý"; // 7¿ù
			break;
			case "13": x = "´Ù¼¸²ý"; // 7¿ù
			break;
			case "15": x = "ÆÈ¶¯"; // 8¿ù
			break;
			case "16": x = "ÀÏ°ö²ý"; // 9¿ù
			break;
			case "17": x = "ÀÏ°ö²ý"; // 9¿ù
			break;
			case "18": x = "¿©´ü²ý"; // 10¿ù
			break;
			case "19": x = "¿©´ü²ý"; // 10¿ù
			break;
			}
		break;
		case "15" : // 8¿ù
			switch(card2) {
			case "00": x = "°©¿À"; // 1±¤
			break;
			case "01": x = "°©¿À"; // 1¿ù
			break;
			case "02": x = "¸ÁÅë"; // 2¿ù
			break;
			case "03": x = "¸ÁÅë"; // 2¿ù
			break;
			case "04": x = "ÇÑ²ý"; // 3±¤
			break;
			case "05": x = "ÇÑ²à"; // 3¿ù
			break;
			case "06": x = "µÎ²ý"; // 4¿ù
			break;
			case "07": x = "µÎ²ý"; // 4¿ù
			break;
			case "08": x = "¼¼²ý"; // 5¿ù	
			break;
			case "09": x = "¼¼²ý"; // 5¿ù
			break;
			case "10": x = "³×²ý"; // 6¿ù
			break;
			case "11": x = "³×²ý"; // 6¿ù
			break;
			case "12": x = "´Ù¼¸²ý"; // 7¿ù
			break;
			case "13": x = "´Ù¼¸²ý"; // 7¿ù
			break;
			case "14": x = "ÆÈ¶¯"; // 8±¤
			break;
			case "16": x = "ÀÏ°ö²ý"; // 9¿ù
			break;
			case "17": x = "ÀÏ°ö²ý"; // 9¿ù
			break;
			case "18": x = "¿©´ü²ý"; // 10¿ù
			break;
			case "19": x = "¿©´ü²ý"; // 10¿ù
			break;
			}
		break;
		case "16" : // 9¿ù
			switch(card2) {
			case "00": x = "±¸»æ"; // 1±¤
			break;
			case "01": x = "±¸»æ"; // 1¿ù
			break;
			case "02": x = "ÇÑ²ý"; // 2¿ù
			break;	
			case "03": x = "ÇÑ²ý"; // 2¿ù
			break;
			case "04": x = "µÎ²ý"; // 3±¤
			break;
			case "05": x = "µÎ²ý"; // 3¿ù
			break;
			case "06": x = "¸ÛÅÖ±¸¸®±¸»ç"; // 4¿ù
			break;
			case "07": x = "±¸»ç"; // 4¿ù
			break;
			case "08": x = "³×²ý"; // 5¿ù	
			break;
			case "09": x = "³×²ý"; // 5¿ù
			break;
			case "10": x = "´Ù¼¸²ý"; // 6¿ù
			break;
			case "11": x = "´Ù¼¸²ý"; // 6¿ù
			break;
			case "12": x = "¿©¼¸²ý"; // 7¿ù
			break;
			case "13": x = "¿©¼¸²ý"; // 7¿ù
			break;
			case "14": x = "ÀÏ°ö²ý"; // 8±¤
			break;
			case "15": x = "ÀÏ°ö²ý"; // 8¿ù
			break;
			case "17": x = "±¸¶¯"; // 9¿ù
			break;
			case "18": x = "¾ÆÈ©²ý"; // 10¿ù
			break;
			case "19": x = "¾ÆÈ©²ý"; // 10¿ù
			break;
			}
		break;
		case "17" : // 9¿ù
			switch(card2) {
			case "00": x = "±¸»æ"; // 1±¤
			break;
			case "01": x = "±¸»æ"; // 1¿ù
			break;
			case "02": x = "ÇÑ²ý"; // 2¿ù
			break;	
			case "03": x = "ÇÑ²ý"; // 2¿ù
			break;
			case "04": x = "µÎ²ý"; // 3±¤
			break;
			case "05": x = "µÎ²ý"; // 3¿ù
			break;
			case "06": x = "±¸»ç"; // 4¿ù
			break;
			case "07": x = "±¸»ç"; // 4¿ù
			break;
			case "08": x = "³×²ý"; // 5¿ù	
			break;
			case "09": x = "³×²ý"; // 5¿ù
			break;
			case "10": x = "´Ù¼¸²ý"; // 6¿ù
			break;
			case "11": x = "´Ù¼¸²ý"; // 6¿ù
			break;
			case "12": x = "¿©¼¸²ý"; // 7¿ù
			break;
			case "13": x = "¿©¼¸²ý"; // 7¿ù
			break;
			case "14": x = "ÀÏ°ö²ý"; // 8±¤
			break;
			case "15": x = "ÀÏ°ö²ý"; // 8¿ù
			break;
			case "16": x = "±¸¶¯"; // 9¿ù
			break;
			case "18": x = "¾ÆÈ©²ý"; // 10¿ù
			break;
			case "19": x = "¾ÆÈ©²ý"; // 10¿ù
			break;
			}
		break;
		case "18" : // 10¿ù
			switch(card2) {
			case "00": x = "Àå»æ"; // 1±¤
			break;
			case "01": x = "Àå»æ"; // 1¿ù
			break;
			case "02": x = "ÇÑ²ý"; // 2¿ù
			break;	
			case "03": x = "ÇÑ²ý"; // 2¿ù
			break;
			case "04": x = "µÎ²ý"; // 3±¤
			break;
			case "05": x = "µÎ²ý"; // 3¿ù
			break;
			case "06": x = "±¸»ç"; // 4¿ù
			break;
			case "07": x = "±¸»ç"; // 4¿ù
			break;
			case "08": x = "³×²ý"; // 5¿ù		
			break;
			case "09": x = "³×²ý"; // 5¿ù
			break;
			case "10": x = "´Ù¼¸²ý"; // 6¿ù
			break;
			case "11": x = "´Ù¼¸²ý"; // 6¿ù
			break;
			case "12": x = "¿©¼¸²ý"; // 7¿ù
			break;
			case "13": x = "¿©¼¸²ý"; // 7¿ù
			break;
			case "14": x = "ÀÏ°ö²ý"; // 8±¤
			break;
			case "15": x = "ÀÏ°ö²ý"; // 8¿ù
			break;
			case "16": x = "¾ÆÈ©²ý"; // 9¿ù
			break;
			case "17": x = "¾ÆÈ©²ý"; // 9¿ù
			break;
			case "19": x = "Àå¶¯"; // 10¿ù
			break;
			}
		break;
		case "19" : // 10¿ù
			switch(card2) {
			case "00": x = "Àå»æ"; // 1±¤
			break;
			case "01": x = "Àå»æ"; // 1¿ù
			break;
			case "02": x = "ÇÑ²ý"; // 2¿ù
			break;	
			case "03": x = "ÇÑ²ý"; // 2¿ù
			break;
			case "04": x = "µÎ²ý"; // 3±¤
			break;
			case "05": x = "µÎ²ý"; // 3¿ù
			break;
			case "06": x = "±¸»ç"; // 4¿ù
			break;
			case "07": x = "±¸»ç"; // 4¿ù
			break;
			case "08": x = "³×²ý"; // 5¿ù	
			break;
			case "09": x = "³×²ý"; // 5¿ù
			break;
			case "10": x = "´Ù¼¸²ý"; // 6¿ù
			break;
			case "11": x = "´Ù¼¸²ý"; // 6¿ù
			break;
			case "12": x = "¿©¼¸²ý"; // 7¿ù
			break;
			case "13": x = "¿©¼¸²ý"; // 7¿ù
			break;
			case "14": x = "ÀÏ°ö²ý"; // 8±¤
			break;
			case "15": x = "ÀÏ°ö²ý"; // 8¿ù
			break;
			case "16": x = "¾ÆÈ©²ý"; // 9¿ù
			break;
			case "17": x = "¾ÆÈ©²ý"; // 9¿ù
			break;
			case "18": x = "Àå¶¯"; // 10¿ù
			break;
			}
		break;
		}
		return x;
	} // calculateJokbo ³¡
}	