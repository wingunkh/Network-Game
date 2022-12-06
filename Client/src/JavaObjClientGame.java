import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import java.awt.Component;

public class JavaObjClientGame extends JFrame {
   private static final long serialVersionUID = 1L;
   private JPanel contentPane;
   private String UserName;
   private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
   private Socket socket; // 연결소켓
   private ObjectInputStream ois;
   private ObjectOutputStream oos;
   private JLabel lblUserName;
   
   /*우리가 만든 JavaObjectClientView 클래스 내 지역변수 선언하는 공간*/
   private String uID;
   private JLabel Background;
   private JButton shuffle;
   private JLabel durumari;
   private JLabel bbing;
   private JLabel half;
   private JLabel ddadang;
   private JLabel die;
   private JLabel call;
   private JLabel myJokbo;
   private JLabel myJokboBg;
   private JLabel panmoney;
   private JLabel mymoney;
   private JLabel moneyBoard;
   private JLabel myMessage;
   private JLabel otherMessage;
   private JLabel angryBtn;
   private JLabel thankBtn;
   private JLabel sadBtn;
   private JLabel sorryBtn;
   private JLabel myEmotionIcon;
   private JLabel otherEmotionIcon;
   private JLabel myBettingIcon;
   private JLabel otherBettingIcon;
   private Image img;
   private Image updateimg;
   private String backSrc;
   private ImageIcon backIcon;
   private Card myLeft;
   private Card myRight;
   private Card otherLeft;
   private Card otherRight;
   private String array[];
   private Jokbo jokbo;
   private JokboMatch result;
   private JLabel panResult;
   private JLabel gameResult;
   private String previous = "none";
   private ImageIcon panWin = new ImageIcon("src/images/smallWin.png");
   private ImageIcon panLose = new ImageIcon("src/images/smallLose.png");
   private ImageIcon panDraw = new ImageIcon("src/images/smallDraw.png");
   private ImageIcon gameWin = new ImageIcon("src/images/win.png");
   private ImageIcon gameLose = new ImageIcon("src/images/lose.png");
   private int count=0;
   private int amount=0;
   private int property=100000;
   private boolean toggle=true;
   private MouseAdapter leftcardpressed = new MouseAdapter() {
       @Override
       public void mouseClicked(MouseEvent e) {
     	  ChatMsg msg;
     	  if(uID.equals("1"))
     		  msg = new ChatMsg(UserName, "2", "1 myLeft");
     	  else
     		  msg = new ChatMsg(UserName, "2", "2 myLeft");
          SendObject(msg);
       }
   };
   
   private MouseAdapter rightcardpressed = new MouseAdapter() {
       @Override
       public void mouseClicked(MouseEvent e) {
     	  ChatMsg msg;
     	  if(uID.equals("1"))
     		  msg = new ChatMsg(UserName, "2", "1 myRight");
     	  else
     		  msg = new ChatMsg(UserName, "2", "2 myRight");
          SendObject(msg);
       }
   };
   
   private MouseAdapter bbingpressed = new MouseAdapter() {
       @Override
       public void mouseClicked(MouseEvent e) {
    	   ChatMsg msg;
     	   ChatMsg msg2;
     	   ChatMsg msg3;
     	   String message = String.format("%s bbing", uID);
     	   msg = new ChatMsg(UserName, "4", message);
     	   msg2 = new ChatMsg(UserName, "5", message);
     	   msg3 = new ChatMsg(UserName, "6", message);
     	   updateMymoney(property-=10000);
           SendObject(msg3);
           SendObject(msg);
           SendObject(msg2);
       }
   };
   
   private MouseAdapter halfpressed = new MouseAdapter() {
       @Override
       public void mouseClicked(MouseEvent e) {
    	   ChatMsg msg;
     	   ChatMsg msg2;
     	   ChatMsg msg3;
     	   String message = String.format("%s half", uID);
     	   msg = new ChatMsg(UserName, "4", message);
     	   msg2 = new ChatMsg(UserName, "5", message);
     	   msg3 = new ChatMsg(UserName, "6", message);
     	   updateMymoney(property-=15000);
           SendObject(msg3);
           SendObject(msg);
           SendObject(msg2);
       }
   };
   
   private MouseAdapter ddadangpressed = new MouseAdapter() {
       @Override
       public void mouseClicked(MouseEvent e) {
    	   ChatMsg msg;
     	   ChatMsg msg2;
     	   ChatMsg msg3;
     	   String message = String.format("%s ddadang", uID);
     	   msg = new ChatMsg(UserName, "4", message);
     	   msg2 = new ChatMsg(UserName, "5", message);
     	   msg3 = new ChatMsg(UserName, "6", message);
      	  updateMymoney(property-=20000);
          SendObject(msg3);
          SendObject(msg);
          SendObject(msg2);
      }
   };
   
   private MouseAdapter diepressed = new MouseAdapter() {
       @Override
       public void mouseClicked(MouseEvent e) {
       	   ChatMsg msg;
       	   ChatMsg msg2;
       	   String message = String.format("%s die", uID);
       	   msg = new ChatMsg(UserName, "5", message);
       	   msg2 = new ChatMsg(UserName, "6", message);
           SendObject(msg2);
           SendObject(msg);
       }
   };
   
   private MouseAdapter callpressed = new MouseAdapter() {
       @Override
       public void mouseClicked(MouseEvent e) {
       	   ChatMsg msg;
       	   ChatMsg msg2;
       	   String message = String.format("%s call",  uID);
       	   msg = new ChatMsg(UserName, "5", message);
       	   msg2 = new ChatMsg(UserName, "6", message);
           SendObject(msg2);
           SendObject(msg);
       }
   };
   private JLabel userBoard;
   private JLabel exitBtn;
   /*우리가 만든 JavaObjectClientView 클래스 내 지역변수 선언하는 공간*/

