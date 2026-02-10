package br.com.sw2you.realmeet.report.model;

import br.com.sw2you.realmeet.email.TemplateType;
import br.com.sw2you.realmeet.report.enumeration.ReportFormat;
import java.util.Arrays;
import java.util.Objects;

public class GeneratedReport {
    private final byte[] bytes;
    private final TemplateType templateType;
    private final ReportFormat reportFormat;
    private final String fileName;
    private final String email;

    private GeneratedReport(Builder builder) {
        bytes = builder.bytes;
        templateType = builder.templateType;
        reportFormat = builder.reportFormat;
        fileName = builder.fileName;
        email = builder.email;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public TemplateType getTemplateType() {
        return templateType;
    }

    public ReportFormat getReportFormat() {
        return reportFormat;
    }

    public String getFileName() {
        return fileName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GeneratedReport that = (GeneratedReport) o;
        return Objects.deepEquals(bytes, that.bytes) && templateType == that.templateType && reportFormat == that.reportFormat && Objects.equals(fileName, that.fileName) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(bytes), templateType, reportFormat, fileName, email);
    }

    @Override
    public String toString() {
        return "GeneratedReport{" +
                "bytes=" + Arrays.toString(bytes) +
                ", templateType=" + templateType +
                ", reportFormat=" + reportFormat +
                ", fileName='" + fileName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private byte[] bytes;
        private TemplateType templateType;
        private ReportFormat reportFormat;
        private String fileName;
        private String email;

        private Builder() {
        }


        public Builder bytes(byte[] bytes) {
            this.bytes = bytes;
            return this;
        }

        public Builder templateType(TemplateType templateType) {
            this.templateType = templateType;
            return this;
        }

        public Builder reportFormat(ReportFormat reportFormat) {
            this.reportFormat = reportFormat;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public GeneratedReport build() {
            return new GeneratedReport(this);
        }
    }
}
