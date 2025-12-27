package org.civichelpapi.civichelpapi.ngo.service;

import org.civichelpapi.civichelpapi.ngo.dto.NgoOfferDto;
import org.civichelpapi.civichelpapi.report.dto.response.ReportResponse;

import java.util.List;

public interface NgoService {
    NgoOfferDto offerHelp(Long ngoId, Long reportId);
    List<ReportResponse> getUnresolvedReports();
}
