<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="form-group">
        <form action="/main" class="form-inline">
            <input type="text" name="filter" class="form-control" value="${filter!}" placeholder="Enter your tag"/>
            <button type="submit" class="btn btn-primary ml-2">Search</button>
        </form>

        <div>
            <span><a href="/user/userList">User List</a> </span>
        </div>

        <a class="btn btn-primary" data-toggle="collapse" href="#collapseMessage" role="button" aria-expanded="false"
           aria-controls="collapseExample">
            Add new message
        </a>
        <div class="collapse <#if message??>show</#if>" id="collapseMessage">
            <div class="form-group">
                <form method="post" action="/addMessage" enctype="multipart/form-data">
                    <div class="form-group">
                        <input type="text" name="text" class="form-control mt-2 ${(textError??)?string('is-invalid', '')}"
                               value="<#if message??>${message.text}</#if>" placeholder="Enter the text value"/>
                        <#if textError??>
                        <div class="invalid-feedback">
                            ${textError}
                        </div>
                        </#if>
                    </div>
                    <div class="form-group">
                        <input type="text" name="tag" class="form-control" placeholder="Enter the tag"
                               value="<#if message??>${message.tag}</#if>"/>
                        <#if tagError??>
                            <div class="invalid-feedback">
                                ${tagError}
                            </div>
                        </#if>
                    </div>
                    <div class="form-group">
                        <div class="custom-file">
                            <input type="file" name="file" id="customFile">
                            <label class="custom-file-label" for="customFile">Choose a file</label>
                        </div>
                    </div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Send</button>
                    </div>
                </form>
            </div>
        </div>

        <div class="card-columns">
            <#list messages as message>
                <div class="card my-3">
                    <#if message.filename??>
                        <img src="/img/${message.filename}" class="card-img-top" alt="...">
                    </#if>
                    <div class="m-2">
                        <span>${message.text}</span>
                        <i>${message.tag}</i>
                    </div>
                    <div class="card-footer text-muted">
                        ${message.authorName}
                    </div>
                </div>
            <#else>
                No messages
            </#list>
        </div>
    </div>
</@c.page>