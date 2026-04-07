
CREATE TABLE BOM
(
  BOM_no     number   NOT NULL,
  BOM_code   varchar2 NOT NULL,
  process_no number   NOT NULL,
  item_no    number   NOT NULL,
  BOM_qty    number   NOT NULL,
  item_no    number   NOT NULL,
  deleted    varchar ,
  CONSTRAINT PK_BOM PRIMARY KEY (BOM_no)
);

COMMENT ON TABLE BOM IS 'BOM';

COMMENT ON COLUMN BOM.BOM_no IS 'sequence';

COMMENT ON COLUMN BOM.BOM_code IS 'BOM_100001';

COMMENT ON COLUMN BOM.process_no IS 'sequence';

COMMENT ON COLUMN BOM.item_no IS '선행 item (재료)';

COMMENT ON COLUMN BOM.BOM_qty IS '결과 item 1개당 선행 item 소요량';

COMMENT ON COLUMN BOM.item_no IS '결과 item (생산물)';

COMMENT ON COLUMN BOM.deleted IS '삭제 시 Y';

CREATE TABLE comment
(
  comment_no number   NOT NULL,
  post_no    number   NOT NULL,
  comment_no number  ,
  user_id    varchar2 NOT NULL,
  content    varchar2 NOT NULL,
  mtime      date    ,
  ctime      date     NOT NULL,
  deleted    varchar ,
  CONSTRAINT PK_comment PRIMARY KEY (comment_no)
);

COMMENT ON TABLE comment IS '댓글';

COMMENT ON COLUMN comment.comment_no IS 'sequence';

COMMENT ON COLUMN comment.post_no IS 'sequence';

COMMENT ON COLUMN comment.comment_no IS '부모 댓글';

COMMENT ON COLUMN comment.user_id IS '작성자';

COMMENT ON COLUMN comment.content IS '내용';

COMMENT ON COLUMN comment.mtime IS '최종 수정시간';

COMMENT ON COLUMN comment.ctime IS '최초 작성시간';

COMMENT ON COLUMN comment.deleted IS '삭제 시 Y';

CREATE TABLE defect
(
  defect_no   number   NOT NULL,
  defect_code varchar2 NOT NULL,
  qc_no       number   NOT NULL,
  defect_cnt  number   NOT NULL,
  solution    varchar2,
  deleted     varchar ,
  dtype_no    number   NOT NULL,
  CONSTRAINT PK_defect PRIMARY KEY (defect_no)
);

COMMENT ON TABLE defect IS '불량 기록 관리';

COMMENT ON COLUMN defect.defect_no IS 'sequence';

COMMENT ON COLUMN defect.defect_code IS 'def_100001';

COMMENT ON COLUMN defect.qc_no IS 'sequence';

COMMENT ON COLUMN defect.defect_cnt IS '불량수량';

COMMENT ON COLUMN defect.solution IS '조치 내용';

COMMENT ON COLUMN defect.deleted IS '삭제 시 Y';

COMMENT ON COLUMN defect.dtype_no IS 'sequence';

CREATE TABLE defect_type
(
  dtype_no   number   NOT NULL,
  dtype_name varchar2 NOT NULL,
  CONSTRAINT PK_defect_type PRIMARY KEY (dtype_no)
);

COMMENT ON TABLE defect_type IS '불량 원인 타입';

COMMENT ON COLUMN defect_type.dtype_no IS 'sequence';

COMMENT ON COLUMN defect_type.dtype_name IS '기기불량, 원자재불량 등';

CREATE TABLE dept
(
  dept_no   number   NOT NULL,
  dept_name varchar2 NOT NULL,
  deleted   varchar ,
  CONSTRAINT PK_dept PRIMARY KEY (dept_no)
);

COMMENT ON TABLE dept IS '부서';

COMMENT ON COLUMN dept.deleted IS '삭제 시 Y';

