package net.certiv.antlrdt.graph.models;

public class EdgeModel extends BaseModel {

	private NodeModel source;
	private NodeModel target;
	private boolean connected;

	public EdgeModel(NodeModel source, NodeModel target) {
		connect(source, target);
	}

	public NodeModel getSource() {
		return source;
	}

	public void setSource(NodeModel source) {
		if (this.source != source) {
			NodeModel prior = this.source;
			this.source = source;
			fire(PROP_SOURCE, prior, source);
		}
	}

	public NodeModel getTarget() {
		return target;
	}

	public void setTarget(NodeModel target) {
		if (this.target != target) {
			NodeModel prior = this.target;
			this.target = target;
			fire(PROP_TARGET, prior, target);
		}
	}

	public void connect(NodeModel source, NodeModel target) {
		if (source == null || target == null || source == target) {
			throw new IllegalArgumentException();
		}
		disconnect();
		this.source = source;
		this.target = target;
		reconnect();
	}

	public void disconnect() {
		if (connected) {
			source.removeOutgoingConnection(this);
			target.removeIncomingConnection(this);
			connected = false;
		}
	}

	public void reconnect() {
		if (!connected) {
			source.addOutgoingConnection(this);
			target.addIncomingConnection(this);
			connected = true;
		}
	}

	public void dispose() {
		source = null;
		target = null;
		connected = false;
	}
}
