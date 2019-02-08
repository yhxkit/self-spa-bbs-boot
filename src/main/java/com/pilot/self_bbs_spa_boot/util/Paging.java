package com.pilot.self_bbs_spa_boot.util;

import org.springframework.data.domain.PageRequest;

public interface Paging {
    PageRequest elementsByPage(int requiredPage, int elementsNumberForOnePage, String sortBy);
}
