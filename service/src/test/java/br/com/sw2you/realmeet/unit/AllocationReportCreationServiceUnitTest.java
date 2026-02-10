package br.com.sw2you.realmeet.unit;

import static br.com.sw2you.realmeet.utils.TestConstants.EMAIL_TO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.sw2you.realmeet.core.BaseUnitTest;
import br.com.sw2you.realmeet.domain.repository.AllocationRepository;
import br.com.sw2you.realmeet.exception.InvalidRequestException;
import br.com.sw2you.realmeet.report.enumeration.ReportFormat;
import br.com.sw2you.realmeet.report.handler.AllocationReportHandler;
import br.com.sw2you.realmeet.report.resolver.ReportHandlerResolver;
import br.com.sw2you.realmeet.report.validator.AllocationReportValidator;
import br.com.sw2you.realmeet.service.ReportCreationService;
import br.com.sw2you.realmeet.service.ReportDispatcherService;
import br.com.sw2you.realmeet.validator.ValidationError;
import br.com.sw2you.realmeet.validator.ValidatorConstants;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class AllocationReportCreationServiceUnitTest extends BaseUnitTest {

    private static final int MAX_MONTHS = 2;

    private ReportCreationService victim;

    @Mock
    private ReportHandlerResolver reportHandlerResolver;

    @Mock
    private ReportDispatcherService reportDispatcherService;

    @Mock
    private AllocationRepository allocationRepository;

    @Mock
    private AllocationReportHandler allocationReportHandlerMock;

    @BeforeEach
    void setupEach() {
        victim = new ReportCreationService(reportHandlerResolver, reportDispatcherService);

        AllocationReportValidator realValidator = new AllocationReportValidator(MAX_MONTHS);

        when(allocationReportHandlerMock.getReportValidator()).thenReturn(realValidator);
        when(reportHandlerResolver.resolveReportHandler(any())).thenReturn(allocationReportHandlerMock);
    }

    @Test
    void testCreateAllocationReportSuccess() {
        victim.createAllocationReport(LocalDate.now().minusDays(5), LocalDate.now().minusDays(1), EMAIL_TO, ReportFormat.PDF.name());

        verify(reportDispatcherService).dispatch(any());
    }

    @Test
    void testCreateAllocationReportNoEmail() {

        var exception = Assertions.assertThrows(InvalidRequestException.class, () ->
                victim.createAllocationReport(LocalDate.now().minusDays(5), LocalDate.now().minusDays(1), "", ReportFormat.PDF.name()));

        Assertions.assertEquals(1, exception.getValidationErrors().getNumberOfErrors());
        Assertions.assertEquals(new ValidationError(ValidatorConstants.EMAIL, ValidatorConstants.EMAIL + ValidatorConstants.MISSING),
                exception.getValidationErrors().getError(0));
    }
}