CREATE TABLE emp
(
  emp_no   number   NOT NULL,
  emp_code varchar2 NOT NULL,
  job      varchar2 NOT NULL,
  auth     number   NOT NULL,
  dept_no  number   NOT NULL,
  pw       varchar2,
  hiredate date     NOT NULL,
  enddate  date    ,
  status   varchar  DEFAULT Y NOT NULL,
  CONSTRAINT PK_emp PRIMARY KEY (emp_no)
);

COMMENT ON TABLE emp IS '사원';

COMMENT ON COLUMN emp.emp_no IS 'sequence';

COMMENT ON COLUMN emp.emp_code IS 'emp_100001';

COMMENT ON COLUMN emp.job IS '직책';

COMMENT ON COLUMN emp.auth IS '권한';

COMMENT ON COLUMN emp.enddate IS '퇴사일';

COMMENT ON COLUMN emp.status IS '재직 Y / 퇴사 N';

CREATE TABLE equiplment_log
(
  log_no       number   NOT NULL,
  log_code     varchar2 NOT NULL,
  equipment_no number   NOT NULL,
  emp_no       number   NOT NULL,
  log_sdate    date     NOT NULL,
  log_edate    date    ,
  log_content  varchar2,
  deleted      varchar 
);

COMMENT ON TABLE equiplment_log IS '설비유지보수 내역';

COMMENT ON COLUMN equiplment_log.log_no IS 'sequence';

COMMENT ON COLUMN equiplment_log.log_code IS 'log_100001';

COMMENT ON COLUMN equiplment_log.equipment_no IS '유지보수한 설비';

COMMENT ON COLUMN equiplment_log.emp_no IS '담당자';

COMMENT ON COLUMN equiplment_log.log_sdate IS '시작일';

COMMENT ON COLUMN equiplment_log.log_edate IS '종료일';

COMMENT ON COLUMN equiplment_log.log_content IS '유지보수 내용';

COMMENT ON COLUMN equiplment_log.deleted IS '삭제 시 Y';

CREATE TABLE equipment
(
  equipment_no   number   NOT NULL,
  equipment_code varchar2 NOT NULL,
  installdate    date     NOT NULL,
  process_no     number   NOT NULL,
  estatus_no     number   NOT NULL,
  deleted        varchar ,
  CONSTRAINT PK_equipment PRIMARY KEY (equipment_no)
);

COMMENT ON TABLE equipment IS '설비';

COMMENT ON COLUMN equipment.equipment_no IS 'sequence';

COMMENT ON COLUMN equipment.equipment_code IS 'equip_100001';

COMMENT ON COLUMN equipment.installdate IS '설치 날짜';

COMMENT ON COLUMN equipment.process_no IS 'sequence';

COMMENT ON COLUMN equipment.estatus_no IS 'sequence';

COMMENT ON COLUMN equipment.deleted IS '삭제 시 Y';

CREATE TABLE equipment_status
(
  estatus_no   number   NOT NULL,
  estatus_name varchar2 NOT NULL,
  CONSTRAINT PK_equipment_status PRIMARY KEY (estatus_no)
);

COMMENT ON TABLE equipment_status IS '설비 상태';

COMMENT ON COLUMN equipment_status.estatus_no IS 'sequence';

COMMENT ON COLUMN equipment_status.estatus_name IS '정상 / 점검필요 / 고장 / 폐기 / 기타';

CREATE TABLE io
(
  io_no   number   NOT NULL,
  io_code varchar2 NOT NULL,
  item_no number   NOT NULL,
  LOT_no  number   NOT NULL,
  ior_no  number   NOT NULL,
  io_qty  number   NOT NULL,
  io_date date     NOT NULL,
  deleted varchar ,
  CONSTRAINT PK_io PRIMARY KEY (io_no)
);

COMMENT ON TABLE io IS '입출고 기록';

COMMENT ON COLUMN io.io_no IS 'sequence';

COMMENT ON COLUMN io.io_code IS 'in_100001 / out_100001';

COMMENT ON COLUMN io.item_no IS 'sequence';

COMMENT ON COLUMN io.LOT_no IS 'sequence';

