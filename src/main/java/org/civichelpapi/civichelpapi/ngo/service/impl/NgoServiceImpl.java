package org.civichelpapi.civichelpapi.ngo.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.auth.jwt.JwtService;
import org.civichelpapi.civichelpapi.exception.BusinessException;
import org.civichelpapi.civichelpapi.exception.NotFoundException;
import org.civichelpapi.civichelpapi.ngo.dto.NgoOfferDto;
import org.civichelpapi.civichelpapi.ngo.entity.NgoOffer;
import org.civichelpapi.civichelpapi.ngo.event.NgoHelpOfferedEvent;
import org.civichelpapi.civichelpapi.ngo.repository.NgoOfferRepository;
import org.civichelpapi.civichelpapi.ngo.service.NgoService;
import org.civichelpapi.civichelpapi.report.entity.Report;
import org.civichelpapi.civichelpapi.report.repository.ReportRepository;
import org.civichelpapi.civichelpapi.user.entity.User;
import org.civichelpapi.civichelpapi.user.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NgoServiceImpl implements NgoService {

    private final ReportRepository reportRepository;
    private final NgoOfferRepository ngoOfferRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public NgoOfferDto offerHelp(Long ngoId, Long reportId) {

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new NotFoundException("Report not found"));

        if (!report.isUnresolved()) {
            throw new BusinessException("Cannot offer help on resolved or rejected reports");
        }

        if (ngoOfferRepository.existsByNgoIdAndReportId(ngoId, reportId)) {
            throw new BusinessException("You have already offered help for this report");
        }

        if (ngoOfferRepository.existsByNgoIdAndReportId(ngoId, reportId)) {
            throw new BusinessException("You have already offered help for this report");
        }

        User ngo = userRepository.findById(ngoId)
                .orElseThrow(() -> new NotFoundException("NGO user not found"));

        NgoOffer offer = new NgoOffer();
        offer.setNgo(ngo);
        offer.setReport(report);
        offer.setOfferedAt(LocalDateTime.now());

        offer = ngoOfferRepository.save(offer);

        eventPublisher.publishEvent(
                new NgoHelpOfferedEvent(this,report, ngo)
        );

        return new NgoOfferDto(
                offer.getId(),
                offer.getNgo().getId(),
                offer.getReport().getId()
        );

    }
}
