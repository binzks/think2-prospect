package org.think2framework.wap.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.think2framework.context.ConstantFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by zhoubin on 16/8/23. 自行车控制器
 */
public class BikeController extends BaseController {

	private static Logger logger = LogManager.getLogger(BikeController.class); // log4j日志对象

	public static void run() {
		try {
			String msg = "<?xml version=\"1.0\" encoding=\"gb2312\"?>\n<REQUEST><TYPE>Subscribe</TYPE></REQUEST>";
			Socket socket = new Socket("58.211.65.178", 11112);
			System.out.println("连接服务器：" + socket.getInetAddress() + ":" + socket.getPort());
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			byte[] buf = new byte[1024];
			int length = 1024;
			os.write(msg.getBytes());
			os.flush();
			String data = "";
			while (length == 1024) {
				System.out.println("start");
				length = is.read(buf);
				System.out.println("read" + length);
				if (length > 0) {
					data += new String(buf, 0, length);
					// System.out.println("客户收到：" + data);
				}
				Thread.sleep(500);
			}
			System.out.println("客户收到：" + data);
		} catch (Exception ex) {
			System.out.println("socket建立连接失败," + ex.getMessage());
		}
	}

	public static void main(String[] args) {
		run();
	}
}