COMMENT ON COLUMN io.ior_no IS 'sequence';

COMMENT ON COLUMN io.io_qty IS '수량';

COMMENT ON COLUMN io.io_date IS '입출고 일자';

COMMENT ON COLUMN io.deleted IS '삭제 시 N';

CREATE TABLE io_reason
(
  ior_no    number   NOT NULL,
  io_type   varchar  NOT NULL,
  io_reason varchar2 NOT NULL,
  CONSTRAINT PK_io_reason PRIMARY KEY (ior_no)
);

COMMENT ON TABLE io_reason IS '입출고 이유';

COMMENT ON COLUMN io_reason.ior_no IS 'sequence';

COMMENT ON COLUMN io_reason.io_type IS 'i / o';

COMMENT ON COLUMN io_reason.io_reason IS '생산 / 구매 / 폐기 / 출하 / 기타 등';

CREATE TABLE item
(
  item_no   number   NOT NULL,
  item_code varchar2 NOT NULL,
  item_name varchar2 NOT NULL,
  itype_no  number   NOT NULL,
  unit      varchar2,
  deleted   varchar ,
  CONSTRAINT PK_item PRIMARY KEY (item_no)
);

COMMENT ON TABLE item IS '제품';

COMMENT ON COLUMN item.item_no IS 'sequence';

COMMENT ON COLUMN item.item_code IS 'item_100001';

COMMENT ON COLUMN item.item_name IS '제품 이름';

COMMENT ON COLUMN item.itype_no IS '제품 타입';

COMMENT ON COLUMN item.unit IS '단위';

COMMENT ON COLUMN item.deleted IS '삭제 시 Y';

CREATE TABLE item_type
(
  itype_no   number   NOT NULL,
  itype_name varhcar2 NOT NULL,
  CONSTRAINT PK_item_type PRIMARY KEY (itype_no)
);

COMMENT ON TABLE item_type IS '제품 타입';

COMMENT ON COLUMN item_type.itype_no IS 'sequence';

COMMENT ON COLUMN item_type.itype_name IS '원자재, 반제품, 완제품';

CREATE TABLE LOT
(
  LOT_no                 number   NOT NULL,
  LOT_code               varchar2 NOT NULL,
  item_no                number   NOT NULL,
  LOT_qty                number   DEFAULT 1 NOT NULL,
  prod_date              date     NOT NULL,
  expiry_date            date     NOT NULL,
  Lstatus_no             number   NOT NULL,
  작업지시 id 있어야 함, 어디서 왔는지         ,
  CONSTRAINT PK_LOT PRIMARY KEY (LOT_no)
);

COMMENT ON TABLE LOT IS 'LOT';

COMMENT ON COLUMN LOT.LOT_no IS 'sequence';

COMMENT ON COLUMN LOT.LOT_code IS 'LOT_100001';

COMMENT ON COLUMN LOT.item_no IS 'sequence';

COMMENT ON COLUMN LOT.LOT_qty IS '수량';

COMMENT ON COLUMN LOT.prod_date IS '생산날짜';

COMMENT ON COLUMN LOT.expiry_date IS '유통기한';

COMMENT ON COLUMN LOT.Lstatus_no IS 'sequence';

CREATE TABLE LOT_status
(
  Lstatus_no   number   NOT NULL,
  Lstatus_name varchar2 NOT NULL,
  CONSTRAINT PK_LOT_status PRIMARY KEY (Lstatus_no)
);

COMMENT ON TABLE LOT_status IS 'LOT 상태';

COMMENT ON COLUMN LOT_status.Lstatus_no IS 'sequence';

COMMENT ON COLUMN LOT_status.Lstatus_name IS '정상 / 홀드 / 폐기';

CREATE TABLE plan_status
(
  plstatus_no   number   NOT NULL,
  plstatus_name varchar2 NOT NULL,
  CONSTRAINT PK_plan_status PRIMARY KEY (plstatus_no)
);

COMMENT ON TABLE plan_status IS '생산계획 상태';

