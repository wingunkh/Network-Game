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
   private String previous = "none";
   private Image winimg = new ImageIcon("src/images/win.png").getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
   private Image loseimg = new ImageIcon("src/images/lose.png").getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
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
//     	   if(uID.equals("1")) {
//     		   msg = new ChatMsg(UserName, "4", "1 bbing");
//     		   msg2 = new ChatMsg(UserName, "5", "1 bbing");
//     	   }
//     	   else {
//      	 	   msg = new ChatMsg(UserName, "4", "2 bbing");
//       		   msg2 = new ChatMsg(UserName, "5", "2 bbing");
//     	   }
     	   updateMymoney(property-=10000);
           SendObject(msg);
           SendObject(msg2);
           SendObject(msg3);
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
           SendObject(msg);
           SendObject(msg2);
           SendObject(msg3);
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
          SendObject(msg);
          SendObject(msg2);
          SendObject(msg3);
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
           SendObject(msg);
           SendObject(msg2);
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
           SendObject(msg);
           SendObject(msg2);
       }
   };
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
      backSrc = "src/images/background.png";
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
				msg = new ChatMsg(UserName, "6", "1 angry");
			else 
				msg = new ChatMsg(UserName, "6", "2 angry");
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
  				msg = new ChatMsg(UserName, "6", "1 sorry");
  			else 
  				msg = new ChatMsg(UserName, "6", "2 sorry");
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
  				msg = new ChatMsg(UserName, "6", "1 thank");
  			else 
  				msg = new ChatMsg(UserName, "6", "2 thank");
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
  				msg = new ChatMsg(UserName, "6", "1 sad");
  			else 
  				msg = new ChatMsg(UserName, "6", "2 sad");
  			SendObject(msg);
      	 }
      });
      myEmotionIcon = new JLabel("myEmotionIcon");
      myEmotionIcon.setLocation(205, 365);
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
      myBettingIcon.setLocation(192, 360);
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
      panmoney = new JLabel(Integer.toString(amount));
      panmoney.setFont(new Font("한컴 말랑말랑 Bold", Font.PLAIN, 24));
      panmoney.setBounds(114, 235, 150, 50);
      contentPane.add(panmoney);
      /*판돈*/
      
      /*내돈*/  
      mymoney = new JLabel("100000");
      mymoney.setFont(new Font("한컴 말랑말랑 Bold", Font.PLAIN, 24));
      mymoney.setBounds(524, 131, 150, 50);
      contentPane.add(mymoney);
      /*내돈*/
      
      /*말풍선*/
      myMessage = new JLabel("");
      myMessage.setBounds(180, 350, 90, 80);
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
      img = new ImageIcon("src/images/start.png").getImage();
      updateimg = img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
      shuffle = new JButton(new ImageIcon(updateimg));
      shuffle.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
        	  ChatMsg msg = new ChatMsg(UserName, "1", "덱 분배");
              SendObject(msg);
          }
       });
      /* 배팅 버튼 */
      half = new JLabel(new ImageIcon(new ImageIcon("src/images/half2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
      half.setBounds(686, 384, 130, 50);
      contentPane.add(half);
      
      ddadang = new JLabel(new ImageIcon(new ImageIcon("src/images/ddadang2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
      ddadang.setBounds(524, 444, 130, 50);
      contentPane.add(ddadang);
      
      die = new JLabel(new ImageIcon(new ImageIcon("src/images/die2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
      die.setBounds(686, 444, 130, 50);
      contentPane.add(die);
      call = new JLabel(new ImageIcon(new ImageIcon("src/images/call2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
      call.setBounds(524, 504, 130, 50);
      contentPane.add(call);
      
      bbing= new JLabel(new ImageIcon(new ImageIcon("src/images/bbing2.png").getImage().getScaledInstance(130, 50,java.awt.Image.SCALE_SMOOTH)));
      bbing.setBounds(524, 384, 130, 50);
      contentPane.add(bbing);
      shuffle.setBorderPainted(false);
      shuffle.setContentAreaFilled(false);
      shuffle.setBounds(210, 210, 100, 100);
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
      lblUserName.setBounds(524, 95, 62, 40);
      contentPane.add(lblUserName);
      setVisible(true);

      UserName = username;
      lblUserName.setText(username);
      
      JButton btnNewButton = new JButton("종 료");
      btnNewButton.setFont(new Font("굴림", Font.PLAIN, 14));
      btnNewButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ChatMsg msg = new ChatMsg(UserName, "400", "Bye");
            SendObject(msg);
            System.exit(0);
         }
      });
      btnNewButton.setBounds(807, 10, 69, 40);
      contentPane.add(btnNewButton);
      Background = new JLabel("");
      Background.setIcon(backIcon);
      Background.setBounds(0, 0, 900, 595);
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
            		   if(uID.equals("1"))
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
            		   updatePanmoney(0);
            		   showResult("2");
            		   if(uID.equals("2"))
            			   updateMymoney(property+=amount);
            		   reGame();
            	   }
            	   else if(cm.getData().equals("2 die")) {
            		   updatePanmoney(0);
            		   showResult("1");
            		   if(uID.equals("1"))
            			   updateMymoney(property+=amount);
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
                while(myLeft.getX() > 100 || myLeft.getY() < 410 ) {
                   if (myLeft.getX() > 100) myLeft.setX(myLeft.getX() - 5);
                   if (myLeft.getY() < 410) myLeft.setY(myLeft.getY() + 10);
                   myLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             case 1 :
                myRight.setCardSrc(array[i]);
                myRight.setCardIcon(myRight.getCardSrc());
                while(myRight.getX() < 300 || myRight.getY() < 410 ) {
                   if (myRight.getX() < 300) myRight.setX(myRight.getX() + 5);
                   if (myRight.getY() < 410) myRight.setY(myRight.getY() + 10);
                   myRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             case 2 :
            	otherLeft.setCardSrc(array[i]);
                while(otherLeft.getX() > 100 || otherLeft.getY() > 10 ) {
                   if (otherLeft.getX() > 100) otherLeft.setX(otherLeft.getX() - 5);
                   if (otherLeft.getY() > 10) otherLeft.setY(otherLeft.getY() - 10);
                   otherLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             case 3 :
            	otherRight.setCardSrc(array[i]);
                while(otherRight.getX() < 300 || otherRight.getY() > 10 ) {
                   if (otherRight.getX() < 300) otherRight.setX(otherRight.getX() + 5);
                   if (otherRight.getY() > 10) otherRight.setY(otherRight.getY() - 10);
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
                while(otherLeft.getX() > 100 || otherLeft.getY() > 10 ) {
                   if (otherLeft.getX() > 100) otherLeft.setX(otherLeft.getX() - 5);
                   if (otherLeft.getY() > 10) otherLeft.setY(otherLeft.getY() - 10);
                   otherLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             case 1 :
            	otherRight.setCardSrc(array[i]);
                while(otherRight.getX() < 300 || otherRight.getY() > 10 ) {
                   if (otherRight.getX() < 300) otherRight.setX(otherRight.getX() + 5);
                   if (otherRight.getY() > 10) otherRight.setY(otherRight.getY() - 10);
                   otherRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             case 2 :
                myLeft.setCardSrc(array[i]);
                myLeft.setCardIcon(myLeft.getCardSrc());
                while(myLeft.getX() > 100 || myLeft.getY() < 410 ) {
                   if (myLeft.getX() > 100) myLeft.setX(myLeft.getX() - 5);
                   if (myLeft.getY() < 410) myLeft.setY(myLeft.getY() + 10);
                   myLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             case 3 :
                myRight.setCardSrc(array[i]);
                myRight.setCardIcon(myRight.getCardSrc());
                while(myRight.getX() < 300 || myRight.getY() < 410 ) {
                   if (myRight.getX() < 300) myRight.setX(myRight.getX() + 5);
                   if (myRight.getY() < 410) myRight.setY(myRight.getY() + 10);
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
                   myLeft.setY(myLeft.getY() - 11);
                   myLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
               }
			   Thread.sleep(1000);
			   while(myLeft.getY() < 410) {
                   myLeft.setY(myLeft.getY() + 11);
                   myLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
			   }
			   myLeft.getCard().removeMouseListener(leftcardpressed);
			   myRight.getCard().removeMouseListener(rightcardpressed);
		   }
		   else {
			   while(otherLeft.getY() < 120) {
				   otherLeft.setY(otherLeft.getY() + 11);
				   otherLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(otherLeft.getY() > 10) {
				   otherLeft.setY(otherLeft.getY() - 11);
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
                   myLeft.setY(myLeft.getY() - 11);
                   myLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(myLeft.getY() < 410) {
                   myLeft.setY(myLeft.getY() + 11);
                   myLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   myLeft.getCard().removeMouseListener(leftcardpressed);
			   myRight.getCard().removeMouseListener(rightcardpressed);
		   }
		   else {
			   while(otherLeft.getY() < 120) {
				   otherLeft.setY(otherLeft.getY() + 11);
				   otherLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(otherLeft.getY() > 10) {
				   otherLeft.setY(otherLeft.getY() - 11);
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
                   myRight.setY(myRight.getY() - 11);
                   myRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(myRight.getY() < 410) {
                   myRight.setY(myRight.getY() + 11);
                   myRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
               }
			   myLeft.getCard().removeMouseListener(leftcardpressed);
			   myRight.getCard().removeMouseListener(rightcardpressed);
		   }
		   else {
			   while(otherRight.getY() < 120) {
				   otherRight.setY(otherRight.getY() + 11);
				   otherRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(otherRight.getY() > 10) {
				   otherRight.setY(otherRight.getY() - 11);
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
                   myRight.setY(myRight.getY() - 11);
                   myRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(myRight.getY() < 410) {
                   myRight.setY(myRight.getY() + 11);
                   myRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
               }
			   myLeft.getCard().removeMouseListener(leftcardpressed);
			   myRight.getCard().removeMouseListener(rightcardpressed);
		   }
		   else {
			   while(otherRight.getY() < 120) {
				   otherRight.setY(otherRight.getY() + 11);
				   otherRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(otherRight.getY() > 10) {
				   otherRight.setY(otherRight.getY() - 11);
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
	   if(property==0) {
		   try {
			Thread.sleep(1500);
			System.exit(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	   }
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
    	   showResult("1");
           if(uID.equals("1"))
              updateMymoney(property+=amount);
       }
       else if(result.selectWinner().equals("B")) {
           showResult("2");
           if(uID.equals("2"))
              updateMymoney(property+=amount);
       }
       reGame();
   }
   
   public void showResult(String winner) throws InterruptedException {
	   if(winner.equals("1")) {
		   if(uID.equals("1")) {
			   myMessage.setIcon(new ImageIcon(winimg));
			   otherMessage.setIcon(new ImageIcon(loseimg));
			   myMessage.setVisible(true);
			   otherMessage.setVisible(true);
			   Thread.sleep(2500);
			   myMessage.setVisible(false);
			   otherMessage.setVisible(false);  
		   }
		   else {
			   myMessage.setIcon(new ImageIcon(loseimg));
			   otherMessage.setIcon(new ImageIcon(winimg));
			   myMessage.setVisible(true);
			   otherMessage.setVisible(true);
			   Thread.sleep(2500);
			   myMessage.setVisible(false);
			   otherMessage.setVisible(false);  
		   }   
	   }
	   else {
		   if(uID.equals("1")) {
			   myMessage.setIcon(new ImageIcon(loseimg));
			   otherMessage.setIcon(new ImageIcon(winimg));
			   myMessage.setVisible(true);
			   otherMessage.setVisible(true);
			   Thread.sleep(2500);
			   myMessage.setVisible(false);
			   otherMessage.setVisible(false);  
		   }
		   else {
			   myMessage.setIcon(new ImageIcon(winimg));
			   otherMessage.setIcon(new ImageIcon(loseimg));
			   myMessage.setVisible(true);
			   otherMessage.setVisible(true);
			   Thread.sleep(2500);
			   myMessage.setVisible(false);
			   otherMessage.setVisible(false);  
		   }   
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
   
}
