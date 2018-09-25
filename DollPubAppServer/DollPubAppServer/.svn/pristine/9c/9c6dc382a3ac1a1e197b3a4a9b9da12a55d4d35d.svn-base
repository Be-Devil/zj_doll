package com.imlianai.dollpub.app.modules.publics.rpctest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;

@Component("test.testRpcCall")
public class CmdRpcTest extends RootCmd {

//	@Reference//(url="dubbo://localhost:2888")
//	private IWebBusinessRemoteHandlerService webBusinessRemoteHandlerService;

	public String doCommand(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ret="aaabbb";
		try {
			List<Object> objLst= new ArrayList<Object>();

			
			//callWeb
			/*objLst= webBusinessRemoteHandlerService.testWebCall(objLst, "app==>testWebCall",
					Byte.valueOf("1"), 
					Short.valueOf("2"),
					3,
					4l,
					'编',
					false,
					7.7f,
					Double.valueOf("8.88")
				);	*/	
			
			ret= "";
			for (int i = 0; i < objLst.size(); i++) {
				System.out.println(objLst.get(i).toString());
				ret= ret+"<br />"+objLst.get(i).toString();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			ret= "got error: "+e.getMessage();
		}
		
		return responseJson(response, ret, null);
	}
}