COMMENT ON COLUMN plan_status.plstatus_name IS '완료 / 미완료 / 보류';

CREATE TABLE post
(
  post_no  number   NOT NULL,
  ptype_no number   NOT NULL,
  user_id  varchar2 NOT NULL,
  title    varhcar2 NOT NULL,
  content  varchar2 NOT NULL,
  ctime    date     NOT NULL,
  mtime    date    ,
  deleted  varchar ,
  CONSTRAINT PK_post PRIMARY KEY (post_no)
);

COMMENT ON TABLE post IS '게시글';

COMMENT ON COLUMN post.post_no IS 'sequence';

COMMENT ON COLUMN post.ptype_no IS '1-건의사항, 2-공지사항';

COMMENT ON COLUMN post.user_id IS '작성자';

COMMENT ON COLUMN post.title IS '제목';

COMMENT ON COLUMN post.content IS '내용';

COMMENT ON COLUMN post.ctime IS '최초 작성시간';

COMMENT ON COLUMN post.mtime IS '최종 수정시간';

COMMENT ON COLUMN post.deleted IS '삭제 시 Y';

CREATE TABLE post_type
(
  ptype_no   number   NOT NULL,
  ptype_name varhcar2 NOT NULL,
  CONSTRAINT PK_post_type PRIMARY KEY (ptype_no)
);

COMMENT ON TABLE post_type IS '게시글 타입';

COMMENT ON COLUMN post_type.ptype_no IS '1-건의사항, 2-공지사항';

COMMENT ON COLUMN post_type.ptype_name IS '건의사항, 공지사항';

CREATE TABLE process
(
  process_no   number   NOT NULL,
  process_code varchar2 NOT NULL,
  process_no   number  ,
  process_no   number  ,
  deleted      varchar ,
  CONSTRAINT PK_process PRIMARY KEY (process_no)
);

COMMENT ON TABLE process IS '공정';

COMMENT ON COLUMN process.process_no IS 'sequence';

COMMENT ON COLUMN process.process_code IS 'proc_100001';

COMMENT ON COLUMN process.process_no IS '선행 공정';

COMMENT ON COLUMN process.process_no IS '후행 공정';

COMMENT ON COLUMN process.deleted IS '삭제 시 Y';

CREATE TABLE prod_plan
(
  plan_no     number   NOT NULL,
  plan_code   varchar2 NOT NULL,
  emp_no      number   NOT NULL,
  item_no     number   NOT NULL,
  plan_qty    number   NOT NULL,
  plan_sdate  date     NOT NULL,
  plan_edate  date     NOT NULL,
  plstatus_no number   NOT NULL,
  deleted     varchar ,
  CONSTRAINT PK_prod_plan PRIMARY KEY (plan_no)
);

COMMENT ON TABLE prod_plan IS '생산계획';

COMMENT ON COLUMN prod_plan.plan_no IS 'sequence';

COMMENT ON COLUMN prod_plan.plan_code IS 'plan_100001';

COMMENT ON COLUMN prod_plan.emp_no IS '생산계획 관리자';

COMMENT ON COLUMN prod_plan.item_no IS '생산 목표 제품';

COMMENT ON COLUMN prod_plan.plan_qty IS '생산 목표량';

COMMENT ON COLUMN prod_plan.plan_sdate IS '시작일';

COMMENT ON COLUMN prod_plan.plan_edate IS '종료일';

COMMENT ON COLUMN prod_plan.plstatus_no IS '생산계획 상태';

COMMENT ON COLUMN prod_plan.deleted IS '삭제 시 Y';

CREATE TABLE quality_check
(
  qc_no    number   NOT NULL,
  qc_code  varchar2 NOT NULL,
  wo_no    number   NOT NULL,
  qc_sdate date     NOT NULL,
  qc_edate date    ,
  emp_no   number   NOT NULL,
  deleted  varchar ,
  CONSTRAINT PK_quality_check PRIMARY KEY (qc_no)
);

COMMENT ON TABLE quality_check IS '품질관리';

