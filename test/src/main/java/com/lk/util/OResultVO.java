package com.lk.util;

import java.io.Serializable;

import com.alibaba.dubbo.common.json.JSONObject;

/**
 * service返回对象
 * @author kang.lu
 */
public class OResultVO implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4740142776111225391L;

	/**
	 * 成功标识
	 */
	private Boolean success;
	
	/**
	 * 消息代码
	 */
	private int reCode;
	
	/**
	 * 消息描述
	 */
	private Object context;

	public OResultVO() {}

	public OResultVO(Boolean success, int reCode, Object context)
	{
		super();
		this.success = success;
		this.reCode = reCode;
		this.context = context;
	}
	
	public OResultVO setVaule(Boolean success, int reCode, Object context)
	{
		this.success = success;
		this.reCode = reCode;
		this.context = context;
		return this;
	}

	public int getReCode()
	{
		return reCode;
	}

	public void setReCode(int reCode)
	{
		this.reCode = reCode;
	}

	public Boolean getSuccess()
	{
		return success;
	}

	public void setSuccess(Boolean success)
	{
		this.success = success;
	}

	public Object getContext()
	{
		return context;
	}

	public void setContext(Object context)
	{
		this.context = context;
	}

	@Override
	public String toString()
	{
		return "OResultVO [success=" + success + ", reCode=" + reCode + ", context=" + context + "]";
	}
	
	public  String toJson(){
		JSONObject obj = new JSONObject();
		obj.put("success", success);
		obj.put("reCode", reCode);
		obj.put("context", context);
		return obj.toString();
	}
	
}
