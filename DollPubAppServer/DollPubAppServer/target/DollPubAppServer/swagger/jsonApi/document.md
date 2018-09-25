#增景娃娃机项目文档


## HTTP:///DollPubAppServer


增景娃娃机项目文档



**Version** v1

[**Terms of Service**]()












# APIs


## /api/cmsPush/apply






### POST


<a id="apply">上机</a>

上机成功后返回操作ID







#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/操作推币机请求对象">操作推币机请求对象</a> 
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/推币机操作返回对象">推币机操作返回对象</a>|















## /api/cmsPush/finish






### POST


<a id="finish">主动下机</a>

主动下机操作







#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/操作推币机请求对象">操作推币机请求对象</a> 
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/BaseRespVO">BaseRespVO</a>|















## /api/cmsPush/getStatus






### POST


<a id="getStatus">推币机状态</a>

推币机状态







#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/操作推币机请求对象">操作推币机请求对象</a> 
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/BaseRespVO">BaseRespVO</a>|















## /api/cmsPush/operate






### POST


<a id="operate">摆动</a>

摆动







#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/操作推币机请求对象">操作推币机请求对象</a> 
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/BaseRespVO">BaseRespVO</a>|















## /api/cmsPush/push






### POST


<a id="push">投币</a>

投币相当于上机，投币之后超过30秒未继续投币则自动结束上机







#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/操作推币机请求对象">操作推币机请求对象</a> 
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/推币机操作返回对象">推币机操作返回对象</a>|















## /api/cmsPush/query






### POST


<a id="query">查询结果</a>

查询结果







#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/推币机操作结果查询请求对象">推币机操作结果查询请求对象</a> 
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/查询操作结果返回对象">查询操作结果返回对象</a>|















## /api/setting/queryMachineSetting






### POST


<a id="queryMachineSetting">查询机器设置</a>

查询机器设置







#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/机器相关设置查询请求对象">机器相关设置查询请求对象</a> 
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/BaseRespVO">BaseRespVO</a>|















## /api/setting/setMachineBasicData






### POST


<a id="setMachineBasicData">发送机器基础数据设置指令</a>

发送机器基础数据设置指令







#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/机器基础数据设置请求对象">机器基础数据设置请求对象</a> 
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/BaseRespVO">BaseRespVO</a>|















## /api/setting/setMachineClawVoltage






### POST


<a id="setMachineClawVoltage">发送机器爪力电压设置指令</a>

发送机器爪力电压设置指令







#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/机器爪力电压设置请求对象">机器爪力电压设置请求对象</a> 
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/BaseRespVO">BaseRespVO</a>|















## /api/shipping/order






### POST


<a id="order">申请发货接口</a>

申请发货







#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/娃娃机列表请求对象">娃娃机列表请求对象</a> 
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/BaseRespVO">BaseRespVO</a>|















## /api/wawa/apply






### POST


<a id="apply">申请上机接口</a>

申请上机接口







#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/娃娃机上机操作请求对象">娃娃机上机操作请求对象</a> 
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/娃娃机上机操作响应对象">娃娃机上机操作响应对象</a>|















## /api/wawa/applyType






### POST


<a id="applyType">线下机上机接口(新版子)</a>

线下机上机接口(新版子)







#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/DollBusApplyTypeReqVO">DollBusApplyTypeReqVO</a> 
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/娃娃机上机操作响应对象">娃娃机上机操作响应对象</a>|















## /api/wawa/control






### POST


<a id="control">娃娃机操作接口</a>

娃娃机操作接口







#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/娃娃机操作请求对象">娃娃机操作请求对象</a> 
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/BaseRespVO">BaseRespVO</a>|















## /api/wawa/downClaw






### POST


<a id="downClaw">娃娃机下爪操作接口</a>

娃娃机下爪操作接口







#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/娃娃机下爪请求对象">娃娃机下爪请求对象</a> 
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/BaseRespVO">BaseRespVO</a>|















## /api/wawa/list






### POST


<a id="list">获取商户娃娃机列表接口</a>

获取商户娃娃机列表







#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/娃娃机列表请求对象">娃娃机列表请求对象</a> 
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/娃娃机列表响应对象">娃娃机列表响应对象</a>|















## /api/wawa/queryResult






### POST


<a id="queryResult">查询捉取结果接口</a>

查询捉取结果接口







#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/娃娃抓取结果请求">娃娃抓取结果请求</a> 
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/娃娃机抓取结果响应对象">娃娃机抓取结果响应对象</a>|















