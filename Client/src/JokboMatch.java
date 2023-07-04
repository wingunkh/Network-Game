public class JokboMatch {
	private String AJokbo; // À¯Àú A ÀÇ Á·º¸
	private String BJokbo; // À¯Àú B ÀÇ Á·º¸

	public JokboMatch(String str) {
		String array[] = str.split(" ");
		this.AJokbo = array[0];
		this.BJokbo = array[1];
	}

	public String selectWinner() {
		String winner = "";
		if (AJokbo.equals("»ïÆÈ±¤¶¯")) {
			return "A";
		} else if (BJokbo.equals("»ïÆÈ±¤¶¯")) {
			return "B";
		}

		int Alevel = 0;
		int Blevel = 0;

		if (AJokbo.equals("ÀÏÆÈ±¤¶¯") || AJokbo.equals("ÀÏ»ï±¤¶¯")) {
			Alevel = 50;
		}

		else if (AJokbo.charAt(1) == '¶¯') {
			switch (AJokbo.substring(0, 1)) {
			case "Àå":
				Alevel = 49;
				break;
			case "±¸":
				Alevel = 48;
				break;
			case "ÆÈ":
				Alevel = 47;
				break;
			case "Ä¥":
				Alevel = 46;
				break;
			case "À°":
				Alevel = 45;
				break;
			case "¿À":
				Alevel = 44;
				break;
			case "»ç":
				Alevel = 43;
				break;
			case "»ï":
				Alevel = 42;
				break;
			case "ÀÌ":
				Alevel = 41;
				break;
			case "»æ":
				Alevel = 40;
				break;
			}
		} else {
			switch (AJokbo) {
			case "¾Ë¸®": // 1¿ù + 2¿ù Á·º¸
				Alevel = 39;
				break;
			case "µ¶»ç": // 1¿ù + 4¿ù Á·º¸
				Alevel = 38;
				break;
			case "±¸»æ": // 1¿ù + 9¿ù Á·º¸
				Alevel = 37;
				break;
			case "Àå»æ": // 1¿ù + 10¿ù Á·º¸
				Alevel = 36;
				break;
			case "Àå»ç": // 4¿ù + 10¿ù Á·º¸
				Alevel = 35;
				break;
			case "¼¼·ú": // 4¿ù + 6¿ù Á·º¸
				Alevel = 34;
				break;
			case "°©¿À": // µÎ ÆÐÀÇ ÇÕÀÇ 1ÀÇ ÀÚ¸® 9
				Alevel = 33;
				break;
			case "¿©´ü²ý": // µÎ ÆÐÀÇ ÇÕÀÇ 1ÀÇ ÀÚ¸® 8
				Alevel = 32;
				break;
			case "ÀÏ°ö²ý": // µÎ ÆÐÀÇ ÇÕÀÇ 1ÀÇ ÀÚ¸® 7
				Alevel = 31;
				break;
			case "¿©¼¸²ý": // µÎ ÆÐÀÇ ÇÕÀÇ 1ÀÇ ÀÚ¸® 6
				Alevel = 30;
				break;
			case "´Ù¼¸²ý": // µÎ ÆÐÀÇ ÇÕÀÇ 1ÀÇ ÀÚ¸® 5
				Alevel = 29;
				break;
			case "³×²ý": // µÎ ÆÐÀÇ ÇÕÀÇ 1ÀÇ ÀÚ¸® 4
				Alevel = 28;
				break;
			case "¼¼²ý": // µÎ ÆÐÀÇ ÇÕÀÇ 1ÀÇ ÀÚ¸® 3
				Alevel = 27;
				break;
			case "µÎ²ý": // µÎ ÆÐÀÇ ÇÕÀÇ 1ÀÇ ÀÚ¸® 2
				Alevel = 26;
				break;
			case "ÇÑ²ý": // µÎ ÆÐÀÇ ÇÕÀÇ 1ÀÇ ÀÚ¸® 1
				Alevel = 25;
				break;
			case "¸ÁÅë": // µÎ ÆÐÀÇ ÇÕÀÇ 1ÀÇ ÀÚ¸® 0
				Alevel = 24;
				break;
			}
		}

		if (BJokbo.equals("ÀÏÆÈ±¤¶¯") || BJokbo.equals("ÀÏ»ï±¤¶¯")) {
			Blevel = 50;
		} else if (BJokbo.charAt(1) == '¶¯') {
			switch (BJokbo.substring(0, 1)) {
			case "Àå":
				Blevel = 49;
				break;
			case "±¸":
				Blevel = 48;
				break;
			case "ÆÈ":
				Blevel = 47;
				break;
			case "Ä¥":
				Blevel = 46;
				break;
			case "À°":
				Blevel = 45;
				break;
			case "¿À":
				Blevel = 44;
				break;
			case "»ç":
				Blevel = 43;
				break;
			case "»ï":
				Blevel = 42;
				break;
			case "ÀÌ":
				Blevel = 41;
				break;
			case "»æ":
				Blevel = 40;
				break;
			}
		} else {
			switch (BJokbo) {
			case "¾Ë¸®":
				Blevel = 39;
				break;
			case "µ¶»ç":
				Blevel = 38;
				break;
			case "±¸»æ":
				Blevel = 37;
				break;
			case "Àå»æ":
				Blevel = 36;
				break;
			case "Àå»ç":
				Blevel = 35;
				break;
			case "¼¼·ú":
				Blevel = 34;
				break;
			case "°©¿À":
				Blevel = 33;
				break;
			case "¿©´ü²ý":
				Blevel = 32;
				break;
			case "ÀÏ°ö²ý":
				Blevel = 31;
				break;
			case "¿©¼¸²ý":
				Blevel = 30;
				break;
			case "´Ù¼¸²ý":
				Blevel = 29;
				break;
			case "³×²ý":
				Blevel = 28;
				break;
			case "¼¼²ý":
				Blevel = 27;
				break;
			case "µÎ²ý":
				Blevel = 26;
				break;
			case "ÇÑ²ý":
				Blevel = 25;
				break;
			case "¸ÁÅë":
				Blevel = 24;
				break;
			}
		}

		if (AJokbo.equals("¶¯ÀâÀÌ")) {
			switch (BJokbo) {
			case "±¸¶¯":
			case "ÆÈ¶¯":
			case "Ä¥¶¯":
			case "À°¶¯":
			case "¿À¶¯":
			case "»ç¶¯":
			case "»ï¶¯":
			case "ÀÌ¶¯":
			case "»æ¶¯":
				return "A";
			case "¸ÁÅë":
				return "¹«½ÂºÎ";
			default:
				return "B";
			}
		}

		if (BJokbo.equals("¶¯ÀâÀÌ")) {
			switch (AJokbo) {
			case "±¸¶¯":
			case "ÆÈ¶¯":
			case "Ä¥¶¯":
			case "À°¶¯":
			case "¿À¶¯":
			case "»ç¶¯":
			case "»ï¶¯":
			case "ÀÌ¶¯":
			case "»æ¶¯":
				return "B";
			case "¸ÁÅë":
				return "¹«½ÂºÎ";
			default:
				return "A";
			}
		}

		if (AJokbo.equals("±¸»ç")) {
			if (Blevel <= 39)
				Alevel = Blevel;
			else
				Alevel = 39;
		} else if (AJokbo.equals("¸ÛÅÖ±¸¸®±¸»ç")) {
			if (Blevel <= 48)
				Alevel = Blevel;
			else
				Alevel = 48;
		} else if (AJokbo.equals("¾ÏÇà¾î»ç")) {
			if (Blevel == 50)
				Alevel = 51;
			else
				Alevel = 25;
		}

		if (BJokbo.equals("±¸»ç")) {
			if (Alevel <= 39)
				Blevel = Alevel;
			else
				Blevel = 39;
		} else if (BJokbo.equals("¸ÛÅÖ±¸¸®±¸»ç")) {
			if (Alevel <= 48)
				Blevel = Alevel;
			else
				Blevel = 48;
		} else if (BJokbo.equals("¾ÏÇà¾î»ç")) {
			if (Alevel == 50)
				Blevel = 51;
			else
				Blevel = 25;
		}

		if (Alevel == Blevel)
			return "¹«½ÂºÎ";
		else
			return Alevel > Blevel ? "A" : "B";
	}
}