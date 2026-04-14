package P06_prod;

import java.sql.Date;

public class ProdDTO {

    // production_plan 컬럼
    private String planId;       // plan_id (PK)
    private String itemId;       // item_id (FK)
    private String empId;        // emp_id  (FK)
    private int    planQty;      // plan_qty  목표수량
    private Date   planSdate;    // plan_sdate
    private Date   planEdate;    // plan_edate
    private int    status;       // 0:대기 1:진행중 2:완료 3:보류

    // JOIN 결과
    private String itemName;     // item.item_name  → 제품명
    private String ename;        // user_info.ename → 담당자
    private int prevQty;  // prev_qty  생산수량

    // 페이지네이션용
    private int size  = 10;
    private int page  = 1;
    private int start;
    private int end;

    // ─── Getters / Setters ───────────────────────────────────────
    
    public int  getPrevQty()              { return prevQty; }
    public void setPrevQty(int prevQty)   { this.prevQty = prevQty; }

    public String getPlanId()              { return planId; }
    public void   setPlanId(String planId) { this.planId = planId; }

    public String getItemId()              { return itemId; }
    public void   setItemId(String itemId) { this.itemId = itemId; }

    public String getEmpId()               { return empId; }
    public void   setEmpId(String empId)   { this.empId = empId; }

    public int  getPlanQty()               { return planQty; }
    public void setPlanQty(int planQty)    { this.planQty = planQty; }

    public Date getPlanSdate()                   { return planSdate; }
    public void setPlanSdate(Date planSdate)     { this.planSdate = planSdate; }

    public Date getPlanEdate()                   { return planEdate; }
    public void setPlanEdate(Date planEdate)     { this.planEdate = planEdate; }

    public int  getStatus()                { return status; }
    public void setStatus(int status)      { this.status = status; }

    public String getItemName()                  { return itemName; }
    public void   setItemName(String itemName)   { this.itemName = itemName; }

    public String getEname()               { return ename; }
    public void   setEname(String ename)   { this.ename = ename; }

    public int  getSize()                  { return size; }
    public void setSize(int size)          { this.size = size; }

    public int  getPage()                  { return page; }
    public void setPage(int page)          { this.page = page; }

    public int  getStart()                 { return start; }
    public void setStart(int start)        { this.start = start; }

    public int  getEnd()                   { return end; }
    public void setEnd(int end)            { this.end = end; }
}