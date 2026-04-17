package P03_suggestion;

import java.sql.Date;

public class CommentDTO {

    private String comno;
    private String content;
    private Date   ctime;
    private String boardno;
    private String parentComno; // 부모 댓글 번호 (NULL = 최상위)
    private int    depth;       // CONNECT BY LEVEL - 1

    public String getComno()                         { return comno; }
    public void   setComno(String comno)             { this.comno = comno; }

    public String getContent()                       { return content; }
    public void   setContent(String content)         { this.content = content; }

    public Date   getCtime()                         { return ctime; }
    public void   setCtime(Date ctime)               { this.ctime = ctime; }

    public String getBoardno()                       { return boardno; }
    public void   setBoardno(String boardno)         { this.boardno = boardno; }

    public String getParentComno()                   { return parentComno; }
    public void   setParentComno(String parentComno) { this.parentComno = parentComno; }

    public int    getDepth()                         { return depth; }
    public void   setDepth(int depth)                { this.depth = depth; }
}