   public JavaObjClientGame(String username, String ip_addr, String port_no) {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      contentPane = new JPanel();
      setBounds(100, 100, 900, 630);
      contentPane.setBackground(new Color(255, 255, 255));
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      /*게임 배경*/
      backSrc = "src/images/background2.png";
      backIcon = new ImageIcon(new ImageIcon(backSrc).getImage().getScaledInstance(900, 600, java.awt.Image.SCALE_SMOOTH));     
      /*게임 배경*/

      /*화투 패*/
      myLeft = new Card(200, 210);
      myLeft.setCardBounds();
      contentPane.add(myLeft.getCard());
          
      myRight = new Card(200, 210);
      myRight.setCardBounds();
      contentPane.add(myRight.getCard());
          
      otherLeft = new Card(200, 210);
      otherLeft.setCardBounds();
      contentPane.add(otherLeft.getCard());
      otherRight = new Card(200, 210);
      otherRight.setCardBounds();
      contentPane.add(otherRight.getCard());
      /*화투 패*/
      
      /*내 족보*/
      
      /* 감정 표현 */
      angryBtn = new JLabel("New label");
      angryBtn.setIcon(new ImageIcon("src/emotions/angry2.png"));
      angryBtn.setBounds(12, 545, 110, 42);
      contentPane.add(angryBtn);
      
      sorryBtn = new JLabel("New label");
      sorryBtn.setIcon(new ImageIcon("src/emotions/sorry2.png"));
      sorryBtn.setBounds(232, 545, 110, 42);
      contentPane.add(sorryBtn);
      
      thankBtn = new JLabel("New label");
      thankBtn.setIcon(new ImageIcon("src/emotions/thank2.png"));
      thankBtn.setBounds(122, 545, 110, 42);
      contentPane.add(thankBtn);
      
      sadBtn = new JLabel("New label");
      sadBtn.setIcon(new ImageIcon("src/emotions/sad2.png"));
      sadBtn.setBounds(342, 545, 110, 42);
      contentPane.add(sadBtn);
      
      angryBtn.addMouseListener(new MouseAdapter() {
    	  public void mouseEntered(MouseEvent e) {
    		  angryBtn.setIcon(new ImageIcon("src/emotions/angry1.png"));
    	  }
    	  public void mouseExited(MouseEvent e) {
    		  angryBtn.setIcon(new ImageIcon("src/emotions/angry2.png"));
    	  }
    	  public void mouseClicked(MouseEvent e) {
			ChatMsg msg;
			if(uID.equals("1"))
				msg = new ChatMsg(UserName, "7", "1 angry");
			else 
				msg = new ChatMsg(UserName, "7", "2 angry");
			SendObject(msg);
    	 }
      });
      
      sorryBtn.addMouseListener(new MouseAdapter() {
    	  public void mouseEntered(MouseEvent e) {
    		  sorryBtn.setIcon(new ImageIcon("src/emotions/sorry1.png"));
    	  }
    	  public void mouseExited(MouseEvent e) {
    		  sorryBtn.setIcon(new ImageIcon("src/emotions/sorry2.png"));
    	  }
    	  public void mouseClicked(MouseEvent e) {
  			ChatMsg msg;
  			if(uID.equals("1"))
  				msg = new ChatMsg(UserName, "7", "1 sorry");
  			else 
  				msg = new ChatMsg(UserName, "7", "2 sorry");
  			SendObject(msg);
      	 }
      });
      
      thankBtn.addMouseListener(new MouseAdapter() {
    	  public void mouseEntered(MouseEvent e) {
    		  thankBtn.setIcon(new ImageIcon("src/emotions/thank1.png"));
    	  }
    	  public void mouseExited(MouseEvent e) {
    		  thankBtn.setIcon(new ImageIcon("src/emotions/thank2.png"));
    	  }
    	  public void mouseClicked(MouseEvent e) {
  			ChatMsg msg;
  			if(uID.equals("1"))
  				msg = new ChatMsg(UserName, "7", "1 thank");
  			else 
  				msg = new ChatMsg(UserName, "7", "2 thank");
  			SendObject(msg);
      	 }
      });
      
      sadBtn.addMouseListener(new MouseAdapter() {
    	  public void mouseEntered(MouseEvent e) {
    		  sadBtn.setIcon(new ImageIcon("src/emotions/sad1.png"));
    	  }
    	  public void mouseExited(MouseEvent e) {
    		  sadBtn.setIcon(new ImageIcon("src/emotions/sad2.png"));
    	  }
    	  public void mouseClicked(MouseEvent e) {
  			ChatMsg msg;
  			if(uID.equals("1"))
  				msg = new ChatMsg(UserName, "7", "1 sad");
  			else 
  				msg = new ChatMsg(UserName, "7", "2 sad");
  			SendObject(msg);
      	 }
      });
      myEmotionIcon = new JLabel("myEmotionIcon");
      myEmotionIcon.setLocation(205, 345);
      myEmotionIcon.setVisible(false);
      myEmotionIcon.setSize(35, 35);
      contentPane.add(myEmotionIcon);
      otherEmotionIcon = new JLabel("otherEmotionIcon");
      otherEmotionIcon.setLocation(205, 175);
      otherEmotionIcon.setSize(35, 35);
      otherEmotionIcon.setVisible(false);
      contentPane.add(otherEmotionIcon);
      /* 감정 표현 */
      /* 베팅 표현 */
      myBettingIcon = new JLabel("myBettingIcon");
      myBettingIcon.setLocation(192, 340);
      myBettingIcon.setVisible(false);
      myBettingIcon.setSize(64, 40);
      contentPane.add(myBettingIcon);
      otherBettingIcon = new JLabel("otherBettingIcon");
      otherBettingIcon.setLocation(192, 170);
      otherBettingIcon.setSize(64, 40);
      otherBettingIcon.setVisible(false);
      contentPane.add(otherBettingIcon);
      /* 베팅 표현 */
      /* 내 족보 */
      myJokbo = new JLabel();
      myJokbo.setLocation(706, 504);
      myJokbo.setSize(110, 50);
      myJokbo.setFont(new Font("한컴 울주 반구대 암각화체", Font.PLAIN, 32));
      contentPane.add(myJokbo);
      
      myJokboBg = new JLabel();
      myJokboBg.setLocation(686, 504);
      myJokboBg.setSize(130, 50);
      myJokboBg.setIcon(new ImageIcon("src/images/flower.png"));
      contentPane.add(myJokboBg);
      /*내 족보*/
      
      /*판돈*/
      panmoney = new JLabel(Integer.toString(amount), SwingConstants.RIGHT);
      panmoney.setForeground(new Color(255, 255, 255));
      panmoney.setFont(new Font("맑은 고딕 Semilight", Font.BOLD, 25));
      panmoney.setBounds(608, 253, 110, 28);
      contentPane.add(panmoney);
      /*판돈*/
      
      /*내돈*/  
      mymoney = new JLabel("100000", SwingConstants.RIGHT);
      mymoney.setForeground(new Color(255, 255, 255));
      mymoney.setFont(new Font("맑은 고딕 Semilight", Font.BOLD, 20));
      mymoney.setBounds(620, 299, 100, 19);
      contentPane.add(mymoney);
      /*내돈*/
      
      /*말풍선*/
      myMessage = new JLabel("");
      myMessage.setBounds(180, 330, 90, 80);
      myMessage.setVisible(false);
      myMessage.setIcon(new ImageIcon("src/images/myBubble.png"));
      contentPane.add(myMessage);
      
      otherMessage = new JLabel("");
      otherMessage.setBounds(180, 145, 90, 80);
      otherMessage.setVisible(false);
      otherMessage.setIcon(new ImageIcon("src/images/otherBubble.png"));
      contentPane.add(otherMessage);
      /*말풍선*/
      
      /*시작 버튼*/
//      img = new ImageIcon("src/buttons/Start1.png").getImage();
//      updateimg = img.getScaledInstance(140, 54, java.awt.Image.SCALE_SMOOTH);
//      shuffle = new JButton(new ImageIcon(updateimg));

      /* 배팅 버튼 */
      half = new JLabel(new ImageIcon(new ImageIcon("src/images/half2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
      half.setToolTipText("\"1.5\uB9CC\uC6D0\"");
      half.setBounds(686, 384, 130, 50);
      contentPane.add(half);
      
      ddadang = new JLabel(new ImageIcon(new ImageIcon("src/images/ddadang2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
      ddadang.setToolTipText("\"2\uB9CC\uC6D0\"");
      ddadang.setBounds(524, 444, 130, 50);
      contentPane.add(ddadang);
      
      die = new JLabel(new ImageIcon(new ImageIcon("src/images/die2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
      die.setBounds(686, 444, 130, 50);
      contentPane.add(die);
      call = new JLabel(new ImageIcon(new ImageIcon("src/images/call2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
      call.setBounds(524, 504, 130, 50);
      contentPane.add(call);
      
      bbing= new JLabel(new ImageIcon(new ImageIcon("src/images/bbing2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
      bbing.setToolTipText("\"1\uB9CC\uC6D0\"");
      bbing.setBounds(524, 384, 130, 50);
      contentPane.add(bbing);
      
      shuffle = new JButton("");
      shuffle.setIcon(new ImageIcon("src/buttons/Start1.png"));
      shuffle.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
        	  ChatMsg msg = new ChatMsg(UserName, "1", "덱 분배");
              SendObject(msg);
          }
          public void mouseEntered(MouseEvent e) {
        	  shuffle.setIcon(new ImageIcon("src/buttons/Start2.png"));
          }
          public void mouseExited(MouseEvent e) {
        	  shuffle.setIcon(new ImageIcon("src/buttons/Start1.png"));
          }
       });
      shuffle.setBorderPainted(false);
      shuffle.setContentAreaFilled(false);
      shuffle.setBounds(165, 260, 140, 54);
      shuffle.setVisible(false);
      contentPane.add(shuffle);
      /* 배팅 버튼 */
      /*시작 버튼*/

      /*두루마리*/
      durumari = new JLabel(new ImageIcon(new ImageIcon("src/images/durumari.png").getImage().getScaledInstance(400, 240, java.awt.Image.SCALE_SMOOTH)));
      durumari.setBounds(476, 345, 400, 240);
      contentPane.add(durumari);
      /*두루마리*/

      lblUserName = new JLabel("Name");
      lblUserName.setBackground(Color.WHITE);
      lblUserName.setFont(new Font("굴림", Font.BOLD, 14));
      lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
      lblUserName.setBounds(746, 17, 62, 40);
      contentPane.add(lblUserName);
      setVisible(true);

      UserName = username;
      lblUserName.setText(username);
      
      moneyBoard = new JLabel("New label");
      moneyBoard.setIcon(new ImageIcon("src/images/moneyboard.png"));
      moneyBoard.setBounds(484, 246, 275, 83);
      contentPane.add(moneyBoard);
      
      userBoard = new JLabel("New label");
      userBoard.setIcon(new ImageIcon("src/images/userboard.png"));
      userBoard.setBounds(743, 10, 70, 54);
      contentPane.add(userBoard);
      
      exitBtn = new JLabel("New label");
      exitBtn.setIcon(new ImageIcon("src/buttons/Exit1.png"));
      exitBtn.setBounds(813, 10, 63, 54);
      exitBtn.addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent e) {
             ChatMsg msg = new ChatMsg(UserName, "400", "Bye");
             SendObject(msg);
             System.exit(0);
          }
          public void mouseEntered(MouseEvent e) {
        	  exitBtn.setIcon(new ImageIcon("src/buttons/Exit2.png"));
          }
          public void mouseExited(MouseEvent e) {
        	  exitBtn.setIcon(new ImageIcon("src/buttons/Exit1.png"));
          }
       });
      contentPane.add(exitBtn);
      
      panResult = new JLabel("New label");
      panResult.setBounds(120, 235, 200, 100);
      panResult.setVisible(false);
      contentPane.add(panResult);
      
      gameResult = new JLabel("New label");
      gameResult.setBounds(175, 29, 500, 525);
      gameResult.setVisible(false);
      contentPane.add(gameResult);
      
      Background = new JLabel("");
      Background.setFont(new Font("맑은 고딕 Semilight", Font.PLAIN, 15));
      Background.setIcon(backIcon);
      Background.setBounds(0, 0, 884, 595);
      contentPane.add(Background);
      
      try {
         socket = new Socket(ip_addr, Integer.parseInt(port_no));

         oos = new ObjectOutputStream(socket.getOutputStream());
         oos.flush();
         ois = new ObjectInputStream(socket.getInputStream());

         ChatMsg obcm = new ChatMsg(UserName, "100", "Hello");
         SendObject(obcm);
         
         ListenNetwork net = new ListenNetwork();
         net.start();

      } catch (NumberFormatException | IOException e) {
         e.printStackTrace();
         AppendText("connect error");
      }
   }

   // Server Message를 수신해서 화면에 표시
   class ListenNetwork extends Thread {
      public void run() {
         while (true) {
            try {
               Object obcm = null;
               String msg = null;
               ChatMsg cm;
               try {
                  obcm = ois.readObject();
               } catch (ClassNotFoundException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                  break;
               }
               if (obcm == null)
                  break;
               if (obcm instanceof ChatMsg) {
                  cm = (ChatMsg) obcm;
                     msg = String.format("[%s] %s", cm.getId(), cm.getData());
               } else
                  continue;
               switch (cm.getCode()) {
               case "100":
                  uID=cm.getData();
                  break;
               case "104":
            	  shuffle.setVisible(true);
            	  contentPane.repaint();
            	  break;
               case "1":
                  array = cm.getData().split(" ");
                  shuffle.setVisible(false);
                  myLeft.getCard().setVisible(true);
                  myRight.getCard().setVisible(true);
                  otherLeft.getCard().setVisible(true);
                  otherRight.getCard().setVisible(true);
                  myLeft.backside();
                  myRight.backside();
                  otherLeft.backside();
                  otherRight.backside();
                  Thread.sleep(1500);
                 
                  Placing(uID);
                    
                  myLeft.flip();
                  myRight.flip();
                  otherLeft.flip();
                  otherRight.flip();
                  
                  myLeft.getCard().addMouseListener(leftcardpressed);
                  myRight.getCard().addMouseListener(rightcardpressed);
                  
                  updateMymoney(property-=10000);
                  updatePanmoney(amount+=20000);
                  break;
               case "2":
            	   if(cm.getData().equals("1 myLeft")) {
            		   Opening(cm.getData());
            	   }
            	   else if(cm.getData().equals("2 myLeft")) {
            		   Opening(cm.getData());
            	   }
            	   else if(cm.getData().equals("1 myRight")) {
            		   Opening(cm.getData());
            	   }
            	   else if(cm.getData().equals("2 myRight")){
            		   Opening(cm.getData());
            	   }
            	   break;
               case "3":
            	   if(uID.equals("1")) {
                	   ChatMsg chatmsg = new ChatMsg(UserName, "4", "배팅 시작!");
                       SendObject(chatmsg);
            	   }
            	   break;
               case "4":
            	   if(toggle) {
            		   if(uID.equals("1")) // 여기까지 정상
            			   batting();
                	   else
                		   waitbatting();  
            		   toggle=!toggle;
            	   }
            	   else {
            		   if(uID.equals("1"))
                		   waitbatting();
                	   else
                		   batting();
            		   toggle=!toggle;
            	   }
            	   break;
               case "5":
            	   if(!(cm.getData().split(" ")[1].equals("call")))
                	   previous = cm.getData().split(" ")[1];
            	   if(!previous.equals("none")&&(!(cm.getData().split(" ")[0].equals(uID)))) {
            		   //1. 첫 베팅이 아닐 때
            		   //2. 자신의 차례일 때
            		   //3. 상대가 베팅한 금액만큼 베팅할 수 있을 때
            		   if(previous.equals("bbing")&&(property>10000)) {
                		   call.addMouseListener(callpressed);
                		   call.setIcon(new ImageIcon(new ImageIcon("src/images/call1.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
            		   }
            		   else if(previous.equals("half")&&(property>15000)) {
                		   call.addMouseListener(callpressed);
                		   call.setIcon(new ImageIcon(new ImageIcon("src/images/call1.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
            		   }
            		   else if(previous.equals("ddadang")&&(property>20000)) {
                		   call.addMouseListener(callpressed);
                		   call.setIcon(new ImageIcon(new ImageIcon("src/images/call1.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
            		   }
            		   //만 콜 버튼이 활성화 되어야한다.
            	   }
            	   if(cm.getData().equals("1 bbing"))
            		   updatePanmoney(amount+=10000);
            	   else if(cm.getData().equals("2 bbing"))
            		   updatePanmoney(amount+=10000);
            	   else if(cm.getData().equals("1 half")) {
            		   updatePanmoney(amount+=15000);
            		   bbing.removeMouseListener(bbingpressed);
            		   bbing.setIcon(new ImageIcon(new ImageIcon("src/images/bbing2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
            	   }
            	   else if(cm.getData().equals("2 half")) {
        			   updatePanmoney(amount+=15000);
        			   bbing.removeMouseListener(bbingpressed);
        			   bbing.setIcon(new ImageIcon(new ImageIcon("src/images/bbing2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
            	   }
            	   else if(cm.getData().equals("1 ddadang")) {
        			   updatePanmoney(amount+=20000);
        			   bbing.removeMouseListener(bbingpressed);
        			   bbing.setIcon(new ImageIcon(new ImageIcon("src/images/bbing2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
        			   half.removeMouseListener(halfpressed);
        			   half.setIcon(new ImageIcon(new ImageIcon("src/images/half2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
            	   }
            	   else if(cm.getData().equals("2 ddadang")) {
        			   updatePanmoney(amount+=20000);
        			   bbing.removeMouseListener(bbingpressed);
        			   bbing.setIcon(new ImageIcon(new ImageIcon("src/images/bbing2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
        			   half.removeMouseListener(halfpressed);
        			   half.setIcon(new ImageIcon(new ImageIcon("src/images/half2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
            	   }
            	   else if(cm.getData().equals("1 die")) {    		   
            		   showResult("2");
            		   if(uID.equals("2"))
            			   updateMymoney(property+=amount);
               		   updatePanmoney(0);
            		   reGame();
            	   }
            	   else if(cm.getData().equals("2 die")) {
            		   showResult("1");
            		   if(uID.equals("1"))
            			   updateMymoney(property+=amount);
            		   updatePanmoney(0);
            		   reGame();
            	   }
            	   else if(cm.getData().equals("1 call")) {
            		   if(uID.equals("1")) {
                		   switch (previous) {
            	   		   case "bbing":
            	   			   updateMymoney(property-=10000);
            	   			   break;
            	   		   case "half":
            	   			   updateMymoney(property-=15000);
            	   			   break;
            	   		   case "ddadang":
            	   			   updateMymoney(property-=20000);
            	   			   break;
            	   		   }   
            		   }
            		   switch (previous) {
        	   		   case "bbing":
        	   			   updatePanmoney(amount+=10000);
        	   			   break;
        	   		   case "half":
        	   			   updatePanmoney(amount+=15000);
        	   			   break;
        	   		   case "ddadang":
        	   			   updatePanmoney(amount+=20000);
        	   			   break;
        	   		   } 	
            		   battle();
            	   }
            	   else if(cm.getData().equals("2 call")) {
            		   if(uID.equals("2")) {
                		   switch (previous) {
            	   		   case "bbing":
            	   			   updateMymoney(property-=10000);
            	   			   break;
            	   		   case "half":
            	   			   updateMymoney(property-=15000);
            	   			   break;
            	   		   case "ddadang":
            	   			   updateMymoney(property-=20000);
            	   			   break;
            	   		   }   
            		   }
         	   		   switch (previous) {
        	   		   case "bbing":
        	   			   updatePanmoney(amount+=10000);
        	   			   break;
        	   		   case "half":
        	   			   updatePanmoney(amount+=15000);
        	   			   break;
        	   		   case "ddadang":
        	   			   updatePanmoney(amount+=20000);
        	   			   break;
        	   		   } 	
            		   battle();
            	   }
            	   break;
               case "6": //배팅 표시 프로토콜
            	   if (cm.getData().split(" ")[0].equals("1")) { //1번 client에서 배팅 누를 경우
            		   if (uID.equals("1")) //1번 client에서 출력
            			   printMyBetting(cm.getData().split(" ")[1]);
            		   else //2번 client에서 출력
            			   printOtherBetting(cm.getData().split(" ")[1]);
            	   }
            	   else {//2번 client에서 배팅 누를 경우
            		   if (uID.equals("1")) //1번 client에서 출력
            			   printOtherBetting(cm.getData().split(" ")[1]);
            		   else //2번 client에서 출력
            			   printMyBetting(cm.getData().split(" ")[1]);
            	   }
            	   break;
               case "7": //감정표현 프로토콜
            	   if (cm.getData().split(" ")[0].equals("1")) { //1번 client에서 배팅 누를 경우
            		   if (uID.equals("1")) //1번 client에서 출력
            			   printMyEmotion(cm.getData().split(" ")[1]);
            		   else //2번 client에서 출력
            			   printOtherEmotion(cm.getData().split(" ")[1]);
            	   }
            	   else {//2번 client에서 배팅 누를 경우
            		   if (uID.equals("1")) //1번 client에서 출력
            			   printOtherEmotion(cm.getData().split(" ")[1]);
            		   else //2번 client에서 출력
            			   printMyEmotion(cm.getData().split(" ")[1]);
            	   }
            	   break;
               case "0": //게임 전체 승패 프로토콜
            	   gameEnd(cm.getData().split(" ")[0]);
            	   break;
               }
            } catch (IOException e) {
               AppendText("ois.readObject() error");
               try {
                  ois.close();
                  oos.close();
                  socket.close();
                  break;
               } catch (Exception ee) {
                  break;
               } // catch문 끝
            } // 바깥 catch문끝
            catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            }
         }
      }
   }

   // 화면에 출력
   public void AppendText(String msg) {
      msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
      myJokbo.setText(msg);
   }

   // Windows 처럼 message 제외한 나머지 부분은 NULL 로 만들기 위한 함수
   public byte[] MakePacket(String msg) {
      byte[] packet = new byte[BUF_LEN];
      byte[] bb = null;
      int i;
      for (i = 0; i < BUF_LEN; i++)
         packet[i] = 0;
      try {
         bb = msg.getBytes("euc-kr");
      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
         System.exit(0);
      }
      for (i = 0; i < bb.length; i++)
         packet[i] = bb[i];
      return packet;
   }

   // Server에게 메세지를 보내는 메소드
   public void SendObject(Object ob) {
      try {
         oos.writeObject(ob);
      } catch (IOException e) {
         AppendText("SendObj ect Error");
      }
   }
   
   public void Placing(String uid) throws InterruptedException {
      if(uid.equals("1")) {
         for(int i=0;i<4;i++) {
            switch(i) {
             case 0 :
                myLeft.setCardSrc(array[i]);
                myLeft.setCardIcon(myLeft.getCardSrc());
                while(myLeft.getX() > 100 || myLeft.getY() < 390 ) {
                   if (myLeft.getX() > 100) myLeft.setX(myLeft.getX() - 5);
                   if (myLeft.getY() < 390) myLeft.setY(myLeft.getY() + 9);
                   myLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             case 1 :
                myRight.setCardSrc(array[i]);
                myRight.setCardIcon(myRight.getCardSrc());
                while(myRight.getX() < 300 || myRight.getY() < 390 ) {
                   if (myRight.getX() < 300) myRight.setX(myRight.getX() + 5);
                   if (myRight.getY() < 390) myRight.setY(myRight.getY() + 9);
                   myRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             case 2 :
            	otherLeft.setCardSrc(array[i]);
                while(otherLeft.getX() > 100 || otherLeft.getY() > 30 ) {
                   if (otherLeft.getX() > 100) otherLeft.setX(otherLeft.getX() - 5);
                   if (otherLeft.getY() > 30) otherLeft.setY(otherLeft.getY() - 9);
                   otherLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             case 3 :
            	otherRight.setCardSrc(array[i]);
                while(otherRight.getX() < 300 || otherRight.getY() > 30 ) {
                   if (otherRight.getX() < 300) otherRight.setX(otherRight.getX() + 5);
                   if (otherRight.getY() > 30) otherRight.setY(otherRight.getY() - 9);
                   otherRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             } 
         }
         jokbo = new Jokbo(myLeft, myRight);
         AppendText(jokbo.calculateJokbo());
      }
      else {
         for(int i=0;i<4;i++) {
            switch(i) {
            case 0 :
            	otherLeft.setCardSrc(array[i]);
                while(otherLeft.getX() > 100 || otherLeft.getY() > 30 ) {
                   if (otherLeft.getX() > 100) otherLeft.setX(otherLeft.getX() - 5);
                   if (otherLeft.getY() > 30) otherLeft.setY(otherLeft.getY() - 9);
                   otherLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             case 1 :
            	otherRight.setCardSrc(array[i]);
                while(otherRight.getX() < 300 || otherRight.getY() > 30 ) {
                   if (otherRight.getX() < 300) otherRight.setX(otherRight.getX() + 5);
                   if (otherRight.getY() > 30) otherRight.setY(otherRight.getY() - 9);
                   otherRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             case 2 :
                myLeft.setCardSrc(array[i]);
                myLeft.setCardIcon(myLeft.getCardSrc());
                while(myLeft.getX() > 100 || myLeft.getY() < 390 ) {
                   if (myLeft.getX() > 100) myLeft.setX(myLeft.getX() - 5);
                   if (myLeft.getY() < 390) myLeft.setY(myLeft.getY() + 9);
                   myLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             case 3 :
                myRight.setCardSrc(array[i]);
                myRight.setCardIcon(myRight.getCardSrc());
                while(myRight.getX() < 300 || myRight.getY() < 390 ) {
                   if (myRight.getX() < 300) myRight.setX(myRight.getX() + 5);
                   if (myRight.getY() < 390) myRight.setY(myRight.getY() + 9);
                   myRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             } 
         }
         jokbo = new Jokbo(myLeft, myRight);
         AppendText(jokbo.calculateJokbo());
      }
   }   
   
   public void Opening(String str) throws InterruptedException {  
	   switch(str) {
	   case "1 myLeft":
		   if(uID.equals("1")) {
			   while(myLeft.getY() > 300) {
                   myLeft.setY(myLeft.getY() - 9);
                   myLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
               }
			   Thread.sleep(1000);
			   while(myLeft.getY() < 390) {
                   myLeft.setY(myLeft.getY() + 9);
                   myLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
			   }
			   myLeft.getCard().removeMouseListener(leftcardpressed);
			   myRight.getCard().removeMouseListener(rightcardpressed);
		   }
		   else {
			   while(otherLeft.getY() < 120) {
				   otherLeft.setY(otherLeft.getY() + 9);
				   otherLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(otherLeft.getY() > 30) {
				   otherLeft.setY(otherLeft.getY() - 9);
				   otherLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
			   }
			   otherLeft.setCardSrc(array[0]);
			   otherLeft.setCardIcon(otherLeft.getCardSrc());
			   otherLeft.flip();
		   }
		   break;
	   case "2 myLeft":
		   if(uID.equals("2")) {
			   while(myLeft.getY() > 300) {
                   myLeft.setY(myLeft.getY() - 9);
                   myLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(myLeft.getY() < 390) {
                   myLeft.setY(myLeft.getY() + 9);
                   myLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   myLeft.getCard().removeMouseListener(leftcardpressed);
			   myRight.getCard().removeMouseListener(rightcardpressed);
		   }
		   else {
			   while(otherLeft.getY() < 120) {
				   otherLeft.setY(otherLeft.getY() + 9);
				   otherLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(otherLeft.getY() > 30) {
				   otherLeft.setY(otherLeft.getY() - 9);
				   otherLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
			   }
			   otherLeft.setCardSrc(array[2]);
			   otherLeft.setCardIcon(otherLeft.getCardSrc());
			   otherLeft.flip();
		   }
		   break;
	   case "1 myRight":
		   if(uID.equals("1")) {
			   while(myRight.getY() > 300) {
                   myRight.setY(myRight.getY() - 9);
                   myRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(myRight.getY() < 390) {
                   myRight.setY(myRight.getY() + 9);
                   myRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
               }
			   myLeft.getCard().removeMouseListener(leftcardpressed);
			   myRight.getCard().removeMouseListener(rightcardpressed);
		   }
		   else {
			   while(otherRight.getY() < 120) {
				   otherRight.setY(otherRight.getY() + 9);
				   otherRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(otherRight.getY() > 30) {
				   otherRight.setY(otherRight.getY() - 9);
				   otherRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
			   }
			   otherRight.setCardSrc(array[1]);
			   otherRight.setCardIcon(otherRight.getCardSrc());
			   otherRight.flip();
		   }
		   break;
	   case "2 myRight":
		   if(uID.equals("2")) {
			   while(myRight.getY() > 300) {
                   myRight.setY(myRight.getY() - 9);
                   myRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(myRight.getY() < 390) {
                   myRight.setY(myRight.getY() + 9);
                   myRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
               }
			   myLeft.getCard().removeMouseListener(leftcardpressed);
			   myRight.getCard().removeMouseListener(rightcardpressed);
		   }
		   else {
			   while(otherRight.getY() < 120) {
				   otherRight.setY(otherRight.getY() + 9);
				   otherRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(otherRight.getY() > 30) {
				   otherRight.setY(otherRight.getY() - 9);
				   otherRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
			   }
			   otherRight.setCardSrc(array[3]);
			   otherRight.setCardIcon(otherRight.getCardSrc());
			   otherRight.flip();
		   }
		   break;
	   }
	   count++;
	   if(count == 2) {
		   if(uID.equals("1")) {
			   ChatMsg msg = new ChatMsg(UserName, "3", "배팅버튼 활성화");
	           SendObject(msg);  
		   }
	   }
   }
   
   public void batting() {
	   if(property>10000) {
		   bbing.addMouseListener(bbingpressed);
		   bbing.setIcon(new ImageIcon(new ImageIcon("src/images/bbing1.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
	   }
		   
	   if(property>15000) {
		   half.addMouseListener(halfpressed);
		   half.setIcon(new ImageIcon(new ImageIcon("src/images/half1.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
	   }
	   if(property>20000) {
		   ddadang.addMouseListener(ddadangpressed);
		   ddadang.setIcon(new ImageIcon(new ImageIcon("src/images/ddadang1.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
	   }
	   die.addMouseListener(diepressed);
	   die.setIcon(new ImageIcon(new ImageIcon("src/images/die1.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
   }
   
   public void waitbatting() {
	   bbing.removeMouseListener(bbingpressed);
	   half.removeMouseListener(halfpressed);
	   ddadang.removeMouseListener(ddadangpressed);
	   die.removeMouseListener(diepressed);
	   call.removeMouseListener(callpressed);
	   
	   bbing.setIcon(new ImageIcon(new ImageIcon("src/images/bbing2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
	   half.setIcon(new ImageIcon(new ImageIcon("src/images/half2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
	   ddadang.setIcon(new ImageIcon(new ImageIcon("src/images/ddadang2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
	   die.setIcon(new ImageIcon(new ImageIcon("src/images/die2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
	   call.setIcon(new ImageIcon(new ImageIcon("src/images/call2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
   }
   
   public void updatePanmoney(int updatedAmount) {
	   panmoney.setText(Integer.toString(updatedAmount));
   }
   
   public void updateMymoney(int updatedAmount) {	   
	   if(updatedAmount<10000) {
		   bbing.removeMouseListener(bbingpressed);
		   bbing.setIcon(new ImageIcon(new ImageIcon("src/images/bbing2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
		   half.removeMouseListener(halfpressed);
		   half.setIcon(new ImageIcon(new ImageIcon("src/images/half2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
		   ddadang.removeMouseListener(ddadangpressed);
		   ddadang.setIcon(new ImageIcon(new ImageIcon("src/images/ddadang2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
		   mymoney.setText(Integer.toString(updatedAmount));
	   }
	   else if(updatedAmount<15000) {
		   half.removeMouseListener(halfpressed);
		   half.setIcon(new ImageIcon(new ImageIcon("src/images/half2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
		   ddadang.removeMouseListener(ddadangpressed);
		   ddadang.setIcon(new ImageIcon(new ImageIcon("src/images/ddadang2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
		   mymoney.setText(Integer.toString(updatedAmount));
	   }
	   else if(updatedAmount<20000) {
		   ddadang.removeMouseListener(ddadangpressed);
		   ddadang.setIcon(new ImageIcon(new ImageIcon("src/images/ddadnag2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
		   mymoney.setText(Integer.toString(updatedAmount));
	   }
	   else 
		   mymoney.setText(Integer.toString(updatedAmount));
   }
   
   public void reGame() {
	   previous="none";
	   amount=0;
	   count=0;
	   
	   myLeft.setX(200);
	   myLeft.setY(210);
	   myLeft.setCardBounds();
	   myLeft.getCard().setVisible(false);
	   myRight.setX(200);
	   myRight.setY(210);
	   myRight.setCardBounds();
	   myRight.getCard().setVisible(false);
	   otherLeft.setX(200);
	   otherLeft.setY(210);
	   otherLeft.setCardBounds();
	   otherLeft.getCard().setVisible(false);
	   otherRight.setX(200);
	   otherRight.setY(210);
	   otherRight.setCardBounds();
	   otherRight.getCard().setVisible(false);
	   
	   bbing.setIcon(new ImageIcon(new ImageIcon("src/images/bbing2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
	   half.setIcon(new ImageIcon(new ImageIcon("src/images/half2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
	   ddadang.setIcon(new ImageIcon(new ImageIcon("src/images/ddadang2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
	   die.setIcon(new ImageIcon(new ImageIcon("src/images/die2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
	   call.setIcon(new ImageIcon(new ImageIcon("src/images/call2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
	   
	   bbing.removeMouseListener(bbingpressed);
	   half.removeMouseListener(halfpressed);
	   ddadang.removeMouseListener(ddadangpressed);
	   die.removeMouseListener(diepressed);
	   call.removeMouseListener(callpressed);
	   
	   shuffle.setVisible(true);
   }
   
   public void battle() throws InterruptedException {
	   if(uID.equals("1"))
           result = new JokboMatch(new Jokbo(myLeft, myRight).calculateJokbo()+" "+new Jokbo(otherLeft, otherRight).calculateJokbo());
       else
           result = new JokboMatch(new Jokbo(otherLeft, otherRight).calculateJokbo()+" "+new Jokbo(myLeft, myRight).calculateJokbo());
       updatePanmoney(0);
       if(result.selectWinner().equals("A")) {
           if(uID.equals("1"))
              updateMymoney(property+=amount);
       	   showResult("1");
       }
       else if(result.selectWinner().equals("B")) {
           if(uID.equals("2"))
              updateMymoney(property+=amount);
           showResult("2");
       }
       else if(result.selectWinner().equals("무승부")) {
    	   if (uID.equals("1"))
    		   updateMymoney(property+=amount/2);
    	   else
    		   updateMymoney(property+=amount/2);
    	   showResult("무승부");
       }
       reGame();
   }
   
   public void showResult(String winner) throws InterruptedException {
	   if(winner.equals("1")) {
		   if(uID.equals("1")) {
			   panResult.setIcon(panWin);
			   panResult.setVisible(true);
			   Thread.sleep(2500);
			   panResult.setVisible(false);
		   }
		   else {
			   panResult.setIcon(panLose);
			   panResult.setVisible(true);
			   Thread.sleep(2500);
			   panResult.setVisible(false);
		   }   
	   }
	   else if (winner.equals("2")){
		   if(uID.equals("1")) {
			   panResult.setIcon(panLose);
			   panResult.setVisible(true);
			   Thread.sleep(2500);
			   panResult.setVisible(false);
		   }
		   else {
			   panResult.setIcon(panWin);
			   panResult.setVisible(true);
			   Thread.sleep(2500);
			   panResult.setVisible(false);
		   }   
	   }
	   else if (winner.equals("무승부")) {
		   panResult.setIcon(panDraw);
		   panResult.setVisible(true);
		   Thread.sleep(2500);
		   panResult.setVisible(false);
	   }
	   if(property <= 10000) {
		   ChatMsg end = new ChatMsg(UserName, "0", String.format("%s end", uID));
		   SendObject(end);
	   }
   }
   private void printMyEmotion(String emotion) throws InterruptedException {
	   switch(emotion) {
	   case "angry":
		   myEmotionIcon.setIcon(new ImageIcon("src/emotions/angry_icon.png"));
		   myEmotionIcon.setVisible(true);
		   myMessage.setVisible(true);
		   Thread.sleep(1000);
		   myMessage.setVisible(false);
		   myEmotionIcon.setVisible(false);
		   break;
	   case "sad":
		   myEmotionIcon.setIcon(new ImageIcon("src/emotions/sad_icon.png"));
		   myEmotionIcon.setVisible(true);
		   myMessage.setVisible(true);
		   Thread.sleep(1000);
		   myMessage.setVisible(false);
		   myEmotionIcon.setVisible(false);
		   break;
	   case "thank":
		   myEmotionIcon.setIcon(new ImageIcon("src/emotions/thank_icon.png"));
		   myEmotionIcon.setVisible(true);
		   myMessage.setVisible(true);
		   Thread.sleep(1000);
		   myMessage.setVisible(false);
		   myEmotionIcon.setVisible(false);
		   break;
	   case "sorry":
		   myEmotionIcon.setIcon(new ImageIcon("src/emotions/sorry_icon.png"));
		   myEmotionIcon.setVisible(true);
		   myMessage.setVisible(true);
		   Thread.sleep(1000);
		   myMessage.setVisible(false);
		   myEmotionIcon.setVisible(false);
		   break;
	   }
   }
   private void printOtherEmotion(String emotion) throws InterruptedException {
	   switch(emotion) {
	   case "angry":
		   otherEmotionIcon.setIcon(new ImageIcon("src/emotions/angry_icon.png"));
		   otherEmotionIcon.setVisible(true);
		   otherMessage.setVisible(true);
		   Thread.sleep(1000);
		   otherMessage.setVisible(false);
		   otherEmotionIcon.setVisible(false);
		   break;
	   case "sad":
		   otherEmotionIcon.setIcon(new ImageIcon("src/emotions/sad_icon.png"));
		   otherEmotionIcon.setVisible(true);
		   otherMessage.setVisible(true);
		   Thread.sleep(1000);
		   otherMessage.setVisible(false);
		   otherEmotionIcon.setVisible(false);
		   break;
	   case "thank":
		   otherEmotionIcon.setIcon(new ImageIcon("src/emotions/thank_icon.png"));
		   otherEmotionIcon.setVisible(true);
		   otherMessage.setVisible(true);
		   Thread.sleep(1000);
		   otherMessage.setVisible(false);
		   otherEmotionIcon.setVisible(false);
		   break;
	   case "sorry":
		   otherEmotionIcon.setIcon(new ImageIcon("src/emotions/sorry_icon.png"));
		   otherEmotionIcon.setVisible(true);
		   otherMessage.setVisible(true);
		   Thread.sleep(1000);
		   otherMessage.setVisible(false);
		   otherEmotionIcon.setVisible(false);
		   break;
	   }
   }
   private void printMyBetting(String betting) throws InterruptedException {
	   switch(betting) {
	   case "call":
		   myBettingIcon.setIcon(new ImageIcon("src/emotions/call_send.png"));
		   myBettingIcon.setVisible(true);
		   myMessage.setVisible(true);
		   Thread.sleep(1000);
		   myMessage.setVisible(false);
		   myBettingIcon.setVisible(false);
		   break;
	   case "bbing":
		   myBettingIcon.setIcon(new ImageIcon("src/emotions/bbing_send.png"));
		   myBettingIcon.setVisible(true);
		   myMessage.setVisible(true);
		   Thread.sleep(1000);
		   myMessage.setVisible(false);
		   myBettingIcon.setVisible(false);
		   break;
	   case "half":
		   myBettingIcon.setIcon(new ImageIcon("src/emotions/half_send.png"));
		   myBettingIcon.setVisible(true);
		   myMessage.setVisible(true);
		   Thread.sleep(1000);
		   myMessage.setVisible(false);
		   myBettingIcon.setVisible(false);
		   break;
	   case "ddadang":
		   myBettingIcon.setIcon(new ImageIcon("src/emotions/ddadang_send.png"));
		   myBettingIcon.setVisible(true);
		   myMessage.setVisible(true);
		   Thread.sleep(1000);
		   myMessage.setVisible(false);
		   myBettingIcon.setVisible(false);
		   break;
	   case "die":
		   myBettingIcon.setIcon(new ImageIcon("src/emotions/die_send.png"));
		   myBettingIcon.setVisible(true);
		   myMessage.setVisible(true);
		   Thread.sleep(1000);
		   myMessage.setVisible(false);
		   myBettingIcon.setVisible(false);
		   break;
	   }
   }
   private void printOtherBetting(String betting) throws InterruptedException {
	   switch(betting) {
	   case "call":
		   otherBettingIcon.setIcon(new ImageIcon("src/emotions/call_send.png"));
		   otherBettingIcon.setVisible(true);
		   otherMessage.setVisible(true);
		   Thread.sleep(1000);
		   otherMessage.setVisible(false);
		   otherBettingIcon.setVisible(false);
		   break;
	   case "bbing":
		   otherBettingIcon.setIcon(new ImageIcon("src/emotions/bbing_send.png"));
		   otherBettingIcon.setVisible(true);
		   otherMessage.setVisible(true);
		   Thread.sleep(1000);
		   otherMessage.setVisible(false);
		   otherBettingIcon.setVisible(false);
		   break;
	   case "half":
		   otherBettingIcon.setIcon(new ImageIcon("src/emotions/half_send.png"));
		   otherBettingIcon.setVisible(true);
		   otherMessage.setVisible(true);
		   Thread.sleep(1000);
		   otherMessage.setVisible(false);
		   otherBettingIcon.setVisible(false);
		   break;
	   case "ddadang":
		   otherBettingIcon.setIcon(new ImageIcon("src/emotions/ddadang_send.png"));
		   otherBettingIcon.setVisible(true);
		   otherMessage.setVisible(true);
		   Thread.sleep(1000);
		   otherMessage.setVisible(false);
		   otherBettingIcon.setVisible(false);
		   break;
	   case "die":
		   otherBettingIcon.setIcon(new ImageIcon("src/emotions/die_send.png"));
		   otherBettingIcon.setVisible(true);
		   otherMessage.setVisible(true);
		   Thread.sleep(1000);
		   otherMessage.setVisible(false);
		   otherBettingIcon.setVisible(false);
		   break;
	   }
   }
   private void gameEnd(String Loser) {
	   moneyBoard.setVisible(false);
	   mymoney.setVisible(false);
	   panmoney.setVisible(false);
	   sadBtn.setVisible(false);
	   angryBtn.setVisible(false);
	   thankBtn.setVisible(false);
	   sorryBtn.setVisible(false);
	   shuffle.setVisible(false);
	   call.setVisible(false);
	   bbing.setVisible(false);
	   ddadang.setVisible(false);
	   half.setVisible(false);
	   die.setVisible(false);
	   myJokbo.setVisible(false);
	   myJokboBg.setVisible(false);
	   durumari.setVisible(false);
	   myLeft.getCard().setVisible(false);
	   myRight.getCard().setVisible(false);
	   otherLeft.getCard().setVisible(false);
	   otherRight.getCard().setVisible(false);
	   if (uID.equals(Loser)) {
		   gameResult.setIcon(gameLose);
		   gameResult.setVisible(true);
	   }
	   else {
		   gameResult.setIcon(gameWin);
		   gameResult.setVisible(true);;
	   }
   }
}