## /api/wawa/querySetting






### POST


<a id="querySetting">查询机器配置接口(弃用)</a>

查询机器配置接口(弃用)







#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/娃娃设置查询">娃娃设置查询</a> 
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/娃娃机设置响应对象">娃娃机设置响应对象</a>|















## /api/wawa/setting






### POST


<a id="setting">设置机器配置接口(弃用)</a>

设置机器配置接口(弃用)







#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/娃娃设置">娃娃设置</a> 
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/娃娃机设置响应对象">娃娃机设置响应对象</a>|















## /api/wawa/video






### POST


<a id="video">获取录播接口</a>

获取录播







#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/娃娃录播生成请求">娃娃录播生成请求</a> 
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/DollVideoRespVO">DollVideoRespVO</a>|















## /商户的回调地址






### POST


<a id="list">捉取结果回调</a>

捉取结果回调







#### Request



##### Parameters






#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | successful operation | <a href="#/definitions/操作记录传输对象">操作记录传输对象</a>|
















# Definitions

## <a name="/definitions/BaseRespVO">BaseRespVO</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>result</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>state</td>
            <td>
                
                    
                    boolean
                
            </td>
            <td>optional</td>
            <td>请求状态 true-成功 false-失败</td>
            <td></td>
        </tr>
    
        <tr>
            <td>msg</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>错误信息</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/DollBusApplyTypeReqVO">DollBusApplyTypeReqVO</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>uid</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>optional</td>
            <td>uid</td>
            <td></td>
        </tr>
    
        <tr>
            <td>loginKey</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>ts</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>每次请求的秒时间戳</td>
            <td></td>
        </tr>
    
        <tr>
            <td>sign</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>签名</td>
            <td></td>
        </tr>
    
        <tr>
            <td>appId</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>商户id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>busId</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>机器id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>type</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>上机下爪类型(1：默认抓力，2：强爪抓力)</td>
            <td></td>
        </tr>
    
        <tr>
            <td>remark</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>备注</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/DollVideoRespVO">DollVideoRespVO</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>result</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>state</td>
            <td>
                
                    
                    boolean
                
            </td>
            <td>optional</td>
            <td>请求状态 true-成功 false-失败</td>
            <td></td>
        </tr>
    
        <tr>
            <td>msg</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>错误信息</td>
            <td></td>
        </tr>
    
        <tr>
            <td>saveRes</td>
            <td>
                
                    <a href="#/definitions/SaveRet">SaveRet</a>
                    
                
            </td>
            <td>optional</td>
            <td>录播结果</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/SaveRet">SaveRet</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>fname</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>文件名</td>
            <td></td>
        </tr>
    
        <tr>
            <td>error</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>错误原因</td>
            <td></td>
        </tr>
    
        <tr>
            <td>persistentID</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/娃娃录播生成请求">娃娃录播生成请求</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>uid</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>optional</td>
            <td>uid</td>
            <td></td>
        </tr>
    
        <tr>
            <td>loginKey</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>ts</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>每次请求的秒时间戳</td>
            <td></td>
        </tr>
    
        <tr>
            <td>sign</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>签名</td>
            <td></td>
        </tr>
    
        <tr>
            <td>appId</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>商户id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>streamUrl</td>
            <td>
                
                    
                    string
                
            </td>
            <td>required</td>
            <td>流地址</td>
            <td></td>
        </tr>
    
        <tr>
            <td>start</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>required</td>
            <td>开始时间秒戳</td>
            <td></td>
        </tr>
    
        <tr>
            <td>end</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>required</td>
            <td>结束时间秒戳</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/娃娃抓取结果请求">娃娃抓取结果请求</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>uid</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>optional</td>
            <td>uid</td>
            <td></td>
        </tr>
    
        <tr>
            <td>loginKey</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>ts</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>每次请求的秒时间戳</td>
            <td></td>
        </tr>
    
        <tr>
            <td>sign</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>签名</td>
            <td></td>
        </tr>
    
        <tr>
            <td>appId</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>商户id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>optId</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>required</td>
            <td>操作id</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/娃娃机上机操作响应对象">娃娃机上机操作响应对象</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>result</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>state</td>
            <td>
                
                    
                    boolean
                
            </td>
            <td>optional</td>
            <td>请求状态 true-成功 false-失败</td>
            <td></td>
        </tr>
    
        <tr>
            <td>msg</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>消息</td>
            <td></td>
        </tr>
    
        <tr>
            <td>optId</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>optional</td>
            <td>操作记录id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>gameTime</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>游戏时间,单位秒</td>
            <td></td>
        </tr>
    
        <tr>
            <td>deviceId</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>设备id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>success</td>
            <td>
                
                    
                    boolean
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/娃娃机上机操作请求对象">娃娃机上机操作请求对象</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>uid</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>required</td>
            <td>用户id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>loginKey</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>ts</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>每次请求的秒时间戳</td>
            <td></td>
        </tr>
    
        <tr>
            <td>sign</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>签名</td>
            <td></td>
        </tr>
    
        <tr>
            <td>appId</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>商户id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>busId</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>机器id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>remark</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>备注</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/娃娃机下爪请求对象">娃娃机下爪请求对象</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>uid</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>required</td>
            <td>用户id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>loginKey</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>ts</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>每次请求的秒时间戳</td>
            <td></td>
        </tr>
    
        <tr>
            <td>sign</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>签名</td>
            <td></td>
        </tr>
    
        <tr>
            <td>appId</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>商户id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>busId</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>机器id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>optId</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>required</td>
            <td>操作id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>type</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>机器操作指令:【0】默认爪，【1】弱爪，【2】强爪</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/娃娃机列表响应对象">娃娃机列表响应对象</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>result</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>state</td>
            <td>
                
                    
                    boolean
                
            </td>
            <td>optional</td>
            <td>请求状态 true-成功 false-失败</td>
            <td></td>
        </tr>
    
        <tr>
            <td>msg</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>错误信息</td>
            <td></td>
        </tr>
    
        <tr>
            <td>dollBusList</td>
            <td>
                
                
                    array[<a href="#/definitions/娃娃机数据传输对象">娃娃机数据传输对象</a>]
                
                
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/娃娃机列表请求对象">娃娃机列表请求对象</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>uid</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>optional</td>
            <td>uid</td>
            <td></td>
        </tr>
    
        <tr>
            <td>loginKey</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>ts</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>每次请求的秒时间戳</td>
            <td></td>
        </tr>
    
        <tr>
            <td>sign</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>签名</td>
            <td></td>
        </tr>
    
        <tr>
            <td>appId</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>商户id</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/娃娃机抓取结果响应对象">娃娃机抓取结果响应对象</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>result</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>state</td>
            <td>
                
                    
                    boolean
                
            </td>
            <td>optional</td>
            <td>请求状态 true-成功 false-失败</td>
            <td></td>
        </tr>
    
        <tr>
            <td>msg</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>错误信息</td>
            <td></td>
        </tr>
    
        <tr>
            <td>optRecord</td>
            <td>
                
                    <a href="#/definitions/操作记录传输对象">操作记录传输对象</a>
                    
                
            </td>
            <td>optional</td>
            <td>结果</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/娃娃机操作请求对象">娃娃机操作请求对象</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>uid</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>required</td>
            <td>用户id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>loginKey</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>ts</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>每次请求的秒时间戳</td>
            <td></td>
        </tr>
    
        <tr>
            <td>sign</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>签名</td>
            <td></td>
        </tr>
    
        <tr>
            <td>appId</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>商户id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>busId</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>机器id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>optId</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>required</td>
            <td>操作id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>type</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>机器操作指令:【1】向前移动，【2】向后移动，【3】向左移动，【4】向右移动，【6】下抓，【7】停止移动</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/娃娃机数据传输对象">娃娃机数据传输对象</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>busId</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>机器id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>dollId</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>娃娃id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>name</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>设备名称</td>
            <td></td>
        </tr>
    
        <tr>
            <td>deviceId</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>设备id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>cover</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>封面</td>
            <td></td>
        </tr>
    
        <tr>
            <td>status</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>设备状态 0-空闲，1-游戏中,2-故障状态</td>
            <td></td>
        </tr>
    
        <tr>
            <td>stream1</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>正面流地址</td>
            <td></td>
        </tr>
    
        <tr>
            <td>stream2</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>侧面流地址</td>
            <td></td>
        </tr>
    
        <tr>
            <td>stream1Realy</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>正面流地址-操作者</td>
            <td></td>
        </tr>
    
        <tr>
            <td>stream2Realy</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>侧面流地址-操作者</td>
            <td></td>
        </tr>
    
        <tr>
            <td>type</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>virtual</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/娃娃机设置响应对象">娃娃机设置响应对象</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>result</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>state</td>
            <td>
                
                    
                    boolean
                
            </td>
            <td>optional</td>
            <td>请求状态 true-成功 false-失败</td>
            <td></td>
        </tr>
    
        <tr>
            <td>msg</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>错误信息</td>
            <td></td>
        </tr>
    
        <tr>
            <td>busId</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>设备id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>gameTime</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>游戏时间，单位秒</td>
            <td></td>
        </tr>
    
        <tr>
            <td>probability</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>捉力概率，出奖概率取值范围（1-250）,意义为平均捉中一次所需的累计游戏次数</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/娃娃设置">娃娃设置</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>uid</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>optional</td>
            <td>uid</td>
            <td></td>
        </tr>
    
        <tr>
            <td>loginKey</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>ts</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>每次请求的秒时间戳</td>
            <td></td>
        </tr>
    
        <tr>
            <td>sign</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>签名</td>
            <td></td>
        </tr>
    
        <tr>
            <td>appId</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>商户id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>busId</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>机器id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>type</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>设置类型 1-游戏时间 2-概率 3-礼品出口</td>
            <td></td>
        </tr>
    
        <tr>
            <td>value</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>设置值，游戏时间范围为5-60秒；概率范围为1-250；礼品出口0-左前 1-左后</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/娃娃设置查询">娃娃设置查询</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>uid</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>optional</td>
            <td>uid</td>
            <td></td>
        </tr>
    
        <tr>
            <td>loginKey</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>ts</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>每次请求的秒时间戳</td>
            <td></td>
        </tr>
    
        <tr>
            <td>sign</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>签名</td>
            <td></td>
        </tr>
    
        <tr>
            <td>appId</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>商户id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>busId</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>机器id</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/推币机操作结果查询请求对象">推币机操作结果查询请求对象</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>uid</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>optional</td>
            <td>uid</td>
            <td></td>
        </tr>
    
        <tr>
            <td>loginKey</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>ts</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>每次请求的秒时间戳</td>
            <td></td>
        </tr>
    
        <tr>
            <td>sign</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>签名</td>
            <td></td>
        </tr>
    
        <tr>
            <td>appId</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>商户id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>optId</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>required</td>
            <td>操作流水ID</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/推币机操作返回对象">推币机操作返回对象</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>result</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>state</td>
            <td>
                
                    
                    boolean
                
            </td>
            <td>optional</td>
            <td>请求状态 true-成功 false-失败</td>
            <td></td>
        </tr>
    
        <tr>
            <td>msg</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>错误信息</td>
            <td></td>
        </tr>
    
        <tr>
            <td>optId</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>optional</td>
            <td>操作ID</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/操作推币机请求对象">操作推币机请求对象</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>uid</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>optional</td>
            <td>uid</td>
            <td></td>
        </tr>
    
        <tr>
            <td>loginKey</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>ts</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>每次请求的秒时间戳</td>
            <td></td>
        </tr>
    
        <tr>
            <td>sign</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>签名</td>
            <td></td>
        </tr>
    
        <tr>
            <td>appId</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>商户id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>busId</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>机器ID</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/操作记录传输对象">操作记录传输对象</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>optId</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>optional</td>
            <td>操作id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>busId</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>设备id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>uid</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>optional</td>
            <td>用户id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>result</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>抓取结果【1:成功,0:失败】</td>
            <td></td>
        </tr>
    
        <tr>
            <td>remark</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>备注</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/机器基础数据设置请求对象">机器基础数据设置请求对象</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>uid</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>optional</td>
            <td>uid</td>
            <td></td>
        </tr>
    
        <tr>
            <td>loginKey</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>ts</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>每次请求的秒时间戳</td>
            <td></td>
        </tr>
    
        <tr>
            <td>sign</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>签名</td>
            <td></td>
        </tr>
    
        <tr>
            <td>appId</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>商户id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>busId</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>机器id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>language</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>语言(0为英文 ，1为中文),取值范围（0-1）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>advertMusicOnOff</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>广告音乐开关(0音乐无，1音乐有),取值范围（0-1）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>advertMusicIntervalTime</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>广告音乐间隔时间(当广告音乐开关为1有效),取值范围（0-30）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>coin</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>币数(币数设置),取值范围（1-10）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>inning</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>局数(局数设置),取值范围（1-10）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>coinRetain</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>币数保留(0关机不保留，1关机保留),取值范围（0-1）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>playTime</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>游戏时间,取值范围（5-60)</td>
            <td></td>
        </tr>
    
        <tr>
            <td>gameType</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>游戏模式,取值范围（1-6）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>probability</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>出奖概率,取值范围（1-250）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>exitPosition</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>礼品出口位置(0左后角，1左前角),取值范围（0-1）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>airGrip</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>空中抓物(0为关，1为开),取值范围（0-1）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>bgMusic</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>背景音乐</td>
            <td></td>
        </tr>
    
        <tr>
            <td>gameMusic</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>游戏音乐</td>
            <td></td>
        </tr>
    
        <tr>
            <td>photoEye</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>光眼电平(0为常开，1为常闭),取值范围（0-1）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>shake</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>摇晃清分,取值范围（0-1）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>retain</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>保留,取值范围（0-1）</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/机器爪力电压设置请求对象">机器爪力电压设置请求对象</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>uid</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>optional</td>
            <td>uid</td>
            <td></td>
        </tr>
    
        <tr>
            <td>loginKey</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>ts</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>每次请求的秒时间戳</td>
            <td></td>
        </tr>
    
        <tr>
            <td>sign</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>签名</td>
            <td></td>
        </tr>
    
        <tr>
            <td>appId</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>商户id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>busId</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>机器id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>strongClawVoltage</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>强爪电压,取值范围十进制（130-475）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>weakClawVoltage</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>弱爪电压 ,取值范围十进制（20-200）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>weakClawBackVoltage</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>弱爪后电压,取值范围十进制（20-400）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>winVoltage</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>中奖电压,取值范围十进制（45-480）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>strongForceTime</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>强力维持时间,取值范围（1-30）08表示0.8秒</td>
            <td></td>
        </tr>
    
        <tr>
            <td>weakForceTime</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>弱力维持时间,取值范围（1-30）08表示0.8秒</td>
            <td></td>
        </tr>
    
        <tr>
            <td>strongToWeakType</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>强变弱方式(0强爪时间后变弱抓 1碰到微动后变弱抓),取值范围（0-1）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>payingOffLengthTime</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>放线长度时间,取值范围（5-100）,12表示1.2秒</td>
            <td></td>
        </tr>
    
        <tr>
            <td>closeClawSpeed</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>收爪速度,取值范围（0-20）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>riseDelay</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>上升延时,取值范围（1-30）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>dropDelayRatio</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>掉落延时比例，取值范围（0-99）</td>
            <td></td>
        </tr>
    
        <tr>
            <td>dropDelayTime</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>掉落延时时间，取值范围（1-30）</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/机器相关设置查询请求对象">机器相关设置查询请求对象</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>uid</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>optional</td>
            <td>uid</td>
            <td></td>
        </tr>
    
        <tr>
            <td>loginKey</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>ts</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>每次请求的秒时间戳</td>
            <td></td>
        </tr>
    
        <tr>
            <td>sign</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>签名</td>
            <td></td>
        </tr>
    
        <tr>
            <td>appId</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>商户id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>type</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>查询类型[1:普通数据查询，2:爪力电压查询，3:机器马达速度]</td>
            <td></td>
        </tr>
    
        <tr>
            <td>busId</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>required</td>
            <td>机器id</td>
            <td></td>
        </tr>
    
        <tr>
            <td>customerId</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/查询操作结果返回对象">查询操作结果返回对象</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>result</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>-</td>
            <td></td>
        </tr>
    
        <tr>
            <td>state</td>
            <td>
                
                    
                    boolean
                
            </td>
            <td>optional</td>
            <td>请求状态 true-成功 false-失败</td>
            <td></td>
        </tr>
    
        <tr>
            <td>msg</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>错误信息</td>
            <td></td>
        </tr>
    
        <tr>
            <td>optId</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>optional</td>
            <td>操作ID</td>
            <td></td>
        </tr>
    
        <tr>
            <td>busId</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>机器ID</td>
            <td></td>
        </tr>
    
        <tr>
            <td>uid</td>
            <td>
                
                    
                    integer (int64)
                
            </td>
            <td>optional</td>
            <td>用户ID</td>
            <td></td>
        </tr>
    
        <tr>
            <td>startTime</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>游戏开始时间(上机开始计算)</td>
            <td></td>
        </tr>
    
        <tr>
            <td>endTime</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>游戏结束时间(最后一次投币为准 ，超过xx秒未投币则自动结束)</td>
            <td></td>
        </tr>
    
        <tr>
            <td>intoCoin</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>投币个数</td>
            <td></td>
        </tr>
    
        <tr>
            <td>outCoin</td>
            <td>
                
                    
                    integer (int32)
                
            </td>
            <td>optional</td>
            <td>出币个数</td>
            <td></td>
        </tr>
    
</table>


