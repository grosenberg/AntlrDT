package net.certiv.antlr.dt.vis.model;

public class PathConnector {

	private PathNode source;
	private PathNode target;

	public PathConnector(PathNode source, PathNode target) {
		this.source = source;
		this.target = target;
	}

	public PathNode getSource() {
		return this.source;
	}

	public void setSource(PathNode source) {
		this.source = source;
	}

	public PathNode getTarget() {
		return this.target;
	}

	public void setTarget(PathNode target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return String.format("%s -> %s", source.toString(), target.toString());
	}
}
