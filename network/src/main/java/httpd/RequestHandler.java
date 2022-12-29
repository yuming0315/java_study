package httpd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;

public class RequestHandler extends Thread {
	private static final String DOCUMENT_ROOT = "./webapp";
	private Socket socket;

	public RequestHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			// get IOStream
			OutputStream outputStream = socket.getOutputStream();
			// 파이프로 안 한 이유: 이미지도보내고 이것저것 보내야해서
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			consoleLog("connected from " + inetSocketAddress.getAddress().getHostAddress() + ":"
					+ inetSocketAddress.getPort());
			String request = null;
			while (true) {
				String line = br.readLine();
				// 브라우저 연결을 끊으면
				if (line == null) {
					break;
				}
				// simpleHttpServer는 요청의 헤더만 처리한다.
				if ("".equals(line)) {
					break;
				}

				// 요청 헤더의 첫번째 라인만 읽음
				if (request == null) {
					request = line;
					break;
				}
			}

//			consoleLog(request);

			String[] tokens = request.split(" ");
			consoleLog(request);
			if ("GET".equals(tokens[0])) {
				reponseStaticResource(outputStream, tokens[1], tokens[2]);
			} else {
				// methods: post,put,delete, head, connect
				// simpleHttpServer에서는 무시할거다 모든통신은 답장?를 넘겨줘야함 (400bad resquest)
				reponse400Error(outputStream, tokens[2]); // 구현하기 과제
			}

			// 예제 응답입니다.
			// 서버 시작과 테스트를 마친 후, 주석 처리 합니다.

		} catch (Exception ex) {
			consoleLog("error:" + ex);
		} finally {
			// clean-up
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}

			} catch (IOException ex) {
				consoleLog("error:" + ex);
			}
		}
	}

	private void reponse400Error(OutputStream outputStream, String protocol) throws IOException {
		// HTTP/1.1 404 bad request
		// Content-Type:....
		// \r\n
		// .....
		reponseStaticResource(outputStream,"/error/400.html",protocol);
	}

	private void reponseStaticResource(OutputStream outputStream, String url, String protocol) throws IOException {
		// default(welcome) file set
		if ("/".equals(url)) {
			url = "/index.html";
		}
		File file = new File(DOCUMENT_ROOT + url);
		if (!file.exists()) {
			reponse404Error(outputStream, protocol);
			return;
		}
		// nio
		byte[] body = Files.readAllBytes(file.toPath());
		String contentType = Files.probeContentType(file.toPath());

		// 응답
		outputStream.write((protocol + " 200 OK\r\n").getBytes("UTF-8"));
		outputStream.write(("Content-Type:" + contentType + "; charset=utf-8\r\n").getBytes("UTF-8"));
		outputStream.write("\r\n".getBytes());
		outputStream.write(body);
	}

	private void reponse404Error(OutputStream outputStream, String protocol) throws IOException {
		// HTTP/1.1 404 Not Found
		// Content-Type:....
		// \r\n
		// .....
		reponseStaticResource(outputStream,"/error/404.html",protocol);
	}

	public void consoleLog(String message) {
		System.out.println("[httpd#" + getId() + "] " + message);
	}
}
