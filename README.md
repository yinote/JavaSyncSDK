JavaSyncSDK
===========

JavaSyncSDK实现了YDEP（Yinote Data Exchagne Protocol）同步协议。
该SDK中，包含了一个实例程序（ToDo List），可供大家参考。

代码结构如下：

    src
      yinote
        ydep         +++ ydep协议实现
          exception        ydep协议预定义异常，包含处理远程请求所返回的错误信息。
          local            为了使用ydep，本地系统所需要实现的接口
          remote           ydep协议远程请求处理中心
          service          同步服务的核心处理器
          util             帮助类
        android      +++ demo示例
          todo             todo实现
          todoexample      todo启动程序
          ydep             todo的ydep接口实现
          
在ToDoAcitivity.java中我们可以看到如下启动同步的代码调用:
```java
// 配置远程服务的地址，以及端口，设置用户信息
RemoteSetting settings = new RemoteSetting("42.96.141.70", 9000, new UserInfo("accT1"));
try {
	Log.d("ToDoActivity", "invoke sync");
	new SyncServiceImpl(settings, new LocalSyncStateStoreImpl()) // 设置本地同步状态管理器的实现
		.enableTodoSync(new LocalTodoStoreImpl())                // 启用针对Todo的同步功能
		.doSync();                                               // 执行同步
} catch (JSONException e) {
    // 处理JSON解析异常
	Log.e("ToDoActivity", e.getMessage());
} catch (RequestError e) {
    // 处理远程请求访问异常，这个包括系统返回的错误信息
	Log.e("ToDoActivity", e.getMessage());
}
```

为了支持同步，您必须要实现LocalSyncStateStore接口，该接口提供了对于SyncState（同步状态）的本地存储能力。

如果您的应用支持更多的翼笔记数据，如笔记，笔记本，标签，资源等。
您只需要实现对应的本地Java接口，并在创建SyncServiceImpl时进行启用即可。
可实现的接口如下

    LocalNotebookStore
    LocalNoteStore
    LocalTagStore
    LocalResourceStore
    LocalTodoStore


其中RequestError，包含如下的数据，您可以根据情况进行处理
```java
public class RequestError extends Exception {
    // 异常信息
    private String message = null;
    // 异常编码
	private String code = null;
    // 发生异常的请求的uri
	private String uri = null;
    // 请求的ID，可以通过该ID获取更为详细的异常流程
	private String requestId = null;
	
	public RequestError(String jsonStr) {
		try {
			JSONObject json = new JSONObject(jsonStr);
			this.code = json.getString("code");
			this.message = json.getString("message");
			this.uri = json.getString("uri");
			this.requestId = json.getString("requestId");
		} catch (JSONException e) {
			this.message = jsonStr;
		}
	}
}
```
