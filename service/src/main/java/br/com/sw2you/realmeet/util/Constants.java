package br.com.sw2you.realmeet.util;

public final class Constants {

    private Constants() {
    }

    public static final String ALLOCATION_MAX_FILTER_LIMIT = "${realmeet.allocations.maxFilterLimit:50}";
    public static final String ALLOCATION_REPORT_MAX_MONTHS_INTERVAL = "${realmeet.reports.allocationReport.maxMonthsInterval:12}";
    public static final String ALLOCATION = "allocation";
    public static final String REPORT = "report_";
}