COMMENT ON COLUMN quality_check.qc_no IS 'sequence';

COMMENT ON COLUMN quality_check.qc_code IS 'qc_100001';

COMMENT ON COLUMN quality_check.wo_no IS 'sequence';

COMMENT ON COLUMN quality_check.qc_sdate IS '시작일';

COMMENT ON COLUMN quality_check.qc_edate IS '종료일';

COMMENT ON COLUMN quality_check.emp_no IS '검사자';

COMMENT ON COLUMN quality_check.deleted IS '삭제 시 Y';

CREATE TABLE stock
(
  stock_no   number   NOT NULL,
  stock_code varchar2 NOT NULL,
  LOT_no     number   NOT NULL,
  item_no    number   NOT NULL,
  stock_qty  number   DEFAULT 0 NOT NULL,
  CONSTRAINT PK_stock PRIMARY KEY (stock_no)
);

COMMENT ON TABLE stock IS '재고';

COMMENT ON COLUMN stock.stock_no IS 'sequence';

COMMENT ON COLUMN stock.stock_code IS 'stock_100001';

COMMENT ON COLUMN stock.LOT_no IS 'sequence';

COMMENT ON COLUMN stock.item_no IS 'sequence';

COMMENT ON COLUMN stock.stock_qty IS '재고 수량';

CREATE TABLE user
(
  user_id varchar2 NOT NULL,
  emp_no  number   NOT NULL,
  pw      varchar2 NOT NULL,
  deleted varchar ,
  CONSTRAINT PK_user PRIMARY KEY (user_id)
);

COMMENT ON TABLE user IS '사용자';

COMMENT ON COLUMN user.user_id IS '아이디';

COMMENT ON COLUMN user.emp_no IS 'sequence';

COMMENT ON COLUMN user.pw IS '비밀번호';

COMMENT ON COLUMN user.deleted IS '삭제 시 Y';

CREATE TABLE vendor
(
  vendor_no   number   NOT NULL,
  vendor_code varhcar2 NOT NULL,
  vendor_name varchar2 NOT NULL,
  tel         varchar2,
  vtype_no    number   NOT NULL,
  deleted     varhcar ,
  CONSTRAINT PK_vendor PRIMARY KEY (vendor_no)
);

COMMENT ON TABLE vendor IS '거래처';

COMMENT ON COLUMN vendor.vendor_no IS 'sequence';

COMMENT ON COLUMN vendor.vendor_code IS 'vend_100001';

COMMENT ON COLUMN vendor.vendor_name IS '거래처 이름';

COMMENT ON COLUMN vendor.tel IS '연락처';

COMMENT ON COLUMN vendor.vtype_no IS 'sequence';

COMMENT ON COLUMN vendor.deleted IS '삭제 시 Y';

CREATE TABLE vendor_type
(
  vtype_no   number   NOT NULL,
  vtype_name varchar2 NOT NULL,
  CONSTRAINT PK_vendor_type PRIMARY KEY (vtype_no)
);

COMMENT ON TABLE vendor_type IS '거래처 타입';

COMMENT ON COLUMN vendor_type.vtype_no IS 'sequence';

COMMENT ON COLUMN vendor_type.vtype_name IS '공급업체 / 고객사';

CREATE TABLE wo_status
(
  wostatus_no   number   NOT NULL,
  wostatus_name varchar2 NOT NULL,
  CONSTRAINT PK_wo_status PRIMARY KEY (wostatus_no)
);

COMMENT ON TABLE wo_status IS '작업 진행상태';

COMMENT ON COLUMN wo_status.wostatus_no IS 'sequence';

COMMENT ON COLUMN wo_status.wostatus_name IS '완료 / 미완료 / 보류';

