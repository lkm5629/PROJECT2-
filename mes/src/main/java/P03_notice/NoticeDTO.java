package P03_notice;

import java.sql.Date;

public class NoticeDTO {

    // notice 테이블 컬럼
    private String boardno;
    private String title;
    private String content;
    private Date   ctime;
    private Date   mtime;
    private int    views;
    private String empId;
    private String ename;      // user_info JOIN용
    private String deleted;    // 'N' 기본값

    // 첨부파일
    private String originName;  // 원본 파일명
    private String saveName;   // millis_원본파일명

    // 검색
    private String keyword;

    // 페이지네이션
    private int size  = 10;
    private int page  = 1;
    private int start;
    private int end;

    // Getter / Setter

    public String getBoardno()  { return boardno; }
    public void   setBoardno(String boardno) { this.boardno = boardno; }

    public String getTitle()    { return title; }
    public void   setTitle(String title) { this.title = title; }

    public String getContent()  { return content; }
    public void   setContent(String content) { this.content = content; }

    public Date   getCtime()    { return ctime; }
    public void   setCtime(Date ctime) { this.ctime = ctime; }

    public Date   getMtime()    { return mtime; }
    public void   setMtime(Date mtime) { this.mtime = mtime; }

    public int    getViews()    { return views; }
    public void   setViews(int views) { this.views = views; }

    public String getEmpId()    { return empId; }
    public void   setEmpId(String empId) { this.empId = empId; }

    public String getEname()    { return ename; }
    public void   setEname(String ename) { this.ename = ename; }

    public String getDeleted()  { return deleted; }
    public void   setDeleted(String deleted) { this.deleted = deleted; }

    public String getOriginName() { return originName; }
    public void   setOriginName(String originName) { this.originName = originName; }

    public String getSaveName()  { return saveName; }
    public void   setSaveName(String saveName) { this.saveName = saveName; }

    public String getKeyword()  { return keyword; }
    public void   setKeyword(String keyword) { this.keyword = keyword; }

    public int    getSize()     { return size; }
    public void   setSize(int size) { this.size = size; }

    public int    getPage()     { return page; }
    public void   setPage(int page) { this.page = page; }

    public int    getStart()    { return start; }
    public void   setStart(int start) { this.start = start; }

    public int    getEnd()      { return end; }
    public void   setEnd(int end) { this.end = end; }
}