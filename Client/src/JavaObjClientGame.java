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

public class JavaObjClientGame extends JFrame {
   private static final long serialVersionUID = 1L;
   private JPanel contentPane;
   private String UserName;
   private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
   private Socket socket; // 연결소켓
   private ObjectInputStream ois;
   private ObjectOutputStream oos;
   private JLabel lblUserName;
   private JTextPane textArea;
   
   /*우리가 만든 JavaObjectClientView 클래스 내 지역변수 선언하는 공간*/
   private String uID;
   private JLabel Background;
   private JButton shuffle;
   private JButton bbing;
   private JButton half;
   private JButton ddadang;
   private JButton call;
   private JLabel panmoney;
   private JLabel mymoney;
   private Image img;
   private Image updateimg;
   private JButton die;
   private String backSrc;
   private ImageIcon backIcon;
   private Card myLeft;
   private Card myRight;
   private Card otherLeft;
   private Card otherRight;
   private String array[];
   private Jokbo jokbo;
   private int count=0;
   private int amount=10000;
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
     	   if(uID.equals("1")) {
     		   msg = new ChatMsg(UserName, "4", "1 bbing");
     		   msg2 = new ChatMsg(UserName, "5", "1 bbing");
     	   }
     	   else {
      	 	   msg = new ChatMsg(UserName, "4", "2 bbing");
       		   msg2 = new ChatMsg(UserName, "5", "2 bbing");
     	   }
     	   updateMymoney(property-=10000);
           SendObject(msg);
           SendObject(msg2);
       }
   };
   
   private MouseAdapter halfpressed = new MouseAdapter() {
       @Override
       public void mouseClicked(MouseEvent e) {
     	   ChatMsg msg;
     	   ChatMsg msg2;
     	   if(uID.equals("1")) {
     	 	  msg = new ChatMsg(UserName, "4", "1 half");
     		  msg2 = new ChatMsg(UserName, "5", "1 half");
     	   } 
     	   else {
     		  msg = new ChatMsg(UserName, "4", "2 half");
     		  msg2 = new ChatMsg(UserName, "5", "2 half");
     	   }
     	   updateMymoney(property-=15000);
           SendObject(msg);
           SendObject(msg2);
       }
   };
   
   private MouseAdapter ddadangpressed = new MouseAdapter() {
       @Override
       public void mouseClicked(MouseEvent e) {
      	  ChatMsg msg;
      	  ChatMsg msg2;
      	  if(uID.equals("1")) {
      		 msg = new ChatMsg(UserName, "4", "1 ddadang");
      		 msg2 = new ChatMsg(UserName, "5", "1 ddadang");
      	  }
      		 
      	  else {
      		 msg = new ChatMsg(UserName, "4", "2 ddadang");
      		 msg2 = new ChatMsg(UserName, "5", "2 ddadang");
      	  }
      	  updateMymoney(property-=20000);
          SendObject(msg);
          SendObject(msg2);
      }
   };
   
   private MouseAdapter diepressed = new MouseAdapter() {
       @Override
       public void mouseClicked(MouseEvent e) {
       	   ChatMsg msg;
       	   ChatMsg msg2;
       	   if(uID.equals("1")) {
       	 	  msg = new ChatMsg(UserName, "4", "1 die");
       		  msg2 = new ChatMsg(UserName, "5", "1 die");
       	   }
       	   else {
       		  msg = new ChatMsg(UserName, "4", "2 die");
       		  msg2 = new ChatMsg(UserName, "5", "2 die");
       	   }
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
      backSrc = "src/images/background.jpg";
      backIcon = new ImageIcon(backSrc);
      /*게임 배경*/

      /*화투 패*/
      myLeft = new Card(200, 250);
      myLeft.setCardBounds();
      contentPane.add(myLeft.getCard());
          
      myRight = new Card(200, 250);
      myRight.setCardBounds();
      contentPane.add(myRight.getCard());
          
      otherLeft = new Card(200, 250);
      otherLeft.setCardBounds();
      contentPane.add(otherLeft.getCard());
          
      otherRight = new Card(200, 250);
      otherRight.setCardBounds();
      contentPane.add(otherRight.getCard());
      /*화투 패*/
      
      /*판돈*/
      panmoney = new JLabel(Integer.toString(amount));
      panmoney.setFont(new Font("한컴 말랑말랑 Bold", Font.PLAIN, 24));
      panmoney.setBounds(524, 324, 150, 50);
      contentPane.add(panmoney);
      /*판돈*/
      
      /*내돈*/  
      mymoney = new JLabel("100000");
      mymoney.setFont(new Font("한컴 말랑말랑 Bold", Font.PLAIN, 24));
      mymoney.setBounds(686, 324, 150, 50);
      contentPane.add(mymoney);
      /*내돈*/
      
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
      shuffle.setBorderPainted(false);
      shuffle.setContentAreaFilled(false);
      shuffle.setBounds(210, 250, 100, 100);
      contentPane.add(shuffle);
      /*시작 버튼*/
      
      /*배팅 버튼*/
      bbing= new JButton("삥");
      bbing.setBounds(524, 384, 150, 50);
      contentPane.add(bbing);
      
      half = new JButton("하프");
      half.setBounds(686, 384, 150, 50);
      contentPane.add(half);
      
      ddadang = new JButton("따당");
      ddadang.setBounds(524, 444, 150, 50);
      contentPane.add(ddadang);
      
      die = new JButton("다이");
      die.setBounds(686, 444, 150, 50);
      contentPane.add(die);
      
      call = new JButton("콜");
      call.setBounds(524, 505, 312, 50);
      contentPane.add(call);
      /*배팅 버튼*/
      
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setBounds(524, 10, 352, 237);
      contentPane.add(scrollPane);
      textArea = new JTextPane();
      textArea.setEditable(true);
      textArea.setFont(new Font("굴림체", Font.PLAIN, 14));
      scrollPane.setViewportView(textArea);

      lblUserName = new JLabel("Name");
      lblUserName.setBorder(new LineBorder(new Color(0, 0, 0)));
      lblUserName.setBackground(Color.WHITE);
      lblUserName.setFont(new Font("굴림", Font.BOLD, 14));
      lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
      lblUserName.setBounds(524, 256, 62, 40);
      contentPane.add(lblUserName);
      setVisible(true);

      AppendText("User " + username + " connecting " + ip_addr + " " + port_no);
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
      btnNewButton.setBounds(598, 256, 69, 40);
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
               case "200":
                  AppendText(msg);
                   break;
               case "1":
                  AppendText(msg);
                  array = cm.getData().split(" ");
                  shuffle.setVisible(false);
                  myLeft.backside();
                  myRight.backside();
                  otherLeft.backside();
                  otherRight.backside();
                  Thread.sleep(1500);
                 
                  Placing(uID);
                  
                  AppendText(String.format(
                 		 "myLeft : %s, myRight : %s, otherLeft : %s, otherRight : %s",
                 		 myLeft.getCardSrc().substring(10, 12), myRight.getCardSrc().substring(10, 12), otherLeft.getCardSrc().substring(10, 12), otherRight.getCardSrc().substring(10, 12)));
                    
                  myLeft.flip();
                  myRight.flip();
                  otherLeft.flip();
                  otherRight.flip();
                  
                  myLeft.getCard().addMouseListener(leftcardpressed);
                  myRight.getCard().addMouseListener(rightcardpressed);
                  
                  
                  break;
               case "2":
            	   AppendText(msg);
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
            	   AppendText(msg);
            	   if(uID.equals("1")) {
                	   ChatMsg chatmsg = new ChatMsg(UserName, "4", "배팅 시작!");
                       SendObject(chatmsg);
            	   }
            	   break;
               case "4":
            	   AppendText(msg);
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
            	   AppendText(msg);
            	   if(cm.getData().equals("1 bbing"))
            		   updatePanmoney(amount+=10000);
            	   else if(cm.getData().equals("2 bbing"))
            		   updatePanmoney(amount+=10000);
            	   else if(cm.getData().equals("1 half"))
            		   updatePanmoney(amount+=15000);
            	   else if(cm.getData().equals("2 half"))
        			   updatePanmoney(amount+=15000);
            	   else if(cm.getData().equals("1 ddadang"))
        			   updatePanmoney(amount+=20000);
            	   else if(cm.getData().equals("2 ddadang"))
        			   updatePanmoney(amount+=20000);
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
      int len = textArea.getDocument().getLength();
      // 끝으로 이동
      textArea.setCaretPosition(len);
      textArea.replaceSelection(msg + "\n");
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

   // Server에게 network으로 전송
   public void SendMessage(String msg) {
      try {
         ChatMsg obcm = new ChatMsg(UserName, "200", msg);
         oos.writeObject(obcm);
      } catch (IOException e) {
         AppendText("oos.writeObject() error");
         try {
            ois.close();
            oos.close();
            socket.close();
         } catch (IOException e1) {
            e1.printStackTrace();
            System.exit(0);
         }
      }
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
                while(myLeft.getX() > 100 || myLeft.getY() < 460 ) {
                   if (myLeft.getX() > 100) myLeft.setX(myLeft.getX() - 5);
                   if (myLeft.getY() < 460) myLeft.setY(myLeft.getY() + 12);
                   myLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             case 1 :
                myRight.setCardSrc(array[i]);
                myRight.setCardIcon(myRight.getCardSrc());
                while(myRight.getX() < 300 || myRight.getY() < 460 ) {
                   if (myRight.getX() < 300) myRight.setX(myRight.getX() + 5);
                   if (myRight.getY() < 460) myRight.setY(myRight.getY() + 12);
                   myRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             case 2 :
                otherLeft.backside();
                while(otherLeft.getX() > 100 || otherLeft.getY() > 10 ) {
                   if (otherLeft.getX() > 100) otherLeft.setX(otherLeft.getX() - 5);
                   if (otherLeft.getY() > 10) otherLeft.setY(otherLeft.getY() - 12);
                   otherLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             case 3 :
                otherRight.backside();
                while(otherRight.getX() < 300 || otherRight.getY() > 10 ) {
                   if (otherRight.getX() < 300) otherRight.setX(otherRight.getX() + 5);
                   if (otherRight.getY() > 10) otherRight.setY(otherRight.getY() - 12);
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
                otherLeft.backside();
                while(otherLeft.getX() > 100 || otherLeft.getY() > 10 ) {
                   if (otherLeft.getX() > 100) otherLeft.setX(otherLeft.getX() - 5);
                   if (otherLeft.getY() > 10) otherLeft.setY(otherLeft.getY() - 12);
                   otherLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             case 1 :
                otherRight.backside();
                while(otherRight.getX() < 300 || otherRight.getY() > 10 ) {
                   if (otherRight.getX() < 300) otherRight.setX(otherRight.getX() + 5);
                   if (otherRight.getY() > 10) otherRight.setY(otherRight.getY() - 12);
                   otherRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             case 2 :
                myLeft.setCardSrc(array[i]);
                myLeft.setCardIcon(myLeft.getCardSrc());
                while(myLeft.getX() > 100 || myLeft.getY() < 460 ) {
                   if (myLeft.getX() > 100) myLeft.setX(myLeft.getX() - 5);
                   if (myLeft.getY() < 460) myLeft.setY(myLeft.getY() + 12);
                   myLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
             case 3 :
                myRight.setCardSrc(array[i]);
                myRight.setCardIcon(myRight.getCardSrc());
                while(myRight.getX() < 300 || myRight.getY() < 460 ) {
                   if (myRight.getX() < 300) myRight.setX(myRight.getX() + 5);
                   if (myRight.getY() < 460) myRight.setY(myRight.getY() + 12);
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
                   myLeft.setY(myLeft.getY() - 12);
                   myLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
               }
			   Thread.sleep(1000);
			   while(myLeft.getY() < 460) {
                   myLeft.setY(myLeft.getY() + 12);
                   myLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
			   }
			   myLeft.getCard().removeMouseListener(leftcardpressed);
			   myRight.getCard().removeMouseListener(rightcardpressed);
		   }
		   else {
			   while(otherLeft.getY() < 160) {
				   otherLeft.setY(otherLeft.getY() + 12);
				   otherLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(otherLeft.getY() > 10) {
				   otherLeft.setY(otherLeft.getY() - 12);
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
                   myLeft.setY(myLeft.getY() - 12);
                   myLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(myLeft.getY() < 460) {
                   myLeft.setY(myLeft.getY() + 12);
                   myLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   myLeft.getCard().removeMouseListener(leftcardpressed);
			   myRight.getCard().removeMouseListener(rightcardpressed);
		   }
		   else {
			   while(otherLeft.getY() < 160) {
				   otherLeft.setY(otherLeft.getY() + 12);
				   otherLeft.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(otherLeft.getY() > 10) {
				   otherLeft.setY(otherLeft.getY() - 12);
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
                   myRight.setY(myRight.getY() - 12);
                   myRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(myRight.getY() < 460) {
                   myRight.setY(myRight.getY() + 12);
                   myRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
               }
			   myLeft.getCard().removeMouseListener(leftcardpressed);
			   myRight.getCard().removeMouseListener(rightcardpressed);
		   }
		   else {
			   while(otherRight.getY() < 160) {
				   otherRight.setY(otherRight.getY() + 12);
				   otherRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(otherRight.getY() > 10) {
				   otherRight.setY(otherRight.getY() - 12);
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
                   myRight.setY(myRight.getY() - 12);
                   myRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(myRight.getY() < 460) {
                   myRight.setY(myRight.getY() + 12);
                   myRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
               }
			   myLeft.getCard().removeMouseListener(leftcardpressed);
			   myRight.getCard().removeMouseListener(rightcardpressed);
		   }
		   else {
			   while(otherRight.getY() < 160) {
				   otherRight.setY(otherRight.getY() + 12);
				   otherRight.setCardBounds();
                   repaint();
                   Thread.sleep(10);
                }
			   Thread.sleep(1000);
			   while(otherRight.getY() > 10) {
				   otherRight.setY(otherRight.getY() - 12);
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
	   bbing.addMouseListener(bbingpressed);
	   half.addMouseListener(halfpressed);
	   ddadang.addMouseListener(ddadangpressed);
	   die.addMouseListener(diepressed);
   }
   
   public void waitbatting() {
	   bbing.removeMouseListener(bbingpressed);
	   half.removeMouseListener(halfpressed);
	   ddadang.removeMouseListener(ddadangpressed);
	   die.removeMouseListener(diepressed);
   }
   
   public void updatePanmoney(int updatedAmount) {
	   panmoney.setText(Integer.toString(updatedAmount));
   }
   
   public void updateMymoney(int updatedAmount) {
	   mymoney.setText(Integer.toString(updatedAmount));
   }
}