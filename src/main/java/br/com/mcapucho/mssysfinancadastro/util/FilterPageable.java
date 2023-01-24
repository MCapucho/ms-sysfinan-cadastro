package br.com.mcapucho.mssysfinancadastro.util;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
@Builder
public class FilterPageable {

    private Integer page;
    private Integer linesPerPage;
    private String orderBy;
    private String direction;

    public FilterPageable(Integer page, Integer linesPerPage, String orderBy, String direction) {
        this.page = page;
        this.linesPerPage = linesPerPage;
        this.orderBy = orderBy;
        this.direction = direction;
    }

    public PageRequest listByPage() {
        return PageRequest.of(
                getPage(),
                getLinesPerPage(),
                Sort.Direction.valueOf(getDirection().toUpperCase()),
                getOrderBy());
    }
}
