package P06_prod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import P06_prod.ProdDAO;
import P06_prod.ProdDTO;

public class ProdService {

    ProdDAO prodPlanDAO = new ProdDAO();

    public Map getPlanList(ProdDTO planDTO) {

        int size = planDTO.getSize();
        int page = planDTO.getPage();

        // EmpService 와 동일한 rownum 계산 방식
        int end   = size * page;
        int start = end - (size - 1);

        planDTO.setEnd(end);
        planDTO.setStart(start);

        List list        = prodPlanDAO.selectAll(planDTO);
        int  totalCount  = prodPlanDAO.selectTotal();

        Map map = new HashMap();
        map.put("list",       list);
        map.put("totalCount", totalCount);

        return map;
    }
    public ProdDTO getPlanDetail(String planId) {
        return prodPlanDAO.selectOne(planId);
    }
}