CREATE TABLE workorder
(
  wo_no        number   NOT NULL,
  wo_code      varchar2 NOT NULL,
  plan_no      number   NOT NULL,
  equipment_no number   NOT NULL,
  process_no   number   NOT NULL,
  emp_no       number   NOT NULL,
  emp_no       number   NOT NULL,
  workdate     date     NOT NULL,
  wo_qty       number   NOT NULL,
  wostatus_no  number   NOT NULL,
  deleted      varchar ,
  fin_qty      number   NOT NULL,
  CONSTRAINT PK_workorder PRIMARY KEY (wo_no)
);

COMMENT ON TABLE workorder IS '작업지시';

COMMENT ON COLUMN workorder.wo_no IS 'sequence';

COMMENT ON COLUMN workorder.wo_code IS 'wo_100001';

COMMENT ON COLUMN workorder.plan_no IS '생산계획';

COMMENT ON COLUMN workorder.equipment_no IS '설비';

COMMENT ON COLUMN workorder.process_no IS '공정';

COMMENT ON COLUMN workorder.emp_no IS '지시자';

COMMENT ON COLUMN workorder.emp_no IS '관리자';

COMMENT ON COLUMN workorder.workdate IS '작업일';

COMMENT ON COLUMN workorder.wo_qty IS '목표수량';

COMMENT ON COLUMN workorder.wostatus_no IS 'sequence';

COMMENT ON COLUMN workorder.deleted IS '삭제 시 Y';

COMMENT ON COLUMN workorder.fin_qty IS '완료수량';

ALTER TABLE emp
  ADD CONSTRAINT FK_dept_TO_emp
    FOREIGN KEY (dept_no)
    REFERENCES dept (dept_no);

ALTER TABLE post
  ADD CONSTRAINT FK_post_type_TO_post
    FOREIGN KEY (ptype_no)
    REFERENCES post_type (ptype_no);

ALTER TABLE comment
  ADD CONSTRAINT FK_post_TO_comment
    FOREIGN KEY (post_no)
    REFERENCES post (post_no);

ALTER TABLE comment
  ADD CONSTRAINT FK_comment_TO_comment
    FOREIGN KEY (comment_no)
    REFERENCES comment (comment_no);

ALTER TABLE item
  ADD CONSTRAINT FK_item_type_TO_item
    FOREIGN KEY (itype_no)
    REFERENCES item_type (itype_no);

ALTER TABLE process
  ADD CONSTRAINT FK_process_TO_process
    FOREIGN KEY (process_no)
    REFERENCES process (process_no);

ALTER TABLE process
  ADD CONSTRAINT FK_process_TO_process1
    FOREIGN KEY (process_no)
    REFERENCES process (process_no);

ALTER TABLE equipment
  ADD CONSTRAINT FK_equipment_status_TO_equipment
    FOREIGN KEY (estatus_no)
    REFERENCES equipment_status (estatus_no);

ALTER TABLE vendor
  ADD CONSTRAINT FK_vendor_type_TO_vendor
    FOREIGN KEY (vtype_no)
    REFERENCES vendor_type (vtype_no);

ALTER TABLE equipment
  ADD CONSTRAINT FK_process_TO_equipment
    FOREIGN KEY (process_no)
    REFERENCES process (process_no);

ALTER TABLE BOM
  ADD CONSTRAINT FK_process_TO_BOM
    FOREIGN KEY (process_no)
    REFERENCES process (process_no);

ALTER TABLE user
  ADD CONSTRAINT FK_emp_TO_user
    FOREIGN KEY (emp_no)
    REFERENCES emp (emp_no);

ALTER TABLE comment
  ADD CONSTRAINT FK_user_TO_comment
    FOREIGN KEY (user_id)
    REFERENCES user (user_id);

ALTER TABLE post
  ADD CONSTRAINT FK_user_TO_post
    FOREIGN KEY (user_id)
    REFERENCES user (user_id);

ALTER TABLE LOT
  ADD CONSTRAINT FK_LOT_status_TO_LOT
    FOREIGN KEY (Lstatus_no)
    REFERENCES LOT_status (Lstatus_no);

ALTER TABLE stock
  ADD CONSTRAINT FK_LOT_TO_stock
    FOREIGN KEY (LOT_no)
    REFERENCES LOT (LOT_no);

