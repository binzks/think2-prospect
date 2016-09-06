package org.think2framework.common;

import java.io.File;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.think2framework.common.exception.SimpleException;

/**
 * Created by zhoubin on 15/10/8. fastdfs附件上传工具
 */
public class FastDfsUtils {

	// private static TrackerClient trackerClient;
	// private static TrackerServer trackerServer;
	// private static StorageServer storageServer;
	private static StorageClient storageClient;

	static {
		try {
			String classPath = FastDfsUtils.class.getResource("/").getPath();// .getFile()).getCanonicalPath();
			String fdfsClientConfigFilePath = classPath + File.separator + "fdfs_client.conf";
			ClientGlobal.init(fdfsClientConfigFilePath);
			TrackerClient trackerClient = new TrackerClient();
			TrackerServer trackerServer = trackerClient.getConnection();
			storageClient = new StorageClient(trackerServer, null);
		} catch (Exception e) {
			throw new SimpleException(e);
		}
	}

	/**
	 * fastdfs附件上传
	 * 
	 * @param data
	 *            上传文件
	 * @param suffix
	 *            文件后缀
	 * @return 图片地址
	 */
	public static String upload(byte[] data, String suffix) {
		return upload(data, 0, 0, null, suffix);
	}

	/**
	 * fastdfs附件上传
	 *
	 * @param data
	 *            上传文件
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param author
	 *            作者
	 * @param suffix
	 *            文件后缀
	 * @return 文件路径
	 */
	public static String upload(byte[] data, int width, int height, String author, String suffix) {
		NameValuePair[] meta_list = null;
		if (width > 0 && height > 0) {
			meta_list = new NameValuePair[3];
			meta_list[0] = new NameValuePair("width", String.valueOf(width));
			meta_list[1] = new NameValuePair("height", String.valueOf(height));
			meta_list[2] = new NameValuePair("author", author);
		}
		String[] uploadResults;
		try {
			uploadResults = storageClient.upload_file(data, suffix, meta_list);
		} catch (Exception e) {
			throw new SimpleException(e);
		}
		if (uploadResults == null) {
			throw new SimpleException("upload file fail, error code: " + storageClient.getErrorCode());
		}
		String groupName = uploadResults[0];
		String remoteFileName = uploadResults[1];
		return groupName + "/" + remoteFileName;
	}

}
