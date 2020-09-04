package kz.ok.training.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaResponseDto {

    private boolean isSuccess;

    @JsonAlias("error-codes")
    private Set<String> errorCods;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Set<String> getErrorCods() {
        return errorCods;
    }

    public void setErrorCods(Set<String> errorCods) {
        this.errorCods = errorCods;
    }
}