ALTER TABLE BOM
  ADD CONSTRAINT FK_item_TO_BOM
    FOREIGN KEY (item_no)
    REFERENCES item (item_no);

ALTER TABLE BOM
  ADD CONSTRAINT FK_item_TO_BOM1
    FOREIGN KEY (item_no)
    REFERENCES item (item_no);

ALTER TABLE LOT
  ADD CONSTRAINT FK_item_TO_LOT
    FOREIGN KEY (item_no)
    REFERENCES item (item_no);

ALTER TABLE stock
  ADD CONSTRAINT FK_item_TO_stock
    FOREIGN KEY (item_no)
    REFERENCES item (item_no);

ALTER TABLE io
  ADD CONSTRAINT FK_item_TO_io
    FOREIGN KEY (item_no)
    REFERENCES item (item_no);

ALTER TABLE io
  ADD CONSTRAINT FK_LOT_TO_io
    FOREIGN KEY (LOT_no)
    REFERENCES LOT (LOT_no);

ALTER TABLE io
  ADD CONSTRAINT FK_io_reason_TO_io
    FOREIGN KEY (ior_no)
    REFERENCES io_reason (ior_no);

ALTER TABLE prod_plan
  ADD CONSTRAINT FK_item_TO_prod_plan
    FOREIGN KEY (item_no)
    REFERENCES item (item_no);

ALTER TABLE prod_plan
  ADD CONSTRAINT FK_plan_status_TO_prod_plan
    FOREIGN KEY (plstatus_no)
    REFERENCES plan_status (plstatus_no);

ALTER TABLE workorder
  ADD CONSTRAINT FK_prod_plan_TO_workorder
    FOREIGN KEY (plan_no)
    REFERENCES prod_plan (plan_no);

ALTER TABLE workorder
  ADD CONSTRAINT FK_equipment_TO_workorder
    FOREIGN KEY (equipment_no)
    REFERENCES equipment (equipment_no);

ALTER TABLE workorder
  ADD CONSTRAINT FK_process_TO_workorder
    FOREIGN KEY (process_no)
    REFERENCES process (process_no);

ALTER TABLE workorder
  ADD CONSTRAINT FK_emp_TO_workorder
    FOREIGN KEY (emp_no)
    REFERENCES emp (emp_no);

ALTER TABLE workorder
  ADD CONSTRAINT FK_emp_TO_workorder1
    FOREIGN KEY (emp_no)
    REFERENCES emp (emp_no);

ALTER TABLE workorder
  ADD CONSTRAINT FK_wo_status_TO_workorder
    FOREIGN KEY (wostatus_no)
    REFERENCES wo_status (wostatus_no);

ALTER TABLE prod_plan
  ADD CONSTRAINT FK_emp_TO_prod_plan
    FOREIGN KEY (emp_no)
    REFERENCES emp (emp_no);

ALTER TABLE quality_check
  ADD CONSTRAINT FK_workorder_TO_quality_check
    FOREIGN KEY (wo_no)
    REFERENCES workorder (wo_no);

ALTER TABLE defect
  ADD CONSTRAINT FK_defect_type_TO_defect
    FOREIGN KEY (dtype_no)
    REFERENCES defect_type (dtype_no);

ALTER TABLE defect
  ADD CONSTRAINT FK_quality_check_TO_defect
    FOREIGN KEY (qc_no)
    REFERENCES quality_check (qc_no);

ALTER TABLE quality_check
  ADD CONSTRAINT FK_emp_TO_quality_check
    FOREIGN KEY (emp_no)
    REFERENCES emp (emp_no);

ALTER TABLE equiplment_log
  ADD CONSTRAINT FK_equipment_TO_equiplment_log
    FOREIGN KEY (equipment_no)
    REFERENCES equipment (equipment_no);

ALTER TABLE equiplment_log
  ADD CONSTRAINT FK_emp_TO_equiplment_log
    FOREIGN KEY (emp_no)
    REFERENCES emp (emp_no);
