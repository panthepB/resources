/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  distar.project.service.UploadFileService
 *  org.springframework.web.multipart.MultipartFile
 */
package distar.project.DLT.service;

import distar.project.service.UploadFileService;
import java.io.File;
import org.springframework.web.multipart.MultipartFile;

public class UploadService
extends UploadFileService {
    private String uploadPath;

    public String getUploadPath() {
        return this.uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getError() {
        if (this.file.getSize() > (long)this.getLimitSize() && this.getLimitSize() != -1) {
            return "size";
        }
        return null;
    }

    public void uploadFile(Long currentDataId, String fileName) throws Exception {
        String path = String.valueOf(this.uploadPath) + File.separator + currentDataId;
        File pathDir = new File(path);
        if (!pathDir.isDirectory()) {
            pathDir.mkdir();
        }
        this.doUpload(path, fileName);
    }

    public void uploadSS(Long currentDataId, String fileName) throws Exception {
        File pathDir;
        String pathCur = String.valueOf(this.uploadPath) + File.separator + currentDataId;
        String path = String.valueOf(pathCur) + File.separator + "ScreenCapture";
        File pathCurDir = new File(pathCur);
        if (!pathCurDir.isDirectory()) {
            pathCurDir.mkdir();
        }
        if (!(pathDir = new File(path)).isDirectory()) {
            pathDir.mkdir();
        }
        this.doUpload(path, fileName);
    }
}
