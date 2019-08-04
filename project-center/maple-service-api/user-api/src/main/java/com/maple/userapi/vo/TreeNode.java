package com.maple.userapi.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class TreeNode {
	protected int id;
	protected int parentId;
	protected boolean disabled;
	protected boolean checked;
	protected boolean expand;
	protected List<TreeNode> children = new ArrayList<TreeNode>();

	public void add(TreeNode node) {
		children.add(node);
	}
}
