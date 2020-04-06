package distar.project.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileService {
	protected MultipartFile file;
	private int limitSize;
	private List<String> contentTypes;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public UploadFileService() {
		contentTypes = new ArrayList<String>();
	}

	public int getLimitSize() {
		return limitSize;
	}

	// byte
	public void setLimitSize(int limitSize) {
		this.limitSize = limitSize;
	}

	public List<String> getContentTypes() {
		return contentTypes;
	}

	public void setContentTypes(List<String> contentTypes) {
		this.contentTypes = contentTypes;
	}

	// image/jpeg,image/pjpeg
	public void setContent(String contentTypes) {
		StringTokenizer st = new StringTokenizer(contentTypes, ",");
		while (st.hasMoreTokens())
			this.contentTypes.add(st.nextToken());
	}

	protected void doUpload(String path, String fileName) throws Exception {

		StringBuffer img = new StringBuffer();
		img.append(path).append(File.separator).append(File.separator).append(
				fileName);

		//byte[] buf = new byte[1024];
		byte[] buf = new byte[2097152];
		File output = new File(img.toString());
		FileOutputStream fileOutputStream = new FileOutputStream(output);
		InputStream inputStream = file.getInputStream();
		while (true) {
			int count = inputStream.read(buf);
			if (count == -1)
				break;
			fileOutputStream.write(buf, 0, count);
		}
		fileOutputStream.flush();
		inputStream.close();
		fileOutputStream.close();
	}

}
