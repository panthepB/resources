/*
 * Decompiled with CFR 0_114.
 */
package distar.project.DLT.service;

public class ExerciseForm {
    private String password;
    private String status;
    private String subject;
    private String classEdu;
    private String sectionEdu;
    private String teacherName;
    private String desc;
    private String topic;
    private Long examId;
    private Long currentDataId;
    private int st;
    private int[] test;

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getClassEdu() {
        return this.classEdu;
    }

    public void setClassEdu(String classEdu) {
        this.classEdu = classEdu;
    }

    public String getSectionEdu() {
        return this.sectionEdu;
    }

    public void setSectionEdu(String sectionEdu) {
        this.sectionEdu = sectionEdu;
    }

    public String getTeacherName() {
        return this.teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getExamId() {
        return this.examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Long getCurrentDataId() {
        return this.currentDataId;
    }

    public void setCurrentDataId(Long currentDataId) {
        this.currentDataId = currentDataId;
    }

    public int[] getTest() {
        return this.test;
    }

    public void setTest(int[] test) {
        this.test = test;
    }

    public int getSt() {
        return this.st;
    }

    public void setSt(int st) {
        this.st = st;
    }
}
