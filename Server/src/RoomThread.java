import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.Random;

public class RoomThread extends Thread {
	private static final long serialVersionUID = 1L;

	private ServerSocket serverSocket; // 서버소켓
	private Socket socket; // accept() 에서 생성된 client 소켓
	private Vector UserVec = new Vector(); // 연결된 사용자를 저장할 벡터
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	private String creator;
	private int[] dupCheck = new int[4];

	public int getUserCount() {
		return UserVec.size();
	}

	public int getServerPort() {
		return this.serverSocket.getLocalPort();
	}

	public String getCreator() {
		return this.creator;
	}

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

	public RoomThread(ServerSocket serverSocket, Socket socket, String creator) {
		super();
		this.serverSocket = serverSocket;
		this.socket = socket;
		this.creator = creator;
		AcceptServer accept_server = new AcceptServer();
		accept_server.start();
	}

	// 새로운 참가자 accept() 하고 user thread를 새로 생성한다.
	class AcceptServer extends Thread {
		public void run() {
			while (true) { // 사용자 접속을 계속해서 받기 위해 while문
				try {
					socket = serverSocket.accept(); // accept가 일어나기 전까지는 무한 대기중
					UserService new_user = new UserService(socket);
					UserVec.add(new_user); // 새로운 참가자 배열에 추가
					new_user.start(); // 만든 객체의 스레드 실행
				} catch (IOException e) {
				}
			}
		}
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
			WriteAllObject(msg); // 나를 제외한 다른 User들에게 전송
			if (UserVec.size() == 0) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
				oos.writeObject(ob);
			} catch (IOException e) {
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
					} else
						continue;
					if (cm.getCode().matches("100")) {
						UserName = cm.getId();
						cm.setData(Integer.toString(user_vc.size()));
						msg = String.format("[%s] %s", cm.getId(), cm.getData());
						WriteOneObject(cm);
						if (UserVec.size() == 2) {
							cm.setCode("104");
							WriteAllObject(cm);
						}
					} else if (cm.getCode().matches("400")) { // logout message 처리
						Logout();
						break;
					} else if (cm.getCode().matches("1")) {
						for (int i = 0; i < 4; i++) {
							dupCheck[i] = rand.nextInt(20);
							for (int j = 0; j < i; j++) {
								if (dupCheck[i] == dupCheck[j])
									i--;
							}
						}
						cm.setData(String.format("%02d %02d %02d %02d", dupCheck[0], dupCheck[1], dupCheck[2],
								dupCheck[3]));
						msg = String.format("[%s] %s", cm.getId(), cm.getData());
						WriteAllObject(cm);
					} else if (cm.getCode().matches("2")) {
						cm.setData(cm.getData());
						msg = String.format("%s", cm.getData());
						WriteAllObject(cm);
					} else if (cm.getCode().matches("3")) {
						cm.setData(cm.getData());
						msg = String.format("%s", cm.getData());
						WriteAllObject(cm);
					} else if (cm.getCode().matches("4")) {
						cm.setData(cm.getData());
						msg = String.format("%s", cm.getData());
						WriteAllObject(cm);
					} else if (cm.getCode().matches("5")) {
						cm.setData(cm.getData());
						msg = String.format("%s", cm.getData());
						WriteAllObject(cm);
					} else if (cm.getCode().matches("6")) {
						cm.setData(cm.getData());
						msg = String.format("%s", cm.getData());
						WriteAllObject(cm);
					} else if (cm.getCode().matches("7")) {
						cm.setData(cm.getData());
						msg = String.format("%s", cm.getData());
						WriteAllObject(cm);
					} else if (cm.getCode().matches("0")) {
						cm.setData(cm.getData());
						msg = String.format("%s", cm.getData());
						WriteAllObject(cm);
					}
				} catch (IOException e) {
					try {
						ois.close();
						oos.close();
						client_socket.close();
						Logout(); // 에러가 난 현재 객체를 벡터에서 지운다
						break;
					} catch (Exception ee) {
						break;
					}
				}
			}
		}
	}
}