package net.certiv.antlr.dt.vis.model;

public class TreeConnector {

	private TreeNode source;
	private TreeNode target;

	public TreeConnector(TreeNode source, TreeNode target) {
		this.source = source;
		this.target = target;
	}

	public TreeNode getTarget() {
		return this.target;
	}

	public void setTarget(TreeNode target) {
		this.target = target;
	}

	public TreeNode getSource() {
		return this.source;
	}

	public void setSource(TreeNode source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return String.format("%s -> %s", source.toString(), target.toString());
	}
}
