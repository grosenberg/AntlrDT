package net.certiv.antlrdt.graph;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

public class EventManager {

	private final List<ServiceRegistration<EventHandler>> registrations = new ArrayList<>();

	private BundleContext ctx;
	private ServiceReference<EventAdmin> serviceRef;
	private EventAdmin eventAdmin;

	public void start() {
		if (ctx == null) {
			ctx = FrameworkUtil.getBundle(getClass()).getBundleContext();
			serviceRef = ctx.getServiceReference(EventAdmin.class);
			eventAdmin = ctx.getService(serviceRef);
		}
	}

	public void stop() {
		if (ctx != null) {
			eventAdmin = null;
			for (ServiceRegistration<EventHandler> reg : registrations) {
				reg.unregister();
			}
			registrations.clear();
			ctx.ungetService(serviceRef);
			serviceRef = null;
			ctx = null;
		}
	}

	public ServiceRegistration<EventHandler> addHandler(EventHandler handler, String topics) {
		Dictionary<String, String> props = new Hashtable<>();
		props.put(EventConstants.EVENT_TOPIC, topics);
		ServiceRegistration<EventHandler> reg = ctx.registerService(EventHandler.class, handler, props);
		registrations.add(reg);
		return reg;
	}

	public void removeHandler(ServiceRegistration<EventHandler> reg) {
		reg.unregister();
		registrations.remove(reg);
	}

	public void postEvent(String topic, boolean state) {
		Map<String, Object> props = new HashMap<>();
		props.put(IEvents.STATE, state);
		postEvent(topic, props);
	}

	public void postEvent(String topic, Map<String, Object> props) {
		if (eventAdmin != null) eventAdmin.postEvent(new Event(topic, props));
	}
}
