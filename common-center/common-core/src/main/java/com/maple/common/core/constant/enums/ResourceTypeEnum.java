package com.maple.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhua
 * @date 2019/7/30
 * 资源类型
 */
@Getter
@AllArgsConstructor
public enum ResourceTypeEnum {
	/**
	 * 图片资源
	 */
	IMAGE("image", "图片资源"),

	/**
	 * xml资源
	 */
	XML("xml", "xml资源"),

	/**
	 * xls资源
	 */
	XLS("xls", "xls资源"),

	/**
	 * xlsx资源
	 */
	XLSX("xlsx", "xlsx资源"),

	/**
	 * doc资源
	 */
	DOC("doc", "doc资源"),

	/**
	 * docx资源
	 */
	DOCX("docx", "docx资源"),

	/**
	 * pdf资源
	 */
	PDF("pdf", "pdf资源");

	/**
	 * 类型
	 */
	private final String type;
	/**
	 * 描述
	 */
	private final String description;
}
