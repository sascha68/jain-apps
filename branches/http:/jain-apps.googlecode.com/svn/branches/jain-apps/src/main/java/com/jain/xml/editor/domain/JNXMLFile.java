package com.jain.xml.editor.domain;

import com.jain.addon.web.bean.JPropertyType;
import com.jain.addon.web.bean.annotation.JNIAttribute;

public class JNXMLFile {
	private String rootElementName;
	private String xmlContent;
	private byte[] xmlFile;

	@JNIAttribute(lable = "xml.editor.file.root.tag.name")
	public String getRootElementName() {
		return rootElementName;
	}

	public void setRootElementName(String rootElementName) {
		this.rootElementName = rootElementName;
	}

	@JNIAttribute(lable = "xml.editor.file.content.name", type = JPropertyType.TEXT_AREA)
	public String getXmlContent() {
		return xmlContent;
	}

	public void setXmlContent(String xmlContent) {
		this.xmlContent = xmlContent;
	}

	@JNIAttribute(lable = "xml.editor.upload.file.name", type = JPropertyType.FILE)
	public byte[] getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(byte[] xmlFile) {
		this.xmlFile = xmlFile;
	}
}
