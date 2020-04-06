package distar.project.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileHolder {
	public static final String NAME = "uploadFileHolder";
	private ParamImage paramImage;
	private int limitSize;
	private List<String> contentTypes;
	private MultipartFile file;

	public UploadFileHolder() {
		contentTypes = new ArrayList<String>();
	}

	public ParamImage getParamImage() {
		return paramImage;
	}

	public void setParamImage(ParamImage paramImage) {
		this.paramImage = paramImage;
	}

	// 100000
	public void setLimitSize(int limitSize) {
		this.limitSize = limitSize;
	}

	// image/jpeg,image/pjpeg
	public void setContent(String contentTypes) {
		StringTokenizer st = new StringTokenizer(contentTypes, ",");
		while (st.hasMoreTokens())
			this.contentTypes.add(st.nextToken());
	}

	public void setContentTypes(List<String> contentTypes) {
		this.contentTypes = contentTypes;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getError() {
		if (file.getSize() > limitSize)
			return "error size";

		String contextType = file.getContentType();
		if (!contentTypes.contains(contextType))
			return "error content type";
		return null;
	}

	public void doUpload(String dir, String fileName) throws Exception {
		StringBuffer img = new StringBuffer();
		img.append(paramImage.getPath()).append(File.separator);
		img.append(dir).append(File.separator).append(fileName);

		byte[] buf = new byte[1024];
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

	public void doUploadStu(Long schoolId, String fileName) throws Exception {
		StringBuffer dir = new StringBuffer();
		dir.append(schoolId).append(File.separator);
		dir.append(paramImage.getStuDir());
		doUpload(dir.toString(), fileName);
	}

	public void doUploadEmp(Long schoolId, String fileName) throws Exception {
		StringBuffer dir = new StringBuffer();
		dir.append(schoolId).append(File.separator);
		dir.append(paramImage.getEmpDir());
		doUpload(dir.toString(), fileName);
	}
	
	public void doUploadVoting(String fileName) throws Exception {
		StringBuffer dir = new StringBuffer();
		dir.append(File.separator);
		dir.append(paramImage.getVotingDir());
		doUpload(dir.toString(), fileName);
	}
	
	public void doUploadStuBehavior(Long schoolId, String fileName) throws Exception {
		StringBuffer dir = new StringBuffer();
		dir.append(schoolId).append(File.separator);
		dir.append(paramImage.getBehaviorDir());
		doUpload(dir.toString(), fileName);
	}

	public UploadFileHolder createHandler() {
		UploadFileHolder uploadService = new UploadFileHolder();
		uploadService.setParamImage(paramImage);
		uploadService.setLimitSize(limitSize);
		uploadService.setContentTypes(contentTypes);
		return uploadService;
	}
}
