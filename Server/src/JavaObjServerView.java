import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JavaObjServerView extends JFrame {
   private static final long serialVersionUID = 1L;
   private JPanel contentPane;
   JTextArea textArea;
   private JTextField txtPortNumber;

   private ServerSocket socket; // 서버소켓
   private Socket client_socket; // accept() 에서 생성된 client 소켓
   private Vector UserVec = new Vector(); // 연결된 사용자를 저장할 벡터
   private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
   private Vector Room = new Vector();
   
   private int[] dupCheck = new int[4];
   private int cnt=0;
   
   private Random rand = new Random();
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               JavaObjServerView frame = new JavaObjServerView();
               frame.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   public JavaObjServerView() {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 338, 440);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);

      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setBounds(12, 10, 300, 298);
      contentPane.add(scrollPane);

      textArea = new JTextArea();
      textArea.setEditable(false);
      scrollPane.setViewportView(textArea);

      JLabel lblNewLabel = new JLabel("Port Number");
      lblNewLabel.setBounds(13, 318, 87, 26);
      contentPane.add(lblNewLabel);

      txtPortNumber = new JTextField();
      txtPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
      txtPortNumber.setText("30000");
      txtPortNumber.setBounds(112, 318, 199, 26);
      contentPane.add(txtPortNumber);
      txtPortNumber.setColumns(10);

      JButton btnServerStart = new JButton("Server Start");
      btnServerStart.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
               socket = new ServerSocket(Integer.parseInt(txtPortNumber.getText()));
            } catch (NumberFormatException | IOException e1) {
               e1.printStackTrace();
            }
            AppendText("Chat Server Running..");
            btnServerStart.setText("Chat Server Running..");
            btnServerStart.setEnabled(false); // 서버를 더이상 실행시키지 못 하게 막는다
            txtPortNumber.setEnabled(false); // 더이상 포트번호 수정못 하게 막는다
            AcceptServer accept_server = new AcceptServer();
            accept_server.start();
         }
      });
      btnServerStart.setBounds(12, 356, 300, 35);
      contentPane.add(btnServerStart);
   }

   // 새로운 참가자 accept() 하고 user thread를 새로 생성한다.
   class AcceptServer extends Thread {
      @SuppressWarnings("unchecked")
      public void run() {
         while (true) { // 사용자 접속을 계속해서 받기 위해 while문
            try {
               AppendText("Waiting new clients ...");
               client_socket = socket.accept(); // accept가 일어나기 전까지는 무한 대기중
               AppendText("새로운 참가자 from " + client_socket);
               // User 당 하나씩 Thread 생성
               UserService new_user = new UserService(client_socket);
               UserVec.add(new_user); // 새로운 참가자 배열에 추가
               new_user.start(); // 만든 객체의 스레드 실행
               AppendText("현재 참가자 수 " + UserVec.size());
               if (Room.size() >= 1) {
                  for (int i = 0; i < Room.size(); i++) {
                     RoomThread rt = (RoomThread)Room.get(i);
                     if (rt.getUserCount() == 0) {
                        System.out.println(String.format("%d room ended", rt.getServerPort()));
                        rt.interrupt();
                        if (Room.removeElement(rt));
                        AppendText(String.format("인터럽티드 룸 삭제 완료"));
                     }
                  }
               }
            } catch (IOException e) {
               AppendText("accept() error");
            }
         }
      }
   }

   public void AppendText(String str) { // server 화면에 출력
      textArea.append(str + "\n");
      textArea.setCaretPosition(textArea.getText().length());
   }

   public void AppendObject(ChatMsg msg) { // server 화면에 출력
      textArea.append("code = " + msg.getCode() + "\n");
      textArea.append("id = " + msg.getId() + "\n");
      textArea.append("data = " + msg.getData() + "\n");
      textArea.setCaretPosition(textArea.getText().length());
   }

   // User 당 생성되는 Thread
   // Read One 에서 대기 -> Write All
   class UserService extends Thread {
      private ObjectInputStream ois;
      private ObjectOutputStream oos;
      private Socket client_socket;
      private Vector user_vc;
      public String UserName = "";

      public UserService(Socket client_socket) {
         // 매개변수로 넘어온 자료 저장
         this.client_socket = client_socket;
         this.user_vc = UserVec;
         try {
            oos = new ObjectOutputStream(client_socket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(client_socket.getInputStream());
         } catch (Exception e) {
            AppendText("userService error");
         }
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
         }
         for (i = 0; i < bb.length; i++)
            packet[i] = bb[i];
         return packet;
      }

      public void Logout() {
         String msg = "[" + UserName + "]님이 퇴장 하였습니다.\n";
         UserVec.removeElement(this); // Logout한 현재 객체를 벡터에서 지운다
         AppendText("사용자 " + "[" + UserName + "] 퇴장. 현재 참가자 수 " + UserVec.size());
      }

      // 모든 User들에게 Object를 방송. 채팅 message와 image object를 보낼 수 있다
      public void WriteAllObject(Object ob) {
         for (int i = 0; i < user_vc.size(); i++) {
            UserService user = (UserService) user_vc.elementAt(i);
            user.WriteOneObject(ob);
         }
      }
      
      public void WriteOneObject(Object ob) {
         try {
            ChatMsg msg = (ChatMsg) ob;
             System.out.println(String.format("WriteOneObject %s", msg.getCode()));
             oos.writeObject(ob);
         } 
         catch (IOException e) {
            AppendText("oos.writeObject(ob) error");      
            try {
               ois.close();
               oos.close();
               client_socket.close();
               client_socket = null;
               ois = null;
               oos = null;            
            } catch (IOException e1) {
               e1.printStackTrace();
            }
            Logout();
         }
      }
      
      // UserService Thread가 담당하는 Client 에게 1:1 전송
      public void WriteOne(String msg) {
         try {
            ChatMsg obcm = new ChatMsg("SERVER", "200", msg);
            oos.writeObject(obcm);
         } catch (IOException e) {
            AppendText("dos.writeObject() error");
            try {
               ois.close();
               oos.close();
               client_socket.close();
               client_socket = null;
               ois = null;
               oos = null;
            } catch (IOException e1) {
               e1.printStackTrace();
            }
            Logout(); // 에러가난 현재 객체를 벡터에서 지운다
         }
      }

		// 나를 제외한 User들에게 방송. 각각의 UserService Thread의 WriteONe() 을 호출한다.
		public void WriteOthers(String str) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user != this)
					user.WriteOne(str);
			}
		}
		
		public void WriteOthersObject(Object ob) {
			ChatMsg msg = (ChatMsg)ob;
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user != this)
					user.WriteOneObject(msg);
			}
		}
      public void run() {
         while (true) { // 사용자 접속을 계속해서 받기 위해 while문
            try {
               Object obcm = null;
               String msg = null;
               ChatMsg cm = null;
               if (socket == null)
                  break;
               try {
                  obcm = ois.readObject();
               } catch (ClassNotFoundException e) {
                  e.printStackTrace();
                  return;
               }
               if (obcm == null)
                  break;
               if (obcm instanceof ChatMsg) {
                  cm = (ChatMsg) obcm;
                  AppendObject(cm);
               } else
                  continue;
               if (cm.getCode().matches("100")) {
                  UserName = cm.getId();
                  cm.setData(Integer.toString(user_vc.size()));
                  msg = String.format("[%s] %s", cm.getId(),cm.getData());
                  AppendText(msg); // server 화면에 출력
                  WriteOneObject(cm);
               } else if (cm.getCode().matches("200")) {
                  msg = String.format("[%s] %s", cm.getId(), cm.getData());
                  AppendText(msg); // server 화면에 출력
                  WriteAllObject(cm);
               } else if (cm.getCode().matches("400")) { // logout message 처리
                  Logout();
                  break;
               } else if (cm.getCode().matches("1")) {
                  for (int i = 0; i < 4; i++) {
                     dupCheck[i] = rand.nextInt(20);
                     for (int j = 0; j < i; j++) {
                        if (dupCheck[i] == dupCheck[j]) {
                           i--;
                        }
                     }
                  }
                  cm.setData(
                        String.format("%02d %02d %02d %02d",
                           dupCheck[0], dupCheck[1], dupCheck[2], dupCheck[3]
                        )
                  );
                  msg = String.format("[%s] %s", cm.getId(), cm.getData());
                  AppendText(msg);
                  WriteAllObject(cm);
               } else if(cm.getCode().matches("2")) {
                  cm.setData(cm.getData());
                  msg = String.format("%s", cm.getData());
                  AppendText(msg);
                  WriteAllObject(cm);
               } else if(cm.getCode().matches("3")) {
                  cm.setData(cm.getData());
                  msg = String.format("%s", cm.getData());
                  AppendText(msg); // server 화면에 출력
                  WriteAllObject(cm);
               } else if(cm.getCode().matches("4")) {
                  cm.setData(cm.getData());
                  msg = String.format("%s", cm.getData());
                  AppendText(msg); // server 화면에 출력
                  WriteAllObject(cm);
               } else if(cm.getCode().matches("5")) {
                  cm.setData(cm.getData());
                  msg = String.format("%s", cm.getData());
                  AppendText(msg); // server 화면에 출력
                  WriteAllObject(cm);
               } else if (cm.getCode().matches("101")){ // 로비 입장 프로토콜
                  cnt = 0;
                  System.out.println("101 Lobby Enter Protocol");
                  System.out.println(Room.size());
                  cm.setData(Integer.toString(Room.size()));
                  AppendText(msg);
                  WriteOneObject(cm);
               } else if (cm.getCode().matches("102")) { // 클라이언트 방 입장 요청 프로토콜
                  String port_number = cm.getData();
                  String roomAccess = "";
                  for (int i = 0; i < Room.size(); i++) {
                     RoomThread rt = (RoomThread)Room.get(i);
                     if (port_number.equals(Integer.toString(rt.getServerPort()))) {
                        if (rt.getUserCount() == 1) roomAccess = "true";
                        else if (rt.getUserCount() == 0) roomAccess = "empty";
                        else if (rt.getUserCount() >= 2) roomAccess = "full";
                        else roomAccess = "Error";
                     }
                  }
                  cm.setData(String.format("%s %s", port_number, roomAccess));
                  WriteOneObject(cm);
               } else if (cm.getCode().matches("103")) {
                  RoomThread rt = (RoomThread)Room.get(cnt);
                  System.out.println(cnt);
                  System.out.println(Room.size());
                  if (cnt == (Room.size() - 1)) 
                     cm.setData(String.format("%d last", rt.getServerPort()));
                  else
                     cm.setData(String.format("%d yet",  rt.getServerPort()));
                  WriteOneObject(cm);
                  cnt++;
               } else if (cm.getCode().matches("999")) { // 방 생성 프로토콜
                  msg = String.format("%s",  cm.getData());
                  AppendText(msg);
                  try {
                     int thisPortNumber = Integer.parseInt(txtPortNumber.getText());
                     int emptyPortNumber = 0;
                     if (Room.size() > 0) {
                        RoomThread lastRoom = (RoomThread)Room.get(Room.size()-1);
                        emptyPortNumber = lastRoom.getServerPort()+1;
                     }
                     else emptyPortNumber = thisPortNumber + 1;
                     msg = String.format("%d", emptyPortNumber);
                     ServerSocket newRoomSocket = new ServerSocket(Integer.parseInt(msg));
                     cm.setData(msg);
                     AppendText(cm.getData());
                     WriteOneObject(cm);
                     Room.add(new RoomThread(newRoomSocket, client_socket));
                  } catch (NumberFormatException | IOException e1) {
                     e1.printStackTrace();
                  }
               } else if (cm.getCode().matches("998")) {
                  msg = String.format("%s",  cm.getData());
                  AppendText(msg);
                  cm.setData(Integer.toString(Room.size()));
                  WriteOneObject(cm);
               }
            } catch (IOException e) {
               AppendText("ois.readObject() error");
               try {
                  ois.close();
                  oos.close();
                  client_socket.close();
                  Logout(); // 에러가 난 현재 객체를 벡터에서 지운다
                  break;
               } catch (Exception ee) {
                  break;
               } // catch문 끝
            } // 바깥 catch문끝
         } // while
      } // run
   }
}