package P03_suggestion;

import java.sql.Date;
import java.util.List;

public class SuggestionDTO {

    private String boardno;
    private String title;
    private String content;
    private Date   ctime;
    private Date   mtime;
    private int    views;
    private String empId;
    private int    complete;

    // 첨부파일
    private String originName;
    private String saveName;

    // user_info JOIN
    private String ename;

    // comment_info 입력용
    private String commentContent;

    // 댓글 목록 (detail 조회 시 사용)
    private List<CommentDTO> commentList;

    // 페이지네이션
    private int page;
    private int size;
    private int start;
    private int end;

    public String getBoardno()                           { return boardno; }
    public void   setBoardno(String boardno)             { this.boardno = boardno; }

    public String getTitle()                             { return title; }
    public void   setTitle(String title)                 { this.title = title; }

    public String getContent()                           { return content; }
    public void   setContent(String content)             { this.content = content; }

    public Date   getCtime()                             { return ctime; }
    public void   setCtime(Date ctime)                   { this.ctime = ctime; }

    public Date   getMtime()                             { return mtime; }
    public void   setMtime(Date mtime)                   { this.mtime = mtime; }

    public int    getViews()                             { return views; }
    public void   setViews(int views)                    { this.views = views; }

    public String getEmpId()                             { return empId; }
    public void   setEmpId(String empId)                 { this.empId = empId; }

    public int    getComplete()                          { return complete; }
    public void   setComplete(int complete)              { this.complete = complete; }

    public String getOriginName()                        { return originName; }
    public void   setOriginName(String originName)       { this.originName = originName; }

    public String getSaveName()                          { return saveName; }
    public void   setSaveName(String saveName)           { this.saveName = saveName; }

    public String getEname()                             { return ename; }
    public void   setEname(String ename)                 { this.ename = ename; }

    public String getCommentContent()                    { return commentContent; }
    public void   setCommentContent(String c)            { this.commentContent = c; }

    public List<CommentDTO> getCommentList()             { return commentList; }
    public void setCommentList(List<CommentDTO> list)    { this.commentList = list; }

    public int  getPage()                                { return page; }
    public void setPage(int page)                        { this.page = page; }

    public int  getSize()                                { return size; }
    public void setSize(int size)                        { this.size = size; }

    public int  getStart()                               { return start; }
    public void setStart(int start)                      { this.start = start; }

    public int  getEnd()                                 { return end; }
    public void setEnd(int end)                          { this.end = end; }
}