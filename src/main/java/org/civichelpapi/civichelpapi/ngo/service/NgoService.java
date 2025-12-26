package org.civichelpapi.civichelpapi.ngo.service;

import org.civichelpapi.civichelpapi.ngo.dto.NgoOfferDto;

public interface NgoService {
    NgoOfferDto offerHelp(Long ngoId, Long reportId);
}
