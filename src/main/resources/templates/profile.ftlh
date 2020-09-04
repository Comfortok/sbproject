<#import "parts/common.ftlh" as c>
<@c.page>
    ${message!}

    <h5>${username}</h5>
    <form method="post">
        <div class="form-group">
            <label class="col-sm-2 col-form-label" for="password">Password</label>
            <input type="password" class="col-sm-6" id="password" name="password" placeholder="Password">
        </div>
            <div class="form-group">
                <label class="col-sm-2 col-form-label" for="email">Email</label>
                <input type="email" class="col-sm-6" id="email" name="email" placeholder="email@email.em" value="${email!''}">
            </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">
            Save</button>
    </form>
</@c.page>