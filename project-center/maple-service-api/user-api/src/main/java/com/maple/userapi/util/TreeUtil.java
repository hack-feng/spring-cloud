package com.maple.userapi.util;

import com.maple.userapi.bean.BaseResources;
import com.maple.userapi.vo.MenuTree;
import com.maple.userapi.vo.TreeNode;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhua
 * @date 2019/8/4
 */
@UtilityClass
public class TreeUtil {

	/**
	 * 两层循环实现建树
	 *
	 * @param treeNodes 传入的树节点列表
	 * @return
	 */
	public <T extends TreeNode> List<T> build(List<T> treeNodes, Object root) {
		List<T> trees = new ArrayList<>();
		for (T treeNode : treeNodes) {
			if (root.equals(treeNode.getParentId())) {
				trees.add(treeNode);
			}
			for (T it : treeNodes) {
				if (it.getParentId() == treeNode.getId()) {
					if (treeNode.getChildren() == null) {
						treeNode.setChildren(new ArrayList<>());
					}
					treeNode.add(it);
				}
			}
		}
		return trees;
	}

	/**
	 * 使用递归方法建树
	 *
	 * @param treeNodes
	 * @return
	 */
	public <T extends TreeNode> List<T> buildByRecursive(List<T> treeNodes, Object root) {
		List<T> trees = new ArrayList<T>();
		if(treeNodes == null || treeNodes.size() == 0) {
			return trees;
		}
		for (T treeNode : treeNodes) {
			if (root.equals(treeNode.getParentId())) {
				trees.add(findChildren(treeNode, treeNodes));
			}
		}
		return trees;
	}

	/**
	 * 递归查找子节点
	 *
	 * @param treeNodes
	 * @return
	 */
	public <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
		for (T it : treeNodes) {
			if (treeNode.getId() == it.getParentId()) {
				if (treeNode.getChildren() == null) {
					treeNode.setChildren(new ArrayList<>());
				}
				treeNode.add(findChildren(it, treeNodes));
			}
		}
		return treeNode;
	}

	/**
	 * 通过sysMenu创建树形节点
	 *
	 * @param permission
	 * @param root
	 * @return
	 */
	public List<MenuTree> buildTree(List<BaseResources> permission, int root) {
		List<MenuTree> trees = new ArrayList<>();
		if(permission == null || permission.size() == 0) {
			return trees;
		}
		MenuTree node;
		for (BaseResources p : permission) {
			node = new MenuTree(p);
			trees.add(node);
		}
		return TreeUtil.build(trees, root);
	}
}
