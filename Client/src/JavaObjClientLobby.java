import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Panel;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.JList;
//JavaObjClientMain 에서 넘어와 로비 화면 띄우는 코드

public class JavaObjClientLobby extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtInput;
	private String UserName;
	private JButton btnSend;
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	private Socket socket; // 연결소켓
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private JLabel lblUserName;
	private JTextPane textArea;
	
	private String uID;
	private String backSrc;
	private ImageIcon backIcon;
	
	private JScrollPane lobbyPane;
	DefaultListModel model;
	private JList lobbyList;
	private String username;
	private String ip_addr;
	private String port_no;
	private Vector room;
	int currentRoomSize;
    Object obcm = null;
    String msg = null;
    ChatMsg cm;
	
	public JavaObjClientLobby(String username, String ip_addr, String port_no) {
	  this.username = username;
	  this.ip_addr = ip_addr;
	  this.port_no = port_no;
      contentPane = new JPanel();
      setBounds(100, 100, 900, 630);
      contentPane.setBackground(new Color(255, 255, 255));
      contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
      setContentPane(contentPane);
      contentPane.setLayout(null);

      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setBounds(494, 10, 382, 471);
      contentPane.add(scrollPane);
      textArea = new JTextPane();
      textArea.setEditable(true);
      textArea.setFont(new Font("굴림체", Font.PLAIN, 14));
      scrollPane.setViewportView(textArea);

      txtInput = new JTextField();
      txtInput.setBounds(586, 491, 209, 40);
      contentPane.add(txtInput);
      txtInput.setColumns(10);

      btnSend = new JButton("Send");
      btnSend.setFont(new Font("굴림", Font.PLAIN, 14));
      btnSend.setBounds(807, 491, 69, 40);
      contentPane.add(btnSend);

      lblUserName = new JLabel("Name");
      lblUserName.setBorder(new LineBorder(new Color(0, 0, 0)));
      lblUserName.setBackground(Color.WHITE);
      lblUserName.setFont(new Font("굴림", Font.BOLD, 14));
      lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
      lblUserName.setBounds(492, 545, 94, 40);
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
      btnNewButton.setBounds(807, 545, 69, 40);
      contentPane.add(btnNewButton);
      backSrc = "src/images/background.jpg";
      backIcon = new ImageIcon(backSrc);
      
      /* 로비 스크롤팬 내용 */
      lobbyPane = new JScrollPane();
      lobbyPane.setBorder(new LineBorder(new Color(130, 135, 144), 0));
      lobbyPane.setBounds(12, 10, 456, 555);
      contentPane.add(lobbyPane);
      
      room = new Vector();
      
      lobbyList = new JList();
      lobbyPane.setViewportView(lobbyList);
      lobbyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      lobbyList.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
    		 String port_number = lobbyList.getSelectedValue().toString();
    		 AppendText(port_number);
    		 ChatMsg msg = new ChatMsg(UserName, "102", port_number);
    		 SendObject(msg);
    	 }
      });
      
      /* 방 생성 버튼*/ 
      JButton createRoomBtn = new JButton("\uBC29 \uC0DD\uC131");
      createRoomBtn.addMouseListener(new MouseAdapter() {
      	@Override
      	public void mouseClicked(MouseEvent e) {
      		ChatMsg msg = new ChatMsg(UserName, "999", "방 만들기 버튼 클릭");
      		AppendText(msg.getData());
      		SendObject(msg);
      }});
      createRoomBtn.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      	}
      });
      createRoomBtn.setBounds(492, 491, 87, 40);
      contentPane.add(createRoomBtn);
 
      try {
         socket = new Socket(ip_addr, Integer.parseInt(port_no));

         oos = new ObjectOutputStream(socket.getOutputStream());
         oos.flush();
         ois = new ObjectInputStream(socket.getInputStream());

         ChatMsg obcm = new ChatMsg(UserName, "101", "Lobby");
         SendObject(obcm);
         
         ListenNetwork net = new ListenNetwork();
         net.start();
         TextSendAction action = new TextSendAction();
         btnSend.addActionListener(action);
         txtInput.addActionListener(action);
         txtInput.requestFocus();
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
	               case "101": // 방 생성 프로토콜
	            	  currentRoomSize = Integer.parseInt(cm.getData());
	            	  for (int i = 0; i < currentRoomSize; i++) {
	            		  int roomNumber = Integer.parseInt(port_no) + (i+1);
	            		  room.addElement(Integer.toString(roomNumber));
	            	  }
	            	  lobbyList.setListData(room);
	            	  break;
	               case "102": // 방 입장 프로토콜
	            	   String[] code = cm.getData().split(" ");
	            	   AppendText(msg);
	            	  if (code[1].equals("true")) {	
	            		  setVisible(false);
	            		  JavaObjClientGame game = new JavaObjClientGame(username, ip_addr, code[0]);
	            	  }
	            	  else if (code[1].equals("full")) AppendText("입장 실패 : 최대 인원수");
	            	  else AppendText("입장 실패");
	            	  break;
	               case "200":
	                  AppendText(msg);
	                  break;
	               case "999":
	            	   AppendText(msg);
	            	   setVisible(false);
	            	   JavaObjClientGame game = new JavaObjClientGame(username, ip_addr, cm.getData());
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
        }
     }
  }
	   // keyboard enter key 치면 서버로 전송
   class TextSendAction implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         // Send button을 누르거나 메시지 입력하고 Enter key 치면
         if (e.getSource() == btnSend || e.getSource() == txtInput) {
            String msg = null;
            msg = txtInput.getText();
            SendMessage(msg);
            txtInput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
            txtInput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
            if (msg.contains("/exit")) // 종료 처리
               System.exit(0);
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
}	   

