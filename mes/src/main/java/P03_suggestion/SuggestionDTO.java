package P03_suggestion;

import java.sql.Date;

public class SuggestionDTO {

    // ── 테이블 컬럼 ───────────────────────────────────────────────────────
    private int    boardno;   // PK
    private String title;     // 제목
    private String content;   // 내용
    private Date   ctime;     // 최초 작성일
    private Date   mtime;     // 최종 수정일
    private int    views;     // 조회수
    private String empId;     // FK (로그인 세션 ID)
    private int    complete;  // 0: 처리중, 1: 처리완료

    // ── 페이지네이션용 ────────────────────────────────────────────────────
    private int page;
    private int size;
    private int start;
    private int end;

    // ── Getters & Setters ─────────────────────────────────────────────────

    public int getBoardno()               { return boardno; }
    public void setBoardno(int boardno)   { this.boardno = boardno; }

    public String getTitle()              { return title; }
    public void setTitle(String title)    { this.title = title; }

    public String getContent()            { return content; }
    public void setContent(String content){ this.content = content; }

    public Date getCtime()                { return ctime; }
    public void setCtime(Date ctime)      { this.ctime = ctime; }

    public Date getMtime()                { return mtime; }
    public void setMtime(Date mtime)      { this.mtime = mtime; }

    public int getViews()                 { return views; }
    public void setViews(int views)       { this.views = views; }

    public String getEmpId()              { return empId; }
    public void setEmpId(String empId)    { this.empId = empId; }

    public int getComplete()              { return complete; }
    public void setComplete(int complete) { this.complete = complete; }

    public int getPage()                  { return page; }
    public void setPage(int page)         { this.page = page; }

    public int getSize()                  { return size; }
    public void setSize(int size)         { this.size = size; }

    public int getStart()                 { return start; }
    public void setStart(int start)       { this.start = start; }

    public int getEnd()                   { return end; }
    public void setEnd(int end)           { this.end = end; }
}