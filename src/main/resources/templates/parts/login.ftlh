<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group">
            <label class="col-sm-2 col-form-label" for="username">Username</label>
            <input type="text" class="col-sm-6 ${(usernameError??)?string('is-invalid', '')}"
                   id="username" name="username" placeholder="Username" value="<#if user??>${user.username}</#if>">
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label class="col-sm-2 col-form-label" for="password">Password</label>
            <input type="password" class="col-sm-6 ${(passwordError??)?string('is-invalid', '')}"
                   id="password" name="password" placeholder="Password">
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
        </div>
        <#if isRegisterForm>
            <div class="form-group">
                <label class="col-sm-2 col-form-label" for="passwordConfirm">Password confirmation</label>
                <input type="password" class="col-sm-6 ${(passwordConfirmError??)?string('is-invalid', '')}"
                       id="passwordConfirm" name="passwordConfirm" placeholder="Confirm your password">
                <#if passwordConfirmError??>
                    <div class="invalid-feedback">
                        ${passwordConfirmError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <label class="col-sm-2 col-form-label" for="email">Email</label>
                <input type="email" class="col-sm-6 ${(emailError??)?string('is-invalid', '')}"
                       id="email" name="email" placeholder="email@email.em" value="<#if user??>${user.email}</#if>">
                <#if emailError??>
                    <div class="invalid-feedback">
                        ${emailError}
                    </div>
                </#if>
            </div>
<#--            <form action="?" method="POST">-->
<#--                <div class="g-recaptcha" data-sitekey="6Ld5NMcZAAAAAD8YBXHNv09c4tGgA8RR6nYW_prg"></div>-->
<#--                <br/>-->
<#--                <input type="submit" value="Submit">-->
<#--            </form>-->
            <div class="col-sm-6">
                <div class="g-recaptcha" data-sitekey="6Ld5NMcZAAAAAD8YBXHNv09c4tGgA8RR6nYW_prg"></div>
                <#if captchaError??>
                    <br>
                    <div class="alert alert-danger" role="alert">
                        ${captchaError}
                    </div>
                </#if>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm><a href="/registration">Registration</a></#if>
        <button type="submit" class="btn btn-primary">
            <#if isRegisterForm>Sign Up<#else>Sign In</#if></button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">
            Sign Out
        </button>
    </form>
</#macro>